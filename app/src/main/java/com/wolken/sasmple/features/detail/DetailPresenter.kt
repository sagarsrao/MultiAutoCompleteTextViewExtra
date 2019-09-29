package com.wolken.sasmple.features.detail

import com.wolken.sasmple.data.DataManager
import com.wolken.sasmple.data.model.Pokemon
import com.wolken.sasmple.features.base.BasePresenter
import com.wolken.sasmple.injection.ConfigPersistent
import com.wolken.sasmple.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class DetailPresenter @Inject
constructor(private val dataManager: DataManager) : BasePresenter<DetailMvpView>() {

    fun getPokemon(name: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        dataManager.getPokemon(name)
                .compose<Pokemon>(SchedulerUtils.ioToMain<Pokemon>())
                .subscribe({ pokemon ->
                    // It should be always checked if MvpView (Fragment or Activity) is attached.
                    // Calling showProgress() on a not-attached fragment will throw a NPE
                    // It is possible to ask isAdded() in the fragment, but it's better to ask in the presenter
                    mvpView?.apply {
                        showProgress(false)
                        showPokemon(pokemon)
                        for (statistic in pokemon.stats) {
                            showStat(statistic)
                        }
                    }
                }) { throwable ->
                    mvpView?.apply {
                        showProgress(false)
                        showError(throwable)
                    }
                }
    }
}