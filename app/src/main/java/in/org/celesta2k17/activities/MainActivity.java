package in.org.celesta2k17.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import in.org.celesta2k17.R;
import in.org.celesta2k17.adapters.PageFragmentAdapter;

public class MainActivity extends AppCompatActivity {

    private Menu menu = null;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.home);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_item_sponsors:
                        Toast.makeText(MainActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_item_faq:
                        startActivity( new Intent(MainActivity.this, FaqActivity.class));
                        break;
                    case R.id.menu_item_about:
                        startActivity( new Intent(MainActivity.this, AboutActivity.class));
                        break;
                    case R.id.menu_item_profile:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                    case R.id.menu_item_logout:
                        Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_item_schedule:
                        startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
                        break;
                    case R.id.menu_item_lectures:
                        startActivity(new Intent(MainActivity.this, LecturesActivity.class));
                        break;
                    case R.id.menu_item_workshops:
                        startActivity(new Intent(MainActivity.this, WorkshopsActivity.class));
                        break;
                    case R.id.menu_item_exhibitions:
                        startActivity(new Intent(MainActivity.this, ExpoEvents.class));
                        break;
                    case R.id.menu_item_team:
                        startActivity(new Intent(MainActivity.this, TeamActivity.class));
                        break;
                    case R.id.menu_item_developers:
                        startActivity(new Intent(MainActivity.this, DevelopersActivity.class));
                        break;
                    case R.id.menu_item_events:
                        startActivity(new Intent(MainActivity.this, EventsActivity.class));
                        break;
                    case R.id.menu_item_map:
                        String uri="https://www.google.com/maps/d/viewer?mid=1Tub6_KM_0Tv8UHkh97SP9Tehv78HBv1e&usp=sharingax&basemap=satellite";
                        Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(uri));
                        Objects.requireNonNull(MainActivity.this).startActivity(intent);
                        break;
                }
                item.setChecked(true);
                drawerLayout.closeDrawers();

                return true;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        PageFragmentAdapter mPageFragmentAdapter = new PageFragmentAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPageFragmentAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        int tabIcons[] = {R.drawable.ic_home_white_24dp,
                R.drawable.ic_stars_white_24dp,
                R.drawable.ic_photo_library_white_24dp};

        for (int i = 0; i < mPageFragmentAdapter.getCount(); i++)
            Objects.requireNonNull(tabLayout.getTabAt(i)).setIcon(tabIcons[i]);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        refreshMenu();
        return true;
    }

    private void refreshMenu() {
        if (menu != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (!sharedPreferences.getBoolean(getString(R.string.login_status), false)) {
                menu.findItem(R.id.action_log_out).setVisible(false);
                menu.findItem(R.id.action_log_in).setVisible(true);
            } else {
                menu.findItem(R.id.action_log_in).setVisible(false);
                menu.findItem(R.id.action_log_out).setVisible(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        if (id == R.id.action_log_out) {
            SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this).edit();
            sharedPreferences.putBoolean(getString(R.string.login_status), false);
            sharedPreferences.apply();
            refreshMenu();
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_log_in) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            refreshMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    // Go to myProfile Activity
    public void toMyProfile(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.getBoolean(getString(R.string.login_status), false)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_register_signup_or_signin);
            TextView fullNameTextView = findViewById(R.id.fullName);
            TextView nameTextView = findViewById(R.id.nameTextView);
            TextView idTextView = findViewById(R.id.idValue);
            TextView collegeTextView = findViewById(R.id.collegeNameValue);
            TextView eventTextView = findViewById(R.id.eventsParticipatedValue);

            String full_name = sharedPreferences.getString(getString(R.string.full_name), "Mayank Vaidya");

            fullNameTextView.setText(sharedPreferences.getString(getString(R.string.full_name), "Mayank Vaidya"));
            String nameViewText = "" + Character.toUpperCase(full_name.charAt(0)) + Character.toUpperCase(full_name.charAt(full_name.indexOf(' ') + 1));
            nameTextView.setText(nameViewText);
            idTextView.setText(sharedPreferences.getString(getString(R.string.id), "12345"));
            collegeTextView.setText(sharedPreferences.getString(getString(R.string.college_name), "IIT Patna"));
            eventTextView.setText(sharedPreferences.getString(getString(R.string.event_participated), "-"));
            eventTextView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (menu != null) {
            refreshMenu();
        }
    }

}
