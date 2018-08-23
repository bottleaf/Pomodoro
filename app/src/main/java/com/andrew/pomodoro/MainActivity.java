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

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {
    public static final long STANDARD_POMODORO_LENGTH = (long) 1.5e6;
    public static final long TEST_POMODORO_LENGTH = 3000;
    private Context mContext;
    private TextView countdownText;
    private Button countdownButton, resetButton;
    private CountDownTimer countdownTimer;
    private long timeLeftInMilliseconds = STANDARD_POMODORO_LENGTH;
    private long resetTimeLeft = timeLeftInMilliseconds;
    private boolean timerRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        countdownText = findViewById(R.id.countdown_text);
        countdownButton = findViewById(R.id.countdown_button);
        resetButton = findViewById(R.id.reset_button);

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        resetTimer();
    }

    private void resetTimer() {
        timeLeftInMilliseconds = resetTimeLeft;
        updateTimer();
        resetButton.setVisibility(INVISIBLE);
        countdownButton.setVisibility(VISIBLE);
        countdownButton.setText("Start A New Pomodoro");
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
        countdownButton.setText("Continue This Pomodoro");
        resetButton.setVisibility(VISIBLE);
        timerRunning = false;
    }

    private void startTimer() { //pausing and restarting actually creates a new timer
        countdownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                //Todo: play alarm
                timeLeftInMilliseconds = 0;
                updateTimer();
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                Ringtone ringtone = RingtoneManager.getRingtone(mContext,uri);
                ringtone.play();
                countdownButton.setVisibility(INVISIBLE);
                resetButton.setVisibility(VISIBLE);
                timerRunning = false;
            }
        }.start();
        countdownButton.setText("Pause");
        resetButton.setVisibility(INVISIBLE);
        timerRunning = true;
    }

    private void updateTimer() {
        int timerDisplayMinutes = (int) timeLeftInMilliseconds / 60000;
        int timerDisplaySeconds = (int) timeLeftInMilliseconds % 60000 / 1000;
        String timeLeftText = String.format("%02d", timerDisplayMinutes) + ":" + String.format("%02d", timerDisplaySeconds);
        countdownText.setText(timeLeftText);
    }
}
