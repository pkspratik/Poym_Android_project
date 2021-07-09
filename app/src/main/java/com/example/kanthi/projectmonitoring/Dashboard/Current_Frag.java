package com.example.kanthi.projectmonitoring.Dashboard;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.Project_Risk_OpenRv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.RouteSalesViewsAdapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRisk;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRiskViews;
import com.example.kanthi.projectmonitoring.PoJo.RiskTypes;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
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
public class Current_Frag extends Fragment {

    private RecyclerView recyclerView;
    private RouteSalesViewsAdapter adapter;
    LinearLayoutManager layoutManager;
    List<RouteSalesViews> mListRouteSalesView;
    AvahanSqliteDbHelper mDbHelper;
    int partnerid;
    ProgressDialog mprogress;
    public Current_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_current_, container, false);
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        partnerid=AppPreferences.getPartnerid(getActivity());
        recyclerView= (RecyclerView) v.findViewById(R.id.rv_routesalesviews);
        mprogress = new ProgressDialog(getActivity());
        mprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mprogress.setMessage("Loading..");
        mprogress.setIndeterminate(true);
        mprogress.setCancelable(false);
        mprogress.show();
        new FetchDetailsFromDbTask().execute();
        /*ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getQARouteSalesViewsRouteassignmentSummaryId(AppPreferences.getUserId(getActivity()), AppPreferences.getRouteAssignmentSummaryId(getActivity()),false);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<RouteSalesViews> routeSalesViews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<RouteSalesViews>>() {
                        }.getType());
                mListRouteSalesView = routeSalesViews;
                mprogress.dismiss();
                adapter=new RouteSalesViewsAdapter(mListRouteSalesView,partnerid, AppUtilities.getDateTime().split("T")[0]);
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                mprogress.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });*/
        return v;
    }

    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Log.d("routeid",""+AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                RuntimeExceptionDao<RouteSalesViews, Integer> routeSalesViewsDao = mDbHelper.getRouteSalesViewsRuntimeDao();
                Where<RouteSalesViews, Integer> where = routeSalesViewsDao.queryBuilder().where();
                //routeSalesViewsBuilder.where().eq("routeassignmentsummaryid", AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                //routeSalesViewsBuilder.where().eq("qasubmitflag", false);
                //PreparedQuery<RouteSalesViews> preparedQuery1 = routeSalesViewsBuilder.prepare();
                where.and(where.eq("routeassignmentsummaryid",AppPreferences.getRouteAssignmentSummaryId(getActivity())),
                        where.eq("qasubmitflag",false));
                mListRouteSalesView = where.query();
                //Log.d("routesalesviewsSize",""+mListRouteSalesView.size( ));
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mprogress.dismiss();
            adapter=new RouteSalesViewsAdapter(mListRouteSalesView,partnerid, AppUtilities.getDateTime().split("T")[0]);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
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
