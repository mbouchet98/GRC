package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class preferenceUtils {
    public preferenceUtils(){

    }

    public static void saveEmail(String email, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(constant.KEY_EMAIL, email);
        prefsEditor.apply();
    }

    public static String getEmail(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(constant.KEY_EMAIL, null);
    }

    public static void savePassword(String password, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(constant.KEY_PASSWORD, password);
        prefsEditor.apply();
    }

    public static String getPassword(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(constant.KEY_PASSWORD, null);
    }
}
