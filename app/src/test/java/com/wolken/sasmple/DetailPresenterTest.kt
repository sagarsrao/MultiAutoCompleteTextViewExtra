package com.wolken.sasmple

import com.nhaarman.mockito_kotlin.*
import com.wolken.sasmple.common.TestDataFactory
import com.wolken.sasmple.data.DataManager
import com.wolken.sasmple.data.model.Pokemon
import com.wolken.sasmple.features.detail.DetailMvpView
import com.wolken.sasmple.features.detail.DetailPresenter
import com.wolken.sasmple.util.RxSchedulersOverrideRule
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by ravindra on 24/12/16.
 */
@RunWith(MockitoJUnitRunner::class)
class DetailPresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()
    val pokemon = TestDataFactory.makePokemon("id")

    val mockDetailMvpView: DetailMvpView = mock()
    val mockDataManager: DataManager = mock {
        on { getPokemon(anyString()) } doReturn Single.just(pokemon)
        on { getPokemon("id") } doReturn Single.error<Pokemon>(RuntimeException())
    }
    private var detailPresenter: DetailPresenter? = null

    @Before
    fun setUp() {
        detailPresenter = DetailPresenter(mockDataManager)
        detailPresenter?.attachView(mockDetailMvpView)
    }

    @After
    fun tearDown() {
        detailPresenter?.detachView()
    }

    @Test
    @Throws(Exception::class)
    fun getPokemonDetailReturnsPokemon() {
        detailPresenter?.getPokemon(anyString())

        verify(mockDetailMvpView, times(2)).showProgress(anyBoolean())
        verify(mockDetailMvpView).showPokemon(pokemon)
        verify(mockDetailMvpView, never()).showError(RuntimeException())
    }

    @Test
    @Throws(Exception::class)
    fun getPokemonDetailReturnsError() {
        detailPresenter?.getPokemon("id")

        verify(mockDetailMvpView, times(2)).showProgress(anyBoolean())
        verify(mockDetailMvpView).showError(any())
        verify(mockDetailMvpView, never()).showPokemon(any())
    }
}