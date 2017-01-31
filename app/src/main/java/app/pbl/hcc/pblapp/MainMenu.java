package app.pbl.hcc.pblapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainMenu extends AppCompatActivity {

    /**
     * Variables
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static boolean logged = false;
    public static User userInfo;
    private SharedPreferences storage;
    public static boolean runing=false;


    /**
     * method called at the begining of the app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        runing=true;

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

        // chacks if user is log in if not send to log in activity
        if(!logged) {
            Log.d("auth_failed", "catched by log in");
            // start log in
            startActivity(new Intent(MainMenu.this, LoginActivity.class));
        }
    }

    public static boolean isRuning() {
        return runing;
    }

    @Override
    public void onResume() {
        super.onResume();
        //prepares menu for changes
        invalidateOptionsMenu();
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(logged){
            menu.findItem(R.id.action_log).setTitle(R.string.action_log_out);
        }
        else {
            menu.findItem(R.id.action_log).setTitle(R.string.action_log);
        }
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
        if(id==R.id.action_log){
            if(logged) {
                userInfo = new User();
                logged = false;
                SharedPreferences.Editor editor = storage.edit();
                editor.putString("user", null);
                editor.putString("password", null);
                editor.putString("name", null);
                editor.putInt("chapterCode", 999);
                editor.putInt("position", 999);
                startActivity(new Intent(this, LoginActivity.class));
            }
            else {
                startActivity(new Intent(this, LoginActivity.class));
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
                    DiscusionUI discusionUI = new DiscusionUI();
                    return discusionUI;
                case 1:
                    Calendar calendar = new Calendar();
                    return calendar;
                case 2:
                    ChapterUI chapterUI = new ChapterUI();
                    return chapterUI;
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
