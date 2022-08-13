package com.example.mareu.Utils;



//Associer un nouvel ID à chaque nouvelle réunion créée

import static com.example.mareu.Fragments.ListMeetingFragment.itemViewModel;

import com.example.mareu.service.MeetingApiService;

class IdUtils {

    static int SetId(MeetingApiService meetingApiService) {
        int id = 0;
        for (int i = 0; i < itemViewModel.getMeetings().getValue().size(); i++) {
            if (id < itemViewModel.getMeetings().getValue().get(i).getId())
                id = (int) itemViewModel.getMeetings().getValue().get(i).getId();
        }
        return id + 1;
    }


}
