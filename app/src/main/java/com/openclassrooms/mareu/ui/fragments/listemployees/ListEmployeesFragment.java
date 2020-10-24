package com.openclassrooms.mareu.ui.fragments.listemployees;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Employee;
import com.openclassrooms.mareu.model.SelectedEmployee;
import com.openclassrooms.mareu.service.ListEmployeesGenerator;
import com.openclassrooms.mareu.ui.MainActivityCallback;
import java.util.ArrayList;
import java.util.List;

/**
 * This class enables user to select participants for a meeting.
 * It also implement the OnItemClickBoxListener interface from @{@link RecyclerViewAdapterListEmployees} class adapter
 * to perform this selection.
 */
public class ListEmployeesFragment extends Fragment implements  RecyclerViewAdapterListEmployees.OnItemClickBoxListener {

    public static final String TAG = "TAG_LIST_EMPLOYEES_FRAGMENT";

    // For displaying list of employees
    private List<Employee> listEmployees;
    private RecyclerViewAdapterListEmployees adapterListEmployees;

    /**
     * This variable is sent back to AddMeetingFragment, instead of sending a List<Employee> object
     * It specifies which Employee is selected for a Meeting :
     *      - "selection" size is equals to the number of existing Employee defined in @{@link ListEmployeesGenerator}
     *      - A "0" char value means that the corresponding Employee is not selected
     *      - A "1" char value means that the corresponding Employee is selected
     */
    private String selection = "";
    private final String TAG_SELECTION = "TAG_SELECTION";

    public ListEmployeesFragment() { }

    public static ListEmployeesFragment newInstance() {

        return new ListEmployeesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {

        super.onResume();
        initializeCheckBoxes();
        updateToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_employees, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        listEmployees = DI.getListApiService().getListEmployees();

        initializeRecyclerView(view);

        initializeToolbar();

        if (savedInstanceState != null) {
            selection = savedInstanceState.getString(TAG_SELECTION);
        }

        getOldSelectionFromAddMeetingFragment();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_list_employee_fragment, menu);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            // Save String selection in Bundle
            saveSelectionToForNewMeeting();
            // Quit fragment
            ((MainActivityCallback) getActivity()).popBack();
        }
        else { // id == R.id.reset_item_menu
            // Reset "selected" status + items display
            adapterListEmployees.reinitAllSelectedStatus();

            // Reset toolbar title
            ((MainActivityCallback) getActivity()).setToolbarTitle(R.string.toolbar_name_frag_list_employees);

            // Reset toolbar icon
            ((MainActivityCallback) getActivity()).updateHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initializeToolbar() {

        ((MainActivityCallback) getActivity()).setToolbarTitle(R.string.toolbar_name_frag_list_employees);

        // Add "back button"
        ((MainActivityCallback) getActivity()).setHomeAsUpIndicator(true);
    }

    private void initializeRecyclerView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list_employees);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterListEmployees = new RecyclerViewAdapterListEmployees(listEmployees, this);
        recyclerView.setAdapter(adapterListEmployees);
    }

    /**
     * This method updates the "selected" status of all SelectionEmployee
     * used to determine the status of each Checkbox displayed
     */
    private void initializeCheckBoxes() {

        // If a selection has already been made, restore checkboxes status
        if(!selection.equals("")){
            for (int i = 0; i < adapterListEmployees.getListSelectedEmployee().size(); i++) {
                if (Character.toString(selection.charAt(i)).equals("1")) {
                    adapterListEmployees.getListSelectedEmployee().get(i).setSelected(true);
                    adapterListEmployees.incrementSelectedEmployees();
                }
                else {
                    adapterListEmployees.getListSelectedEmployee().get(i).setSelected(false);
                }
            }
        }
    }


    /**
     * OnItemClickBoxListener interface implementation
     * @param position : int
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onItemClickBox(int position) {

        // Update list of selected employees
        adapterListEmployees.getListSelectedEmployee().get(position).setSelected(!adapterListEmployees.getListSelectedEmployee().get(position).getSelected());

        // Update number of selected employees
        if(adapterListEmployees.getListSelectedEmployee().get(position).getSelected()){ adapterListEmployees.incrementSelectedEmployees(); }
        else{ adapterListEmployees.decrementSelectedEmployees(); }

        // Update toolbar
        updateToolbar();
    }

    public void updateToolbar() {

        if (adapterListEmployees.getNbSelectedEmployees() == 0) {
            // Update title
            ((MainActivityCallback) getActivity()).setToolbarTitle(R.string.toolbar_name_frag_list_employees);

            // Update icon
            ((MainActivityCallback) getActivity()).updateHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        }
        else {
            // Update title
            String newToolbarTitle = getResources().getString(R.string.txt_participants) + " (" + adapterListEmployees.getNbSelectedEmployees() + ")";
            ((MainActivityCallback) getActivity()).setToolbarTitle(newToolbarTitle);

            // Update icon
            ((MainActivityCallback) getActivity()).updateHomeAsUpIndicator(R.drawable.ic_baseline_check_white_24dp);
        }
    }
    /**
     * This method is used to specify to AddMeetingFragment which Employee has been selected
     * Instead of sending all the list, a String containing a number of characters equal to the number of Employee is sent
     *              - If the Employee is selected, the corresponding character is "1"
     *              - If the Employee is not selected, the corresponding character is "0"
     *
     */
    public void saveSelectionToForNewMeeting() {

        convertStatusIntoString();

        // Store value in Bundle
        Bundle result = new Bundle();
        result.putString("newSelectionString", selection);
        getActivity().getSupportFragmentManager().setFragmentResult("newSelection", result);
    }

    public void resetSelectionParameter() {

        selection = "";
    }

    /**
     * Method use to convert the information about how many Employee has been selected,
     * into a String.
     */
    public void convertStatusIntoString() {

        // Reinit "selection" value
        selection = "";

        ArrayList<SelectedEmployee> list = adapterListEmployees.getListSelectedEmployee();

        // Convert boolean status into String
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSelected()){ selection = selection + "1"; }
            else { selection = selection + "0"; }
        }
    }

    /**
     * Overriden to save "Selection" value and retrieve list of selected
     * Employee in case of configuration changes
     * @param outState : Bundle
     * */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        // Converting list of selected CheckBox in String
        convertStatusIntoString();
        // Save selection value
        outState.putString(TAG_SELECTION, selection);
    }

    /**
     * Method use to retrieve previous selection value, in case user display again
     * the ListEmployeesFragment list to update his selection
     */
    public void getOldSelectionFromAddMeetingFragment(){

        getActivity().getSupportFragmentManager().setFragmentResultListener("oldSelection", this,
                (@NonNull String requestKey, @NonNull Bundle result) -> {
                // Get back previous selection value
                selection = result.getString("oldSelectionString");
            }
        );
    }
}