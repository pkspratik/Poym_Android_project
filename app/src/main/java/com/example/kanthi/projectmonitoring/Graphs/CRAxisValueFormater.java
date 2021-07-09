package com.example.kanthi.projectmonitoring.Graphs;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class CRAxisValueFormater implements IAxisValueFormatter{

    private DecimalFormat mFormat;

    public CRAxisValueFormater(){
        mFormat=new DecimalFormat("######.0");
    }
    @Override
    public String getFormattedValue(float v, AxisBase axisBase) {
        if(v > 0) {
            return mFormat.format(v);
        } else {
            return "";
        }
    }
}
