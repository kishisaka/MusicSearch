package us.ttyl.musicsearch.ui.main

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoroutineDispatcherProvider @Inject constructor() {
    val main by lazy{Dispatchers.Main}
    val io by lazy{Dispatchers.IO}
    val default by lazy{Dispatchers.Default}
}