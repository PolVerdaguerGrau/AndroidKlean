package com.orioltobar.androidklean.di.components

import android.content.Context
import com.orioltobar.androidklean.App
import com.orioltobar.androidklean.di.modules.ActivityBindingModule
import com.orioltobar.androidklean.di.modules.AppModule
import com.orioltobar.networkdatasource.di.NetworkModule
import com.orioltobar.networkdatasource.di.NetworkServicesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        NetworkModule::class,
        NetworkServicesModule::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}