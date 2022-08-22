package com.example.mareu;


import static androidx.test.InstrumentationRegistry.getContext;
import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.fragments.ListMeetingFragment;
import com.example.mareu.injection.Injection;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.DummyMeetingGenerator;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.viewModel.ItemViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MeetingServiceTest {
    private ItemViewModel itemViewModel;
    private Context context;
    // FOR DATA

    @Before
    public void setup() {
        this.context= InstrumentationRegistry.getContext().getApplicationContext();
        //context= ApplicationProvider.getApplicationContext();
        //ViewModelStore::new
       // instrumentationCtx = ApplicationProvider.getApplicationContext();

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.itemViewModel = new ViewModelProvider(new ViewModelStoreOwner() {
            @NonNull
            @Override
            public ViewModelStore getViewModelStore() {
                return new ViewModelStore();
            }
        }.getViewModelStore(), mViewModelFactory).get(ItemViewModel.class);
    }


    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetingList = itemViewModel.getMeetings();
        List<Meeting> expectedNeighbours = DummyMeetingGenerator.DUMMY_METINGS;
        assertThat(meetingList, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));


    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = itemViewModel.getMeetings().get(0);
        itemViewModel.deleteItem(meetingToDelete);
        assertTrue(itemViewModel.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void filterByDateMeetingWithSuccess() {
        DateTime dateTime = new DateTime(2022,8,25,0,0);
        List<Meeting> meetingListFilteredByDate = itemViewModel.getMeetingsByDate(dateTime);
        assertTrue(meetingListFilteredByDate.size()==2);
    }

}
