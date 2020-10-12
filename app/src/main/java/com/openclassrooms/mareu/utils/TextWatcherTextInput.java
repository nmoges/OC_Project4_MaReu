package com.openclassrooms.mareu.utils;

import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * This class extends the TextWatcher class and is used to detect if all
 * mandatory fields (TextInputEditText) are specified by user.
 * If yes, the MaterialButton (Ok button) can be enabled to create a new Meeting
 * If not, the MaterialButton remains disabled
 *
 * Minimal information to specify by user to enable new Meeting validation :
 *          - Object Meeting
 *          - Room Meeting
 *          - Date of the Meeting
 *          - Hour of the Meeting
 *          - List of participants
 *
 * Field "Information" from AddMeetingFragment is optional
 */
public class TextWatcherTextInput implements TextWatcher {

    private TextInputEditText firstTextInput;
    private TextInputEditText secondTextInput;
    private TextInputEditText thirdTextInput;
    private TextInputEditText fourthTextInput;
    private MaterialButton button;

    public TextWatcherTextInput(MaterialButton button, TextInputEditText firstTextInput,
                                TextInputEditText secondTextInput, TextInputEditText thirdTextInput,
                                TextInputEditText fourthTextInput){
        this.button = button;
        this.firstTextInput = firstTextInput;
        this.secondTextInput = secondTextInput;
        this.thirdTextInput = thirdTextInput;
        this.fourthTextInput = fourthTextInput;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // NOT NEEDED
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // NOT NEEDED
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // If all mandatory fields are specified, button can be enabled
        if(editable.length() > 0
                && firstTextInput.getText().length() > 0
                && secondTextInput.getText().length() > 0
                && thirdTextInput.getText().length() > 0
                && fourthTextInput.getText().length() > 0){
            button.setEnabled(true);
        }
        else{
            button.setEnabled(false);
        }
    }
}
