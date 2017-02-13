package app.pbl.hcc.pblapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * class to manage the calendar tab and its related methods
 */
public class Calendar extends Fragment {

    //variables use through the class
    private CalendarView datePicker;
    private ListView list;
    private DatabaseReference eventDatabase;
    private List<Event> events;
    private ArrayList<View> views;
    private boolean firstRun =true;
    private AdapterOrganizer adapter;
    private View.OnClickListener onClick;

    /**
     * creats view when called on main menu
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar, container, false);
        events= new ArrayList<Event>();
        views = new ArrayList<View>();
        return rootView;
    }

    /**
     * use to assign views to code after activity has loaded
     */
    @Override
    public void onResume() {
        super.onResume();
        datePicker = (CalendarView) getView().findViewById(R.id.calendarView);
        list = (ListView) getView().findViewById(R.id.list);
        list.setItemsCanFocus(true);
        onClick = new OnClick();
        adapter = new Calendar.AdapterOrganizer(this.getContext());
        list.setAdapter(adapter);
        java.util.Calendar timer = java.util.Calendar.getInstance();
        datePicker.setDate(timer.getTimeInMillis());
        eventDisplayer(timer.get(java.util.Calendar.YEAR), timer.get(java.util.Calendar.MONTH),timer.get(java.util.Calendar.DAY_OF_MONTH));
        datePicker.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Log.d("calendar","called");
                eventDisplayer(year,month,dayOfMonth);
            }
        });
    }

    /**
     * displays events during the selected day in the calendar view
     * @param year
     * @param month
     * @param day
     */
    public void eventDisplayer(int year, int month, int day){
        int date = (year*365)+((month+1)*30)+(day);
        int difference = 99999;
        int keeper=0;
        int indicator=0;
        for(Event choosen: events) {
            if(difference>Math.abs(choosen.getDate()-date)) {
                keeper=indicator;
                difference= Math.abs(choosen.getDate()-date);
                Log.d("calendar", String.valueOf(difference));
            }
            indicator++;
        }
        if(!views.isEmpty()){
            Log.d("calendar", "focus");
            //list.findViewById(views.get(keeper).getId()).requestFocus();
        }
    }

    /**
     * This class orgaices a list of objects into a scrolable list view
     */
    public class AdapterOrganizer extends BaseAdapter {

        Context context;

        public AdapterOrganizer(Context context) {
            eventDatabase = FirebaseDatabase.getInstance().getReference().child("events");
            this.context =context;
            ValueEventListener eventsListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot postsSnapshot) {
                    if(isAdded()) {
                        events = new ArrayList<Event>();
                        views = new ArrayList<View>();
                        for (DataSnapshot postSnapshot : postsSnapshot.getChildren()) {
                            Event temp = postSnapshot.getValue(Event.class);
                            if (temp.getChapterCode() == MainMenu.userInfo.getChapterCode() || temp.getChapterCode() == Integer.parseInt(getString(R.string.main_chapter))) {
                                events.add(temp);
                            }
                        }
                        Collections.sort(events, new dateComparator());
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting info failed
                    Log.d("get events", "loadPost:onCancelled", databaseError.toException());
                }
            };
            eventDatabase.addValueEventListener(eventsListener);
        }

        @Override
        public int getCount() {
            return events.size();
        }

        @Override
        public Object getItem(int position) {
            return events.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * sets informations on the view
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.single_event, parent, false);
            TextView title = (TextView) row.findViewById(R.id.txt_title);
            title.setText(events.get(position).getTitle());
            TextView place = (TextView) row.findViewById(R.id.txt_place);
            place.setText(events.get(position).getPlace());
            TextView time = (TextView) row.findViewById(R.id.txt_time);
            time.setText(events.get(position).getTime());
            TextView summary = (TextView) row.findViewById(R.id.txt_content);
            TextView date = (TextView) row.findViewById(R.id.date_txt);
            int numberDate = events.get(position).getDate();
            int year = numberDate/365;
            numberDate= numberDate%365;
            int month = numberDate/30;
            numberDate=numberDate%30;
            int days = numberDate;
            row.setTag(position);
            row.setId(position);
            row.setOnClickListener(onClick);
            date.setText(String.valueOf(month)+"/"+String.valueOf(days)+"/"+String.valueOf(year));
            summary.setText(events.get(position).getContent());
            ImageView chapter_image = (ImageView) row.findViewById(R.id.info_icon);
            if (events.get(position).getChapterCode()== MainMenu.userInfo.getChapterCode()) {
                chapter_image.setImageResource(android.R.drawable.star_big_on);
            }
            else {
                chapter_image.setImageResource(android.R.drawable.presence_online);
            }
            views.add(row);
            return row;
        }
    }

    /**
     * use to order events by date
     */
    class dateComparator implements Comparator<Event> {
        @Override
        public int compare(Event a, Event b) {
            return a.getDate()< b.getDate() ? -1 : a.getDate() == b.getDate() ? 0 : 1;
        }
    }

    /**
     * on click listener use to display event info
     */
    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            MainMenu.clickEvent=events.get((int)v.getTag());
            startActivity(new Intent(Calendar.this.getContext(), EventDisplayer.class));
        }

    }
}