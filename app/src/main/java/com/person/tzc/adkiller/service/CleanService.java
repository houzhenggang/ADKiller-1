package com.person.tzc.adkiller.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.person.tzc.adkiller.Common;
import com.person.tzc.adkiller.Logger;

/**
 * Created by root on 11/14/16.
 */

public class CleanService extends Service {

    private static final String TAG = "CleanService";

    private Handler mRemoveHandler = new Handler(Looper.getMainLooper());
    private boolean mIsScan = false;

    private View mTView;
    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(0, 0);
    private View mRootView;

    private static final int TIME = 1000 * 3;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mTView != null && mTView.getParent() != null) {
            ((ViewGroup) mTView.getParent()).removeView(mTView);
        }
        mTView = new View(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getFlags()) {
            case Common.START_SCAN:
                stratScan();
                break;
            case Common.STOP_SCAN:
                stopScan();
                break;
        }
        return START_STICKY;
    }

    private void stratScan() {
        if (mIsScan) {
            return;
        }
        mIsScan = true;
        mRemoveHandler.postDelayed(checkRunnable,TIME);
    }

    private void stopScan() {
        mIsScan = false;
        mRemoveHandler.removeCallbacks(checkRunnable);
    }

    private Runnable checkRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsScan) {
                cleanAd();
                mRemoveHandler.postDelayed(checkRunnable,TIME);
            }
        }
    };

    private void cleanAd() {
        getAdView();
    }

    private View getAdView() {
        getView();
        getChildren((ViewGroup) mRootView);
        return null;
    }

    private void getChildren(ViewGroup viewGroup) {
        int len = viewGroup.getChildCount();

        for (int i = 0; i < len; i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                getChildren((ViewGroup) viewGroup.getChildAt(i));
            } else {
                String name = viewGroup.getChildAt(i).getClass().getName();
                Logger.d(TAG, "view  --  " + name);
            }
        }

    }

    private void getView() {
        if (mRootView != null) {
            return;
        }
        if (mTView == null) {
            mTView = new View(getApplicationContext());
        }
        WindowManager wm = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        wm.addView(mTView, layoutParams);
        mRootView = mTView.getRootView();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
