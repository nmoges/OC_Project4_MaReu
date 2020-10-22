package com.openclassrooms.mareu.ui.fragments.listmeetings;

import com.openclassrooms.mareu.model.Meeting;

/**
 * Handles "Delete" action  in Meeting list
 */

public interface ListMeetingActionListener {
    void onDeleteItem(Meeting meeting);
}
