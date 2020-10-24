package com.openclassrooms.mareu;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.testutils.DeleteViewAction;
import com.openclassrooms.mareu.ui.MainActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.openclassrooms.mareu.testutils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

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
     * Test to check if ListMeetingsFragment is correctly displayed aftet launching application
     */
    @Test // STATUS : OK
    public void checkIf_ListMeetingsFragment_isDisplayed() {

        onView(withId(R.id.list_meetings_fragment))
                .check(matches(isDisplayed()));
    }

    /**
     * Test to check if recyclerview displays at least one element
     */
    @Test // STATUS : OK
    public void checkIf_listOfMeetings_isNotBeEmpty() {

        onView(withId(R.id.recycler_view_list_meetings)).check(matches(hasMinimumChildCount(1)));
    }

    @Test // STATUS : OK
    public void checkIf_deleteMeetingIcon_displays_confirmSuppressDialog() {

        // Perform a click on a delete icon
        onView(withId(R.id.recycler_view_list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));

        // Check if Dialog is correctly displayed
        onView(ViewMatchers.withText(R.string.title_dialog_confirm_delete))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test // STATUS : OK
    public void checkIf_deleteAction_removeItemFromMeetingList() {

        onView(withId(R.id.recycler_view_list_meetings))
                .check(withItemCount(NB_MEETINGS));

        // Perform a click on a delete icon to remove associated Meeting
        onView(withId(R.id.recycler_view_list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));

        // Perform a click on positive Dialog button
        onView(withId(android.R.id.button1))
                .inRoot(isDialog())
                .perform(click());

        // Check if size of list of Meetings has changed
        onView(withId(R.id.recycler_view_list_meetings))
                .check(withItemCount(NB_MEETINGS-1));
    }



}
