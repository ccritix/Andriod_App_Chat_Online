package com.anonymous.message;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Setting {
    private static final String USER_NAME = "USER_NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String CURRENT_USER = "CURRENT";
    private static final String CURRENT_PASSWORD = "CURRENT_PASSWORD";
    private static final String USER_NEW_MESSAGE = "USER_NEW_MESSAGE";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public Setting(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        editor = sharedPreferences.edit();
    }

    public String getUsername(){
        return sharedPreferences.getString(USER_NAME, "");
    }

    public void setUserName(String  userName){
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public String getPassword(){
        return sharedPreferences.getString(PASSWORD, "");
    }

    public void setPassword(String password){
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public String getCurrentUser(){
        return sharedPreferences.getString(CURRENT_USER, "");
    }

    public void setCurrentUser(String current){
        editor.putString(CURRENT_USER, current);
        editor.commit();
    }

    public String getCurrentPassword(){
        return sharedPreferences.getString(CURRENT_PASSWORD, "");
    }

    public void setCurrentPassword(String current){
        editor.putString(CURRENT_PASSWORD, current);
        editor.commit();
    }

    public String getUserNewMessage(){
        return sharedPreferences.getString(USER_NEW_MESSAGE, "");
    }

    public void setUserNewMessage(String current){
        editor.putString(USER_NEW_MESSAGE, current);
        editor.commit();
    }
}
