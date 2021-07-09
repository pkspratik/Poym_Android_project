package com.example.kanthi.projectmonitoring.Dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kanthi.projectmonitoring.Adapters.Remarks_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.WeeklyTask_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummaries;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignmentSummariesViews;
import com.example.kanthi.projectmonitoring.PoJo.RouteAssignments;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapScreen extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private LocationManager manager;
    private LocationListener listener;
    private double lati;
    private double longi;
    private LatLng destination;
    private LatLng source;
    private Button surveymap, project_monitor, status_update;
    private TextView tv_surveymap, tv_projectmonitor;
    private Toolbar toolbar;
    private TextView field_mgr, field_code, field_location, field_desig;
    private TextView startdate, enddate, totaltarget, currenttarget, actualtarget, daysleft, act_stdate, act_enddate;
    private LinearLayout layout;
    private Surveys surveys;
    private ProgressDialog progres;
    private double tolat = 0.0, tolong = 0.0;
    private double fromlat = 0.0, fromlong = 0.0;
    private TextView save, cancel;
    private List<Remarks> mremarks;
    private List<Surveys> msurveys;
    private List<Surveys> mfilteredsurveys;
    private Paint mPaint;
    int remarkid;
    String finaldate;
    String remark_name, day;
    String fromaddress, toaddress;
    float distanceInMeters;
    int partnerid;
    AlertDialog mdailog;
    DonutProgress chart;
    private CoordinatorLayout coordinatorLayout;
    AvahanSqliteDbHelper mDbHelper;
    ProgressDialog progress;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);

        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        surveymap = (Button) findViewById(R.id.surveymap);
        project_monitor = (Button) findViewById(R.id.project_monitor);
        status_update = (Button) findViewById(R.id.statusupdate);
        tv_surveymap = (TextView) findViewById(R.id.tv_pendingtask);
        tv_projectmonitor = (TextView) findViewById(R.id.tv_projectmonitor);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Intent in = getIntent();
        Bundle bun = in.getExtras();
        partnerid = bun.getInt("partnerid");
        day = bun.getString("date");
        getSupportActionBar().setTitle("Pending Task" + " ( " + day + " )");
        surveymap.setBackgroundResource(R.drawable.poym_pending_purple);
        if (AppPreferences.getGroupId(MapScreen.this) == 23 || AppPreferences.getGroupId(MapScreen.this) == 41 || AppPreferences.getGroupId(MapScreen.this) == 39) {
            project_monitor.setBackgroundResource(R.drawable.greyapproval);
            tv_projectmonitor.setText("Approval");
        }
        tv_surveymap.setTextColor(getResources().getColor(R.color.bottom_purple));
        /*field_mgr= (TextView) findViewById(R.id.field_engineer);
        field_code= (TextView) findViewById(R.id.field_sterlite);
        field_location= (TextView) findViewById(R.id.field_location);
        field_desig= (TextView) findViewById(R.id.field_desigination);*/
        startdate = (TextView) findViewById(R.id.startdate);
        enddate = (TextView) findViewById(R.id.enddate);
        totaltarget = (TextView) findViewById(R.id.tot_target);
        currenttarget = (TextView) findViewById(R.id.curr_target);
        actualtarget = (TextView) findViewById(R.id.act_target);
        daysleft = (TextView) findViewById(R.id.daysleft);
        act_stdate = (TextView) findViewById(R.id.actualstartdate);
        act_enddate = (TextView) findViewById(R.id.actualenddate);
        layout = (LinearLayout) findViewById(R.id.task_details_layout);
        chart = (DonutProgress) findViewById(R.id.piechart);
        chart.setProgress(AppPreferences.getChartPercentage(MapScreen.this));
        if (AppPreferences.getActualTarget(MapScreen.this) < AppPreferences.getCurrentTarget(MapScreen.this)) {
            //layout.setBackgroundColor(getResources().getColor(R.color.red));
            currenttarget.setTextColor(getResources().getColor(R.color.red));
        }
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        final int mm = calendar.get(Calendar.MONTH);
        final int m = mm;
        final int dd = calendar.get(Calendar.DAY_OF_MONTH);
        startdate.setText(AppPreferences.getStartDate(MapScreen.this));
        enddate.setText(AppPreferences.getEndDate(MapScreen.this));
        totaltarget.setText(String.valueOf(AppPreferences.getTotalTarget(MapScreen.this)) + " KM");
        actualtarget.setText(String.valueOf(AppPreferences.getActualTarget(MapScreen.this)) + " KM");
        currenttarget.setText(String.valueOf(AppPreferences.getCurrentTarget(MapScreen.this)) + " KM");
        act_stdate.setText(AppUtilities.getDateWithTimeString(AppPreferences.getActualStartDate(MapScreen.this)));
        act_enddate.setText(AppUtilities.getDateWithTimeString(AppPreferences.getActualEndDate(MapScreen.this)));
        String st_date = AppUtilities.getDateString(yy, m, dd);
        String d = st_date.substring(0, 2);
        String mo = st_date.substring(3, 6);
        finaldate = mo + " " + d;
        //Toast.makeText(this, ""+finaldate, Toast.LENGTH_SHORT).show();
        if (day.equalsIgnoreCase(finaldate)) {
            daysleft.setText(String.valueOf(AppPreferences.getDaysLeft(MapScreen.this)) + " Days");
        } else {
            daysleft.setText("Overdue");
            daysleft.setTextColor(getResources().getColor(R.color.red));
        }
        save = (TextView) findViewById(R.id.bt_save);
        cancel = (TextView) findViewById(R.id.bt_cancel);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        msurveys=new ArrayList<>();
        mfilteredsurveys=new ArrayList<>();
        //networkRemark();
        progress = new ProgressDialog(MapScreen.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Fetching Data,Please Wait...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
        new FetchDetailsFromDbTask().execute();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surveymap_popup();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent in = new Intent(MapScreen.this, MapScreen.class);
                in.putExtra("partnerid", partnerid);
                in.putExtra("date", day);
                startActivity(in);
            }
        });
        project_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pro = new Intent(MapScreen.this, ProjectMonitorActivity.class);
                //pro.putExtra("Latitude",lati);
                //pro.putExtra("Longitude",longi);
                pro.putExtra("partnerid", partnerid);
                pro.putExtra("date", day);
                startActivity(pro);
            }
        });
        status_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MapScreen.this, StatusUpdate.class);
                in.putExtra("Latitude", lati);
                in.putExtra("Longitude", longi);
                in.putExtra("partnerid", partnerid);
                in.putExtra("date", day);
                startActivity(in);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) MapScreen.this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerDragListener(this);
        //mMap.setOnMarkerClickListener(this);

        //mMap.setOnCameraIdleListener(onCameraIdleListener);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }


    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.getTitle().equalsIgnoreCase("To")) {
            tolat = marker.getPosition().latitude;
            tolong = marker.getPosition().longitude;
            //Toast.makeText(this, "ToMarker:"+tolat+","+tolong, Toast.LENGTH_SHORT).show();
        }
        if (marker.getTitle().equalsIgnoreCase("From")) {
            fromlat = marker.getPosition().latitude;
            fromlong = marker.getPosition().longitude;
            //Toast.makeText(this, "FromMarker:"+fromlat+","+fromlong, Toast.LENGTH_SHORT).show();
        }
        if (tolat != 0.0 && tolong != 0.0 && fromlat != 0.0 && fromlong != 0.0) {
            Location loc1 = new Location("");
            loc1.setLatitude(tolat);
            loc1.setLongitude(tolong);
            Location loc2 = new Location("");
            loc2.setLatitude(fromlat);
            loc2.setLongitude(fromlong);
            try {
                getAddress1(tolat, tolong);
                getAddress2(fromlat, fromlong);
            } catch (Exception e) {
                e.printStackTrace();
            }
            distanceInMeters = loc1.distanceTo(loc2);
            //Toast.makeText(this, ""+distanceInMeters+" Meters", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.landing, menu);
        getMenuInflater().inflate(R.menu.pendingtask, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pending_detail) {
            //pendingpoints();
            Pending_popup pending_popup = new Pending_popup();
            pending_popup.show(getSupportFragmentManager(), null);
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent in1 = new Intent(MapScreen.this, LandingActivity.class);
            in1.putExtra("partnerid", partnerid);
            in1.putExtra("usertype", AppPreferences.getUserType(MapScreen.this));
            in1.putExtra("projecttype", AppPreferences.getProjectType(MapScreen.this));
            in1.putExtra("user_email", AppPreferences.getUserEmail(MapScreen.this));
            //in1.putExtra("usersalesAreaid",AppPreferences.getUser_SalesAreaId(MapScreen.this));
            startActivity(in1);
        }
        return super.onOptionsItemSelected(item);
    }

    public void postSurvey() {
        final ProgressDialog progressDialog = new ProgressDialog(MapScreen.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson = new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            benjson = parser.parse(new Gson().toJson(
                    surveys, Surveys.class)).getAsJsonObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        benjson.remove("id");
        call = service.insertSurveys(AppPreferences.getUserId(this), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    mdailog.dismiss();
                    Toast.makeText(MapScreen.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(MapScreen.this, MapScreen.class);
                    intent.putExtra("partnerid", partnerid);
                    intent.putExtra("date", day);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MapScreen.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void surveymap_popup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapScreen.this);
        View view = LayoutInflater.from(MapScreen.this).inflate(R.layout.mapscreen_popup, null);
        builder.setCancelable(false);
        builder.setTitle("PendingTask Reason");
        builder.setView(view);
        final Spinner spinner = (Spinner) view.findViewById(R.id.mapscreen_spinner);
        final EditText editText = (EditText) view.findViewById(R.id.mapscreen_et_value);
        //Button bt_add= (Button) view.findViewById(R.id.mapscreen_add);
        //final TextView textView= (TextView) view.findViewById(R.id.mapscreen_value);
        Button bt_submit = (Button) view.findViewById(R.id.mapscreen_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.mapscreen_cancel);
        mdailog = builder.create();
        Remarks_Sp_Adapter adapter = new Remarks_Sp_Adapter(MapScreen.this, mremarks);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    remarkid = mremarks.get(spinner.getSelectedItemPosition() - 1).getId();
                    remark_name = mremarks.get(spinner.getSelectedItemPosition() - 1).getName();
                    if (remark_name.equalsIgnoreCase("Others")) {
                        editText.setVisibility(View.VISIBLE);
                        editText.requestFocus();
                    } else {
                        editText.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tolat != 0.0 && tolong != 0.0 && fromlat != 0.0 && fromlong != 0.0) {
                    surveys = new Surveys();
                    surveys.setLinkid(AppPreferences.getPrefAreaId(MapScreen.this));
                    surveys.setLatitude(tolat);
                    surveys.setLongitude(tolong);
                    surveys.setToLatitude(fromlat);
                    surveys.setToLongitude(fromlong);
                    surveys.setPendingflag(true);
                    surveys.setDistance(distanceInMeters);
                    if (AppPreferences.getGroupId(MapScreen.this) == 23 || AppPreferences.getGroupId(MapScreen.this) == 41) {
                        surveys.setQaflag(true);
                    }
                    surveys.setRouteassignmentid((int) AppPreferences.getRouteAssignmentId(MapScreen.this));
                    surveys.setZoneid(AppPreferences.getZoneId(MapScreen.this));
                    surveys.setAreaid(AppPreferences.getPrefAreaId(MapScreen.this));
                    surveys.setDistareaid(AppPreferences.getDist_Area_Id(MapScreen.this));
                    surveys.setRemarkid(remarkid);
                    surveys.setRemark(remark_name);
                    surveys.setDeleteFlag(false);
                    surveys.setInsertFlag(true);
                    surveys.setFromaddress(fromaddress);
                    surveys.setToaddress(toaddress);
                    surveys.setDate(AppUtilities.getDateTime());
                    surveys.setRouteassignmentsummaryid(AppPreferences.getRouteAssignmentSummaryId(MapScreen.this));
                    if (remark_name.equalsIgnoreCase("Others")) {
                        surveys.setRemarkdesc(editText.getText().toString());
                    }

                    try {
                        RuntimeExceptionDao<Surveys, Integer> surveysDao = mDbHelper.getSurveysRuntimeDao();
                        surveysDao.create(surveys);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    mdailog.dismiss();
                    Toast.makeText(MapScreen.this, "Saved", Toast.LENGTH_SHORT).show();
                    //postSurvey();
                    progress.show();
                    new FetchDetailsFromDbTask().execute();
                } else {
                    Toast.makeText(MapScreen.this, "Please,Select Markers", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdailog.dismiss();
            }
        });
        mdailog.show();
    }

    private void getAddress1(double latitude, double longitude) {

        //Set Address
        try {
            Geocoder geocoder = new Geocoder(MapScreen.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String fr_address = addresses.get(0).getAddressLine(0); //comlete address
                String city = addresses.get(0).getLocality(); //bangalore
                String state = addresses.get(0).getAdminArea(); //karnataka
                String postalCode = addresses.get(0).getPostalCode(); //560076
                String subLocality = addresses.get(0).getSubLocality(); //arekere
                String subarea = addresses.get(0).getSubAdminArea(); //bangalore urban
                String featurename = addresses.get(0).getFeatureName();//streetname
                String fare = addresses.get(0).getThoroughfare();//peoplecalled
                System.out.println("getaddress:-" + addresses.get(0).getAddressLine(0));
                System.out.println("getfare1:-" + addresses.get(0).getThoroughfare());
                fromaddress = featurename + "," + fare + "," + subLocality;
                Log.d("getfeaturename1:", fr_address);
                Log.d("getAddress1:", fromaddress);
                Log.d("getcity1:", city);
                Log.d("getstate1:", state);
                Log.d("getcode1:", postalCode);
                Log.d("getsubloca1:", subLocality);
                Log.d("getsubarea1:", subarea);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    private void getAddress2(double latitude, double longitude) {

        //Set Address
        try {
            Geocoder geocoder = new Geocoder(MapScreen.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String to_add = addresses.get(0).getAddressLine(0); //comlete address
                String city = addresses.get(0).getLocality(); //bangalore
                String state = addresses.get(0).getAdminArea(); //karnataka
                String postalCode = addresses.get(0).getPostalCode(); //560076
                String subLocality = addresses.get(0).getSubLocality(); //arekere
                String subarea = addresses.get(0).getSubAdminArea(); //bangalore urban
                String featurename = addresses.get(0).getFeatureName();
                String fare = addresses.get(0).getThoroughfare();
                toaddress = featurename + "," + fare + "," + subLocality;

                /*System.out.println("getfare2:-"+addresses.get(0).getThoroughfare());
                Log.d("getfeaturename:", to_add);
                Log.d("getAddress2:", toaddress);
                Log.d("getcity2:",city);
                Log.d("getstate2:", state);
                Log.d("getcode2:", postalCode);
                Log.d("getsubloca2:", subLocality);
                Log.d("getsubarea2:", subarea);*/

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    private void networkRemark() {
        progress = new ProgressDialog(MapScreen.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Fetching Data,Please Wait...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getRemarks(AppPreferences.getUserId(MapScreen.this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Remarks> remarks = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Remarks>>() {
                        }.getType());
                mremarks = remarks;
                Remarks remarks1 = new Remarks();
                remarks1.setId(0);
                remarks1.setName("Others");
                mremarks.add(remarks1);
                networkSurvey();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                CallingSnackbar();
                //Toast.makeText(MapScreen.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkSurvey() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getPendingSurvey(AppPreferences.getUserId(MapScreen.this), AppPreferences.getZoneId(MapScreen.this),
                AppPreferences.getPrefAreaId(MapScreen.this), AppPreferences.getDist_Area_Id(MapScreen.this), true, false);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Surveys> surveys = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Surveys>>() {
                        }.getType());
                msurveys = surveys;
                userlocation();
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setDither(true);
                mPaint.setColor(getResources().getColor(R.color.red));
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                mPaint.setStrokeJoin(Paint.Join.ROUND);
                mPaint.setStrokeCap(Paint.Cap.ROUND);
                mPaint.setXfermode(null);
                mPaint.setTextSize(50f);
                mPaint.setAlpha(0xff);
                for (int i = 0; i < msurveys.size(); i++) {
                    if (msurveys.get(i).getPendingflag() == true) {
                        double sur_lat = msurveys.get(i).getLatitude();
                        double sur_long = msurveys.get(i).getLongitude();
                        LatLng sur_map = new LatLng(sur_lat, sur_long);
                        ///////////////////////////////////////////////
                        double sur_lat_to = msurveys.get(i).getToLatitude();
                        double sur_long_to = msurveys.get(i).getToLongitude();
                        LatLng sur_map_to = new LatLng(sur_lat_to, sur_long_to);

                        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                        Bitmap bmp = Bitmap.createBitmap(200, 50, conf);
                        Canvas canvas = new Canvas(bmp);
                        canvas.drawText(String.valueOf(i), 0, 50, mPaint); // paint defines the text color, stroke width, size
                        mMap.addMarker(new MarkerOptions()
                                .position(sur_map)
                                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                                .anchor(0.5f, 1)
                        );
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);
                        mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                        mMap.addMarker(new MarkerOptions().position(sur_map_to).icon(icon));
                        //add polyline
                        mMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(sur_lat, sur_long), new LatLng(sur_lat_to, sur_long_to))
                                .width(10)
                                .color(Color.RED));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                CallingSnackbar();
                //Toast.makeText(MapScreen.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userlocation() {
        manager = (LocationManager) MapScreen.this.getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                source = new LatLng(lati, longi);
                progress.dismiss();

                if(msurveys.size()>0){
                    double f_lati = lati = msurveys.get(0).getLatitude();
                    double f_longi = longi = msurveys.get(0).getLongitude();
                    LatLng first_latlon = new LatLng(f_lati, f_longi);

                    double s_lati = lati = msurveys.get(0).getLatitude() - 00.000050;
                    double s_longi = longi = msurveys.get(0).getLongitude() - 00.000050;
                    LatLng second_latlon = new LatLng(s_lati, s_longi);

                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.execution);
                    BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.drawable.approved);
                    //mMap.addMarker(new MarkerOptions().position(source).title("User in Source").icon(icon));
                    if (AppPreferences.getGroupId(MapScreen.this) == 14 || AppPreferences.getGroupId(MapScreen.this) == 17 || AppPreferences.getGroupId(MapScreen.this) == 18) {
                        mMap.addMarker(new MarkerOptions().position(first_latlon).draggable(true).title("To").icon(icon));
                        mMap.addMarker(new MarkerOptions().position(second_latlon).draggable(true).title("From").icon(icon));
                    }
                    if (AppPreferences.getGroupId(MapScreen.this) == 23 || AppPreferences.getGroupId(MapScreen.this) == 41 || AppPreferences.getGroupId(MapScreen.this) == 39) {
                        mMap.addMarker(new MarkerOptions().position(first_latlon).draggable(true).title("To").icon(icon1));
                        mMap.addMarker(new MarkerOptions().position(second_latlon).draggable(true).title("From").icon(icon1));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first_latlon, 18));
                }else{
                    double s_lati = lati = location.getLatitude() - 00.000050;
                    double s_longi = longi = location.getLongitude() - 00.000050;
                    LatLng second_latlon = new LatLng(s_lati, s_longi);
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.execution);
                    BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.drawable.approved);
                    //mMap.addMarker(new MarkerOptions().position(source).title("User in Source").icon(icon));
                    if (AppPreferences.getGroupId(MapScreen.this) == 14 || AppPreferences.getGroupId(MapScreen.this) == 17 || AppPreferences.getGroupId(MapScreen.this) == 18) {
                        mMap.addMarker(new MarkerOptions().position(source).draggable(true).title("To").icon(icon));
                        mMap.addMarker(new MarkerOptions().position(second_latlon).draggable(true).title("From").icon(icon));
                    }
                    if (AppPreferences.getGroupId(MapScreen.this) == 23 || AppPreferences.getGroupId(MapScreen.this) == 41 || AppPreferences.getGroupId(MapScreen.this) == 39) {
                        mMap.addMarker(new MarkerOptions().position(source).draggable(true).title("To").icon(icon1));
                        mMap.addMarker(new MarkerOptions().position(second_latlon).draggable(true).title("From").icon(icon1));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(source, 18));
                }

                /*double s_lati = lati = location.getLatitude() - 00.000050;
                double s_longi = longi = location.getLongitude() - 00.000050;
                LatLng second_latlon = new LatLng(s_lati, s_longi);
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.execution);
                BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.drawable.approved);
                //mMap.addMarker(new MarkerOptions().position(source).title("User in Source").icon(icon));
                if (AppPreferences.getGroupId(MapScreen.this) == 14 || AppPreferences.getGroupId(MapScreen.this) == 17 || AppPreferences.getGroupId(MapScreen.this) == 18) {
                    mMap.addMarker(new MarkerOptions().position(source).draggable(true).title("To").icon(icon));
                    mMap.addMarker(new MarkerOptions().position(second_latlon).draggable(true).title("From").icon(icon));
                }
                if (AppPreferences.getGroupId(MapScreen.this) == 23 || AppPreferences.getGroupId(MapScreen.this) == 41 || AppPreferences.getGroupId(MapScreen.this) == 39) {
                    mMap.addMarker(new MarkerOptions().position(source).draggable(true).title("To").icon(icon1));
                    mMap.addMarker(new MarkerOptions().position(second_latlon).draggable(true).title("From").icon(icon1));
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(source, 19));*/
                if (ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                //mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setAllGesturesEnabled(true);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                if (i == LocationProvider.OUT_OF_SERVICE) {
                    Toast.makeText(MapScreen.this, "Network Unavaliable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(MapScreen.this, "Fetching..PlzWait", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(MapScreen.this, "Please enable GPS", Toast.LENGTH_SHORT).show();
            }
        };
        if (ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 50, listener);
    }

    private void CallingSnackbar() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, R.string.check_internet, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //networkRemark();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }


    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                RuntimeExceptionDao<Surveys, Integer> surveyDao = mDbHelper.getSurveysRuntimeDao();
                Where<Surveys, Integer> where = surveyDao.queryBuilder().where();
                /*surveysQueryBuilder.where().eq("zoneid", AppPreferences.getZoneId(MapScreen.this));
                surveysQueryBuilder.where().eq("areaid", AppPreferences.getPrefAreaId(MapScreen.this));
                surveysQueryBuilder.where().eq("distareaid", AppPreferences.getDist_Area_Id(MapScreen.this));
                surveysQueryBuilder.where().eq("pendingflag", true);
                surveysQueryBuilder.where().eq("deleteFlag", false);
                PreparedQuery<Surveys> preparedQuery1 = surveysQueryBuilder.prepare();*/
                //added zode id filter
                //@Query("filter[where][zoneid]") int zoneid
                where.and(where.eq("areaid",AppPreferences.getPrefAreaId(MapScreen.this)),
                        where.eq("distareaid",AppPreferences.getDist_Area_Id(MapScreen.this)));
                //where.eq("pendingflag",true),where.eq("deleteFlag",false)
                mfilteredsurveys = where.query();

                RuntimeExceptionDao<Remarks, Integer> remarksDao = mDbHelper.getRemarksRuntimeDao();
                mremarks = remarksDao.queryForAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            for(Surveys survey:mfilteredsurveys){
                if(AppPreferences.getDistributionSubAreaId(MapScreen.this).equalsIgnoreCase("null")){
                    msurveys.add(survey);
                } else {
                    if(Integer.valueOf(AppPreferences.getDistributionSubAreaId(MapScreen.this)).equals(survey.getDistsubareaid())){
                        msurveys.add(survey);
                    }
                }
            }
            userlocation();
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setColor(getResources().getColor(R.color.red));
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setXfermode(null);
            mPaint.setTextSize(50f);
            mPaint.setAlpha(0xff);
            for (int i = 0; i < msurveys.size(); i++) {
                if (msurveys.get(i).getPendingflag() && !msurveys.get(i).getDeleteFlag()) {
                    double sur_lat = msurveys.get(i).getLatitude();
                    double sur_long = msurveys.get(i).getLongitude();
                    LatLng sur_map = new LatLng(sur_lat, sur_long);
                    ///////////////////////////////////////////////
                    double sur_lat_to = msurveys.get(i).getToLatitude();
                    double sur_long_to = msurveys.get(i).getToLongitude();
                    LatLng sur_map_to = new LatLng(sur_lat_to, sur_long_to);

                    Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                    Bitmap bmp = Bitmap.createBitmap(200, 50, conf);
                    Canvas canvas = new Canvas(bmp);
                    canvas.drawText(String.valueOf(i), 0, 50, mPaint); // paint defines the text color, stroke width, size
                    mMap.addMarker(new MarkerOptions()
                            .position(sur_map)
                            .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                            .anchor(0.5f, 1)
                    );
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);
                    mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                    mMap.addMarker(new MarkerOptions().position(sur_map_to).icon(icon));
                    //add polyline
                    mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(sur_lat, sur_long), new LatLng(sur_lat_to, sur_long_to))
                            .width(10)
                            .color(Color.RED));
                }else{
                    double sur_lat = msurveys.get(i).getLatitude();
                    double sur_long = msurveys.get(i).getLongitude();
                    LatLng sur_map = new LatLng(sur_lat, sur_long);

                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);
                    mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
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

