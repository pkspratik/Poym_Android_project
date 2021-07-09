package com.example.kanthi.projectmonitoring.ProgressView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.Sales_Area;
import com.example.kanthi.projectmonitoring.PoJo.Zones;
import com.example.kanthi.projectmonitoring.R;

import java.util.List;

/**
 * Created by Kanthi on 23/8/2018.
 */

public class SalesArea_Sp_Adapter extends BaseAdapter {
    private List<Sales_Area> msalesAreas;
    private LayoutInflater inflater;
    private Context mContext;

    public SalesArea_Sp_Adapter(Context context, List<Sales_Area> salesAreas) {
        msalesAreas = salesAreas;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return msalesAreas.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ?  null : msalesAreas.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SalesArea_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (SalesArea_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new SalesArea_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Sales_Area area = (Sales_Area) getItem(position);
        if (area != null) {
            holder.spinnerItem.setText(area.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_condition));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        SalesArea_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (SalesArea_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new SalesArea_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Sales_Area area = (Sales_Area) getItem(position);
        if (area!= null) {
            holder.spinnerItem.setText(area.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_condition));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView spinnerItem;

        public ViewHolder(View view) {
            spinnerItem= (TextView) view.findViewById(R.id.spinner_text);
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }
}