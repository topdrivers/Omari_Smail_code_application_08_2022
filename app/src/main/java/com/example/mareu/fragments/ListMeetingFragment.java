package com.example.mareu.fragments;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.R;
import com.example.mareu.viewModel.ItemViewModel;
import com.example.mareu.views.MyMeetingRecyclerViewAdapter;
import com.example.mareu.injection.Injection;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.model.Meeting;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ListMeetingFragment extends Fragment  {


    public static RecyclerView mRecyclerView;
    public static MyMeetingRecyclerViewAdapter myMeetingRecyclerViewAdapter;
    public static ItemViewModel itemViewModel;
    public static Context context;


    public ListMeetingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureViewModel();

    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.itemViewModel = new ViewModelProvider(this, mViewModelFactory).get(ItemViewModel.class);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Init the List of meeting
     */
    private void initList()   {
        List<Meeting> meetingList = itemViewModel.getMeetings().getValue();
        myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(meetingList);
        mRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);
    }




    private void getMeetings(){
        List<Meeting> meetingList = itemViewModel.getMeetings().getValue();
        myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(meetingList);
        mRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);
    }


    public static void initList(List<Meeting> meetings) {
        myMeetingRecyclerViewAdapter = new MyMeetingRecyclerViewAdapter(meetings);
        mRecyclerView.setAdapter(myMeetingRecyclerViewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        context = getActivity();
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
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        itemViewModel.deleteItem(event.meeting);
        initList();
    }
}