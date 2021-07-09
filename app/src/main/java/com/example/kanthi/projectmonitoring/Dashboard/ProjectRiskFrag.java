package com.example.kanthi.projectmonitoring.Dashboard;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.Project_Risk_OpenRv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.ResourceList_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Risk_Type_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Survey_Points_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.TaskRemarks_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.*;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRisk;
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
import com.j256.ormlite.stmt.Where;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectRiskFrag extends Fragment  implements ClickInterface {
    EditText cur_date,oocur_date,summary;
    Spinner sp_risktype;
    Button save,cancel;
    int risktypeid;
    String risk_name;
    ProjectRisk projectRisk;
    ProjectRiskViews projectRiskViews;
    private List<ProjectRisk> mrisks;
    private List<RiskTypes> mriskTypes;
    private List<ProjectRiskViews> mRiskViews;
    RecyclerView rv_projectrisk;
    AlertDialog dialog;
    String date;
    Project_Risk_OpenRv_Adapter adapter;
    private CoordinatorLayout coordinatorLayout;

    AvahanSqliteDbHelper mDbHelper;
    ProgressDialog progres;
    int updateposition = 0;
    String tv_risk;
    String tv_probablity;
    String tv_impact;
    String tv_responce;
    String tv_status;

    private ClickInterface listner;
    public ProjectRiskFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_project_risk, container, false);
        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);
        listner = this;
        rv_projectrisk= (RecyclerView) v.findViewById(R.id.rv_projectrisk);
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        //networkcall();
        progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        new FetchDetailsFromDbTask().execute();
        /*final ProgressDialog progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getProjectRiskopen(AppPreferences.getUserId(getActivity()),AppPreferences.getZoneId(getActivity()),
                AppPreferences.getPrefAreaId(getActivity())
                , AppPreferences.getDist_Area_Id(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectRisk> risks = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectRisk>>() {
                        }.getType());
                mrisks = risks;
                ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
                Call<String> call1 = service1.getRiskType(AppPreferences.getUserId(getActivity()));
                call1.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        ArrayList<RiskTypes> risktypes = new Gson().fromJson(response.body(),
                                new TypeToken<ArrayList<RiskTypes>>() {
                                }.getType());
                        mriskTypes = risktypes;
                        if(mrisks.size()>0){
                            adapter=new Project_Risk_OpenRv_Adapter(mrisks,mriskTypes);
                            LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                            rv_projectrisk.setAdapter(adapter);
                            rv_projectrisk.setLayoutManager(manager);
                            adapter.notifyDataSetChanged();
                        }
                        progres.dismiss();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                        progres.dismiss();
                        Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });*/
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup();
            }
        });
        return v;
    }
    private void popup(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View v1= LayoutInflater.from(getActivity()).inflate(R.layout.date_picker,null);
        builder.setView(v1);
        builder.setCancelable(false);
        cur_date= (EditText) v1.findViewById(R.id.sp_date);
        sp_risktype= (Spinner) v1.findViewById(R.id.sp_risktype);
        oocur_date= (EditText) v1.findViewById(R.id.occur_date);
        summary= (EditText) v1.findViewById(R.id.risk_summary);
        save= (Button) v1.findViewById(R.id.risksave);
        cancel= (Button) v1.findViewById(R.id.riskcancel);
        dialog=builder.create();
        currentdate();
        Risk_Type_Sp_Adapter adapter=new Risk_Type_Sp_Adapter(getActivity(),mriskTypes);
        sp_risktype.setAdapter(adapter);
        cur_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                final int mm = calendar.get(Calendar.MONTH);
                final int dd = calendar.get(Calendar.DAY_OF_MONTH);
                String comparedates = String.valueOf(calendar.getTimeInMillis());
                DatePickerDialog dpDialog = new DatePickerDialog(getActivity(), toDateListener, yy, mm, dd);
                dpDialog.getDatePicker().setMinDate(Long.parseLong(comparedates));
                dpDialog.show();
            }
        });
        oocur_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                final int mm = calendar.get(Calendar.MONTH);
                final int dd = calendar.get(Calendar.DAY_OF_MONTH);
                String comparedates = String.valueOf(calendar.getTimeInMillis());
                DatePickerDialog dpDialog = new DatePickerDialog(getActivity(), toDateListener1, yy, mm, dd);
                dpDialog.getDatePicker().setMinDate(Long.parseLong(comparedates));
                dpDialog.show();
            }
        });
        sp_risktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    risktypeid=mriskTypes.get(sp_risktype.getSelectedItemPosition() - 1).getId();
                    risk_name = mriskTypes.get(sp_risktype.getSelectedItemPosition() - 1).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(risktypeid!=0&&date!=""){
                    projectRisk=new ProjectRisk();
                    long masterid=System.currentTimeMillis();
                    projectRisk.setId(masterid);
                    projectRisk.setZoneId(AppPreferences.getZoneId(getActivity()));
                    projectRisk.setSalesareaId(AppPreferences.getPrefAreaId(getActivity()));
                    projectRisk.setDistributionareaId(AppPreferences.getDist_Area_Id(getActivity()));
                    projectRisk.setRisktypeId(risktypeid);
                    projectRisk.setManagerid(AppPreferences.getSaleMngrIdId(getActivity()));
                    projectRisk.setEmployeeid(AppPreferences.getEmployeeId(getActivity()));
                    projectRisk.setDate(AppUtilities.getDateTime());
                    projectRisk.setShortSummary(summary.getText().toString());
                    projectRisk.setLikelyOccuranceDate(AppUtilities.getTimestampWithDate(date));
                    projectRisk.setLastModifiedDate(AppUtilities.getDateTime());
                    projectRisk.setStatus("Reported");
                    projectRisk.setInsertFlag(true);

                    projectRiskViews=new ProjectRiskViews();
                    //long masterid=System.currentTimeMillis();
                    projectRiskViews.setId(masterid);
                    projectRiskViews.setZoneid(AppPreferences.getZoneId(getActivity()));
                    projectRiskViews.setSalesareaid(AppPreferences.getPrefAreaId(getActivity()));
                    projectRiskViews.setDistributionareaid(AppPreferences.getDist_Area_Id(getActivity()));
                    projectRiskViews.setRisktype(risk_name);
                    projectRiskViews.setStatus("Reported");

                    try {
                        RuntimeExceptionDao<ProjectRisk, Integer> projectRisksDao = mDbHelper.getProjectRiskRuntimeDao();
                        projectRisksDao.create(projectRisk);

                        RuntimeExceptionDao<ProjectRiskViews, Integer> projectRiskViewsDao = mDbHelper.getProjectRiskViewsRuntimeDao();
                        projectRiskViewsDao.create(projectRiskViews);


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //postProjectRisk();
                    dialog.dismiss();
                    new FetchDetailsFromDbTask().execute();
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
            final int m = monthOfYear;
            //Log.d("first tvalue", "" + monthOfYear + dayOfMonth + year);
            String date = String.valueOf(m) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
            cur_date.setText(AppUtilities.getDateString(year, m, dayOfMonth));
        }
    };
    private DatePickerDialog.OnDateSetListener toDateListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
            final int m = monthOfYear;
            Log.d("dateformat", "" + monthOfYear + dayOfMonth + year);
            date = String.valueOf(year) + "-" + String.valueOf(monthOfYear+1) + "-" + String.valueOf(dayOfMonth);
            oocur_date.setText(AppUtilities.getDateString(year, m, dayOfMonth));
        }
    };
    private void currentdate() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        final int mm = calendar.get(Calendar.MONTH);
        final int m = mm;
        final int dd = calendar.get(Calendar.DAY_OF_MONTH);
        date = String.valueOf(yy) + "-" + String.valueOf(m+1) + "-" + String.valueOf(dd);
        cur_date.setText(AppUtilities.getDateString(yy, m, dd));
        oocur_date.setText(AppUtilities.getDateString(yy, m, dd));
    }
    private void postProjectRisk(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                    projectRisk, ProjectRisk.class)).getAsJsonObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        benjson.remove("id");
        call = service.insertProjectRisk(AppPreferences.getUserId(getActivity()), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    networkcall();
                    /*getActivity().finish();
                    Intent intent=new Intent(getActivity(),ProjectRisk.class);
                    getActivity().startActivity(intent);*/
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void networkcall(){
        final ProgressDialog progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getProjectRiskopen(AppPreferences.getUserId(getActivity()),AppPreferences.getZoneId(getActivity()),
                AppPreferences.getPrefAreaId(getActivity())
                , AppPreferences.getDist_Area_Id(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ProjectRisk> risks = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ProjectRisk>>() {
                        }.getType());
                mrisks = risks;
                ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
                Call<String> call1 = service1.getRiskType(AppPreferences.getUserId(getActivity()));
                call1.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        ArrayList<RiskTypes> risktypes = new Gson().fromJson(response.body(),
                                new TypeToken<ArrayList<RiskTypes>>() {
                                }.getType());
                        mriskTypes = risktypes;
                        progres.dismiss();
                        if(mrisks.size()>0){
                            adapter=new Project_Risk_OpenRv_Adapter(mrisks,mriskTypes,listner);
                            LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                            rv_projectrisk.setAdapter(adapter);
                            rv_projectrisk.setLayoutManager(manager);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                        progres.dismiss();
                        //CallingSnackbar();
                        //Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                //CallingSnackbar();
            }
        });
    }

    private void CallingSnackbar(){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, R.string.check_internet, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //networkcall();
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
                RuntimeExceptionDao<ProjectRisk, Integer> ProjectRiskDao = mDbHelper.getProjectRiskRuntimeDao();
                Where<ProjectRisk, Integer> projectRiskWhere = ProjectRiskDao.queryBuilder().where();
                //projectRiskWhere.eq("zoneId",AppPreferences.getZoneId(getActivity())), added filter
                projectRiskWhere.and(projectRiskWhere.eq("salesareaId",AppPreferences.getPrefAreaId(getActivity())),
                        projectRiskWhere.eq("distributionareaId",AppPreferences.getDist_Area_Id(getActivity())));
                mrisks = projectRiskWhere.query();

                RuntimeExceptionDao<RiskTypes, Integer> riskTypesDao = mDbHelper.getRiskTypesRuntimeDao();
                mriskTypes=riskTypesDao.queryForAll();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progres.dismiss();
            if(mrisks.size()>0){
                adapter=new Project_Risk_OpenRv_Adapter(mrisks,mriskTypes,listner);
                LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                rv_projectrisk.setAdapter(adapter);
                rv_projectrisk.setLayoutManager(manager);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        mDbHelper = null;
        super.onDestroy();
    }

    @Override
    public void recyclerviewOnClick(int position) {
        updateposition=position;
        long tv_id=mrisks.get(position).getId();
        try {
            RuntimeExceptionDao<ProjectRiskViews, Integer> projectRiskViewsDoa = mDbHelper.getProjectRiskViewsRuntimeDao();
            Where<ProjectRiskViews, Integer> projectRiskViewsWhere = projectRiskViewsDoa.queryBuilder().where();
            projectRiskViewsWhere.and(projectRiskViewsWhere.eq("zoneid",AppPreferences.getZoneId(getActivity())),
                    projectRiskViewsWhere.eq("salesareaid",AppPreferences.getPrefAreaId(getActivity())),
                    projectRiskViewsWhere.eq("distributionareaid",AppPreferences.getDist_Area_Id(getActivity())),
                    projectRiskViewsWhere.eq("id",tv_id));
            mRiskViews = projectRiskViewsWhere.query();

            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            View vi=LayoutInflater.from(getActivity()).inflate(R.layout.projectriskviews_popup,null);
            builder.setView(vi);
            builder.setCancelable(false);
            final TextView risk= (TextView) vi.findViewById(R.id.pro_risktype);
            final TextView probablity= (TextView) vi.findViewById(R.id.pro_probability);
            final TextView impact= (TextView) vi.findViewById(R.id.pro_impact);
            final TextView responce= (TextView) vi.findViewById(R.id.pro_responce);
            final TextView status= (TextView) vi.findViewById(R.id.pro_status);
            Button cancel= (Button) vi.findViewById(R.id.cancel_projectrisk);
            final AlertDialog dialog=builder.create();
            for(int i=0;i<mRiskViews.size();i++){
                if(mRiskViews.get(i).getId()==tv_id){
                    tv_risk=mRiskViews.get(i).getRisktype();
                    tv_probablity=mRiskViews.get(i).getProbability();
                    tv_impact=mRiskViews.get(i).getImpact();
                    tv_responce=mRiskViews.get(i).getResponse();
                    tv_status=mRiskViews.get(i).getStatus();
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
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
