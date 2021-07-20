package com.example.test.gym.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test.gym.R;
import com.example.test.gym.preferance.SharePref;
import com.example.test.gym.util.ImageLoader;
import com.squareup.picasso.Picasso;

public class DietChartActivity extends AppCompatActivity {

    Context context = DietChartActivity.this;
    ImageView dietChartImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_chart);
        SharePref shrObj = new SharePref();
        Bundle bundle = getIntent().getExtras();

        String dietChartFile = bundle.getString("dietChart");

        dietChartImg = (ImageView) findViewById(R.id.dietChart);

        String image_url = "http://" + shrObj.getServerURL(context) + "/Gym/GymImages/" + dietChartFile;

        Picasso.with(context).load(image_url).into(dietChartImg);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
            {
                Toast.makeText(getApplicationContext(),"Logged out successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(context,LoginActivity.class));
                finish();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
