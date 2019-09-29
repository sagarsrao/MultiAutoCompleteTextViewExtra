package com.wolken.sasmple.features.main

import android.annotation.SuppressLint
import com.wolken.sasmple.data.DataManager
import com.wolken.sasmple.features.base.BasePresenter
import com.wolken.sasmple.injection.ConfigPersistent
import com.wolken.sasmple.util.rx.scheduler.SchedulerUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val dataManager: DataManager) : BasePresenter<MainMvpView>() {

    fun getPokemon(limit: Int) {
        checkViewAttached()
        mvpView?.showProgress(true)
        dataManager.getPokemonList(limit)
                .compose(SchedulerUtils.ioToMain<List<String>>())
                .subscribe({ pokemons ->
                    mvpView?.apply {
                        showProgress(false)
                        showPokemon(pokemons)
                    }
                }) { throwable ->
                    mvpView?.apply {
                        showProgress(false)
                        showError(throwable)
                    }
                }
    }



    @SuppressLint("CheckResult")
    fun getNesList(q: String) {
        checkViewAttached()
        mvpView?.showProgress(true)
        dataManager.getNews(q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ pokemons ->
                    mvpView?.apply {
                        showProgress(false)
                        if(pokemons.status == "ok"){
                            getListNews(pokemons)
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