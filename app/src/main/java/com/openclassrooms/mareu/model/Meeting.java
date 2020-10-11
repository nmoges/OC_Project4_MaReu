package com.openclassrooms.mareu.model;

import java.util.List;

/**
 * Model object representing a Meeting
 */
public class Meeting {

    private String objectMeeting;
    private String meetingRoom;
    private String date;
    private String hour;
    private List<Employee> listParticipants;
    private String information;

    /**
     * Construtor
     * @param objectMeeting : String
     * @param meetingRoom : String
     * @param date : String
     * @param hour : String
     * @param information : String
     * @param participants : List<Employee>
     */
    public Meeting(String objectMeeting, String meetingRoom, String date, String hour, String information, List<Employee> participants){
        this.objectMeeting = objectMeeting;
        this.meetingRoom = meetingRoom;
        this.date = date;
        this.hour = hour;
        this.information = information;
        this.listParticipants = participants;
    }

    public String getObjectMeeting(){
        return this.objectMeeting;
    }

    public String getMeetingRoom(){
        return this.meetingRoom;
    }

    public String getDate(){
        return this.date;
    }

    public String getHour(){
        return this.hour;
    }

    public List<Employee> getListParticipants(){
        return this.listParticipants;
    }

    public String getInformation(){
        return this.information;
    }
}
