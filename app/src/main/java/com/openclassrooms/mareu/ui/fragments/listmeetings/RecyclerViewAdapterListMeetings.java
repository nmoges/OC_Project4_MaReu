package com.openclassrooms.mareu.ui.fragments.listmeetings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;

import java.util.List;

/**
 * Adapter for ListMeetingsFragment
 */
public class RecyclerViewAdapterListMeetings extends RecyclerView.Adapter<RecyclerViewAdapterListMeetings.ViewHolderItemMeeting>{

    private List<Meeting> listMeetings;

    public RecyclerViewAdapterListMeetings(List<Meeting> listMeetings){
        this.listMeetings = listMeetings;
    }

    @NonNull
    @Override
    public ViewHolderItemMeeting onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_meetings_item, parent, false);
        return new ViewHolderItemMeeting(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderItemMeeting holder, int position) {

        // Icon Status Item
        // TODO()

        // Title Item
        String title = listMeetings.get(position).getObjectMeeting();
        holder.titleItem.setText(title);

        // Text Item
        String text = listMeetings.get(position).getDate() + " - " + listMeetings.get(position).getHour() + " - " + listMeetings.get(position).getMeetingRoom();
        holder.textItem.setText(text);

        // Subtext Item
        String subText = listMeetings.get(position).getListParticipants().get(0).getEmail() + "...";
        holder.subTextItem.setText(subText);

        // Icon Delete Item
        holder.iconDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listMeetings.size();
    }

    static class ViewHolderItemMeeting extends RecyclerView.ViewHolder {

        private ImageView iconStatusItem;
        private TextView titleItem;
        private TextView textItem;
        private TextView subTextItem;
        private ImageView iconDeleteItem;

        ViewHolderItemMeeting(View view){
            super(view);

            iconStatusItem = view.findViewById(R.id.img_item_recycler_view);
            titleItem = view.findViewById(R.id.title_item_recycler_view);
            textItem = view.findViewById(R.id.text_item_recycler_view);
            subTextItem = view.findViewById(R.id.subtext_item_recycler_view);
            iconDeleteItem = view.findViewById(R.id.icon_delete_item_recycler_view);
        }
    }

}
