package app.pbl.hcc.pblapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * class use to create events
 */
public class EventUI extends AppCompatActivity {

    private EditText title;
    private EditText author;
    private EditText place;
    private EditText time;
    private DatePicker date;
    private EditText description;
    private Button create;
    private DatabaseReference eventDatabase;

    /**
     * sets xml file for activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_ui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    /**
     * link gui elements to code
     */
    @Override
    public void onResume() {
        super.onResume();
        title = (EditText) findViewById(R.id.event_title);
        author = (EditText) findViewById(R.id.event_author);
        place = (EditText) findViewById(R.id.event_place);
        time = (EditText)findViewById(R.id.event_time);
        date = (DatePicker)findViewById(R.id.datePicker);
        description = (EditText)findViewById(R.id.event_description);
        create = (Button) findViewById(R.id.create_event);
        create.setOnClickListener(new OnClick());
        eventDatabase = FirebaseDatabase.getInstance().getReference().child("events");
    }

    /**
     * finishes activity when back key is pressed
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

    /**
     * checks for empty fields and uploads info to data base
     */
    private class OnClick implements View.OnClickListener{
        private boolean error;

        @Override
        public void onClick(View v) {
            Event temp= new Event();
            error = false;
            if(title.getText().toString().isEmpty()) {
                title.setError(getString(R.string.error_field_required));
                title.requestFocus();
                error = true;
            } else {
                temp.setTitle(title.getText().toString());
            }
            if(author.getText().toString().isEmpty()) {
                author.setError(getString(R.string.error_field_required));
                author.requestFocus();
                error = true;
            } else {
                temp.setAuthor(author.getText().toString());
            }
            if (time.getText().toString().isEmpty()) {
                time.setError(getString(R.string.error_field_required));
                time.requestFocus();
                error = true;
            } else {
                temp.setTime(time.getText().toString());
            }

            if (place.getText().toString().isEmpty()){
                place.setError(getString(R.string.error_field_required));
                place.requestFocus();
                error = true;
            } else {
                temp.setPlace(place.getText().toString());
            }
            if (description.getText().toString().isEmpty()) {
                description.setError(getString(R.string.error_field_required));
                description.requestFocus();
                error = true;
            } else {
                temp.setContent(description.getText().toString());
            }

            if(!error) {
                temp.setDate((date.getYear()*365)+((date.getMonth()+1)*30)+(date.getDayOfMonth()));
                temp.setChapterCode(MainMenu.userInfo.getChapterCode());
                eventDatabase.child(String.valueOf(temp.getChapterCode())+System.currentTimeMillis()).setValue(temp);
                finish();
            }


        }
    }
}
