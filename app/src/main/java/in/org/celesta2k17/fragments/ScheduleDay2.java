package in.org.celesta2k17.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.org.celesta2k17.R;
import in.org.celesta2k17.adapters.ScheduleRecyclerViewAdapter;
import in.org.celesta2k17.data.EventsData;

/**

 */
public class ScheduleDay2 extends android.support.v4.app.Fragment implements ScheduleRecyclerViewAdapter.ListCardClick {

    RecyclerView recyclerView;
    ScheduleRecyclerViewAdapter scheduleRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_schedule_day2, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_schedule_day2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        Resources resources = getResources();

        scheduleRecyclerView = new ScheduleRecyclerViewAdapter(getActivity(),
                this,
                resources.getStringArray(R.array.array_day2_event_header),
                resources.getStringArray(R.array.array_day2_event_time),
                resources.getStringArray(R.array.array_day2_event_dates),
                resources.getStringArray(R.array.array_day2_event_location));
        recyclerView.setAdapter(scheduleRecyclerView);
        return rootView;
    }

    @Override
    public void onListClick(EventsData eventsData, View view) throws ClassNotFoundException {
    }
}
