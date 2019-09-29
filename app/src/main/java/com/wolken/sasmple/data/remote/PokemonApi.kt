package com.wolken.sasmple.data.remote


import com.wolken.sasmple.data.model.Pokemon
import com.wolken.sasmple.data.model.PokemonListResponse
import com.wolken.sasmple.features.main.models.ResponseNews
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit: Int): Single<PokemonListResponse>

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Single<Pokemon>

    @GET("v2/everything")
    fun  getNews(@Query("q") query: String,@Query("from") from:String = "2019-08-29",@Query("sortBy") sortBy:String = "publishedAt"
                 ,@Query("apiKey") apiKey:String = "2276e35c25ef492cb23fd3ab136d06ee"):Observable<ResponseNews>

}
