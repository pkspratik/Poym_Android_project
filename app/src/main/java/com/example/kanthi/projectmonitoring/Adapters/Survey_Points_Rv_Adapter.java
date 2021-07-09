package com.example.kanthi.projectmonitoring.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.Dashboard.Survey;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.PoJo.TaskResourceLinkViews;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kanthi on 4/24/2018.
 */

public class Survey_Points_Rv_Adapter extends RecyclerView.Adapter<Survey_Points_Rv_Adapter.ViewHolder> {

    private List<Surveys> msurveys;
    private Context mContext;
    public AppCompatActivity activity;
    List<String> mgeoadd;

    public Survey_Points_Rv_Adapter(List<Surveys> surveys) {
        msurveys = surveys;
        //mgeoadd=geoaddress;
    }


    @Override
    public Survey_Points_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_survey_points,
                parent, false);
        mContext = parent.getContext();
        return new Survey_Points_Rv_Adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.slno.setText(String.valueOf(position));
        String latlon="Lat :"+new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(msurveys.get(position).getLatitude())))
                +" Long:"+new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(msurveys.get(position).getLongitude())));
        holder.location.setText(latlon);
        holder.address.setText(msurveys.get(position).getLandmark()==null?"":msurveys.get(position).getLandmark());
    }
    @Override
    public int getItemCount() {
        return msurveys.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView slno;
        TextView location;
        TextView address;

        public ViewHolder(View view) {
            super(view);
            slno= (TextView) view.findViewById(R.id.points_slno);
            location= (TextView) view.findViewById(R.id.points_location);
            address= (TextView) view.findViewById(R.id.points_address);
        }
    }
}
