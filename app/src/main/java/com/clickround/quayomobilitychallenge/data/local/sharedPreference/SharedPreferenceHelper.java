package com.clickround.quayomobilitychallenge.data.local.sharedPreference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class SharedPreferenceHelper {

    private final String SHARE_USER_INFO = "bilani_trans";

    private final String SHARE_UNIQUE_ID = "unique_id";
    private final String SHARE_RUN_ONLY_ONCE = "only_once";
    private final String SHARE_ACCESS_TOKEN = "access_token";
    private final String SHARE_PRINTER_MAC_ADDRESS = "printer_mac_address";
    private final String SHARE_USER = "user_data";
    private final String SHARE_ZONES = "zones";
    private final String LANGUAGE = "lang";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    @SuppressLint("CommitPrefEdits")
    private SharedPreferenceHelper(@NotNull Context context) {
        preferences = context.getSharedPreferences(SHARE_USER_INFO, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
    }

    private static SharedPreferenceHelper instance;

    public static SharedPreferenceHelper getInstance(Context context) {
        if (instance == null)
            instance = new SharedPreferenceHelper(context);
        return instance;
    }

    public void saveUniqueId(String s) {
        editor.putString(SHARE_UNIQUE_ID, s);
        editor.apply();
    }

    public String getUniqueId() {
        return preferences.getString(SHARE_UNIQUE_ID, null);
    }

    public void savePrinterMacAddress(String s) {
        editor.putString(SHARE_PRINTER_MAC_ADDRESS, s);
        editor.apply();
    }

    public String getPrinterMacAddress() {
        return preferences.getString(SHARE_PRINTER_MAC_ADDRESS, null);
    }

    public void saveOnlyOneTime(boolean firstRun) {
        editor.putBoolean(SHARE_RUN_ONLY_ONCE, firstRun);
        editor.apply();
    }

    public boolean getIsFirstRun() {
        return preferences.getBoolean(SHARE_RUN_ONLY_ONCE, false);
    }

    public void saveAccessToken(String s) {
        editor.putString(SHARE_ACCESS_TOKEN, s);
        editor.apply();
    }

    public String getUserAccessToken() {
        return preferences.getString(SHARE_ACCESS_TOKEN, "");
    }

    public boolean isLogin() {
        return preferences.getString(SHARE_ACCESS_TOKEN, null) != null;
    }

    public void clearAccessToken() {
        editor.remove(SHARE_ACCESS_TOKEN).apply();
    }

    public String getLanguage() {
        return preferences.getString(LANGUAGE, null);
    }

    public void setLanguage(String lang) {
        editor.putString(LANGUAGE, lang).apply();
    }
}
