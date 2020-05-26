package us.ttyl.musicsearch.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import toothpick.Toothpick
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(private val app: Application): ViewModelProvider.Factory {

    private val appScope by lazy {
        Toothpick.openScope(app.applicationContext)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = appScope.getInstance(modelClass) as T
}