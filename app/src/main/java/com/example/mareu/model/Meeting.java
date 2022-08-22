package com.example.mareu.model;

import static com.example.mareu.fragments.ListMeetingFragment.itemViewModel;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Model object representing a Neighbour
 */
public class Meeting implements Serializable {

    /** Identifier */
    private long id;

    /** title meeting subject */
    private String title;

    /** list of participant */
    private final String participantList;

    /** begining date meeting */
    private final DateTime startDate;

    /** end date meeting */
    private final DateTime endTime;

    /** Room selected meeting */
    private final Room room;

    /** Room status meeting */
    private  String status;


    public Meeting(long id, String title, String participantList, DateTime startDate, DateTime endTime, Room room) {
        this.id = id;
        this.title = title;
        this.participantList = participantList;
        this.startDate = startDate;
        this.endTime = endTime;
        this.room = room;
    }





    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getParticipantList() {
        return participantList;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public Room getRoom() {
        return room;
    }

    public String getStatus (){
        List<Meeting> meetingList = itemViewModel.getMeetings();
        for ( Meeting meeting : meetingList  ){
            Date currentTime = Calendar.getInstance().getTime();
            float diffenceTime = meeting.getStartDate().toDate().getTime() - currentTime.getTime();
            float differenceInHours = (diffenceTime) / 1000L / 60L / 60L; // Divide by millis/sec, secs/min, mins/hr


            if(differenceInHours<=1.5 && differenceInHours>=0){
                meeting.setStatus("BETWEEN");
            }else if(differenceInHours > 1.5){
                meeting.setStatus("AFTER");
            }else {
                meeting.setStatus("BEFORE");
            }
        }
        return status;}


    public void setId(long id) {
        this.id = id;
    }


    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
