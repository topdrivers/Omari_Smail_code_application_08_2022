package com.example.mareu.Utils;

import android.widget.Spinner;
import android.widget.TextView;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;
import com.example.mareu.service.MeetingApiService;

import org.joda.time.DateTime;

public class MeetingUtils {
    public static Meeting newMeeting(MeetingApiService apiservice, TextView textView, DateTime dateTime, DateTime beginTime, DateTime endTime, String participants, Spinner spinner) {

        /* Création nouveau meeting */

        return new Meeting(
                IdUtils.SetId(apiservice),
                textView.getText().toString(),
                participants,
                new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), beginTime.getHourOfDay(), beginTime.getMinuteOfHour()),
                new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), endTime.getHourOfDay(), endTime.getMinuteOfHour()),

                new Room(20,spinner.getSelectedItem().toString()));
    }
}
