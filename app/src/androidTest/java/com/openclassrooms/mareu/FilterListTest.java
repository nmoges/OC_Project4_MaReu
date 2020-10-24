package com.openclassrooms.mareu;

import android.widget.DatePicker;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.ui.MainActivity;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class FilterListTest {

    private static final int NB_MEETINGS = DI.getListApiService().getListMeetings().size();
    private MainActivity mainActivity;

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        mainActivity = mMainActivityRule.getActivity();
        assertThat(mainActivity, notNullValue());
    }

    /**
     * Test to check if :
     *      - The FilterDateDialog DialogFragment is launched by clicking on associated menu item
     *      - The FilterRoomDialog DialogFragment is launched by clicking on associated menu item
     */
    @Test // STATUS : OK
    public void checkIf_clickOnItemsMenu_display_associatedDialog() {

        // Open collapsing menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // Click on "Filter by date" icon
        onView(withText(mainActivity.getString(R.string.title_icon_filter_by_date)))
                .perform(click());

        // Close Dialog
        pressBack();

        // Open collapsing menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // Click on "Filter by room" icon
        onView(withText(mainActivity.getString(R.string.title_icon_filter_by_room)))
                 .perform(click());
    }

    /**
     * Test to check if filtering the Meeting list using Filter by date "Option 1"
     * is performed correctly
     */
    @Test // STATUS : OK
    public void checkIf_filter_by_date_option1_isPerformedCorrectly() {

        // Open collapsing menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // Click on "Filter by date" icon
        onView(withText(mainActivity.getString(R.string.title_icon_filter_by_date)))
                .perform(click());

        // Click on TextInputEditText with hint "Date :" (Option 1)
        onView(withId(R.id.input_edit_filter_date_1_date))
                .perform(click());

        // Select date 25/11/2020 on DatePicker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 11, 25));

        // Confirm date (close DatePickerDialog)
        onView(withText("OK"))
                .perform(click());

        // Confirm filter (close FilterDateDialog DialogFragment)
        onView(withText("YES"))
                .perform(click());

        // Reset filter : list meeting no longer filtered
        onView(withId(R.id.reset_filters_item_menu))
                .perform(click());
    }

    /**
     * Test to check if filtering the Meeting list using Filter by date "Option 2"
     * is performed correctly
     */
    @Test // STATUS : OK
    public void checkIf_filter_by_date_option2_isPerformedCorrectly() {

        // Open collapsing menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // Click on "Filter by date" icon
        onView(withText(mainActivity.getString(R.string.title_icon_filter_by_date)))
                .perform(click());

        // Click on TextInputEditText with hint "From :" (Option 2)
        onView(withId(R.id.input_edit_filter_date_2_start_date))
                .perform(click());

        // Select date 25/11/2020 on DatePicker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 10, 19));

        // Confirm start date (close DatePickerDialog)
        onView(withText("OK"))
                .perform(click());

        // Click on TextInputEditText with hint "From :" (Option 2)
        onView(withId(R.id.input_edit_filter_date_2_end_date))
                .perform(click());

        // Select date 25/11/2020 on DatePicker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 11, 30));

        // Confirm start date (close DatePickerDialog)
        onView(withText("OK"))
                .perform(click());

        // Confirm filter (close FilterDateDialog DialogFragment)
        onView(withText("YES"))
                .perform(click());

        // Reset filter : list meeting no longer filtered
        onView(withId(R.id.reset_filters_item_menu))
               .perform(click());
    }

    /**
     * Test to check if filtering the Meeting list using Filter by date "Option 2"
     * is performed correctly
     */
    @Test // STATUS : OK
    public void checkIf_filter_by_room_isPerformedCorrectly() {


        // Open collapsing menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // Click on "Filter by date" icon
        onView(withText(mainActivity.getString(R.string.title_icon_filter_by_room)))
                .perform(click());

        // Click on checkbox to deselect associated filter
        onView(withId(R.id.checkbox_filter_room_1))
                .perform(click());
        onView(withId(R.id.checkbox_filter_room_2))
                .perform(click());
        onView(withId(R.id.checkbox_filter_room_3))
                .perform(click());
        onView(withId(R.id.checkbox_filter_room_4))
                .perform(click());

        // Confirm filter (close FilterDateDialog DialogFragment)
        onView(withText("YES"))
                .perform(click());

        // Reset filter : list meeting no longer filtered
        onView(withId(R.id.reset_filters_item_menu))
                .perform(click());
    }
}
