package com.example.mareu.fragments;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.mareu.R;
import com.example.mareu.utils.ChipUtils;
import com.example.mareu.model.Meeting;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsMeetingFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.details_meeting_fragment_spinner) Spinner meetingRoomsSpinner;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.details_meeting_fragment_select) TextView selectRoom;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.details_meeting_fragment_name) TextView meetingName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.details_meeting_fragment_date_textView) TextView dateEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.details_meeting_fragment_start_hour) TextView beginTimeEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.details_meeting_fragment_end_hour) TextView endTimeEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.participant_autoCompleteTextView) AutoCompleteTextView participantsAutoCompleteTextView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chipGroup) ChipGroup participantsChipGroup;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.details_meeting_fragment_email_input) TextInputLayout emailInput;
    Meeting meeting;
    String  dateTime;
    String  hourBegin;
    String  hourEnd;
    String  mParticipants;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    public DetailsMeetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DetailsMeetingFragment.
     */
    public static DetailsMeetingFragment newInstance(Meeting meeting) {
        DetailsMeetingFragment fragment = new DetailsMeetingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, meeting);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            meeting = (Meeting) getArguments().getSerializable(ARG_PARAM1);
        }
        ButterKnife.bind(requireActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        /* Affichage toolbar avec flèche retour
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
 */


      /* click from an item*/

        if (meeting!=null) {

            //call funtion to show selected room
            showSelectedRoom();

            //call function to print details informations fiels
            setDetailsInfos();

            //call funtion to split email
           splitparticipantEmail();
        }


    }

    private void splitparticipantEmail() {
        /* split participants with coma */
        String[] parts = mParticipants.split(getString(R.string.commaParticipants));
        for (String part : parts) {
            @SuppressLint("UseCompatLoadingForDrawables") final com.google.android.material.chip.Chip chip = ChipUtils.addChip(part, participantsChipGroup, getResources().getDrawable(R.drawable.ic_person_pin_black_18dp));

            participantsChipGroup.addView(chip);
            chip.setCheckable(false);
            chip.setClickable(false);
            chip.setCloseIconVisible(false);
            chip.setOnCloseIconClickListener
                    (view -> participantsChipGroup.removeView(chip));
        }
    }

    private void setDetailsInfos() {

        dateTime = meeting.getStartDate().toString("dd/MM/yyyy");
        hourBegin = meeting.getStartDate().toString("HH:mm");
        hourEnd = meeting.getEndTime().toString("HH:mm");
        mParticipants = meeting.getParticipantList();

        String nameReunion = meeting.getTitle();

        meetingName.setText(nameReunion);
        meetingName.setEnabled(false);
        meetingName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        meetingName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        dateEdit.setText(dateTime);
        dateEdit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        beginTimeEdit.setText(hourBegin);
        beginTimeEdit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        endTimeEdit.setText(hourEnd);
        endTimeEdit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        selectRoom.setText(R.string.nameRoom);
        emailInput.setHint("Participant(s) :");
        participantsAutoCompleteTextView.setEnabled(false);

    }



    private void showSelectedRoom() {
        /* Spinner to choose meeting room : */
        final ArrayList<String> meetingRooms = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.meeting_rooms_arrays)));
        meetingRooms.add(0, getString(R.string.choose_room));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, meetingRooms);

        meetingRoomsSpinner.setAdapter(spinnerAdapter);

        int position = spinnerAdapter.getPosition(meeting.getRoom().getName());

        meetingRoomsSpinner.setSelection(position);
        meetingRoomsSpinner.setEnabled(false);

    }

    /* La flèche sur la toolbar a le même comportement que la touche BACK du téléphone */
   /*
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_meeting, container, false);
        ButterKnife.bind(this, view );

        return view;
    }


}