package app.pbl.hcc.pblapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Luis on 1/28/2017.
 */


public class Calendar extends Fragment {

    CalendarView datePicker;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar, container, false);
        return rootView;
    }

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
        java.util.Calendar timer = java.util.Calendar.getInstance();
        datePicker.setDate(timer.getTimeInMillis());
        eventDisplayer(timer.get(java.util.Calendar.YEAR), timer.get(java.util.Calendar.MONTH),timer.get(java.util.Calendar.DAY_OF_MONTH));
    }

    public void eventDisplayer(int year, int month, int day){
        String[] info= {String.valueOf(year), String.valueOf(day),String.valueOf(month+1)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(list.getContext(), android.R.layout.simple_list_item_1 ,info);
        list.setAdapter(adapter);

    }
}