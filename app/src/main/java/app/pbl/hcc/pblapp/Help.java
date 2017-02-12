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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Help extends AppCompatActivity {
    private EditText descriptions;
    private Button addReport;
    private DatabaseReference reportsDatabase;
    private RatingBar stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        reportsDatabase = FirebaseDatabase.getInstance().getReference().child("reports");
    }

    @Override
    public void onResume() {
        super.onResume();
        descriptions = (EditText) findViewById(R.id.problem_description);
        addReport = (Button) findViewById(R.id.summit_report);
        stars = (RatingBar) findViewById(R.id.ratingBar);
        stars.setNumStars(0);
        addReport.setOnClickListener(new Help.OnClick());
    }

    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(descriptions.getText().toString().isEmpty()) {
                descriptions.setError(getString(R.string.error_field_required));
            }
            else {
                Report temp = new Report(stars.getNumStars(),MainMenu.userInfo.getEmail(), descriptions.getText().toString(), MainMenu.userInfo.getName());
                reportsDatabase.child(MainMenu.userInfo.getEmail().replace(".", "@")+ System.currentTimeMillis()).setValue(temp);
                finish();
            }

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
