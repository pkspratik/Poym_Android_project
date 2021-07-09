package com.example.kanthi.projectmonitoring.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.TaskItemLinkView;
import com.example.kanthi.projectmonitoring.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kanthi on 12/20/2017.
 */

public class AdditemRV_Adapter extends RecyclerView.Adapter<AdditemRV_Adapter.ViewHolder> {
    private List<TaskItemLinkView> mAdditem;
    private Context mContext;
    public ArrayList<TaskItemLinkView> list;
    AlertDialog dialog;
    public static String edtqty="";

    public AdditemRV_Adapter(Context context, List<TaskItemLinkView> shgMeetingList,
                             AlertDialog dilog) {
        mAdditem = shgMeetingList;
        mContext = context;
        this.dialog = dilog;
    }

    @Override
    public AdditemRV_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.additem_rvlayout,
                parent, false);
        mContext = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        list=new ArrayList<TaskItemLinkView>();

        holder.name.setText(mAdditem.get(position).getItemname()!=null?mAdditem.get(position).getItemname():"");
        holder.price.setText(mAdditem.get(position).getUnitprice()!=null?String.valueOf("MRP - "+mAdditem.get(position).getUnitprice()):"MRP - ");
        holder.et_qty.setText(mAdditem.get(holder.getAdapterPosition()).getQuantity()!=null?"": String.valueOf(mAdditem.get(holder.getAdapterPosition()).getQuantity()));
        //add this lime for empty
       // holder.et_qty.setText(" ");
        holder.et_qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                        mAdditem.get(position).setQuantity(Integer.parseInt(s.toString()));
                        mAdditem.get(position).setItemid(mAdditem.get(position).getItemid());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAdditem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public TextView flavour;

        public TextView price;

        public TextView name;

        public EditText et_qty;

        //public Spinner sp_multipack;

        //public Spinner sp_packets;

        //public LinearLayout additem;

        public ViewHolder(View view) {
            super(view);
            //flavour= (TextView) view.findViewById(R.id.flavour);
            price= (TextView) view.findViewById(R.id.price);
            name= (TextView) view.findViewById(R.id.name);
            et_qty= (EditText) view.findViewById(R.id.qty1);

            edtqty=et_qty.getText().toString();



            //sp_multipack= (Spinner) view.findViewById(R.id.sp_multipackets);
            //sp_packets= (Spinner) view.findViewById(R.id.sp_packets);
            //additem= (LinearLayout) view.findViewById(R.id.additem);
        }
    }
}

