package com.openclassrooms.mareu.ui.fragments.listmeetings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.utils.DateAndTimeConverter;
import java.util.Calendar;
import java.util.List;

/**
 * Adapter for @{@link ListMeetingsFragment} fragment
 */
public class RecyclerViewAdapterListMeetings extends RecyclerView.Adapter<RecyclerViewAdapterListMeetings.ViewHolderItemMeeting> {

    // Contains all Meeting to display
    private List<Meeting> listMeetings;

    // For handling user actions
    private final ListMeetingActionListener listener;

    private final Context context;

    public RecyclerViewAdapterListMeetings(List<Meeting> listMeetings, ListMeetingActionListener listener, Context context) {
        this.listMeetings = listMeetings;
        this.listener = listener;
        this.context = context;
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

        // Get Meeting status
        int statusMeeting = compareDateMeetingToCurrentDate(listMeetings.get(position).getDate(), listMeetings.get(position).getHourStart(), listMeetings.get(position).getHourEnd());
        // Get corresponding Drawable and update icon
        Drawable drawable = defineIconStatusMeeting(statusMeeting);
        holder.iconStatusItem.setImageDrawable(drawable);

        // Title Item : Object Meeting + Name Meeting room
        String title = "";
        if(listMeetings.get(position).getObjectMeeting().length() < 17){
            title = listMeetings.get(position).getObjectMeeting() + " (" +
                    listMeetings.get(position).getMeetingRoom().toUpperCase() + ")";
        }
        else{
            title = listMeetings.get(position).getObjectMeeting().substring(0,17) + "... ("
                    + listMeetings.get(position).getMeetingRoom().toUpperCase() + ")";
        }
        holder.titleItem.setText(title);

        // Text Item : Date + Start hour + End hour
        String text = listMeetings.get(position).getDate() + " - " + listMeetings.get(position).getHourStart()
                        + " - " + listMeetings.get(position).getHourEnd();
        holder.textItem.setText(text);

        // Subtext Item : email first Employee + "..." (if several Employee)
        String subText = "";
        if (listMeetings.get(position).getListParticipants().size() > 1) {
            subText = listMeetings.get(position).getListParticipants().get(0).getEmail() + "...";
        } else {
            subText = listMeetings.get(position).getListParticipants().get(0).getEmail();
        }

        holder.subTextItem.setText(subText);

        // Icon Delete Item
        holder.iconDeleteItem.setOnClickListener((View view) -> {
                    listener.onDeleteItem(listMeetings.get(position));
                }
        );
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
     * This method compares current Date with Meeting Date information
     * If current Date and Meeting date are the same, then compareTimeMeetingToCurrentTime() method is called
     * to compare hours.
     *      - If current date&hour are before Meeting -> return 1
     *      - If current date&hour are during Meeting -> return 0
     *      - If current date&hour are after Meeting -> return -1 (Meeting ended)
     * @param date : String
     * @param hourStart : String
     * @param hourEnd : String
     * @return : int
     */

    public int compareDateMeetingToCurrentDate(String date, String hourStart, String hourEnd) {
        final Calendar calendar = Calendar.getInstance();

        // Current Date
        String formatYear = Integer.toString(calendar.get(Calendar.YEAR)).substring(2);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = Integer.parseInt(formatYear);//calendar.get(Calendar.YEAR);

        // Date Meeting
        int meetingDay = Integer.parseInt(date.substring(0,2));
        int meetingMonth = Integer.parseInt(date.substring(3,5));
        int meetingYear = Integer.parseInt(date.substring(6));

        // Compare Year
        if(meetingYear < currentYear){
            return -1; }
        else if(meetingYear > currentYear) {
            return 1; }
        else{
            // Compare Month
            if(meetingMonth < currentMonth){ return -1; }
            else if(meetingMonth > currentMonth) { return 1; }
            else{
                // Compare Day
                if(meetingDay < currentDay){ return -1; }
                else if(meetingDay > currentDay) { return 1; }
                else{
                    return compareTimeMeetingToCurrentTime(calendar, hourStart, hourEnd);
                }
            }
        }
    }

    /**
     * This method compares current time with "start hour" and "end hour" of a Meeting.
     * This method is only called if comparing Date is not enough.
     *      - If Current hour is before Meeting hour -> return 1
     *      - if Current hour is during Meeting hour -> return 0
     *      - If Current hour is after Meeting jour -> return -1 (Meeting ended)
     * @param calendar : Calendar
     * @param hourStart : String
     * @param hourEnd : String
     * @return : int
     */
    public int compareTimeMeetingToCurrentTime(Calendar calendar, String hourStart, String hourEnd){
        // Current Time
        String currentTime = DateAndTimeConverter.timeConverter(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        int currentHour = Integer.parseInt(currentTime.substring(0,2));
        int currentMinutes = Integer.parseInt(currentTime.substring(3));

        // Start Hour Meeting
        int startHour = Integer.parseInt(hourStart.substring(0,2));
        int startMinutes = Integer.parseInt(hourStart.substring(3));

        // End Hour Meeting
        int endHour = Integer.parseInt(hourEnd.substring(0,2));
        int endMinutes = Integer.parseInt(hourEnd.substring(3));

        if(startHour == endHour){
            if(currentHour < startHour){return 1;}
            else if(currentHour > startHour){return -1;} // (equivalent : current > endHour)
            else{
                if(currentMinutes < startMinutes){return 1;}
                else if(currentMinutes > endMinutes){return -1;}
                else{return 0;}
            }
        }
        else{ // startHour < endHour
            if(currentHour < startHour){return  1;}
            else if(currentHour > endHour){return -1;}
            else{
                if(currentHour == startHour){
                    if(currentMinutes < startMinutes){return 1;}
                    else {return 0;} // currentMinuter >= startMinutes
                }
                else if(currentHour == endHour){
                    if(currentMinutes < endMinutes){return 0;}
                    else {return -1;} // currentMinutes >= endMinutes
                }
                else{
                    return 0;
                }
            }
        }
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
}