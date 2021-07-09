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

import com.example.kanthi.projectmonitoring.Adapters.Change_ReqClose_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Change_ReqOpen_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Project_Risk_CloseRv_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.ChangeReqCategories;
import com.example.kanthi.projectmonitoring.PoJo.ChangeReqViews;
import com.example.kanthi.projectmonitoring.PoJo.ChangeRequests;
import com.example.kanthi.projectmonitoring.PoJo.Priority;
import com.example.kanthi.projectmonitoring.PoJo.ProjectRisk;
import com.example.kanthi.projectmonitoring.PoJo.RiskTypes;
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
public class ChangeReqCloseFrag extends Fragment {

    private List<ChangeRequests> mreq;
    private List<Priority> mpriorities;
    RecyclerView rv_close;
    private List<ChangeReqViews> mchangeReqViews;

    private CoordinatorLayout coordinatorLayout;

    AvahanSqliteDbHelper mDbHelper;
    ProgressDialog progres;
    public ChangeReqCloseFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_change_req_close, container, false);
        rv_close= (RecyclerView) v.findViewById(R.id.rv_changerequestclose);
        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        //networkcall();
        progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();

        mchangeReqViews=new ArrayList<>();

        new FetchDetailsFromDbTask().execute();
        return v;
    }

    private void networkcall() {
        progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getChangeRequest(AppPreferences.getUserId(getActivity()),AppPreferences.getZoneId(getActivity()),
                AppPreferences.getPrefAreaId(getActivity())
                , AppPreferences.getDist_Area_Id(getActivity()),true);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ChangeRequests> priorities = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ChangeRequests>>() {
                        }.getType());
                mreq = priorities;
                networkpriority();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                CallingSnackbar();
            }
        });
    }

    private void networkpriority() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getPriority(AppPreferences.getUserId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Priority> priorities = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Priority>>() {
                        }.getType());
                mpriorities = priorities;
                networkcrViews();
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

    private void networkcrViews() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getCRViews(AppPreferences.getUserId(getActivity()),AppPreferences.getZoneId(getActivity()),
                AppPreferences.getPrefAreaId(getActivity())
                , AppPreferences.getDist_Area_Id(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ChangeReqViews> reqviews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ChangeReqViews>>() {
                        }.getType());
                mchangeReqViews = reqviews;
                progres.dismiss();
                if(mchangeReqViews.size()>0){
                    for(ChangeRequests requests:mreq){
                        for(int i=0;i<mchangeReqViews.size();i++){
                            if(requests.getChangereqCategoryId()==mchangeReqViews.get(i).getChangereqcategoryid()){
                                requests.setCategoryname(mchangeReqViews.get(i).getCategory());
                                break;
                            }
                        }
                        for(int j=0;j<mpriorities.size();j++){
                            if(requests.getPriorityId()==mpriorities.get(j).getId()){
                                requests.setPriorityname(mpriorities.get(j).getName());
                            }
                        }
                    }
                }
                if(mreq.size()>0){
                    Change_ReqClose_Rv_Adapter adapter=new Change_ReqClose_Rv_Adapter(mreq);
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
                RuntimeExceptionDao<ChangeRequests, Integer> ChangeRequestsDao = mDbHelper.getChangeRequestsRuntimeDao();
                Where<ChangeRequests, Integer> changeRequestsQueryBuilder = ChangeRequestsDao.queryBuilder().where();
                changeRequestsQueryBuilder.and(changeRequestsQueryBuilder.eq("salesareaId",AppPreferences.getPrefAreaId(getActivity())),
                        changeRequestsQueryBuilder.eq("distributionareaId",AppPreferences.getDist_Area_Id(getActivity())),
                        changeRequestsQueryBuilder.eq("completedFlag",true));

                //changeRequestsQueryBuilder.eq("zoneId", AppPreferences.getZoneId(getActivity())); added filter
                PreparedQuery<ChangeRequests> preparedQuery1 = changeRequestsQueryBuilder.prepare();
                mreq = ChangeRequestsDao.query(preparedQuery1);

                RuntimeExceptionDao<Priority, Integer> prioritiesDao = mDbHelper.getPriorityRuntimeDao();
                mpriorities=prioritiesDao.queryForAll();

                RuntimeExceptionDao<ChangeReqViews, Integer> ChangeReqViewsDao = mDbHelper.getChangeReqViewsRuntimeDao();
                Where<ChangeReqViews, Integer> changeReqViewsQueryBuilder = ChangeReqViewsDao.queryBuilder().where();
                changeRequestsQueryBuilder.and(changeRequestsQueryBuilder.eq("areaid",AppPreferences.getPrefAreaId(getActivity())),
                        changeRequestsQueryBuilder.eq("distareaid",AppPreferences.getDist_Area_Id(getActivity())));
                //changeReqViewsQueryBuilder.eq("zoneid", AppPreferences.getZoneId(getActivity())); added filter
                PreparedQuery<ChangeReqViews> preparedQuery2 = changeReqViewsQueryBuilder.prepare();
                mchangeReqViews = ChangeReqViewsDao.query(preparedQuery2);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progres.dismiss();
            if(mchangeReqViews.size()>0){
                for(ChangeRequests requests:mreq){
                    for(int i=0;i<mchangeReqViews.size();i++){
                        if(requests.getChangereqCategoryId()==mchangeReqViews.get(i).getChangereqcategoryid()){
                            requests.setCategoryname(mchangeReqViews.get(i).getCategory());
                            break;
                        }
                    }
                    for(int j=0;j<mpriorities.size();j++){
                        if(requests.getPriorityId()==mpriorities.get(j).getId()){
                            requests.setPriorityname(mpriorities.get(j).getName());
                        }
                    }
                }
            }
            if(mreq.size()>0){
                Change_ReqClose_Rv_Adapter adapter=new Change_ReqClose_Rv_Adapter(mreq);
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
