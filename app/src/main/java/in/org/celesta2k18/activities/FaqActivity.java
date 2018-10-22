package in.org.celesta2k18.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.org.celesta2k18.R;
import in.org.celesta2k18.adapters.FaqRecyclerAdapter;

public class FaqActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FaqRecyclerAdapter faqRecyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        List<Faq_answer> Faq = new ArrayList<>();
        Faq.add(new Faq_answer("Question", "Answer","100"));
        Faq.add(new Faq_answer("Question", "Answer","100"));
        Faq.add(new Faq_answer("Question", "Answer","100"));
        Faq.add(new Faq_answer("Question", "Answer","100"));
        Faq.add(new Faq_answer("Question", "Answer","100"));
        recyclerView = findViewById(R.id.rv_faq);
        faqRecyclerAdapter = new FaqRecyclerAdapter(FaqActivity.this, Faq);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FaqActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(faqRecyclerAdapter);

    }
    public void onItemClick(View view ,int position){
            Toast.makeText(this,"Clicked", Toast.LENGTH_SHORT).show();
        }
}
