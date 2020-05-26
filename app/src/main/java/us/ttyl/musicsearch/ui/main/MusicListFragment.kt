package us.ttyl.musicsearch.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import us.ttyl.musicsearch.R
import us.ttyl.musicsearch.dao.MusicItem
import toothpick.Toothpick
import javax.inject.Inject

class MusicListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel

    private var adapter: MusicItemAdapter? = null

    private val musicSearchDoneObserver = Observer<List<MusicItem>> {musicItemList ->
        adapter?.setItems(musicItemList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScopes(activity?.application, activity, this))
        activity?.run {
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.music_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            adapter = MusicItemAdapter(it, object: OnClickCallBack {
                override fun onClick(collectionName: String?, artistName: String?, musicName: String?, musicArtwork: String) {
                    val direction = MusicListFragmentDirections.musicListToDetailFragment(collectionName, artistName, musicName, musicArtwork)
                    findNavController().navigate(direction)
                }
            })
            val searchButton: ImageButton = view.findViewById(R.id.searchButton)
            val search: TextView = view.findViewById(R.id.search)
            searchButton.setOnClickListener {
                viewModel.searchMusicItems(search.text.toString())
            }
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(it)
            recyclerView.adapter = adapter
            viewModel.musicSearchDoneState.observe(viewLifecycleOwner, musicSearchDoneObserver)

            // populate default items or return to back
            if (viewModel.previousSearchTerm.isEmpty()) {
                viewModel.searchMusicItems("Easy E")
            } else {
                viewModel.searchMusicItems(viewModel.previousSearchTerm)
            }
        }
    }

    interface OnClickCallBack {
        fun onClick(collectionName: String?,
                    artistName: String?,
                    musicName: String?,
                    musicArtwork: String)
    }


}
