package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Employee;
import com.openclassrooms.mareu.model.Meeting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class generates a predifined list of @{@link Meeting}
 */
public abstract class ListMeetingsGenerator {

    // Predifined lists of Employee for different Meeting
    private static List<Employee> LIST_EMPLOYEE_MEETING_1 = Arrays.asList(
            ListEmployeesGenerator.generateListEmployee().get(0), // Alexandra
            ListEmployeesGenerator.generateListEmployee().get(1), // Amanda
            ListEmployeesGenerator.generateListEmployee().get(23) // Youssef
    );

    private static List<Employee> LIST_EMPLOYEE_MEETING_2 = Arrays.asList(
            ListEmployeesGenerator.generateListEmployee().get(0), // Alexandra
            ListEmployeesGenerator.generateListEmployee().get(2), // Arthur
            ListEmployeesGenerator.generateListEmployee().get(10), // Farah
            ListEmployeesGenerator.generateListEmployee().get(12), // Margaux
            ListEmployeesGenerator.generateListEmployee().get(17), // Omar
            ListEmployeesGenerator.generateListEmployee().get(21) // Vincent
    );

    private static List<Employee> LIST_EMPLOYEE_MEETING_3 = Arrays.asList(
            ListEmployeesGenerator.generateListEmployee().get(1), // Amanda
            ListEmployeesGenerator.generateListEmployee().get(22) // Yassine
    );

    private static List<Employee> LIST_EMPLOYEE_MEETING_4 = Arrays.asList(
                ListEmployeesGenerator.generateListEmployee().get(13), // Marion
                ListEmployeesGenerator.generateListEmployee().get(15), // Ming
                ListEmployeesGenerator.generateListEmployee().get(19), // Polina
                ListEmployeesGenerator.generateListEmployee().get(21) // Vincent
            );

    public static ArrayList<Meeting> LIST_MEETINGS = new ArrayList<Meeting>(
            Arrays.asList(
                    new Meeting("Kick-off meeting","Faraday","08/10/20",
                            "10:00","Lancement du projet", LIST_EMPLOYEE_MEETING_1),
                    new Meeting("Code review", "Maxwell", "10/08/20",
                            "11:00", "Revue mise à jour 1.2", LIST_EMPLOYEE_MEETING_2),
                    new Meeting("Point hebdo", "Planck", "11/08/20",
                            "11:00", "Avancement de la semaine", LIST_EMPLOYEE_MEETING_3),
                    new Meeting("Entretien annuel", "Einstein", "11/08/20",
                            "14:30", "Point individuel", LIST_EMPLOYEE_MEETING_4)
            )
    );

    public static ArrayList<Meeting> generateListMeetings(){
        return LIST_MEETINGS;
    }
}
