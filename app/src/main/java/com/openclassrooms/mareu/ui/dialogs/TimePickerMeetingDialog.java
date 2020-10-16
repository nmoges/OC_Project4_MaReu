package com.openclassrooms.mareu.ui.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.utils.DateAndTimeConverter;
import java.util.Calendar;
import java.util.Objects;

/**
 * Class used to display a TimePickerDialog to allow user to choose
 * a meeting hour
 * Used in @{@link com.openclassrooms.mareu.ui.fragments.AddMeetingFragment} fragment
 */
public class TimePickerMeetingDialog extends DialogFragment {

    private Context context;
    private TextInputEditText textInput;

    private TimePickerDialog timePickerDialog;

    public TimePickerMeetingDialog(){ }

    public TimePickerMeetingDialog(Context context, TextInputEditText textInput){
        this.context = context;
        this.textInput = textInput;
    }

    public void setTextInput(TextInputEditText textInput){
        this.textInput = textInput;
    }

    /**
     * This method creates a new TimePickerMeetingDialog and show it
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        setRetainInstance(true);

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
        timePickerDialog = new TimePickerDialog(context, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                String timeToDisplay = "";

                // Format HH:MM
                timeToDisplay = DateAndTimeConverter.timeConverter(hour, minutes);

                // Update text input hour
                textInput.setText(timeToDisplay);
            }
        }, hourCalendarToSet, minutesCalendarToSet, true);
        return timePickerDialog;
    }

}
