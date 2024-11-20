package pl.fewbits.radioexample.component.radioplayer

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val componentRadioPlayerModule = module {
    single { RadioPlayer(androidContext()) }
}
