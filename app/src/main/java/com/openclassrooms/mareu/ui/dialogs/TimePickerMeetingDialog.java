package com.openclassrooms.mareu.ui.dialogs;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.mareu.R;
import java.util.Calendar;
import java.util.Objects;

/**
 * Class used to display a TimePickerDialog to allow user to choose
 * a meeting hour
 * Used in @{@link com.openclassrooms.mareu.ui.fragments.AddMeetingFragment} fragment
 */
public class TimePickerMeetingDialog {

    private Context context;
    private TextInputEditText textInput;

    public TimePickerMeetingDialog(Context context, TextInputEditText textInput){
        this.context = context;
        this.textInput = textInput;
    }

    public void showTimePickerDialogOnClick(){

        int hourCalendarToSet;
        int minutesCalendarToSet;

        // Initialize hour data to display
        if(!Objects.requireNonNull(textInput.getText()).toString().equals("")){
            // If an hour has already been selected
            String time = textInput.getText().toString();
            hourCalendarToSet = Integer.parseInt(time.substring(0,2));
            minutesCalendarToSet = Integer.parseInt(time.substring(3));
        }
        else{
            // If first display of the Dialog, no hour selected yet
            final Calendar calendar = Calendar.getInstance();
            hourCalendarToSet = calendar.get(Calendar.HOUR_OF_DAY);
            minutesCalendarToSet = calendar.get(Calendar.MINUTE);
        }

        // Create TimePickerDialog instance
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                String hourToDisplay = "";

                // Format HH:MM
                if(hour < 10){ hourToDisplay = hourToDisplay + "0" + hour + ":" + minutes; }
                else{ hourToDisplay = hourToDisplay + hour + ":" + minutes;}

                textInput.setText(hourToDisplay);
            }
        }, hourCalendarToSet, minutesCalendarToSet, false);

        // Display TimePickerDialog
        timePickerDialog.show();
    }
}
