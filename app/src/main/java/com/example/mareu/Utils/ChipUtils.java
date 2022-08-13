package com.example.mareu.Utils;

import android.graphics.drawable.Drawable;

import com.example.mareu.R;
import com.google.android.material.chip.ChipGroup;


public class ChipUtils {

    public static com.google.android.material.chip.Chip addChip(String participant, ChipGroup chipgroup, Drawable drawable) {

        final com.google.android.material.chip.Chip chip = new com.google.android.material.chip.Chip(chipgroup.getContext());
        chip.setChipBackgroundColorResource(R.color.colorGrey);

        chip.setText(participant);
        chip.setChipIcon(drawable);
        chip.setCheckable(false);
        chip.setClickable(true);
        chip.setCloseIconVisible(true);
        return chip;
    }


}
