package us.ttyl.musicsearch

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import toothpick.Toothpick
import javax.inject.Inject

class FakeModelFactory @Inject constructor(private val context: Context): ViewModelProvider.Factory {
    private val appScope by lazy { Toothpick.openScope(context) }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return appScope.getInstance(modelClass) as T
    }

}