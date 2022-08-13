package com.example.mareu.Activities;

import static com.example.mareu.Fragments.ListMeetingFragment.initList;
import static com.example.mareu.Fragments.ListMeetingFragment.itemViewModel;
import static com.example.mareu.Fragments.ListMeetingFragment.meetingApiService;
import static com.example.mareu.Utils.DatePickerUtils.configureDialogCalendar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.example.mareu.DI.DI;
import com.example.mareu.Fragments.ItemViewModel;
import com.example.mareu.Fragments.ListMeetingFragment;
import com.example.mareu.R;
import com.example.mareu.Utils.ToastUtils;
import com.example.mareu.Views.MyMeetingRecyclerViewAdapter;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.tabs.TabLayout;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.List;

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


        //meetingApiService = DI.getNeighbourApiService();

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){

            case R.id.byDate:
                configureDialogCalendar(this);
                return true;

            case R.id.eclair:
                itemViewModel.filterItemRoom("Eclair");
                toolbar.setTitle("Ma réu - Salle Eclair");
                return true;

            case R.id.andromede:
                itemViewModel.filterItemRoom("Andromede");
                toolbar.setTitle("Ma réu - Salle Andromede");
                return true;

            case R.id.paintsilvia:
                itemViewModel.filterItemRoom("Paintsilvia");
                toolbar.setTitle("Ma réu - Salle Paintsilvia");
                return true;

            case R.id.pegasus:
                itemViewModel.filterItemRoom("Pegasus");
                toolbar.setTitle("Ma réu - Salle Pegasus");
                return true;

            case R.id.vulton:
                itemViewModel.filterItemRoom("Vulton");
                toolbar.setTitle("Ma réu - Salle Vulton");
                return true;

            case R.id.quantum:
                itemViewModel.filterItemRoom("Quantum");
                toolbar.setTitle("Ma réu - Salle Quantum");
                return true;

            case R.id.sirius:
                itemViewModel.filterItemRoom("Sirius");
                toolbar.setTitle("Ma réu - Salle Sirius");
                return true;

            case R.id.phebus:
                itemViewModel.filterItemRoom("Phebus");
                toolbar.setTitle("Ma réu - Salle Phebus");
                return true;

            case R.id.betelgeuse:
                itemViewModel.filterItemRoom("Betelgeuse");
                toolbar.setTitle("Ma réu - Salle Betelgeuse");
                return true;

            case R.id.centurion:
                itemViewModel.filterItemRoom("Centurion");
                toolbar.setTitle("Ma réu - Salle Centurion");
                return true;

            case R.id.allMeeting:
                initList(itemViewModel.getMeetings().getValue());
                toolbar.setTitle("Ma réu");
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }


}