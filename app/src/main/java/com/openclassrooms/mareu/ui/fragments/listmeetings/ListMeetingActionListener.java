package com.openclassrooms.mareu.ui.fragments.listmeetings;

import com.openclassrooms.mareu.model.Meeting;

/**
 * Handles "Delete" action  in Meeting list
 * Implemented in @{@link ListMeetingsFragment}
 */

public interface ListMeetingActionListener {
    void onDeleteItem(Meeting meeting);
}
