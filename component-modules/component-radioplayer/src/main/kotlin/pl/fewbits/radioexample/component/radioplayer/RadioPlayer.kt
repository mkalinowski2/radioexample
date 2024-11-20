package pl.fewbits.radioexample.component.radioplayer

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class RadioPlayer(private val context: Context) {

    private val isPlayingStateFlow = MutableStateFlow(false)
    private val radioIdStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val playerErrorStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val stateOfPlayerStateFlow: MutableStateFlow<PlayerState> = MutableStateFlow(PlayerState.Idle)

    private var mediaController: MediaController? = null
    private val controllerFuture: ListenableFuture<MediaController> by lazy {
        MediaController.Builder(
            context,
            SessionToken(context, ComponentName(context, PlaybackService::class.java))
        ).buildAsync()
    }

    init {
        controllerFuture.addListener(
            {
                mediaController = controllerFuture.get().apply {
                    addListener(
                        object : Player.Listener {
                            override fun onIsPlayingChanged(isPlaying: Boolean) {
                                isPlayingStateFlow.value = isPlaying
                                if (!isPlaying) {
                                    radioIdStateFlow.value = null
                                }
                            }

                            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                                super.onMediaItemTransition(mediaItem, reason)
                                radioIdStateFlow.value = currentMediaItem?.mediaId
                            }

                            override fun onPlayerError(error: PlaybackException) {
                                playerErrorStateFlow.value = UUID.randomUUID().toString()
                                super.onPlayerError(error)
                            }

                            override fun onPlaybackStateChanged(playbackState: Int) {
                                stateOfPlayerStateFlow.value = when (playbackState) {
                                    Player.STATE_IDLE -> PlayerState.Idle
                                    Player.STATE_BUFFERING -> PlayerState.Buffering
                                    Player.STATE_READY -> PlayerState.Ready
                                    Player.STATE_ENDED -> PlayerState.Ended
                                    else -> PlayerState.Idle
                                }
                                super.onPlaybackStateChanged(playbackState)
                            }
                        }
                    )
                }
            },
            MoreExecutors.directExecutor()
        )
    }

    fun getPlayingStateFlow() = isPlayingStateFlow.asStateFlow()
    fun getCurrentRadioIdStateFlow() = radioIdStateFlow.asStateFlow()
    fun getPlayerErrorStateFlow() = playerErrorStateFlow.asStateFlow()
    fun getPlayerBufferingStateFlow() = stateOfPlayerStateFlow.asStateFlow()

    fun consumeLastError() {
        playerErrorStateFlow.value = null
    }

    fun playStream(radioId: String, streamUrl: String, radioTitle: String, imageUrl: String) {
        radioIdStateFlow.value = radioId
        isPlayingStateFlow.value = false
        playerErrorStateFlow.value = null
        stateOfPlayerStateFlow.value = PlayerState.Idle

        mediaController?.let { controller ->
            val mediaItem = MediaItem.Builder()
                .setMediaId(radioId)
                .setRequestMetadata(
                    MediaItem.RequestMetadata.Builder()
                        .setMediaUri(Uri.parse(streamUrl))
                        .setExtras(Bundle().apply {
                            putString(PlaybackService.ARTIST_KEY, radioTitle)
                            putString(PlaybackService.ARTWORK_URL_KEY, imageUrl)
                            putString(PlaybackService.MEDIA_ID_KEY, radioId)
                        })
                        .build()
                )
                .build()

            controller.setMediaItem(mediaItem)
            controller.prepare()
            controller.play()
        }
    }

    fun stopStream() {
        mediaController?.let { controller ->
            controller.stop()
            controller.clearMediaItems()
        }
    }

    fun release() {
        MediaController.releaseFuture(controllerFuture)
        mediaController = null
    }
}
