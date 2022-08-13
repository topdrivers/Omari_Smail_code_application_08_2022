package com.example.mareu.Fragments;

import static com.example.mareu.Utils.DatePickerUtils.generateDatePickerDialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.mareu.Activities.DetailsMeetingActivity;
import com.example.mareu.DI.DI;
import com.example.mareu.Events.DeleteMeetingEvent;
import com.example.mareu.R;
import com.example.mareu.Utils.ToastUtils;
import com.example.mareu.Views.MyMeetingRecyclerViewAdapter;
import com.example.mareu.injection.Injection;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.model.Meeting;
import com.example.mareu.repository.ItemDataRepository;
import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMeetingFragment extends Fragment  {


    public static RecyclerView mRecyclerView;
    public static MeetingApiService meetingApiService;
    public static MyMeetingRecyclerViewAdapter myMeetingRecyclerViewAdapter;
    public static ItemViewModel itemViewModel;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListMeetingFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListMeetingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMeetingFragment newInstance(String param1, String param2) {
        ListMeetingFragment fragment = new ListMeetingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static ListMeetingFragment newInstance() {
        ListMeetingFragment fragment = new ListMeetingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //mApiService = DI.getNeighbourApiService();
        //itemDataRepository = Injection.provideItemDataSource(getContext());
        configureViewModel();


    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.itemViewModel = new ViewModelProvider(this, mViewModelFactory).get(ItemViewModel.class);
        //this.itemViewModel = new ViewModelFactory(this, mViewModelFactory).get(ItemViewModel.class);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

    /**
     * Init the List of meeting
     */
    private void initList() {
        //List<Meeting> meetingList = mApiService.getMeetings();
        List<Meeting> meetingList = itemViewModel.getMeetings().getValue();




        for ( Meeting meeting : meetingList  ){
            Date currentTime = Calendar.getInstance().getTime();
            float diffenceTime = meeting.getStartDate().toDate().getTime() - currentTime.getTime();
            float differenceInHours = (diffenceTime) / 1000L / 60L / 60L; // Divide by millis/sec, secs/min, mins/hr
            //long differenceInHours = (diffenceTime) / 1000L / 60L / 60L; // Divide by millis/sec, secs/min, mins/hr
            //return (int)differenceInHours;
            //if (meeting.getStartDate() < currentTime)
            /*
            if (currentTime.after(meeting.getStartDate().toDate())){
                meeting.setStatus("BEFORE");
            }else {
                meeting.setStatus("AFTER");
            }

             */
            System.out.println("----------------difference time ---------*"+differenceInHours);
            if(differenceInHours<=1.5 && differenceInHours>=0){
                meeting.setStatus("BETWEEN");
            }else if(differenceInHours > 1.5){
                meeting.setStatus("AFTER");
            }else {
                meeting.setStatus("BEFORE");
            }
        }

        myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(meetingList);
        mRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);
    }




    private void getMeetings(){
        List<Meeting> meetingList = itemViewModel.getMeetings().getValue();
        myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(meetingList);
        mRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);
        this.itemViewModel.getMeetings().observe(this, this::updateItemsList);
    }


    private void deleteItem(Meeting meeting){
        this.itemViewModel.deleteItem(meeting);
    }

    private void updateItemsList(List<Meeting> meetings){
        this.myMeetingRecyclerViewAdapter.updateData(meetings);
    }


    public static void initList(List<Meeting> meetings) {
        myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(meetings);
        mRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);
        //myMeetingRecyclerViewAdapter.notifyDataSetChanged();
    }






    @Override
    public void onResume() {
        super.onResume();
        initList();
        this.getMeetings();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteMeetingEvent event) {
        //mApiService.deleteMeeting(event.meeting);
        itemViewModel.deleteItem(event.meeting);
        initList();

    }
}