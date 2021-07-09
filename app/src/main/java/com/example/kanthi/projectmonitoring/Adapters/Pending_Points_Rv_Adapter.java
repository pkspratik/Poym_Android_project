package com.example.kanthi.projectmonitoring.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Dashboard.StatusUpdate;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kanthi on 4/29/2018.
 */

public class Pending_Points_Rv_Adapter extends RecyclerView.Adapter<Pending_Points_Rv_Adapter.ViewHolder>{
    private List<Surveys> msurveys;
    private Context mContext;
    public AppCompatActivity activity;

    private AvahanSqliteDbHelper dbHelper;

    int pointid;
    public Pending_Points_Rv_Adapter(List<Surveys> surveys, AvahanSqliteDbHelper mDbHelper) {
        msurveys = surveys;
        dbHelper=mDbHelper;
    }

    @Override
    public Pending_Points_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_pendingpoints,
                parent, false);
        mContext = parent.getContext();
        return new Pending_Points_Rv_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Pending_Points_Rv_Adapter.ViewHolder holder, final int position) {
        holder.slno.setText(String.valueOf(position));
        String fromadd=msurveys.get(position).getFromaddress()==null?"point A":msurveys.get(position).getFromaddress();
        String toadd=msurveys.get(position).getToaddress()==null?"Point B":msurveys.get(position).getToaddress();
        String lat1="Lat:"+new DecimalFormat("##.##").format(Float.parseFloat(String.valueOf(msurveys.get(position).getLatitude())));
        String long1="Long:"+new DecimalFormat("##.##").format(Float.parseFloat(String.valueOf(msurveys.get(position).getLongitude())));
        String lat2="Lat:"+new DecimalFormat("##.##").format(Float.parseFloat(String.valueOf(msurveys.get(position).getToLatitude())));
        String long2="Long:"+new DecimalFormat("##.##").format(Float.parseFloat(String.valueOf(msurveys.get(position).getToLongitude())));
        String distance=new DecimalFormat("##.#").format(Float.parseFloat(String.valueOf(msurveys.get(position).getDistance())));
        /*String lat1="Lat:"+String.valueOf(msurveys.get(position).getLatitude());
        String long1=" Long:"+String.valueOf(msurveys.get(position).getLongitude());
        String lat2="Lat:"+String.valueOf(msurveys.get(position).getToLatitude());
        String long2=" Long:"+String.valueOf(msurveys.get(position).getToLongitude());
        String distance=String.valueOf(msurveys.get(position).getDistance());*/
        holder.lat1.setText(fromadd);
        //holder.long1.setText(long1);
        holder.lat2.setText(toadd);
        //holder.long2.setText(long2);
        holder.distance.setText(distance+" m");
        holder.distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //msurveys.remove(position);
                //notifyDataSetChanged();
                android.app.AlertDialog.Builder builder1=new android.app.AlertDialog.Builder(mContext);
                View v1 = LayoutInflater.from(mContext).inflate(R.layout.confirmpopup, null);
                builder1.setView(v1);
                TextView tv_message= (TextView) v1.findViewById(R.id.confirm_message);
                Button bt_ok= (Button) v1.findViewById(R.id.confirm_ok);
                Button bt_cancel= (Button) v1.findViewById(R.id.confirm_cancel);
                final android.app.AlertDialog mDialog = builder1.create();
                bt_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pointid= (int) msurveys.get(position).getId();
                        if(pointid!=0){
                            Toast.makeText(mContext, ""+pointid, Toast.LENGTH_SHORT).show();
                            //putSurveys();
                            try {
                                RuntimeExceptionDao<Surveys,Integer> surveysDao = dbHelper.getSurveysRuntimeDao();
                                UpdateBuilder<Surveys,Integer> surveysIntegerUpdate=surveysDao.updateBuilder();
                                surveysIntegerUpdate.where().eq("id",pointid);
                                surveysIntegerUpdate.updateColumnValue("deleteFlag","true");
                                surveysIntegerUpdate.updateColumnValue("updateflag",true);
                                surveysIntegerUpdate.update();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            msurveys.remove(position);
                            notifyDataSetChanged();
                            mDialog.dismiss();
                        }
                    }
                });
                bt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
                tv_message.setText("Are you sure to remove this point.?");
                builder1.setCancelable(false);
                mDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return msurveys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView slno;
        TextView lat1;
        //TextView long1;
        TextView lat2;
        //TextView long2;
        TextView distance;
        public ViewHolder(View view) {
            super(view);
            slno= (TextView) view.findViewById(R.id.pen_slno);
            lat1= (TextView) view.findViewById(R.id.pointlat1);
            //long1= (TextView) view.findViewById(R.id.pointlong1);
            lat2= (TextView) view.findViewById(R.id.pointlat2);
            //long2= (TextView) view.findViewById(R.id.pointlong2);
            distance= (TextView) view.findViewById(R.id.pointdistance);
        }
    }
    public void putSurveys(){
        final ProgressDialog progress1 = new ProgressDialog(mContext);
        progress1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress1.setMessage("Loading..");
        progress1.setIndeterminate(true);
        progress1.setCancelable(false);
        progress1.show();
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson= new JsonObject();
        try {
            benjson.addProperty("deleteFlag","true");
            benjson.addProperty("id",pointid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        call = service.updateSurveys(AppPreferences.getUserId(mContext), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    progress1.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
