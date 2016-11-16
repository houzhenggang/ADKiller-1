package com.person.tzc.adkiller;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.person.tzc.adkiller.service.CleanService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private CleanService.CleanBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bind).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
        findViewById(R.id.startScan).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind:
                bind();
                break;
            case R.id.startScan:
                start();
                break;
            case R.id.stop:
                stop();
                break;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinder = (CleanService.CleanBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private void bind() {
        Logger.d(TAG, "bind");
        if (mBinder != null) {
            return;
        }
        ComponentName componentName = new ComponentName("com.person.tzc.adkiller", "com.person.tzc.adkiller.service.CleanService");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        startService(intent);
        bindService(intent, serviceConnection, 0);
    }

    private void start() {
        Logger.d(TAG, "start");
        if (mBinder == null) {
            Logger.d(TAG, "mBinder == null");
            return;
        }
        if (mBinder.getRootView() == null) {
            mBinder.setRootView(getWindow().getDecorView());
        }
        Logger.d(TAG, "toStartScan");
        mBinder.toStartScan();
    }

    private void stop() {
        Logger.d(TAG, "stop");
        if (mBinder == null) {
            bind();
        }
        mBinder.toStopScan();
    }
}
