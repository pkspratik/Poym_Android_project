package com.example.kanthi.projectmonitoring.Dashboard;

import android.graphics.Path;

import com.mapbox.mapboxsdk.annotations.Marker;

import java.util.ArrayList;
import java.util.HashMap;

public interface GetPaths {
    // i am commant this code because added map box
    void onMarkerDragStart(Marker marker);

    void onMarkerDrag(Marker marker);

    void onMarkerDragEnd(Marker marker)   // i am comment this code becouse added mapbox
    ;

    void polygonPaths(ArrayList<Path> path, HashMap<Path, Integer> mPathsMap);
}
