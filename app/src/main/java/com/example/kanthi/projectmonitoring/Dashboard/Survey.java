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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.AdditemRV_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Details_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Details_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.DistributioRouteView_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.ItemType_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.PoJo.BOQHeaders;
import com.example.kanthi.projectmonitoring.PoJo.BOQTrailers;
import com.example.kanthi.projectmonitoring.PoJo.DistributionRouteView;
import com.example.kanthi.projectmonitoring.PoJo.ItemDefinition;
import com.example.kanthi.projectmonitoring.PoJo.ItemType;
import com.example.kanthi.projectmonitoring.PoJo.ItemsCategory;
import com.example.kanthi.projectmonitoring.PoJo.ParamCategories;
import com.example.kanthi.projectmonitoring.PoJo.ParamDetails;
import com.example.kanthi.projectmonitoring.PoJo.SurveyPromotions;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.PoJo.TaskItemLinkView;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
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

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Survey extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnCameraIdleListener {
    //private GoogleMap mMap;
    private MapboxMap mMapbox;
    private MapView mMapView;

    private FloatingActionButton sur_save,sur_order;
    private FloatingActionButton sur_location,sur_details;
    private LocationManager manager;
    private LocationListener listener;
    private double lati;
    private double longi;
    private double latitude;
    private double longitude;
    private LatLng source;
    private TextView drag_marker;
    private List<ParamCategories> mparamCategories;
    private int paramid;
    private String param_name,param_type,dropdown_value,rb_values;
    private ArrayList<ParamDetails> mdetails;
    int item_sub_type_id, mIntCategoryPriority = 0,mIntItemPriority=0,mIntItemSubPriority=0;
    private ParamDetails details;
    private Surveys surveys;
    private SurveyPromotions surveyPromotions;
    private int count=0;
    String cal;
    private CoordinatorLayout coordinatorLayout;
    private List<Surveys> msurvey;
    private List<Surveys> mfiltersurvey;
    ArrayList<LatLng> points;
    ProgressDialog progressDialog2;
    private BOQHeaders headers;
    private BOQTrailers trailers;
    private List<BOQHeaders> mheaders;
    //
    private List<BOQTrailers> mTrailers;
    //private ArrayList<BOQTrailers> mTrailers;
    private Paint mPaint;
    int partnerid;
    ProgressDialog progressDialog;
    private Boolean Initial=true;
    private AdditemRV_Adapter additemRV_adapter;
    private RecyclerView rvOrderedItems;
    private LinearLayoutManager manager1;
    private List<ItemsCategory> mitemcategory;
    private List<DistributionRouteView> mdistrubutionRoutrViews;
    private List<ItemType> mitemtypes;
    private List<TaskItemLinkView> mitemdefinition;
    private ArrayList<TaskItemLinkView> mEnteredItemDefinitions;
    ProgressDialog progres;
    public RecyclerView rv_ItemDefinition;
    public Spinner item_category, item_type_rv;
    AlertDialog mSearchOptionsDialog;
    ArrayList<ItemType> filtereditemtype;
    private LatLng sur_map;
    String day;
    String geoaddress;
    private File file;
    private String imagepath;
    int surveyid;
    String imagename;
    int trailerCount=0,detailscount=0;

    public static final int REQUEST_IMAGE_CAPTURE=1;
    private Uri picUri;
    private File picFile;
    public String imagePath;
    private static final float maxHeight = 920.0f;
    private static final float maxWidth = 920.0f;

    ProgressDialog mObjDialog;

    //offline progress bar
    private ProgressBar progressBar;
    // JSON encoding/decoding
    public static final String JSON_CHARSET = "UTF-8";
    public static final String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";

    // Offline objects
    private OfflineManager offlineManager;
    private OfflineRegion offlineRegion;

    int spinnerSelectPosition=0;

    AvahanSqliteDbHelper mDbHelper;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_survey);

        mMapView = findViewById(R.id.mapView);
        progressBar = findViewById(R.id.progress_bar);
        mMapView.onCreate(savedInstanceState);

        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        imagePath = createFile();

        AppPreferences.setPrefCaptureImage(Survey.this, null);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        drag_marker = findViewById(R.id.drag_marker);
        sur_location = findViewById(R.id.survey_location);
        sur_details = findViewById(R.id.survey_details);
        sur_save = findViewById(R.id.survey_save);
        sur_order = findViewById(R.id.survey_order);
        Intent in = getIntent();
        Bundle bun = in.getExtras();
        //Toast.makeText(this, ""+bun.getInt("partnerid"), Toast.LENGTH_SHORT).show();
        partnerid = bun.getInt("partnerid");
        day=bun.getString("date");
        getSupportActionBar().setTitle("Survey"+" ( "+day+" )");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        cal = String.valueOf(date);
        sur_details.setEnabled(false);
        sur_order.setEnabled(false);
        sur_location.setEnabled(false);
        sur_save.setEnabled(false);

        //userLocation();

        progres = new ProgressDialog(Survey.this);
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        //networksurvey();

        //todo unmask for offline
        /*if(!isNetworkAvailable() && !AppPreferences.getofflineSuccess(Survey.this)){
            Log.d("internetcheck","if");
            new AlertDialog.Builder(Survey.this)
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

        mObjDialog = new ProgressDialog(Survey.this);
        mObjDialog.setMessage("Saving Image,Please Wait..");
        mObjDialog.setIndeterminate(true);
        mObjDialog.setCancelable(false);

        mdetails = new ArrayList<ParamDetails>();
        msurvey=new ArrayList<>();
        mfiltersurvey=new ArrayList<>();
        sur_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latitude != 0.0 && longitude != 0.0) {
                    //Toast.makeText(Survey.this, "" + latitude + "," + longitude, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Survey.this, "Location Captured", Toast.LENGTH_SHORT).show();
                    sur_location.setEnabled(false);
                    sur_details.setEnabled(true);
                    sur_location.setBackgroundColor(getResources().getColor(R.color.lite_white));
                    //sur_location.setTextColor(getResources().getColor(R.color.red));
                    sur_details.setBackgroundColor(getResources().getColor(R.color.bottom_purple));
                    mMapbox.getUiSettings().setScrollGesturesEnabled(false);
                    drag_marker.setText("Click The Details");
                } else {
                    Toast.makeText(Survey.this, "Drag The Marker", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sur_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Survey.this);
                View view = LayoutInflater.from(Survey.this).inflate(R.layout.survey_details_popup, null);
                builder.setCancelable(false);
                builder.setTitle("Survey Details");
                builder.setView(view);
                final LinearLayout la_edittext = view.findViewById(R.id.detail_edittext);
                final LinearLayout la_location = view.findViewById(R.id.detail_location);
                final LinearLayout la_spinner = view.findViewById(R.id.details_spinner1);
                final LinearLayout la_radiio = view.findViewById(R.id.details_radio);
                Button capture_image = view.findViewById(R.id.survey_capture);
                final Spinner sp_parameter = view.findViewById(R.id.details_spinner);
                final EditText param_value = view.findViewById(R.id.details_value);
                final Button bt_location = view.findViewById(R.id.location_button);
                final Spinner sp_dropdown = view.findViewById(R.id.sp_details_spinner);
                final RadioGroup rd_group = view.findViewById(R.id.radiogroup);


                final RadioButton rb_yes = view.findViewById(R.id.rg_details_yes);
                final RadioButton rb_no = view.findViewById(R.id.rb_details_no);

                Button para_add = view.findViewById(R.id.details_add);
                final RecyclerView re_details = view.findViewById(R.id.rv_details_popup);
                Button sur_ok = view.findViewById(R.id.survey_ok);
                Button sur_cancel = view.findViewById(R.id.survey_cancel);
                final AlertDialog dailog = builder.create();
                capture_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*imagename="Ptm"+String.valueOf(AppUtilities.getDateandTimeString())+".jpg";
                        //Log.d("imagename",imagename);
                        Intent in =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File imagefolder=new File(Environment.getExternalStorageDirectory(),"Project Monitoring");
                        imagefolder.mkdirs();
                        file=new File(imagefolder,imagename);
                        //imagepath=file.getPath();
                        Uri outputfile = Uri.fromFile(file);
                        in.putExtra(MediaStore.EXTRA_OUTPUT,outputfile);
                        startActivityForResult(in,1000);*/

                        if (ContextCompat.checkSelfPermission(Survey.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Survey.this,
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
                });
                try {
                    final Details_Sp_Adapter adapter = new Details_Sp_Adapter(Survey.this, mparamCategories);
                    sp_parameter.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
                sp_parameter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i > 0) {
                            paramid = mparamCategories.get(sp_parameter.getSelectedItemPosition() - 1).getId();
                            param_name = mparamCategories.get(sp_parameter.getSelectedItemPosition() - 1).getName();
                            param_type = mparamCategories.get(sp_parameter.getSelectedItemPosition() - 1).getInputtype();
                            if (param_type.equalsIgnoreCase("numeric")) {
                                param_value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED );
                            } else {
                                param_value.setInputType(InputType.TYPE_CLASS_TEXT);
                            }
                            if (param_type.equalsIgnoreCase("text") || param_type.equalsIgnoreCase("numeric")) {
                                la_spinner.setVisibility(View.GONE);
                                la_radiio.setVisibility(View.GONE);
                                la_edittext.setVisibility(View.VISIBLE);
                                la_location.setVisibility(View.GONE);
                            }
                            if (param_type.equalsIgnoreCase("location")) {
                                la_spinner.setVisibility(View.GONE);
                                la_radiio.setVisibility(View.GONE);
                                la_edittext.setVisibility(View.GONE);
                                la_location.setVisibility(View.VISIBLE);
                                bt_location.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //userLocation();
                                        Toast.makeText(Survey.this, "Location Captured", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if (param_type.equalsIgnoreCase("dropdown")) {
                                la_location.setVisibility(View.GONE);
                                la_edittext.setVisibility(View.GONE);
                                la_spinner.setVisibility(View.VISIBLE);
                                la_radiio.setVisibility(View.GONE);
                                String inputvalues = mparamCategories.get(sp_parameter.getSelectedItemPosition() - 1).getInputvalues();
                                String[] Array = inputvalues.split(",");
                                List<String> param_values = new ArrayList<String>();
                                for (String name : Array) {
                                    param_values.add(name);
                                   // param_values.set(0,"Val");
                                }

                                ArrayAdapter<String> sp_adapter;
                                sp_adapter = new ArrayAdapter<String>(Survey.this, android.R.layout.simple_list_item_1, param_values);
                                sp_dropdown.setAdapter(sp_adapter);
                                sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        dropdown_value = (String) adapterView.getItemAtPosition(i);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            if (param_type.equalsIgnoreCase("rb")) {
                                la_edittext.setVisibility(View.GONE);
                                la_location.setVisibility(View.GONE);
                                la_radiio.setVisibility(View.VISIBLE);
                                la_spinner.setVisibility(View.GONE);
                                //String rb_values;
                                String inputvalues = mparamCategories.get(sp_parameter.getSelectedItemPosition() - 1).getInputvalues();
                                String[] Array = inputvalues.split(",");

                                //add this new
                               // RadioButton rdbtn = new RadioButton(Survey.this);

                                for (int r = 0; r < Array.length; r++) {

//                                   RadioButton rdbtn = new RadioButton(Survey.this);
//                                    rdbtn.setId(r);
//                                    rdbtn.setText(Array[r]);
//                                    rd_group.addView(rdbtn);

//                                    //add new line
//                                     RadioButton radioButton= findViewById(R.id.rg_details_yes);
//                                     radioButton.setId(r);
//                                     radioButton.setText(Array[r]);
//                                     rd_group.addView(radioButton);
//                                   // rd_group.addView(radioButton);
                                }
                                rd_group.clearCheck();
                                rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        RadioButton rb_yes1 = (RadioButton) group.findViewById(checkedId);
                                       /* if (null != rb_yes1 && checkedId > -1) {
                                            rb_values = rb_yes1.getText().toString();
                                        }*/


                                        if (null != rb_yes1 ) {
                                            rb_values = rb_yes1.getText().toString();
                                        }
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                final Details_Rv_Adapter recyclerview_adapter = new Details_Rv_Adapter(mdetails);
                LinearLayoutManager layoutManager = new LinearLayoutManager(Survey.this, LinearLayoutManager.VERTICAL, false);
                re_details.setAdapter(recyclerview_adapter);
                re_details.setLayoutManager(layoutManager);
                para_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if((param_type!=null && param_value.length()!=0)
                                || (param_type!=null && dropdown_value!=null)
                                || (param_type!=null && rb_values!=null)){
                            details = new ParamDetails();
                            long masterId = System.currentTimeMillis();
                            details.setCategoryid(String.valueOf(paramid));
                            details.setParametervalue(param_name==null?"":param_name);
                            if (param_type.equalsIgnoreCase("text") || param_type.equalsIgnoreCase("numeric")) {
                                details.setDescription(param_value.getText().toString());
                            }
                            if (param_type.equalsIgnoreCase("location")) {
                                details.setDescription(new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(lati))) + "," + new DecimalFormat("##.###").format(Float.parseFloat(String.valueOf(longi))));
                            }
                            if (param_type.equalsIgnoreCase("dropdown")) {
                                details.setDescription(dropdown_value);
                                sp_dropdown.setSelection(0);
                            }
                            if (param_type.equalsIgnoreCase("rb")) {
                                details.setDescription(rb_values);
                                rd_group.clearCheck();
                            }
                            if (details != null) {
                                //details.setSurveyid(String.valueOf(masterId));
                                details.setInsertFlag(true);
                                details.setId(masterId);
                                mdetails.add(details);
                                if (recyclerview_adapter != null) {
                                    recyclerview_adapter.notifyDataSetChanged();
                                }
                                param_value.setText("");
                                param_type=null;
                                sp_parameter.setSelection(0);la_spinner.setVisibility(View.GONE);
                                la_radiio.setVisibility(View.GONE);
                                la_edittext.setVisibility(View.VISIBLE);
                                la_location.setVisibility(View.GONE);

                            /*try {
                                RuntimeExceptionDao<ParamDetails,Integer> paramDetailsDao=mDbHelper.getParamDetailsRuntimeDao();
                                paramDetailsDao.create(details);
                            }catch (Exception e){
                                e.printStackTrace();
                            }*/
                            } else {
                                Toast.makeText(Survey.this, "Empty data", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(Survey.this, "Select Parameter/ Enter value", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                sur_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mdetails.size()>0){
                            dailog.dismiss();
                            sur_details.setBackgroundColor(getResources().getColor(R.color.lite_white));
                            drag_marker.setText("Click The Order");
                            sur_order.setBackgroundColor(getResources().getColor(R.color.bottom_purple));
                            sur_order.setEnabled(true);
                        }else {
                            Toast.makeText(Survey.this, "Select Parameter", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                sur_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dailog.dismiss();
                    }
                });
                dailog.show();
            }
        });
        sur_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showconfirmpopup();
            }
        });
        sur_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (latitude != 0.0 && longitude != 0.0 && param_name != null && AppPreferences.getPrefCaptureImage(Survey.this) != null)
                if (latitude != 0.0 && longitude != 0.0 && param_name != null) {
                    long masterId = System.currentTimeMillis();
                    surveys = new Surveys();
                    surveys.setId(masterId);
                    surveys.setLinkid(AppPreferences.getPrefAreaId(Survey.this));
                    surveys.setLatitude(latitude);
                    surveys.setLongitude(longitude);
                    surveys.setLandmark(geoaddress);
                    surveys.setSlno(msurvey.size() + 1);
                    surveys.setUserid(AppPreferences.getEmployeeId(Survey.this));
                    surveys.setRouteassignmentid(AppPreferences.getRouteAssignmentId(Survey.this));
                    surveys.setRouteassignmentsummaryid(AppPreferences.getRouteAssignmentSummaryId(Survey.this));
                    surveys.setZoneid(AppPreferences.getZoneId(Survey.this));
                    surveys.setAreaid(AppPreferences.getPrefAreaId(Survey.this));
                    surveys.setDistareaid(AppPreferences.getDist_Area_Id(Survey.this));
                    surveys.setDistsubareaid(AppPreferences.getDistributionSubAreaId(Survey.this).equalsIgnoreCase("null")?0:Integer.valueOf(AppPreferences.getDistributionSubAreaId(Survey.this)));
                    surveys.setPendingflag(false);
                    surveys.setInsertFlag(true);
                    surveys.setDate(AppUtilities.getDateTime());
                    //postSurvey();
                    surveyPromotions = new SurveyPromotions();
                    surveyPromotions.setId(masterId);
                    surveyPromotions.setInsertFlag(true);
                    surveyPromotions.setRetailername(String.valueOf(AppPreferences.getZoneId(Survey.this)));
                    surveyPromotions.setRetailerid(AppPreferences.getPrefAreaId(Survey.this));
                    surveyPromotions.setRetaileraddress(cal.substring(4, 10));
                    surveyPromotions.setRetailerimage(AppPreferences.getPrefCaptureImage(Survey.this)==null?"null":AppPreferences.getPrefCaptureImage(Survey.this));
                    surveyPromotions.setDatetime(AppUtilities.getDateTime1());
                    surveyPromotions.setLatitude(latitude);
                    surveyPromotions.setLongitude(longitude);
                    surveyPromotions.setEmployeeid(AppPreferences.getEmployeeId(Survey.this));
                    surveyPromotions.setRouteid(AppPreferences.getPrefRouteId(Survey.this));
                    surveyPromotions.setTourtypeid(AppPreferences.getTourTypeId(Survey.this));
                    surveyPromotions.setAreaid(AppPreferences.getPrefAreaId(Survey.this));
                    surveyPromotions.setDistareaid(AppPreferences.getDist_Area_Id(Survey.this));
                    surveyPromotions.setZoneid(AppPreferences.getZoneId(Survey.this));
                    surveyPromotions.setSalesmgrid(AppPreferences.getSaleMngrIdId(Survey.this));
                    surveyPromotions.setRouteassignmentsummaryid(AppPreferences.getRouteAssignmentSummaryId(Survey.this));
                    surveyPromotions.setRouteassignmentid(AppPreferences.getRouteAssignmentId(Survey.this));
                    try {
                        RuntimeExceptionDao<Surveys,Integer> surveysDao = mDbHelper.getSurveysRuntimeDao();
                        surveysDao.create(surveys);

                        for(int i=0;i<mdetails.size();i++){
                            mdetails.get(i).setSurveyid(String.valueOf(masterId));
                            RuntimeExceptionDao<ParamDetails,Integer> paramDetailsDao=mDbHelper.getParamDetailsRuntimeDao();
                            paramDetailsDao.create(mdetails.get(i));
                        }

                        headers.setSurveyid(masterId);
                        RuntimeExceptionDao<BOQHeaders,Integer> boqHeadersdao=mDbHelper.getBoqHeadersRuntimeDao();
                        boqHeadersdao.create(headers);

                        Log.d("mtrailers",""+mTrailers.size());
//                        for(int i=0;i<mTrailers.size();i++){
//                            mTrailers.get(i).setSurveyid(masterId);
//                            RuntimeExceptionDao<BOQTrailers,Integer> boqTrailers=mDbHelper.getBoqTrailersRuntimeDao();
//                            boqTrailers.create(mTrailers.get(i));
//                        }
                    // i am add new line and comment upper line
                        trailers.setSurveyid(masterId);
                        RuntimeExceptionDao<BOQTrailers,Integer> boqTrailersdao=mDbHelper.getBoqTrailersRuntimeDao();
                        boqTrailersdao.create(trailers);


                        RuntimeExceptionDao<SurveyPromotions,Integer> surveyPromotionsDao=mDbHelper.getSurveyPromotionsRuntimeDao();
                        surveyPromotionsDao.create(surveyPromotions);

                        Toast.makeText(Survey.this, "Saved", Toast.LENGTH_SHORT).show();
                        mdetails.clear();
                        mMapbox.getUiSettings().setScrollGesturesEnabled(true);
                        mEnteredItemDefinitions.clear();
                        drag_marker.setText("Click the Location");
                        sur_details.setEnabled(false);
                        sur_order.setEnabled(false);
                        sur_location.setEnabled(false);
                        sur_save.setEnabled(false);

                        progres.show();
                        userLocation();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Survey.this, "Select the Marker/Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(Survey.this);*/

        mMapView.getMapAsync(Survey.this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.statusupdate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id==R.id.action_details){
            //Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            //surveypoints();
            SurveyPoints_Popup surveyPoints_popup=new SurveyPoints_Popup();
            surveyPoints_popup.show(getSupportFragmentManager(),null);
        }
        if (id == R.id.action_refresh) {
            finish();
            Intent in = new Intent(Survey.this, Survey.class);
            in.putExtra("date",day);
            in.putExtra("partnerid", partnerid);
            startActivity(in);
            //return true;
        }
        if (id == R.id.action_statusupdate) {
            Intent in1 = new Intent(Survey.this, StatusUpdate.class);
            in1.putExtra("partnerid", partnerid);
            in1.putExtra("date",day);
            in1.putExtra("Latitude", lati);
            in1.putExtra("Longitude", longi);
            startActivity(in1);
            //return true;
        }
        return Survey.super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setOnMarkerDragListener(this);
        mMap.setOnCameraIdleListener(Survey.this);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }*/


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mMapbox = mapboxMap;
        mMapbox.addOnCameraIdleListener(Survey.this);
        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        //offlineManager = OfflineManager.getInstance(Survey.this);
                        progres.show();
                        userLocation();
                        //new FetchDetailsFromDbTask().execute();

                        /*if(!AppPreferences.getofflineSuccess(Survey.this)){
                            DownloadRegion();
                        }*/
                    }
                });
    }

    @Override
    public void onCameraIdle() {
        if(mMapbox!=null){
            com.mapbox.mapboxsdk.geometry.LatLng latLng = mMapbox.getCameraPosition().target;
            if(latLng!=null){
                latitude = latLng.getLatitude();
                longitude = latLng.getLongitude();
                Geocoder geocoder = new Geocoder(Survey.this);
                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.getLatitude(), latLng.getLongitude(),1);
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        if (!locality.isEmpty()) {
                            geoaddress=locality;
                            //Toast.makeText(this, ""+locality, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    geoaddress="not found";
                }
                //Toast.makeText(Survey.this, "" + latitude + "," + longitude, Toast.LENGTH_SHORT).show();
                //drag_marker.setVisibility(View.GONE);
                sur_location.setEnabled(true);
                drag_marker.setText("Click The Location");
                sur_location.setBackgroundColor(getResources().getColor(R.color.poym));
            }else{
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
            }
        }
        /*LatLng latLng = mMapbox.getCameraPosition().target;
        if (source != null) {
            latitude = latLng.latitude;
            longitude = latLng.longitude;
            Log.e("gettingaddress","true");
            Geocoder geocoder = new Geocoder(Survey.this);
            try {
                List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1);
                if (addressList != null && addressList.size() > 0) {
                    String locality = addressList.get(0).getAddressLine(0);
                    //String country = addressList.get(0).getCountryName();
                    if (!locality.isEmpty()) {
                        geoaddress=locality;
                        //Toast.makeText(this, ""+geoaddress, Toast.LENGTH_SHORT).show();
                        //geoaddress.add(locality);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Toast.makeText(Survey.this, "" + latitude + "," + longitude, Toast.LENGTH_SHORT).show();
            //drag_marker.setVisibility(View.GONE);
            sur_location.setEnabled(true);
            drag_marker.setText("Click The Location");
            sur_location.setBackgroundColor(getResources().getColor(R.color.poym));
            *//*if (Initial == false) {

            } else {
                Log.e("gettingaddress","false");
                Initial = false;
            }*//*
        }*/
    }
    private void userLocation() {
        final ProgressDialog progress = new ProgressDialog(Survey.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Fetching ur location,Plz Wait..");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        manager = (LocationManager) Survey.this.getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                source = new LatLng(lati, longi);
                new FetchDetailsFromDbTask().execute();

                /*if(source!=null) {
                    if (msurvey.size() > 0) {
                        double lat = msurvey.get(0).getLatitude();
                        double lon = msurvey.get(0).getLongitude();
                        CameraPosition pos = new CameraPosition.Builder()
                                .target(new com.mapbox.mapboxsdk.geometry.LatLng(lat, lon))
                                .zoom(15)
                                .tilt(20)
                                .build();
                        if (mMapbox != null)
                            mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(pos), 1000);
                    } else {
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new com.mapbox.mapboxsdk.geometry.LatLng(lati, longi))
                                .zoom(15)
                                .tilt(20)
                                .build();
                        if (mMapbox != null)
                            mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);
                    }
                }*/

                /*CameraPosition position = new CameraPosition.Builder()
                        .target(new com.mapbox.mapboxsdk.geometry.LatLng(lati, longi))
                        .zoom(15)
                        .tilt(20)
                        .build();
                if(mMapbox!=null)
                mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);*/

                /*try {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(source, 19));
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                if (ActivityCompat.checkSelfPermission(Survey.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Survey.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                if (i == LocationProvider.OUT_OF_SERVICE) {
                    Toast.makeText(Survey.this, "Network Unavaliable", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(Survey.this, "Fetching..PlzWait", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(Survey.this, "Please enable GPS", Toast.LENGTH_SHORT).show();
            }
        };
        if (ActivityCompat.checkSelfPermission(Survey.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Survey.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 50, listener);
    }

    public void ItemCategoryCallingAdapter(String itemcategoryid, int position) {
        filtereditemtype = new ArrayList<ItemType>();
        mIntCategoryPriority++;
        mitemcategory.get(position).setPriority(mIntCategoryPriority);
        for (int j = 0; j < itemcategoryid.split(",").length; j++) {
            ItemsCategory itemsCategory = null;
            itemsCategory = new ItemsCategory(0);
            if(!itemcategoryid.split(",")[j].equalsIgnoreCase("")){
                itemsCategory = new ItemsCategory(Integer.parseInt(itemcategoryid.split(",")[j]));
            }
            for (int i = 0; i < mitemtypes.size(); i++) {
                if (itemcategoryid.split(",")[j].equalsIgnoreCase(String.valueOf(mitemtypes.get(i).getItemcategoryId()))) {
                    //int index=mitemcategory.indexOf(itemsCategory)== -1 ? 0 :mitemcategory.indexOf(itemsCategory);
                    for(int k=0;k<mitemcategory.size();k++){
                        if(mitemcategory.get(k).getId().equals(Integer.valueOf(itemcategoryid.split(",")[j]))){
                            Log.e("priority",String.valueOf(mitemcategory.get(k).getPriority()));
                            ItemType itemType = mitemtypes.get(i);
                            itemType.setChildpriority(mitemcategory.get(k).getPriority());
                            filtereditemtype.add(itemType);
                        }
                    }
                }
            }
        }
        Collections.sort(filtereditemtype, new Comparator<ItemType>() {
            public int compare(ItemType emp1, ItemType emp2) {
                return emp2.getChildpriority().compareTo(emp1.getChildpriority());
            }
        });
        ItemType_Sp_Adapter adapter1 = new ItemType_Sp_Adapter(Survey.this,filtereditemtype);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(Survey.this, LinearLayoutManager.HORIZONTAL, false);
        item_type_rv.setAdapter(adapter1);
//        item_type_rv.setLayoutManager(layoutManager1);
        adapter1.notifyDataSetChanged();
    }
    public void ItemTypeCallingAdapter(int tasktypeid, int position) {
        ArrayList<TaskItemLinkView> filtereditemdefinition = new ArrayList<TaskItemLinkView>();

        for (int j = 0; j < mitemdefinition.size(); j++) {
            if (tasktypeid == mitemdefinition.get(j).getTasktypeid()) {
                filtereditemdefinition.add(mitemdefinition.get(j));
            }
        }

        Collections.sort(filtereditemdefinition, new Comparator<TaskItemLinkView>() {
            public int compare(TaskItemLinkView emp1, TaskItemLinkView emp2) {
                return emp2.getId().compareTo(emp1.getId());
            }
        });
        additemRV_adapter = new AdditemRV_Adapter(Survey.this, filtereditemdefinition,
                mSearchOptionsDialog);
        LinearLayoutManager manager1 = new LinearLayoutManager(Survey.this, LinearLayoutManager.VERTICAL, false);
        rv_ItemDefinition.setAdapter(additemRV_adapter);
        rv_ItemDefinition.setLayoutManager(manager1);
        additemRV_adapter.notifyDataSetChanged();
    }
    /*public void ItemsubTypeCallingAdapter(String itemsubtypeid, int position) {
        ArrayList<ItemDefinition> filtereditemdefinition = new ArrayList<ItemDefinition>();
        mIntItemSubPriority++;
        filteredsubtype.get(position).setParentpriority(mIntItemSubPriority);
        for (int l = 0; l < itemsubtypeid.split(",").length; l++) {
            ItemSubTypes itemsub = null;
            itemsub=new ItemSubTypes(0);
            if(!itemsubtypeid.split(",")[l].equalsIgnoreCase("")){
                itemsub = new ItemSubTypes(Integer.parseInt(itemsubtypeid.split(",")[l]));
            }
            for (int k = 0; k < mitemdefinition.size(); k++) {
                if (itemsubtypeid.split(",")[l].equalsIgnoreCase(String.valueOf(mitemdefinition.get(k).getItemsubtypeId()))) {
                    ItemDefinition itemDefinition = mitemdefinition.get(k);
                    itemDefinition.setChildpriority(filteredsubtype.get(filteredsubtype.indexOf(itemsub)).getParentpriority());
                    filtereditemdefinition.add(mitemdefinition.get(k));
                }
            }
        }
        Collections.sort(filtereditemdefinition, new Comparator<ItemDefinition>() {
            public int compare(ItemDefinition emp1, ItemDefinition emp2) {
                return emp2.getChildpriority().compareTo(emp1.getChildpriority());
            }
        });
        additemRV_adapter = new AdditemRV_Adapter(Survey.this, filtereditemdefinition,
                mSearchOptionsDialog);
        LinearLayoutManager manager1 = new LinearLayoutManager(Survey.this, LinearLayoutManager.VERTICAL, false);
        rv_ItemDefinition.setAdapter(additemRV_adapter);
        rv_ItemDefinition.setLayoutManager(manager1);
        additemRV_adapter.notifyDataSetChanged();
    }*/
    public void ItemCategoryEmptyAdapter() {
        ArrayList<ItemType> empty_itemtype=new ArrayList<ItemType>();
        ArrayList<ItemDefinition> empty_itemdef=new ArrayList<ItemDefinition>();
        ItemType_Sp_Adapter adapter1 = new ItemType_Sp_Adapter(Survey.this,empty_itemtype);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(Survey.this, LinearLayoutManager.HORIZONTAL, false);
        item_type_rv.setAdapter(adapter1);
//        item_type_rv.setLayoutManager(layoutManager1);
        adapter1.notifyDataSetChanged();
        ItemTypeEmptyAdapter();
        for(ItemType itemType : mitemtypes){
            itemType.setIsselected(false);
        }
    }

    public void ItemTypeEmptyAdapter(){
        ArrayList<TaskItemLinkView> emptyitemdefinition=new ArrayList<TaskItemLinkView>();
        additemRV_adapter = new AdditemRV_Adapter(Survey.this, emptyitemdefinition,
                mSearchOptionsDialog);
        LinearLayoutManager manager1 = new LinearLayoutManager(Survey.this, LinearLayoutManager.VERTICAL, false);
        rv_ItemDefinition.setAdapter(additemRV_adapter);
        rv_ItemDefinition.setLayoutManager(manager1);
        additemRV_adapter.notifyDataSetChanged();
    }

    private void showconfirmpopup(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Survey.this);
        final View v1 = LayoutInflater.from(Survey.this).inflate(R.layout.survey_order_popup, null);
        item_category = (Spinner) v1.findViewById(R.id.item_category);
        item_type_rv = (Spinner) v1.findViewById(R.id.item_type_rv);
        rv_ItemDefinition = (RecyclerView) v1.findViewById(R.id.rvadditem);
        Button bt_ok = (Button) v1.findViewById(R.id.popup_ok);
        Button bt_cancel = (Button) v1.findViewById(R.id.popup_cancel);
        builder.setCancelable(false);
        builder.setTitle("BOQ Details");
        builder.setView(v1);
        mSearchOptionsDialog = builder.create();
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mSearchOptionsDialog.dismiss();
                        AlertDialog.Builder builder1=new AlertDialog.Builder(Survey.this);
                        View v = LayoutInflater.from(Survey.this).inflate(R.layout.confirmpopup, null);
                        builder1.setView(v);
                        TextView tv_message= (TextView) v.findViewById(R.id.confirm_message);
                        Button bt_ok= (Button) v.findViewById(R.id.confirm_ok);
                        Button bt_cancel= (Button) v.findViewById(R.id.confirm_cancel);
                        final AlertDialog mDialog = builder1.create();
                        bt_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mSearchOptionsDialog.dismiss();
                                mDialog.dismiss();
                            }
                        });
                        bt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDialog.dismiss();
                            }
                        });
                        tv_message.setText(" Complete This Order ?");
                        builder1.setCancelable(false);
                        mDialog.show();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             int sp =  spinnerSelectPosition;

                        mEnteredItemDefinitions = new ArrayList<TaskItemLinkView>();
                        for (TaskItemLinkView itemDefinition : mitemdefinition) {
                            if (itemDefinition.getQuantity() != null) {
                                try {
                                    mEnteredItemDefinitions.add(itemDefinition);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(mEnteredItemDefinitions.size()>0){

                           // int qty=0;
                            int position=0;
                            for(int j=0;j<mEnteredItemDefinitions.size();j++) {

                                if(position < mEnteredItemDefinitions.size())
                                {
                                    position=0;
                                }
                                else
                                 position=j;
                               // qty = qty + (mEnteredItemDefinitions.get(j).getQuantity() * (mEnteredItemDefinitions.get(j).getUnitprice()));

                            }

                            int q=mEnteredItemDefinitions.get(sp).getQuantity();



//
//

                                for(int i=1;i<=2;i++) {
                                    TaskItemLinkView definition = mEnteredItemDefinitions.get(i);

                                    int boqno = mheaders.size() <= 0 ? 1 : mheaders.size() + 1;

                                    //int boqno=mEnteredItemDefinitions.get(position).getUnitprice();
                                    long masterId = System.currentTimeMillis();
                                    headers = new BOQHeaders();
                                    headers.setId(masterId);
                                    headers.setZoneId(AppPreferences.getZoneId(Survey.this));
                                    headers.setSalesareaId(AppPreferences.getPrefAreaId(Survey.this));
                                    headers.setDistributionareaId(AppPreferences.getDist_Area_Id(Survey.this));
                                    headers.setBoqNo("BOQ " + boqno);
                                    headers.setBoqDate(AppUtilities.getDateTime());


                                    int price = mEnteredItemDefinitions.get(i).getUnitprice();
                                    int qua = mEnteredItemDefinitions.get(i).getQuantity();
                                    int amount = (price * qua);

                                    headers.setTotalamount(String.valueOf(amount));
                                    headers.setNetamount(String.valueOf(amount));

//                                headers.setTotalamount(String.valueOf(qty));
//                                headers.setNetamount(String.valueOf(qty));

                                    //headers.setSurveyid(masterId);
                                    headers.setLastModifiedDate(AppUtilities.getDateTime());
                                    headers.setInsertFlag(true);
                                    headers.setSurveyFlag(true);

                                }
                                    //postBoqHeaders();
                           /* try {
                                RuntimeExceptionDao<BOQHeaders,Integer> boqHeadersdao=mDbHelper.getBoqHeadersRuntimeDao();
                                boqHeadersdao.create(headers);
                            }catch (Exception e){
                                e.printStackTrace();
                            }*/


                                    //mTrailers = new ArrayList<BOQTrailers>();

                                    int pos = 0;

                                    for (int j = 0; j < mEnteredItemDefinitions.size(); j++) {

                                        if (pos < mEnteredItemDefinitions.size()) {
                                            pos = 0;
                                        } else
                                            position = j;

                                        //  TaskItemLinkView definition = mEnteredItemDefinitions.get(i);
                                    }
                                    TaskItemLinkView definition = mEnteredItemDefinitions.get(pos);
                                    //  ItemDefinition item =mEnteredItemDefinitions.get(pos);
                                    //int  boqn=mTrailers.size()<=0?1:mTrailers.size()+1;

                                    trailers = new BOQTrailers();
                                    trailers.setInsertFlag(true);
                                    trailers.setId(System.currentTimeMillis());
                                    trailers.setZoneId(AppPreferences.getZoneId(Survey.this));
                                    trailers.setSalesareaId(AppPreferences.getPrefAreaId(Survey.this));
                                    trailers.setDistributionareaId(AppPreferences.getDist_Area_Id(Survey.this));
                           //         trailers.setBoqNo("BOQ" + boqno);
                                    trailers.setBoqDate(AppUtilities.getDateTime());
                                    trailers.setQuantity(String.valueOf(mEnteredItemDefinitions.get(sp).getQuantity()));
                                    trailers.setPrice(String.valueOf(mEnteredItemDefinitions.get(sp).getUnitprice()));
                                    int price1 = definition.getUnitprice();
                                    int quan = definition.getQuantity();
                                    trailers.setAmount(String.valueOf(quan * price1));

                                    //   trailers.setAmount(String.valueOf(definition.getQuantity()));
                                    // trailers.setPrice(AdditemRV_Adapter.edtqty);

                                    // trailers.setItemdefinitionId(definition.getId());

                                    int itemdef = mEnteredItemDefinitions.get(sp).getItemid();

                                    trailers.setItemdefinitionId(itemdef);

                          //          trailers.setSurveyid(masterId);
                           //         trailers.setBoqheaderId(masterId);
                                    trailers.setSurveyFlag(true);



                               //   mTrailers.add(trailers);

                                //enable this comment
                              /* try {
                                    RuntimeExceptionDao<BOQTrailers,Integer> boqTrailers=mDbHelper.getBoqTrailersRuntimeDao();
                                    boqTrailers.create(trailers);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }*/


                        }
                        mSearchOptionsDialog.dismiss();

                        //show  this code
                        /*orderAddItemAdapter = new OrderAddItem_Adapter(mEnteredItemDefinitions);
                        manager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        rvOrderedItems.setAdapter(orderAddItemAdapter);
                        rvOrderedItems.setLayoutManager(manager1);
                        orderAddItemAdapter.notifyDataSetChanged();*/


                sur_order.setBackgroundColor(getResources().getColor(R.color.lite_white));
                //sur_order.setTextColor(getResources().getColor(R.color.red));
                drag_marker.setText("Click The Save");
                sur_save.setEnabled(true);
                sur_save.setBackgroundColor(getResources().getColor(R.color.bottom_purple));
            }
        });
        DistributioRouteView_Sp_Adapter adapter = new DistributioRouteView_Sp_Adapter(Survey.this,mdistrubutionRoutrViews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Survey.this, LinearLayoutManager.HORIZONTAL, false);
        item_type_rv.setAdapter(adapter);

//        item_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position>0){
//                    mitemcategory.get(position-1).setIsselected(mitemcategory.get(position-1).isselected()?false:true);
//                    String selectedCategories = "";
//                    for(int i=0;i<mitemcategory.size();i++){
//                        if(mitemcategory.get(i).isselected()){
//                            selectedCategories = selectedCategories.length()==0? String.valueOf(mitemcategory.get(i).getId())
//                                    :selectedCategories+","+ String.valueOf(mitemcategory.get(i).getId());
//                        }
//                    }
//                    ItemCategoryCallingAdapter(selectedCategories,position-1);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        })
        item_type_rv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){

                    spinnerSelectPosition = position;

                    mdistrubutionRoutrViews.get(position-1);
                    ItemTypeCallingAdapter(mdistrubutionRoutrViews.get(position-1).getTourtypeid(),position-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        item_category.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        mSearchOptionsDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(requestCode==1000){
            if(resultCode==RESULT_OK){
                String imagePath = file.getPath();
                Intent in=new Intent(Survey.this,CameraActivity.class);
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

    /*private void CallingSnackbar(){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, R.string.check_internet, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(Patroling_Activity.this, "done", Toast.LENGTH_SHORT).show();
                        //networksurvey();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }*/

    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                RuntimeExceptionDao<Surveys, Integer> surveyDao = mDbHelper.getSurveysRuntimeDao();
                Where<Surveys, Integer> where = surveyDao.queryBuilder().where();
                //where.eq("zoneid",AppPreferences.getZoneId(Survey.this)), added filter
                where.and(where.eq("areaid",AppPreferences.getPrefAreaId(Survey.this)),
                        where.eq("distareaid",AppPreferences.getDist_Area_Id(Survey.this)));
                mfiltersurvey = where.query();
                //where.eq("order","id") todo bcz of this data cant see

                RuntimeExceptionDao<ParamCategories, Integer> paramCategoriesDao = mDbHelper.getParamCategoriesRuntimeDao();
                mparamCategories=paramCategoriesDao.queryForAll();
                Log.e("mparamCategories",""+mparamCategories.size());

                RuntimeExceptionDao<ItemsCategory, Integer> ItemsCategoryDao = mDbHelper.getItemsCategoryRuntimeDao();
                QueryBuilder<ItemsCategory, Integer> itemsCategoryQueryBuilder = ItemsCategoryDao.queryBuilder();
                itemsCategoryQueryBuilder.where().eq("zoneId", AppPreferences.getZoneId(Survey.this));
                PreparedQuery<ItemsCategory> preparedQuery2 = itemsCategoryQueryBuilder.prepare();
                mitemcategory = ItemsCategoryDao.query(preparedQuery2);

                RuntimeExceptionDao<ItemType, Integer> ItemTypeDao = mDbHelper.getItemTypeRuntimeDao();
                QueryBuilder<ItemType, Integer> itemTypeQueryBuilder = ItemTypeDao.queryBuilder();
                itemTypeQueryBuilder.where().eq("zoneId", AppPreferences.getZoneId(Survey.this));
                PreparedQuery<ItemType> preparedQuery3 = itemTypeQueryBuilder.prepare();
                mitemtypes = ItemTypeDao.query(preparedQuery3);

                RuntimeExceptionDao<TaskItemLinkView, Integer> TaskItemLinkViewDao = mDbHelper.getTaskItemLinkViewRuntimeDao();
                QueryBuilder<TaskItemLinkView, Integer> taskItemLinkViewQueryBuilder = TaskItemLinkViewDao.queryBuilder();
                taskItemLinkViewQueryBuilder.where().eq("zoneId", AppPreferences.getZoneId(Survey.this));
                PreparedQuery<TaskItemLinkView> preparedQuery4 = taskItemLinkViewQueryBuilder.prepare();
                mitemdefinition = TaskItemLinkViewDao.query(preparedQuery4);

                RuntimeExceptionDao<DistributionRouteView, Integer> DistributionRouteViewDao = mDbHelper.getDistributionRouteViewRuntimeDao();
                QueryBuilder<DistributionRouteView, Integer> distributionRouteViewQueryBuilder = DistributionRouteViewDao.queryBuilder();
                distributionRouteViewQueryBuilder.where().eq("zoneId", AppPreferences.getZoneId(Survey.this));
                PreparedQuery<DistributionRouteView> preparedQuery5 = distributionRouteViewQueryBuilder.prepare();
                mdistrubutionRoutrViews = DistributionRouteViewDao.query(preparedQuery5);


                RuntimeExceptionDao<BOQHeaders, Integer> boqHeadersDoa = mDbHelper.getBoqHeadersRuntimeDao();
                mheaders=boqHeadersDoa.queryForAll();

                //add new line
                RuntimeExceptionDao<BOQTrailers, Integer> boqTrailersDoa = mDbHelper.getBoqTrailersRuntimeDao();
                mTrailers=boqTrailersDoa.queryForAll();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progres.dismiss();

            for(Surveys survey:mfiltersurvey){
                if(AppPreferences.getDistributionSubAreaId(Survey.this).equalsIgnoreCase("null")){
                    msurvey.add(survey);
                } else {
                    if(Integer.valueOf(AppPreferences.getDistributionSubAreaId(Survey.this)).equals(survey.getDistsubareaid())){
                        msurvey.add(survey);
                    }
                }
            }

            if(source!=null) {
                if (msurvey.size() > 0) {
                    double lat = msurvey.get(0).getLatitude();
                    double lon = msurvey.get(0).getLongitude();
                    CameraPosition pos = new CameraPosition.Builder()
                            .target(new com.mapbox.mapboxsdk.geometry.LatLng(lat, lon))
                            .zoom(14)
                            .tilt(20)
                            .build();
                    if (mMapbox != null)
                        mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(pos), 1000);
                } else {
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new com.mapbox.mapboxsdk.geometry.LatLng(lati, longi))
                            .zoom(14)
                            .tilt(20)
                            .build();
                    if (mMapbox != null)
                        mMapbox.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newCameraPosition(position), 1000);
                }
            }
            try {
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setDither(true);
                mPaint.setColor(getResources().getColor(R.color.red));
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                mPaint.setStrokeJoin(Paint.Join.ROUND);
                mPaint.setStrokeCap(Paint.Cap.ROUND);
                mPaint.setXfermode(null);
                //////////////increase the text size/////////////////
                mPaint.setTextSize(50f);
                mPaint.setAlpha(0xff);
                points = new ArrayList<LatLng>();
                //geoaddress=new ArrayList<String>();
                for (int i = 0; i < msurvey.size(); i++) {
                    double sur_lat = msurvey.get(i).getLatitude();
                    double sur_long = msurvey.get(i).getLongitude();
                    //String slno= String.valueOf(i);
                    LatLng sur_map = new LatLng(sur_lat, sur_long);
                    points.add(sur_map);
                    Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                    Bitmap bmp = Bitmap.createBitmap(200, 50, conf);
                    Canvas canvas = new Canvas(bmp);
                    canvas.drawText(String.valueOf(i), 1, 50, mPaint); // paint defines the text color, stroke width, size

                    //TODO marker code
                    /*mMapbox.getStyle().addImage("marker-icon-id",
                            BitmapFactory.decodeResource(
                                    Survey.this.getResources(), R.drawable.red_dot));

                    GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(
                            Point.fromLngLat(sur_lat, sur_long)));
                    mMapbox.getStyle().addSource(geoJsonSource);

                    SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                    symbolLayer.withProperties(
                            PropertyFactory.iconImage("marker-icon-id")
                    );
                    mMapbox.getStyle().addLayer(symbolLayer);*/

                    //TODO add nos on map


                    //mMapbox.addMarker(new MarkerOptions().position(sur_map).icon(BitmapDescriptorFactory.fromBitmap(bmp)).anchor(0.5f, 1));
                    //mMap.addPolyline(new PolylineOptions().addAll(points).width(10).color(Color.GRAY));

                    com.mapbox.mapboxsdk.geometry.LatLng latLng=new com.mapbox.mapboxsdk.geometry.LatLng(sur_lat,sur_long);
                    //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.red_dot);

                    IconFactory iconFactory=IconFactory.getInstance(Survey.this);
                    Icon icon1=iconFactory.fromResource(R.drawable.red_dot);
                    mMapbox.addMarker(new MarkerOptions().position(latLng).setIcon(icon1));


                    //mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sur_map, 12));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
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
            Intent in=new Intent(Survey.this,CameraActivity.class);
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
        AppPreferences.setofflineSuccess(Survey.this,true);
        // Show a toast
        //Toast.makeText(Survey.this, message, Toast.LENGTH_LONG).show();
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
                Survey.this.offlineRegion = offlineRegion;
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