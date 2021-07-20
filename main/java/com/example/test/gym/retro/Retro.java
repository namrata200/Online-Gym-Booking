package com.example.test.gym.retro;

import android.content.Context;

import com.example.test.gym.preferance.SharePref;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class Retro {

    public static String BASE_URL = "http://192.168.49.70:8080/Gym/rest/AppService/";

    public static RestAdapter getClient(Context context) {
        SharePref shrobj = new SharePref();

        BASE_URL = "http://" + shrobj.getServerURL(context) + "/Gym/rest/AppService/";
       // BASE_URL = "http://192.168.43.70:8080/Gym/rest/AppService/";

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BASE_URL).setLogLevel(RestAdapter.LogLevel.FULL).build();

        return adapter;
    }

    public static RetroInterface getInterface(Context context) {
        return getClient(context).create(RetroInterface.class);
    }

    public interface RetroInterface {

        @FormUrlEncoded
        @POST("/login")
        public void login(@Field("email") String email,@Field("password") String password, Callback<LoginResponse> response);

        @FormUrlEncoded
        @POST("/register")
        public void register(@Field("name") String name,@Field("email") String email,@Field("password") String password,@Field("mobile") String mobile ,Callback<GeneralResponse> response);

        @FormUrlEncoded
        @POST("/nearByGym")
        public void nearByGym(@Field("latitude") double latitude, @Field("longitude") double longitude, Callback<GeneralResponse> response);

        @FormUrlEncoded
        @POST("/allGyms")
        public void allGyms(@Field("latitude") double latitude, @Field("longitude") double longitude, Callback<GeneralResponse> response);




        @FormUrlEncoded
        @POST("/gymDetail")
        public void gymDetail(@Field("gymId") int gymId ,Callback<GymResponse> response);

    }
}
