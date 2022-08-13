package com.example.mareu.service;

import static com.example.mareu.Fragments.ListMeetingFragment.initList;
import static com.example.mareu.Fragments.ListMeetingFragment.meetingApiService;
import static com.example.mareu.Fragments.ListMeetingFragment.myMeetingRecyclerViewAdapter;
import static com.example.mareu.service.DummyMeetingGenerator.DUMMY_METINGS;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mareu.DI.DI;
import com.example.mareu.Fragments.ListMeetingFragment;
import com.example.mareu.Utils.ToastUtils;
import com.example.mareu.Views.MyMeetingRecyclerViewAdapter;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService, LifecycleOwner {


    ListMeetingFragment listMeetingFragment;

    private final LiveData<List<Meeting>> meetingList = DummyMeetingGenerator.generateMeeting();
    //private List<Meeting> filteredList = new ArrayList<>();
//    private final LiveData<List<Meeting>> filteredList = DummyMeetingGenerator.generateMeetingFilteredMeeting();
    private final LiveData<List<Meeting>> filteredList =DummyMeetingGenerator.generateMeetingFilteredMeeting();

    //private LiveData<List<Meeting>> filteredList;




    //private List<Meeting> filteredList = new ArrayList<>();
    //private LiveData<List<Meeting>> filteredList = new LiveData<List<Meeting>>() {   };



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
    public void deleteMeeting(Meeting meeting) { Objects.requireNonNull(meetingList.getValue()).remove(meeting);
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
        /* Filtre par salle */
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
           // meetingApiService.getMeetingsFilteredByRoom(room);
            //myMeetingRecyclerViewAdapter.notifyDataSetChanged();


        } else {
            ToastUtils.showToastLong("Aucune réunion de prévue dans cette salle", listMeetingFragment.getContext());
        }
    }


    private void resetList() {
        for(Meeting m : Objects.requireNonNull(meetingList.getValue())){
            filteredList.getValue().clear();
        }
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}
