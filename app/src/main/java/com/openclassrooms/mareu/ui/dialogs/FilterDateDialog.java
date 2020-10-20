package com.openclassrooms.mareu.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.openclassrooms.mareu.R;

public class FilterDateDialog extends DialogFragment {

    public FilterDateDialog(){

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Retain current DialogFragment instance
        setRetainInstance(true);

        // Create Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Set builder
        builder.setView(inflater.inflate(R.layout.layout_dialog_filter_meeting_date, null));
        builder.setTitle("Filter by date");
        builder.setPositiveButton("Yes", (DialogInterface dialog, int which) -> {

                }
        ).setNegativeButton("No", (DialogInterface dialog, int which) -> {

                }
        );

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
