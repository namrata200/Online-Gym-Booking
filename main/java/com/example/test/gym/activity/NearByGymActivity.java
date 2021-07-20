package com.example.test.gym.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test.gym.R;
import com.example.test.gym.preferance.SharePref;
import com.example.test.gym.retro.GeneralResponse;
import com.example.test.gym.retro.GymResponse;
import com.example.test.gym.retro.Retro;
import com.example.test.gym.util.LocationTrack;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.ContentValues.TAG;


public class NearByGymActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList();
    private ArrayList<String> permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    Context context = NearByGymActivity.this;

    Button btnNearGym;
    Button btnAllGym;
    Button btnfilter;


    EditText startYear;
    EditText endYear;
    EditText startMonth;
    EditText endMonth;
    EditText startWeek;
    EditText endWeek;
    EditText startOne;
    EditText endOne;
    EditText startTwo;
    EditText endTwo;
    Spinner spinner;
    String gymType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_gym);

        String[]  gymTypeList = {"Male", "Female", "Common"};


        spinner = (Spinner)findViewById(R.id.gymType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NearByGymActivity.this,
                android.R.layout.simple_spinner_item,gymTypeList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnNearGym = (Button) findViewById(R.id.nearButton);
        btnAllGym = (Button) findViewById(R.id.allButton);
        btnfilter = (Button) findViewById(R.id.filterButton);

        startYear = (EditText) findViewById(R.id.startYearlyRange);
        endYear = (EditText) findViewById(R.id.endYearlyRange);
        startMonth =(EditText) findViewById(R.id.startMonthlyRange);
        endMonth= (EditText) findViewById(R.id.endMonthlyRange);
        startWeek =(EditText) findViewById(R.id.startWeeklyRange);
        endWeek = (EditText) findViewById(R.id.endWeeklyRange);
        startOne = (EditText) findViewById(R.id.startOneRange);
        endOne   = (EditText) findViewById(R.id.endOneRange);
        startTwo   = (EditText) findViewById(R.id.startTwoRange);
        endTwo  = (EditText) findViewById(R.id.endTwoRange);

    /*    startYear.setText(String.valueOf(0));
        endYear.setText(String.valueOf(10000));
        startMonth.setText(String.valueOf(0));
        endMonth.setText(String.valueOf(10000));
        startWeek.setText(String.valueOf(0));
        endWeek.setText(String.valueOf(10000));
        startOne.setText(String.valueOf(0));
        endOne.setText(String.valueOf(10000));
        startTwo.setText(String.valueOf(0));
        endTwo.setText(String.valueOf(10000));*/

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        locationTrack = new LocationTrack(NearByGymActivity.this);


        if (locationTrack.canGetLocation()) {

            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();
            final Intent intent = new Intent(context, ViewGymActivity.class);
            intent.putExtra("latitude", Double.toString(latitude));
            intent.putExtra("longitude", Double.toString(longitude));

            btnNearGym.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intent.putExtra("area", "near");

                    startActivity(intent);
                }
            });

            btnAllGym.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intent.putExtra("area", "all");

                    startActivity(intent);
                }
            });

            btnfilter.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    intent.putExtra("area", "filter");
                    intent.putExtra("startYearRange", startYear.getText().toString());
                    intent.putExtra("startMonthRange", startMonth.getText().toString());
                    intent.putExtra("startWeekRange", startWeek.getText().toString());
                    intent.putExtra("endYearRange", endYear.getText().toString());
                    intent.putExtra("endMonthRange", endMonth.getText().toString());
                    intent.putExtra("endWeekRange", endWeek.getText().toString());
                    intent.putExtra("startOneRange", startOne.getText().toString());
                    intent.putExtra("endOneRange", endOne.getText().toString());
                    intent.putExtra("startTwoRange", startTwo.getText().toString());
                    intent.putExtra("endTwoRange", endTwo.getText().toString());
                    intent.putExtra("gymType",gymType);

                    startActivity(intent);
                }
            });

        } else {

            locationTrack.showSettingsAlert();
        }


    }


    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(NearByGymActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gymType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}