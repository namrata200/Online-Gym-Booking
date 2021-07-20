package com.example.test.gym.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.gym.R;
import com.example.test.gym.retro.GeneralResponse;
import com.example.test.gym.retro.GymResponse;
import com.example.test.gym.retro.Retro;
import com.example.test.gym.util.Gym;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.ContentValues.TAG;

public class HomeActivity extends AppCompatActivity{

    Context context = HomeActivity.this;
    String gymCity;
    List<String> city;
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    ListView listView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        city = new ArrayList<String>();

        listView=(ListView)findViewById(R.id.city_list);
        textView=(TextView)findViewById(R.id.textViewCity);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, city);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                gymCity=adapter.getItem(position);
                Intent intent = new Intent(context, ViewGymActivity.class);

                intent.putExtra("area", "city");
                intent.putExtra("gymCity", gymCity);

                startActivity(intent);
            }
        });
        final ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        Retro.getInterface(context).allGyms(0.0, 0.0, new Callback<GeneralResponse>() {
            @Override
            public void success(GeneralResponse generalResponse, Response response) {

                progressDialog.dismiss();

                if (generalResponse.getResult().equalsIgnoreCase("success")) {

                    GymResponse gymResponse[] = generalResponse.getJarray();

                    for (int i = 0; i < gymResponse.length; i++) {
                        city.add(gymResponse[i].getCity());
                    }

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