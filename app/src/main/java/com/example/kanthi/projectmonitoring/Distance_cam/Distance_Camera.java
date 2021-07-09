package com.example.kanthi.projectmonitoring.Distance_cam;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Dashboard.ProjectMonitorActivity;
import com.example.kanthi.projectmonitoring.Distance_cam.Helper.CameraPreview;
import com.example.kanthi.projectmonitoring.Distance_cam.Helper.PreviewSurfaceView;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Distance_Camera extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PictureCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CAMERA_PERMISSION = 123;
    private Camera mCamera;
    private PreviewSurfaceView mPreviewView;
    private SurfaceHolder mSurfaceHolder;
    private int mCameraID;
    private ImageParameters mImageParameters;
    private CameraOrientationListener mOrientationListener;
    private static int PICTURE_SIZE_MAX_WIDTH = 1280;
    private static int PREVIEW_SIZE_MAX_WIDTH = 1080;
    private boolean mIsSafeToTakePhoto = false;
    private LocationManager manager;
    private LocationListener listener;

    private final String aspectRatio1_1 = "1:1";
    private final String aspectRatio4_3 = "4:3";
    private final String aspectRatio16_9 = "16:9";
    private String mAspectRatioValue = aspectRatio16_9;

    private TextView mEnterHeightTv;
    private TextView mMarkThisPointTv;
    private TextView mDistanceTv;
    private TextView mObjectHeightTv;

    private float[] mAccelerometerReading = new float[3];
    private float[] mMagnetometerReading = new float[3];

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];

    private String height = "150";
    private SensorManager mSensorManager;
    private SensorEventListener mySensorEventListener;
    private Sensor gsensor;
    private Sensor msensor;

    private Float initialPitchAngle;
    private Float groundAngle;
    private float objectTopEndAngle;

    private float mTempPitch;
    private float thetaAngle;
    private double distance;

    private boolean mInitialSelected;

    private float mLastUpdatedAngle;


    private ImageView mCompass;
    private TextView mCompassAngle;

    private String inputUnit;
    private String outputUnit;

    private float objectHeight;
    private ImageView mTargetIv;
    private boolean firstPointClicked;
    private boolean isFlashOn;
    private ImageView mFlashIv;
    private String mSavedInputUnit;

    private float tempHeight;
    private double lati;
    private double longi;


    private static double floatBearing = 0;

    private static GeomagneticField gmf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_camera);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initializeViews();
        userLocation();
        mFlashIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFlashOn) {
                    turnOffFlash();
                    mFlashIv.setImageResource(R.drawable.ic_flash_on_white_24dp);
                } else {
                    turnOnFlash();
                    mFlashIv.setImageResource(R.drawable.ic_flash_off_white_24dp);
                }
            }
        });
        mySensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                final float alpha = 0.8f;

                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                    mAccelerometerReading = event.values.clone();
//                    mAccelerometerReading[0] = alpha * mAccelerometerReading[0] + (1 - alpha)
//                            * event.values[0];
//                    mAccelerometerReading[1] = alpha * mAccelerometerReading[1] + (1 - alpha)*
//                            * event.values[1];
//                    mAccelerometerReading[2] = alpha * mAccelerometerReading[2] + (1 - alpha)
//                            * event.values[2];

                }

                if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    mMagnetometerReading = event.values.clone();
//                    mMagnetometerReading[0] = alpha * mMagnetometerReading[0] + (1 - alpha)
//                            * event.values[0];
//                    mMagnetometerReading[1] = alpha * mMagnetometerReading[1] + (1 - alpha)
//                            * event.values[1];
//                    mMagnetometerReading[2] = alpha * mMagnetometerReading[2] + (1 - alpha)
//                            * event.values[2];
                }


                if (SensorManager.getRotationMatrix(mRotationMatrix, new float[9],
                        mAccelerometerReading, mMagnetometerReading)) {
                    SensorManager.getOrientation(mRotationMatrix, mOrientationAngles);

                    floatBearing = mOrientationAngles[0];

                    // Convert from radians to degrees
                    floatBearing = Math.toDegrees(floatBearing); // degrees east of true
                    // north (180 to -180)

                    // Compensate for the difference between true north and magnetic north
                    if (gmf != null) floatBearing += gmf.getDeclination();

                    // adjust to 0-360
                    if (floatBearing < 0) floatBearing += 360;

                    mTempPitch = (float) Math.toDegrees(Math.acos(mRotationMatrix[8]));
//                    mTempPitch = (float) Math.toDegrees(mOrientationAngles[1]);

                    mCompass.setRotation((float) (360 - floatBearing));

//                    mObjectHeightTv.setText((int) t + " " + (int) Math.toDegrees(mOrientationAngles[1]) + " " + (int) Math.toDegrees(mOrientationAngles[2]));
                    mCompassAngle.setText("" + (int) mTempPitch);
                    if (firstPointClicked) {
                        if (mInitialSelected) {
                            if (Math.abs(mTempPitch - mLastUpdatedAngle) > 5) {
                                if (mTempPitch < 90) {

                                    tempHeight = (float) (Float.parseFloat(height) * (Math.abs((Math.tan(Math.toRadians(90 - mTempPitch)) / Math.tan(Math.toRadians(90 - groundAngle)) - 1))));
                                    if (inputUnit.equals(getString(R.string.centi_metre))) {
                                        tempHeight = convertCmToOther(0, tempHeight);
                                        tempHeight = (float) (Math.round((tempHeight) * 100.0) / 100.0);
                                        mObjectHeightTv.setText("" + tempHeight + " cm");
                                    } else if (inputUnit.equals(getString(R.string.metre))) {
                                        tempHeight = convertCmToOther(1, tempHeight);
                                        tempHeight = (float) (Math.round((tempHeight) * 100.0) / 100.0);
                                        mObjectHeightTv.setText("" + tempHeight + " m");
                                    } else if (inputUnit.equals(getString(R.string.inch))) {
                                        tempHeight = convertCmToOther(3, tempHeight);
                                        tempHeight = (float) (Math.round((tempHeight) * 100.0) / 100.0);
                                        mObjectHeightTv.setText("" + tempHeight + " in");
                                    } else {
                                        tempHeight = convertCmToOther(2, tempHeight);
                                        tempHeight = (float) (Math.round((tempHeight) * 100.0) / 100.0);
                                        mObjectHeightTv.setText("" + tempHeight + " ft");
                                    }

                                } else {
                                    tempHeight = (float) (Float.parseFloat(height) * (Math.abs((Math.tan(Math.toRadians(mTempPitch - 90)) / Math.tan(Math.toRadians(90 - groundAngle)) + 1))));
                                    if (inputUnit.equals(getString(R.string.centi_metre))) {
                                        tempHeight = convertCmToOther(0, tempHeight);
                                        tempHeight = (float) (Math.round((tempHeight) * 100.0) / 100.0);
                                        mObjectHeightTv.setText("" + tempHeight + " cm");
                                    } else if (inputUnit.equals(getString(R.string.metre))) {
                                        tempHeight = convertCmToOther(1, tempHeight);
                                        tempHeight = (float) (Math.round((tempHeight) * 100.0) / 100.0);
                                        mObjectHeightTv.setText("" + tempHeight + " m");
                                    } else if (inputUnit.equals(getString(R.string.inch))) {
                                        tempHeight = convertCmToOther(3, tempHeight);
                                        tempHeight = (float) (Math.round((tempHeight) * 100.0) / 100.0);
                                        mObjectHeightTv.setText("" + tempHeight + " in");
                                    } else {
                                        tempHeight = convertCmToOther(2, tempHeight);
                                        tempHeight = (float) (Math.round((tempHeight) * 100.0) / 100.0);
                                        mObjectHeightTv.setText("" + tempHeight + " ft");
                                    }
                                }
                                mLastUpdatedAngle = mTempPitch;
                            }
                        }
                    } else {
                        if (mTempPitch > 84 && mTempPitch < 96) {
                            mTargetIv.setImageResource(R.drawable.ic_square_targeting_interface_symbol);
                            mMarkThisPointTv.setTextColor(Color.parseColor("#e36c09"));
                            mMarkThisPointTv.setEnabled(true);
                        } else {
                            mTargetIv.setImageResource(R.drawable.ic_square_targeting_interface_symbol_white);
                            mMarkThisPointTv.setTextColor(Color.parseColor("#ffffff"));
                            mMarkThisPointTv.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        setMarkerListener();
        mCameraID = getBackCameraID();
        mImageParameters = new ImageParameters();
        mPreviewView = findViewById(R.id.camera_preview_view);

        mPreviewView.getHolder().addCallback(this);
        mPreviewView.setListener(new CameraPreview());

        final View topCoverView = findViewById(R.id.cover_top_view);

        mImageParameters.mIsPortrait =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        if (savedInstanceState == null) {
            ViewTreeObserver observer = mPreviewView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mImageParameters.mPreviewWidth = mPreviewView.getWidth();
                    mImageParameters.mPreviewHeight = mPreviewView.getHeight();
                    mImageParameters.mCoverWidth = mImageParameters.mCoverHeight
                            = mImageParameters.calculateCoverWidthHeight();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mPreviewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mPreviewView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
        } else {
            if (mImageParameters.isPortrait()) {
                topCoverView.getLayoutParams().height = mImageParameters.mCoverHeight;
            } else {
                topCoverView.getLayoutParams().width = mImageParameters.mCoverWidth;
            }
        }
    }

    private void turnOnFlash() {
        if (!isFlashOn) {
            if (mCamera == null || mCamera.getParameters() == null) {
                return;
            }

            Camera.Parameters params = mCamera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(params);
            mCamera.startPreview();
            isFlashOn = true;

            // changing button/switch image
        }
    }

    private void turnOffFlash() {
        if (isFlashOn) {
            if (mCamera == null || mCamera.getParameters() == null) {
                return;
            }
            Camera.Parameters params = mCamera.getParameters();

            params = mCamera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(params);
            isFlashOn = false;

        }
    }

    private void setMarkerListener() {
        mMarkThisPointTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (initialPitchAngle == null) {
                    initialPitchAngle = mTempPitch;
                    mMarkThisPointTv.setText("Start");
                    firstPointClicked = true;
                } else {
                    if (groundAngle == null) {
                        mInitialSelected = true;
                        groundAngle = mTempPitch;
                        thetaAngle = Math.abs(initialPitchAngle) - Math.abs(groundAngle);
                        distance = Float.parseFloat(height) / Math.tan(Math.toRadians(thetaAngle));
                        if (inputUnit.equals(getString(R.string.centi_metre))) {
                            distance = convertCmToOther(0, (float) distance);
                            distance = Math.round((distance) * 100.0) / 100.0;
                            mDistanceTv.setText(Math.abs(distance) + " cm");
                        } else if (inputUnit.equals(getString(R.string.metre))) {
                            distance = convertCmToOther(1, (float) distance);
                            distance = Math.round((distance) * 100.0) / 100.0;
                            mDistanceTv.setText(Math.abs(distance) + " m");
                        } else if (inputUnit.equals(getString(R.string.inch))) {
                            distance = convertCmToOther(3, (float) distance);
                            distance = Math.round((distance) * 100.0) / 100.0;
                            mDistanceTv.setText(Math.abs(distance) + " in");
                        } else {
                            distance = convertCmToOther(2, (float) distance);
                            distance = Math.round((distance) * 100.0) / 100.0;
                            mDistanceTv.setText(Math.abs(distance) + " ft");
                        }

                        mDistanceTv.setVisibility(View.VISIBLE);

                        mMarkThisPointTv.setText("End");
                    } else {
                        mInitialSelected = false;
                        objectTopEndAngle = mTempPitch;
                        if (objectTopEndAngle < 90) {
                            objectHeight = (float) (Float.parseFloat(height) * (Math.abs((Math.tan(Math.toRadians(90 - objectTopEndAngle)) / Math.tan(Math.toRadians(90 - groundAngle)) - 1))));

                            if (inputUnit.equals(getString(R.string.centi_metre))) {
                                objectHeight = convertCmToOther(0, objectHeight);
                                objectHeight = (float) (Math.round((objectHeight) * 100.0) / 100.0);
                                mObjectHeightTv.setText("" + objectHeight + " cm");
                            } else if (inputUnit.equals(getString(R.string.metre))) {
                                objectHeight = convertCmToOther(1, objectHeight);
                                objectHeight = (float) (Math.round((objectHeight) * 100.0) / 100.0);
                                mObjectHeightTv.setText("" + objectHeight + " m");
                            } else if (inputUnit.equals(getString(R.string.inch))) {
                                objectHeight = convertCmToOther(3, objectHeight);
                                objectHeight = (float) (Math.round((objectHeight) * 100.0) / 100.0);
                                mObjectHeightTv.setText("" + objectHeight + " in");
                            } else {
                                objectHeight = convertCmToOther(2, objectHeight);
                                objectHeight = (float) (Math.round((objectHeight) * 100.0) / 100.0);
                                mObjectHeightTv.setText("" + objectHeight + " ft");
                            }


                        } else {
                            objectHeight = (float) (Float.parseFloat(height) * (Math.abs((Math.tan(Math.toRadians(objectTopEndAngle - 90)) / Math.tan(Math.toRadians(90 - groundAngle)) + 1))));
                            if (inputUnit.equals(getString(R.string.centi_metre))) {
                                objectHeight = convertCmToOther(0, objectHeight);
                                objectHeight = (float) (Math.round((objectHeight) * 100.0) / 100.0);
                                mObjectHeightTv.setText("" + objectHeight + " cm");
                            } else if (inputUnit.equals(getString(R.string.metre))) {
                                objectHeight = convertCmToOther(1, objectHeight);
                                objectHeight = (float) (Math.round((objectHeight) * 100.0) / 100.0);
                                mObjectHeightTv.setText("" + objectHeight + " m");
                            } else if (inputUnit.equals(getString(R.string.inch))) {
                                objectHeight = convertCmToOther(3, objectHeight);
                                objectHeight = (float) (Math.round((objectHeight) * 100.0) / 100.0);
                                mObjectHeightTv.setText("" + objectHeight + " in");
                            } else {
                                objectHeight = convertCmToOther(2, objectHeight);
                                objectHeight = (float) (Math.round((objectHeight) * 100.0) / 100.0);
                                mObjectHeightTv.setText("" + objectHeight + " ft");
                            }

                        }
                        takePhoto();
                    }
                }
            }
        });
    }

    private float convertCmToOther(int i, float measure) {
        if (i == 0) {
            //cm to cm
            return measure;
        } else if (i == 1) {
            //cm to metre
            return measure / 100;
        } else if (i == 2) {
            //cm to feet
            return measure / 30;
        } else {
            return (float) (measure / 2.5);
        }
    }

    private float convertOthrrToCm(int i, float measure) {
        if (i == 0) {
            //cm to cm
            return measure;
        } else if (i == 1) {
            //metre to cm
            return measure * 100;
        } else if (i == 2) {
            //feet to cm
            return measure * 30;
        } else {
            return (float) (measure * 2.5);
        }
    }

    private void takePhoto() {
        if (mIsSafeToTakePhoto) {
            setSafeToTakePhoto(false);


            mOrientationListener.rememberOrientation();

            // Shutter callback occurs after the image is captured. This can
            // be used to trigger a sound to let the user know that image is taken
            Camera.ShutterCallback shutterCallback = null;

            // Raw callback occurs when the raw image data is available
            Camera.PictureCallback raw = null;

            // postView callback occurs when a scaled, fully processed
            // postView image is available.
            Camera.PictureCallback postView = null;

            // jpeg callback occurs when the compressed image is available
            mCamera.takePicture(shutterCallback, raw, this);

        }
    }



    private void initializeViews() {
        mDistanceTv = findViewById(R.id.distance_text);
        mFlashIv = findViewById(R.id.flash);
        mTargetIv = findViewById(R.id.target_center);
        mObjectHeightTv = findViewById(R.id.object_height);
        mCompassAngle = findViewById(R.id.compass_angle);
        mCompass = findViewById(R.id.compass);
        mEnterHeightTv = findViewById(R.id.enter_height);

        mDistanceTv = findViewById(R.id.distance_tv);
        mMarkThisPointTv = findViewById(R.id.mark_this_point);

        mEnterHeightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHeightDialog();
            }
        });
    }

    private void showHeightDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View heightDialog = LayoutInflater.from(this).inflate(R.layout.distance_dialog, null);
        final Spinner mInputUnitSpinner = heightDialog.findViewById(R.id.input_unit);
        builder.setView(heightDialog);

        ArrayAdapter<String> mInputAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.input_units));
        mInputUnitSpinner.setAdapter(mInputAdapter);
        String[] mInputArray = getResources().getStringArray(R.array.input_units);
        mSavedInputUnit = AppPreferences.getInputUnit(this);

        for (int i = 0; i < mInputArray.length; i++) {
            if (mInputArray[i].equals(mSavedInputUnit)) {
                mInputUnitSpinner.setSelection(i);
            }
        }
        final EditText mHeightEt = heightDialog.findViewById(R.id.height_et);
        Button mOkBtn = heightDialog.findViewById(R.id.save_btn);
        final Dialog dialog = builder.create();
        dialog.show();
        if (inputUnit.equals(getString(R.string.centi_metre))) {
            mHeightEt.setText("" + convertCmToOther(0, Float.parseFloat(height)));
        } else if (inputUnit.equals(getString(R.string.metre))) {
            mHeightEt.setText("" + convertCmToOther(1, Float.parseFloat(height)));
        } else if (inputUnit.equals(getString(R.string.inch))) {
            mHeightEt.setText("" + convertCmToOther(3, Float.parseFloat(height)));
        } else {
            mHeightEt.setText("" + convertCmToOther(2, Float.parseFloat(height)));
        }
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHeightEt.getText().toString().length() > 0) {
                    height = mHeightEt.getText().toString();
                    AppPreferences.setInputUnit(Distance_Camera.this, (String) mInputUnitSpinner.getSelectedItem());
                    inputUnit = AppPreferences.getInputUnit(Distance_Camera.this);

                    dialog.dismiss();

                    if (inputUnit.equals(getString(R.string.centi_metre))) {
                        mEnterHeightTv.setText("" + Float.parseFloat(height) + " cm");
                        height = "" + convertOthrrToCm(0, Float.parseFloat(height));
                    } else if (inputUnit.equals(getString(R.string.metre))) {
                        mEnterHeightTv.setText("" + Float.parseFloat(height) + " m");
                        height = "" + convertOthrrToCm(1, Float.parseFloat(height));
                    } else if (inputUnit.equals(getString(R.string.inch))) {
                        mEnterHeightTv.setText("" + Float.parseFloat(height) + " in");
                        height = "" + convertOthrrToCm(3, Float.parseFloat(height));
                    } else {
                        mEnterHeightTv.setText("" + Float.parseFloat(height) + " ft");
                        height = "" + convertOthrrToCm(2, Float.parseFloat(height));
                    }


                } else {
                    dialog.dismiss();
                }
            }
        });
    }

    private int getBackCameraID() {
        return Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mOrientationListener = new CameraOrientationListener(this);

        inputUnit = AppPreferences.getInputUnit(this);

        if (inputUnit.equals(getString(R.string.inch))) {
            mEnterHeightTv.setText(Float.valueOf(height) / 2.5 + " in");
        } else if (inputUnit.equals(getString(R.string.metre))) {
            mEnterHeightTv.setText(Float.valueOf(height) / 100 + " m");
        } else if (inputUnit.equals(getString(R.string.centi_metre))) {
            mEnterHeightTv.setText(Float.valueOf(height) + " cm");
        } else {
            mEnterHeightTv.setText(Float.valueOf(height) / 30 + " ft");
        }
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        msensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mOrientationListener.enable();
        mSensorManager.registerListener(mySensorEventListener, gsensor,
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mySensorEventListener, msensor,
                SensorManager.SENSOR_DELAY_GAME);
    }


    private void setSafeToTakePhoto(final boolean isSafeToTakePhoto) {
        mIsSafeToTakePhoto = isSafeToTakePhoto;
    }

    private void setCameraFocusReady(final boolean isFocusReady) {
        if (this.mPreviewView != null) {
//            mPreviewView.setIsFocusReady(isFocusReady);
        }
    }

    private void stopCameraPreview() {
        setSafeToTakePhoto(false);
        setCameraFocusReady(false);

        // Nulls out callbacks, stops face detection
        mCamera.stopPreview();
        mPreviewView.camPreview.setCamera(null);
    }

    private void getCamera(int cameraID) {
        try {
            mCamera = Camera.open(cameraID);
            mPreviewView.camPreview.setCamera(mCamera);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;

        getCamera(mCameraID);
        startCameraPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private void startCameraPreview() {
        determineDisplayOrientation(this, mCameraID, mCamera, mImageParameters);
        setupCamera();

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();

            setSafeToTakePhoto(true);
            setCameraFocusReady(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void determineDisplayOrientation(FragmentActivity activity, int mCameraID, Camera mCamera, ImageParameters mImageParameters) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraID, cameraInfo);

        // Clockwise rotation needed to align the window display to the natural position
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: {
                degrees = 0;
                break;
            }
            case Surface.ROTATION_90: {
                degrees = 90;
                break;
            }
            case Surface.ROTATION_180: {
                degrees = 180;
                break;
            }
            case Surface.ROTATION_270: {
                degrees = 270;
                break;
            }
        }

        int displayOrientation;

        // CameraInfo.Orientation is the angle relative to the natural position of the device
        // in clockwise rotation (angle that is rotated clockwise from the natural position)
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            // Orientation is angle of rotation when facing the camera for
            // the camera image to match the natural orientation of the device
            displayOrientation = (cameraInfo.orientation + degrees) % 360;
            displayOrientation = (360 - displayOrientation) % 360;
        } else {
            displayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
        }

        mImageParameters.mDisplayOrientation = displayOrientation;
        mImageParameters.mLayoutOrientation = degrees;

        mCamera.setDisplayOrientation(mImageParameters.mDisplayOrientation);
    }

    private void setupCamera() {
        // Never keep a global parameters

        Camera.Parameters parameters = mCamera.getParameters();

        Camera.Size bestPreviewSize = determineBestPreviewSize(parameters);
        Camera.Size bestPictureSize = determineBestPictureSize(parameters);

        parameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
        parameters.setPictureSize(bestPictureSize.width, bestPictureSize.height);

        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        // Lock in the changes
        mCamera.setParameters(parameters);
    }

    private Camera.Size determineBestPreviewSize(Camera.Parameters parameters) {
        PREVIEW_SIZE_MAX_WIDTH = AppUtilities.getScreenWidth(this);
        return determineBestSize(parameters.getSupportedPreviewSizes(), PREVIEW_SIZE_MAX_WIDTH);
    }

    private Camera.Size determineBestPictureSize(Camera.Parameters parameters) {
        PICTURE_SIZE_MAX_WIDTH = AppUtilities.getScreenWidth(this);
        return determineBestSize(parameters.getSupportedPictureSizes(), PICTURE_SIZE_MAX_WIDTH);
    }

    private Camera.Size determineBestSize(List<Camera.Size> sizes, int widthThreshold) {
        Camera.Size bestSize = null;
        Camera.Size size;
        int numOfSizes = sizes.size();
        for (int i = 0; i < numOfSizes; i++) {
            size = sizes.get(i);

            boolean isDesireRatio = false;
            if (mAspectRatioValue.equals(aspectRatio1_1)) {
                isDesireRatio = (size.width) == (size.height);
            } else if (mAspectRatioValue.equals(aspectRatio4_3)) {
                isDesireRatio = (size.width / 4) == (size.height / 3);
            } else if (mAspectRatioValue.equals(aspectRatio16_9)) {
                isDesireRatio = (size.width / 16) == (size.height / 9);
            }
            boolean isBetterSize = (bestSize == null) || size.width > bestSize.width;


            if (isDesireRatio && isBetterSize) {
                bestSize = size;
            }
        }

        if (bestSize == null) {
            return sizes.get(sizes.size() - 1);
        }

        return bestSize;
    }

    public int getPhotoRotation(CameraOrientationListener mOrientationListener, int mCameraID) {
        int rotation;
        int orientation = mOrientationListener.getRememberedNormalOrientation();
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraID, info);

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotation = (info.orientation - orientation + 360) % 360;
        } else {
            rotation = (info.orientation + orientation) % 360;
        }

        return rotation;
    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        ImageByteArray.imageArray = bytes;
        int rotation = getPhotoRotation(mOrientationListener, mCameraID);
        if (isFlashOn) {
            turnOffFlash();
        }
        if(objectHeight!=0 && distance!=0){
            AppPreferences.setImageHeight(Distance_Camera.this, String.valueOf(objectHeight));
            AppPreferences.setImageDistance(Distance_Camera.this, String.valueOf(distance));
            Toast.makeText(this, "Height & Distance Captured", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No Object Fount To Capture", Toast.LENGTH_SHORT).show();
        }
        /*startActivity(new Intent(Distance_Camera.this, ImageActivity.class)
                .putExtra(IntentExtras.HEIGHT, objectHeight)
                .putExtra(IntentExtras.DISTANCE, distance).putExtra(IntentExtras.ROTATION, rotation)
                .putExtra(IntentExtras.TIMESTAMP, System.currentTimeMillis())
                .putExtra(IntentExtras.LATITUDE, lati)
                .putExtra(IntentExtras.LONGITUDE, longi)
                .putExtra(IntentExtras.AZIMUTH, (float) (360-floatBearing))
        );*/
        finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        if (initialPitchAngle != null) {
            initialPitchAngle = null;
            groundAngle = null;
            mObjectHeightTv.setText("");
            mDistanceTv.setText("");
            mMarkThisPointTv.setText("Initialize");
            firstPointClicked = false;
            mInitialSelected = false;
        } else {
            super.onBackPressed();
        }
    }

    private void userLocation(){
        /*final ProgressDialog progress = new ProgressDialog(Distance_Camera.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Fetching location,Please Wait..");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();*/
        manager = (LocationManager) Distance_Camera.this.getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
                //progress.dismiss();
                /*if(lati!=0.0 && longi!=0.0){
                    Toast.makeText(Distance_Camera.this, "Location Captured", Toast.LENGTH_SHORT).show();
                }*/
                if (ActivityCompat.checkSelfPermission(Distance_Camera.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(Distance_Camera.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    Toast.makeText(Distance_Camera.this, "Network Unavaliable", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(Distance_Camera.this, "Fetching Location,Please Wait", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(Distance_Camera.this, "Please enable GPS", Toast.LENGTH_SHORT).show();
            }
        };
        if (ActivityCompat.checkSelfPermission(Distance_Camera.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Distance_Camera.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
    }
}
