package app.pbl.hcc.pblapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import java.util.List;

/**
 * displays posts info
 */
public class PostDisplayer extends AppCompatActivity {
    private Button addComments;
    private ListView list;
    private TextView author;
    private TextView title;
    private TextView content;
    private DatabaseReference commentsDatabase;
    private ArrayList<Comment> comments;
    private CommentsList commentsList;
    private PostDisplayer.AdapterOrganizer adapter;

    /**
     * sets xml file
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_displayer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        comments= new ArrayList<Comment>();
    }

    /**
     * sets gui elements in code and gets initial coment list
     */
    @Override
    public void onResume() {
        super.onResume();
        addComments= (Button) findViewById(R.id.add_comment);
        list = (ListView) findViewById(R.id.list_comments);
        author= (TextView) findViewById(R.id.show_author);
        author.setText(MainMenu.clickPost.getAuthor());
        title = (TextView) findViewById(R.id.show_title);
        title.setText(MainMenu.clickPost.getTitle());
        content = (TextView) findViewById(R.id.show_description);
        content.setText(MainMenu.clickPost.getContent());
        addComments.setOnClickListener(new OnClick());
        commentsDatabase = FirebaseDatabase.getInstance().getReference().child("comments");
        ValueEventListener commentsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot postsSnapshot) {
                comments = new ArrayList<Comment>();
                Log.d("comments", "entered");
                for (DataSnapshot postSnapshot : postsSnapshot.getChildren()) {
                    Log.d("comments", "scaning");
                    if (postSnapshot.getKey().equals(MainMenu.clickPostReference)) {
                        Log.d("comments", "found comments list");
                        CommentsList set = postSnapshot.getValue(CommentsList.class);
                        comments= set.getList();
                    }
                }
                adapter.update();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting info failed
                Log.d("get comments", "loadPost:onCancelled", databaseError.toException());
            }
        };
        commentsDatabase.addListenerForSingleValueEvent(commentsListener);
        adapter = new PostDisplayer.AdapterOrganizer(this.getApplicationContext());
        list.setAdapter(adapter);
    }

    /**
     * click for button to add comment activity
     */
    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(PostDisplayer.this.getApplicationContext(), AddComment.class));
        }

    }

    /**
     * finishes activity on key press
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

    public class AdapterOrganizer extends BaseAdapter {

        Context context;

        public AdapterOrganizer(Context context) {
            this.context =context;
        }

        @Override
        public int getCount() {
            Log.d("comments", String.valueOf(comments.size()));
            return comments.size();
        }

        @Override
        public Object getItem(int position) {
            return comments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("comments", "found comment");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.single_comment, parent, false);
            TextView author = (TextView) row.findViewById(R.id.show_author);
            TextView commentTxt = (TextView) row.findViewById(R.id.show_comment);
            ImageView msg = (ImageView) row.findViewById(R.id.msg);
            msg.setImageResource(android.R.drawable.stat_notify_chat);
            author.setText(comments.get(position).getAuthor());
            commentTxt.setText(comments.get(position).getMessage());
            return row;
        }

        public void update() {
            notifyDataSetChanged();
        }
    }
}
