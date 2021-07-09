package com.example.kanthi.projectmonitoring.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.Dashboard.ClickInterface;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRisk;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRiskViews;
import com.example.kanthi.projectmonitoring.PoJo.RiskTypes;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.Where;

import java.util.List;

/**
 * Created by kanthi on 4/30/2018.
 */

public class Project_Risk_OpenRv_Adapter extends RecyclerView.Adapter<Project_Risk_OpenRv_Adapter.ViewHolder> {

    private List<ProjectRisk> mrisk;
    private List<RiskTypes> mriskTypes;
    private List<ProjectRiskViews> mRiskViews;
    private Context mContext;
    public AppCompatActivity activity;
    String tv_risk;
    String tv_probablity;
    String tv_impact;
    String tv_responce;
    String tv_status;

    private ClickInterface mlistner;

    public Project_Risk_OpenRv_Adapter(List<ProjectRisk> risk, List<RiskTypes> riskTypes, ClickInterface listner) {
        mrisk = risk;
        mriskTypes=riskTypes;
        mlistner=listner;
    }
    @Override
    public Project_Risk_OpenRv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_project_risk_open,
                parent, false);
        mContext = parent.getContext();
        return new Project_Risk_OpenRv_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        for(int i=0;i<mriskTypes.size();i++){
            int id=mriskTypes.get(i).getId();
            String name=mriskTypes.get(i).getName();
            if(id==mrisk.get(position).getRisktypeId()){
                holder.risk.setText(name);
            }
        }
        //final long tv_id=mrisk.get(position).getId();
        final String date= AppUtilities.getDateWithTimeString(mrisk.get(position).getLikelyOccuranceDate());
        holder.occ_date.setText(date);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mlistner.recyclerviewOnClick(holder.getAdapterPosition());
                notifyDataSetChanged();

                /*final ProgressDialog progress = new ProgressDialog(mContext);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setMessage("Loading...");
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();
                ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
                Call<String> call1 = service1.getProjectRiskViews(AppPreferences.getUserId(mContext),AppPreferences.getZoneId(mContext),
                        AppPreferences.getPrefAreaId(mContext)
                        , AppPreferences.getDist_Area_Id(mContext),tv_id);
                call1.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        ArrayList<ProjectRiskViews> riskviews = new Gson().fromJson(response.body(),
                                new TypeToken<ArrayList<ProjectRiskViews>>() {
                                }.getType());
                        mprojectRiskViews = riskviews;
                        progress.dismiss();
                        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                        View vi=LayoutInflater.from(mContext).inflate(R.layout.projectriskviews_popup,null);
                        builder.setView(vi);
                        builder.setCancelable(false);
                        final TextView risk= (TextView) vi.findViewById(R.id.pro_risktype);
                        final TextView probablity= (TextView) vi.findViewById(R.id.pro_probability);
                        final TextView impact= (TextView) vi.findViewById(R.id.pro_impact);
                        final TextView responce= (TextView) vi.findViewById(R.id.pro_responce);
                        final TextView status= (TextView) vi.findViewById(R.id.pro_status);
                        Button cancel= (Button) vi.findViewById(R.id.cancel_projectrisk);
                        final AlertDialog dialog=builder.create();
                        for(int i=0;i<mprojectRiskViews.size();i++){
                            if(mprojectRiskViews.get(i).getId()==tv_id){
                                tv_risk=mprojectRiskViews.get(i).getRisktype();
                                tv_probablity=mprojectRiskViews.get(i).getProbability();
                                tv_impact=mprojectRiskViews.get(i).getImpact();
                                tv_responce=mprojectRiskViews.get(i).getResponse();
                                tv_status=mprojectRiskViews.get(i).getStatus();
                                risk.setText(tv_risk);
                                probablity.setText(tv_probablity);
                                impact.setText(tv_impact);
                                responce.setText(tv_responce);
                                status.setText(tv_status);
                            }
                        }
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                    }
                });*/
            }
        });
    }
    @Override
    public int getItemCount() {
        return mrisk.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView risk;
        TextView occ_date;
        LinearLayout layout;
        public ViewHolder(View view) {
            super(view);
            risk= (TextView) view.findViewById(R.id.rv_risktype);
            occ_date= (TextView) view.findViewById(R.id.rv_occurdate);
            layout= (LinearLayout) view.findViewById(R.id.projectrisk_layout);
        }
    }
}
