package com.wolken.sasmple

import com.wolken.sasmple.common.TestComponentRule
import com.wolken.sasmple.common.TestDataFactory
import com.wolken.sasmple.data.model.Pokemon
import com.wolken.sasmple.features.detail.DetailActivity
import com.wolken.sasmple.util.ErrorTestUtil
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    val component = TestComponentRule(InstrumentationRegistry.getTargetContext())
    val main = ActivityTestRule(DetailActivity::class.java, false, false)

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule @JvmField
    var chain: TestRule = RuleChain.outerRule(component).around(main)

    @Test
    fun checkPokemonDisplays() {
        val pokemon = TestDataFactory.makePokemon("id")
        stubDataManagerGetPokemon(Single.just(pokemon))
        main.launchActivity(
                DetailActivity.getStartIntent(InstrumentationRegistry.getContext(), pokemon.name))

        for (stat in pokemon.stats) {
            onView(withText(stat.stat?.name))
                    .check(matches(isDisplayed()))
        }
    }

    @Test
    fun checkErrorViewDisplays() {
        stubDataManagerGetPokemon(Single.error<Pokemon>(RuntimeException()))
        val pokemon = TestDataFactory.makePokemon("id")
        main.launchActivity(
                DetailActivity.getStartIntent(InstrumentationRegistry.getContext(), pokemon.name))
        ErrorTestUtil.checkErrorViewsDisplay()
    }

    fun stubDataManagerGetPokemon(single: Single<Pokemon>) {
        `when`(component.mockDataManager.getPokemon(ArgumentMatchers.anyString()))
                .thenReturn(single)
    }

}