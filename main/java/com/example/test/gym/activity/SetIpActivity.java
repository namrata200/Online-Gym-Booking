package com.example.test.gym.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.gym.R;
import com.example.test.gym.preferance.SharePref;

public class SetIpActivity extends AppCompatActivity {

    EditText setIp;
    Button btnSubmit;
    SharePref shrObj;
    Context context = SetIpActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ip);

        setIp=(EditText)findViewById(R.id.setIpPort);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        shrObj= new SharePref();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String ip = setIp.getText().toString();

            shrObj.setServerURL(context,ip );
                startActivity(new Intent(context,LoginActivity.class));
            }
        });
    }
}
