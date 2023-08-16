package com.whatstools.gallery;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Video.Media;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.MainActivity;
import com.whatstools.R;

import java.io.File;
import java.util.ArrayList;

public class VideoPlayersGallery extends AppCompatActivity implements OnClickListener {
    static ArrayList<String> savefiles = new ArrayList<>();
    FloatingActionButton btn_delete;
    FloatingActionButton btn_share;
    String filename;
    FloatingActionMenu materialDesignFAM;
    VideoView myVideoView;
    int type;


    //Click event of Button Fab
    private class btnFamListner implements OnClickListener {
        public void onClick(View view) {
            if (VideoPlayersGallery.this.materialDesignFAM.isOpened()) {
                VideoPlayersGallery.this.materialDesignFAM.close(true);
            }
        }
    }

    //Dialog of YES dialog
    private class dialogYesListner implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            File fdelete = new File(new File(Environment.getExternalStorageDirectory().toString() + "/Whatsapp/Media/Whatsapp Video"), VideoPlayersGallery.this.filename);
            if (!fdelete.exists()) {
                return;
            }
            if (fdelete.delete()) {
                VideoPlayersGallery.this.addVideoGallery(new File(VideoPlayersGallery.this.filename));
                Toast.makeText(VideoPlayersGallery.this, "Video Deleted Successfully....", Toast.LENGTH_SHORT).show();
                VideoPlayersGallery.this.AdsCount();
                VideoPlayersGallery.this.finish();
                return;
            }
            Toast.makeText(VideoPlayersGallery.this, "Some problems..", Toast.LENGTH_SHORT).show();
        }
    }
    //Dialog of NO dialog
    private class dialogNoListner implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getSupportActionBar().setTitle("Video");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.materialDesignFAM = findViewById(R.id.material_design_android_floating_action_menu);
        this.btn_share = findViewById(R.id.share);
        this.myVideoView = findViewById(R.id.myvideoview);
        this.btn_delete = findViewById(R.id.delete);
        this.btn_share.setOnClickListener(this);
        this.btn_delete.setOnClickListener(this);
        this.type = getIntent().getExtras().getInt("type");
        this.materialDesignFAM.setOnClickListener(new btnFamListner());
        this.myVideoView.setVideoPath(getViewSrc());
        this.myVideoView.requestFocus();
        this.myVideoView.start();
        setMediaController();
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:
                this.materialDesignFAM.close(true);
                DeleteActivity();
                return;
            case R.id.share:
                this.materialDesignFAM.close(true);
                ShareActivity();
                return;
            default:
        }
    }

    //Delete Activity Listner
    private void DeleteActivity() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure to delete the Video from your device's local storage?");
        alertDialog.setPositiveButton("YES", new dialogYesListner());
        alertDialog.setNegativeButton("NO", new dialogNoListner());
        alertDialog.show();
    }

    //Add Video into gallery Listener
    private void addVideoGallery(File targetLocation) {
        String[] projection = new String[]{"_id"};
        String[] selectionArgs = new String[]{targetLocation.getAbsolutePath()};
        Uri queryUri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor c = contentResolver.query(queryUri, projection, "_data = ?", selectionArgs, null);
        if (c.moveToFirst()) {
            contentResolver.delete(ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, c.getLong(c.getColumnIndexOrThrow("_id"))), null, null);
        }
        c.close();
    }

    //Share Activity Listener
    private void ShareActivity() {
        Uri uri = FileProvider.getUriForFile(this, "com.whatstools.provider", new File(new File(Environment.getExternalStorageDirectory().toString() + "/Whatsapp/Media/Whatsapp Video"), this.filename));
        Intent shareIntent = new Intent();
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.putExtra("android.intent.extra.STREAM", uri);
        shareIntent.setType("video/*");
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public boolean onSupportNavigateUp() {
        finish();
        AdsCount();
        return true;
    }


    //Ads Count Method
    private void AdsCount() {
        if (MainActivity.countAds >= 6) {
            LoadAds();
            MainActivity.countAds = 0;
            return;
        }
        MainActivity.countAds++;
    }

    private void LoadAds() {
        StartAppAd.showAd(this);
    }
}
