package com.openclassrooms.mareu;

import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Employee;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.ListApiService;
import com.openclassrooms.mareu.service.ListEmployeesGenerator;
import com.openclassrooms.mareu.service.ListMeetingsGenerator;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test file to test ListApiService class
 */
@RunWith(JUnit4.class)
public class ListApiServiceTest {

    private ListApiService service;

    @Before
    public void setupService() {
        service = DI.getListApiService();
    }

    /**
     * Test to check if list of Meeting is correctly generated
     */
    @Test
    public void getListMeetingWithSuccess() {
        List<Meeting> listMeetings = service.getListMeetings();
        List<Meeting> expectedListMeetings = ListMeetingsGenerator.LIST_MEETINGS;
        assertThat(listMeetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedListMeetings.toArray()));
    }

    /**
     * Test to check it list of Employee is correctly generated
     */
    @Test
    public void getListEmployeeWithSuccess(){
        List<Employee> listEmployees = service.getListEmployees();
        List<Employee> expectedListEmployees = ListEmployeesGenerator.LIST_EMPLOYEES;
        assertThat(listEmployees, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedListEmployees.toArray()));

    }

    /**
     * Test to check if a new Meeting object is correctly added to the list
     */
    @Test
    public void addNewMeetingWithSuccess() {

        // Create list Employee
        List<Employee> listEmployees = Arrays.asList(new Employee ("Baptiste", "baptiste@lamzone.com", 4),
                                                     new Employee ("Fanny", "fanny@lamzone.com", 10),
                                                     new Employee ("Vincent", "vincent@lamzone.com", 22));

        // Create list Meeting
        Meeting newMeeting = new Meeting("Réunion d'avancement",
                                         "Planck",
                                                "12/11/20",
                                             "15:30",
                                              "16:00",
                                           "Revues des dernières actions",
                                                      listEmployees);

        // Add Meeting
        service.addMeeting(newMeeting);
        assertTrue(service.getListMeetings().contains(newMeeting));
    }

    /**
     * Test to check if a selected Meeting is correctly removed from list
     */
     @Test
    public void removeMeetingWithSuccess(){
         // Get first Meeting from list
         Meeting meetingToRemove = service.getListMeetings().get(0);
         service.getListMeetings().remove(meetingToRemove);
         assertFalse(service.getListMeetings().contains(meetingToRemove));
     }

}
