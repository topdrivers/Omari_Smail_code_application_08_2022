package com.example.mareu.service;

import static com.example.mareu.Fragments.ListMeetingFragment.initList;
import androidx.lifecycle.LiveData;
import com.example.mareu.Fragments.ListMeetingFragment;
import com.example.mareu.Utils.ToastUtils;

import com.example.mareu.model.Meeting;
import org.joda.time.DateTime;
import java.util.List;
import java.util.Objects;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {


    ListMeetingFragment listMeetingFragment;

    private final LiveData<List<Meeting>> meetingList = DummyMeetingGenerator.generateMeeting();
    private final LiveData<List<Meeting>> filteredList =DummyMeetingGenerator.generateMeetingFilteredMeeting();

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public LiveData<List<Meeting>> getMeetings() {
        return meetingList;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        Objects.requireNonNull(meetingList.getValue()).remove(meeting);
    }

    /**
     * {@inheritDoc}
     * @param
     */
    @Override
    public void createMeeting(Meeting meeting) {
        Objects.requireNonNull(meetingList.getValue()).add(meeting);
    }



    public LiveData<List<Meeting>> getMeetingsBydate(DateTime time){

        resetList();
        for(Meeting m : Objects.requireNonNull(meetingList.getValue())){
            if(m.getStartDate().toLocalDate().equals(time.toLocalDate())) {
                filteredList.getValue().add(m);
            }

        }
        return   filteredList;
    }

    @Override
    public void filterItemRoom(String room) {
        /* Filter by room */
        boolean nothing = true;
        for (Meeting m : Objects.requireNonNull(meetingList.getValue())) {
            if (m.getRoom().getName().equals(room)) {
                nothing = false;
                break;
            }
        }
        if (!nothing) {
            resetList();
            for(Meeting meeting : meetingList.getValue()){
                if(meeting.getRoom().getName().equals(room)){
                    Objects.requireNonNull(filteredList.getValue()).add(meeting);
                }
            }

            initList(filteredList.getValue());
        } else {
            ToastUtils.showToastLong("Aucune réunion de prévue dans cette salle", listMeetingFragment.getContext());
        }
    }

    private void resetList() {
        for(Meeting m : Objects.requireNonNull(meetingList.getValue())){
            filteredList.getValue().clear();
        }
    }


}
