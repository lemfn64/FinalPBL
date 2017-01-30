package app.pbl.hcc.pblapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenu extends AppCompatActivity {

    /**
     * Variables
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static boolean logged = false;
    public static User userInfo;
    private SharedPreferences storage;
    public static FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * method called at the begining of the app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the tabs
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //link tab layout and set its view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //set storage
        storage = getSharedPreferences("savePreference", Context.MODE_PRIVATE);

        //set database
        mAuth = FirebaseAuth.getInstance();

        //check for log in info in storage if not send to log in
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                    if (storage.getString("user", null)==null) {
                        //empty user
                        userInfo = new User("","","",999,999);
                        // start log in
                        startActivity(new Intent(MainMenu.this, LoginActivity.class));
                    }
                    else {
                        mAuth.signInWithEmailAndPassword(storage.getString("user", null), storage.getString("password", null))
                                .addOnCompleteListener(MainMenu.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // succesfully sign in
                                        logged=true;
                                        userInfo = new User(storage.getString("user", null), storage.getString("password", null), storage.getString("name", null), storage.getInt("chapterCode", 999), storage.getInt("position", 999));
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(MainMenu.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                                            //empty user
                                            userInfo = new User("","","",999,999);
                                            // start log in
                                            startActivity(new Intent(MainMenu.this, LoginActivity.class));
                                        }
                                    }
                                });
                    }
                }
            }
        };

        if(!logged) {
            Log.d("auth_failed", "catched by log in");
            // User is signed out
            if (storage.getString("user", null)==null) {
                //empty user
                userInfo = new User("","","",999,999);
                // start log in
                startActivity(new Intent(MainMenu.this, LoginActivity.class));
            }
            else {
                mAuth.signInWithEmailAndPassword(storage.getString("user", null), storage.getString("password", null))
                        .addOnCompleteListener(MainMenu.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // succesfully sign in
                                logged=true;
                                userInfo = new User(storage.getString("user", null), storage.getString("password", null), storage.getString("name", null), storage.getInt("chapterCode", 999), storage.getInt("position", 999));
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainMenu.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                                    //empty user
                                    userInfo = new User("","","",999,999);
                                    // start log in
                                    startActivity(new Intent(MainMenu.this, LoginActivity.class));
                                }
                            }
                        });
            }
        }
    }

    /**
     * starts aunthentication handler
     */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * stops authentication handler
     */
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * creat the corner menu for more options
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    /**
     * handles corner menu and its clicks
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_account) {
            if (!logged) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                    return true;
                // go to manage account
            }
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * page adopter used to mannage Fragments, its contents and views
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * defines class and view for each tab
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Discusion discusion = new Discusion();
                    return discusion;
                case 1:
                    Calendar calendar = new Calendar();
                    return calendar;
                case 2:
                    Chapter chapter = new Chapter();
                    return chapter;
                case 3:
                    Test test = new Test();
                    return test;
            }
            return null;
        }

        @Override
        public int getCount() {
            // number of tabs
            return 4;
        }

        /**
         * defines tab titles
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "discusion";
                case 1:
                    return "calendar";
                case 2:
                    return "chapter";
                case 3:
                    return "test";
            }
            return null;
        }
    }
}
