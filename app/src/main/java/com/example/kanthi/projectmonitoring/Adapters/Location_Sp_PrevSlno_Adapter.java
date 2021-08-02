 package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;

import java.util.List;

public class Location_Sp_PrevSlno_Adapter extends BaseAdapter {

    private List<Surveys> prevSno;
    private LayoutInflater inflater;
    private Context lcontext;

    public Location_Sp_PrevSlno_Adapter(Context context , List<Surveys> lsp_prevsl) {
        prevSno = lsp_prevsl;
        lcontext = context;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return prevSno.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ? null : prevSno.get(position -1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Location_Sp_PrevSlno_Adapter.ViewHolder holder;

        if(convertView != null)
        {
            holder = (Location_Sp_PrevSlno_Adapter.ViewHolder) convertView.getTag();
        }
        else
        {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Location_Sp_PrevSlno_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Surveys surprevslnos = (Surveys) getItem(position);
        if(surprevslnos != null) {
            holder.spinnerItem.setText(String.valueOf(surprevslnos.getSlno()));

        }
        else {
            holder.spinnerItem.setText(lcontext.getString(R.string.select_parameter));
        }


        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Location_Sp_PrevSlno_Adapter.ViewHolder holder;
        if (convertView != null) {
            holder = (Location_Sp_PrevSlno_Adapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            holder = new Location_Sp_PrevSlno_Adapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }
        Surveys surprevslno = (Surveys) getItem(position);
        if (surprevslno != null) {
            holder.spinnerItem.setText(String.valueOf(surprevslno.getSlno()));
        }else {
            holder.spinnerItem.setText(lcontext.getString(R.string.select_condition));
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
