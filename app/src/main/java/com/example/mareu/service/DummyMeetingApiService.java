package com.example.mareu.service;


import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static com.example.mareu.fragments.ListMeetingFragment.initList;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.test.espresso.idling.CountingIdlingResource;


import com.example.mareu.model.Meeting;
import com.google.android.apps.common.testing.accessibility.framework.BuildConfig;

import org.joda.time.DateTime;
import java.util.List;
import java.util.Objects;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    // FOR TESTING
    @VisibleForTesting
    protected CountingIdlingResource espressoTestIdlingResource;

    private final LiveData<List<Meeting>> meetingList = DummyMeetingGenerator.generateMeeting();
    private final LiveData<List<Meeting>> filteredList =DummyMeetingGenerator
                                                .generateMeetingFilteredMeeting();

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public LiveData<List<Meeting>> getMeetings() {
        return meetingList;
    }


    @Override
    public void deleteMeeting(Meeting meeting) {
        this.getEspressoIdlingResource();
        //incrementIdleResource();
        Objects.requireNonNull(meetingList)
                .getValue()
                .remove(meeting);
        //decrementIdleResource();
    }


    @Override
    public void createMeeting(Meeting meeting) {
        Objects.requireNonNull(meetingList)
                .getValue()
                .add(meeting);
    }


    public LiveData<List<Meeting>> getMeetingsBydate(DateTime time){

        resetList();
        for(Meeting m : Objects.requireNonNull(meetingList).getValue()){
            if(m.getStartDate().toLocalDate().equals(time.toLocalDate())) {
                filteredList.getValue().add(m);
            }

        }
        return   filteredList;
    }

    @Override
    public void filterItemRoom(String room,Context contexta) {
        this.getEspressoIdlingResource();
        //this.incrementIdleResource();

        /* Filter by room */
        boolean nothing = true;
        for (Meeting m : Objects.requireNonNull(meetingList).getValue()) {
            if (m.getRoom().getName().equals(room)) {
                nothing = false;
                break;
            }
        }
        if (!nothing) {
            resetList();
            for(Meeting meeting : meetingList.getValue()){
                if(meeting.getRoom().getName().equals(room)){
                    Objects.requireNonNull(filteredList).getValue().add(meeting);
                }
            }

            initList(filteredList.getValue());
          //  this.decrementIdleResource();
        } else {

            try {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(contexta, "sdkfjsdmlkfjskldf",Toast.LENGTH_LONG).show();

                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public List<Meeting> getFilteredList() {
        return filteredList.getValue();
    }

    private void resetList() {
        for(Meeting m : Objects.requireNonNull(meetingList).getValue()){
            filteredList.getValue().clear();
        }
    }

    @VisibleForTesting
    public CountingIdlingResource getEspressoIdlingResource() {
        return espressoTestIdlingResource;
    }

    @VisibleForTesting
    private void configureEspressoIdlingResource(){
        this.espressoTestIdlingResource = new CountingIdlingResource("Network_Call");
    }

    protected void incrementIdleResource(){
        if (BuildConfig.DEBUG) this.espressoTestIdlingResource.increment();
    }

    protected void decrementIdleResource(){
        if (BuildConfig.DEBUG) this.espressoTestIdlingResource.decrement();
    }


}
