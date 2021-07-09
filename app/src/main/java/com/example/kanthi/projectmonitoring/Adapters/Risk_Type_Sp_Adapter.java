package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.CasePackets;
import com.example.kanthi.projectmonitoring.PoJo.RiskTypes;
import com.example.kanthi.projectmonitoring.R;

import java.util.List;

/**
 * Created by Kanthi on 1/25/2018.
 */

public class Risk_Type_Sp_Adapter extends BaseAdapter {
    private List<RiskTypes> mrisktypes;
    private LayoutInflater inflater;
    private Context mContext;

    public Risk_Type_Sp_Adapter(Context context, List<RiskTypes> riskTypes) {
        mrisktypes = riskTypes;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mrisktypes.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ?  null : mrisktypes.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Risk_Type_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Risk_Type_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Risk_Type_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        RiskTypes case_pack = (RiskTypes) getItem(position);
        if (case_pack != null) {
            holder.spinnerItem.setText(case_pack.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_condition));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Risk_Type_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Risk_Type_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Risk_Type_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        RiskTypes case_packs = (RiskTypes) getItem(position);
        if (case_packs != null) {
            holder.spinnerItem.setText(case_packs.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_condition));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView spinnerItem;

        public ViewHolder(View view) {
            spinnerItem= (TextView) view.findViewById(R.id.spinner_text);
            //ButterKnife.bind(this, view);
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }
}
