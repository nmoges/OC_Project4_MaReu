package com.openclassrooms.mareu;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.openclassrooms.mareu.ui.MainActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class AddMeetingTest {

    private MainActivity mainActivity;

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        mainActivity = mMainActivityRule.getActivity();
        assertThat(mainActivity, notNullValue());
    }

    @Test
    public void test(){
        // Click on fab button
        onView(withId(R.id.fab_list_meetings_fragment))
                .perform(click());
    }

}
