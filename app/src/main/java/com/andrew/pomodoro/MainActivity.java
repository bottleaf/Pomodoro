package com.andrew.pomodoro;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.net.Uri;
import android.media.Ringtone;
import android.media.RingtoneManager;

public class MainActivity extends AppCompatActivity {
    public static final long STANDARD_POMODORO_LENGTH = (long) 1.5e6;
    private Context mContext;
    private TextView countdownText;
    private Button countdownButton;
    private CountDownTimer countdownTimer;
    private long timeLeftInMilliseconds = 3000;
    private boolean timerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        countdownText = findViewById(R.id.countdown_text);
        countdownButton = findViewById(R.id.countdown_button);

        updateTimer();
        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });
    }

    private void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    private void stopTimer() {
        countdownTimer.cancel();
        countdownButton.setText("Start");
        timerRunning = false;
    }

    private void startTimer() {
        countdownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                //Todo: play alarm
                updateTimer();
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                Ringtone ringtone = RingtoneManager.getRingtone(mContext,uri);
                ringtone.play();
            }
        }.start();
        countdownButton.setText("Pause");
        timerRunning = true;
    }

    private void updateTimer() {
        int timerDisplayMinutes = (int) timeLeftInMilliseconds / 60000;
        int timerDisplaySeconds = (int) timeLeftInMilliseconds % 60000 / 1000;
        String timeLeftText = String.format("%02d", timerDisplayMinutes) + ":" + String.format("%02d", timerDisplaySeconds);
        countdownText.setText(timeLeftText);
    }
}
