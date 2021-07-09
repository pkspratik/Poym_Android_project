package com.example.kanthi.projectmonitoring.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Dashboard.ProjectMonitorActivity;
import com.example.kanthi.projectmonitoring.PoJo.AssignedItems;
import com.example.kanthi.projectmonitoring.PoJo.Inventories;
import com.example.kanthi.projectmonitoring.PoJo.ItemDefinition;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kanthi on 5/21/2018.
 */

public class AssignedItem_Rv_Adapter extends RecyclerView.Adapter<AssignedItem_Rv_Adapter.ViewHolder>{
    private Context mContext;
    private List<Inventories> minventories;
    int quantity;
    AlertDialog mdialog;

    public AssignedItem_Rv_Adapter(List<Inventories> inventories, AlertDialog dialog) {
        minventories=inventories;
        mdialog = dialog;
    }

    @Override
    public AssignedItem_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_assigned_item,
                parent, false);
        mContext=parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_name.setText(minventories.get(position).getItemname());
        quantity=Integer.parseInt(minventories.get(position).getQuantityinput())-minventories.get(position).getQuantitytransfer();
        holder.tv_qty.setText(String.valueOf(quantity));

        holder.et_qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mdialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    mdialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        holder.et_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (holder.et_qty.hasFocus()) {
                    try {
                        minventories.get(position).setEnteredquantity(Integer.parseInt(s.toString()));
                        minventories.get(position).setInventoryid(minventories.get(position).getId());
                        minventories.get(position).setItemnameid(minventories.get(holder.getAdapterPosition()).getItemdefinitionId());
                        /*if(Integer.parseInt(s.toString())<quantity){
                            minventories.get(position).setEnteredquantity(Integer.parseInt(s.toString()));
                            minventories.get(position).setInventoryid(minventories.get(position).getId());
                        }else{
                            Toast.makeText(mContext, "Plz..Enter less value", Toast.LENGTH_SHORT).show();
                        }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return minventories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_code,tv_name,tv_qty;
        EditText et_qty;
        public ViewHolder(View itemView) {
            super(itemView);
            //tv_code= (TextView) itemView.findViewById(R.id.assign_code);
            tv_name= (TextView) itemView.findViewById(R.id.assign_name);
            tv_qty= (TextView) itemView.findViewById(R.id.assign_itemqty);
            et_qty= (EditText) itemView.findViewById(R.id.assign_enter_qty);
        }
    }
}
