package com.openclassrooms.mareu.ui;

import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.fragments.addmeeting.AddMeetingFragment;
import com.openclassrooms.mareu.ui.fragments.listemployees.ListEmployeesFragment;

/**
 * Main activity of the application, contains all fragments instances to display
 */
public class MainActivity extends AppCompatActivity implements MainActivityCallback{

    private Toolbar toolbar;

    // Parameters to handle fragments to display
    private static AddMeetingFragment addMeetingFragment;
    private static ListEmployeesFragment listEmployeesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        // Initialize fragment instances
        addMeetingFragment = AddMeetingFragment.newInstance();
        listEmployeesFragment = ListEmployeesFragment.newInstance();
    }


    /**
     * Initialize main toolbar
     */
    private void setupToolbar() {

        toolbar = findViewById(R.id.toolbar_main);
        // Configure
        toolbar.setTitle(R.string.toolbar_name_list_meeting_activity);
        setSupportActionBar(toolbar);
    }

    /**
     * Overriden to handle click on hardware back button
     */
    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().findFragmentByTag(AddMeetingFragment.TAG) != null) {
            if (getSupportFragmentManager().findFragmentByTag(AddMeetingFragment.TAG).isAdded()) {
                AddMeetingFragment fragment = (AddMeetingFragment) getSupportFragmentManager().findFragmentByTag(AddMeetingFragment.TAG);
                // Remove AddMeetingFragment from stack
                popBack();
                // Reset text inputs
                fragment.clearTextInputsFields();
                // Reset Error enable display
                fragment.resetErrorEnabled();
            }
            else if (getSupportFragmentManager().findFragmentByTag(ListEmployeesFragment.TAG).isAdded()) {
                ListEmployeesFragment fragment = (ListEmployeesFragment) getSupportFragmentManager().findFragmentByTag(ListEmployeesFragment.TAG);
                // Save selected Employee into String
                fragment.saveSelectionToForNewMeeting();
                // Remove ListEmployeesFragment from stack
                popBack();
            }
        }
        else { // ListMeetingsFragment is added
            finish();
        }
    }

    // Getters for static fragment instances
    public static AddMeetingFragment getAddMeetingFragment() {

        return addMeetingFragment;
    }

    public static ListEmployeesFragment getListEmployeesFragment() {

        return listEmployeesFragment;
    }


    /**
     * Update main toolbar title with "title" resource parameter
     * @param title : @StringRes int
     */
    @Override
    public void setToolbarTitle(@StringRes int title) {

        getSupportActionBar().setTitle(title);
    }

    /**
     * Updates main toolbar titl with "title" String parameter
     * @param title : String
     */
    @Override
    public void setToolbarTitle(String title) {

        getSupportActionBar().setTitle(title);
    }

    /**
     * Hides/Shows "Home" toolbar icon according to "status" parameter value :
     *      - Hidden for ListMeetingsFragment
     *      - Displayed for AddMeetingFragment and ListEmployeesFragment
     * @param status : boolean
     */
    @Override
    public void setHomeAsUpIndicator(boolean status) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(status);
            if (status){
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            }
        }
    }

    /**
     * Updates type of icon displayed in main toolbar :
     *      - If AddMeetingFragment displayed : back arrow icon
     *      - If ListEmployeesFragment displayed : back arrow or check icon
     * @param icon : @DrawableRes int
     */
    @Override
    public void updateHomeAsUpIndicator(@DrawableRes int icon) {

        getSupportActionBar().setHomeAsUpIndicator(icon);
    }

    /**
     * Displays the selected fragment in container R.id.fragment_container_view
     * @param fragment : Fragment
     * @param tag : String
     */
    @Override
    public void changeFragment(Fragment fragment, String tag) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Removes current fragment from backstack
     */
    @Override
    public void popBack() {

        getSupportFragmentManager().popBackStack();
    }
}