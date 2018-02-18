package com.moufee.a14cup;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Ben on 2/15/18.
 * This class will probably need to be modified to mock the data source (ViewModel)
 * to verify that data is correctly displayed and the view updates as the data changes
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testTest() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText("My New List")).check(doesNotExist());
        onView(withId(R.id.newListButton)).perform(click());
        onView(withId(R.id.list_name)).check(matches(isDisplayed()));
        onView(withId(R.id.list_name)).perform(typeText("My New List"));
        onView(withText(R.string.action_new_list_positive)).perform(click());
//        onView(withText("My New List")).check(matches(isDisplayed()));
    }
}
