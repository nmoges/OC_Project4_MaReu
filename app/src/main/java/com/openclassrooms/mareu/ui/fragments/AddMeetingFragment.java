package com.openclassrooms.mareu.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentResultListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Employee;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.ListEmployeesGenerator;
import com.openclassrooms.mareu.ui.dialogs.DatePickerMeetingDialog;
import com.openclassrooms.mareu.ui.dialogs.MeetingRoomDialog;
import com.openclassrooms.mareu.ui.dialogs.TimePickerMeetingDialog;
import com.openclassrooms.mareu.ui.MainActivity;
import com.openclassrooms.mareu.utils.TextWatcherTextInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The AddMeetingFragment fragment is used to create a new Meeting, allowing user
 * to edit meeting information by editing TextInputEditText fields.
 */
public class AddMeetingFragment extends Fragment {

    private MainActivity parentActivity;
    private Toolbar toolbarFragment;
    private MaterialButton cancelButton;
    private MaterialButton okButton;

    // Edit fields
    private TextInputEditText objectMeetingInput;
    private TextInputEditText roomMeetingInput;
    private TextInputEditText dateInput;
    private TextInputEditText hourInput;
    private TextInputEditText participantsInput;
    private TextInputEditText informationInput;

    // Edit Layout
    private TextInputLayout objectMeetingLayout;
    private TextInputLayout roomMeetingLayout;
    private TextInputLayout dateLayout;
    private TextInputLayout hourLayout;
    private TextInputLayout participantsLayout; // Updated with number of participants

    // For meeting participants selection
    private String selection = "";
    private int nbMeetingEmployees = 0;
    private List<Employee> meetingEmployees = new ArrayList<>();
    private String TAG_SELECTION = "TAG_SELECTION";

    // Dialogs
    private MeetingRoomDialog meetingRoomDialog;
    private String MEETING_ROOM_DIALOG_TAG = "MEETING_ROOM_DIALOG_TAG";
    private String DATE_DIALOG_TAG = "DATE_DIALOG_TAG";
    private String TIME_DIALOG_TAG = "TIME_DIALOG_TAG";

    public AddMeetingFragment() {
    }

    public static AddMeetingFragment newInstance() {
        return new AddMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getNewSelectionFromListEmployeesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_add_meeting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize all views
        initializeIds();
        initializeToolbar();

        // Listeners for "CANCEL" and "OK" buttons
        handleButtonsListeners();

        // Listeners for all TextInputEditText fields
        onTextInputsEditTextListeners();

        // Listener for "Object Meeting" TextInputLayout
        handleOkBtnClickableStatus();

        // Restore listEmployee
        if(savedInstanceState != null){
            selection = savedInstanceState.getString(TAG_SELECTION);
            if(selection != null){
                createListEmployeesForMeeting();
                nbMeetingEmployees = meetingEmployees.size();
                updateParticipantsTextInput();
            }
            else{
                selection = "";
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_meeting_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Remove AddMeetingFragment from stack
            parentActivity.getSupportFragmentManager().popBackStack();
        }
        else {
            // Update participants input layout hint text
            participantsLayout.setHint(parentActivity.getResources().getString(R.string.edit_input_participants_meeting));
            // Reset selection parameter
            selection = "";
            // Reset nb Employee
            nbMeetingEmployees = 0;
        }
        // Reset CheckBox selection
        MainActivity.getListEmployeesFragment().resetSelectionParameter();

        // Reset text Inputs
        clearTextInputsFields();

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initializeToolbar() {
        parentActivity.setSupportActionBar(toolbarFragment);

        // Add title
        parentActivity.getSupportActionBar().setTitle(parentActivity.getResources().getString(R.string.toolbar_name_frag_add_meeting));

        // Add "Back" button
        parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        parentActivity.getSupportActionBar().setHomeAsUpIndicator(parentActivity.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
    }

    private void initializeIds() {
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
        participantsInput = parentActivity.findViewById(R.id.text_input_participants);
        informationInput = parentActivity.findViewById(R.id.text_input_information);

        // TextInputLayout fields
        objectMeetingLayout = parentActivity.findViewById(R.id.text_layout_object_meeting);
        roomMeetingLayout = parentActivity.findViewById(R.id.text_layout_room_meeting);
        dateLayout = parentActivity.findViewById(R.id.text_layout_date);
        hourLayout = parentActivity.findViewById(R.id.text_layout_hour);
        participantsLayout = parentActivity.findViewById(R.id.text_layout_participants);

        updateDialogs();
    }

    /**
     * Handles click interactions on both bottom fragment buttons :
     * - Click on "CANCEL" cancels new meeting creation and close fragment
     * - Click on "OK" add new meeting to the meetings list and close fragment
     */
    private void handleButtonsListeners() {
        // Set listeners
        cancelButton.setOnClickListener((View view) -> {
                    MainActivity.getListEmployeesFragment().resetSelectionParameter();
                    // Reset text inputs
                    clearTextInputsFields();
                    // Remove AddMeetingFragment from stack
                    parentActivity.getSupportFragmentManager().popBackStack();
                }
        );

        okButton.setOnClickListener((View view) -> {
                    // Create New Meeting
                    Meeting meeting = createNewMeeting();
                    parentActivity.getListApiService().addMeeting(meeting);

                    // Reset text inputs for next Meeting creation
                    clearTextInputsFields();

                    // Reset CheckBox selection if ListEmployeesFragment for next Meeting creation
                    MainActivity.getListEmployeesFragment().resetSelectionParameter();

                    // Remove AddMeetingFragment from stack
                    parentActivity.getSupportFragmentManager().popBackStack();
                }
        );
    }

    /**
     * This method resets all textInput fields
     */
    public void clearTextInputsFields() {
        Objects.requireNonNull(objectMeetingInput.getText()).clear();
        Objects.requireNonNull(roomMeetingInput.getText()).clear();
        Objects.requireNonNull(dateInput.getText()).clear();
        Objects.requireNonNull(hourInput.getText()).clear();
        Objects.requireNonNull(participantsInput.getText()).clear();
        Objects.requireNonNull(informationInput.getText()).clear();
    }

    /**
     * Retrieve instance of dialogs in the fragment stack and update references to input texts
     */
    private void updateDialogs() {
        // Date Fragment
        Fragment dateFragment = getParentFragmentManager().findFragmentByTag(DATE_DIALOG_TAG);
        if (dateFragment instanceof DatePickerMeetingDialog) {
            ((DatePickerMeetingDialog) dateFragment).setTextInput(dateInput);
        }

        // Hour Fragment
        Fragment timeFragment = getParentFragmentManager().findFragmentByTag(TIME_DIALOG_TAG);
        if(timeFragment instanceof TimePickerMeetingDialog){
            ((TimePickerMeetingDialog) timeFragment).setTextInput(hourInput);
        }

        // Room Fragment
        Fragment meetingRoomFragment = getParentFragmentManager().findFragmentByTag(MEETING_ROOM_DIALOG_TAG);
        if(meetingRoomFragment instanceof  MeetingRoomDialog){
            ((MeetingRoomDialog) meetingRoomFragment).setTextInput(roomMeetingInput);
        }
    }

    /**
     * This methods handles clicks in TextInputEditText fields
     */
    private void onTextInputsEditTextListeners() {

        roomMeetingInput.setOnClickListener((View view) -> {
                    InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    meetingRoomDialog = new MeetingRoomDialog(roomMeetingInput);
                    meetingRoomDialog.show(getParentFragmentManager(), MEETING_ROOM_DIALOG_TAG);
                }
        );

        dateInput.setOnClickListener((View view) -> {
                    DatePickerMeetingDialog datePickerMeetingDialog = new DatePickerMeetingDialog(getContext(), dateInput);
                    datePickerMeetingDialog.show(getParentFragmentManager(), DATE_DIALOG_TAG);
                }
        );

        hourInput.setOnClickListener((View view) -> {
                    TimePickerMeetingDialog timePickerMeetingDialog = new TimePickerMeetingDialog(getContext(), hourInput);
                    timePickerMeetingDialog.show(getParentFragmentManager(), TIME_DIALOG_TAG);
                }
        );

        participantsInput.setOnClickListener((View view) -> {
                    // Store selection value in Bundle
                    Bundle previousResults = new Bundle();
                    previousResults.putString("oldSelectionString", selection);
                    parentActivity.getSupportFragmentManager().setFragmentResult("oldSelection", previousResults);
                    parentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, MainActivity.getListEmployeesFragment()).addToBackStack(null).commit();
                }
        );
    }

    /**
     * This method implements @{@link TextWatcherTextInput} objects as TextChangedListener for each TextInputLayout (except "Information" section)
     * If all these TextInpuLayout contains a text specified by user, then the "Ok" button can be enabled
     */
    private void handleOkBtnClickableStatus() {
        TextWatcherTextInput objectMeetingTextWatcher = new TextWatcherTextInput(okButton, roomMeetingInput, dateInput, hourInput, participantsInput);
        TextWatcherTextInput roomMeetingTextWatcher = new TextWatcherTextInput(okButton, objectMeetingInput, dateInput, hourInput, participantsInput);
        TextWatcherTextInput dateTextWatcher = new TextWatcherTextInput(okButton, objectMeetingInput, roomMeetingInput, hourInput, participantsInput);
        TextWatcherTextInput hourTextWatcher = new TextWatcherTextInput(okButton, objectMeetingInput, roomMeetingInput, dateInput, participantsInput);
        TextWatcherTextInput participantsTextWatcher = new TextWatcherTextInput(okButton, objectMeetingInput, roomMeetingInput, dateInput, hourInput);

        Objects.requireNonNull(objectMeetingLayout.getEditText()).addTextChangedListener(objectMeetingTextWatcher);
        Objects.requireNonNull(roomMeetingLayout.getEditText()).addTextChangedListener(roomMeetingTextWatcher);
        Objects.requireNonNull(dateLayout.getEditText()).addTextChangedListener(dateTextWatcher);
        Objects.requireNonNull(hourLayout.getEditText()).addTextChangedListener(hourTextWatcher);
        participantsInput.addTextChangedListener(participantsTextWatcher);
    }

    /**
     * Create a new Meeting object to send to ListMeetingsFragment
     *
     * @return : Meeting
     */
    private Meeting createNewMeeting() {
        String objectMeeting = Objects.requireNonNull(objectMeetingInput.getText()).toString();
        String meetingRoom = Objects.requireNonNull(roomMeetingInput.getText()).toString();
        String date = Objects.requireNonNull(dateInput.getText()).toString();
        String hour = Objects.requireNonNull(hourInput.getText()).toString();
        String information = Objects.requireNonNull(informationInput.getText()).toString();
        return new Meeting(objectMeeting, meetingRoom, date, hour, information, meetingEmployees);
    }

    /**
     * Get selection from ListEmployeesFragment for TextInput participants display update
     */
    private void getNewSelectionFromListEmployeesFragment() {
        parentActivity.getSupportFragmentManager().setFragmentResultListener("newSelection", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Get "selection" from ListMeetingsFragment
                selection = result.getString("newSelectionString");

                createListEmployeesForMeeting();

                // Update text input participants display
                updateParticipantsTextInput();
            }
        });
    }

    public void createListEmployeesForMeeting(){
        // Prepare lists
        List<Employee> listEmployees = ListEmployeesGenerator.generateListEmployee();
        meetingEmployees.clear();

        // Reset nb Employee selected
        nbMeetingEmployees = 0;

        // Initialize create list meeting employees
        for (int i = 0; i < listEmployees.size(); i++) {
            if (Character.toString(selection.charAt(i)).equals("1")) {
                meetingEmployees.add(listEmployees.get(i));
                nbMeetingEmployees++;
            }
        }
    }

    public void updateParticipantsTextInput(){
        String infoToDisplay = "";
        if (meetingEmployees.size() > 0) {
            if (meetingEmployees.size() == 1) {
                infoToDisplay = meetingEmployees.get(0).getEmail();
            } else {
                infoToDisplay = meetingEmployees.get(0).getEmail() + "...";
            }
            participantsLayout.setHint(requireActivity().getResources().getString(R.string.edit_input_participants_meeting) + "(" + nbMeetingEmployees + ")");
        } else {
            participantsLayout.setHint(requireActivity().getResources().getString(R.string.edit_input_participants_meeting));
            participantsInput.getText().clear();
        }
        participantsInput.setText(infoToDisplay);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save "selection" to retrieve list of Employee after configuration change
        if(selection != null){
            if(!selection.equals("")){
                outState.putString(TAG_SELECTION, selection);
            }
        }
    }
}
