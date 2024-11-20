package pl.fewbits.radioexample.navigation

import androidx.navigation.NavController
import pl.fewbits.radioexample.android.appnavigator.AppNavigator
import pl.fewbits.radioexample.android.appnavigator.ScreenArgs
import pl.fewbits.radioexample.core.home.domain.RadioStation

class AppNavigatorImpl(private val navController: NavController) : AppNavigator {
    override fun openRadioDetails(radioStation: RadioStation) {
        navController.navigate(ScreenArgs.RadioDetails(radioStation))
    }

    override fun back() {
        navController.popBackStack()
    }
}
