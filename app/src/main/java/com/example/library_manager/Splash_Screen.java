package com.example.library_manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //-- ProgressBar --//
        ProgressBar progressBar= findViewById(R.id.progressBar);
        Timer t = new Timer();
        TimerTask T = new TimerTask() {
            @Override
            public void run() {
                int current = progressBar.getProgress();
                if (current < progressBar.getMax()) {
                    current += 4;
                    progressBar.setSecondaryProgress(current + 8);
                    progressBar.setProgress(current);
                } else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                    t.cancel();
                }
            }
        };
        t.schedule(T, 0, 100);
    }
}