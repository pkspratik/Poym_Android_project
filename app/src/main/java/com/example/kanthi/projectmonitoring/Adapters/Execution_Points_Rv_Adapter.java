package com.example.kanthi.projectmonitoring.Adapters;

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
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanthi on 4/25/2018.
 */

public class Execution_Points_Rv_Adapter extends RecyclerView.Adapter<Execution_Points_Rv_Adapter.ViewHolder> {

    private List<Promotions> mpromotions;
    private Context mContext;
    public AppCompatActivity activity;

    public Execution_Points_Rv_Adapter(List<Promotions> promotions) {
        mpromotions = promotions;
    }


    @Override
    public Execution_Points_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_executionpoints,
                parent, false);
        mContext = parent.getContext();
        return new Execution_Points_Rv_Adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.slno.setText(String.valueOf(position));
        String image_address=mpromotions.get(position).getAddress()==null?"Location Address":mpromotions.get(position).getAddress();
        /*String latlon="Lat :"+new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(mpromotions.get(position).getLatitude())))
                +" Long:"+new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(mpromotions.get(position).getLongitude())));*/
        holder.location.setText(image_address);
        final String imagename=mpromotions.get(position).getRetailerimage();
        //holder.showimage.setBackgroundResource(R.drawable.ic_shg_pink);
        //holder.address.setText(mpromotions.get(position).getRetailerimage()==null?"":mpromotions.get(position).getRetailerimage());
        holder.showimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                View v1=LayoutInflater.from(mContext).inflate(R.layout.showimage_popup,null);
                builder.setView(v1);
                builder.setCancelable(false);
                PhotoView photoView = (PhotoView) v1.findViewById(R.id.photo_view);
                photoView.setVisibility(View.VISIBLE);
                TextView im_name= (TextView) v1.findViewById(R.id.displayname);
                Button close= (Button) v1.findViewById(R.id.popup_cancel);
                final AlertDialog dailog=builder.create();
                final ProgressDialog progres = new ProgressDialog(mContext);
                progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progres.setMessage("Loading,Plz Wait...");
                progres.setIndeterminate(true);
                progres.setCancelable(false);
                progres.show();
                //https://s3-ap-southeast-1.amazonaws.com/converbiz/PM.JPG
                Picasso.with(mContext).load("https://s3-ap-southeast-1.amazonaws.com/converbiz/"+imagename).into(photoView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progres.dismiss();
                    }

                    @Override
                    public void onError() {

                    }
                });
                im_name.setText(imagename);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dailog.dismiss();
                    }
                });
                dailog.show();

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
            //address= (TextView) view.findViewById(R.id.exe_address);
            showimage= (ImageView) view.findViewById(R.id.showimage);
        }
    }
}