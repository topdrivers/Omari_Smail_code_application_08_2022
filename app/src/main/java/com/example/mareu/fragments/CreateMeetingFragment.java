package com.example.mareu.fragments;

import static com.example.mareu.fragments.ListMeetingFragment.itemViewModel;
import static com.example.mareu.utils.AutocompleteTextViewAdapterUtils.Autocomplete;
import static com.example.mareu.utils.SpinnerAdapterUtils.configureSpinner;
import static com.example.mareu.utils.TimeUtils.beginTimeHandle;
import static com.example.mareu.utils.TimeUtils.dateHandle;
import static com.example.mareu.utils.TimeUtils.endTimeHandle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mareu.activities.ListMeetingActivity;

import com.example.mareu.R;

import com.example.mareu.utils.ToastUtils;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateMeetingFragment extends Fragment {

    public static                                             Spinner    meetingRoomsSpinner;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create_meeting_fragment_select)            TextView   selectRoom;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create_meeting_fragment_name)              TextView   meetingName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create_meeting_fragment_date_textView)     TextView   dateEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create_meeting_fragment_start_hour)        TextView   beginTimeEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create_meeting_fragment_end_hour)          TextView   endTimeEdit;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create_meeting_fragment_start_hour_button) Button     startHourButton;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create_meeting_fragment_date_button)       Button     dateButton;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create_meeting_fragment_end_hour_button)   Button     endHourButton;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.addParticipant_button)                     Button     addParticipantButton;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create_meeeting_fragment_save_button)      Button     buttonSave;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.participant_autoCompleteTextView)AutoCompleteTextView participantsAutoCompleteTextView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chipGroup)                                 ChipGroup  participantsChipGroup;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.create_meeting_fragment_email_input)  TextInputLayout emailInput;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.toolbar)                                   Toolbar    toolbar;



    public CreateMeetingFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(requireActivity());
        /*Affichage toolbar avec flèche retour
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

         */


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onResume() {
        super.onResume();

        configureSpinner(requireContext());

        /* Autocomplete + chips to add the participants : */
        Autocomplete(participantsAutoCompleteTextView, getActivity(), addParticipantButton, participantsChipGroup, getResources().getDrawable(R.drawable.ic_person_pin_black_18dp));


        dateButton.setOnClickListener
                (view -> dateHandle(dateEdit));

        startHourButton.setOnClickListener
                (view -> beginTimeHandle(beginTimeEdit, getContext()));

        endHourButton.setOnClickListener
                (view -> endTimeHandle(endTimeEdit, getContext()));

        buttonSave.setOnClickListener(v -> {

            /* Case field empty or inexacte */
            if (meetingName.getText().toString().equals("")) {
                ToastUtils.showToastLong("Veuillez SVP nommer votre réunion", getContext());
            } else if (dateEdit.getText().toString().equals("")) {
                ToastUtils.showToastLong("Veuillez SVP définir une date", getContext());
            } else if (beginTimeEdit.getText().toString().equals("")) {
                ToastUtils.showToastLong("Veuillez SVP définir l'heure de début", getContext());
            } else if (endTimeEdit.getText().toString().equals("")) {
                ToastUtils.showToastLong("Veuillez SVP définir l'heure de fin", getContext());
            } else if (meetingRoomsSpinner.getSelectedItem().toString().equals("Veuillez choisir une salle")) {
                ToastUtils.showToastLong("Veuillez SVP choisir une salle", getContext());
            } else if (participantsChipGroup.getChildCount() == 0) {
                ToastUtils.showToastLong("Veuillez SVP renseigner les adresses mail des participants", getContext());
            } else {

                checkRoomAvailability();

            }
        });
    }

    private void checkRoomAvailability() {

        //call putComaParticipantsList function
        String participantsList = putComaParticipantsList();

        /* String to DateTime */
        DateTimeFormatter formatterDate = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime mDateEditJoda = formatterDate.parseDateTime(dateEdit.getText().toString());

        DateTimeFormatter formatterHour = DateTimeFormat.forPattern("HH:mm");
        DateTime mBeginTimeEditJoda = formatterHour.parseDateTime(beginTimeEdit.getText().toString());
        DateTime mEndTimeEditJoda = formatterHour.parseDateTime(endTimeEdit.getText().toString());

        DateTime mBeginCompleteJoda = new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mBeginTimeEditJoda.getHourOfDay(), mBeginTimeEditJoda.getMinuteOfHour());
        DateTime mEndCompleteJoda = new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mEndTimeEditJoda.getHourOfDay(), mEndTimeEditJoda.getMinuteOfHour());


        /* Creation new meeting */
        Meeting meeting = new Meeting(itemViewModel.getMeetings().getValue().size(),meetingName.getText().toString(),participantsList, mBeginCompleteJoda,mEndCompleteJoda,new Room(itemViewModel.getMeetings().getValue().size(),meetingRoomsSpinner.getSelectedItem().toString()));

        /* check Room availability */
        boolean timeProblem = false;
        boolean reserved = false;
        for (Meeting m : itemViewModel.getMeetings().getValue()) {
            if (m.getRoom().getName().equals(meetingRoomsSpinner.getSelectedItem().toString()) &&
                    ((mBeginCompleteJoda.isBefore(m.getEndTime()) && mBeginCompleteJoda.isAfter(m.getStartDate()))
                            || (mEndCompleteJoda.isBefore(m.getEndTime()) && mEndCompleteJoda.isAfter(m.getStartDate()))
                            || mBeginCompleteJoda.isEqual(m.getStartDate())
                            || mEndCompleteJoda.isEqual(m.getEndTime())
                            || m.getStartDate().isAfter(mBeginCompleteJoda) && m.getStartDate().isBefore(mEndCompleteJoda)
                            || m.getEndTime().isBefore(mEndCompleteJoda) && m.getEndTime().isAfter(mBeginCompleteJoda))) {
                reserved = true;
                break;
            } else if (mBeginCompleteJoda.isAfter(mEndCompleteJoda) || mBeginCompleteJoda.isEqual(mEndCompleteJoda)) {

                timeProblem = true;
            }
        }
        if (timeProblem) {
            ToastUtils.showToastLong("Veuillez vérifier les heures de début et de fin", getContext());
        } else if (reserved) {
            ToastUtils.showToastLong("Cette salle est déjà réservée", getContext());
        } else {
            itemViewModel.createItem(meeting);
            Intent intent = new Intent(getActivity(), ListMeetingActivity.class);
            startActivity(intent);
        }
    }

    private String putComaParticipantsList() {
        /* Créer la liste des participants dans la liste des réunions sous forme de String séparés par des virgules */
        String mParticipants = "";
        for (int i = 0; i < participantsChipGroup.getChildCount(); i++) {
            com.google.android.material.chip.Chip chip = (com.google.android.material.chip.Chip) participantsChipGroup.getChildAt(i);
            if (i == 0) {
                mParticipants = chip.getText().toString().concat(mParticipants);
            } else {
                mParticipants = chip.getText().toString().concat(", " + mParticipants);
            }
        }
        return mParticipants;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_meeting, container, false);
        meetingRoomsSpinner = view.findViewById(R.id.create_meeting_fragment_spinner);
        ButterKnife.bind(this, view);
        return view;
    }
}