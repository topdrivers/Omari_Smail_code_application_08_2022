package com.example.mareu.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

import org.joda.time.DateTime;

import java.util.List;


public class ItemDataRepository {

    private final MeetingApiService meetingApiService;

    public ItemDataRepository(MeetingApiService meetingApiService) {
        this.meetingApiService = meetingApiService;
    }

    // --- GET ---

    public List<Meeting > getMeetings(){ return this.meetingApiService.getMeetings(); }

    public List<Meeting > getMeetingsByDate(DateTime dateTime){
        return this.meetingApiService.getMeetingsBydate(dateTime);
    }

    // --- CREATE ---

    public void createItem(Meeting meeting){ meetingApiService.createMeeting(meeting); }

    // --- DELETE ---
    public void deleteItem(Meeting meeting){ meetingApiService.deleteMeeting(meeting); }

    // --- Filter item by room ---

    public void filerItemRoom(String room, Context context) {meetingApiService.filterItemRoom(room,context);  }
}
