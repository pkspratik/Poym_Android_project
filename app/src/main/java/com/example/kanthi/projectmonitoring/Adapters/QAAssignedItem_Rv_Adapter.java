package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.AssignedItems;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;

import java.util.List;

/**
 * Created by Kanthi on 5/21/2018.
 */

public class QAAssignedItem_Rv_Adapter extends RecyclerView.Adapter<QAAssignedItem_Rv_Adapter.ViewHolder>{
    private Context mContext;
    private List<AssignedItems> massignedItems;
    int quantity;

    public QAAssignedItem_Rv_Adapter(List<AssignedItems> assignedItems) {
        massignedItems=assignedItems;
    }

    @Override
    public QAAssignedItem_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_qaassigned_item,
                parent, false);
        mContext=parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_name.setText(massignedItems.get(position).getItemname());
        holder.tv_qty.setText(massignedItems.get(position).getQuantityinput()+"/"+massignedItems.get(position).getQuantitytransfer());
        if(massignedItems.get(position).getQatime()!=null){
            holder.date_layout.setVisibility(View.VISIBLE);
            holder.cb_boq_approved.setChecked(true);
            holder.tv_qadate.setText(AppUtilities.getDateWithTimeString(massignedItems.get(position).getQatime()));
        }
        holder.cb_boq_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    massignedItems.get(position).setItemapproved(AppUtilities.getDateTime());
                    massignedItems.get(position).setItemid((int) massignedItems.get(position).getId());
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return massignedItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_code,tv_name,tv_qty,tv_qadate;
        CheckBox cb_boq_approved;
        LinearLayout date_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            //tv_code= (TextView) itemView.findViewById(R.id.assign_code);
            tv_name= (TextView) itemView.findViewById(R.id.assign_name);
            tv_qty= (TextView) itemView.findViewById(R.id.assign_itemqty);
            cb_boq_approved=itemView.findViewById(R.id.cb_boq_approved);
            date_layout=itemView.findViewById(R.id.datelayout);
            tv_qadate=itemView.findViewById(R.id.qa_date);
        }
    }
}
