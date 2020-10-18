package com.openclassrooms.mareu.ui.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.fragments.AddMeetingFragment;
import com.openclassrooms.mareu.utils.DateAndTimeConverter;
import com.openclassrooms.mareu.utils.TimeComparator;
import java.util.Calendar;
import java.util.Objects;

/**
 * Class used to display a TimePickerDialog to allow user to choose
 * a meeting hour
 * Used in @{@link com.openclassrooms.mareu.ui.fragments.AddMeetingFragment} fragment
 */
public class TimePickerMeetingDialog extends DialogFragment {

    private Context context;
    private TextInputEditText textStartHourInput;
    private TextInputEditText textEndHourInput;
    private TextInputLayout textEndHourLayout;
    private TimePickerDialog timePickerDialog;
    private String TAG_TYPE;
    private int resultTimeComparator;

    public TimePickerMeetingDialog(){ }

    public TimePickerMeetingDialog(Context context, TextInputEditText textStartHourInput,
                                   TextInputEditText textEndHourInput, String TAG_TYPE,
                                   TextInputLayout textEndHourLayout){
        this.context = context;
        this.textStartHourInput = textStartHourInput;
        this.textEndHourInput = textEndHourInput;
        this.TAG_TYPE = TAG_TYPE;
        this.resultTimeComparator = 0;
        this.textEndHourLayout = textEndHourLayout;
    }

    public void setTextInputs(TextInputEditText textStartHourInput, TextInputEditText textEndHourInput, TextInputLayout textEndHourLayout){
        this.textStartHourInput = textStartHourInput;
        this.textEndHourInput = textEndHourInput;
        this.textEndHourLayout = textEndHourLayout;
    }

    /**
     * This method creates a new TimePickerMeetingDialog and show it
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Retain current DialogFragment instance
        setRetainInstance(true);

        int hourCalendarToSet;
        int minutesCalendarToSet;

        // Initialize hour data to display
        if(TAG_TYPE.equals("START_HOUR")){
            if(!Objects.requireNonNull(textStartHourInput.getText()).toString().equals("")){
                // If an hour has already been selected
                String time = textStartHourInput.getText().toString();
                hourCalendarToSet = Integer.parseInt(time.substring(0,2));
                minutesCalendarToSet = Integer.parseInt(time.substring(3));
            }
            else{
                // If first display of the Dialog, no hour selected yet
                final Calendar calendar = Calendar.getInstance();
                hourCalendarToSet = calendar.get(Calendar.HOUR_OF_DAY);
                minutesCalendarToSet = calendar.get(Calendar.MINUTE);
            }
        }
        else{ // "END_HOUR"
            if(!Objects.requireNonNull(textEndHourInput.getText()).toString().equals("")){
                // If an hour has already been selected
                String time = textEndHourInput.getText().toString();
                hourCalendarToSet = Integer.parseInt(time.substring(0,2));
                minutesCalendarToSet = Integer.parseInt(time.substring(3));
            }
            else{
                // If first display of the Dialog, no hour selected yet
                final Calendar calendar = Calendar.getInstance();
                hourCalendarToSet = calendar.get(Calendar.HOUR_OF_DAY);
                minutesCalendarToSet = calendar.get(Calendar.MINUTE);
            }
        }


        // Create TimePickerDialog instance
        timePickerDialog = new TimePickerDialog(context, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                String timeToDisplay = "";

                // Format HH:MM
                timeToDisplay = DateAndTimeConverter.timeConverter(hour, minutes);

                // Update text input hour
                if(TAG_TYPE.equals("START_HOUR")){
                    updateTextInput(textStartHourInput, timeToDisplay);
                }
                else{ // "END_HOUR"
                    updateTextInput(textEndHourInput, timeToDisplay);
                }

                // Compare text inputs fields
                compareHourInputsFields();
            }
        }, hourCalendarToSet, minutesCalendarToSet, true);
        return timePickerDialog;
    }

    private void updateTextInput(TextInputEditText textInput, String timeToDisplay){
        // Update text input hour
        textInput.setText(timeToDisplay);
    }

    private void compareHourInputsFields(){
        if(textStartHourInput.getText().length() > 0 && textEndHourInput.getText().length() > 0){
            TimeComparator timeComparator = new TimeComparator();
            resultTimeComparator = timeComparator.compare(textStartHourInput.getText().toString(), textEndHourInput.getText().toString());


            if(resultTimeComparator < 0){
                textEndHourLayout.setErrorEnabled(true);
                textEndHourLayout.setError(getResources().getString(R.string.error_msg));
            }
            else{
                textEndHourLayout.setErrorEnabled(false);
            }
        }
    }
}
