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
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.gym.MainActivity;
import com.example.test.gym.R;
import com.example.test.gym.preferance.SharePref;
import com.example.test.gym.retro.LoginResponse;
import com.example.test.gym.retro.Retro;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText email;
    EditText password;
    TextView register;
    private String TAG = LoginActivity.class.getSimpleName();
    SharePref shrObj;

    Context context = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        shrObj = new SharePref();

        shrObj.setServerURL(context,"192.168.84.232:8082" );
        btnLogin = (Button) findViewById(R.id.btnLogin);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.textReg);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();

                if (strEmail.equals(""))
                    Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                else if (strPassword.equals(""))
                    Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                else if (strPassword.length() < 6)
                    Toast.makeText(context, "Password cannot be less than 6", Toast.LENGTH_SHORT).show();
                else
                    doLogin(strEmail, strPassword);

            }
        });

    }

    public void doLogin(String email, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        Retro.getInterface(context).login(email, password, new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {

                if (loginResponse.getResult().equalsIgnoreCase("success")) {

                    progressDialog.dismiss();

                    Toast.makeText(context, loginResponse.getName()+"  "+loginResponse.getUserId(), Toast.LENGTH_SHORT).show();

                    shrObj.login(context, loginResponse.getName(), loginResponse.getUserId(), true);
                    startActivity(new Intent(context, HomeActivity.class));
                } else
                {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Log.e(TAG, "Error as " + error.getMessage());
            }
        });
    }

   /* boolean checkLogin()
    {
        if(SharePref.get)
            return  true;

            return false;
    }*/


}
