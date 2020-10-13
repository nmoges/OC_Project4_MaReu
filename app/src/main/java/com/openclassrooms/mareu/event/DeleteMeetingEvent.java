package com.openclassrooms.mareu.event;

import com.openclassrooms.mareu.model.Meeting;

/**
 * Event fired when a Meeting is deleted by user
 */
public class DeleteMeetingEvent {

    // Meeting to delete
    public Meeting meeting;

    /**
     * Constructor
     * @param meeting : Meeting
     */
    public DeleteMeetingEvent(Meeting meeting){
        this.meeting = meeting;
    }
}
