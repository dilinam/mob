package com.myapp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME = 6000;

    private TextView txtTime;
    private Button btnStartStop;
    private Button btnReset;

    private CountDownTimer countDownTimer;

    private boolean isTimerRunning;

    private long timeLeft = START_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTime = findViewById(R.id.text_view_time);
        btnStartStop = findViewById(R.id.button_start_stop);
        btnReset = findViewById(R.id.button_reset);

        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTimerRunning){
                    if(countDownTimer != null){
                        countDownTimer.cancel();
                        isTimerRunning = false;
                        btnStartStop.setText("START");
                    }
                }else{
                    countDownTimer = new CountDownTimer(timeLeft, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timeLeft = millisUntilFinished;
                            int minutes = (int) (timeLeft / 1000) / 60;
                            int seconds = (int) (timeLeft / 1000) % 60;
                            String formattedTime = String.format("%02d:%02d", minutes, seconds);
                            txtTime.setText(formattedTime);
                        }

                        @Override
                        public void onFinish() {
                            isTimerRunning = false;
                            btnStartStop.setText("START");
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setTitle("Countdown ends");

                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                            alert.show();

                        }
                    }.start();
                    isTimerRunning = true;
                    btnStartStop.setText("STOP");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countDownTimer != null){
                    countDownTimer.cancel();
                    isTimerRunning = false;
                    btnStartStop.setText("START");
                }
                txtTime.setText("00:05");
                timeLeft = START_TIME;
            }
        });

    }
}