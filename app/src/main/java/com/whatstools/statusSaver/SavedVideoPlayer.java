package com.whatstools.statusSaver;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Video.Media;
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

public class SavedVideoPlayer extends AppCompatActivity implements OnClickListener {
    static ArrayList<String> savefiles = new ArrayList<>();
    FloatingActionButton btn_delete;
    FloatingActionButton btn_share;
    String filename;
    String imageFilePath;
    FloatingActionMenu materialDesignFAM;
    VideoView myVideoView;
    int position;
    int type;

    //Click Event of Fab Button
    private class btnFAMListner implements OnClickListener {
        public void onClick(View view) {
            if (SavedVideoPlayer.this.materialDesignFAM.isOpened()) {
                SavedVideoPlayer.this.materialDesignFAM.close(true);
            }
        }
    }

    //Dialog Yes Button Click event
    private class btnDialogYesListner implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Uri imageConvertToUri = Uri.parse(SavedVideoPlayer.this.imageFilePath);
            File fdelete = new File(imageConvertToUri.getPath());
            if (!fdelete.exists()) {
                return;
            }
            if (fdelete.delete()) {
                SavedVideoPlayer.this.addVideoGallery(new File(SavedVideoPlayer.this.imageFilePath));
                SavedVideoPlayer.this.AdsCount();
                SavedVideoPlayer.this.finish();
                Toast.makeText(SavedVideoPlayer.this, "Video Deleted Successfully....", Toast.LENGTH_SHORT).show();
                return;
            }
            System.out.println("file not Deleted :" + imageConvertToUri.getPath());
        }
    }

    //Dialog NO Button Click event
    private class btnDialogNoListner implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_video_player);
        this.materialDesignFAM = findViewById(R.id.material_design_android_floating_action_menu);
        this.btn_share = findViewById(R.id.share);
        this.btn_delete = findViewById(R.id.delete);
        this.btn_share.setOnClickListener(this);
        this.btn_delete.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.imageFilePath = extras.getString("Vplay");
            this.position = extras.getInt("position");
        }
        this.myVideoView = findViewById(R.id.myvideoview);
        this.myVideoView.setVideoPath(getViewSrc());
        this.myVideoView.requestFocus();
        this.myVideoView.start();
        setMediaController();
        this.type = getIntent().getExtras().getInt("type");
        this.materialDesignFAM.setOnClickListener(new btnFAMListner());
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

    public void onBackPressed() {
        Intent intent = new Intent(this, SavedStoriesActivity.class);
        intent.putExtra("callingactivity", "maincall");
        startActivity(intent);
        finish();
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
                return;
        }
    }

    private void AdsCount() {
        if (MainActivity.countAds >= 3) {
            LoadAds();
            MainActivity.countAds = 0;
            return;
        }
        MainActivity.countAds++;
    }

    private void LoadAds() {
        StartAppAd.showAd(this);
    }

    private void DeleteActivity() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure to delete the Video from your device's local storage?");
        alertDialog.setPositiveButton("YES", new btnDialogYesListner());
        alertDialog.setNegativeButton("NO", new btnDialogNoListner());
        alertDialog.show();
    }

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

    private void ShareActivity() {
        Uri imageConvertToUri = FileProvider.getUriForFile(this, "com.whatstools.provider", new File(this.imageFilePath));
        Intent shareIntent = new Intent();
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.putExtra("android.intent.extra.STREAM", imageConvertToUri);
        shareIntent.setType("video/*");
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }
}
