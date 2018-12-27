package com.example.muhammedraheezrahman.mccollinsmedia.PreferanceManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.muhammedraheezrahman.mccollinsmedia.Model.LoginDetails;
import com.google.gson.Gson;

public class PrefManager {

    Context context;
    static public String PregerenceName = "UserPreference";

    public PrefManager(Context context) {
        this.context = context;
    }

    public void saveEmail(String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PregerenceName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.commit();
    }

    public void saveUserId(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PregerenceName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserId", id);
        editor.commit();
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PregerenceName, 0);
        return sharedPreferences.getString("Email", "");
    }


    public String getUserId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PregerenceName, 0);
        return sharedPreferences.getString("UserId", "");
    }
    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", 0);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }

    public void saveObjectToSharedPreference(Context context, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PregerenceName, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString("userDetail", serializedObject);
        sharedPreferencesEditor.apply();
    }

    public LoginDetails.User getSavedObjectFromPreference(Context context, Class<LoginDetails.User> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PregerenceName, 0);
        if (sharedPreferences.contains("userDetail")) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString("userDetail", ""), LoginDetails.User.class);
        }
        return null;
    }
}