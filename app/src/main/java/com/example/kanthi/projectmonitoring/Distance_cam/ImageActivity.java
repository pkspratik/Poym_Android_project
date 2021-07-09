package com.example.kanthi.projectmonitoring.Distance_cam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.Distance_cam.Helper.DrawCircle;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


import static com.example.kanthi.projectmonitoring.Utils.AppUtilities.getScreenWidth;

public class ImageActivity extends AppCompatActivity {

    private Bitmap bitmap;

    private ImageView mImageIv;
    private ProgressBar mProgressBar;

    private Bitmap mutableBitmap;
    private DrawCircle drawCircle;

    private TextView mHeightTv;
    private TextView mDistanceTv;
    private TextView mTimeTv;

    //private Button mShareBtn;
    private RelativeLayout mMainLayout;

    private TextView mLocationTv;
    private TextView mTagsTv;
    private RelativeLayout mCompassLayout;
    private ImageView mCompassImage;
    private ImageView mNorthtext;

    private FrameLayout mProgressFrame;
    private TextView mVisionTags;

    private TextView mHeightLabel;
    private TextView mDistanceLabel;
    private TextView mTimeLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initializeViews();
        /*mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBackIv.setVisibility(View.GONE);
                mShareBtn.setVisibility(View.GONE);
                mMainLayout.setDrawingCacheEnabled(true);
                mMainLayout.buildDrawingCache();
                Bitmap shareBitmap = mMainLayout.getDrawingCache();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                shareBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                mMainLayout.setDrawingCacheEnabled(false);
                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
                try {
                    if (f.exists()) {
                        f.delete();
                    }
                    f.createNewFile();

                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse(f.getPath()));
                startActivity(Intent.createChooser(share, "Share Image"));
                mShareBtn.setVisibility(View.VISIBLE);
                mBackIv.setVisibility(View.VISIBLE);
            }
        });*/
        new ShowPhoto().execute();
    }


    private static String getTimeStamp(long time) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone tz = TimeZone.getDefault();

        format.setTimeZone(tz);
        String timestamp = "";

        try {
            Date date = new Date(time);
            java.text.SimpleDateFormat todayFormat = new java.text.SimpleDateFormat("dd");
            todayFormat.setTimeZone(tz);
            format = new java.text.SimpleDateFormat("hh:mm \na");
            String date1 = format.format(date);
            timestamp = date1.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }


    private void drawCircles(Uri photoUri) {

        mImageIv.setDrawingCacheEnabled(true);
        mImageIv.buildDrawingCache();

        mutableBitmap = mImageIv.getDrawingCache().copy(Bitmap.Config.ARGB_8888, true);
        mutableBitmap = drawCircle.drawCircleOn(mutableBitmap, this);
        mImageIv.setDrawingCacheEnabled(false);

        mImageIv.setImageBitmap(mutableBitmap);

        drawText();
        setLocationText();
        RelativeLayout.LayoutParams compassParams = new RelativeLayout.LayoutParams(getScreenWidth(this) / 10, getScreenWidth(this) / 10);
        RelativeLayout.LayoutParams compassRingParams = new RelativeLayout.LayoutParams(getScreenWidth(this) / 9, getScreenWidth(this) / 9);

        mCompassImage.setRotation(getIntent().getFloatExtra(IntentExtras.AZIMUTH, 0));
        mCompassLayout.setTranslationX(45);
        mCompassLayout.setTranslationY((AppUtilities.getScreenHeight(this) / 2) - 370);

        mCompassLayout.setVisibility(View.VISIBLE);
    }


    private void saveStory(long timeStamp) {
        Uri fileContentUri = null;

        try {
            File direct = new File(Environment.getExternalStorageDirectory() + File.separator + "PrakshepMeasure"
                    , "PrakshepMeasure"
            );
            if (!direct.exists()) {
                if (!direct.mkdirs()) {
                    return;
                }
            }

            File file = new File(direct.getPath() + File.separator + "IMG__" + timeStamp + ".jpg");

            if (file.exists()) {
                file.delete();
            }
            File file2 = null;

            FileOutputStream out = new FileOutputStream(file);

            mMainLayout.setDrawingCacheEnabled(true);
            mMainLayout.buildDrawingCache();
            mMainLayout.getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 100, out);
            mMainLayout.setDrawingCacheEnabled(false);

            out.flush();
            out.close();
            File file1 = null;
            //file1 = new Compressor(this).compressToFile(file);
            file.delete();
            file2 = new File(direct.getPath() + File.separator + "IMG__" + timeStamp + ".jpg");

            OutputStream out1 = null;
            out1 = new FileOutputStream(file2);
            FileInputStream fis = null;
            fis = new FileInputStream(file1);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
            }
            byte[] bytes = bos.toByteArray();
            out1.write(bytes);

            file1.delete();
            fileContentUri = Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //mProgressFrame.setVisibility(View.GONE);

    }

    private void setLocationText() {
        Geocoder geocoder = new Geocoder(this);
        ArrayList<Address> addresses = new ArrayList<>();
        try {
            addresses = (ArrayList<Address>) geocoder.getFromLocation(getIntent().getDoubleExtra(IntentExtras.LATITUDE, -1), getIntent().getDoubleExtra(IntentExtras.LONGITUDE, -1), 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null) {
            String location = "";
            if (addresses.size() > 0) {

                if (addresses.get(0).getSubLocality() != null) {
                    location += " ," + addresses.get(0).getSubLocality();
                }
                if (addresses.get(0).getLocality() != null) {
                    location += " ," + addresses.get(0).getLocality();
                }
                if (addresses.get(0).getAdminArea() != null) {
                    location += " ," + addresses.get(0).getAdminArea();
                }
                if (addresses.get(0).getPostalCode() != null) {
                    location += " ," + addresses.get(0).getCountryCode();
                }

                SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_month));
                String s = sdf.format(new Date(getIntent().getLongExtra(IntentExtras.TIMESTAMP, 0)));
                String finalLocation = "Image taken on " + s + " at " + location + "\n" + "Latitude: " + Math.round((getIntent().getDoubleExtra(IntentExtras.LATITUDE, -1)) * 100.0) / 100.0 + " , " + "Longitude: " + Math.round((getIntent().getDoubleExtra(IntentExtras.LONGITUDE, -1)) * 100.0) / 100.0;
                mLocationTv.setText(finalLocation);
            }
        }

        String tags = "Your object has a height of " + getIntent().getFloatExtra(IntentExtras.HEIGHT, -1) + " " + AppPreferences.getInputUnit(this) + " which is kept at a distance of " + getIntent().getDoubleExtra(IntentExtras.DISTANCE, -1) + " " + AppPreferences.getInputUnit(this);
        mTagsTv.setText(tags);
    }


    private void drawText() {
        RelativeLayout.LayoutParams satTextParams = new RelativeLayout.LayoutParams((getScreenWidth(this) / 4) - (getScreenWidth(this) / 12), getScreenWidth(this) / 6 - getScreenWidth(this) / 15);
        mHeightTv.setLayoutParams(satTextParams);
        mDistanceTv.setLayoutParams(satTextParams);
        mTimeTv.setLayoutParams(satTextParams);
        mTimeLabel.setLayoutParams(satTextParams);
        mHeightLabel.setLayoutParams(satTextParams);
        mDistanceLabel.setLayoutParams(satTextParams);

        mTimeTv.measure(0, 0);
        mTimeTv.setTranslationX(AppUtilities.getScreenWidth(this) / 12);
        mTimeTv.setTranslationY((AppUtilities.getScreenWidth(this) / 6) - (getScreenWidth(this) / 6 - getScreenWidth(this) / 15) / 2);

        mHeightTv.measure(0, 0);
        mHeightTv.setTranslationX(AppUtilities.getScreenWidth(this) / 12);
        mHeightTv.setTranslationY((AppUtilities.getScreenWidth(this) / 6) - (getScreenWidth(this) / 6 - getScreenWidth(this) / 15) / 2);

        mDistanceTv.measure(0, 0);
        mDistanceTv.setTranslationX((2 * AppUtilities.getScreenWidth(this) / 3) + AppUtilities.getScreenWidth(this) / 12);
        mDistanceTv.setTranslationY((AppUtilities.getScreenWidth(this) / 6) - (getScreenWidth(this) / 6 - getScreenWidth(this) / 15) / 2);

        mHeightTv.measure(0, 0);
        mHeightTv.setTranslationX(AppUtilities.getScreenWidth(this) / 3 + AppUtilities.getScreenWidth(this) / 12);
        mHeightTv.setTranslationY((AppUtilities.getScreenWidth(this) / 6) - (getScreenWidth(this) / 6 - getScreenWidth(this) / 15) / 2);

        mTimeLabel.measure(0,0);
        mTimeLabel.setTranslationX(AppUtilities.getScreenWidth(this)/12);
        mTimeLabel.setTranslationY(AppUtilities.getScreenWidth(this)/3-AppUtilities.getScreenWidth(this)/16);

        mHeightLabel.measure(0,0);
        mHeightLabel.setTranslationX(AppUtilities.getScreenWidth(this)/3+AppUtilities.getScreenWidth(this)/12);
        mHeightLabel.setTranslationY(AppUtilities.getScreenWidth(this)/3-AppUtilities.getScreenWidth(this)/16);



        mDistanceLabel.measure(0,0);
        mDistanceLabel.setTranslationX(2*(AppUtilities.getScreenWidth(this)/3)+AppUtilities.getScreenWidth(this)/12);
        mDistanceLabel.setTranslationY(AppUtilities.getScreenWidth(this)/3-AppUtilities.getScreenWidth(this)/16);

        mTimeLabel.setVisibility(View.VISIBLE);
        mHeightLabel.setVisibility(View.VISIBLE);
        mDistanceLabel.setVisibility(View.VISIBLE);
        long timeStamp = getIntent().getLongExtra(IntentExtras.TIMESTAMP, -1);
        mTimeTv.setText(getTimeStamp(timeStamp));

        if (AppPreferences.getInputUnit(this).equals(getString(R.string.inch))) {
            mHeightTv.setText(Math.abs(getIntent().getFloatExtra(IntentExtras.HEIGHT, -1)) + " in");
            mDistanceTv.setText(Math.abs(getIntent().getDoubleExtra(IntentExtras.DISTANCE, -1)) + " in");
        } else if (AppPreferences.getInputUnit(this).equals(getString(R.string.metre))) {
            mHeightTv.setText(Math.abs(getIntent().getFloatExtra(IntentExtras.HEIGHT, -1)) + " m");
            mDistanceTv.setText(Math.abs(getIntent().getDoubleExtra(IntentExtras.DISTANCE, -1)) + " m");
        } else if (AppPreferences.getInputUnit(this).equals(getString(R.string.centi_metre))) {
            mHeightTv.setText(Math.abs(getIntent().getFloatExtra(IntentExtras.HEIGHT, -1)) + " cm");
            mDistanceTv.setText(Math.abs(getIntent().getDoubleExtra(IntentExtras.DISTANCE, -1)) + " cm");
        } else {
            mHeightTv.setText(Math.abs(getIntent().getFloatExtra(IntentExtras.HEIGHT, -1)) + " ft");
            mDistanceTv.setText(Math.abs(getIntent().getDoubleExtra(IntentExtras.DISTANCE, -1)) + " ft");
        }

        mHeightTv.setVisibility(View.VISIBLE);
        mDistanceTv.setVisibility(View.VISIBLE);
        mTimeTv.setVisibility(View.VISIBLE);

    }

    private void initializeViews() {

        mTimeLabel=findViewById(R.id.time_label);
        mHeightLabel=findViewById(R.id.height_label);
        mDistanceLabel=findViewById(R.id.distance_label);
        mVisionTags = findViewById(R.id.vision_tags);
        mProgressFrame = findViewById(R.id.progress_frame);
        mNorthtext = findViewById(R.id.north_text);
        mCompassImage = findViewById(R.id.compass);
        mCompassLayout = findViewById(R.id.compass_layout);
        mLocationTv = findViewById(R.id.location);
        mTagsTv = findViewById(R.id.tags_tv);
        mTimeTv = findViewById(R.id.time_tv);
        mMainLayout = findViewById(R.id.main_layout);
        mHeightTv = findViewById(R.id.height_tv);
        mDistanceTv = findViewById(R.id.distance_tv);
        drawCircle = new DrawCircle();
        mProgressBar = findViewById(R.id.progress_bar);
        mImageIv = findViewById(R.id.image_iv);
    }

    private class ShowPhoto extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            int rotation = getIntent().getIntExtra(IntentExtras.ROTATION, -1);
            bitmap = BitmapFactory.decodeByteArray(ImageByteArray.imageArray, 0, ImageByteArray.imageArray.length);

            if (rotation != 0) {
                Bitmap oldBitmap = bitmap;

                Matrix matrix = new Matrix();
                matrix.postRotate(rotation);

                try {
                    bitmap = Bitmap.createBitmap(
                            oldBitmap, 0, 0, oldBitmap.getWidth(), oldBitmap.getHeight(), matrix, false
                    );
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mProgressBar.setVisibility(View.GONE);
            mImageIv.setImageBitmap(bitmap);
            drawCircles(null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ImageByteArray.imageArray = null;
    }

}
