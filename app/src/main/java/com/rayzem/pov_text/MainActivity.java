package com.rayzem.pov_text;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Pixel p1, p2, p3,p4, p5, p6, p7, p8;
    int A[] = {1,1,1,1,1,1,1,1, 1,0,0,1,0,0,0,0, 1,0,0,1,0,0,0,0, 1,0,0,1,0,0,0,0, 1,1,1,1,1,1,1,1};
    Pixel [] pixels;
    int letterSpace = 6;
    int dotTime = 5;

    static int cont = 0;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

    }

    private void initUI(){
        p1 = findViewById(R.id.led_1);
        p2 = findViewById(R.id.led_2);
        p3 = findViewById(R.id.led_3);
        p4 = findViewById(R.id.led_4);
        p5 = findViewById(R.id.led_5);
        p6 = findViewById(R.id.led_6);
        p7 = findViewById(R.id.led_7);
        p8 = findViewById(R.id.led_8);

        pixels = new Pixel[8];
        pixels[0] = p1;
        pixels[1] = p2;
        pixels[2] = p3;
        pixels[3] = p4;
        pixels[4] = p5;
        pixels[5] = p6;
        pixels[6] = p7;
        pixels[7] = p8;
    }


    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void stopTimer(){
        if(timer != null){
            timer.cancel();
            timer.purge();
        }
    }


    private void startTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run(){
                        int y;
                        // printing the first y row of the letter


                        if(cont > 33) {
                            for (y=0; y<8; y++){

                                pixels[y].setOn(0);
                            }
                            //Log.i("OSCAR", "1 time finish");
                            cont = 0;
                        }else{
                            for (y=0; y<8; y++){
                                //digitalWrite(y+2, A[y]);
                                pixels[y].setOn(A[y + cont]);


                                //Log.i("OSCAR", "FOR time; " + (cont + y));

                            }

                        }

                        cont = cont + 8;


                        Log.i("OSCAR", "1 time --> " + cont);
                    }
                });
            }
        };

        timer.schedule(timerTask, 2000, dotTime);

    }
}
