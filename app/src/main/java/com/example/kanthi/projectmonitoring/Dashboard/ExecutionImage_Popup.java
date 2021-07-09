package com.example.kanthi.projectmonitoring.Dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.Execution_Points_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.RouteSalesViewsAdapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.DocumentTypes;
import com.example.kanthi.projectmonitoring.PoJo.Documents;
import com.example.kanthi.projectmonitoring.PoJo.Promotions;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.TourTypes;
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
 * Created by Kanthi on 3/19/2018.
 */

public class ExecutionImage_Popup extends DialogFragment {
    Dialog d=null;
    ProgressDialog progres;
    RecyclerView exe_points;
    TextView close;
    private List<Promotions> mpromotions;
    AvahanSqliteDbHelper mDbHelper;
    public ExecutionImage_Popup() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ab=new AlertDialog.Builder(getActivity());
        View v=getActivity().getLayoutInflater().inflate(R.layout.executiondetail_popup,null);
        ab.setView(v);
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        exe_points= (RecyclerView) v.findViewById(R.id.rv_executionpoints);
        close= (TextView) v.findViewById(R.id.close_popup1);
        d=ab.create();
        //LandingFragment fragment = (LandingFragment) getTargetFragment();
        progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        new FetchDetailsFromDbTask().execute();
        /*ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1=null;
        if(AppPreferences.getGroupId(getActivity())==14||AppPreferences.getGroupId(getActivity())==17||AppPreferences.getGroupId(getActivity())==18){
            call1 = service1.getPromotions(AppPreferences.getUserId(getActivity())
                    ,AppPreferences.getZoneId(getActivity()),
                    AppPreferences.getPrefAreaId(getActivity()),
                    AppPreferences.getDist_Area_Id(getActivity()));
        }else if(AppPreferences.getGroupId(getActivity())==23||AppPreferences.getGroupId(getActivity())==41||AppPreferences.getGroupId(getActivity())==39){
            call1 = service1.getQAPromotions(AppPreferences.getUserId(getActivity())
                    ,AppPreferences.getRouteSalesViewid(getActivity()));
        }if(call1!=null){
            call1.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<Promotions> promotionses = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<Promotions>>() {
                            }.getType());
                    mpromotions=promotionses;
                    progres.dismiss();
                    if(mpromotions.size()>0){
                        Execution_Points_Rv_Adapter adapter=new Execution_Points_Rv_Adapter(mpromotions);
                        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                        exe_points.setAdapter(adapter);
                        exe_points.setLayoutManager(manager);
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    progres.dismiss();
                    Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            progres.dismiss();
            Toast.makeText(getActivity(), getString(R.string.check_groupid), Toast.LENGTH_SHORT).show();
        }*/
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        return d;
    }



    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                RuntimeExceptionDao<Promotions, Integer> PromotionsDoa = mDbHelper.getPromotionsRuntimeDao();
                Where<Promotions, Integer> promotionsQueryBuilder = PromotionsDoa.queryBuilder().where();
                if(AppPreferences.getGroupId(getActivity())==23||AppPreferences.getGroupId(getActivity())==41||AppPreferences.getGroupId(getActivity())==39){
                    promotionsQueryBuilder.eq("routeassignmentid",AppPreferences.getRouteSalesViewid(getActivity()));
                }else{
                    promotionsQueryBuilder.and(promotionsQueryBuilder.eq("areaid",AppPreferences.getPrefAreaId(getActivity())),
                            promotionsQueryBuilder.eq("distareaid",AppPreferences.getDist_Area_Id(getActivity())));
                    //promotionsQueryBuilder.eq("zoneid",AppPreferences.getZoneId(getActivity())); added filter
                }
                PreparedQuery<Promotions> preparedQuery = promotionsQueryBuilder.prepare();
                mpromotions = PromotionsDoa.query(preparedQuery);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progres.dismiss();
            if(mpromotions.size()>0){
                Execution_Points_Rv_Adapter adapter=new Execution_Points_Rv_Adapter(mpromotions);
                LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                exe_points.setAdapter(adapter);
                exe_points.setLayoutManager(manager);
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

