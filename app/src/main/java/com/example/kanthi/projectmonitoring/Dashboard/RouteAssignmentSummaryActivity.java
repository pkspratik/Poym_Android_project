package com.example.kanthi.projectmonitoring.Dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.RouteAssignmentSummary_RV_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Graphs.GraphActivity;
import com.example.kanthi.projectmonitoring.Login.LoginActivity;
import com.example.kanthi.projectmonitoring.Network.CustomResultReceiver;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Patroling.Patroling_Activity;
import com.example.kanthi.projectmonitoring.PoJo.ActualDays;
import com.example.kanthi.projectmonitoring.PoJo.Days;
import com.example.kanthi.projectmonitoring.PoJo.Partnerviews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.SalesViews;
import com.example.kanthi.projectmonitoring.PoJo.User;
import com.example.kanthi.projectmonitoring.ProgressView.ProgressView;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.example.kanthi.projectmonitoring.services.SyncInService;
import com.example.kanthi.projectmonitoring.services.SyncOutService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteAssignmentSummaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,CustomResultReceiver.Receiver {

    private RecyclerView recyclerView;
    List<RouteSalesViews> mListRouteSalesView;
    List<RouteAssignmentSummariesViews> mrouteAssignmentSummaryViewses;
    private RouteAssignmentSummary_RV_Adapter adapter;
    ArrayList<Days> al;
    ArrayList<ActualDays> ad;
    LinearLayoutManager layoutManager;
    private TextView field_mgr,field_code,field_location,field_desig;
    private CircleImageView profile_pic;
    List<SalesViews> msalesviews;
    List<Partnerviews> mpartners;
    private TextView mheader_name,mheader_email;

    // i am added new line here
//    Intent inten = getIntent();
//    Bundle submitpress=inten.getExtras();
    // i am added new line here

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    public ProgressDialog mProgressDialog;

    private int STORAGE_PERMISSION_CODE = 23;

    AvahanSqliteDbHelper mDbHelper;
    private ProgressDialog progressDialog;
    private CustomResultReceiver mReceiver;

    boolean afterSync = true;
    ProgressDialog mprogress;
    View mheader;
    private User mSelectedUser;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_assignment_summary);

        mReceiver = new CustomResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);

        field_mgr= (TextView) findViewById(R.id.field_engineer);
        field_code= (TextView) findViewById(R.id.field_sterlite);
        field_location= (TextView) findViewById(R.id.field_location);
        field_desig= (TextView) findViewById(R.id.field_desigination);
        profile_pic= (CircleImageView) findViewById(R.id.profile_icon);
        //TODO to display user image use profile_pic
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        bundle=intent.getExtras();
        if(bundle.getString("projecttype").equalsIgnoreCase("null")){
            new AlertDialog.Builder(this)
                    .setTitle("Please define the Project Type")
                    //.setNegativeButton(R.string.cancel, null) // dismisses by default
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        } else {
            Log.e("projecttype", bundle.getString("projecttype"));
            AppPreferences.setProjectType(RouteAssignmentSummaryActivity.this,bundle.getString("projecttype"));
        }
        AppPreferences.setPartnerid(RouteAssignmentSummaryActivity.this,bundle.getInt("partnerid"));
        AppPreferences.setUserType(RouteAssignmentSummaryActivity.this,bundle.getString("usertype"));
        AppPreferences.setUserEmail(RouteAssignmentSummaryActivity.this,bundle.getString("user_email"));
        //AppPreferences.setUser_SalesAreaId(RouteAssignmentSummaryActivity.this,bundle.getInt("usersalesAreaid"));

        /*if (ContextCompat.checkSelfPermission(RouteAssignmentSummaryActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(RouteAssignmentSummaryActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(RouteAssignmentSummaryActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    55);
        }*/
        //Loaduserdata();
        mprogress = new ProgressDialog(RouteAssignmentSummaryActivity.this);
        mprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mprogress.setMessage("Loading Data...");
        mprogress.setIndeterminate(true);
        mprogress.setCancelable(false);
        mprogress.show();
        new FetchDetailsFromDbTask().execute();

        //Log.d("groupid", String.valueOf(AppPreferences.getGroupId(LandingActivity.this)));
        field_mgr.setText(AppPreferences.getFieldEngineer(RouteAssignmentSummaryActivity.this));
        field_code.setText(AppPreferences.getFieldNumber(RouteAssignmentSummaryActivity.this));
        field_location.setText(AppPreferences.getFieldLocation(RouteAssignmentSummaryActivity.this));
        field_desig.setText(AppPreferences.getFieldDesignition(RouteAssignmentSummaryActivity.this));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mheader =  navigationView.getHeaderView(0);
        mheader_name= (TextView) mheader.findViewById(R.id.header_name);
        mheader_email= (TextView) mheader.findViewById(R.id.header_email);
        mheader_name.setText(AppPreferences.getFieldEngineer(RouteAssignmentSummaryActivity.this));
        mheader_email.setText(AppPreferences.getUserEmail(RouteAssignmentSummaryActivity.this));

        Menu nav_Menu = navigationView.getMenu();
        if(AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this)==29){
            getSupportActionBar().setTitle("Survey");
            nav_Menu.findItem(R.id.nav_qualitycheck).setEnabled(false);
            nav_Menu.findItem(R.id.nav_execution).setEnabled(false);
            nav_Menu.findItem(R.id.nav_patroling).setEnabled(false);
        } else if(AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this)==14||AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this)==17||AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this)==18){
            getSupportActionBar().setTitle("Execution");
            nav_Menu.findItem(R.id.nav_qualitycheck).setEnabled(false);
            nav_Menu.findItem(R.id.nav_survey).setEnabled(false);
            nav_Menu.findItem(R.id.nav_patroling).setEnabled(false);
        } else if(AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this)==23||AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this)==41||AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this)==39){
            getSupportActionBar().setTitle("Quality Check");
            nav_Menu.findItem(R.id.nav_survey).setEnabled(false);
            nav_Menu.findItem(R.id.nav_execution).setEnabled(false);
            nav_Menu.findItem(R.id.nav_patroling).setEnabled(false);
        }if(AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this)==42){
            getSupportActionBar().setTitle("Patroling");
            nav_Menu.findItem(R.id.nav_patroling).setEnabled(true);
            nav_Menu.findItem(R.id.nav_DashBoard).setEnabled(false);
            nav_Menu.findItem(R.id.nav_Progress_view).setEnabled(false);
            nav_Menu.findItem(R.id.nav_survey).setEnabled(false);
            nav_Menu.findItem(R.id.nav_execution).setEnabled(false);
            nav_Menu.findItem(R.id.nav_qualitycheck).setEnabled(false);
            nav_Menu.findItem(R.id.nav_projectrisk).setEnabled(false);
            nav_Menu.findItem(R.id.nav_changerequest).setEnabled(false);
        }/*else{
            nav_Menu.findItem(R.id.nav_patroling).setEnabled(false);
            nav_Menu.findItem(R.id.nav_DashBoard).setEnabled(false);
            nav_Menu.findItem(R.id.nav_Progress_view).setEnabled(false);
            nav_Menu.findItem(R.id.nav_survey).setEnabled(false);
            nav_Menu.findItem(R.id.nav_execution).setEnabled(false);
            nav_Menu.findItem(R.id.nav_qualitycheck).setEnabled(false);
            nav_Menu.findItem(R.id.nav_projectrisk).setEnabled(false);
            nav_Menu.findItem(R.id.nav_changerequest).setEnabled(false);
        }*/

        if (!isReadStorageAllowed()) {
            requestStoragePermission();
        }
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    public void populateData(){
        recyclerView = (RecyclerView) findViewById(R.id.rv_task);
        Log.e("testqa","qa");
        final int partnerid = AppPreferences.getPartnerid(RouteAssignmentSummaryActivity.this);
        try{
            RuntimeExceptionDao<RouteAssignmentSummariesViews, Integer> RouteAssignmentSummariesViewsDao = mDbHelper.getRouteAssignmentSummariesViewsRuntimeDao();
            Where<RouteAssignmentSummariesViews, Integer> where = RouteAssignmentSummariesViewsDao.queryBuilder().where();
            if (AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 14 || AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 29) {
                where.and(where.eq("partnerid",partnerid),where.eq("evFlag","e"));
                //where.eq("partnerid",partnerid);
            } else if (AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 17 || AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 18) {
                where.and(where.eq("partnerid",partnerid),where.eq("evFlag","v"));
                //where.eq("partnerid",partnerid);
            } else if (AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 23||AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 41
                    ||AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 39) {
                where.and(where.eq("consultUserId",AppPreferences.getEmployeeId(RouteAssignmentSummaryActivity.this)),where.eq("completedFlag",true));
            }
            mrouteAssignmentSummaryViewses = where.query();

            if(AppPreferences.getPushDataSuccess(RouteAssignmentSummaryActivity.this)){
                Log.d("qaroutetsize", String.valueOf(mrouteAssignmentSummaryViewses.size()));
                adapter = new RouteAssignmentSummary_RV_Adapter(mrouteAssignmentSummaryViewses);
                layoutManager = new LinearLayoutManager(RouteAssignmentSummaryActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        /*final ProgressDialog progress = new ProgressDialog(RouteAssignmentSummaryActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading Data...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call = null;
        Log.e("getUserId",AppPreferences.getUserId(RouteAssignmentSummaryActivity.this));
        Log.e("getGroupId",AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this).toString());
        if (AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 14 || AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 29) {
            call = service.getRouteSalesViewsUsername(AppPreferences.getUserId(RouteAssignmentSummaryActivity.this), AppPreferences.getUserName(RouteAssignmentSummaryActivity.this));
        } else if (AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 17 || AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 18) {
            Log.d("data1", "size1");
            call = service.getRoutePartnerSalesViewsUsername(AppPreferences.getUserId(RouteAssignmentSummaryActivity.this), AppPreferences.getUserName(RouteAssignmentSummaryActivity.this));
        } else if (AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 23||AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 41||AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 39) {
            Log.e("callingqa","qa");
            call = service.getQARouteSalesViews(AppPreferences.getUserId(RouteAssignmentSummaryActivity.this), AppPreferences.getLoggedUserName(RouteAssignmentSummaryActivity.this), false);
        }
        if(call!=null){
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<RouteSalesViews> routeSalesViews = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<RouteSalesViews>>() {
                            }.getType());
                    mListRouteSalesView = routeSalesViews;
                    Log.d("qalistsize", String.valueOf(mListRouteSalesView.size()));
                    Log.d("partnerid", String.valueOf(AppPreferences.getPartnerid(RouteAssignmentSummaryActivity.this)));
                    ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
                    Call<String> call1 = null;
                    if (AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 14 || AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 29) {
                        call1 = service1.getRouteAssignmentsSummariesViews(AppPreferences.getUserId(RouteAssignmentSummaryActivity.this), partnerid,"e");
                    } else if (AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 17 || AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 18) {
                        call1 = service1.getRouteAssignmentsPartnerSummariesViews(AppPreferences.getUserId(RouteAssignmentSummaryActivity.this), partnerid,"v");
                    } else if (AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 23||AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 41||AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this) == 39) {
                        Log.e("routeasignqa","qa");
                        call1 = service1.getQARouteAssignmentsSummariesViews(AppPreferences.getUserId(RouteAssignmentSummaryActivity.this), AppPreferences.getEmployeeId(RouteAssignmentSummaryActivity.this), true);
                    }
                    if(call1!=null){
                        call1.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                ArrayList<RouteAssignmentSummariesViews> routeAssignmentSummaryViewses = new Gson().fromJson(response.body(),
                                        new TypeToken<ArrayList<RouteAssignmentSummariesViews>>() {
                                        }.getType());
                                mrouteAssignmentSummaryViewses = routeAssignmentSummaryViewses;
                                Log.d("qaroutetsize", String.valueOf(mrouteAssignmentSummaryViewses.size()));
                                adapter = new RouteAssignmentSummary_RV_Adapter(mrouteAssignmentSummaryViewses);
                                layoutManager = new LinearLayoutManager(RouteAssignmentSummaryActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(layoutManager);
                                adapter.notifyDataSetChanged();
                                progress.dismiss();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                t.printStackTrace();
                                progress.dismiss();
                                Toast.makeText(RouteAssignmentSummaryActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        progress.dismiss();
                        Toast.makeText(RouteAssignmentSummaryActivity.this, getString(R.string.check_groupid), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(RouteAssignmentSummaryActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            progress.dismiss();
            Toast.makeText(this, R.string.no_data, Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.logout_confirm))
                    .setNegativeButton(R.string.cancel, null) // dismisses by default
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(RouteAssignmentSummaryActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                            startActivity(intent);
                        }
                    })
                    .create()
                    .show();
        }else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Intent intent;
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            // Handle the logout action
            //Intent intent = new Intent(RouteAssignmentSummaryActivity.this, LoginActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
            startActivity(new Intent(this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (id == R.id.nav_survey) {
            //Toast.makeText(this, "survey", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_execution) {
            //Toast.makeText(this, "execution", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_qualitycheck) {
            //Toast.makeText(this, "qualitycheck", Toast.LENGTH_SHORT).show();
        } else if(id == R.id.nav_patroling){
            //todo patroling
            startActivity(new Intent(this, Patroling_Activity.class)
                    .putExtra("usersalesareaid",AppPreferences.getPrefAreaId(RouteAssignmentSummaryActivity.this))
                    .putExtra("userzoneid",AppPreferences.getZoneId(RouteAssignmentSummaryActivity.this)));
        }
        else if (id == R.id.nav_projectrisk) {
            //Intent in=new Intent(RouteAssignmentSummaryActivity.this,ProjectRisk.class);
            startActivity(new Intent(this,ProjectRisk.class));
        }
        else if (id == R.id.nav_changerequest) {
            //Intent in=new Intent(RouteAssignmentSummaryActivity.this,ChangeRequest.class);
            startActivity(new Intent(this,ChangeRequest.class));
        }else if (id == R.id.nav_DashBoard) {
            //Intent in=new Intent(RouteAssignmentSummaryActivity.this,GraphActivity.class);
            startActivity(new Intent(this,GraphActivity.class));
        }else if (id == R.id.nav_Progress_view) {
            //Intent in = new Intent(RouteAssignmentSummaryActivity.this, ProgressView.class);
            startActivity(new Intent(this,ProgressView.class));
        }else if(id == R.id.nav_PushData){
            if (isReadStorageAllowed()) {
                // Toast.makeText(ActivityHome.this,"You already have the permission",Toast.LENGTH_LONG).show();
                if (isNetworkAvailable() == true) {
                    progressDialog = new ProgressDialog(RouteAssignmentSummaryActivity.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage(getString(R.string.writing_data));
                    progressDialog.show();
                    intent = new Intent(RouteAssignmentSummaryActivity.this, SyncOutService.class);
                    intent.putExtra("RESULT_RECEIVER", mReceiver);
                   // intent.putExtra("submitpress",submitpress.getInt("submitPress")); //added new line
                    startService(intent);
                } else {
                    AppUtilities.errorDialog(RouteAssignmentSummaryActivity.this, getResources().getString(R.string.check_network_for_push_data));
                }
            } else {
                //If the app has not the permission then asking for the permission
                requestStoragePermission();
            }
            return true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Check all connectivities whether available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeresult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED && writeresult == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                //Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
                progressDialog = new ProgressDialog(RouteAssignmentSummaryActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(getString(R.string.writing_data));
                progressDialog.show();
                Intent intent = new Intent(RouteAssignmentSummaryActivity.this, SyncOutService.class);
                intent.putExtra("RESULT_RECEIVER", mReceiver);
                startService(intent);

//                stopService(new Intent(ActivityHome.this,RecordService.class));
//                Intent recordintent = new Intent(ActivityHome.this, RecordService.class);
//                recordintent.putExtra("RESULT_RECEIVER", mReceiver);
//                startService(recordintent);
            } else {
                //Displaying another toast if permission is not granted
                //Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
                requestStoragePermission();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        progressDialog.dismiss();
        if(resultCode == 1){
            Toast.makeText(this, getString(R.string.get_data1)+" "+getString(R.string.success1), Toast.LENGTH_LONG).show();
        }else if(resultCode ==2){
            Toast.makeText(this, getString(R.string.push_data1)+" "+getString(R.string.success1), Toast.LENGTH_LONG).show();
        }
        Log.e("result_code", String.valueOf(resultCode));
        Log.e("result_data", resultData.getString("result"));
        Log.e("result_equal", getResources().getString(R.string.push_data1) + " " + getResources().getString(R.string.success1));
        try {
            if (resultCode == 2 && resultData.getString("result").equalsIgnoreCase(getResources().getString(R.string.push_data1) + " " + getResources().getString(R.string.success1))) {
                progressDialog = new ProgressDialog(RouteAssignmentSummaryActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(getString(R.string.fetching_data));
                progressDialog.show();
                Intent intent = new Intent(RouteAssignmentSummaryActivity.this, SyncInService.class);
                intent.putExtra("RESULT_RECEIVER", mReceiver);
                startService(intent);
            }
            if (resultCode == 1 && resultData.getString("result").equalsIgnoreCase(getResources().getString(R.string.get_data1) + " " + getResources().getString(R.string.success1))) {
                AppPreferences.setPushDataSuccess(RouteAssignmentSummaryActivity.this,true);
                afterSyncIn();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*LandingFragment fragment = LandingFragment.getInstance();
        if (fragment != null) {
            fragment.onSyncCompleteListener();
            fragment.onSyncOutCompleteListener();
        }*/
    }

    public void afterSyncIn() {
        try {
            afterSync = false;
            mprogress.show();
            new FetchDetailsFromDbTask().execute();

            RuntimeExceptionDao<User, Integer> userDao = mDbHelper.getUserRuntimeDao();
            mSelectedUser = userDao.queryBuilder().where().eq("id", AppPreferences.getLoggedUserName(RouteAssignmentSummaryActivity.this)).queryForFirst();
            Menu menuNav = mNavigationView.getMenu();

            if (mSelectedUser != null) {
                Log.e("aftersyncin", String.valueOf(mSelectedUser));
                //mSelectedUser.getSyncInTime() != null
                if (AppPreferences.getPushDataSuccess(RouteAssignmentSummaryActivity.this)) {
                    menuNav.findItem(R.id.nav_DashBoard).setEnabled(true);
                    menuNav.findItem(R.id.nav_Progress_view).setEnabled(true);
                    menuNav.findItem(R.id.nav_survey).setEnabled(true);
                    menuNav.findItem(R.id.nav_execution).setEnabled(true);
                    menuNav.findItem(R.id.nav_qualitycheck).setEnabled(true);
                    menuNav.findItem(R.id.nav_projectrisk).setEnabled(true);
                    menuNav.findItem(R.id.nav_changerequest).setEnabled(true);
                    if(AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this)==42){
                        menuNav.findItem(R.id.nav_patroling).setEnabled(true);
                    }
                } else {
                    menuNav.findItem(R.id.nav_DashBoard).setEnabled(false);
                    menuNav.findItem(R.id.nav_Progress_view).setEnabled(false);
                    menuNav.findItem(R.id.nav_survey).setEnabled(false);
                    menuNav.findItem(R.id.nav_execution).setEnabled(false);
                    menuNav.findItem(R.id.nav_qualitycheck).setEnabled(false);
                    menuNav.findItem(R.id.nav_projectrisk).setEnabled(false);
                    menuNav.findItem(R.id.nav_changerequest).setEnabled(false);
                    menuNav.findItem(R.id.nav_patroling).setEnabled(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RouteAssignmentSummaryActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public void Loaduserdata(){
        final ProgressDialog progress = new ProgressDialog(RouteAssignmentSummaryActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
        if(bundle.getString("usertype").equalsIgnoreCase("partner")){

            final ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
            Call<String> call = service.getPartnerviews(AppPreferences.getUserId(RouteAssignmentSummaryActivity.this), bundle.getInt("partnerid"));
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<Partnerviews> partners= new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<Partnerviews>>() {
                            }.getType());
                    progress.dismiss();
                    mpartners = partners;
                    for(int i=0;i<mpartners.size();i++){
                        AppPreferences.setFieldEngineer(RouteAssignmentSummaryActivity.this,mpartners.get(i).getFname()==null?"":mpartners.get(i).getFname());
                        AppPreferences.setFieldNumber(RouteAssignmentSummaryActivity.this,mpartners.get(i).getZone()==null?"":mpartners.get(i).getZone());
                        AppPreferences.setFieldLocation(RouteAssignmentSummaryActivity.this,mpartners.get(i).getArea()==null?"":mpartners.get(i).getArea());
                        AppPreferences.setFieldDesignition(RouteAssignmentSummaryActivity.this,mpartners.get(i).getDistarea()==null?"":mpartners.get(i).getDistarea());

                        AppPreferences.setPrefAreaId(RouteAssignmentSummaryActivity.this, mpartners.get(i).getAreaid());
                        AppPreferences.setZoneId(RouteAssignmentSummaryActivity.this,mpartners.get(i).getZoneid());
                        AppPreferences.setSaleMngrIdId(RouteAssignmentSummaryActivity.this,mpartners.get(i).getSalesmgrid()==null?0:mpartners.get(i).getSalesmgrid());
                        AppPreferences.setDist_Area_Id(RouteAssignmentSummaryActivity.this, mpartners.get(i).getDistareaid());
                        AppPreferences.setGroupId(RouteAssignmentSummaryActivity.this,mpartners.get(i).getGroupid());
                        AppPreferences.setEmployeeId(RouteAssignmentSummaryActivity.this,mpartners.get(i).getId());
                    }
                    populateData();
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(RouteAssignmentSummaryActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                }
            });
        }else if(bundle.getString("usertype").equalsIgnoreCase("executive")){

            final ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
            Call<String> call = service.getSalesViews(AppPreferences.getUserId(RouteAssignmentSummaryActivity.this), bundle.getInt("partnerid"));
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<SalesViews> salesViews = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<SalesViews>>() {
                            }.getType());
                    progress.dismiss();
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
                        AppPreferences.setFieldEngineer(RouteAssignmentSummaryActivity.this,fname);
                        AppPreferences.setFieldNumber(RouteAssignmentSummaryActivity.this,location);
                        AppPreferences.setFieldLocation(RouteAssignmentSummaryActivity.this,lastname);
                        AppPreferences.setFieldDesignition(RouteAssignmentSummaryActivity.this,mobile);

                        AppPreferences.setPrefAreaId(RouteAssignmentSummaryActivity.this,areaid);
                        AppPreferences.setZoneId(RouteAssignmentSummaryActivity.this,zoneid);
                        AppPreferences.setSaleMngrIdId(RouteAssignmentSummaryActivity.this,salesmgrid);
                        AppPreferences.setDist_Area_Id(RouteAssignmentSummaryActivity.this,distareaid);
                        AppPreferences.setGroupId(RouteAssignmentSummaryActivity.this,groupid);
                        AppPreferences.setEmployeeId(RouteAssignmentSummaryActivity.this,id);
                        Log.e("qaworktest","qatest");
                    }
                    populateData();
                }
                @Override
                public void onFailure(Call<String> call, Throwable t)
                {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(RouteAssignmentSummaryActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                }
            });
        }else if(bundle.getString("usertype").equalsIgnoreCase("null")){
            progress.dismiss();
            Toast.makeText(this, "Please,Define Valid User Type", Toast.LENGTH_SHORT).show();
        }
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
            /*Menu menuNav = mNavigationView.getMenu();
            if (mSelectedUser != null) {
                Log.e("aftersyncin", String.valueOf(mSelectedUser));
                //mSelectedUser.getSyncInTime() != null
                if (AppPreferences.getPushDataSuccess(RouteAssignmentSummaryActivity.this)) {
                    menuNav.findItem(R.id.nav_DashBoard).setEnabled(true);
                    menuNav.findItem(R.id.nav_Progress_view).setEnabled(true);
                    menuNav.findItem(R.id.nav_survey).setEnabled(true);
                    menuNav.findItem(R.id.nav_execution).setEnabled(true);
                    menuNav.findItem(R.id.nav_qualitycheck).setEnabled(true);
                    menuNav.findItem(R.id.nav_projectrisk).setEnabled(true);
                    menuNav.findItem(R.id.nav_changerequest).setEnabled(true);
                    if(AppPreferences.getGroupId(RouteAssignmentSummaryActivity.this)==42){
                        menuNav.findItem(R.id.nav_patroling).setEnabled(true);
                    }
                } else {
                    menuNav.findItem(R.id.nav_DashBoard).setEnabled(false);
                    menuNav.findItem(R.id.nav_Progress_view).setEnabled(false);
                    menuNav.findItem(R.id.nav_survey).setEnabled(false);
                    menuNav.findItem(R.id.nav_execution).setEnabled(false);
                    menuNav.findItem(R.id.nav_qualitycheck).setEnabled(false);
                    menuNav.findItem(R.id.nav_projectrisk).setEnabled(false);
                    menuNav.findItem(R.id.nav_changerequest).setEnabled(false);
                    menuNav.findItem(R.id.nav_patroling).setEnabled(false);
                }
            }*/

            if(bundle.getString("usertype").equalsIgnoreCase("partner")){
                for(int i=0;i<mpartners.size();i++){
                    AppPreferences.setFieldEngineer(RouteAssignmentSummaryActivity.this,mpartners.get(i).getFname()==null?"":mpartners.get(i).getFname());
                    AppPreferences.setFieldNumber(RouteAssignmentSummaryActivity.this,mpartners.get(i).getZone()==null?"":mpartners.get(i).getZone());
                    AppPreferences.setFieldLocation(RouteAssignmentSummaryActivity.this,mpartners.get(i).getArea()==null?"":mpartners.get(i).getArea());
                    AppPreferences.setFieldDesignition(RouteAssignmentSummaryActivity.this,mpartners.get(i).getDistarea()==null?"":mpartners.get(i).getDistarea());

                    AppPreferences.setPrefAreaId(RouteAssignmentSummaryActivity.this, mpartners.get(i).getAreaid());
                    AppPreferences.setZoneId(RouteAssignmentSummaryActivity.this,mpartners.get(i).getZoneid());
                    AppPreferences.setSaleMngrIdId(RouteAssignmentSummaryActivity.this,mpartners.get(i).getSalesmgrid()==null?0:mpartners.get(i).getSalesmgrid());
                    AppPreferences.setDist_Area_Id(RouteAssignmentSummaryActivity.this, mpartners.get(i).getDistareaid());
                    AppPreferences.setGroupId(RouteAssignmentSummaryActivity.this,mpartners.get(i).getGroupid());
                    AppPreferences.setEmployeeId(RouteAssignmentSummaryActivity.this,mpartners.get(i).getId());
                }
                populateData();
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
                    AppPreferences.setFieldEngineer(RouteAssignmentSummaryActivity.this,fname);
                    AppPreferences.setFieldNumber(RouteAssignmentSummaryActivity.this,location);
                    AppPreferences.setFieldLocation(RouteAssignmentSummaryActivity.this,lastname);
                    AppPreferences.setFieldDesignition(RouteAssignmentSummaryActivity.this,mobile);

                    AppPreferences.setPrefAreaId(RouteAssignmentSummaryActivity.this,areaid);
                    AppPreferences.setZoneId(RouteAssignmentSummaryActivity.this,zoneid);
                    AppPreferences.setSaleMngrIdId(RouteAssignmentSummaryActivity.this,salesmgrid);
                    AppPreferences.setDist_Area_Id(RouteAssignmentSummaryActivity.this,distareaid);
                    AppPreferences.setGroupId(RouteAssignmentSummaryActivity.this,groupid);
                    AppPreferences.setEmployeeId(RouteAssignmentSummaryActivity.this,id);
                }
                populateData();
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
