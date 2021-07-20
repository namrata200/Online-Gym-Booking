package com.example.test.gym.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.gym.R;
import com.example.test.gym.preferance.SharePref;
import com.example.test.gym.retro.GeneralResponse;
import com.example.test.gym.retro.GymResponse;
import com.example.test.gym.retro.Retro;
import com.example.test.gym.util.Gym;
import com.example.test.gym.util.ImageLoader;
import com.squareup.picasso.Picasso;

import java.net.URL;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {

    TextView gymName;
    TextView mobile;
    TextView email;
    TextView oneDayfees;
    TextView twoDayfees;
    TextView oneWeekfees;
    TextView oneMonthfees;
    TextView oneYearfees;
    TextView morningBatchTimeOne;
    TextView morningBatchTimeTwo;
    TextView eveningBatchTimeOne;
    TextView eveningBatchTimeTwo;
    TextView gender;
    TextView city;
    TextView address;
    Button btnDietChart;

    Context context = DetailActivity.this;
    ImageView imageView;
    SharePref shrObj;
    String dietChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView = (ImageView) findViewById(R.id.gymImage);
        btnDietChart  = (Button) findViewById(R.id.btnDietChart);

        Bundle bundle = getIntent().getExtras();

        int gymId = bundle.getInt("gymId");

        gymName = (TextView) findViewById(R.id.gymName);
        mobile = (TextView) findViewById(R.id.mobile);
        address = (TextView) findViewById(R.id.address);
        city = (TextView) findViewById(R.id.city);
        gender = (TextView) findViewById(R.id.gender);
        eveningBatchTimeTwo = (TextView) findViewById(R.id.eveningBatchTimeTwo);
        eveningBatchTimeOne = (TextView) findViewById(R.id.eveningBatchTimeOne);
        morningBatchTimeTwo = (TextView) findViewById(R.id.morningBatchTimeTwo);
        morningBatchTimeOne = (TextView) findViewById(R.id.morningBatchTimeOne);
        oneYearfees = (TextView) findViewById(R.id.oneYearfees);
        oneMonthfees = (TextView) findViewById(R.id.oneMonthfees);
        oneWeekfees = (TextView) findViewById(R.id.oneWeekfees);
        twoDayfees = (TextView) findViewById(R.id.twoDayfees);
        oneDayfees = (TextView) findViewById(R.id.oneDayfees);
        email = (TextView) findViewById(R.id.email);

        final ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        shrObj = new SharePref();

        Retro.getInterface(context).gymDetail(gymId, new Callback<GymResponse>() {
            @Override
            public void success(GymResponse gymResponse, Response response) {

                progressDialog.dismiss();

                if (gymResponse.getResult().equalsIgnoreCase("success")) {

                    String image_url = "http://" + shrObj.getServerURL(context) + "/Gym/GymImages/" + gymResponse.getFileName();
                    Picasso.with(context).load(image_url).into(imageView);
                    dietChart = gymResponse.getDietFileName();
                    /*try {
                        dietChart = gymResponse.getDietFileName();

                        //int loader = R.drawable.loader;

                        // Imageview to show

                        // Image url


                        // ImageLoader class instance
                        ImageLoader imgLoader = new ImageLoader(getApplicationContext());

                        // whenever you want to load an image from url
                        // call DisplayImage function
                        // url - image url to load
                        // loader - loader image, will be displayed before getting image
                        // image - ImageView
                        imgLoader.DisplayImage(image_url, loader, imageView);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Image loading Exception", Toast.LENGTH_SHORT).show();

                    }*/

                    gymName.setText("Name: " + gymResponse.getGymName());
                    mobile.setText("Mobile: " + gymResponse.getMobile());
                    address.setText("Address: " + gymResponse.getAddress());
                    city.setText("City: " + gymResponse.getCity());
                    eveningBatchTimeTwo.setText("Evening Batch One : " + gymResponse.getEveningBatchTimeTwo());
                    eveningBatchTimeOne.setText("Evening Batch Two : " + gymResponse.getEveningBatchTimeOne());
                    morningBatchTimeTwo.setText("Morning Batch One: " + gymResponse.getMorningBatchTimeTwo());
                    morningBatchTimeOne.setText("Morning Batch Two: " + gymResponse.getMorningBatchTimeOne());
                    oneYearfees.setText("Yearly Fees: " + gymResponse.getOneYearfees());
                    oneMonthfees.setText("Monthly Fees: " + gymResponse.getOneMonthfees());
                    oneWeekfees.setText("Weekly Fees: " + gymResponse.getOneWeekfees());
                    twoDayfees.setText("Two Day Fees: " + gymResponse.getTwoDayfees());
                    oneDayfees.setText("One Day Fees: " + gymResponse.getOneDayfees());
                    email.setText("Email : " + gymResponse.getEmail());
                    gender.setText("Gym type : " + gymResponse.getGender());

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }

                btnDietChart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context,DietChartActivity.class);

                        intent.putExtra("dietChart",dietChart);

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Log.e(TAG, "Retro Error as " + error.getMessage());
            }
        });


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
