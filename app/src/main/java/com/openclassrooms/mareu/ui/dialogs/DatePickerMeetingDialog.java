package com.openclassrooms.mareu.ui.dialogs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.mareu.R;
import java.util.Calendar;
import java.util.Objects;

/**
 * Class used to display a DatePickerDialog to allow user to choose
 * a meeting Date
 */
public class DatePickerMeetingDialog {

    private Context context;
    private TextInputEditText textInput;

    private DatePickerDialog datePickerDialog;

    public DatePickerMeetingDialog(Context context, TextInputEditText textInput){
        this.context = context;
        this.textInput = textInput;
    }

    /**
     * This method creates a new DatePickerDialog and show it
     */
    public void showDatePickerDialogOnClick(){
        int yearToSet;
        int monthToSet;
        int dayToSet;

        // Initialize date data to display
        if(!Objects.requireNonNull(textInput.getText()).toString().equals("")){
            // If a date has already been selected
            String date = textInput.getText().toString();
            dayToSet = Integer.parseInt(date.substring(0,2));
            monthToSet = Integer.parseInt(date.substring(3,5)) - 1;
            yearToSet = Integer.parseInt(date.substring(6));
        }
        else{
            // If first display of the Dialog, no date selected yet
            final Calendar calendar = Calendar.getInstance();
            yearToSet = calendar.get(Calendar.YEAR);
            monthToSet = calendar.get(Calendar.MONTH);
            dayToSet = calendar.get(Calendar.DAY_OF_MONTH);
        }

        // Create DatePickerDialog instance
        datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String dateToDisplay = "";

                // Format JJ/MM/AAAA
                // JJ
                if(day < 10){ dateToDisplay = "0" + dateToDisplay + day;}
                else{dateToDisplay = dateToDisplay + day; }
                // MM
                if(month+1 < 10){ dateToDisplay = dateToDisplay +  "/0" + (month+1) + "/"; }
                else{ dateToDisplay = dateToDisplay + "/" + (month+1) + "/"; }
                // AA
                dateToDisplay = dateToDisplay + year;
                textInput.setText(dateToDisplay);
            }

        }, yearToSet, monthToSet, dayToSet);

        // Display DatePickerDialog
        datePickerDialog.show();
    }

}
