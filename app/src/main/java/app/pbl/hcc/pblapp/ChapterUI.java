package app.pbl.hcc.pblapp;

import android.content.Context;
import android.content.Intent;
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

/**
 * This class shows chapter informationand depending on posiont inside the chapter it alouds modifications to be made to
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
    public static int privilage;
    private View.OnClickListener click;

    /**
     * sets the xml files for the activity and sets database paths
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chapter, container, false);
        chapterDatabase = FirebaseDatabase.getInstance().getReference().child("chapters");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        return rootView;

    }

    /**
     * sets gui elemets to code and shows options depending on your position
     */
    @Override
    public void onResume() {
        super.onResume();
        modificationsList = (ListView) getView().findViewById(R.id.modification_list);
        president= (TextView) getView().findViewById(R.id.info_president);
        school= (TextView) getView().findViewById(R.id.info_school);
        vice= (TextView) getView().findViewById(R.id.info_vice);
        secretary= (TextView) getView().findViewById(R.id.info_secretary);
        treasurer= (TextView) getView().findViewById(R.id.info_treasurer);
        advisor= (TextView) getView().findViewById(R.id.info_advisor);
        title= (TextView) getView().findViewById(R.id.title_modifications);
        modifications = new ArrayList<>();
        privilage=0;
        click = new onClicker();
        modificationsList.setAdapter(new AdapterOrganizer(this.getContext()));
        ValueEventListener positionListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot postsSnapshot) {
                if(isAdded()) {
                    for (DataSnapshot postSnapshot : postsSnapshot.getChildren()) {
                        if (Integer.parseInt(postSnapshot.getKey()) == MainMenu.userInfo.getChapterCode()) {
                            Log.d("found chapter", "hola");
                            baseChapter = postSnapshot.getValue(Chapter.class);
                        }
                    }
                    title.setVisibility(View.VISIBLE);
                    if (MainMenu.userInfo.getChapterCode() == Integer.parseInt(getResources().getString(R.string.main_chapter))) {
                        privilage = 4;
                        modifications.add("Create Chapter");
                        modifications.add("Change Positions Codes");
                        modifications.add("Make an Announcement");
                        modifications.add("Make an Event");
                    } else if (baseChapter.getAdvisorID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getPresidentID().equals(MainMenu.userInfo.getEmail().replace(".", "@"))) {
                        privilage = 3;
                        modifications.add("Change Positions Codes");
                        modifications.add("Make an Announcement");
                        modifications.add("Make an Event");
                    } else if (baseChapter.getVicePresidentID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getSecretaryID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getTresurerID().equals(MainMenu.userInfo.getEmail().replace(".", "@"))) {
                        privilage = 2;
                        modifications.add("Make an Announcement");
                        modifications.add("Make an Event");
                    } else if (baseChapter.getPosition1ID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getPosition2ID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getPosition3ID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getPosition4ID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getPosition5ID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getPosition6ID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getPosition7ID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getPosition8ID().equals(MainMenu.userInfo.getEmail().replace(".", "@")) || baseChapter.getPosition9ID().equals(MainMenu.userInfo.getEmail().replace(".", "@"))) {
                        privilage = 1;
                        modifications.add("Make an Announcement");
                        modifications.add("Make an Event");
                    } else {
                        privilage = 0;
                        title.setVisibility(View.INVISIBLE);
                    }
                    modificationsList.setAdapter(new ChapterUI.AdapterOrganizer(getContext()));
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
                if(baseChapter.getPresidentID() != null) {
                    president.setText(postsSnapshot.child(baseChapter.getPresidentID().replace(".", "@")).getValue(User.class).getName());
                }
                if(baseChapter.getVicePresidentID()!=null) {
                    vice.setText(postsSnapshot.child(baseChapter.getVicePresidentID().replace(".", "@")).getValue(User.class).getName());
                }
                if(baseChapter.getSecretaryID()!=null) {
                    secretary.setText(postsSnapshot.child(baseChapter.getSecretaryID().replace(".", "@")).getValue(User.class).getName());
                }
                if(baseChapter.getTresurerID()!=null) {
                    treasurer.setText(postsSnapshot.child(baseChapter.getTresurerID().replace(".", "@")).getValue(User.class).getName());
                }
                if(baseChapter.getAdvisorID()!=null) {
                    advisor.setText(postsSnapshot.child(baseChapter.getAdvisorID().replace(".", "@")).getValue(User.class).getName());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting info failed
                Log.d("get users on positions", "loadPost:onCancelled", databaseError.toException());
            }
        };
        userDatabase.addListenerForSingleValueEvent(userFinderListener);


    }

    /**
     * shows objects form a array in a list view
     */
    public class AdapterOrganizer extends BaseAdapter {

        Context context;

        public AdapterOrganizer(Context context) {
            this.context=context;

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

        /**
         * sets what information goes on the view
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.single_line_title, parent, false);
            TextView title = (TextView) row.findViewById(R.id.maintxt);
            row.setOnClickListener(click);
            row.setTag(modifications.get(position));
            title.setText(modifications.get(position));
            return row;
        }
    }

    /**
     * seta listener that will take you to diferent activities depnding on whcih modifications you want to make
     */
    private class onClicker implements View.OnClickListener {
        View temp;

        public onClicker() {

        }

        @Override
        public void onClick(View v) {
            if(((String)v.getTag()).equals("Create Chapter")) {
                startActivity(new Intent(ChapterUI.this.getContext(), ChapterManagment.class));

            } else if(((String)v.getTag()).equals("Change Positions Codes")) {
                startActivity(new Intent(ChapterUI.this.getContext(), OfficerCodes.class));

            }
            else if(((String)v.getTag()).equals("Make an Announcement")) {
                startActivity(new Intent(ChapterUI.this.getContext(), CreatAnnouncements.class));

            }
            else if(((String)v.getTag()).equals("Make an Event")) {
                startActivity(new Intent(ChapterUI.this.getContext(), EventUI.class));

            }
        }
    }


}