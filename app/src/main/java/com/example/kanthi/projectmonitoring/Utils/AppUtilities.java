package com.example.kanthi.projectmonitoring.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.Database.AvahanSqliteDbHelper;
import com.example.kanthi.projectmonitoring.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Kanthi on 21/2/18.
 */
public class AppUtilities {


    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }
    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static long getTimestampFromDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            Date parsedDate = dateFormat.parse(date);
            return parsedDate.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getTimestampFromOnlyDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date parsedDate = dateFormat.parse(date);
            return parsedDate.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getDateWithTimestamp(int year, int monthOfYear, int dayOfMonth) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDateString(int year, int monthOfYear, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        return dateFormat.format(calendar.getTime());
    }

    public static String getMonthYearString(int year, int monthOfYear, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yyyy", Locale.US);
        return dateFormat.format(calendar.getTime());
    }

    public static String getGMTconvert(String date1){
        String inputText = date1;
        DateFormat inputFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zz yyy");
        Date d = null;
        try {
            d = inputFormat.parse(inputText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        return outputFormat.format(d);
    }

    public static String getMonthYearString(String date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date parsedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(parsedDate.getTime());
            dateFormat = new SimpleDateFormat("MMMM-yyyy");
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getTimestampWithDate(String date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date parsedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(parsedDate.getTime());
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDateString(String date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date parsedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(parsedDate.getTime());
            dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDateWithTimeString(String date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date parsedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(parsedDate.getTime());
            dateFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getTimeString(String date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date parsedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(parsedDate.getTime());
            dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }
    public static String getDateandTimeString(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Calendar calendar = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("ddMMyyyyhhmmssSSS", Locale.US);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getOnlyDateString(String date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date parsedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(parsedDate.getTime());
            dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public static String getOnlyTimeString(String date){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date parsedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(parsedDate.getTime());
            dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDateTime1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDateandTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }

    public static void writeDbToSdCard(Context context) throws IOException {
        File sd = Environment.getExternalStorageDirectory();
        String DB_PATH = context.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
        if (sd.canWrite()) {
            String currentDBPath = AvahanSqliteDbHelper.DATABASE_NAME;
            String backupDBPath = "poymbackup.db";
            File currentDB = new File(DB_PATH, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        }
    }


    public static void errorDialog(Context context,String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_error, null);

        TextView txtViewMessage = (TextView)view.findViewById(R.id.messages);
        txtViewMessage.setText(Message);

        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.setView(view);
        builder.create();
        builder.show();
    }

    public static void infoDialog(Context context,String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_info, null);

        TextView txtViewMessage = (TextView)view.findViewById(R.id.messages);
        txtViewMessage.setText(Message);

        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.setView(view);
        builder.create();
        builder.show();
    }


    public static void warnDialog(Context context,String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_warning, null);

        TextView txtViewMessage = (TextView)view.findViewById(R.id.messages);
        txtViewMessage.setText(Message);

        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.setView(view);
        builder.create();
        builder.show();
    }


}
