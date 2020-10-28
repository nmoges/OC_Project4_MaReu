package com.openclassrooms.mareu.ui.dialogs.filter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.utils.DateAndTimeConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * This Dialog is displayed when user select option "Filter by Date" in @{@link com.openclassrooms.mareu.ui.fragments.listmeetings.ListMeetingsFragment}
 * collapsing menu.
 * It allows user to select which Meeting must be displayed according to one or two specified dates.
 */
public class FilterDateDialog extends DialogFragment {

    private Context context;

    // Interface with ListMeetingFragment
    public FilterActionListener listener;

    // Input fields
    private TextInputEditText firstOptionDateInputText;
    private TextInputEditText secondOptionStartDateInputText;
    private TextInputEditText secondOptionEndDateInputText;
    private TextInputLayout secondOptionEndDateLayout;

    // CheckBox for option selection
    private CheckBox checkBox1;
    private CheckBox checkBox2;

    // Dialog to Date selection
    private DatePickerDialog datePickerDialog;

    private String inputTextSelected = "Input1";

    public FilterDateDialog(){/* Empty constructor */}

    public FilterDateDialog(FilterActionListener listener, Context context) {

        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Retain current DialogFragment instance
        setRetainInstance(true);

        // Create Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Set builder
        builder.setView(inflater.inflate(R.layout.layout_dialog_filter_meeting_date, null))
               .setTitle(R.string.title_dialog_filter_by_date)
               .setPositiveButton(R.string.btn_yes_confirm_filter_by_date, (DialogInterface dialog, int which) -> {
                    if (checkBox1.isChecked() && firstOptionDateInputText.getText().length() > 0) {
                        listener.validFilterDateOption1(firstOptionDateInputText.getText().toString());
                    }
                    if (checkBox2.isChecked() && secondOptionStartDateInputText.getText().length() > 0 && secondOptionEndDateInputText.getText().length() > 0) {
                        listener.validFilterDateOption2(secondOptionStartDateInputText.getText().toString(),
                                                        secondOptionEndDateInputText.getText().toString());
                    }
            }
                ).setNegativeButton(R.string.btn_no_confirm_filter_by_date, (DialogInterface dialog, int which) -> {
                    // Close dialog
                }
        );

        return builder.create();
    }

    @Override
    public void onResume() {

        super.onResume();
        // Initialization
        initializeIds();
        initDatePickerDialog();

        // Listeners
        handleCheckBoxOptionsListeners();
        handleTextInputEditTextListeners();
    }

    /**
     * This method handles both Checkbox listeners and updates inputText fields focus according
     * user selection
     */
    public void handleCheckBoxOptionsListeners() {

        checkBox1.setChecked(true);
        checkBox1.setOnClickListener((View v) -> {
                if(checkBox1.isChecked()){
                    // Deselect CheckBox 2
                    checkBox2.setChecked(false);
                    // Request focus on Option 1 EditText instead of Option 2
                    firstOptionDateInputText.requestFocus();
                }
            }
        );

        checkBox2.setOnClickListener((View v) -> {
                if (checkBox2.isChecked()) {
                    // Deselect CheckBox 1
                    checkBox1.setChecked(false);
                    // Request focus on Option 2 EditText instead of Option 1
                    secondOptionStartDateInputText.requestFocus();
                }
            }
        );
    }

    /**
     * This method handles click listener in each InputText field. Every click will select corresponding option,
     * and launch a DatePickerDialog to date selection
     */
    private void handleTextInputEditTextListeners() {

        firstOptionDateInputText.setOnClickListener((View v) -> {
                if (!checkBox1.isChecked()) {
                    // Update Checkbox status
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(false);
                    // Clear fields
                    if (secondOptionStartDateInputText.getText().length() > 0) { secondOptionStartDateInputText.getText().clear(); }
                    if (secondOptionEndDateInputText.getText().length() > 0) { secondOptionEndDateInputText.getText().clear(); }
                }
                // Specify which inputText will be updated in Dialog
                inputTextSelected = "Input1";
                // Display Dialog to user
                datePickerDialog.show();
            }
        );

        secondOptionStartDateInputText.setOnClickListener((View v) -> {
                if (!checkBox2.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(true);
                    if (firstOptionDateInputText.getText().length() > 0){ firstOptionDateInputText.getText().clear(); }
                }
                inputTextSelected = "Input2";
                datePickerDialog.show();
            }
        );

        secondOptionEndDateInputText.setOnClickListener((View v) -> {
                if (!checkBox2.isChecked()) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(true);
                    if (firstOptionDateInputText.getText().length() > 0) { firstOptionDateInputText.getText().clear(); }
                }
                inputTextSelected = "Input3";
                datePickerDialog.show();
            }
        );
    }

    public void initializeIds() {

        // CheckBox
        checkBox1 = Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_1);
        checkBox2 = Objects.requireNonNull(getDialog()).findViewById(R.id.checkbox_filter_2);

        // TextInputEditText
        firstOptionDateInputText = Objects.requireNonNull(getDialog()).findViewById(R.id.input_edit_filter_date_1_date);
        secondOptionStartDateInputText = Objects.requireNonNull(getDialog()).findViewById(R.id.input_edit_filter_date_2_start_date);
        secondOptionEndDateInputText = Objects.requireNonNull(getDialog()).findViewById(R.id.input_edit_filter_date_2_end_date);
        secondOptionEndDateLayout = Objects.requireNonNull(getDialog()).findViewById(R.id.input_layout_filter_date_2_end_date);
    }

    /**
     * This method initializes the DatePickerDialog used for date selection
     */
    public void initDatePickerDialog() {

        final Calendar calendar = Calendar.getInstance();
        int yearToSet = calendar.get(Calendar.YEAR);
        int monthToSet = calendar.get(Calendar.MONTH);
        int dayToSet = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, (DatePicker view, int year, int month, int day) -> {
                String dateToDisplay = DateAndTimeConverter.dateConverter(year, month, day);
                // Update corresponding inputText field
                switch (inputTextSelected) {
                    case "Input1":
                        firstOptionDateInputText.setText(dateToDisplay);
                        break;
                    case "Input2":
                        secondOptionStartDateInputText.setText(dateToDisplay);
                        updateDialogWithErrorStatus();
                        break;
                    case "Input3":
                        secondOptionEndDateInputText.setText(dateToDisplay);
                        updateDialogWithErrorStatus();
                        break;
                }
            }
        , yearToSet, monthToSet, dayToSet);
    }

    /**
     * This method is used to compare start and end date in case of "Option 2" is selected to filter list
     * @return : boolean
     */
    public boolean compareDates() {

        boolean comparison = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

        try{
            Date startDate = dateFormat.parse(secondOptionStartDateInputText.getText().toString());
            Date endDate = dateFormat.parse(secondOptionEndDateInputText.getText().toString());
            if(startDate.compareTo(endDate) < 1){
                // If start date before end date
                comparison = true;
            }
        } catch(ParseException exception){
            exception.printStackTrace();
        }
        return comparison;
    }

    /**
     * This method updates the inputLayout of the end date field, if the end date is not correctly specified.
     * It also enable/disable Dialog Position button according to the error status
     */
    public void updateDialogWithErrorStatus() {

        if (secondOptionStartDateInputText.getText().length() > 0 && secondOptionEndDateInputText.getText().length() > 0) {

            if (!compareDates()) { // If end hour < start hour : ERROR
                if (!secondOptionEndDateLayout.isErrorEnabled()) {
                    // Display Error message
                    secondOptionEndDateLayout.setErrorEnabled(true);
                    secondOptionEndDateLayout.setError("ERROR");
                    // Disable position button
                    ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
            else { // end hour >= start hour : OK
                if (secondOptionEndDateLayout.isErrorEnabled()) {
                    // Hide Error message
                    secondOptionEndDateLayout.setErrorEnabled(false);
                    // Enable position button
                    ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        }
    }
}
