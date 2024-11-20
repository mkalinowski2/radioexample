package pl.fewbits.radioexample.android.appnavigator

import kotlinx.serialization.Serializable
import pl.fewbits.radioexample.core.home.domain.RadioStation

sealed class ScreenArgs {

    @Serializable
    object Home

    @Serializable
    data class RadioDetails(
        val id: String,
        val description: String,
        val name: String,
        val imageUrl: String,
        val streamUrl: String,
        val reliability: Int,
        val popularity: Double?,
        val tags: List<String>
    ) {

        constructor(radioStation: RadioStation): this(
            radioStation.id,
            radioStation.description,
            radioStation.name,
            radioStation.imageUrl,
            radioStation.streamUrl,
            radioStation.reliability,
            radioStation.popularity,
            radioStation.tags
        )

        val radioStation: RadioStation
            get() = RadioStation(
                id,
                description,
                name,
                imageUrl,
                streamUrl,
                reliability,
                popularity,
                tags
            )
    }
}
