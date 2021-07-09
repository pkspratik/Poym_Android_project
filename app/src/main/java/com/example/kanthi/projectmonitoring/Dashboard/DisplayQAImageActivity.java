package com.example.kanthi.projectmonitoring.Dashboard;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.ProgressView.ProgressView;
import com.example.kanthi.projectmonitoring.R;
import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.example.kanthi.projectmonitoring.Utils.AppUtilities;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DisplayQAImageActivity extends AppCompatActivity {
    private PhotoView display_image,display_image2;
    private CheckBox cb_image_approved;
    private Button capture_image;
    String imagename;
    private File file;
    private String imagepath;
    private String qaimage,qaimage2,qatime;

    public static final int REQUEST_IMAGE_CAPTURE=1;
    private Uri picUri;
    private File picFile;
    public String imagePath;
    private static final float maxHeight = 920.0f;
    private static final float maxWidth = 920.0f;
    ProgressDialog mObjDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qaimage);
        getSupportActionBar().setTitle("QA Image");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        imagePath = createFile();

        mObjDialog = new ProgressDialog(DisplayQAImageActivity.this);
        mObjDialog.setMessage("Saving Image,Please Wait..");
        mObjDialog.setIndeterminate(true);
        mObjDialog.setCancelable(false);

        display_image=findViewById(R.id.display_qaimage);
        display_image2=findViewById(R.id.display_qaimage2);
        capture_image=findViewById(R.id.bt_captureqaimage);
        cb_image_approved=findViewById(R.id.cb_image_approved);
        AppPreferences.setImageApprovedFlag(DisplayQAImageActivity.this,null);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        qaimage=bundle.getString("qaimagename");
        qaimage2=bundle.getString("qaimagename2");
        qatime=bundle.getString("qatime");
        if(qatime!=null){
            cb_image_approved.setChecked(true);
            //cb_image_approved.setEnabled(false);
            AppPreferences.setPrefCaptureImage(DisplayQAImageActivity.this,qaimage);
        }
        if(qaimage2!=null){
            display_image2.setVisibility(View.VISIBLE);
            final ProgressDialog progres = new ProgressDialog(DisplayQAImageActivity.this);
            progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progres.setMessage("Loading,Plz Wait...");
            progres.setIndeterminate(true);
            progres.setCancelable(false);
            progres.show();
            //http://converbiz-bank-api.herokuapp.com/api/v1/containers/converbiz/download/PM.JPG
            Picasso.with(DisplayQAImageActivity.this).load("http://idcamp-api.herokuapp.com/api/v1/containers/converbiz/download/"+qaimage2).into(display_image2, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    progres.dismiss();
                }

                @Override
                public void onError() {
                    progres.dismiss();
                    Toast.makeText(DisplayQAImageActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
                }
            });
        }
        final ProgressDialog progres = new ProgressDialog(DisplayQAImageActivity.this);
        progres.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progres.setMessage("Loading,Plz Wait...");
        progres.setIndeterminate(true);
        progres.setCancelable(false);
        progres.show();
        Log.d("qaimage",""+qaimage);
        //http://converbiz-bank-api.herokuapp.com/api/v1/containers/converbiz/download/PM.JPG
        Picasso.with(DisplayQAImageActivity.this).load("http://idcamp-api.herokuapp.com/api/v1/containers/converbiz/download/"+qaimage).into(display_image, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                progres.dismiss();
            }

            @Override
            public void onError() {
                progres.dismiss();
                //Toast.makeText(DisplayQAImageActivity.this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            }
        });
        cb_image_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    AppPreferences.setImageApprovedFlag(DisplayQAImageActivity.this,AppUtilities.getDateTime());
                    AppPreferences.setPrefCaptureImage(DisplayQAImageActivity.this,qaimage);
                    //Toast.makeText(DisplayQAImageActivity.this, ""+cb_image_approved.isChecked(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        capture_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*imagename="Ptm"+String.valueOf(AppUtilities.getDateandTimeString())+".jpg";
                Intent in =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagefolder=new File(Environment.getExternalStorageDirectory(),"Project Monitoring");
                imagefolder.mkdirs();
                file=new File(imagefolder,imagename);
                imagepath=file.getPath();
                Uri outputfile = Uri.fromFile(file);
                in.putExtra(MediaStore.EXTRA_OUTPUT,outputfile);
                startActivityForResult(in,1000);*/
                if (ContextCompat.checkSelfPermission(DisplayQAImageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DisplayQAImageActivity.this,
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
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(requestCode==1000){
            if(resultCode==RESULT_OK){
                String imagePath = file.getPath();
                Intent in=new Intent(DisplayQAImageActivity.this,CameraActivity.class);
                in.putExtra("imagepath",imagePath);
                in.putExtra("imagename",imagename);
                startActivity(in);
                finish();
            }
        }*/
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            mObjDialog.show();
            new ImageCompression().execute(imagePath);
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
            Intent in=new Intent(DisplayQAImageActivity.this,CameraActivity.class);
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

}
