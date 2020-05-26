package us.ttyl.musicsearch.ui.main

import android.content.Context
import toothpick.config.Module

class ToothpickModule(context: Context): Module() {
    init {
        bind(Context::class.java).toInstance(context)
    }
}