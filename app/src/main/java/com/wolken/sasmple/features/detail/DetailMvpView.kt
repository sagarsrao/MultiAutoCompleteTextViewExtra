package com.wolken.sasmple.features.detail

import com.wolken.sasmple.data.model.Pokemon
import com.wolken.sasmple.data.model.Statistic
import com.wolken.sasmple.features.base.MvpView

interface DetailMvpView : MvpView {

    fun showPokemon(pokemon: Pokemon)

    fun showStat(statistic: Statistic)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}