package com.whatstools.fackChat;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.R;

import java.io.IOException;

public class Calls extends AppCompatActivity {
    LinearLayout msg;
    LinearLayout mute;
    LinearLayout speaker;
    private Handler customHandler = new Handler();
    ImageView endCall;
    TextView Name;
    ImageView profileImages;
    String profilename;
    private long startTime = 0;
    TextView times;
    long timeInMilliseconds = 0;
    long timeSwapBuff = 0;
    private Runnable updateTimerThread = new updateTimeThreadListner();
    long updatedTime = 0;

    //Button Speaker Click listener method
    private class btnSpeakerListner implements OnClickListener {
        public void onClick(View v) {
        }
    }

    //Button Message Click listener method
    private class btnMsgListner implements OnClickListener {
        public void onClick(View v) {
        }
    }

    //Button Mute Click listener method
    private class btnMuteListner implements OnClickListener {
        public void onClick(View v) {
        }
    }

    //Button End Call Click listener method
    private class btnEndCallListner implements OnClickListener {
        public void onClick(View view) {
            Calls calls = Calls.this;
            calls.timeSwapBuff += Calls.this.timeInMilliseconds;
            Calls.this.customHandler.removeCallbacks(Calls.this.updateTimerThread);
            Calls.this.finish();
        }
    }

    //Call timer update method
    private class updateTimeThreadListner implements Runnable {
        @SuppressLint("DefaultLocale")
        public void run() {
            Calls.this.timeInMilliseconds = SystemClock.uptimeMillis() - Calls.this.startTime;
            Calls.this.updatedTime = Calls.this.timeSwapBuff + Calls.this.timeInMilliseconds;
            int secs = (int) (Calls.this.updatedTime / 1000);
            int mins = secs / 60;
            secs %= 60;
            Calls.this.times.setText("" + String.format("%02d", mins) + " : " + String.format("%02d", secs));
            Calls.this.customHandler.postDelayed(this, 0);
        }
    }

    //initial method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);
        this.Name = findViewById(R.id.txtname);
        this.times = findViewById(R.id.txtTime);
        this.profileImages = findViewById(R.id.rlProfile);
        this.endCall = findViewById(R.id.imEndCall);
        this.speaker = findViewById(R.id.speaker);
        this.msg = findViewById(R.id.msg);
        this.mute = findViewById(R.id.mute);
        this.speaker.setOnClickListener(new btnSpeakerListner());
        this.msg.setOnClickListener(new btnMsgListner());
        this.mute.setOnClickListener(new btnMuteListner());
        if (getIntent().getExtras().getInt("ID") == 1) {
            this.profilename = getIntent().getExtras().getString("NAME");
            if (CallsFragment.selectedImageUri != null) {
                Bitmap mBitmap = null;
                try {
                    mBitmap = Media.getBitmap(getContentResolver(), CallsFragment.selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.profileImages.setBackground(new BitmapDrawable(getResources(), mBitmap));
            } else {
                this.profileImages.setBackground(getResources().getDrawable(R.drawable.images));
            }
        } else {
            this.profilename = getIntent().getExtras().getString("NAME");
            if (UserChat.callImages != null) {
                this.profileImages.setBackground(new BitmapDrawable(getResources(), UserChat.callImages));
            } else {
                this.profileImages.setBackground(getResources().getDrawable(R.drawable.images));
            }
        }
        this.Name.setText(this.profilename);
        this.startTime = SystemClock.uptimeMillis();
        this.customHandler.postDelayed(this.updateTimerThread, 0);
        this.endCall.setOnClickListener(new btnEndCallListner());
    }

    //Back press method
    public void onBackPressed() {
        super.onBackPressed();
        StartAppAd.onBackPressed(this);
        finish();
    }
}
