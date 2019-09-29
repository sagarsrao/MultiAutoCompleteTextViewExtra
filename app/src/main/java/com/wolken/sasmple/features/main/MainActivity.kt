package com.wolken.sasmple.features.main

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.ArrayAdapter
import com.doodle.android.chips.ChipsView
import com.wolken.sasmple.R
import com.wolken.sasmple.features.base.BaseActivity
import com.wolken.sasmple.features.common.ErrorView
import com.wolken.sasmple.features.detail.DetailActivity
import com.wolken.sasmple.features.main.models.ResponseNews
import com.wolken.sasmple.util.gone
import com.wolken.sasmple.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMvpView, PokemonAdapter.ClickListener, ErrorView.ErrorListener {

    override fun getListNews(pokemons: ResponseNews) {

        Log.d("pokemons",""+pokemons)
        val artcilesList = pokemons.articles
        val emptyList = ArrayList<String>()

        for(item in artcilesList!!){
            emptyList.add(item!!.author!!)
        }

        val array_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, emptyList)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


    }

    @Inject lateinit var pokemonAdapter: PokemonAdapter
    @Inject lateinit var mainPresenter: MainPresenter


    lateinit var mChipsView:ChipsView
    companion object {
        private val POKEMON_COUNT = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mainPresenter.attachView(this)

        setSupportActionBar(main_toolbar)
        mChipsView = findViewById(R.id.chipsView)
        swipeToRefresh?.apply {
            setProgressBackgroundColorSchemeResource(R.color.primary)
            setColorSchemeResources(R.color.white)
            setOnRefreshListener { mainPresenter.getPokemon(POKEMON_COUNT) }
        }

        pokemonAdapter.setClickListener(this)
        recyclerPokemon?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pokemonAdapter
        }

        viewError?.setErrorListener(this)

      //  mainPresenter.getPokemon(POKEMON_COUNT)

        mChipsView.setChipsListener(object :ChipsView.ChipsListener{
            override fun onInputNotRecognized(text: String?): Boolean {
               return true
            }

            override fun onChipDeleted(chip: ChipsView.Chip?) {

            }

            override fun onChipAdded(chip: ChipsView.Chip?) {

            }

            override fun onTextChanged(text: CharSequence?) {

                val q = text.toString()
                mainPresenter.getNesList(q)
            }

        })

    }

    override fun layoutId() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }

    override fun showPokemon(pokemon: List<String>) {
        pokemonAdapter.apply {
            setPokemon(pokemon)
            notifyDataSetChanged()
        }

        recyclerPokemon?.visible()
        swipeToRefresh?.visible()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            if (recyclerPokemon?.visibility == View.VISIBLE && pokemonAdapter.itemCount > 0) {
                swipeToRefresh?.isRefreshing = true
            } else {
                progressBar?.visible()
                recyclerPokemon?.gone()
                swipeToRefresh?.gone()
            }

            viewError?.gone()
        } else {
            swipeToRefresh?.isRefreshing = false
            progressBar?.gone()
        }
    }

    override fun showError(error: Throwable) {
        recyclerPokemon?.gone()
        swipeToRefresh?.gone()
        viewError?.visible()
        Timber.e(error, "There was an error retrieving the pokemon")
    }

    override fun onPokemonClick(pokemon: String) {
        startActivity(DetailActivity.getStartIntent(this, pokemon))
    }

    override fun onReloadData() {
        mainPresenter.getPokemon(POKEMON_COUNT)
    }

}