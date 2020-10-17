package com.openclassrooms.mareu.utils;

import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

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
 *          - Start hour of the Meeting
 *          - End hour of the Meeting
 *          - List of participants
 *
 * Field "Information" from AddMeetingFragment is optional
 */
public class TextWatcherTextInput implements TextWatcher {

    private TextInputEditText firstTextInput;
    private TextInputEditText secondTextInput;
    private TextInputEditText thirdTextInput;
    private TextInputEditText fourthTextInput;
    private TextInputEditText fifthTextInput;
    private MaterialButton button;

    public TextWatcherTextInput(MaterialButton button, TextInputEditText firstTextInput,
                                TextInputEditText secondTextInput, TextInputEditText thirdTextInput,
                                TextInputEditText fourthTextInput, TextInputEditText fifthTextInput){
        this.button = button;
        this.firstTextInput = firstTextInput;
        this.secondTextInput = secondTextInput;
        this.thirdTextInput = thirdTextInput;
        this.fourthTextInput = fourthTextInput;
        this.fifthTextInput = fifthTextInput;
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
                && Objects.requireNonNull(firstTextInput.getText()).length() > 0
                && Objects.requireNonNull(secondTextInput.getText()).length() > 0
                && Objects.requireNonNull(thirdTextInput.getText()).length() > 0
                && Objects.requireNonNull(fourthTextInput.getText()).length() > 0
                && Objects.requireNonNull(fifthTextInput.getText()).length() > 0){
            button.setEnabled(true);
        }
        else{
            button.setEnabled(false);
        }
    }
}
