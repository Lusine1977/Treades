package com.example.lusine.treades;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Half;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button startBtn;
    private Button cancelBtn;
    private Button testSwitch;
    private ProgressBar progressBar;
    private Handler mainhandler;
    private HandlerThread workerTread;
    private Handler workerHandler;


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            doWork(10);
        }
    };

    private volatile boolean cancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startBtn = findViewById(R.id.startBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        testSwitch = findViewById(R.id.testSwitch);
        progressBar = findViewById(R.id.progressBar);

        mainhandler = new Handler(Looper.getMainLooper());
        workerTread = new HandlerThread("worker");
        workerTread.start();
        workerHandler = new Handler(workerTread.getLooper());

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel = false;
                new Thread(runnable).start();

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel = true;
            }
        });
    }


    private void doWork(final int seconds) {

        for (int i = 1; i <= seconds; i++) {
            if (cancel) {
                break;
            }
            try {
                Log.i(TAG, "doWork: " + i);
                progressBar.setProgress(progressBar.getMax() * i / seconds);
                final int finalI = i;
                final int finalI1 = i;
                mainhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        startBtn.setText(progressBar.getMax() * finalI1 / seconds + "%");
                    }
                });
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}





























