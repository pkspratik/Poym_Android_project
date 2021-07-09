package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.Status;
import com.example.kanthi.projectmonitoring.R;

import java.util.List;

/**
 * Created by Kanthi on 3/5/2018.
 */

public class Status_Sp_Adapter extends BaseAdapter {
    private List<Status> mStatus;
    private LayoutInflater inflater;
    private Context mContext;

    public Status_Sp_Adapter(Context context, List<Status> status) {
        mStatus = status;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mStatus.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ?  null : mStatus.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Status_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Status_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Status_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Status status = (Status) getItem(position);
        if (status != null) {
            holder.spinnerItem.setText(status.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_condition));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Status_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Status_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Status_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Status status = (Status) getItem(position);
        if (status!= null) {
            holder.spinnerItem.setText(status.getName());
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

