package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.example.kanthi.projectmonitoring.Dashboard.ProjectMonitorActivity;
import com.example.kanthi.projectmonitoring.Dashboard.Survey;
import com.example.kanthi.projectmonitoring.PoJo.ItemType;
import com.example.kanthi.projectmonitoring.PoJo.ItemsCategory;
import com.example.kanthi.projectmonitoring.R;
import java.util.List;
import butterknife.ButterKnife;

/**
 * Created by Kanthi on 2/7/2018.
 */

public class ItemCategory_Rv_Adapter extends RecyclerView.Adapter<ItemCategory_Rv_Adapter.ViewHolder> {

    private List<ItemsCategory> mitemcategory;
    private List<ItemType> mitemtypes;
    public Survey mbookings;

    private Context mContext;

    public ItemCategory_Rv_Adapter(List<ItemsCategory> itemcategory, List<ItemType> itemTypes, Survey bookings) {
        mitemcategory = itemcategory;
        mitemtypes=itemTypes;
        mbookings=bookings;
    }

    @Override
    public ItemCategory_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_followup,
                parent, false);
        mContext = parent.getContext();
        return new ItemCategory_Rv_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemCategory_Rv_Adapter.ViewHolder holder, final int position) {
        holder.item_category.setText(mitemcategory.get(position).getName());
        holder.item_category.setChecked(mitemcategory.get(position).isselected());
        if (mitemcategory.get(position).isselected()){
            holder.item_category.setCheckMarkDrawable(R.drawable.ic_check_black_24dp);
        }
        else{
            holder.item_category.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
        }
        holder.item_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mitemcategory.get(position).setIsselected(mitemcategory.get(position).isselected()?false:true);
                holder.item_category.setChecked(mitemcategory.get(position).isselected());
                String selectedCategories = "";
                for(int i=0;i<mitemcategory.size();i++){
                    if(mitemcategory.get(i).isselected()){
                        selectedCategories = selectedCategories.length()==0? String.valueOf(mitemcategory.get(i).getId())
                                :selectedCategories+","+ String.valueOf(mitemcategory.get(i).getId());
                    }
                }
                mbookings.ItemCategoryCallingAdapter(selectedCategories,position);
                if (mitemcategory.get(position).isselected()){
                    holder.item_category.setCheckMarkDrawable(R.drawable.ic_check_black_24dp);
                }
                else{
                    holder.item_category.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                    if(selectedCategories.equalsIgnoreCase("")) {
                        mbookings.ItemCategoryEmptyAdapter();
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mitemcategory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckedTextView item_category;

        public ViewHolder(View view) {
            super(view);
            item_category= (CheckedTextView) view.findViewById(R.id.title2);
        }
    }
}
