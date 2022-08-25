package com.example.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.example.mareu.Matchers.detailsItemNameViewMatcher.childAtPosition;
import static com.example.mareu.Matchers.detailsItemViewMatcher.withTitle;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.activities.ListMeetingActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
//@RunWith(RobolectricTestRunner.class)
public class FilterMeetingTest {

    public static void setDate(int year, int monthOfYear, int dayOfMonth) {
        onView(withText("Sélectionner date")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());

    }

    public static void setTime(int timePickerLanchViewId, int hour,int minutes){
        onView(withId(timePickerLanchViewId)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour,minutes));
        onView(withId(android.R.id.button1)).perform(click());
    }

    @Rule
    public ActivityScenarioRule<ListMeetingActivity> mActivityRule =
            new ActivityScenarioRule<>(ListMeetingActivity.class);

    @Before
    public void setUp() {
        ActivityScenario<ListMeetingActivity> mActivity = mActivityRule.getScenario();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void filter_meeting_item_by_room() {

        // Click menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText("Filtrer"))
                .perform(click());

        onView(withText("Sélectionner salle"))
                .perform(click());

        onView(withText("Paintsilvia"))
                .perform(click());

        //check first item text room matches with paintsilvia room filter
        ViewInteraction roomtextView_first_item = onView(
                allOf(withId(R.id.item_room_textView),
                        childAtPosition(
                                allOf(withId(R.id.constraint_layout),
                                        childAtPosition(
                                                withId(R.id.recyclerview_list_meeting),
                                                0)),
                                7),
                        isDisplayed()));

        roomtextView_first_item.check(matches(withTitle("Paintsilvia")));

        //check second item text room matches with paintsilvia room filter
        ViewInteraction roomtextView_second_item = onView(
                allOf(withId(R.id.item_room_textView),
                        childAtPosition(
                                allOf(withId(R.id.constraint_layout),
                                        childAtPosition(
                                                withId(R.id.recyclerview_list_meeting),
                                                1)),
                                7),
                        isDisplayed()));

        roomtextView_second_item.check(matches(withTitle("Paintsilvia")));


    }

    @Test
    public void filter_meeting_item_by_date() {

        // Click menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText("Filtrer"))
                .perform(click());


        setDate(2022, 9, 30);

        //check item text date matches with date select filter
        ViewInteraction date_text_View = onView(
                allOf(withId(R.id.item_time_textView),
                        childAtPosition(
                                allOf(withId(R.id.constraint_layout),
                                        childAtPosition(
                                                withId(R.id.recyclerview_list_meeting),
                                                0)),
                                6),
                        isDisplayed()));

        date_text_View.check(matches(withTitle("30/09 23:30")));


    }
}
