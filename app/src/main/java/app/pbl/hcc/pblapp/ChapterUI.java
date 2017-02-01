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
import java.util.List;

/**
 * Created by Luis on 1/28/2017.
 */

public class ChapterUI extends Fragment {
    private TextView president;
    private TextView school;
    private TextView vice;
    private TextView secretary;
    private TextView treasurer;
    private TextView advisor;
    private TextView title;
    private ListView modificationsList;
    private ArrayList<String> positions;
    private ArrayList<String> modifications;
    private DatabaseReference chapterDatabase;
    private DatabaseReference userDatabase;
    private Chapter baseChapter;
    private int privilage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chapter, container, false);
        chapterDatabase = FirebaseDatabase.getInstance().getReference().child("chapters");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        president= (TextView) getView().findViewById(R.id.info_president);
        school= (TextView) getView().findViewById(R.id.info_school);
        vice= (TextView) getView().findViewById(R.id.info_vice);
        secretary= (TextView) getView().findViewById(R.id.info_secretary);
        treasurer= (TextView) getView().findViewById(R.id.info_treasurer);
        advisor= (TextView) getView().findViewById(R.id.info_advisor);
        title= (TextView) getView().findViewById(R.id.title_modifications);
        modificationsList = (ListView) getView().findViewById(R.id.modification_list);
        positions = new ArrayList<>();
        modifications = new ArrayList<>();
        privilage=0;
        ValueEventListener positionListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot postsSnapshot) {
                for (DataSnapshot postSnapshot: postsSnapshot.getChildren()) {
                    Chapter temp = postSnapshot.getValue(Chapter.class);
                    baseChapter= temp;
                    if(temp.getChapterCode()==MainMenu.userInfo.getChapterCode()){
                        positions.add(temp.getPresidentID());
                        positions.add(temp.getVicePresidentID());
                        positions.add(temp.getSecretaryID());
                        positions.add(temp.getTresurerID());
                        positions.add(temp.getAdvisorID());
                        school.setText(temp.getSchool());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting info failed
                Log.d("get positions", "loadPost:onCancelled", databaseError.toException());
            }
        };
        chapterDatabase.addListenerForSingleValueEvent(positionListener);
        ValueEventListener userFinderListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot postsSnapshot) {
                if(!positions.get(0).isEmpty()) {
                    president.setText(postsSnapshot.child(positions.get(0).replace(".", "@")).getValue(User.class).getName());
                }
                if(!positions.get(1).isEmpty()) {
                    vice.setText(postsSnapshot.child(positions.get(1).replace(".", "@")).getValue(User.class).getName());
                }
                if(!positions.get(2).isEmpty()) {
                    secretary.setText(postsSnapshot.child(positions.get(2).replace(".", "@")).getValue(User.class).getName());
                }
                if(!positions.get(3).isEmpty()) {
                    treasurer.setText(postsSnapshot.child(positions.get(3).replace(".", "@")).getValue(User.class).getName());
                }
                if(!positions.get(4).isEmpty()) {
                    advisor.setText(postsSnapshot.child(positions.get(4).replace(".", "@")).getValue(User.class).getName());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting info failed
                Log.d("get users on positions", "loadPost:onCancelled", databaseError.toException());
            }
        };
        userDatabase.addListenerForSingleValueEvent(userFinderListener);
        title.setVisibility(View.VISIBLE);
        if(MainMenu.userInfo.getChapterCode()==Integer.parseInt(this.getResources().getString(R.string.main_chapter))) {
            privilage=4;
            modifications.add("Chapter Management");
            modifications.add("Change Positions Codes");
            modifications.add("Create a Discussion");
            modifications.add("Make an Announcement");
            modifications.add("Make an Event");
        }
        else if (baseChapter.getAdvisorID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getPresidentID().equals(MainMenu.userInfo.getEmail().replace(".","@"))) {
            privilage =3;
            modifications.add("Change Positions Codes");
            modifications.add("Create a Discussion");
            modifications.add("Make an Announcement");
            modifications.add("Make an Event");
        }
        else if (baseChapter.getVicePresidentID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getSecretaryID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getTresurerID().equals(MainMenu.userInfo.getEmail().replace(".","@")) ) {
            privilage =2;
            modifications.add("Create a Discussion");
            modifications.add("Make an Announcement");
            modifications.add("Make an Event");
        }
        else if (baseChapter.getPosition1ID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getPosition2ID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getPosition3ID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getPosition4ID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getPosition5ID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getPosition6ID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getPosition7ID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getPosition8ID().equals(MainMenu.userInfo.getEmail().replace(".","@")) || baseChapter.getPosition9ID().equals(MainMenu.userInfo.getEmail().replace(".","@")) ) {
            privilage =1;
            modifications.add("Make an Announcement");
            modifications.add("Make an Event");
        }
        else {
            privilage =0;
            title.setVisibility(View.INVISIBLE);
        }
        modificationsList.setAdapter(new ChapterUI.AdapterOrganizer(this.getContext()));
    }

    public class AdapterOrganizer extends BaseAdapter {

        Context context;

        public AdapterOrganizer(Context context) {

        }

        @Override
        public int getCount() {
            if (!modifications.isEmpty()) {
                return modifications.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return modifications.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.single_line_title, parent, false);
            TextView title = (TextView) row.findViewById(R.id.maintxt);
            title.setText(modifications.get(position));
            return null;
        }
    }


}