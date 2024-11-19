package pl.fewbits.radioexample.core.home.mappers

import pl.fewbits.radioexample.core.home.api.APIRadioStation
import pl.fewbits.radioexample.core.home.api.APIRadioStations
import pl.fewbits.radioexample.core.home.domain.HomeResponse
import pl.fewbits.radioexample.core.home.domain.RadioStation

internal fun APIRadioStation.toDomain() = RadioStation(
    id,
    description,
    name,
    imgUrl,
    streamUrl,
    reliability,
    popularity,
    tags
)

internal fun APIRadioStations.toHomeResponse() = HomeResponse(data.map { it.toDomain() })
