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

    private static List<Employee> LIST_EMPLOYEE_MEETING_5 = Arrays.asList(
            ListEmployeesGenerator.generateListEmployee().get(10),
            ListEmployeesGenerator.generateListEmployee().get(12),
            ListEmployeesGenerator.generateListEmployee().get(9)

    );

    private static List<Employee> LIST_EMPLOYEE_MEETING_6 = Arrays.asList(
            ListEmployeesGenerator.generateListEmployee().get(4),
            ListEmployeesGenerator.generateListEmployee().get(5)
    );

    public static ArrayList<Meeting> LIST_MEETINGS = new ArrayList<Meeting>(
            Arrays.asList(
                    new Meeting("Kick-off meeting","Faraday","08/10/2019",
                            "10:00", "11:00", "Lancement du projet", LIST_EMPLOYEE_MEETING_1),
                    new Meeting("Code review", "Maxwell", "10/08/2020",
                            "11:00", "12:00","Revue mise à jour 1.2", LIST_EMPLOYEE_MEETING_2),
                    new Meeting("Point hebdo", "Planck", "12/08/2020",
                            "11:00", "12:00", "Avancement de la semaine", LIST_EMPLOYEE_MEETING_3),
                    new Meeting("Entretien annuel", "Einstein", "18/10/2020",
                            "20:00", "20:59", "Point individuel", LIST_EMPLOYEE_MEETING_4),
                    new Meeting("Point hebdo", "Einstein", "18/10/2020",
                            "20:00", "21:00", "Point d'avancement", LIST_EMPLOYEE_MEETING_5),
                    new Meeting("Point hebdo", "Feynman", "18/10/2020",
                            "21:00", "21:30", "Point d'avancement", LIST_EMPLOYEE_MEETING_6),
                    new Meeting("Point hebdo", "Planck", "20/10/2020",
                            "11:00", "12:00", "Avancement de la semaine", LIST_EMPLOYEE_MEETING_3),
                    new Meeting("Revue spec", "Planck", "21/11/2020",
                            "9:00", "10:00", "", LIST_EMPLOYEE_MEETING_4),
                    new Meeting("Validation essais", "Planck", "20/08/2022",
                            "10:00", "11:00", "", LIST_EMPLOYEE_MEETING_1)
            )
    );

    public static ArrayList<Meeting> generateListMeetings(){
        return LIST_MEETINGS;
    }
}
