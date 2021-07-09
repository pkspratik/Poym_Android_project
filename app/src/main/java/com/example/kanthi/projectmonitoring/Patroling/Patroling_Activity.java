package com.example.kanthi.projectmonitoring.Patroling;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.PoJo.Patrols;
import com.example.kanthi.projectmonitoring.PoJo.Zones;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Patroling_Activity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Button fetch_start, fetch_end;

    private LocationManager manager;
    private android.location.LocationListener listener;
    private double lati;
    private double longi;
    private LatLng source;
    ProgressDialog progres;

    private List<Zones> mzones;
    private List<Patrols> mpatrols;
    AvahanSqliteDbHelper mDbHelper;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patroling);
        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        getSupportActionBar().setTitle("Patroling");
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        fetch_start = findViewById(R.id.fetch_start);
        fetch_end = findViewById(R.id.fetch_end);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        try {
            AppPreferences.setUser_SalesAreaId(Patroling_Activity.this,bundle.getInt("usersalesareaid"));
            AppPreferences.setZoneId(Patroling_Activity.this,bundle.getInt("userzoneid"));
        }catch (Exception e){
            e.printStackTrace();
        }

        progres = new ProgressDialog(Patroling_Activity.this);
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Fetching Data,Please Wait..");
        progres.setIndeterminate(true);
        progres.setCancelable(false);

        userLocation();

        fetch_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLocationServices("start");
            }
        });
        fetch_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLocationServices("stop");
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void handleLocationServices(String status) {
        if (status.equals("start")) {
            GlobalApp globalApp = new GlobalApp();
            globalApp.createLocationRequest(this);
            if (Build.VERSION.SDK_INT >= 26) {
                Intent service = new Intent(this, Notification_Service.class);
                if (!Notification_Service.IS_SERVICE_RUNNING) {
                    service.setAction("startforeground");
                    Notification_Service.IS_SERVICE_RUNNING = true;
                    this.startForegroundService(service);
                }
            }
        } else if (status.equals("stop")) {
            GlobalApp globalApp = new GlobalApp();
            globalApp.removeLocationUpdates(Patroling_Activity.this);
            if (Build.VERSION.SDK_INT >= 26) {
                Intent service = new Intent(this, Notification_Service.class);
                if (Notification_Service.IS_SERVICE_RUNNING) {
                    service.setAction("stopforeground");
                    Notification_Service.IS_SERVICE_RUNNING = true;
                    startForegroundService(service);
                }
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void userLocation() {
        progres.show();
        manager = (LocationManager) Patroling_Activity.this.getSystemService(LOCATION_SERVICE);
        listener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                source = new LatLng(lati, longi);
                if (source != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(source, 19));
                }
                //networkZones();
                mzones=new ArrayList<>();
                new FetchDetailsFromDbTask().execute();
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                if (i == LocationProvider.OUT_OF_SERVICE) {
                    Toast.makeText(Patroling_Activity.this, "Network Unavaliable", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(Patroling_Activity.this, "Fetching Location,Please Wait", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(Patroling_Activity.this, "Please enable GPS", Toast.LENGTH_SHORT).show();
            }
        };
        if (ActivityCompat.checkSelfPermission(Patroling_Activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Patroling_Activity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }

        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, listener);
    }

    private void networkZones(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getZone(AppPreferences.getUserId(Patroling_Activity.this),AppPreferences.getZoneId(Patroling_Activity.this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Zones> zones = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Zones>>() {
                        }.getType());
                mzones = zones;
                String timeinterval= String.valueOf(mzones.get(0).getTimeinterval());
                if(timeinterval!=null){
                    AppPreferences.setTimeInterval(Patroling_Activity.this, Integer.valueOf(timeinterval));
                }else{
                    AppPreferences.setTimeInterval(Patroling_Activity.this,10);
                }
                Log.e("timeinterval",""+AppPreferences.getTimeInterval(Patroling_Activity.this));
                networkPatrols();
                Log.e("patrols",""+AppPreferences.getZoneId(Patroling_Activity.this)+
                        ","+AppPreferences.getUser_SalesAreaId(Patroling_Activity.this)+","+
                        AppPreferences.getLoggedUserName(Patroling_Activity.this));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                CallingSnackbar();
                //Toast.makeText(Patroling_Activity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void networkPatrols(){
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getPatrols(AppPreferences.getUserId(Patroling_Activity.this)
                ,AppPreferences.getZoneId(Patroling_Activity.this),AppPreferences.getUser_SalesAreaId(Patroling_Activity.this),
                AppPreferences.getLoggedUserName(Patroling_Activity.this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Patrols> patrols = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Patrols>>() {
                        }.getType());
                mpatrols = patrols;
                Log.e("patrols_size",""+mpatrols.size());
                progres.dismiss();
                for(int i=0;i<mpatrols.size();i++){
                    double sur_lat = Double.parseDouble(mpatrols.get(i).getLatitude());
                    double sur_long = Double.parseDouble(mpatrols.get(i).getLongitude());
                    LatLng sur_map = new LatLng(sur_lat, sur_long);

                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);
                    mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                CallingSnackbar();
                //Toast.makeText(Patroling_Activity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CallingSnackbar(){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, R.string.check_internet, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(Patroling_Activity.this, "done", Toast.LENGTH_SHORT).show();
                        userLocation();
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
                RuntimeExceptionDao<Patrols, Integer> patrolsDao = mDbHelper.getPatrolsRuntimeDao();
                Where<Patrols, Integer> where = patrolsDao.queryBuilder().where();
                where.and(where.eq("projectid", AppPreferences.getZoneId(Patroling_Activity.this)),
                        where.eq("linkid", AppPreferences.getUser_SalesAreaId(Patroling_Activity.this)));
                mpatrols = where.query();

                RuntimeExceptionDao<Zones, Integer> zonesDao = mDbHelper.getZonesRuntimeDao();
                QueryBuilder<Zones, Integer> zonesIntegerQueryBuilder = zonesDao.queryBuilder();
                zonesIntegerQueryBuilder.where().eq("id", AppPreferences.getZoneId(Patroling_Activity.this));
                PreparedQuery<Zones> preparedQuery4 = zonesIntegerQueryBuilder.prepare();
                mzones = zonesDao.query(preparedQuery4);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progres.dismiss();
            String timeinterval = null;
            if(mzones.size()>0){
                timeinterval= String.valueOf(mzones.get(0).getTimeinterval());
            }
            if(timeinterval!=null){
                AppPreferences.setTimeInterval(Patroling_Activity.this, Integer.valueOf(timeinterval));
            }else{
                AppPreferences.setTimeInterval(Patroling_Activity.this,10);
            }
            if(mpatrols.size()>0){
                for(int i=0;i<mpatrols.size();i++){

                    double sur_lat = Double.parseDouble(mpatrols.get(i).getLatitude());
                    double sur_long = Double.parseDouble(mpatrols.get(i).getLongitude());
                    LatLng sur_map = new LatLng(sur_lat, sur_long);

                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_black);
                    mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                }

                double first_lat = Double.parseDouble(mpatrols.get(0).getLatitude());
                double first_lon = Double.parseDouble(mpatrols.get(0).getLongitude());
                LatLng first = new LatLng(first_lat, first_lon);

                double last_lat = Double.parseDouble(mpatrols.get(mpatrols.size()-1).getLatitude());
                double last_lon = Double.parseDouble(mpatrols.get(mpatrols.size()-1).getLongitude());
                LatLng last = new LatLng(last_lat, last_lon);

                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.survey);
                mMap.addMarker(new MarkerOptions().position(first).icon(icon));

                BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.drawable.survey);
                mMap.addMarker(new MarkerOptions().position(last).icon(icon1));
            }
        }
    }
}
