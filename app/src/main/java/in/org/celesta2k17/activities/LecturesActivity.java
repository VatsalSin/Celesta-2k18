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
import in.org.celesta2k17.adapters.LecturesRecyclerViewAdapter;

public class LecturesActivity extends AppCompatActivity implements LecturesRecyclerViewAdapter.ListCardClick {

    RecyclerView recyclerView;
    LecturesRecyclerViewAdapter lecturesRecyclerViewAdapter;

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

        lecturesRecyclerViewAdapter = new LecturesRecyclerViewAdapter(getApplicationContext(), this,resources.getStringArray(R.array.array_lec_topics), resources.getStringArray(R.array.array_lec_headers),
                resources.getStringArray(R.array.array_lec_date),
                resources.getStringArray(R.array.array_lec_time),
                resources.getStringArray(R.array.array_lec_venue),
                resources.getStringArray(R.array.array_lec_intro),
                resources.getStringArray(R.array.array_lec_description),
                resources.getStringArray(R.array.array_lec_intent),
                resources.obtainTypedArray(R.array.array_lec_images));
        recyclerView.setAdapter(lecturesRecyclerViewAdapter);
    }

    @Override
    public void onListClick(String intent) throws ClassNotFoundException {
        Intent intentNew = new Intent(this, Class.forName(intent));
        startActivity(intentNew);
    }
}

