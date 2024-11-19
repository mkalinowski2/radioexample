package pl.fewbits.radioexample.core.home

import pl.fewbits.radioexample.core.home.api.APIRadioStations
import retrofit2.http.GET

internal interface RadioStationsService {

    @GET("cdn-web.tunein.com/stations.json")
    suspend fun getRadioStations(): APIRadioStations
}
