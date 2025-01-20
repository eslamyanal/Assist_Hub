package com.example.assisthub.Shared_pereferences_profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.assisthub.Activity.LoginActivity;
import com.example.assisthub.model.User_model;

public class SharedPrefManager {

    private static final String KEY_NAME_FILE = "SHARED_PREF_CLASS";

    // User data keys
    private static final String KEY_ID_USER = "KEY_ID_USER";
    private static final String KEY_NAME_USER = "KEY_NAME_USER";
    private static final String KEY_PHONE_USER = "KEY_PHONE_USER";
    private static final String KEY_NATIONALITY = "KEY_NATIONALITY";
    private static final String KEY_PASSWORD_USER = "KEY_PASSWORD_USER";
    private static final String KEY_NATIONALITY_OR_PASSPORT = "KEY_NATIONALITY_OR_PASSPORT";
    private static final String KEY_HEALTH_INSURANCE = "KEY_HEALTH_INSURANCE";
    private static final String KEY_FILE_NAME_USER = "KEY_FILE_NAME_USER";

    // Singleton instance and context
    private static SharedPrefManager sharedPrefManager;
    private static Context context;

    private SharedPrefManager(Context context) {
        this.context = context;
    }

    // Singleton method to ensure only one instance
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (sharedPrefManager == null) {
            sharedPrefManager = new SharedPrefManager(context);
        }
        return sharedPrefManager;
    }

    // Method to save user data to SharedPreferences
    public void userLogin(User_model userModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID_USER, userModel.getId());
        editor.putString(KEY_PHONE_USER, userModel.getPhone());
        editor.putString(KEY_NAME_USER, userModel.getName_User());
        editor.putString(KEY_PASSWORD_USER, userModel.getPassword_User());
        editor.putString(KEY_NATIONALITY, userModel.getNationality());
        editor.putString(KEY_NATIONALITY_OR_PASSPORT, userModel.getNationaltyOrPassportNumber());
        editor.putString(KEY_HEALTH_INSURANCE, userModel.getHealth_Insurance());
        editor.putString(KEY_FILE_NAME_USER, userModel.getFile_Name());

        editor.apply();
    }

    // Method to get user data from SharedPreferences
    public User_model getUserData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME_FILE, Context.MODE_PRIVATE);

        return new User_model(
                sharedPreferences.getInt(KEY_ID_USER, 0),
                sharedPreferences.getString(KEY_NAME_USER, null),
                sharedPreferences.getString(KEY_PHONE_USER, null),
                sharedPreferences.getString(KEY_NATIONALITY, null),
                sharedPreferences.getString(KEY_PASSWORD_USER, null),
                sharedPreferences.getString(KEY_NATIONALITY_OR_PASSPORT, null),
                sharedPreferences.getString(KEY_HEALTH_INSURANCE, null),
                sharedPreferences.getString(KEY_FILE_NAME_USER, null)
        );
    }

    // Method to check if the user is logged in
    public boolean isLogin() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.contains(KEY_ID_USER); // Checking if user data exists
    }

    // Method to log the user out by clearing shared preferences
    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();

        // Redirect to SplashScreen after logout
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void updateData(User_model userModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID_USER, userModel.getId());
        editor.putString(KEY_NAME_USER, userModel.getName_User());
        editor.putString(KEY_PHONE_USER, userModel.getPhone());
        editor.putString(KEY_NATIONALITY, userModel.getNationality());
        editor.putString(KEY_PASSWORD_USER, userModel.getPassword_User());
        editor.putString(KEY_NATIONALITY_OR_PASSPORT, userModel.getNationaltyOrPassportNumber());
        editor.putString(KEY_HEALTH_INSURANCE, userModel.getHealth_Insurance());
        editor.putString(KEY_FILE_NAME_USER, userModel.getFile_Name());


        editor.apply();


    }




}
