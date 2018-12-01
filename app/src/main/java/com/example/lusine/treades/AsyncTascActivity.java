package com.example.lusine.treades;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class AsyncTascActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private Button startBtn;
    private Button cancelBtn;
    private Button testSwitch;
    private ProgressBar progressBar;
    private Handler mainhandler;
    private HandlerThread workerTread;
    private Handler workerHandler;
    private WorkTask workTasc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_async_tasc);
        startBtn = findViewById(R.id.startBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        testSwitch = findViewById(R.id.testSwitch);
        progressBar = findViewById(R.id.progressBar);

        mainhandler = new Handler(Looper.getMainLooper());
        workerTread = new HandlerThread("worker");
        workerTread.start();
        workerHandler = new Handler(workerTread.getLooper());
        workTasc=new WorkTask();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workTasc.execute(10);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workTasc.cancel(true);
            }
        });
    }


        private  class WorkTask extends AsyncTask<Integer,Float,Integer>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
            }

            @Override
            protected void onProgressUpdate(Float... progress) {
                progressBar.setProgress((int) (progressBar.getMax()*progress[0]));
                startBtn.setText(100*progress[0]+"%");
            }

            @Override
            protected Integer doInBackground(Integer... params) {
                int seconds = params[0];
                for (int i = 1; i <= seconds; i++) {
                    if(isCancelled()){
                        break;
                    }
                    try {
                        Log.i(TAG, "doWork: " + i);
                        Thread.sleep(1000);
                        publishProgress((float)i/seconds);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }

}