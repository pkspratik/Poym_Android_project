package com.example.kanthi.projectmonitoring.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Canvas.Floor;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.AssignedItems;
import com.example.kanthi.projectmonitoring.PoJo.BOQHeaders;
import com.example.kanthi.projectmonitoring.PoJo.ChangeReqCategories;
import com.example.kanthi.projectmonitoring.PoJo.ChangeReqViews;
import com.example.kanthi.projectmonitoring.PoJo.ChangeRequests;
import com.example.kanthi.projectmonitoring.PoJo.Checklist;
import com.example.kanthi.projectmonitoring.PoJo.DistributionRouteView;
import com.example.kanthi.projectmonitoring.PoJo.DistributionRoutes;
import com.example.kanthi.projectmonitoring.PoJo.DistributionSubAreas;
import com.example.kanthi.projectmonitoring.PoJo.DocumentTypes;
import com.example.kanthi.projectmonitoring.PoJo.Documents;
import com.example.kanthi.projectmonitoring.PoJo.Inventories;
import com.example.kanthi.projectmonitoring.PoJo.IssueTypes;
import com.example.kanthi.projectmonitoring.PoJo.ItemDefinition;
import com.example.kanthi.projectmonitoring.PoJo.ItemType;
import com.example.kanthi.projectmonitoring.PoJo.ItemsCategory;
import com.example.kanthi.projectmonitoring.PoJo.ParamCategories;
import com.example.kanthi.projectmonitoring.PoJo.Partnerviews;
import com.example.kanthi.projectmonitoring.PoJo.Patrols;
import com.example.kanthi.projectmonitoring.PoJo.PoNumbers;
import com.example.kanthi.projectmonitoring.PoJo.Priority;
import com.example.kanthi.projectmonitoring.PoJo.ProjectIssues;
import com.example.kanthi.projectmonitoring.PoJo.ProjectPercentages;
import com.example.kanthi.projectmonitoring.PoJo.ProjectProgress;
import com.example.kanthi.projectmonitoring.PoJo.ProjectResources;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRisk;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRiskViews;
import com.example.kanthi.projectmonitoring.PoJo.ProjectStatuses;
import com.example.kanthi.projectmonitoring.PoJo.Promotions;
import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.ResourceProgress;
import com.example.kanthi.projectmonitoring.PoJo.RiskTypes;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentPartnerSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummaries;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignments;
import com.example.kanthi.projectmonitoring.PoJo.RoutePartnerSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.SalesViews;
import com.example.kanthi.projectmonitoring.PoJo.Sales_Area;
import com.example.kanthi.projectmonitoring.PoJo.Shape;
import com.example.kanthi.projectmonitoring.PoJo.Status;
import com.example.kanthi.projectmonitoring.PoJo.SubTaskTypes;
import com.example.kanthi.projectmonitoring.PoJo.SurveyPromotions;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.PoJo.TaskItemLinkView;
import com.example.kanthi.projectmonitoring.PoJo.TaskRemarkLinks;
import com.example.kanthi.projectmonitoring.PoJo.TaskResourceLinkViews;
import com.example.kanthi.projectmonitoring.PoJo.TourTypes;
import com.example.kanthi.projectmonitoring.PoJo.User;
import com.example.kanthi.projectmonitoring.PoJo.WareHouses;
import com.example.kanthi.projectmonitoring.PoJo.Zones;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * Created by kanthi on 19/3/19.
 */
public class SyncInService extends Service {

    private AvahanSqliteDbHelper mDbHelper;
    private ResultReceiver mReceiver;
    public User mUser;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Log.d("syncin","hindi");
            mReceiver = intent.getParcelableExtra("RESULT_RECEIVER");
            mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
            RuntimeExceptionDao<User, Integer> userDao = mDbHelper.getUserRuntimeDao();
            mUser = userDao.queryBuilder().where().eq("id", AppPreferences.getLoggedUserName(SyncInService.this)).queryForFirst();

            User mSelectedUser = userDao.queryBuilder().where().eq("id", AppPreferences.getLoggedUserName(SyncInService.this)).queryForFirst();
            if (mSelectedUser != null) {
                RuntimeExceptionDao<User, Integer> userupdateDao = mDbHelper.getUserRuntimeDao();
                UpdateBuilder<User, Integer> updateBuilder = userupdateDao.updateBuilder();
                updateBuilder.where().eq("id", mSelectedUser.getId());
                updateBuilder.updateColumnValue("sync_in_time", null);
                updateBuilder.update();
            }

            fetchZones();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    private void fetchZones() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBZone(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Zones> zones = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Zones>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Zones.class);
                    RuntimeExceptionDao<Zones, Integer> dao = mDbHelper.getZonesRuntimeDao();
                    dao.create(zones);

                    fetchSalesViews();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchSalesViews(){
        if(mUser.getUsertype().equalsIgnoreCase("partner")){
            ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
            Call<String> call = service.getDBPartnerviews(AppPreferences.getUserId(this), mUser.getEmployeeid());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<Partnerviews> partners= new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<Partnerviews>>() {
                            }.getType());
                    try {
                        TableUtils.clearTable(mDbHelper.getConnectionSource(), Partnerviews.class);
                        RuntimeExceptionDao<Partnerviews, Integer> dao = mDbHelper.getPartnerviewsRuntimeDao();
                        dao.create(partners);

                        //fetchAssignedItems();
                        fetchRouteSalesViews();
                    } catch (SQLException e) {
                        sendResult(e.getMessage());
                        e.printStackTrace();
                        stopSelf();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else if(mUser.getUsertype().equalsIgnoreCase("executive")){
            final ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
            Call<String> call = service.getDBSalesViews(AppPreferences.getUserId(this), mUser.getEmployeeid());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<SalesViews> salesViews = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<SalesViews>>() {
                            }.getType());
                    try {
                        TableUtils.clearTable(mDbHelper.getConnectionSource(), SalesViews.class);
                        RuntimeExceptionDao<SalesViews, Integer> dao = mDbHelper.getSalesViewsRuntimeDao();
                        dao.create(salesViews);

                        if(mUser.getGroupId()==42){
                            fetchPatrols();
                        }else{
                            fetchTaskItemLinkViews();
                        }
                    } catch (SQLException e) {
                        sendResult(e.getMessage());
                        e.printStackTrace();
                        stopSelf();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t)
                {
                    t.printStackTrace();
                }
            });
        }else if(mUser.getUsertype().equalsIgnoreCase("null")){
            Toast.makeText(this, "Please,Define Valid User Type", Toast.LENGTH_SHORT).show();
        }
    }
    private void fetchTaskItemLinkViews(){
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call=null;
            call = service.getDBTaskItemLinkViews(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<TaskItemLinkView> taskItemLinkViewViews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<TaskItemLinkView>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), TaskItemLinkView.class);
                    RuntimeExceptionDao<TaskItemLinkView, Integer> dao = mDbHelper.getTaskItemLinkViewRuntimeDao();
                    dao.create(taskItemLinkViewViews);

                    fetchRouteSalesViews();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }
    private void fetchRouteSalesViews(){
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call=null;
        if(mUser.getGroupId()==17 || mUser.getGroupId()==18){
            call = service.getDBRoutePartnerSalesViews(AppPreferences.getUserId(this));
        }else {
            call = service.getDBRouteSalesViewsUsername(AppPreferences.getUserId(this));
        }
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<RouteSalesViews> routeSalesViews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<RouteSalesViews>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), RouteSalesViews.class);
                    RuntimeExceptionDao<RouteSalesViews, Integer> dao = mDbHelper.getRouteSalesViewsRuntimeDao();
                    dao.create(routeSalesViews);

                    fetchRouteAssignmentSummariesViews();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void fetchRouteAssignmentSummariesViews(){

        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1=null;
        if(mUser.getGroupId()==17 || mUser.getGroupId()==18){
            call1 = service1.getDBRouteAssignmentsPartnerSummariesViews(AppPreferences.getUserId(this));
        }else{
            call1 = service1.getDBRouteAssignmentsSummariesViews(AppPreferences.getUserId(this));
        }
        //call1 = service1.getDBRouteAssignmentsSummariesViews(AppPreferences.getUserId(this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<RouteAssignmentSummariesViews> routeAssignmentSummaryViewses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<RouteAssignmentSummariesViews>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), RouteAssignmentSummariesViews.class);
                    RuntimeExceptionDao<RouteAssignmentSummariesViews, Integer> dao = mDbHelper.getRouteAssignmentSummariesViewsRuntimeDao();
                    dao.create(routeAssignmentSummaryViewses);

                    fetchRouteAssignments();
                    //fetchRouteAssignmentPartnerSummariesViews();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void fetchRouteAssignments(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDBRouteAssignments(AppPreferences.getUserId(this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<RouteAssignments> routeAssignments = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<RouteAssignments>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), RouteAssignments.class);
                    RuntimeExceptionDao<RouteAssignments, Integer> dao = mDbHelper.getRouteAssignmentsRuntimeDao();
                    dao.create(routeAssignments);

                    fetchRouteAssignmentSummaries();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchRouteAssignmentSummaries(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDBRouteAssignmentsSummaries(AppPreferences.getUserId(this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<RouteAssignmentSummaries> routeAssignmentSummaries = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<RouteAssignmentSummaries>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), RouteAssignmentSummaries.class);
                    RuntimeExceptionDao<RouteAssignmentSummaries, Integer> dao = mDbHelper.getRouteAssignmentSummariesRuntimeDao();
                    dao.create(routeAssignmentSummaries);

                    fetchRemarks();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void fetchRemarks(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDBRemarks(AppPreferences.getUserId(this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Remarks> remarks = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Remarks>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Remarks.class);
                    RuntimeExceptionDao<Remarks, Integer> dao = mDbHelper.getRemarksRuntimeDao();
                    dao.create(remarks);

                    fetchSurveys();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchSurveys(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDBSurveys(AppPreferences.getUserId(this),mUser.getZoneId());
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Surveys> surveys = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Surveys>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Surveys.class);
                    RuntimeExceptionDao<Surveys, Integer> dao = mDbHelper.getSurveysRuntimeDao();
                    dao.create(surveys);

                    fetchPromotions();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchPromotions(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDBPromotions(AppPreferences.getUserId(this),mUser.getZoneId());
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Promotions> promotions = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Promotions>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Promotions.class);
                    RuntimeExceptionDao<Promotions, Integer> dao = mDbHelper.getPromotionsRuntimeDao();
                    dao.create(promotions);

                    fetchPoNumbers();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchPoNumbers(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDBPonumbers(AppPreferences.getUserId(this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<PoNumbers> poNumbers = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<PoNumbers>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), PoNumbers.class);
                    RuntimeExceptionDao<PoNumbers, Integer> dao = mDbHelper.getPoNumbersRuntimeDao();
                    dao.create(poNumbers);

                    fetchDistributionSubAreas();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchDistributionSubAreas(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDBDistributionSubAreas(AppPreferences.getUserId(this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<DistributionSubAreas> distributionSubAreas = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<DistributionSubAreas>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), DistributionSubAreas.class);
                    RuntimeExceptionDao<DistributionSubAreas, Integer> dao = mDbHelper.getDistributionSubAreasRuntimeDao();
                    dao.create(distributionSubAreas);

                    fetchSubTaskTypes();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchSubTaskTypes(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDBSubTask(AppPreferences.getUserId(this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<SubTaskTypes> subTaskTypes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<SubTaskTypes>>() {
                        }.getType());
                //Log.d("subtasktypes",""+subTaskTypes.size());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), SubTaskTypes.class);
                    RuntimeExceptionDao<SubTaskTypes, Integer> dao = mDbHelper.getSubTaskTypesRuntimeDao();
                    dao.create(subTaskTypes);

                    fetchTaskRemarkLinks();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchTaskRemarkLinks(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDBTaskRemarkLink(AppPreferences.getUserId(this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<TaskRemarkLinks> taskRemarkLinks = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<TaskRemarkLinks>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), TaskRemarkLinks.class);
                    RuntimeExceptionDao<TaskRemarkLinks, Integer> dao = mDbHelper.getTaskRemarkLinksRuntimeDao();
                    dao.create(taskRemarkLinks);

                    fetchTaskRemarkLinkViews();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchTaskRemarkLinkViews(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call=null;
        Log.e("called","done");
        if(mUser.getGroupId()==14 || mUser.getGroupId()==17 || mUser.getGroupId()==18){
            call = service1.getDBTaskResourceLinkViews(AppPreferences.getUserId(this),mUser.getZoneId());
            Log.e("called","if");
        }
        else if(mUser.getGroupId()==23 || mUser.getGroupId()==41 || mUser.getGroupId()==39){
            call = service1.getDBProjectResourceLinkViews(AppPreferences.getUserId(this));
            Log.e("called","else");
        }else if(mUser.getGroupId()==29){
            fetchProjectResources();
        }
        if(call!=null){
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    final ArrayList<TaskResourceLinkViews> taskResourceLinkViewses = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<TaskResourceLinkViews>>() {
                            }.getType());

                    try {
                        TableUtils.clearTable(mDbHelper.getConnectionSource(), TaskResourceLinkViews.class);
                        RuntimeExceptionDao<TaskResourceLinkViews, Integer> dao = mDbHelper.getTaskResourceLinkViewsRuntimeDao();
                        dao.create(taskResourceLinkViewses);

                        fetchProjectResources();
                    } catch (SQLException e) {
                        sendResult(e.getMessage());
                        e.printStackTrace();
                        stopSelf();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();

                }
            });
        }
    }

    private void fetchProjectResources(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDBProjectResources(AppPreferences.getUserId(this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectResources> projectResources = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectResources>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ProjectResources.class);
                    RuntimeExceptionDao<ProjectResources, Integer> dao = mDbHelper.getProjectResourcesRuntimeDao();
                    dao.create(projectResources);

                    fetchItemDefinitions();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchItemDefinitions(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = null;
        if(mUser.getGroupId() ==29 ){
            call1 = service1.getDBsurveyItemDefinitions(AppPreferences.getUserId(this),mUser.getZoneId());
        }else{
            call1 = service1.getDBBoqItemDefinitions(AppPreferences.getUserId(this));
        }
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ItemDefinition> itemDefinitions = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ItemDefinition>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ItemDefinition.class);
                    RuntimeExceptionDao<ItemDefinition, Integer> dao = mDbHelper.getItemDefinitionRuntimeDao();
                    dao.create(itemDefinitions);

                    fetchAssignedItems();
                    //fetchdistributionAreaShapes();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //TODO for floor map
    private void fetchdistributionAreaShapes(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<Floor> mFloorCall = service1.getDBDistributionAreaShapes(mUser.getDistributionAreaId(),
                AppPreferences.getUserId(this));
        mFloorCall.enqueue(new Callback<Floor>() {
            @Override
            public void onResponse(Call<Floor> call, Response<Floor> response) {
                if (response.isSuccessful()) {
                    //String mShape = response.body().getShapes().toString();
                    try {
                        TableUtils.clearTable(mDbHelper.getConnectionSource(), Shape.class);
                        RuntimeExceptionDao<Shape, Integer> dao = mDbHelper.getShapeRuntimeDao();
                        dao.create((Collection<Shape>) response.body().getShapes());

                        fetchAssignedItems();
                    } catch (SQLException e) {
                        sendResult(e.getMessage());
                        e.printStackTrace();
                        stopSelf();
                    }

                }
            }
            @Override
            public void onFailure(Call<Floor> call, Throwable t) {

            }
        });
    }

    private void fetchAssignedItems() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBAssignedItems(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<AssignedItems> assignedItems = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<AssignedItems>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), AssignedItems.class);
                    RuntimeExceptionDao<AssignedItems, Integer> dao = mDbHelper.getAssignedItemsRuntimeDao();
                    dao.create(assignedItems);

                    fetchWarehouses();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchWarehouses() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 =null;
        if(mUser.getGroupId() == 17 || mUser.getGroupId() ==18){
            call1= service1.getDBPartnerWareHouse(AppPreferences.getUserId(this));
        }else{
            call1= service1.getDBWareHouses(AppPreferences.getUserId(this),mUser.getEmployeeid());
        }if(call1!=null){
            call1.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<WareHouses> wareHouses = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<WareHouses>>() {
                            }.getType());
                    try {
                        TableUtils.clearTable(mDbHelper.getConnectionSource(), WareHouses.class);
                        RuntimeExceptionDao<WareHouses, Integer> dao = mDbHelper.getWareHousesRuntimeDao();
                        dao.create(wareHouses);

                        fetchInventories();
                    } catch (SQLException e) {
                        sendResult(e.getMessage());
                        e.printStackTrace();
                        stopSelf();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(this, getString(R.string.check_groupid), Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchInventories() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBInventories(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Inventories> inventories = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Inventories>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Inventories.class);
                    RuntimeExceptionDao<Inventories, Integer> dao = mDbHelper.getInventoriesRuntimeDao();
                    dao.create(inventories);

                    fetchExecutionDocuments();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchExecutionDocuments() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBExecutionDocument(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Documents> documents = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Documents>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Documents.class);
                    RuntimeExceptionDao<Documents, Integer> dao = mDbHelper.getDocumentsRuntimeDao();
                    dao.create(documents);

                    fetchDocumentTypes();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchDocumentTypes() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDocumentType(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<DocumentTypes> documentTypes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<DocumentTypes>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), DocumentTypes.class);
                    RuntimeExceptionDao<DocumentTypes, Integer> dao = mDbHelper.getDocumentTypesRuntimeDao();
                    dao.create(documentTypes);

                    fetchTourTypes();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchTourTypes() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBTourType(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<TourTypes> tourTypes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<TourTypes>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), TourTypes.class);
                    RuntimeExceptionDao<TourTypes, Integer> dao = mDbHelper.getTourTypesRuntimeDao();
                    dao.create(tourTypes);

                    fetchPriority();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchPriority() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBPriority(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Priority> priorities = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Priority>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Priority.class);
                    RuntimeExceptionDao<Priority, Integer> dao = mDbHelper.getPriorityRuntimeDao();
                    dao.create(priorities);

                    fetchIssueTypes();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchIssueTypes() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBIssueTypes(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<IssueTypes> issueTypes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<IssueTypes>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), IssueTypes.class);
                    RuntimeExceptionDao<IssueTypes, Integer> dao = mDbHelper.getIssueTypesRuntimeDao();
                    dao.create(issueTypes);

                    fetchStatus();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchStatus() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBStatus(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Status> statuses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Status>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Status.class);
                    RuntimeExceptionDao<Status, Integer> dao = mDbHelper.getStatusRuntimeDao();
                    dao.create(statuses);

                    //TODO check empty string in project statuses
                    fetchProjectStatus();
                    /*if(mUser.getGroupId()==14){
                        fetchParamCategories();
                    }else{
                        fetchProjectStatus();
                    }*/
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchProjectStatus() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBProjectStatuses(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectStatuses> projectStatuses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectStatuses>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ProjectStatuses.class);
                    RuntimeExceptionDao<ProjectStatuses, Integer> dao = mDbHelper.getProjectStatusesRuntimeDao();
                    dao.create(projectStatuses);

                    fetchParamCategories();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchParamCategories() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBParamCategories(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ParamCategories> paramCategories = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ParamCategories>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ParamCategories.class);
                    RuntimeExceptionDao<ParamCategories, Integer> dao = mDbHelper.getParamCategoriesRuntimeDao();
                    dao.create(paramCategories);

                    fetchChecklist();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchChecklist() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getChecklists(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Checklist> checklists = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Checklist>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Checklist.class);
                    RuntimeExceptionDao<Checklist, Integer> dao = mDbHelper.getChecklistRuntimeDao();
                    dao.create(checklists);

                    fetchItemCategories();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchItemCategories() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBItemCategories(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ItemsCategory> itemsCategories = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ItemsCategory>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ItemsCategory.class);
                    RuntimeExceptionDao<ItemsCategory, Integer> dao = mDbHelper.getItemsCategoryRuntimeDao();
                    dao.create(itemsCategories);

                    fetchItemTypes();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchItemTypes() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBItemTypes(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ItemType> itemTypes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ItemType>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ItemType.class);
                    RuntimeExceptionDao<ItemType, Integer> dao = mDbHelper.getItemTypeRuntimeDao();
                    dao.create(itemTypes);

                    fetchBOQHeaders();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchBOQHeaders() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBBoqHeaders(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<BOQHeaders> boqHeaders = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<BOQHeaders>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), BOQHeaders.class);
                    RuntimeExceptionDao<BOQHeaders, Integer> dao = mDbHelper.getBoqHeadersRuntimeDao();
                    dao.create(boqHeaders);

                    fetchChangeRequest();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchChangeRequest() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBChangeRequestopen(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ChangeRequests> changeRequests = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ChangeRequests>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ChangeRequests.class);
                    RuntimeExceptionDao<ChangeRequests, Integer> dao = mDbHelper.getChangeRequestsRuntimeDao();
                    dao.create(changeRequests);

                    fetchChangeRequestCategories();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchChangeRequestCategories() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBChangeRequestCategories(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ChangeReqCategories> changeReqCategories = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ChangeReqCategories>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ChangeReqCategories.class);
                    RuntimeExceptionDao<ChangeReqCategories, Integer> dao = mDbHelper.getChangeReqCategoriesRuntimeDao();
                    dao.create(changeReqCategories);

                    fetchChangeRequestCount();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchChangeRequestCount() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBCRViews(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ChangeReqViews> changeReqViews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ChangeReqViews>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ChangeReqViews.class);
                    RuntimeExceptionDao<ChangeReqViews, Integer> dao = mDbHelper.getChangeReqViewsRuntimeDao();
                    dao.create(changeReqViews);

                    fetchProjectRisk();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchProjectRisk() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBProjectRiskopen(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectRisk> projectRisks = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectRisk>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ProjectRisk.class);
                    RuntimeExceptionDao<ProjectRisk, Integer> dao = mDbHelper.getProjectRiskRuntimeDao();
                    dao.create(projectRisks);

                    fetchRiskTypes();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchRiskTypes() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBRiskType(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<RiskTypes> riskTypes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<RiskTypes>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), RiskTypes.class);
                    RuntimeExceptionDao<RiskTypes, Integer> dao = mDbHelper.getRiskTypesRuntimeDao();
                    dao.create(riskTypes);

                    fetchProjectPercentages();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }


    private void fetchProjectPercentages() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBProjectPercentages(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectPercentages> projectPercentages = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectPercentages>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ProjectPercentages.class);
                    RuntimeExceptionDao<ProjectPercentages, Integer> dao = mDbHelper.getProjectPercentagesRuntimeDao();
                    dao.create(projectPercentages);

                    fetchProjectProgress();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchProjectProgress() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBProjectProgress(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectProgress> projectProgresses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectProgress>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ProjectProgress.class);
                    RuntimeExceptionDao<ProjectProgress, Integer> dao = mDbHelper.getProjectProgressRuntimeDao();
                    dao.create(projectProgresses);

                    fetchProjectIssues();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchProjectIssues() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBProjectIssues(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectIssues> projectIssues = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectIssues>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ProjectIssues.class);
                    RuntimeExceptionDao<ProjectIssues, Integer> dao = mDbHelper.getProjectIssuesRuntimeDao();
                    dao.create(projectIssues);

                    fetchResourceProgress();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchResourceProgress() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBResourceProgress(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ResourceProgress> resourceProgresses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ResourceProgress>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ResourceProgress.class);
                    RuntimeExceptionDao<ResourceProgress, Integer> dao = mDbHelper.getResourceProgressRuntimeDao();
                    dao.create(resourceProgresses);

                    fetchProjectRiskViews();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchProjectRiskViews() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBProjectRiskViews(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectRiskViews> projectRiskViews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectRiskViews>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), ProjectRiskViews.class);
                    RuntimeExceptionDao<ProjectRiskViews, Integer> dao = mDbHelper.getProjectRiskViewsRuntimeDao();
                    dao.create(projectRiskViews);

                    fetchSalesAreas();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchSalesAreas() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBSalesAreas(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Sales_Area> sales_areas = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Sales_Area>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Sales_Area.class);
                    RuntimeExceptionDao<Sales_Area, Integer> dao = mDbHelper.getSales_AreaRuntimeDao();
                    dao.create(sales_areas);

                    fetchSurveyPromotions();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchSurveyPromotions() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBSurveyPrommotions(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<SurveyPromotions> surveyPromotions = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<SurveyPromotions>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), SurveyPromotions.class);
                    RuntimeExceptionDao<SurveyPromotions, Integer> dao = mDbHelper.getSurveyPromotionsRuntimeDao();
                    dao.create(surveyPromotions);

                    fetchDistributionRoutes();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchDistributionRoutes() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBDistributionroutes(AppPreferences.getUserId(this));
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<DistributionRoutes> distributionRoutes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<DistributionRoutes>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), DistributionRoutes.class);
                    RuntimeExceptionDao<DistributionRoutes, Integer> dao = mDbHelper.getDistributionRoutesRuntimeDao();
                    dao.create(distributionRoutes);

                    fetchDistributionRouteViews();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }
    private void fetchDistributionRouteViews() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBDistributionRouteViews(AppPreferences.getUserId(this),mUser.getZoneId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<DistributionRouteView> distributionRouteViews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<DistributionRouteView>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), DistributionRouteView.class);
                    RuntimeExceptionDao<DistributionRouteView, Integer> dao = mDbHelper.getDistributionRouteViewRuntimeDao();
                    dao.create(distributionRouteViews);

                    fetchPatrols();
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    private void fetchPatrols() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getDBPatrols(AppPreferences.getUserId(this),mUser.getId());
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Patrols> patrols = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Patrols>>() {
                        }.getType());
                try {
                    TableUtils.clearTable(mDbHelper.getConnectionSource(), Patrols.class);
                    RuntimeExceptionDao<Patrols, Integer> dao = mDbHelper.getPatrolsRuntimeDao();
                    dao.create(patrols);
                    //InsertUserLoginDetails(getDateTime());

                    try {
                        AppUtilities.writeDbToSdCard(SyncInService.this);
                        sendResult(getResources().getString(R.string.get_data1)+" "+getResources().getString(R.string.success1));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    sendResult(e.getMessage());
                    e.printStackTrace();
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }

    /*private void InsertUserLoginDetails(String date) {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        JsonObject userjson = new JsonObject();
        userjson.addProperty("userid", mUser.getId());
        userjson.addProperty("platform", "mobile");
        userjson.addProperty("operationflag", "syncin");
        userjson.addProperty("datetime", date);
        userjson.addProperty("role", mUser.getRoleId());
//        userjson.addProperty("zone", mUser.getZoneId());
//        userjson.addProperty("salesarea", mUser.getSalesAreaId());
//        userjson.addProperty("facility", mUser.getCoorgId());
//        userjson.addProperty("employeeid", mUser.getEmployeeId());
        Call<String> user_call = service.insertUserLoginDetails(AppPreferences.getUserId(this), userjson);
        user_call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("", "");
                if (response.raw().code() == 200) {
                    //fetchFW_f();
                    try {
                        RuntimeExceptionDao<User, Integer> userDao = mDbHelper.getUserRuntimeDao();
                        User mSelectedUser = userDao.queryBuilder().where().eq("id", AppPreferences.getLoggedUserName(SyncInService.this)).queryForFirst();
                        if (mSelectedUser != null) {
                            RuntimeExceptionDao<User, Integer> userupdateDao = mDbHelper.getUserRuntimeDao();
                            UpdateBuilder<User, Integer> updateBuilder = userupdateDao.updateBuilder();
                            updateBuilder.where().eq("id", mSelectedUser.getId());
                            updateBuilder.updateColumnValue("sync_in_time", getDateTime());
                            //updateBuilder.updateColumnValue("sync_out_time", null);
                            updateBuilder.updateColumnValue("update_flag", true);
                            updateBuilder.update();
                        }

                        AppUtilities.writeDbToSdCard(SyncInService.this);
                        sendResult(getResources().getString(R.string.get_data1)+" "+getResources().getString(R.string.success1));
                    } catch (Exception e) {
                        sendResult(e.getMessage());
                        e.printStackTrace();
                        Log.e("db", String.valueOf(e));
                        stopSelf();
                    }
                } else {
                    sendResult(String.valueOf(response.raw().code()));
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sendResult(t.getMessage());
                stopSelf();
            }
        });
    }*/

    private void sendResult(String message) {
        Bundle bundle = new Bundle();
        bundle.putString("result", message);
        mReceiver.send(1, bundle);
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        mDbHelper = null;
        super.onDestroy();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        return dateFormat.format(date);
    }
}
