package com.example.kanthi.projectmonitoring.Graphs;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class LineChartValueFormator implements IAxisValueFormatter {

    private String[] mValues;

    public LineChartValueFormator(String[] values) {
        mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        try {
            return mValues[(int) value];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mValues[0];
    }
}