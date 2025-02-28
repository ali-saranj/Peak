package ir.halion.peak

import android.app.Application
import ir.halion.peak.peresenter.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PeakApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PeakApplication)
            modules(ViewModelModule)
        }
    }
}