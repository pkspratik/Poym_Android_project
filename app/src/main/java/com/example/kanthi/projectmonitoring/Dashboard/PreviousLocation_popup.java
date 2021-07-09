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
import android.widget.Button;

import com.example.kanthi.projectmonitoring.Adapters.Previous_Points_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.PoJo.Promotions;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.SelectedLocation;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.Where;

import java.util.List;

/**
 * Created by Kanthi on 3/19/2018.
 */

public class PreviousLocation_popup extends DialogFragment implements SelectedLocation{
    Dialog d=null;
    ProgressDialog progres;
    RecyclerView loc_points;

    private Button bt_cancel;
    private List<Promotions> mpromotions;
    AvahanSqliteDbHelper mDbHelper;
    Previous_Points_Rv_Adapter adapter;
    public PreviousLocation_popup() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ab=new AlertDialog.Builder(getActivity());
        View v=getActivity().getLayoutInflater().inflate(R.layout.previouslocation_popup,null);
        ab.setView(v);
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        loc_points= (RecyclerView) v.findViewById(R.id.rv_locationpoints);
        bt_cancel = v.findViewById(R.id.popup_cancel);
        d=ab.create();
        progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        new FetchDetailsFromDbTask().execute();

        adapter=new Previous_Points_Rv_Adapter( this);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        loc_points.setAdapter(adapter);
        loc_points.setLayoutManager(manager);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppPreferences.setPreviousLocationId(getActivity(),0);
                AppPreferences.setPreviousLocationFlag(getActivity(),false);
                d.dismiss();
            }
        });

        return d;
    }

    @Override
    public void onclick(Promotions promotions) {
        //Toast.makeText(getActivity(), ""+id, Toast.LENGTH_SHORT).show();
        AppPreferences.setPreviousLocationId(getActivity(),promotions.getId());
        AppPreferences.setPreviousLocationFlag(getActivity(),promotions.getInsertFlag());
        d.dismiss();
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
                adapter.update(mpromotions);
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

