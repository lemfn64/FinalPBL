package app.pbl.hcc.pblapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * display the info of a event
 */
public class EventDisplayer extends AppCompatActivity {

    private TextView author;
    private TextView title;
    private TextView date;
    private TextView time;
    private TextView place;
    private TextView description;
    private  Event event;

    /**
     * sets xml file for activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_displayer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * set code reference for gui elements and date
     */
    @Override
    public void onResume() {
        super.onResume();
        event= MainMenu.clickEvent;
        author = (TextView) findViewById(R.id.show_author);
        title = (TextView) findViewById(R.id.show_title);
        date = (TextView) findViewById(R.id.show_date);
        time = (TextView) findViewById(R.id.show_time);
        place = (TextView) findViewById(R.id.show_place);
        description = (TextView) findViewById(R.id.show_description);
        author.setText(event.getAuthor());
        title.setText(event.getTitle());
        time.setText(event.getTime());
        place.setText(event.getPlace());
        description.setText(event.getContent());
        int numberDate = event.getDate();
        int year = numberDate/365;
        numberDate= numberDate%365;
        int month = numberDate/30;
        numberDate=numberDate%30;
        int days = numberDate;
        date.setText(String.valueOf(month)+"/"+String.valueOf(days)+"/"+String.valueOf(year));
    }

    /**
     * finishes activity on back key press
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
