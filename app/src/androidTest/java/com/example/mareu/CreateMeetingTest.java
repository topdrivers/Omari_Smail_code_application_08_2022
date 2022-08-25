package com.example.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import static com.example.mareu.Matchers.detailsItemNameViewMatcher.childAtPosition;
import static com.example.mareu.Matchers.detailsItemViewMatcher.withTitle;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.activities.ListMeetingActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreateMeetingTest {


    @Rule
    public ActivityScenarioRule<ListMeetingActivity> mActivityRule =
            new ActivityScenarioRule<>(ListMeetingActivity.class);


    public static void setDate(int year, int monthOfYear, int dayOfMonth) {
        onView(withId(R.id.create_meeting_fragment_date_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());

    }

    public static void setTime(int timePickerLanchViewId, int hour,int minutes){
        onView(withId(timePickerLanchViewId)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour,minutes));
        onView(withId(android.R.id.button1)).perform(click());
    }

    /**
     * We ensure that our info item name is correct
     */
    @Test
    public void check_creating_meeting_item() {

        //Click floating add meeting button
        onView(withId(R.id.list_meeting_activity_add_meeting))
                .perform(click());

        onView(withId(R.id.create_meeting_fragment_name))
                .perform(replaceText("Réunion test"));

        setDate(2022, 9, 25);
        setTime(R.id.create_meeting_fragment_start_hour_button,3,10);

        setTime(R.id.create_meeting_fragment_end_hour_button,5,8);

        onView(ViewMatchers.withId(R.id.create_meeting_fragment_spinner)).perform(click());
                DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(withClassName(is("androidx.appcompat.widget.DropDownListView")))
                .atPosition(6);
        appCompatTextView.perform(click());


        ViewInteraction appCompatAutoCompleteTextView = onView(
                 allOf(withId(R.id.participant_autoCompleteTextView),
                         childAtPosition(
                                 childAtPosition(
                                         withId(R.id.create_meeting_fragment_email_input),
                                         0),
                                 0),
                         isDisplayed()));
            appCompatAutoCompleteTextView.perform(replaceText("smail.omari@laposte.net"), closeSoftKeyboard());
            onView(ViewMatchers.withId(R.id.addParticipant_button)).perform(click());
            onView(ViewMatchers.withId(R.id.create_meeeting_fragment_save_button)).perform(click());


        //check item name in recycler view in ListmeetingFragment
        ViewInteraction textView = onView(
                allOf(withId(R.id.item_name),
                        childAtPosition(
                                allOf(withId(R.id.constraint_layout),
                                        childAtPosition(
                                                withId(R.id.recyclerview_list_meeting),
                                                6)),
                                3),
                        isDisplayed()));

        textView.check(matches(withTitle("Réunion test")));

        }


    }

