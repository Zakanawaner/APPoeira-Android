package com.onethousandprojects.appoeira.commonThings;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";
    private SharedPreferencesManager(){}
    private static SharedPreferences getSharedPreferences() {
        return MyApp.getContext().getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }
    public static void setStringValue(String dataLabel, String dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel, dataValue);
        editor.apply();
    }
    public static void setIntegerValue(String dataLabel, Integer dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(dataLabel, dataValue);
        editor.apply();
    }
    public static void setBooleanValue(String dataLabel, boolean dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(dataLabel, dataValue);
        editor.apply();
    }
    public static String getStringValue(String dataLabel) {
        return getSharedPreferences().getString(dataLabel, null);
    }
    public static Integer getIntegerValue(String dataLabel) {
        return getSharedPreferences().getInt(dataLabel, 0);
    }
    public static boolean getBooleanValue(String dataLabel) {
        return getSharedPreferences().getBoolean(dataLabel, false);
    }
    public static void clearValues() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
    }
}
