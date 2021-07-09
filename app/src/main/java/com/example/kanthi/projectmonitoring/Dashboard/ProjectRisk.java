package com.example.kanthi.projectmonitoring.Dashboard;

import android.app.DatePickerDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProjectRisk extends AppCompatActivity {
    private TextView field_mgr,field_code,field_location,field_desig;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_risk);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        field_mgr= (TextView) findViewById(R.id.field_engineer);
        field_code= (TextView) findViewById(R.id.field_sterlite);
        field_location= (TextView) findViewById(R.id.field_location);
        field_desig= (TextView) findViewById(R.id.field_desigination);
        getSupportActionBar().setTitle("Project Risk");
        field_mgr.setText(AppPreferences.getFieldEngineer(ProjectRisk.this));
        field_code.setText(AppPreferences.getFieldNumber(ProjectRisk.this));
        field_location.setText(AppPreferences.getFieldLocation(ProjectRisk.this));
        field_desig.setText(AppPreferences.getFieldDesignition(ProjectRisk.this));
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProjectRiskFrag(), "Open");
        adapter.addFragment(new ProjectRiskCloseFrag(), "Closed");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

