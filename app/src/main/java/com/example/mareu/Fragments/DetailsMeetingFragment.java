package com.example.mareu.Fragments;


import static com.example.mareu.Utils.AutocompleteTextViewAdapterUtils.Autocomplete;
import static com.example.mareu.Utils.TimeUtils.beginTimeHandle;
import static com.example.mareu.Utils.TimeUtils.dateHandle;
import static com.example.mareu.Utils.TimeUtils.endTimeHandle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
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
import com.example.mareu.Utils.ChipUtils;
import com.example.mareu.Utils.MeetingUtils;
import com.example.mareu.Utils.ToastUtils;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.DummyMeetingGenerator;
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
 * Use the {@link DetailsMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsMeetingFragment extends Fragment {

    private MeetingApiService mApiService ;
    @BindView(R.id.details_meeting_fragment_spinner) Spinner meetingRoomsSpinner;
    @BindView(R.id.details_meeting_fragment_select) TextView selectRoom;
    @BindView(R.id.details_meeting_fragment_name) TextView meetingName;
    @BindView(R.id.details_meeting_fragment_date_textView) TextView dateEdit;
    @BindView(R.id.details_meeting_fragment_start_hour) TextView beginTimeEdit;
    @BindView(R.id.details_meeting_fragment_end_hour) TextView endTimeEdit;
    @BindView(R.id.participant_autoCompleteTextView) AutoCompleteTextView participantsAutoCompleteTextView;
    @BindView(R.id.chipGroup) ChipGroup participantsChipGroup;
    @BindView(R.id.addParticipant_button) Button addParticipantButton;
    @BindView(R.id.details_meeeting_fragment_save_button) Button buttonSave;
    @BindView(R.id.details_meeting_fragment_email_input) TextInputLayout emailInput;
    int id;
    Meeting meeting;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsMeetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment DetailsMeetingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsMeetingFragment newInstance(Meeting meeting) {
        DetailsMeetingFragment fragment = new DetailsMeetingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, meeting);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            meeting = (Meeting) getArguments().getSerializable(ARG_PARAM1);

        }
        System.out.println("--------------getarguments-------------"+id);
        mApiService = DI.getMeetingApiService();
        ButterKnife.bind(getActivity());


    }

    @Override
    public void onResume() {
        super.onResume();

        /* Spinner to choose meeting room : */
        final ArrayList<String> meetingRooms = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.meeting_rooms_arrays)));
        meetingRooms.add(0, getString(R.string.choose_room));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,meetingRooms){
            @Override
            public boolean isEnabled(int position) {

                // Disable the first item from Spinner, first item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);// Set the hint text color gray
                    tv.setTextSize(25);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        /* spinner menu process */
        meetingRoomsSpinner.setAdapter(spinnerAdapter);
        meetingRoomsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0 && view != null) {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /* Affichage toolbar avec flèche retour
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
 */
        /* Autocomplete + chips to add the participants : */
        Autocomplete(participantsAutoCompleteTextView, getActivity(), addParticipantButton, participantsChipGroup, getResources().getDrawable(R.drawable.ic_person_pin_black_18dp));

        //Bundle bundle = getArguments();
/*
        if (bundle != null) {
            Meeting meeting = (Meeting) bundle.getSerializable("id");
        }

 */

        //int id = bundle(getArguments().getInt("id",-1));
        //String id = getArguments().getString("id","-1");
        //int id = bundle.getInt("id",-1);


        /* click à partir d'un item */
        System.out.println("----------------meeting------------"+meeting);
        if (meeting!=null) {



            //meeting = mApiService.getMeetings().get(id);


            int position = spinnerAdapter.getPosition(meeting.getRoom().getName());
            meetingRoomsSpinner.setSelection(position);

            String mParticipants = meeting.getParticipantList();
            String nameReunion = meeting.getTitle();
            String dateTime = meeting.getStartDate().toString("dd/MM/yyyy");
            String hourBegin = meeting.getStartDate().toString("HH:mm");
            String hourEnd = meeting.getEndTime().toString("HH:mm");
            meetingName.setText(nameReunion);

            /* Taille police mMeetingName suivant taille écran */
            if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                meetingName.setTextSize(65);
            } else if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_NORMAL) {
                meetingName.setTextSize(35);
            } else if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_LARGE) {
                meetingName.setTextSize(55);
            }

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
            buttonSave.setVisibility(View.GONE);
            meetingRoomsSpinner.setEnabled(false);
            participantsAutoCompleteTextView.setEnabled(false);
            addParticipantButton.setVisibility(View.GONE);

            /* Récupération des participants en splitant à l'endroit de la virgule */
            String[] parts = mParticipants.split(getString(R.string.commaParticipants));
            for (String part : parts) {
                final com.google.android.material.chip.Chip chip = ChipUtils.addChip(part, participantsChipGroup, getResources().getDrawable(R.drawable.ic_person_pin_black_18dp));

                participantsChipGroup.addView(chip);
                chip.setCheckable(false);
                chip.setClickable(false);
                chip.setCloseIconVisible(false);
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        participantsChipGroup.removeView(chip);
                    }
                });
            }
        }


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