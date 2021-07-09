package com.example.kanthi.projectmonitoring.Dashboard;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.kanthi.projectmonitoring.Adapters.RouteSalesViewsAdapter;
import com.example.kanthi.projectmonitoring.Adapters.SubAreas_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Adapters.SubTask_Sp_Adapter;
import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.Network.ProjectMonitorNetworkServices;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.Network.ToStringConverterFactory;
import com.example.kanthi.projectmonitoring.PoJo.DistributionSubAreas;
import com.example.kanthi.projectmonitoring.PoJo.Remarks;
import com.example.kanthi.projectmonitoring.PoJo.RouteSalesViews;
import com.example.kanthi.projectmonitoring.PoJo.SubTaskTypes;
import com.example.kanthi.projectmonitoring.PoJo.Surveys;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity {
    private TextView ta_subtype,ta_subtasktype;
    private Spinner sp_subtype,sp_subtasktype;
    FloatingActionButton ok_pop,cancel_pop;
    private File file;
    private Button image_popup;
    private String imagepath;
    private static final int CAMERA_REQUEST = 1000;
    private ImageView imageView;
    private String subtype_name,subtasktype_name;
    private Integer subtype_id,subtasktype_id;
    String filePath="";
    String imagename;
    ProgressDialog mObjDialog;
    String Str_WorkerImageUpload="";
    int workerimagecount=0;
    long workerimagelength;
    Bitmap photo;
    LinearLayout subtype_layout,subtasktype_layout;
    Bitmap bitmap;
    private List<SubTaskTypes> msubtypes;
    private List<DistributionSubAreas> msubareas;
    ProgressDialog progress1;
    ProgressDialog progress2;

    ProgressDialog progress;
    private CoordinatorLayout coordinatorLayout;

    AvahanSqliteDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mDbHelper = OpenHelperManager.getHelper(this, AvahanSqliteDbHelper.class);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        getSupportActionBar().setTitle("Captured Image"+"-"+AppPreferences.getFieldDesignition(CameraActivity.this));
        ta_subtype= (TextView) findViewById(R.id.cam_subtype);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        ta_subtasktype=findViewById(R.id.cam_subtasktype);
        sp_subtype= (Spinner) findViewById(R.id.subtype_spinner);
        sp_subtasktype= (Spinner) findViewById(R.id.subtasktype_spinner);
        imageView= (ImageView) findViewById(R.id.displayimage);
        subtype_layout= (LinearLayout) findViewById(R.id.subtype_layout);
        subtasktype_layout= (LinearLayout) findViewById(R.id.subtasktype_layout);
        ok_pop= (FloatingActionButton) findViewById(R.id.ok_popup);
        cancel_pop= (FloatingActionButton) findViewById(R.id.cancel_popup);

        AppPreferences.setSubTaskId(CameraActivity.this,0);
        AppPreferences.setSubTaskType(CameraActivity.this,null);
        AppPreferences.setSubTypeId(CameraActivity.this,0);

        progress = new ProgressDialog(CameraActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading Data,Please Wait..");
        progress.setIndeterminate(true);
        progress.setCancelable(false);

        /*if(AppPreferences.getProjectType(CameraActivity.this).equalsIgnoreCase("static")){
            ta_subtype.setText("Sub Type :");
        }else if(AppPreferences.getProjectType(CameraActivity.this).equalsIgnoreCase("dynamic")){
            ta_subtype.setText("Sub Area :");
        }*/
        ta_subtasktype.setText(AppPreferences.getTaskName(CameraActivity.this)==null?"":AppPreferences.getTaskName(CameraActivity.this));
        if(AppPreferences.getGroupId(CameraActivity.this)==14||AppPreferences.getGroupId(CameraActivity.this)==23||AppPreferences.getGroupId(CameraActivity.this)==41||AppPreferences.getGroupId(CameraActivity.this)==17||AppPreferences.getGroupId(CameraActivity.this)==18){
            if(AppPreferences.getProjectType(CameraActivity.this).equalsIgnoreCase("static")){
                subtype_layout.setVisibility(View.VISIBLE);
                subtasktype_layout.setVisibility(View.VISIBLE);
                ta_subtype.setText("Sub Type :");
                //networkStaticDistriibutionSubAreaId();
                progress.show();
                new FetchDetailsFromDbTask().execute();
            }
            else if(AppPreferences.getProjectType(CameraActivity.this).equalsIgnoreCase("dynamic")){
                subtype_layout.setVisibility(View.VISIBLE);
                subtasktype_layout.setVisibility(View.VISIBLE);
                ta_subtype.setText("Sub Area :");
                progress.show();
                new FetchDetailsFromDbTask().execute();
                //networkDynamicDistributionSubAreaid();
            }
            sp_subtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i>0){
                        subtype_id = msubareas.get(sp_subtype.getSelectedItemPosition() - 1).getId();
                        subtype_name = msubareas.get(sp_subtype.getSelectedItemPosition() - 1).getName();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            sp_subtasktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position>0){
                        subtasktype_id = msubtypes.get(sp_subtasktype.getSelectedItemPosition() - 1).getId();
                        subtasktype_name = msubtypes.get(sp_subtasktype.getSelectedItemPosition() - 1).getName();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        Intent in=getIntent();
        Bundle b=in.getExtras();
        imagepath = b.getString("imagepath");
        if (imagepath != null){
            /*ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, FilenameUtils.getBaseName(imagepath));
            values.put(MediaStore.Images.Media.DESCRIPTION,
                    "Image capture by camera");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put("_data", imagepath);*/
            imageView.setImageBitmap(BitmapFactory.decodeFile(new File(imagepath).getAbsolutePath()));
            imagename = new File(imagepath).getName();
        }
        //Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
        /*File imagefolder=new File(Environment.getExternalStorageDirectory(),"Project Monitoring");
        imagefolder.mkdirs();
        file=new File(imagefolder,imagename);
        imagepath=file.getPath();
        Uri outputfile = Uri.fromFile(file);
        getImageContentUri(file);
        decodeImage(file.getPath());*/

        ok_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagename==null){
                    Toast.makeText(CameraActivity.this, "Capture the image", Toast.LENGTH_SHORT).show();
                }else{
                    AppPreferences.setPrefCaptureImage(CameraActivity.this,imagename);
                    AppPreferences.setQACaptureImage(CameraActivity.this,imagename);
                    /////for static & dynamic area id subtypeid 327
                    AppPreferences.setSubTypeId(CameraActivity.this,subtype_id==null?0:subtype_id);
                    /////for static & dynamic these are sub task type 154
                    AppPreferences.setSubTaskId(CameraActivity.this,subtasktype_id==null?0:subtasktype_id);
                    AppPreferences.setSubTaskType(CameraActivity.this,subtasktype_name==null?"":subtasktype_name);

                    //new ImageFileUploader().execute("null");
                    finish();
                    //Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void networkStaticDistriibutionSubAreaId() {
        progress1 = new ProgressDialog(CameraActivity.this);
        progress1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress1.setMessage("Loading..");
        progress1.setIndeterminate(true);
        progress1.setCancelable(false);
        progress1.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1=null;
        if(AppPreferences.getDistributionSubAreaId(CameraActivity.this).equalsIgnoreCase("null")){
            call1 = service1.getDistributionSubAreas(AppPreferences.getUserId(CameraActivity.this),AppPreferences.getDist_Area_Id(CameraActivity.this));
        }else{
            call1 = service1.getDistributionSubArea(AppPreferences.getUserId(CameraActivity.this),Integer.parseInt(AppPreferences.getDistributionSubAreaId(CameraActivity.this)));
        }if(call1!=null){
            call1.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<DistributionSubAreas> distributionSubAreas = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<DistributionSubAreas>>() {
                            }.getType());
                    msubareas = distributionSubAreas;
                    if(msubareas.size()>0){
                        SubAreas_Sp_Adapter areasSpAdapter=new SubAreas_Sp_Adapter(CameraActivity.this,msubareas);
                        sp_subtype.setAdapter(areasSpAdapter);
                    }
                    networkStaticSubTask();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    progress1.dismiss();
                    CallingSnackbar();
                }
            });
        }else {
            progress1.dismiss();
            Toast.makeText(CameraActivity.this, getString(R.string.check_groupid), Toast.LENGTH_SHORT).show();
        }
    }

    private void networkStaticSubTask() {
        ProjectMonitorNetworkServices service = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service.getSubTask(AppPreferences.getUserId(CameraActivity.this),AppPreferences.getTourTypeId(CameraActivity.this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<SubTaskTypes> subTaskTypes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<SubTaskTypes>>() {
                        }.getType());
                msubtypes = subTaskTypes;
                progress1.dismiss();
                if(msubtypes.size()>0){
                    SubTask_Sp_Adapter subTask_sp_adapter=new SubTask_Sp_Adapter(CameraActivity.this,msubtypes);
                    sp_subtasktype.setAdapter(subTask_sp_adapter);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress1.dismiss();
                CallingSnackbar();
            }
        });
    }

    private void networkDynamicDistributionSubAreaid() {
        progress2 = new ProgressDialog(CameraActivity.this);
        progress2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress2.setMessage("Loading Data,Please Wait..");
        progress2.setIndeterminate(true);
        progress2.setCancelable(false);
        progress2.show();
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1=null;
        if(AppPreferences.getDistributionSubAreaId(CameraActivity.this).equalsIgnoreCase("null")){
            call1 = service1.getDistributionSubAreas(AppPreferences.getUserId(CameraActivity.this),AppPreferences.getDist_Area_Id(CameraActivity.this));
        }else{
            call1 = service1.getDistributionSubArea(AppPreferences.getUserId(CameraActivity.this),Integer.parseInt(AppPreferences.getDistributionSubAreaId(CameraActivity.this)));
        }if(call1!=null){
            call1.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    ArrayList<DistributionSubAreas> distributionSubAreas = new Gson().fromJson(response.body(),
                            new TypeToken<ArrayList<DistributionSubAreas>>() {
                            }.getType());
                    msubareas = distributionSubAreas;
                    if(msubareas.size()>0){
                        SubAreas_Sp_Adapter areasSpAdapter=new SubAreas_Sp_Adapter(CameraActivity.this,msubareas);
                        sp_subtype.setAdapter(areasSpAdapter);
                    }
                    networkDynamicSubTask();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    progress2.dismiss();
                    CallingSnackbar();
                }
            });
        }else{
            progress2.dismiss();
            Toast.makeText(this, getString(R.string.check_groupid), Toast.LENGTH_SHORT).show();
        }

    }

    private void networkDynamicSubTask() {
        ProjectMonitorNetworkServices service1 = RetrofitHelper.getInstance().getProjectMonitorNetworkService();
        Call<String> call1 = service1.getSubTask(AppPreferences.getUserId(CameraActivity.this),AppPreferences.getTourTypeId(CameraActivity.this));
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ArrayList<SubTaskTypes> subTaskTypes = new Gson().fromJson(response.body(),
                        new TypeToken<ArrayList<SubTaskTypes>>() {
                        }.getType());
                msubtypes = subTaskTypes;
                progress2.dismiss();
                if(msubtypes.size()>0){
                    SubTask_Sp_Adapter subTask_sp_adapter=new SubTask_Sp_Adapter(CameraActivity.this,msubtypes);
                    sp_subtasktype.setAdapter(subTask_sp_adapter);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                progress2.dismiss();
                CallingSnackbar();
            }
        });
    }


    private void decodeImage(String path) {
        int targetW = 100;
        int targetH = 100;
        final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bitmap = BitmapFactory.decodeFile(path, bmOptions);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }
    public Uri getImageContentUri(File imageFile) {
        filePath = imageFile.getAbsolutePath();
        Cursor cursor = CameraActivity.this.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                /*if(!values.equals("")){
                    ImageFileUploader imageFileUploader=new ImageFileUploader();
                    imageFileUploader.execute("null");
                }*/
                return CameraActivity.this.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 33) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
            }
            else {
                // Your app will not have this permission. Turn off all functions
                Toast.makeText(CameraActivity.this, "camera permissions required", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class ImageFileUploader extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            mObjDialog = new ProgressDialog(CameraActivity.this);
            mObjDialog.setMessage("Uploading data....");
            mObjDialog.setIndeterminate(true);
            mObjDialog.setCancelable(false);
            mObjDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            TransferManager transferManager = new TransferManager(new
                    BasicAWSCredentials("AKIAIXDFMQH745IHQPEA",
                    "UTe4fsvOsTyK/RHgjIWBIRRItoxVRz9lb2BhZgNu"));

            AmazonS3Client mClient = new AmazonS3Client(new BasicAWSCredentials("AKIAIXDFMQH745IHQPEA",
                    "UTe4fsvOsTyK/RHgjIWBIRRItoxVRz9lb2BhZgNu"));
            List<S3ObjectSummary> summaries = mClient.listObjects("converbiz").getObjectSummaries();
            String[] keys = new String[summaries.size()];
            File uploadFile2 = new File(filePath);
            workerimagelength = uploadFile2.length();
            //workerimagelength = workerimagelength /1024;
            if (workerimagelength > 500) {
                workerimagecount = 1;
                Upload upload = transferManager.upload("converbiz", imagename, uploadFile2);
                try {
                    // Or you can block and wait for the upload to finish
                    upload.waitForCompletion();
                    if (upload.isDone() == true) {
                        Str_WorkerImageUpload = "Uploaded";
                    } else {
                        Str_WorkerImageUpload = "Not Uploaded";
                    }
                    System.out.println("Upload complete.");
                } catch (AmazonClientException amazonClientException) {
                    System.out.println("Unable to upload file, upload was aborted.");
                    amazonClientException.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(String result) {
            try {
                if (workerimagecount == 1) {
                    mObjDialog.dismiss();
                    finish();
                } else {
                    Toast toast = Toast
                            .makeText(
                                    CameraActivity.this,
                                    "Image Size is more than 500kb change your settings",
                                    Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    View view = toast.getView();
                    view.setBackgroundResource(android.R.color.holo_red_dark);
                    toast.show();
                    mObjDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void CallingSnackbar(){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, R.string.check_internet, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*if(AppPreferences.getProjectType(CameraActivity.this).equalsIgnoreCase("static")){
                            networkStaticDistriibutionSubAreaId();
                        }else if(AppPreferences.getProjectType(CameraActivity.this).equalsIgnoreCase("dynamic")){
                            networkDynamicDistributionSubAreaid();
                        }*/
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
            try{
                RuntimeExceptionDao<DistributionSubAreas, Integer> distributionSubAreasDoa = mDbHelper.getDistributionSubAreasRuntimeDao();
                QueryBuilder<DistributionSubAreas, Integer> distributionSubAreasQueryBuilder = distributionSubAreasDoa.queryBuilder();
                if(AppPreferences.getDistributionSubAreaId(CameraActivity.this).equalsIgnoreCase("null")){
                    distributionSubAreasQueryBuilder.where().eq("distributionAreaId", AppPreferences.getDist_Area_Id(CameraActivity.this));
                }else{
                    distributionSubAreasQueryBuilder.where().eq("id", Integer.parseInt(AppPreferences.getDistributionSubAreaId(CameraActivity.this)));
                }
                PreparedQuery<DistributionSubAreas> preparedQuery1 = distributionSubAreasQueryBuilder.prepare();
                msubareas = distributionSubAreasDoa.query(preparedQuery1);

                RuntimeExceptionDao<SubTaskTypes, Integer> subTaskTypesDao = mDbHelper.getSubTaskTypesRuntimeDao();
                QueryBuilder<SubTaskTypes, Integer> subTaskTypesQueryBuilder = subTaskTypesDao.queryBuilder();
                subTaskTypesQueryBuilder.where().eq("tasktypeid", AppPreferences.getTourTypeId(CameraActivity.this));
                PreparedQuery<SubTaskTypes> preparedQuery2 = subTaskTypesQueryBuilder.prepare();
                msubtypes = subTaskTypesDao.query(preparedQuery2);


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();
            Log.d("tourtype",""+AppPreferences.getTourTypeId(CameraActivity.this));
            if(msubareas.size()>0){
                SubAreas_Sp_Adapter areasSpAdapter=new SubAreas_Sp_Adapter(CameraActivity.this,msubareas);
                sp_subtype.setAdapter(areasSpAdapter);
            }
            if(msubtypes.size()>0){
                SubTask_Sp_Adapter subTask_sp_adapter=new SubTask_Sp_Adapter(CameraActivity.this,msubtypes);
                sp_subtasktype.setAdapter(subTask_sp_adapter);
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
