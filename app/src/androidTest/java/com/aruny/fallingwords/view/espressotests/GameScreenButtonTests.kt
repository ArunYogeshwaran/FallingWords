package com.aruny.fallingwords.view.espressotests


import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.aruny.fallingwords.R
import com.aruny.fallingwords.view.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class GameScreenButtonTests {
    private lateinit var context: Context

    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun gameScreenButtonTests() {
        activityScenarioRule.scenario.onActivity {
            context = it
        }

        clickStartGame()

        veifyWrongButton()

        verifyCorrectButton()
    }

    private fun verifyCorrectButton() {
        val correctButton = onView(
            allOf(
                withId(R.id.button_correct),
                withContentDescription(context.getString(R.string.correct_button)),
                withParent(withParent(withId(R.id.nav_host_fragment_content_main))),
                isDisplayed()
            )
        )
        correctButton.check(matches(isDisplayed()))
    }

    private fun veifyWrongButton() {
        val wrongButton = onView(
            allOf(
                withId(R.id.button_wrong),
                withContentDescription(context.getString(R.string.wrong_button)),
                withParent(withParent(withId(R.id.nav_host_fragment_content_main))),
                isDisplayed()
            )
        )
        wrongButton.check(matches(isDisplayed()))
    }

    private fun clickStartGame() {
        val startGameButton = onView(
            allOf(
                withId(R.id.button_start_game), withText(context.getString(R.string.start_game)),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        startGameButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
