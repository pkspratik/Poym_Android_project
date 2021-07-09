package com.example.kanthi.projectmonitoring.Dashboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.kanthi.projectmonitoring.R;

public class BaseActivity  {
    private AlertDialog dialog=null;
    private AlertDialog.Builder alertDialogBuilder;

    public void showProgressbar(Activity context,String msg){
        alertDialogBuilder=new AlertDialog.Builder(context);
        View v=context.getLayoutInflater().inflate(R.layout.progress_bar,null);
        alertDialogBuilder.setView(v);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setMessage(msg);
        dialog = alertDialogBuilder.create();
        try {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        } catch (NullPointerException e) {
            Log.e("exception", " dialog  " + e.getMessage());
        }
        dialog.show();
    }
    public void hideProgressbar(Context context){
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
