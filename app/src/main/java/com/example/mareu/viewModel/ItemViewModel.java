package com.example.mareu.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.example.mareu.model.Meeting;
import com.example.mareu.repository.ItemDataRepository;

import org.joda.time.DateTime;

import java.util.List;
import java.util.concurrent.Executor;

public class ItemViewModel extends ViewModel {

    // REPOSITORIES
    private final ItemDataRepository itemDataSource;

    private final Executor executor;



    public ItemViewModel(ItemDataRepository itemDataSource, Executor executor) {
        this.itemDataSource = itemDataSource;

        this.executor = executor;
    }

    // -------------
    // FOR ITEM
    // -------------

    public List<Meeting> getMeetings() {
        return itemDataSource.getMeetings();
    }

    public List<Meeting> getMeetingsByDate(DateTime dateTime) {
        return itemDataSource.getMeetingsByDate(dateTime);
    }

    public void createItem(Meeting meeting) {
        executor.execute(() -> itemDataSource.createItem(meeting));
    }

    public void deleteItem(Meeting meeting) {
        executor.execute(() -> itemDataSource.deleteItem(meeting));
    }

    public void filterItemRoom(String room ,Context context){
        executor.execute(() -> itemDataSource.filerItemRoom(room,context));
    }




}
