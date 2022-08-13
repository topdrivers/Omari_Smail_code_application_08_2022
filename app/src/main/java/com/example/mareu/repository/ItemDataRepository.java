package com.example.mareu.repository;

import androidx.lifecycle.LiveData;

import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Philippe on 27/02/2018.
 */

public class ItemDataRepository {

    private final MeetingApiService meetingApiService;

    public ItemDataRepository(MeetingApiService meetingApiService) { this.meetingApiService = meetingApiService; }

    // --- GET ---

    public LiveData<List<Meeting >> getMeetings(){ return this.meetingApiService.getMeetings(); }

    public LiveData<List<Meeting >> getMeetingsByDate(DateTime dateTime){ return this.meetingApiService.getMeetingsBydate(dateTime); }

    // --- CREATE ---

    public void createItem(Meeting meeting){ meetingApiService.createMeeting(meeting); }

    // --- DELETE ---
    public void deleteItem(Meeting meeting){ meetingApiService.deleteMeeting(meeting); }

    // --- Filter item by room ---

    public void filerItemRoom(String room) {meetingApiService.filterItemRoom(room);  }
}
