package com.whatstools.emojiText;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.startapp.android.publish.ads.banner.Banner;
import com.whatstools.Internetconnection;
import com.whatstools.R;

import java.io.IOException;
import java.io.InputStream;

public class Texttoemoji extends AppCompatActivity {
    Button clearTxtBtn;
    Button convertButton;
    EditText convertedText;
    Button copyBtn;
    EditText emojeeText;
    EditText inputText;
    Button shareButton;


    //button convert click event listener
    private class btnConvertListner implements OnClickListener {
        public void onClick(View view) {
            if (Texttoemoji.this.inputText.getText().toString().isEmpty()) {
                Toast.makeText(Texttoemoji.this.getApplicationContext(), "Enter text", Toast.LENGTH_SHORT).show();
                return;
            }
            char[] charArray = Texttoemoji.this.inputText.getText().toString().toCharArray();
            Texttoemoji.this.convertedText.setText(".\n");
            for (char character : charArray) {
                byte[] localObject3;
                InputStream localObject2;
                if (character == '?') {
                    try {
                        InputStream localObject1 = Texttoemoji.this.getBaseContext().getAssets().open("ques.txt");
                        localObject3 = new byte[localObject1.available()];
                        localObject1.read(localObject3);
                        localObject1.close();
                        Texttoemoji.this.convertedText.append(new String(localObject3).replaceAll("[*]", Texttoemoji.this.emojeeText.getText().toString()) + "\n\n");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                } else if (character == ((char) (character & 95)) || Character.isDigit(character)) {
                    try {
                        localObject2 = Texttoemoji.this.getBaseContext().getAssets().open(character + ".txt");
                        localObject3 = new byte[localObject2.available()];
                        localObject2.read(localObject3);
                        localObject2.close();
                        Texttoemoji.this.convertedText.append(new String(localObject3).replaceAll("[*]", Texttoemoji.this.emojeeText.getText().toString()) + "\n\n");
                    } catch (IOException ioe2) {
                        ioe2.printStackTrace();
                    }
                } else {
                    try {
                        localObject2 = Texttoemoji.this.getBaseContext().getAssets().open("low" + character + ".txt");
                        localObject3 = new byte[localObject2.available()];
                        localObject2.read(localObject3);
                        localObject2.close();
                        Texttoemoji.this.convertedText.append(new String(localObject3).replaceAll("[*]", Texttoemoji.this.emojeeText.getText().toString()) + "\n\n");
                    } catch (IOException ioe22) {
                        ioe22.printStackTrace();
                    }
                }
            }
        }
    }

    //Button - clear Text click listener method
    private class btnClearTextListner implements OnClickListener {
        public void onClick(View view) {
            Texttoemoji.this.convertedText.setText("");
        }
    }

    //Button  - Convert Text click listener method
    private class btnConvertedTextListner implements OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (!Texttoemoji.this.convertedText.getText().toString().isEmpty()) {
                ((ClipboardManager) Texttoemoji.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(Texttoemoji.this.inputText.getText().toString(), Texttoemoji.this.convertedText.getText().toString()));
                Toast.makeText(Texttoemoji.this.getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Copy button click listener method
    private class btnCopyButtonListner implements OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (Texttoemoji.this.convertedText.getText().toString().isEmpty()) {
                Toast.makeText(Texttoemoji.this.getApplicationContext(), "Convert text before copy", Toast.LENGTH_SHORT).show();
                return;
            }
            ((ClipboardManager) Texttoemoji.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(Texttoemoji.this.inputText.getText().toString(), Texttoemoji.this.convertedText.getText().toString()));
            Toast.makeText(Texttoemoji.this.getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    //Share Button click listener method
    private class btnShareListner implements OnClickListener {
        public void onClick(View view) {
            if (Texttoemoji.this.convertedText.getText().toString().isEmpty()) {
                Toast.makeText(Texttoemoji.this.getApplicationContext(), "Convert text to share", Toast.LENGTH_LONG).show();
                return;
            }
            Intent shareIntent = new Intent();
            shareIntent.setAction("android.intent.action.SEND");
            shareIntent.setPackage("com.whatsapp");
            shareIntent.putExtra("android.intent.extra.TEXT", Texttoemoji.this.convertedText.getText().toString());
            shareIntent.setType("text/plain");
            Texttoemoji.this.startActivity(Intent.createChooser(shareIntent, "Select an app to share"));
        }
    }

    //initial method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texttoemoji);
        getSupportActionBar().setTitle("Text To Emoji");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!Internetconnection.checkConnection(this)) {
            Banner banner = findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        this.inputText = findViewById(R.id.inputText);
        this.emojeeText = findViewById(R.id.emojeeTxt);
        this.convertedText = findViewById(R.id.convertedEmojeeTxt);
        this.convertButton = findViewById(R.id.convertEmojeeBtn);
        this.copyBtn = findViewById(R.id.copyTxtBtn);
        this.shareButton = findViewById(R.id.shareTxtBtn);
        this.clearTxtBtn = findViewById(R.id.clearTxtBtn);
        this.convertButton.setOnClickListener(new btnConvertListner());
        this.clearTxtBtn.setOnClickListener(new btnClearTextListner());
        this.convertedText.setOnClickListener(new btnConvertedTextListner());
        this.copyBtn.setOnClickListener(new btnCopyButtonListner());
        this.shareButton.setOnClickListener(new btnShareListner());
    }


    //Menu initialisation
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
