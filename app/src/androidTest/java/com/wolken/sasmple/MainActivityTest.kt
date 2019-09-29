package com.wolken.sasmple

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.wolken.sasmple.common.TestComponentRule
import com.wolken.sasmple.common.TestDataFactory
import com.wolken.sasmple.data.model.Pokemon
import com.wolken.sasmple.features.main.MainActivity
import com.wolken.sasmple.util.ErrorTestUtil
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val component = TestComponentRule(InstrumentationRegistry.getTargetContext())
    private val main = ActivityTestRule(MainActivity::class.java, false, false)

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule @JvmField
    var chain: TestRule = RuleChain.outerRule(component).around(main)

    @Test
    fun checkPokemonsDisplay() {
        val namedResourceList = TestDataFactory.makeNamedResourceList(5)
        val pokemonList = TestDataFactory.makePokemonNameList(namedResourceList)
        stubDataManagerGetPokemonList(Single.just(pokemonList))
        main.launchActivity(null)

        for (pokemonName in namedResourceList) {
            onView(withText(pokemonName.name))
                    .check(matches(isDisplayed()))
        }
    }

    @Test
    fun clickingPokemonLaunchesDetailActivity() {
        val namedResourceList = TestDataFactory.makeNamedResourceList(5)
        val pokemonList = TestDataFactory.makePokemonNameList(namedResourceList)
        stubDataManagerGetPokemonList(Single.just(pokemonList))
        stubDataManagerGetPokemon(Single.just(TestDataFactory.makePokemon("id")))
        main.launchActivity(null)

        onView(withText(pokemonList[0]))
                .perform(click())

        onView(withId(R.id.image_pokemon))
                .check(matches(isDisplayed()))
    }

    @Test
    fun checkErrorViewDisplays() {
        stubDataManagerGetPokemonList(Single.error<List<String>>(RuntimeException()))
        main.launchActivity(null)
        ErrorTestUtil.checkErrorViewsDisplay()
    }

    fun stubDataManagerGetPokemonList(single: Single<List<String>>) {
        `when`(component.mockDataManager.getPokemonList(ArgumentMatchers.anyInt()))
                .thenReturn(single)
    }

    fun stubDataManagerGetPokemon(single: Single<Pokemon>) {
        `when`(component.mockDataManager.getPokemon(ArgumentMatchers.anyString()))
                .thenReturn(single)
    }

}