package app.pbl.hcc.pblapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreatAnnouncements extends AppCompatActivity {

    private EditText title;
    private  EditText author;
    private EditText summary;
    private EditText content;
    private Button create;
    private DatabaseReference postsDatabase;
    private onClick click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_announcements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        postsDatabase = FirebaseDatabase.getInstance().getReference().child("posts");

    }

    @Override
    public void onResume() {
        super.onResume();
        title = (EditText) findViewById(R.id.update_title);
        author = (EditText) findViewById(R.id.update_author);
        summary = (EditText) findViewById(R.id.update_abstract);
        content = (EditText) findViewById(R.id.update_content);
        create = (Button) findViewById(R.id.create_announcement);
        click = new onClick();
        create.setOnClickListener(click);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class onClick implements View.OnClickListener {

        public onClick() {
        }

        @Override
        public void onClick(View v) {
            if(title.getText().toString().isEmpty()){
                title.setError(getString(R.string.error_field_required));
                title.requestFocus();
            } else  if(author.getText().toString().isEmpty()){
                author.setError(getString(R.string.error_field_required));
                author.requestFocus();
            } else  if(summary.getText().toString().isEmpty()){
                summary.setError(getString(R.string.error_field_required));
                summary.requestFocus();
            } else  if(content.getText().toString().isEmpty()){
                content.setError(getString(R.string.error_field_required));
                content.requestFocus();
            } else {
                Post announcement = new Post();
                announcement.setAbstaract(summary.getText().toString());
                announcement.setAuthor(author.getText().toString());
                announcement.setChapterCode(MainMenu.userInfo.getChapterCode());
                announcement.setTitle(title.getText().toString());
                announcement.setContent(content.getText().toString());
                postsDatabase.child(String.valueOf(announcement.getChapterCode())+System.currentTimeMillis()).setValue(announcement);
                finish();
            }

        }
    }
}
