package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.CasePackets;
import com.example.kanthi.projectmonitoring.PoJo.ChangeReqCategories;
import com.example.kanthi.projectmonitoring.R;

import java.util.List;

/**
 * Created by Kanthi on 1/25/2018.
 */

public class Change_Req_Category_Sp_Adapter extends BaseAdapter {
    private List<ChangeReqCategories> mCasepackets;
    private LayoutInflater inflater;
    private Context mContext;

    public Change_Req_Category_Sp_Adapter(Context context, List<ChangeReqCategories> casePacket) {
        mCasepackets = casePacket;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mCasepackets.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ?  null : mCasepackets.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Change_Req_Category_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Change_Req_Category_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Change_Req_Category_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ChangeReqCategories case_pack = (ChangeReqCategories) getItem(position);
        if (case_pack != null) {
            holder.spinnerItem.setText(case_pack.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_condition));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Change_Req_Category_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Change_Req_Category_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Change_Req_Category_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ChangeReqCategories case_packs = (ChangeReqCategories) getItem(position);
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
