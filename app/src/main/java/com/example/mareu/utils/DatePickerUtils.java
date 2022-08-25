package com.example.mareu.utils;

import static com.example.mareu.fragments.ListMeetingFragment.itemViewModel;
import static com.example.mareu.fragments.ListMeetingFragment.mRecyclerView;

import static com.example.mareu.fragments.ListMeetingFragment.myMeetingRecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import com.example.mareu.views.MyMeetingRecyclerViewAdapter;
import com.example.mareu.model.Meeting;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.List;

public class DatePickerUtils {

    public static void configureDialogCalendar(Context context) {


        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogDate = new DatePickerDialog(context, generateDatePickerDialog(), year, month, day);
        dialogDate.getDatePicker().setMinDate(System.currentTimeMillis());
        dialogDate.show();
    }
    
    @SuppressLint("NotifyDataSetChanged")
    public static DatePickerDialog.OnDateSetListener generateDatePickerDialog() {


        return (view, year, monthOfYear, dayOfMonth) -> {

            DateTime time = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
            List<Meeting> meetingList;

            /* Filtre par date */
            boolean nothing = true;
            for (Meeting m : itemViewModel.getMeetings().getValue()) {
                if (m.getStartDate().toLocalDate().equals(time.toLocalDate())) {
                    nothing = false;
                }
            }
            if (!nothing) {

                meetingList = itemViewModel.getMeetingsByDate(time).getValue();
                myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(meetingList);
                mRecyclerView.setAdapter( myMeetingRecyclerViewAdapter);
                myMeetingRecyclerViewAdapter.notifyDataSetChanged();

            } else {
                ToastUtils.showToastLong("Aucune réunion prévue à cette date", view.getContext());
            }
        };

    }
}
