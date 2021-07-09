package com.example.kanthi.projectmonitoring.Login;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.PoJo.User;
import com.example.kanthi.projectmonitoring.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanthi on 22-feb-18.
 */

public class UserAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<User> mFilteredUserList;
    private List<User> mOriginalUserList;

    public UserAdapter(Context context, List<User> usersList) {
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
        User user = mFilteredUserList.get(position);
        details.setText(user.getUsername());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<User> FilteredArrayNames = new ArrayList<>();

                // perform your search here using the searchConstraint String.
                String query = constraint.toString().toLowerCase();
                for (User user : mOriginalUserList) {
                    Log.d("", "");
                    if ((user.getUsername().toLowerCase().contains(query))) {
                        FilteredArrayNames.add(user);
                    }
                }
                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredUserList = (ArrayList<User>) filterResults.values;
                if (mFilteredUserList != null) {
                    notifyDataSetChanged();
                }
            }
        };
    }
}
