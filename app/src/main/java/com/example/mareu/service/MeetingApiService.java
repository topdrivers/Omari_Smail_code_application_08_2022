package com.example.mareu.service;

import androidx.lifecycle.LiveData;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import org.joda.time.DateTime;

import java.util.List;


/**
 * Neighbour API client
 */
public interface MeetingApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    LiveData<List<Meeting>> getMeetings();



    /**
     * Deletes a neighbour
     * param neighbour
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a neighbour
     * param neighbour
     */
    void createMeeting(Meeting meeting);



    LiveData<List<Meeting>> getMeetingsBydate(DateTime dateTime);

    void filterItemRoom(String room);
}
