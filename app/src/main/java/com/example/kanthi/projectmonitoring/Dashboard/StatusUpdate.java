package com.example.kanthi.projectmonitoring.Dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.Checklist_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Details_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Details_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Importance_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.IssueTypes_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.QAAssignedItem_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Remarks_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Review_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Status_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.AssignedItems;
import com.example.kanthi.projectmonitoring.PoJo.Checklist;
import com.example.kanthi.projectmonitoring.PoJo.ChecklistAnswers;
import com.example.kanthi.projectmonitoring.PoJo.Inventories;
import com.example.kanthi.projectmonitoring.PoJo.IssueList;
import com.example.kanthi.projectmonitoring.PoJo.IssueTypes;
import com.example.kanthi.projectmonitoring.PoJo.ItemDefinition;
import com.example.kanthi.projectmonitoring.PoJo.ParamCategories;
import com.example.kanthi.projectmonitoring.PoJo.ParamDetails;
import com.example.kanthi.projectmonitoring.PoJo.Priority;
import com.example.kanthi.projectmonitoring.PoJo.ProjectResources;
import com.example.kanthi.projectmonitoring.PoJo.ProjectStatuses;
import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummaries;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignments;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.Status;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.PoJo.WareHouses;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusUpdate extends AppCompatActivity {
    private EditText task_name,task_type,actual,per_complete;
    private Spinner sp_status,sp_remark,sp_review;
    private TextView target,unitmeasurementname1,unitmeasurementname2;
    private Button status_up_submit;
    private CardView bt_nav;
    private Button survey_map,project_mtr,stats_update;
    private TextView tv_statusupdate,tv_projectMonitor;
    private TextView field_mgr,field_code,field_location,field_desig;
    private TextView startdate,enddate,totaltarget,currenttarget,actualtarget,daysleft,act_stdate,act_enddate;
    List<Status> mstatuses;
    List<Remarks> mremarks;
    List<Priority> mpriorities;
    List<IssueTypes> missuetypes;
    List<ProjectStatuses> mprojectStatus;
    List<RouteAssignments> mrouteAssignments;
    private Status_Sp_Adapter status_sp_adapter;
    private Remarks_Sp_Adapter remarks_sp_adapter;
    private Review_Sp_Adapter review_sp_adapter;
    private int status_id,remark_id,review_id;
    double latitude,longitude;
    private LinearLayout per_layout,sp_layout,layout;
    ProjectStatuses projectStatuses;
    String status_name,remark_name,review_name;
    float act_value,final_value;
    IssueList issueList;
    int partnerid,imp_id,issue_id;
    String projectstatusid,imp_name,issue_name;
    ProgressDialog progressDialog;
    EditText et_duedate;
    String day,date,finaldate;
    AlertDialog dialog;
    private CoordinatorLayout coordinatorLayout;
    ProgressDialog progress1;
    ProgressDialog progress;
    float qa_actual= 0;
    public static final int REQUEST_IMAGE_CAPTURE=1;
    private Uri picUri;
    private File picFile;
    public String imagePath;
    private List<Checklist> mparamCategories;
    private int paramid;
    private String param_name,param_type,dropdown_value,rb_values;
    private ArrayList<ParamDetails> mdetails;
    private ParamDetails details;
    private double lati=0.0;
    private double longi=0.0;
    private LocationManager manager;
    private LocationListener listener;

    AvahanSqliteDbHelper mDbHelper;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_update);

        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(StatusUpdate.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        mdetails = new ArrayList<ParamDetails>();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        latitude=bundle.getDouble("Latitude");
        longitude=bundle.getDouble("Longitude");
        partnerid=bundle.getInt("partnerid");
        day=bundle.getString("date");
        final Calendar calendar1 = Calendar.getInstance();
        int yy = calendar1.get(Calendar.YEAR);
        final int mm = calendar1.get(Calendar.MONTH);
        final int m = mm;
        final int dd = calendar1.get(Calendar.DAY_OF_MONTH);
        String st_date=AppUtilities.getDateString(yy, m, dd);
        String d=st_date.substring(0,2);
        String mo=st_date.substring(3,6);
        finaldate=mo+" "+d;
        getSupportActionBar().setTitle("Status Update"+" ( "+day+" )");
        task_name= (EditText) findViewById(R.id.task_name);
        task_type= (EditText) findViewById(R.id.task_type);
        sp_status= (Spinner) findViewById(R.id.sp_status);
        target= (TextView) findViewById(R.id.target);
        actual= (EditText) findViewById(R.id.actual);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        unitmeasurementname1= (TextView) findViewById(R.id.unitmeasurementname);
        unitmeasurementname2= (TextView) findViewById(R.id.unitmeasurementname1);
        per_complete= (EditText) findViewById(R.id.percentage_complete);
        sp_remark= (Spinner) findViewById(R.id.sp_remark);
        sp_review= (Spinner) findViewById(R.id.sp_review);
        bt_nav= (CardView) findViewById(R.id.bt_nav_card);
        tv_projectMonitor= (TextView) findViewById(R.id.tv_projectmonitor);
        status_up_submit= (Button) findViewById(R.id.statusupdate_submit);

        startdate= (TextView) findViewById(R.id.startdate);
        enddate= (TextView) findViewById(R.id.enddate);
        totaltarget= (TextView) findViewById(R.id.tot_target);
        currenttarget= (TextView) findViewById(R.id.curr_target);
        actualtarget= (TextView) findViewById(R.id.act_target);
        daysleft= (TextView) findViewById(R.id.daysleft);
        act_stdate= (TextView) findViewById(R.id.actualstartdate);
        act_enddate= (TextView) findViewById(R.id.actualenddate);
        layout= (LinearLayout) findViewById(R.id.task_details_layout);
        if(AppPreferences.getActualTarget(StatusUpdate.this)<AppPreferences.getCurrentTarget(StatusUpdate.this)){
            //layout.setBackgroundColor(getResources().getColor(R.color.red));
            currenttarget.setTextColor(getResources().getColor(R.color.red));
        }
        startdate.setText(AppPreferences.getStartDate(StatusUpdate.this));
        enddate.setText(AppPreferences.getEndDate(StatusUpdate.this));
        totaltarget.setText(String.valueOf(AppPreferences.getTotalTarget(StatusUpdate.this))+" "+AppPreferences.getUnitMeasurementName(StatusUpdate.this));
        actualtarget.setText(String.valueOf(AppPreferences.getActualTarget(StatusUpdate.this))+" "+AppPreferences.getUnitMeasurementName(StatusUpdate.this));
        currenttarget.setText(String.valueOf(AppPreferences.getCurrentTarget(StatusUpdate.this))+" "+AppPreferences.getUnitMeasurementName(StatusUpdate.this));
        act_stdate.setText(AppUtilities.getDateWithTimeString(AppPreferences.getActualStartDate(StatusUpdate.this)));
        act_enddate.setText(AppUtilities.getDateWithTimeString(AppPreferences.getActualEndDate(StatusUpdate.this)));
        if(day.equalsIgnoreCase(finaldate)){
            daysleft.setText(String.valueOf(AppPreferences.getDaysLeft(StatusUpdate.this))+" Days");
        }else{
            daysleft.setText("Overdue");
            daysleft.setTextColor(getResources().getColor(R.color.red));
        }
        //Toast.makeText(this, ""+AppPreferences.getSubTaskId(StatusUpdate.this), Toast.LENGTH_SHORT).show();
        /*field_mgr.setText(AppPreferences.getFieldEngineer(StatusUpdate.this));
        field_code.setText(AppPreferences.getFieldNumber(StatusUpdate.this));
        field_location.setText(AppPreferences.getFieldLocation(StatusUpdate.this));
        field_desig.setText(AppPreferences.getFieldDesignition(StatusUpdate.this));*/
        survey_map= (Button) findViewById(R.id.surveymap);
        project_mtr= (Button) findViewById(R.id.project_monitor);
        if(AppPreferences.getGroupId(StatusUpdate.this)==23||AppPreferences.getGroupId(StatusUpdate.this)==41||AppPreferences.getGroupId(StatusUpdate.this)==39){
            project_mtr.setBackgroundResource(R.drawable.greyapproval);
            tv_projectMonitor.setText("Approval");
        }
        stats_update= (Button) findViewById(R.id.statusupdate);
        per_layout= (LinearLayout) findViewById(R.id.per_layout);
        sp_layout= (LinearLayout) findViewById(R.id.spinner_layout);
        tv_statusupdate= (TextView) findViewById(R.id.tv_statusupdate);
        task_name.setText(AppPreferences.getTaskName(StatusUpdate.this)+"-"+AppPreferences.getFieldDesignition(StatusUpdate.this));
        task_type.setText(AppPreferences.getTaskType(StatusUpdate.this)+"-"+AppPreferences.getFieldDesignition(StatusUpdate.this));
        unitmeasurementname1.setText("- "+AppPreferences.getUnitMeasurementName(StatusUpdate.this));
        unitmeasurementname2.setText("- "+AppPreferences.getUnitMeasurementName(StatusUpdate.this));
        //float tar=Float.parseFloat(String.valueOf(AppPreferences.getTarget(StatusUpdate.this)));

        //TODO
        //target.setText(new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(AppPreferences.getTotalTarget(StatusUpdate.this)-AppPreferences.getTotalActual(StatusUpdate.this)))));

        target.setText(new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(AppPreferences.getTarget(StatusUpdate.this)))));
        stats_update.setBackgroundResource(R.drawable.poym_status_purple);
        tv_statusupdate.setTextColor(getResources().getColor(R.color.bottom_purple));

        progress = new ProgressDialog(StatusUpdate.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading..");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
        new FetchDetailsFromDbTask().execute();
        //networkStatus();

        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if(i>0){
                        status_id = mstatuses.get(sp_status.getSelectedItemPosition()-1).getId();
                        status_name=mstatuses.get(sp_status.getSelectedItemPosition()-1).getName();
                        if((AppPreferences.getGroupId(StatusUpdate.this)==14)||AppPreferences.getGroupId(StatusUpdate.this)==29||AppPreferences.getGroupId(StatusUpdate.this)==17||AppPreferences.getGroupId(StatusUpdate.this)==18){
                            if(status_name.equalsIgnoreCase("Off Track")||status_name.equalsIgnoreCase("Concern")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(StatusUpdate.this);
                                View v = LayoutInflater.from(StatusUpdate.this).inflate(R.layout.sp_status_popup, null);
                                builder.setView(v);
                                builder.setCancelable(false);
                                EditText et_type = (EditText) v.findViewById(R.id.status_tasktype);
                                final Spinner sp_stat_importance = (Spinner) v.findViewById(R.id.status_importance);
                                final Spinner sp_stat_issuetype = (Spinner) v.findViewById(R.id.status_issuetype);
                                final Spinner sp_stat_assignto = (Spinner) v.findViewById(R.id.status_assign);
                                EditText et_summary = (EditText) v.findViewById(R.id.status_summary);
                                et_duedate = (EditText) v.findViewById(R.id.status_duedate);
                                Button status_save = (Button) v.findViewById(R.id.status_save);
                                Button status_cacel = (Button) v.findViewById(R.id.status_cancel);
                                dialog = builder.create();
                                et_type.setText(AppPreferences.getTaskName(StatusUpdate.this));
                                final String[] arr = {"manager"};
                                ArrayAdapter<String> aa = new ArrayAdapter<String>(StatusUpdate.this, android.R.layout.simple_list_item_1, arr);
                                sp_stat_assignto.setAdapter(aa);

                                Importance_Sp_Adapter adapter = new Importance_Sp_Adapter(StatusUpdate.this, mpriorities);
                                sp_stat_importance.setAdapter(adapter);

                                IssueTypes_Sp_Adapter adapter1 = new IssueTypes_Sp_Adapter(StatusUpdate.this, missuetypes);
                                sp_stat_issuetype.setAdapter(adapter1);

                                /*final ProgressDialog progres = new ProgressDialog(StatusUpdate.this);
                                progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                progres.setMessage("Loading,Plz Wait...");
                                progres.setIndeterminate(true);
                                progres.setCancelable(false);
                                progres.show();
                                ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
                                Call<String> call1 = service1.getPriority(AppPreferences.getUserId(StatusUpdate.this));
                                call1.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        ArrayList<Priority> priorities = new Gson().fromJson(response.body(),
                                                new TypeToken<ArrayList<Priority>>() {
                                                }.getType());
                                        mpriorities = priorities;
                                        Importance_Sp_Adapter adapter = new Importance_Sp_Adapter(StatusUpdate.this, mpriorities);
                                        sp_stat_importance.setAdapter(adapter);
                                        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
                                        Call<String> call1 = service1.getIssueTypes(AppPreferences.getUserId(StatusUpdate.this));
                                        call1.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                ArrayList<IssueTypes> issueTypes = new Gson().fromJson(response.body(),
                                                        new TypeToken<ArrayList<IssueTypes>>() {
                                                        }.getType());
                                                missuetypes = issueTypes;
                                                IssueTypes_Sp_Adapter adapter1 = new IssueTypes_Sp_Adapter(StatusUpdate.this, missuetypes);
                                                sp_stat_issuetype.setAdapter(adapter1);
                                                progres.dismiss();
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                t.printStackTrace();
                                                progres.dismiss();
                                                Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        t.printStackTrace();
                                        progres.dismiss();
                                        Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                                    }
                                });*/
                                final Calendar calendar = Calendar.getInstance();
                                int yy = calendar.get(Calendar.YEAR);
                                final int mm = calendar.get(Calendar.MONTH);
                                final int m = mm+1;
                                final int dd = calendar.get(Calendar.DAY_OF_MONTH);
                                date = String.valueOf(yy) + "-" + String.valueOf(m) + "-" + String.valueOf(dd);
                                et_duedate.setText(AppUtilities.getDateString(yy, m, dd));
                                et_duedate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final Calendar calendar = Calendar.getInstance();
                                        int yy = calendar.get(Calendar.YEAR);
                                        final int mm = calendar.get(Calendar.MONTH);
                                        final int dd = calendar.get(Calendar.DAY_OF_MONTH);
                                        String comparedates = String.valueOf(calendar.getTimeInMillis());
                                        DatePickerDialog dpDialog = new DatePickerDialog(StatusUpdate.this, toDateListener1, yy, mm, dd);
                                        dpDialog.getDatePicker().setMinDate(Long.parseLong(comparedates));
                                        dpDialog.show();
                                    }
                                });
                                sp_stat_importance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if(position>0){
                                            imp_id=mpriorities.get(sp_stat_importance.getSelectedItemPosition()-1).getId();
                                            imp_name=mpriorities.get(sp_stat_importance.getSelectedItemPosition()-1).getName();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                sp_stat_issuetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if(position>0){
                                            issue_id=missuetypes.get(sp_stat_issuetype.getSelectedItemPosition()-1).getId();
                                            issue_name=missuetypes.get(sp_stat_issuetype.getSelectedItemPosition()-1).getName();
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                                status_save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(imp_id!=0&&issue_id!=0) {
                                            long masterid=System.currentTimeMillis();
                                            issueList = new IssueList();
                                            issueList.setId(masterid);
                                            issueList.setTask(AppPreferences.getTaskName(StatusUpdate.this));
                                            issueList.setTasktype(AppPreferences.getTaskType(StatusUpdate.this));
                                            issueList.setStatus(status_name);
                                            issueList.setStatusid(status_id);
                                            issueList.setImportance(imp_name);
                                            issueList.setImportanceid(imp_id);
                                            issueList.setRemark(String.valueOf(remark_id));
                                            issueList.setRemarkdesc(remark_name);
                                            issueList.setRouteassignid((int) AppPreferences.getRouteAssignmentId(StatusUpdate.this));
                                            issueList.setProject(AppPreferences.getZoneId(StatusUpdate.this));
                                            issueList.setLink(AppPreferences.getPrefAreaId(StatusUpdate.this));
                                            issueList.setLocation(AppPreferences.getDist_Area_Id(StatusUpdate.this));
                                            issueList.setEmployeeid(AppPreferences.getEmployeeId(StatusUpdate.this));
                                            issueList.setManagerid(AppPreferences.getSaleMngrIdId(StatusUpdate.this));
                                            issueList.setInsertdate(AppPreferences.getFromDate(StatusUpdate.this));
                                            issueList.setCloseddate(AppPreferences.getToDate(StatusUpdate.this));
                                            issueList.setTasktypeid(AppPreferences.getTaskTypeId(StatusUpdate.this));
                                            issueList.setTaskid(AppPreferences.getTaskTypeId(StatusUpdate.this));
                                            issueList.setDueDate(AppUtilities.getTimestampWithDate(date));
                                            issueList.setInsertFlag(true);
                                            //postIssuelist();
                                            //todo post issuelist
                                            try {
                                                RuntimeExceptionDao<IssueList, Integer> issueListDao = mDbHelper.getIssueListRuntimeDao();
                                                issueListDao.create(issueList);
                                                dialog.dismiss();
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                                status_cacel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_remark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if(i>0){
                        remark_id = mremarks.get(sp_remark.getSelectedItemPosition()-1).getId();
                        remark_name=mremarks.get(sp_remark.getSelectedItemPosition()-1).getName();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_review.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if(i>0){
                        review_id = mremarks.get(sp_review.getSelectedItemPosition()-1).getId();
                        review_name=mremarks.get(sp_review.getSelectedItemPosition()-1).getName();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        status_up_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppPreferences.getGroupId(StatusUpdate.this)==14||AppPreferences.getGroupId(StatusUpdate.this)==29||AppPreferences.getGroupId(StatusUpdate.this)==17||AppPreferences.getGroupId(StatusUpdate.this)==18) {
                    if (status_name != null && remark_name != null) {
                        long masterId = System.currentTimeMillis();
                        projectStatuses = new ProjectStatuses();
                        projectStatuses.setId(masterId);
                        projectStatuses.setTask(AppPreferences.getTaskName(StatusUpdate.this));
                        projectStatuses.setTasktype(AppPreferences.getTaskType(StatusUpdate.this));
                        projectStatuses.setStatus(status_name);
                        projectStatuses.setStatusid(status_id);
                        if (final_value == 100) {
                            projectStatuses.setPercentcompleted(final_value);
                            projectStatuses.setDesccompleted("closed");
                        } else {
                            projectStatuses.setPercentcompleted(final_value);
                            projectStatuses.setDesccompleted("partial");
                        }
                        projectStatuses.setLatitude(String.valueOf(latitude));
                        projectStatuses.setInsertFlag(true);
                        projectStatuses.setRouteassignid(AppPreferences.getRouteAssignmentId(StatusUpdate.this));
                        projectStatuses.setLongitude(String.valueOf(longitude));
                        projectStatuses.setTaskid(AppPreferences.getPrefRouteId(StatusUpdate.this));
                        projectStatuses.setTasktypeid(AppPreferences.getTourTypeId(StatusUpdate.this));
                        projectStatuses.setStatustime(AppUtilities.getDateTime1());
                        //TODO
                        //projectStatuses.setTarget(AppPreferences.getTotalActual(StatusUpdate.this));
                        projectStatuses.setTarget(AppPreferences.getTarget(StatusUpdate.this));
                        if (AppPreferences.getGroupId(StatusUpdate.this) == 14||AppPreferences.getGroupId(StatusUpdate.this)==17||AppPreferences.getGroupId(StatusUpdate.this)==18) {
                            projectStatuses.setActual(act_value);
                        }
                        projectStatuses.setRemark(remark_id);
                        projectStatuses.setRemarkdesc(remark_name);
                        projectStatuses.setEmployeeid(AppPreferences.getEmployeeId(StatusUpdate.this));
                        projectStatuses.setZoneId(AppPreferences.getZoneId(StatusUpdate.this));
                        projectStatuses.setDistributionAreaId(AppPreferences.getDist_Area_Id(StatusUpdate.this));
                        projectStatuses.setSalesAreaId(AppPreferences.getPrefAreaId(StatusUpdate.this));
                        //postProjectStatus();
                        /*String fromdate=AppPreferences.getFromDateValue(StatusUpdate.this);
                        String updateview_date=null;
                        if(fromdate!=null){
                            int from = Integer.parseInt(fromdate.substring(8, 10));
                            int fro_date = from + 1;
                            updateview_date = fromdate.substring(0, 8) + fro_date+"T18:30:00.000Z";
                        }*/

                        try {
                            RuntimeExceptionDao<ProjectStatuses,Integer> projectStatusesDao=mDbHelper.getProjectStatusesRuntimeDao();
                            projectStatusesDao.create(projectStatuses);

                            //todo update project statuses id in route assignments
                            RuntimeExceptionDao<RouteAssignments,Integer> routeAssignmentsDao=mDbHelper.getRouteAssignmentsRuntimeDao();
                            UpdateBuilder<RouteAssignments,Integer> routeAssignmentsUpdate=routeAssignmentsDao.updateBuilder();
                            routeAssignmentsUpdate.updateColumnValue("totalactual",act_value);
                            routeAssignmentsUpdate.updateColumnValue("submitflag","true");
                            if(mrouteAssignments.get(0).getInsertFlag()){
                                routeAssignmentsUpdate.updateColumnValue("is_updated",false);
                            }else{
                                routeAssignmentsUpdate.updateColumnValue("is_updated",true);
                            }
                            //routeAssignmentsUpdate.updateColumnValue("projectstatusid",masterId);
                            //routeAssignmentsUpdate.updateColumnValue("is_updated",false);
                            routeAssignmentsUpdate.updateColumnValue("distributionSubAreaId",AppPreferences.getSubTaskId(StatusUpdate.this));
                            routeAssignmentsUpdate.updateColumnValue("subtaskid",AppPreferences.getSubTypeId(StatusUpdate.this));
                            routeAssignmentsUpdate.updateColumnValue("areaid",AppPreferences.getSubTypeId(StatusUpdate.this));
                            routeAssignmentsUpdate.where().eq("id",AppPreferences.getRouteAssignmentId(StatusUpdate.this));
                            routeAssignmentsUpdate.update();

                            //update local routesalesviews
                            RuntimeExceptionDao<RouteSalesViews,Integer> routeSalesViewsDao=mDbHelper.getRouteSalesViewsRuntimeDao();
                            UpdateBuilder<RouteSalesViews,Integer> routeSalesViewsUpdate=routeSalesViewsDao.updateBuilder();
                            routeSalesViewsUpdate.updateColumnValue("submitflag",true);
                            routeSalesViewsUpdate.updateColumnValue("totalactual",act_value);
                            routeSalesViewsUpdate.updateColumnValue("updateflag",true);
                            routeSalesViewsUpdate.updateColumnValue("rowclicked",false);
                            routeSalesViewsUpdate.where().eq("id",AppPreferences.getFromDateValueID(StatusUpdate.this));
                            routeSalesViewsUpdate.update();

                            RuntimeExceptionDao<RouteAssignmentSummaries,Integer> routeAssignmentSummariesDao=mDbHelper.getRouteAssignmentSummariesRuntimeDao();
                            UpdateBuilder<RouteAssignmentSummaries,Integer> routeAssignmentSummariesUpdate=routeAssignmentSummariesDao.updateBuilder();
                            if(AppPreferences.getPrefCompletedFlag(StatusUpdate.this) && act_value<AppPreferences.getTarget(StatusUpdate.this)){
                                String final_extend_date = null;
                                if(AppPreferences.getTaskExtendDate(StatusUpdate.this)!=null){
                                    String date=AppPreferences.getTaskExtendDate(StatusUpdate.this);
                                    int Extend_date = Integer.parseInt(date.substring(8, 10));
                                    int to_date = Extend_date + 1;
                                    if(to_date==1 || to_date==2 ||to_date==3 ||to_date==4 ||to_date==5
                                            ||to_date==6 ||to_date==7 ||to_date==8 ||to_date==9){
                                        final_extend_date = date.substring(0, 8) +"0"+ to_date+"T18:30:00.000Z";
                                    }else{
                                        final_extend_date = date.substring(0, 8) + to_date+"T18:30:00.000Z";
                                    }
                                    //final_extend_date = date.substring(0, 8) + to_date+"T18:30:00.000Z";
                                    Log.e("final_extend_date",""+final_extend_date);
                                }
                                Log.e("Task_notfinished","if");
                                routeAssignmentSummariesUpdate.updateColumnValue("isupdateflag",true);
                                routeAssignmentSummariesUpdate.updateColumnValue("updateflag",true);
                                routeAssignmentSummariesUpdate.updateColumnValue("totalactual",AppPreferences.getTotalActual(StatusUpdate.this)+act_value);
                                routeAssignmentSummariesUpdate.updateColumnValue("startFlag",AppPreferences.getStartFlag(StatusUpdate.this));
                                routeAssignmentSummariesUpdate.updateColumnValue("todate",final_extend_date);
                                routeAssignmentSummariesUpdate.where().eq("id",AppPreferences.getRouteAssignmentSummaryId(StatusUpdate.this));
                            }else{
                                Log.e("Task_notfinished","else");
                                routeAssignmentSummariesUpdate.updateColumnValue("isupdateflag",true);
                                routeAssignmentSummariesUpdate.updateColumnValue("updateflag",true);
                                routeAssignmentSummariesUpdate.updateColumnValue("totalactual",AppPreferences.getTotalActual(StatusUpdate.this)+act_value);
                                routeAssignmentSummariesUpdate.updateColumnValue("startFlag",AppPreferences.getStartFlag(StatusUpdate.this));
                                routeAssignmentSummariesUpdate.updateColumnValue("completedFlag",AppPreferences.getPrefCompletedFlag(StatusUpdate.this));
                                routeAssignmentSummariesUpdate.where().eq("id",AppPreferences.getRouteAssignmentSummaryId(StatusUpdate.this));
                            }
                            routeAssignmentSummariesUpdate.update();
                            //updating local RouteAssignmentSummariesViews table
                            RuntimeExceptionDao<RouteAssignmentSummariesViews,Integer> routeAssignmentSummariesViewsDao=mDbHelper.getRouteAssignmentSummariesViewsRuntimeDao();
                            UpdateBuilder<RouteAssignmentSummariesViews,Integer> routeAssignmentSummariesViewsUpdate=routeAssignmentSummariesViewsDao.updateBuilder();
                            if(AppPreferences.getPrefCompletedFlag(StatusUpdate.this) && act_value<AppPreferences.getTarget(StatusUpdate.this)){
                                String final_extend_date = null;
                                if(AppPreferences.getTaskExtendDate(StatusUpdate.this)!=null){
                                    String date=AppPreferences.getTaskExtendDate(StatusUpdate.this);
                                    int Extend_date = Integer.parseInt(date.substring(8, 10));
                                    int to_date = Extend_date + 1;
                                    if(to_date==1 || to_date==2 ||to_date==3 ||to_date==4 ||to_date==5
                                            ||to_date==6 ||to_date==7 ||to_date==8 ||to_date==9){
                                        final_extend_date = date.substring(0, 8) +"0"+ to_date+"T18:30:00.000Z";
                                    }else{
                                        final_extend_date = date.substring(0, 8) + to_date+"T18:30:00.000Z";
                                    }
                                    Log.e("final_extend_date",""+final_extend_date);
                                }
                                Log.e("Task_notfinished","if");
                                routeAssignmentSummariesViewsUpdate.updateColumnValue("updateflag",true);
                                routeAssignmentSummariesViewsUpdate.updateColumnValue("totalactual",AppPreferences.getTotalActual(StatusUpdate.this)+act_value);
                                routeAssignmentSummariesViewsUpdate.updateColumnValue("startFlag",AppPreferences.getStartFlag(StatusUpdate.this));
                                routeAssignmentSummariesViewsUpdate.updateColumnValue("todate",final_extend_date);
                                routeAssignmentSummariesViewsUpdate.updateColumnValue("percentage",((AppPreferences.getTotalActual(StatusUpdate.this)+act_value)/AppPreferences.getTotalTarget(StatusUpdate.this))*100);
                                routeAssignmentSummariesViewsUpdate.where().eq("id",AppPreferences.getRouteAssignmentSummaryId(StatusUpdate.this));
                            }else{
                                Log.e("Task_notfinished","else");
                                routeAssignmentSummariesViewsUpdate.updateColumnValue("updateflag",true);
                                routeAssignmentSummariesViewsUpdate.updateColumnValue("totalactual",AppPreferences.getTotalActual(StatusUpdate.this)+act_value);
                                routeAssignmentSummariesViewsUpdate.updateColumnValue("startFlag",AppPreferences.getStartFlag(StatusUpdate.this));
                                routeAssignmentSummariesViewsUpdate.updateColumnValue("completedFlag",AppPreferences.getPrefCompletedFlag(StatusUpdate.this));
                                routeAssignmentSummariesViewsUpdate.updateColumnValue("percentage",((AppPreferences.getTotalActual(StatusUpdate.this)+act_value)/AppPreferences.getTotalTarget(StatusUpdate.this))*100);
                                routeAssignmentSummariesViewsUpdate.where().eq("id",AppPreferences.getRouteAssignmentSummaryId(StatusUpdate.this));
                            }
                            Log.e("percentage",String.valueOf(((AppPreferences.getTotalActual(StatusUpdate.this)+act_value)/AppPreferences.getTotalTarget(StatusUpdate.this))*100));
                            routeAssignmentSummariesViewsUpdate.update();

                            Toast.makeText(StatusUpdate.this, "Submited", Toast.LENGTH_SHORT).show();
                            sp_status.setSelection(0);
                            sp_remark.setSelection(0);
                            actual.setText("");
                            per_complete.setText("");

                            Intent in1 = new Intent(StatusUpdate.this, LandingActivity.class);
                            in1.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                            in1.putExtra("partnerid",partnerid);
                            in1.putExtra("usertype",AppPreferences.getUserType(StatusUpdate.this));
                            in1.putExtra("projecttype",AppPreferences.getProjectType(StatusUpdate.this));
                            in1.putExtra("user_email",AppPreferences.getUserEmail(StatusUpdate.this));
                            //in1.putExtra("usersalesAreaid",AppPreferences.getUser_SalesAreaId(StatusUpdate.this));
                            startActivity(in1);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(StatusUpdate.this, "Please Enter values", Toast.LENGTH_SHORT).show();
                    }
                } else if(AppPreferences.getGroupId(StatusUpdate.this) == 23||AppPreferences.getGroupId(StatusUpdate.this) == 41){
                    //putProjectStatuese();
                    if(review_name!=null){
                        try{
                            //todo update project statuses
                            RuntimeExceptionDao<ProjectStatuses,Integer> projectStatusesDao = mDbHelper.getProjectStatusesRuntimeDao();
                            UpdateBuilder<ProjectStatuses,Integer> updateBuilder=projectStatusesDao.updateBuilder();
                            updateBuilder.updateColumnValue("id",AppPreferences.getProjectStatusid(StatusUpdate.this));
                            updateBuilder.updateColumnValue("qaremark",review_id);
                            updateBuilder.updateColumnValue("qaremarkdesc",review_name);
                            updateBuilder.updateColumnValue("qasubmitflag",true);
                            updateBuilder.update();

                            //todo Update route assignments
                            RuntimeExceptionDao<RouteAssignments,Integer> routeAssignmentsDao=mDbHelper.getRouteAssignmentsRuntimeDao();
                            UpdateBuilder<RouteAssignments,Integer> updateBuilder1=routeAssignmentsDao.updateBuilder();
                            updateBuilder1.updateColumnValue("id",AppPreferences.getRouteAssignmentId(StatusUpdate.this));
                            updateBuilder1.updateColumnValue("routeassignmentsummaryid",AppPreferences.getRouteAssignmentSummaryId(StatusUpdate.this));
                            updateBuilder1.updateColumnValue("totalactual",qa_actual);
                            updateBuilder1.updateColumnValue("qasubmitflag",true);
                            updateBuilder1.updateColumnValue("is_updated",true);
                            updateBuilder1.update();

                            RuntimeExceptionDao<RouteSalesViews,Integer> routeSalesViewsDao=mDbHelper.getRouteSalesViewsRuntimeDao();
                            UpdateBuilder<RouteSalesViews,Integer> routeSalesViewsUpdate=routeSalesViewsDao.updateBuilder();
                            routeSalesViewsUpdate.updateColumnValue("qasubmitflag",true);
                            routeSalesViewsUpdate.where().eq("id",AppPreferences.getRouteSalesViewid(StatusUpdate.this));
                            routeSalesViewsUpdate.update();

                            sp_review.setSelection(0);
                            Intent in1 = new Intent(StatusUpdate.this, LandingActivity.class);
                            in1.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                            in1.putExtra("partnerid",partnerid);
                            in1.putExtra("usertype",AppPreferences.getUserType(StatusUpdate.this));
                            in1.putExtra("projecttype",AppPreferences.getProjectType(StatusUpdate.this));
                            in1.putExtra("user_email",AppPreferences.getUserEmail(StatusUpdate.this));
                            //in1.putExtra("usersalesAreaid",AppPreferences.getUser_SalesAreaId(StatusUpdate.this));
                            startActivity(in1);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(StatusUpdate.this, "Select Review", Toast.LENGTH_SHORT).show();
                    }

                }else if(AppPreferences.getGroupId(StatusUpdate.this) == 39){
                    sp_review.setSelection(0);
                    Toast.makeText(StatusUpdate.this, "Consult User Can't Update", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(AppPreferences.getGroupId(StatusUpdate.this)==14||AppPreferences.getGroupId(StatusUpdate.this)==29||AppPreferences.getGroupId(StatusUpdate.this)==17||AppPreferences.getGroupId(StatusUpdate.this)==18){
            per_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!actual.getText().toString().equalsIgnoreCase("")){
                        act_value= Float.parseFloat(actual.getText().toString());
                        //TODO
                        //String str_Target = new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(AppPreferences.getTotalTarget(StatusUpdate.this)-AppPreferences.getTotalActual(StatusUpdate.this))));

                        String str_Target = new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(AppPreferences.getTarget(StatusUpdate.this))));
                        //if(act_value>AppPreferences.getTarget(StatusUpdate.this)){
                        if(act_value>Float.valueOf(str_Target)){
                            Toast.makeText(StatusUpdate.this, "Please.,Enter Minimum Value", Toast.LENGTH_SHORT).show();
                        }else {
                            final_value=(act_value/Float.parseFloat(str_Target))*100;
                            per_complete.setText(String.valueOf(final_value));
                        }
                    }else{
                        Toast.makeText(StatusUpdate.this, "Please.,Enter Actual Value", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        survey_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(StatusUpdate.this,MapScreen.class);
                intent1.putExtra("partnerid",partnerid);
                intent1.putExtra("date",day);
                startActivity(intent1);
            }
        });
        /*stats_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        project_mtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(StatusUpdate.this,ProjectMonitorActivity.class);
                //intent2.putExtra("Latitude",latitude);
                //intent2.putExtra("Longitude",longitude);
                intent2.putExtra("partnerid",partnerid);
                intent2.putExtra("date",day);
                startActivity(intent2);
            }
        });
    }


    private void networkStatus() {
        progress = new ProgressDialog(StatusUpdate.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading..");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getStatus(AppPreferences.getUserId(StatusUpdate.this));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Status> statuses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Status>>() {
                        }.getType());
                mstatuses = statuses;
                networkRemarks();
                status_sp_adapter = new Status_Sp_Adapter(StatusUpdate.this, mstatuses);
                sp_status.setAdapter(status_sp_adapter);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                //CallingSnackbar();
                //Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkRemarks() {

        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service.getRemarks(AppPreferences.getUserId(StatusUpdate.this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Remarks> remarkses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Remarks>>() {
                        }.getType());
                mremarks = remarkses;
                remarks_sp_adapter = new Remarks_Sp_Adapter(StatusUpdate.this, mremarks);
                sp_remark.setAdapter(remarks_sp_adapter);
                review_sp_adapter=new Review_Sp_Adapter(StatusUpdate.this,mremarks);
                sp_review.setAdapter(review_sp_adapter);
                if(AppPreferences.getGroupId(StatusUpdate.this)==14||AppPreferences.getGroupId(StatusUpdate.this)==17||AppPreferences.getGroupId(StatusUpdate.this)==18){
                    per_layout.setVisibility(View.VISIBLE);
                    progress.dismiss();
                }
                if(AppPreferences.getGroupId(StatusUpdate.this)==29){
                    per_layout.setVisibility(View.VISIBLE);
                    bt_nav.setVisibility(View.GONE);
                    progress.dismiss();
                }
                if(AppPreferences.getGroupId(StatusUpdate.this)==23||AppPreferences.getGroupId(StatusUpdate.this)==41||AppPreferences.getGroupId(StatusUpdate.this)==39){
                    networkProjectStatus();
                    sp_layout.setVisibility(View.VISIBLE);
                    actual.setEnabled(false);
                    sp_remark.setEnabled(false);
                    sp_status.setEnabled(false);
                    status_up_submit.setText("Update");
                    //Toast.makeText(this, ""+AppPreferences.getProjectStatusid(StatusUpdate.this), Toast.LENGTH_SHORT).show();
                    Log.d("projectstatusid", String.valueOf(AppPreferences.getProjectStatusid(StatusUpdate.this)));


                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                //CallingSnackbar();
                //Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkProjectStatus() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getProjectStatuses(AppPreferences.getUserId(StatusUpdate.this),AppPreferences.getProjectStatusid(StatusUpdate.this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectStatuses> projectStatus = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectStatuses>>() {
                        }.getType());
                mprojectStatus = projectStatus;
                progress.dismiss();
                Log.d("projectstatus",String.valueOf(mprojectStatus));
                float qa_actual= 0;
                int remark=0;
                int status=0;
                try {
                    qa_actual = mprojectStatus.get(0).getActual();
                    remark=mprojectStatus.get(0).getRemark();
                    status=mprojectStatus.get(0).getStatusid();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for(int i=0;i<mremarks.size();i++){
                    int id=mremarks.get(i).getId();
                    if(id==remark){
                        sp_remark.setSelection(i+1);
                        break;
                    }
                }
                for(int j=0;j<mstatuses.size();j++){
                    int s_id=mstatuses.get(j).getId();
                    if(s_id==status){
                        sp_status.setSelection(j+1);
                        break;
                    }
                }
                actual.setText(new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(qa_actual))));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                //CallingSnackbar();
                //Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pendingtask, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent in1 = new Intent(StatusUpdate.this, LandingActivity.class);
            in1.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            in1.putExtra("partnerid",partnerid);
            in1.putExtra("usertype",AppPreferences.getUserType(StatusUpdate.this));
            in1.putExtra("projecttype",AppPreferences.getProjectType(StatusUpdate.this));
            in1.putExtra("user_email",AppPreferences.getUserEmail(StatusUpdate.this));
            //in1.putExtra("usersalesAreaid",AppPreferences.getUser_SalesAreaId(StatusUpdate.this));
            startActivity(in1);
            //Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.pending_detail) {
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(StatusUpdate.this);
                View view = LayoutInflater.from(StatusUpdate.this).inflate(R.layout.survey_details_popup, null);
                builder.setCancelable(false);
                builder.setTitle("Checklist Details");
                builder.setView(view);
                final LinearLayout la_edittext = view.findViewById(R.id.detail_edittext);
                final LinearLayout la_location = view.findViewById(R.id.detail_location);
                final LinearLayout la_spinner = view.findViewById(R.id.details_spinner1);
                final LinearLayout la_radiio = view.findViewById(R.id.details_radio);
                Button capture_image = view.findViewById(R.id.survey_capture);
                final Spinner sp_parameter = view.findViewById(R.id.details_spinner);
                final EditText param_value = view.findViewById(R.id.details_value);
                final Button bt_location = view.findViewById(R.id.location_button);
                final Spinner sp_dropdown = view.findViewById(R.id.sp_details_spinner);
                final RadioGroup rd_group = view.findViewById(R.id.radiogroup);
//                final RadioButton rb_yes = view.findViewById(R.id.rb_details_yes);
//                final RadioButton rb_no = view.findViewById(R.id.rb_details_no);
                Button para_add = view.findViewById(R.id.details_add);
                final RecyclerView re_details = view.findViewById(R.id.rv_details_popup);
                Button sur_ok = view.findViewById(R.id.survey_ok);
                Button sur_cancel = view.findViewById(R.id.survey_cancel);
                final AlertDialog dailog = builder.create();
                capture_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ContextCompat.checkSelfPermission(StatusUpdate.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(StatusUpdate.this,
                                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                        } else {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            picFile = new File(imagePath);
                            picUri = Uri.fromFile(picFile);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                });
                try {
                    final Checklist_Sp_Adapter adapter = new Checklist_Sp_Adapter(StatusUpdate.this, mparamCategories);
                    sp_parameter.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
                sp_parameter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i > 0) {
                            paramid = mparamCategories.get(sp_parameter.getSelectedItemPosition() - 1).getId();
                            param_name = mparamCategories.get(sp_parameter.getSelectedItemPosition() - 1).getName();
                            param_type = mparamCategories.get(sp_parameter.getSelectedItemPosition() - 1).getInputtype();
                            if (param_type.equalsIgnoreCase("numeric")) {
                                param_value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED );
                            } else {
                                param_value.setInputType(InputType.TYPE_CLASS_TEXT);
                            }
                            if (param_type.equalsIgnoreCase("text") || param_type.equalsIgnoreCase("numeric")) {
                                la_spinner.setVisibility(View.GONE);
                                la_radiio.setVisibility(View.GONE);
                                la_edittext.setVisibility(View.VISIBLE);
                                la_location.setVisibility(View.GONE);
                            }
                            if (param_type.equalsIgnoreCase("dt")) {
                                la_spinner.setVisibility(View.GONE);
                                la_radiio.setVisibility(View.GONE);
                                la_edittext.setVisibility(View.GONE);
                                la_location.setVisibility(View.GONE);
                            }
                            if (param_type.equalsIgnoreCase("location")) {
                                la_spinner.setVisibility(View.GONE);
                                la_radiio.setVisibility(View.GONE);
                                la_edittext.setVisibility(View.GONE);
                                la_location.setVisibility(View.VISIBLE);
                                userLocation();
                                bt_location.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        userLocation();
                                        Toast.makeText(StatusUpdate.this, "Location Captured", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if (param_type.equalsIgnoreCase("dropdown")) {
                                la_location.setVisibility(View.GONE);
                                la_edittext.setVisibility(View.GONE);
                                la_spinner.setVisibility(View.VISIBLE);
                                la_radiio.setVisibility(View.GONE);
                                String inputvalues = mparamCategories.get(sp_parameter.getSelectedItemPosition() - 1).getInputvalues();
                                String[] Array = inputvalues.split(",");
                                List<String> param_values = new ArrayList<String>();
                                for (String name : Array) {
                                    param_values.add(name);
//                                    param_values.set(0,"Value");
                                }

                                ArrayAdapter<String> sp_adapter;
                                sp_adapter = new ArrayAdapter<String>(StatusUpdate.this, android.R.layout.simple_list_item_1, param_values);
                                sp_dropdown.setAdapter(sp_adapter);
                                sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        dropdown_value = (String) adapterView.getItemAtPosition(i);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            if (param_type.equalsIgnoreCase("rb")) {
                                la_edittext.setVisibility(View.GONE);
                                la_location.setVisibility(View.GONE);
                                la_radiio.setVisibility(View.VISIBLE);
                                la_spinner.setVisibility(View.GONE);
                                //String rb_values;
                                String inputvalues = mparamCategories.get(sp_parameter.getSelectedItemPosition() - 1).getInputvalues();
                                Log.e("inputvalues",inputvalues);
                                String[] Array = inputvalues.split(",");
                                try {
                                    for (int r = 0; r < Array.length; r++) {
                                        RadioButton rdbtn = new RadioButton(StatusUpdate.this);
                                        rdbtn.setId(r);
                                        rdbtn.setText(Array[r]);
                                        rd_group.addView(rdbtn);
                                    }
                                }catch (Exception e){
                                    Log.e("Exception",String.valueOf(e));
                                }
                                rd_group.clearCheck();
                                rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        RadioButton rb_yes1 = (RadioButton) group.findViewById(checkedId);
                                        /*if (null != rb_yes1 && checkedId > -1) {
                                            rb_values = rb_yes1.getText().toString();
                                        }*/
                                        if (null != rb_yes1 ) {
                                            rb_values = rb_yes1.getText().toString();
                                        }
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                final Details_Rv_Adapter recyclerview_adapter = new Details_Rv_Adapter(mdetails);
                LinearLayoutManager layoutManager = new LinearLayoutManager(StatusUpdate.this, LinearLayoutManager.VERTICAL, false);
                re_details.setAdapter(recyclerview_adapter);
                re_details.setLayoutManager(layoutManager);
                para_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if((param_type!=null && param_value.length()!=0)
                                || (param_type!=null && dropdown_value!=null)
                                || (param_type!=null && rb_values!=null) || (param_type!=null && lati!=0.0) || (param_type!=null && param_type.equalsIgnoreCase("dt"))){
                            details = new ParamDetails();
                            long masterId = System.currentTimeMillis();
                            details.setCategoryid(String.valueOf(paramid));
                            details.setParametervalue(param_type==null?"":param_type);
                            if (param_type.equalsIgnoreCase("text") || param_type.equalsIgnoreCase("numeric")) {
                                details.setDescription(param_value.getText().toString());
                            }
                            if (param_type.equalsIgnoreCase("location")) {
                                details.setDescription(new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(lati))) + "," + new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(longi))));
                            }
                            if (param_type.equalsIgnoreCase("dropdown")) {
                                details.setDescription(dropdown_value);
                                sp_dropdown.setSelection(0);
                            }
                            if (param_type.equalsIgnoreCase("rb")) {
                                details.setDescription(rb_values);
                                rd_group.clearCheck();
                            }
                            if (param_type.equalsIgnoreCase("dt")) {
                                details.setDescription(AppUtilities.getDateTime());
                            }
                            if (details != null) {
                                //details.setSurveyid(String.valueOf(masterId));
                                details.setInsertFlag(true);
                                details.setId(masterId);
                                mdetails.add(details);
                                if (recyclerview_adapter != null) {
                                    recyclerview_adapter.notifyDataSetChanged();
                                }
                                param_value.setText("");
                                param_type=null;
                                sp_parameter.setSelection(0);la_spinner.setVisibility(View.GONE);
                                la_radiio.setVisibility(View.GONE);
                                la_edittext.setVisibility(View.VISIBLE);
                                la_location.setVisibility(View.GONE);

                            /*try {
                                RuntimeExceptionDao<ParamDetails,Integer> paramDetailsDao=mDbHelper.getParamDetailsRuntimeDao();
                                paramDetailsDao.create(details);
                            }catch (Exception e){
                                e.printStackTrace();
                            }*/
                            } else {
                                Toast.makeText(StatusUpdate.this, "Empty data", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(StatusUpdate.this, "Select Parameter/ Enter value", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                sur_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mdetails.size()>0){
                            for(int pv=0;pv<mdetails.size();pv++){
                                try {
                                    long masterid = System.currentTimeMillis();
                                    ChecklistAnswers checklistAnswers=new ChecklistAnswers();
                                    checklistAnswers.setId(masterid);
                                    checklistAnswers.setRouteassignmentid(AppPreferences.getRouteAssignmentId(StatusUpdate.this));
                                    checklistAnswers.setRouteassignmentsummaryid(AppPreferences.getRouteAssignmentSummaryId(StatusUpdate.this));
                                    checklistAnswers.setInsertFlag(true);
                                    checklistAnswers.setChecklistid(Integer.parseInt(mdetails.get(pv).getCategoryid()));
                                    checklistAnswers.setQuestiontype(mdetails.get(pv).getParametervalue());
                                    checklistAnswers.setAnswers(mdetails.get(pv).getDescription());
                                    RuntimeExceptionDao<ChecklistAnswers, Integer> checklistAnswersDao = mDbHelper.getChecklistAnswersRuntimeDao();
                                    checklistAnswersDao.create(checklistAnswers);
                                    Log.d("ChecklistAnswers","post");
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                            dailog.dismiss();
                        }else {
                            Toast.makeText(StatusUpdate.this, "Select Parameter", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                sur_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dailog.dismiss();
                    }
                });
                dailog.show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void userLocation() {
        final ProgressDialog progress = new ProgressDialog(StatusUpdate.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Fetching ur location,Plz Wait..");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        manager = (LocationManager) StatusUpdate.this.getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();

                /*if(source!=null) {
                    if (msurvey.size() > 0) {
                        double lat = msurvey.get(0).getLatitude();
                        double lon = msurvey.get(0).getLongitude();
                        CameraPosition pos = new CameraPosition.Builder()
                                .target(new com.mapbox.mapboxsdk.geometry.LatLng(lat, lon))
                                .zoom(15)
                                .tilt(20)
                                .build();
                        if (mMapbox != null)
                            mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(pos), 1000);
                    } else {
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new com.mapbox.mapboxsdk.geometry.LatLng(lati, longi))
                                .zoom(15)
                                .tilt(20)
                                .build();
                        if (mMapbox != null)
                            mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);
                    }
                }*/

                /*CameraPosition position = new CameraPosition.Builder()
                        .target(new com.mapbox.mapboxsdk.geometry.LatLng(lati, longi))
                        .zoom(15)
                        .tilt(20)
                        .build();
                if(mMapbox!=null)
                mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);*/

                /*try {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(source, 19));
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                if (ActivityCompat.checkSelfPermission(StatusUpdate.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(StatusUpdate.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                if (i == LocationProvider.OUT_OF_SERVICE) {
                    Toast.makeText(StatusUpdate.this, "Network Unavaliable", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(StatusUpdate.this, "Fetching..PlzWait", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(StatusUpdate.this, "Please enable GPS", Toast.LENGTH_SHORT).show();
            }
        };
        if (ActivityCompat.checkSelfPermission(StatusUpdate.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(StatusUpdate.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 50, listener);
    }

    public void postProjectStatus(){
        progressDialog.show();
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            benjson= parser.parse(new Gson().toJson(
                    projectStatuses, ProjectStatuses.class)).getAsJsonObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        benjson.remove("id");
        call = service.insertProjectStatus(AppPreferences.getUserId(this), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        projectstatusid=object.getString("id");
                        Log.d("projectstatusid", projectstatusid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("statusupdate"," project statuc done");
                    putRouteAssignment();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void putRouteAssignment(){
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            benjson.addProperty("totalactual",act_value);
            benjson.addProperty("submitflag","true");
            benjson.addProperty("projectstatusid",projectstatusid);
            benjson.addProperty("distributionSubAreaId",AppPreferences.getSubTaskId(StatusUpdate.this));
            benjson.addProperty("subtaskid",AppPreferences.getSubTypeId(StatusUpdate.this));
            benjson.addProperty("areaid",AppPreferences.getSubTypeId(StatusUpdate.this));
            benjson.addProperty("id",AppPreferences.getRouteAssignmentId(StatusUpdate.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        call = service.updateRouteAssigments(AppPreferences.getUserId(StatusUpdate.this), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Log.d("statusupdate","routeassign done");
                    putRouteAssignmentSummaries();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void putRouteAssignmentSummaries(){
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            benjson.addProperty("totalactual",AppPreferences.getTotalActual(StatusUpdate.this)+act_value);
            benjson.addProperty("startFlag",AppPreferences.getStartFlag(StatusUpdate.this));
            benjson.addProperty("completedFlag",AppPreferences.getPrefCompletedFlag(StatusUpdate.this));
            benjson.addProperty("id",AppPreferences.getRouteAssignmentSummaryId(StatusUpdate.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        call = service.updateRouteAssigmentSummaries(AppPreferences.getUserId(StatusUpdate.this), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Log.d("statusupdate","route assign summaries");
                    progressDialog.dismiss();
                    Toast.makeText(StatusUpdate.this, "Submited", Toast.LENGTH_SHORT).show();
                    sp_status.setSelection(0);
                    sp_remark.setSelection(0);
                    actual.setText("");
                    per_complete.setText("");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void putProjectStatuese(){
        progress1 = new ProgressDialog(StatusUpdate.this);
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
            benjson.addProperty("qaremark",review_id);
            benjson.addProperty("qaremarkdesc",review_name);
            benjson.addProperty("qasubmitflag",true);
            benjson.addProperty("id",AppPreferences.getProjectStatusid(StatusUpdate.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        call = service.updateProjectStatuses(AppPreferences.getUserId(StatusUpdate.this), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    putRouteAssignment1();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                t.printStackTrace();
                progress1.dismiss();
                Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void putRouteAssignment1(){
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            benjson.addProperty("qasubmitflag","true");
            benjson.addProperty("id",AppPreferences.getRouteAssignmentId(StatusUpdate.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        call = service.updateRouteAssigments(AppPreferences.getUserId(StatusUpdate.this), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Log.d("statusupdate","routeassign done");
                    progress1.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress1.dismiss();
                Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private DatePickerDialog.OnDateSetListener toDateListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
            final int m = monthOfYear;
            Log.d("first tvalue", "" + monthOfYear + dayOfMonth + year);
            date = String.valueOf(year) + "-" + String.valueOf(m+1) + "-" + String.valueOf(dayOfMonth);
            et_duedate.setText(AppUtilities.getDateString(year, m, dayOfMonth));
        }
    };
    private void postIssuelist(){
        final ProgressDialog progressDialog = new ProgressDialog(StatusUpdate.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson = new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            benjson = parser.parse(new Gson().toJson(
                    issueList, IssueList.class)).getAsJsonObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        benjson.remove("id");
        call = service.insertIssueList(AppPreferences.getUserId(StatusUpdate.this), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(StatusUpdate.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void CallingSnackbar(){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, R.string.check_internet, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //networkStatus();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }*/

    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                RuntimeExceptionDao<com.example.kanthi.projectmonitoring.PoJo.Status, Integer> StatusDao = mDbHelper.getStatusRuntimeDao();
                mstatuses = StatusDao.queryForAll();

                RuntimeExceptionDao<Remarks, Integer> RemarksDao = mDbHelper.getRemarksRuntimeDao();
                mremarks = RemarksDao.queryForAll();


                RuntimeExceptionDao<Checklist, Integer> paramCategoriesDao = mDbHelper.getChecklistRuntimeDao();
                mparamCategories=paramCategoriesDao.queryForAll();
                Log.e("mparamCategories",""+mparamCategories.size());

                RuntimeExceptionDao<RouteAssignments, Integer> RouteAssignmentsDao = mDbHelper.getRouteAssignmentsRuntimeDao();
                QueryBuilder<RouteAssignments, Integer> routeAssignmentsQueryBuilder = RouteAssignmentsDao.queryBuilder();
                routeAssignmentsQueryBuilder.where().eq("id", AppPreferences.getRouteAssignmentId(StatusUpdate.this));
                PreparedQuery<RouteAssignments> preparedQuery = routeAssignmentsQueryBuilder.prepare();
                mrouteAssignments = RouteAssignmentsDao.query(preparedQuery);

                if(AppPreferences.getGroupId(StatusUpdate.this)==23||AppPreferences.getGroupId(StatusUpdate.this)==41||AppPreferences.getGroupId(StatusUpdate.this)==39){
                    RuntimeExceptionDao<ProjectStatuses, Integer> ProjectStatusesDao = mDbHelper.getProjectStatusesRuntimeDao();
                    QueryBuilder<ProjectStatuses, Integer> projectStatusesQueryBuilder = ProjectStatusesDao.queryBuilder();
                    //projectStatusesQueryBuilder.where().eq("id", AppPreferences.getProjectStatusid(StatusUpdate.this));
                    projectStatusesQueryBuilder.where().eq("routeassignid", AppPreferences.getRouteSalesViewid(StatusUpdate.this));
                    PreparedQuery<ProjectStatuses> preparedQuery1 = projectStatusesQueryBuilder.prepare();
                    mprojectStatus = ProjectStatusesDao.query(preparedQuery1);
                }

                RuntimeExceptionDao<Priority, Integer> PriorityDao = mDbHelper.getPriorityRuntimeDao();
                mpriorities = PriorityDao.queryForAll();

                RuntimeExceptionDao<IssueTypes, Integer> IssueTypesDao = mDbHelper.getIssueTypesRuntimeDao();
                missuetypes = IssueTypesDao.queryForAll();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Log.d("projectstatus",""+mprojectStatus.size());
            status_sp_adapter = new Status_Sp_Adapter(StatusUpdate.this, mstatuses);
            sp_status.setAdapter(status_sp_adapter);

            remarks_sp_adapter = new Remarks_Sp_Adapter(StatusUpdate.this, mremarks);
            sp_remark.setAdapter(remarks_sp_adapter);

            review_sp_adapter=new Review_Sp_Adapter(StatusUpdate.this,mremarks);
            sp_review.setAdapter(review_sp_adapter);

            if(AppPreferences.getGroupId(StatusUpdate.this)==14||AppPreferences.getGroupId(StatusUpdate.this)==17||AppPreferences.getGroupId(StatusUpdate.this)==18){
                per_layout.setVisibility(View.VISIBLE);
                progress.dismiss();
            }
            if(AppPreferences.getGroupId(StatusUpdate.this)==29){
                per_layout.setVisibility(View.VISIBLE);
                bt_nav.setVisibility(View.GONE);
                progress.dismiss();
            }
            if(AppPreferences.getGroupId(StatusUpdate.this)==23||AppPreferences.getGroupId(StatusUpdate.this)==41||AppPreferences.getGroupId(StatusUpdate.this)==39){
                progress.dismiss();
                int remark=0;
                int status=0;
                try {
                    AppPreferences.setProjectStatusid(StatusUpdate.this,mprojectStatus.get(0).getId());
                    qa_actual = mprojectStatus.get(0).getActual();
                    remark=mprojectStatus.get(0).getRemark();
                    status=mprojectStatus.get(0).getStatusid();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for(int i=0;i<mremarks.size();i++){
                    int id=mremarks.get(i).getId();
                    if(id==remark){
                        sp_remark.setSelection(i+1);
                        break;
                    }
                }
                for(int j=0;j<mstatuses.size();j++){
                    int s_id=mstatuses.get(j).getId();
                    if(s_id==status){
                        sp_status.setSelection(j+1);
                        break;
                    }
                }
                actual.setText(new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(qa_actual))));

                sp_layout.setVisibility(View.VISIBLE);
                actual.setEnabled(false);
                sp_remark.setEnabled(false);
                sp_status.setEnabled(false);
                status_up_submit.setText("Update");
            }

        }
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        mDbHelper = null;
        super.onDestroy();
    }
}
