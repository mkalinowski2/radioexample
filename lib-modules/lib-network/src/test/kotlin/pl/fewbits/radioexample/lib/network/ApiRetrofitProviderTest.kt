package pl.fewbits.radioexample.lib.network

import kotlinx.coroutines.runBlocking
import okhttp3.Cache
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import pl.fewbits.radioexample.lib.network.config.ApiBaseUrlProvider
import pl.fewbits.radioexample.lib.network.config.ApiCacheProvider
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File
import java.net.HttpURLConnection
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class ApiRetrofitProviderTest(val enabledCache: Boolean, val enabledLogging: Boolean) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(true, true),
                arrayOf(true, false),
                arrayOf(false, true),
                arrayOf(false, false)
            )
        }
    }

    @JvmField
    @Rule
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    @Test
    fun testRetrofitConnection() {
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = ApiRetrofitProvider(
            ApiBaseUrlProvider(mockWebServer.url("/")),
            ApiOkHttpClientFactory(
                ApiCacheProvider(
                    Cache(
                        directory = File(temporaryFolder.root, "http_cache"),
                        // $0.05 worth of phone storage in 2020
                        maxSize = 50L * 1024L * 1024L // 50 MiB
                    ).takeIf { enabledCache }
                )
            ),
            ApiMoshiProvider(),
            enabledLogging
        ).retrofit

        val service = retrofit.create(ApiService::class.java)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody("""{"name":"productTest"}""")
        )

        val product = runBlocking { service.getProduct("4") }
        assertEquals("productTest", product.name)
    }

    data class Product(val name: String)

    interface ApiService {
        @GET("/product")
        suspend fun getProduct(@Query("id") id: String): Product
    }
}
