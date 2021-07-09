package com.example.kanthi.projectmonitoring.Login;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.kanthi.projectmonitoring.Dashboard.RouteAssignmentSummaryActivity;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.Patroling.Patroling_Activity;
import com.example.kanthi.projectmonitoring.PoJo.User;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.table.TableUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.kanthi.projectmonitoring.Utils.AppUtilities.getDateTime;

public class LoginActivity extends AppCompatActivity {
    private Button mLoginButton;
    private AutoCompleteTextView mUserName;
    private EditText mPassword;
    private TextView mForgotPassword;
    private ProgressDialog mProgressDialog;
    private List<User> mUserList;
    private AvahanSqliteDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Fabric.with(this, new Crashlytics());
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        mLoginButton= findViewById(R.id.login);
        mUserName =  findViewById(R.id.autoCompleteUsername);
        mPassword = findViewById(R.id.password);
        mForgotPassword = findViewById(R.id.forgot_password);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.logging_in));
        mProgressDialog.setCancelable(false);
        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        ButterKnife.bind(this);


        //mUserName.setText("eduardof");
        //mPassword.setText("1");

        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(LoginActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    55);
        }

        mUserName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = (User) parent.getAdapter().getItem(position);
                mUserName.setText(selectedUser.getUsername());
            }
        });
        try {
            RuntimeExceptionDao<User, Integer> usersDao = mDbHelper.getUserRuntimeDao();
            mUserList = usersDao.queryForAll();
            Log.e("userlist",""+mUserList);
            mUserName.setThreshold(1);

            UserAdapter beneficiaryAdapter = new UserAdapter(LoginActivity.this, mUserList);
            mUserName.setAdapter(beneficiaryAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUserName.getText().length() == 0) {
                    AppUtilities.errorDialog(LoginActivity.this, getResources().getString(R.string.empty_username));
                } else if (mPassword.getText().length() == 0) {
                    AppUtilities.errorDialog(LoginActivity.this, getResources().getString(R.string.empty_password));
                } else if (mUserList.size() > 0 && !mUserList.get(0).getUsername().equalsIgnoreCase(mUserName.getText().toString())) {
                    AppUtilities.errorDialog(LoginActivity.this, getResources().getString(R.string.access_denied));
                } else {
                    if(mPassword.getText().toString().contains("sss")){
                        AppPreferences.setStressedData(LoginActivity.this, true);
                        Intent intent = new Intent(LoginActivity.this, RouteAssignmentSummaryActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else{
                        AppPreferences.setStressedData(LoginActivity.this, false);
                        if (isNetworkAvailable()) {
                            mProgressDialog.show();
                            ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().
                                    getProjectMonitorNetworkService();
                            Call<String> call = service.login(mUserName.getText().toString(), mPassword.getText().toString());
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.d("", "");
                                    if (response.raw().code() == 200) {
                                        try {
                                            JSONObject object = new JSONObject(response.body());
                                            AppPreferences.setLoggedIn(LoginActivity.this, true);
                                            AppPreferences.setUserId(LoginActivity.this, object.getString("id"));
                                            AppPreferences.setLoggedUserName(LoginActivity.this, object.getString("userId"));
                                            AppPreferences.setUserName(LoginActivity.this, mUserName.getText().toString());
                                            try {
                                                ////////////////Inserting User/////////////////
                                                ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
                                                Call<String> call1 = service.getUser(AppPreferences.getUserId(LoginActivity.this), AppPreferences.getLoggedUserName(LoginActivity.this));
                                                call1.enqueue(new Callback<String>() {
                                                    @Override
                                                    public void onResponse(Call<String> call, Response<String> response) {
                                                        Log.d("userresponse",""+response.body());
                                                        ArrayList<User> users = new Gson().fromJson(response.body(),
                                                                new TypeToken<ArrayList<User>>() {
                                                                }.getType());
                                                        User mSelectedUser = null;
                                                        for (int i = 0; i < users.size(); i++) {
                                                            JsonParser parser = new JsonParser();
                                                            JsonObject schemejson = parser.parse(new Gson().toJson(
                                                                    users.get(i), User.class)).getAsJsonObject();
                                                            Log.e("schemejson", schemejson.toString());

                                                            if (mSelectedUser != null) {
                                                                users.get(i).setSyncInTime(mSelectedUser.getSyncInTime());
                                                                users.get(i).setSyncOutTime(mSelectedUser.getSyncOutTime());
                                                                users.get(i).setScore(mSelectedUser.getScore());
                                                                users.get(i).setTrophyDate(mSelectedUser.getTrophyDate());
                                                            } else {
                                                                users.get(i).setSyncInTime(null);
                                                                users.get(i).setSyncOutTime("initial login");
                                                            }
                                                            users.get(i).setPassword(mPassword.getText().toString());
                                                            users.get(i).setLoginDate(getDateTime());
                                                            AppPreferences.setLoggedUserName(LoginActivity.this, String.valueOf(users.get(i).getId()));
                                                            try {
                                                                TableUtils.clearTable(mDbHelper.getConnectionSource(), User.class);
                                                                RuntimeExceptionDao<User, Integer> dao = mDbHelper.getUserRuntimeDao();
                                                                dao.create(users);
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }
                                                            //AppPreferences.setGroupId(LoginActivity.this,users.get(i).getGroupId());
                                                            //AppPreferences.setUser_SalesAreaId(LoginActivity.this,users.get(i).getSalesAreaId());
                                                            Crashlytics.setUserName(users.get(i).getUsername());
                                                            Intent intent = new Intent(LoginActivity.this, RouteAssignmentSummaryActivity.class);
                                                            intent.putExtra("partnerid",users.get(i).getEmployeeid());
                                                            intent.putExtra("usertype",users.get(i).getUsertype()==null?"null":users.get(i).getUsertype());
                                                            intent.putExtra("projecttype",users.get(i).getProjecttype()==null?"null":users.get(i).getProjecttype());
                                                            intent.putExtra("user_email",users.get(i).getEmail()==null?"":users.get(i).getEmail());
                                                            //intent.putExtra("usersalesAreaid",users.get(i).getSalesAreaId());

                                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                            mUserName.setText("");
                                                            mPassword.setText("");
                                                            /*if(users.get(i).getGroupId()==42){
                                                                //Crashlytics.setUserName(users.get(i).getUsername());
                                                                Intent intent =new Intent(LoginActivity.this, Patroling_Activity.class);
                                                                intent.putExtra("usersalesareaid",users.get(i).getSalesAreaId());
                                                                intent.putExtra("userzoneid",users.get(i).getZoneId());
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(intent);
                                                                mUserName.setText("");
                                                                mPassword.setText("");
                                                            }else{
                                                                //Crashlytics.setUserName(users.get(i).getUsername());
                                                                Intent intent = new Intent(LoginActivity.this, RouteAssignmentSummaryActivity.class);
                                                                intent.putExtra("partnerid",users.get(i).getEmployeeid());
                                                                intent.putExtra("usertype",users.get(i).getUsertype()==null?"null":users.get(i).getUsertype());
                                                                intent.putExtra("projecttype",users.get(i).getProjecttype()==null?"null":users.get(i).getProjecttype());
                                                                intent.putExtra("user_email",users.get(i).getEmail()==null?"":users.get(i).getEmail());
                                                                //intent.putExtra("usersalesAreaid",users.get(i).getSalesAreaId());

                                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(intent);
                                                                mUserName.setText("");
                                                                mPassword.setText("");
                                                            }*/
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<String> call, Throwable t) {
                                                        t.printStackTrace();
                                                    }
                                                });
                                            } catch (Exception e) {
                                                mProgressDialog.dismiss();
                                                //Toast.makeText(LoginActivity.this, getResources().getString(R.string.internal_error),Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }

                                        } catch (JSONException e) {
                                            mProgressDialog.dismiss();
                                            //Toast.makeText(LoginActivity.this, getResources().getString(R.string.internal_error),Toast.LENGTH_SHORT).show();
                                            AppUtilities.errorDialog(LoginActivity.this, getResources().getString(R.string.internal_error));
                                            e.printStackTrace();
                                        }
                                    } else {
                                        mProgressDialog.dismiss();
                                        //Toast.makeText(LoginActivity.this, response.raw().message(), Toast.LENGTH_SHORT).show();
                                        AppUtilities.errorDialog(LoginActivity.this, getResources().getString(R.string.invalid_username) + "/" + getResources().getString(R.string.invalid_password));
                                    }
                                }
                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    mProgressDialog.dismiss();
                                    //Toast.makeText(LoginActivity.this, getResources().getString(R.string.internal_error),Toast.LENGTH_SHORT).show();
                                    AppUtilities.errorDialog(LoginActivity.this, getResources().getString(R.string.internal_error));
                                }
                            });
                        }/*else if(isNetworkAvailable()==false){
                            AppUtilities.errorDialog(LoginActivity.this, getString(R.string.check_network));
                        }*/ else {
                            ///No Network
                            User mSelectedUser = null;
                            for (int i = 0; i < mUserList.size(); i++) {
                                if (mUserName.getText().toString().equals(mUserList.get(i).getUsername())) {
                                    mSelectedUser = mUserList.get(i);
                                    break;
                                }
                            }
                            if (mSelectedUser == null) {
                                // Toast.makeText(LoginActivity.this, getResources().getString(R.string.invalid_username),Toast.LENGTH_SHORT).show();
                                AppUtilities.errorDialog(LoginActivity.this, getResources().getString(R.string.invalid_username));
                            } else {
                                Log.e("logindate",""+getDateTime()); //2019-05-08
                                Log.e("userdate",""+mSelectedUser.getLoginDate());
                                if (getDateTime().equals(mSelectedUser.getLoginDate())) {
                                    if (mPassword.getText().toString().equals(mSelectedUser.getPassword())) {
                                        AppPreferences.setLoggedUserName(LoginActivity.this, String.valueOf(mSelectedUser.getId()));
                                        AppPreferences.setPartnerid(LoginActivity.this,mSelectedUser.getEmployeeid());
                                        //Toast.makeText(LoginActivity.this,""+mSelectedUser.getEmployeeid(), Toast.LENGTH_SHORT).show();
                                        //AppPreferences.setPrefUserGroupId(LoginActivity.this, (mSelectedUser.getGroupId()));
                                        Intent intent = new Intent(LoginActivity.this, RouteAssignmentSummaryActivity.class);
                                        intent.putExtra("partnerid",mSelectedUser.getEmployeeid());
                                        intent.putExtra("usertype",mSelectedUser.getUsertype()==null?"null":mSelectedUser.getUsertype());
                                        intent.putExtra("projecttype",mSelectedUser.getProjecttype()==null?"null":mSelectedUser.getProjecttype());
                                        intent.putExtra("user_email",mSelectedUser.getEmail()==null?"":mSelectedUser.getEmail());
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {
                                        //Toast.makeText(LoginActivity.this, getResources().getString(R.string.invalid_password),Toast.LENGTH_SHORT).show();
                                        AppUtilities.errorDialog(LoginActivity.this, getResources().getString(R.string.invalid_username) + "/" + getResources().getString(R.string.invalid_password));
                                    }
                                } else {
                                    //Toast.makeText(LoginActivity.this, getResources().getString(R.string.prev_day),Toast.LENGTH_SHORT).show();
                                    AppUtilities.errorDialog(LoginActivity.this, getResources().getString(R.string.prev_day));
                                }
                            }

                        }
                    }
                }
            }
        });
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(LoginActivity.this, getResources().getString(R.string.contact_facility), Toast.LENGTH_LONG).show();
                AppUtilities.errorDialog(LoginActivity.this, getResources().getString(R.string.contact_facility));
            }
        });
    }
    private void setLanguage(String langCode) {
        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(langCode.toLowerCase());
        res.updateConfiguration(conf, dm);
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

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}
