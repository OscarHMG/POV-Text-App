package com.rayzem.pov_text;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Pixel p1, p2, p3,p4, p5, p6, p7, p8;
    Pixel [] pixels;
    int letterSpace = 6;
    long dotTime = 1;

    static int cont = 0, indexChar = 0, contNumWordTimes = 0, indexWord = 0;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler();
    private boolean firstTime = true;
    String word = "HELLO WORLD";
    String pov_text = "";
    String[] splitted_pov_text;

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
        splitted_pov_text = word.split("\\s+");


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
        //startTimerPool();
        //arduinoCode();
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


    private void startTimerPool(){

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

                            Log.i("OSCAR", "TERMINE LA LETRA : " + pov_text.charAt(indexChar));

                            //Aqui termina una letra completa, voy a la siguiente letra. Pero pregunto primero si
                            //alcance el limite de la cadena de texto


                            /*try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }*/



                            if (indexChar == pov_text.length() - 1) {
                                indexChar = 0;
                                Log.i("OSCAR", "TERMINE TODA LA PALABRA");
                                try {
                                    Thread.sleep(6);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                indexChar = indexChar + 1;
                            }


                            result = LEDPattern.getPatternLetter(pov_text.charAt(indexChar));

                        } else {
                            //PARCHE: Solo entrara la primera vez que inice el juego con el texto.
                            if (firstTime) {
                                result = LEDPattern.getPatternLetter(pov_text.charAt(indexChar));
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

        exec.scheduleAtFixedRate(timerTask, 0, 1, TimeUnit.MILLISECONDS);

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

                            Log.i("OSCAR", "TERMINE LA LETRA : " + pov_text.charAt(indexChar));

                            //Aqui termina una letra completa, voy a la siguiente letra. Pero pregunto primero si
                            //alcance el limite de la cadena de texto

                            try {
                                Thread.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            if (indexChar == pov_text.length() - 1) {
                                indexChar = 0;
                                Log.i("OSCAR", "TERMINE TODA LA PALABRA");
                            } else {
                                indexChar = indexChar + 1;
                            }


                            result = LEDPattern.getPatternLetter(pov_text.charAt(indexChar));

                        } else {
                            //PARCHE: Solo entrara la primera vez que inice el juego con el texto.
                            if (firstTime) {
                                result = LEDPattern.getPatternLetter(pov_text.charAt(indexChar));
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



    }


    private void startTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run(){
                        int y;


                        if (cont > 33) {
                            setOffLeds();
                            setOffLeds();
                            setOffLeds();
                            setOffLeds();
                            /*for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }

                            for (y = 0; y < 8; y++) {

                                pixels[y].setOn(0);
                            }*/


                            cont = 0;


                            Log.i("OSCAR", "TERMINE LA LETRA : " + pov_text.charAt(indexChar));

                            //Aqui termina una letra completa, voy a la siguiente letra. Pero pregunto primero si
                            //alcance el limite de la cadena de texto


                            if (indexChar == pov_text.length() - 1 ) {
                                indexChar = 0;
                                if(contNumWordTimes == 100){ //TIME TO ADVANCE ANOTHER WORD
                                    if(indexWord == splitted_pov_text.length - 1)
                                        indexWord = 0;
                                    else
                                        indexWord = indexWord + 1;

                                    contNumWordTimes = 0;
                                    pov_text = splitted_pov_text[indexWord];
                                    Log.i("OSCAR", "TERMINE TODA LA PALABRA - AVANZO CON "+pov_text);
                                }

                                contNumWordTimes = contNumWordTimes + 1;

                            } else
                                indexChar = indexChar + 1;

                            result = LEDPattern.getPatternLetter(pov_text.charAt(indexChar));

                        } else {
                            //PARCHE: Solo entrara la primera vez que inice el juego con el texto.
                            if (firstTime) {
                                pov_text = splitted_pov_text[indexWord];
                                result = LEDPattern.getPatternLetter(pov_text.charAt(indexChar));
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

        timer.schedule(timerTask, 2000, 16);

    }

    private void setOffLeds(){
        for (int y = 0; y < 8; y++)
            pixels[y].setOn(0);


    }

    private void arduinoCode(){
        Thread t = new Thread (){

            @Override
            public void run(){
                while (true){
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            int y;


                            if (cont > 33) {
                                for (y = 0; y < 8; y++) {

                                    pixels[y].setOn(0);
                                }

                                cont = 0;

                                Log.i("OSCAR", "TERMINE LA LETRA : " + pov_text.charAt(indexChar));

                                //Aqui termina una letra completa, voy a la siguiente letra. Pero pregunto primero si
                                //alcance el limite de la cadena de texto


                                try {
                                    Thread.sleep(6);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if (indexChar == pov_text.length() - 1) {
                                    indexChar = 0;
                                    Log.i("OSCAR", "TERMINE TODA LA PALABRA");
                                } else {
                                    indexChar = indexChar + 1;
                                }


                                result = LEDPattern.getPatternLetter(pov_text.charAt(indexChar));

                            } else {
                                //PARCHE: Solo entrara la primera vez que inice el juego con el texto.
                                if (firstTime) {
                                    result = LEDPattern.getPatternLetter(pov_text.charAt(indexChar));
                                    firstTime = false;
                                }

                                for (y = 0; y < 8; y++) {
                                    pixels[y].setOn(result[y + cont]);
                                }

                                cont = cont + 8;

                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                Log.i("OSCAR", "TERMINE PATRON: DEL " + cont);

                            }
                        }

                    });
                }
            }
        };
        t.start();

    }
}
