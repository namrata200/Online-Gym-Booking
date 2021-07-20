package com.example.test.gym.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.gym.MainActivity;
import com.example.test.gym.R;
import com.example.test.gym.retro.GeneralResponse;
import com.example.test.gym.retro.LoginResponse;
import com.example.test.gym.retro.Retro;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText name;
    EditText mobile;

    Button btnRegister;
    Context context = RegisterActivity.this;
    private String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();
                String strName = name.getText().toString();
                String strMobile = mobile.getText().toString();

                if (strEmail.equals(""))
                    Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                else if (strPassword.equals(""))
                    Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                else if (strName.equals(""))
                    Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                else if (strMobile.equals(""))
                    Toast.makeText(context, "Mobile cannot be empty", Toast.LENGTH_SHORT).show();
                else if(strMobile.length()!=10)
                    Toast.makeText(context, "Mobile number should be 10 digits", Toast.LENGTH_SHORT).show();
                else
                    doRegister(strName,strEmail, strPassword,strMobile);

            }
        });

    }

    public void doRegister(String name,String email, String password,String mobile) {
        final ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        Retro.getInterface(context).register(name,email, password,mobile, new Callback<GeneralResponse>() {
            @Override
            public void success(GeneralResponse generalResponse, Response response) {

                progressDialog.dismiss();
                if (generalResponse.getResult().equalsIgnoreCase("success")) {

                    startActivity(new Intent(context, LoginActivity.class));
                } else
                    Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Log.e(TAG, "Error as " + error.getMessage());
            }
        });
    }
}
