package com.example.kashaf.ocm.fyp.Activities.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    Activity context;
    private final String PREF_FILE = "ocm";
    private final String LOGIN_PREF = "login_pref";
    private final String ID = "id";
    private final String ELECTRICITY_PREF = "pref";
    private final String NAME = "firstName";
    //private final String LAST_NAME = "lastName";
    private final String EMAIL = "email";
    private final String PHONE_NO = "city";
    private final String CNIC_NO = "cnic";
    private final String TYPE = "type";
    // private final String GENDER = "gender";
    // private final String PROFILE_URL = "profile_url";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * function for saving teacher screen status
     *
     * @param _context     Activity
     * @param login_status 1 or 0
     */
    public void loginPreference(Activity _context, int login_status) {
        context = _context;
        sharedPreferences = _context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt(LOGIN_PREF, login_status);
        editor.apply();
        editor.commit();
    }

    /**
     * function for saving electricity distributor
     *
     * @param _context
     * @param pref
     */
    public void saveElectricityPref(Activity _context, String pref) {
        context = _context;
        sharedPreferences = _context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(ELECTRICITY_PREF, pref);
        editor.apply();
        editor.commit();
    }


    public String getUserID(Activity _context) {
        sharedPreferences = _context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(ID, "");
        return id;
    }


    /**
     * @param _context Activity
     * @return 1 or 0
     */
    public int getLoginPreference(Activity _context) {
        sharedPreferences = _context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        int getActivityPrefConstant = sharedPreferences.getInt(LOGIN_PREF, 0);
        return getActivityPrefConstant;
    }

    public void saveUserData(Activity _context,
                             String id,
                             String name,
                             String email,
                             String phoneNo) {

        context = _context;
        sharedPreferences = _context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(ID, id);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(PHONE_NO, phoneNo);
        editor.apply();
        editor.commit();

    }

    public void saveMechanicData(Activity _context,
                                 String id,
                                 String name,
                                 String email,
                                 String phoneNo,
                                 String cnicNo,
                                 String type) {

        context = _context;
        sharedPreferences = _context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(ID, id);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(PHONE_NO, phoneNo);
        editor.putString(CNIC_NO, cnicNo);
        editor.putString(TYPE, type);
        editor.apply();
        editor.commit();

    }


    public void clearAllPreference(Activity _context) {
        SharedPreferences clearSettings = _context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        clearSettings.edit().clear().apply();
    }
}
