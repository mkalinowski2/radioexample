package pl.fewbits.radioexample.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import pl.fewbits.radioexample.android.appnavigator.AppNavigator
import pl.fewbits.radioexample.android.appnavigator.ScreenArgs
import pl.fewbits.radioexample.feature.home.HomeScreen
import pl.fewbits.radioexample.feature.radiodetails.RadioDetailsScreen

const val TRANSITION_ANIM_DURATION_MS = 250

object AppGraphFactory {

    fun createAppGraph(navGraphBuilder: NavGraphBuilder, appNavigator: AppNavigator) {
        navGraphBuilder.composableAnimated<ScreenArgs.Home> {
            HomeScreen.Content(appNavigator)
        }

        navGraphBuilder.composableAnimated<ScreenArgs.RadioDetails> { backStackEntry ->
            val args: ScreenArgs.RadioDetails = backStackEntry.toRoute()
            RadioDetailsScreen.Content(radioStation = args.radioStation, appNavigator)
        }
    }

    private inline fun <reified T : Any> NavGraphBuilder.composableAnimated(
        noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
    ) = composable<T>(
        enterTransition = { fadeIn(animationSpec = tween(TRANSITION_ANIM_DURATION_MS, delayMillis = 0)) },
        exitTransition = { fadeOut(animationSpec = tween(1, delayMillis = TRANSITION_ANIM_DURATION_MS - 1)) },
        popEnterTransition = { fadeIn(animationSpec = tween(1, delayMillis = 0)) },
        popExitTransition = { fadeOut(animationSpec = tween(TRANSITION_ANIM_DURATION_MS, delayMillis = 0)) },
        content = content
    )
}
