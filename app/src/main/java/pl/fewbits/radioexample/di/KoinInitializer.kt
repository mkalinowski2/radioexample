package pl.fewbits.radioexample.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import pl.fewbits.radioexample.core.home.coreHomeModule
import pl.fewbits.radioexample.feature.home.featureHomeModule
import pl.fewbits.radioexample.lib.network.libNetworkModule

object KoinInitializer {

    val modules = listOf(
        appModule,
        featureHomeModule,
        coreHomeModule,
        libNetworkModule
    )

    fun init(applicationContext: Context) {
        startKoin {
            androidContext(applicationContext)
            modules(modules)
        }
    }
}
