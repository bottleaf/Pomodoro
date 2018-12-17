package com.andrew.pomodoro;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class launchBreakActivity extends AppCompatActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_break);
        mContext = getApplicationContext();
    }
}
