package pl.fewbits.radioexample.core.home

import pl.fewbits.radioexample.core.home.mappers.toHomeResponse
import pl.fewbits.radioexample.lib.network.ApiRetrofitProvider

class HomeNetwork(private val retrofitProvider: ApiRetrofitProvider) {

    private val service: RadioStationsService by lazy {
        retrofitProvider.retrofit.create(RadioStationsService::class.java)
    }

    suspend fun getHome() = service.getRadioStations().toHomeResponse()
}