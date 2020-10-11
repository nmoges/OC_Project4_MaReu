package com.openclassrooms.mareu.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentResultListener;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.dialogs.DatePickerMeetingDialog;
import com.openclassrooms.mareu.ui.dialogs.MeetingRoomDialog;
import com.openclassrooms.mareu.ui.dialogs.TimePickerMeetingDialog;
import com.openclassrooms.mareu.ui.MainActivity;
import java.util.Objects;

public class AddMeetingFragment extends Fragment {

    private MainActivity parentActivity;
    private Toolbar toolbarFragment;
    private Button cancelButton;
    private Button okButton;

    // Edit fields
    private TextInputEditText objectMeetingInput;
    private TextInputEditText roomMeetingInput;
    private TextInputEditText dateInput;
    private TextInputEditText hourInput;
    private TextInputEditText participantsInputs;
    private TextInputEditText informationInput;

    private String selection = "";

    // Dialogs
    private MeetingRoomDialog meetingRoomDialog;
    private String MEETING_ROOM_DIALOG_TAG = "MEETING_ROOM_DIALOG_TAG";

    public AddMeetingFragment(MainActivity mainActivity) {
        this.parentActivity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume(){
        super.onResume();


        getSelectionFromListEmployeesFragment();

        // Reset textInputs
        //clearTextInputsFields();

        // Listeners for "CANCEL" and "OK" buttons
        handleButtonsListeners();

        // Listeners for all TextInputEditText fields
        onTextInputsEditTextFields();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_meeting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        // Initialize all views
        initializeIds();
        initializeToolbar();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_add_meeting_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        int id = item.getItemId();

        if(id == android.R.id.home){
            // Replace current fragment by existing ListMeetingFragment
            parentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, parentActivity.getListMeetingsFragment()).commit();
            // Reset text Inputs
            clearTextInputsFields();
        }
        else { // id == R.id.reset_item_menu
            // Reset textInputs
            clearTextInputsFields();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeToolbar(){
        parentActivity.setSupportActionBar(toolbarFragment);

        // Add title
        Objects.requireNonNull(parentActivity.getSupportActionBar()).setTitle(parentActivity.getResources().getString(R.string.toolbar_name_frag_add_meeting));

        // Add "Back" button
        parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        parentActivity.getSupportActionBar().setHomeAsUpIndicator(parentActivity.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
    }

    private void initializeIds(){
        // Toolbar
        toolbarFragment = parentActivity.findViewById(R.id.toolbar_fragment_add_meeting);

        // Buttons
        cancelButton = parentActivity.findViewById(R.id.cancel_button);
        okButton = parentActivity.findViewById(R.id.ok_button);

        // TextInputEditText fields
        objectMeetingInput = parentActivity.findViewById(R.id.text_input_object_meeting);
        roomMeetingInput = parentActivity.findViewById(R.id.text_input_room_meeting);
        dateInput = parentActivity.findViewById(R.id.text_input_date);
        hourInput = parentActivity.findViewById(R.id.text_input_hour);
        participantsInputs = parentActivity.findViewById(R.id.text_input_participants);
        informationInput = parentActivity.findViewById(R.id.text_input_information);
    }

    /**
     * Handles click interactions on both bottom fragment buttons :
     *      - Click on "CANCEL" cancels new meeting creation and close fragment
     *      - Click on "OK" add new meeting to the meetings list and close fragment
     */
    private void handleButtonsListeners(){
        // Set listeners
        cancelButton.setOnClickListener((View view) -> {
                parentActivity.getListEmployeesFragment().resetSelectionParameter();
                parentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, parentActivity.getListMeetingsFragment()).commit();
                // Reset text inputs
                clearTextInputsFields();
            }
        );

        okButton.setOnClickListener((View view) -> {
                parentActivity.getListEmployeesFragment().resetSelectionParameter();
                parentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, parentActivity.getListMeetingsFragment()).commit();
                // Reset text inputs
                clearTextInputsFields();
        }
        );
    }

    /**
     * This method resets all textInput fields
     */
    public void clearTextInputsFields(){
        Objects.requireNonNull(objectMeetingInput.getText()).clear();
        Objects.requireNonNull(roomMeetingInput.getText()).clear();
        Objects.requireNonNull(dateInput.getText()).clear();
        Objects.requireNonNull(hourInput.getText()).clear();
        Objects.requireNonNull(participantsInputs.getText()).clear();
        Objects.requireNonNull(informationInput.getText()).clear();
    }

    /**
     * This methods handles clicks in TextInputEditText fields
     */
    private void onTextInputsEditTextFields(){

        roomMeetingInput.setOnClickListener((View view) -> {
                    assert getFragmentManager() != null;

                    InputMethodManager manager =  (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    meetingRoomDialog = new MeetingRoomDialog(roomMeetingInput);
                    meetingRoomDialog.show(getFragmentManager(), MEETING_ROOM_DIALOG_TAG);
                }
        );

        dateInput.setOnClickListener((View view) -> {

                    DatePickerMeetingDialog datePickerMeetingDialog = new DatePickerMeetingDialog(getContext(), dateInput);
                    datePickerMeetingDialog.showDatePickerDialogOnClick();
                }
        );

        hourInput.setOnClickListener((View view) -> {
                    TimePickerMeetingDialog timePickerMeetingDialog = new TimePickerMeetingDialog(getContext(), hourInput);
                    timePickerMeetingDialog.showTimePickerDialogOnClick();
                }
        );

        participantsInputs.setOnClickListener((View view) -> {
                parentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, parentActivity.getListEmployeesFragment())
                        .commit();
            }
        );
    }


    private void getSelectionFromListEmployeesFragment(){
        parentActivity.getSupportFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                selection = result.getString("Selection");
            }
        });
    }
}
