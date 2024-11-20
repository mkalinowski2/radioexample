package pl.fewbits.radioexample.component.radioplayer

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

class PlaybackService : MediaSessionService(), MediaSession.Callback {

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).setCallback(this).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onAddMediaItems(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItems: MutableList<MediaItem>
    ): ListenableFuture<MutableList<MediaItem>> {
        val updatedMediaItems = mediaItems.map {
            it.buildUpon()
                .setMediaId(it.requestMetadata.extras?.getString(MEDIA_ID_KEY) ?: it.mediaId)
                .setUri(it.requestMetadata.mediaUri)
                .setMediaMetadata(
                    it.mediaMetadata.buildUpon()
                        .setArtist(it.requestMetadata.extras?.getString(ARTIST_KEY))
                        .setArtworkUri(it.requestMetadata.extras?.getString(ARTWORK_URL_KEY)?.let { url -> Uri.parse(url) })
                        .build()
                ).build()
        }.toMutableList()
        return Futures.immediateFuture(updatedMediaItems)
    }

    companion object {
        const val MEDIA_ID_KEY = "MEDIA_ID_KEY"
        const val ARTIST_KEY = "ARTIST_KEY"
        const val ARTWORK_URL_KEY = "IMAGE_URL_KEY"
    }

}
