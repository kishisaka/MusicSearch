package us.ttyl.musicsearch

import android.app.Application
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator
import toothpick.smoothie.module.SmoothieApplicationModule

class TestApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Toothpick.setConfiguration(Configuration.forDevelopment().enableReflection().preventMultipleRootScopes())
        FactoryRegistryLocator.setRootRegistry(FactoryRegistry())
        MemberInjectorRegistryLocator.setRootRegistry(MemberInjectorRegistry())
        val scope = Toothpick.openScope(applicationContext)
        scope.installModules(SmoothieApplicationModule(this))
    }
}