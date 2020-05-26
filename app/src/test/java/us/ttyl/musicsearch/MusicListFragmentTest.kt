package us.ttyl.musicsearch

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import us.ttyl.musicsearch.dao.MusicDao
import us.ttyl.musicsearch.dao.MusicItem
import us.ttyl.musicsearch.dao.MusicSearchResult
import us.ttyl.musicsearch.ui.main.CoroutineDispatcherProvider
import us.ttyl.musicsearch.ui.main.MusicListFragment
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import toothpick.Toothpick
import toothpick.config.Module

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class MusicListFragmentTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var musicDao: MusicDao

    @MockK
    lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    lateinit var context: Context

    lateinit var fakeModelFactory: FakeModelFactory

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        context = ApplicationProvider.getApplicationContext()
        fakeModelFactory = FakeModelFactory(context)
        Toothpick.reset()
        val scope = Toothpick.openScope(ApplicationProvider.getApplicationContext())
        scope.installTestModules(object: Module() {
            init {
                bind(MusicDao::class.java).toInstance(musicDao)
                bind(CoroutineDispatcherProvider::class.java).toInstance(coroutineDispatcherProvider)
                bind(ViewModelProvider.Factory::class.java).toInstance(fakeModelFactory)
                bind(Application::class.java).toInstance(ApplicationProvider.getApplicationContext())
            }
        })
        every { musicDao.getMusicItems(any()) } returns MusicSearchResult(1, listOf(
            MusicItem(
                "artist name", "20", "track name",
                "collection name",
                "https://test101.com/test.jpg","http://test101.com/test",
                42342454324, "2010-06-08T12:00:00Z",
                "genre", "USA", "USD",
                "0.99")
        ))
        every {coroutineDispatcherProvider.io} returns Dispatchers.Main
        every {coroutineDispatcherProvider.main} returns Dispatchers.Main
    }

    @LooperMode(LooperMode.Mode.PAUSED)
    @Test
    fun checkForItemsInList() {
        launchFragmentInContainer<MusicListFragment>()
        val recyclerViewMatcher = RecyclerViewMatcher(R.id.recyclerView)
        onView(recyclerViewMatcher.atPosition(0))
            .check(matches(hasDescendant(withText("artist name"))))
        onView(recyclerViewMatcher.atPosition(0))
            .check(matches(hasDescendant(withText("track name"))))
        onView(recyclerViewMatcher.atPosition(0))
            .check(matches(hasDescendant(withText("collection name"))))
    }

}