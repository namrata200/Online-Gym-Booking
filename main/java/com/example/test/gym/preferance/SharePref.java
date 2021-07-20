package com.example.test.gym.preferance;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharePref {

    private static final String KEY_PREF = "key_pref";
    private static final String KEY_PREFERENCE = "key_preference";
    private static final String KEY_SERVER_URL = "key_server_url";
    private static final String KEY_NAME = "key_name";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_PASSWORD = "key_password";
    private static final String KEY_MOBILE = "key_mobile";
    private static final String KEY_ID = "key_id";
    private static final String KEY_IS_LOGIN = "key_is_login";

    public void login(Context context, String name, String userId, boolean isLogin) {
        SharedPreferences shrObj = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shrObj.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_ID, userId);
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.apply();
    }

    public void setServerURL(Context context, String url) {
        SharedPreferences shrObj = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shrObj.edit();
        editor.putString(KEY_SERVER_URL, url);
        editor.apply();
    }

    public String getServerURL(Context context) {
        SharedPreferences shrObj = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shrObj.edit();
        Log.e("###","PreferenceIP is "+shrObj.getString(KEY_SERVER_URL,""));
        return shrObj.getString(KEY_SERVER_URL,"192.168.1.201:8080");
    }

    public String getUserId(Context context) {
        SharedPreferences shrObj = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shrObj.edit();
        Log.e("###","UserId is "+shrObj.getString(KEY_ID,""));
        return shrObj.getString(KEY_ID,"");
    }
}
