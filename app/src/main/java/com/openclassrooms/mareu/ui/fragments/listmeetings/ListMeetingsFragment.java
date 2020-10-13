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
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.event.DeleteMeetingEvent;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.ui.MainActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Objects;

public class ListMeetingsFragment extends Fragment {

    private FloatingActionButton fab;
    private Toolbar toolbar;

    // For displaying list of meetings
    private ArrayList<Meeting> listMeetings;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterListMeetings adapterListMeetings;

    // Parent activity
    private MainActivity parentActivity;

    // Background text
    private TextView backgroundText; // Displayed if no Meeting stored

    public ListMeetingsFragment(){ }

    public static ListMeetingsFragment newInstance(){
        return new ListMeetingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        handleFabClick();
        adapterListMeetings.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_list_meetings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeIds();
        initializeToolbar();
        initializeList();
        initializeRecyclerView();
        updateBackgroundTxtDisplay();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_list_meetings_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.filter_by_date_item_menu :
                // TODO() : To implement
                break;
            case R.id.filter_by_room_item_menu :
                // TODO() : To implement
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initializeList(){
        listMeetings = parentActivity.getListApiService().getListMeetings();
    }

    public void initializeIds(){
        toolbar = parentActivity.findViewById(R.id.toolbar_list_meeting_fragment);
        fab = parentActivity.findViewById(R.id.fab_list_meetings_fragment);
        backgroundText = parentActivity.findViewById(R.id.background_txt);
    }

    /**
     * Updates display of the background text according to the size of listMeetings :
     *      - If no Meeting stored in list : display "No Meetings" message
     *      - Else no display of 'No Meetings" message
     */
    public void updateBackgroundTxtDisplay(){
        if(listMeetings.size() == 0){
            backgroundText.setVisibility(View.VISIBLE);
        }
        else{
            backgroundText.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Initializes adapter and recyclerview display
     */
    public void initializeRecyclerView(){
        recyclerView = parentActivity.findViewById(R.id.recycler_view_list_meetings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterListMeetings = new RecyclerViewAdapterListMeetings(listMeetings, getParentFragmentManager());
        recyclerView.setAdapter(adapterListMeetings);
    }

    /**
     * This method initialize Toolbar Fragment, using parent activity
     */
    private void initializeToolbar(){
        // Set Support Action Bar to modify Toolbar title
        parentActivity.setSupportActionBar(toolbar);
        Objects.requireNonNull(parentActivity.getSupportActionBar()).setTitle(getResources().getString(R.string.toolbar_name_list_meeting_activity));
    }

    /**
     * This method handle click interaction on FloatingActionButton of MainActivity activity
     * When fab is clicked : AddMeetingFragment fragment is displayed, and the fab is hided.
     */
    public void handleFabClick(){
        fab.setOnClickListener((View view) -> {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, MainActivity.getAddMeetingFragment()).addToBackStack(null).commit();
                }
        );
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event : DeleteMeetingEvent
     */
    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event){

        // Remove Meeting from list
        parentActivity.getListApiService().removeMeeting(event.meeting);

        // Update Meeting list
        adapterListMeetings.notifyDataSetChanged();

        // Update background text display
        updateBackgroundTxtDisplay();
    }
}