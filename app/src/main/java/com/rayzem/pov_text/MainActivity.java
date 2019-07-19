package com.rayzem.pov_text;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Pixel p1, p2, p3,p4, p5, p6, p7, p8;
    Pixel [] pixels;
    int letterSpace = 6;
    long dotTime = 1;

    static int cont = 0, index = 0;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler();
    private boolean firstTime = true;
    String pov_text = "OSC";
    int result[] = null;

    ScheduledExecutorService exec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


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
        //stopTimer();
    }

    private void stopTimer(){
        if(timer != null){
            timer.cancel();
            timer.purge();
        }
    }


    private void startTimer(){

        //timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run(){

                        int y;


                        if (cont > 33) {
                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            cont = 0;

                            Log.i("OSCAR", "TERMINE LA LETRA : " + pov_text.charAt(index));

                            //Aqui termina una letra completa, voy a la siguiente letra. Pero pregunto primero si
                            //alcance el limite de la cadena de texto

                            try {
                                Thread.sleep(6);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            if (index == pov_text.length() - 1) {
                                index = 0;
                                Log.i("OSCAR", "TERMINE TODA LA PALABRA");
                            } else {
                                index = index + 1;
                            }


                            result = LEDPattern.getPatternLetter(pov_text.charAt(index));

                        } else {
                            //PARCHE: Solo entrara la primera vez que inice el juego con el texto.
                            if (firstTime) {
                                result = LEDPattern.getPatternLetter(pov_text.charAt(index));
                                firstTime = false;
                            }

                            for (y = 0; y < 8; y++) {
                                pixels[y].setOn(result[y + cont]);
                            }

                            cont = cont + 8;

                            Log.i("OSCAR", "TERMINE PATRON: DEL " + cont);

                        }

                    }
                });
            }
        };
        //https://codeday.me/es/qa/20181230/58770.html

        //https://stackoverflow.com/questions/24226933/high-frequency-android-ui-thread-updating-from-a-separate-thread

        //timer.schedule(timerTask, 2000, dotTime);

        exec = new ScheduledThreadPoolExecutor(1);
        //exec.scheduleAtFixedRate(timerTask, 0, 1, TimeUnit.MILLISECONDS);

        exec.scheduleAtFixedRate(timerTask, 10000000, 1, TimeUnit.MICROSECONDS);

       /* exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int y;


                        if (cont > 33) {
                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            cont = 0;

                            Log.i("OSCAR", "TERMINE LA LETRA : " + pov_text.charAt(index));

                            //Aqui termina una letra completa, voy a la siguiente letra. Pero pregunto primero si
                            //alcance el limite de la cadena de texto

                            try {
                                Thread.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            if (index == pov_text.length() - 1) {
                                index = 0;
                                Log.i("OSCAR", "TERMINE TODA LA PALABRA");
                            } else {
                                index = index + 1;
                            }


                            result = LEDPattern.getPatternLetter(pov_text.charAt(index));

                        } else {
                            //PARCHE: Solo entrara la primera vez que inice el juego con el texto.
                            if (firstTime) {
                                result = LEDPattern.getPatternLetter(pov_text.charAt(index));
                                firstTime = false;
                            }

                            for (y = 0; y < 8; y++) {
                                pixels[y].setOn(result[y + cont]);
                            }

                            cont = cont + 8;

                            Log.i("OSCAR", "TERMINE PATRON: DEL " + cont);

                        }

                    }


                });
            }
        }, 2000, 5, TimeUnit.NANOSECONDS);*/





        /*exec.schedule(new Runnable() {
            @Override
            public void run() {
                int y;

                Log.i("Running Task.. ", " YIKES");


                if (cont > 33) {
                    for (y = 0; y < 8; y++) {

                        pixels[y].setOn(0);
                    }

                    cont = 0;

                    Log.i("OSCAR", "TERMINE LA LETRA : " + pov_text.charAt(index));

                    //Aqui termina una letra completa, voy a la siguiente letra. Pero pregunto primero si
                    //alcance el limite de la cadena de texto

                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    if (index == pov_text.length() - 1) {
                        index = 0;
                        Log.i("OSCAR", "TERMINE TODA LA PALABRA");
                    } else {
                        index = index + 1;
                    }


                    result = LEDPattern.getPatternLetter(pov_text.charAt(index));

                } else {
                    //PARCHE: Solo entrara la primera vez que inice el juego con el texto.
                    if (firstTime) {
                        result = LEDPattern.getPatternLetter(pov_text.charAt(index));
                        firstTime = false;
                    }

                    for (y = 0; y < 8; y++) {
                        pixels[y].setOn(result[y + cont]);
                    }

                    cont = cont + 8;

                    Log.i("OSCAR", "TERMINE PATRON: DEL " + cont);

                }

            }

        }, 2000, 500, TimeUnit.MILLISECONDS);*/

    }
}
