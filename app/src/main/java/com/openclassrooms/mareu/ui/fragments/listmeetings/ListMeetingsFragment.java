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
import com.openclassrooms.mareu.ui.MainActivity;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_list_meetings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = requireActivity().findViewById(R.id.toolbar_list_meeting_fragment);
        fab = requireActivity().findViewById(R.id.fab_list_meetings_fragment);

        initializeToolbar();
        initializeList();
        initializeRecyclerView();
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
}