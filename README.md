# todoy

Kotlin Multiplatform + Compose Multiplatform で作る ToDo アプリ。
Android / iOS / Desktop (JVM) / Web (Kotlin/Wasm) を 1 つのコードベースで扱う。

> 現在は **基盤 (プロジェクト構成・DB・DI・ナビゲーション・CI) の構築段階**。
> ホーム画面は DB → Repository → ViewModel → UI の疎通確認用の最小実装。

## 技術スタック

| 領域 | 採用技術 |
| --- | --- |
| 言語 / ビルド | Kotlin 2.4, Gradle 9 (Version Catalog + `build-logic` の Convention Plugin), AGP 9 |
| UI | Compose Multiplatform 1.12, Material 3 |
| 画面遷移 | Navigation Compose (型安全ルート / kotlinx.serialization) |
| 状態管理 | Lifecycle ViewModel + `StateFlow` |
| DB | Room 3 (`androidx.room3`) + `androidx.sqlite` ドライバ |
| DI | Koin 4 |
| Lint / Format | kotlinter (ktlint) |
| CI | GitHub Actions |

### DB ドライバ

| プラットフォーム | ドライバ | 保存先 |
| --- | --- | --- |
| Android | `BundledSQLiteDriver` | アプリの databases ディレクトリ |
| iOS | `BundledSQLiteDriver` | Documents ディレクトリ |
| Desktop | `BundledSQLiteDriver` | macOS: `~/Library/Application Support/todoy` / Windows: `%APPDATA%\todoy` / Linux: `$XDG_DATA_HOME/todoy` |
| Web | `WebWorkerSQLiteDriver` + [`@sqlite.org/sqlite-wasm`](https://www.npmjs.com/package/@sqlite.org/sqlite-wasm) | OPFS (SAH Pool VFS)。使えないブラウザではメモリ上に一時保存 |

Web の SQLite は Web Worker 上で動く。Worker は
[`composeApp/src/wasmJsMain/resources/sqlite-worker.js`](composeApp/src/wasmJsMain/resources/sqlite-worker.js)
で、`androidx.sqlite` の Web Worker プロトコル (open / prepare / step / close) を実装している。
Worker スクリプトはアプリ側の資産なので、ドライバ (`WebWorkerSQLiteDriver`) は `composeApp` の `webModule` で生成し、
`SQLiteDriver` として Koin 経由で `shared` に渡している。
Web でも動くよう、**DAO は `suspend` 関数か `Flow` を返す形でのみ定義する**。

## モジュール構成

```
.
├── build-logic/   # Convention Plugin (todoy.kmp.library: KMP ターゲット・Android 設定の共通化)
├── shared/        # データ層: Room DB / DAO / Repository / ドメインモデル / Koin モジュール
├── composeApp/    # UI 層: 共通 UI・ViewModel・ナビゲーション + Desktop / Web / iOS のエントリポイント
├── androidApp/    # Android アプリ (Application / MainActivity)
└── iosApp/        # iOS アプリ (SwiftUI のシェル。XcodeGen でプロジェクトを生成)
```

依存の向き: `androidApp` / `iosApp` → `composeApp` → `shared`

パッケージは `jp.ikanoshiokara.todoy` 配下。KMP モジュールの Android namespace は
`jp.ikanoshiokara.todoy.<モジュール名>` が自動で設定される。

## 開発環境

- JDK 17 以上 (CI は 21)
- Android Studio (Android / 共通コードの開発)
- Xcode + [XcodeGen](https://github.com/yonaskolb/XcodeGen) (iOS。macOS のみ)

## 実行方法

| 対象 | コマンド |
| --- | --- |
| Android | `./gradlew :androidApp:installDebug` |
| Desktop | `./gradlew :composeApp:run` |
| Web (開発サーバ) | `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` |
| Web (配布用ビルド) | `./gradlew :composeApp:wasmJsBrowserDistribution` → `composeApp/build/dist/wasmJs/productionExecutable/` |
| iOS | `cd iosApp && xcodegen generate && open iosApp.xcodeproj` して Xcode から実行 |

よく使うタスクは `Makefile` にもまとめてある (`make lint` / `make test` / `make web` など)。

## Web 版の日本語フォント

Compose for Web は OS のフォントを使えないため、Web 版にだけ
[Noto Sans JP](https://fonts.google.com/noto/specimen/Noto+Sans+JP) (Regular / Bold, v2.004, 未改変) を同梱している。
`composeApp/src/wasmJsMain/kotlin/.../Main.kt` でフォールバックフォントとして登録しているので、
欧文は既定のフォント、日本語だけ Noto Sans JP で描画される。

- 置き場所: `composeApp/src/wasmJsMain/composeResources/font/`
- 1 ウェイトあたり約 5.7MB あり、Web 版の初回表示はその分遅くなる。必要になったらサブセット化を検討する
  (サブセット化は OFL 上の「改変」にあたるため、名前に予約名 "Source" を含めないこと)。
- Android / iOS / Desktop は端末のフォントで表示できるので同梱していない。

## 開発ルール

- `./gradlew lintKotlin` で lint、`./gradlew formatKotlin` で自動整形。
- DB スキーマは `shared/schemas/` に JSON で出力されるのでコミットする。
- 現在は開発初期のため、スキーマ変更時は破壊的マイグレーション (`fallbackToDestructiveMigration`) を許可している。
  公開前に Migration を整備すること (`shared/.../DatabaseFactory.kt` の TODO)。

## CI

`.github/workflows/ci.yaml`:

- **ubuntu**: lint → `:shared:jvmTest` → Android / Desktop / Web のビルド (Web の成果物はアーティファクトとして保存)
- **macOS**: iOS Simulator 向けフレームワークのリンク

## ライセンス

アプリのソースコードは [MIT](LICENSE)。

同梱しているフォント Noto Sans JP は SIL Open Font License 1.1 で配布されている
([ライセンス全文](composeApp/src/wasmJsMain/resources/licenses/NotoSansJP-OFL.txt)。Web 版の配布物にも `licenses/` として含まれる)。
