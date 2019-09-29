package com.wolken.sasmple.injection.component

import android.app.Application
import android.content.Context
import dagger.Component
import com.wolken.sasmple.data.DataManager
import com.wolken.sasmple.data.remote.PokemonApi
import com.wolken.sasmple.injection.ApplicationContext
import com.wolken.sasmple.injection.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun pokemonApi(): PokemonApi
}
