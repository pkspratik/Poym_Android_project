package com.example.kanthi.projectmonitoring.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;

import java.util.ArrayList;
import java.util.List;

public class Loc_Auto_Detail extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<Surveys> mFilteredUserList;
    private List<Surveys> mOriginalUserList;

    public Loc_Auto_Detail(Context context, List<Surveys> usersList) {
        mContext = context;
        mFilteredUserList = usersList;
        mOriginalUserList = usersList;
    }

    @Override
    public int getCount() {
        try {
            if (mFilteredUserList == null) {
                Log.e("LOG", "Warn, null filteredData");
                return 0;
            } else {
                return mFilteredUserList.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mFilteredUserList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.beneficiary_item, null);
        }
        TextView details = (TextView) convertView.findViewById(R.id.user_details);
        Surveys surveys = mFilteredUserList.get(position);
        details.setText(surveys.getDetail());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Surveys> FilteredArrayNames = new ArrayList<>();

                // perform your search here using the searchConstraint String.

                String query = constraint.toString().toLowerCase();

             //   String s = String.valueOf(constraint);

               // Integer quer =Integer.parseInt(query); (user.getUsername().toLowerCase().contains(query))

                for (Surveys surveys : mOriginalUserList) {
                    Log.d("", "");
                    if ((surveys.getDetail().toLowerCase().contains(query))) {
                   // if ((surveys.getSlno().equals(quer))) {

                        FilteredArrayNames.add(surveys);
                    }
                }
                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredUserList = (ArrayList<Surveys>) filterResults.values;
                if (mFilteredUserList != null) {
                    notifyDataSetChanged();
                }
            }
        };
    }
}
