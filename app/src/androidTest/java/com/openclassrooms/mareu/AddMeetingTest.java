package com.openclassrooms.mareu;

import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.testutils.CheckBoxClickAction;
import com.openclassrooms.mareu.ui.MainActivity;
import com.openclassrooms.mareu.ui.fragments.addmeeting.AddMeetingFragment;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.mareu.testutils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Instrumented test file to test functionalities of AddMeetingFragment
 */
@RunWith(AndroidJUnit4.class)
public class AddMeetingTest {

    private int ITEM_MEETING_COUNT;

    @Rule
    public final ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        ITEM_MEETING_COUNT = DI.getListApiService().getListMeetings().size();

        MainActivity mainActivity = mMainActivityRule.getActivity();
        assertThat(mainActivity, notNullValue());
        mainActivity.changeFragment(MainActivity.getAddMeetingFragment(), AddMeetingFragment.TAG);
    }


    /**
     * Test to check all Dialog are correctly displayed when clicking in associated TextInputEditText fields
     */
    @Test
    public void checkIf_all_Dialog_areCorrectlyDisplayed(){

        // Click on "Room Meeting selection" TextInputEditText
        onView(withId(R.id.text_input_room_meeting))
                .perform(click());

        Espresso.pressBack();

        // Click on "Date selection" TextInputEditText
        onView(withId(R.id.text_input_date))
                .perform(click());

        Espresso.pressBack();

        // Click on "Start hour selection" TextInputEditText
        onView(withId(R.id.text_input_hour_start))
                .perform(click());

        Espresso.pressBack();

        // Click on "End hour selection" TextInputEditText
        onView(withId(R.id.text_input_hour_end))
                .perform(click());

        Espresso.pressBack();
    }

    /**
     * Test to check if selecting a Room meeting in "Room selection" Dialog updates
     * the associated TextInputEditText field in AddMeetingFragment
     */
    @Test
    public void checkIf_RoomSelection_updateRoomTextInputEditText(){

        // Click on "Room Meeting selection" TextInputEditText
        onView(withId(R.id.text_input_room_meeting))
                .perform(click());

        // Click on a specific "Room" button
        onView(withText(R.string.room_1))
                .perform(click());

        Espresso.pressBack();
    }

    /**
     * Test to check if selecting a Date meeting in "Date selection" Dialog updates
     * the associated TextInputEditText field in AddMeetingFragment
     */
    @Test
    public void checkIf_DateSelection_updateDateTextInputEditText(){

        // Click on "Date selection" TextInputEditText
        onView(withId(R.id.text_input_date))
                .perform(click());

        // Select date 25/11/2020 on DatePicker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 11, 30));

        // Confirm start date (close DatePickerDialog)
        onView(withText("OK"))
                .perform(click());
    }

    /**
     * Test to check if selecting a start hour meeting/end hour meeting in "Start/end hour selection" Dialogs updates
     * the associated TextInputEditText fields in AddMeetingFragment
     */
    @Test
    public void checkIf_HourSelection_updateHourTextInputEditText(){
        // Click on "Start hour selection" TextInputEditText
        onView(withId(R.id.text_input_hour_start))
                .perform(click());

        // Select start hour (H24 format) : 10h30
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(10,30));

        // Confirm selection
        onView(withText("OK"))
                .perform(click());

        // Click on "End hour selection" TextInputEditText
        onView(withId(R.id.text_input_hour_end))
                .perform(click());

        // Select end hour (H24 format) : 11h30
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(11,30));

        // Confirm selection
        onView(withText("OK"))
                .perform(click());
    }

    /**
     * Test to check if FragmentTransaction performed by clicking in Participants TextInputEditText
     * successfully display ListEmployeesFragment to user
     */
    @Test
    public void checkIf_clickOnParticipantsTextInputEditText_displayListEmployeesFragment(){

        // Perform click on "Participants" TextInputEditText
        onView(withId(R.id.text_input_participants))
                .perform(click());

        // Check if ListEmployeesFragment is displayed
        onView(withId(R.id.list_employees_fragment))
                .check(matches(isDisplayed()));
    }

    /**
     * Test to check "OK" and "CANCEL" buttons status before user start interacting with AddMeetingFragment
     */
    @Test
    public void check_ButtonsStatus_whenFragmentIsShown(){

        // Check if Cancel button is enabled (always)
        onView(withId(R.id.cancel_button)).check(matches(isEnabled()));

        // Check if Ok button is disabled (when all necessary text fields are not edited yet)
        onView(withId(R.id.ok_button)).check(matches(not(isEnabled())));
    }

    /**
     * Test to check if participants selection in ListEmployeesFragment works and that
     * TextInputEditText field in AddMeetingFragment is correctly updated
     */
    @Test
    public void checkIf_ParticipantsSelection_updated_TextInputEditText_inAddMeetingFragment(){
        // Perform click on "Participants" TextInputEditText
        onView(withId(R.id.text_input_participants))
                .perform(click());

        // Check if ListEmployeesFragment is displayed
        onView(withId(R.id.list_employees_fragment))
                .check(matches(isDisplayed()));

        // Select fist five participants
        for(int i = 0; i < 5; i++){
            onView(withId(R.id.recycler_view_list_employees))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, CheckBoxClickAction.clickCheckBoxWithId(R.id.checkbox_item_recycler_view)));
        }

        // Click on back button
        onView(withContentDescription(R.string.abc_action_bar_up_description))
                .perform(click());
    }

    /**
     * Check if a new Meeting object is correctly created in AddMeetingFragment and ListEmployeesFragment,
     * and added to the list displayed in ListMeetingsFragment
     */
    @Test
    public void checkIf_MeetingCreation_isCorrectlyDone() {

        // Specify an "Object meeting information"
        onView(withId(R.id.text_input_object_meeting))
                .perform(typeText("Code review meeting"));

        closeSoftKeyboard();

        // Click on "Room Meeting selection" TextInputEditText
        onView(withId(R.id.text_input_room_meeting))
                .perform(click());

        // Click on a specific "Room" button
        onView(withText(R.string.room_1))
                .perform(click());

        // Click on "Date selection" TextInputEditText
        onView(withId(R.id.text_input_date))
                .perform(click());

        // Select date 25/11/2020 on DatePicker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 11, 30));

        // Confirm start date (close DatePickerDialog)
        onView(withText("OK"))
                .perform(click());

        // Click on "Start hour selection" TextInputEditText
        onView(withId(R.id.text_input_hour_start))
                .perform(click());

        // Select start hour (H24 format) : 10h30
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(10,30));

        // Confirm selection
        onView(withText("OK"))
                .perform(click());

        // Click on "End hour selection" TextInputEditText
        onView(withId(R.id.text_input_hour_end))
                .perform(click());

        // Select end hour (H24 format) : 11h30
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(11,30));

        // Confirm selection
        onView(withText("OK"))
                .perform(click());

        // Perform click on "Participants" TextInputEditText
        onView(withId(R.id.text_input_participants))
                .perform(click());

        // Select fist five participants
        for(int i = 0; i < 5; i++){
            onView(withId(R.id.recycler_view_list_employees))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, CheckBoxClickAction.clickCheckBoxWithId(R.id.checkbox_item_recycler_view)));
        }

        // Click on back button
        onView(withContentDescription(R.string.abc_action_bar_up_description))
                .perform(click());

        // Specify an "Object meeting information"
        onView(withId(R.id.text_input_information))
                .perform(typeText("Information"));

        closeSoftKeyboard();

        // Confirm Meeting creation
        onView(withId(R.id.ok_button))
                .perform(click());

        // Check if size of the Meeting list has changed
        onView(withId(R.id.recycler_view_list_meetings))
                .check(withItemCount(ITEM_MEETING_COUNT+1));
    }
}
