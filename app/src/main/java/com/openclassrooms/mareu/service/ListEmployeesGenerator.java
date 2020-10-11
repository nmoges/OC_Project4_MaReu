package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Employee;
import java.util.Arrays;
import java.util.List;

/**
 * This class generates a predefined list of Employee
 */
public abstract class ListEmployeesGenerator {

    public static List<Employee> LIST_EMPLOYEES = Arrays.asList(
            new Employee("Alexandra", "alexandra@lamzone.com", 1),
            new Employee("Amanda", "amanda@lamzone.com", 1),
            new Employee("Arthur", "arthur@lamzone.com", 2),
            new Employee("Baptiste", "baptiste@lamzone.com", 3),
            new Employee("Caroline", "caroline@lamzone.com", 4),
            new Employee("Corentin", "corentin@lamzone.com", 5),
            new Employee("Delphine", "delphine@lamzone.com", 6),
            new Employee("Emilie", "emilie@lamzone.com", 7),
            new Employee("Francis", "francis@lamzone.com", 8),
            new Employee("Fanny", "fanny@lamzone.com", 9),
            new Employee("Farah", "farah@lamzone.com", 10),
            new Employee("Jérémy", "jeremy@lamzone.com", 11),
            new Employee("Margaux", "margaux@lamzone.com", 12),
            new Employee("Marion", "marion@lamzone.com", 13),
            new Employee("Medhi", "medhi@lamzone.com", 14),
            new Employee("Ming", "ming@lamzone.com", 15),
            new Employee("Nicolas", "nicolas@lamzone.com", 16),
            new Employee("Omar", "omar@lamzone.com", 17),
            new Employee("Patricia", "patricia@lamzone.com", 18),
            new Employee("Polina", "polina@lamzone.com", 19),
            new Employee("Véronica", "veronica@lamzone.com", 20),
            new Employee("Vincent", "vincent@lamzone.com", 21),
            new Employee("Yassine", "yassine@lamzone.com", 22),
            new Employee("Youssef", "youssef@lamzone.com", 23),
            new Employee("Zven", "zven@lamzone.com", 24)
    );

    public static List<Employee> generateListEmployee(){
        return LIST_EMPLOYEES;
    }
}
