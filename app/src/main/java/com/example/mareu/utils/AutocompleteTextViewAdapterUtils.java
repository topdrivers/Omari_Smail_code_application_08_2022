package com.example.mareu.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.mareu.R;
import com.google.android.material.chip.ChipGroup;


public class AutocompleteTextViewAdapterUtils {

    //AutocompleteTextView + chips to add the participants :

    public static void Autocomplete(final AutoCompleteTextView textView, final Context context, Button button, final ChipGroup chipgroup, final Drawable drawable) {


        ArrayAdapter<CharSequence> adapterParticipants = ArrayAdapter.createFromResource(context, R.array.participants_arrays, android.R.layout.simple_dropdown_item_1line);
        textView.setAdapter(adapterParticipants);
        button.setOnClickListener(view -> {
            if (textView.getText() != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 1);

                String participant;
                participant = textView.getText().toString().trim();


                /* check user put an email */
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(participant).matches() || participant.isEmpty()) {
                    Toast.makeText(context, R.string.error_message
                            , Toast.LENGTH_SHORT).show();
                } else {

                    final com.google.android.material.chip.Chip chip = ChipUtils.addChip(participant, chipgroup, drawable);
                    chip.setChipBackgroundColorResource(R.color.colorGrey);
                    chipgroup.addView(chip);
                    chip.setOnCloseIconClickListener
                            (view1 -> chipgroup.removeView(chip));
                }
            }
            textView.setText("");
        });
    }
}