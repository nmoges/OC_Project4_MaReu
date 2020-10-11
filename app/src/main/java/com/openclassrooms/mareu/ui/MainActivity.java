package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.fragments.AddMeetingFragment;
import com.openclassrooms.mareu.ui.fragments.InfoMeetingFragment;
import com.openclassrooms.mareu.ui.fragments.listemployees.ListEmployeesFragment;
import com.openclassrooms.mareu.ui.fragments.listmeetings.ListMeetingsFragment;

public class MainActivity extends AppCompatActivity {

    // Parameters to handle fragments to display
    private FragmentManager fragmentManager;
    private AddMeetingFragment addMeetingFragment;
    private InfoMeetingFragment infoMeetingFragment;
    private ListMeetingsFragment listMeetingsFragment;
    private ListEmployeesFragment listEmployeesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        // Initialize fragment instances
        addMeetingFragment = new AddMeetingFragment(this);
        infoMeetingFragment = new InfoMeetingFragment(this);
        listMeetingsFragment = new ListMeetingsFragment(this);
        listEmployeesFragment = new ListEmployeesFragment(this);

        // Initialize by displaying list fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listMeetingsFragment).commit();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

        if(addMeetingFragment.isVisible()){
            // If AddMeetingFragment visible, replace by mListMeetingFragment
            fragmentManager.beginTransaction().replace(R.id.fragment_container, listMeetingsFragment).commit();
            // Reset text inputs
            addMeetingFragment.clearTextInputsFields();
        }
        else if (infoMeetingFragment.isVisible()){
            // If InfoMeetingFragment visible, replace by mListMeetingFragment
            fragmentManager.beginTransaction().replace(R.id.fragment_container, listMeetingsFragment).commit();
        }
        else if(listEmployeesFragment.isVisible()){
            listEmployeesFragment.saveSelectionToForNewMeeting();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, addMeetingFragment).commit();
        }
        else { // ListMeetingsFragment.isVisible()
            // Else no fragment visible, then quit application
            finish();
        }
    }

    public AddMeetingFragment getAddMeetingFragment(){
        return this.addMeetingFragment;
    }

    public ListMeetingsFragment getListMeetingsFragment(){
        return this.listMeetingsFragment;
    }

    public ListEmployeesFragment getListEmployeesFragment(){
        return this.listEmployeesFragment;
    }
}
