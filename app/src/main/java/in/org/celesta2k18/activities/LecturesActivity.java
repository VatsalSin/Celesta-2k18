package in.org.celesta2k18.activities;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import in.org.celesta2k18.R;
import in.org.celesta2k18.adapters.LecturesRecyclerViewAdapter;
import in.org.celesta2k18.data.ClubsData;
import in.org.celesta2k18.data.EventsData;
import in.org.celesta2k18.data.lAndwData;
import static in.org.celesta2k18.activities.landwInfoActivity.EXTRA_CONTACTS;
import static in.org.celesta2k18.activities.landwInfoActivity.EXTRA_DATE;
import static in.org.celesta2k18.activities.landwInfoActivity.EXTRA_DESCRIPTION;
import static in.org.celesta2k18.activities.landwInfoActivity.EXTRA_HEADER;
import static in.org.celesta2k18.activities.landwInfoActivity.EXTRA_IMAGE_ID;
import static in.org.celesta2k18.activities.landwInfoActivity.EXTRA_ORGANIZERS;
import static in.org.celesta2k18.activities.landwInfoActivity.EXTRA_TIME;
import static in.org.celesta2k18.activities.landwInfoActivity.EXTRA_VENUE;

public class LecturesActivity extends AppCompatActivity implements LecturesRecyclerViewAdapter.ListCardClick {

    RecyclerView recyclerView;
    LecturesRecyclerViewAdapter lecturesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_lectures);

        recyclerView = findViewById(R.id.rv_lec);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        Resources resources = getResources();

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorEvents)));

        lecturesRecyclerViewAdapter = new LecturesRecyclerViewAdapter(getApplicationContext(), this,resources.getStringArray(R.array.array_lec_topics), resources.getStringArray(R.array.array_lec_headers),
                resources.getStringArray(R.array.array_lec_date),
                resources.getStringArray(R.array.array_lec_time),
                resources.getStringArray(R.array.array_lec_venue),
                resources.getStringArray(R.array.array_lec_intro),
                resources.getStringArray(R.array.array_lec_description),
                resources.getStringArray(R.array.array_lec_organiser),
                resources.getStringArray(R.array.array_lec_contacts),
                resources.obtainTypedArray(R.array.array_lec_images),
                resources.getStringArray(R.array.array_lec_intent));
        recyclerView.setAdapter(lecturesRecyclerViewAdapter);
    }

    @Override
    public void onListClick(lAndwData landwData, View view) throws ClassNotFoundException {
//        Intent intentNew = new Intent(this, Class.forName("in.org.celesta2k17.activities.landwInfoActivity"));
        Uri webpage = Uri.parse(landwData.intents);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }
}

