package com.openclassrooms.mareu;

import com.openclassrooms.mareu.utils.DateAndTimeConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertEquals;

/**
 * Unit test file to test Date and Time conversion methods
 */
@RunWith(JUnit4.class)
public class DateTimeFormatTest {

    /**
     * Test several time values with DateAndTimeConverter class
     */
    @Test
    public void testTimeConverter(){
        assertEquals("00:00", DateAndTimeConverter.timeConverter(0,0));
        assertEquals("09:30", DateAndTimeConverter.timeConverter(9,30));
        assertEquals("12:01", DateAndTimeConverter.timeConverter(12,1));
        assertEquals("23:59", DateAndTimeConverter.timeConverter(23,59));
    }

    /**
     * Test several date values with DateAndTimeConverter class
     */
    @Test
    public void testDateConverter(){
        assertEquals("01/01/2020", DateAndTimeConverter.dateConverter(2020,0,1));
        assertEquals("10/02/2022", DateAndTimeConverter.dateConverter(2022, 1,10));
        assertEquals("12/12/2023", DateAndTimeConverter.dateConverter(2023, 11, 12));
    }

}
