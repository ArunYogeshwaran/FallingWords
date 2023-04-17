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
class GameScreenTest {

    private lateinit var context: Context

    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun gameScreenTest_verifyTextViews() {
        activityScenarioRule.scenario.onActivity {
            context = it
        }

        clickStartGame()

        verifyScoresText()

        verifyLivesTest()

        verifyEnglishWordTextView()

        verifyTimerTextView()
    }

    private fun verifyLivesTest() {
        val livesTextView = onView(
            allOf(
                withId(R.id.text_current_lives),
                withText(context.getString(R.string.lives_number, 3)),
                withParent(withParent(withId(R.id.nav_host_fragment_content_main))),
                isDisplayed()
            )
        )
        livesTextView.check(matches(withText("Lives: 3")))
    }

    private fun verifyScoresText() {
        val scoresTextView = onView(
            allOf(
                withId(R.id.text_current_score),
                withText(context.getString(R.string.score_number, 0)),
                withParent(withParent(withId(R.id.nav_host_fragment_content_main))),
                isDisplayed()
            )
        )
        scoresTextView.check(matches(withText("Score: 0")))
    }

    private fun clickStartGame() {
        val materialButton = onView(
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
        materialButton.perform(click())
    }

    private fun verifyTimerTextView() {
        val timerTextView = onView(
            allOf(
                withId(R.id.text_timer),
                withParent(withParent(withId(R.id.nav_host_fragment_content_main))),
                isDisplayed()
            )
        )
        timerTextView.check(matches(isDisplayed()))
    }

    private fun verifyEnglishWordTextView() {
        val textEnglishWord = onView(
            allOf(
                withId(R.id.text_english_word),
                withParent(withParent(withId(R.id.nav_host_fragment_content_main))),
                isDisplayed()
            )
        )
        textEnglishWord.check(matches(isDisplayed()))
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
