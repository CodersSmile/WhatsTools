package com.whatstools.cleaner;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.startapp.android.publish.ads.banner.Banner;
import com.whatstools.Internetconnection;
import com.whatstools.R;

import java.io.File;
import java.lang.reflect.Method;

public class WACleanMainActivity extends AppCompatActivity {
    RelativeLayout back;
    LinearLayout FooterMain;
    RelativeLayout Help;
    ImageView btnStart;
    private Handler cleanerHandler = new cleanHandlerMethod();
    private FreshDownloadView freshDownloads;
    ObjectAnimator objAnimator;
    private TextView textDownloader;


    //Button Back click listner
    private class btnBackListner implements OnClickListener {

        public void onClick(View v) {
            WACleanMainActivity.this.onBackPressed();
        }
    }


    //Button Help click listner
    private class btnHelpListner implements OnClickListener {
        public void onClick(View v) {
            WACleanMainActivity.this.ShowHelp();
        }
    }

    @SuppressLint("HandlerLeak")
    private class cleanHandlerMethod extends Handler {

        public void handleMessage(Message message) {
            WACleanMainActivity.this.freshDownloads.upDateProgress((Integer) message.obj);
            if (message.what == 11) {
                WACleanMainActivity.this.freshDownloads.showDownloadError();
            }
        }
    }

    //Button start Listner method
    private class btnStartListner implements OnClickListener {
        WACleanMainActivity objClean;

        btnStartListner(WACleanMainActivity whatsappCleaner) {
            this.objClean = whatsappCleaner;
        }

        public void onClick(View view) {
            WACleanMainActivity.this.FooterMain.setVisibility(View.INVISIBLE);
            WACleanMainActivity.this.freshDownloads.setVisibility(View.VISIBLE);
            WACleanMainActivity.this.btnStart.setVisibility(View.INVISIBLE);
            if (!WACleanMainActivity.this.freshDownloads.using()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message obtain = Message.obtain();
                            obtain.obj = i;
                            WACleanMainActivity.this.cleanerHandler.sendMessage(obtain);
                        }
                    }
                }).start();
                WACleanMainActivity.this.getImageDetails();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WACleanMainActivity.this.btnStart.setVisibility(View.VISIBLE);
                        WACleanMainActivity.this.freshDownloads.setVisibility(View.GONE);
                        WACleanMainActivity.this.freshDownloads.reset();
                    }
                }, 8000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WACleanMainActivity.this.FooterMain.setVisibility(View.VISIBLE);
                        try {
                            WACleanMainActivity.this.objAnimator = ObjectAnimator.ofFloat(WACleanMainActivity.this.textDownloader, "Alpha", 0.0f, 1.0f);
                            String str = String.valueOf(CleanMain.objCleanMain / 1048576.0f);
                            WACleanMainActivity.this.textDownloader.setText("Your Whatsapp is boosted \n\t\t\tStorage Saved " + new Double(str).toString().substring(0, str.indexOf(46) + 3) + " MB");
                            WACleanMainActivity.this.objAnimator.setDuration(5000L);
                            WACleanMainActivity.this.objAnimator.start();
                            CleanMain.objCleanMain = 0.0f;
                        } catch (Exception e) {
                            WACleanMainActivity.this.textDownloader.setText("Your Whatsapp is boosted \n\t\t\tStorage Saved 0 MB");
                            WACleanMainActivity.this.objAnimator.setDuration(5000L);
                            WACleanMainActivity.this.objAnimator.start();
                            CleanMain.objCleanMain = 0.0f;
                        }
                    }
                }, 4000);
            }
        }
    }


    //It's method of getUser Images
    private class getUserImage extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... voids) {
            int i = 0;
            try {
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/Databases/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Audio/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/Calls/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Documents/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Animated Gifs/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Images/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Profile Pictures/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Profile Photos/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Video/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Profile/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Voice Notes/Sent"));
                CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/Wallpaper/Sent"));
                PackageManager packageManager = WACleanMainActivity.this.getPackageManager();
                Method[] declaredMethods = packageManager.getClass().getDeclaredMethods();
                int length = declaredMethods.length;
                while (i < length) {
                    Method method = declaredMethods[i];
                    if (method.getName().equals("freeStorage")) {
                        try {
                            method.invoke(packageManager, 0L, null);
                            break;
                        } catch (Exception e) {
                        }
                    } else {
                        i++;
                    }
                }
            } catch (Exception e2) {
            }
            return null;
        }

        public void onPostExecute(Void voids) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_whatsappcleaner);
        if (!Internetconnection.checkConnection(this)) {
            Banner banner = findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        if (!(ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NETWORK_STATE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SET_WALLPAPER") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.INTERNET") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SYSTEM_ALERT_WINDOW") == 0) && VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.SET_WALLPAPER", "android.permission.INTERNET", "android.permission.SYSTEM_ALERT_WINDOW"}, 0);
        }
        ShowHelp();
        this.back = findViewById(R.id.back);
        this.Help = findViewById(R.id.help);
        this.back.setOnClickListener(new btnBackListner());
        this.Help.setOnClickListener(new btnHelpListner());
        this.btnStart = findViewById(R.id.startClean);
        this.FooterMain = findViewById(R.id.footermain);
        this.textDownloader = findViewById(R.id.rl_footer);
        this.freshDownloads = findViewById(R.id.pitt);
        this.btnStart.setOnClickListener(new btnStartListner(this));
    }

    //Its a method of showing help dialog.
    private void ShowHelp() {
        final Dialog dialog = new Dialog(this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialog.requestWindowFeature(1);
        View inflate = layoutInflater.inflate(R.layout.whatsapp_cleaner_info_dialog, null);
        ImageView imageView = inflate.findViewById(R.id.btncancel);
        dialog.setContentView(inflate);
        dialog.show();
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void getImageDetails() {
        new getUserImage().execute();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
