package com.example.kanthi.projectmonitoring.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kanthi.projectmonitoring.Dashboard.YoutubeActivity;
import com.example.kanthi.projectmonitoring.PoJo.Documents;
import com.example.kanthi.projectmonitoring.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanthi on 4/25/2018.
 */

public class Execution_Document_Rv_Adapter extends RecyclerView.Adapter<Execution_Document_Rv_Adapter.ViewHolder> {

    private List<Documents> mdocuments;
    private Context mContext;
    public AppCompatActivity activity;
    PDFView pdfView;
    ProgressDialog mObjDialog;

    public Execution_Document_Rv_Adapter(List<Documents> promotions) {
        mdocuments = promotions;
    }


    @Override
    public Execution_Document_Rv_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_exe_document,
                parent, false);
        mContext = parent.getContext();
        return new Execution_Document_Rv_Adapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String filename=mdocuments.get(position).getFilename()==null?"":mdocuments.get(position).getFilename();
        //final String imagename=mdocuments.get(position).getDocname()==null?"":mdocuments.get(position).getDocname();
        final String type=mdocuments.get(position).getDocumenttype()==null?"":mdocuments.get(position).getDocumenttype();
        holder.name.setText(mdocuments.get(position).getTaskname()==null?"":mdocuments.get(position).getTaskname());
        holder.image.setText(mdocuments.get(position).getDocname()==null?"":mdocuments.get(position).getDocname());
        if(type.equalsIgnoreCase("video")){
            holder.imageView.setBackgroundResource(R.drawable.blue_play);
        }
        if(type.equalsIgnoreCase("image")) {
            holder.imageView.setBackgroundResource(R.drawable.blue_image);
        }
        if(type.equalsIgnoreCase("pdf")) {
            holder.imageView.setBackgroundResource(R.drawable.blue_pdf);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equalsIgnoreCase("video")) {
                    Intent intent=new Intent(mContext, YoutubeActivity.class);
                    intent.putExtra("video",filename);
                    mContext.startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    View v1 = LayoutInflater.from(mContext).inflate(R.layout.showimage_popup, null);
                    builder.setView(v1);
                    builder.setCancelable(false);
                    PhotoView photoView = (PhotoView) v1.findViewById(R.id.photo_view);
                    pdfView= (PDFView) v1.findViewById(R.id.pdfviewer);
                    TextView name= (TextView) v1.findViewById(R.id.displayname);
                    Button close = (Button) v1.findViewById(R.id.popup_cancel);
                    final AlertDialog dailog = builder.create();
                    if(type.equalsIgnoreCase("image")){
                        photoView.setVisibility(View.VISIBLE);
                        name.setText(filename);
                        Picasso.with(mContext).load("https://s3-ap-southeast-1.amazonaws.com/converbiz/" + filename).into(photoView);
                    }
                    if(type.equalsIgnoreCase("pdf")){
                        pdfView.setVisibility(View.VISIBLE);
                        pdfView.useBestQuality(true);
                        pdfView.setMaxZoom(1.75f);
                        //new Pdfviewer().execute("http://gahp.net/wp-content/uploads/2017/09/sample.pdf");
                        new Pdfviewer().execute("http://s3-ap-southeast-1.amazonaws.com/converbiz/"+filename);
                        name.setText(filename);
                    }
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dailog.dismiss();
                        }
                    });
                    dailog.show();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mdocuments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView image;
        ImageView imageView;
        LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            name= (TextView) view.findViewById(R.id.document_name);
            image= (TextView) view.findViewById(R.id.document_image);
            imageView= (ImageView) view.findViewById(R.id.showimage);
            layout= (LinearLayout) view.findViewById(R.id.document_layout);
        }
    }
    class Pdfviewer extends AsyncTask<String,Void,InputStream>{
        @Override
        protected void onPreExecute() {
            mObjDialog = new ProgressDialog(mContext);
            mObjDialog.setMessage("Loading data....");
            mObjDialog.setIndeterminate(true);
            mObjDialog.setCancelable(false);
            mObjDialog.show();
        }
        @Override
        protected InputStream doInBackground(String... params) {
            InputStream inputStream =null;
            try {
                URL url=new URL(params[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                if(connection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(connection.getInputStream());
                }
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }
        @Override
        protected void onPostExecute(InputStream inputStream) {
            mObjDialog.dismiss();
            pdfView.fromStream(inputStream).enableSwipe(true).swipeHorizontal(true).load();
        }
    }
}