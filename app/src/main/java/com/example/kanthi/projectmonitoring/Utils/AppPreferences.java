package com.example.kanthi.projectmonitoring.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kanthi.projectmonitoring.R;

/***
 * Created by Kanthi on 21/2/18.
 */
public class AppPreferences {

    public static final String PREF_USER_SESSION = "USER_SESSION";
    public static final String PREF_IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String PREF_USER_NAME = "USER_NAME";
    public static final String PREF_LOGGED_USER_NAME = "LOGGED_USER_NAME";
    public static final String PREF_USER_ID = "USER_ID";
    public static final String PREF_ROUTE_ID = "ROUTE_ID";
    public static final String PREF_ROUTE_SUMMARY_ID = "PREF_ROUTE_SUMMARY_ID";
    public static final String PREF_CAPTURE_IMAGE = "CAPTURE_IMAGE";
    public static final String PREF_QA_CAPTURE_IMAGE = "QA_CAPTURE_IMAGE";
    public static final String PREF_SALES_AREA_ID = "SALES_AREA_ID";
    public static final String PREF_DISTRIBUTION_AREA_ID = "DISTRIBUTION_AREA_ID";
    public static final String PREF_DISTRIBUTION_SUB_AREA_ID = "PREF_DISTRIBUTION_SUB_AREA_ID";
    public static final String PREF_ASSIGN_ROUTE_DATE = "ASSIGN_ROUTE_DATE";
    public static final String PREF_REMARK_ROUTE_ID = "REMARK_ROUTE_ID";
    public static final String PREF_STRESS_DATA = "STRESS_DATA";
    public static final String PREF_RESOURCE_FLAG = "RESOURCE_FLAG";
    public static final String PREF_PREVOUS_FLAG = "PREVOUS_FLAG";
    public static final String PREF_BOQ_FLAG = "BOQ_FLAG";
    public static final String PREF_BOQ_QA_FLAG = "BOQ_QA_FLAG";
    public static final String PREF_USER_GROUP_ID = "USER_GROUP_ID";
    public static final String PREF_FIELD_ENGINEER = "FIELD_ENGINEER";
    public static final String PREF_FIELD_NUMBER = "FIELD_NUMBER";
    public static final String PREF_FIELD_LOCATION = "FIELD_LOCATION";
    public static final String PREF_FIELD_DESIGINITION = "FIELD_DESIGINITION";
    public static final String PREF_USER_AREA_ID = "AREA_ID";
    public static final String PREF_TASK_NAME = "TASK_NAME";
    public static final String PREF_TASK_TYPE = "TASK_TYPE";
    public static final String PREF_TARGET = "TARGET";
    public static final String PREF_USER_ROUTE_ID = "ROUTE_ID";
    public static final String PREF_USER_R_ID = "R_ID";
    public static final String PREF_TOURTYPE_ID = "TOURTYPE_ID";
    public static final String PREF_TOTAL_ACTUAL= "TOTAL_ACTUAL";
    public static final String RETAILER_USER_ID = "RETAILER_USER_ID";
    public static final String ZONE_ID = "ZONE_ID";
    public static final String DIST_AREA_ID = "DIST_AREA_ID";
    public static final String SALES_MNGR_ID = "SALES_MNGR_ID";
    public static final String USER_LATITUDE = "USER_LATITUDE";
    public static final String USER_LONGITUDE = "USER_LONGITUDE";
    public static final String IMAGE_LATITUDE = "IMAGE_LATITUDE";
    public static final String IMAGE_LONGITUDE = "IMAGE_LONGITUDE";
    public static final String USER_ZONE_ID = "ZONE_ID";
    public static final String WORKING_DAYS = "WORKING_DAYS";
    public static final String EMPLOYEE_ID = "EMPLOYEE_ID";
    public static final String ROUTE_ASSIGNMENT_ID = "ROUTE_ASSIGNMENT_ID";
    public static final String PREF_START_FLAG = "PREF_START_FLAG";
    public static final String PREF_COMPLETED_FLAG = "PREF_COMPLETED_FLAG";
    public static final String GROUP_ID = "GROUP_ID";
    public static final String ROUTE_ASSIGNMENT_SUMMARY_ID = "ROUTE_ASSIGNMENT_SUMMARY_ID";
    public static final String DEPENDENT_ROUTE_ASSIGNMENT_SUMMARY_ID = "DEPENDENT_ROUTE_ASSIGNMENT_SUMMARY_ID";
    public static final String PROJECT_STATUS_ID = "PROJECT_STATUS_ID";
    public static final String ROUTE_SALES_VIEWS_ID = "ROUTE_SALES_VIEWS_ID";
    public static final String START_DATE = "START_DATE";
    public static final String END_DATE = "END_DATE";
    public static final String FROM_DATE = "FROM_DATE";
    public static final String TO_DATE = "TO_DATE";
    public static final String TOTAL_TARGET = "TOTAL_TARGET";
    public static final String CURRENT_TARGET = "CURRENT_TARGET";
    public static final String ACTUAL_TARGET = "ACTUAL_TARGET";
    public static final String DAYS_LEFT = "DAYS_LEFT";
    public static final String TASK_TYPE_ID = "TASK_TYPE_ID";
    public static final String UNIT_MEASUREMENT_ID = "UNIT_MEASUREMENT_ID";
    public static final String ACTUAL_START_DATE = "ACTUAL_START_DATE";
    public static final String ACTUAL_END_DATE = "ACTUAL_END_DATE";
    public static final String CHART_PERCENTAGE = "CHART_PERCENTAGE";
    public static final String SUBTASK_TYPE = "SUBTASK_TYPE";
    public static final String SUBTASK_ID = "SUBTASK_ID";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String PROJECT_TYPE = "PROJECT_TYPE";
    public static final String UNIT_MEASUREMENT_NAME = "MEASUREMENT_NAME";
    public static final String SUBTYPE_ID = "SUBTYPE_ID";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String IMAGE_APPROVED_FLAG = "IMAGE_APPROVED_FLAG";
    public static final String IMAGE_DISTANCE = "IMAGE_DISTANCE";
    public static final String IMAGE_HEIGHT = "IMAGE_HEIGHT";
    public static final String INPUT_UNIT="input_unit";
    public static final String USER_SALES_AREA_ID = "USER_SALES_AREA_ID";
    public static final String TIME_INTERVAL = "TIME_INTERVAL";
    public static final String PREF_START_DATE_FLAG = "PREF_START_DATE_FLAG";
    public static final String PREF_START_DATE_FLAG_ID= "PREF_START_DATE_FLAG_ID";
    public static final String TASK_NOT_FINISHED_DATE = "TASK_NOT_FINISHED_DATE";
    private static final String PUSHDATA_SUCCESS = "PUSHDATA_SUCCESS";
    private static final String OFFLINE_SUCCESS = "OFFLINE_SUCCESS";
    private static final String PREVIOUS_LOCATION = "PREVIOUS_LOCATION";

    public static void setPrefCaptureImage(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_CAPTURE_IMAGE, vulnerableChart);
        editor.apply();
    }

    public static String getPrefCaptureImage(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_CAPTURE_IMAGE,null);
    }

    public static void setQACaptureImage(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_QA_CAPTURE_IMAGE, vulnerableChart);
        editor.apply();
    }

    public static String getQACaptureImage(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_QA_CAPTURE_IMAGE,null);
    }

    public static void setResourceFlag(Context context, Boolean resource) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_RESOURCE_FLAG, resource);
        editor.apply();
    }

    public static Boolean getResourceFlag(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getBoolean(PREF_RESOURCE_FLAG,false);
    }

    public static void setPreviousLocationFlag(Context context, Boolean resource) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_PREVOUS_FLAG, resource);
        editor.apply();
    }

    public static Boolean getPreviousLocationFlag(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getBoolean(PREF_PREVOUS_FLAG,false);
    }

    public static void setBoqFlag(Context context, Boolean boq) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_BOQ_FLAG, boq);
        editor.apply();
    }

    public static Boolean getBoqFlag(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getBoolean(PREF_BOQ_FLAG,false);
    }

    public static void setBoqQAFlag(Context context, Boolean boq) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_BOQ_QA_FLAG, boq);
        editor.apply();
    }

    public static Boolean getBoqQAFlag(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getBoolean(PREF_BOQ_QA_FLAG,false);
    }
    /*public static void setPrefUserGroupId(Context context, Integer userId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_USER_GROUP_ID, userId);
        editor.apply();
    }

    public static Integer getPrefUserGroupId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(PREF_USER_GROUP_ID,0);
    }*/
    public static void setStressedData(Context context, Boolean userId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_STRESS_DATA, userId);
        editor.apply();
    }

    public static Boolean getStressedData(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getBoolean(PREF_STRESS_DATA,false);
    }

    public static void setLoggedIn(Context context, Boolean loggedIn) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_IS_LOGGED_IN, loggedIn);
        editor.apply();
    }

    public static Boolean isLoggedIn(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getBoolean(
                PREF_IS_LOGGED_IN, false);
    }

    public static void setUserId(Context context, String userId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USER_ID, userId);
        editor.apply();
    }

    public static String getUserId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_USER_ID,
                null);
    }

    public static void setRouteId(Context context, String routeId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_ROUTE_ID, routeId);
        editor.apply();
    }

    public static String getRouteId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_ROUTE_ID,
                null);
    }

    public static void setUserName(Context context, String userId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USER_NAME, userId);
        editor.apply();
    }

    public static String getUserName(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_USER_NAME,
                null);
    }

    public static void setLoggedUserName(Context context, String userId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_LOGGED_USER_NAME, userId);
        editor.apply();
    }

    public static String getLoggedUserName(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_LOGGED_USER_NAME,
                null);
    }

    public static void setZoneId(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ZONE_ID, vulnerableChart);
        editor.apply();
    }

    public static String getZoneid(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(USER_ZONE_ID,null);
    }

    public static void setAreaid(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_SALES_AREA_ID, vulnerableChart);
        editor.apply();
    }

    public static String getAreaid(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_SALES_AREA_ID,null);
    }

    public static void setDistributionAreaId(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_DISTRIBUTION_AREA_ID, vulnerableChart);
        editor.apply();
    }

    public static String getDistributionAreaId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_DISTRIBUTION_AREA_ID,null);
    }

    public static void setDistributionSubAreaId(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_DISTRIBUTION_SUB_AREA_ID, vulnerableChart);
        editor.apply();
    }

    public static String getDistributionSubAreaId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_DISTRIBUTION_SUB_AREA_ID,null);
    }

    public static void setAssigndateid(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_ASSIGN_ROUTE_DATE, vulnerableChart);
        editor.apply();
    }

    public static String getAssigndateid(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_ASSIGN_ROUTE_DATE,null);
    }

    public static void setRouteRemarkId(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_REMARK_ROUTE_ID, vulnerableChart);
        editor.apply();
    }

    public static String getRouteRemarkId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_REMARK_ROUTE_ID,null);
    }

    public static void setFieldEngineer(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_FIELD_ENGINEER, vulnerableChart);
        editor.apply();
    }

    public static String getFieldEngineer(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_FIELD_ENGINEER,null);
    }

    public static void setFieldNumber(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_FIELD_NUMBER, vulnerableChart);
        editor.apply();
    }

    public static String getFieldNumber(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_FIELD_NUMBER,null);
    }

    public static void setFieldLocation(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_FIELD_LOCATION, vulnerableChart);
        editor.apply();
    }

    public static String getFieldLocation(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_FIELD_LOCATION,null);
    }

    public static void setFieldDesignition(Context context, String vulnerableChart) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_FIELD_DESIGINITION, vulnerableChart);
        editor.apply();
    }

    public static String getFieldDesignition(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_FIELD_DESIGINITION,null);
    }

    public static void setPrefAreaId(Context context, Integer Att_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_USER_AREA_ID, Att_Id);
        editor.apply();
    }

    public static Integer getPrefAreaId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(PREF_USER_AREA_ID,0);
    }

    public static void setTaskName(Context context, String taskName) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_TASK_NAME, taskName);
        editor.apply();
    }

    public static String getTaskName(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_TASK_NAME,null);
    }

    public static void setTaskType(Context context, String taskType) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_TASK_TYPE, taskType);
        editor.apply();
    }

    public static String getTaskType(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_TASK_TYPE,null);
    }

    public static void setTarget(Context context, float target) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(PREF_TARGET, target);
        editor.apply();
    }

    public static Float getTarget(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getFloat(PREF_TARGET,0);
    }

    public static void setPrefRouteId(Context context, Integer route_id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_USER_ROUTE_ID, route_id);
        editor.apply();
    }

    public static Integer getPrefRouteId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(PREF_USER_ROUTE_ID,0);
    }

    public static void setPrefR_Id(Context context, Integer r_id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_USER_R_ID, r_id);
        editor.apply();
    }

    public static Integer getPrefR_Id(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(PREF_USER_R_ID,0);
    }

    public static void setTourTypeId(Context context, Integer tourtypeid) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_TOURTYPE_ID, tourtypeid);
        editor.apply();
    }

    public static Integer getTourTypeId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(PREF_TOURTYPE_ID,0);
    }

    public static void setRet_user_Id(Context context, Integer Ret_user_id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(RETAILER_USER_ID, Ret_user_id);
        editor.apply();
    }

    public static Integer getRet_user_Id(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(RETAILER_USER_ID,0);
    }

    public static void setZoneId(Context context, Integer zoneid) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ZONE_ID, zoneid);
        editor.apply();
    }

    public static Integer getZoneId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(ZONE_ID,0);
    }

    public static void setSaleMngrIdId(Context context, Integer sa_mn_id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SALES_MNGR_ID, sa_mn_id);
        editor.apply();
    }

    public static Integer getSaleMngrIdId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(SALES_MNGR_ID,0);
    }

    public static void setDist_Area_Id(Context context, Integer distid) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(DIST_AREA_ID, distid);
        editor.apply();
    }

    public static Integer getDist_Area_Id(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(DIST_AREA_ID,0);
    }

    public static void setUserLatitude(Context context, float lati) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(USER_LATITUDE, lati);
        editor.apply();
    }

    public static Float getUserLatitude(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getFloat(USER_LATITUDE,0);
    }

    public static void setUserLongitude(Context context, float lati) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(USER_LONGITUDE, lati);
        editor.apply();
    }

    public static Float getUserLongitude(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getFloat(USER_LONGITUDE,0);
    }

    public static void setWorkingdays(Context context, Integer workingdays) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(WORKING_DAYS, workingdays);
        editor.apply();
    }

    public static Integer getWorkingdays(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(WORKING_DAYS,0);
    }

    public static void setPartnerid(Context context, Integer emp_id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(WORKING_DAYS, emp_id);
        editor.apply();
    }

    public static Integer getPartnerid(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(WORKING_DAYS,0);
    }

    public static void setRouteAssignmentId(Context context, long ass_id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(ROUTE_ASSIGNMENT_ID, ass_id);
        editor.apply();
    }

    public static long getRouteAssignmentId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getLong(ROUTE_ASSIGNMENT_ID,0);
    }

    public static void setTotalActual(Context context, Float tourtypeid) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(PREF_TOTAL_ACTUAL, tourtypeid);
        editor.apply();
    }

    public static Float getTotalActual(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getFloat(PREF_TOTAL_ACTUAL,0);
    }

    public static void setStartFlag(Context context, Boolean startflag) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_START_FLAG, startflag);
        editor.apply();
    }

    public static Boolean getStartFlag(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getBoolean(PREF_START_FLAG,false);
    }

    public static void setPrefCompletedFlag(Context context, Boolean startflag) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_COMPLETED_FLAG, startflag);
        editor.apply();
    }

    public static Boolean getPrefCompletedFlag(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getBoolean(PREF_COMPLETED_FLAG,false);
    }

    public static void setGroupId(Context context, Integer grp_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(GROUP_ID, grp_Id);
        editor.apply();
    }

    public static Integer getGroupId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(GROUP_ID,0);
    }

    public static void setRouteAssignmentSummaryId(Context context, long assign_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(ROUTE_ASSIGNMENT_SUMMARY_ID, assign_Id);
        editor.apply();
    }

    public static Long getDependentRouteAssignmentSummaryId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getLong(DEPENDENT_ROUTE_ASSIGNMENT_SUMMARY_ID,0);
    }

    public static void setDependentRouteAssignmentSummaryId(Context context, long assign_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(DEPENDENT_ROUTE_ASSIGNMENT_SUMMARY_ID, assign_Id);
        editor.apply();
    }

    public static Long getRouteAssignmentSummaryId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getLong(ROUTE_ASSIGNMENT_SUMMARY_ID,0);
    }

    public static void setProjectStatusid(Context context, Long projectstatus_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(PROJECT_STATUS_ID, projectstatus_Id);
        editor.apply();
    }

    public static Long getProjectStatusid(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getLong(PROJECT_STATUS_ID,0);
    }

    public static void setRouteSalesViewid(Context context, Long projectstatus_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(ROUTE_SALES_VIEWS_ID, projectstatus_Id);
        editor.apply();
    }

    public static Long getRouteSalesViewid(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getLong(ROUTE_SALES_VIEWS_ID,0);
    }
    public static void setStartDate(Context context, String startdate) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(START_DATE, startdate);
        editor.apply();
    }

    public static String getStartDate(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(START_DATE,null);
    }
    public static void setEndDate(Context context, String enddate) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(END_DATE, enddate);
        editor.apply();
    }

    public static String getEndDate(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(END_DATE,null);
    }
    public static void setTotalTarget(Context context, Integer totaltarget) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(TOTAL_TARGET, totaltarget);
        editor.apply();
    }

    public static Integer getTotalTarget(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(TOTAL_TARGET,0);
    }
    public static void setCurrentTarget(Context context, Float currenttarget) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(CURRENT_TARGET, currenttarget);
        editor.apply();
    }

    public static Float getCurrentTarget(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getFloat(CURRENT_TARGET,0);
    }
    public static void setActualTarget(Context context, Float actual_target) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(ACTUAL_TARGET, actual_target);
        editor.apply();
    }

    public static Float getActualTarget(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getFloat(ACTUAL_TARGET,0);
    }
    public static void setDaysLeft(Context context, Integer daysleft) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(DAYS_LEFT, daysleft);
        editor.apply();
    }

    public static Integer getDaysLeft(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(DAYS_LEFT,0);
    }
    public static void setFromDate(Context context, String startdate) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FROM_DATE, startdate);
        editor.apply();
    }

    public static String getFromDate(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(FROM_DATE,null);
    }
    public static void setToDate(Context context, String enddate) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TO_DATE, enddate);
        editor.apply();
    }

    public static String getToDate(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(TO_DATE,null);
    }

    public static void setEmployeeId(Context context, Integer Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(EMPLOYEE_ID, Id);
        editor.apply();
    }

    public static Integer getEmployeeId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(EMPLOYEE_ID,0);
    }

    public static void setTaskTypeId(Context context, Integer task_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(TASK_TYPE_ID, task_Id);
        editor.apply();
    }

    public static Integer getTaskTypeId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(TASK_TYPE_ID,0);
    }

    public static void setUnitMeasurementId(Context context, Integer unit_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(UNIT_MEASUREMENT_ID, unit_Id);
        editor.apply();
    }

    public static Integer getUnitMeasurementId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(UNIT_MEASUREMENT_ID,0);
    }

    public static void setActualStartDate(Context context, String startdate) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ACTUAL_START_DATE, startdate);
        editor.apply();
    }

    public static String getActualStartDate(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(ACTUAL_START_DATE,null);
    }
    public static void setActualEndDate(Context context, String enddate) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ACTUAL_END_DATE, enddate);
        editor.apply();
    }

    public static String getActualEndDate(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(ACTUAL_END_DATE,null);
    }

    public static void setChartPercentage(Context context, Float currenttarget) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(CHART_PERCENTAGE, currenttarget);
        editor.apply();
    }

    public static Float getChartPercentage(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getFloat(CHART_PERCENTAGE,0);
    }

    public static void setSubTaskType(Context context, String subtype) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SUBTASK_TYPE, subtype);
        editor.apply();
    }

    public static String getSubTaskType(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(SUBTASK_TYPE,null);
    }

    public static void setSubTaskId(Context context, long subtask_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(SUBTASK_ID, subtask_Id);
        editor.apply();
    }

    public static long getSubTaskId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getLong(SUBTASK_ID,0);
    }

    public static void setUserType(Context context, String usertype) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_TYPE, usertype);
        editor.apply();
    }

    public static String getUserType(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(USER_TYPE,null);
    }

    public static void setProjectType(Context context, String usertype) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PROJECT_TYPE, usertype);
        editor.apply();
    }

    public static String getProjectType(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PROJECT_TYPE,null);
    }

    public static void setUnitMeasurementName(Context context, String um_name) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(UNIT_MEASUREMENT_NAME, um_name);
        editor.apply();
    }

    public static String getUnitMeasurementName(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(UNIT_MEASUREMENT_NAME,null);
    }

    public static void setSubTypeId(Context context, Integer subtask_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SUBTYPE_ID, subtask_Id);
        editor.apply();
    }

    public static Integer getSubTypeId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(SUBTYPE_ID,0);
    }

    public static void setUserEmail(Context context, String useremail) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_EMAIL, useremail);
        editor.apply();
    }

    public static String getUserEmail(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(USER_EMAIL,null);
    }

    public static void setImageApprovedFlag(Context context, String approvedflag) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(IMAGE_APPROVED_FLAG, approvedflag);
        editor.apply();
    }

    public static String getImageApprovedFlag(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(IMAGE_APPROVED_FLAG,null);
    }

    public static void setInputUnit(Context context, String input) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(INPUT_UNIT, input);
        editor.apply();
    }

    public static String getInputUnit(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(INPUT_UNIT,context.getString(R.string.metre));
    }

    public static void setImageDistance(Context context, String distance) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(IMAGE_DISTANCE, distance);
        editor.apply();
    }

    public static String getImageDistance(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(IMAGE_DISTANCE,null);
    }

    public static void setImageHeight(Context context, String distance) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(IMAGE_HEIGHT, distance);
        editor.apply();
    }

    public static String getImageHeight(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(IMAGE_HEIGHT,null);
    }

    public static void setUser_SalesAreaId(Context context, Integer area_Id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_SALES_AREA_ID, area_Id);
        editor.apply();
    }

    public static Integer getUser_SalesAreaId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(USER_SALES_AREA_ID,0);
    }

    public static void setTimeInterval(Context context, Integer interval) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(TIME_INTERVAL, interval);
        editor.apply();
    }

    public static Integer getTimeInterval(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getInt(TIME_INTERVAL,0);
    }

    public static void setFromDateValue(Context context, String startflag) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_START_DATE_FLAG, startflag);
        editor.apply();
    }

    public static String getFromDateValue(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(PREF_START_DATE_FLAG,null);
    }


    public static void setTaskExtendDate(Context context, String ext_date) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TASK_NOT_FINISHED_DATE, ext_date);
        editor.apply();
    }

    public static String getTaskExtendDate(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(TASK_NOT_FINISHED_DATE,null);
    }

    public static void setFromDateValueID(Context context, long routesaleviewsid) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(PREF_START_DATE_FLAG_ID, routesaleviewsid);
        editor.apply();
    }

    public static Long getFromDateValueID(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getLong(PREF_START_DATE_FLAG_ID,0);
    }

    public static void setPushDataSuccess(Context context, Boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PUSHDATA_SUCCESS, value);
        editor.apply();
    }

    public static Boolean getPushDataSuccess(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getBoolean(PUSHDATA_SUCCESS,
                false);
    }

    public static void setofflineSuccess(Context context, Boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(OFFLINE_SUCCESS, value);
        editor.apply();
    }

    public static Boolean getofflineSuccess(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getBoolean(OFFLINE_SUCCESS,
                false);
    }

    public static void setPreviousLocationId(Context context, long ass_id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(PREVIOUS_LOCATION, ass_id);
        editor.apply();
    }

    public static long getPreviousLocationId(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getLong(PREVIOUS_LOCATION,0);
    }

    public static void setImageLatitude(Context context, String imageLatitude) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(IMAGE_LATITUDE, imageLatitude);
        editor.apply();
    }

    public static String getImageLatitude(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(IMAGE_LATITUDE,"");
    }

    public static void setImageLongitude(Context context, String imageLongitude) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_USER_SESSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(IMAGE_LONGITUDE, imageLongitude);
        editor.apply();
    }

    public static String getImageLongitude(Context context) {
        return context.getSharedPreferences(PREF_USER_SESSION, Context.MODE_PRIVATE).getString(IMAGE_LONGITUDE,"");
    }
}


