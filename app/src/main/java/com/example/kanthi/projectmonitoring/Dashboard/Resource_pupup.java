package com.example.kanthi.projectmonitoring.Dashboard;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.ResourceList_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.TaskRemarks_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.WeeklyTask_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.PoNumbers;
import com.example.kanthi.projectmonitoring.PoJo.ProjectResources;
import com.example.kanthi.projectmonitoring.PoJo.Promotions;
import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummaries;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignments;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.PoJo.TaskRemarkLinks;
import com.example.kanthi.projectmonitoring.PoJo.TaskResourceLinkViews;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Resource_pupup extends DialogFragment {
    //Dialog d=null;
    private Button resou_list,resource_subtask,res_list_ok,res_list_cancel;
    private RecyclerView rv_resour_list;
    private ProgressBar progress_Bar;
    private Spinner sp_res_list;
    private List<TaskRemarkLinks> mremarklinks;
    private List<Remarks> mremarks;
    private List<ProjectResources> mresources;
    private List<TaskResourceLinkViews> mtaskResourceLinkViewses;
    private TaskRemarks_Sp_Adapter remarks_sp_adapter;
    private int remarkid,sp_id=0;
    private String remarkname;
    private ProjectResources projectresources;
    AlertDialog mdialog=null;
    ProgressDialog progress;

    AvahanSqliteDbHelper mDbHelper;

    public Resource_pupup() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AlertDialog.Builder ab=new AlertDialog.Builder(getActivity());
        View v=getActivity().getLayoutInflater().inflate(R.layout.activity_task_list,null);
        ab.setView(v);

        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);

        resou_list= v.findViewById(R.id.resourcelist);
        resource_subtask= v.findViewById(R.id.subtask);
        rv_resour_list= v.findViewById(R.id.rv_resourcelist);
        progress_Bar=v.findViewById(R.id.progressbar);
        sp_res_list= v.findViewById(R.id.sp_resourcelist);
        res_list_ok= v.findViewById(R.id.resourcelist_ok);
        res_list_cancel= v.findViewById(R.id.resourcelist_cancel);
        mdialog=ab.create();

        progress_Bar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.poym), PorterDuff.Mode.MULTIPLY);
        progress_Bar.setVisibility(View.VISIBLE);
        progress = new ProgressDialog(getActivity());
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading..");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        mremarks=new ArrayList<>();
        mtaskResourceLinkViewses=new ArrayList<>();
        Log.d("routeassignid",""+AppPreferences.getRouteAssignmentId(getActivity()));
        new FetchDetailsFromDbTask().execute();

        resource_subtask.setText(AppPreferences.getSubTaskType(getActivity())==null?"":AppPreferences.getSubTaskType(getActivity()));
        //TODO : resource code
        //networkRemarks();

        sp_res_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    remarkid = mremarklinks.get(sp_res_list.getSelectedItemPosition()-1).getDup_id();
                    remarkname = mremarklinks.get(sp_res_list.getSelectedItemPosition()-1).getName();
                    if(mtaskResourceLinkViewses.size()==0){
                        remarkname=null;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        res_list_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remarkname!=null){
                    for(TaskResourceLinkViews taskresourceslinkviews : mtaskResourceLinkViewses){
                        if(taskresourceslinkviews.getActualofresources()!=null || taskresourceslinkviews.getResourceapproved()!=null){
                            projectresources=new ProjectResources();
                            long masterid = System.currentTimeMillis();
                            AppPreferences.setResourceFlag(getActivity(),true);
                            remarkname=null;
                            Log.d("projectresource",""+mresources.size());
                            if(mresources.size()<=0){
                                projectresources.setId(masterid);
                                projectresources.setDate(AppUtilities.getDateTime());
                                projectresources.setRemarkid(remarkid);
                                projectresources.setTaskid(taskresourceslinkviews.getTasktypeid());
                                projectresources.setResourceid(taskresourceslinkviews.getResourcetypeid());
                                projectresources.setRemarkdesc(remarkname);
                                projectresources.setSubtasktypeid(AppPreferences.getSubTaskId(getActivity()));
                                projectresources.setAreaid(AppPreferences.getSubTypeId(getActivity()));
                                projectresources.setResourceqty(taskresourceslinkviews.getActualofresources());
                                projectresources.setRouteassignid(AppPreferences.getRouteAssignmentId(getActivity()));
                                projectresources.setRouteassignmentid(AppPreferences.getRouteAssignmentId(getActivity()));
                                projectresources.setRouteassignmentsummaryid(AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                                projectresources.setTaskresourcelinkid(taskresourceslinkviews.getId());
                                projectresources.setInsertFlag(true);
                                //postprojectresources();
                                try {
                                    RuntimeExceptionDao<ProjectResources, Integer> projectResourcesDao = mDbHelper.getProjectResourcesRuntimeDao();
                                    projectResourcesDao.create(projectresources);
                                    Log.d("projectresource","post");
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                mdialog.dismiss();
                            } else if(mresources.size()>0){
                                try {
                                    RuntimeExceptionDao<ProjectResources, Integer> projectResourcesDao = mDbHelper.getProjectResourcesRuntimeDao();
                                    UpdateBuilder<ProjectResources, Integer> updateBuilder = projectResourcesDao.updateBuilder();
                                    updateBuilder.where().eq("id",taskresourceslinkviews.getProjectResourceId());
                                    updateBuilder.updateColumnValue("updateflag",true);
                                    updateBuilder.updateColumnValue("date",AppUtilities.getDateTime());
                                    updateBuilder.updateColumnValue("remarkid",remarkid);
                                    updateBuilder.updateColumnValue("taskid",taskresourceslinkviews.getTasktypeid());
                                    updateBuilder.updateColumnValue("resourceid",taskresourceslinkviews.getResourcetypeid());
                                    updateBuilder.updateColumnValue("areaid",AppPreferences.getSubTypeId(getActivity()));
                                    updateBuilder.updateColumnValue("remarkdesc",remarkname);
                                    updateBuilder.updateColumnValue("subtasktypeid",AppPreferences.getSubTaskId(getActivity()));
                                    updateBuilder.updateColumnValue("resourceqty",taskresourceslinkviews.getActualofresources());
                                    updateBuilder.updateColumnValue("routeassignid",AppPreferences.getRouteAssignmentId(getActivity()));
                                    updateBuilder.updateColumnValue("routeassignmentid",AppPreferences.getRouteAssignmentId(getActivity()));
                                    updateBuilder.updateColumnValue("routeassignmentsummaryid",AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                                    updateBuilder.update();
                                    Log.d("projectresource","update");
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                mdialog.dismiss();
                            }
                            if(AppPreferences.getGroupId(getActivity())==14||AppPreferences.getGroupId(getActivity())==23||AppPreferences.getGroupId(getActivity())==41){
                                //projectresources.setQatime(taskresourceslinkviews.getResourceapproved());
                                //projectresources.setId(taskresourceslinkviews.getId());
                                mdialog.dismiss();
                                //putProjectResources();
                                try {
                                    RuntimeExceptionDao<ProjectResources, Integer> projectResourcesDao = mDbHelper.getProjectResourcesRuntimeDao();
                                    UpdateBuilder<ProjectResources, Integer> updateBuilder = projectResourcesDao.updateBuilder();
                                    updateBuilder.where().eq("id",taskresourceslinkviews.getId());
                                    updateBuilder.updateColumnValue("updateflag",true);
                                    updateBuilder.updateColumnValue("qatime",taskresourceslinkviews.getResourceapproved());
                                    updateBuilder.update();
                                    Log.d("projectresource","update");
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }else if(AppPreferences.getGroupId(getActivity())==39){
                                mdialog.dismiss();
                            }
                        }
                    }
                }else{
                    //Toast.makeText(ProjectMonitorActivity.this, "Plz Select Remark", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Are you sure to close Resources.?").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppPreferences.setResourceFlag(getActivity(),true);
                            mdialog.dismiss();
                        }
                    }).setNegativeButton(R.string.cancel,null).create().show();
                }
            }
        });
        res_list_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
            }
        });
        return mdialog;
    }

    private void networkRemarks(){
        progress.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getRemarks(AppPreferences.getUserId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Remarks> remarks = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Remarks>>() {
                        }.getType());
                mremarks = remarks;
                networkTaskRemarklinks();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                progress_Bar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void networkTaskRemarklinks(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getTaskRemarkLink(AppPreferences.getUserId(getActivity()),AppPreferences.getTourTypeId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<TaskRemarkLinks> remarkLinkses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<TaskRemarkLinks>>() {
                        }.getType());
                mremarklinks = remarkLinkses;
                if(mremarks.size()>0){
                    for(TaskRemarkLinks item:mremarklinks){
                        for(int i=0;i<mremarks.size();i++){
                            int remarkid= item.getRemarkid();
                            if(remarkid==mremarks.get(i).getId()){
                                item.setName(mremarks.get(i).getName());
                                item.setDup_id(mremarks.get(i).getId());
                                break;
                            }
                        }
                    }
                }
                networktaskresourcelinkviews();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                progress_Bar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void networktaskresourcelinkviews(){
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call=null;
        if(AppPreferences.getGroupId(getActivity())==14||AppPreferences.getGroupId(getActivity())==17||AppPreferences.getGroupId(getActivity())==18){
            call = service.getTaskResourceLinkViews(AppPreferences.getUserId(getActivity()),AppPreferences.getTourTypeId(getActivity()),AppPreferences.getZoneId(getActivity()));
        }
        else if((AppPreferences.getGroupId(getActivity())==23||AppPreferences.getGroupId(getActivity())==41||AppPreferences.getGroupId(getActivity())==39)){
            call = service.getProjectResourceLinkViews(AppPreferences.getUserId(getActivity()), (int) AppPreferences.getRouteAssignmentId(getActivity()));
        }
        if(call!=null){
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    final ArrayList<TaskResourceLinkViews> taskResourceLinkViewses = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<TaskResourceLinkViews>>() {
                            }.getType());
                    mtaskResourceLinkViewses=taskResourceLinkViewses;
                    networkProjectResources();
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    progress_Bar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            progress.dismiss();
            Toast.makeText(getActivity(), getString(R.string.check_groupid), Toast.LENGTH_SHORT).show();
        }
    }
    private void networkProjectResources(){
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service.getProjectResources(AppPreferences.getUserId(getActivity()), (int) AppPreferences.getRouteAssignmentId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectResources> resources = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectResources>>() {
                        }.getType());
                mresources = resources;
                progress.dismiss();
                progress_Bar.setVisibility(View.GONE);
                remarks_sp_adapter=new TaskRemarks_Sp_Adapter(getActivity(),mremarklinks);
                sp_res_list.setAdapter(remarks_sp_adapter);

                for(int g=0;g<mtaskResourceLinkViewses.size();g++){
                    for(int h=0;h<mresources.size();h++){
                        sp_id=mresources.get(h).getRemarkid();
                        if(mresources.get(h).getResourceid()==mtaskResourceLinkViewses.get(g).getResourcetypeid()
                                &&mresources.get(h).getTaskid()==mtaskResourceLinkViewses.get(g).getTasktypeid()
                                &&mresources.get(h).getTaskresourcelinkid()==mtaskResourceLinkViewses.get(g).getId()){
                            mtaskResourceLinkViewses.get(g).setActualofresources(mresources.get(h).getResourceqty());
                            mtaskResourceLinkViewses.get(g).setProjectResourceId((int) mresources.get(h).getId());
                            //mtaskResourceLinkViewses.get(g).setQa_res_time(mresources.get(h).getQatime());
                        }
                    }
                }
                ResourceList_Rv_Adapter adapter=new ResourceList_Rv_Adapter(mtaskResourceLinkViewses,mdialog);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                rv_resour_list.setAdapter(adapter);
                rv_resour_list.setLayoutManager(layoutManager);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                progress_Bar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postprojectresources(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                    projectresources, ProjectResources.class)).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        benjson.remove("id");
        call = service.insertProjectResource(AppPreferences.getUserId(getActivity()), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void putProjectResources(){
        final ProgressDialog progress1 = new ProgressDialog(getActivity());
        progress1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress1.setMessage("Loading..");
        progress1.setIndeterminate(true);
        progress1.setCancelable(false);
        progress1.show();
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            benjson= parser.parse(new Gson().toJson(
                    projectresources, ProjectResources.class)).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        call = service.updateProjectResources(AppPreferences.getUserId(getActivity()), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    progress1.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress1.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                RuntimeExceptionDao<TaskRemarkLinks, Integer> taskRemarkLinksDoa = mDbHelper.getTaskRemarkLinksRuntimeDao();
                QueryBuilder<TaskRemarkLinks, Integer> taskRemarkLinksQueryBuilder = taskRemarkLinksDoa.queryBuilder();
                taskRemarkLinksQueryBuilder.where().eq("tasktypeid", AppPreferences.getTourTypeId(getActivity()));
                PreparedQuery<TaskRemarkLinks> preparedQuery1 = taskRemarkLinksQueryBuilder.prepare();
                mremarklinks = taskRemarkLinksDoa.query(preparedQuery1);

                RuntimeExceptionDao<TaskResourceLinkViews, Integer> taskResourceLinkViewsDoa = mDbHelper.getTaskResourceLinkViewsRuntimeDao();
                Where<TaskResourceLinkViews, Integer> where = taskResourceLinkViewsDoa.queryBuilder().where();
                if(AppPreferences.getGroupId(getActivity())==14||AppPreferences.getGroupId(getActivity())==17||AppPreferences.getGroupId(getActivity())==18){
                    //taskResourceLinkViewsQueryBuilder.where().eq("tasktypeid", AppPreferences.getTourTypeId(getActivity()));
                    //taskResourceLinkViewsQueryBuilder.where().eq("zoneId", AppPreferences.getZoneId(getActivity()));
                    /*where.and(where.eq("tasktypeid",AppPreferences.getTourTypeId(getActivity())),
                            where.eq("zoneId",AppPreferences.getZoneId(getActivity())));*/
                    where.eq("tasktypeid",AppPreferences.getTourTypeId(getActivity()));
                }else {
                    where.eq("routeassignid",AppPreferences.getRouteAssignmentId(getActivity()));
                    //taskResourceLinkViewsQueryBuilder.where().eq("routeassignid",AppPreferences.getRouteAssignmentId(getActivity()));
                }
                //PreparedQuery<TaskResourceLinkViews> preparedQuery = taskResourceLinkViewsQueryBuilder.prepare();
                mtaskResourceLinkViewses = where.query();

                RuntimeExceptionDao<ProjectResources, Integer> projectResourcesDoa = mDbHelper.getProjectResourcesRuntimeDao();
                QueryBuilder<ProjectResources, Integer> projectResourcesQueryBuilder = projectResourcesDoa.queryBuilder();
                projectResourcesQueryBuilder.where().eq("routeassignmentid",AppPreferences.getRouteAssignmentId(getActivity()));
                PreparedQuery<ProjectResources> preparedQuery2 = projectResourcesQueryBuilder.prepare();
                mresources = projectResourcesDoa.query(preparedQuery2);

                RuntimeExceptionDao<Remarks,Integer> remarksDao =mDbHelper.getRemarksRuntimeDao();
                mremarks = remarksDao.queryForAll();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();
            progress_Bar.setVisibility(View.GONE);
            Log.d("remarksize",""+mremarks.size());
            if(mremarks.size()>0){
                for(TaskRemarkLinks item:mremarklinks){
                    for(int i=0;i<mremarks.size();i++){
                        int remarkid= item.getRemarkid();
                        if(remarkid==mremarks.get(i).getId()){
                            item.setName(mremarks.get(i).getName());
                            item.setDup_id(mremarks.get(i).getId());
                            break;
                        }
                    }
                }
            }

            remarks_sp_adapter=new TaskRemarks_Sp_Adapter(getActivity(),mremarklinks);
            sp_res_list.setAdapter(remarks_sp_adapter);

            for(int g=0;g<mtaskResourceLinkViewses.size();g++){
                for(int h=0;h<mresources.size();h++){
                    sp_id=mresources.get(h).getRemarkid();
                    if(mresources.get(h).getResourceid().equals(mtaskResourceLinkViewses.get(g).getResourcetypeid())
                            &&mresources.get(h).getTaskid().equals(mtaskResourceLinkViewses.get(g).getTasktypeid())
                            &&mresources.get(h).getTaskresourcelinkid().equals(mtaskResourceLinkViewses.get(g).getId())){
                        mtaskResourceLinkViewses.get(g).setActualofresources(mresources.get(h).getResourceqty());
                        mtaskResourceLinkViewses.get(g).setProjectResourceId(mresources.get(h).getId());
                        mtaskResourceLinkViewses.get(g).setQa_res_time(mresources.get(h).getQatime());
                    }
                }
            }
            ResourceList_Rv_Adapter adapter=new ResourceList_Rv_Adapter(mtaskResourceLinkViewses,mdialog);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv_resour_list.setAdapter(adapter);
            rv_resour_list.setLayoutManager(layoutManager);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        mDbHelper = null;
        super.onDestroy();
    }
}
