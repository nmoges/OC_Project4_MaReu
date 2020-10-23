package com.openclassrooms.mareu.ui.dialogs.filter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.MainActivity;
import com.openclassrooms.mareu.ui.fragments.listmeetings.ListMeetingsFragment;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FilterRoomDialog extends DialogFragment {

    // List of Dialog CheckBox
    private List<CheckBox> listCheckBox;

    private boolean[] tabRoomFiltersSelected = new boolean[10];



    private FilterActionListener listener;

    public FilterRoomDialog(FilterActionListener listener, boolean[] tabRoomFiltersSelected){
        this.listener = listener;
        for(int i = 0; i < tabRoomFiltersSelected.length; i++){
            this.tabRoomFiltersSelected[i] = tabRoomFiltersSelected[i];
        }
    }

    public void setTabRoomFiltersSelected(boolean[] tabRoomFiltersSelected){
        for(int i = 0; i < tabRoomFiltersSelected.length; i++){
            this.tabRoomFiltersSelected[i] = tabRoomFiltersSelected[i];
        }
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

        // Set Builder

        builder.setView(inflater.inflate(R.layout.layout_dialog_filter_meeting_rooms, null));
        builder.setTitle(R.string.title_dialog_filter_by_room);
        builder.setPositiveButton(R.string.btn_yes_confirm_filter_by_room, (DialogInterface dialogInterface, int i) -> {
                   // Apply selection to filter
                   listener.validFilterRoom();

                }
        ).setNegativeButton(R.string.btn_no_confirm_filter_by_room, (DialogInterface dialogInterface, int i) -> {
                    // No new selection, restore previous one
                    listener.restorePreviousSelection();
                }
        );
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeListCheckBox();
        handleFiltersRoomSelection();
    }

    /**
     * This method initialize the list of CheckBox, and restore "checked" values using tabRoomFiltersSelected sent from
     * ListMeetingsFragment
     */
    public void initializeListCheckBox(){
        listCheckBox = Arrays.asList(
                Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_room_1),
                Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_room_2),
                Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_room_3),
                Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_room_4),
                Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_room_5),
                Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_room_6),
                Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_room_7),
                Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_room_8),
                Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_room_9),
                Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_room_10)
        );

        // Initialize CheckBox
        for(int i = 0; i < listCheckBox.size(); i++){
            listCheckBox.get(i).setChecked(tabRoomFiltersSelected[i]);
        }
    }

    /**
     * This method handles all CheckBox listeners to update list
     */
    public void handleFiltersRoomSelection(){

        for(int i = 0; i < listCheckBox.size(); i++){
            int indice = i;
            listCheckBox.get(i).setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) -> {
                        // Update tabRoomFiltersSelected from ListMeetingFragment
                        listener.actionChangeFilterRoom(indice);
                    }
            );
        }
    }

}