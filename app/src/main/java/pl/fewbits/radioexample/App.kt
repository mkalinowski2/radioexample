package pl.fewbits.radioexample

import android.app.Application
import org.koin.android.ext.android.inject
import pl.fewbits.radioexample.component.radioplayer.RadioPlayer
import pl.fewbits.radioexample.di.KoinInitializer

class App : Application() {

    private val radioPlayer by inject<RadioPlayer>()

    override fun onCreate() {
        super.onCreate()
        KoinInitializer.init(this)
        radioPlayer.init()
    }

    override fun onTerminate() {
        radioPlayer.stopStream()
        radioPlayer.release()
        super.onTerminate()
    }
}
