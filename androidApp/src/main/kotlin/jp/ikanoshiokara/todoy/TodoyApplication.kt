package jp.ikanoshiokara.todoy

import android.app.Application
import jp.ikanoshiokara.todoy.di.initKoin
import org.koin.android.ext.koin.androidContext

class TodoyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TodoyApplication)
        }
    }
}
