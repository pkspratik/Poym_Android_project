package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.ProjectRisk;
import com.example.kanthi.projectmonitoring.PoJo.RiskTypes;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kanthi on 4/30/2018.
 */

public class Project_Risk_CloseRv_Adapter extends RecyclerView.Adapter<Project_Risk_CloseRv_Adapter.ViewHolder> {

    private List<ProjectRisk> mrisk;
    private Context mContext;
    public AppCompatActivity activity;
    private List<RiskTypes> mriskTypes;

    public Project_Risk_CloseRv_Adapter(List<ProjectRisk> surveys,List<RiskTypes> riskTypes) {
        mrisk = surveys;
        mriskTypes=riskTypes;
    }


    @Override
    public Project_Risk_CloseRv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_project_risk_close,
                parent, false);
        mContext = parent.getContext();
        return new Project_Risk_CloseRv_Adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        for(int i=0;i<mriskTypes.size();i++){
            int id=mriskTypes.get(i).getId();
            String name=mriskTypes.get(i).getName();
            if(id==mrisk.get(position).getRisktypeId()){
                holder.risk.setText(name);
            }
        }
        String date= AppUtilities.getDateWithTimeString(mrisk.get(position).getLikelyOccuranceDate());
        holder.occ_date.setText(date);
    }
    @Override
    public int getItemCount() {
        return mrisk.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView risk;
        TextView occ_date;

        public ViewHolder(View view) {
            super(view);
            risk= (TextView) view.findViewById(R.id.rv_risktype1);
            occ_date= (TextView) view.findViewById(R.id.rv_occurdate1);
        }
    }
}

