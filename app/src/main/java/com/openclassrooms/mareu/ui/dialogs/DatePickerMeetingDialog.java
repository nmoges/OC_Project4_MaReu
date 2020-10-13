package com.openclassrooms.mareu.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.utils.DateAndTimeConverter;
import java.util.Calendar;
import java.util.Objects;

/**
 * Class used to display a DatePickerDialog to allow user to choose
 * a meeting Date
 * Used in @{@link com.openclassrooms.mareu.ui.fragments.AddMeetingFragment} fragment
 */
public class DatePickerMeetingDialog extends DialogFragment {

    private Context context;
    private TextInputEditText textInput;

    private DatePickerDialog datePickerDialog;

    public DatePickerMeetingDialog(){ }

    public DatePickerMeetingDialog(Context context, TextInputEditText textInput){
        this.context = context;
        this.textInput = textInput;
    }

    /**
     * This method creates a new DatePickerDialog and show it
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        setRetainInstance(true);

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
                dateToDisplay = DateAndTimeConverter.dateConverter(year, month, day);

                textInput.setText(dateToDisplay);

            }
        }, yearToSet, monthToSet, dayToSet);

        return datePickerDialog;
    }
}
