package in.org.celesta2k17.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import in.org.celesta2k17.R;
import in.org.celesta2k17.adapters.WorkshopsRecyclerViewAdapter;

public class LecturesActivity extends AppCompatActivity implements WorkshopsRecyclerViewAdapter.ListCardClick {

    RecyclerView recyclerView;
    WorkshopsRecyclerViewAdapter workshopsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_lectures);

        recyclerView = findViewById(R.id.rv_events);

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
                resources.getStringArray(R.array.array_workshop_intent),
                resources.obtainTypedArray(R.array.array_workshop_images));
        recyclerView.setAdapter(workshopsRecyclerViewAdapter);
    }

    @Override
    public void onListClick(String intent) throws ClassNotFoundException {
        Intent intentNew = new Intent(this, Class.forName(intent));
        startActivity(intentNew);
    }
}

