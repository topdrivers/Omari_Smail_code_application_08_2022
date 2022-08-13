package com.example.mareu.Utils;

import static com.example.mareu.Fragments.CreateMeetingFragment.meetingRoomsSpinner;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mareu.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SpinnerAdapterUtils {

    public static void configureSpinner(Context context) {
        /* Spinner to choose meeting room : */
        final ArrayList<String> meetingRooms = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.meeting_rooms_arrays)));
        meetingRooms.add(0, context.getString(R.string.choose_room));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,R.layout.spinner_item,meetingRooms){
            @Override
            public boolean isEnabled(int position) {

                // Disable the first item from Spinner, first item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);// Set the hint text color gray
                    tv.setTextSize(25);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        /* spinner menu process */
        meetingRoomsSpinner.setAdapter(spinnerAdapter);
        meetingRoomsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0 && view != null) {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

}
