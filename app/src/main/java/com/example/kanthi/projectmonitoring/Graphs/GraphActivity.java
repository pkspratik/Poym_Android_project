package com.example.kanthi.projectmonitoring.Graphs;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.Project_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.ResourceList_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.TaskRemarks_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.ChangeReqViews;
import com.example.kanthi.projectmonitoring.PoJo.Priority;
import com.example.kanthi.projectmonitoring.PoJo.ProjectIssues;
import com.example.kanthi.projectmonitoring.PoJo.ProjectPercentages;
import com.example.kanthi.projectmonitoring.PoJo.ProjectProgress;
import com.example.kanthi.projectmonitoring.PoJo.ProjectResources;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRiskViews;
import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.ResourceProgress;
import com.example.kanthi.projectmonitoring.PoJo.TaskRemarkLinks;
import com.example.kanthi.projectmonitoring.PoJo.TaskResourceLinkViews;
import com.example.kanthi.projectmonitoring.PoJo.Zones;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraphActivity extends AppCompatActivity {
    Spinner sp_project, sp_charts;
    private TextView percentage_tv,variance_tv,issue_tv,resource_tv,risk_tv,change_tv;
    private LineChart percentage_lineChart;
    private BarChart variance_barChart,issues_bar, resources_bar;
    private HorizontalBarChart change_hbar, risk_hbar;
    private String[] chartlist = {"Select Chart", "Project Completion", "Variance Analysis", "Project Issues", "Resource Utilization","Project Risk","Project Change Request"};
    private ArrayAdapter<String> chart_adapter;
    private String chartvalue;

    private CoordinatorLayout coordinatorLayout;
    private List<Zones> mzones;
    private int user_id=0;
    private ProgressDialog progres;
    private List<ProjectPercentages> mpercentages;
    private List<ProjectProgress> mprojectProgress;
    private List<ProjectIssues> mprojectIssues;
    private List<ResourceProgress> mresourceProgress;
    private List<ProjectRiskViews> mprojectRiskViews;
    private List<ChangeReqViews> mchangeReqViews;

    private ArrayList<LinePojo> mlinedata;
    private Set<String> uniqueline;

    private ArrayList<MultiLinePojo> mMultiLinedata;
    private Set<String> uniqueMultiLine;

    private ArrayList<IssuesPojo> missues;
    private Set<String> uniqueissue_task;
    private Set<String> uniqueissue_imp;

    private ArrayList<ResourcePojo> mresource;
    private Set<String> uniqueres_taskname;
    private Set<String> uniqueres_resourcetype;
    private Set<String> uniqueres_resttype;

    private ArrayList<RiskPojo> mrisk;
    private Set<String> uniquerisk_probability;
    private Set<String> uniquerisk_impact;

    private ArrayList<CRPojo> mcrdata;
    private Set<String> uniquecr_category;
    private Set<String> uniquecr_priority;

    AvahanSqliteDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        sp_project = (Spinner) findViewById(R.id.sp_project);
        //sp_charts = (Spinner) findViewById(R.id.sp_chart);
        getSupportActionBar().setTitle("Project Dashboard");
        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        percentage_tv= (TextView) findViewById(R.id.percentage_line);
        variance_tv= (TextView) findViewById(R.id.variance_multiline);
        issue_tv= (TextView) findViewById(R.id.issue_bar);
        resource_tv= (TextView) findViewById(R.id.resource_bar);
        risk_tv= (TextView) findViewById(R.id.risk_hbar);
        change_tv= (TextView) findViewById(R.id.change_hbar);
        percentage_lineChart = (LineChart) findViewById(R.id.project_completion_piechart);
        variance_barChart = (BarChart) findViewById(R.id.variance_analysis_line_chart);
        issues_bar = (BarChart) findViewById(R.id.project_issues_barchart);
        resources_bar = (BarChart) findViewById(R.id.resources_barchart);
        change_hbar = (HorizontalBarChart) findViewById(R.id.change_horizontal_barchat);
        risk_hbar = (HorizontalBarChart) findViewById(R.id.risk_horizontal_barchat);
        progres = new ProgressDialog(GraphActivity.this);
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);

        try {
            RuntimeExceptionDao<Zones, Integer> zonesDao = mDbHelper.getZonesRuntimeDao();
            mzones=zonesDao.queryForAll();

            if (mzones.size() > 0) {
                Project_Sp_Adapter adapter = new Project_Sp_Adapter(GraphActivity.this, mzones);
                sp_project.setAdapter(adapter);
                sp_project.setSelection(1);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        percentage_tv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if(user_id!=0){
                    percentage_lineChart.setVisibility(View.VISIBLE);
                    variance_barChart.setVisibility(View.GONE);
                    issues_bar.setVisibility(View.GONE);
                    resources_bar.setVisibility(View.GONE);
                    change_hbar.setVisibility(View.GONE);
                    risk_hbar.setVisibility(View.GONE);
                    percentage_tv.setBackgroundColor(getResources().getColor(R.color.poym));
                    percentage_tv.setTextColor(getResources().getColor(R.color.white_color));
                    variance_tv.setBackgroundResource(R.drawable.textborder);
                    issue_tv.setBackgroundResource(R.drawable.textborder);
                    resource_tv.setBackgroundResource(R.drawable.textborder);
                    risk_tv.setBackgroundResource(R.drawable.textborder);
                    change_tv.setBackgroundResource(R.drawable.textborder);
                    variance_tv.setTextColor(getResources().getColor(R.color.poym));
                    issue_tv.setTextColor(getResources().getColor(R.color.poym));
                    resource_tv.setTextColor(getResources().getColor(R.color.poym));
                    risk_tv.setTextColor(getResources().getColor(R.color.poym));
                    change_tv.setTextColor(getResources().getColor(R.color.poym));
                    //networkpercentages();
                }else{
                    Toast.makeText(GraphActivity.this, "Plz,Select Project", Toast.LENGTH_SHORT).show();
                }
            }
        });
        variance_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id!=0){
                    percentage_lineChart.setVisibility(View.GONE);
                    variance_barChart.setVisibility(View.VISIBLE);
                    issues_bar.setVisibility(View.GONE);
                    resources_bar.setVisibility(View.GONE);
                    change_hbar.setVisibility(View.GONE);
                    risk_hbar.setVisibility(View.GONE);
                    percentage_tv.setBackgroundResource(R.drawable.textborder);
                    variance_tv.setBackgroundColor(getResources().getColor(R.color.poym));
                    variance_tv.setTextColor(getResources().getColor(R.color.white_color));
                    issue_tv.setBackgroundResource(R.drawable.textborder);
                    resource_tv.setBackgroundResource(R.drawable.textborder);
                    risk_tv.setBackgroundResource(R.drawable.textborder);
                    change_tv.setBackgroundResource(R.drawable.textborder);
                    percentage_tv.setTextColor(getResources().getColor(R.color.poym));
                    issue_tv.setTextColor(getResources().getColor(R.color.poym));
                    resource_tv.setTextColor(getResources().getColor(R.color.poym));
                    risk_tv.setTextColor(getResources().getColor(R.color.poym));
                    change_tv.setTextColor(getResources().getColor(R.color.poym));
                    //networkprojectprogress();
                }else{
                    Toast.makeText(GraphActivity.this, "Plz,Select Project", Toast.LENGTH_SHORT).show();
                }
            }
        });
        issue_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id!=0){
                    percentage_lineChart.setVisibility(View.GONE);
                    variance_barChart.setVisibility(View.GONE);
                    issues_bar.setVisibility(View.VISIBLE);
                    resources_bar.setVisibility(View.GONE);
                    change_hbar.setVisibility(View.GONE);
                    risk_hbar.setVisibility(View.GONE);
                    percentage_tv.setBackgroundResource(R.drawable.textborder);
                    variance_tv.setBackgroundResource(R.drawable.textborder);
                    issue_tv.setBackgroundColor(getResources().getColor(R.color.poym));
                    issue_tv.setTextColor(getResources().getColor(R.color.white_color));
                    resource_tv.setBackgroundResource(R.drawable.textborder);
                    risk_tv.setBackgroundResource(R.drawable.textborder);
                    change_tv.setBackgroundResource(R.drawable.textborder);
                    variance_tv.setTextColor(getResources().getColor(R.color.poym));
                    percentage_tv.setTextColor(getResources().getColor(R.color.poym));
                    resource_tv.setTextColor(getResources().getColor(R.color.poym));
                    risk_tv.setTextColor(getResources().getColor(R.color.poym));
                    change_tv.setTextColor(getResources().getColor(R.color.poym));
                    //networkprojectIssues();
                }else{
                    Toast.makeText(GraphActivity.this, "Plz,Select Project", Toast.LENGTH_SHORT).show();
                }
            }
        });
        resource_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id!=0){
                    percentage_lineChart.setVisibility(View.GONE);
                    variance_barChart.setVisibility(View.GONE);
                    issues_bar.setVisibility(View.GONE);
                    resources_bar.setVisibility(View.VISIBLE);
                    change_hbar.setVisibility(View.GONE);
                    risk_hbar.setVisibility(View.GONE);
                    percentage_tv.setBackgroundResource(R.drawable.textborder);
                    variance_tv.setBackgroundResource(R.drawable.textborder);
                    issue_tv.setBackgroundResource(R.drawable.textborder);
                    resource_tv.setBackgroundColor(getResources().getColor(R.color.poym));
                    resource_tv.setTextColor(getResources().getColor(R.color.white_color));
                    risk_tv.setBackgroundResource(R.drawable.textborder);
                    change_tv.setBackgroundResource(R.drawable.textborder);

                    variance_tv.setTextColor(getResources().getColor(R.color.poym));
                    issue_tv.setTextColor(getResources().getColor(R.color.poym));
                    percentage_tv.setTextColor(getResources().getColor(R.color.poym));
                    risk_tv.setTextColor(getResources().getColor(R.color.poym));
                    change_tv.setTextColor(getResources().getColor(R.color.poym));
                    //networkresourceprogress();
                }else{
                    Toast.makeText(GraphActivity.this, "Plz,Select Project", Toast.LENGTH_SHORT).show();
                }
            }
        });
        risk_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id!=0){
                    percentage_lineChart.setVisibility(View.GONE);
                    variance_barChart.setVisibility(View.GONE);
                    issues_bar.setVisibility(View.GONE);
                    resources_bar.setVisibility(View.GONE);
                    change_hbar.setVisibility(View.GONE);
                    risk_hbar.setVisibility(View.VISIBLE);
                    percentage_tv.setBackgroundResource(R.drawable.textborder);
                    variance_tv.setBackgroundResource(R.drawable.textborder);
                    issue_tv.setBackgroundResource(R.drawable.textborder);
                    resource_tv.setBackgroundResource(R.drawable.textborder);
                    risk_tv.setBackgroundColor(getResources().getColor(R.color.poym));
                    risk_tv.setTextColor(getResources().getColor(R.color.white_color));
                    change_tv.setBackgroundResource(R.drawable.textborder);

                    variance_tv.setTextColor(getResources().getColor(R.color.poym));
                    issue_tv.setTextColor(getResources().getColor(R.color.poym));
                    resource_tv.setTextColor(getResources().getColor(R.color.poym));
                    percentage_tv.setTextColor(getResources().getColor(R.color.poym));
                    change_tv.setTextColor(getResources().getColor(R.color.poym));
                    //networkprojectriskviews();
                }else{
                    Toast.makeText(GraphActivity.this, "Plz,Select Project", Toast.LENGTH_SHORT).show();
                }
            }
        });
        change_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id!=0){
                    percentage_lineChart.setVisibility(View.GONE);
                    variance_barChart.setVisibility(View.GONE);
                    issues_bar.setVisibility(View.GONE);
                    resources_bar.setVisibility(View.GONE);
                    change_hbar.setVisibility(View.VISIBLE);
                    risk_hbar.setVisibility(View.GONE);
                    percentage_tv.setBackgroundResource(R.drawable.textborder);
                    variance_tv.setBackgroundResource(R.drawable.textborder);
                    issue_tv.setBackgroundResource(R.drawable.textborder);
                    resource_tv.setBackgroundResource(R.drawable.textborder);
                    risk_tv.setBackgroundResource(R.drawable.textborder);
                    change_tv.setBackgroundColor(getResources().getColor(R.color.poym));
                    change_tv.setTextColor(getResources().getColor(R.color.white_color));

                    variance_tv.setTextColor(getResources().getColor(R.color.poym));
                    issue_tv.setTextColor(getResources().getColor(R.color.poym));
                    resource_tv.setTextColor(getResources().getColor(R.color.poym));
                    risk_tv.setTextColor(getResources().getColor(R.color.poym));
                    percentage_tv.setTextColor(getResources().getColor(R.color.poym));
                    //networkprojectchangerequest();
                }else{
                    Toast.makeText(GraphActivity.this, "Plz,Select Project", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getZones(AppPreferences.getUserId(GraphActivity.this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Zones> zones = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Zones>>() {
                        }.getType());
                progres.dismiss();
                mzones = zones;
                if (mzones.size() > 0) {
                    Project_Sp_Adapter adapter = new Project_Sp_Adapter(GraphActivity.this, mzones);
                    sp_project.setAdapter(adapter);
                    sp_project.setSelection(1);

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(GraphActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });*/
        sp_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    user_id = mzones.get(sp_project.getSelectedItemPosition() - 1).getId();
                    if(user_id!=0){
                        percentage_lineChart.setVisibility(View.VISIBLE);
                        variance_barChart.setVisibility(View.GONE);
                        issues_bar.setVisibility(View.GONE);
                        resources_bar.setVisibility(View.GONE);
                        change_hbar.setVisibility(View.GONE);
                        risk_hbar.setVisibility(View.GONE);
                        percentage_tv.setBackgroundColor(getResources().getColor(R.color.poym));
                        percentage_tv.setTextColor(getResources().getColor(R.color.white_color));
                        variance_tv.setBackgroundResource(R.drawable.textborder);
                        issue_tv.setBackgroundResource(R.drawable.textborder);
                        resource_tv.setBackgroundResource(R.drawable.textborder);
                        risk_tv.setBackgroundResource(R.drawable.textborder);
                        change_tv.setBackgroundResource(R.drawable.textborder);
                        variance_tv.setTextColor(getResources().getColor(R.color.poym));
                        issue_tv.setTextColor(getResources().getColor(R.color.poym));
                        resource_tv.setTextColor(getResources().getColor(R.color.poym));
                        risk_tv.setTextColor(getResources().getColor(R.color.poym));
                        change_tv.setTextColor(getResources().getColor(R.color.poym));
                        //networkpercentages();
                        progres.show();
                        new FetchDetailsFromDbTask().execute();
                    }
                    //Toast.makeText(GraphActivity.this, "" + user_id, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void networkpercentages() {
        progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getProjectPercentages(AppPreferences.getUserId(GraphActivity.this), user_id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectPercentages> projectPercentages = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectPercentages>>() {
                        }.getType());
                mpercentages = projectPercentages;
                //progres.dismiss();
                networkprojectprogress();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                CallingSnackbar();
                //Toast.makeText(GraphActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkprojectprogress() {
        //progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getProjectProgress(AppPreferences.getUserId(GraphActivity.this),user_id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectProgress> projectProgresses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectProgress>>() {
                        }.getType());
                mprojectProgress = projectProgresses;
                //progres.dismiss();
                networkprojectIssues();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                CallingSnackbar();
                //Toast.makeText(GraphActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkprojectIssues() {
        //progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getProjectIssues(AppPreferences.getUserId(GraphActivity.this),user_id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectIssues> projectIssues = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectIssues>>() {
                        }.getType());
                mprojectIssues = projectIssues;
                //progres.dismiss();
                networkresourceprogress();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                CallingSnackbar();
                //Toast.makeText(GraphActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkresourceprogress() {
        //progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getResourceProgress(AppPreferences.getUserId(GraphActivity.this),user_id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ResourceProgress> resourceProgresses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ResourceProgress>>() {
                        }.getType());
                mresourceProgress = resourceProgresses;
                //progres.dismiss();

                networkprojectriskviews();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                CallingSnackbar();
                //Toast.makeText(GraphActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkprojectriskviews() {
        //progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getProjectRiskViews(AppPreferences.getUserId(GraphActivity.this),user_id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectRiskViews> projectRiskViews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectRiskViews>>() {
                        }.getType());
                mprojectRiskViews = projectRiskViews;
                //progres.dismiss();
                Log.d("risk_size",""+mprojectRiskViews.size());
                networkprojectchangerequest();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                CallingSnackbar();
                //Toast.makeText(GraphActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkprojectchangerequest() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getChangeRequestViews(AppPreferences.getUserId(GraphActivity.this),user_id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ChangeReqViews> changeReqViews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ChangeReqViews>>() {
                        }.getType());
                mchangeReqViews = changeReqViews;
                progres.dismiss();
                if(mpercentages.size()>=0){
                    setLinechartdata();
                }
                if(mprojectProgress.size()>=0){
                    setMultilinechart();
                }
                if(mprojectIssues.size()>=0){
                    setBarProjectissue();
                }
                if(mresourceProgress.size()>=0){
                    setBarResource();
                }
                if(mprojectRiskViews.size()>=0){
                    setHorizontalBarRisk();
                }
                if(mchangeReqViews.size()>=0){
                    setHorizontalBarProjectCr();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                CallingSnackbar();
                //Toast.makeText(GraphActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLinechartdata() {
        mlinedata=new ArrayList<LinePojo>();
        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        mlinedata.clear();
        percentage_lineChart.invalidate();
        percentage_lineChart.clear();
        dataSets.clear();
        String[] xVals=null;

        uniqueline = new HashSet<String>();
        for(int i=0;i<mpercentages.size();i++){
            uniqueline.add(mpercentages.get(i).getTourtype());
        }
        int n=uniqueline.size();
        String array[]=new String[n];
        array=uniqueline.toArray(array);
        Log.d("Linevalues",""+ Arrays.toString(array));
        Log.d("linesize",""+array.length);
        for(int i=0;i<array.length;i++){
            LinePojo line=new LinePojo();
            line.setName(array[i]);
            mlinedata.add(line);
        }
        List<Entry> yVals1 = new ArrayList<>();
        for(int j=0;j<mpercentages.size();j++){
            for(int k=0;k<mlinedata.size();k++){
                if(mpercentages.get(j).getTotpercentage()!=null){
                    if(mpercentages.get(j).getTourtype().equalsIgnoreCase(mlinedata.get(k).getName())){
                        mlinedata.get(k).setValue(mlinedata.get(k).getValue()+mpercentages.get(j).getTotpercentage());
                    }
                }
            }
        }
        String[] names = new String[mlinedata.size()];
        int count = 0;
        for(int l=0;l<mlinedata.size();l++){
            names[count] = mlinedata.get(count).getName();
            yVals1.add(new Entry(l,mlinedata.get(l).getValue()));
            Log.d("line_names", mlinedata.get(count).getName());
            count++;
        }
        LineDataSet set=new LineDataSet(yVals1,"");
        set.setLineWidth(3f);
        set.setColor(ContextCompat.getColor(GraphActivity.this, R.color.chartyellow));
        dataSets.add(set);

        percentage_lineChart.setData(new LineData(dataSets));

        xVals =names;
        Log.d("line_datasize",""+names.length);
        XAxis axis=percentage_lineChart.getXAxis();
        axis.setValueFormatter(new LineChartValueFormator(xVals));
        axis.setGranularity(1f);
        axis.setDrawGridLines(false);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }
    private void setMultilinechart(){
        int count=1;
        int count1=0;
        mMultiLinedata=new ArrayList<MultiLinePojo>();
        mMultiLinedata.clear();
        variance_barChart.clear();
        variance_barChart.invalidate();

        uniqueMultiLine = new HashSet<String>();
        for(int i=0;i<mprojectProgress.size();i++){
            uniqueMultiLine.add(mprojectProgress.get(i).getTaskname());
        }
        int n=uniqueMultiLine.size();
        String array[]=new String[n];
        array=uniqueMultiLine.toArray(array);
        Log.d("MultiLinevalues",""+ Arrays.toString(array));
        Log.d("multilinesize",""+array.length);
        for(int i=0;i<array.length;i++){
            MultiLinePojo line=new MultiLinePojo();
            line.setName(array[i]);
            mMultiLinedata.add(line);
        }
        List<BarEntry> data=new ArrayList<>();
        List<BarEntry> data1=new ArrayList<>();
        for(int j=0;j<mprojectProgress.size();j++){
            for(int k=0;k<mMultiLinedata.size();k++){
                if(mprojectProgress.get(j).getTotaltarget() != null){
                    if(mprojectProgress.get(j).getTaskname().equalsIgnoreCase(mMultiLinedata.get(k).getName())){
                        if(mprojectProgress.get(j).getWorktypename().equalsIgnoreCase("Actual")){
                            mMultiLinedata.get(k).setActualvalue(mMultiLinedata.get(k).getActualvalue()+mprojectProgress.get(j).getTotaltarget());
                        }else if(mprojectProgress.get(j).getWorktypename().equalsIgnoreCase("Target")){
                            mMultiLinedata.get(k).setTargetValue(mMultiLinedata.get(k).getTargetValue()+mprojectProgress.get(j).getTotaltarget());
                        }
                    }
                }
            }
        }
        //ArrayList<String> labels=new ArrayList<String>();
        String[] names = new String[mlinedata.size()];
        int inc = 0;
        for(int l=0;l<mMultiLinedata.size();l++){
            data.add(new BarEntry(count1,mMultiLinedata.get(l).getActualvalue()));
            names[inc]=mMultiLinedata.get(inc).getName();
            count1+=2;
        }
        for(int m=0;m<mMultiLinedata.size();m++){
            data1.add(new BarEntry(count,mMultiLinedata.get(m).getTargetValue()));
            count+=2;
        }
        BarDataSet set=new BarDataSet(data,"Actual");
        set.setColor(ContextCompat.getColor(GraphActivity.this, R.color.chartgreen));

        BarDataSet set1=new BarDataSet(data1,"Target");
        set1.setColor(ContextCompat.getColor(GraphActivity.this, R.color.chartblue));
        variance_barChart.setData(new BarData(set,set1));
        variance_barChart.notifyDataSetChanged();

        String[] xVals =array;
        Log.e("multiline_datasize",""+names.length);
        XAxis axis=variance_barChart.getXAxis();
        axis.setValueFormatter(new MultilineChartValueFormator(xVals));
        axis.setGranularity(1f);
        axis.setDrawGridLines(false);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);

    }
    private void setBarProjectissue() {
        uniqueissue_task = new HashSet<String>();
        uniqueissue_imp=new HashSet<>();
        for(int i=0;i<mprojectIssues.size();i++){
            uniqueissue_task.add(mprojectIssues.get(i).getTask());
            uniqueissue_imp.add(mprojectIssues.get(i).getImportance());
        }
        int m=uniqueissue_task.size();
        String task_array[]=new String[m];
        task_array=uniqueissue_task.toArray(task_array);
        Log.d("issuetaskvalues",""+ Arrays.toString(task_array));

        int n=uniqueissue_imp.size();
        String imp_array[]=new String[n];
        imp_array=uniqueissue_task.toArray(imp_array);
        Log.d("issueimpvalues",""+ Arrays.toString(imp_array));

        for(int i=0;i<task_array.length;i++){
            int importance=0;
            for(int j=0;j<imp_array.length;j++){
                importance=importance+1;
            }
        }
        /*missues=new ArrayList<>();
        for(int i=0;i<task_array.length;i++){
            IssuesPojo issuesPojo=new IssuesPojo();
            issuesPojo.setTask(task_array[i]);
            missues.add(issuesPojo);
        }
        for(int j=0;j<imp_array.length;j++){
            IssuesPojo issuesPojo=new IssuesPojo();
            issuesPojo.setImportance(imp_array[j]);
            missues.add(issuesPojo);
        }

        ArrayList<BarEntry> open_array = new ArrayList<BarEntry>();
        ArrayList<BarEntry> close_array = new ArrayList<BarEntry>();
        for(int k=0;k<mprojectIssues.size();k++){
            int open=0,close=0;
            for(int l=0;l<missues.size();l++){
                if(mprojectIssues.get(k).getTask().equalsIgnoreCase(missues.get(l).getTask())){
                    if(mprojectIssues.get(k).getImportance().equalsIgnoreCase(missues.get(l).getImportance())){
                        if(mprojectIssues.get(k).getClosedflag()==true){
                            //missues.get(l).setClose(missues.get(l).getClose()+mprojectIssues.get(k).getTrueflag());
                            close=close+mprojectIssues.get(k).getTrueflag();
                        }else{
                            //missues.get(l).setOpen(missues.get(l).getOpen()+mprojectIssues.get(k).getTrueflag());
                            open=open+mprojectIssues.get(k).getTrueflag();
                        }
                    }
                }
            }
            //open_array.add(new BarEntry(0,new float[]{Low_tnd,Medium_tnd,High_tnd}));
            //close_array.add(new BarEntry(1,new float[]{Low_flag_tnd,Medium_flag_tnd,High_flag_tnd}));
        }*/

        /*int Low_tnd = 0, Medium_tnd = 0, High_tnd = 0;
        int Low_blow = 0, Medium_blow = 0, High_blow = 0;
        int Low_splic = 0, Medium_spilc = 0, High_splic = 0;
        int Low_sur = 0, Medium_sur = 0, High_sur = 0;

        int Low_flag_tnd = 0, Medium_flag_tnd = 0, High_flag_tnd = 0;
        int Low_flag_blow = 0, Medium_flag_blow = 0, High_flag_blow = 0;
        int Low_flag_splic = 0, Medium_flag_spilc = 0, High_flag_splic = 0;
        int Low_flag_sur = 0, Medium_flag_sur = 0, High_flag_sur = 0;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yValsflag1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yValsflag2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yValsflag3 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals4 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yValsflag4 = new ArrayList<BarEntry>();

        for(int i=0;i<mprojectIssues.size();i++){
            if(mprojectIssues.get(i).getTask().equalsIgnoreCase("T & D") &&mprojectIssues.get(i).getImportance().equalsIgnoreCase("Low")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    Low_flag_tnd=Low_flag_tnd+mprojectIssues.get(i).getTrueflag();
                }else{
                    Low_tnd=Low_tnd+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("OFC Blow")&&mprojectIssues.get(i).getImportance().equalsIgnoreCase("Low")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    Low_flag_blow=Low_flag_blow+mprojectIssues.get(i).getTrueflag();
                }else{
                    Low_blow=Low_blow+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("Splic & Term") &&mprojectIssues.get(i).getImportance().equalsIgnoreCase("Low")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    Low_flag_splic=Low_flag_splic+mprojectIssues.get(i).getTrueflag();
                }else{
                    Low_splic=Low_splic+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("T & D") &&mprojectIssues.get(i).getImportance().equalsIgnoreCase("Medium")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    Medium_flag_tnd=Medium_flag_tnd+mprojectIssues.get(i).getTrueflag();
                }else{
                    Medium_tnd=Medium_tnd+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("OFC Blow")&&mprojectIssues.get(i).getImportance().equalsIgnoreCase("Medium")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    Medium_flag_blow=Medium_flag_blow+mprojectIssues.get(i).getTrueflag();
                }else{
                    Medium_blow=Medium_blow+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("Splic & Term") &&mprojectIssues.get(i).getImportance().equalsIgnoreCase("Medium")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    Medium_flag_spilc=Medium_flag_spilc+mprojectIssues.get(i).getTrueflag();
                }else{
                    Medium_spilc=Medium_spilc+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("T & D") &&mprojectIssues.get(i).getImportance().equalsIgnoreCase("high")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    High_flag_tnd=High_flag_tnd+mprojectIssues.get(i).getTrueflag();
                }else{
                    High_tnd=High_tnd+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("OFC Blow")&&mprojectIssues.get(i).getImportance().equalsIgnoreCase("high")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    High_flag_blow=High_flag_blow+mprojectIssues.get(i).getTrueflag();
                }else{
                    High_blow=High_blow+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("Splic & Term")&&mprojectIssues.get(i).getImportance().equalsIgnoreCase("high")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    High_flag_splic=High_flag_splic+mprojectIssues.get(i).getTrueflag();
                }else{
                    High_splic=High_splic+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("Survey") &&mprojectIssues.get(i).getImportance().equalsIgnoreCase("Low")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    Low_flag_sur=Low_flag_sur+mprojectIssues.get(i).getTrueflag();
                }else{
                    Low_sur=Low_sur+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("Survey")&&mprojectIssues.get(i).getImportance().equalsIgnoreCase("Medium")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    Medium_flag_sur=Medium_flag_sur+mprojectIssues.get(i).getTrueflag();
                }else{
                    Medium_sur=Medium_sur+mprojectIssues.get(i).getTrueflag();
                }
            }else if(mprojectIssues.get(i).getTask().equalsIgnoreCase("Survey")&&mprojectIssues.get(i).getImportance().equalsIgnoreCase("high")){
                if(mprojectIssues.get(i).getClosedflag()==true){
                    High_flag_sur=High_flag_sur+mprojectIssues.get(i).getTrueflag();
                }else{
                    High_sur=High_sur+mprojectIssues.get(i).getTrueflag();
                }
            }
        }
        yVals1.add(new BarEntry(0,new float[]{Low_tnd,Medium_tnd,High_tnd}));
        yValsflag1.add(new BarEntry(1,new float[]{Low_flag_tnd,Medium_flag_tnd,High_flag_tnd}));

        yVals2.add(new BarEntry(2,new float[]{Low_blow,Medium_blow,High_blow}));
        yValsflag2.add(new BarEntry(3,new float[]{Low_flag_blow,Medium_flag_blow,High_flag_blow}));

        yVals3.add(new BarEntry(4,new float[]{Low_splic,Medium_spilc,High_splic}));
        yValsflag3.add(new BarEntry(5,new float[]{Low_flag_splic,Medium_flag_spilc,High_flag_splic}));

        yVals4.add(new BarEntry(6,new float[]{Low_sur,Medium_sur,High_sur}));
        yValsflag4.add(new BarEntry(7,new float[]{Low_flag_sur,Medium_flag_sur,High_flag_sur}));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartblue));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartbrown));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgray));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartorange));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgreen));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartred));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartviolet));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartyellow));

        BarDataSet set1 = new BarDataSet(yVals1, null);
        set1.setColors(colors);
        set1.setStackLabels(new String[]{"Low","Medium","High"});

        BarDataSet setflag1 = new BarDataSet(yValsflag1, null);
        setflag1.setColors(colors);
        setflag1.setStackLabels(new String[]{"","",""});

        BarDataSet set2= new BarDataSet(yVals2,null);
        set2.setColors(colors);
        set2.setStackLabels(new String[]{"","",""});

        BarDataSet setflag2 = new BarDataSet(yValsflag2, null);
        setflag2.setColors(colors);
        setflag2.setStackLabels(new String[]{"","",""});

        BarDataSet set3= new BarDataSet(yVals3,null);
        set3.setColors(colors);
        set3.setStackLabels(new String[]{"","",""});

        BarDataSet setflag3 = new BarDataSet(yValsflag3, null);
        setflag3.setColors(colors);
        setflag3.setStackLabels(new String[]{"","",""});

        BarDataSet set4= new BarDataSet(yVals4,null);
        set4.setColors(colors);
        set4.setStackLabels(new String[]{"","",""});

        BarDataSet setflag4 = new BarDataSet(yValsflag4, null);
        setflag4.setColors(colors);
        setflag4.setStackLabels(new String[]{"","",""});

        String[] xVals ={"T & D","","OFC Blow","","Splic & Term","","Survey",""};
        XAxis axis=issues_bar.getXAxis();
        axis.setValueFormatter(new ChartValueFormatter(xVals));
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setDrawGridLines(false);

        BarData data = new BarData(set1,setflag1,set2,setflag2,set3,setflag3,set4,setflag4);
        data.setValueFormatter(new ChartValueFormatter());
        issues_bar.setBorderWidth(0.5f);
        issues_bar.setTouchEnabled(true);
        issues_bar.invalidate(); // refresh
        issues_bar.setData(data);
        issues_bar.setFitBars(true);*/
    }
    private void setBarResource() {
        /*uniqueres_taskname=new HashSet<>();
        uniqueres_resourcetype=new HashSet<>();
        uniqueres_resttype=new HashSet<>();
        for(int i=0;i<mresourceProgress.size();i++){
            uniqueres_taskname.add(mresourceProgress.get(i).getTaskname());
            uniqueres_resourcetype.add(mresourceProgress.get(i).getResourcetype());
            uniqueres_resttype.add(mresourceProgress.get(i).getRestype());
        }

        int m=uniqueres_taskname.size();
        String task_array[]=new String[m];
        task_array=uniqueres_taskname.toArray(task_array);
        Log.d("resourcetaskvalues",""+ Arrays.toString(task_array));

        int n=uniqueres_resourcetype.size();
        String resourcetype_array[]=new String[n];
        resourcetype_array=uniqueres_resourcetype.toArray(resourcetype_array);
        Log.d("resourcetypevalues",""+ Arrays.toString(resourcetype_array));

        int o=uniqueres_resttype.size();
        String rest_type_array[]=new String[o];
        rest_type_array=uniqueres_resttype.toArray(rest_type_array);
        Log.d("rest_typevalues",""+ Arrays.toString(rest_type_array));

        ArrayList<BarEntry> projected_array = new ArrayList<BarEntry>();
        ArrayList<BarEntry> utilized_array = new ArrayList<BarEntry>();

        List<Float> temp_projected = new ArrayList<>();
        List<Float> temp_Utilized = new ArrayList<>();
        List<IBarDataSet> dataSets = new ArrayList<>();
        int counter=-1;
        for(int i=0;i<task_array.length;i++){
            for(int j=0;j<rest_type_array.length;j++){
                float projected=0,utiliized=0;
                for(int k=0;k<mresourceProgress.size();k++){
                    if (mresourceProgress.get(k).getResourcetype().equalsIgnoreCase("Projected")&mresourceProgress.get(k).getTaskname().equalsIgnoreCase(task_array[i])
                            &mresourceProgress.get(k).getRestype().equalsIgnoreCase(rest_type_array[j])) {
                        projected=projected+1;
                    } else if (mresourceProgress.get(k).getResourcetype().equalsIgnoreCase("Utilised")&mresourceProgress.get(k).getTaskname().equalsIgnoreCase(task_array[i])
                            &mresourceProgress.get(k).getRestype().equalsIgnoreCase(rest_type_array[j])){
                        utiliized=utiliized+1;
                    }
                }
                temp_projected.add(projected);
                temp_Utilized.add(utiliized);
            }

            float[] floatprojectedArray = new float[temp_projected.size()];
            float[] floatUtilizedArray = new float[temp_Utilized.size()];
            int fpa = 0,fua=0;

            for (Float f : temp_projected) {
                floatprojectedArray[fpa++] = (f != null ? f : Float.NaN); // Or whatever default you want.
            }

            for (Float f : temp_Utilized) {
                floatUtilizedArray[fua++] = (f != null ? f : Float.NaN); // Or whatever default you want.
            }

            counter=counter+1;
            projected_array.add(new BarEntry(counter,floatprojectedArray));
            counter=counter+1;
            utilized_array.add(new BarEntry(counter,floatUtilizedArray));

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartblue));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgray));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartorange));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgreen));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartred));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartviolet));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartyellow));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartbrown));

            BarDataSet set1 = new BarDataSet(projected_array, null);
            set1.setColors(colors);
            set1.setDrawIcons(false);
            set1.setStackLabels(rest_type_array);

            BarDataSet set2 = new BarDataSet(utilized_array, null);
            set2.setColors(colors);
            set2.setDrawIcons(false);
            set2.setStackLabels(rest_type_array);

            dataSets.add(set1);
            dataSets.add(set2);
        }

        ArrayList<String> task_arrayList = new ArrayList<>();
        for(String s : task_array){
            task_arrayList.add(s);
            task_arrayList.add("");
        }

        String[] stringTask_array = new String[task_arrayList.size()];
        int sta = 0;

        for (String f : task_arrayList) {
            stringTask_array[sta++] = (f != null ? f : ""); // Or whatever default you want.
        }


        Log.e("task_array",String.valueOf(stringTask_array.length));
        Log.e("dataSets",String.valueOf(dataSets.size()));

        String[] xVals = stringTask_array;
        XAxis axis=resources_bar.getXAxis();
        axis.setValueFormatter(new ChartValueFormatter(xVals));
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setDrawGridLines(false);

        BarData data = new BarData(dataSets);
        data.setValueFormatter(new ChartValueFormatter());
        resources_bar.setBorderWidth(0.5f);
        resources_bar.setTouchEnabled(true);
        resources_bar.invalidate(); // refresh
        resources_bar.setData(data);
        resources_bar.setFitBars(true);*/
        /*mresource=new ArrayList<>();
        for(int i=0;i<task_array.length;i++){
            ResourcePojo resourcePojo=new ResourcePojo();
            resourcePojo.setTaskname(task_array[i]);
            mresource.add(resourcePojo);
        }
        for(int j=0;j<resourcetype_array.length;j++){
            ResourcePojo resourcePojo=new ResourcePojo();
            resourcePojo.setResourcetype(resourcetype_array[j]);
            mresource.add(resourcePojo);
        }
        for(int k=0;k<rest_type_array.length;k++){
            ResourcePojo resourcePojo=new ResourcePojo();
            resourcePojo.setResttype(rest_type_array[k]);
            mresource.add(resourcePojo);
        }
        for(int i=0;i<mresourceProgress.size();i++){
            for(int j=0;j<mresource.size();j++){
                if(mresourceProgress.get(i).getResource()!=null){
                    if(mresourceProgress.get(i).getTaskname().equalsIgnoreCase(mresource.get(j).getTaskname())){
                        if(mresourceProgress.get(i).getRestype().equalsIgnoreCase(mresource.get(j).getResttype())){
                            if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected")) {
                                mresource.get(j).setProjected_value(mresource.get(j).getProjected_value()+ Integer.parseInt(mresourceProgress.get(i).getResource()));
                            } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised")){
                                mresource.get(j).setUtilized_value(mresource.get(j).getUtilized_value()+ Integer.parseInt(mresourceProgress.get(i).getResource()));
                            }
                        }
                    }
                }
            }
        }*/

        ArrayList<BarEntry> pro1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> uti1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> pro2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> uti2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> pro3 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> uti3 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> pro4 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> uti4 = new ArrayList<BarEntry>();

        int tnd_tech=0,tnd_cable=0,tnd_iron=0,tnd_rest=0,tnd_labour=0,tnd_hdd=0,tnd_drill=0;
        int dit_tech=0,dit_cable=0,dit_iron=0,dit_rest=0,dit_labour=0,dit_hdd=0,dit_drill=0;
        int ofc_tech=0,ofc_cable=0,ofc_iron=0,ofc_rest=0,ofc_labour=0,ofc_hdd=0,ofc_drill=0;
        int spill_tech=0,spill_cable=0,spill_iron=0,spill_rest=0,spill_labour=0,spill_hdd=0,spill_drill=0;

        int tnd_uti_tech=0,tnd_uti_cable=0,tnd_uti_iron=0,tnd_uti_rest=0,tnd_uti_labour=0,tnd_uti_hdd=0,tnd_uti_drill=0;
        int dit_uti_tech=0,dit_uti_cable=0,dit_uti_iron=0,dit_uti_rest=0,dit_uti_labour=0,dit_uti_hdd=0,dit_uti_drill=0;
        int ofc_uti_tech=0,ofc_uti_cable=0,ofc_uti_iron=0,ofc_uti_rest=0,ofc_uti_labour=0,ofc_uti_hdd=0,ofc_uti_drill=0;
        int spill_uti_tech=0,spill_uti_cable=0,spill_uti_iron=0,spill_uti_rest=0,spill_uti_labour=0,spill_uti_hdd=0,spill_uti_drill=0;

        for(int i=0;i<mresourceProgress.size();i++) {
            if (mresourceProgress.get(i).getTaskname().equalsIgnoreCase("T & D")&& mresourceProgress.get(i).getResource()!=null) {
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Technician")) {
                    tnd_tech = tnd_tech + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Technician")){
                    tnd_uti_tech = tnd_uti_tech + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Cable")) {
                    tnd_cable = tnd_cable + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Cable")){
                    tnd_uti_cable = tnd_uti_cable + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("iron1")) {
                    tnd_iron = tnd_iron + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("iron1")) {
                    tnd_uti_iron = tnd_uti_iron + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("WRest34")) {
                    tnd_rest = tnd_rest + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("WRest34")) {
                    tnd_uti_rest = tnd_uti_rest + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Labour")) {
                    tnd_labour = tnd_labour + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Labour")){
                    tnd_uti_labour = tnd_uti_labour + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("HDD Machine")) {
                    tnd_hdd = tnd_hdd + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("HDD Machine")) {
                    tnd_uti_hdd = tnd_uti_hdd + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Drill Machine")) {
                    tnd_drill = tnd_drill + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Drill Machine")){
                    tnd_uti_drill = tnd_uti_drill + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
            } else if (mresourceProgress.get(i).getTaskname().equalsIgnoreCase("DIT")&& mresourceProgress.get(i).getResource()!=null) {
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Technician")) {
                    dit_tech = dit_tech + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Technician")) {
                    dit_uti_tech = dit_uti_tech + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Cable")) {
                    dit_cable = dit_cable + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Cable")){
                    dit_uti_cable = dit_uti_cable + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("iron1")) {
                    dit_iron = dit_iron + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("iron1")){
                    dit_uti_iron = dit_uti_iron + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("WRest34")) {
                    dit_rest = dit_rest + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("WRest34")) {
                    dit_uti_rest = dit_uti_rest + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Labour")) {
                    dit_labour = dit_labour + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Labour")){
                    dit_uti_labour = dit_uti_labour + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("HDD Machine")) {
                    dit_hdd = dit_hdd + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("HDD Machine")) {
                    dit_uti_hdd = dit_uti_hdd + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Drill Machine")) {
                    dit_drill = dit_drill + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Drill Machine")) {
                    dit_uti_drill = dit_uti_drill + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
            } else if (mresourceProgress.get(i).getTaskname().equalsIgnoreCase("OFC Blow")&& mresourceProgress.get(i).getResource()!=null) {
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Technician")) {
                    ofc_tech = ofc_tech + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Technician")){
                    ofc_uti_tech = ofc_uti_tech + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Cable")) {
                    ofc_cable = ofc_cable + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Cable")){
                    ofc_uti_cable = ofc_uti_cable + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("iron1")) {
                    ofc_iron = ofc_iron + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("iron1")) {
                    ofc_uti_iron = ofc_uti_iron + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("WRest34")) {
                    ofc_rest = ofc_rest + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("WRest34")){
                    ofc_uti_rest = ofc_uti_rest + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Labour")) {
                    ofc_labour = ofc_labour + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Labour")){
                    ofc_uti_labour = ofc_uti_labour + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("HDD Machine")) {
                    ofc_hdd = ofc_hdd + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("HDD Machine")){
                    ofc_uti_hdd = ofc_uti_hdd + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Drill Machine")) {
                    ofc_drill = ofc_drill + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Drill Machine")){
                    ofc_uti_drill = ofc_uti_drill + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
            } else if (mresourceProgress.get(i).getTaskname().equalsIgnoreCase("Splic & Term")&& mresourceProgress.get(i).getResource()!=null) {
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Technician")) {
                    spill_tech = spill_tech + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Technician")){
                    spill_uti_tech = spill_uti_tech + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Cable")) {
                    spill_cable = spill_cable + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Cable")){
                    spill_uti_cable = spill_uti_cable + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("iron1")) {
                    spill_iron = spill_iron + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("iron1")) {
                    spill_uti_iron = spill_uti_iron + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("WRest34")) {
                    spill_rest = spill_rest + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("WRest34")){
                    spill_uti_rest = spill_uti_rest + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Labour")) {
                    spill_labour = spill_labour + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Labour")) {
                    spill_uti_labour = spill_uti_labour + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("HDD Machine")) {
                    spill_hdd = spill_hdd + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("HDD Machine")){
                    spill_uti_hdd = spill_uti_hdd + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
                if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Projected") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Drill Machine")) {
                    spill_drill = spill_drill + Integer.parseInt(mresourceProgress.get(i).getResource());
                } else if (mresourceProgress.get(i).getResourcetype().equalsIgnoreCase("Utilised") && mresourceProgress.get(i).getRestype().equalsIgnoreCase("Drill Machine")){
                    spill_uti_drill = spill_uti_drill + Integer.parseInt(mresourceProgress.get(i).getResource());
                }
            }
        }
        pro1.add(new BarEntry(0,new float[]{tnd_tech,tnd_cable,tnd_iron,tnd_rest,tnd_labour,tnd_hdd,tnd_drill}));
        uti1.add(new BarEntry(1,new float[]{tnd_uti_tech,tnd_uti_cable,tnd_uti_iron,tnd_uti_rest,tnd_uti_labour,tnd_uti_hdd,tnd_uti_drill}));

        pro2.add(new BarEntry(2,new float[]{dit_tech,dit_cable,dit_iron,dit_rest,dit_labour,dit_hdd,dit_drill}));
        uti2.add(new BarEntry(3,new float[]{dit_uti_tech,dit_uti_cable,dit_uti_iron,dit_uti_rest,dit_uti_labour,dit_uti_hdd,dit_uti_drill}));

        pro3.add(new BarEntry(4,new float[]{ofc_tech,ofc_cable,ofc_iron,ofc_rest,ofc_labour,ofc_hdd,ofc_drill}));
        uti3.add(new BarEntry(5,new float[]{ofc_uti_tech,ofc_uti_cable,ofc_uti_iron,ofc_uti_rest,ofc_uti_labour,ofc_uti_hdd,ofc_uti_drill}));

        pro4.add(new BarEntry(6,new float[]{spill_tech,spill_cable,spill_iron,spill_rest,spill_labour,spill_hdd,spill_drill}));
        uti4.add(new BarEntry(7,new float[]{spill_uti_tech,spill_uti_cable,spill_uti_iron,spill_uti_rest,spill_uti_labour,spill_uti_hdd,spill_uti_drill}));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartblue));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgray));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartorange));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgreen));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartred));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartviolet));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartyellow));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartbrown));

        BarDataSet set1 = new BarDataSet(pro1, null);
        set1.setColors(colors);
        set1.setDrawIcons(false);
        set1.setStackLabels(new String[]{"Test Mc","Technician","HDD Machine","Cable","Labour","WRest34","iron1"});

        BarDataSet uti_set1 = new BarDataSet(uti1, null);
        uti_set1.setColors(colors);
        uti_set1.setDrawIcons(false);
        uti_set1.setStackLabels(new String[]{"","","","","","",""});

        BarDataSet set2 = new BarDataSet(pro2, null);
        set2.setColors(colors);
        set2.setDrawIcons(false);
        set2.setStackLabels(new String[]{"","","","","","",""});

        BarDataSet uti_set2 = new BarDataSet(uti2, null);
        uti_set2.setColors(colors);
        uti_set2.setDrawIcons(false);
        uti_set2.setStackLabels(new String[]{"","","","","","",""});

        BarDataSet set3 = new BarDataSet(pro3, null);
        set3.setColors(colors);
        set3.setDrawIcons(false);
        set3.setStackLabels(new String[]{"","","","","","",""});

        BarDataSet uti_set3 = new BarDataSet(uti3, null);
        uti_set3.setColors(colors);
        uti_set3.setDrawIcons(false);
        uti_set3.setStackLabels(new String[]{"","","","","","",""});

        BarDataSet set4 = new BarDataSet(pro4, null);
        set4.setColors(colors);
        set4.setDrawIcons(false);
        set4.setStackLabels(new String[]{"","","","","","",""});

        BarDataSet uti_set4 = new BarDataSet(uti4, null);
        uti_set4.setColors(colors);
        uti_set4.setDrawIcons(false);
        uti_set4.setStackLabels(new String[]{"","","","","","",""});

        String[] xVals ={"T & D","","DIT","","OFC Blow","","Splic & Term",""};
        XAxis axis=resources_bar.getXAxis();
        axis.setValueFormatter(new ChartValueFormatter(xVals));
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setDrawGridLines(false);

        BarData data = new BarData(set1,uti_set1,set2,uti_set2,set3,uti_set3,set4,uti_set4);
        data.setValueFormatter(new ChartValueFormatter());
        resources_bar.setBorderWidth(0.5f);
        resources_bar.setTouchEnabled(true);
        resources_bar.invalidate(); // refresh
        resources_bar.setData(data);
        resources_bar.setFitBars(true);
    }
    private void setHorizontalBarRisk() {
        uniquerisk_probability=new HashSet<>();
        uniquerisk_impact=new HashSet<>();
        for(int i=0;i<mprojectRiskViews.size();i++){
            uniquerisk_probability.add(mprojectRiskViews.get(i).getProbability());
            uniquerisk_impact.add(mprojectRiskViews.get(i).getImpact());
        }
        int m=uniquerisk_probability.size();
        String risk_probability[]=new String[m];
        risk_probability=uniquerisk_probability.toArray(risk_probability);
        Log.d("riskvalues",""+ Arrays.toString(risk_probability));

        int n=uniquerisk_impact.size();
        String risk_impact[]=new String[n];
        risk_impact=uniquerisk_impact.toArray(risk_impact);
        Log.d("riskvalues",""+ Arrays.toString(risk_impact));

        for(int i=0;i<mprojectRiskViews.size();i++) {
            if(mprojectRiskViews.get(i).getStatus()!=null&& mprojectRiskViews.get(i).getImpact() != null){
                if (mprojectRiskViews.get(i).getStatus().equalsIgnoreCase("Reviewed")) {
                    for(int j=0;j<risk_impact.length;j++){
                        int probabiility=0;
                        for(int k=0;k<risk_probability.length;k++){
                            probabiility=probabiility+1;
                        }
                    }
                }
            }
        }

        /*mrisk=new ArrayList<>();
        for(int i=0;i<risk_probability.length;i++){
            RiskPojo riskPojo=new RiskPojo();
            riskPojo.setProbability(risk_probability[i]);
            mrisk.add(riskPojo);
        }
        for(int j=0;j<risk_impact.length;j++){
            RiskPojo riskPojo=new RiskPojo();
            riskPojo.setImpact(risk_impact[j]);
            mrisk.add(riskPojo);
        }
        for(int i=0;i<mprojectRiskViews.size();i++){
            for(int j=0;j<mrisk.size();j++){
                if(mprojectRiskViews.get(i).getImpact()!=null){
                    if(mprojectRiskViews.get(i).getImpact().equalsIgnoreCase(mrisk.get(j).getImpact())){
                        if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase(mrisk.get(j).getProbability())){
                            mrisk.get(j).setValue(mrisk.get(j).getValue()+1);
                        }
                    }
                }
            }
        }*/
        /*int insig_rare=0,insig_unlike=0,insig_poss=0,insig_like=0,insig_almost=0;
        int maj_rare=0,maj_unlike=0,maj_poss=0,maj_like=0,maj_almost=0;
        int cat_rare=0,cat_unlike=0,cat_poss=0,cat_like=0,cat_almost=0;
        int mod_rare=0,mod_unlike=0,mod_poss=0,mod_like=0,mod_almost=0;
        int min_rare=0,min_unlike=0,min_poss=0,min_like=0,min_almost=0;
        int imp_rare=0,imp_unlike=0,imp_poss=0,imp_like=0,imp_almost=0;

        ArrayList<BarEntry> insig = new ArrayList<BarEntry>();
        ArrayList<BarEntry> major = new ArrayList<BarEntry>();
        ArrayList<BarEntry> cat = new ArrayList<BarEntry>();
        ArrayList<BarEntry> moderate = new ArrayList<BarEntry>();
        ArrayList<BarEntry> minor = new ArrayList<BarEntry>();
        ArrayList<BarEntry> impact = new ArrayList<BarEntry>();
        for(int i=0;i<mprojectRiskViews.size();i++){
            if(mprojectRiskViews.get(i).getStatus().equalsIgnoreCase("Reviewed") && mprojectRiskViews.get(i).getImpact()!=null){
                if(mprojectRiskViews.get(i).getImpact().equalsIgnoreCase("Insignificant")){
                    if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Rare (0- 20%)")){
                        insig_rare=insig_rare+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Unlikely (20 - 40%)")){
                        insig_unlike=insig_unlike+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Possible (40 - 60%")){
                        insig_poss=insig_poss+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Likely (60 - 80%)")){
                        insig_like=insig_like+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Almost Certain (GT 80%)")){
                        insig_almost=insig_almost+1;
                    }
                }if(mprojectRiskViews.get(i).getImpact().equalsIgnoreCase("Major")){
                    if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Rare (0- 20%)")){
                        maj_rare=maj_rare+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Unlikely (20 - 40%)")){
                        maj_unlike=maj_unlike+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Possible (40 - 60%")){
                        maj_poss=maj_poss+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Likely (60 - 80%)")){
                        maj_like=maj_like+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Almost Certain (GT 80%)")){
                        maj_almost=maj_almost+1;
                    }
                }if(mprojectRiskViews.get(i).getImpact().equalsIgnoreCase("Catastrophic")){
                    if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Rare (0- 20%)")){
                        cat_rare=cat_rare+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Unlikely (20 - 40%)")){
                        cat_unlike=cat_unlike+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Possible (40 - 60%")){
                        cat_poss=cat_poss+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Likely (60 - 80%)")){
                        cat_like=cat_like+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Almost Certain (GT 80%)")){
                        cat_almost=cat_almost+1;
                    }
                }if(mprojectRiskViews.get(i).getImpact().equalsIgnoreCase("Moderate")){
                    if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Rare (0- 20%)")){
                        mod_rare=mod_rare+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Unlikely (20 - 40%)")){
                        mod_unlike=mod_unlike+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Possible (40 - 60%")){
                        mod_poss=mod_poss+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Likely (60 - 80%)")){
                        mod_like=mod_like+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Almost Certain (GT 80%)")){
                        mod_almost=mod_almost+1;
                    }
                }if(mprojectRiskViews.get(i).getImpact().equalsIgnoreCase("Minor")){
                    if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Rare (0- 20%)")){
                        min_rare=min_rare+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Unlikely (20 - 40%)")){
                        min_unlike=min_unlike+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Possible (40 - 60%")){
                        min_poss=min_poss+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Likely (60 - 80%)")){
                        min_like=min_like+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Almost Certain (GT 80%)")){
                        min_almost=min_almost+1;
                    }
                }if(mprojectRiskViews.get(i).getImpact().equalsIgnoreCase("Impact1")){
                    if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Rare (0- 20%)")){
                        imp_rare=imp_rare+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Unlikely (20 - 40%)")){
                        imp_unlike=imp_unlike+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Possible (40 - 60%")){
                        imp_poss=imp_poss+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Likely (60 - 80%)")){
                        imp_like=imp_like+1;
                    }if(mprojectRiskViews.get(i).getProbability().equalsIgnoreCase("Almost Certain (GT 80%)")){
                        imp_almost=imp_almost+1;
                    }
                }
            }

            insig.add(new BarEntry(0,new float[]{insig_rare,insig_unlike,insig_poss,insig_like,insig_almost}));
            major.add(new BarEntry(1,new float[]{maj_rare,maj_unlike,maj_poss,maj_like,maj_almost}));
            cat.add(new BarEntry(2,new float[]{cat_rare,cat_unlike,cat_poss,cat_like,cat_almost}));
            moderate.add(new BarEntry(3,new float[]{mod_rare,mod_unlike,mod_poss,mod_like,mod_almost}));
            minor.add(new BarEntry(4,new float[]{min_rare,min_unlike,min_poss,min_like,min_almost}));
            impact.add(new BarEntry(5,new float[]{imp_rare,imp_unlike,imp_poss,imp_like,imp_almost}));

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartblue));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartbrown));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgray));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartorange));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgreen));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartred));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartviolet));
            colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartyellow));

            BarDataSet set1 = new BarDataSet(insig, null);
            set1.setColors(colors);
            set1.setDrawIcons(false);
            set1.setStackLabels(new String[]{"Rare (0-20%)","Unlikely (20-40%)","Possible (40-60%)","Likely (60-80%)","Almost (80%)"});


            BarDataSet set2 = new BarDataSet(major, null);
            set2.setColors(colors);

            BarDataSet set3 = new BarDataSet(cat, null);
            set3.setColors(colors);

            BarDataSet set4 = new BarDataSet(moderate, null);
            set4.setColors(colors);

            BarDataSet set5 = new BarDataSet(minor, null);
            set5.setColors(colors);

            BarDataSet set6 = new BarDataSet(impact, null);
            set6.setColors(colors);

            BarData data = new BarData(set1,set2,set3,set4,set5,set6);
            data.setValueFormatter(new ChartValueFormatter());

            String[] xVals ={"Insignificant","Major","Catastrophic","Moderate","Minor","Impact"};
            XAxis axis=risk_hbar.getXAxis();
            axis.setValueFormatter(new ChartValueFormatter(xVals));
            //axis.setPosition(XAxis.XAxisPosition.);
            axis.setDrawGridLines(false);

            risk_hbar.setBorderWidth(0.5f);
            risk_hbar.setTouchEnabled(true);
            risk_hbar.invalidate(); // refresh
            risk_hbar.setData(data);
            risk_hbar.setFitBars(true);
        }*/
    }

    private void setHorizontalBarProjectCr() {
        uniquecr_category = new HashSet<String>();
        uniquecr_priority = new HashSet<String>();
        for(int i=0;i<mchangeReqViews.size();i++){
            uniquecr_category.add(mchangeReqViews.get(i).getCategory());
            uniquecr_priority.add(mchangeReqViews.get(i).getPriority());
        }
        int m=uniquecr_category.size();
        String array_category[]=new String[m];
        array_category=uniquecr_category.toArray(array_category);
        Log.d("Crvalues",""+ Arrays.toString(array_category));
        Log.d("crsize",""+array_category.length);

        int n=uniquecr_priority.size();
        String array_priority[]=new String[n];
        array_priority=uniquecr_priority.toArray(array_priority);

        for(int i=0;i<array_priority.length;i++){
            int category=0;
            for(int j=0;j<array_category.length;j++){
                category=category+1;
            }
        }

        /*mcrdata=new ArrayList<CRPojo>();
        for(int i=0;i<array_category.length;i++){
            CRPojo cr=new CRPojo();
            cr.setCategory(array_category[i]);
            mcrdata.add(cr);
        }
        for(int i=0;i<array_priority.length;i++){
            CRPojo cr=new CRPojo();
            cr.setPrioirty(array_priority[i]);
            mcrdata.add(cr);
        }
        ArrayList<BarEntry> low = new ArrayList<BarEntry>();
        int low_sch=0,low_boq=0,low_vend=0,low_Design=0,low_train=0,low_draw=0,low_design=0;
        int count=0;
        for(int i=0;i<mchangeReqViews.size();i++){
            for(int j=0;j<mcrdata.size();j++){
                if(mchangeReqViews.get(i).getPriority()!=null&&mchangeReqViews.get(i).getCategory()!=null){
                    if(mchangeReqViews.get(i).getPriority().equalsIgnoreCase(mcrdata.get(j).getPrioirty())){
                        count++;
                        *//*if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase(mcrdata.get(j).getCategory())){
                            mcrdata.get(j).setTotal(mcrdata.get(j).getTotal()+1);
                        }*//*
                        if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Schedule Change1")){
                            low_sch=low_sch+1;
                        }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("BOQ Change")){
                            low_boq=low_boq+1;
                        }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Vendor Change")){
                            low_vend=low_vend+1;
                        }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Design Change")){
                            low_Design=low_Design+1;
                        }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Training Content Change")){
                            low_train=low_train+1;
                        }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Drawing Change")){
                            low_draw=low_draw+1;
                        }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("design change")){
                            low_design=low_design+1;
                        }
                    }
                }
            }
        }
        low.add(new BarEntry(count,new float[]{low_sch,low_boq,low_vend,low_Design,low_train,low_draw,low_design}));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartblue));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartbrown));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgray));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartorange));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgreen));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartred));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartviolet));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartyellow));

        BarDataSet set1 = new BarDataSet(low, null);
        set1.setColors(colors);
        set1.setDrawIcons(false);
        set1.setStackLabels(new String[]{"Schedule","BOQ","Vendor","Design",
                "Training","Drawing","design"});

        BarData data = new BarData(set1);
        data.setValueFormatter(new CRValueFormater());
        change_hbar.setData(data);
        change_hbar.setFitBars(true);
        change_hbar.invalidate();*/

        /*for(int k=0;k<mcrdata.size();k++){
            low.add(new BarEntry(k,mcrdata.get(k).getTotal()));
        }*/


        /*ArrayList<BarEntry> low = new ArrayList<BarEntry>();
        ArrayList<BarEntry> medium = new ArrayList<BarEntry>();
        ArrayList<BarEntry> hign = new ArrayList<BarEntry>();
        int low_sch=0,low_boq=0,low_vend=0,low_Design=0,low_train=0,low_draw=0,low_design=0;
        int med_sch=0,med_boq=0,med_vend=0,med_Design=0,med_train=0,med_draw=0,med_design=0;
        int high_sch=0,high_boq=0,high_vend=0,high_Design=0,high_train=0,high_draw=0,high_design=0;
        for(int i=0;i<mchangeReqViews.size();i++){
            if(mchangeReqViews.get(i).getPriority()!=null&&mchangeReqViews.get(i).getCategory()!=null){
                if(mchangeReqViews.get(i).getPriority().equalsIgnoreCase("Low")){
                    if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Schedule Change1")){
                        low_sch=low_sch+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("BOQ Change")){
                        low_boq=low_boq+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Vendor Change")){
                        low_vend=low_vend+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Design Change")){
                        low_Design=low_Design+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Training Content Change")){
                        low_train=low_train+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Drawing Change")){
                        low_draw=low_draw+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("design change")){
                        low_design=low_design+1;
                    }
                }if(mchangeReqViews.get(i).getPriority().equalsIgnoreCase("Medium")){
                    if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Schedule Change1")){
                        med_sch=med_sch+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("BOQ Change")){
                        med_boq=med_boq+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Vendor Change")){
                        med_vend=med_vend+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Design Change")){
                        med_Design=med_Design+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Training Content Change")){
                        med_train=med_train+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Drawing Change")){
                        med_draw=med_draw+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("design change")){
                        med_design=med_design+1;
                    }
                }if(mchangeReqViews.get(i).getPriority().equalsIgnoreCase("high")){
                    if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Schedule Change1")){
                        high_sch=high_sch+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("BOQ Change")){
                        high_boq=high_boq+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Vendor Change")){
                        high_vend=high_vend+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Design Change")){
                        high_Design=high_Design+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Training Content Change")){
                        high_train=high_train+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("Drawing Change")){
                        high_draw=high_draw+1;
                    }if(mchangeReqViews.get(i).getCategory().equalsIgnoreCase("design change")){
                        high_design=high_design+1;
                    }
                }
            }
        }

        low.add(new BarEntry(0,new float[]{low_sch,low_boq,low_vend,low_Design,low_train,low_draw,low_design}));
        medium.add(new BarEntry(1,new float[]{med_sch,med_boq,med_vend,med_Design,med_train,med_draw,med_design}));
        hign.add(new BarEntry(2,new float[]{high_sch,high_boq,high_vend,high_Design,high_train,high_draw,high_design}));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartblue));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartbrown));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgray));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartorange));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartgreen));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartred));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartviolet));
        colors.add(ContextCompat.getColor(GraphActivity.this, R.color.chartyellow));

        BarDataSet set1 = new BarDataSet(low, null);
        set1.setColors(colors);
        set1.setDrawIcons(false);
        set1.setStackLabels(new String[]{"Schedule","BOQ","Vendor","Design",
                "Training","Drawing","design"});

        BarDataSet set2 = new BarDataSet(medium, null);
        set2.setStackLabels(new String[]{"","","","",
                "","",""});
        set2.setDrawIcons(false);
        set2.setColors(colors);


        BarDataSet set3 = new BarDataSet(hign, null);
        set3.setStackLabels(new String[]{"","","","",
                "","",""});
        set3.setDrawIcons(false);
        set3.setColors(colors);

        String[] xVals ={"Low","Medium","High"};
        XAxis axis=change_hbar.getXAxis();
        axis.setValueFormatter(new ChartValueFormatter(xVals));
        //axis.setPosition(XAxis.XAxisPosition.);
        axis.setDrawGridLines(false);

        ArrayList<IBarDataSet> dataSets=new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);


        BarData data = new BarData(dataSets);

        data.setValueFormatter(new ChartValueFormatter());
        change_hbar.getLegend().setWordWrapEnabled(true);
        change_hbar.setBorderWidth(0.5f);
        change_hbar.setTouchEnabled(true);
        change_hbar.getDescription().setEnabled(false);
        change_hbar.invalidate(); // refresh
        change_hbar.setData(data);
        change_hbar.setFitBars(true);*/

    }


    private void CallingSnackbar() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, R.string.check_internet, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //networkpercentages();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                RuntimeExceptionDao<ProjectPercentages, Integer> projectPercentagesDao = mDbHelper.getProjectPercentagesRuntimeDao();
                QueryBuilder<ProjectPercentages, Integer> projectPercentagesQueryBuilder = projectPercentagesDao.queryBuilder();
                projectPercentagesQueryBuilder.where().eq("zoneid", user_id);
                PreparedQuery<ProjectPercentages> preparedQuery1 = projectPercentagesQueryBuilder.prepare();
                mpercentages = projectPercentagesDao.query(preparedQuery1);

                RuntimeExceptionDao<ProjectProgress, Integer> projectProgressesDao = mDbHelper.getProjectProgressRuntimeDao();
                QueryBuilder<ProjectProgress, Integer> projectProgressQueryBuilder = projectProgressesDao.queryBuilder();
                projectProgressQueryBuilder.where().eq("zoneid", user_id);
                PreparedQuery<ProjectProgress> preparedQuery2 = projectProgressQueryBuilder.prepare();
                mprojectProgress = projectProgressesDao.query(preparedQuery2);

                RuntimeExceptionDao<ProjectIssues, Integer> projectIssuesDao = mDbHelper.getProjectIssuesRuntimeDao();
                QueryBuilder<ProjectIssues, Integer> projectIssuesQueryBuilder = projectIssuesDao.queryBuilder();
                projectIssuesQueryBuilder.where().eq("zoneid", user_id);
                PreparedQuery<ProjectIssues> preparedQuery3 = projectIssuesQueryBuilder.prepare();
                mprojectIssues = projectIssuesDao.query(preparedQuery3);

                RuntimeExceptionDao<ResourceProgress, Integer> resourceProgressesDao = mDbHelper.getResourceProgressRuntimeDao();
                QueryBuilder<ResourceProgress, Integer> resourceProgressQueryBuilder = resourceProgressesDao.queryBuilder();
                resourceProgressQueryBuilder.where().eq("zoneid", user_id);
                PreparedQuery<ResourceProgress> preparedQuery4 = resourceProgressQueryBuilder.prepare();
                mresourceProgress = resourceProgressesDao.query(preparedQuery4);

                RuntimeExceptionDao<ProjectRiskViews, Integer> projectRiskViewsDao = mDbHelper.getProjectRiskViewsRuntimeDao();
                QueryBuilder<ProjectRiskViews, Integer> projectRiskViewsQueryBuilder = projectRiskViewsDao.queryBuilder();
                projectRiskViewsQueryBuilder.where().eq("zoneid", user_id);
                PreparedQuery<ProjectRiskViews> preparedQuery5 = projectRiskViewsQueryBuilder.prepare();
                mprojectRiskViews = projectRiskViewsDao.query(preparedQuery5);

                RuntimeExceptionDao<ChangeReqViews, Integer> ChangeReqViewsDao = mDbHelper.getChangeReqViewsRuntimeDao();
                QueryBuilder<ChangeReqViews, Integer> changeReqViewsQueryBuilder = ChangeReqViewsDao.queryBuilder();
                changeReqViewsQueryBuilder.where().eq("zoneid", user_id);
                PreparedQuery<ChangeReqViews> preparedQuery6 = changeReqViewsQueryBuilder.prepare();
                mchangeReqViews = ChangeReqViewsDao.query(preparedQuery6);


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progres.dismiss();
            try {
                if(mpercentages.size()>=0){
                    setLinechartdata();
                }
                if(mprojectProgress.size()>=0){
                    setMultilinechart();
                }
                if(mprojectIssues.size()>=0){
                    setBarProjectissue();
                }
                if(mresourceProgress.size()>=0){
                    setBarResource();
                }
                if(mprojectRiskViews.size()>=0){
                    setHorizontalBarRisk();
                }
                if(mchangeReqViews.size()>=0){
                    setHorizontalBarProjectCr();
                }
            }catch (Exception e){
                e.printStackTrace();
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
