package com.example.kanthi.projectmonitoring.Graphs;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class CRValueFormater implements IValueFormatter {

    private DecimalFormat mformat;

    public CRValueFormater(){
        mformat=new DecimalFormat("######.0");
    }
    @Override
    public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
        if(v > 0) {
            return mformat.format(v);
        } else {
            return "";
        }
    }
}
