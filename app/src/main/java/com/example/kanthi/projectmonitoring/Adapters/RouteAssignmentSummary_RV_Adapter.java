package com.example.kanthi.projectmonitoring.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.Dashboard.LandingActivity;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummariesViews;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;

import java.util.List;

public class RouteAssignmentSummary_RV_Adapter extends RecyclerView.Adapter<RouteAssignmentSummary_RV_Adapter.ViewHolder> {

    private List<RouteAssignmentSummariesViews> msummaries;
    private Context mContext;
    int pStatus = 0;
    private Handler handler = new Handler();

    public RouteAssignmentSummary_RV_Adapter(List<RouteAssignmentSummariesViews> summaries) {
        msummaries = summaries;
    }

    @Override
    public RouteAssignmentSummary_RV_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_routesummaryview,
                parent, false);
        mContext = parent.getContext();
        return new RouteAssignmentSummary_RV_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RouteAssignmentSummary_RV_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final String mStr_Tourtype = msummaries.get(position).getTourtype(),
                mStr_PercentageCompleted = msummaries.get(position).getPercentage().toString(),
                mStr_UnitMeasurement = msummaries.get(position).getUnitmeasurementname(),
                mfrom_date=msummaries.get(position).getFromdate(),
                mto_date=msummaries.get(position).getTodate(),
                mStr_FromDate = msummaries.get(position).getFrom_Date() + "-" + msummaries.get(position).getFrom_Month(),
                mStr_ToDate = msummaries.get(position).getTo_Date() + "-" + msummaries.get(position).getTo_Month();
        final Float mStr_TotalTarget = msummaries.get(position).getTotaltarget();
        final Float mStr_TotalActual = msummaries.get(position).getTotalactual();

        String color_flag=msummaries.get(position).getHcflag();
        holder.tour_type.setText(mStr_Tourtype);
        holder.from_date.setText("From : "+ AppUtilities.getDateWithTimeString(mfrom_date));
        holder.to_date.setText("To : " + AppUtilities.getDateWithTimeString(mto_date));
        holder.total_target.setText("Total : "+mStr_TotalTarget + " " + mStr_UnitMeasurement);
        if(color_flag!=null){
            if(color_flag.equalsIgnoreCase("h")){
                holder.tv_arrow.setBackground(mContext.getResources().getDrawable(R.drawable.blue_marker));
                holder.layout.setEnabled(false);
                //Toast.makeText(mContext, ""+msummaries.get(position).getHcflag(), Toast.LENGTH_SHORT).show();
            }else{
                //holder.tv_arrow.setBackground(mContext.getResources().getDrawable(R.drawable.yellow_marker1));
            }
        }
        /*if(mStr_TotalActual!=0){
            AppPreferences.setStartDateFlag(mContext,true);
        }else{
            AppPreferences.setStartDateFlag(mContext,false);
        }*/

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LandingActivity.class);
                intent.putExtra("partnerid", AppPreferences.getPartnerid(mContext));
                intent.putExtra("usertype", AppPreferences.getUserType(mContext));
                intent.putExtra("projecttype", AppPreferences.getProjectType(mContext));
                intent.putExtra("user_email", AppPreferences.getUserEmail(mContext));
                intent.putExtra("route_assignment_summary_id", msummaries.get(position).getId());
                AppPreferences.setRouteAssignmentSummaryId(mContext, (int) msummaries.get(position).getId());
                AppPreferences.setDependentRouteAssignmentSummaryId(mContext, msummaries.get(position).getDependentTaskId().length()>0?Integer.parseInt(msummaries.get(position).getDependentTaskId()):0);
                mContext.startActivity(intent);
            }
        });


        //Circular Progress Bar
        Resources res = mContext.getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        holder.mProgress.setProgress(0);   // Main Progress
        holder.mProgress.setSecondaryProgress(100); // Secondary Progress
        holder.mProgress.setMax(100); // Maximum Progress
        holder.mProgress.setProgressDrawable(drawable);

        double d = Double.parseDouble(mStr_PercentageCompleted);
        int i = (int) d;

        holder.mProgress.setProgress((i));
        if(i==100){
            Resources res1 = mContext.getResources();
            Drawable drawable1 = res1.getDrawable(R.drawable.circular_green);
            holder.mProgress.setProgress(i);   // Main Progress
            holder.mProgress.setSecondaryProgress(100); // Secondary Progress
            holder.mProgress.setMax(100); // Maximum Progress
            holder.mProgress.setProgressDrawable(drawable1);
        }
        holder.tv_percentage.setText((i) + "%");

    }

    @Override
    public int getItemCount() {
        return msummaries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tour_type;
        TextView from_date;
        TextView to_date;
        TextView total_target;
        LinearLayout layout;
        ProgressBar mProgress;
        TextView tv_percentage;
        ImageView tv_arrow;

        public ViewHolder(View view) {
            super(view);
            tour_type = (TextView) view.findViewById(R.id.tour_type);
            total_target = (TextView) view.findViewById(R.id.total_actual);
            from_date = (TextView) view.findViewById(R.id.from_date);
            to_date = (TextView) view.findViewById(R.id.to_date);
            tv_percentage = (TextView) view.findViewById(R.id.tv_percent_complete);
            layout = (LinearLayout) view.findViewById(R.id.layout);
            mProgress = (ProgressBar) view.findViewById(R.id.circularProgressbar);
            tv_arrow = (ImageView) view.findViewById(R.id.arrow);
        }
    }
}