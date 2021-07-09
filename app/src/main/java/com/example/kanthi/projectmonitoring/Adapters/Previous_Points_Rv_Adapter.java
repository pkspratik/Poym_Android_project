package com.example.kanthi.projectmonitoring.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.Promotions;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.SelectedLocation;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanthi on 4/25/2018.
 */

public class Previous_Points_Rv_Adapter extends RecyclerView.Adapter<Previous_Points_Rv_Adapter.ViewHolder> {

    private List<Promotions> mpromotions;
    private Context mContext;
    public AppCompatActivity activity;

    private SelectedLocation mLocation;

    public Previous_Points_Rv_Adapter(SelectedLocation location) {
        this.mpromotions=new ArrayList<>();
        mLocation=location;

    }

    public void update(List<Promotions> stringList) {
        this.mpromotions.clear();
        this.mpromotions.addAll(stringList);
        notifyDataSetChanged();
    }


    @Override
    public Previous_Points_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_previouspoints,
                parent, false);
        mContext = parent.getContext();
        return new Previous_Points_Rv_Adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.slno.setText(String.valueOf(position));
        String image_address=mpromotions.get(position).getAddress()==null?"Location Address":mpromotions.get(position).getAddress();
        String latlon="Lat :"+new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(mpromotions.get(position).getLatitude())))
                +" Long:"+new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(mpromotions.get(position).getLongitude())));

        String areaid="Areaid:"+mpromotions.get(position).getAreaid();
        String distareaid="DisAreaid:"+mpromotions.get(position).getDistareaid();

        holder.location.setText(latlon+"\n"+areaid+","+distareaid+"\n"+image_address);

        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocation.onclick(mpromotions.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mpromotions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView slno;
        TextView location;
        //TextView address;
        ImageView showimage;

        public ViewHolder(View view) {
            super(view);
            slno= (TextView) view.findViewById(R.id.exe_slno);
            location= (TextView) view.findViewById(R.id.exe_location);
        }
    }
}