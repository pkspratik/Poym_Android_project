package com.example.kanthi.projectmonitoring.Dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.kanthi.projectmonitoring.Network.RetrofitHelper;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.kanthi.projectmonitoring.R.id.ok_popup;

/**
 * Created by Kanthi on 3/19/2018.
 */

public class CaptureImage_Popup  extends DialogFragment {
    Dialog d=null;
    FloatingActionButton take_pic,ok_pop,cancel_pop;
    private File file;
    private Button image_popup;
    private String imagepath;
    private static final int CAMERA_REQUEST = 1000;
    private ImageView imageView;
    String Str_WorkerImageUpload="";
    String filePath="";
    String imagename;
    ProgressDialog mObjDialog;
    int workerimagecount=0;
    long workerimagelength;
    Bitmap photo;
    Bitmap bitmap;
    public CaptureImage_Popup() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ab=new AlertDialog.Builder(getActivity());
        View v=getActivity().getLayoutInflater().inflate(R.layout.captureimage_popup,null);
        ab.setView(v);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        take_pic= (FloatingActionButton) v.findViewById(R.id.takepicture);
        imageView= (ImageView) v.findViewById(R.id.displayimage);
        ok_pop= (FloatingActionButton) v.findViewById(ok_popup);
        cancel_pop= (FloatingActionButton) v.findViewById(R.id.cancel_popup);
        d=ab.create();
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagename="Ptm"+String.valueOf(AppUtilities.getDateandTimeString())+".jpg";
                Log.d("imagename",imagename);
                Intent in =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagefolder=new File(Environment.getExternalStorageDirectory(),"Project Monitoring");
                imagefolder.mkdirs();
                file=new File(imagefolder,imagename);
                imagepath=file.getPath();
                Uri outputfile = Uri.fromFile(file);
                in.putExtra(MediaStore.EXTRA_OUTPUT,outputfile);
                startActivityForResult(in,1000);
            }
        });
        ok_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagename==null){
                    //ImageFileUploader imageFileUploader=new ImageFileUploader();
                    //imageFileUploader.execute("null");
                    Toast.makeText(getActivity(), "Capture the image", Toast.LENGTH_SHORT).show();
                    //d.dismiss();
                }else{
                    AppPreferences.setPrefCaptureImage(getActivity(),imagename);
                    ImageFileUploader imageFileUploader=new ImageFileUploader();
                    imageFileUploader.execute("null");
                    //Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    d.dismiss();
                }
            }
        });
        cancel_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        return d;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode==RESULT_OK){
                decodeImage(file.getPath());
                String filepath = file.getPath();
                ContentValues values=new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, FilenameUtils.getBaseName(filepath));
                values.put(MediaStore.Images.Media.DESCRIPTION,"ProjectMonitorImages");
                values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
                values.put("_data",filepath);
                getImageContentUri(file);
            }
        }
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
        Cursor cursor = getActivity().getContentResolver().query(
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
                return getActivity().getContentResolver().insert(
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
                Toast.makeText(getActivity(), "camera permissions required", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class ImageFileUploader extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            mObjDialog = new ProgressDialog(getActivity());
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
            AmazonS3Client mClient = new AmazonS3Client(new
                    BasicAWSCredentials("AKIAIXDFMQH745IHQPEA",
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
                    if
                            (upload.isDone() == true) {
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
                } else {
                    Toast toast = Toast
                            .makeText(
                                    getActivity(),
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
}

