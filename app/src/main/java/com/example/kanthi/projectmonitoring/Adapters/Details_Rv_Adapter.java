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

import com.example.kanthi.projectmonitoring.PoJo.Details;
import com.example.kanthi.projectmonitoring.PoJo.ParamDetails;
import com.example.kanthi.projectmonitoring.PoJo.TaskResourceLinkViews;
import com.example.kanthi.projectmonitoring.R;

import java.util.ArrayList;

/**
 * Created by Kanthi on 3/13/2018.
 */

public class Details_Rv_Adapter extends RecyclerView.Adapter<Details_Rv_Adapter.ViewHolder> {

    private ArrayList<ParamDetails> mdetailses;
    private Context mContext;

    public Details_Rv_Adapter(ArrayList<ParamDetails> details) {
        mdetailses=details;
    }


    @Override
    public Details_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_popup,
                parent, false);
        mContext = parent.getContext();
        return new Details_Rv_Adapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.parameter.setText(mdetailses.get(position).getParametervalue());
        holder.value.setText(mdetailses.get(position).getDescription());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdetailses.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdetailses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView parameter;
        TextView value;
        TextView remove;

        public ViewHolder(View view) {
            super(view);
            parameter= (TextView) view.findViewById(R.id.parameter);
            value= (TextView) view.findViewById(R.id.value);
            remove= (TextView) view.findViewById(R.id.remove_data);
        }
    }
}

