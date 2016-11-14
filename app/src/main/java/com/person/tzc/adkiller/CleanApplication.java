package com.person.tzc.adkiller;

import android.app.Application;

import java.io.IOException;

/**
 * Created by root on 11/14/16.
 */

public class CleanApplication extends Application {

    private final static String TAG = "CleanApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Logger.d(TAG, "exec start");
            Process process = Runtime.getRuntime().exec("su");
            Logger.d(TAG, "exec over");
        } catch (IOException e) {
            Logger.d(TAG, "exec IOException :" + e.getMessage());
        }
    }
}
