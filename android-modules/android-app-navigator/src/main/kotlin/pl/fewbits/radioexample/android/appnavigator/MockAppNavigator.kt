package pl.fewbits.radioexample.android.appnavigator

import pl.fewbits.radioexample.core.home.domain.RadioStation

object MockAppNavigator: AppNavigator {
    override fun openRadioDetails(radioStation: RadioStation) {}

    override fun back() {}
}
