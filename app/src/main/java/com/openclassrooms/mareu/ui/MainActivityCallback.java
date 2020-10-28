package com.openclassrooms.mareu.ui;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

/**
 * This interface allows fragments to access several methods to handle :
 *      - Toolbar title & icon updates
 *      - fragment transactions in back stack
 * Implemented in @{@link MainActivity}
 */
public interface MainActivityCallback {

    // Toolbar handler methods
    void setToolbarTitle(@StringRes int title);
    void setToolbarTitle(String title);
    void setHomeAsUpIndicator(boolean status);
    void updateHomeAsUpIndicator(@DrawableRes int icon);

    // Back stack handler methods
    void changeFragment(Fragment fragment, String tag);
    void popBack();

}
