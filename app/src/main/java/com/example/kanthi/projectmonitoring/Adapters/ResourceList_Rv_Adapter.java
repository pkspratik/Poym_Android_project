package com.example.kanthi.projectmonitoring.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.TaskResourceLinkViews;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kanthi on 3/1/2018.
 */

public class ResourceList_Rv_Adapter extends RecyclerView.Adapter<ResourceList_Rv_Adapter.ViewHolder> {

    private List<TaskResourceLinkViews> mtaskResourceLinkViewses;
    private Context mContext;
    public AppCompatActivity activity;
    private AlertDialog mdialog;

    public ResourceList_Rv_Adapter(List<TaskResourceLinkViews> taskResourceLinkViewses, AlertDialog dialog) {
        mtaskResourceLinkViewses = taskResourceLinkViewses;
        mdialog=dialog;
    }

    @Override
    public ResourceList_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_resource_list,
                parent, false);
        mContext = parent.getContext();
        return new ResourceList_Rv_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mappartner.setText(mtaskResourceLinkViewses.get(position).getResourcetype()==null?"":mtaskResourceLinkViewses.get(position).getResourcetype());
        if(AppPreferences.getGroupId(mContext)==14||AppPreferences.getGroupId(mContext)==17||AppPreferences.getGroupId(mContext)==18){
            holder.act_qty.setVisibility(View.VISIBLE);
            holder.res_qty.setText(mtaskResourceLinkViewses.get(position).getNumberofresources()==null?"":String.valueOf(mtaskResourceLinkViewses.get(position).getNumberofresources()));
            holder.act_qty.setText(mtaskResourceLinkViewses.get(position).getActualofresources()==null?"":String.valueOf(mtaskResourceLinkViewses.get(position).getActualofresources()));
            Log.d("actual", String.valueOf(mtaskResourceLinkViewses.get(position).getActualofresources()));
        }
        else if(AppPreferences.getGroupId(mContext)==23||AppPreferences.getGroupId(mContext)==41||AppPreferences.getGroupId(mContext)==39){
            holder.res_approved.setVisibility(View.VISIBLE);
            String noofresource=mtaskResourceLinkViewses.get(position).getNumberofresources()==null?"":String.valueOf(mtaskResourceLinkViewses.get(position).getNumberofresources());
            String enteredqty= String.valueOf(mtaskResourceLinkViewses.get(position).getEnteredqty()==null?"":mtaskResourceLinkViewses.get(position).getEnteredqty());
            holder.res_qty.setText(noofresource+"/"+enteredqty);
            Log.d("resourceqatime", String.valueOf(mtaskResourceLinkViewses.get(position).getQatime()));
            if(mtaskResourceLinkViewses.get(position).getQatime()!=null){
                holder.date_layout.setVisibility(View.VISIBLE);
                holder.res_approved.setChecked(true);
                holder.tv_qadate.setText(AppUtilities.getDateWithTimeString(mtaskResourceLinkViewses.get(position).getQatime()));
                //holder.res_approved.setEnabled(false);
            }
        }
        holder.res_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    mtaskResourceLinkViewses.get(position).setResourceapproved(AppUtilities.getDateTime());
                }
            }
        });
        holder.act_qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mdialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    mdialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        holder.act_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (holder.act_qty.hasFocus()) {
                    try {
                        mtaskResourceLinkViewses.get(position).setActualofresources(Integer.parseInt(s.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mtaskResourceLinkViewses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mappartner;
        TextView res_qty,tv_qadate;
        EditText act_qty;
        CheckBox res_approved;
        LinearLayout date_layout;

        public ViewHolder(View view) {
            super(view);
            mappartner= (TextView) view.findViewById(R.id.mappartner);
            res_qty= (TextView) view.findViewById(R.id.res_qty);
            act_qty= (EditText) view.findViewById(R.id.act_qty);
            res_approved=view.findViewById(R.id.cb_resource_approved);
            date_layout=itemView.findViewById(R.id.datelayout);
            tv_qadate=itemView.findViewById(R.id.qa_date);
        }
    }
}
