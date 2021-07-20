package com.example.test.gym.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.test.gym.R;
import com.example.test.gym.adapter.GymAdapter;
import com.example.test.gym.retro.GeneralResponse;
import com.example.test.gym.retro.GymResponse;
import com.example.test.gym.retro.Retro;
import com.example.test.gym.util.Gym;
import com.example.test.gym.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.ContentValues.TAG;


public class ViewGymActivity extends AppCompatActivity {

    Context context = ViewGymActivity.this;
    public List<Gym> gymList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GymAdapter gAdapter;
    float startYearRange = 0, startMonthRange = 0, startWeekRange = 0, endYearRange = 0, endWeekRange = 0, endMonthRange = 0;
    float startOneRange = 0, endOneRange = 0, startTwoRange = 0, endTwoRange = 0;
    String gymType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gym);

        Bundle bundle = getIntent().getExtras();

        String latitude = bundle.getString("latitude");
        String longitude = bundle.getString("longitude");
        String area = bundle.getString("area");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        gAdapter = new GymAdapter(gymList,context);
        RecyclerView.LayoutManager pLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(pLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(gAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Gym gym = gymList.get(position);
                Toast.makeText(getApplicationContext(), gym.getGymName() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("gymId", gym.getGymId());

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        final ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        if (area.equalsIgnoreCase("near")) {
            Retro.getInterface(context).nearByGym(Double.parseDouble(latitude), Double.parseDouble(longitude), new Callback<GeneralResponse>() {
                @Override
                public void success(GeneralResponse generalResponse, Response response) {

                    progressDialog.dismiss();

                    if (generalResponse.getResult().equalsIgnoreCase("success")) {

                        GymResponse gymResponse[] = generalResponse.getJarray();

                        for (int i = 0; i < gymResponse.length; i++) {
                            Gym gym = new Gym(gymResponse[i].getGymId(), gymResponse[i].getGymName(), gymResponse[i].getAddress(), gymResponse[i].getFileName());
                            gymList.add(gym);
                        }
                        gAdapter.notifyDataSetChanged();

                    } else
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.e(TAG, "Error as " + error.getMessage());
                }
            });
        } else if (area.equalsIgnoreCase("all")) {
            Retro.getInterface(context).allGyms(Double.parseDouble(latitude), Double.parseDouble(longitude), new Callback<GeneralResponse>() {
                @Override
                public void success(GeneralResponse generalResponse, Response response) {

                    progressDialog.dismiss();

                    if (generalResponse.getResult().equalsIgnoreCase("success")) {

                        GymResponse gymResponse[] = generalResponse.getJarray();

                        for (int i = 0; i < gymResponse.length; i++) {
                            Gym gym = new Gym(gymResponse[i].getGymId(), gymResponse[i].getGymName(), gymResponse[i].getAddress(), gymResponse[i].getFileName());
                            gymList.add(gym);
                        }
                        gAdapter.notifyDataSetChanged();

                    } else
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.e(TAG, "Error as " + error.getMessage());
                }
            });
        } else if(area.equalsIgnoreCase("filter")) {

            startYearRange = Float.parseFloat( bundle.getString("startYearRange"));
            startMonthRange =  Float.parseFloat(  bundle.getString("startMonthRange"));
            startWeekRange = Float.parseFloat(  bundle.getString("startWeekRange"));
            endYearRange  =  Float.parseFloat( bundle.getString("endYearRange"));
            endMonthRange =  Float.parseFloat( bundle.getString("endMonthRange"));
            endWeekRange = Float.parseFloat(  bundle.getString("endWeekRange"));
            startOneRange =  Float.parseFloat( bundle.getString("startOneRange"));
            endOneRange =  Float.parseFloat( bundle.getString("endOneRange"));
            startTwoRange =  Float.parseFloat( bundle.getString("startTwoRange"));
            endTwoRange =  Float.parseFloat( bundle.getString("endTwoRange"));
            gymType =  bundle.getString("gymType");

            Retro.getInterface(context).allGyms(Double.parseDouble(latitude), Double.parseDouble(longitude), new Callback<GeneralResponse>() {
                @Override
                public void success(GeneralResponse generalResponse, Response response) {

                    progressDialog.dismiss();

                    if (generalResponse.getResult().equalsIgnoreCase("success")) {

                        GymResponse gymResponse[] = generalResponse.getJarray();

                        for (int i = 0; i < gymResponse.length; i++) {
                            Gym gym = new Gym(gymResponse[i].getGymId(), gymResponse[i].getGymName(), gymResponse[i].getAddress(), gymResponse[i].getFileName());

                            if ((gymResponse[i].getGender().equalsIgnoreCase(gymType))&&(gymResponse[i].getOneYearfees() >= startYearRange && gymResponse[i].getOneYearfees() <= endYearRange) && (gymResponse[i].getOneMonthfees() >= startMonthRange && gymResponse[i].getOneMonthfees() <= endMonthRange) && (gymResponse[i].getOneWeekfees() >= startWeekRange && gymResponse[i].getOneWeekfees() <= endWeekRange)&&(gymResponse[i].getOneDayfees() >= startOneRange && gymResponse[i].getOneDayfees() <= endOneRange) && (gymResponse[i].getTwoDayfees() >= startTwoRange && gymResponse[i].getTwoDayfees() <= endTwoRange)) {
                                gymList.add(gym);
                            }
                        }
                        gAdapter.notifyDataSetChanged();

                    } else
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.e(TAG, "Error as " + error.getMessage());
                }
            });

        }

        else if(area.equalsIgnoreCase("city")) {
           final String gymCity = bundle.getString("gymCity");

            Retro.getInterface(context).allGyms(0.0, 0.00, new Callback<GeneralResponse>() {
                @Override
                public void success(GeneralResponse generalResponse, Response response) {

                    progressDialog.dismiss();

                    if (generalResponse.getResult().equalsIgnoreCase("success")) {

                        GymResponse gymResponse[] = generalResponse.getJarray();

                        for (int i = 0; i < gymResponse.length; i++) {
                            Gym gym = new Gym(gymResponse[i].getGymId(), gymResponse[i].getGymName(), gymResponse[i].getAddress(), gymResponse[i].getFileName());

                            if ((gymResponse[i].getCity().equalsIgnoreCase(gymCity))){
                                gymList.add(gym);
                            }
                        }
                        gAdapter.notifyDataSetChanged();

                    } else
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.e(TAG, "Error as " + error.getMessage());
                }
            });

        }

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
