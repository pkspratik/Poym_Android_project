package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Dashboard.ProjectMonitorActivity;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kanthi on 3/30/2018.
 */

public class RouteSalesViewsAdapter extends RecyclerView.Adapter<RouteSalesViewsAdapter.ViewHolder> {

    private List<RouteSalesViews> msalesViews;
    private Context mContext;
    private String mdate;
    public AppCompatActivity activity;
    private int mpartnerid;

    public RouteSalesViewsAdapter(List<RouteSalesViews> salesViewses, int partnerid, String date) {
        msalesViews = salesViewses;
        mpartnerid=partnerid;
        mdate = date;
    }

    @Override
    public RouteSalesViewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_route_sales_views,
                parent, false);
        mContext = parent.getContext();
        return new RouteSalesViewsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String month=msalesViews.get(position).getMonth()==null?"":msalesViews.get(position).getMonth();
        final String date=msalesViews.get(position).getDate()==null?"":msalesViews.get(position).getDate();
        String tourname=msalesViews.get(position).getDay()==null?"":msalesViews.get(position).getDay();
        String typename=msalesViews.get(position).getTourtype()==null?"":msalesViews.get(position).getTourtype();
        String kms=msalesViews.get(position).getTotalactual()==null?"":String.valueOf(msalesViews.get(position).getTotalactual());
        String unit_kms=msalesViews.get(position).getUnitmeasurementname()==null?"":msalesViews.get(position).getUnitmeasurementname();
        final String status= msalesViews.get(position).getStatus()==null?"null":msalesViews.get(position).getStatus();
        final int routeid=msalesViews.get(position).getRouteid();
        String amonth=msalesViews.get(position).getAmonth()==null?"":msalesViews.get(position).getAmonth();
        String adate=msalesViews.get(position).getAdate()==null?"":msalesViews.get(position).getAdate();
        //int actdate= Integer.parseInt(adate.substring(0,2)+1);
        String aday=msalesViews.get(position).getAday()==null?"":msalesViews.get(position).getAday();
        holder.tour1.setText(month+" "+date+",");
        holder.day1.setText(tourname);
        holder.task1.setText(typename);
        holder.kms1.setText(kms+"- "+unit_kms);
        holder.actdate.setText(amonth+" "+adate);
        holder.actday.setText(aday);
        AppPreferences.setUnitMeasurementName(mContext,unit_kms);
        //Toast.makeText(mContext, ""+adate.substring(0,2), Toast.LENGTH_SHORT).show();
        //Toast.makeText(mContext, "routesalesviewsid"+msalesViews.get(msalesViews.size()-1).getRouteassignmentsummaryid(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(mContext, "routeassignmentsummaryid"+massignmentSummaries.get(massignmentSummaries.size()-1).getId(), Toast.LENGTH_SHORT).show();
        /*if(msalesViews.get(msalesViews.size()-1).getRouteassignmentsummaryid().equals(massignmentSummaries.get(massignmentSummaries.size()-1).getId())){
            Log.d("routesalesviewsid",String.valueOf(msalesViews.get(msalesViews.size()-1).getRouteassignmentsummaryid()));
            Log.d("routeassignmentsumaryid",String.valueOf(massignmentSummaries.get(massignmentSummaries.size()-1).getId()));
            String actualdate= AppUtilities.getDateWithTimeString(massignmentSummaries.get(massignmentSummaries.size()-1).getActualstartdate());
            holder.actdate.setText(actualdate.substring(0,6));
            holder.actday.setText(actualdate.substring(7,9));
        }*/
        if(AppPreferences.getGroupId(mContext)==23||AppPreferences.getGroupId(mContext)==41||AppPreferences.getGroupId(mContext)==39){
            holder.arrow.setVisibility(View.VISIBLE);
        }else{
            holder.arrow.setVisibility(View.GONE);
        }
        if(AppPreferences.getGroupId(mContext)==23||AppPreferences.getGroupId(mContext)==41||AppPreferences.getGroupId(mContext)==39){
            holder.layout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0){
                        AppPreferences.setTaskName(mContext,msalesViews.get(position).getRoutename());
                        AppPreferences.setTaskType(mContext,msalesViews.get(position).getTourtype());
                        AppPreferences.setTarget(mContext,msalesViews.get(position).getTotaltarget());
                        AppPreferences.setPrefRouteId(mContext,msalesViews.get(position).getRouteid());
                        AppPreferences.setRouteSalesViewid(mContext,msalesViews.get(position).getId());
                        AppPreferences.setRouteAssignmentId(mContext,msalesViews.get(position).getId());
                        AppPreferences.setTourTypeId(mContext,msalesViews.get(position).getTourtypeid());
                        AppPreferences.setChartPercentage(mContext, (float) 0.0);
                        //Todo:for network routeassignmentsumaryid
                        AppPreferences.setRouteAssignmentSummaryId(mContext,msalesViews.get(position).getRouteassignmentsummaryid());
                        AppPreferences.setProjectStatusid(mContext,msalesViews.get(position).getProjectstatusid());


                        String month=msalesViews.get(position).getMonth();
                        String date=msalesViews.get(position).getDate();

                        Intent intent=new Intent(mContext, ProjectMonitorActivity.class);
                        intent.putExtra("partnerid",mpartnerid);
                        intent.putExtra("date",month+" "+date);
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "Please,Click the First Row", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return msalesViews==null?0:msalesViews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tour1;
        TextView task1;
        TextView day1;
        TextView kms1;
        ImageView arrow;
        TextView actdate;
        TextView actday;
        LinearLayout layout1;

        public ViewHolder(View view) {
            super(view);
            tour1 = (TextView) view.findViewById(R.id.tour1);
            task1 = (TextView) view.findViewById(R.id.task1);
            day1 = (TextView) view.findViewById(R.id.day1);
            kms1 = (TextView) view.findViewById(R.id.kms1);
            arrow= (ImageView) view.findViewById(R.id.routearrow);
            actdate= (TextView) view.findViewById(R.id.actual_date);
            actday= (TextView) view.findViewById(R.id.actual_day);
            layout1 = (LinearLayout) view.findViewById(R.id.layout1);
        }
    }
}
