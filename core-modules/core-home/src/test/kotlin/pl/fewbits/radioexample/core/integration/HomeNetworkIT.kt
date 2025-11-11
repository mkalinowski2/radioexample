package pl.fewbits.radioexample.core.integration

import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import pl.fewbits.radioexample.core.home.HomeNetwork
import pl.fewbits.radioexample.core.home.domain.RadioStation
import pl.fewbits.radioexample.lib.network.ApiMoshiProvider
import pl.fewbits.radioexample.lib.network.ApiOkHttpClientFactory
import pl.fewbits.radioexample.lib.network.ApiRetrofitProvider
import pl.fewbits.radioexample.lib.network.config.ApiBaseUrlProvider
import pl.fewbits.radioexample.lib.network.config.ApiCacheProvider
import java.util.Date
import kotlin.test.assertEquals

private const val baseUrl = "https://s3-us-west-1.amazonaws.com"

@Ignore("Integration test - enable locally on demand")
class HomeNetworkIT {

    private lateinit var retrofitProvider: ApiRetrofitProvider

    @Before
    fun setup() {
        retrofitProvider = ApiRetrofitProvider(
            ApiBaseUrlProvider(baseUrl.toHttpUrl()),
            ApiOkHttpClientFactory(ApiCacheProvider(null)),
            ApiMoshiProvider(),
        )
    }

    @Test
    fun testGetHome() {
        val network = HomeNetwork(retrofitProvider, Date())

        val homeResponse = runBlocking { network.getHome() }
        assertEquals(10, homeResponse.radioStations.size)
        assertEquals(
            homeResponse.radioStations[0],
            RadioStation(
                "s135217",
                "Only Mozart Music",
                "Radio Mozart",
                "https://cdn-radiotime-logos.tunein.com/s135217d.png",
                "https://streamingv2.shoutcast.com/Radio-Mozart",
                68,
                2.9,
                listOf("music", "classical")
            )
        )
        assertEquals(
            "s135217,s15205,s162210,s21606,s300866,s131270,s25478,s128348,s17077,s404",
            homeResponse.radioStations.joinToString(",") { it.id }
        )
    }
}
