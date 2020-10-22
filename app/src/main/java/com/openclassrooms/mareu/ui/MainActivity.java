package com.openclassrooms.mareu.ui;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.fragments.listmeetings.ListMeetingsFragment;

/**
 * Main activity of the application, contains all fragments instances to display
 */
public class MainActivity extends AppCompatActivity implements MainActivityCallback {

    // Parameters to handle fragments to display
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();

        //set the first fragment to [ListMeetingsFragment]
        changeFragment(ListMeetingsFragment.newInstance(), ListMeetingsFragment.TAG);
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_name_list_meeting_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back_white_24dp));

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    showBack();
                } else {
                    hideBAck();
                }
            }
        });

    }

    @Override
    public void setToolbarTitle(@StringRes int title) {
        toolbar.setTitle(title);
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void changeFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    private void showBack() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back_white_24dp));
        }
    }

    private void hideBAck() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void popBack() {
        getSupportFragmentManager().popBackStack();
    }
}
