package in.org.celesta2k17.activities;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;


import com.bumptech.glide.Glide;

import in.org.celesta2k17.R;

public class EventInfoActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    public  Button rulesButton,registerButton;
    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    public static final String EXTRA_HEADER = "Header",
            EXTRA_DESCRIPTION = "Text",
            EXTRA_RULES = "Rules",
            EXTRA_VENUE = "Venue",
            EXTRA_DATE_TIME = "DateTime",
            EXTRA_IMAGE_ID = "ImageId",
            EXTRA_ORGANIZERS = "Organizers",
            EXTRA_CONTACTS = "Contacts",
            EXTRA_LINKS = "links",
            EXTRA_RULES_LINKS = "rulesLinks";

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(getApplicationContext()).clearMemory();
        Glide.get(getApplicationContext()).trimMemory(TRIM_MEMORY_COMPLETE);
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        registerButton = (Button)findViewById(R.id.registerButton);
        rulesButton = (Button)findViewById(R.id.rulesButton);;
        mToolbar        = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_appbar);
        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        // Get the Intent that started this activity and extract the strings needed
        Intent intent = getIntent();
        final String header = intent.getStringExtra(EXTRA_HEADER);
        String text = intent.getStringExtra(EXTRA_DESCRIPTION);
        String rules = intent.getStringExtra(EXTRA_RULES);
        final String dateTime = intent.getStringExtra(EXTRA_DATE_TIME);
        String venue = intent.getStringExtra(EXTRA_VENUE);
        final int imageId = intent.getIntExtra(EXTRA_IMAGE_ID, -1);
        final String link = intent.getStringExtra(EXTRA_LINKS);
        final String ruleLink = intent.getStringExtra(EXTRA_RULES_LINKS);

        ((TextView) findViewById(R.id.event_info_name)).setText(header);
        mTitle.setText(header);
        if (imageId != -1)
            Glide.with(this)
                    .load(imageId)
                    .into((ImageView) findViewById(R.id.event_info_imageview));

        if (text.equals("-1"))
            findViewById(R.id.event_info_textview).setVisibility(View.GONE);
        else {
            ((TextView) findViewById(R.id.event_info_textview)).setText(text);
        }

        ((TextView)findViewById(R.id.event_info_textview)).setMovementMethod(LinkMovementMethod.getInstance());
        final String finalText = text.equals("-1") ? "Keep checking the app and website for updates." : text;

        TextView rulesTextView = findViewById(R.id.event_rules_textview);
        if (rules.equals("-1")) {
            rulesTextView.setVisibility(View.GONE);
            (findViewById(R.id.rules_header)).setVisibility(View.GONE);
        } else {
            rulesTextView.setText(rules);
        }
        String organizers = intent.getStringExtra(EXTRA_ORGANIZERS);
        final String contacts = intent.getStringExtra(EXTRA_CONTACTS);
        if (organizers.equals("-1"))
            ((TextView) findViewById(R.id.event_organizers)).setVisibility(View.GONE);
        else
            ((TextView) findViewById(R.id.event_organizers)).setText(organizers);

        if (contacts.equals("-1"))
            ((TextView) findViewById(R.id.event_contact)).setVisibility(View.GONE);
        else
            ((TextView) findViewById(R.id.event_contact)).setText(contacts);

        if (!dateTime.equals("-1"))
            ((TextView) findViewById(R.id.event_date_time)).setText(dateTime);
        else
            ((TextView) findViewById(R.id.event_date_time)).setVisibility(View.GONE);

        if (!venue.equals("-1"))
            ((TextView) findViewById(R.id.event_venue)).setText(venue);
        else
            ((TextView) findViewById(R.id.event_venue)).setVisibility(View.GONE);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View view) {
                Uri webpage = Uri.parse(EXTRA_LINKS);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
                }
            });
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
        rulesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(ruleLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab_share_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources resources = getResources();
                String shareString = resources.getText(R.string.share_message) + "\n"
                        + resources.getText(R.string.name) + ": " + header + "\n"
                        + resources.getText(R.string.date_time) + ": " + (dateTime.equals("-1") ? "Keep checking the app and website for updates." : dateTime) + "\n"
                        + finalText;
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.share_to)));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }
    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}

