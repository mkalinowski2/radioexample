package pl.fewbits.radioexample.android.appnavigator

import pl.fewbits.radioexample.core.home.domain.RadioStation

interface AppNavigator {
    fun openRadioDetails(radioStation: RadioStation)
    fun back()
}
