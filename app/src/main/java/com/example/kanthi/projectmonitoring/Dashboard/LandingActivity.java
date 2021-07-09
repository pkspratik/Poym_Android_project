package com.example.kanthi.projectmonitoring.Dashboard;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Graphs.GraphActivity;
import com.example.kanthi.projectmonitoring.Login.LoginActivity;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.Partners;
import com.example.kanthi.projectmonitoring.PoJo.Partnerviews;
import com.example.kanthi.projectmonitoring.PoJo.SalesViews;
import com.example.kanthi.projectmonitoring.ProgressView.ProgressView;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<SalesViews> msalesviews;
    List<Partnerviews> mpartners;
    private TextView field_mgr,field_code,field_location,field_desig;
    private CircleImageView profile_pic;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    View mheader;
    private TextView mheader_name,mheader_email;
    private int groupid=0,areaid=0,zoneid=0, salesmgrid=0,distareaid=0,id=0;
    private String fname=null,lastname=null,mobile=null,location=null;
    SharedPreferences sp;
    Boolean partner=false,salesview=false;
    AvahanSqliteDbHelper mDbHelper;
    Bundle bundle;
    ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        field_mgr= (TextView) findViewById(R.id.field_engineer);
        field_code= (TextView) findViewById(R.id.field_sterlite);
        field_location= (TextView) findViewById(R.id.field_location);
        field_desig= (TextView) findViewById(R.id.field_desigination);
        profile_pic= (CircleImageView) findViewById(R.id.profile_icon);
        //TODO to display user image use profile_pic
        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        bundle=intent.getExtras();
        if(bundle.getString("projecttype").equalsIgnoreCase("null")){
            new AlertDialog.Builder(this)
                    .setTitle("Please define the Project Type")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }else{
            Log.e("projecttype", bundle.getString("projecttype"));
            AppPreferences.setProjectType(LandingActivity.this,bundle.getString("projecttype"));
        }
        AppPreferences.setPartnerid(LandingActivity.this,bundle.getInt("partnerid"));
        AppPreferences.setUserType(LandingActivity.this,bundle.getString("usertype"));
        AppPreferences.setUserEmail(LandingActivity.this,bundle.getString("user_email"));
        //AppPreferences.setUser_SalesAreaId(LandingActivity.this,bundle.getInt("usersalesAreaid"));

        if (ContextCompat.checkSelfPermission(LandingActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(LandingActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(LandingActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    55);
        }

        mprogress = new ProgressDialog(LandingActivity.this);
        mprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mprogress.setMessage("Loading...");
        mprogress.setIndeterminate(true);
        mprogress.setCancelable(false);
        mprogress.show();
        new FetchDetailsFromDbTask().execute();

        /*if(bundle.getString("usertype").equalsIgnoreCase("partner")){
            final ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
            Call<String> call = service.getPartnerviews(AppPreferences.getUserId(LandingActivity.this), bundle.getInt("partnerid"));
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<Partnerviews> partners= new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<Partnerviews>>() {
                            }.getType());
                    mprogress.dismiss();
                    mpartners = partners;
                    for(int i=0;i<mpartners.size();i++){
                        AppPreferences.setFieldEngineer(LandingActivity.this,mpartners.get(i).getFname()==null?"":mpartners.get(i).getFname());
                        AppPreferences.setFieldNumber(LandingActivity.this,mpartners.get(i).getZone()==null?"":mpartners.get(i).getZone());
                        AppPreferences.setFieldLocation(LandingActivity.this,mpartners.get(i).getArea()==null?"":mpartners.get(i).getArea());
                        AppPreferences.setFieldDesignition(LandingActivity.this,mpartners.get(i).getDistarea()==null?"":mpartners.get(i).getDistarea());

                        AppPreferences.setPrefAreaId(LandingActivity.this, mpartners.get(i).getAreaid());
                        AppPreferences.setZoneId(LandingActivity.this,mpartners.get(i).getZoneid());
                        AppPreferences.setSaleMngrIdId(LandingActivity.this,mpartners.get(i).getSalesmgrid()==null?0:mpartners.get(i).getSalesmgrid());
                        AppPreferences.setDist_Area_Id(LandingActivity.this, mpartners.get(i).getDistareaid());
                        AppPreferences.setGroupId(LandingActivity.this,mpartners.get(i).getGroupid());
                        AppPreferences.setEmployeeId(LandingActivity.this,mpartners.get(i).getId());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    mprogress.dismiss();
                    Toast.makeText(LandingActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                }
            });
        } else if(bundle.getString("usertype").equalsIgnoreCase("executive")){
        final ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = service.getSalesViews(AppPreferences.getUserId(LandingActivity.this), bundle.getInt("partnerid"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<SalesViews> salesViews = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<SalesViews>>() {
                        }.getType());
                mprogress.dismiss();
                msalesviews = salesViews;
                for(int i=0;i<msalesviews.size();i++){
                    String fname=msalesviews.get(i).getFname()==null?"":msalesviews.get(i).getFname();
                    String location=msalesviews.get(i).getZone()==null?"":msalesviews.get(i).getZone();
                    String lastname=msalesviews.get(i).getArea()==null?"":msalesviews.get(i).getArea();
                    String mobile=msalesviews.get(i).getDistarea()==null?"":msalesviews.get(i).getDistarea();

                    int areaid=msalesviews.get(i).getAreaid();
                    int zoneid=msalesviews.get(i).getZoneid();
                    int salesmgrid=msalesviews.get(i).getSalesmgrid();
                    int distareaid=msalesviews.get(i).getDistareaid();
                    int groupid=msalesviews.get(i).getGroupid();
                    int id=msalesviews.get(i).getId();
                    AppPreferences.setFieldEngineer(LandingActivity.this,fname);
                    AppPreferences.setFieldNumber(LandingActivity.this,location);
                    AppPreferences.setFieldLocation(LandingActivity.this,lastname);
                    AppPreferences.setFieldDesignition(LandingActivity.this,mobile);

                    AppPreferences.setPrefAreaId(LandingActivity.this,areaid);
                    AppPreferences.setZoneId(LandingActivity.this,zoneid);
                    AppPreferences.setSaleMngrIdId(LandingActivity.this,salesmgrid);
                    AppPreferences.setDist_Area_Id(LandingActivity.this,distareaid);
                    AppPreferences.setGroupId(LandingActivity.this,groupid);
                    AppPreferences.setEmployeeId(LandingActivity.this,id);
                }

                *//*Log.d("areaid", String.valueOf(msalesviews.get(0).getAreaid()));
                Log.d("zoneid", String.valueOf(msalesviews.get(0).getZoneid()));
                Log.d("salesid", String.valueOf(msalesviews.get(0).getSalesmgrid()));
                Log.d("distareaid", String.valueOf(msalesviews.get(0).getDistareaid()));
                Log.d("groupid", String.valueOf(msalesviews.get(0).getGroupid()));
                *//*
            }
            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                t.printStackTrace();
                mprogress.dismiss();
                Toast.makeText(LandingActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
        }else if(bundle.getString("usertype").equalsIgnoreCase("null")){
            mprogress.dismiss();
            Toast.makeText(this, "Please,Define Valid User Type", Toast.LENGTH_SHORT).show();
        }*/

        field_mgr.setText(AppPreferences.getFieldEngineer(LandingActivity.this));
        field_code.setText(AppPreferences.getFieldNumber(LandingActivity.this));
        field_location.setText(AppPreferences.getFieldLocation(LandingActivity.this));
        field_desig.setText(AppPreferences.getFieldDesignition(LandingActivity.this));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //TODO disabled navigation drawer
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mheader =  navigationView.getHeaderView(0);
        mheader_name= (TextView) mheader.findViewById(R.id.header_name);
        mheader_email= (TextView) mheader.findViewById(R.id.header_email);
        mheader_name.setText(AppPreferences.getFieldEngineer(LandingActivity.this));
        mheader_email.setText(AppPreferences.getUserEmail(LandingActivity.this));

        Menu nav_Menu = navigationView.getMenu();
        if(AppPreferences.getGroupId(LandingActivity.this)==29){
            getSupportActionBar().setTitle("Survey");
            nav_Menu.findItem(R.id.nav_qualitycheck).setEnabled(false);
            nav_Menu.findItem(R.id.nav_execution).setEnabled(false);
        }
        else if(AppPreferences.getGroupId(LandingActivity.this)==14||AppPreferences.getGroupId(LandingActivity.this)==17||AppPreferences.getGroupId(LandingActivity.this)==18){
            getSupportActionBar().setTitle("Execution");
            nav_Menu.findItem(R.id.nav_qualitycheck).setEnabled(false);
            nav_Menu.findItem(R.id.nav_survey).setEnabled(false);
        }
        else if(AppPreferences.getGroupId(LandingActivity.this)==23||AppPreferences.getGroupId(LandingActivity.this)==41||AppPreferences.getGroupId(LandingActivity.this)==39){
            getSupportActionBar().setTitle("Quality Check");
            nav_Menu.findItem(R.id.nav_survey).setEnabled(false);
            nav_Menu.findItem(R.id.nav_execution).setEnabled(false);
        }
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==55){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(getApplicationContext(), "Location Permission Denied", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    private void setupViewPager(ViewPager viewPager) {
        if(AppPreferences.getGroupId(LandingActivity.this)==14||AppPreferences.getGroupId(LandingActivity.this)==29||AppPreferences.getGroupId(LandingActivity.this)==17||AppPreferences.getGroupId(LandingActivity.this)==18){
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new WeeklyTaskFrag(), "Pending");
            adapter.addFragment(new RouteAssignFrag(), "Completed");
            viewPager.setAdapter(adapter);
        }
        else if(AppPreferences.getGroupId(LandingActivity.this)==23||AppPreferences.getGroupId(LandingActivity.this)==41||AppPreferences.getGroupId(LandingActivity.this)==39){
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new Current_Frag(), "Current");
            adapter.addFragment(new Future_Frag(), "Completed");
            viewPager.setAdapter(adapter);
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent onBack=new Intent(LandingActivity.this,RouteAssignmentSummaryActivity.class);
        onBack.putExtra("partnerid",bundle.getInt("partnerid"));
        onBack.putExtra("usertype",bundle.getString("usertype"));
        onBack.putExtra("projecttype",bundle.getString("usertype"));
        onBack.putExtra("user_email",bundle.getString("user_email"));
        startActivity(onBack);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            // Handle the logout action
            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
            startActivity(intent);
        } else if (id == R.id.nav_survey) {

        } else if (id == R.id.nav_execution) {

        } else if (id == R.id.nav_qualitycheck) {

        }
        else if (id == R.id.nav_projectrisk) {
            Intent in=new Intent(LandingActivity.this,ProjectRisk.class);
            startActivity(in);
        }
        else if (id == R.id.nav_changerequest) {
            Intent in=new Intent(LandingActivity.this,ChangeRequest.class);
            startActivity(in);
        }else if (id == R.id.nav_DashBoard) {
            Intent in=new Intent(LandingActivity.this,GraphActivity.class);
            startActivity(in);
        }else if (id == R.id.nav_Progress_view) {
            Intent in = new Intent(LandingActivity.this, ProgressView.class);
            startActivity(in);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            if(bundle.getString("usertype").equalsIgnoreCase("partner")){
                try{
                    RuntimeExceptionDao<Partnerviews, Integer> partnerviewsDao = mDbHelper.getPartnerviewsRuntimeDao();
                    QueryBuilder<Partnerviews, Integer> partnerviewsqueryBuilder = partnerviewsDao.queryBuilder();
                    partnerviewsqueryBuilder.where().eq("id", bundle.getInt("partnerid"));
                    PreparedQuery<Partnerviews> preparedQuery = partnerviewsqueryBuilder.prepare();
                    mpartners = partnerviewsDao.query(preparedQuery);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(bundle.getString("usertype").equalsIgnoreCase("executive")){
                try{
                    RuntimeExceptionDao<SalesViews, Integer> saleviewsDao = mDbHelper.getSalesViewsRuntimeDao();
                    QueryBuilder<SalesViews, Integer> salesViewsQueryBuilder = saleviewsDao.queryBuilder();
                    salesViewsQueryBuilder.where().eq("id", bundle.getInt("partnerid"));
                    PreparedQuery<SalesViews> preparedQuery = salesViewsQueryBuilder.prepare();
                    msalesviews = saleviewsDao.query(preparedQuery);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mprogress.dismiss();
            if(bundle.getString("usertype").equalsIgnoreCase("partner")){
                for(int i=0;i<mpartners.size();i++){
                    AppPreferences.setFieldEngineer(LandingActivity.this,mpartners.get(i).getFname()==null?"":mpartners.get(i).getFname());
                    AppPreferences.setFieldNumber(LandingActivity.this,mpartners.get(i).getZone()==null?"":mpartners.get(i).getZone());
                    AppPreferences.setFieldLocation(LandingActivity.this,mpartners.get(i).getArea()==null?"":mpartners.get(i).getArea());
                    AppPreferences.setFieldDesignition(LandingActivity.this,mpartners.get(i).getDistarea()==null?"":mpartners.get(i).getDistarea());

                    AppPreferences.setPrefAreaId(LandingActivity.this, mpartners.get(i).getAreaid());
                    AppPreferences.setZoneId(LandingActivity.this,mpartners.get(i).getZoneid());
                    AppPreferences.setSaleMngrIdId(LandingActivity.this,mpartners.get(i).getSalesmgrid()==null?0:mpartners.get(i).getSalesmgrid());
                    AppPreferences.setDist_Area_Id(LandingActivity.this, mpartners.get(i).getDistareaid());
                    AppPreferences.setGroupId(LandingActivity.this,mpartners.get(i).getGroupid());
                    AppPreferences.setEmployeeId(LandingActivity.this,mpartners.get(i).getId());
                }
            }else if(bundle.getString("usertype").equalsIgnoreCase("executive")){
                for(int i=0;i<msalesviews.size();i++){
                    String fname=msalesviews.get(i).getFname()==null?"":msalesviews.get(i).getFname();
                    String location=msalesviews.get(i).getZone()==null?"":msalesviews.get(i).getZone();
                    String lastname=msalesviews.get(i).getArea()==null?"":msalesviews.get(i).getArea();
                    String mobile=msalesviews.get(i).getDistarea()==null?"":msalesviews.get(i).getDistarea();

                    int areaid=msalesviews.get(i).getAreaid();
                    int zoneid=msalesviews.get(i).getZoneid();
                    int salesmgrid=msalesviews.get(i).getSalesmgrid();
                    int distareaid=msalesviews.get(i).getDistareaid();
                    int groupid=msalesviews.get(i).getGroupid();
                    int id=msalesviews.get(i).getId();
                    AppPreferences.setFieldEngineer(LandingActivity.this,fname);
                    AppPreferences.setFieldNumber(LandingActivity.this,location);
                    AppPreferences.setFieldLocation(LandingActivity.this,lastname);
                    AppPreferences.setFieldDesignition(LandingActivity.this,mobile);

                    AppPreferences.setPrefAreaId(LandingActivity.this,areaid);
                    AppPreferences.setZoneId(LandingActivity.this,zoneid);
                    AppPreferences.setSaleMngrIdId(LandingActivity.this,salesmgrid);
                    AppPreferences.setDist_Area_Id(LandingActivity.this,distareaid);
                    AppPreferences.setGroupId(LandingActivity.this,groupid);
                    AppPreferences.setEmployeeId(LandingActivity.this,id);
                }
            }

        }
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        mDbHelper = null;
        super.onDestroy();
    }
}
