package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.Zones;
import com.example.kanthi.projectmonitoring.R;

import java.util.List;

/**
 * Created by Kanthi on 23/8/2018.
 */

public class Project_Sp_Adapter extends BaseAdapter {
    private List<Zones> mZones;
    private LayoutInflater inflater;
    private Context mContext;

    public Project_Sp_Adapter(Context context, List<Zones> zones) {
        mZones = zones;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mZones.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ?  null : mZones.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Project_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Project_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Project_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Zones zones = (Zones) getItem(position);
        if (zones != null) {
            holder.spinnerItem.setText(zones.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_condition));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Project_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Project_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Project_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Zones zones = (Zones) getItem(position);
        if (zones!= null) {
            holder.spinnerItem.setText(zones.getName());
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