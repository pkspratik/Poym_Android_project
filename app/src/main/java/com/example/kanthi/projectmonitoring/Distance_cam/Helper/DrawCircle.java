package com.example.kanthi.projectmonitoring.Distance_cam.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;

/**
 * Created by Nishant on 11/1/2017.
 */

public class DrawCircle {
    private Canvas mCanvas;


    public Bitmap drawCircleOn(Bitmap bitmap, Context context) {
        mCanvas = new Canvas(bitmap);

        Paint paint=new Paint();
        paint.setColor(Color.parseColor("#e36c09"));

        int width = AppUtilities.getScreenWidth(context);
        mCanvas.drawCircle((width / 6), width / 6, (width / 6) - (width / 15), paint);
        mCanvas.drawCircle((width / 3) + (width / 6), width / 6, (width / 6) - (width / 15),paint);
        mCanvas.drawCircle(2 * (width / 3) + (width / 6), width / 6, (width / 6) - (width / 15), paint);

        mCanvas.drawCircle((width / 6), width / 6, (width / 6) - (width / 15), paint);
        mCanvas.drawCircle((width / 3) + (width / 6), width / 6, (width / 6) - (width / 15), paint);
        mCanvas.drawCircle(2 * (width / 3) + (width / 6), width / 6, (width / 6) - (width / 15), paint);

        return bitmap;
    }
}

