package com.example.mareu.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;

import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class DummyMeetingGenerator {

    private static final DateTime startTime1 = new DateTime(2022,8,25,16, 0);
    private static final DateTime endTime1 = new DateTime(2022,8,25,17,0);
    private static final DateTime startTime2 = new DateTime(2022,8,15,16,0);
    private static final DateTime endTime2 = new DateTime(2022,8,15,18,0);
    private static final DateTime startTime3 = new DateTime(2022,7,5,9,0);
    private static final DateTime endTime3 = new DateTime(2022,7,5,10,0);
    private static final DateTime startTime4 = new DateTime(2022,8,25,15,45);
    private static final DateTime endTime4 = new DateTime(2022,8,25,16,45);
    private static final DateTime startTime5 = new DateTime(2022,9,30,23,30);
    private static final DateTime endTime5 = new DateTime(2022,9,30,23,45);
    private static final DateTime startTime6 = new DateTime(2022,8,10,23,45);
    private static final DateTime endTime6 = new DateTime(2022,8,10,23,55);

    public static List<Meeting> DUMMY_METINGS = new ArrayList<>();

    public static List<Meeting> FILTERED_MEETINGS = new ArrayList<>();


    static LiveData<List<Meeting>> generateMeeting() {

        DUMMY_METINGS.add(new     Meeting(0, "Réunion 1",
                "user1@user.fr, user2@user.fr, user3@user.fr", startTime1,
                endTime1, new Room(0, "Pegasus")));

        DUMMY_METINGS.add(new     Meeting(1, "Réunion 2",
                "user4@user.fr, user5@user.fr", startTime2,
                endTime2, new Room(1, "Paintsilvia")));

        DUMMY_METINGS.add(new     Meeting(2, "Réunion 3",
                "user6@user.fr", startTime3,
                endTime3, new Room(2, "Vulton")));

        DUMMY_METINGS.add(new     Meeting(3, "Réunion 4",
                "user11@user.fr, user21@user.fr, user3@user.fr", startTime4,
                endTime4, new Room(3, "Pegasus")));

        DUMMY_METINGS.add(new     Meeting(4, "Réunion 5",
                "user14@user.fr, user15@user.fr", startTime5,
                endTime5, new Room(4, "Paintsilvia")));

        DUMMY_METINGS.add(new     Meeting(5, "Réunion 6",
                "user16@user.fr", startTime6,
                endTime6, new Room(5, "Vulton")));

        return new LiveData<List<Meeting>>(DUMMY_METINGS) {
            @Override
            public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Meeting>> observer) {
                super.observe(owner, observer);
            }
        };
    }

    static LiveData<List<Meeting>> generateMeetingFilteredMeeting() {

        return new LiveData<List<Meeting>>(FILTERED_MEETINGS) {
            @Override
            public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Meeting>> observer) {
                super.observe(owner, observer);
            }
        };
    }


}
