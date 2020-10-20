package com.openclassrooms.mareu.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.mareu.R;
import java.util.Arrays;
import java.util.List;

/**
 * This Dialog is displayed every time the user clicks on "Room meeting" TextInput of AddMeetingFragment
 * Used in @{@link com.openclassrooms.mareu.ui.fragments.AddMeetingFragment} fragment
 */

public class MeetingRoomDialog extends DialogFragment {

    // Interface
    private InputTextChangeCallback callback;

    // Current value displayed
    private String currentValue;

    public MeetingRoomDialog(){ /* Empty constructor */ }

    public MeetingRoomDialog(InputTextChangeCallback callback){
        this.callback = callback;
    }

    public void setCallback(InputTextChangeCallback callback){
        this.callback = callback;
    }

    /**
     * This method creates a new MeetingRoomDialog and show it
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        // Retain current DialogFragment instance
        setRetainInstance(true);

        // Create Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Set Builder
        builder.setView(inflater.inflate(R.layout.layout_dialog_meeting_rooms, null))
                .setTitle(R.string.title_dialog_meeting_room);

        return builder.create();
    }

    /**
     * Called when DialogFragment is visible
     */
    @Override
    public void onResume(){
        super.onResume();
        handleMeetingRoomSelection();
    }

    /**
     * Handles click on all "meeting room selection" buttons
     */
    public void handleMeetingRoomSelection(){
        // Initialize list
        List<Button> rooms = Arrays.asList(
                getDialog().findViewById(R.id.button_room_1), // Feynman
                getDialog().findViewById(R.id.button_room_2), // Bohr
                getDialog().findViewById(R.id.button_room_3), // Heisenberg
                getDialog().findViewById(R.id.button_room_4), // Newton
                getDialog().findViewById(R.id.button_room_5), // Dirac
                getDialog().findViewById(R.id.button_room_6), // Faraday
                getDialog().findViewById(R.id.button_room_7), // Planck
                getDialog().findViewById(R.id.button_room_8), // Schrodinger
                getDialog().findViewById(R.id.button_room_9), // Einstein
                getDialog().findViewById(R.id.button_room_10) // Pauli
        );

        for(int i = 0; i < rooms.size(); i++){
            rooms.get(i).setOnClickListener((View view) -> {
                        // Get name Meeting from button
                        String roomName = ((Button) view).getText().toString();
                        callback.onSetMeetingRoom(roomName);
                        // Close Dialog
                        getDialog().cancel();
                    }
            );
        }
    }
}
