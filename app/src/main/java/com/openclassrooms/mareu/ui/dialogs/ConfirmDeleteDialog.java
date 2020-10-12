package com.openclassrooms.mareu.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.ui.fragments.listmeetings.RecyclerViewAdapterListMeetings;

import java.util.List;

/**
 * This Dialog is displayed every time the user clicks on a "Delete" icon of the
 * displayed meetings list.
 * Used in @{@link com.openclassrooms.mareu.ui.fragments.listmeetings.ListMeetingsFragment} fragment
 */
public class ConfirmDeleteDialog extends DialogFragment {

    private List<Meeting> listMeetings;
    private int position;
    private RecyclerViewAdapterListMeetings adapter;

    public ConfirmDeleteDialog(){ }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configure builder
        builder.setTitle(R.string.title_dialog_confirm_delete)
                .setMessage(R.string.text_dialog_confirm_delete)
                .setPositiveButton(R.string.btn_yes_dialog_confirm_delete, (DialogInterface dialogInterface, int i) -> {
                        // TODO() : to define, must specify that the selected item can be removed from list
                    }
                )
                .setNegativeButton(R.string.btn_no_dialog_confirm_delete, (DialogInterface dialogInterface, int i) -> {
                        // Close Dialog
                    }
                );

        return builder.create();
    }
}
