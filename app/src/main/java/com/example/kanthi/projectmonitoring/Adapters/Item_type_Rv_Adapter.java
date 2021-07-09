package com.example.kanthi.projectmonitoring.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.example.kanthi.projectmonitoring.Dashboard.Survey;
import com.example.kanthi.projectmonitoring.PoJo.ItemDefinition;
import com.example.kanthi.projectmonitoring.PoJo.ItemSubTypes;
import com.example.kanthi.projectmonitoring.PoJo.ItemType;
import com.example.kanthi.projectmonitoring.R;

import java.util.List;

/**
 * Created by Kanthi on 1/23/2018.
 */

public class Item_type_Rv_Adapter extends RecyclerView.Adapter<Item_type_Rv_Adapter .ViewHolder> {

    private List<ItemType> mitemtypes;
    private List<ItemDefinition> mitemsubtypes;
    private Context mContext;
    private String mStr_SelectedItemTypes = "";
    public Survey mob;

    public Item_type_Rv_Adapter(List<ItemType> itemTypes, List<ItemDefinition> itemsubTypes, Survey ob) {
        mitemtypes = itemTypes;
        mitemsubtypes=itemsubTypes;
        mob=ob;
    }

    @Override
    public Item_type_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_follow_ups_recycler_view,
                parent, false);
        mContext = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.itemtype.setText(mitemtypes.get(position).getName());
        holder.itemtype.setChecked(mitemtypes.get(position).isselected());
        if (mitemtypes.get(position).isselected()){
            holder.itemtype.setCheckMarkDrawable(R.drawable.ic_check_black_24dp);
        }
        else{
            holder.itemtype.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
        }
        holder.itemtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mitemtypes.get(position).setIsselected(mitemtypes.get(position).isselected()?false:true);
                holder.itemtype.setChecked(mitemtypes.get(position).isselected());
                String selectedtypes = "";
                for(int i=0;i<mitemtypes.size();i++){
                    if(mitemtypes.get(i).isselected()){
                        selectedtypes = selectedtypes.length()==0? String.valueOf(mitemtypes.get(i).getId())
                                :selectedtypes+","+ String.valueOf(mitemtypes.get(i).getId());
                    }
                }
                mob.ItemTypeCallingAdapter(Integer.parseInt(selectedtypes),position);
                if (mitemtypes.get(position).isselected()){
                    holder.itemtype.setCheckMarkDrawable(R.drawable.ic_check_black_24dp);
                }
                else{
                    holder.itemtype.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                    if(selectedtypes.equalsIgnoreCase("")){
                        mob.ItemTypeEmptyAdapter();
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mitemtypes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CheckedTextView itemtype;

        public ViewHolder(View view) {
            super(view);
            itemtype= (CheckedTextView) view.findViewById(R.id.tv_item_type);
        }
    }
}
