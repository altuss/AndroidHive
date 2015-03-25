package com.irvin.sharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by root on 3/25/15.
 */
public class SessionManagement {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public SessionManagement(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String name, String email) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);

        editor.commit();
    }

    /**
     * Get stored data
     */
    public HashMap<String,String> getUserDetails() {
        HashMap<String,String> user = new HashMap<>();

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        return user;
    }

    /**
     * Check Login
     */
    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent intent = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
    }

    /**
     * Get login state
     */
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Logout user
     */
    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(intent);
    }
}
