package com.openclassrooms.mareu.ui.dialogs.inputtexts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.fragments.addmeeting.AddMeetingFragment;
import com.openclassrooms.mareu.utils.DateAndTimeConverter;
import java.util.Calendar;

/**
 * Class used to display a DatePickerDialog to allow user to choose
 * a meeting Date
 * Used in @{@link AddMeetingFragment} fragment
 */
public class DatePickerMeetingDialog extends DialogFragment {

    private Context context;

    // Interface
    private InputTextChangeCallback callback;

    // Current value displayed
    private String currentValue;

    public DatePickerMeetingDialog(){/* Empty constructor */}

    public DatePickerMeetingDialog(Context context, String currentValue, InputTextChangeCallback callback) {

        this.context = context;
        this.currentValue = currentValue;
        this.callback = callback;
    }

    public void setCallback(InputTextChangeCallback callback){

        this.callback = callback;
    }

    /**
     * This method creates a new DatePickerDialog and show it
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Retain current DialogFragment instance
        setRetainInstance(true);

        int yearToSet;
        int monthToSet;
        int dayToSet;

        // Extract day, month, year in a String[3] tab
        String[] parts = currentValue.split("/");
        if(parts.length >= 3){
            // If a date is already displayed, parts[] contains previous values
            dayToSet = Integer.parseInt(parts[0]);
            monthToSet = Integer.parseInt(parts[1]);
            yearToSet = Integer.parseInt(parts[2]);
        }
        else{
            // else no date displayed, then display current values from Calendar instance
            final Calendar calendar = Calendar.getInstance();
            yearToSet = calendar.get(Calendar.YEAR);
            monthToSet = calendar.get(Calendar.MONTH);
            dayToSet = calendar.get(Calendar.DAY_OF_MONTH);
        }

        // Create DatePickerMeetingDialog
        // Format : DD/MM/YYYY
        // Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, (DatePicker view, int year, int month, int day) -> {
            // Format : DD/MM/YYYY

            String dateToDisplay = DateAndTimeConverter.dateConverter(year, month, day);
            callback.onSetDate(dateToDisplay);
        }
                , yearToSet, monthToSet, dayToSet);

        // Specify min date
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        return datePickerDialog;
    }
}
