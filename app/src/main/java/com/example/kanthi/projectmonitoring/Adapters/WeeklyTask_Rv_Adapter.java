package com.example.kanthi.projectmonitoring.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Dashboard.ProjectMonitorActivity;
import com.example.kanthi.projectmonitoring.Dashboard.Survey;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.PoJo.ActualDays;
import com.example.kanthi.projectmonitoring.PoJo.Days;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummaries;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignments;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.util.Preconditions.checkArgument;

/**
 * Created by kanthi on 2/21/2018.
 */

public class WeeklyTask_Rv_Adapter extends RecyclerView.Adapter<WeeklyTask_Rv_Adapter.ViewHolder> {

    private List<RouteAssignmentSummariesViews> msummaries;
    private Days days;
    private ArrayList<Days> mdays;
    private Context mContext;
    private String mdate;
    public AppCompatActivity activity;
    private RouteAssignments routeassignments;
    private RouteSalesViews routeSalesViews;
    private List<RouteSalesViews> routesalesviews;
    int tourtypeid,zoneid,duration,worktypeid,unitid,dist_area_id,salesareaid,sal_managerid,partnerid;
    long summ_id;
    float total_target;
    String actual_target;
    int phaseid,processid,subprocessid;
    List<RouteAssignmentSummaries> mrouteassignsummaries;
    ArrayList<ActualDays> mactualdays;
    private String day,day1;
    int mpartnerid;
    int dd,m,yy;
    int consultuserid,informuserid,supportuserid;
    String dependenttaskid;

    List<RouteAssignments> mRouteAssignments;
    private boolean isApperence;
    AvahanSqliteDbHelper mDbHelper;
    private long RouteAssignmentId,RouteSalesViewsId;

    public WeeklyTask_Rv_Adapter(ArrayList<Days> array, List<RouteAssignmentSummariesViews> summaries,
                                 String date, int partner_id, List<RouteAssignmentSummaries> assignsummaries, ArrayList<ActualDays> actdays,
                                 List<RouteAssignments> mrouteAssignments, AvahanSqliteDbHelper helper) {
        mdays = array;
        msummaries=summaries;
        mdate=date;
        mpartnerid=partner_id;
        mrouteassignsummaries=assignsummaries;
        mactualdays=actdays;
        mRouteAssignments=mrouteAssignments;
        mDbHelper=helper;
    }

    @Override
    public WeeklyTask_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_weekly_task,
                parent, false);
        mContext = parent.getContext();
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final String days=mdays.get(position).getDays();
        String gmt=mdays.get(position).getGmt();
        final String convertedgmt=AppUtilities.getGMTconvert(gmt);
        Log.e("Gmt",convertedgmt);
        final int date= Integer.parseInt(convertedgmt.substring(8,10));
        final int curr_date=date-1==0?1:date-1;

        final String dayofweek=days.substring(0,4);
        day=days.substring(4,10);


        final String days1=mdays.get(0).getDays();

        day1=days1.substring(4,10);



        //final String day1=days.substring(4,10);
        int workingdays=AppPreferences.getWorkingdays(mContext);
        final float totaltarget=(mdays.get(position).getTotaltarget())-(mdays.get(position).getTotalactual());
        final float totalkm= ((Float.parseFloat(String.valueOf(totaltarget)))/(Float.parseFloat(String.valueOf(workingdays))));
        String tourtype=mdays.get(position).getTourtype();
        holder.tour.setText(day);
        holder.task.setText(tourtype);
        holder.day1.setText(dayofweek);
        AppPreferences.setUnitMeasurementName(mContext,msummaries.get(0).getUnitmeasurementname());
        holder.kms.setText(new DecimalFormat("##.###").format(totalkm)+" - "+msummaries.get(0).getUnitmeasurementname());
        if(AppPreferences.getGroupId(mContext)==14||AppPreferences.getGroupId(mContext)==29||AppPreferences.getGroupId(mContext)==17||AppPreferences.getGroupId(mContext)==18){
            holder.arrow.setVisibility(View.VISIBLE);
        }else{
            holder.arrow.setVisibility(View.GONE);
        }
        AppPreferences.setStartFlag(mContext,true);
        AppPreferences.setFromDate(mContext,msummaries.get(0).getFromdate());
        AppPreferences.setToDate(mContext,msummaries.get(0).getTodate());
        AppPreferences.setTotalTarget(mContext, msummaries.get(0).getTotaltarget().intValue());
        AppPreferences.setTotalActual(mContext,msummaries.get(0).getTotalactual());
        AppPreferences.setRouteAssignmentSummaryId(mContext,msummaries.get(0).getRouteassignmentsummaryid());
        AppPreferences.setDependentRouteAssignmentSummaryId(mContext, msummaries.get(0).getDependentTaskId().length()>0?Integer.parseInt(msummaries.get(0).getDependentTaskId()):0);
        AppPreferences.setUnitMeasurementId(mContext,msummaries.get(0).getUnitmeasurementid());
        AppPreferences.setChartPercentage(mContext,msummaries.get(0).getPercentage());

        if(mdays.size()==1){
            //completeflag =true based on array length
            //startflag=true
            AppPreferences.setPrefCompletedFlag(mContext,true);
        }else {
            AppPreferences.setPrefCompletedFlag(mContext,false);
        }
        if(AppPreferences.getGroupId(mContext)==14||AppPreferences.getGroupId(mContext)==29||AppPreferences.getGroupId(mContext)==17||AppPreferences.getGroupId(mContext)==18){
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0){
                        try {
                            AppPreferences.setTarget(mContext, totalkm);

                            int i=0;
                            {
                                tourtypeid= msummaries.get(i).getTourtypeid()==null?0:msummaries.get(i).getTourtypeid();
                                zoneid=msummaries.get(i).getZoneid()==null?0:msummaries.get(i).getZoneid();
                                duration=msummaries.get(i).getDuration().intValue();
                                worktypeid=msummaries.get(i).getWorktypeid()==null?0:msummaries.get(i).getWorktypeid();
                                unitid=msummaries.get(i).getUnitmeasurementid()==null?0:msummaries.get(i).getUnitmeasurementid();
                                dist_area_id=msummaries.get(i).getDistributionareaid()==null?0:msummaries.get(i).getDistributionareaid();
                                salesareaid=msummaries.get(i).getSalesareaid()==null?0:msummaries.get(i).getSalesareaid();
                                sal_managerid=msummaries.get(i).getSalesmanagerid()==null?0:msummaries.get(i).getSalesmanagerid();
                                partnerid=msummaries.get(i).getPartnerid()==null?0:msummaries.get(i).getPartnerid();
                                //total_target=msummaries.get(i).getTotaltarget()==null?null:msummaries.get(i).getTotaltarget();
                                actual_target= String.valueOf(msummaries.get(i).getTotalactual());
                                summ_id=msummaries.get(i).getId();
                                phaseid=msummaries.get(i).getPhaseid()==null?0:msummaries.get(i).getPhaseid();
                                processid=msummaries.get(i).getProcessid()==null?0:msummaries.get(i).getProcessid();
                                subprocessid=msummaries.get(i).getSubprocessid()==null?0:msummaries.get(i).getSubprocessid();
                                consultuserid=msummaries.get(i).getConsultUserId()==null?0:msummaries.get(i).getConsultUserId();
                                informuserid=msummaries.get(i).getInformedUserId()==null?0:msummaries.get(i).getInformedUserId();
                                supportuserid=msummaries.get(i).getSupportUserId()==null?0:msummaries.get(i).getSupportUserId();
                                dependenttaskid=msummaries.get(i).getDependentTaskId()==null?"0":msummaries.get(i).getDependentTaskId().toString();

                                AppPreferences.setStartDate(mContext,AppUtilities.getDateWithTimeString(msummaries.get(i).getFromdate()));
                                AppPreferences.setEndDate(mContext,AppUtilities.getDateWithTimeString(msummaries.get(i).getTodate()));
                                AppPreferences.setTaskExtendDate(mContext,msummaries.get(i).getTodate());
                                AppPreferences.setTotalTarget(mContext,msummaries.get(i).getTotaltarget().intValue());
                                AppPreferences.setCurrentTarget(mContext, Float.valueOf(new DecimalFormat("##.###").format(msummaries.get(i).getTotaltarget()- msummaries.get(i).getTotalactual())));
                                AppPreferences.setActualTarget(mContext, msummaries.get(i).getTotalactual());
                                AppPreferences.setDaysLeft(mContext,mdays.size());
                                AppPreferences.setTaskName(mContext,msummaries.get(i).getRoutename());
                                AppPreferences.setTaskType(mContext,msummaries.get(i).getTourtype());
                                AppPreferences.setPrefRouteId(mContext,msummaries.get(i).getRouteid());
                                AppPreferences.setTourTypeId(mContext,msummaries.get(i).getTourtypeid());
                                AppPreferences.setRouteAssignmentSummaryId(mContext,msummaries.get(i).getRouteassignmentsummaryid());
                                AppPreferences.setDependentRouteAssignmentSummaryId(mContext, msummaries.get(position).getDependentTaskId().length()>0?Integer.parseInt(msummaries.get(position).getDependentTaskId()):0);
                                AppPreferences.setTotalActual(mContext,msummaries.get(i).getTotalactual());
                                AppPreferences.setChartPercentage(mContext,msummaries.get(i).getPercentage());
                            }

                            if(mrouteassignsummaries.get(mrouteassignsummaries.size()-1).getActualstartdate()==null
                                    &&mrouteassignsummaries.get(mrouteassignsummaries.size()-1).getActualenddate()==null){
                                putRouteAssignmentSummaries();
                                putSummaries();
                            }else if(AppPreferences.getGroupId(mContext)==14||AppPreferences.getGroupId(mContext)==17||AppPreferences.getGroupId(mContext)==18){
                                Intent intent=new Intent(mContext, ProjectMonitorActivity.class);
                                intent.putExtra("partnerid",mpartnerid);
                                intent.putExtra("date",day1);
                                mContext.startActivity(intent);
                            }else if(AppPreferences.getGroupId(mContext)==29){
                                Intent intent=new Intent(mContext, Survey.class);
                                intent.putExtra("partnerid",mpartnerid);
                                intent.putExtra("date",day1);
                                mContext.startActivity(intent);
                            }


                            long masterId = System.currentTimeMillis();
                            routeassignments=new RouteAssignments();
                            routeassignments.setId(masterId);
                            routeassignments.setInsertFlag(true);
                            routeassignments.setDate(convertedgmt.substring(0,8)+(curr_date<10?"0"+curr_date:curr_date)+"T18:30:00.000Z");
                            routeassignments.setActualdate(AppUtilities.getDateTime());
                            routeassignments.setTourtype(tourtypeid);
                            routeassignments.setWorktypeid(worktypeid);
                            routeassignments.setUnitmeasurementid(unitid);
                            routeassignments.setTotaltarget(totalkm);
                            routeassignments.setZoneId(zoneid);
                            routeassignments.setSalesAreaId(salesareaid);
                            routeassignments.setSalesManagerId(sal_managerid);
                            routeassignments.setDistributionAreaId(dist_area_id);
                            routeassignments.setRouteassignmentsummaryid(summ_id);
                            routeassignments.setPartnerId(partnerid);
                            routeassignments.setConsultUserId(consultuserid);
                            routeassignments.setSupportUserId(supportuserid);
                            routeassignments.setInformedUserId(informuserid);
                            routeassignments.setDependentTaskId(dependenttaskid);
                            routeassignments.setPhaseId(phaseid);
                            routeassignments.setIsupdated(false);
                            routeassignments.setProcessId(processid);
                            routeassignments.setSubProcessId(subprocessid);
                            if(AppPreferences.getGroupId(mContext) == 17 || AppPreferences.getGroupId(mContext) == 18){
                                routeassignments.setEvFlag("v");
                            }else{
                                routeassignments.setEvFlag("e");
                            }

                            /*String fromdate=AppPreferences.getFromDateValue(StatusUpdate.this);
                            String updateview_date=null;
                            if(fromdate!=null){
                                int from = Integer.parseInt(fromdate.substring(8, 10));
                                int fro_date = from + 1;
                                updateview_date = fromdate.substring(0, 8) + fro_date+"T18:30:00.000Z";
                            }*/

                            long masterid = System.currentTimeMillis();
                            routeSalesViews=new RouteSalesViews();
                            routeSalesViews.setId(masterid);
                            routeSalesViews.setInsertFlag(true);
                            routeSalesViews.setRouteid(tourtypeid);
                            routeSalesViews.setTourtypeid(tourtypeid);
                            routeSalesViews.setWorktypeid(worktypeid);
                            routeSalesViews.setUnitmeasurementid(unitid);
                            routeSalesViews.setUnitmeasurementname(msummaries.get(0).getUnitmeasurementname());
                            routeSalesViews.setTourtype(mdays.get(position).getTourtype());
                            routeSalesViews.setDay(dayofweek);
                            routeSalesViews.setRowclicked(true);
                            routeSalesViews.setMonth(days.substring(4,8));
                            routeSalesViews.setTotaltarget(totalkm);
                            routeSalesViews.setRouteassignmentsummaryid(summ_id);
                            routeSalesViews.setUpdateflag(false);
                            routeSalesViews.setDate(date+getDayOfMonthSuffix(date));
                            String fromdate=mdays.get(position).getRouteAssigndate();
                            String updateview_date=null;
                            if(fromdate!=null){
                                int from = Integer.parseInt(fromdate.substring(8, 10));
                                int fro_date = from + 1;
                                if(fro_date==1 || fro_date==2 || fro_date==3 || fro_date==4 || fro_date==5 || fro_date==6 || fro_date==7 || fro_date==8 || fro_date==9 ){
                                    //fro_date= Integer.valueOf("0"+fromdate);
                                    updateview_date = fromdate.substring(0, 8) +"0"+ fro_date+"T18:30:00.000Z";
                                }else{
                                    updateview_date = fromdate.substring(0, 8) + fro_date+"T18:30:00.000Z";
                                }
                            }
                            routeSalesViews.setAssigndate(updateview_date==null?"":updateview_date);

                            //TODO for status update purpose
                            //AppPreferences.setFromDateValue(mContext,mdays.get(position).getRouteAssigndate());
                            //AppPreferences.setFromDateValueID(mContext,mdays.get(position).getRoutesalesviewsId());
                            Log.e("mRoutesize()",String.valueOf(mRouteAssignments.size()));
                            isApperence=false;
                            for(int j=0;j<mRouteAssignments.size();j++){
                                String AssignmentDate=mRouteAssignments.get(j).getDate();//2020-10-01T18:30:00.000Z
                                //from date
                                String RouteDate=mdays.get(position).getRouteAssigndate();//2020-10-01T18:30:00.000Z
                                String FinalDate=RouteDate.substring(0,10)+"T18:30:00.000Z";
                                Log.e("AssignDate",AssignmentDate);
                                Log.e("RouteDate",FinalDate);
                                if(FinalDate.equalsIgnoreCase(AssignmentDate)){
                                    isApperence=true;
                                    Log.e("ifrouteinsertFlag", String.valueOf(isApperence));
                                    RouteAssignmentId=mRouteAssignments.get(j).getId();
                                }
//                                else{
//                                    isApperence=false;
//                                    Log.e("ifelserouteinsertFlag", String.valueOf(isApperence));
//                                }
                            }

                            if(!isApperence) {
                                //postrouteassignments();
                                try {
                                    RuntimeExceptionDao<RouteAssignments, Integer> routeAssignmentsDao = mDbHelper.getRouteAssignmentsRuntimeDao();
                                    routeAssignmentsDao.create(routeassignments);

                                    RuntimeExceptionDao<RouteSalesViews, Integer> routeSalesViewsDoa = mDbHelper.getRouteSalesViewsRuntimeDao();
                                    routeSalesViewsDoa.create(routeSalesViews);

                                    AppPreferences.setRouteAssignmentId(mContext, masterId);

                                    AppPreferences.setFromDateValueID(mContext,masterid);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                Log.e("if_routeinsertFlag", String.valueOf(!isApperence));
                            }else{
                                AppPreferences.setRouteAssignmentId(mContext, RouteAssignmentId);
                                Log.e("else_routeinsertFlag", String.valueOf(RouteAssignmentId));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(mContext, "Please,Click the First Row", Toast.LENGTH_SHORT).show();
                    }

                    /*
                    if(!mdays.get(position).getStartFlag() //flag
                           )
                    {
                        postrouteassignments();
                    }else{
                        AppPreferences.setRouteAssignmentId(mContext, mdays.get(position).getRouteassignId());
                    }*/

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mdays.size();
    }


    String getDayOfMonthSuffix(final int n) {
        if(n >= 1 && n <= 31){
            if (n >= 11 && n <= 13) {
                return "th";
            }
            switch (n % 10) {
                case 1:  return "st";
                case 2:  return "nd";
                case 3:  return "rd";
                default: return "th";
            }
        }
        else{
            Log.e("illegal day of month: " , String.valueOf(n));
        }
        return "";

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tour;
        TextView task;
        TextView day1;
        TextView kms;
        ImageView arrow;
        LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            tour= (TextView) view.findViewById(R.id.tour);
            task= (TextView) view.findViewById(R.id.task);
            day1= (TextView) view.findViewById(R.id.days);
            kms= (TextView) view.findViewById(R.id.kms);
            arrow= (ImageView) view.findViewById(R.id.arrow);
            layout= (LinearLayout) view.findViewById(R.id.layout);
        }
    }
    private void postrouteassignments(){
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            benjson= parser.parse(new Gson().toJson(
                    routeassignments, RouteAssignments.class)).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        benjson.remove("id");
        call = service.insertRouteAssigments(AppPreferences.getUserId(mContext), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        Log.d("RouteAssignid", String.valueOf(object.getString("id")));
                        AppPreferences.setRouteAssignmentId(mContext, Integer.valueOf(object.getString("id")));
                        //Toast.makeText(mContext, ""+String.valueOf(object.getString("id")), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Log.d("RouteAssignid", String.valueOf(mrouteAssignments.get(mrouteAssignments.size()-1).getId()));
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void putRouteAssignmentSummaries(){
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            benjson.addProperty("actualstartdate",AppUtilities.getGMTconvert(mactualdays.get(0).getActualdays())+" 18:30:00+00");
            benjson.addProperty("actualenddate",AppUtilities.getGMTconvert(mactualdays.get(mactualdays.size()-1).getActualdays())+" 18:30:00+00");
            benjson.addProperty("id",AppPreferences.getRouteAssignmentSummaryId(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
        call = service.updateRouteAssigmentSummaries(AppPreferences.getUserId(mContext), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Log.d("routeassignments","route assign summaries");
                    progressDialog.dismiss();
                    if(AppPreferences.getGroupId(mContext)==14||AppPreferences.getGroupId(mContext)==17||AppPreferences.getGroupId(mContext)==18){
                        AppPreferences.setActualStartDate(mContext,mactualdays.get(0).getActualdays());
                        AppPreferences.setActualEndDate(mContext,mactualdays.get(mactualdays.size()-1).getActualdays());
                        Intent intent=new Intent(mContext, ProjectMonitorActivity.class);
                        intent.putExtra("partnerid",mpartnerid);
                        intent.putExtra("date",day1);
                        mContext.startActivity(intent);
                    }else if(AppPreferences.getGroupId(mContext)==29){
                        Intent intent=new Intent(mContext, Survey.class);
                        intent.putExtra("partnerid",mpartnerid);
                        intent.putExtra("date",day1);
                        mContext.startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void putSummaries(){
        Log.d("summaried","check");
        try {
            RuntimeExceptionDao<RouteAssignmentSummaries, Integer> routeAssignmentSummariesDao = mDbHelper.getRouteAssignmentSummariesRuntimeDao();
            UpdateBuilder<RouteAssignmentSummaries, Integer> updateBuilder = routeAssignmentSummariesDao.updateBuilder();
            updateBuilder.where().eq("id", AppPreferences.getRouteAssignmentSummaryId(mContext));
            updateBuilder.updateColumnValue("updateflag",true);
            updateBuilder.updateColumnValue("isupdateflag",true);
            updateBuilder.updateColumnValue("actualstartdate",AppUtilities.getGMTconvert(mactualdays.get(0).getActualdays())+" 18:30:00+00");
            updateBuilder.updateColumnValue("actualenddate", AppUtilities.getGMTconvert(mactualdays.get(mactualdays.size()-1).getActualdays())+" 18:30:00+00");
            updateBuilder.update();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(AppPreferences.getGroupId(mContext)==14||AppPreferences.getGroupId(mContext)==17||AppPreferences.getGroupId(mContext)==18){
            AppPreferences.setActualStartDate(mContext,mactualdays.get(0).getActualdays());
            AppPreferences.setActualEndDate(mContext,mactualdays.get(mactualdays.size()-1).getActualdays());
            Intent intent=new Intent(mContext, ProjectMonitorActivity.class);
            intent.putExtra("partnerid",mpartnerid);
            intent.putExtra("date",day1);
            mContext.startActivity(intent);
        }else if(AppPreferences.getGroupId(mContext)==29){
            Intent intent=new Intent(mContext, Survey.class);
            intent.putExtra("partnerid",mpartnerid);
            intent.putExtra("date",day1);
            mContext.startActivity(intent);
        }
    }
}