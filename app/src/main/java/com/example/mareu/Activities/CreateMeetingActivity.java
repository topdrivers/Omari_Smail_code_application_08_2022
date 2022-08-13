package com.example.mareu.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mareu.Fragments.CreateMeetingFragment;
import com.example.mareu.Fragments.ListMeetingFragment;
import com.example.mareu.R;

public class CreateMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        configureAndShowCreateMeetingFragment();
    }

    private void configureAndShowCreateMeetingFragment() {
        CreateMeetingFragment createMeetingFragment = (CreateMeetingFragment) getSupportFragmentManager().findFragmentById(R.id.frame_create_meeting_activity);

        if(createMeetingFragment==null){
            createMeetingFragment = new CreateMeetingFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_create_meeting_activity, createMeetingFragment)
                    .commit();
        }
    }
}