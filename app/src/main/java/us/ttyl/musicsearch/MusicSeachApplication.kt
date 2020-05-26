package us.ttyl.musicsearch

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import us.ttyl.musicsearch.ui.main.ToothpickModule
import us.ttyl.musicsearch.ui.main.ViewModelFactory
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator
import toothpick.smoothie.module.SmoothieApplicationModule

class MusicSearchApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupToothpickConfiguration()
        Toothpick.inject(this, setupDIGraph())
    }
    private fun setupToothpickConfiguration() {
        val configuration = Configuration.forDevelopment()
        configuration.disableReflection().preventMultipleRootScopes()

        FactoryRegistryLocator.setRootRegistry(FactoryRegistry())
        MemberInjectorRegistryLocator.setRootRegistry(MemberInjectorRegistry())

        Toothpick.setConfiguration(configuration)
    }

    private fun setupDIGraph(): Scope {
        val scope = Toothpick.openScope(this)
        scope.installModules(
            ToothpickModule(this),
            SmoothieApplicationModule(this)
        )
        scope.installModules(object: Module() {
            init {
                bind(ViewModelProvider.Factory::class.java).to(ViewModelFactory::class.java)
            }
        })
        return scope
    }
}