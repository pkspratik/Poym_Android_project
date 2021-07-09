package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.Checklist;
import com.example.kanthi.projectmonitoring.R;

import java.util.List;

public class Checklist_Sp_Adapter extends BaseAdapter {
    private List<Checklist> mparameter;
    private LayoutInflater inflater;
    private Context mContext;

    public Checklist_Sp_Adapter(Context context, List<Checklist> parameters) {
        mparameter = parameters;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mparameter.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ?  null : mparameter.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Details_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Details_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Details_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Checklist parameter = (Checklist) getItem(position);
        if (parameter != null) {
            holder.spinnerItem.setText(parameter.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_checklist));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Details_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Details_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Details_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Checklist parameter = (Checklist) getItem(position);
        if (parameter!= null) {
            holder.spinnerItem.setText(parameter.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_checklist));
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
