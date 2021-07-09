package com.example.kanthi.projectmonitoring.Graphs;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MultilineChartValueFormator implements IAxisValueFormatter {

    private String[] mValues;

    public MultilineChartValueFormator(String[] values) {
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