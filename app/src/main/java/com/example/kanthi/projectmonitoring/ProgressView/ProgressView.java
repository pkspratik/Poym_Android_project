package com.example.kanthi.projectmonitoring.ProgressView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Adapters.Execution_Document_Rv_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.Project_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.RouteSalesViewsAdapter;
import com.example.kanthi.projectmonitoring.Dashboard.Survey;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Graphs.GraphActivity;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.ChangeRequests;
import com.example.kanthi.projectmonitoring.PoJo.DistributionRoutes;
import com.example.kanthi.projectmonitoring.PoJo.Documents;
import com.example.kanthi.projectmonitoring.PoJo.Priority;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.Sales_Area;
import com.example.kanthi.projectmonitoring.PoJo.SurveyPoints;
import com.example.kanthi.projectmonitoring.PoJo.SurveyPromotions;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.PoJo.TourTypes;
import com.example.kanthi.projectmonitoring.PoJo.Zones;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressView extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    private Spinner sp_project,sp_link,sp_task;
    private EditText et_date;
    String SelectedDate;
    private List<Zones> mzones;
    private List<Sales_Area> msalesAreas;
    private List<Surveys> msurveyPoints;
    private List<SurveyPromotions> msurveyPromotions;
    private List<DistributionRoutes> mroutes;
    private List<TourTypes> mTourtypes;
    ProgressDialog progres;
    private GoogleMap mMap;
    int user_id,link_id;
    String task_name;

    AvahanSqliteDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view);
        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        sp_project= (Spinner) findViewById(R.id.sp_project);
        sp_link= (Spinner) findViewById(R.id.sp_link);
        et_date= (EditText) findViewById(R.id.et_date);
        sp_task= (Spinner) findViewById(R.id.sp_task);
        getSupportActionBar().setTitle("Progress View");
        currentdate();
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                final int mm = calendar.get(Calendar.MONTH);
                final int dd = calendar.get(Calendar.DAY_OF_MONTH);
                String comparedates = String.valueOf(calendar.getTimeInMillis());
                DatePickerDialog dpDialog = new DatePickerDialog(ProgressView.this, toDateListener, yy, mm, dd);
                dpDialog.getDatePicker().setMaxDate(Long.parseLong(comparedates));
                dpDialog.show();
            }
        });

        try {
            RuntimeExceptionDao<Zones, Integer> zonesDao = mDbHelper.getZonesRuntimeDao();
            mzones=zonesDao.queryForAll();

            if (mzones.size() > 0) {
                Project_Sp_Adapter adapter = new Project_Sp_Adapter(ProgressView.this, mzones);
                sp_project.setAdapter(adapter);
                //sp_project.setSelection(1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        progres = new ProgressDialog(ProgressView.this);
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);

        /*ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getZones(AppPreferences.getUserId(ProgressView.this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Zones> zones = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Zones>>() {
                        }.getType());
                mzones = zones;
                progres.dismiss();
                if (mzones.size() > 0) {
                    Project_Sp_Adapter adapter = new Project_Sp_Adapter(ProgressView.this, mzones);
                    sp_project.setAdapter(adapter);
                    //sp_project.setSelection(1);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(ProgressView.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });*/
        sp_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    user_id = mzones.get(sp_project.getSelectedItemPosition() - 1).getId();
                    //Toast.makeText(ProgressView.this, ""+user_id, Toast.LENGTH_SHORT).show();
                    mMap.clear();
                    sp_link.setSelection(0);
                    sp_task.setSelection(0);
                    if(user_id!=0){
                        progres.show();
                        new FetchDetailsFromDbTask().execute();
                        //networksalesareas();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_link.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    link_id = msalesAreas.get(sp_link.getSelectedItemPosition() - 1).getId();
                    if(link_id!=0){
                        try {
                            RuntimeExceptionDao<Surveys, Integer> surveysDao = mDbHelper.getSurveysRuntimeDao();
                            QueryBuilder<Surveys, Integer> surveysQueryBuilder = surveysDao.queryBuilder();
                            surveysQueryBuilder.where().eq("linkid", link_id);
                            PreparedQuery<Surveys> preparedQuery2 = surveysQueryBuilder.prepare();
                            msurveyPoints = surveysDao.query(preparedQuery2);

                            RuntimeExceptionDao<SurveyPromotions, Integer> surveyPromotionsDao = mDbHelper.getSurveyPromotionsRuntimeDao();
                            Where<SurveyPromotions, Integer> where = surveyPromotionsDao.queryBuilder().where();
                            where.and(where.eq("retailerid",link_id),where.eq("retailername",user_id));
                            /*surveyPromotionsQueryBuilder.where().eq("retailerid", link_id);
                            surveyPromotionsQueryBuilder.where().eq("retailername", user_id);
                            PreparedQuery<SurveyPromotions> preparedQuery3 = surveyPromotionsQueryBuilder.prepare();*/
                            msurveyPromotions = where.query();

                            if(msurveyPoints.size()>0){
                                for(int i=0;i<msurveyPoints.size();i++){
                                    double sur_lat = msurveyPoints.get(i).getLatitude();
                                    double sur_long = msurveyPoints.get(i).getLongitude();
                                    LatLng sur_map = new LatLng(sur_lat, sur_long);
                                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.survey);
                                    mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                                }
                                double s_lat = msurveyPoints.get(msurveyPoints.size()-1).getLatitude();
                                double s_lon = msurveyPoints.get(msurveyPoints.size()-1).getLongitude();
                                LatLng zoom = new LatLng(s_lat, s_lon);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom, 16));
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //networksurveypoints();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_task.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    task_name = mroutes.get(sp_task.getSelectedItemPosition() - 1).getName();
                    //Toast.makeText(ProgressView.this, ""+name, Toast.LENGTH_SHORT).show();
                    if(task_name!=null){
                        mMap.clear();
                        for(int j=0;j<msurveyPromotions.size();j++){
                            if(task_name.equalsIgnoreCase(msurveyPromotions.get(j).getNames())){
                                double sur_lat = msurveyPromotions.get(j).getLatitude();
                                double sur_long = msurveyPromotions.get(j).getLongitude();
                                LatLng sur_map = new LatLng(sur_lat, sur_long);
                                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.execution);
                                mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                            }
                            double s_lat = msurveyPromotions.get(msurveyPromotions.size()-1).getLatitude();
                            double s_lon = msurveyPromotions.get(msurveyPromotions.size()-1).getLongitude();
                            LatLng zoom = new LatLng(s_lat, s_lon);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom, 16));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void networksalesareas() {
        progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getSalesAreas(AppPreferences.getUserId(ProgressView.this),user_id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Sales_Area> sales_areas = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Sales_Area>>() {
                        }.getType());
                msalesAreas = sales_areas;
                progres.dismiss();
                if(msalesAreas.size()>0){
                    SalesArea_Sp_Adapter areaSpAdapter=new SalesArea_Sp_Adapter(ProgressView.this,msalesAreas);
                    sp_link.setAdapter(areaSpAdapter);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(ProgressView.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void networksurveypoints() {
        progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getSurveypoints(AppPreferences.getUserId(ProgressView.this),link_id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<Surveys> surveyPoints = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<Surveys>>() {
                        }.getType());
                msurveyPoints = surveyPoints;
                networkSurveyPromotions();
                if(msurveyPoints.size()>0){
                    for(int i=0;i<msurveyPoints.size();i++){
                        double sur_lat = msurveyPoints.get(i).getLatitude();
                        double sur_long = msurveyPoints.get(i).getLongitude();
                        LatLng sur_map = new LatLng(sur_lat, sur_long);
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.survey);
                        mMap.addMarker(new MarkerOptions().position(sur_map).icon(icon));
                    }
                    double s_lat = msurveyPoints.get(msurveyPoints.size()-1).getLatitude();
                    double s_lon = msurveyPoints.get(msurveyPoints.size()-1).getLongitude();
                    LatLng zoom = new LatLng(s_lat, s_lon);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom, 16));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(ProgressView.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void networkSurveyPromotions() {
        //progres.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getSurveyPrommotions(AppPreferences.getUserId(ProgressView.this),link_id,user_id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<SurveyPromotions> promotions = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<SurveyPromotions>>() {
                        }.getType());
                msurveyPromotions = promotions;
                networkDistRoutes();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(ProgressView.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkDistRoutes() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getDistributionroutes(AppPreferences.getUserId(ProgressView.this),user_id);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<DistributionRoutes> routes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<DistributionRoutes>>() {
                        }.getType());
                mroutes = routes;
                ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
                Call<String> call1 = service1.getTourType(AppPreferences.getUserId(ProgressView.this));
                call1.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        ArrayList<TourTypes> tourTypes = new Gson().fromJson(response.body(),
                                new TypeToken<ArrayList<TourTypes>>() {
                                }.getType());
                        mTourtypes = tourTypes;
                        progres.dismiss();
                        if (mTourtypes.size()>0) {
                            for (DistributionRoutes routes : mroutes) {
                                int routeid=routes.getTourtypeid();
                                for (int i = 0; i < mTourtypes.size(); i++) {
                                    int tourid=mTourtypes.get(i).getId();
                                    if (routeid == tourid) {
                                        routes.setName(mTourtypes.get(i).getName());
                                        break;
                                    }
                                }
                            }
                            for (SurveyPromotions surveyPromotions :msurveyPromotions) {
                                for (int i = 0; i < mTourtypes.size(); i++) {
                                    if (surveyPromotions.getTourtypeid() == mTourtypes.get(i).getId()) {
                                        surveyPromotions.setNames(mTourtypes.get(i).getName());
                                        break;
                                    }
                                }
                            }
                            if(mroutes.size()>0){
                                Routes_Sp_Adapter adapter=new Routes_Sp_Adapter(ProgressView.this,mroutes);
                                sp_task.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                        progres.dismiss();
                        Toast.makeText(ProgressView.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progres.dismiss();
                Toast.makeText(ProgressView.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
            final int m = monthOfYear;
            //Log.d("first tvalue", "" + monthOfYear + dayOfMonth + year);
            SelectedDate = AppUtilities.getDateString(year, m, dayOfMonth);
            et_date.setText(AppUtilities.getDateString(year, m, dayOfMonth));
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
    private void currentdate() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        final int mm = calendar.get(Calendar.MONTH);
        final int m = mm;
        final int dd = calendar.get(Calendar.DAY_OF_MONTH);
        SelectedDate = AppUtilities.getDateString(yy, m, dd);
        et_date.setText(AppUtilities.getDateString(yy, m, dd));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng position = marker.getPosition(); //
        //Toast.makeText(ProgressView.this, "Lat " + position.latitude + " " + "Long " + position.longitude, Toast.LENGTH_SHORT).show();&& SelectedDate.equalsIgnoreCase(Surveydate)
        for (int i = 0; i < msurveyPromotions.size(); i++) {
            String Surveydate=AppUtilities.getDateString(msurveyPromotions.get(i).getDatetime());
            if (position.latitude == msurveyPromotions.get(i).getLatitude() && position.longitude==msurveyPromotions.get(i).getLongitude()) {
                String image=msurveyPromotions.get(i).getRetailerimage();
                showimage(image);
                break;
            }
        }
        return true;
    }

    public void showimage(String image){
        AlertDialog.Builder builder=new AlertDialog.Builder(ProgressView.this);
        View v1= LayoutInflater.from(ProgressView.this).inflate(R.layout.show_image,null);
        builder.setView(v1);
        builder.setCancelable(false);
        PhotoView photoView = (PhotoView) v1.findViewById(R.id.photo_view);
        TextView im_name= (TextView) v1.findViewById(R.id.displayname);
        Button close= (Button) v1.findViewById(R.id.popup_cancel);
        final AlertDialog dailog=builder.create();
        final ProgressDialog progres = new ProgressDialog(ProgressView.this);
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        //http://converbiz-bank-api.herokuapp.com/api/v1/containers/converbiz/download/PM.JPG
        Picasso.with(ProgressView.this).load("http://converbiz-bank-api.herokuapp.com/api/v1/containers/converbiz/download/"+image).into(photoView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                progres.dismiss();
            }

            @Override
            public void onError() {
                Toast.makeText(ProgressView.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        im_name.setText(image);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailog.dismiss();
            }
        });
        dailog.show();
    }


    private class FetchDetailsFromDbTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                RuntimeExceptionDao<Sales_Area, Integer> sales_areasDao = mDbHelper.getSales_AreaRuntimeDao();
                QueryBuilder<Sales_Area, Integer> sales_areaQueryBuilder = sales_areasDao.queryBuilder();
                sales_areaQueryBuilder.where().eq("zoneId", user_id);
                PreparedQuery<Sales_Area> preparedQuery1 = sales_areaQueryBuilder.prepare();
                msalesAreas = sales_areasDao.query(preparedQuery1);

                RuntimeExceptionDao<DistributionRoutes, Integer> distributionRoutesDao = mDbHelper.getDistributionRoutesRuntimeDao();
                QueryBuilder<DistributionRoutes, Integer> distributionRoutesQueryBuilder = distributionRoutesDao.queryBuilder();
                distributionRoutesQueryBuilder.where().eq("zoneId", user_id);
                PreparedQuery<DistributionRoutes> preparedQuery4 = distributionRoutesQueryBuilder.prepare();
                mroutes = distributionRoutesDao.query(preparedQuery4);

                RuntimeExceptionDao<TourTypes, Integer> tourTypesDao = mDbHelper.getTourTypesRuntimeDao();
                mTourtypes=tourTypesDao.queryForAll();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progres.dismiss();
            try {
                if(msalesAreas.size()>0){
                    SalesArea_Sp_Adapter areaSpAdapter=new SalesArea_Sp_Adapter(ProgressView.this,msalesAreas);
                    sp_link.setAdapter(areaSpAdapter);
                }
                if (mTourtypes.size()>0) {
                    for (DistributionRoutes routes : mroutes) {
                        int routeid=routes.getTourtypeid();
                        for (int i = 0; i < mTourtypes.size(); i++) {
                            int tourid=mTourtypes.get(i).getId();
                            if (routeid == tourid) {
                                routes.setName(mTourtypes.get(i).getName());
                                break;
                            }
                        }
                    }
                    for (SurveyPromotions surveyPromotions :msurveyPromotions) {
                        int surveypromotionid=surveyPromotions.getTourtypeid();
                        for (int i = 0; i < mTourtypes.size(); i++) {
                            int tourtypeid=mTourtypes.get(i).getId();
                            if (surveypromotionid==tourtypeid) {
                                surveyPromotions.setNames(mTourtypes.get(i).getName());
                                break;
                            }
                        }
                    }
                    if(mroutes.size()>0){
                        Routes_Sp_Adapter adapter=new Routes_Sp_Adapter(ProgressView.this,mroutes);
                        sp_task.setAdapter(adapter);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
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
