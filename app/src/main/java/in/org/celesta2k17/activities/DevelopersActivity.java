package in.org.celesta2k17.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import in.org.celesta2k17.R;

public class DevelopersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        ImageView github_vatsal = findViewById(R.id.github_vatsal);
        ImageView github_sheetal = findViewById(R.id.github_sheetal);
        ImageView github_manish = findViewById(R.id.github_manish);
        ImageView fb_vatsal = findViewById(R.id.fb_vatsal);
        ImageView fb_sheetal = findViewById(R.id.fb_sheetal);
        ImageView fb_manish = findViewById(R.id.fb_manish);

        String urls[] = {
                "https://github.com/VatsalSin",
                "https://github.com/BVShe001",
                "https://github.com/ivary43",
                "https://www.facebook.com/vatsalsinghal1",
                "https://www.facebook.com/profile.php?id=100021865192488",
                "https://www.facebook.com/imanishk16"
        };

        github_vatsal.setOnClickListener(new devOnClickListener(urls[0]));
        github_sheetal.setOnClickListener(new devOnClickListener(urls[1]));
        github_manish.setOnClickListener(new devOnClickListener(urls[2]));
        fb_vatsal.setOnClickListener(new devOnClickListener(urls[3]));
        fb_sheetal.setOnClickListener(new devOnClickListener(urls[4]));
        fb_manish.setOnClickListener(new devOnClickListener(urls[5]));
    }

    public class devOnClickListener implements View.OnClickListener {

        String mUrl;

        devOnClickListener(String url) {
            super();
            mUrl = url;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
            startActivity(intent);
        }
    }


}
