package com.example.kanthi.projectmonitoring.Utils;

import android.app.Application;
import android.util.Log;

public class GlobalTextChange extends Application {
    private static GlobalTextChange globalTextChange;

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "lato.ttf");
    }
    /*public static GlobalTextChange getInstance(){
        if(globalTextChange == null){
            globalTextChange = new GlobalTextChange();
        }
        return globalTextChange;
    }*/
}
