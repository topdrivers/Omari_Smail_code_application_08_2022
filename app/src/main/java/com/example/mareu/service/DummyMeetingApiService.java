package com.example.mareu.service;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static com.example.mareu.fragments.ListMeetingFragment.initList;
import static com.example.mareu.fragments.ListMeetingFragment.mRecyclerView;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.mareu.activities.ListMeetingActivity;
import com.example.mareu.fragments.ListMeetingFragment;
import com.example.mareu.utils.ToastUtils;

import com.example.mareu.model.Meeting;
import org.joda.time.DateTime;
import java.util.List;
import java.util.Objects;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> meetingList = DummyMeetingGenerator.generateMeeting();
    private final List<Meeting> filteredList =DummyMeetingGenerator.generateMeetingFilteredMeeting();

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetingList;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        Objects.requireNonNull(meetingList).remove(meeting);
    }

    /**
     * {@inheritDoc}
     * @param
     */
    @Override
    public void createMeeting(Meeting meeting) {
        Objects.requireNonNull(meetingList).add(meeting);
    }



    public List<Meeting> getMeetingsBydate(DateTime time){

        resetList();
        for(Meeting m : Objects.requireNonNull(meetingList)){
            if(m.getStartDate().toLocalDate().equals(time.toLocalDate())) {
                filteredList.add(m);
            }

        }
        return   filteredList;
    }

    @Override
    public void filterItemRoom(String room,Context context) {
        /* Filter by room */
        boolean nothing = true;
        for (Meeting m : Objects.requireNonNull(meetingList)) {
            if (m.getRoom().getName().equals(room)) {
                nothing = false;
                break;
            }
        }
        if (!nothing) {
            resetList();
            for(Meeting meeting : meetingList){
                if(meeting.getRoom().getName().equals(room)){
                    Objects.requireNonNull(filteredList).add(meeting);
                }
            }

            initList(filteredList);
        } else {
//listMeetingFragment= listMeetingFragment.newInstance();

            try {
                runOnUiThread(new Runnable() {
                    public void run() {
                        InstrumentationRegistry.getInstrumentation();
                        //Toast.makeText(getApplicationContext(), "Could not find weather :(", Toast.LENGTH_SHORT).show();
                        ToastUtils.showToastLong("Aucune réunion de prévue dans cette salle", new ListMeetingActivity().getApplicationContext());
                        //new ListMeetingFragment().getView().getContext()
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public List<Meeting> getFilteredList() {
        return filteredList;
    }

    private void resetList() {
        for(Meeting m : Objects.requireNonNull(meetingList)){
            filteredList.clear();
        }
    }


}
