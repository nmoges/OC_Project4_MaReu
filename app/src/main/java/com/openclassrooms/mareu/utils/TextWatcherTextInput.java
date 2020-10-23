package com.openclassrooms.mareu.utils;

import android.text.Editable;
import android.text.TextWatcher;
import com.openclassrooms.mareu.ui.dialogs.inputtexts.InputTextChangeCallback;

/**
 * This class inherits of the TextWatcher class to check any change detected in the associated
 * TextInputEditText.
 * If a change is detected, the interface InputTextChangeCallback is called to perform action in AddMeetingFragment
 */
public class TextWatcherTextInput implements TextWatcher {

    private InputTextChangeCallback callback;

    public TextWatcherTextInput(InputTextChangeCallback callback){
        this.callback = callback;
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

        if(editable.length() > 0){
            callback.onCheckInputsTextStatus();
        }
    }
}
