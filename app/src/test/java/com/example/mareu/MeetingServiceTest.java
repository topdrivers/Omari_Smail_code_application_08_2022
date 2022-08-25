package com.example.mareu;


import static androidx.test.InstrumentationRegistry.getContext;
import static com.example.mareu.fragments.ListMeetingFragment.itemViewModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import android.content.Context;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.example.mareu.Utils.LiveDataTestUtil;
import com.example.mareu.di.DI;
import com.example.mareu.injection.Injection;
import com.example.mareu.injection.ViewModelFactory;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.DummyMeetingGenerator;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.viewModel.ItemViewModel;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(AndroidJUnit4.class)
public class MeetingServiceTest  {
    private Context context;
    private IdlingResource mIdlingResource;


    @Before
    public void setup() {
        this.context= InstrumentationRegistry.getContext().getApplicationContext();

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        itemViewModel = new ViewModelProvider(new ViewModelStoreOwner() {
            @NonNull
            @Override
            public ViewModelStore getViewModelStore() {
                return new ViewModelStore();
            }
        }.getViewModelStore(), mViewModelFactory).get(ItemViewModel.class);
    }


    @Test
    public void getMeetingWithSuccess() throws InterruptedException {
        List<Meeting> meetingList = LiveDataTestUtil.getValue( itemViewModel.getMeetings());
        List<Meeting> expectedNeighbours = DummyMeetingGenerator.DUMMY_METINGS;
        assertThat(meetingList, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));


    }

    @Test
    public void deleteMeetingWithSuccess() throws InterruptedException {
        List<Meeting> meetingList = LiveDataTestUtil.getValue(itemViewModel.getMeetings());
        Meeting meetingToDelete = meetingList.get(3);
        itemViewModel.deleteItem(meetingToDelete);
        waitFilterProcess();
        LiveDataTestUtil.getValue(itemViewModel.getMeetings());
        assertFalse(itemViewModel.getMeetings().getValue().contains(meetingToDelete));
    }

    @Test
    public void filterByDateMeetingWithSuccess() throws InterruptedException {
        DateTime dateTime = new DateTime(2022,8,25,0,0);
        List<Meeting> meetingListFilteredByDate = LiveDataTestUtil.getValue(itemViewModel.getMeetingsByDate(dateTime));
        assertTrue(meetingListFilteredByDate.size()==2);
    }

    @Test
    public void filterByRoomMeetingWithSuccess() {
        MeetingApiService meetingApiService;
        meetingApiService= DI.getMeetingApiService();
        itemViewModel.filterItemRoom("Pegasus",context);
        waitFilterProcess();
        List<Meeting> meetingListFilteredByRoom = meetingApiService.getFilteredList();
        assertTrue(meetingListFilteredByRoom.size()==2);
    }

    private void waitFilterProcess() {
            IdlingRegistry.getInstance().register(mIdlingResource);
    }


    @Test
    public void filterByDateMeetingWithNoResultsMatching() throws InterruptedException {
        DateTime dateTime = new DateTime(2022,8,27,0,0);
        List<Meeting> meetingListFilteredByDate = LiveDataTestUtil.getValue(itemViewModel.getMeetingsByDate(dateTime)) ;
        assertTrue(meetingListFilteredByDate.size()==0);
    }
    @Test
    public void filterByRoomMeetingWithNoResultsMatching() {
        MeetingApiService meetingApiService;
        meetingApiService= DI.getMeetingApiService();
        itemViewModel.filterItemRoom("Centurion",context);
        waitFilterProcess();
        List<Meeting> meetingListFilteredByRoom = meetingApiService.getFilteredList();
        assertTrue(meetingListFilteredByRoom.size()==0);
    }

}
