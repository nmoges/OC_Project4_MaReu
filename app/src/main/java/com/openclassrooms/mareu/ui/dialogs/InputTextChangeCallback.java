package com.openclassrooms.mareu.ui.dialogs;

/**
 * Handles Update TextInputEditText actions in AddMeetingFragment
 */
public interface InputTextChangeCallback {

    void onSetTime(String time, TimeType type);
    void onSetDate(String date);
    void onSetMeetingRoom(String nameMeetingRoom);
}
