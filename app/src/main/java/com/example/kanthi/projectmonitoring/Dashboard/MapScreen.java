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
import android.graphics.Path;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kanthi.projectmonitoring.Adapters.Remarks_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.PoJo.PoNumbers;
import com.example.kanthi.projectmonitoring.PoJo.Promotions;
import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.Where;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*public class MapScreen extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {*/

public class MapScreen extends AppCompatActivity implements OnMapReadyCallback
        , MapboxMap.OnCameraIdleListener
        //, MapboxMap.OnMapClickListener
        , MapboxMap.OnMarkerClickListener
        , GetPaths, com.mapbox.mapboxsdk.maps.OnMapReadyCallback {
        //private GoogleMap mMap;
        //todo implements MapboxMap.OnMapClickListener

        private MapboxMap mMapbox;
        private MapView mMapView;
       private ArrayList<Path> mPathsList;
       private HashMap<Path, Integer> mPathsMap;
       private double imagelati,imagelongi;
       String address;
       private List<Promotions> mpromotions;
       private String QAImage=null,QAImage2=null,imageQatime=null,QAHeight=null,QADistance=null,qa_comment=null;
        long promotonid;
        private Boolean resource=false,distance_and_height=false,qaorder=false;

        //this is adding on syncking
        private ArrayList<LatLng> points1;
        private List<Surveys> msurvey;
        private List<Surveys> mfilteredsurvey;
        private List<Promotions> mfilteredpromotions;
        private ArrayList<LatLng> points;
        private List<PoNumbers> mpoNumberses;
        int ponumber_value;



    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private LocationManager manager;
    private LocationListener listener;

    // i am added this line

    private double lat;
    private double lon;

    // i am added this line


    private double lati;
    private double longi;
    private LatLng destination;
    private LatLng source;
    private Button surveymap, project_monitor, status_update;
    private TextView tv_surveymap, tv_projectmonitor;
    private ImageView image_marka1,image_marka2;
    private Toolbar toolbar;
    private TextView field_mgr, field_code, field_location, field_desig;
    private TextView startdate, enddate, totaltarget, currenttarget, actualtarget, daysleft, act_stdate, act_enddate;
    private LinearLayout layout;
    private Surveys surveys;
    private ProgressDialog progres;
    private double tolat = 0.0, tolong = 0.0;
    private double fromlat = 0.0, fromlong = 0.0;
    private TextView Next,save, cancel;
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
    private Marker marker;

    @Override
    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }  // i am added new line here

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token)); // i am added new line

        setContentView(R.layout.activity_map_screen);


        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);


        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        surveymap = (Button) findViewById(R.id.surveymap);
        project_monitor = (Button) findViewById(R.id.project_monitor);
        status_update = (Button) findViewById(R.id.statusupdate);
        tv_surveymap = (TextView) findViewById(R.id.tv_pendingtask);
        tv_projectmonitor = (TextView) findViewById(R.id.tv_projectmonitor);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        image_marka1 = findViewById(R.id.image_marker1);
       // image_marka2 = findViewById(R.id.image_marker2);



        Intent in = getIntent();
        Bundle bun = in.getExtras();
        partnerid = bun.getInt("partnerid");
        day = bun.getString("date");

       // i am added both lati and longi
        lati=bun.getDouble("lati");
        longi=bun.getDouble("longi");

        // i am adding new line here

        if(AppPreferences.getGroupId(MapScreen.this)==23||AppPreferences.getGroupId(MapScreen.this)==41||AppPreferences.getGroupId(MapScreen.this)==39){
           // project_mtr.setBackgroundResource(R.drawable.poym_tick_purple);
            tv_projectmonitor.setText("Approval");
            if(AppPreferences.getProjectType(MapScreen.this).equalsIgnoreCase("dynamic")){
                //image_marker.setBackgroundResource(R.drawable.approved);
            }
        }
        else if(AppPreferences.getGroupId(MapScreen.this)==14||AppPreferences.getGroupId(MapScreen.this)==17||AppPreferences.getGroupId(MapScreen.this)==18){
         //   project_mtr.setBackgroundResource(R.drawable.poym_execution_purple); // hide this line also
            if(AppPreferences.getProjectType(MapScreen.this).equalsIgnoreCase("dynamic")){
                image_marka1.setBackgroundResource(R.drawable.execution);
               // image_marka2.setBackgroundResource(R.drawable.execution);
            }
        }

        // i am adding new line here





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

        Next = (TextView) findViewById(R.id.bt_Next);
        save = (TextView) findViewById(R.id.bt_save);
        cancel = (TextView) findViewById(R.id.bt_cancel);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        msurveys=new ArrayList<>();
        mfilteredsurveys=new ArrayList<>();

       // networkRemark();     // i am unhide this colling statement

        progress = new ProgressDialog(MapScreen.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Fetching Data,Please Wait...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
        new FetchDetailsFromDbTask().execute();

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapScreen.this, "Press_Next", Toast.LENGTH_SHORT).show();

                // i am added code here
                if (lat != 0.0 && lon != 0.0) {
                    //Toast.makeText(Survey.this, "" + latitude + "," + longitude, Toast.LENGTH_SHORT).show();
                    Toast.makeText(MapScreen.this, "Location Captured", Toast.LENGTH_SHORT).show();
                    Next.setBackgroundColor(getResources().getColor(R.color.lite_white));
                    Next.setEnabled(false);
                    mMapbox.getUiSettings().setScrollGesturesEnabled(false);


                    // i am added new line here
                  //  details.setDescription(new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(lati))) + "," + new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(longi))));
                    String strlatlng=(new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(latitude))) + "," + new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(longitude))));


                    Toast.makeText(MapScreen.this, "lat & lon "+strlatlng, Toast.LENGTH_SHORT).show();


                    // i am added alertdilog here for taking srino

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MapScreen.this);
                    View view = LayoutInflater.from(MapScreen.this).inflate(R.layout.pending_task_next_detail, null);
                    builder1.setCancelable(false);
//                    builder1.setTitle("Survey");
                    builder1.setView(view);

                    TextView tv_details = view.findViewById(R.id.tv_next_detail);
                    tv_details.setText(strlatlng);

                    TextView tv_result = view.findViewById(R.id.tv_next_show_details);
                    AutoCompleteTextView preslnoauto = view.findViewById(R.id.autoCompletePrivous);
                    //Spinner dropdown = view.findViewById(R.id.dropdown_prev_sl);

                    Button btn_ok = view.findViewById(R.id.btn_pending_next_ok);
                    Button btn_cancel = view.findViewById(R.id.btn_pending_next_cancel);
                    // final AlertDialog alertDialog = builder1.create();

                    AlertDialog alert = builder1.create();

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MapScreen.this, "Okay Button", Toast.LENGTH_SHORT).show();
                        }
                    });


                    alert.show();
                    alert.getWindow().setLayout(1000, 1300);

                    // i am added alertdilog here for taking srino


//                    Polyline polyline=null;

                    // marka2 visual

                    // i am added new polyline
//                    LatLng[] points = new LatLng[2];
//                    LatLng loc = new LatLng(28.47, 77.168);
//                    LatLng dest = new LatLng(28.77, 77.200);
//                    points[0] = loc;
//                    points[1] = dest;
//
//                    if(polyline != null) polyline.remove();

//                    PolylineOptions poly = new PolylineOptions()
//                            .add(points)
//                            .color(Color.parseColor("#3887be"))
//                            .width(5);
//                   // Polyline polyline1 = mMapbox.addPolyline(new com.mapbox.mapboxsdk.annotations.PolylineOptions().add(points));


//                    mMapbox.addPolyline(new com.mapbox.mapboxsdk.annotations.PolylineOptions()
//                            .add(new LatLng(28.47, 77.168), new LatLng(28.77, 77.200)));
//                            .width(10)
//                            .color(Color.green(Color.parseColor())));
//                    line = mMapbox.addPolyline(poly);
                 //  mMapbox.addPolyline(new com.mapbox.mapboxsdk.annotations.PolylineOptions().add(points))

                    /*try {

                        com.mapbox.mapboxsdk.geometry.LatLng latLng1=new com.mapbox.mapboxsdk.geometry.LatLng(28.47, 77.168);
                        com.mapbox.mapboxsdk.geometry.LatLng latLng2=new com.mapbox.mapboxsdk.geometry.LatLng(28.77, 77.200);

                        mMapbox.addPolyline(new com.mapbox.mapboxsdk.annotations.PolylineOptions().add(latLng1,latLng2).width(5).color(Color.red(250)));
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/


                }



                else {
                    Toast.makeText(MapScreen.this, "Drag The Marker", Toast.LENGTH_SHORT).show();
                }



            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               surveymap_popup();

               // Toast.makeText(MapScreen.this, "Press Save", Toast.LENGTH_SHORT).show();
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
      /*  SupportMapFragment mapFragment = (SupportMapFragment) MapScreen.this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

        mMapView.getMapAsync(MapScreen.this);










    }




               // i am commant this code because added map box
  /* @Override
    public void onMarkerDragStart(Marker marker) {

   }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.getTitle().equalsIgnoreCase("To")) {
            tolat = marker.getPosition().lati;
            tolong = marker.getPosition().loang;
            //Toast.makeText(this, "ToMarker:"+tolat+","+tolong, Toast.LENGTH_SHORT).show();
        }
        if (marker.getTitle().equalsIgnoreCase("From")) {
            fromlat = marker.getPosition().lati;
            fromlong = marker.getPosition().loang;
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
    } */  // i am comment this code becouse added mapbox


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
        spinner.setAdapter(adapter);  // i am hide this line
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

               // networkRemark(); // i am added new line here
               // userlocation();
               // userLocation();   // i am added new location
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


    // i am added new method here
    private void userLocation(){
        manager = (LocationManager) MapScreen.this.getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                source = new LatLng(lati, longi);
               new MapScreen.FetchDetailsFromDbTask().execute();




                if (ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                if (i == LocationProvider.OUT_OF_SERVICE) {
                    Toast.makeText(MapScreen.this, "Network Unavaliable", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(MapScreen.this, "Fetching Location,Please Wait", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(MapScreen.this, "Please enable GPS", Toast.LENGTH_SHORT).show();
            }
        };
        if (ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 100, listener);
    }

  // up side i added that method



    private void userlocation() {
        manager = (LocationManager) MapScreen.this.getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                source = new LatLng(lati, longi);
                progress.dismiss();

                  // when am i adding this line i am getting map marka  and after hide this line i an getting current location
                // new MapScreen.FetchDetailsFromDbTask().execute();   // i am added here

                if(msurveys.size()>0){
                    double f_lati = lati = msurveys.get(0).getLatitude();
                    double f_longi = longi = msurveys.get(0).getLongitude();
                    LatLng first_latlon = new LatLng(f_lati, f_longi);

                    double s_lati = lati = msurveys.get(0).getLatitude() - 00.000050;
                    double s_longi = longi = msurveys.get(0).getLongitude() - 00.000050;
                    LatLng second_latlon = new LatLng(s_lati, s_longi);

//                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.execution);
//                    BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.drawable.approved);
                    //mMap.addMarker(new MarkerOptions().position(source).title("User in Source").icon(icon));
                    if (AppPreferences.getGroupId(MapScreen.this) == 14 || AppPreferences.getGroupId(MapScreen.this) == 17 || AppPreferences.getGroupId(MapScreen.this) == 18) {
//                        mMap.addMarker(new MarkerOptions().position(first_latlon).draggable(true).title("To").icon(icon));
//                        mMap.addMarker(new MarkerOptions().position(second_latlon).draggable(true).title("From").icon(icon));


                        //i am added here
                        try {
                            com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(first_latlon.latitude,first_latlon.longitude);
                            IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
                            Icon ico=iconFactory.fromResource(R.drawable.execution);
                            mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng).setIcon(ico));

                            com.mapbox.mapboxsdk.geometry.LatLng latLng1=new com.mapbox.mapboxsdk.geometry.LatLng(second_latlon.latitude,second_latlon.longitude);
                            IconFactory iconFactory1=IconFactory.getInstance(MapScreen.this);
                            Icon icon2=iconFactory1.fromResource(R.drawable.approved);
                            mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng1).setIcon(icon2));
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                    if (AppPreferences.getGroupId(MapScreen.this) == 23 || AppPreferences.getGroupId(MapScreen.this) == 41 || AppPreferences.getGroupId(MapScreen.this) == 39) {
//                        mMap.addMarker(new MarkerOptions().position(first_latlon).draggable(true).title("To").icon(icon1));
//                        mMap.addMarker(new MarkerOptions().position(second_latlon).draggable(true).title("From").icon(icon1));

                        try {
                            com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(first_latlon.latitude,first_latlon.longitude);
                            IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
                            Icon ico=iconFactory.fromResource(R.drawable.execution);
                            mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng).setIcon(ico));

                            com.mapbox.mapboxsdk.geometry.LatLng latLng1=new com.mapbox.mapboxsdk.geometry.LatLng(second_latlon.latitude,second_latlon.longitude);
                            IconFactory iconFactory1=IconFactory.getInstance(MapScreen.this);
                            Icon icon2=iconFactory1.fromResource(R.drawable.approved);
                            mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng1).setIcon(icon2));
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    CameraPosition position = new CameraPosition.Builder()
                                .target(new com.mapbox.mapboxsdk.geometry.LatLng(f_lati, f_longi))
                                .zoom(14)
                                .tilt(20)
                                .build();
                        try {
                            mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }




                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first_latlon, 18));
                }else{
                    double s_lati = lati = location.getLatitude() - 00.000050;
                    double s_longi = longi = location.getLongitude() - 00.000050;
                    LatLng second_latlon = new LatLng(s_lati, s_longi);
//                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.execution);
//                    BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.drawable.approved);
                    //mMap.addMarker(new MarkerOptions().position(source).title("User in Source").icon(icon));
                    if (AppPreferences.getGroupId(MapScreen.this) == 14 || AppPreferences.getGroupId(MapScreen.this) == 17 || AppPreferences.getGroupId(MapScreen.this) == 18) {
//                        mMap.addMarker(new MarkerOptions().position(source).draggable(true).title("To").icon(icon));
//                        mMap.addMarker(new MarkerOptions().position(second_latlon).draggable(true).title("From").icon(icon));


                     // i am hideing this this icon
                        try {

                            com.mapbox.mapboxsdk.geometry.LatLng latLng1=new com.mapbox.mapboxsdk.geometry.LatLng(second_latlon.latitude,second_latlon.longitude);
                            IconFactory iconFactory1=IconFactory.getInstance(MapScreen.this);
                            Icon icon2=iconFactory1.fromResource(R.drawable.approved);
                            mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng1).setIcon(icon2));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        // i am hideing this this icon


                        CameraPosition position = new CameraPosition.Builder()
                                .target(new com.mapbox.mapboxsdk.geometry.LatLng(s_lati, s_longi))
                                .zoom(14)
                                .tilt(20)
                                .build();
                        try {
                            mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                    if (AppPreferences.getGroupId(MapScreen.this) == 23 || AppPreferences.getGroupId(MapScreen.this) == 41 || AppPreferences.getGroupId(MapScreen.this) == 39) {
//                        mMap.addMarker(new MarkerOptions().position(source).draggable(true).title("To").icon(icon1));
//                        mMap.addMarker(new MarkerOptions().position(second_latlon).draggable(true).title("From").icon(icon1));

                        try {

                            com.mapbox.mapboxsdk.geometry.LatLng latLng1=new com.mapbox.mapboxsdk.geometry.LatLng(second_latlon.latitude,second_latlon.longitude);
                            IconFactory iconFactory1=IconFactory.getInstance(MapScreen.this);
                            Icon icon2=iconFactory1.fromResource(R.drawable.approved);
                            mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng1).setIcon(icon2));
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                 //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(source, 18));

                    CameraPosition position = new CameraPosition.Builder()
                            .target(new com.mapbox.mapboxsdk.geometry.LatLng(/*lati, longi*/s_lati, s_longi))
                            .zoom(14)
                            .tilt(20)
                            .build();
                    try {
                        mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }



                    //  new MapScreen.FetchDetailsFromDbTask().execute();   // i am added here
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

                // i am hideing this all line
//                mMap.setMyLocationEnabled(true);
//                //mMap.getUiSettings().setZoomControlsEnabled(true);
//                mMap.getUiSettings().setCompassEnabled(true);
//                mMap.getUiSettings().setMyLocationButtonEnabled(true);
//                mMap.getUiSettings().setAllGesturesEnabled(true);


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

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        // i am added new line here


        if (marker.getTitle().equalsIgnoreCase("To")) {
            tolat = marker.getPosition().getLatitude();
            tolong = marker.getPosition().getLongitude();
            //Toast.makeText(this, "ToMarker:"+tolat+","+tolong, Toast.LENGTH_SHORT).show();
        }
        if (marker.getTitle().equalsIgnoreCase("From")) {
            fromlat = marker.getPosition().getLatitude();
            fromlong = marker.getPosition().getLongitude();
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




        // i am added new line here






    }

    @Override
    public void polygonPaths(ArrayList<Path> path, HashMap<Path, Integer> mPathsMap) {


        this.mPathsList.clear();
        this.mPathsMap.clear();
        this.mPathsList.addAll(path);
        this.mPathsMap.putAll(mPathsMap);

    }

    @Override
    public void onCameraIdle() {

        //i am added this new line
        if(mMapbox !=null) {
            com.mapbox.mapboxsdk.geometry.LatLng latLng = mMapbox.getCameraPosition().target;
            if (latLng != null) {
                latitude = latLng.getLatitude();
                longitude = latLng.getLongitude();

                AppPreferences.setImageLatitude(MapScreen.this,String.valueOf(latitude));
                AppPreferences.setImageLongitude(MapScreen.this,String.valueOf(longitude));
                try{
                    //  getAddres(imagelati,imagelongi); // i am added method here

                    getAddress1(latitude,longitude);
                    getAddress2(latitude,longitude);
                }catch (Exception e){
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
            }

        }

       /* // i am hidding this code

        com.mapbox.mapboxsdk.geometry.LatLng latLng = mMapbox.getCameraPosition().target;
        if(source!=null){

//            imagelati=latLng.getLatitude();
//            imagelongi=latLng.getLongitude();

            lati= latLng.getLatitude();
            longi=latLng.getLongitude();


            AppPreferences.setImageLatitude(MapScreen.this,String.valueOf(lati));
            AppPreferences.setImageLongitude(MapScreen.this,String.valueOf(longi));
            try{
              //  getAddres(imagelati,imagelongi); // i am added method here

                getAddress1(lati,longi);
                getAddress2(lati,longi);
            }catch (Exception e){
                e.printStackTrace();
            }
        }*/

    }

    private void getAddres(double imagelati, double imagelongi) {


        try {
            Geocoder geocoder = new Geocoder(MapScreen.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(imagelati, imagelongi, 1);
            if (addresses != null && addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); //comlete address
                String city = addresses.get(0).getLocality(); //bangalore
                String state = addresses.get(0).getAdminArea(); //karnataka
                String postalCode = addresses.get(0).getPostalCode(); //560076
                String subLocality = addresses.get(0).getSubLocality(); //arekere
                String subarea = addresses.get(0).getSubAdminArea(); //bangalore urban
                String featurename = addresses.get(0).getFeatureName();////peoplecalled

                Log.d("getAddress:", address);
                Log.d("getcity:",city);
                Log.d("getstate:", state);
                Log.d("getcode:", postalCode);
                Log.d("getsubloca:", subLocality);
                Log.d("getsubarea:", subarea);
                Log.d("getfeaturename:", featurename);
            }
        } catch (IOException e) {
            e.printStackTrace();
            address="not found";
        }

    }

    @Override
    public boolean onMarkerClick(@NonNull  com.mapbox.mapboxsdk.annotations.Marker marker) {
        com.mapbox.mapboxsdk.geometry.LatLng position = marker.getPosition(); //
        String lat= String.valueOf(position.getLatitude());
        String lon= String.valueOf(position.getLongitude());
        for (int i = 0; i < mpromotions.size(); i++) {
            String pro_lat=String.valueOf(mpromotions.get(i).getLatitude());
            String pro_lon=String.valueOf(mpromotions.get(i).getLongitude());
            if (lat.equalsIgnoreCase(pro_lat) && lon.equalsIgnoreCase(pro_lon)) {
                Log.d("latlon",""+lat+","+lon);
                QAImage=mpromotions.get(i).getRetailerimage();
                QAImage2=mpromotions.get(i).getQaimage();
                promotonid=mpromotions.get(i).getId();
                imageQatime=mpromotions.get(i).getQatime();
                qa_comment=mpromotions.get(i).getComment();
                QADistance= String.valueOf(mpromotions.get(i).getDistance());
                QAHeight= String.valueOf(mpromotions.get(i).getHeight());
              //  Toast.makeText(this, "Marker Selected", Toast.LENGTH_SHORT).show();

                Toast.makeText(this, "Press_Blue_dot  "+promotonid, Toast.LENGTH_SHORT).show();  // i am added new line here

                if(imageQatime!=null){
                    Log.d("calling","done");
                    AppPreferences.setPrefCaptureImage(MapScreen.this,QAImage);
                    distance_and_height=true;
                    resource=true;
                    qaorder=true;
                    AppPreferences.setResourceFlag(MapScreen.this,true);
                    AppPreferences.setBoqQAFlag(MapScreen.this,true);
                }
                break;
            }
        }

        // i am added new line here foe black dot click event

        for (int i = 0; i < msurvey.size(); i++) {
            String survey_lat=String.valueOf(msurvey.get(i).getLatitude());
            String survey_lon=String.valueOf(msurvey.get(i).getLongitude());
            if (lat.equalsIgnoreCase(survey_lat) && lon.equalsIgnoreCase(survey_lon)) {
                Log.d("latlon",""+lat+","+lon);

              long  surveyid=msurvey.get(i).getId();

                //  Toast.makeText(this, "Marker Selected", Toast.LENGTH_SHORT).show();


//                try {
//
//
//                    com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(msurvey.get(i).getLatitude(),msurvey.get(i).getLatitude());
//                    IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
//                    Icon icon1=iconFactory.fromResource(R.drawable.marker_black);  // i am change here black to boder
//                    mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng).setIcon(icon1).setTitle("hii"));
                    // mMapbox.addPolygon(new PolygonOptions().addAll((Iterable<com.mapbox.mapboxsdk.geometry.LatLng>) latLng));    // i am added this line here for draw line
//                    setTitle("hii");
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

                setTitle("Black");


                Toast.makeText(this, "Press_Black_dot "+surveyid, Toast.LENGTH_SHORT).show();  // i am added new line here


                break;
            }
        }


        // i am added new line here foe black dot click event



        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
       // userLocation();
//        mMap = googleMap;
//        mMap.setOnMarkerClickListener(this);
//        mMap.setOnCameraIdleListener(this);

    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        mMapbox = mapboxMap;
        mapboxMap.addOnCameraIdleListener(this);
        mapboxMap.setOnMarkerClickListener(this);

        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        Log.d("off_check","offline check map");
                        //todo unmask for offline
                        progress.show();
                       // userlocation();

                        userLocation();

                        try {

                            com.mapbox.mapboxsdk.geometry.LatLng latLng1=new com.mapbox.mapboxsdk.geometry.LatLng(28.47, 77.168);
                            com.mapbox.mapboxsdk.geometry.LatLng latLng2=new com.mapbox.mapboxsdk.geometry.LatLng(28.77, 77.200);

                            mMapbox.addPolyline(new com.mapbox.mapboxsdk.annotations.PolylineOptions().add(latLng1,latLng2).width(5).color(Color.red(250)));
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                });


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
//            try {
//
//                // i am added code here
//                msurvey=new ArrayList<Surveys>();
//                mfilteredsurvey=new ArrayList<>();
//                mpromotions=new ArrayList<>();
//                mfilteredpromotions=new ArrayList<>();
//                // i am added code here
//
//
//                RuntimeExceptionDao<Surveys, Integer> surveyDao = mDbHelper.getSurveysRuntimeDao();
//                Where<Surveys, Integer> where = surveyDao.queryBuilder().where();
//                /*surveysQueryBuilder.where().eq("zoneid", AppPreferences.getZoneId(MapScreen.this));
//                surveysQueryBuilder.where().eq("areaid", AppPreferences.getPrefAreaId(MapScreen.this));
//                surveysQueryBuilder.where().eq("distareaid", AppPreferences.getDist_Area_Id(MapScreen.this));
//                surveysQueryBuilder.where().eq("pendingflag", true);
//                surveysQueryBuilder.where().eq("deleteFlag", false);
//                PreparedQuery<Surveys> preparedQuery1 = surveysQueryBuilder.prepare();*/
//                //added zode id filter
//                //@Query("filter[where][zoneid]") int zoneid
//                where.and(where.eq("areaid",AppPreferences.getPrefAreaId(MapScreen.this)),
//                        where.eq("distareaid",AppPreferences.getDist_Area_Id(MapScreen.this)));
//                //where.eq("pendingflag",true),where.eq("deleteFlag",false)
//                mfilteredsurveys = where.query();
//
//                RuntimeExceptionDao<Remarks, Integer> remarksDao = mDbHelper.getRemarksRuntimeDao();
//                mremarks = remarksDao.queryForAll();
//
//                // i am added new line here
//
//
//                // i am added new line here
//
//
//
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;


            try{
               // userlocation(); // i am addedhere

                msurvey=new ArrayList<Surveys>();
                mfilteredsurvey=new ArrayList<>();
                mpromotions=new ArrayList<>();
                mfilteredpromotions=new ArrayList<>();
                RuntimeExceptionDao<Surveys, Integer> surveyDao = mDbHelper.getSurveysRuntimeDao();
                Where<Surveys, Integer> wheresurveys = surveyDao.queryBuilder().where();
                if(AppPreferences.getGroupId(MapScreen.this)==23||AppPreferences.getGroupId(MapScreen.this)==41
                        ||AppPreferences.getGroupId(MapScreen.this)==39){
                    wheresurveys.eq("routeassignmentid",AppPreferences.getRouteSalesViewid(MapScreen.this));
                    //wheresurveys.and(wheresurveys.eq("routeassignmentid",AppPreferences.getRouteSalesViewid(ProjectMonitorActivity.this))
                    //                            wheresurveys.eq("order","slno ASC"));
                }else{
                    //wheresurveys.eq("zoneid",AppPreferences.getZoneId(ProjectMonitorActivity.this)), added filter
                    wheresurveys.and(wheresurveys.eq("areaid",AppPreferences.getPrefAreaId(MapScreen.this)),
                            wheresurveys.eq("distareaid",AppPreferences.getDist_Area_Id(MapScreen.this)));
                    //wheresurveys.eq("order","slno ASC")
                }
                mfilteredsurvey = wheresurveys.query();

                RuntimeExceptionDao<Promotions, Integer> promotionsDao = mDbHelper.getPromotionsRuntimeDao();
                Where<Promotions, Integer> wherepromotions = promotionsDao.queryBuilder().where();
                if(AppPreferences.getGroupId(MapScreen.this)==23||AppPreferences.getGroupId(MapScreen.this)==41
                        ||AppPreferences.getGroupId(MapScreen.this)==39){
                    wherepromotions.eq("routeassignmentid",AppPreferences.getRouteSalesViewid(MapScreen.this));
                }else{
                    //wherepromotions.eq("zoneid",AppPreferences.getZoneId(ProjectMonitorActivity.this)), added filter
                    wherepromotions.and(wherepromotions.eq("areaid",AppPreferences.getPrefAreaId(MapScreen.this)),
                            wherepromotions.eq("distareaid",AppPreferences.getDist_Area_Id(MapScreen.this)));
                }
                mfilteredpromotions = wherepromotions.query();

                RuntimeExceptionDao<PoNumbers, Integer> poNumbersDoa = mDbHelper.getPoNumbersRuntimeDao();
                mpoNumberses=poNumbersDoa.queryForAll();


                // i am added remarka here
                RuntimeExceptionDao<Remarks, Integer> remarksDao = mDbHelper.getRemarksRuntimeDao();
                mremarks = remarksDao.queryForAll();
                // i am added remarka here



                if(AppPreferences.getProjectType(MapScreen.this).equalsIgnoreCase("static")){
                    //getFloors();
                    Toast.makeText(MapScreen.this, "get floors data", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;




        }

      /*  @Override
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
            userLocation();     // i am change this method call statement userlocation()
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



                    // i am added new line and hide this line up
//                    try {
//
//                        com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(sur_map.latitude,sur_map.longitude);
//                        IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
//                        Icon icon1=iconFactory.fromResource(R.drawable.marker_black);
//                        //mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng).setIcon(bmps));
//                        mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng).setIcon(icon1));
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }


                  //  mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latlng).setIcon(BitmapDescriptorFactory.fromBitmap(bmp)).anchor(0.5f, 1));




//                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);
//                    mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
//                    mMap.addMarker(new MarkerOptions().position(sur_map_to).icon(icon));
//                    //add polyline
//                    mMap.addPolyline(new PolylineOptions()
//                            .add(new LatLng(sur_lat, sur_long), new LatLng(sur_lat_to, sur_long_to))
//                            .width(10)
//                            .color(Color.RED));

                    // i am added new line here comment upline
                    try {
                        com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(sur_map.latitude,sur_map.longitude);
                        IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
                        Icon icon1=iconFactory.fromResource(R.drawable.marker_black);
                        mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng).setIcon(icon1));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(sur_map_to.latitude,sur_map_to.longitude);
                        IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
                        Icon icon1=iconFactory.fromResource(R.drawable.marker_black);
                        mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng).setIcon(icon1));

                    }catch (Exception e){
                        e.printStackTrace();
                    }




                }else{
                    double sur_lat = msurveys.get(i).getLatitude();
                    double sur_long = msurveys.get(i).getLongitude();
                    LatLng sur_map = new LatLng(sur_lat, sur_long);

//                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);
//                    mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));


                    // i am added new line here hide uper line


                    try {
                        com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(sur_map.latitude,sur_map.longitude);
                        IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
                        Icon icon1=iconFactory.fromResource(R.drawable.marker_black);
                        mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng).setIcon(icon1));

                    }catch (Exception e){
                        e.printStackTrace();
                    }




                }
            }

        }*/


        // i am adding new method here and  comment up method
        @Override
        protected void onPostExecute(Void result) {
           // userlocation();
            progress.dismiss();
            Log.d("off_check","offline check db");
            points1=new ArrayList<LatLng>();

            if(AppPreferences.getGroupId(MapScreen.this)==14
                    ||AppPreferences.getGroupId(MapScreen.this)==17
                    ||AppPreferences.getGroupId(MapScreen.this)==18){
                for(Surveys survey:mfilteredsurvey){
                    if(AppPreferences.getDistributionSubAreaId(MapScreen.this).equalsIgnoreCase("null")){
                        msurvey.add(survey);
                    } else {
                        if(Integer.valueOf(AppPreferences.getDistributionSubAreaId(MapScreen.this)).equals(survey.getDistsubareaid())){
                            msurvey.add(survey);
                        }
                    }
                }
            }else{
                msurvey.addAll(mfilteredsurvey);
            }

            if(AppPreferences.getGroupId(MapScreen.this)==14
                    ||AppPreferences.getGroupId(MapScreen.this)==17
                    ||AppPreferences.getGroupId(MapScreen.this)==18){
                for(Promotions promotions:mfilteredpromotions){
                    if(AppPreferences.getDistributionSubAreaId(MapScreen.this).equalsIgnoreCase("null")){
                        mpromotions.add(promotions);
                    } else {
                        if(Integer.valueOf(AppPreferences.getDistributionSubAreaId(MapScreen.this)).equals(promotions.getDistsubareaid())){
                            mpromotions.add(promotions);
                        }
                    }
                }
            }else{
                mpromotions.addAll(mfilteredpromotions);
            }

            if(AppPreferences.getProjectType(MapScreen.this).equalsIgnoreCase("dynamic")){
                if(source!=null){
                    if(AppPreferences.getGroupId(MapScreen.this)==14
                            ||AppPreferences.getGroupId(MapScreen.this)==17
                            ||AppPreferences.getGroupId(MapScreen.this)==18){
                        if(msurvey.size()>0) {

                            // i am added new line here


                             lat = msurvey.get(0).getLatitude();
                             lon = msurvey.get(0).getLongitude();


                            double f_lati = lati = msurvey.get(0).getLatitude();
                            double f_longi = longi = msurvey.get(0).getLongitude();
                            LatLng first_latlon = new LatLng(f_lati, f_longi);

                            double s_lati = lati = msurvey.get(0).getLatitude() - 00.000050;
                            double s_longi = longi = msurvey.get(0).getLongitude() - 00.000050;
                            LatLng second_latlon = new LatLng(s_lati, s_longi);

//                            i am hide this code for stop showing icon

//                            try {
//
//
//                                com.mapbox.mapboxsdk.geometry.LatLng latLng1 = new com.mapbox.mapboxsdk.geometry.LatLng(second_latlon.latitude, second_latlon.longitude /*lati,longi*/);
//                                IconFactory iconFactory1 = IconFactory.getInstance(MapScreen.this);
//                                Icon icon2 = iconFactory1.fromResource(R.drawable.approved);
//                                mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng1).setIcon(icon2)); // i am added title here
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }

//                            i am hide this code for stop showing icon


                            CameraPosition pos = new CameraPosition.Builder()
                                    .target(new com.mapbox.mapboxsdk.geometry.LatLng(lat, lon))
                                    .zoom(20)
                                    .tilt(20)
                                    .build();
                            try {
                                mMapbox.animateCamera(CameraUpdateFactory.newCameraPosition(pos), 1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                         /*   try {

                                com.mapbox.mapboxsdk.geometry.LatLng latLng1=new com.mapbox.mapboxsdk.geometry.LatLng(source.latitude,source.longitude);
                                IconFactory iconFactory1=IconFactory.getInstance(MapScreen.this);
                                Icon icon2=iconFactory1.fromResource(R.drawable.execution);
                                mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng1).setIcon(icon2));
                            }catch (Exception e){
                                e.printStackTrace();
                            }*/


                            // i am added new line here

                         /*   double lat = msurvey.get(0).getLatitude();
                            double lon = msurvey.get(0).getLongitude();

                            try {

                                com.mapbox.mapboxsdk.geometry.LatLng latLng1=new com.mapbox.mapboxsdk.geometry.LatLng(lat,lon);
                                IconFactory iconFactory1=IconFactory.getInstance(MapScreen.this);
                                Icon icon2=iconFactory1.fromResource(R.drawable.approved);
                                mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng1).setIcon(icon2));
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                            CameraPosition pos = new CameraPosition.Builder()
                                    .target(new com.mapbox.mapboxsdk.geometry.LatLng(lat, lon))
                                    .zoom(20)
                                    .tilt(20)
                                    .build();
                            try {
                                mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(pos), 1000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }*/

                        }else {



                            CameraPosition position = new CameraPosition.Builder()
                                    .target(new com.mapbox.mapboxsdk.geometry.LatLng(lati, longi))
                                    .zoom(20)
                                    .tilt(20)
                                    .build();
                            try {
                                mMapbox.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }else if(AppPreferences.getGroupId(MapScreen.this)==23
                            ||AppPreferences.getGroupId(MapScreen.this)==41
                            || AppPreferences.getGroupId(MapScreen.this)==39){
                        if(mpromotions.size()>0){
                            double lat = Double.parseDouble(mpromotions.get(0).getLatitude());
                            double lon = Double.parseDouble(mpromotions.get(0).getLongitude());
                            CameraPosition pos = new CameraPosition.Builder()
                                    .target(new com.mapbox.mapboxsdk.geometry.LatLng(lat, lon))
                                    .zoom(14)
                                    .tilt(20)
                                    .build();
                            try {
                                mMapbox.animateCamera(CameraUpdateFactory.newCameraPosition(pos), 1000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }else {
                            CameraPosition position = new CameraPosition.Builder()
                                    .target(new com.mapbox.mapboxsdk.geometry.LatLng(lati, longi))
                                    .zoom(14)
                                    .tilt(20)
                                    .build();
                            try {
                                mMapbox.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }else{
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new com.mapbox.mapboxsdk.geometry.LatLng(lati, longi))
                                .zoom(14)
                                .tilt(20)
                                .build();
                        try {
                            mMapbox.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }



            if(AppPreferences.getProjectType(MapScreen.this).equalsIgnoreCase("dynamic")){
                if(msurvey!=null){
                    for(int i=0;i<msurvey.size();i++){
                        if(msurvey.get(i).getPendingflag()!=null){
                            if(msurvey.get(i).getPendingflag()){
                                double sur_lat =msurvey.get(i).getLatitude();
                                double sur_long =msurvey.get(i).getLongitude();
                                LatLng sur_map = new LatLng(sur_lat, sur_long);
                                double sur_lat_to = msurvey.get(i).getToLatitude();
                                double sur_long_to = msurvey.get(i).getToLongitude();
                                LatLng sur_map_to = new LatLng(sur_lat_to, sur_long_to);
                                //todo add markers and polyline
                                try {
                                    com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(sur_map.latitude,sur_map.longitude);
                                    IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
                                    Icon icon1=iconFactory.fromResource(R.drawable.marker_black);   // i am changing here black to red dot
                                    mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng).setIcon(icon1));

                                    com.mapbox.mapboxsdk.geometry.LatLng latLng1=new com.mapbox.mapboxsdk.geometry.LatLng(sur_map_to.latitude,sur_map_to.longitude);
                                    IconFactory iconFactory1=IconFactory.getInstance(MapScreen.this);
                                    Icon icon2=iconFactory1.fromResource(R.drawable.marker_black);
                                    mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng1).setIcon(icon2));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);

                                //mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                                //mMap.addMarker(new MarkerOptions().position(sur_map_to).icon(icon));
                                //add polyline
                                //mMap.addPolyline(new PolylineOptions().add(new LatLng(sur_lat,sur_long), new LatLng(sur_lat_to, sur_long_to)).width(10).color(Color.RED));
                            }
                            else {
                                double sur_lat = msurvey.get(i).getLatitude();
                                double sur_long = msurvey.get(i).getLongitude();
                                 long mtest = msurvey.get(i).getId();

                                 String titleid=Long.toString(mtest);



                                LatLng sur_map = new LatLng(sur_lat, sur_long);
                                points1.add(sur_map);
                                //todo add markers and polyline



                                try {


                                    com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(sur_map.latitude,sur_map.longitude);
                                    IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
                                    Icon icon1=iconFactory.fromResource(R.drawable.marker_black);  // i am change here black to boder
                                    mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().position(latLng).setIcon(icon1));
                                   // mMapbox.addPolygon(new PolygonOptions().addAll((Iterable<com.mapbox.mapboxsdk.geometry.LatLng>) latLng));    // i am added this line here for draw line
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);
                                //mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                                //mMap.addPolyline(new PolylineOptions().addAll(points1).width(10).color(Color.GRAY));

                            }
                        }
                    }
                }
            }
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setColor(getResources().getColor(R.color.blue));
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setXfermode(null);
            mPaint.setTextSize(50f);
            mPaint.setAlpha(0xff);
            points=new ArrayList<LatLng>();
            if(AppPreferences.getProjectType(MapScreen.this).equalsIgnoreCase("static")){


            }
            //todo
            Log.e("promotions",""+mpromotions.size());
            if(AppPreferences.getProjectType(MapScreen.this).equalsIgnoreCase("dynamic")){
                for(int j=0;j<mpromotions.size();j++){
                    if(mpromotions.get(j).getLatitude()!=null && mpromotions.get(j).getLongitude()!=null){
                        Log.e("off_check","not null latlon");
                        double pro_lat= Double.parseDouble(mpromotions.get(j).getLatitude());
                        double pro_longi=Double.parseDouble(mpromotions.get(j).getLongitude());
                        String qatime=mpromotions.get(j).getQatime();
                        if(pro_lat!=0.0&&pro_longi!=0.0){
                            LatLng prolatlong=new LatLng(pro_lat,pro_longi);
                            points.add(prolatlong);
                            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                            Bitmap bmp = Bitmap.createBitmap(200, 50, conf);
                            Canvas canvas = new Canvas(bmp);
                            canvas.drawText(String.valueOf(j), 0, 50, mPaint); // paint defines the text color, stroke width, size

                            //todo add markers
                            //mMap.addMarker(new MarkerOptions().position(prolatlong).icon(BitmapDescriptorFactory.fromBitmap(bmp)).anchor(0.5f, 1));
                            if(AppPreferences.getGroupId(MapScreen.this)==23||AppPreferences.getGroupId(MapScreen.this)==41){
                                if((AppPreferences.getGroupId(MapScreen.this)==23||AppPreferences.getGroupId(MapScreen.this)==41) && qatime!=null){
                                    //todo add markers
                                    try {
                                        com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(prolatlong.latitude,prolatlong.longitude);
                                        IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
                                        Icon icon1=iconFactory.fromResource(R.drawable.green_marker);
                                        mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().title(String.valueOf(latLng)).position(latLng).setIcon(icon1));
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.green_marker);
                                    //mMap.addMarker(new MarkerOptions().position(prolatlong).icon(icon));
                                }else{
                                    Log.e("off_check","qa data");
                                    //todo add marker
                                    double s_lati = lati = prolatlong.latitude;
                                    double s_longi = longi = prolatlong.longitude;
                                    //double s_lati = lati = prolatlong.latitude - 00.000180;
                                    //double s_longi = longi = prolatlong.longitude - 00.000180;
                                    try {
                                        com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(s_lati,s_longi);
                                        IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
                                        Icon icon1=iconFactory.fromResource(R.drawable.yellow_marker1);
                                        mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().title(String.valueOf(latLng)).position(latLng).setIcon(icon1));
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.yellow_marker1);
                                    //mMap.addMarker(new MarkerOptions().position(prolatlong).icon(icon));


                                }
                            }
                            if(AppPreferences.getGroupId(MapScreen.this)==14
                                    ||AppPreferences.getGroupId(MapScreen.this)==17
                                    ||AppPreferences.getGroupId(MapScreen.this)==18){
                                //todo add markers
                                try {
                                    com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(prolatlong.latitude,prolatlong.longitude);
                                    IconFactory iconFactory=IconFactory.getInstance(MapScreen.this);
                                    if(mpromotions.get(j).getRouteassignmentsummaryid()==AppPreferences.getRouteAssignmentSummaryId(MapScreen.this)){
                                        Icon icon1=iconFactory.fromResource(R.drawable.blue_marker);
                                        mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().title(String.valueOf(latLng)).position(latLng).setIcon(icon1));
                                    }else if(mpromotions.get(j).getRouteassignmentsummaryid()==AppPreferences.getDependentRouteAssignmentSummaryId(MapScreen.this)){
                                        Icon icon1=iconFactory.fromResource(R.drawable.green_marker);
                                        mMapbox.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions().title(String.valueOf(latLng)).position(latLng).setIcon(icon1));
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.blue_marker);
                                //mMap.addMarker(new MarkerOptions().position(prolatlong).icon(icon));

                            }
                        }
                    }
                }
            }

            ponumber_value=mpoNumberses.get(0).getNextvalue()==null?0:mpoNumberses.get(0).getNextvalue();
        }
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        mDbHelper = null;
        super.onDestroy();
        mMapView.onDestroy(); // i am added new line
    }

//    @Override
//    public void onDestroy() {
//        OpenHelperManager.releaseHelper();
//        mDbHelper = null;
//        super.onDestroy();
//        mMapView.onDestroy();
//    }

    // here to below added new line

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



}

