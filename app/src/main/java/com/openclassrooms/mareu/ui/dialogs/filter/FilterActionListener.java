package com.openclassrooms.mareu.ui.dialogs.filter;

public interface FilterActionListener {

    // Room Filter actions
    void actionChangeFilterRoom(int position);
    void validFilterRoom();
    void restorePreviousSelection();

    // Date filter actions
    void validFilterDateOption1(String dateFilter);
    void validFilterDateOption2(String startDateFilter, String endDateFilter);
}
