package com.openclassrooms.mareu;

import com.openclassrooms.mareu.ui.fragments.listmeetings.ListMeetingsFragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test file to test filter functionalities
 */
@RunWith(JUnit4.class)
public class FiltersTest {

    private ListMeetingsFragment listMeetingsFragment;

    @Before
    public void setupService() {
        listMeetingsFragment = ListMeetingsFragment.newInstance();
    }

    /**
     * Test to check if the "filter by room" functionality works
     */
    @Test
    public void filterListByRoomWithSuccess(){

        // Initialize variables for test
        listMeetingsFragment.initListMeetingTest();

        // Specify a room filter selection (only "Planck" room selected)
        listMeetingsFragment.testTabSelectionValues();

        // Launch filter
        listMeetingsFragment.validFilterRoom();

        // Check if filtered list contains only meetings with "PLANCK" room
        assertEquals(4, listMeetingsFragment.getListToDisplay().size());
        for(int i = 0; i < listMeetingsFragment.getListToDisplay().size(); i++){
            assertEquals("Planck", listMeetingsFragment.getListToDisplay().get(i).getMeetingRoom());
        }
    }

    /**
     * Test to check if first option of "filter by date" functionality works
     */
    @Test
    public void filterListByDateOption1WithSuccess(){

        // Initialize variables for test
        listMeetingsFragment.initListMeetingTest();

        String filter = "30/11/2020";

        // Valid filter
        listMeetingsFragment.validFilterDateOption1(filter);

        // Check if every item date is equal or higher then filterDate
        assertEquals(2, listMeetingsFragment.getListToDisplay().size());
        for(int i = 0; i < listMeetingsFragment.getListToDisplay().size(); i++){
            assertTrue(listMeetingsFragment.getListToDisplay().get(i).getDate().compareTo(filter) <= 0);
        }

    }

    /**
     * Test to check if second option of "filter by date" functionality works
     */
    @Test
    public void filterListByDateOption2WithSuccess(){

        // Initialize variables for test
        listMeetingsFragment.initListMeetingTest();

        String filterStart = "20/08/2020";
        String filterEnd = "28/10/2020";

        // Valid filter
        listMeetingsFragment.validFilterDateOption2(filterStart, filterEnd);

        // Check if every item date is equal or higher then startDate and equal or lesser than endDate
        assertEquals(5, listMeetingsFragment.getListToDisplay().size());
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = dateFormat.parse(filterStart);
            Date endDate = dateFormat.parse(filterEnd);

            for(int i = 0; i < listMeetingsFragment.getListToDisplay().size(); i++){
                Date currentDate = dateFormat.parse(listMeetingsFragment.getListToDisplay().get(i).getDate());

                assertTrue(currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0);
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
    }
}
