package com.person.tzc.adkiller;

import android.util.Log;

/**
 * Created by root on 11/14/16.
 */

public class Logger {

    private static final String TAG = "CleanApplcation";

    private static final boolean DEBUG = true;

    public static void d(String tag, String msg) {
        if(DEBUG){
            Log.d(TAG, tag + " ---- " + msg);
        }
    }
}
