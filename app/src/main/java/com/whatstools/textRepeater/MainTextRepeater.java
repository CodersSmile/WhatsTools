package com.whatstools.textRepeater;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.startapp.android.publish.ads.banner.Banner;
import com.whatstools.Internetconnection;
import com.whatstools.R;

public class MainTextRepeater extends AppCompatActivity {
    String Maintext;
    int NoofRepeat;
    String RepeatText;
    Button clearTxtBtn;
    Button convertButton;
    EditText convertedText;
    Button btnCopy;
    EditText emojeeText;
    ImageView imNewLine;
    EditText txtInput;
    boolean isNewLine = false;
    String no;
    ProgressDialog pDialog;
    Button btnShare;
    TextView txtNewLine;


    //Click event of Button Convert
    private class btnConverListner implements OnClickListener {
        public void onClick(View view) {
            MainTextRepeater.this.convertedText.setText("");
            MainTextRepeater.this.RepeatText = MainTextRepeater.this.txtInput.getText().toString();
            MainTextRepeater.this.no = MainTextRepeater.this.emojeeText.getText().toString();
            try {
                MainTextRepeater.this.NoofRepeat = Integer.parseInt(MainTextRepeater.this.no);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            if (MainTextRepeater.this.txtInput.getText().toString().isEmpty()) {
                Toast.makeText(MainTextRepeater.this.getApplicationContext(), "Enter Repeat Text", Toast.LENGTH_SHORT).show();
            } else if (MainTextRepeater.this.emojeeText.getText().toString().isEmpty()) {
                Toast.makeText(MainTextRepeater.this.getApplicationContext(), "Enter Number of Repeat Text", Toast.LENGTH_SHORT).show();
            } else if (MainTextRepeater.this.NoofRepeat <= 10000) {
                new CreateRepeateText().execute();
            } else {
                Toast.makeText(MainTextRepeater.this.getApplicationContext(), "Number of Repeter Text Limited Please Enter Limited Number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Click event of Button Clear
    private class btnClearTextListner implements OnClickListener {
        public void onClick(View view) {
            MainTextRepeater.this.convertedText.setText("");
        }
    }


    //Listener of while converted text
    private class btnConvertedTexListner implements OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (!MainTextRepeater.this.convertedText.getText().toString().isEmpty()) {
                ((ClipboardManager) MainTextRepeater.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(MainTextRepeater.this.txtInput.getText().toString(), MainTextRepeater.this.convertedText.getText().toString()));
                Toast.makeText(MainTextRepeater.this.getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Click event of Button Copy
    private class btnCopyListner implements OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (MainTextRepeater.this.convertedText.getText().toString().isEmpty()) {
                Toast.makeText(MainTextRepeater.this.getApplicationContext(), "Convert text before copy", Toast.LENGTH_SHORT).show();
                return;
            }
            ((ClipboardManager) MainTextRepeater.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(MainTextRepeater.this.txtInput.getText().toString(), MainTextRepeater.this.convertedText.getText().toString()));
            Toast.makeText(MainTextRepeater.this.getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
        }
    }


    //Click event of Button Share
    private class btnShareListner implements OnClickListener {
        public void onClick(View view) {
            if (MainTextRepeater.this.convertedText.getText().toString().isEmpty()) {
                Toast.makeText(MainTextRepeater.this.getApplicationContext(), "Please Convert text to share", Toast.LENGTH_LONG).show();
                return;
            }
            Intent shareIntent = new Intent();
            shareIntent.setAction("android.intent.action.SEND");
            shareIntent.setPackage("com.whatsapp");
            shareIntent.putExtra("android.intent.extra.TEXT", MainTextRepeater.this.convertedText.getText().toString());
            shareIntent.setType("text/plain");
            MainTextRepeater.this.startActivity(Intent.createChooser(shareIntent, "Select an app to share"));
        }
    }

    //Create repeat text in background
    private class CreateRepeateText extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            MainTextRepeater.this.pDialog.setMessage("Please Wait...");
            MainTextRepeater.this.pDialog.setProgressStyle(0);
            MainTextRepeater.this.pDialog.setCancelable(false);
            MainTextRepeater.this.pDialog.show();
        }

        public String doInBackground(String... strings) {
            int i;
            if (MainTextRepeater.this.isNewLine) {
                for (i = 1; i <= MainTextRepeater.this.NoofRepeat; i++) {
                    if (i == 1) {
                        MainTextRepeater.this.Maintext = MainTextRepeater.this.RepeatText;
                    } else {
                        MainTextRepeater.this.Maintext += "\n" + MainTextRepeater.this.RepeatText;
                    }
                }
            } else {
                for (i = 1; i <= MainTextRepeater.this.NoofRepeat; i++) {
                    if (i == 1) {
                        MainTextRepeater.this.Maintext = MainTextRepeater.this.RepeatText;
                    } else {
                        MainTextRepeater.this.Maintext += "\t" + MainTextRepeater.this.RepeatText;
                    }
                }
            }
            return null;
        }

        @SuppressLint({"LongLogTag"})
        public void onPostExecute(String result) {
            MainTextRepeater.this.pDialog.dismiss();
            MainTextRepeater.this.convertedText.setText(MainTextRepeater.this.Maintext);
        }
    }

    //Switch of New line is on or off
    private class newLineClick implements OnClickListener {

        public void onClick(View v) {
            if (MainTextRepeater.this.isNewLine) {
                MainTextRepeater.this.isNewLine = false;
                MainTextRepeater.this.txtNewLine.setText("New Line Off");
                MainTextRepeater.this.imNewLine.setImageResource(R.drawable.offs);
                return;
            }
            MainTextRepeater.this.isNewLine = true;
            MainTextRepeater.this.txtNewLine.setText("New Line On");
            MainTextRepeater.this.imNewLine.setImageResource(R.drawable.ons);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_repeater);
        getSupportActionBar().setTitle("Text Repeater");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!Internetconnection.checkConnection(this)) {
            Banner banner = findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        this.pDialog = new ProgressDialog(this);
        this.txtNewLine = findViewById(R.id.txtNewLine);
        this.imNewLine = findViewById(R.id.btnNewLine);
        if (this.isNewLine) {
            this.txtNewLine.setText("New Line On");
            this.imNewLine.setImageResource(R.drawable.ons);
        } else {
            this.txtNewLine.setText("New Line Off");
            this.imNewLine.setImageResource(R.drawable.offs);
        }
        this.imNewLine.setOnClickListener(new newLineClick());
        this.txtInput = findViewById(R.id.inputText);
        this.emojeeText = findViewById(R.id.emojeeTxt);
        this.convertedText = findViewById(R.id.convertedEmojeeTxt);
        this.convertButton = findViewById(R.id.convertEmojeeBtn);
        this.btnCopy = findViewById(R.id.copyTxtBtn);
        this.btnShare = findViewById(R.id.shareTxtBtn);
        this.clearTxtBtn = findViewById(R.id.clearTxtBtn);
        this.convertButton.setOnClickListener(new btnConverListner());
        this.clearTxtBtn.setOnClickListener(new btnClearTextListner());
        this.convertedText.setOnClickListener(new btnConvertedTexListner());
        this.btnCopy.setOnClickListener(new btnCopyListner());
        this.btnShare.setOnClickListener(new btnShareListner());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
