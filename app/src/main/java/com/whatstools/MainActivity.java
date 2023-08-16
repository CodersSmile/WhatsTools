package com.whatstools;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.asciiFaces.AsciiFacesMainActivity;
import com.whatstools.captionStatusShare.Captionitem;
import com.whatstools.cleaner.WACleanMainActivity;
import com.whatstools.directChat.ChatDirect;
import com.whatstools.emojiText.Texttoemoji;
import com.whatstools.fackChat.MainFackChat;
import com.whatstools.gallery.MainWhatsGalleryActivity;
import com.whatstools.shakeShortcut.ShakeMain;
import com.whatstools.statusSaver.StatusSaverMainActivity;
import com.whatstools.textRepeater.MainTextRepeater;
import com.whatstools.walkChat.WalkMainActivity;
import com.whatstools.whatsWebScan.WebActivity;

public class MainActivity extends AppCompatActivity {
    public static int countAds;
    LinearLayout linearRateUs;
    LinearLayout whatsWeb;
    LinearLayout linearWhatsApAsciFace;
    LinearLayout linearWPCaptionStatus;
    LinearLayout linearWPCleaner;
    LinearLayout linearWPDirectChat;
    LinearLayout linearWPEmojis;
    LinearLayout linearWPFakeChat;
    LinearLayout linearWPAppGallery;
    LinearLayout linearWPAppShortcut;
    LinearLayout linearWpAppStatusSaver;
    LinearLayout linearWPTextRepeter;
    LinearLayout linearWPWalk;
    private InterstitialAd mInterstitialAdMob;
    public int varCounter;
    int isCallFor;


    //Initialisation Method of this view
    @SuppressLint({"ObsoleteSdkInt"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showGoogleInterstitial();

        if (!Internetconnection.checkConnection(this)) {
            Banner banner = findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        if (VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName()));
            Log.e("Packagename", getPackageName());
            startActivityForResult(intent, 1234);
        }
//        if (!(ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NETWORK_STATE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SET_WALLPAPER") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.INTERNET") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SYSTEM_ALERT_WINDOW") == 0) && VERSION.SDK_INT >= 23) {
//            requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.SET_WALLPAPER", "android.permission.INTERNET", "android.permission.SYSTEM_ALERT_WINDOW"}, 0);
//        }
        this.whatsWeb = findViewById(R.id.whtsWeb);
        this.linearWPAppShortcut = findViewById(R.id.shortcut);
        this.linearWPAppGallery = findViewById(R.id.Gallery);
        this.linearWPWalk = findViewById(R.id.walkchat);
        this.linearWPCleaner = findViewById(R.id.cleaner);
        this.linearWPDirectChat = findViewById(R.id.directChat);
        this.linearWpAppStatusSaver = findViewById(R.id.StatusSaver);
        this.linearWPEmojis = findViewById(R.id.Textemoji);
        this.linearWPCaptionStatus = findViewById(R.id.caption);
        this.linearWPTextRepeter = findViewById(R.id.RepeatText);
        this.linearWPFakeChat = findViewById(R.id.FackChat);
        this.linearWhatsApAsciFace = findViewById(R.id.ascifaces);
        this.linearRateUs = findViewById(R.id.rateus);

        this.linearWPAppShortcut.setOnClickListener(new btnWhatsappShortcutListner());
        this.whatsWeb.setOnClickListener(new btnWhatsWebClick());
        this.linearWPAppGallery.setOnClickListener(new btnWpAppGallryLiatner());
        this.linearWPWalk.setOnClickListener(new btnWpWalkChatListner());
        this.linearWPCleaner.setOnClickListener(new btnWpCleanerListner());
        this.linearWPDirectChat.setOnClickListener(new btnWpDirectChatListner());
        this.linearWpAppStatusSaver.setOnClickListener(new btnWpStatusSaverListner());
        this.linearWPEmojis.setOnClickListener(new btnWpEmojiListner());
        this.linearWPCaptionStatus.setOnClickListener(new btnWpCaptionStatusListner());
        this.linearWPTextRepeter.setOnClickListener(new btnWpTextRepeterLitstner());

        //Fake Chat Method
        this.linearWPFakeChat.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                isCallFor = 4;
                if (varCounter == 0) {
                    if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                        mInterstitialAdMob.show();
                        return;
                    }
                }
                MainActivity.this.startActivity(new Intent(MainActivity.this, MainFackChat.class));
                StartAppAd.showAd(MainActivity.this);
            }
        });

        //Create ASCI Faced Method
        this.linearWhatsApAsciFace.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                isCallFor = 6;
                if (varCounter == 0) {
                    if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                        mInterstitialAdMob.show();
                        return;
                    }
                }
                MainActivity.this.startActivity(new Intent(MainActivity.this, AsciiFacesMainActivity.class));
                StartAppAd.showAd(MainActivity.this);
            }
        });
        //Rate Us method
        this.linearRateUs.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.RateApp();
            }
        });
    }


    //Click event of Button Whatsapp Shortcut
    private class btnWhatsappShortcutListner implements OnClickListener {
        public void onClick(View v) {
            isCallFor = 10;
            if (varCounter == 0) {
                if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                    return;
                }
            }
            MainActivity.this.startActivity(new Intent(MainActivity.this, ShakeMain.class));
            StartAppAd.showAd(MainActivity.this);
        }
    }

    //Click event of Button Whatsapp Web
    private class btnWhatsWebClick implements OnClickListener {
        public void onClick(View v) {
            isCallFor = 0;
            if (varCounter == 0) {
                if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                    return;
                }
            }
            MainActivity.this.startActivity(new Intent(MainActivity.this, WebActivity.class));
            StartAppAd.showAd(MainActivity.this);
        }
    }

    //Click event of Button gallery
    private class btnWpAppGallryLiatner implements OnClickListener {
        public void onClick(View v) {
            isCallFor = 11;
            if (varCounter == 0) {
                if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                    return;
                }
            }
            MainActivity.this.startActivity(new Intent(MainActivity.this, MainWhatsGalleryActivity.class));
            StartAppAd.showAd(MainActivity.this);
        }
    }

    //Click event of Walk and chat Button
    private class btnWpWalkChatListner implements OnClickListener {
        public void onClick(View v) {

            isCallFor = 1;
            if (varCounter == 0) {
                if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                    return;
                }
            }
            Toast.makeText(getApplicationContext(),"Please turn on Accessibility Service for Whats Tools",Toast.LENGTH_LONG).show();
            MainActivity.this.startActivity(new Intent(MainActivity.this, WalkMainActivity.class));
            StartAppAd.showAd(MainActivity.this);
        }
    }

    //Click event of cleaner Button
    private class btnWpCleanerListner implements OnClickListener {
        public void onClick(View v) {
            isCallFor = 5;
            if (varCounter == 0) {
                if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                    return;
                }
            }
            MainActivity.this.startActivity(new Intent(MainActivity.this, WACleanMainActivity.class));
            StartAppAd.showAd(MainActivity.this);
        }
    }


    //Click event of Direct Chat Without saving number Button
    private class btnWpDirectChatListner implements OnClickListener {
        public void onClick(View v) {
            isCallFor = 3;
            if (varCounter == 0) {
                if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                    return;
                }
            }
            MainActivity.this.startActivity(new Intent(MainActivity.this, ChatDirect.class));
            StartAppAd.showAd(MainActivity.this);
        }
    }

    //Click event of WP Status Saver Button
    private class btnWpStatusSaverListner implements OnClickListener {
        public void onClick(View v) {
            isCallFor = 2;
            if (varCounter == 0) {
                if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                    return;
                }
            }
            MainActivity.this.startActivity(new Intent(MainActivity.this, StatusSaverMainActivity.class));
            StartAppAd.showAd(MainActivity.this);
        }
    }

    //Click event of Emoji Creator Button
    private class btnWpEmojiListner implements OnClickListener {
        public void onClick(View v) {
            isCallFor = 9;
            if (varCounter == 0) {
                if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                    return;
                }
            }
            MainActivity.this.startActivity(new Intent(MainActivity.this, Texttoemoji.class));
            StartAppAd.showAd(MainActivity.this);
        }
    }


    //Click event of Caption or Status Button
    private class btnWpCaptionStatusListner implements OnClickListener {
        public void onClick(View v) {
            isCallFor = 8;
            if (varCounter == 0) {
                if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                    return;
                }
            }
            MainActivity.this.startActivity(new Intent(MainActivity.this, Captionitem.class));
            StartAppAd.showAd(MainActivity.this);
        }
    }


    //Click event of Text Repeater Button
    private class btnWpTextRepeterLitstner implements OnClickListener {
        public void onClick(View v) {
            isCallFor = 7;
            if (varCounter == 0) {
                if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
                    mInterstitialAdMob.show();
                    return;
                }
            }
            MainActivity.this.startActivity(new Intent(MainActivity.this, MainTextRepeater.class));
            StartAppAd.showAd(MainActivity.this);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //Rate Us Method
    private void RateApp() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.main_rate_dialog, null);
        dialogBuilder.setView(dialogView);
        Button rate_us = dialogView.findViewById(R.id.btn_rate_us);
        Button cancle = dialogView.findViewById(R.id.btn_cancle);
        final AlertDialog b = dialogBuilder.create();
        rate_us.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                } catch (ActivityNotFoundException e) {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                }
                b.cancel();
            }
        });
        cancle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                b.cancel();
            }
        });
        b.show();
    }

    @Override
    protected void onStart() {
        manageAd();
        super.onStart();
    }

    public void manageAd() {
        SharedPreferences sp = getSharedPreferences("WhatzWebScan", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        varCounter = sp.getInt("counter", 0);
        Log.e("VAR", "" + varCounter);
        if (varCounter == 0) {
            requestNewGoogleInterstitial();
            editor.putInt("counter", varCounter + 1);
        } else if (varCounter >= 2) {
            editor.putInt("counter", 0);
        } else {
            editor.putInt("counter", varCounter + 1);
        }
        editor.apply();
    }

    //GOOGLE AD
    public void showGoogleInterstitial() {
        this.mInterstitialAdMob = new com.google.android.gms.ads.InterstitialAd(this);
        this.mInterstitialAdMob.setAdUnitId(getString(R.string.interstitial_id));
        this.mInterstitialAdMob.setAdListener(new GoogleAdListner());
    }

    private void requestNewGoogleInterstitial() {
        this.mInterstitialAdMob.loadAd(new AdRequest.Builder().addTestDevice("437639BF640142DFB48A98851705A70F").build());
    }

    private class GoogleAdListner extends com.google.android.gms.ads.AdListener {
        @SuppressLint("WrongConstant")
        public void onAdClosed() {
            if (isCallFor == 0) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, WebActivity.class));
            } else if (isCallFor == 1) {
                Toast.makeText(getApplicationContext(),"Please turn on Accessibility Service for Whats Tools",Toast.LENGTH_LONG).show();
                MainActivity.this.startActivity(new Intent(MainActivity.this, WalkMainActivity.class));
            } else if (isCallFor == 2) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, StatusSaverMainActivity.class));
            } else if (isCallFor == 3) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ChatDirect.class));
            } else if (isCallFor == 4) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, MainFackChat.class));
            } else if (isCallFor == 5) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, WACleanMainActivity.class));
            } else if (isCallFor == 6) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, AsciiFacesMainActivity.class));
            } else if (isCallFor == 7) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, MainTextRepeater.class));
            } else if (isCallFor == 8) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Captionitem.class));
            } else if (isCallFor == 9) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Texttoemoji.class));
            } else if (isCallFor == 10) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ShakeMain.class));
            } else if (isCallFor == 11) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, MainWhatsGalleryActivity.class));
            }
        }

        @Override
        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
        }
    }

}
