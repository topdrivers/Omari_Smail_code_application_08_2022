package com.example.mareu.injection;

import android.content.Context;


import com.example.mareu.DI.DI;
import com.example.mareu.repository.ItemDataRepository;
import com.example.mareu.service.MeetingApiService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static ItemDataRepository provideItemDataSource(Context context) {
        MeetingApiService meetingApiService = DI.getMeetingApiService();
        return new ItemDataRepository(meetingApiService);
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        ItemDataRepository dataSourceItem = provideItemDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceItem, executor);
    }



    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }


}
