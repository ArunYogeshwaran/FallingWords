package com.aruny.fallingwords.view.espressotests


import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aruny.fallingwords.R
import com.aruny.fallingwords.view.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WelcomeScreenTest {

    private lateinit var context: Context

    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun welcomeScreenTest_verifyTitle() {
        activityScenarioRule.scenario.onActivity {
            context = it
        }

        val titleText = onView(
            allOf(
                withId(R.id.text_welcome_title),
                withText(context.getString(R.string.welcome_text_title)),
                withParent(withParent(withId(R.id.nav_host_fragment_content_main))),
                isDisplayed()
            )
        )
        titleText.check(matches(withText(context.getString(R.string.welcome_text_title))))
    }

    @Test
    fun welcomeScreenTest_verifySubtitle() {
        activityScenarioRule.scenario.onActivity {
            context = it
        }

        val welcomeSubtitleText = onView(
            allOf(
                withId(R.id.text_welcome_subtitle),
                withText(context.getString(R.string.welcome_text_subtitle)),
                withParent(withParent(withId(R.id.nav_host_fragment_content_main))),
                isDisplayed()
            )
        )
        welcomeSubtitleText.check(
            matches(withText(context.getString(R.string.welcome_text_subtitle)))
        )
    }

    @Test
    fun welcomeScreenTest_verifyNote() {
        activityScenarioRule.scenario.onActivity {
            context = it
        }

        val welcomeNoteText = onView(
            allOf(
                withId(R.id.text_welcome_note),
                withText(context.getString(R.string.welcome_text_note)),
                withParent(withParent(withId(R.id.nav_host_fragment_content_main))),
                isDisplayed()
            )
        )
        welcomeNoteText.check(
            matches(withText(context.getString(R.string.welcome_text_note)))
        )
    }

    @Test
    fun welcomeScreenTest_verifyStateButtonState() {
        activityScenarioRule.scenario.onActivity {
            context = it
        }

        val button = onView(
            allOf(
                withId(R.id.button_start_game),
                withText(context.getString(R.string.start_game))
            )
        )
        button.check(matches(isDisplayed()))
    }
}
