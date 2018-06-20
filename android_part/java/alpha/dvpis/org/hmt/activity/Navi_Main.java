package alpha.dvpis.org.hmt.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import alpha.dvpis.org.hmt.R;
import alpha.dvpis.org.hmt.api.MeasurementSetting;
import alpha.dvpis.org.hmt.api.Reminder;
import alpha.dvpis.org.hmt.fragment.MonitoringFragment;
import alpha.dvpis.org.hmt.fragment.PilotFragment;
import alpha.dvpis.org.hmt.fragment.VideoChatFragment;
import alpha.dvpis.org.hmt.unused.Pilot_Frag;
import alpha.dvpis.org.hmt.fragment.ReminderFragment;
import alpha.dvpis.org.hmt.service.HttpRequests;

public class Navi_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnDismissListener {

    ViewPager viewPager;
    TabLayout tabLayout;

    String primaryName;
    String token;

    String[] primaryNames;

    //ReminderFragment reminderDialog;

    HttpRequests getReminder;

    Handler mHandler;
    Runnable mRunnable;

    Reminder reminder;

    boolean running, actived;
    long oneHourMilli = 1000 * 60 * 60;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_main);

        Intent intent = getIntent();
        if (intent != null){
            token = intent.getStringExtra("token");
            primaryName = intent.getStringExtra("primaryName");
            primaryNames = intent.getStringArrayExtra("primaryNames");

            setTitle(primaryName);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(), getApplicationContext()));

        tabLayout = (TabLayout) findViewById(R.id.tapLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            public void onTabSelected(TabLayout.Tab tab){
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        this.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

        mHandler = new Handler();

        makeNewAsyncTask();

        running = false;
        actived = false;
        getReminder.execute("getReminder", token);
    }

    private class CustomAdapter extends FragmentPagerAdapter {
        private String fragments [] = {"Monitoring", "VideoChat", "Pilot"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
                super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    //return new MonitoringFragment();
                    return MonitoringFragment.newInstance(token, primaryName);
                    //return new VideoChatFragment();
                case 1:
                    return new VideoChatFragment();
                    //return new Pilot_Frag();
                case 2:
                    return new PilotFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Intent intent;

        int id = item.getItemId();

        if (id == R.id.nav_primary) {
            intent = new Intent(getApplicationContext(), PrimaryUsers.class);
            intent.putExtra("token", token);
            System.out.println("token1: " + token);
            intent.putExtra("primaryNames", primaryNames);
            startActivityForResult(intent, 1003);
        } else if (id == R.id.nav_monitoring) {
            intent = new Intent(getApplicationContext(), MonitoringFragment.class);
            startActivityForResult(intent, 1004);
        } else if (id == R.id.nav_videochat) {
            intent = new Intent(getApplicationContext(), VideoChatFragment.class);
            startActivityForResult(intent, 1005);
        } else if (id == R.id.nav_pilot) {
            intent = new Intent(getApplicationContext(), Pilot_Frag.class);
            startActivityForResult(intent, 1005);
        } else if (id == R.id.nav_reminder) {
            DialogFragment reminderDialog = new ReminderFragment().newInstance(token, "setReminder");
            reminderDialog.show(getSupportFragmentManager(), "ReminderFragment");

            System.out.println("DialogFragment starts!");
        } else if (id == R.id.nav_logOut) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void makeNewAsyncTask(){
        getReminder = new HttpRequests(new HttpRequests.AsyncResponse() {

            @Override
            public void authenticateUser(String token, String[] primaryNames) {}
            @Override
            public void getMeasurementsSetting(MeasurementSetting setting) {}
            @Override
            public void getMeasurementsByPeriod(int[] measurements) {}
            public void ResourceAccessException(){}
            @Override
            public void getReminder(Reminder r) {
                if(r != null) {
                    reminder = r;
                    mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("run call!");
                            DialogFragment reminderDialog = new ReminderFragment().newInstance(token, reminder.getDescription());
                            reminderDialog.show(getSupportFragmentManager(), "ReminderFragment");
                            //running = false;
                            actived = true;
                        }
                    };

                    System.out.println("date: " + reminder.getDate() + "   description: " + reminder.getDescription());
                    System.out.println("curTime: " + (System.currentTimeMillis()+oneHourMilli) + "    time diff: " + ((System.currentTimeMillis()+oneHourMilli)  - reminder.getDate()));
                    if (reminder.getDate() < (System.currentTimeMillis()+oneHourMilli)) {
                        System.out.println("active call");
                        DialogFragment reminderDialog = new ReminderFragment().newInstance(token, reminder.getDescription());
                        reminderDialog.show(getSupportFragmentManager(), "ReminderFragment");
                        actived = true;
                        //running = false;
                    } else {
                        System.out.println("timer call: " + reminder.getDate());
                        mHandler.postDelayed(mRunnable, (reminder.getDate()-(System.currentTimeMillis()+oneHourMilli)));
                        actived = false;
                        //running = true;
                    }
                }
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        System.out.println("DialogFragment ends!");
        /*if(!running) {
            makeNewAsyncTask();
            getReminder.execute("getReminder", token);
        }*/
        if(actived){
            makeNewAsyncTask();
            getReminder.execute("deleteReminder", token, String.format("%d",reminder.getDate()), reminder.getDescription());
            makeNewAsyncTask();
            getReminder.execute("getReminder", token);
        }else{
            mHandler.removeCallbacks(mRunnable);
            makeNewAsyncTask();
            getReminder.execute("getReminder", token);
        }
        actived = false;

        //mHandler.removeCallbacks(mRunnable);

    }
}
