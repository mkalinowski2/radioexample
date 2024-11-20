package pl.fewbits.radioexample.feature.radiodetails

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import pl.fewbits.radioexample.android.ui.viewmodel.BaseViewModel
import pl.fewbits.radioexample.component.radioplayer.PlayerState
import pl.fewbits.radioexample.component.radioplayer.RadioPlayer
import pl.fewbits.radioexample.core.home.domain.RadioStation
import pl.fewbits.radioexample.feature.radiodetails.state.RadioDetailsState

class RadioDetailsViewModelImpl(
    private val radioStation: RadioStation,
    private val radioPlayer: RadioPlayer
) : RadioDetailsViewModel() {

    private val stateFlow = combine(
        radioPlayer.getPlayingStateFlow(),
        radioPlayer.getCurrentRadioIdStateFlow(),
        radioPlayer.getPlayerBufferingStateFlow()
    ) { isPlaying, id, playerState ->
        val isCurrentRadio = id == radioStation.id
        RadioDetailsState(
            (isPlaying || playerState == PlayerState.Ready) && isCurrentRadio,
            playerState == PlayerState.Buffering && isCurrentRadio
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, RadioDetailsState(isRadioPlaying = false, isRadioBuffering = false))

    override fun getStateFlow() = stateFlow
    override fun getPlayerErrorStateFlow() = radioPlayer.getPlayerErrorStateFlow()

    override fun play() {
        radioPlayer.stopStream()
        radioPlayer.playStream(
            radioId = radioStation.id,
            streamUrl = radioStation.streamUrl,
            radioTitle = radioStation.name,
            imageUrl = radioStation.imageUrl
        )
    }

    override fun stop() {
        radioPlayer.stopStream()
    }

    override fun consumeLastError() {
        radioPlayer.consumeLastError()
    }

}

class MockRadioDetailsViewModel : RadioDetailsViewModel() {
    override fun getStateFlow() = MutableStateFlow(RadioDetailsState(isRadioPlaying = false, isRadioBuffering = false))
    override fun getPlayerErrorStateFlow() = MutableStateFlow(null)

    override fun play() = Unit
    override fun stop() = Unit
    override fun consumeLastError() = Unit
}

abstract class RadioDetailsViewModel : BaseViewModel() {
    abstract fun getStateFlow(): StateFlow<RadioDetailsState>
    abstract fun getPlayerErrorStateFlow(): StateFlow<String?>

    abstract fun play()
    abstract fun stop()
    abstract fun consumeLastError()
}
