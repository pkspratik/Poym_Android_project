package com.example.kanthi.projectmonitoring.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.PoJo.AssignedItems;
import com.example.kanthi.projectmonitoring.PoJo.BOQHeaders;
import com.example.kanthi.projectmonitoring.PoJo.BOQTrailers;
import com.example.kanthi.projectmonitoring.PoJo.ChangeRequests;
import com.example.kanthi.projectmonitoring.PoJo.ChecklistAnswers;
import com.example.kanthi.projectmonitoring.PoJo.Inventories;
import com.example.kanthi.projectmonitoring.PoJo.IssueList;
import com.example.kanthi.projectmonitoring.PoJo.ParamDetails;
import com.example.kanthi.projectmonitoring.PoJo.Patrols;
import com.example.kanthi.projectmonitoring.PoJo.PoNumbers;
import com.example.kanthi.projectmonitoring.PoJo.ProjectResources;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRisk;
import com.example.kanthi.projectmonitoring.PoJo.ProjectStatuses;
import com.example.kanthi.projectmonitoring.PoJo.Promotions;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummaries;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignments;
import com.example.kanthi.projectmonitoring.PoJo.SurveyPromotions;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.PoJo.User;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanthi on 22-Mar-19.
 */
public class SyncOutService extends Service {


    private static final String TAG = SyncOutService.class.getName();
    private AvahanSqliteDbHelper mDbHelper;
    private ResultReceiver mReceiver;
    private User mSelectedUser;
    String Str_WorkerImageUpload="";
    int workerimagecount=0;
    long workerimagelength;
    File TempFile;
    File uploadFile2;
    String mStr_SyncOutTime;


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
            mReceiver = intent.getParcelableExtra("RESULT_RECEIVER");
            mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
            RuntimeExceptionDao<User, Integer> userDao = mDbHelper.getUserRuntimeDao();
            mSelectedUser = userDao.queryBuilder().where().eq("id", AppPreferences.getLoggedUserName(SyncOutService.this)).queryForFirst();

            RuntimeExceptionDao<User, Integer> userupdateDao = mDbHelper.getUserRuntimeDao();
            UpdateBuilder<User, Integer> updateBuilder = userupdateDao.updateBuilder();
            Log.e("syncout_userid", String.valueOf(mSelectedUser.getId()));
            updateBuilder.where().eq("id", mSelectedUser.getId());
            updateBuilder.updateColumnValue("sync_out_time", null);
            updateBuilder.update();

            if (mSelectedUser != null) {
                try {
                    File direct = new File(
                            Environment.getExternalStorageDirectory()
                                    + File.separator + "POYMBackup");
                    if (!direct.exists()) {
                        File wallpaperDirectory = new File(
                                Environment.getExternalStorageDirectory()
                                        + File.separator + "POYMBackup");
                        boolean success = wallpaperDirectory.mkdirs();
                        if (!success) {
                            Log.i("directory not created", "directory not created");
                        } else {
                            Log.i("directory created", "directory created");
                        }
                    }
                    TempFile = new File(
                            Environment.getExternalStorageDirectory()
                                    + File.separator + "POYMBackup",
                            mSelectedUser.getUsername() + getDateTime2() + ".txt");

                } catch (Exception e) {
                    e.printStackTrace();
                    // Log.e("Error", String.valueOf(e));

                }
            }
            if(mSelectedUser.getGroupId()==42){
                insertPatrols();
            }else{
                insertRouteAssignments(); // i am uncomment this calling statement
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            AppUtilities.writeDbToSdCard(SyncOutService.this);
            //sendResult("success");
        }
        catch (IOException e){
            e.printStackTrace();
        }*/
        return START_STICKY;
    }



    private void insertRouteAssignments() {
        try {
            final RuntimeExceptionDao<RouteAssignments, Integer> routeAssignmentsDao = mDbHelper.getRouteAssignmentsRuntimeDao();
            final RouteAssignments routeAssignments = routeAssignmentsDao.queryBuilder().where().//eq("is_inserted", true /*"submitflag","true"*/).or().
                    eq("is_updated", true).queryForFirst();

            /*final RouteAssignments routeAssignments = routeAssignmentsDao.queryBuilder().where().eq("submitflag","true")
                    .queryForFirst();*/


            if (routeAssignments != null) { // i am hide this line

               // if (routeAssignments.getSubmitflag().equals("true") ) { // i am change here add getSubmitFlage
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject routeAssignmentsJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    routeAssignmentsJson = parser.parse(new Gson().toJson(routeAssignments, RouteAssignments.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    routeAssignmentsJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                if (routeAssignments.isIsupdated()) {
                    addToFile(routeAssignmentsJson.toString(), "Updating routeAssignments");
                    call = service.updateRouteAssigments(AppPreferences.getUserId(this), routeAssignmentsJson);
                } else {
                    routeAssignmentsJson.remove("id");
                    addToFile(routeAssignmentsJson.toString(), "Inserting routeAssignments");
                    call = service.insertRouteAssigments(AppPreferences.getUserId(this), routeAssignmentsJson);
                }
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            int serverId = new JSONObject(response.body()).getInt("id");
                            Log.d("updated_routeid",""+routeAssignments.getId()+","+serverId);

                            UpdateBuilder<RouteAssignments, Integer> updateBuilder = routeAssignmentsDao.updateBuilder();
                            updateBuilder.where().eq("id", routeAssignments.getId());
                            //updateBuilder.updateColumnValue("sync_in_time", null);
                            updateBuilder.updateColumnValue("is_inserted", false);
                            updateBuilder.updateColumnValue("is_updated", false); // i amchanging here fals to true
                           // updateBuilder.updateColumnValue("is_updated", true); // i am added new line here
                            updateBuilder.update();

                            if(mSelectedUser.getGroupId()==29){
                                updateRouteAssignmentSurveyId(routeAssignments.getId(),serverId);
                            }else{
                                updateRouteAssignmentFWId(routeAssignments.getId(),serverId);
                            }

                            insertRouteAssignments();

                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertRouteAssignmentSummaries();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertRouteAssignmentSummaries() {
        try {
            final RuntimeExceptionDao<RouteAssignmentSummaries, Integer> routeAssignmentSummariesDao = mDbHelper.getRouteAssignmentSummariesRuntimeDao();
            final RouteAssignmentSummaries routeAssignmentSummaries = routeAssignmentSummariesDao.queryBuilder().where().eq("is_inserted", true).or().
                    eq("isupdateflag", true).queryForFirst();
            if (routeAssignmentSummaries != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call = null;
                JsonObject routeassignmentsummariesJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    routeassignmentsummariesJson = parser.parse(new Gson().toJson(routeAssignmentSummaries, RouteAssignmentSummaries.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    routeassignmentsummariesJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                Log.e("routeassignmentsumma",String.valueOf(routeassignmentsummariesJson));
                if (routeAssignmentSummaries.getUpdateflag()) {
                    addToFile(routeassignmentsummariesJson.toString(), "Updating RouteAssignmmentSummaries");
                    call = service.updateRouteAssigmentSummaries(AppPreferences.getUserId(this), routeassignmentsummariesJson);
                } /*else {

                }*/
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code()==200){
                            try {
                                Log.d("test",response.toString());
                                //long serverId = new JSONObject(response.body()).getLong("id");
                                //routeAssignmentSummaries.setUpdateflag(false);
                                //routeAssignmentSummaries.setInsertFlag(false);
                                //routeAssignmentSummariesDao.update(routeAssignmentSummaries);

                                try {
                                    UpdateBuilder<RouteAssignmentSummaries, Integer> updateBuilder = routeAssignmentSummariesDao.updateBuilder();
                                    updateBuilder.where().eq("id", routeAssignmentSummaries.getId());
                                    //updateBuilder.updateColumnValue("sync_in_time", null);
                                    updateBuilder.updateColumnValue("is_inserted", false);
                                    updateBuilder.updateColumnValue("isupdateflag", false);
                                    updateBuilder.update();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                                insertRouteAssignmentSummaries();

                            } catch (Exception e) {
                                e.printStackTrace();
                                sendResult(e.getMessage());
                                stopSelf();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertChecklistAnswers();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertChecklistAnswers() {
        try {
            final RuntimeExceptionDao<ChecklistAnswers, Integer> checklistAnswersDao = mDbHelper.getChecklistAnswersRuntimeDao();
            final ChecklistAnswers checklistAnswers = checklistAnswersDao.queryBuilder().where().eq("insert_flag", true).or().
                    eq("updateflag", true).queryForFirst();
            if (checklistAnswers != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject checklistAnswersJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    checklistAnswersJson = parser.parse(new Gson().toJson(checklistAnswers, ChecklistAnswers.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    checklistAnswersJson.addProperty("syncouttime", mStr_SyncOutTime);
                    checklistAnswersJson.addProperty("usernameid", mSelectedUser.getId());
                    checklistAnswersJson.addProperty("createdBy", mSelectedUser.getId());
                    checklistAnswersJson.addProperty("lastModifiedBy", mSelectedUser.getId());
                    checklistAnswersJson.addProperty("createdDate", AppUtilities.getDateTime());
                    checklistAnswersJson.addProperty("lastModifiedDate", AppUtilities.getDateTime());
                    checklistAnswersJson.addProperty("zoneId", mSelectedUser.getZoneId());
                }
                if(checklistAnswers.getUpdateflag() && !checklistAnswers.getInsertFlag()) {
                    addToFile(checklistAnswersJson.toString(), "Updating ChecklistAnswers");
                    call = service.updateChecklistAnswers(AppPreferences.getUserId(this), checklistAnswersJson);
                } else {
                    checklistAnswersJson.remove("id");
                    addToFile(checklistAnswersJson.toString(), "Inserting ChecklistAnswers");
                    call = service.insertChecklistAnswers(AppPreferences.getUserId(this), checklistAnswersJson);
                }

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            long serverId = new JSONObject(response.body()).getLong("id");
                            //projectResources.setUpdateflag(false);
                            //projectResources.setInsertFlag(false);
                            //projectResourcesDao.update(projectResources);
                            try {
                                UpdateBuilder<ChecklistAnswers, Integer> updateBuilder = checklistAnswersDao.updateBuilder();
                                updateBuilder.where().eq("id", checklistAnswers.getId());
                                //updateBuilder.updateColumnValue("sync_in_time", null);
                                updateBuilder.updateColumnValue("insert_flag", false);
                                updateBuilder.updateColumnValue("updateflag", false);
                                updateBuilder.update();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            insertChecklistAnswers();
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertProjectResources();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertProjectResources() {
        try {
            final RuntimeExceptionDao<ProjectResources, Integer> projectResourcesDao = mDbHelper.getProjectResourcesRuntimeDao();
            final ProjectResources projectResources = projectResourcesDao.queryBuilder().where().eq("insert_flag", true).or().
                    eq("updateflag", true).queryForFirst();
            if (projectResources != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject projectResourcesJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    projectResourcesJson = parser.parse(new Gson().toJson(projectResources, ProjectResources.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    projectResourcesJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                if(projectResources.getUpdateflag() && !projectResources.getInsertFlag()) {
                    addToFile(projectResourcesJson.toString(), "Updating ProjectResources");
                    call = service.updateProjectResources(AppPreferences.getUserId(this), projectResourcesJson);
                } else {
                    projectResourcesJson.remove("id");
                    addToFile(projectResourcesJson.toString(), "Inserting ProjectResources");
                    call = service.insertProjectResource(AppPreferences.getUserId(this), projectResourcesJson);
                }

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            long serverId = new JSONObject(response.body()).getLong("id");
                            //projectResources.setUpdateflag(false);
                            //projectResources.setInsertFlag(false);
                            //projectResourcesDao.update(projectResources);
                            try {
                                UpdateBuilder<ProjectResources, Integer> updateBuilder = projectResourcesDao.updateBuilder();
                                updateBuilder.where().eq("id", projectResources.getId());
                                //updateBuilder.updateColumnValue("sync_in_time", null);
                                updateBuilder.updateColumnValue("insert_flag", false);
                                updateBuilder.updateColumnValue("updateflag", false);
                                updateBuilder.update();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            insertProjectResources();
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertInventories();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertInventories() {
        try {
            final RuntimeExceptionDao<Inventories, Integer> inventoriesDao = mDbHelper.getInventoriesRuntimeDao();
            final Inventories inventories = inventoriesDao.queryBuilder().where().eq("insert_flag", true).or().
                    eq("updateflag", true).queryForFirst();
            if (inventories != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call = null;
                JsonObject inventoriesJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    inventoriesJson = parser.parse(new Gson().toJson(inventories, Inventories.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    inventoriesJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                if (inventories.getUpdateflag()) {
                    addToFile(inventoriesJson.toString(), "Updating Inventories");
                    call = service.updateinventories(AppPreferences.getUserId(this), inventoriesJson);
                } /*else {
                    inventoriesJson.remove("id");
                    addToFile(inventoriesJson.toString(), "Inserting Inventories");
                    call = service.insertProjectResource(AppPreferences.getUserId(this), inventoriesJson);
                }*/

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            long serverId = new JSONObject(response.body()).getLong("id");
                            //inventories.setUpdateflag(false);
                            //inventories.setInsertFlag(false);
                            //inventoriesDao.update(inventories);

                            try {
                                UpdateBuilder<Inventories, Integer> updateBuilder = inventoriesDao.updateBuilder();
                                updateBuilder.where().eq("id", inventories.getId());
                                //updateBuilder.updateColumnValue("sync_in_time", null);
                                updateBuilder.updateColumnValue("insert_flag", false);
                                updateBuilder.updateColumnValue("updateflag", false);
                                updateBuilder.update();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            insertInventories();

                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertAssignedItems();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertAssignedItems() {
        try {
            final RuntimeExceptionDao<AssignedItems, Integer> assignedItemsDao = mDbHelper.getAssignedItemsRuntimeDao();
            final AssignedItems assignedItems = assignedItemsDao.queryBuilder().where().eq("insert_flag", true).or().
                    eq("updateflag", true).queryForFirst();
            if (assignedItems != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject assignedItemsJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    assignedItemsJson = parser.parse(new Gson().toJson(assignedItems, AssignedItems.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    assignedItemsJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                if (assignedItems.getUpdateflag()) {
                    addToFile(assignedItemsJson.toString(), "Updating AssignedItems");
                    call = service.updateAssigneditems(AppPreferences.getUserId(this), assignedItemsJson);
                } else {
                    assignedItemsJson.remove("id");
                    addToFile(assignedItemsJson.toString(), "Inserting AssignedItems");
                    call = service.insertAssigneditems(AppPreferences.getUserId(this), assignedItemsJson);
                }

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            long serverId = new JSONObject(response.body()).getLong("id");
                            //assignedItems.setUpdateflag(false);
                            //assignedItems.setInsertFlag(false);
                            //assignedItemsDao.update(assignedItems);

                            try {
                                UpdateBuilder<AssignedItems, Integer> updateBuilder = assignedItemsDao.updateBuilder();
                                updateBuilder.where().eq("id", assignedItems.getId());
                                //updateBuilder.updateColumnValue("sync_in_time", null);
                                updateBuilder.updateColumnValue("insert_flag", false);
                                updateBuilder.updateColumnValue("updateflag", false);
                                updateBuilder.update();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            insertAssignedItems();

                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertPONumbers();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertPONumbers() {

        Log.d("ponubers","ponummbers inserted");
        try {
            final RuntimeExceptionDao<PoNumbers, Integer> poNumbersDao = mDbHelper.getPoNumbersRuntimeDao();
            final PoNumbers poNumbers = poNumbersDao.queryBuilder().where().eq("insert_flag", true).or().
                    eq("updateflag", true).queryForFirst();
            if (poNumbers != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call = null;
                JsonObject poNumbersJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    poNumbersJson = parser.parse(new Gson().toJson(poNumbers, PoNumbers.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    poNumbersJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                if (poNumbers.getUpdateflag()) {
                    addToFile(poNumbersJson.toString(), "Updating PoNumbers");
                    call = service.updatePoNumbers(AppPreferences.getUserId(this), poNumbersJson);
                } /*else {
                    poNumbersJson.remove("id");
                    addToFile(poNumbersJson.toString(), "Inserting PoNumbers");
                    call = service.insertProjectResource(AppPreferences.getUserId(this), poNumbersJson);
                }*/

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            long serverId = new JSONObject(response.body()).getLong("id");
                            //poNumbers.setUpdateflag(false);
                            //poNumbers.setInsertFlag(false);
                            //poNumbersDao.update(poNumbers);

                            try {
                                UpdateBuilder<PoNumbers, Integer> updateBuilder = poNumbersDao.updateBuilder();
                                updateBuilder.where().eq("id", poNumbers.getId());
                                //updateBuilder.updateColumnValue("sync_in_time", null);
                                //updateBuilder.updateColumnValue("insert_flag", false);
                                updateBuilder.updateColumnValue("updateflag", false);
                                updateBuilder.update();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            insertPONumbers();

                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertPromotions();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertPromotions() {
        Log.d("insertPromotions","prommotions inserted");
        try {
            final RuntimeExceptionDao<Promotions, Integer> promotionsDao = mDbHelper.getPromotionsRuntimeDao();
            /*final Promotions promotions = promotionsDao.queryBuilder().where().eq("insert_flag", true).or().
                    eq("updateflag", true).and().eq("previous_flag",true).queryForFirst();*/
            QueryBuilder<Promotions, Integer> queryBuilder = promotionsDao.queryBuilder();
            Where<Promotions, Integer> where = queryBuilder.where();
            where.and(
                    where.or(
                            where.eq("insert_flag", true),
                            where.eq("updateflag", true)),
                    where.eq("previous_flag",false));

            final Promotions promotions = queryBuilder.queryForFirst();
            if (promotions != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject promotionsJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    promotionsJson = parser.parse(new Gson().toJson(promotions, Promotions.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    promotionsJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                if (promotions.getUpdateflag()) {
                    addToFile(promotionsJson.toString(), "Updating promotions");
                    call = service.updatePromotions(AppPreferences.getUserId(this), promotionsJson);
                } else {
                    promotionsJson.remove("id");
                    addToFile(promotionsJson.toString(), "Inserting promotions");
                    call = service.insertPromotions(AppPreferences.getUserId(this), promotionsJson);
                }

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            long serverId = new JSONObject(response.body()).getLong("id");
                            //promotions.setUpdateflag(false);
                            //promotions.setInsertFlag(false);
                            //promotionsDao.update(promotions);

                            try {
                                UpdateBuilder<Promotions, Integer> updateBuilder = promotionsDao.updateBuilder();
                                updateBuilder.where().eq("id", promotions.getId());
                                //updateBuilder.updateColumnValue("sync_in_time", null);
                                updateBuilder.updateColumnValue("insert_flag", false);
                                updateBuilder.updateColumnValue("updateflag", false);
                                updateBuilder.update();

                                UpdateBuilder<Promotions, Integer> updatepreviouslocationBuilder = promotionsDao.updateBuilder();
                                updatepreviouslocationBuilder.where().eq("previouslocation", promotions.getId());
                                updatepreviouslocationBuilder.updateColumnValue("previouslocation", serverId);
                                updatepreviouslocationBuilder.updateColumnValue("previous_flag", false);
                                updatepreviouslocationBuilder.update();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            insertPromotions();

                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertIssuesList();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertPreviousLocationPromotions() {
        try {
            final RuntimeExceptionDao<Promotions, Integer> promotionsDao = mDbHelper.getPromotionsRuntimeDao();
            /*final Promotions promotions = promotionsDao.queryBuilder().where().eq("insert_flag", true).or().
                    eq("updateflag", true).and().eq("previous_flag",true).queryForFirst();*/
            QueryBuilder<Promotions, Integer> queryBuilder = promotionsDao.queryBuilder();
            Where<Promotions, Integer> where = queryBuilder.where();
            where.and(
                    where.or(
                            where.eq("insert_flag", true),
                            where.eq("updateflag", true)),
                    where.eq("previous_flag",true));

            final Promotions promotions = queryBuilder.queryForFirst();
            if (promotions != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject promotionsJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    promotionsJson = parser.parse(new Gson().toJson(promotions, Promotions.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    promotionsJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                if (promotions.getUpdateflag()) {
                    addToFile(promotionsJson.toString(), "Updating previous locations promotions");
                    call = service.updatePromotions(AppPreferences.getUserId(this), promotionsJson);
                } else {
                    promotionsJson.remove("id");
                    addToFile(promotionsJson.toString(), "Inserting previous locations promotions");
                    call = service.insertPromotions(AppPreferences.getUserId(this), promotionsJson);
                }

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            long serverId = new JSONObject(response.body()).getLong("id");
                            //promotions.setUpdateflag(false);
                            //promotions.setInsertFlag(false);
                            //promotionsDao.update(promotions);

                            try {
                                UpdateBuilder<Promotions, Integer> updateBuilder = promotionsDao.updateBuilder();
                                updateBuilder.where().eq("id", promotions.getId());
                                //updateBuilder.updateColumnValue("sync_in_time", null);
                                updateBuilder.updateColumnValue("insert_flag", false);
                                updateBuilder.updateColumnValue("updateflag", false);
                                updateBuilder.update();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            insertPromotions();

                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertIssuesList();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }


    private void insertIssuesList() {
        try {
            final RuntimeExceptionDao<IssueList, Integer> issueListsdao = mDbHelper.getIssueListRuntimeDao();
            final IssueList issueList = issueListsdao.queryBuilder().where().eq("insert_flag", true).or().
                    eq("updateflag", true).queryForFirst();
            if (issueList != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject issuelistJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    issuelistJson = parser.parse(new Gson().toJson(issueList, IssueList.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    issuelistJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                if (issueList.getUpdateflag()) {
                    addToFile(issuelistJson.toString(), "Updating promotions");
                    call = service.updateIssueList(AppPreferences.getUserId(this), issuelistJson);
                } else {
                    issuelistJson.remove("id");
                    addToFile(issuelistJson.toString(), "Inserting promotions");
                    call = service.insertIssueList(AppPreferences.getUserId(this), issuelistJson);
                }

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            long serverId = new JSONObject(response.body()).getLong("id");
                            //issueList.setUpdateflag(false);
                            //issueList.setInsertFlag(false);
                            //issueListsdao.update(issueList);

                            try {
                                UpdateBuilder<IssueList, Integer> updateBuilder = issueListsdao.updateBuilder();
                                updateBuilder.where().eq("id", issueList.getId());
                                //updateBuilder.updateColumnValue("sync_in_time", null);
                                updateBuilder.updateColumnValue("insert_flag", false);
                                updateBuilder.updateColumnValue("updateflag", false);
                                updateBuilder.update();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            insertIssuesList();

                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertProjectStatuses();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertProjectStatuses() {
        try {
            final RuntimeExceptionDao<ProjectStatuses, Integer> projectStatusesDao = mDbHelper.getProjectStatusesRuntimeDao();
            final ProjectStatuses projectStatuses = projectStatusesDao.queryBuilder().where().eq("insert_flag", true).or().
                    eq("updateflag", true).queryForFirst();
            if (projectStatuses != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject projectStatusesJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    projectStatusesJson = parser.parse(new Gson().toJson(projectStatuses, ProjectStatuses.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    projectStatusesJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                if (projectStatuses.getUpdateflag()) {
                    addToFile(projectStatusesJson.toString(), "Updating projectStatuses");
                    call = service.updateProjectStatuses(AppPreferences.getUserId(this), projectStatusesJson);
                } else {
                    projectStatusesJson.remove("id");
                    addToFile(projectStatusesJson.toString(), "Inserting projectStatuses");
                    call = service.insertProjectStatus(AppPreferences.getUserId(this), projectStatusesJson);
                }

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            int serverId = new JSONObject(response.body()).getInt("id");

                            //todo
                            //updateProjectStatusesId(projectStatuses.getId(),serverId);

                            try {
                                UpdateBuilder<ProjectStatuses, Integer> updateBuilder = projectStatusesDao.updateBuilder();
                                updateBuilder.where().eq("id", projectStatuses.getId());
                                //updateBuilder.updateColumnValue("sync_in_time", null);
                                updateBuilder.updateColumnValue("insert_flag", false);
                                updateBuilder.updateColumnValue("updateflag", false);
                                updateBuilder.update();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            insertProjectStatuses();

                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertSurveys();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertSurveys() {
        try {
            final RuntimeExceptionDao<Surveys, Integer> surveysDao = mDbHelper.getSurveysRuntimeDao();


           /* QueryBuilder<Surveys, Integer> queryBuilder = surveysDao.queryBuilder();
            Where<Surveys, Integer> where = queryBuilder.where();
            where.and(
                    where.or(
                            where.eq("insert_flag", true),
                            where.eq("updateflag", true)),
                    where.eq("previous_flag",false));

            final Surveys surveys = queryBuilder.queryForFirst();*/




            final Surveys surveys = surveysDao.queryBuilder().where().eq("insert_flag", true).or().
                    eq("updateflag", true).queryForFirst();
            if (surveys != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject surveysJson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    surveysJson = parser.parse(new Gson().toJson(surveys, Surveys.class)).getAsJsonObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mSelectedUser != null) {
                    surveysJson.addProperty("syncouttime", mStr_SyncOutTime);
                }
                if (surveys.getUpdateflag()) {
                    addToFile(surveysJson.toString(), "Updating surveys");
                    call = service.updateSurveys(AppPreferences.getUserId(this), surveysJson);
                } else {
                    surveysJson.remove("id");
                    addToFile(surveysJson.toString(), "Inserting surveys");
                    call = service.insertSurveys(AppPreferences.getUserId(this), surveysJson);
                }

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            Log.d("test",response.toString());
                            int serverId = new JSONObject(response.body()).getInt("id");

                            UpdateSurveysId(surveys.getId(),serverId);
                            /*if(mSelectedUser.getGroupId()==29){
                                UpdateSurveysId(surveys.getId(),serverId);
                            }*/
                            //surveys.setUpdateflag(false);
                            //surveys.setInsertFlag(false);
                            //surveysDao.update(surveys);
                            try {
                                UpdateBuilder<Surveys, Integer> updateBuilder = surveysDao.updateBuilder();
                                updateBuilder.where().eq("id", surveys.getId());
                                //updateBuilder.updateColumnValue("sync_in_time", null);
                                updateBuilder.updateColumnValue("insert_flag", false);
                                updateBuilder.updateColumnValue("updateflag", false);
                                updateBuilder.update();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            insertSurveys();

                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                insertParamDetails();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertParamDetails() {
        try {
            final RuntimeExceptionDao<ParamDetails, Integer> paramDetailsDao = mDbHelper.getParamDetailsRuntimeDao();
            final ParamDetails paramDetails = paramDetailsDao.queryBuilder().where().eq("insert_flag", true).queryForFirst();
            if (paramDetails != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject paramDetailsjson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    paramDetailsjson = parser.parse(new Gson().toJson(
                            paramDetails, ParamDetails.class)).getAsJsonObject();
                    if (mSelectedUser != null) {
                        paramDetailsjson.addProperty("syncouttime", mStr_SyncOutTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                paramDetailsjson.remove("id");
                addToFile(paramDetailsjson.toString(), "Inserting paramDetails");
                call = service.insertParamDetails(AppPreferences.getUserId(this), paramDetailsjson);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            DeleteBuilder<ParamDetails, Integer> deleteBuilder = paramDetailsDao.deleteBuilder();
                            deleteBuilder.where().eq("id", paramDetails.getId());
                            deleteBuilder.delete();

                            insertParamDetails();
                        } catch (SQLException e) {
                            sendResult(e.getMessage());
                            e.printStackTrace();
                            stopSelf();
                        } catch (Exception e) {

                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                //updateTodo();
                insertBoqHeaders();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertBoqHeaders() {
        try {
            final RuntimeExceptionDao<BOQHeaders, Integer> boqHeadersDao = mDbHelper.getBoqHeadersRuntimeDao();
            final BOQHeaders boqHeaders = boqHeadersDao.queryBuilder().where().eq("insert_flag", true).queryForFirst();
            if (boqHeaders != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject boqHeadersjson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    boqHeadersjson = parser.parse(new Gson().toJson(
                            boqHeaders, BOQHeaders.class)).getAsJsonObject();
                    if (mSelectedUser != null) {
                        boqHeadersjson.addProperty("syncouttime", mStr_SyncOutTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boqHeadersjson.remove("id");
                addToFile(boqHeadersjson.toString(), "Inserting boqHeaders");
                call = service.insertBoqHeaders(AppPreferences.getUserId(this), boqHeadersjson);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            int recordId = new JSONObject(response.body()).getInt("id");

                            updateBOQTrailers(boqHeaders.getId(),recordId);

                            DeleteBuilder<BOQHeaders, Integer> deleteBuilder = boqHeadersDao.deleteBuilder();
                            deleteBuilder.where().eq("id", boqHeaders.getId());
                            deleteBuilder.delete();

                            insertBoqHeaders();

                        } catch (SQLException e) {
                            sendResult(e.getMessage());
                            e.printStackTrace();
                            stopSelf();
                        } catch (Exception e) {

                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                //updateTodo();
                insertTrailers();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertTrailers() {
        try {
            final RuntimeExceptionDao<BOQTrailers, Integer> boqTrailersDao = mDbHelper.getBoqTrailersRuntimeDao();
            final BOQTrailers boqTrailers = boqTrailersDao.queryBuilder().where().eq("insert_flag", true).queryForFirst();
            if (boqTrailers != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject boqTrailersjson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    boqTrailersjson = parser.parse(new Gson().toJson(
                            boqTrailers, BOQTrailers.class)).getAsJsonObject();
                    if (mSelectedUser != null) {
                        boqTrailersjson.addProperty("syncouttime", mStr_SyncOutTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("trailersresponse",""+boqTrailersjson.toString());
                boqTrailersjson.remove("id");
                addToFile(boqTrailersjson.toString(), "Inserting boqTrailers");
                call = service.insertBoqTrailers(AppPreferences.getUserId(this), boqTrailersjson);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("trailersresponse1",""+response.body());
                        try {
                            //int recordId = new JSONObject(response.body()).getInt("id");

                            DeleteBuilder<BOQTrailers, Integer> deleteBuilder = boqTrailersDao.deleteBuilder();
                            deleteBuilder.where().eq("id", boqTrailers.getId());
                            deleteBuilder.delete();

                            insertTrailers();
                        } catch (SQLException e) {
                            sendResult(e.getMessage());
                            e.printStackTrace();
                            stopSelf();
                        } catch (Exception e) {

                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                //updateTodo();
                insertSurveyPromotions();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertSurveyPromotions() {
        try {
            final RuntimeExceptionDao<SurveyPromotions, Integer> surveyPromotionsDao = mDbHelper.getSurveyPromotionsRuntimeDao();
            final SurveyPromotions surveyPromotions = surveyPromotionsDao.queryBuilder().where().eq("insert_flag", true).queryForFirst();
            if (surveyPromotions != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject surveyPromotionsjson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    surveyPromotionsjson = parser.parse(new Gson().toJson(
                            surveyPromotions, SurveyPromotions.class)).getAsJsonObject();
                    if (mSelectedUser != null) {
                        surveyPromotionsjson.addProperty("syncouttime", mStr_SyncOutTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                surveyPromotionsjson.remove("id");
                addToFile(surveyPromotionsjson.toString(), "Inserting surveyPromotions");
                call = service.insertSurveyPromotions(AppPreferences.getUserId(this), surveyPromotionsjson);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            //int recordId = new JSONObject(response.body()).getInt("id");

                            DeleteBuilder<SurveyPromotions, Integer> deleteBuilder = surveyPromotionsDao.deleteBuilder();
                            deleteBuilder.where().eq("id", surveyPromotions.getId());
                            deleteBuilder.delete();

                            insertSurveyPromotions();
                        } catch (SQLException e) {
                            sendResult(e.getMessage());
                            e.printStackTrace();
                            stopSelf();
                        } catch (Exception e) {

                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                //updateTodo();
                insertChangeRequest();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertChangeRequest() {
        try {
            final RuntimeExceptionDao<ChangeRequests, Integer> changeRequestsDao = mDbHelper.getChangeRequestsRuntimeDao();
            final ChangeRequests changeRequests = changeRequestsDao.queryBuilder().where().eq("insert_flag", true).queryForFirst();
            if (changeRequests != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject changeRequestsjson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    changeRequestsjson = parser.parse(new Gson().toJson(
                            changeRequests, ChangeRequests.class)).getAsJsonObject();
                    if (mSelectedUser != null) {
                        changeRequestsjson.addProperty("syncouttime", mStr_SyncOutTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                changeRequestsjson.remove("id");
                addToFile(changeRequestsjson.toString(), "Inserting changeRequests");
                call = service.insertChangeRequest(AppPreferences.getUserId(this), changeRequestsjson);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            //int recordId = new JSONObject(response.body()).getInt("id");

                            DeleteBuilder<ChangeRequests, Integer> deleteBuilder = changeRequestsDao.deleteBuilder();
                            deleteBuilder.where().eq("id", changeRequests.getId());
                            deleteBuilder.delete();

                            insertChangeRequest();
                        } catch (SQLException e) {
                            sendResult(e.getMessage());
                            e.printStackTrace();
                            stopSelf();
                        } catch (Exception e) {

                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                //updateTodo();
                insertProjectRisk();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertProjectRisk() {
        try {
            final RuntimeExceptionDao<ProjectRisk, Integer> projectRiskDao = mDbHelper.getProjectRiskRuntimeDao();
            final ProjectRisk projectRisk = projectRiskDao.queryBuilder().where().eq("insert_flag", true).queryForFirst();
            if (projectRisk != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject projectRiskjson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    projectRiskjson = parser.parse(new Gson().toJson(
                            projectRisk, ProjectRisk.class)).getAsJsonObject();
                    if (mSelectedUser != null) {
                        projectRiskjson.addProperty("syncouttime", mStr_SyncOutTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                projectRiskjson.remove("id");
                addToFile(projectRiskjson.toString(), "Inserting projectRisk");
                call = service.insertChangeRequest(AppPreferences.getUserId(this), projectRiskjson);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            //int recordId = new JSONObject(response.body()).getInt("id");

                            DeleteBuilder<ProjectRisk, Integer> deleteBuilder = projectRiskDao.deleteBuilder();
                            deleteBuilder.where().eq("id", projectRisk.getId());
                            deleteBuilder.delete();

                            insertProjectRisk();
                        } catch (SQLException e) {
                            sendResult(e.getMessage());
                            e.printStackTrace();
                            stopSelf();
                        } catch (Exception e) {

                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                //updateTodo();
                insertPatrols();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void insertPatrols() {
        try {
            final RuntimeExceptionDao<Patrols, Integer> patrolsDao = mDbHelper.getPatrolsRuntimeDao();
            final Patrols patrols = patrolsDao.queryBuilder().where().eq("insert_flag", true).queryForFirst();
            if (patrols != null) {
                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                        getProjectMonitorNetworkService();

                Call<String> call;
                JsonObject patrolsjson = new JsonObject();
                try {
                    JsonParser parser = new JsonParser();
                    patrolsjson = parser.parse(new Gson().toJson(
                            patrols, Patrols.class)).getAsJsonObject();
                    if (mSelectedUser != null) {
                        patrolsjson.addProperty("syncouttime", mStr_SyncOutTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                patrolsjson.remove("id");
                addToFile(patrolsjson.toString(), "Inserting patrols");
                call = service.insertPatrols(AppPreferences.getUserId(this), patrolsjson);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            //int recordId = new JSONObject(response.body()).getInt("id");

                            DeleteBuilder<Patrols, Integer> deleteBuilder = patrolsDao.deleteBuilder();
                            deleteBuilder.where().eq("id", patrols.getId());
                            deleteBuilder.delete();

                            insertPatrols();
                        } catch (SQLException e) {
                            sendResult(e.getMessage());
                            e.printStackTrace();
                            stopSelf();
                        } catch (Exception e) {

                            e.printStackTrace();
                            sendResult(e.getMessage());
                            stopSelf();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        sendResult(t.getMessage());
                        t.printStackTrace();
                        stopSelf();
                    }
                });
            } else {
                //InsertUserLoginDetails();
                InsertImages();
            }

        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void InsertImages(){
        try {
            String path = Environment.getExternalStorageDirectory().toString()+"/ProjectMonitoring";
            Log.d("File_path", "Path: "+path);
            File directory = new File(path);
            File[] files = directory.listFiles();
            if(directory.listFiles()!=null){
                Log.d("Files_length", "Size: "+ files.length);
                if(files.length>0){
                    for (int i = 0; i < files.length; i++)
                    {
                        Log.d("Files_name", "FileName:" + files[i].getName());
                        Log.d("Files_absolutepath", "FileAbsolutePath:" + files[i].getAbsolutePath());
                        new ImageFileUploader().execute(files[i].getAbsolutePath(),files[i].getName());
                    }
                }else{
                    try {
                        AppUtilities.writeDbToSdCard(SyncOutService.this);
                        sendResult(getResources().getString(R.string.push_data1) + " " + getResources().getString(R.string.success1));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                try {
                    AppUtilities.writeDbToSdCard(SyncOutService.this);
                    sendResult(getResources().getString(R.string.push_data1) + " " + getResources().getString(R.string.success1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class ImageFileUploader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String filePath = params[0];
            String imagename=params[1];
            TransferManager transferManager = new TransferManager(new
                    BasicAWSCredentials("AKIA5WXQGMRO46N2JMYD",
                    "vlbKErTsNUypaT7Y3+duAyroXpvNE786Rb6x0Plt"));

            AmazonS3Client mClient = new AmazonS3Client(
                    new BasicAWSCredentials("AKIA5WXQGMRO46N2JMYD",
                    "vlbKErTsNUypaT7Y3+duAyroXpvNE786Rb6x0Plt"));
            List<S3ObjectSummary> summaries = mClient.listObjects("converbiz").getObjectSummaries();
            String[] keys = new String[summaries.size()];
            uploadFile2 = new File(filePath);
            workerimagelength = uploadFile2.length();
            workerimagelength = workerimagelength /1024;
            if (workerimagelength < 500) {
                workerimagecount = 1;
                Upload upload = transferManager.upload("converbiz",imagename , uploadFile2);
                try {
                    // Or you can block and wait for the upload to finish
                    upload.waitForCompletion();
                    if (upload.isDone() == true) {
                        Str_WorkerImageUpload = "Uploaded";
                    } else {
                        Str_WorkerImageUpload = "Not Uploaded";
                    }
                    System.out.println("Upload complete.");
                } catch (AmazonClientException amazonClientException) {
                    System.out.println("Unable to upload file, upload was aborted.");
                    amazonClientException.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(String result) {
            try {
                if (workerimagecount == 1) {
                    //new File(result).delete();
                    Log.e("Imageprocess","Success");
                    uploadFile2.delete();
                    Str_WorkerImageUpload="";
                    workerimagecount=0;
                    InsertImages();
                } else {
                    Log.e("ImageSize","Image Size is more than 500kb");
                    /*Toast toast = Toast
                            .makeText(
                                    SyncOutService.this,
                                    "Image Size is more than 500kb change your settings",
                                    Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    View view = toast.getView();
                    view.setBackgroundResource(R.color.colorPrimary);
                    toast.show();*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*private void InsertUserLoginDetails() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        JsonObject userjson = new JsonObject();
        userjson.addProperty("userid", mSelectedUser.getId());
        userjson.addProperty("platform", "mobile");
        userjson.addProperty("operationflag", "syncout");
        userjson.addProperty("datetime", mStr_SyncOutTime);
        userjson.addProperty("role", mSelectedUser.getRoleId());
//        userjson.addProperty("zone", mSelectedUser.getZoneId());
//        userjson.addProperty("salesarea", mSelectedUser.getSalesAreaId());
//        userjson.addProperty("facility", mSelectedUser.getCoorgId());
//        userjson.addProperty("employeeid", mSelectedUser.getEmployeeId());
        addToFile(userjson.toString(), "Inserting UserLoginDetails");
        Call<String> user_call = service.insertUserLoginDetails(AppPreferences.getUserId(this), userjson);
        user_call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("", "");
                if (response.raw().code() == 200) {
                    //fetchFW_f();
                    try {
                        try {
                            RuntimeExceptionDao<User, Integer> userupdateDao = mDbHelper.getUserRuntimeDao();
                            UpdateBuilder<User, Integer> updateBuilder = userupdateDao.updateBuilder();
                            updateBuilder.where().eq("id", mSelectedUser.getId());
                            //updateBuilder.updateColumnValue("sync_in_time", null);
                            updateBuilder.updateColumnValue("sync_out_time", mStr_SyncOutTime);
                            updateBuilder.updateColumnValue("update_flag", true);
                            updateBuilder.update();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        AppUtilities.writeDbToSdCard(SyncOutService.this);
                        sendResult(getResources().getString(R.string.push_data1) + " " + getResources().getString(R.string.success1));
                    } catch (IOException e) {

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

    private void updateProjectStatusesId(long oldId, int newid) {
        try {
            RuntimeExceptionDao<RouteAssignments, Integer> routeAssignmentsDao = mDbHelper.getRouteAssignmentsRuntimeDao();
            UpdateBuilder<RouteAssignments, Integer> updaterouteassignmentBuilder = routeAssignmentsDao.updateBuilder();
            updaterouteassignmentBuilder.where().eq("projectstatusid", oldId);
            updaterouteassignmentBuilder.updateColumnValue("projectstatusid", newid);
            updaterouteassignmentBuilder.update();
        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void updateRouteAssignmentSurveyId(long oldId, int newid) {
        try {
            RuntimeExceptionDao<RouteAssignments, Integer> routeAssignmentsDao = mDbHelper.getRouteAssignmentsRuntimeDao();
            UpdateBuilder<RouteAssignments, Integer> routeAssignmentsIntegerUpdate = routeAssignmentsDao.updateBuilder();
            routeAssignmentsIntegerUpdate.where().eq("id", oldId);
            routeAssignmentsIntegerUpdate.updateColumnValue("id", newid);
            routeAssignmentsIntegerUpdate.update();

            RuntimeExceptionDao<Surveys, Integer> surveys = mDbHelper.getSurveysRuntimeDao();
            UpdateBuilder<Surveys, Integer> surveysIntegerUpdateBuilder = surveys.updateBuilder();
            surveysIntegerUpdateBuilder.where().eq("routeassignmentid", oldId);
            surveysIntegerUpdateBuilder.updateColumnValue("routeassignmentid", newid);
            surveysIntegerUpdateBuilder.update();

            RuntimeExceptionDao<SurveyPromotions, Integer> surveyPromotions = mDbHelper.getSurveyPromotionsRuntimeDao();
            UpdateBuilder<SurveyPromotions, Integer> surveyPromotionsIntegerUpdateBuilder = surveyPromotions.updateBuilder();
            surveyPromotionsIntegerUpdateBuilder.where().eq("routeassignmentid", oldId);
            surveyPromotionsIntegerUpdateBuilder.updateColumnValue("routeassignmentid", newid);
            surveyPromotionsIntegerUpdateBuilder.update();

            RuntimeExceptionDao<ProjectStatuses, Integer> projectStatusesDao = mDbHelper.getProjectStatusesRuntimeDao();
            UpdateBuilder<ProjectStatuses, Integer> projectStatusesUpdate = projectStatusesDao.updateBuilder();
            projectStatusesUpdate.where().eq("routeassignid", oldId);
            projectStatusesUpdate.updateColumnValue("routeassignid", newid);
            projectStatusesUpdate.update();

            RuntimeExceptionDao<ChecklistAnswers, Integer> checklistAnswersDao = mDbHelper.getChecklistAnswersRuntimeDao();
            UpdateBuilder<ChecklistAnswers, Integer> checklistAnswersUpdate = checklistAnswersDao.updateBuilder();
            checklistAnswersUpdate.where().eq("routeassignmentid", oldId);
            checklistAnswersUpdate.updateColumnValue("routeassignmentid", newid);
            checklistAnswersUpdate.update();
        } catch (SQLException e) {
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void updateRouteAssignmentFWId(long oldId, int newid) {
        Log.d("updated_oldid",""+oldId);
        Log.d("updated_newid",""+newid);
        try {
            Log.d("updated_data","routeassignments");
            RuntimeExceptionDao<RouteAssignments, Integer> routeAssignmentsDao = mDbHelper.getRouteAssignmentsRuntimeDao();
            UpdateBuilder<RouteAssignments, Integer> routeAssignmentsIntegerUpdate = routeAssignmentsDao.updateBuilder();
            routeAssignmentsIntegerUpdate.where().eq("id", oldId);
            routeAssignmentsIntegerUpdate.updateColumnValue("id", newid);
            routeAssignmentsIntegerUpdate.update();

            Log.d("updated_data","assign");
            RuntimeExceptionDao<AssignedItems, Integer> assignedItemsDao = mDbHelper.getAssignedItemsRuntimeDao();
            UpdateBuilder<AssignedItems, Integer> assignedItemsBuilder = assignedItemsDao.updateBuilder();
            assignedItemsBuilder.where().eq("routeassignmentid", oldId);
            assignedItemsBuilder.updateColumnValue("routeassignmentid", newid);
            assignedItemsBuilder.update();

            Log.d("updated_data","projectresources");
            RuntimeExceptionDao<ProjectResources, Integer> projectResourcesDao = mDbHelper.getProjectResourcesRuntimeDao();
            UpdateBuilder<ProjectResources, Integer> projectResourcesIntegerUpdateBuilder = projectResourcesDao.updateBuilder();
            projectResourcesIntegerUpdateBuilder.where().eq("routeassignid", oldId).and().eq("routeassignmentid", oldId);
            projectResourcesIntegerUpdateBuilder.updateColumnValue("routeassignid", newid);
            projectResourcesIntegerUpdateBuilder.updateColumnValue("routeassignmentid", newid);
            projectResourcesIntegerUpdateBuilder.update();

            Log.d("updated_data","surveys");
            RuntimeExceptionDao<Surveys, Integer> surveysRuntimeDao = mDbHelper.getSurveysRuntimeDao();
            UpdateBuilder<Surveys, Integer> surveysIntegerUpdateBuilder = surveysRuntimeDao.updateBuilder();
            surveysIntegerUpdateBuilder.where().eq("routeassignmentid", oldId);
            surveysIntegerUpdateBuilder.updateColumnValue("routeassignmentid", newid);
            surveysIntegerUpdateBuilder.update();

            Log.d("updated_data","promotions");
            RuntimeExceptionDao<Promotions, Integer> promotionsDao = mDbHelper.getPromotionsRuntimeDao();
            UpdateBuilder<Promotions, Integer> promotionsUpdate = promotionsDao.updateBuilder();
            promotionsUpdate.where().eq("routeassignmentid", oldId);
            promotionsUpdate.updateColumnValue("routeassignmentid", newid);
            promotionsUpdate.update();

            Log.d("updated_data","issuelist");
            RuntimeExceptionDao<IssueList, Integer> issueListsDao = mDbHelper.getIssueListRuntimeDao();
            UpdateBuilder<IssueList, Integer> issueListIntegerUpdate = issueListsDao.updateBuilder();
            issueListIntegerUpdate.where().eq("routeassignid", oldId);
            issueListIntegerUpdate.updateColumnValue("routeassignid", newid);
            issueListIntegerUpdate.update();

            Log.d("updated_data","projectstatuses");
            RuntimeExceptionDao<ProjectStatuses, Integer> projectStatusesDao = mDbHelper.getProjectStatusesRuntimeDao();
            UpdateBuilder<ProjectStatuses, Integer> projectStatusesUpdate = projectStatusesDao.updateBuilder();
            projectStatusesUpdate.where().eq("routeassignid", oldId);
            projectStatusesUpdate.updateColumnValue("routeassignid", newid);
            projectStatusesUpdate.update();

            RuntimeExceptionDao<ChecklistAnswers, Integer> checklistAnswersDao = mDbHelper.getChecklistAnswersRuntimeDao();
            UpdateBuilder<ChecklistAnswers, Integer> checklistAnswersUpdate = checklistAnswersDao.updateBuilder();
            checklistAnswersUpdate.where().eq("routeassignmentid", oldId);
            checklistAnswersUpdate.updateColumnValue("routeassignmentid", newid);
            checklistAnswersUpdate.update();

        } catch (SQLException e) {
            sendResult(e.getMessage());
            Log.e("error",""+e);
            e.printStackTrace();
            stopSelf();
        }
    }

    private void UpdateSurveysId(long id, int serverId) {
        try {
            RuntimeExceptionDao<ParamDetails,Integer> paramDetailsDao = mDbHelper.getParamDetailsRuntimeDao();
            UpdateBuilder<ParamDetails,Integer> paramDetailsIntegerUpdate=paramDetailsDao.updateBuilder();
            paramDetailsIntegerUpdate.where().eq("surveyid",id);
            paramDetailsIntegerUpdate.updateColumnValue("surveyid",serverId);
            paramDetailsIntegerUpdate.update();

            RuntimeExceptionDao<BOQHeaders,Integer> boqHeadersDao=mDbHelper.getBoqHeadersRuntimeDao();
            UpdateBuilder<BOQHeaders,Integer> boqHeadersIntegerUpdate = boqHeadersDao.updateBuilder();
            boqHeadersIntegerUpdate.where().eq("surveyid",id);
            boqHeadersIntegerUpdate.updateColumnValue("surveyid",serverId);
            boqHeadersIntegerUpdate.update();

            RuntimeExceptionDao<BOQTrailers,Integer> boqTrailersDao =mDbHelper.getBoqTrailersRuntimeDao();
            UpdateBuilder<BOQTrailers,Integer> boqTrailersIntegerUpdate = boqTrailersDao.updateBuilder();
            boqTrailersIntegerUpdate.where().eq("surveyid",id);
            boqTrailersIntegerUpdate.updateColumnValue("surveyid",serverId);
            boqTrailersIntegerUpdate.update();
            Log.d("boq_trailer","calling");
        }catch (Exception e){
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }

    private void updateBOQTrailers(long id, int recordId) {
        try {
            RuntimeExceptionDao<BOQTrailers,Integer> boqTrailersDao =mDbHelper.getBoqTrailersRuntimeDao();
            UpdateBuilder<BOQTrailers,Integer> boqTrailersIntegerUpdate = boqTrailersDao.updateBuilder();
            boqTrailersIntegerUpdate.where().eq("boqheaderId",id);
            boqTrailersIntegerUpdate.updateColumnValue("boqheaderId",recordId);
            boqTrailersIntegerUpdate.update();
        }catch (Exception e){
            sendResult(e.getMessage());
            e.printStackTrace();
            stopSelf();
        }
    }


    private void sendResult(String message) {
        Log.e("pushdata",message);
        Bundle bundle = new Bundle();
        bundle.putString("result", message);
        mReceiver.send(2, bundle);
        OpenHelperManager.releaseHelper();
        mDbHelper = null;
    }



    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getDateTime2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onDestroy() {
        /*OpenHelperManager.releaseHelper();
        mDbHelper = null;*/
        super.onDestroy();
    }

    private void addToFile(String JsonData, String Message) {
        if (TempFile.exists()) {
            try {
                long fileLength = TempFile.length();
                RandomAccessFile raf = new RandomAccessFile(
                        TempFile, "rw");
                raf.seek(fileLength);
                raf.writeBytes("\r\n"
                        + Message + ":"
                        + JsonData);
                raf.close();
            } catch (Exception e) {

            }
        } else {
            try {
                TempFile.createNewFile();
                long fileLength = TempFile.length();
                RandomAccessFile raf = new RandomAccessFile(
                        TempFile, "rw");
                raf.seek(fileLength);
                raf.writeBytes("\r\n"
                        + Message + ":"
                        + JsonData);
                raf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

