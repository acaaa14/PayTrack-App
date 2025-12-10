package com.example.tagihan123.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;

    private static final String PREF_NAME = "tagihan_session";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_PHONE = "user_phone";

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserId(int id) {
        prefs.edit().putInt(KEY_USER_ID, id).apply();
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public void setName(String name) {
        prefs.edit().putString(KEY_NAME, name).apply();
    }

    public String getName() {
        return prefs.getString(KEY_NAME, "");
    }

    public void setEmail(String email) {
        prefs.edit().putString(KEY_EMAIL, email).apply();
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }

    public void setPhone(String phone) {
        prefs.edit().putString(KEY_PHONE, phone).apply();
    }

    public String getPhone() {
        return prefs.getString(KEY_PHONE, "");
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }

    public void logout() {
        prefs.edit().clear().apply();
    }
}
