package com.example.fundoonotes;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public static final String myPreference = "myPreference";

    public SessionManager(Context _context) {
        this.context = _context;
        sharedPreferences = this.context.getSharedPreferences(myPreference, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String username,String firstName, String lastName) {
        editor.putString("username",username);
        editor.putString("firstName",firstName);
        editor.putString("lastName",lastName);
        editor.putBoolean("is_Logged_In",true);
        editor.commit();
    }

    public void endLoginSession() {
        editor.clear();
        editor.commit();
    }

    public Boolean isLoggedIn() {
        return sharedPreferences.getBoolean("is_Logged_In", false);
    }
}
