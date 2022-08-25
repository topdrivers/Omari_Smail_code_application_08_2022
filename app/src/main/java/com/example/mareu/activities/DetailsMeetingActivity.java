package com.example.mareu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mareu.fragments.DetailsMeetingFragment;
import com.example.mareu.R;
import com.example.mareu.model.Meeting;

public class DetailsMeetingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_meeting);
        configureAndShowDetailsmeetingFragment((Meeting)getIntent().getSerializableExtra("id"));

    }

    private void configureAndShowDetailsmeetingFragment(Meeting meeting) {

        DetailsMeetingFragment detailsMeetingFragment = (DetailsMeetingFragment) getSupportFragmentManager().findFragmentById(R.id.frame_details_meeting_activity);

        if(detailsMeetingFragment==null){
            detailsMeetingFragment =  DetailsMeetingFragment.newInstance(meeting);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_details_meeting_activity, detailsMeetingFragment)
                    .commit();
        }
    }
}