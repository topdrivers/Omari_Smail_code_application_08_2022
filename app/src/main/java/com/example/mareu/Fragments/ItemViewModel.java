package com.example.mareu.Fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.example.mareu.model.Meeting;
import com.example.mareu.repository.ItemDataRepository;

import org.joda.time.DateTime;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Philippe on 27/02/2018.
 */

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

    public LiveData<List<Meeting>> getMeetings() {
        return itemDataSource.getMeetings();
    }

    public LiveData<List<Meeting>> getMeetingsByDate(DateTime dateTime) {
        return itemDataSource.getMeetingsByDate(dateTime);
    }

    public void createItem(Meeting meeting) {
        executor.execute(() -> {
            itemDataSource.createItem(meeting);
        });
    }

    public void deleteItem(Meeting meeting) {
        executor.execute(() -> {
            itemDataSource.deleteItem(meeting);
        });
    }

    public void filterItemRoom(String room){
        executor.execute(() -> {
            itemDataSource.filerItemRoom(room);
        });
    }




}
