package com.example.mareu.Utils;

import static com.example.mareu.Fragments.ListMeetingFragment.itemViewModel;
import static com.example.mareu.Fragments.ListMeetingFragment.mRecyclerView;
import static com.example.mareu.Fragments.ListMeetingFragment.meetingApiService;
import static com.example.mareu.Fragments.ListMeetingFragment.myMeetingRecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import androidx.lifecycle.LiveData;

import com.example.mareu.DI.DI;
import com.example.mareu.Views.MyMeetingRecyclerViewAdapter;
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
        meetingApiService =DI.getMeetingApiService();

        return (view, year, monthOfYear, dayOfMonth) -> {

            DateTime time = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
            LiveData<List<Meeting>> meetingList;

            /* Filtre par date */
            boolean nothing = true;
            for (Meeting m : itemViewModel.getMeetings().getValue()) {
                if (m.getStartDate().toLocalDate().equals(time.toLocalDate())) {
                    nothing = false;
                }
            }
            if (!nothing) {

                meetingList = itemViewModel.getMeetingsByDate(time);
                myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(meetingList.getValue());
                mRecyclerView.setAdapter( myMeetingRecyclerViewAdapter);
                myMeetingRecyclerViewAdapter.notifyDataSetChanged();

            } else {
                ToastUtils.showToastLong("Aucune réunion prévue à cette date", view.getContext());
            }
        };

    }
}
