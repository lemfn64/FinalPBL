package app.pbl.hcc.pblapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {
    private EditText newPosition;
    private  EditText newChapter;
    private EditText newChapterPosition;
    private Button updatePosition;
    private Button updateChapter;
    private DatabaseReference chapterDatabase;
    private DatabaseReference userDatabase;
    private Chapter tempChapter;
    private User tempUser;
    private String userReference;
    private String chapterReference;
    private int position;
    private int chapterCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        chapterDatabase = FirebaseDatabase.getInstance().getReference().child("chapters");
    }

    @Override
    public void onResume() {
        super.onResume();
        newPosition = (EditText) findViewById(R.id.update_position);
        newChapter = (EditText) findViewById(R.id.update_chapter);
        newChapterPosition = ( EditText) findViewById(R.id.update_chapter_position);
        updatePosition = (Button) findViewById(R.id.update_pos);
        updateChapter = (Button) findViewById(R.id.update_chap);
        OnClick click = new OnClick();
        updateChapter.setOnClickListener(click);
        updatePosition.setOnClickListener(click);
    }

    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.update_pos){
                ValueEventListener chapterListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot postsSnapshot) {
                        for (DataSnapshot postSnapshot : postsSnapshot.getChildren()) {
                            if(postSnapshot.getKey().equals(MainMenu.userInfo.getChapterCode())) {
                                tempChapter=(Chapter) postSnapshot.getValue();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting info failed
                        Log.d("get comments", "loadPost:onCancelled", databaseError.toException());
                    }
                };
                if(Integer.parseInt(updatePosition.getText().toString())== tempChapter.getPresidentCode() || Integer.parseInt(updatePosition.getText().toString())== tempChapter.getVicePresidentCode()) {

                }
                ValueEventListener userListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot postsSnapshot) {
                        for (DataSnapshot postSnapshot : postsSnapshot.getChildren()) {
                                if(postSnapshot.getKey().equals(MainMenu.userInfo.getEmail().replace(".", "@"))) {
                                    tempUser=(User) postSnapshot.getValue();
                                }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting info failed
                        Log.d("get comments", "loadPost:onCancelled", databaseError.toException());
                    }
                };
                userDatabase.addListenerForSingleValueEvent(userListener);
                tempUser.setPosition(position);
            }
        }
    }
}

