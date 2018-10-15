package in.org.celesta2k17.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import in.org.celesta2k17.R;


/**
 * Created by mayank on 15/7/17.
 */

public class MyProfile extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
    }

    private void setView() {
        RequestQueue mQueue;
        mQueue = Volley.newRequestQueue(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView eventTextView = findViewById(R.id.eventsParticipatedValue);
        TextView eventAllTextView = findViewById(R.id.eventsParticipated);
        setContentView(R.layout.activity_register_signup_or_signin);
        TextView fullNameTextView = findViewById(R.id.fullName);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView idTextView = findViewById(R.id.idValue);
        TextView collegeTextView = findViewById(R.id.collegeNameValue);
        String full_name = sharedPreferences.getString(getString(R.string.full_name), "Mayank Vaidya");
        fullNameTextView.setText(sharedPreferences.getString(getString(R.string.full_name), "Mayank Vaidya"));
        String nameViewText = "" + Character.toUpperCase(full_name.charAt(0)) + Character.toUpperCase(full_name.charAt(full_name.indexOf(' ') + 1));
        nameTextView.setText(nameViewText);
        idTextView.setText("CLST"+sharedPreferences.getString(getString(R.string.id), "12345"));
        collegeTextView.setText(sharedPreferences.getString(getString(R.string.college_name), "IIT Patna"));
//        eventTextView.setText(sharedPreferences.getString(getString(R.string.event_participated), "-"));
//        eventTextView.setVisibility(View.GONE);
                    String mUrl = getString(R.string.url_eventinfo);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, mUrl,
                            response -> {
                                Log.v("Response:", response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int status = Integer.parseInt(jsonObject.getString(getString(R.string.JSON_status)));
                                    switch (status) {
                                        case 200:
                                            Toast.makeText(this, "Fetching Data...", Toast.LENGTH_LONG).show();

                                        JSONArray events = jsonObject.getJSONArray("events");
                                        int numEvents = events.length();
                                        String eventList="";
                                        for(int i=0;i<numEvents;i++)
                                        {
                                            if(i!=0)
                                            {
                                                eventList = eventList + ", ";
                                            }
                                            eventList = eventList + events.get(i);
                                        }
                                        eventTextView.setText(""+numEvents);
                                        eventAllTextView.setText(eventList);
//                                        Log.v("events:", eventList);
//                                        String events = jsonObject.getString("events");
                                            break;
                                        case 400:
                                            Toast.makeText(this, "Invalid Celesta Id", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 409:
                                            Toast.makeText(this, R.string.message_registration_duplicate, Toast.LENGTH_LONG).show();
                                            finish();
                                            break;
                                        case 403:
                                            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Toast.makeText(this, "Error logging in. Please try again later", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            },
                            error -> {
                                Log.v("Error : ", error.toString());
                                error.printStackTrace();
                                Toast.makeText(this, "Error logging in. Please try again later", Toast.LENGTH_SHORT).show();
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("id",sharedPreferences.getString(getString(R.string.id),"12345"));
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Accept", "application/json");
                            return headers;
                        }
                    };
                    mQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setView();
    }
}
