package us.ttyl.musicsearch

import us.ttyl.musicsearch.dao.MusicDao
import us.ttyl.musicsearch.dao.MusicItem
import us.ttyl.musicsearch.dao.MusicSearchResult
import us.ttyl.musicsearch.ui.main.CoroutineDispatcherProvider
import us.ttyl.musicsearch.ui.main.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class MainViewModelTest {
    @MockK
    lateinit var musicDao: MusicDao

    @MockK
    lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        every { musicDao.getMusicItems(any()) } returns MusicSearchResult(1, listOf(MusicItem(
            "artist name", "20", "track name",
            "collection name",
            "https://test101.com/test.jpg","http://test101.com/test",
            42342454324, "2010-06-08T12:00:00Z",
            "genre", "USA", "USD",
            "0.99")))
        every {coroutineDispatcherProvider.io} returns Dispatchers.Main
        every {coroutineDispatcherProvider.main} returns Dispatchers.Main
    }

    @Test
    fun `test view model transform to large url`() {
        val viewModel = MainViewModel(musicDao, coroutineDispatcherProvider)
        val url = viewModel.transformUrlForLargePicture("https://is2-ssl.mzstatic.com/image/thumb/Music/v4/34/00/26/34002672-cc90-82a0-0e2e-ee84202847b5/source/100x100bb.jpg")
        assertEquals("https://is2-ssl.mzstatic.com/image/thumb/Music/v4/34/00/26/34002672-cc90-82a0-0e2e-ee84202847b5/source/500x500bb.jpg", url)
    }
}
