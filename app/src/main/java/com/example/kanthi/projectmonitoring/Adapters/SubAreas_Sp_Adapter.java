package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.DistributionSubAreas;
import com.example.kanthi.projectmonitoring.PoJo.SubTaskTypes;
import com.example.kanthi.projectmonitoring.R;

import java.util.List;

/**
 * Created by Kanthi on 1/25/2018.
 */

public class SubAreas_Sp_Adapter extends BaseAdapter {
    private List<DistributionSubAreas> msubAreas;
    private LayoutInflater inflater;
    private Context mContext;

    public SubAreas_Sp_Adapter(Context context, List<DistributionSubAreas> subtask) {
        msubAreas = subtask;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return msubAreas.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ?  null : msubAreas.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubAreas_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (SubAreas_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new SubAreas_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        DistributionSubAreas areas = (DistributionSubAreas) getItem(position);
        if (areas != null) {
            holder.spinnerItem.setText(areas.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_condition));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        SubAreas_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (SubAreas_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new SubAreas_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        DistributionSubAreas areas = (DistributionSubAreas) getItem(position);
        if (areas != null) {
            holder.spinnerItem.setText(areas.getName());
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
