package com.example.riotgamestracker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MatchHistoryTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void summonerSearchTest() {
        onView(withId(R.id.search)).perform(typeText("Gunner62"));
        onView(withId(R.id.searchButton)).perform(click());
        onView(withId(R.id.summonerProfileSpinner)).check(matches(isDisplayed()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.summonerNameText)).check(matches(withText("Gunner62")));
        onView(withId(R.id.summonerLastMatchButton)).perform(click());
        onView(withId(R.id.matchHistorySpinner)).check(matches(isDisplayed()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onData(anything()).inAdapterView(withId(R.id.matchHistoryListView)).atPosition(0).
                onChildView(withId(R.id.matchHistoryHeaderText)).
                check(matches(withText("Winners")));
        onData(anything()).inAdapterView(withId(R.id.matchHistoryListView)).atPosition(6).
                onChildView(withId(R.id.matchHistoryHeaderText)).
                check(matches(withText("Losers")));
    }

}