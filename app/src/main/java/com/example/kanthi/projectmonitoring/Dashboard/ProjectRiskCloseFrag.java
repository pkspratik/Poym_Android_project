package com.example.kanthi.projectmonitoring.Dashboard;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.Project_Risk_CloseRv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Project_Risk_OpenRv_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.*;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRisk;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectRiskCloseFrag extends Fragment {

    private List<ProjectRisk> mrisks;
    private List<RiskTypes> mriskTypes;
    RecyclerView rv_close;
    private CoordinatorLayout coordinatorLayout;

    AvahanSqliteDbHelper mDbHelper;
    ProgressDialog progres;
    public ProjectRiskCloseFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_project_risk_close, container, false);
        rv_close= (RecyclerView) v.findViewById(R.id.rv_projectriskclose);
        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        //networkcall();
        progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        new FetchDetailsFromDbTask().execute();
        return v;
    }

    private void networkcall() {
        final ProgressDialog progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getProjectRisk(AppPreferences.getUserId(getActivity()),AppPreferences.getZoneId(getActivity()),
                AppPreferences.getPrefAreaId(getActivity())
                , AppPreferences.getDist_Area_Id(getActivity()),true);
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
                            Project_Risk_CloseRv_Adapter adapter=new Project_Risk_CloseRv_Adapter(mrisks,mriskTypes);
                            LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                            rv_close.setAdapter(adapter);
                            rv_close.setLayoutManager(manager);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                        progres.dismiss();
                        CallingSnackbar();
                    }
                });
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                CallingSnackbar();
                //Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
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
                Where<ProjectRisk, Integer> projectRiskQueryBuilder = ProjectRiskDao.queryBuilder().where();
                projectRiskQueryBuilder.and(projectRiskQueryBuilder.eq("salesareaId",AppPreferences.getPrefAreaId(getActivity())),
                        projectRiskQueryBuilder.eq("distributionareaId",AppPreferences.getDist_Area_Id(getActivity())));
                //projectRiskQueryBuilder.eq("zoneId", AppPreferences.getZoneId(getActivity())); added filter
                PreparedQuery<ProjectRisk> preparedQuery1 = projectRiskQueryBuilder.prepare();
                mrisks = ProjectRiskDao.query(preparedQuery1);

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
                Project_Risk_CloseRv_Adapter adapter=new Project_Risk_CloseRv_Adapter(mrisks,mriskTypes);
                LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                rv_close.setAdapter(adapter);
                rv_close.setLayoutManager(manager);
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

}
