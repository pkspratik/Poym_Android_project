package com.example.kanthi.projectmonitoring.Dashboard;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.Change_ReqOpen_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Change_Req_Category_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Importance_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.PoJo.*;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.Where;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeReqOpenFrag extends Fragment implements ClickInterface{

    private List<ChangeRequests> mchangeRequests;
    private List<ChangeRequests> mchangeRequestcount;
    private List<ChangeReqViews> mchangeReqViews;
    private List<ChangeReqCategories> mchangeReqCategories;
    private List<Priority> mpriorities;
    int cat_id,imp_id;
    String cat_name,imp_name;
    RecyclerView rv_change_req_open;
    ChangeRequests changeRequests;
    ChangeReqViews changeReqViews;
    AlertDialog dialog;
    ProgressDialog progres;
    AvahanSqliteDbHelper mDbHelper;
    int count;

    private ClickInterface listner;

    String tv_reqno,tv_priotity,tv_cat,tv_desc;
    private CoordinatorLayout coordinatorLayout;
    public ChangeReqOpenFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_change_req_open, container, false);
        listner=this;
        rv_change_req_open= (RecyclerView) v.findViewById(R.id.rv_changerequestopen);
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

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changepopup();
            }
        });
        return v;
    }
    private void changepopup(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View v1= LayoutInflater.from(getActivity()).inflate(R.layout.change_request_popup,null);
        builder.setView(v1);
        builder.setCancelable(false);
        builder.setTitle("Change Request");
        //EditText et_changeno= (EditText) v1.findViewById(R.id.et_chagereq_no);
        final Spinner sp_item_tochange= (Spinner) v1.findViewById(R.id.sp_itemtochange);
        final Spinner sp_priority= (Spinner) v1.findViewById(R.id.sp_priority);
        final EditText et_desc= (EditText) v1.findViewById(R.id.et_description);
        Button bt_save= (Button) v1.findViewById(R.id.change_save);
        Button bt_cancel= (Button) v1.findViewById(R.id.change_cancel);
        dialog=builder.create();
        Change_Req_Category_Sp_Adapter category_sp_adapter=new Change_Req_Category_Sp_Adapter(getActivity(),mchangeReqCategories);
        sp_item_tochange.setAdapter(category_sp_adapter);
        Importance_Sp_Adapter importance_sp_adapter=new Importance_Sp_Adapter(getActivity(),mpriorities);
        sp_priority.setAdapter(importance_sp_adapter);
        sp_item_tochange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    cat_id=mchangeReqCategories.get(sp_item_tochange.getSelectedItemPosition()-1).getId();
                    cat_name=mchangeReqCategories.get(sp_item_tochange.getSelectedItemPosition()-1).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    imp_id=mpriorities.get(sp_priority.getSelectedItemPosition()-1).getId();
                    imp_name=mpriorities.get(sp_priority.getSelectedItemPosition()-1).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imp_id!=0&&cat_id!=0) {
                    changeRequests = new ChangeRequests();
                    long masterid=System.currentTimeMillis();
                    changeRequests.setId(masterid);
                    int reqno =mchangeRequestcount.size()+2;
                    changeRequests.setChangereqno("Req00"+reqno);
                    changeRequests.setZoneId(AppPreferences.getZoneId(getActivity()));
                    changeRequests.setSalesareaId(AppPreferences.getPrefAreaId(getActivity()));
                    changeRequests.setDistributionareaId(AppPreferences.getDist_Area_Id(getActivity()));
                    changeRequests.setPriorityId(imp_id);
                    changeRequests.setChangereqCategoryId(cat_id);
                    changeRequests.setDescription(et_desc.getText().toString());
                    changeRequests.setRequestDate(AppUtilities.getDateTime());
                    changeRequests.setLastModifiedDate(AppUtilities.getDateTime());
                    changeRequests.setManagerid(AppPreferences.getSaleMngrIdId(getActivity()));
                    changeRequests.setEmployeeid(AppPreferences.getEmployeeId(getActivity()));
                    changeRequests.setFromdate(AppPreferences.getFromDate(getActivity()));
                    changeRequests.setTodate(AppPreferences.getToDate(getActivity()));
                    changeRequests.setTotaltarget(String.valueOf(AppPreferences.getTotalTarget(getActivity())));
                    changeRequests.setActualtarget(String.valueOf(AppPreferences.getTotalActual(getActivity())));
                    changeRequests.setRouteassignmentId(AppPreferences.getRouteAssignmentSummaryId(getActivity()));
                    changeRequests.setUnitmeasurementid(AppPreferences.getUnitMeasurementId(getActivity()));
                    changeRequests.setInsertFlag(true);

                    //long masterid1=System.currentTimeMillis();
                    changeReqViews=new ChangeReqViews();
                    changeReqViews.setId(masterid);
                    changeReqViews.setZoneid(AppPreferences.getZoneId(getActivity()));
                    changeReqViews.setSalesareaid(AppPreferences.getPrefAreaId(getActivity()));
                    changeReqViews.setDistributionareaid(AppPreferences.getDist_Area_Id(getActivity()));
                    changeReqViews.setChangereqno("Req00"+reqno);
                    changeReqViews.setPriority(imp_name);
                    changeReqViews.setCategory(cat_name);
                    changeReqViews.setDescription(et_desc.length()==0?"":et_desc.getText().toString());

                    try {
                        RuntimeExceptionDao<ChangeRequests, Integer> changeRequestsDao = mDbHelper.getChangeRequestsRuntimeDao();
                        changeRequestsDao.create(changeRequests);

                        RuntimeExceptionDao<ChangeReqViews, Integer> changeReqViewsDao = mDbHelper.getChangeReqViewsRuntimeDao();
                        changeReqViewsDao.create(changeReqViews);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //postchangereq();
                    dialog.dismiss();
                    new FetchDetailsFromDbTask().execute();
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void postchangereq(){
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
                    changeRequests, ChangeRequests.class)).getAsJsonObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        benjson.remove("id");
        call = service.insertChangeRequest(AppPreferences.getUserId(getActivity()), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    //networkcall();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void networkcall(){
        progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getChangeRequestopen(AppPreferences.getUserId(getActivity()),AppPreferences.getZoneId(getActivity()),
                AppPreferences.getPrefAreaId(getActivity())
                , AppPreferences.getDist_Area_Id(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ChangeRequests> change_req = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ChangeRequests>>() {
                        }.getType());
                mchangeRequests = change_req;
                networkCRCategories();
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

    private void networkCRCategories() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getChangeRequestCategories(AppPreferences.getUserId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ChangeReqCategories> categories = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ChangeReqCategories>>() {
                        }.getType());
                mchangeReqCategories = categories;
                networkPriority();
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

    private void networkPriority() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getPriority(AppPreferences.getUserId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Priority> priorities = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Priority>>() {
                        }.getType());
                mpriorities = priorities;
                networkCRCount();
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

    private void networkCRCount() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getChangeRequestCount(AppPreferences.getUserId(getActivity()));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<ChangeRequests> requestses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<ChangeRequests>>() {
                        }.getType());
                mchangeRequestcount = requestses;
                networkCRViews();
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

    private void networkCRViews() {
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
                    for(ChangeRequests requests:mchangeRequests){
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
                if(mchangeRequests.size()>0){
                    Change_ReqOpen_Rv_Adapter adapter=new Change_ReqOpen_Rv_Adapter(mchangeRequests, listner);
                    LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                    rv_change_req_open.setAdapter(adapter);
                    rv_change_req_open.setLayoutManager(manager);
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
                        networkcall();
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
                Where<ChangeRequests, Integer> changeReqWhere = ChangeRequestsDao.queryBuilder().where();
                //changeReqWhere.eq("zoneId",AppPreferences.getZoneId(getActivity())), added filter
                changeReqWhere.and(changeReqWhere.eq("salesareaId",AppPreferences.getPrefAreaId(getActivity())),
                        changeReqWhere.eq("distributionareaId",AppPreferences.getDist_Area_Id(getActivity())));
                mchangeRequests = changeReqWhere.query();

                RuntimeExceptionDao<ChangeReqCategories, Integer> ChangeReqCategoriesDao = mDbHelper.getChangeReqCategoriesRuntimeDao();
                mchangeReqCategories=ChangeReqCategoriesDao.queryForAll();

                RuntimeExceptionDao<Priority, Integer> prioritiesDao = mDbHelper.getPriorityRuntimeDao();
                mpriorities=prioritiesDao.queryForAll();

                RuntimeExceptionDao<ChangeRequests, Integer> changeRequestsDoa = mDbHelper.getChangeRequestsRuntimeDao();
                mchangeRequestcount=changeRequestsDoa.queryForAll();

                RuntimeExceptionDao<ChangeReqViews, Integer> ChangeReqViewsDao = mDbHelper.getChangeReqViewsRuntimeDao();
                Where<ChangeReqViews, Integer> changeReqViewsWhere = ChangeReqViewsDao.queryBuilder().where();
                //changeReqViewsWhere.eq("zoneid",AppPreferences.getZoneId(getActivity())), added filter
                changeReqViewsWhere.and(changeReqViewsWhere.eq("salesareaid",AppPreferences.getPrefAreaId(getActivity())),
                        changeReqViewsWhere.eq("distributionareaid",AppPreferences.getDist_Area_Id(getActivity())));
                mchangeReqViews = changeReqViewsWhere.query();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progres.dismiss();
            try {
                if(mchangeReqViews.size()>0){
                    for(ChangeRequests requests:mchangeRequests){
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
            }catch (Exception e){
                e.printStackTrace();
            }
            if(mchangeRequests.size()>0){
                Change_ReqOpen_Rv_Adapter adapter=new Change_ReqOpen_Rv_Adapter(mchangeRequests,listner);
                LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                rv_change_req_open.setAdapter(adapter);
                rv_change_req_open.setLayoutManager(manager);
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
        long tv_id=mchangeRequests.get(position).getId();
        try {
            RuntimeExceptionDao<ChangeReqViews, Integer> ChangeReqViewsDao = mDbHelper.getChangeReqViewsRuntimeDao();
            Where<ChangeReqViews, Integer> changeReqViewsWhere = ChangeReqViewsDao.queryBuilder().where();
            //changeReqViewsWhere.eq("zoneid",AppPreferences.getZoneId(getActivity())), added filter
            changeReqViewsWhere.and(changeReqViewsWhere.eq("salesareaid",AppPreferences.getPrefAreaId(getActivity())),
                    changeReqViewsWhere.eq("distributionareaid",AppPreferences.getDist_Area_Id(getActivity())),
                    changeReqViewsWhere.eq("id",tv_id));
            mchangeReqViews = changeReqViewsWhere.query();
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            View vi=LayoutInflater.from(getActivity()).inflate(R.layout.changereq_popup,null);
            builder.setView(vi);
            builder.setCancelable(false);
            final TextView reqno= (TextView) vi.findViewById(R.id.ch_reqno);
            final TextView priority= (TextView) vi.findViewById(R.id.ch_priority);
            final TextView category= (TextView) vi.findViewById(R.id.ch_category);
            final TextView desc= (TextView) vi.findViewById(R.id.ch_description);
            Button cancel= (Button) vi.findViewById(R.id.cancel_changereq);
            final AlertDialog dialog=builder.create();
            for(int i=0;i<mchangeReqViews.size();i++){
                if(mchangeReqViews.get(i).getId()==tv_id){
                    tv_reqno=mchangeReqViews.get(i).getChangereqno()==null?"":mchangeReqViews.get(i).getChangereqno();
                    tv_priotity=mchangeReqViews.get(i).getPriority();
                    tv_cat=mchangeReqViews.get(i).getCategory();
                    tv_desc=mchangeReqViews.get(i).getDescription()==null?"":mchangeReqViews.get(i).getDescription();
                    reqno.setText(tv_reqno);
                    priority.setText(tv_priotity);
                    category.setText(tv_cat);
                    desc.setText(tv_desc);
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
