package com.whatstools.shake_Detector;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

class appPreferences {
    private SharedPreferences sharedPreferences;

    appPreferences(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public String getString(String key, String defValue) throws ClassCastException {
        return this.sharedPreferences.getString(key, defValue);
    }

    void putInt(String key, Integer value) {
        try {
            Editor prefEdit = this.sharedPreferences.edit();
            prefEdit.putInt(key, value);
            prefEdit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getInt(String key, Integer defValue) throws ClassCastException {
        return this.sharedPreferences.getInt(key, defValue);
    }

    void putBoolean(String key, Boolean defValue) {
        try {
            Editor prefEdit = this.sharedPreferences.edit();
            prefEdit.putBoolean(key, defValue);
            prefEdit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Boolean getBoolean(String key, Boolean defValue) throws ClassCastException {
        return this.sharedPreferences.getBoolean(key, defValue);
    }

    void putFloat(String key, Float defValue) {
        try {
            Editor prefEdit = this.sharedPreferences.edit();
            prefEdit.putFloat(key, defValue);
            prefEdit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Float getFloat(String key, Float defValue) throws ClassCastException {
        return this.sharedPreferences.getFloat(key, defValue);
    }
}
