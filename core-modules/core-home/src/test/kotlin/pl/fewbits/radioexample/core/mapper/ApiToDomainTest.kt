package pl.fewbits.radioexample.core.mapper

import pl.fewbits.radioexample.core.home.api.APIRadioStation
import pl.fewbits.radioexample.core.home.api.APIRadioStations
import pl.fewbits.radioexample.core.home.domain.HomeResponse
import pl.fewbits.radioexample.core.home.domain.RadioStation
import pl.fewbits.radioexample.core.home.mappers.toDomain
import pl.fewbits.radioexample.core.home.mappers.toHomeResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class ApiToDomainTest {

    @Test
    fun testApiRadioStationToDomain() {
        val apiRadioStation = APIRadioStation(
            "s135217",
            "Only Mozart Music",
            "Radio Mozart",
            "https://cdn-radiotime-logos.tunein.com/s135217d.png",
            "https://streamingv2.shoutcast.com/Radio-Mozart",
            68,
            2.9,
            listOf("music", "classical")
        )

        val radioStation = apiRadioStation.toDomain()

        assertEquals(
            RadioStation(
                "s135217",
                "Only Mozart Music",
                "Radio Mozart",
                "https://cdn-radiotime-logos.tunein.com/s135217d.png",
                "https://streamingv2.shoutcast.com/Radio-Mozart",
                68,
                2.9,
                listOf("music", "classical")
            ),
            radioStation
        )
    }

    @Test
    fun testApiRadioStationsToHome() {
        val apiRadioStation = APIRadioStation(
            "s135217",
            "Only Mozart Music",
            "Radio Mozart",
            "https://cdn-radiotime-logos.tunein.com/s135217d.png",
            "https://streamingv2.shoutcast.com/Radio-Mozart",
            68,
            2.9,
            listOf("music", "classical")
        )
        val apiRadioStation2 = apiRadioStation.copy(id = "2")
        val apiRadioStation3 = apiRadioStation.copy(id = "3")

        val apiRadioStations = APIRadioStations(listOf(apiRadioStation, apiRadioStation2, apiRadioStation3))

        val homeResponse = apiRadioStations.toHomeResponse()

        assertEquals(
            HomeResponse(apiRadioStations.data.map { it.toDomain() }),
            homeResponse
        )
    }
}
