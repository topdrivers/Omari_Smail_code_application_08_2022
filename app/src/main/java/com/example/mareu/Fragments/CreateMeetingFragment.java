package com.example.mareu.Fragments;

import static com.example.mareu.Fragments.ListMeetingFragment.itemViewModel;
import static com.example.mareu.Utils.AutocompleteTextViewAdapterUtils.Autocomplete;
import static com.example.mareu.Utils.SpinnerAdapterUtils.configureSpinner;
import static com.example.mareu.Utils.TimeUtils.beginTimeHandle;
import static com.example.mareu.Utils.TimeUtils.dateHandle;
import static com.example.mareu.Utils.TimeUtils.endTimeHandle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mareu.Activities.ListMeetingActivity;
import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.Utils.MeetingUtils;
import com.example.mareu.Utils.ToastUtils;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.Room;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateMeetingFragment extends Fragment {
    private MeetingApiService mApiService ;
    public static Spinner meetingRoomsSpinner;
    @BindView(R.id.create_meeting_fragment_select)
    TextView selectRoom;
    @BindView(R.id.create_meeting_fragment_name) TextView meetingName;
    @BindView(R.id.create_meeting_fragment_date_textView) TextView dateEdit;
    @BindView(R.id.create_meeting_fragment_start_hour) TextView beginTimeEdit;
    @BindView(R.id.create_meeting_fragment_end_hour) TextView endTimeEdit;
    @BindView(R.id.create_meeting_fragment_start_hour_button) Button startHourButton;
    @BindView(R.id.create_meeting_fragment_date_button) Button dateButton;
    @BindView(R.id.create_meeting_fragment_end_hour_button) Button endHourButton;

    @BindView(R.id.participant_autoCompleteTextView)
    AutoCompleteTextView participantsAutoCompleteTextView;
    @BindView(R.id.chipGroup)
    ChipGroup participantsChipGroup;
    @BindView(R.id.addParticipant_button)
    Button addParticipantButton;
    @BindView(R.id.create_meeeting_fragment_save_button) Button buttonSave;
    @BindView(R.id.create_meeting_fragment_email_input)
    TextInputLayout emailInput;
    int id;
    Meeting meeting;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateMeetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateMeetingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateMeetingFragment newInstance(String param1, String param2) {
        CreateMeetingFragment fragment = new CreateMeetingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //mApiService = DI.getNeighbourApiService();
        ButterKnife.bind(getActivity());


        /* Affichage toolbar avec flèche retour
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
 */





    }

    @Override
    public void onResume() {
        super.onResume();

        configureSpinner(getContext());

        /* Autocomplete + chips to add the participants : */
        Autocomplete(participantsAutoCompleteTextView, getActivity(), addParticipantButton, participantsChipGroup, getResources().getDrawable(R.drawable.ic_person_pin_black_18dp));

        /* click à partir du FLOAT (création meeting) */

        //meetingName.setHint(R.string.meeting_name);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateHandle(dateEdit);
            }
        });

        startHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginTimeHandle(beginTimeEdit, getContext());
            }
        });
        endHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimeHandle(endTimeEdit, getContext());
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                /* Gestion des cas où l'utilisateur ne remplit pas tous les champs */
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
            }
        });
    }

    private void checkRoomAvailability() {

        String participantsList = putComaParticipantsList();

        /* String vers DateTime */
        DateTimeFormatter formatterDate = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime mDateEditJoda = formatterDate.parseDateTime(dateEdit.getText().toString());

        DateTimeFormatter formatterHour = DateTimeFormat.forPattern("HH:mm");
        DateTime mBeginTimeEditJoda = formatterHour.parseDateTime(beginTimeEdit.getText().toString());
        DateTime mEndTimeEditJoda = formatterHour.parseDateTime(endTimeEdit.getText().toString());

        DateTime mBeginCompleteJoda = new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mBeginTimeEditJoda.getHourOfDay(), mBeginTimeEditJoda.getMinuteOfHour());
        DateTime mEndCompleteJoda = new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mEndTimeEditJoda.getHourOfDay(), mEndTimeEditJoda.getMinuteOfHour());

        System.out.println("-------------size------------------"+itemViewModel.getMeetings().getValue().size());

        /* Création nouveau meeting */
        //Meeting meeting = MeetingUtils.newMeeting(mApiService, meetingName, mDateEditJoda, mBeginTimeEditJoda, mEndTimeEditJoda, participantsList, meetingRoomsSpinner);
        Meeting meeting = new Meeting(itemViewModel.getMeetings().getValue().size(),meetingName.getText().toString(),participantsList, mBeginTimeEditJoda,mEndTimeEditJoda,new Room(itemViewModel.getMeetings().getValue().size(),meetingRoomsSpinner.getSelectedItem().toString()));

        /* Gestion de la disponibilité des salles */
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