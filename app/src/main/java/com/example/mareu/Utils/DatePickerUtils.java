package com.example.mareu.Utils;

import static com.example.mareu.Fragments.ListMeetingFragment.itemViewModel;
import static com.example.mareu.Fragments.ListMeetingFragment.mRecyclerView;
import static com.example.mareu.Fragments.ListMeetingFragment.meetingApiService;
import static com.example.mareu.Fragments.ListMeetingFragment.myMeetingRecyclerViewAdapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import androidx.lifecycle.LiveData;

import com.example.mareu.DI.DI;
import com.example.mareu.Fragments.ListMeetingFragment;
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
    public static DatePickerDialog.OnDateSetListener generateDatePickerDialog() {
        //ListMeetingFragment listMeetingFragment = ListMeetingFragment.newInstance();
        meetingApiService =DI.getMeetingApiService();

        return new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                DateTime time = new DateTime(year, monthOfYear + 1, dayOfMonth, 00, 00);
                LiveData<List<Meeting>> meetingList;

                /* Filtre par date */
                boolean nothing = true;
                for (Meeting m : itemViewModel.getMeetings().getValue()) {
                    if (m.getStartDate().toLocalDate().equals(time.toLocalDate())) {
                        nothing = false;
                    }
                }
                if (!nothing) {
                    //initList(mApiService.getMeetingsBydate(time));
/*
                    meetingList = meetingApiService.getMeetingsBydate(time);
                    myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(meetingList);
                    mRecyclerView.setAdapter( myMeetingRecyclerViewAdapter);
                    myMeetingRecyclerViewAdapter.notifyDataSetChanged();

 */
                    //-------------------------------------------------------
                    meetingList = itemViewModel.getMeetingsByDate(time);
                    myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(meetingList.getValue());
                    mRecyclerView.setAdapter( myMeetingRecyclerViewAdapter);
                    myMeetingRecyclerViewAdapter.notifyDataSetChanged();

                } else {
                    ToastUtils.showToastLong("Aucune réunion prévue à cette date", view.getContext());
                }
            }
        };

    }
}
