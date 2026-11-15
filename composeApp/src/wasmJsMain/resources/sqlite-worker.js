/*
 * androidx.sqlite の WebWorkerSQLiteDriver が話すメッセージプロトコルを実装した Web Worker。
 * SQLite 本体には @sqlite.org/sqlite-wasm を使い、OPFS (SAH Pool VFS) に永続化する。
 *
 * プロトコル: https://developer.android.com/reference/kotlin/androidx/sqlite/driver/web/WebWorkerSQLiteDriver
 *   open    { fileName }                -> { databaseId }
 *   prepare { databaseId, sql }         -> { statementId, parameterCount, columnNames }
 *   step    { statementId, bindings }   -> { rows, columnTypes }
 *   close   { statementId?, databaseId? }  (応答なし)
 */
import sqlite3InitModule from "@sqlite.org/sqlite-wasm";

let sqlite3Promise = null;
let poolUtil = null;

const databases = new Map();
const statements = new Map();
let nextDatabaseId = 1;
let nextStatementId = 1;

function initSqlite() {
  if (sqlite3Promise === null) {
    sqlite3Promise = sqlite3InitModule().then(async (sqlite3) => {
      try {
        poolUtil = await sqlite3.installOpfsSAHPoolVfs({ name: "todoy-opfs" });
      } catch (e) {
        // OPFS が使えない環境 (古いブラウザ・プライベートモード等) ではメモリ上の DB にフォールバックする
        console.warn("[sqlite-worker] OPFS is unavailable. Data will not be persisted.", e);
      }
      return sqlite3;
    });
  }
  return sqlite3Promise;
}

function openDatabase(sqlite3, fileName) {
  if (fileName === ":memory:" || poolUtil === null) {
    return new sqlite3.oo1.DB(":memory:", "c");
  }
  return new poolUtil.OpfsSAHPoolDb(fileName.startsWith("/") ? fileName : `/${fileName}`);
}

function normalizeBindings(bindings) {
  // Kotlin 側は疎な配列を送ってくることがあるので undefined を null に揃える
  return Array.from(bindings ?? [], (value) => (value === undefined ? null : value));
}

async function handle(data) {
  const sqlite3 = await initSqlite();
  switch (data.cmd) {
    case "open": {
      const databaseId = nextDatabaseId++;
      databases.set(databaseId, openDatabase(sqlite3, data.fileName));
      return { databaseId };
    }
    case "prepare": {
      const db = databases.get(data.databaseId);
      if (!db) throw new Error(`Unknown databaseId: ${data.databaseId}`);
      const stmt = db.prepare(data.sql);
      const statementId = nextStatementId++;
      statements.set(statementId, stmt);
      return {
        statementId,
        parameterCount: stmt.parameterCount,
        // oo1 の getColumnNames() は結果列のない文 (PRAGMA の設定など) で例外を投げるため C API を直接使う
        columnNames: Array.from({ length: stmt.columnCount }, (_, i) =>
          sqlite3.capi.sqlite3_column_name(stmt.pointer, i),
        ),
      };
    }
    case "step": {
      const stmt = statements.get(data.statementId);
      if (!stmt) throw new Error(`Unknown statementId: ${data.statementId}`);
      try {
        stmt.clearBindings();
        const bindings = normalizeBindings(data.bindings);
        if (bindings.length > 0) stmt.bind(bindings);
        const rows = [];
        let columnTypes = [];
        while (stmt.step()) {
          if (rows.length === 0) {
            columnTypes = Array.from({ length: stmt.columnCount }, (_, i) =>
              sqlite3.capi.sqlite3_column_type(stmt.pointer, i),
            );
          }
          rows.push(stmt.get([]));
        }
        return { rows, columnTypes };
      } finally {
        stmt.reset();
      }
    }
    case "close": {
      if (data.statementId != null) {
        statements.get(data.statementId)?.finalize();
        statements.delete(data.statementId);
      }
      if (data.databaseId != null) {
        databases.get(data.databaseId)?.close();
        databases.delete(data.databaseId);
      }
      return undefined;
    }
    default:
      throw new Error(`Unsupported command: ${data.cmd}`);
  }
}

self.onmessage = async (event) => {
  const { id, data } = event.data;
  try {
    const result = await handle(data);
    if (data.cmd !== "close") {
      self.postMessage({ id, data: result });
    }
  } catch (e) {
    console.error("[sqlite-worker]", data?.cmd, data?.sql ?? data?.statementId, e);
    self.postMessage({ id, error: e?.message ?? String(e) });
  }
};
