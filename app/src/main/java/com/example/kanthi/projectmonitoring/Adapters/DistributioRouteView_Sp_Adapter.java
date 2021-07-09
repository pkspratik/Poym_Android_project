package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.DistributionRouteView;
import com.example.kanthi.projectmonitoring.R;

import java.util.List;

public class DistributioRouteView_Sp_Adapter extends BaseAdapter {
    private List<DistributionRouteView> mDistributionRouteView;
    private LayoutInflater inflater;
    private Context mContext;

    public DistributioRouteView_Sp_Adapter(Context context, List<DistributionRouteView> casePacket) {
        mDistributionRouteView = casePacket;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDistributionRouteView.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ?  null : mDistributionRouteView.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Case_Packets_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Case_Packets_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Case_Packets_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        DistributionRouteView case_pack = (DistributionRouteView) getItem(position);
        if (case_pack != null) {
            holder.spinnerItem.setText(case_pack.getTourtypename());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_condition));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Case_Packets_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Case_Packets_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Case_Packets_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        DistributionRouteView case_packs = (DistributionRouteView) getItem(position);
        if (case_packs != null) {
            holder.spinnerItem.setText(case_packs.getTourtypename());
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