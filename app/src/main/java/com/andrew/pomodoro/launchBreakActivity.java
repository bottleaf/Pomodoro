package com.andrew.pomodoro;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

public class launchBreakActivity extends AppCompatActivity {
    private static final long STANDARD_BREAK_LENGTH = (long) 3e5;
    private static final long TEST_POMODORO_LENGTH = 3000;;
    private Context mContext;
    private TextView breakTimerText;
    private Button endBreakButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_break);
        mContext = getApplicationContext();
        breakTimerText = findViewById(R.id.break_timer_text);
        endBreakButton = findViewById(R.id.finish_break_button);
    }
}
