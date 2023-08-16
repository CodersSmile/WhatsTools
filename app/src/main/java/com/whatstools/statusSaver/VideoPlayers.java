package com.whatstools.statusSaver;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Video.Media;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.whatstools.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class VideoPlayers extends AppCompatActivity {
    static ArrayList<String> savefiles = new ArrayList<>();
    String filename;
    File imageRoot = new File(Environment.getExternalStorageDirectory() + "/Whatsapp/Media/Whatsapp Video/");
    File imageRoot1 = new File(Environment.getExternalStorageDirectory() + "/Whatsapp/Media/Whatsapp Video/");
    VideoView myVideoView;
    int type;


    //Method of Download Button click
    private class btnDownloadListner implements OnClickListener {
        public void onClick(View view) {
            try {
                VideoPlayers.this.copyDirectory(VideoPlayers.this.imageRoot, VideoPlayers.this.imageRoot1);
                Toast.makeText(VideoPlayers.this.getApplicationContext(), "Successfully downloaded", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(VideoPlayers.this.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getSupportActionBar().setTitle("Video");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.myVideoView = findViewById(R.id.myvideoview);
        FloatingActionButton download = findViewById(R.id.download);
        this.myVideoView.setVideoPath(getViewSrc());
        this.myVideoView.requestFocus();
        this.myVideoView.start();
        setMediaController();
        this.type = getIntent().getExtras().getInt("type");
        download.setOnClickListener(new btnDownloadListner());
    }

    private void setMediaController() {
        this.myVideoView.setMediaController(new MediaController(this));
    }

    private String getViewSrc() {
        savefiles.clear();
        this.filename = getIntent().getExtras().getString("Vplay").substring(getIntent().getExtras().getString("Vplay").lastIndexOf("/") + 1);
        savefiles.add(this.filename);
        return getIntent().getExtras().getString("Vplay");
    }

    public void copyDirectory(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdirs();
            }
            for (int i = 0; i < savefiles.size(); i++) {
                copyDirectory(new File(sourceLocation, savefiles.get(i)), new File(targetLocation, savefiles.get(i)));
            }
            return;
        }
        InputStream in = new FileInputStream(sourceLocation);
        OutputStream out = new FileOutputStream(targetLocation);
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                addImageGallery(targetLocation);
                return;
            }
        }
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void addImageGallery(File targetLocation) {
        ContentValues values = new ContentValues();
        values.put("title", getString(R.string.picture_title));
        values.put("description", getString(R.string.picture_description));
        values.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        values.put("bucket_id", Integer.valueOf(targetLocation.toString().toLowerCase(Locale.US).hashCode()));
        values.put("bucket_display_name", targetLocation.getName().toLowerCase(Locale.US));
        values.put("_data", targetLocation.getAbsolutePath());
        getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
    }
}
