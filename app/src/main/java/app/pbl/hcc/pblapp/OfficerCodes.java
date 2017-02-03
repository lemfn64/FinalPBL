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

public class OfficerCodes extends AppCompatActivity {

    private EditText advisorCode;
    private EditText presidentCode;
    private EditText vicePresidentCode;
    private EditText secretaryCode;
    private EditText treasurerCode;
    private EditText officer1Code;
    private EditText officer2Code;
    private EditText officer3Code;
    private EditText officer4Code;
    private EditText officer5Code;
    private EditText officer6Code;
    private EditText officer7Code;
    private EditText officer8Code;
    private EditText officer9Code;
    private EditText memebersCode;
    private Button changeCodes;
    private onClick click;
    private DatabaseReference chapterDatabase;
    private Chapter chapter;
    private Chapter saved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_codes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        chapterDatabase = FirebaseDatabase.getInstance().getReference().child("chapters");
        ValueEventListener chapterListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot postsSnapshot) {
                for (DataSnapshot postSnapshot: postsSnapshot.getChildren()) {
                    if(Integer.parseInt(postSnapshot.getKey())==MainMenu.userInfo.getChapterCode()){
                        chapter= postSnapshot.getValue(Chapter.class);
                        saved = postSnapshot.getValue(Chapter.class);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        advisorCode=(EditText) findViewById(R.id.advisor_code);
        presidentCode=(EditText) findViewById(R.id.president_code);
        secretaryCode=(EditText) findViewById(R.id.secretary_code);
        treasurerCode=(EditText) findViewById(R.id.treasurer_code);
        vicePresidentCode=(EditText) findViewById(R.id.vice_code);
        officer1Code=(EditText) findViewById(R.id.pos1_code);
        officer2Code=(EditText) findViewById(R.id.pos2_code);
        officer3Code=(EditText) findViewById(R.id.pos3_code);
        officer4Code=(EditText) findViewById(R.id.pos4_code);
        officer5Code=(EditText) findViewById(R.id.pos5_code);
        officer6Code=(EditText) findViewById(R.id.pos6_code);
        officer7Code=(EditText) findViewById(R.id.pos7_code);
        officer8Code=(EditText) findViewById(R.id.pos8_code);
        officer9Code=(EditText) findViewById(R.id.pos9_code);
        changeCodes= (Button) findViewById(R.id.commit_changes);
        if(MainMenu.userInfo.getChapterCode()!= Integer.parseInt(getString(R.string.main_chapter))) {
            advisorCode.setVisibility(View.INVISIBLE);
            presidentCode.setVisibility(View.INVISIBLE);
        }
        memebersCode = (EditText) findViewById(R.id.members_code);
        click = new onClick();
        changeCodes.setOnClickListener(click);
    }

    private class onClick implements View.OnClickListener {
        boolean trigger = false;
        boolean repeated = false;
        boolean error = false;
        ArrayList<Integer> codes;

        public onClick() {
            codes = new ArrayList<Integer>();
        }

        @Override
        public void onClick(View v) {
            trigger=false;
            error= false;
            repeated=false;
            //chapter.clone(saved) TODO
            codes= new ArrayList<Integer>();
            codes.add(chapter.getAdvisorCode());
            codes.add(chapter.getPresidentCode());
            codes.add(chapter.getVicePresidentCode());
            codes.add(chapter.getSecretaryCode());
            codes.add(chapter.getTresurerCode());
            codes.add(chapter.getPosition1Code());
            codes.add(chapter.getPosition2Code());
            codes.add(chapter.getPosition3Code());
            codes.add(chapter.getPosition4Code());
            codes.add(chapter.getPosition5Code());
            codes.add(chapter.getPosition6Code());
            codes.add(chapter.getPosition7Code());
            codes.add(chapter.getPosition8Code());
            codes.add(chapter.getPosition9Code());
            codes.add(chapter.getMemeberCode());
            if(!advisorCode.getText().toString().isEmpty()){
                if(advisorCode.getText().toString().length()<7){
                    advisorCode.setError(getString(R.string.error_invalid_code));
                    advisorCode.requestFocus();
                    error=true;
                } else {
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(advisorCode.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setAdvisorCode(Integer.parseInt(advisorCode.getText().toString()));
                        codes.add(Integer.parseInt(advisorCode.getText().toString()));
                    }
                    else {
                        advisorCode.setError(getString(R.string.error_repeated_code));
                        advisorCode.requestFocus();
                        error=true;
                    }

                }
            }
            if(!presidentCode.getText().toString().isEmpty()){
                if(presidentCode.getText().toString().length()<7){
                    presidentCode.setError(getString(R.string.error_invalid_code));
                    presidentCode.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(presidentCode.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setPresidentCode(Integer.parseInt(presidentCode.getText().toString()));
                        codes.add(Integer.parseInt(presidentCode.getText().toString()));
                    }
                    else {
                        presidentCode.setError(getString(R.string.error_repeated_code));
                        presidentCode.requestFocus();
                        error=true;
                    }

                }
            }
            if(!vicePresidentCode.getText().toString().isEmpty()){
                if(vicePresidentCode.getText().toString().length()<7){
                    vicePresidentCode.setError(getString(R.string.error_invalid_code));
                    vicePresidentCode.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(vicePresidentCode.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setVicePresidentCode(Integer.parseInt(vicePresidentCode.getText().toString()));
                        codes.add(Integer.parseInt(vicePresidentCode.getText().toString()));
                    }
                    else {
                        vicePresidentCode.setError(getString(R.string.error_repeated_code));
                        vicePresidentCode.requestFocus();
                        error=true;
                    }

                }
            }
            if(!secretaryCode.getText().toString().isEmpty()){
                if(secretaryCode.getText().toString().length()<7){
                    secretaryCode.setError(getString(R.string.error_invalid_code));
                    secretaryCode.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(secretaryCode.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setSecretaryCode(Integer.parseInt(secretaryCode.getText().toString()));
                        codes.add(Integer.parseInt(secretaryCode.getText().toString()));
                    }
                    else {
                        secretaryCode.setError(getString(R.string.error_repeated_code));
                        secretaryCode.requestFocus();
                        error=true;
                    }

                }
            }
            if(!treasurerCode.getText().toString().isEmpty()){
                if(treasurerCode.getText().toString().length()<7){
                    treasurerCode.setError(getString(R.string.error_invalid_code));
                    treasurerCode.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(treasurerCode.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setTresurerCode(Integer.parseInt(treasurerCode.getText().toString()));
                        codes.add(Integer.parseInt(treasurerCode.getText().toString()));
                    }
                    else {
                        treasurerCode.setError(getString(R.string.error_repeated_code));
                        treasurerCode.requestFocus();
                        error=true;
                    }
                }
            }
            if(!memebersCode.getText().toString().isEmpty()){
                if(memebersCode.getText().toString().length()<7){
                    memebersCode.setError(getString(R.string.error_invalid_code));
                    memebersCode.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(memebersCode.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setMemeberCode(Integer.parseInt(memebersCode.getText().toString()));
                        codes.add(Integer.parseInt(memebersCode.getText().toString()));
                    }
                    else {
                        memebersCode.setError(getString(R.string.error_repeated_code));
                        memebersCode.requestFocus();
                        error=true;
                    }
                }
            }
            if(!officer1Code.getText().toString().isEmpty()){
                if(officer1Code.getText().toString().length()<7){
                    officer1Code.setError(getString(R.string.error_invalid_code));
                    officer1Code.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(officer1Code.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setPosition1Code(Integer.parseInt(officer1Code.getText().toString()));
                        codes.add(Integer.parseInt(officer1Code.getText().toString()));
                    }
                    else {
                        officer1Code.setError(getString(R.string.error_repeated_code));
                        officer1Code.requestFocus();
                        error=true;
                    }

                }
            }
            if(!officer2Code.getText().toString().isEmpty()){
                if(officer2Code.getText().toString().length()<7){
                    officer2Code.setError(getString(R.string.error_invalid_code));
                    officer2Code.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(officer2Code.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setPosition2Code(Integer.parseInt(officer2Code.getText().toString()));
                        codes.add(Integer.parseInt(officer2Code.getText().toString()));
                    }
                    else {
                        officer2Code.setError(getString(R.string.error_repeated_code));
                        officer2Code.requestFocus();
                        error=true;
                    }

                }
            }
            if(!officer3Code.getText().toString().isEmpty()){
                if(officer3Code.getText().toString().length()<7){
                    officer3Code.setError(getString(R.string.error_invalid_code));
                    officer3Code.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(officer3Code.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setPosition3Code(Integer.parseInt(officer3Code.getText().toString()));
                        codes.add(Integer.parseInt(officer3Code.getText().toString()));
                    }
                    else {
                        officer3Code.setError(getString(R.string.error_repeated_code));
                        officer3Code.requestFocus();
                        error=true;
                    }
                }
            }
            if(!officer4Code.getText().toString().isEmpty()){
                if(officer4Code.getText().toString().length()<7){
                    officer4Code.setError(getString(R.string.error_invalid_code));
                    officer4Code.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(officer4Code.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setPosition4Code(Integer.parseInt(officer4Code.getText().toString()));
                        codes.add(Integer.parseInt(officer4Code.getText().toString()));
                    }
                    else {
                        officer4Code.setError(getString(R.string.error_repeated_code));
                        officer4Code.requestFocus();
                        error=true;
                    }
                }
            }
            if(!officer5Code.getText().toString().isEmpty()){
                if(officer5Code.getText().toString().length()<7){
                    officer5Code.setError(getString(R.string.error_invalid_code));
                    officer5Code.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(officer5Code.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setPosition5Code(Integer.parseInt(officer5Code.getText().toString()));
                        codes.add(Integer.parseInt(officer5Code.getText().toString()));
                    }
                    else {
                        officer5Code.setError(getString(R.string.error_repeated_code));
                        officer5Code.requestFocus();
                        error=true;
                    }
                }
            }
            if(!officer6Code.getText().toString().isEmpty()){
                if(officer6Code.getText().toString().length()<7){
                    officer6Code.setError(getString(R.string.error_invalid_code));
                    officer6Code.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(officer6Code.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setPosition6Code(Integer.parseInt(officer6Code.getText().toString()));
                        codes.add(Integer.parseInt(officer6Code.getText().toString()));
                    }
                    else {
                        officer6Code.setError(getString(R.string.error_repeated_code));
                        officer6Code.requestFocus();
                        error=true;
                    }
                }
            }
            if(!officer7Code.getText().toString().isEmpty()){
                if(officer7Code.getText().toString().length()<7){
                    officer7Code.setError(getString(R.string.error_invalid_code));
                    officer7Code.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(officer7Code.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setPosition7Code(Integer.parseInt(officer7Code.getText().toString()));
                        codes.add(Integer.parseInt(officer7Code.getText().toString()));
                    }
                    else {
                        officer7Code.setError(getString(R.string.error_repeated_code));
                        officer7Code.requestFocus();
                        error=true;
                    }
                }
            }
            if(!officer8Code.getText().toString().isEmpty()){
                if(officer8Code.getText().toString().length()<7){
                    officer8Code.setError(getString(R.string.error_invalid_code));
                    officer8Code.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(officer8Code.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setPosition8Code(Integer.parseInt(officer8Code.getText().toString()));
                        codes.add(Integer.parseInt(officer8Code.getText().toString()));
                    }
                    else {
                        officer8Code.setError(getString(R.string.error_repeated_code));
                        officer8Code.requestFocus();
                        error=true;
                    }
                }
            }
            if(!officer9Code.getText().toString().isEmpty()){
                if(officer9Code.getText().toString().length()<7){
                    officer9Code.setError(getString(R.string.error_invalid_code));
                    officer9Code.requestFocus();
                    error=true;
                } else{
                    repeated= false;
                    for(int code: codes) {
                        if(code==Integer.parseInt(officer9Code.getText().toString())) {
                            repeated=true;
                        }
                    }
                    if (!repeated) {
                        trigger= true;
                        chapter.setPosition9Code(Integer.parseInt(officer9Code.getText().toString()));
                        codes.add(Integer.parseInt(officer9Code.getText().toString()));
                    }
                    else {
                        officer9Code.setError(getString(R.string.error_repeated_code));
                        officer9Code.requestFocus();
                        error=true;
                    }
                }
            }
            if(trigger && !error) {
                chapterDatabase.child(String.valueOf(chapter.getChapterCode())).setValue(chapter);
                finish();
            }

        }

    }
}
