package com.openclassrooms.mareu.ui;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public interface MainActivityCallback {
    void setToolbarTitle(@StringRes int title);
    void setToolbarTitle(String title);
    void changeFragment(Fragment fragment, String tag);
    void popBack();
}
