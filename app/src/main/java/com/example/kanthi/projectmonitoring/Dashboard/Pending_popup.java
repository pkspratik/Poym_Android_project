package com.example.kanthi.projectmonitoring.Dashboard;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.Pending_Points_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.RouteSalesViewsAdapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.PoNumbers;
import com.example.kanthi.projectmonitoring.PoJo.Promotions;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
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
public class Pending_popup extends DialogFragment {
    private Dialog d=null;
    private RecyclerView pen_points;
    private TextView closepopup;
    private List<Surveys> msurveys;
    AvahanSqliteDbHelper mDbHelper;
    ProgressDialog progres;
    public Pending_popup() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ab=new AlertDialog.Builder(getActivity());
        View v=getActivity().getLayoutInflater().inflate(R.layout.pending_points_popup,null);
        ab.setView(v);
        mDbHelper = OpenHelperManager.getHelper(getActivity(), AvahanSqliteDbHelper.class);
        //ab.setCancelable(false);
        pen_points= v.findViewById(R.id.rv_pendingpoints);
        closepopup= v.findViewById(R.id.close_popup2);
        d=ab.create();
        progres = new ProgressDialog(getActivity());
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Please Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        new FetchDetailsFromDbTask().execute();

        /*ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getPendingSurvey(AppPreferences.getUserId(getActivity()),AppPreferences.getZoneId(getActivity()),
                AppPreferences.getPrefAreaId(getActivity()),AppPreferences.getDist_Area_Id(getActivity()),true,false);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Surveys> surveys = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Surveys>>() {
                        }.getType());
                msurveys = surveys;
                progres.dismiss();
                if(msurveys.size()>0){
                    Pending_Points_Rv_Adapter adapter=new Pending_Points_Rv_Adapter(msurveys);
                    LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                    pen_points.setLayoutManager(manager);
                    pen_points.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(getActivity(), R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });*/
        closepopup.setOnClickListener(new View.OnClickListener() {
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
                RuntimeExceptionDao<Surveys, Integer> surveyDao = mDbHelper.getSurveysRuntimeDao();
                Where<Surveys, Integer> where = surveyDao.queryBuilder().where();

                //where.eq("zoneid",AppPreferences.getZoneId(getActivity())),  added filter
                where.and(where.eq("areaid",AppPreferences.getPrefAreaId(getActivity())),
                        where.eq("distareaid",AppPreferences.getDist_Area_Id(getActivity())),
                        where.eq("pendingflag",true),
                        where.eq("deleteFlag",false));

                /*surveysQueryBuilder.where().eq("zoneid", AppPreferences.getZoneId(getActivity()));
                surveysQueryBuilder.where().eq("areaid", AppPreferences.getPrefAreaId(getActivity()));
                surveysQueryBuilder.where().eq("distareaid", AppPreferences.getDist_Area_Id(getActivity()));
                surveysQueryBuilder.where().eq("pendingflag", true);
                surveysQueryBuilder.where().eq("deleteFlag", false);
                PreparedQuery<Surveys> preparedQuery1 = surveysQueryBuilder.prepare();*/
                msurveys = where.query();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progres.dismiss();
            if(msurveys.size()>0){
                Pending_Points_Rv_Adapter adapter=new Pending_Points_Rv_Adapter(msurveys,mDbHelper);
                LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                pen_points.setLayoutManager(manager);
                pen_points.setAdapter(adapter);
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
