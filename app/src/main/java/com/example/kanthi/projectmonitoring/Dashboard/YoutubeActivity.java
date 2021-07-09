package com.example.kanthi.projectmonitoring.Dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.kanthi.projectmonitoring.R;

public class YoutubeActivity extends AppCompatActivity {
    private VideoView playerview;
    private MediaController mediaController;
    private String videoname=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        getSupportActionBar().setTitle("Video Player");
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        videoname=bundle.getString("video");
        playerview= (VideoView) findViewById(R.id.video_view);
        mediaController=new MediaController(YoutubeActivity.this);
        if(videoname!=null){
            video(videoname);
        }else{
            Toast.makeText(this, "No Video", Toast.LENGTH_SHORT).show();
        }
        //playerView.initialize("AIzaSyDI4-wyDZhAsQTkOzfdSJOiZUlQ7gSX78s",this);
    }

    private void video(String videoname) {
        String videopath="http://s3-ap-southeast-1.amazonaws.com/converbiz/"+videoname;
        Uri uri=Uri.parse(videopath);
        playerview.setVideoURI(uri);
        playerview.setMediaController(mediaController);
        mediaController.setAnchorView(playerview);
        playerview.start();
    }

}
