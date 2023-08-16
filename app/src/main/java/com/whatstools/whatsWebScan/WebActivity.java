package com.whatstools.whatsWebScan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.startapp.android.publish.ads.banner.Banner;
import com.whatstools.Internetconnection;
import com.whatstools.R;

public class WebActivity extends AppCompatActivity {
    public static Handler handler;
    private static ValueCallback<Uri[]> mUploadMessageArr;
    String TAG = WebActivity.class.getSimpleName();
    boolean doubleBackToExitPressedOnce = false;
    ProgressBar progressBar;
    private WebView webViewscanner;


    @SuppressLint("HandlerLeak")
    private class btnInitHandlerListner extends Handler {
        @SuppressLint({"SetTextI18n"})
        public void handleMessage(Message msg) {
        }
    }

    //Webview Client Methods
    private class webChromeClients extends WebChromeClient {
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.e("CustomClient", consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }


    //Webview Client Methods
    private class MyBrowser extends WebViewClient {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            WebActivity.this.progressBar.setVisibility(View.VISIBLE);
            Log.e(WebActivity.this.TAG, "progressbar");
            super.onPageStarted(view, url, favicon);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            view.loadUrl(request);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e(WebActivity.this.TAG, "progressbar GONE");
            WebActivity.this.progressBar.setVisibility(View.GONE);
        }
    }

    //Initialisation Method
    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = 17)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_main);
        InitHandler();
        if (!Internetconnection.checkConnection(this)) {
            Banner banner = findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        this.progressBar = findViewById(R.id.progressBar);
        if (VERSION.SDK_INT >= 24) {
            onstart();
            this.webViewscanner = findViewById(R.id.webViewscan);
            this.webViewscanner.clearFormData();
            this.webViewscanner.getSettings().setSaveFormData(true);
            this.webViewscanner.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            this.webViewscanner.setLayoutParams(new LayoutParams(-1, -1));
            this.webViewscanner.setWebChromeClient(new webChromeClients());
            this.webViewscanner.setWebViewClient(new MyBrowser());
            this.webViewscanner.getSettings().setAppCacheMaxSize(5242880);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setAppCacheEnabled(true);
            this.webViewscanner.getSettings().setJavaScriptEnabled(true);
            this.webViewscanner.getSettings().setDefaultTextEncodingName( "UTF-8");
            this.webViewscanner.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            this.webViewscanner.getSettings().setDatabaseEnabled(true);
            this.webViewscanner.getSettings().setBuiltInZoomControls(false);
            this.webViewscanner.getSettings().setSupportZoom(false);
            this.webViewscanner.getSettings().setUseWideViewPort(true);
            this.webViewscanner.getSettings().setDomStorageEnabled(true);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.getSettings().setLoadsImagesAutomatically(true);
            this.webViewscanner.getSettings().setBlockNetworkImage(false);
            this.webViewscanner.getSettings().setBlockNetworkLoads(false);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.loadUrl("https://web.whatsapp.com/%F0%9F%8C%90/en");
        } else {
            onstart();
            this.webViewscanner = findViewById(R.id.webViewscan);
            this.webViewscanner.clearFormData();
            this.webViewscanner.getSettings().setSaveFormData(true);
            this.webViewscanner.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            this.webViewscanner.setLayoutParams(new LayoutParams(-1, -1));
            this.webViewscanner.setWebChromeClient(new webChromeClients());
            this.webViewscanner.setWebViewClient(new MyBrowser());
            this.webViewscanner.getSettings().setAppCacheMaxSize(5242880);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setAppCacheEnabled(true);
            this.webViewscanner.getSettings().setJavaScriptEnabled(true);
            this.webViewscanner.getSettings().setDefaultTextEncodingName( "UTF-8");
            this.webViewscanner.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            this.webViewscanner.getSettings().setDatabaseEnabled(true);
            this.webViewscanner.getSettings().setBuiltInZoomControls(false);
            this.webViewscanner.getSettings().setSupportZoom(false);
            this.webViewscanner.getSettings().setUseWideViewPort(true);
            this.webViewscanner.getSettings().setDomStorageEnabled(true);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.getSettings().setLoadsImagesAutomatically(true);
            this.webViewscanner.getSettings().setBlockNetworkImage(false);
            this.webViewscanner.getSettings().setBlockNetworkLoads(false);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.loadUrl("https://web.whatsapp.com/%F0%9F%8C%90/en");
        }
    }

    public void onstart() {
        if (VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && VERSION.SDK_INT >= 21) {
            mUploadMessageArr.onReceiveValue(FileChooserParams.parseResult(i2, intent));
            mUploadMessageArr = null;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean z = true;
        if (keyCode == 4) {
            try {
                if (this.webViewscanner.canGoBack()) {
                    this.webViewscanner.goBack();
                    return z;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
        z = super.onKeyDown(keyCode, event);
        return z;
    }


    //Menu Initialisation
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu Initialisation
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rateapp:
                RateApp();
                return true;
            case R.id.shareapp:
                more();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //It's Method of when you Back Pressed
    @SuppressLint({"WrongConstant"})
    @RequiresApi(api = 21)
    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press again to exit", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                WebActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    protected void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        this.webViewscanner.clearCache(true);
    }

    public void onDestroy() {
        super.onDestroy();
        this.webViewscanner.clearCache(true);
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        this.webViewscanner.clearCache(true);
        super.onStop();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @SuppressLint({"HandlerLeak"})
    private void InitHandler() {
        handler = new btnInitHandlerListner();
    }


    //It's Method of More App
    private void more() {
        String appPackageName = getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction("android.intent.action.SEND");
        sendIntent.putExtra("android.intent.extra.TEXT", "Whats Web Scan\n  https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    //It's Method of Rate App
    private void RateApp() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.rate_dialog, null);
        dialogBuilder.setView(dialogView);
        Button rate_us = dialogView.findViewById(R.id.btn_rate_us);
        Button cancle = dialogView.findViewById(R.id.btn_cancle);
        final AlertDialog b = dialogBuilder.create();
        rate_us.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    WebActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + WebActivity.this.getPackageName())));
                } catch (ActivityNotFoundException e) {
                    WebActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + WebActivity.this.getPackageName())));
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
}
