package pl.fewbits.radioexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import pl.fewbits.radioexample.android.ui.theme.RadioExampleTheme
import pl.fewbits.radioexample.navigation.AppGraphFactory
import pl.fewbits.radioexample.navigation.AppNavigatorImpl
import pl.fewbits.radioexample.android.appnavigator.ScreenArgs
import pl.fewbits.radioexample.navigation.TRANSITION_ANIM_DURATION_MS

@Serializable
data class User(
    val id: Int,
    val name: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            RadioExampleTheme {
                NavHost(
                    navController = navController,
                    startDestination = ScreenArgs.Home,
                    enterTransition = { fadeIn(animationSpec = tween(TRANSITION_ANIM_DURATION_MS, delayMillis = 0)) },
                    exitTransition = { fadeOut(animationSpec = tween(1, delayMillis = TRANSITION_ANIM_DURATION_MS - 1)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(1, delayMillis = 0)) },
                    popExitTransition = { fadeOut(animationSpec = tween(TRANSITION_ANIM_DURATION_MS, delayMillis = 0)) },
                ) {
                    AppGraphFactory.createAppGraph(this, AppNavigatorImpl(navController))
                }
            }
        }
    }
}
