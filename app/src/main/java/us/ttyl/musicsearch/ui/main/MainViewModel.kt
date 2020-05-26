package us.ttyl.musicsearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import us.ttyl.musicsearch.dao.MusicDao
import us.ttyl.musicsearch.dao.MusicItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val musicSearchService: MusicDao,
    private val coroutineDispatcher: CoroutineDispatcherProvider
): ViewModel() {

    var previousSearchTerm: String = ""
    val musicSearchDoneState: LiveData<List<MusicItem>> get() = _musicSearchDoneState

    private val _musicSearchDoneState: MutableLiveData<List<MusicItem>> by lazy {
        MutableLiveData<List<MusicItem>>()
    }

    fun searchMusicItems( searchTerm: String) {
        previousSearchTerm = searchTerm
        CoroutineScope(coroutineDispatcher.io).launch {
            val results = musicSearchService.getMusicItems(searchTerm)
            withContext(coroutineDispatcher.main) {
                _musicSearchDoneState.value = results?.results
            }
        }
    }

    fun transformUrlForLargePicture(url: String?): String? {
        url?.let {
            val index = url.lastIndexOf('/', url.length, false)
            val temp = url.substring(0, index)
            return "${temp}/500x500bb.jpg"
        }
        return url
    }
}
