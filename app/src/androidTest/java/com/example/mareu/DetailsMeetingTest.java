package com.example.mareu;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.mareu.Matchers.detailsItemViewMatcher.withTitle;

import android.content.Context;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.activities.ListMeetingActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class DetailsMeetingTest {

        // FOR DATA
        private Context context;

        @Rule
        public ActivityScenarioRule<ListMeetingActivity> mActivityRule =
                new ActivityScenarioRule<>(ListMeetingActivity.class);

        @Before
        public void setup() {this.context =  getApplicationContext().getApplicationContext();}

        /**
         * We ensure that our info item  is correct
         */
        @Test
        public void check_fields_match_with_item_parent() {

            //Click first item main recyclerview
            onView(withId(R.id.recyclerview_list_meeting))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

            //check field name equals Réunion 1
            onView(withId(R.id.details_meeting_fragment_name))
                    .check(matches(withTitle(context.getString((R.string.textview_name_for_test)))));

            //check field date
            onView(withId(R.id.details_meeting_fragment_date_textView))
                    .check(matches(withTitle(context.getString((R.string.textview_date_for_test)))));

            //check field start hour
            onView(withId(R.id.details_meeting_fragment_start_hour))
                    .check(matches(withTitle(context.getString((R.string.textview_start_hour_for_test)))));

            //check field end hour
            onView(withId(R.id.details_meeting_fragment_end_hour))
                    .check(matches(withTitle(context.getString((R.string.textview_end_hour_for_test)))));

        }

        @Test
        public void check_onClickItem_detailActivityOpened() {
            //detailActivuty launched
            //Click item
            onView(withId(R.id.recyclerview_list_meeting))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
            //item name is displayed
            onView(withId(R.id.details_meeting_fragment_name)).check(matches(isDisplayed()));
        }

    }
