package com.whatstools.fackChat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.fackChat.DataBaseDetails.DatabaseHelper;
import com.whatstools.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditChatUserProfile extends AppCompatActivity implements OnClickListener {
    LinearLayout backmenu;
    byte[] blob;
    byte[] bmyimage;
    DatabaseHelper databaseHelper;
    TextView delete_Profile;
    String isonline;
    String istyping;
    Switch online;
    Uri selectedImageUri;
    String status;
    Switch typing;
    TextView update_Profile;
    String user;
    int user_id;
    EditText user_name;
    CircleImageView user_profilepic;
    EditText user_status;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_chat_user_profile);
        this.databaseHelper = new DatabaseHelper(this);
        this.user_id = getIntent().getExtras().getInt("USER_ID");
        this.user_name = findViewById(R.id.user_name);
        this.user_status = findViewById(R.id.user_status);
        this.user_profilepic = findViewById(R.id.user_profilepic);
        this.online = findViewById(R.id.user_onlile);
        this.typing = findViewById(R.id.user_typing);
        this.backmenu = findViewById(R.id.backmenu);
        this.backmenu.setOnClickListener(this);
        this.update_Profile = findViewById(R.id.edit_userProfile);
        this.delete_Profile = findViewById(R.id.delete_userProfile);
        GetCurrentUserDetails();
        this.update_Profile.setOnClickListener(this);
        this.delete_Profile.setOnClickListener(this);
        this.user_profilepic.setOnClickListener(this);
    }


    //Get user all details from this method
    private void GetCurrentUserDetails() {
        Cursor c = this.databaseHelper.getUserHistory(this.user_id + "");
        Log.d("Total Colounmn", c.getCount() + "");
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            this.user = c.getString(c.getColumnIndex("uname"));
            this.status = c.getString(c.getColumnIndex("ustatus"));
            this.isonline = c.getString(c.getColumnIndex("uonline"));
            this.istyping = c.getString(c.getColumnIndex("utyping"));
            this.blob = c.getBlob(c.getColumnIndex("uprofile"));
        }
        this.user_name.setText(this.user + "");
        this.user_status.setText(this.status + "");
        if (this.isonline.equals("online")) {
            this.online.setChecked(true);
        } else {
            this.online.setChecked(false);
        }
        if (this.istyping.equals("typing")) {
            this.typing.setChecked(true);
        } else {
            this.typing.setChecked(false);
        }
        this.user_profilepic.setImageBitmap(getImagefromdatabase(this.blob));
    }

    //Get user image from database
    private Bitmap getImagefromdatabase(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backmenu:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                return;
            case R.id.delete_userProfile:
                this.databaseHelper.DeleteUserProfile(this.user_id);
                startActivity(new Intent(this, MainFackChat.class));
                finish();
                return;
            case R.id.edit_userProfile:
                String isonline;
                String istyping;
                String uname = this.user_name.getText().toString();
                String status = this.user_status.getText().toString();
                if (this.online.isChecked()) {
                    isonline = "online";
                } else {
                    isonline = "offline";
                }
                if (this.typing.isChecked()) {
                    istyping = "typing";
                } else {
                    istyping = "nottyping";
                }
                if (this.bmyimage == null) {
                    Bitmap bitmap = ((BitmapDrawable) this.user_profilepic.getDrawable()).getBitmap();
                    ByteArrayOutputStream blob = new ByteArrayOutputStream();
                    bitmap.compress(CompressFormat.PNG, 0, blob);
                    this.bmyimage = blob.toByteArray();
                }
                this.databaseHelper.getUserDetailsUpdate(this.user_id, uname, status, isonline, istyping, this.bmyimage);
                Toast.makeText(this, "Profile Updated...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainFackChat.class));
                return;
            case R.id.user_profilepic:
                startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 101);
                return;
            default:
                return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 101) {
            onSelectFromGalleryResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        try {
            this.selectedImageUri = data.getData();
            this.user_profilepic.setImageURI(this.selectedImageUri);
            this.bmyimage = saveImageInDB(this.selectedImageUri);
            getPath(this.selectedImageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Save changed data into databse
    private byte[] saveImageInDB(Uri selectedImageUri) {
        try {
            return getBytes(getContentResolver().openInputStream(selectedImageUri));
        } catch (IOException ioe) {
            Log.e("Hello1", "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            return null;
        }
    }

    private byte[] getBytes(InputStream iStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int len = iStream.read(buffer);
            if (len == -1) {
                return byteBuffer.toByteArray();
            }
            byteBuffer.write(buffer, 0, len);
        }
    }

    private String getPath(Uri selectedImageUri) {
        Cursor cursor = managedQuery(selectedImageUri, new String[]{"_data"}, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        finish();
    }

}
