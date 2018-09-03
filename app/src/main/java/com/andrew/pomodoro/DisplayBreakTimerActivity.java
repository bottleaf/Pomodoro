package com.andrew.pomodoro;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayBreakTimerActivity extends AppCompatActivity {
    public static final long STANDARD_BREAK_LENGTH = (long) 3e5;
    public static final long TEST_POMODORO_LENGTH = 3000;
    private Context mContext;
    private TextView countDownText, titleText;
    private Button endBreakButton;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;
    private long timeLeftInMilliseconds = TEST_POMODORO_LENGTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_break_timer);

        mContext = getApplicationContext();
        countDownText = findViewById(R.id.countdown_text);
        titleText = findViewById(R.id.break_title_text);
        endBreakButton = findViewById(R.id.break_end_button);
        startBreakTimer();

        endBreakButton.setVisibility(View.INVISIBLE);
        endBreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) { mediaPlayer.stop(); }
                if (countDownTimer != null) { countDownTimer.cancel(); }
            }
        });
    }

    private void startBreakTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timeLeftInMilliseconds = 0;
                updateTimer();
                endBreakButton.setVisibility(View.VISIBLE);
                mediaPlayer = MediaPlayer.create(mContext, R.raw.alarm_drum);
                mediaPlayer.start();
            }
        }.start();
    }

    private void updateTimer() {
        int timerDisplayMinutes = (int) timeLeftInMilliseconds / 60000;
        int timerDisplaySeconds = (int) timeLeftInMilliseconds % 60000 / 1000;
        String timeLeftText = String.format("%02d", timerDisplayMinutes) + ":" + String.format("%02d", timerDisplaySeconds);
        countDownText.setText(timeLeftText);
    }
}
