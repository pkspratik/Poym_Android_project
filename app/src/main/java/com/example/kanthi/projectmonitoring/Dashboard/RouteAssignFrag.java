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
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.Partnerviews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignments;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.SalesViews;
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
public class RouteAssignFrag extends Fragment {
    private RecyclerView recyclerView;
    private RouteSalesViewsAdapter adapter;
    LinearLayoutManager layoutManager;
    List<RouteSalesViews> mListRouteSalesView;

    ProgressDialog mprogress;
    int partnerid;
    AvahanSqliteDbHelper mDbHelper;
    public RouteAssignFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_route_assign, container, false);
        //Toast.makeText(getActivity(), ""+AppPreferences.getLoggedUserName(getActivity()), Toast.LENGTH_SHORT).show();
        //Log.d("userid",""+AppPreferences.getLoggedUserName(getActivity()));
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        recyclerView= (RecyclerView) v.findViewById(R.id.rv_routesalesviews);
        partnerid=AppPreferences.getPartnerid(getActivity());
        mprogress = new ProgressDialog(getActivity());
        mprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mprogress.setMessage("Loading..");
        mprogress.setIndeterminate(true);
        mprogress.setCancelable(false);
        mprogress.show();
        new FetchDetailsFromDbTask().execute();
        /*ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = null;
        if(AppPreferences.getGroupId(getActivity())==14||AppPreferences.getGroupId(getActivity())==29){
            call = service.getRouteSalesViews(AppPreferences.getUserId(getActivity()), AppPreferences.getRouteAssignmentSummaryId(getActivity()).toString());
        }
        else if(AppPreferences.getGroupId(getActivity())==17||AppPreferences.getGroupId(getActivity())==18){
            Log.d("data","size");
            call = service.getRoutePartnerSalesViews(AppPreferences.getUserId(getActivity()), AppPreferences.getRouteAssignmentSummaryId(getActivity()).toString());
        }
        else if(AppPreferences.getGroupId(getActivity())==23||AppPreferences.getGroupId(getActivity())==41||AppPreferences.getGroupId(getActivity())==39){
            call = service.getQARouteSalesViews(AppPreferences.getUserId(getActivity()), AppPreferences.getLoggedUserName(getActivity()),false);
        }
        if(call!=null){
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
            });
        }else{
            mprogress.dismiss();
            Toast.makeText(getActivity(), R.string.check_groupid, Toast.LENGTH_SHORT).show();
        }*/
        return v;
    }

    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                RuntimeExceptionDao<RouteSalesViews, Integer> routeSalesViewsDao = mDbHelper.getRouteSalesViewsRuntimeDao();
                Where<RouteSalesViews, Integer> where = routeSalesViewsDao.queryBuilder().where();
                if(AppPreferences.getGroupId(getActivity())==23||AppPreferences.getGroupId(getActivity())==41||AppPreferences.getGroupId(getActivity())==39){
                    //routeSalesViewsBuilder.where().eq("consultUserId", AppPreferences.getLoggedUserName(getActivity()));
                    //routeSalesViewsBuilder.where().eq("submitflag", false);
                    where.and(where.eq("consultUserId",AppPreferences.getLoggedUserName(getActivity())),where.eq("submitflag",true));
                }else{
                    where.eq("routeassignmentsummaryid", AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                   // where.and(where.eq("routeassignmentsummaryid",AppPreferences.getRouteAssignmentSummaryId(getActivity())),where.eq("submitflag",true));
                    //routeSalesViewsBuilder.where().eq("routeassignmentsummaryid", AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                }
                mListRouteSalesView = where.query();

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
