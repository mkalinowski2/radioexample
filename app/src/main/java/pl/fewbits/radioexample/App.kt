package pl.fewbits.radioexample

import android.app.Application
import pl.fewbits.radioexample.di.KoinInitializer

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer.init(this)
    }
}