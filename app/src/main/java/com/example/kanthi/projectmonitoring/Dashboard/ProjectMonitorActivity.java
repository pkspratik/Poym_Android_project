package com.example.kanthi.projectmonitoring.Dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Canvas.CustomCanvas;
import com.example.kanthi.projectmonitoring.Canvas.Floor;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Distance_cam.Distance_Camera;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.PoJo.Cord;
import com.example.kanthi.projectmonitoring.PoJo.PoNumbers;
import com.example.kanthi.projectmonitoring.PoJo.Promotions;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.offline.OfflineRegionError;
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectMonitorActivity extends AppCompatActivity implements OnMapReadyCallback
        , MapboxMap.OnCameraIdleListener
        //, MapboxMap.OnMapClickListener
        , MapboxMap.OnMarkerClickListener
        , GetPaths{
    //private GoogleMap mMap;
    //todo implements MapboxMap.OnMapClickListener

    private MapboxMap mMapbox;
    private MapView mMapView;

    //offline progress bar
    private ProgressBar progressBar;
    // JSON encoding/decoding
    public static final String JSON_CHARSET = "UTF-8";
    public static final String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";

    // Offline objects
    private OfflineManager offlineManager;
    private OfflineRegion offlineRegion;

    private LocationManager manager;
    private LocationListener listener;
    private double lati;
    private double longi;
    private LatLng source;
    private DonutProgress chart;
    private FloatingActionButton image_popup,resource_dts,previouslocation;
    TextView distance_height,boq,submit;
    private Button survey_map, project_mtr, stats_update;
    private TextView tv_projectmonitor;
    private ImageView image_marker,blueprint;
    int ponumber_value;
    private List<PoNumbers> mpoNumberses;
    Promotions promotions;
    String cal,remarkname,comment=null;
    double latitude,longitude;
    private List<Surveys> msurvey;
    private List<Surveys> mfilteredsurvey;
    private List<Promotions> mpromotions;
    private List<Promotions> mfilteredpromotions;
    private ArrayList<LatLng> points;
    private ArrayList<LatLng> points1;
    private ProgressDialog progres;
    private double imagelati,imagelongi;
    private LinearLayout layout;
    private TextView startdate,enddate,totaltarget,currenttarget,actualtarget,daysleft,act_stdate,act_enddate;
    int partnerid;
    String day,finaldate;
    private File file;
    private String imagepath,qa_comment=null;
    private int warehouseid,routeid,routepartnerid;
    String filePath="";
    String imagename;
    String address;
    private CustomCanvas customCanvas;
    private JSONArray test;
    private RelativeLayout mLayout;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private ArrayList<Cord> mcord;
    /*TODO unmask for map */
    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;
    public static final PatternItem DOT = new Dot();
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);
    private Paint mPaint;
    private Boolean resource=false,distance_and_height=false;
    private Boolean order=false,previous=false,qaorder=false;
    long sp_id=0,promotonid;
    //SupportMapFragment mapFragment;
    private ArrayList<Path> mPathsList;
    private HashMap<Path, Integer> mPathsMap;
    private ArrayList<Floor> mFloorList;
    ProgressDialog Dialog1;
    private String mShape;
    private TextView mFloorTv;
    ProgressDialog progress;
    private String QAImage=null,QAImage2=null,imageQatime=null,QAHeight=null,QADistance=null;
    BaseActivity baseActivity;
    private CoordinatorLayout coordinatorLayout;

    public static final int REQUEST_IMAGE_CAPTURE=1;
    private Uri picUri;
    private File picFile;
    public static String imagePath;
    private static final float maxHeight = 920.0f;
    private static final float maxWidth = 920.0f;
    ProgressDialog mObjDialog;

    AvahanSqliteDbHelper mDbHelper;
    @Override
    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_project_monitor);

        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        mMapView = findViewById(R.id.mapView);
        progressBar = findViewById(R.id.progress_bar);
        mMapView.onCreate(savedInstanceState);

        mObjDialog = new ProgressDialog(ProjectMonitorActivity.this);
        mObjDialog.setMessage("Saving Image,Please Wait..");
        mObjDialog.setIndeterminate(true);
        mObjDialog.setCancelable(false);

        imagePath = createFile();

        AppPreferences.setPrefCaptureImage(ProjectMonitorActivity.this,null);
        AppPreferences.setImageDistance(ProjectMonitorActivity.this,null);
        AppPreferences.setImageHeight(ProjectMonitorActivity.this,null);
        AppPreferences.setQACaptureImage(ProjectMonitorActivity.this,null);
        AppPreferences.setSubTaskType(ProjectMonitorActivity.this,null);
        AppPreferences.setResourceFlag(ProjectMonitorActivity.this,false);
        AppPreferences.setPreviousLocationFlag(ProjectMonitorActivity.this,false);
        AppPreferences.setBoqFlag(ProjectMonitorActivity.this,false);
        AppPreferences.setBoqQAFlag(ProjectMonitorActivity.this,false);
        AppPreferences.setPreviousLocationId(ProjectMonitorActivity.this,0);
        AppPreferences.setProjectType(ProjectMonitorActivity.this,"dynamic");
        mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());
        mcord=new ArrayList<>();
        mPathsList = new ArrayList<>();
        mPathsMap = new HashMap<>();
        mFloorList = new ArrayList<Floor>();
        final Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        //latitude=bundle.getDouble("Latitude");
        //longitude=bundle.getDouble("Longitude");
        partnerid=bundle.getInt("partnerid");
        day=bundle.getString("date");
        if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==14||AppPreferences.getGroupId(ProjectMonitorActivity.this)==17||AppPreferences.getGroupId(ProjectMonitorActivity.this)==18){
            getSupportActionBar().setTitle("Execution"+" ( "+day+" )" );
        }else{
            getSupportActionBar().setTitle("Approval"+" ( "+day+" )");
        }
        if (ContextCompat.checkSelfPermission(ProjectMonitorActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&ContextCompat.checkSelfPermission(ProjectMonitorActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProjectMonitorActivity.this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    33);

        }

        Dialog1 = new ProgressDialog(ProjectMonitorActivity.this);
        Dialog1.setMessage("Loading...");
        Dialog1.setCancelable(false);

        /*mapFragment = (SupportMapFragment) ProjectMonitorActivity.this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/


        final Calendar calendar1 = Calendar.getInstance();
        int yy = calendar1.get(Calendar.YEAR);
        final int mm = calendar1.get(Calendar.MONTH);
        final int m = mm;
        final int dd = calendar1.get(Calendar.DAY_OF_MONTH);
        String st_date=AppUtilities.getDateString(yy, m, dd);
        final String d=st_date.substring(0,2);
        String mo=st_date.substring(3,6);
        finaldate=mo+" "+d;
        chart= (DonutProgress) findViewById(R.id.circle_progress);
        chart.setProgress(AppPreferences.getChartPercentage(ProjectMonitorActivity.this));
        //Toast.makeText(this, ""+AppPreferences.getChartPercentage(ProjectMonitorActivity.this), Toast.LENGTH_SHORT).show();
        Calendar calendar=Calendar.getInstance();
        final Date date=calendar.getTime();
        cal= String.valueOf(date);
        startdate= (TextView) findViewById(R.id.startdate);
        enddate= (TextView) findViewById(R.id.enddate);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        totaltarget= (TextView) findViewById(R.id.tot_target);
        currenttarget= (TextView) findViewById(R.id.curr_target);
        actualtarget= (TextView) findViewById(R.id.act_target);
        daysleft= (TextView) findViewById(R.id.daysleft);
        act_stdate= (TextView) findViewById(R.id.actualstartdate);
        act_enddate= (TextView) findViewById(R.id.actualenddate);
        layout= (LinearLayout) findViewById(R.id.task_details_layout);
        if(AppPreferences.getActualTarget(ProjectMonitorActivity.this)<AppPreferences.getCurrentTarget(ProjectMonitorActivity.this)){
            //layout.setBackgroundColor(getResources().getColor(R.color.red));
            currenttarget.setTextColor(getResources().getColor(R.color.red));
        }
        startdate.setText(AppPreferences.getStartDate(ProjectMonitorActivity.this));
        enddate.setText(AppPreferences.getEndDate(ProjectMonitorActivity.this));
        totaltarget.setText(String.valueOf(AppPreferences.getTotalTarget(ProjectMonitorActivity.this))+" "+AppPreferences.getUnitMeasurementName(ProjectMonitorActivity.this));
        actualtarget.setText(String.valueOf(AppPreferences.getActualTarget(ProjectMonitorActivity.this))+" "+AppPreferences.getUnitMeasurementName(ProjectMonitorActivity.this));
        currenttarget.setText(String.valueOf(AppPreferences.getCurrentTarget(ProjectMonitorActivity.this))+" "+AppPreferences.getUnitMeasurementName(ProjectMonitorActivity.this));
        act_stdate.setText(AppUtilities.getDateWithTimeString(AppPreferences.getActualStartDate(ProjectMonitorActivity.this)));
        act_enddate.setText(AppUtilities.getDateWithTimeString(AppPreferences.getActualEndDate(ProjectMonitorActivity.this)));
        if(day.equalsIgnoreCase(finaldate)){
            daysleft.setText(String.valueOf(AppPreferences.getDaysLeft(ProjectMonitorActivity.this))+" Days");
        }else{
            daysleft.setText("Overdue");
            daysleft.setTextColor(getResources().getColor(R.color.red));
        }
        blueprint= (ImageView) findViewById(R.id.blueprint);
        mLayout= (RelativeLayout) findViewById(R.id.layout);
        mFloorTv= (TextView) findViewById(R.id.floor_name_tv);
        if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("static")){
            //blueprint.setVisibility(View.VISIBLE);
            mLayout.setVisibility(View.VISIBLE);
        }else if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("dynamic")){
            mMapView.setVisibility(View.VISIBLE);
        }
        image_marker= (ImageView) findViewById(R.id.image_marker);
        image_popup= (FloatingActionButton) findViewById(R.id.bt_captureImage);
        submit = (TextView) findViewById(R.id.bt_submit);
        boq= (TextView) findViewById(R.id.bt_boq);
        distance_height=findViewById(R.id.bt_distance_height);
        resource_dts = (FloatingActionButton) findViewById(R.id.bt_resourcedetails);
        previouslocation = (FloatingActionButton) findViewById(R.id.bt_prevoiuslocation);
        survey_map = (Button) findViewById(R.id.surveymap);
        project_mtr = (Button) findViewById(R.id.project_monitor);
        tv_projectmonitor= (TextView) findViewById(R.id.tv_projectmonitor);
        stats_update = (Button) findViewById(R.id.statusupdate);
        /*TODO unmask for map */
        if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41||AppPreferences.getGroupId(ProjectMonitorActivity.this)==39){
            project_mtr.setBackgroundResource(R.drawable.poym_tick_purple);
            tv_projectmonitor.setText("Approval");
            if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("dynamic")){
                //image_marker.setBackgroundResource(R.drawable.approved);
            }
        }
        else if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==14||AppPreferences.getGroupId(ProjectMonitorActivity.this)==17||AppPreferences.getGroupId(ProjectMonitorActivity.this)==18){
            project_mtr.setBackgroundResource(R.drawable.poym_execution_purple);
            if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("dynamic")){
                image_marker.setBackgroundResource(R.drawable.execution);
            }
        }
        tv_projectmonitor.setTextColor(getResources().getColor(R.color.bottom_purple));

        //todo unmask for offline download check
        /*if(!isNetworkAvailable() && !AppPreferences.getofflineSuccess(ProjectMonitorActivity.this)){
            Log.d("internetcheck","if");
            new AlertDialog.Builder(ProjectMonitorActivity.this)
                    .setTitle("Connect to Internet to Download Map and Press Ok")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            try {
                                DownloadRegion();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    })
                    .create()
                    .show();
        }*/

        progress = new ProgressDialog(ProjectMonitorActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Fetching Data,Please Wait..");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        /*progress.show();
        userLocation();*/

        image_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41||AppPreferences.getGroupId(ProjectMonitorActivity.this)==39){
                    if(QAImage==null){
                        Toast.makeText(ProjectMonitorActivity.this, "Please,Select Marker", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent1=new Intent(ProjectMonitorActivity.this,DisplayQAImageActivity.class);
                        intent1.putExtra("qaimagename",QAImage);
                        intent1.putExtra("qaimagename2",QAImage2);
                        intent1.putExtra("qatime",imageQatime);
                        startActivity(intent1);
                    }
                }else {
                    /*imagename="Ptm"+String.valueOf(AppUtilities.getDateandTimeString())+".jpg";
                    Intent in =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File imagefolder=new File(Environment.getExternalStorageDirectory(),"Project Monitoring");
                    imagefolder.mkdirs();
                    file=new File(imagefolder,imagename);
                    imagepath=file.getPath();
                    Uri outputfile = Uri.fromFile(file);
                    in.putExtra(MediaStore.EXTRA_OUTPUT,outputfile);
                    startActivityForResult(in,1000);*/

                    if (ContextCompat.checkSelfPermission(ProjectMonitorActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProjectMonitorActivity.this,
                                new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        picFile = new File(imagePath);
                        picUri = Uri.fromFile(picFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
        distance_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41||AppPreferences.getGroupId(ProjectMonitorActivity.this)==39){
                    //set values for qa
                    AppPreferences.setImageDistance(ProjectMonitorActivity.this,QADistance);
                    AppPreferences.setImageHeight(ProjectMonitorActivity.this,QAHeight);
                    AlertDialog.Builder builder=new AlertDialog.Builder(ProjectMonitorActivity.this);
                    View v1=LayoutInflater.from(ProjectMonitorActivity.this).inflate(R.layout.distance_height_popup,null);
                    builder.setView(v1);
                    builder.setCancelable(false);
                    final TextView tv_height_distance=v1.findViewById(R.id.tv_height_distance);
                    Button bt_ok=v1.findViewById(R.id.bt_comment_ok);
                    Button bt_cancel=v1.findViewById(R.id.bt_comment_cancel);
                    final AlertDialog dialog=builder.create();
                    if(AppPreferences.getImageDistance(ProjectMonitorActivity.this)!=null && AppPreferences.getImageHeight(ProjectMonitorActivity.this)!=null){
                        tv_height_distance.setText("Distance : "+AppPreferences.getImageDistance(ProjectMonitorActivity.this)+" , "+
                        "Height :"+AppPreferences.getImageHeight(ProjectMonitorActivity.this));
                    } else {
                        tv_height_distance.setText("No Data Found");
                    }
                    bt_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    bt_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else{
                    if(AppPreferences.getPrefCaptureImage(ProjectMonitorActivity.this)!=null){
                        distance_and_height=true;
                        startActivity(new Intent(ProjectMonitorActivity.this, Distance_Camera.class));
                    }else{
                        Toast.makeText(ProjectMonitorActivity.this, "Please,Capture Image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41||AppPreferences.getGroupId(ProjectMonitorActivity.this)==39){
                    ///for comment
                    if(qaorder==true||AppPreferences.getBoqQAFlag(ProjectMonitorActivity.this)==true){
                        AlertDialog.Builder builder=new AlertDialog.Builder(ProjectMonitorActivity.this);
                        View v1=LayoutInflater.from(ProjectMonitorActivity.this).inflate(R.layout.comment_popup,null);
                        builder.setView(v1);
                        builder.setCancelable(false);
                        final EditText et_comment=v1.findViewById(R.id.et_comment);
                        Button bt_ok=v1.findViewById(R.id.bt_comment_ok);
                        Button bt_cancel=v1.findViewById(R.id.bt_comment_cancel);
                        final AlertDialog dialog=builder.create();
                        if(qa_comment!=null){
                            et_comment.setText(qa_comment);
                        }
                        bt_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41){
                                    comment=et_comment.getText().toString().trim();
                                    //putPromotions();

                                    try {
                                        RuntimeExceptionDao<Promotions, Integer> promotionsDao = mDbHelper.getPromotionsRuntimeDao();
                                        UpdateBuilder<Promotions, Integer> updateBuilder = promotionsDao.updateBuilder();
                                        updateBuilder.where().eq("id", promotonid);
                                        updateBuilder.updateColumnValue("updateflag",true);
                                        updateBuilder.updateColumnValue("qatime",AppPreferences.getImageApprovedFlag(ProjectMonitorActivity.this));
                                        updateBuilder.updateColumnValue("qaimage", AppPreferences.getQACaptureImage(ProjectMonitorActivity.this));
                                        updateBuilder.updateColumnValue("comment",comment);
                                        updateBuilder.updateColumnValue("qaflag",true);
                                        updateBuilder.update();

                                        finish();
                                        Intent pro=new Intent(ProjectMonitorActivity.this,ProjectMonitorActivity.class);
                                        pro.putExtra("Latitude",lati);
                                        pro.putExtra("Longitude",longi);
                                        pro.putExtra("partnerid",partnerid);
                                        pro.putExtra("routeid",routeid);
                                        pro.putExtra("date",day);
                                        startActivity(pro);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                    dialog.dismiss();
                                }else{
                                    dialog.dismiss();
                                }
                                //Toast.makeText(ProjectMonitorActivity.this, ""+comment, Toast.LENGTH_SHORT).show();
                            }
                        });
                        bt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }else{
                        Toast.makeText(ProjectMonitorActivity.this, "Please,Approve BOQ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("subtaskid",""+AppPreferences.getSubTypeId(ProjectMonitorActivity.this));
                    if(AppPreferences.getPrefCaptureImage(ProjectMonitorActivity.this)==null){
                        Toast.makeText(ProjectMonitorActivity.this, "Capture Image/Select Marker", Toast.LENGTH_SHORT).show();
                    } /*else if(!distance_and_height || AppPreferences.getImageDistance(ProjectMonitorActivity.this)==null
                            && AppPreferences.getImageHeight(ProjectMonitorActivity.this)==null){
                        Toast.makeText(ProjectMonitorActivity.this, "Capture Distance and Height", Toast.LENGTH_SHORT).show();
                    }*/ else if(!resource|| !AppPreferences.getResourceFlag(ProjectMonitorActivity.this)){
                        Toast.makeText(ProjectMonitorActivity.this, "Enter Resources", Toast.LENGTH_SHORT).show();
                    } else if(!order || !AppPreferences.getBoqFlag(ProjectMonitorActivity.this)){
                        Toast.makeText(ProjectMonitorActivity.this, "Enter BOQ", Toast.LENGTH_SHORT).show();
                    } else if(mpromotions.size()>0
                            && !previous){
                        Toast.makeText(ProjectMonitorActivity.this, "Select previous location", Toast.LENGTH_SHORT).show();
                    } else {
                        promotions =new Promotions();
                        long systemid=System.currentTimeMillis();
                        promotions.setId(systemid);
                        promotions.setRetailername(String.valueOf(AppPreferences.getZoneId(ProjectMonitorActivity.this)));
                        promotions.setRetailerid(AppPreferences.getPrefAreaId(ProjectMonitorActivity.this));
                        promotions.setRetaileraddress(cal.substring(4, 10));
                        promotions.setRetailerimage(AppPreferences.getPrefCaptureImage(ProjectMonitorActivity.this));
                        promotions.setDatetime(AppUtilities.getDateTime());
                        promotions.setImgreslinkid(ponumber_value);
                        if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("dynamic")){
                            promotions.setLatitude(String.valueOf(imagelati));
                            promotions.setLongitude(String.valueOf(imagelongi));
                        }
                        promotions.setApprovedflag(null);
                        promotions.setRejectedflag(null);
                        promotions.setDistsubareaid(AppPreferences.getSubTypeId(ProjectMonitorActivity.this));
                        //promotions.setAreaid(AppPreferences.getSubTypeId(ProjectMonitorActivity.this));
                        promotions.setAddress(address);
                        promotions.setEmployeeid(partnerid);
                        promotions.setRouteid(AppPreferences.getPrefRouteId(ProjectMonitorActivity.this));
                        promotions.setTourtypeid(AppPreferences.getTourTypeId(ProjectMonitorActivity.this));
                        promotions.setAreaid(AppPreferences.getPrefAreaId(ProjectMonitorActivity.this));
                        promotions.setDistareaid(AppPreferences.getDist_Area_Id(ProjectMonitorActivity.this));
                        promotions.setZoneid(AppPreferences.getZoneId(ProjectMonitorActivity.this));
                        promotions.setSalesmgrid(AppPreferences.getSaleMngrIdId(ProjectMonitorActivity.this));
                        promotions.setRouteassignmentsummaryid(AppPreferences.getRouteAssignmentSummaryId(ProjectMonitorActivity.this));
                        promotions.setRouteassignmentid(AppPreferences.getRouteAssignmentId(ProjectMonitorActivity.this));
                        if(AppPreferences.getImageHeight(ProjectMonitorActivity.this)==null){
                            promotions.setHeight(Float.valueOf("0"));
                            promotions.setDistance(Float.valueOf("0"));
                        }else{
                            promotions.setHeight(AppPreferences.getImageHeight(ProjectMonitorActivity.this)==null?0:Float.valueOf(AppPreferences.getImageHeight(ProjectMonitorActivity.this)));
                            promotions.setDistance(AppPreferences.getImageDistance(ProjectMonitorActivity.this)==null?0:Float.valueOf(AppPreferences.getImageDistance(ProjectMonitorActivity.this)));
                        }
                        if(mpromotions.size()>0){
                            promotions.setPreviouslocation(AppPreferences.getPreviousLocationId(ProjectMonitorActivity.this));
                            promotions.setPrevious_flag(AppPreferences.getPreviousLocationFlag(ProjectMonitorActivity.this));
                        }else{
                            promotions.setPreviouslocation(Long.valueOf(0));
                            promotions.setPrevious_flag(false);
                        }
                        promotions.setInsertFlag(true);
                        try {
                            RuntimeExceptionDao<PoNumbers, Integer> poNumbersDao = mDbHelper.getPoNumbersRuntimeDao();
                            UpdateBuilder<PoNumbers, Integer> updateBuilder = poNumbersDao.updateBuilder();
                            updateBuilder.updateColumnValue("nextvalue",ponumber_value+1);
                            updateBuilder.updateColumnValue("updateflag",true);
                            updateBuilder.updateColumnValue("id",1);
                            updateBuilder.update();

                            RuntimeExceptionDao<Promotions, Integer> promotionsDao = mDbHelper.getPromotionsRuntimeDao();
                            promotionsDao.create(promotions);

                            imagePath = createFile();
                            AppPreferences.setPrefCaptureImage(ProjectMonitorActivity.this,null);
                            distance_and_height=false;
                            resource=false;
                            order=false;
                            previous=false;

                            progress.show();
                            userLocation();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        resource_dts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Resource_pupup resource_pupup=new Resource_pupup();
                //resource_pupup.show(getSupportFragmentManager(),null);
                if(AppPreferences.getPrefCaptureImage(ProjectMonitorActivity.this)!=null){
                    distance_and_height=true;
                    resource=true;
                    Resource_pupup resource_pupup=new Resource_pupup();
                    resource_pupup.show(getSupportFragmentManager(),null);
                }else{
                    Toast.makeText(ProjectMonitorActivity.this, "Please,Capture Image", Toast.LENGTH_SHORT).show();
                }
                /*if(distance_and_height==true ||
                        AppPreferences.getImageDistance(ProjectMonitorActivity.this)!=null
                                && AppPreferences.getImageHeight(ProjectMonitorActivity.this)!=null){
                    resource=true;
                    Resource_pupup resource_pupup=new Resource_pupup();
                    resource_pupup.show(getSupportFragmentManager(),null);
                }else{
                    if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41||AppPreferences.getGroupId(ProjectMonitorActivity.this)==39){
                        Toast.makeText(ProjectMonitorActivity.this, "Please,Approve Distance & Height", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ProjectMonitorActivity.this, "Please,Capture Distance & Height", Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });
        boq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Boq_pupup boq_pupup=new Boq_pupup();
                //boq_pupup.show(getSupportFragmentManager(),null);
                if(resource==true||AppPreferences.getResourceFlag(ProjectMonitorActivity.this)==true){
                    order=true;
                    Boq_pupup boq_pupup=new Boq_pupup();
                    boq_pupup.show(getSupportFragmentManager(),null);
                }else{
                    if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41||AppPreferences.getGroupId(ProjectMonitorActivity.this)==39){
                        Toast.makeText(ProjectMonitorActivity.this, "Please,Approve resource", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ProjectMonitorActivity.this, "Please,Enter resource", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        previouslocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpromotions.size()>0){
                    if(order==true||AppPreferences.getBoqFlag(ProjectMonitorActivity.this)==true){
                        previous=true;
                        PreviousLocation_popup resource_pupup=new PreviousLocation_popup();
                        resource_pupup.show(getSupportFragmentManager(),null);
                    }else{
                        Toast.makeText(ProjectMonitorActivity.this, "Please Enter BOQ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ProjectMonitorActivity.this, "click submit button for first point", Toast.LENGTH_SHORT).show();
                }
            }
        });

        survey_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ProjectMonitorActivity.this, MapScreen.class);
                intent2.putExtra("partnerid",partnerid);
                intent2.putExtra("date",day);
                startActivity(intent2);
            }
        });
        stats_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ProjectMonitorActivity.this, StatusUpdate.class);
                intent3.putExtra("partnerid",partnerid);
                intent3.putExtra("Latitude",lati);
                intent3.putExtra("Longitude",longi);
                intent3.putExtra("date",day);
                startActivity(intent3);
            }
        });
        //mShape = (String) ((Floor) getIntent().getSerializableExtra("shape")).getShapes();
        //mShape = "shapes"+","+mFloorList.get(0);
        /*mShape = "shape"+mFloorList.get(0);
        try {
            test = new JSONArray(mShape);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        mMapView.getMapAsync(ProjectMonitorActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.execution, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_document){
            //showDocument();
            ExecutionDocument_Popup document_popup=new ExecutionDocument_Popup();
            document_popup.show(getSupportFragmentManager(),null);
        }
        if(id==R.id.action_detail){
            //ExecutionImagedetail();
            ExecutionImage_Popup image_popup=new ExecutionImage_Popup();
            image_popup.show(getSupportFragmentManager(),null);
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent in1 = new Intent(ProjectMonitorActivity.this, LandingActivity.class);
            in1.putExtra("partnerid",partnerid);
            in1.putExtra("usertype",AppPreferences.getUserType(ProjectMonitorActivity.this));
            in1.putExtra("projecttype",AppPreferences.getProjectType(ProjectMonitorActivity.this));
            in1.putExtra("user_email",AppPreferences.getUserEmail(ProjectMonitorActivity.this));
            //in1.putExtra("usersalesAreaid",AppPreferences.getUser_SalesAreaId(ProjectMonitorActivity.this));
            startActivity(in1);
            //return true;
        }
            return super.onOptionsItemSelected(item);
    }

    /*unmask for map for gmap */
    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraIdleListener(this);
    }
*/

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mMapbox=mapboxMap;
        mapboxMap.addOnCameraIdleListener(this);
        mapboxMap.setOnMarkerClickListener(this);

        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        Log.d("off_check","offline check map");
                        //todo unmask for offline
                        progress.show();
                        userLocation();

                        /*offlineManager = OfflineManager.getInstance(ProjectMonitorActivity.this);
                        *//*progres.show();
                        new FetchDetailsFromDbTask().execute();*//*

                        //if(!AppPreferences.getofflineSuccess(ProjectMonitorActivity.this)){ }
                        if(!AppPreferences.getofflineSuccess(ProjectMonitorActivity.this)){
                            DownloadRegion();
                        }*/

                    }
                });
    }

    private void userLocation(){
        manager = (LocationManager) ProjectMonitorActivity.this.getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                source = new LatLng(lati, longi);
                new FetchDetailsFromDbTask().execute();
                if (ActivityCompat.checkSelfPermission(ProjectMonitorActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProjectMonitorActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    Toast.makeText(ProjectMonitorActivity.this, "Network Unavaliable", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(ProjectMonitorActivity.this, "Fetching Location,Please Wait", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(ProjectMonitorActivity.this, "Please enable GPS", Toast.LENGTH_SHORT).show();
            }
        };
        if (ActivityCompat.checkSelfPermission(ProjectMonitorActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProjectMonitorActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 100, listener);
    }

    private void networkSurveys() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call =null;
        if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==14||AppPreferences.getGroupId(ProjectMonitorActivity.this)==17||AppPreferences.getGroupId(ProjectMonitorActivity.this)==18){
            call = service.getSurveys(AppPreferences.getUserId(ProjectMonitorActivity.this),
                    AppPreferences.getZoneId(ProjectMonitorActivity.this),
                    AppPreferences.getPrefAreaId(ProjectMonitorActivity.this),AppPreferences.getDist_Area_Id(ProjectMonitorActivity.this)
                    ,"slno ASC");
        } else if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41){
            call = service.getQASurveys(AppPreferences.getUserId(ProjectMonitorActivity.this),
                    AppPreferences.getRouteSalesViewid(ProjectMonitorActivity.this),"slno ASC");
        }
        if(call!=null){
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<Surveys> survey = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<Surveys>>() {
                            }.getType());
                    msurvey = survey;
                    networkPromotions();
                    Log.e("surveydata",""+msurvey+","+msurvey.size());
                    points1=new ArrayList<LatLng>();
                    if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("dynamic")){
                        for(int i=0;i<msurvey.size();i++){
                            if(msurvey.get(i).getPendingflag()==true){
                                double sur_lat =msurvey.get(i).getLatitude();
                                double sur_long =msurvey.get(i).getLongitude();
                                LatLng sur_map = new LatLng(sur_lat, sur_long);
                                double sur_lat_to = msurvey.get(i).getToLatitude();
                                double sur_long_to = msurvey.get(i).getToLongitude();
                                LatLng sur_map_to = new LatLng(sur_lat_to, sur_long_to);
                                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);
                                //mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                                //mMap.addMarker(new MarkerOptions().position(sur_map_to).icon(icon));
                                //add polyline
                                //mMap.addPolyline(new PolylineOptions().add(new LatLng(sur_lat,sur_long), new LatLng(sur_lat_to, sur_long_to)).width(10).color(Color.RED));
                            }
                            else {
                                double sur_lat = msurvey.get(i).getLatitude();
                                double sur_long = msurvey.get(i).getLongitude();
                                LatLng sur_map = new LatLng(sur_lat, sur_long);
                                points1.add(sur_map);
                                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);
                                //mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                                //mMap.addPolyline(new PolylineOptions().addAll(points1).width(10).color(Color.GRAY));
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    //Toast.makeText(ProjectMonitorActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, R.string.check_groupid, Toast.LENGTH_SHORT).show();
        }
    }

    private void networkPromotions() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1=null;
        if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==14||AppPreferences.getGroupId(ProjectMonitorActivity.this)==17||AppPreferences.getGroupId(ProjectMonitorActivity.this)==18){
            call1 = service1.getPromotions(AppPreferences.getUserId(ProjectMonitorActivity.this)
                    ,AppPreferences.getZoneId(ProjectMonitorActivity.this),
                    AppPreferences.getPrefAreaId(ProjectMonitorActivity.this),
                    AppPreferences.getDist_Area_Id(ProjectMonitorActivity.this));
        }else if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41){
            call1 = service1.getQAPromotions(AppPreferences.getUserId(ProjectMonitorActivity.this)
                    ,AppPreferences.getRouteSalesViewid(ProjectMonitorActivity.this));
        }
        if(call1!=null){
            call1.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<Promotions> promotionses = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<Promotions>>() {
                            }.getType());
                    mpromotions=promotionses;
                    networkPoNumbers();

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
                    if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("static")){
                        getFloors();
                    }
                    if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("dynamic")){
                        for(int j=0;j<mpromotions.size();j++){
                            if(mpromotions.get(j).getLatitude()!=null&&mpromotions.get(j).getLongitude()!=null){
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
                                    //mMap.addMarker(new MarkerOptions().position(prolatlong).icon(BitmapDescriptorFactory.fromBitmap(bmp)).anchor(0.5f, 1));
                                    if((AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41) && qatime!=null){
                                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.green_marker);
                                        //mMap.addMarker(new MarkerOptions().position(prolatlong).icon(icon));
                                    }else{
                                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.yellow_marker1);
                                        //mMap.addMarker(new MarkerOptions().position(prolatlong).icon(icon));
                                            /*PolylineOptions polyOptions = new PolylineOptions();
                                            polyOptions.color(ContextCompat.getColor(ProjectMonitorActivity.this, R.color.poymyellow));
                                            polyOptions.addAll(points);
                                            polyOptions.pattern(PATTERN_POLYGON_ALPHA);
                                            mMap.addPolyline(polyOptions);*/
                                    }
                                    if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==14
                                            ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==17
                                            ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==18){
                                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.blue_marker);
                                        //mMap.addMarker(new MarkerOptions().position(prolatlong).icon(icon));
                                            /*PolylineOptions polyOptions = new PolylineOptions();
                                            polyOptions.color(ContextCompat.getColor(ProjectMonitorActivity.this, R.color.blue));
                                            polyOptions.addAll(points);
                                            polyOptions.pattern(PATTERN_POLYGON_ALPHA);
                                            mMap.addPolyline(polyOptions);*/
                                    }
                                }
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                }
            });
        }else{

            Toast.makeText(this, R.string.check_groupid, Toast.LENGTH_SHORT).show();
        }
    }

    private void networkPoNumbers() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getPonumbers(AppPreferences.getUserId(ProjectMonitorActivity.this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<PoNumbers> poNumberses = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<PoNumbers>>() {
                        }.getType());
                progress.dismiss();
                mpoNumberses=poNumberses;
                for(int i=0;i<mpoNumberses.size();i++){
                    ponumber_value=mpoNumberses.get(i).getNextvalue();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                //Toast.makeText(ProjectMonitorActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFloors() {
        final ProgressDialog progress1 = new ProgressDialog(ProjectMonitorActivity.this);
        progress1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress1.setMessage("Fetching Data,Please Wait..");
        progress1.setIndeterminate(true);
        progress1.setCancelable(false);
        progress1.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<Floor> mFloorCall = service1.getDistributionAreaShapes(AppPreferences.getDist_Area_Id(ProjectMonitorActivity.this),AppPreferences.getUserId(ProjectMonitorActivity.this));
        mFloorCall.enqueue(new Callback<Floor>() {
            @Override
            public void onResponse(Call<Floor> call, Response<Floor> response) {
                Log.e("getFloors","getFloors");
                if (response.isSuccessful()) {
                    progress1.dismiss();
                    mFloorList.clear();
                    Log.d("floor_data",""+response.body());
                    Log.d("floor_data1",""+response.body().getShapes());
                    //mFloorList.addAll(response.body().getData());
                    mShape = response.body().getShapes().toString();
                    Log.d("shape",""+mShape);
                    try {
                        test = new JSONArray(mShape);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("testvalues",""+test.toString());
                    if(test!=null){
                        ArrayList<Integer> mShapeIdList = new ArrayList<>();
                        customCanvas=new CustomCanvas(ProjectMonitorActivity.this, test.toString(), ProjectMonitorActivity.this, mShapeIdList);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        params.addRule(RelativeLayout.BELOW, mFloorTv.getId());
                        customCanvas.setLayoutParams(params);
                        mLayout.addView(customCanvas);
                    }
                }
            }
            @Override
            public void onFailure(Call<Floor> call, Throwable t) {
                Log.e("Floor Error", t.getMessage());
                progress1.dismiss();
                //Toast.makeText(ProjectMonitorActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCameraIdle() {
        com.mapbox.mapboxsdk.geometry.LatLng latLng = mMapbox.getCameraPosition().target;
        if(source!=null){
            imagelati=latLng.getLatitude();
            imagelongi=latLng.getLongitude();

            AppPreferences.setImageLatitude(ProjectMonitorActivity.this,String.valueOf(imagelati));
            AppPreferences.setImageLongitude(ProjectMonitorActivity.this,String.valueOf(imagelongi));
            try{
                getAddress(imagelati,imagelongi);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(requestCode==1000){
            if(resultCode==RESULT_OK){
                String imagePath = file.getPath();
                Intent in=new Intent(ProjectMonitorActivity.this,CameraActivity.class);
                in.putExtra("imagepath",imagePath);
                in.putExtra("imagename",imagename);
                startActivity(in);
            }
        }*/
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            mObjDialog.show();
            new ImageCompression().execute(imagePath);
        }
    }
    private void getAddress( double latitude, double longitude) {
        try {
            Geocoder geocoder = new Geocoder(ProjectMonitorActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
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

    //todo
    @Override
    public boolean onMarkerClick(@NonNull com.mapbox.mapboxsdk.annotations.Marker marker) {
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
                Toast.makeText(this, "Marker Selected", Toast.LENGTH_SHORT).show();
                if(imageQatime!=null){
                    Log.d("calling","done");
                    AppPreferences.setPrefCaptureImage(ProjectMonitorActivity.this,QAImage);
                    distance_and_height=true;
                    resource=true;
                    qaorder=true;
                    AppPreferences.setResourceFlag(ProjectMonitorActivity.this,true);
                    AppPreferences.setBoqQAFlag(ProjectMonitorActivity.this,true);
                }
                break;
            }
        }
        return true;
    }

    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(1f, Math.min(mScaleFactor, 5.0f));
            customCanvas.setmScaleFactor(mScaleFactor);
            customCanvas.invalidate();
            return true;
        }
    }
    @Override
    public void polygonPaths(ArrayList<Path> path, HashMap<Path, Integer> mPathsMap) {
        this.mPathsList.clear();
        this.mPathsMap.clear();
        this.mPathsList.addAll(path);
        this.mPathsMap.putAll(mPathsMap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==33){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                // main logic
            } else {
                image_popup.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Camera/Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                msurvey=new ArrayList<Surveys>();
                mfilteredsurvey=new ArrayList<>();
                mpromotions=new ArrayList<>();
                mfilteredpromotions=new ArrayList<>();
                RuntimeExceptionDao<Surveys, Integer> surveyDao = mDbHelper.getSurveysRuntimeDao();
                Where<Surveys, Integer> wheresurveys = surveyDao.queryBuilder().where();
                if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41
                        ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==39){
                    wheresurveys.eq("routeassignmentid",AppPreferences.getRouteSalesViewid(ProjectMonitorActivity.this));
                    //wheresurveys.and(wheresurveys.eq("routeassignmentid",AppPreferences.getRouteSalesViewid(ProjectMonitorActivity.this))
                    //                            wheresurveys.eq("order","slno ASC"));
                }else{
                    //wheresurveys.eq("zoneid",AppPreferences.getZoneId(ProjectMonitorActivity.this)), added filter
                    wheresurveys.and(wheresurveys.eq("areaid",AppPreferences.getPrefAreaId(ProjectMonitorActivity.this)),
                            wheresurveys.eq("distareaid",AppPreferences.getDist_Area_Id(ProjectMonitorActivity.this)));
                    //wheresurveys.eq("order","slno ASC")
                }
                mfilteredsurvey = wheresurveys.query();

                RuntimeExceptionDao<Promotions, Integer> promotionsDao = mDbHelper.getPromotionsRuntimeDao();
                Where<Promotions, Integer> wherepromotions = promotionsDao.queryBuilder().where();
                if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41
                        ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==39){
                    wherepromotions.eq("routeassignmentid",AppPreferences.getRouteSalesViewid(ProjectMonitorActivity.this));
                }else{
                    //wherepromotions.eq("zoneid",AppPreferences.getZoneId(ProjectMonitorActivity.this)), added filter
                    wherepromotions.and(wherepromotions.eq("areaid",AppPreferences.getPrefAreaId(ProjectMonitorActivity.this)),
                            wherepromotions.eq("distareaid",AppPreferences.getDist_Area_Id(ProjectMonitorActivity.this)));
                }
                mfilteredpromotions = wherepromotions.query();

                RuntimeExceptionDao<PoNumbers, Integer> poNumbersDoa = mDbHelper.getPoNumbersRuntimeDao();
                mpoNumberses=poNumbersDoa.queryForAll();

                if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("static")){
                    //getFloors();
                    Toast.makeText(ProjectMonitorActivity.this, "get floors data", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //userLocation();
            progress.dismiss();
            Log.d("off_check","offline check db");
            points1=new ArrayList<LatLng>();

            if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==14
                    ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==17
                    ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==18){
                for(Surveys survey:mfilteredsurvey){
                    if(AppPreferences.getDistributionSubAreaId(ProjectMonitorActivity.this).equalsIgnoreCase("null")){
                        msurvey.add(survey);
                    } else {
                        if(Integer.valueOf(AppPreferences.getDistributionSubAreaId(ProjectMonitorActivity.this)).equals(survey.getDistsubareaid())){
                            msurvey.add(survey);
                        }
                    }
                }
            }else{
                msurvey.addAll(mfilteredsurvey);
            }

            if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==14
                    ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==17
                    ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==18){
                for(Promotions promotions:mfilteredpromotions){
                    if(AppPreferences.getDistributionSubAreaId(ProjectMonitorActivity.this).equalsIgnoreCase("null")){
                        mpromotions.add(promotions);
                    } else {
                        if(Integer.valueOf(AppPreferences.getDistributionSubAreaId(ProjectMonitorActivity.this)).equals(promotions.getDistsubareaid())){
                            mpromotions.add(promotions);
                        }
                    }
                }
            }else{
                mpromotions.addAll(mfilteredpromotions);
            }

            if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("dynamic")){
                if(source!=null){
                    if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==14
                            ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==17
                            ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==18){
                        if(msurvey.size()>0){
                            double lat = msurvey.get(0).getLatitude();
                            double lon = msurvey.get(0).getLongitude();
                            CameraPosition pos = new CameraPosition.Builder()
                                    .target(new com.mapbox.mapboxsdk.geometry.LatLng(lat, lon))
                                    .zoom(14)
                                    .tilt(20)
                                    .build();
                            try {
                                mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(pos), 1000);
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
                                mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }else if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23
                            ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41
                            || AppPreferences.getGroupId(ProjectMonitorActivity.this)==39){
                        if(mpromotions.size()>0){
                            double lat = Double.parseDouble(mpromotions.get(0).getLatitude());
                            double lon = Double.parseDouble(mpromotions.get(0).getLongitude());
                            CameraPosition pos = new CameraPosition.Builder()
                                    .target(new com.mapbox.mapboxsdk.geometry.LatLng(lat, lon))
                                    .zoom(14)
                                    .tilt(20)
                                    .build();
                            try {
                                mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(pos), 1000);
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
                                mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);
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
                            mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            /*for(Promotions promotions:mfilteredpromotions){
                if(AppPreferences.getDistributionSubAreaId(ProjectMonitorActivity.this).equalsIgnoreCase("null")){
                    mpromotions.add(promotions);
                } else {
                    if(Integer.valueOf(AppPreferences.getDistributionSubAreaId(ProjectMonitorActivity.this)).equals(promotions.getDistsubareaid())){
                        mpromotions.add(promotions);
                    }
                }
            }*/

            if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("dynamic")){
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
                                    IconFactory iconFactory=IconFactory.getInstance(ProjectMonitorActivity.this);
                                    Icon icon1=iconFactory.fromResource(R.drawable.marker_black);
                                    mMapbox.addMarker(new MarkerOptions().position(latLng).setIcon(icon1));

                                    com.mapbox.mapboxsdk.geometry.LatLng latLng1=new com.mapbox.mapboxsdk.geometry.LatLng(sur_map_to.latitude,sur_map_to.longitude);
                                    IconFactory iconFactory1=IconFactory.getInstance(ProjectMonitorActivity.this);
                                    Icon icon2=iconFactory1.fromResource(R.drawable.marker_black);
                                    mMapbox.addMarker(new MarkerOptions().position(latLng1).setIcon(icon2));
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
                                LatLng sur_map = new LatLng(sur_lat, sur_long);
                                points1.add(sur_map);
                                //todo add markers and polyline
                                try {
                                    com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(sur_map.latitude,sur_map.longitude);
                                    IconFactory iconFactory=IconFactory.getInstance(ProjectMonitorActivity.this);
                                    Icon icon1=iconFactory.fromResource(R.drawable.marker_black);
                                    mMapbox.addMarker(new MarkerOptions().position(latLng).setIcon(icon1));
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
            if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("static")){
                /*if (response.isSuccessful()) {
                    progress1.dismiss();
                    mFloorList.clear();
                    Log.d("floor_data",""+response.body());
                    Log.d("floor_data1",""+response.body().getShapes());
                    //mFloorList.addAll(response.body().getData());
                    mShape = response.body().getShapes().toString();
                    Log.d("shape",""+mShape);
                    try {
                        test = new JSONArray(mShape);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("testvalues",""+test.toString());
                    if(test!=null){
                        ArrayList<Integer> mShapeIdList = new ArrayList<>();
                        customCanvas=new CustomCanvas(ProjectMonitorActivity.this, test.toString(), ProjectMonitorActivity.this, mShapeIdList);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        params.addRule(RelativeLayout.BELOW, mFloorTv.getId());
                        customCanvas.setLayoutParams(params);
                        mLayout.addView(customCanvas);
                    }
                }*/
            }
            //todo
            Log.e("promotions",""+mpromotions.size());
            if(AppPreferences.getProjectType(ProjectMonitorActivity.this).equalsIgnoreCase("dynamic")){
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
                            if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41){
                                if((AppPreferences.getGroupId(ProjectMonitorActivity.this)==23||AppPreferences.getGroupId(ProjectMonitorActivity.this)==41) && qatime!=null){
                                    //todo add markers
                                    try {
                                        com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(prolatlong.latitude,prolatlong.longitude);
                                        IconFactory iconFactory=IconFactory.getInstance(ProjectMonitorActivity.this);
                                        Icon icon1=iconFactory.fromResource(R.drawable.green_marker);
                                        mMapbox.addMarker(new MarkerOptions().title(String.valueOf(latLng)).position(latLng).setIcon(icon1));
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
                                        IconFactory iconFactory=IconFactory.getInstance(ProjectMonitorActivity.this);
                                        Icon icon1=iconFactory.fromResource(R.drawable.yellow_marker1);
                                        mMapbox.addMarker(new MarkerOptions().title(String.valueOf(latLng)).position(latLng).setIcon(icon1));
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.yellow_marker1);
                                    //mMap.addMarker(new MarkerOptions().position(prolatlong).icon(icon));

                                            /*PolylineOptions polyOptions = new PolylineOptions();
                                            polyOptions.color(ContextCompat.getColor(ProjectMonitorActivity.this, R.color.poymyellow));
                                            polyOptions.addAll(points);
                                            polyOptions.pattern(PATTERN_POLYGON_ALPHA);
                                            mMap.addPolyline(polyOptions);*/
                                }
                            }
                            if(AppPreferences.getGroupId(ProjectMonitorActivity.this)==14
                                    ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==17
                                    ||AppPreferences.getGroupId(ProjectMonitorActivity.this)==18){
                                //todo add markers
                                try {
                                    com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(prolatlong.latitude,prolatlong.longitude);
                                    IconFactory iconFactory=IconFactory.getInstance(ProjectMonitorActivity.this);
                                    if(mpromotions.get(j).getRouteassignmentsummaryid()==AppPreferences.getRouteAssignmentSummaryId(ProjectMonitorActivity.this)){
                                        Icon icon1=iconFactory.fromResource(R.drawable.blue_marker);
                                        mMapbox.addMarker(new MarkerOptions().title(String.valueOf(latLng)).position(latLng).setIcon(icon1));
                                    }else if(mpromotions.get(j).getRouteassignmentsummaryid()==AppPreferences.getDependentRouteAssignmentSummaryId(ProjectMonitorActivity.this)){
                                        Icon icon1=iconFactory.fromResource(R.drawable.green_marker);
                                        mMapbox.addMarker(new MarkerOptions().title(String.valueOf(latLng)).position(latLng).setIcon(icon1));
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.blue_marker);
                                //mMap.addMarker(new MarkerOptions().position(prolatlong).icon(icon));
                                            /*PolylineOptions polyOptions = new PolylineOptions();
                                            polyOptions.color(ContextCompat.getColor(ProjectMonitorActivity.this, R.color.blue));
                                            polyOptions.addAll(points);
                                            polyOptions.pattern(PATTERN_POLYGON_ALPHA);
                                            mMap.addPolyline(polyOptions);*/
                            }
                        }
                    }
                }
            }
            /*for(int i=0;i<mpoNumberses.size();i++){
                ponumber_value=mpoNumberses.get(i).getNextvalue();
            }*/
            ponumber_value=mpoNumberses.get(0).getNextvalue()==null?0:mpoNumberses.get(0).getNextvalue();
        }
    }

    public class ImageCompression extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length == 0 || strings[0] == null)
                return null;
            return compressImage(strings[0]);
        }
        protected void onPostExecute(String imagePath) {
            mObjDialog.dismiss();
            Intent in=new Intent(ProjectMonitorActivity.this,CameraActivity.class);
            in.putExtra("imagepath",imagePath);
            //in.putExtra("imagename",imagename);
            startActivity(in);
            //captureImage.setImageBitmap(BitmapFactory.decodeFile(new File(imagePath).getAbsolutePath()));
        }
    }

    public static String compressImage(String imagePath) {
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }
        options.inSampleSize = calculateSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
            bitmap = BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        if (bitmap != null) {
            bitmap.recycle();
        }
        ExifInterface exif;
        try {
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        String filepath = imagePath;
        try {
            out = new FileOutputStream(filepath);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filepath;
    }

    public static int calculateSampleSize(BitmapFactory.Options options, int requireWidth, int requireHeight) {
        final int actualHeight = options.outHeight;
        final int actualWidth = options.outWidth;
        int sampleSize = 1;

        if (actualHeight > requireHeight || actualWidth > requireWidth) {
            final int heightRatio = Math.round((float) actualHeight / (float) requireHeight);
            final int widthRatio = Math.round((float) actualWidth / (float) requireWidth);
            sampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = actualWidth * actualHeight;
        final float totalReqPixelsCap = requireWidth * requireHeight * 2;
        while (totalPixels / (sampleSize * sampleSize) > totalReqPixelsCap) {
            sampleSize++;
        }
        return sampleSize;
    }

    public static String createFile() {
        File imageFile = new File(Environment.getExternalStorageDirectory()
                + "/ProjectMonitoring");
        if (!imageFile.exists()) {
            imageFile.mkdirs();
        }
        String imageName = "Ptm"+String.valueOf(AppUtilities.getDateandTimeString())+".jpg";
        String uri = (imageFile.getAbsolutePath() + "/" + imageName);
        return uri;
    }

    @Override
    public void onDestroy() {
        OpenHelperManager.releaseHelper();
        mDbHelper = null;
        super.onDestroy();
        mMapView.onDestroy();
    }

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

    private void startProgress() {
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setPercentage(final int percentage) {
        progressBar.setIndeterminate(false);
        progressBar.setProgress(percentage);
    }

    private void endProgress(final String message) {
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);
        AppPreferences.setofflineSuccess(ProjectMonitorActivity.this,true);
        // Show a toast
        //Toast.makeText(ProjectMonitorActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void DownloadRegion(){

        startProgress();
        String styleUrl = mMapbox.getStyle().getUrl();

        // Create a bounding box for the offline region
        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(new com.mapbox.mapboxsdk.geometry.LatLng(12.916409, 77.599889)) // Northeast
                .include(new com.mapbox.mapboxsdk.geometry.LatLng(12.877463, 77.595082)) // Southwest
                .build();

        double minZoom = mMapbox.getCameraPosition().zoom;
        double maxZoom = mMapbox.getMaxZoomLevel();
        float pixelRatio = this.getResources().getDisplayMetrics().density;
        OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
                styleUrl, latLngBounds, minZoom, maxZoom, pixelRatio);

        // Build a JSONObject using the user-defined offline region title,
        // convert it into string, and use it to create a metadata variable.
        // The metadata variable will later be passed to createOfflineRegion()
        byte[] metadata;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JSON_FIELD_REGION_NAME, "Benguluru");
            String json = jsonObject.toString();
            metadata = json.getBytes(JSON_CHARSET);
        } catch (Exception exception) {
            //Timber.e("Failed to encode metadata: %s", exception.getMessage());
            metadata = null;
        }

        // Create the offline region and launch the download
        offlineManager.createOfflineRegion(definition, metadata, new OfflineManager.CreateOfflineRegionCallback() {
            @Override
            public void onCreate(OfflineRegion offlineRegion) {
                //Timber.d( "Offline region created: %s" , "Benguluru");
                ProjectMonitorActivity.this.offlineRegion = offlineRegion;
                launchDownload();
            }

            @Override
            public void onError(String error) {
                //Timber.e( "Error: %s" , error);
            }
        });
    }

    private void launchDownload() {
        // Set up an observer to handle download progress and
        // notify the user when the region is finished downloading
        offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
            @Override
            public void onStatusChanged(OfflineRegionStatus status) {
                // Compute a percentage
                double percentage = status.getRequiredResourceCount() >= 0
                        ? (100.0 * status.getCompletedResourceCount() / status.getRequiredResourceCount()) :
                        0.0;

                if (status.isComplete()) {
                    // Download complete
                    endProgress(getString(R.string.end_progress_success));
                    return;
                } else if (status.isRequiredResourceCountPrecise()) {
                    // Switch to determinate state
                    setPercentage((int) Math.round(percentage));
                }

                // Log what is being currently downloaded
                /*Timber.d("%s/%s resources; %s bytes downloaded.",
                        String.valueOf(status.getCompletedResourceCount()),
                        String.valueOf(status.getRequiredResourceCount()),
                        String.valueOf(status.getCompletedResourceSize()));*/
            }

            @Override
            public void onError(OfflineRegionError error) {
                //Timber.e("onError reason: %s", error.getReason());
                //Timber.e("onError message: %s", error.getMessage());
            }

            @Override
            public void mapboxTileCountLimitExceeded(long limit) {
                //Timber.e("Mapbox tile count limit exceeded: %s", limit);
            }
        });

        // Change the region state
        offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);
    }

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
}
