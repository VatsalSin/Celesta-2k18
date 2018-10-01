package in.org.celesta2k17.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import in.org.celesta2k17.R;
import in.org.celesta2k17.adapters.EventsAdapter;
import in.org.celesta2k17.listeners.ViewPagerCustomDuration;


public class ActivityPronite  extends AppCompatActivity {

    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    private int currentPage = 0;
    Timer timer ;
    private int NUM_PAGES = 6;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_pronite);


        final ViewPagerCustomDuration viewPagerCustomDuration = findViewById(R.id.events_pager);
        viewPagerCustomDuration.setScrollDuration(900);
        EventsAdapter eventsAdapter = new EventsAdapter(this,
                getResources().obtainTypedArray(R.array.array_pronite_slide_show));

        viewPagerCustomDuration.setAdapter(eventsAdapter);
        viewPagerCustomDuration.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /*Adding automatic swap to the images
        * */
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            viewPagerCustomDuration.setCurrentItem(currentPage++, true);
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
}
