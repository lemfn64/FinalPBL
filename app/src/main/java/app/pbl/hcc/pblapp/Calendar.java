package app.pbl.hcc.pblapp;

import android.content.Context;
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
        datePicker.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                eventDisplayer(year,month,dayOfMonth);
            }
        });
        list = (ListView) getView().findViewById(R.id.list);
        list.setAdapter( new Calendar.AdapterOrganizer(this.getContext()));
        java.util.Calendar timer = java.util.Calendar.getInstance();
        datePicker.setDate(timer.getTimeInMillis());
        eventDisplayer(timer.get(java.util.Calendar.YEAR), timer.get(java.util.Calendar.MONTH),timer.get(java.util.Calendar.DAY_OF_MONTH));
    }

    /**
     * displays events during the selected day in the calendar view
     * @param year
     * @param month
     * @param day
     */
    public void eventDisplayer(int year, int month, int day){
        int date = (year*365)+((month+1)*30)+(day);
        int datePlus = date;
        int dateMinus = date;
        int indicator=0;
        boolean found = false;
        for(Event choosen: events) {
            if(!found){
                if(choosen.getDate()==date) {
                    found= true;
                }
                indicator++;
            }
        }
        if(!found){
            indicator=0;
            for(Event choosen: events) {
                if(!found){
                    if(choosen.getDate()==datePlus) {
                        found= true;
                    }
                    else {
                        if(choosen.getDate()==dateMinus){
                            found = true;
                        }
                    }
                    datePlus++;
                    dateMinus--;
                    indicator++;
                }
            }
        }
        if(!found) {
            indicator=0;
        }
        if(!views.isEmpty()){
            views.get(indicator).requestFocus();
        }
    }

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
                            events.add(temp);
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
            summary.setText(events.get(position).getContent());
            ImageView chapter_image = (ImageView) row.findViewById(R.id.info_icon);
            if (events.get(position).getChapterCode()== MainMenu.userInfo.getChapterCode()) {
                chapter_image.setImageResource(android.R.drawable.star_big_on);
            }
            else {
                chapter_image.setImageResource(android.R.drawable.presence_online);
            }
            views.add(row);
            return null;
        }
    }

    class dateComparator implements Comparator<Event> {
        @Override
        public int compare(Event a, Event b) {
            return a.getDate()< b.getDate() ? -1 : a.getDate() == b.getDate() ? 0 : 1;
        }
    }
}