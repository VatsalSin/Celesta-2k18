package in.org.celesta2k17.activities;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
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

import in.org.celesta2k17.R;
import in.org.celesta2k17.adapters.WorkshopsRecyclerViewAdapter;
import in.org.celesta2k17.data.lAndwData;

import static in.org.celesta2k17.activities.landwInfoActivity.EXTRA_CONTACTS;
import static in.org.celesta2k17.activities.landwInfoActivity.EXTRA_DATE;
import static in.org.celesta2k17.activities.landwInfoActivity.EXTRA_DESCRIPTION;
import static in.org.celesta2k17.activities.landwInfoActivity.EXTRA_HEADER;
import static in.org.celesta2k17.activities.landwInfoActivity.EXTRA_IMAGE_ID;
import static in.org.celesta2k17.activities.landwInfoActivity.EXTRA_ORGANIZERS;
import static in.org.celesta2k17.activities.landwInfoActivity.EXTRA_TIME;
import static in.org.celesta2k17.activities.landwInfoActivity.EXTRA_VENUE;


public class WorkshopsActivity extends AppCompatActivity implements WorkshopsRecyclerViewAdapter.ListCardClick {

    RecyclerView recyclerView;
    WorkshopsRecyclerViewAdapter workshopsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_workshops);

        recyclerView = findViewById(R.id.rv_workshops);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        Resources resources = getResources();

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorEvents)));

        workshopsRecyclerViewAdapter = new WorkshopsRecyclerViewAdapter(getApplicationContext(), this,resources.getStringArray(R.array.array_workshop_topics), resources.getStringArray(R.array.array_workshop_headers),
                resources.getStringArray(R.array.array_workshop_date),
                resources.getStringArray(R.array.array_workshop_time),
                resources.getStringArray(R.array.array_workshop_venue),
                resources.getStringArray(R.array.array_workshop_intro),
                resources.getStringArray(R.array.array_workshop_description),
                resources.getStringArray(R.array.array_workshop_organiser),
                resources.getStringArray(R.array.array_workshop_contacts),
                resources.obtainTypedArray(R.array.array_workshop_images));
        recyclerView.setAdapter(workshopsRecyclerViewAdapter);
    }

    @Override
    public void onListClick(lAndwData landwData, View view) throws ClassNotFoundException {
        Intent intentNew = new Intent(this, Class.forName("in.org.celesta2k17.activities.landwInfoActivity"));


        intentNew.putExtra(EXTRA_HEADER, landwData.headers);
        intentNew.putExtra(EXTRA_DESCRIPTION, landwData.description);
        intentNew.putExtra(EXTRA_TIME, landwData.time);
        intentNew.putExtra(EXTRA_DATE, landwData.date);
        intentNew.putExtra(EXTRA_VENUE, landwData.venue);
        intentNew.putExtra(EXTRA_IMAGE_ID, landwData.img);
        intentNew.putExtra(EXTRA_ORGANIZERS, landwData.organiser);
        intentNew.putExtra(EXTRA_CONTACTS, landwData.contacts);
        startActivity(intentNew);
    }
}

