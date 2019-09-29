package com.wolken.sasmple.features.main

import com.wolken.sasmple.features.base.MvpView
import com.wolken.sasmple.features.main.models.ResponseNews

interface MainMvpView : MvpView {

    fun showPokemon(pokemon: List<String>)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)
    fun getListNews(pokemons: ResponseNews)

}