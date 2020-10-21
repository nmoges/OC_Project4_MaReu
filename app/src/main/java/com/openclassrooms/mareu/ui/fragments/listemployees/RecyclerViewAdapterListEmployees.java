package com.openclassrooms.mareu.ui.fragments.listemployees;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Employee;
import com.openclassrooms.mareu.model.SelectedEmployee;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for ListEmployeesFragment
 */
public class RecyclerViewAdapterListEmployees extends RecyclerView.Adapter<RecyclerViewAdapterListEmployees.ViewHolderItemEmployee> {

    private List<Employee> listEmployees;
    private ArrayList<SelectedEmployee> listSelectedEmployee = new ArrayList<>();
    private int nbSelectedEmployees;

    // To perform click on CheckBox items
    private final OnItemClickBoxListener onItemClickBoxListener;

    public RecyclerViewAdapterListEmployees(List<Employee> listEmployees, OnItemClickBoxListener onItemClickBoxListener){
        this.listEmployees = listEmployees;
        this.onItemClickBoxListener = onItemClickBoxListener;

        // Initialize list
        for(int i = 0; i < listEmployees.size(); i++){
            listSelectedEmployee.add(new SelectedEmployee(listEmployees.get(i))); // selected == false (by default)
        }

        this.nbSelectedEmployees = 0;
    }

    @NonNull
    @Override
    public ViewHolderItemEmployee onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_employees_item, parent, false);
        return new ViewHolderItemEmployee(view, onItemClickBoxListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItemEmployee holder, int position) {
        // Name employee
        holder.nameEmployee.setText(listEmployees.get(position).getName());

        // Email employee
        holder.mailEmployee.setText(listEmployees.get(position).getEmail());

        // CheckBox
        holder.employeeSelected.setChecked(listSelectedEmployee.get(position).getSelected());
    }

    @Override
    public int getItemCount() {
        return listEmployees.size();
    }

    /**
     * ViewHolder class for RecyclerView
     */
    static class ViewHolderItemEmployee extends RecyclerView.ViewHolder implements  CheckBox.OnClickListener {

        private final TextView nameEmployee;
        private final TextView mailEmployee;
        private final CheckBox employeeSelected;
        private final OnItemClickBoxListener onItemClickBoxListener;

        ViewHolderItemEmployee(View view, OnItemClickBoxListener onItemClickBoxListener){
            super(view);
            // Init
            nameEmployee = view.findViewById(R.id.name_employee_item_recycler_view);
            mailEmployee = view.findViewById(R.id.mail_employee_item_recycler_view);
            employeeSelected = view.findViewById(R.id.checkbox_item_recycler_view);
            this.onItemClickBoxListener = onItemClickBoxListener;

            // Enable onClickListener property on CheckBox
            employeeSelected.setOnClickListener(this);
        }

        /**
         * Overriden to send adapter position to onItemClickBox to perform
         * action on clicked CheckBox
         * @param view : View
         */
        @Override
        public void onClick(View view){
            onItemClickBoxListener.onItemClickBox(getAdapterPosition());
        }
    }

    /**
     * Interface used to perform action when CheckBox at indice "position" in
     * RecyclerView is clicked
     */
    public interface OnItemClickBoxListener{
        void onItemClickBox(int position);
    }

    /**
     * Method to update "selected" status from ListEmployeesFragment,
     * using the implemented OnItemClickBoxListener interface
     * @return : ArrayList<SelectedEmployee>
     */
    public ArrayList<SelectedEmployee> getListSelectedEmployee(){
        return this.listSelectedEmployee;
    }

    /**
     * Method which reinitialize all "seleted" status to false when user
     * clicks on "Reset" icon from toolbar ListEmployeesFragment fragment
     */
    public void reinitAllSelectedStatus(){
        int indice = 0;

        // Reinit status
        while(nbSelectedEmployees > 0 && indice < listSelectedEmployee.size()){
            if(listSelectedEmployee.get(indice).getSelected()){
                listSelectedEmployee.get(indice).setSelected(false);
                nbSelectedEmployees--;

            }
            else{
                indice++;
            }
        }
        // Update display
        notifyDataSetChanged();
    }

    // Getter
    public int getNbSelectedEmployees(){
        return this.nbSelectedEmployees;
    }

    // Methods for updates of the number of selected Employee
    public void incrementSelectedEmployees(){
        this.nbSelectedEmployees++;
    }

    public void decrementSelectedEmployees(){
        this.nbSelectedEmployees--;
    }
}
