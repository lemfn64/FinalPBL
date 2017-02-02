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

import java.util.ArrayList;

public class ChapterManagment extends AppCompatActivity {

    private View scroll;
    private EditText newChapterCode;
    private EditText newPresidentCode;
    private EditText newAdvisorCode;
    private EditText newDistrictCode;
    private EditText newChapterSchool;
    private Button newChapter;
    private View.OnClickListener click;
    private DatabaseReference chapterDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_managment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        chapterDatabase = FirebaseDatabase.getInstance().getReference().child("chapters");
    }

    @Override
    public void onResume() {
        super.onResume();
        scroll = findViewById(R.id.scroll_managment);
        newChapterCode = (EditText) findViewById(R.id.create_chapter_code);
        newPresidentCode = (EditText) findViewById(R.id.create_president_code);
        newAdvisorCode = (EditText) findViewById(R.id.create_advisor_code);
        newDistrictCode = (EditText) findViewById(R.id.create_district);
        newChapterSchool = (EditText) findViewById(R.id.create_school);
        newChapter = (Button) findViewById(R.id.create_commit);
        click = new onClick();
        newChapter.setOnClickListener(click);
    }

    private class onClick implements View.OnClickListener {
        boolean codeExists = false;

        public onClick() {

        }

        @Override
        public void onClick(View v) {
             if(v.getId()==R.id.create_commit) {
                 codeExists= false;
                 if(newChapterCode.getText().toString().isEmpty()){
                     newChapterCode.setError(getString(R.string.error_field_required));
                     newChapterCode.requestFocus();

                 }
                 else if(newPresidentCode.getText().toString().isEmpty()){
                     newPresidentCode.setError(getString(R.string.error_field_required));
                     newPresidentCode.requestFocus();

                 } else if(newAdvisorCode.getText().toString().isEmpty()){
                     newAdvisorCode.setError(getString(R.string.error_field_required));
                     newAdvisorCode.requestFocus();

                 } else if(newDistrictCode.getText().toString().isEmpty()){
                     newDistrictCode.setError(getString(R.string.error_field_required));
                     newDistrictCode.requestFocus();

                 } else if(newChapterSchool.getText().toString().isEmpty()){
                     newChapterSchool.setError(getString(R.string.error_field_required));
                     newChapterSchool.requestFocus();

                 } else if(newChapterCode.getText().toString().length()<7){
                     newChapterCode.setError(getString(R.string.error_invalid_code));
                     newChapterCode.requestFocus();

                 } else if(newPresidentCode.getText().toString().length()<7){
                     newPresidentCode.setError(getString(R.string.error_invalid_code));
                     newPresidentCode.requestFocus();

                 } else if(newAdvisorCode.getText().toString().length()<7){
                     newAdvisorCode.setError(getString(R.string.error_invalid_code));
                     newAdvisorCode.requestFocus();

                 } else if(newDistrictCode.getText().toString().length()<7){
                     newDistrictCode.setError(getString(R.string.error_invalid_code));
                     newDistrictCode.requestFocus();

                 } else {
                     ValueEventListener chapterListener = new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot postsSnapshot) {
                             for (DataSnapshot postSnapshot: postsSnapshot.getChildren()) {
                                 if(Integer.parseInt(postSnapshot.getKey())==Integer.parseInt(newChapterCode.getText().toString())){
                                     codeExists = true;
                                 }
                             }
                         }
                         @Override
                         public void onCancelled(DatabaseError databaseError) {
                             // Getting info failed
                             Log.d("get chapter", "loadPost:onCancelled", databaseError.toException());
                         }
                     };
                     chapterDatabase.addListenerForSingleValueEvent(chapterListener);
                     if(codeExists){
                         newChapterCode.setError("Code already exists");
                         newChapterCode.requestFocus();
                     }
                     else {
                         Chapter temp = new Chapter(Integer.parseInt(newChapterCode.getText().toString()),newChapterSchool.getText().toString(), Integer.parseInt(newDistrictCode.getText().toString()),
                                 Integer.parseInt(newPresidentCode.getText().toString()), Integer.parseInt(newAdvisorCode.getText().toString()));
                         chapterDatabase.child(String.valueOf(temp.getChapterCode())).setValue(temp);
                         finish();
                     }

                 }


            }
        }
    }
}
