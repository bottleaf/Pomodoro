package com.andrew.pomodoro;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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
    private Button countdownButton, resetButton, breakButton;
    private CountDownTimer countdownTimer;
    private MediaPlayer mediaPlayer;
    private long timeLeftInMilliseconds = TEST_POMODORO_LENGTH;
    private long resetTimeLeft = timeLeftInMilliseconds;
    private boolean timerRunning, pomodoroCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        countdownText = findViewById(R.id.countdown_text);
        countdownButton = findViewById(R.id.countdown_button);
        resetButton = findViewById(R.id.reset_button);
        breakButton = findViewById(R.id.break_button);

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

//        breakButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pomodoroCompleted = false;
//                sendMessage(breakButton);
//            }
//        });


        resetTimer();
    }

    private void resetTimer() {
        timeLeftInMilliseconds = resetTimeLeft;
        updateTimer();
        resetButton.setVisibility(INVISIBLE);
        if (pomodoroCompleted) {
            breakButton.setVisibility(VISIBLE);
        } else {
            breakButton.setVisibility(INVISIBLE);
        }
        countdownButton.setVisibility(VISIBLE);
        countdownButton.setText("Start A New Pomodoro");
        if (mediaPlayer != null) { mediaPlayer.pause();}
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
        breakButton.setVisibility(INVISIBLE);
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
                timeLeftInMilliseconds = 0;
                updateTimer();
                countdownButton.setVisibility(INVISIBLE);
                resetButton.setVisibility(VISIBLE);
                breakButton.setVisibility(VISIBLE);
                timerRunning = false;
                pomodoroCompleted = true;
                mediaPlayer = MediaPlayer.create(mContext, R.raw.alarm_drum);
                mediaPlayer.start();
            }
        }.start();
        countdownButton.setText("Pause");
        resetButton.setVisibility(INVISIBLE);
        breakButton.setVisibility(INVISIBLE);
        timerRunning = true;
        pomodoroCompleted = false;
    }

    private void updateTimer() {
        int timerDisplayMinutes = (int) timeLeftInMilliseconds / 60000;
        int timerDisplaySeconds = (int) timeLeftInMilliseconds % 60000 / 1000;
        String timeLeftText = String.format("%02d", timerDisplayMinutes) + ":" + String.format("%02d", timerDisplaySeconds);
        countdownText.setText(timeLeftText);
    }
    /*Called when timer is reset after pomodoro is completed to start a break timer */
    public void launchDisplayBreakTimerActivity(View view) {
        Intent intent = new Intent(MainActivity.this, DisplayBreakTimerActivity.class);
        startActivity(intent);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, testLaunchActivity.class);
        startActivity(intent);
    }

}
