package app.pbl.hcc.pblapp;

import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddComment extends AppCompatActivity {

    private EditText text;
    private Button add;
    private CommentsList set;
    private DatabaseReference commentsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        commentsDatabase = FirebaseDatabase.getInstance().getReference().child("comments");
    }

    @Override
    public void onResume() {
        super.onResume();
        text = (EditText) findViewById(R.id.update_content);
        add = (Button) findViewById(R.id.create_comment);
        ValueEventListener commentsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot postsSnapshot) {
                for (DataSnapshot postSnapshot : postsSnapshot.getChildren()) {
                    if (postSnapshot.getKey().equals(MainMenu.clickPostReference)) {
                        set = postSnapshot.getValue(CommentsList.class);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting info failed
                Log.d("get comments", "loadPost:onCancelled", databaseError.toException());
            }
        };
        commentsDatabase.addListenerForSingleValueEvent(commentsListener);
        add.setOnClickListener(new OnClick());
    }

    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(text.getText().toString().isEmpty()) {
                text.setError(getString(R.string.error_field_required));
            }
            else {
                Comment temp = new Comment(text.getText().toString(), MainMenu.userInfo.getName());
                if(set == null) {
                    set = new CommentsList();
                }
                set.getList().add(temp);
                commentsDatabase.child(MainMenu.clickPostReference).setValue(set);
                finish();
            }
        }

    }

    /**
     *
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
