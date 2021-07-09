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

import com.example.kanthi.projectmonitoring.Adapters.RouteSalesViewsAdapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
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
public class Future_Frag extends Fragment {
    private RecyclerView mrecyclerView;
    private RouteSalesViewsAdapter routeSalesViewsAdapter;
    LinearLayoutManager mlayoutManager;
    List<RouteSalesViews> mlistRouteSalesView;

    AvahanSqliteDbHelper mDbHelper;
    int partnerid;
    ProgressDialog progress;
    public Future_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_future_, container, false);
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        mrecyclerView= (RecyclerView) v.findViewById(R.id.rv_task);
        partnerid= AppPreferences.getPartnerid(getActivity());
        progress = new ProgressDialog(getActivity());
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading..");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
        new FetchDetailsFromDbTask().execute();
        /*ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getQARouteSalesViewsRouteassignmentSummaryId(AppPreferences.getUserId(getActivity()), AppPreferences.getRouteAssignmentSummaryId(getActivity()),true);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<RouteSalesViews> routeSalesViews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<RouteSalesViews>>() {
                        }.getType());
                mlistRouteSalesView = routeSalesViews;
                progress.dismiss();
                routeSalesViewsAdapter=new RouteSalesViewsAdapter(mlistRouteSalesView,partnerid, AppUtilities.getDateTime().split("T")[0]);
                mlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mrecyclerView.setAdapter(routeSalesViewsAdapter);
                mrecyclerView.setLayoutManager(mlayoutManager);
                routeSalesViewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
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
                where.and(where.eq("routeassignmentsummaryid",AppPreferences.getRouteAssignmentSummaryId(getActivity())),
                        where.eq("qasubmitflag",true));
                mlistRouteSalesView = where.query();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();
            routeSalesViewsAdapter=new RouteSalesViewsAdapter(mlistRouteSalesView,partnerid, AppUtilities.getDateTime().split("T")[0]);
            mlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mrecyclerView.setAdapter(routeSalesViewsAdapter);
            mrecyclerView.setLayoutManager(mlayoutManager);
            routeSalesViewsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        mDbHelper = null;
        super.onDestroy();
    }
}
