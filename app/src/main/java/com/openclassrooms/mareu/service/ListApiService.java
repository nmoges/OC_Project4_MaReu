package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Employee;
import com.openclassrooms.mareu.model.Meeting;
import java.util.ArrayList;
import java.util.List;

/**
 * API Service to access to all predefined lists : listEmployees and listMeetings
 */
public class ListApiService {

    private final List<Employee> listEmployees = ListEmployeesGenerator.generateListEmployee();

    private final ArrayList<Meeting> listMeetings = ListMeetingsGenerator.generateListMeetings();

    public List<Employee> getListEmployees(){
        return this.listEmployees;
    }

    public ArrayList<Meeting> getListMeetings(){
        return this.listMeetings;
    }

    public void removeMeeting(Meeting meeting){
        listMeetings.remove(meeting);
    }

    public void addMeeting(Meeting meeting){
        listMeetings.add(meeting);
    }

}
