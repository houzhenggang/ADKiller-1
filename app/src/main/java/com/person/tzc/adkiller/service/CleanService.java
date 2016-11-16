package com.person.tzc.adkiller.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import com.person.tzc.adkiller.Common;
import com.person.tzc.adkiller.Logger;

import java.util.List;

/**
 * Created by root on 11/14/16.
 */

public class CleanService extends Service {

    private static final String TAG = "CleanService";

    private Handler mRemoveHandler = new Handler(Looper.getMainLooper());
    private boolean mIsScan = false;

    private View mRootView;

    private static final int TIME = 1000 * 3;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent == null) {
//            Logger.d(TAG, "START_STICKY");
//            stratScan();
//            return START_STICKY;
//        }
//        Logger.d(TAG, "onStartCommand: intent.getFlags():" + intent.getFlags() + "  flags:" + flags);
//        switch (intent.getFlags()) {
//            case Common.START_SCAN:
//                stratScan();
//                break;
//            case Common.STOP_SCAN:
//                stopScan();
//                break;
//        }
        return START_STICKY;
    }

    private void startScan() {
        Logger.d(TAG, "stratScan");
        if (mIsScan) {
            Logger.d(TAG, "scanning...");
            return;
        }
        mIsScan = true;
        mRemoveHandler.postDelayed(checkRunnable, TIME);
    }

    private void stopScan() {
        Logger.d(TAG, "stopScan");
        mIsScan = false;
        mRemoveHandler.removeCallbacks(checkRunnable);
    }

    private Runnable checkRunnable = new Runnable() {
        @Override
        public void run() {
            Logger.d(TAG, "run");
            if (mIsScan) {
                Logger.d(TAG, "scan");
                cleanAd();
                mRemoveHandler.postDelayed(checkRunnable, TIME);
            }
        }
    };

    private void cleanAd() {
        Logger.d(TAG, "cleanAd");
        getAdView();
    }

    private View getAdView() {
        Logger.d(TAG, "mRootView:" + mRootView.getClass().getName());
        getParentView(mRootView);
//        if (mRootView.getParent() != null) {
//            Logger.d(TAG, "mRootView:getParent " + mRootView.getRootView().getClass().getName());
//        } else {
//            Logger.d(TAG, "mRootView:getParent null");
//        }
//        if (mRootView instanceof ViewGroup) {
//            getChildren((ViewGroup) mRootView);
//        } else {
//            Logger.d(TAG, "mRootView:" + mRootView.getClass().getName());
//        }

        return null;
    }

    public void getParentView(View view) {
        Logger.d(TAG, "p:" + view.getClass().getName());
        ViewParent p = view.getParent();

        Logger.d(TAG, "pp:" + p.getClass().getName());
    }

    private void getChildren(ViewGroup viewGroup) {
        Logger.d(TAG, "getChildren");
        int len = viewGroup.getChildCount();

        for (int i = 0; i < len; i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                getChildren((ViewGroup) viewGroup.getChildAt(i));
            } else {
                String name = viewGroup.getChildAt(i).getClass().getSimpleName();
                Logger.d(TAG, "view  --  " + name);
            }
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CleanBinder();
    }

    public class CleanBinder extends Binder {

        public void setRootView(View view) {
            mRootView = view;
        }

        public View getRootView() {
            return mRootView;
        }

        public void toStartScan() {
            startScan();
        }

        public void toStopScan() {
            stopScan();
        }

    }

    private void isHome() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        ComponentName name = rti.get(0).topActivity;
    }
}
