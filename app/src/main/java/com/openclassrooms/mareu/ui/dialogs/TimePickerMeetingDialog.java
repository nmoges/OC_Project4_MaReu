package com.openclassrooms.mareu.ui.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.fragments.addmeeting.AddMeetingFragment;
import com.openclassrooms.mareu.utils.DateAndTimeConverter;
import java.util.Calendar;

/**
 * Class used to display a TimePickerDialog to allow user to choose
 * a meeting hour
 * Used in @{@link AddMeetingFragment} fragment
 */
public class TimePickerMeetingDialog extends DialogFragment {

    private Context context;
    // Dialog
    private TimePickerDialog timePickerDialog;
    // Interface
    private InputTextChangeCallback callback;
    // Current value displayed
    private String currentValue;
    // Type (Start hour or End hour)
    private TimeType timeType;

    public TimePickerMeetingDialog(){/* Empty constructor */}

    public TimePickerMeetingDialog(Context context, String currentValue, TimeType timeType, InputTextChangeCallback callback){
        this.context = context;
        this.callback = callback;
        this.currentValue = currentValue;
        this.timeType = timeType;
    }

    public void setCallback(InputTextChangeCallback callback) { this.callback = callback; }

    /**
     * This method creates a new TimePickerMeetingDialog and show it
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Retain current DialogFragment instance
        setRetainInstance(true);

        // Parameters to send to Dialog
        int hourCalendarToSet;
        int minutesCalendarToSet;

        // Extract hour and minutes in a String[2] tab
        String[] parts = currentValue.split(":");

        if(parts.length >= 2){ // Current time displayed
            hourCalendarToSet = Integer.parseInt(parts[0]);
            minutesCalendarToSet = Integer.parseInt(parts[1]);
        }
        else{ // No time displayed yet
            final Calendar calendar = Calendar.getInstance();
            hourCalendarToSet = calendar.get(Calendar.HOUR_OF_DAY);
            minutesCalendarToSet = calendar.get(Calendar.MINUTE);
        }

        // Create TimePickerDialog instance
        timePickerDialog = new TimePickerDialog(context, R.style.DialogTheme,
                (TimePicker timePicker, int hour, int minutes) -> {
                    // Format HH:MM
                    String timeToDisplay = DateAndTimeConverter.timeConverter(hour, minutes);
                    callback.onSetTime(timeToDisplay, timeType);
                }, hourCalendarToSet, minutesCalendarToSet, true);

        return timePickerDialog;
    }
}
