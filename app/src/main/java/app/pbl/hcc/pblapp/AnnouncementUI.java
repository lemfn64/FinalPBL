package app.pbl.hcc.pblapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Luis on 1/28/2017.
 */

public class AnnouncementUI extends Fragment {

    private ListView list;
    private DatabaseReference postsDatabase;
    private ArrayList<Post> posts = new ArrayList<Post>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.announcement, container, false);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        list= (ListView) getView().findViewById(R.id.list_view);
        list.setAdapter(new AdapterOrganizer(this.getContext()));
    }


    public class AdapterOrganizer extends BaseAdapter {

        Context context;

        public AdapterOrganizer(Context context) {
            postsDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
            this.context =context;
            ValueEventListener postsListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot postsSnapshot) {
                        posts = new ArrayList<Post>();
                        for (DataSnapshot postSnapshot : postsSnapshot.getChildren()) {
                            Post temp = postSnapshot.getValue(Post.class);
                            if (temp.getChapterCode() == MainMenu.userInfo.getChapterCode() || temp.getChapterCode() == Integer.parseInt(getString(R.string.main_chapter))) {
                                posts.add(temp);
                            }
                            notifyDataSetChanged();
                        }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting info failed
                    Log.d("get posts", "loadPost:onCancelled", databaseError.toException());
                }
            };
            postsDatabase.addListenerForSingleValueEvent(postsListener);
        }

        @Override
        public int getCount() {
            if (!posts.isEmpty()) {
                return posts.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return posts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.single_post, parent, false);
            TextView title = (TextView) row.findViewById(R.id.txt_title);
            title.setText(posts.get(position).getTitle());
            TextView author = (TextView) row.findViewById(R.id.txt_author);
            author.setText(posts.get(position).getAuthor());
            TextView summary = (TextView) row.findViewById(R.id.txt_content);
            summary.setText(posts.get(position).getAbstaract());
            ImageView chapter_image = (ImageView) row.findViewById(R.id.info_icon);
            if (posts.get(position).getChapterCode()== MainMenu.userInfo.getChapterCode()) {
                chapter_image.setImageResource(android.R.drawable.star_big_on);
            }
            else {
                chapter_image.setImageResource(android.R.drawable.presence_online);
            }
            return row;
        }
    }
}
