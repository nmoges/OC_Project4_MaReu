package com.openclassrooms.mareu.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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

    // Tab containing all CheckBox status (filters selected)
    private boolean[] tabRoomFiltersSelected;
    // Backup tab used to restore previous value (Dialog closed with click on "NO" button : no modifications)
    private boolean[] initialFiltersSelection;
    // List of Dialog CheckBox
    private List<CheckBox> listCheckBox;
    // Tag parent fragment
    private String TAG_LIST_MEETINGS_FRAGMENT = "TAG_LIST_MEETINGS_FRAGMENT";
    public FilterRoomDialog(boolean[] tabRoomFiltersSelected){
        this.tabRoomFiltersSelected = tabRoomFiltersSelected;
    }

    public void setListFiltersRoom(boolean[] tabRoomFiltersSelected){
        this.tabRoomFiltersSelected = tabRoomFiltersSelected;
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
                // Update list display
                ListMeetingsFragment fragment = (ListMeetingsFragment) getParentFragmentManager().findFragmentByTag(TAG_LIST_MEETINGS_FRAGMENT);
                fragment.filterListByRoomSelection();
            }
        ).setNegativeButton(R.string.btn_no_confirm_filter_by_room, (DialogInterface dialogInterface, int i) -> {
                // Restore values and close dialog
                for(int j =0 ; j < tabRoomFiltersSelected.length; j++){
                    tabRoomFiltersSelected[j] = initialFiltersSelection[j];
                }
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

        // Restore previous values
        for(int i =0; i < listCheckBox.size(); i++){
            listCheckBox.get(i).setChecked(tabRoomFiltersSelected[i]);
        }

        // Prepare backup value
        initialFiltersSelection = new boolean[tabRoomFiltersSelected.length];
        for(int i = 0; i < tabRoomFiltersSelected.length; i++){
            initialFiltersSelection[i] = tabRoomFiltersSelected[i];
        }
    }

    /**
     * This method handles all CheckBox listeners to update list
     */
    public void handleFiltersRoomSelection(){

        for(int i = 0; i < listCheckBox.size(); i++){
            int indice = i;
            listCheckBox.get(i).setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) -> {
                    // Change status filter
                    tabRoomFiltersSelected[indice] = isChecked;
                }
            );
        }
    }

}
