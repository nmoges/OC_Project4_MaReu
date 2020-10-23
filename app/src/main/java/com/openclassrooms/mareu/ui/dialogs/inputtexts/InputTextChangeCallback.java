package com.openclassrooms.mareu.ui.dialogs.inputtexts;

/**
 * Handles Update TextInputEditText actions in @{@link com.openclassrooms.mareu.ui.fragments.addmeeting.AddMeetingFragment}
 */
public interface InputTextChangeCallback {

    // Updates TextInput fields from AddMeetingFragment
    void onSetTime(String time, TimeType type);
    void onSetDate(String date);
    void onSetMeetingRoom(String nameMeetingRoom);
    // Updates "Ok" button status according to TextInputEditText fields & TextInputLayoutText layout (for End hour only) changes
    void onCheckInputsTextStatus();
}
