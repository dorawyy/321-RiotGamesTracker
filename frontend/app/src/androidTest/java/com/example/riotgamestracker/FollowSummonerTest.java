package com.example.riotgamestracker;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FollowSummonerTest {
    private final String REAL_USER_ID_STRING = "Gunner62";
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void followSummonerTest() {
        onView(withId(R.id.search)).perform(typeText(REAL_USER_ID_STRING));
        onView(withId(R.id.searchButton)).perform(click());
        onView(withId(R.id.summonerProfileSpinner)).check(matches(isDisplayed()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.summonerNameText)).check(matches(withText(REAL_USER_ID_STRING)));
        onView(withId(R.id.summonerFollowButton)).check(matches(withText("Follow")));
        onView(withId(R.id.summonerFollowButton)).perform(click());
        onView(withId(R.id.summonerFollowButton)).check(matches(withText("Unfollow")));
    }

    @Test
    public void unfollowSummonerTest() {
        onView(withId(R.id.search)).perform(typeText(REAL_USER_ID_STRING));
        onView(withId(R.id.searchButton)).perform(click());
        onView(withId(R.id.summonerProfileSpinner)).check(matches(isDisplayed()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.summonerNameText)).check(matches(withText(REAL_USER_ID_STRING)));
        onView(withId(R.id.summonerFollowButton)).check(matches(withText("Unfollow")));
        onView(withId(R.id.summonerFollowButton)).perform(click());
        onView(withId(R.id.summonerFollowButton)).check(matches(withText("Follow")));
    }

}