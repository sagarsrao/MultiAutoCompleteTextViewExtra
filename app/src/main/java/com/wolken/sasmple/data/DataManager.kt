package com.wolken.sasmple.data

import com.wolken.sasmple.data.model.Pokemon
import com.wolken.sasmple.data.remote.PokemonApi
import com.wolken.sasmple.features.main.models.ResponseNews
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(private val pokemonApi: PokemonApi) {

    fun getPokemonList(limit: Int): Single<List<String>> {
        return pokemonApi.getPokemonList(limit)
                .toObservable()
                .flatMapIterable { (results) -> results }
                .map { (name) -> name }
                .toList()
    }

    fun getPokemon(name: String): Single<Pokemon> {
        return pokemonApi.getPokemon(name)
    }

    fun getNews(q: String): Observable<ResponseNews> {
        return pokemonApi.getNews(q)
    }
}