package com.openclassrooms.mareu.testutils;

import android.view.View;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import com.openclassrooms.mareu.R;
import org.hamcrest.Matcher;

public class DeleteViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {

        View button = view.findViewById(R.id.icon_delete_item_recycler_view);

        if(button != null){
            button.performClick();
        }
    }
}

