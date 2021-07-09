package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.ChangeRequests;
import com.example.kanthi.projectmonitoring.PoJo.Priority;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanthi on 4/30/2018.
 */

public class Change_ReqClose_Rv_Adapter extends RecyclerView.Adapter<Change_ReqClose_Rv_Adapter.ViewHolder> {
    private List<ChangeRequests> mchange;
    private Context mContext;
    public AppCompatActivity activity;
    //private ArrayList<Priority> mpriorities;

    public Change_ReqClose_Rv_Adapter(List<ChangeRequests> surveys)
    {
        mchange = surveys;
        //mpriorities=priorities;
    }
    @Override
    public Change_ReqClose_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_change_req_close,
                parent, false);
        mContext = parent.getContext();
        return new Change_ReqClose_Rv_Adapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.no.setText(mchange.get(position).getCategoryname());
        //holder.priority.setText(String.valueOf(mchange.get(position).getPriorityId()==null?"":mchange.get(position).getPriorityId()));
        String date=AppUtilities.getDateWithTimeString(mchange.get(position).getRequestDate()==null?"":mchange.get(position).getRequestDate());
        holder.date.setText(date);
        holder.priority.setText(mchange.get(position).getPriorityname());
    }

    @Override
    public int getItemCount() {
        return mchange.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView no;
        TextView priority;
        TextView date;

        public ViewHolder(View view) {
            super(view);
            no= (TextView) view.findViewById(R.id.changereq_no1);
            priority= (TextView) view.findViewById(R.id.priority1);
            date= (TextView) view.findViewById(R.id.date1);
        }
    }
}