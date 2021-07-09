package com.example.kanthi.projectmonitoring.Canvas;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.kanthi.projectmonitoring.Dashboard.GetPaths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomCanvas extends View {
    private JSONArray test;
    private Paint paint;
    private String shape;
    private ArrayList<Point> points;
    private GetPaths getPaths;
    private ArrayList<Path> mPathsList;
    private HashMap<Path, Integer> mPathsMap;
    private ArrayList<Integer> mShapeIdList;
    private float mScaleFactor = 1.0f;

    public void setmScaleFactor(float mScaleFactor) {
        this.mScaleFactor = mScaleFactor;
    }

    public CustomCanvas(Context context, String shape, GetPaths getPaths, ArrayList<Integer> mShapeIdList) {
        super(context);
        this.shape = shape;
        paint = new Paint();
        paint.setColor(Color.parseColor("#084aa6"));
        points = new ArrayList<>();
        paint.setStrokeWidth(2.2f);
        paint.setStyle(Paint.Style.STROKE);
        try {
            test = new JSONArray(shape);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.getPaths = getPaths;
        mPathsList = new ArrayList<>();
        mPathsMap = new HashMap<>();
        this.mShapeIdList = mShapeIdList;

    }

    public CustomCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            setMeasuredDimension(screenWidth, screenHeight);
        }else{
            setMeasuredDimension(screenHeight, screenWidth);
        }

    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        //...
        //Your onDraw() code
        //...
        mPathsList.clear();
        mPathsMap.clear();
        for (int i = 0; i < test.length(); i++) {
            try {
                Path path = new Path();

                JSONArray cordsArr = ((JSONArray) ((JSONObject) test.get(i)).get("cords"));
                Log.i("jtesttt", "onDraw: "+cordsArr.length());

                for (int j = 0; j < cordsArr.length(); j++) {
                    Point currentPoint = new Point(new Point((int) (mScaleFactor*((int)((JSONObject) cordsArr.get(j)).get("x"))), (int) (mScaleFactor* ((int) ((JSONObject) cordsArr.get(j)).get("y")))));
                    if (j > 0 && j < cordsArr.length() - 1) {
                        Log.i("jtesttt", "onDraw: "+j);
                        path.lineTo(currentPoint.x, currentPoint.y);
                    } else if (j == cordsArr.length() - 1) {
                        Log.i("jtesttt", "onDraw: "+j);
                        path.lineTo(currentPoint.x,currentPoint.y);
                        path.lineTo(points.get(0).x, points.get(0).y);
                    } else if (j == 0) {
                        Log.i("jtesttt", "onDraw: "+j);

                        path.moveTo(currentPoint.x, currentPoint.y);
                    } else {
                        Log.i("polkioloi", "onDraw: ");
                    }
                    points.add(currentPoint);
                }
                if (mShapeIdList.contains((int) ((JSONObject) test.get(i)).get("id"))) {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                } else {
                    paint.setStyle(Paint.Style.STROKE);
                }
                canvas.drawPath(path, paint);
                Log.i("polkiol", "onDraw: " + ((JSONObject) test.get(i)).get("id"));
                mPathsMap.put(path, (int) ((JSONObject) test.get(i)).get("id"));
                mPathsList.add(path);
                points.clear();
                Log.i("pathtest", "onDraw: " + path);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getPaths.polygonPaths(mPathsList, mPathsMap);
        canvas.restore();

    }


}
