package com.example.kanthi.projectmonitoring.Patroling;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Dashboard.Survey;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.Patrols;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.android.gms.location.LocationResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReceiver extends BroadcastReceiver {
    private Patrols patrols;
    public static final String ACTION_PROCESS_UPDATES =
                    "PROCESS_UPDATES";
    public static final String PROVIDERS_CHANGED =
            "android.location.PROVIDERS_CHANGED";

    AvahanSqliteDbHelper mDbHelper;

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action=intent.getAction();
        mDbHelper = OpenHelperManager.getHelper(context, AvahanSqliteDbHelper.class);
        if (ACTION_PROCESS_UPDATES.equals(action)) {
            LocationResult result = LocationResult.extractResult(intent);
            if (result != null) {
                onNewLocation(result.getLastLocation(), context);
            }
        }
    }
    private void onNewLocation(Location location, Context context) {

        int accuracy = (int) location.getAccuracy();
        final String lat = String.valueOf(location.getLatitude());
        final String lon = String.valueOf(location.getLongitude());

        if(lat!=null && lon!=null){
            patrols = new Patrols();
            patrols.setLinkid(AppPreferences.getUser_SalesAreaId(context));
            patrols.setLatitude(lat);
            patrols.setLongitude(lon);
            patrols.setUserid(Integer.valueOf(AppPreferences.getLoggedUserName(context)));
            patrols.setProjectid(AppPreferences.getZoneId(context));
            patrols.setDatetime(AppUtilities.getDateandTime());
            patrols.setInsertFlag(true);
            patrols.setId(System.currentTimeMillis());
            Log.e("geoaddress_latlon",""+lat+","+lon);

            try {
                RuntimeExceptionDao<Patrols,Integer> patrolsDao=mDbHelper.getPatrolsRuntimeDao();
                patrolsDao.create(patrols);
            }catch (Exception e){
                e.printStackTrace();
            }
            Toast.makeText(context, ""+lat+","+lon, Toast.LENGTH_SHORT).show();
            //Postpatrols(context);
        }

    }

    public void Postpatrols(final Context context) {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                getProjectMonitorNetworkService();
        Call<String> call;
        JsonObject benjson = new JsonObject();
        try {
            JsonParser parser = new JsonParser();
            benjson = parser.parse(new Gson().toJson(patrols, Patrols.class)).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        benjson.remove("id");
        call = service.insertPatrols(AppPreferences.getUserId(context), benjson);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    ///ur code////
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context,R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
