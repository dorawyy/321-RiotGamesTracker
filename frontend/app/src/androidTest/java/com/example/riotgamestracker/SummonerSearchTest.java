package com.example.riotgamestracker;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SummonerSearchTest {
    private final String REAL_USER_ID_STRING = "Gunner62";
    private final String FAKE_USER_ID_STRING = "fake user";

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void summonerSearchTest() {
        onView(withId(R.id.search)).perform(typeText(REAL_USER_ID_STRING));
        onView(withId(R.id.searchButton)).perform(click());
        onView(withId(R.id.summonerProfileSpinner)).check(matches(isDisplayed()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.summonerNameText)).check(matches(withText(REAL_USER_ID_STRING)));
    }

    @Test
    public void summonerSearchNotFoundTest() {
        onView(withId(R.id.search)).perform(typeText(FAKE_USER_ID_STRING));
        onView(withId(R.id.searchButton)).perform(click());
        onView(withId(R.id.summonerProfileSpinner)).check(matches(isDisplayed()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.summonerErrorText)).check(matches(withText("Summoner not found")));
    }
}