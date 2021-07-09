package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.InnerPacks;
import com.example.kanthi.projectmonitoring.R;
import java.util.List;
import butterknife.ButterKnife;

/**
 * Created by Kanthi on 1/25/2018.
 */

public class Inner_Packets_Sp_Adapter extends BaseAdapter {
    private List<InnerPacks> mInnerpack;
    private LayoutInflater inflater;
    private Context mContext;

    public Inner_Packets_Sp_Adapter(Context context, List<InnerPacks> innerPack) {
        mInnerpack = innerPack;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mInnerpack.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ?  null : mInnerpack.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Inner_Packets_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Inner_Packets_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Inner_Packets_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        InnerPacks inner_pack = (InnerPacks) getItem(position);
        if (inner_pack != null) {
            holder.spinnerItem.setText(inner_pack.getName());
        }else {
            holder.spinnerItem.setText(mContext.getString(R.string.select_condition));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Inner_Packets_Sp_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Inner_Packets_Sp_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Inner_Packets_Sp_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        InnerPacks innerpacks = (InnerPacks) getItem(position);
        if (innerpacks != null) {
            holder.spinnerItem.setText(innerpacks.getName());
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

