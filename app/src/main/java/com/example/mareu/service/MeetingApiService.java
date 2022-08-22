package com.example.mareu.service;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mareu.model.Meeting;
import org.joda.time.DateTime;

import java.util.List;


/**
 * Meeting API client
 */
public interface MeetingApiService {

    /**
     * Get all my Meeting
     * @return {@link List}
     */
    List<Meeting> getMeetings();



    /**
     * Deletes a meeting
     * param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     * param meeting
     */
    void createMeeting(Meeting meeting);


    //Get meeting by date
    List<Meeting> getMeetingsBydate(DateTime dateTime);

    //Filter item by room
    void filterItemRoom(String room, Context context);

    //get filtered item by room
    public List<Meeting> getFilteredList() ;
}
