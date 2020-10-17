package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.service.ListApiService;
import com.openclassrooms.mareu.ui.fragments.AddMeetingFragment;
import com.openclassrooms.mareu.ui.fragments.InfoMeetingFragment;
import com.openclassrooms.mareu.ui.fragments.listemployees.ListEmployeesFragment;
import com.openclassrooms.mareu.ui.fragments.listmeetings.ListMeetingsFragment;

/**
 * Main activity of the application, contains all fragments instances to display
 */
public class MainActivity extends AppCompatActivity {

    // Parameters to handle fragments to display
    private static FragmentManager fragmentManager;
    private static AddMeetingFragment addMeetingFragment;
    private static InfoMeetingFragment infoMeetingFragment;
    private static ListMeetingsFragment listMeetingsFragment;
    private static ListEmployeesFragment listEmployeesFragment;

    private String TAG_ADD_MEETING_FRAGMENT = "TAG_ADD_MEETING_FRAGMENT";
    private String TAG_LIST_EMPLOYEES_FRAGMENT = "TAG_LIST_EMPLOYEES_FRAGMENT";

    // Service
    private ListApiService listApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        // Initialize fragment instances
        addMeetingFragment = AddMeetingFragment.newInstance();
        infoMeetingFragment = InfoMeetingFragment.newInstance();
        listMeetingsFragment = ListMeetingsFragment.newInstance();
        listEmployeesFragment = ListEmployeesFragment.newInstance();

        // Initialize service
        listApiService = DI.getListApiService();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    /**
     * Overriden to handle click on hardware back button
     */
    @Override
    public void onBackPressed(){
        if(getSupportFragmentManager().findFragmentByTag(TAG_ADD_MEETING_FRAGMENT).isAdded()){
            AddMeetingFragment fragment = (AddMeetingFragment) getSupportFragmentManager().findFragmentByTag(TAG_ADD_MEETING_FRAGMENT);
            // Remove AddMeetingFragment from stack
            fragmentManager.popBackStack();
            // Reset text inputs
            fragment.clearTextInputsFields();
        }
        else if(getSupportFragmentManager().findFragmentByTag(TAG_LIST_EMPLOYEES_FRAGMENT).isAdded()){
            ListEmployeesFragment fragment = (ListEmployeesFragment) getSupportFragmentManager().findFragmentByTag(TAG_LIST_EMPLOYEES_FRAGMENT);
            // Save selected Employee into String
            fragment.saveSelectionToForNewMeeting();
            // Remove ListEmployeesFragment from stack
            fragmentManager.popBackStack();
        }
        else{ // ListMeetingsFragment is added
            finish();
        }
    }

    // Getters for static fragment instances
    public static AddMeetingFragment getAddMeetingFragment(){
        return addMeetingFragment;
    }

    public static ListMeetingsFragment getListMeetingsFragment(){
        return listMeetingsFragment;
    }

    public static ListEmployeesFragment getListEmployeesFragment(){
        return listEmployeesFragment;
    }

    // Getter for service
    public ListApiService getListApiService(){
        return this.listApiService;
    }
}
