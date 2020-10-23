package com.openclassrooms.mareu.ui.dialogs.filter;

public interface FilterActionListener {

    // Room Filter actions
    void actionChangeFilterRoom(int position);
    void validFilterRoom();
    void restorePreviousSelection();

    // Date filter actions
    boolean validFilterDateOption1(String dateFilter);
    boolean validFilterDateOption2(String startDateFilter, String endDateFilter);
}
