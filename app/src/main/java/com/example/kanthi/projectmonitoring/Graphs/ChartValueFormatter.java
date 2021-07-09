package com.example.kanthi.projectmonitoring.Graphs;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Kanthi on 24-Aug-18.
 */
public class ChartValueFormatter implements IValueFormatter ,IAxisValueFormatter{

    private DecimalFormat mFormat;
    private String[] values;

    public ChartValueFormatter(String[] values) {
        this.values = values;
    }

    public ChartValueFormatter() {
        mFormat = new DecimalFormat("######.0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if(value > 0) {
            return mFormat.format(value);
        } else {
            return "";
        }
        //return mFormat.format(value);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return values[(int) value];
    }
}
