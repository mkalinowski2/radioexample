package pl.fewbits.radioexample.core

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import pl.fewbits.radioexample.core.home.GetHomeUseCase
import pl.fewbits.radioexample.core.home.HomeNetwork
import pl.fewbits.radioexample.core.home.domain.HomeResponse
import kotlin.test.assertEquals

class GetHomeUseCaseTest {

    @Test
    fun testGetHomeUseCase() {
        val homeResponse = mockk<HomeResponse>(relaxed = true)
        val homeNetwork = mockk<HomeNetwork>().apply {
            coEvery { getHome() } returns homeResponse
        }

        val getHomeUseCase = GetHomeUseCase(homeNetwork)

        assertEquals(homeResponse, runBlocking { getHomeUseCase.getHome() })
    }
}