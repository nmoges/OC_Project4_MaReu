package com.openclassrooms.mareu.ui.fragments.listmeetings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.ListMeetingsGenerator;
import com.openclassrooms.mareu.ui.MainActivity;

import java.util.List;
import java.util.Objects;

public class ListMeetingsFragment extends Fragment {

    private FloatingActionButton fab;
    private Toolbar toolbar;

    private MainActivity parentActivity;

    // For displaying list of meetings
    private List<Meeting> listMeetings;
    private RecyclerView recyclerView;

    public ListMeetingsFragment(MainActivity mainActivity) {
        this.parentActivity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        handleFabClick();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_meetings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = parentActivity.findViewById(R.id.toolbar_list_meeting_fragment);
        fab = parentActivity.findViewById(R.id.fab_list_meetings_fragment);

        initializeToolbar();

        listMeetings = ListMeetingsGenerator.generateListMeetings();

        recyclerView = parentActivity.findViewById(R.id.recycler_view_list_meetings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewAdapterListMeetings adapterListMeetings = new RecyclerViewAdapterListMeetings(listMeetings);
        recyclerView.setAdapter(adapterListMeetings);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_list_meetings_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.filter_by_date_item_menu :
                Log.i("FILTER_OPTION", "DATE");
                break;
            case R.id.filter_by_room_item_menu :
                Log.i("FILTER_OPTION", "ROOM");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method initialize Toolbar Fragment, using parent activity
     */
    private void initializeToolbar(){
        // Set Support Action Bar to modify Toolbar title
        parentActivity.setSupportActionBar(toolbar);
        Objects.requireNonNull(parentActivity.getSupportActionBar())
                .setTitle(getResources().getString(R.string.toolbar_name_list_meeting_activity));
    }

    /**
     * This method handle click interaction on FloatingActionButton of MainActivity activity
     * When fab is clicked : AddMeetingFragment fragment is displayed, and the fab is hided.
     */
    public void handleFabClick(){
        fab.setOnClickListener((View view) -> {
                    parentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, parentActivity.getAddMeetingFragment()).commit();
                }
        );
    }
}