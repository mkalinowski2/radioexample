package pl.fewbits.radioexample

import android.content.Context
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.test.check.checkKoinModules
import pl.fewbits.radioexample.core.home.domain.RadioStation
import pl.fewbits.radioexample.di.KoinInitializer
import pl.fewbits.radioexample.lib.network.config.ApiCacheProvider

class KoinCheckModuleTest {

    private val appContext: Context = mockk<Context>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun checkModules() {
        val overrideModule = module {
            single { mockk<ApiCacheProvider>(relaxed = true) }
        }

        checkKoinModules(
            modules = KoinInitializer.modules + overrideModule,
            appDeclaration = {
                allowOverride(true)
                androidContext(appContext)
            }
        ) {
            withInstance<RadioStation>(mockk(relaxed = true))
        }
    }
}
