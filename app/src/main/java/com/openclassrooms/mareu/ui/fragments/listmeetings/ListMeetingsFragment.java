package com.openclassrooms.mareu.ui.fragments.listmeetings;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.ui.MainActivity;
import com.openclassrooms.mareu.ui.dialogs.filter.FilterActionListener;
import com.openclassrooms.mareu.ui.dialogs.filter.FilterDateDialog;
import com.openclassrooms.mareu.ui.dialogs.filter.FilterRoomDialog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * This class displays the list of all Meeting created by user,
 * using the @{@link RecyclerViewAdapterListMeetings} class
 */
public class ListMeetingsFragment extends Fragment implements ListMeetingActionListener, FilterActionListener {

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

    // Fragment TAG
    private String TAG_ADD_MEETING_FRAGMENT = "TAG_ADD_MEETING_FRAGMENT";
    private String TAG_FILTER_BY_ROOM = "TAG_FILTER_BY_ROOM";
    private String TAG_FILTER_BY_DATE = "TAG_FILTER_BY_DATE";

    // Parameters for "Room" filtering (FilterRoomDialog)
    private boolean[] tabRoomFiltersSelected;
    private boolean[] previousRoomFiltersSelected;

    // Parameters for ConfirmDeleteDialog
    private int positionDelete;


    public ListMeetingsFragment() { /* Empty constructor */ }

    public static ListMeetingsFragment newInstance() { return new ListMeetingsFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Initialize tab
        tabRoomFiltersSelected = new boolean[10];
        previousRoomFiltersSelected = new boolean[10];
        if(savedInstanceState != null){
            // Get existing tab value after configuration change
            tabRoomFiltersSelected = savedInstanceState.getBooleanArray("FiltersRoom");
            previousRoomFiltersSelected = savedInstanceState.getBooleanArray("PreviousFiltersRoom");
            positionDelete = savedInstanceState.getInt("positionDelete");
        }
        else{
            // If not : first launch (all meeting rooms selected)
            Arrays.fill(tabRoomFiltersSelected, true);
            Arrays.fill(previousRoomFiltersSelected, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handleFabClick();
        initializeList();
        filterListByRoomSelection();
        adapterListMeetings.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentActivity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_list_meetings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialization
        initializeIds(view);
        initializeToolbar();
        initializeList();
        initializeRecyclerView(view);
        updateBackgroundTxtDisplay();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) { inflater.inflate(R.menu.menu_list_meetings_fragment, menu); }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.reset_filters_item_menu:
                // Reset tab values
                Arrays.fill(tabRoomFiltersSelected, true);
                // Reset display list
                adapterListMeetings.resetDisplayAfterFilterRemoved();
                break;
            case R.id.filter_by_date_item_menu:
                FilterDateDialog filterDateDialog = new FilterDateDialog(this, getContext());
                filterDateDialog.show(getParentFragmentManager(), TAG_FILTER_BY_DATE);
                break;
            case R.id.filter_by_room_item_menu:
                storePreviousSelection();
                FilterRoomDialog filterRoomDialog = new FilterRoomDialog(this, tabRoomFiltersSelected);
                filterRoomDialog.show(getParentFragmentManager(), TAG_FILTER_BY_ROOM);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initializeList() { listMeetings = parentActivity.getListApiService().getListMeetings(); }

    //TODO C'est plus le boulot de l'activity de gérer l'action bar
    //Et comment faire ?
    // 1) Créer une interface qui contiendra l'ensemble des actions que l'activité doit faire pour ses fragments
    // 2) pour tous les objets gérés par le parent, il faut récupérer l'instance de l'activité, la caster en ton interface
    // Et appeler l'action que tu veux exécuter
    // C'est principalement le cas pour le changement de fragment
    public void initializeIds(View view) {
        toolbar = view.findViewById(R.id.toolbar_list_meeting_fragment);
        fab = view.findViewById(R.id.fab_list_meetings_fragment);
        backgroundText = view.findViewById(R.id.background_txt);
    }


    /**
     * Updates display of the background text according to the size of listMeetings :
     * - If no Meeting stored in list : display "No Meetings" message
     * - Else no display of 'No Meetings" message
     */
    public void updateBackgroundTxtDisplay() {
        if (listMeetings.size() == 0) { backgroundText.setVisibility(View.VISIBLE); }
        else { backgroundText.setVisibility(View.INVISIBLE); }
    }

    /**
     * Initializes adapter and recyclerview display
     */
    public void initializeRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recycler_view_list_meetings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterListMeetings = new RecyclerViewAdapterListMeetings(listMeetings, this, getContext());
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
    public void handleFabClick() {
        fab.setOnClickListener((View view) -> {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, MainActivity.getAddMeetingFragment(), TAG_ADD_MEETING_FRAGMENT).addToBackStack(null).commit();
                }
        );
    }

    /**
     * Display an alert dialog to confirm the meeting deletion
     * @param meeting : Meeting
     */
    private void confirmSuppress(Meeting meeting) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Configure builder
        builder.setTitle(R.string.title_dialog_confirm_delete)
                .setMessage(R.string.text_dialog_confirm_delete)
                .setPositiveButton(R.string.btn_yes_dialog_confirm_delete, (DialogInterface dialogInterface, int i) -> {
                            deleteItem(meeting);
                        }
                )
                .setNegativeButton(R.string.btn_no_dialog_confirm_delete, (DialogInterface dialogInterface, int i) -> {
                            // Close Dialog
                        }
                );

        //show the dialog
        builder.create().show();
    }

    /**
     * This methods handles the Meeting list update by removing selected meeting,
     * and updates display (background elements + list)
     * @param meeting : Meeting
     */
    private void deleteItem(Meeting meeting) {
        // Remove Meeting from list
        parentActivity.getListApiService().removeMeeting(meeting);
        // Update Meeting list
        adapterListMeetings.notifyDataSetChanged();
        // Update background text display
        updateBackgroundTxtDisplay();
        // Update display list
        adapterListMeetings.deleteMeetingInListDisplayed(meeting);
    }

    /**
     * Implementation of ListMeetingActionListener interface
     * @param meeting : Meeting
     */
    @Override
    public void onDeleteItem(Meeting meeting) {
        confirmSuppress(meeting);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBooleanArray("FiltersRoom", tabRoomFiltersSelected);
        outState.putBooleanArray("PreviousFiltersRoom", previousRoomFiltersSelected);
        outState.putInt("positionDelete", positionDelete);
    }

    public void filterListByRoomSelection(){
        ArrayList<Meeting> newFilteredListMeeting = new ArrayList<>();
        ArrayList<String> filterNames = new ArrayList<>();

        // Extract filter name
        for(int i =0; i < tabRoomFiltersSelected.length; i++){
            if(tabRoomFiltersSelected[i]){ // If filter selected
                switch(i){
                    case 0: // Room Bohr
                        filterNames.add("BOHR");
                        break;
                    case 1: // Room Dirac
                        filterNames.add("DIRAC");
                        break;
                    case 2: // Room Einstein
                        filterNames.add("EINSTEIN");
                        break;
                    case 3: // Room Faraday
                        filterNames.add("FARADAY");
                        break;
                    case 4: // Room Feynman
                        filterNames.add("FEYNMAN");
                        break;
                    case 5 : // Room Heisenberg
                        filterNames.add("HEISENBERG");
                        break;
                    case 6 : // Room Maxwell
                        filterNames.add("MAXWELL");
                        break;
                    case 7 : // Room Newton
                        filterNames.add("NEWTON");
                        break;
                    case 8: // Room Pauli
                        filterNames.add("PAULI");
                        break;
                    case 9: // Room Planck
                        filterNames.add("PLANCK");
                        break;
                }
            }
        }

        // Filter meeting list
        for(int i = 0; i< listMeetings.size(); i++) {
            boolean found = false;
            int j = 0;
            while (j < filterNames.size() && !found) {
                if (listMeetings.get(i).getMeetingRoom().toUpperCase().equals(filterNames.get(j))) {
                    newFilteredListMeeting.add(listMeetings.get(i));
                    found = true;
                } else j++;
            }
        }

        // Update List
        adapterListMeetings.updateListMeetingToDisplay(newFilteredListMeeting);
    }

    /**
     * FilterActionListener interface implementation :
     * actionChangeFilterRoom(int position) : When a CheckBox from FilterRoomDialogFragment is
     * clicked, the associated value in tabRoomFiltersSelected is updated
     * @param position : int
     */
    @Override
    public void actionChangeFilterRoom(int position) {
        tabRoomFiltersSelected[position] = !tabRoomFiltersSelected[position];
    }

    @Override
    public void validFilterRoom() {
        filterListByRoomSelection();
    }

    @Override
    public void restorePreviousSelection() {
        for(int i =0; i < previousRoomFiltersSelected.length; i++){
            tabRoomFiltersSelected[i] = previousRoomFiltersSelected[i];
        }
    }

    @Override
    public boolean validFilterDateOption1(String dateFilter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        ArrayList<Meeting> newFilteredListMeeting = new ArrayList<>();

        try{
            Date dateFilterFormat = dateFormat.parse(dateFilter);
            for(int i = 0; i < listMeetings.size(); i++){
                Date dateMeetingFormat = dateFormat.parse(listMeetings.get(i).getDate());
                if(dateMeetingFormat.compareTo(dateFilterFormat) >= 0){
                    newFilteredListMeeting.add(listMeetings.get(i));
                }
            }

            // Update List
            adapterListMeetings.updateListMeetingToDisplay(newFilteredListMeeting);

        } catch(ParseException exception){
            exception.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean validFilterDateOption2(String startDateFilter, String endDateFilter) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        ArrayList<Meeting> newFilteredListMeeting = new ArrayList<>();

        try{
            Date startDateFilterFormat = dateFormat.parse(startDateFilter);
            Date endDateFilterFormat = dateFormat.parse(endDateFilter);
            for(int i = 0; i < listMeetings.size(); i++){
                Date dateMeetingFormat = dateFormat.parse(listMeetings.get(i).getDate());

                if(dateMeetingFormat.compareTo(startDateFilterFormat) >= 0 && dateMeetingFormat.compareTo(endDateFilterFormat) <= 0){
                    newFilteredListMeeting.add(listMeetings.get(i));
                }
            }

            // Update List
            adapterListMeetings.updateListMeetingToDisplay(newFilteredListMeeting);

        } catch(ParseException exception){
            exception.printStackTrace();
        }

        return false;
    }

    private void storePreviousSelection(){
        for(int i =0; i < tabRoomFiltersSelected.length; i++){
            previousRoomFiltersSelected[i] = tabRoomFiltersSelected[i];
        }
    }
}