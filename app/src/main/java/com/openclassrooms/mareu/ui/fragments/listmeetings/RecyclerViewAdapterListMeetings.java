package com.openclassrooms.mareu.ui.fragments.listmeetings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Adapter for @{@link ListMeetingsFragment} fragment
 */
public class RecyclerViewAdapterListMeetings extends RecyclerView.Adapter<RecyclerViewAdapterListMeetings.ViewHolderItemMeeting> {

    // Contains all Meeting to display
    private List<Meeting> listMeetings;
    private List<Meeting> listToDisplay;
    // For handling user actions
    private final ListMeetingActionListener listener;

    private final Context context;

    public RecyclerViewAdapterListMeetings(List<Meeting> listMeetings, ListMeetingActionListener listener, Context context) {
        this.listMeetings = listMeetings;
        this.listToDisplay = new ArrayList<>();
        this.listToDisplay.addAll(listMeetings);
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderItemMeeting onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_meetings_item, parent, false);
        return new ViewHolderItemMeeting(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O) // API 21
    @Override
    public void onBindViewHolder(@NonNull ViewHolderItemMeeting holder, int position) {
        // Icon Status Item

        // Get Meeting status
        int statusMeeting = compareDateMeetingToCurrentDate(listToDisplay.get(position).getDate(), listToDisplay.get(position).getHourStart(), listToDisplay.get(position).getHourEnd());

        // Get corresponding Drawable and update icon
        Drawable drawable = defineIconStatusMeeting(statusMeeting);
        holder.iconStatusItem.setImageDrawable(drawable);

        // Title Item : Object Meeting + Name Meeting room
        String title = "";
        if(listToDisplay.get(position).getObjectMeeting().length() < 17){
            title = listToDisplay.get(position).getObjectMeeting() + " (" +
                    listToDisplay.get(position).getMeetingRoom().toUpperCase() + ")";
        }
        else{
            title = listToDisplay.get(position).getObjectMeeting().substring(0,17) + "... ("
                    + listToDisplay.get(position).getMeetingRoom().toUpperCase() + ")";
        }
        holder.titleItem.setText(title);

        // Text Item : Date + Start hour + End hour
        String text = listToDisplay.get(position).getDate() + " - " + listToDisplay.get(position).getHourStart()
                        + " - " + listToDisplay.get(position).getHourEnd();
        holder.textItem.setText(text);

        // Subtext Item : email first Employee + "..." (if several Employee)
        String subText = "";
        if (listToDisplay.get(position).getListParticipants().size() > 1) {
            subText = listToDisplay.get(position).getListParticipants().get(0).getEmail() + "...";
        } else {
            subText = listToDisplay.get(position).getListParticipants().get(0).getEmail();
        }

        holder.subTextItem.setText(subText);

        // Icon Delete Item
        holder.iconDeleteItem.setOnClickListener((View view) -> {
                    listener.onDeleteItem(listToDisplay.get(position));
                    //listToDisplay.remove(position);
                }
        );
    }

    @Override
    public int getItemCount() {
        return listToDisplay.size();
    }

    static class ViewHolderItemMeeting extends RecyclerView.ViewHolder {

        private ImageView iconStatusItem;
        private TextView titleItem;
        private TextView textItem;
        private TextView subTextItem;
        private ImageView iconDeleteItem;

        ViewHolderItemMeeting(View view) {
            super(view);

            iconStatusItem = view.findViewById(R.id.img_item_recycler_view);
            titleItem = view.findViewById(R.id.title_item_recycler_view);
            textItem = view.findViewById(R.id.text_item_recycler_view);
            subTextItem = view.findViewById(R.id.subtext_item_recycler_view);
            iconDeleteItem = view.findViewById(R.id.icon_delete_item_recycler_view);
        }
    }

    /**
     * This method compares current Date & Time with Meeting Date & Time information
     *      - If current date&hour are before Meeting -> return 1
     *      - If current date&hour are during Meeting -> return 0
     *      - If current date&hour are after Meeting -> return -1 (Meeting ended)
     * @param date : String
     * @param hourStart : String
     * @param hourEnd : String
     * @return : int
     */
    public int compareDateMeetingToCurrentDate(String date, String hourStart, String hourEnd) {

        // Initialize date/hour format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");

        final Calendar calendar = Calendar.getInstance();
        // Current Date & Hour
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = calendar.get(Calendar.MINUTE);

        // Meeting
        // Date
        int meetingDay = Integer.parseInt(date.substring(0,2));
        int meetingMonth = Integer.parseInt(date.substring(3,5));
        int meetingYear = Integer.parseInt(date.substring(6));

        // Time Start (Hour + Minutes)
        String[] timeStart = hourStart.split(":");

        // Time End (Hour + Minutes)
        String[] timeEnd = hourEnd.split(":");

        try{
            Date currentDateTime = dateFormat.parse(currentDay + "/" +  currentMonth + "/" + currentYear + " " + currentHour + ":" + currentMinutes + ":00");
            Date startDateTime = dateFormat.parse(meetingDay + "/" + meetingMonth + "/" + meetingYear + " " + timeStart[0] + ":" + timeStart[1] + ":00");
            Date endDateTime = dateFormat.parse(meetingDay + "/" + meetingMonth + "/" + meetingYear + " " + timeEnd[0] + ":" + timeEnd[1] + ":00");

            if(currentDateTime.compareTo(startDateTime) < 0){ // current time < start meeting time
                return 1; // current Date before Start of meeting
            }
            else if (currentDateTime.compareTo(startDateTime) >=0 && currentDateTime.compareTo(endDateTime) < 0){
                return 0; // current Date during meeting
            }
            else{
                return -1; // current Date after End of meeting
            }

        } catch(ParseException exception){
            exception.printStackTrace();
        }

        return 0;
    }

    /**
     * This method returns the corresponding Drawable icon for a Meeting item, according to its status
     * @param status: int
     * @return : Drawable
     */
    public Drawable defineIconStatusMeeting(int status){

        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_lens_light_green_24dp, null);
        switch (status){
            case -1: // Meeting ended - Icon Red
                drawable = context.getResources().getDrawable(R.drawable.ic_baseline_lens_red_24dp, null);
                break;
            case 0: // Current Meeting - Icon Orange
                drawable = context.getResources().getDrawable(R.drawable.ic_baseline_lens_orange_24dp, null);
                break;
            case 1: // Planned Meeting - Icon Green
                drawable = context.getResources().getDrawable(R.drawable.ic_lens_light_green_24dp, null);
                break;
        }
        return drawable;
    }

    /**
     * Update current displayed list with new filtered list
     * @param newListMeetings : ArrayList<Meeting>
     */
    public void updateListMeetingToDisplay(final ArrayList<Meeting> newListMeetings){
        listToDisplay.clear();
        listToDisplay.addAll(newListMeetings);
        notifyDataSetChanged();
    }

    /**
     * Reset filters by restoring all Meeting item in listToDisplay
     */
    public void resetDisplayAfterFilterRemoved(){
        listToDisplay.clear();
        listToDisplay.addAll(listMeetings);
        notifyDataSetChanged();
    }

    /**
     * This method is called by Delete Dialog from ListMeetingsFragment to confirm
     * @param meeting
     */
    public void deleteMeetingInListDisplayed(Meeting meeting){
        listToDisplay.remove(meeting);
        notifyDataSetChanged();
    }
}