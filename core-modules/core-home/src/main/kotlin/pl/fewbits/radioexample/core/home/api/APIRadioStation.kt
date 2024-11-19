package pl.fewbits.radioexample.core.home.api

internal data class APIRadioStation(
    val id: String,
    val description: String,
    val name: String,
    val imgUrl: String,
    val streamUrl: String,
    val reliability: Int,
    val popularity: Double?,
    val tags: List<String>
)
