package com.person.tzc.adkiller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.person.tzc.adkiller.service.CleanService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                start();
                break;
            case R.id.stop:
                stop();
                break;
        }
    }

    private void start() {
        Intent intent = new Intent(this, CleanService.class);
        intent.setFlags(Common.START_SCAN);
        startService(intent);

    }

    private void stop() {
        Intent intent = new Intent(this, CleanService.class);
        intent.setFlags(Common.STOP_SCAN);
        startService(intent);
    }
}
