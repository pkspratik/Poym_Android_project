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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.Dashboard.ClickInterface;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.PoJo.ChangeReqViews;
import com.example.kanthi.projectmonitoring.PoJo.ChangeRequests;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanthi on 4/30/2018.
 */

public class Change_ReqOpen_Rv_Adapter extends RecyclerView.Adapter<Change_ReqOpen_Rv_Adapter.ViewHolder> {

    private List<ChangeRequests> mchange;
    private Context mContext;
    public AppCompatActivity activity;
    //private ArrayList<Priority> mpriorities;
    String tv_reqno,tv_priotity,tv_cat,tv_desc;
    private List<ChangeReqViews> mchangeReqViews;
    private ClickInterface mlistner;

    public Change_ReqOpen_Rv_Adapter(List<ChangeRequests> surveys, ClickInterface listner) {
        mchange = surveys;
        mlistner=listner;
        //mpriorities=priorities;
    }


    @Override
    public Change_ReqOpen_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_change_req_open,
                parent, false);
        mContext = parent.getContext();
        return new Change_ReqOpen_Rv_Adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.no.setText(mchange.get(position).getCategoryname());
        holder.priority.setText(mchange.get(position).getPriorityname());
        //holder.priority.setText(String.valueOf(mchange.get(position).getPriorityId()==null?"":mchange.get(position).getPriorityId()));
        String date=AppUtilities.getDateWithTimeString(mchange.get(position).getRequestDate()==null?"":mchange.get(position).getRequestDate());
        holder.date.setText(date);
        //final int tv_id=mchange.get(position).getId();
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistner.recyclerviewOnClick(holder.getAdapterPosition());
                notifyDataSetChanged();
                /*final ProgressDialog progress = new ProgressDialog(mContext);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setMessage("Loading...");
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();
                ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
                Call<String> call1 = service1.getChangeRequestViews(AppPreferences.getUserId(mContext),AppPreferences.getZoneId(mContext),
                        AppPreferences.getPrefAreaId(mContext)
                        , AppPreferences.getDist_Area_Id(mContext),tv_id);
                call1.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        ArrayList<ChangeReqViews> reqviews = new Gson().fromJson(response.body(),
                                new TypeToken<ArrayList<ChangeReqViews>>() {
                                }.getType());
                        mchangeReqViews = reqviews;
                        progress.dismiss();
                        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                        View vi=LayoutInflater.from(mContext).inflate(R.layout.changereq_popup,null);
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
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                    }
                });*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mchange.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView no;
        TextView priority;
        TextView date;
        LinearLayout layout;
        public ViewHolder(View view) {
            super(view);
            no= (TextView) view.findViewById(R.id.changereq_no);
            priority= (TextView) view.findViewById(R.id.priority);
            date= (TextView) view.findViewById(R.id.date);
            layout= (LinearLayout) view.findViewById(R.id.changereq_layout);
        }
    }
}