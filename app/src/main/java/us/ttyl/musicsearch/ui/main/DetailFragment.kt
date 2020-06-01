package us.ttyl.musicsearch.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import us.ttyl.musicsearch.R
import toothpick.Toothpick
import javax.inject.Inject

class DetailFragment: Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel

    private var artworkLarge: ImageView? = null
    private var artistName: TextView? = null
    private var trackName: TextView? = null
    private var collectionName: TextView? = null
    private var player : SimpleExoPlayer? = null
    private var musicControls: PlayerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity?.application, activity, this))
        activity?.run {
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.music_detail, container, false)
    }

    /**
     * show the rest of the returned data in the next release!
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artworkLarge = view.findViewById(R.id.artworkLarge)
        artistName = view.findViewById(R.id.artistName)
        collectionName = view.findViewById(R.id.collectionName)
        trackName = view.findViewById(R.id.trackName)
        musicControls = view.findViewById(R.id.musicControls)
        context?.let {
            player = SimpleExoPlayer.Builder(it).build()
            val dataSourceFactory = DefaultDataSourceFactory(it, Util.getUserAgent(it, "us.ttyl.musicsearch"))
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                Uri.parse(arguments?.getString("previewUrl")))
            musicControls?.player = player
            musicControls?.controllerShowTimeoutMs = 10000000
            player?.playWhenReady = true
            player?.prepare(mediaSource, false, false)
        }

        artworkLarge?.let {
            val bigImageUrl = viewModel.transformUrlForLargePicture(arguments?.getString("musicArtwork"))
            Glide.with(this).load(bigImageUrl).placeholder(R.drawable.ic_error_24px).into(it)
        }
        artistName?.text = arguments?.getString("artistName")
        trackName?.text = arguments?.getString("trackName")
        collectionName?.text = arguments?.getString("collectionName")
    }

    override fun onDetach() {
        player?.stop()
        player?.release()
        super.onDetach()
    }
}