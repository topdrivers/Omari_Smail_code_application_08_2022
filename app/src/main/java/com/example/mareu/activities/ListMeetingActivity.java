package com.example.mareu.activities;

import static com.example.mareu.fragments.ListMeetingFragment.initList;
import static com.example.mareu.fragments.ListMeetingFragment.itemViewModel;
import static com.example.mareu.utils.DatePickerUtils.configureDialogCalendar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.mareu.fragments.ListMeetingFragment;
import com.example.mareu.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListMeetingActivity extends AppCompatActivity {


    // UI Components
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ListMeetingFragment listMeetingFragment;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        configureAndShowListmeetingFragment();

    }


    private void configureAndShowListmeetingFragment() {

        listMeetingFragment = (ListMeetingFragment) getSupportFragmentManager().findFragmentById(R.id.frame_list_meeting_activity);

        if(listMeetingFragment==null){
            listMeetingFragment = new ListMeetingFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_list_meeting_activity, listMeetingFragment)
                    .commit();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.list_meeting_activity_add_meeting)
    void addMeeting() {
        Intent intent = new Intent(ListMeetingActivity.this, CreateMeetingActivity.class);

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){

            case R.id.byDate:
                configureDialogCalendar(this);
                return true;

            case R.id.eclair:
                itemViewModel.filterItemRoom("Eclair",getApplicationContext());
                toolbar.setTitle("Ma réu - Salle Eclair");
                return true;

            case R.id.andromede:
                itemViewModel.filterItemRoom("Andromede",getApplicationContext());
                toolbar.setTitle("Ma réu - Salle Andromede");
                return true;

            case R.id.paintsilvia:
                itemViewModel.filterItemRoom("Paintsilvia",getApplicationContext());
                toolbar.setTitle("Ma réu - Salle Paintsilvia");
                return true;

            case R.id.pegasus:
                itemViewModel.filterItemRoom("Pegasus",getApplicationContext());
                toolbar.setTitle("Ma réu - Salle Pegasus");
                return true;

            case R.id.vulton:
                itemViewModel.filterItemRoom("Vulton",getApplicationContext());
                toolbar.setTitle("Ma réu - Salle Vulton");
                return true;

            case R.id.quantum:
                itemViewModel.filterItemRoom("Quantum",getApplicationContext());
                toolbar.setTitle("Ma réu - Salle Quantum");
                return true;

            case R.id.sirius:
                itemViewModel.filterItemRoom("Sirius",getApplicationContext());
                toolbar.setTitle("Ma réu - Salle Sirius");
                return true;

            case R.id.phebus:
                itemViewModel.filterItemRoom("Phebus",getApplicationContext());
                toolbar.setTitle("Ma réu - Salle Phebus");
                return true;

            case R.id.betelgeuse:
                itemViewModel.filterItemRoom("Betelgeuse",getApplicationContext());
                toolbar.setTitle("Ma réu - Salle Betelgeuse");
                return true;

            case R.id.centurion:
                itemViewModel.filterItemRoom("Centurion",getApplicationContext());
                toolbar.setTitle("Ma réu - Salle Centurion");
                return true;

            case R.id.allMeeting:
                initList(itemViewModel.getMeetings());
                toolbar.setTitle("Ma réu");
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }


}