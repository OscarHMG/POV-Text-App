package com.rayzem.pov_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class Pixel extends View {
    private int on;
    Paint paint = new Paint();

    public Pixel(Context context) {
        super(context);
        this.on = 0;
    }

    public Pixel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.on = 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(this.on == 1){
            canvas.drawColor(ContextCompat.getColor(getContext(),R.color.red));
        }else{
            canvas.drawColor(ContextCompat.getColor(getContext(),R.color.dark_red));
        }

    }


    public int getON() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
        invalidate();
    }
}
