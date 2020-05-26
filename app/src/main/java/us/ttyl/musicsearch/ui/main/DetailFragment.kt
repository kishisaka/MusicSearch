package us.ttyl.musicsearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import us.ttyl.musicsearch.R
import toothpick.Toothpick
import javax.inject.Inject

class DetailFragment: Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel

    var artworkLarge: ImageView? = null
    var artistName: TextView? = null
    var trackName: TextView? = null
    var collectionName: TextView? = null

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

        artworkLarge?.let {
            val bigImageUrl = viewModel.transformUrlForLargePicture(arguments?.getString("musicArtwork"))
            Glide.with(this).load(bigImageUrl).placeholder(R.drawable.ic_error_24px).into(it)
        }
        artistName?.text = arguments?.getString("artistName")
        trackName?.text = arguments?.getString("trackName")
        collectionName?.text = arguments?.getString("collectionName")
    }
}