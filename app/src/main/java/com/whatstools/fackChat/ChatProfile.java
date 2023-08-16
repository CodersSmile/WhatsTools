package com.whatstools.fackChat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
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

import androidx.appcompat.app.AppCompatActivity;

import com.whatstools.fackChat.DataBaseDetails.DatabaseHelper;
import com.whatstools.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatProfile extends AppCompatActivity implements OnClickListener {
    LinearLayout backmenu;
    byte[] bmyimage;
    DatabaseHelper dataBaseDetails;
    Switch online;
    TextView save_Profile;
    String selectedImagePath;
    Uri selectedImageUri;
    Switch typing;
    EditText user_name;
    CircleImageView user_profilepic;
    EditText user_status;
    String usename;
    String useronline;
    String userstatus;
    String usertyping;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_profile);
        this.dataBaseDetails = new DatabaseHelper(this);
        this.user_name = findViewById(R.id.user_name);
        this.user_status = findViewById(R.id.user_status);
        this.user_profilepic = findViewById(R.id.user_profilepic);
        this.online = findViewById(R.id.user_onlile);
        this.typing = findViewById(R.id.user_typing);
        this.backmenu = findViewById(R.id.backmenu);
        this.save_Profile = findViewById(R.id.save_userProfile);
        this.user_profilepic.setOnClickListener(this);
        this.save_Profile.setOnClickListener(this);
        this.backmenu.setOnClickListener(this);
    }

    //Click event method
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backmenu:
                finish();
                return;
            case R.id.save_userProfile:
                SavaProfile();
                return;
            case R.id.user_profilepic:
                startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 101);
                return;
            default:
        }
    }

    //Save profile method
    private void SavaProfile() {
        this.usename = this.user_name.getText().toString();
        this.userstatus = this.user_status.getText().toString();
        if (this.online.isChecked()) {
            this.useronline = "online";
        } else {
            this.useronline = "offline";
        }
        if (this.typing.isChecked()) {
            this.usertyping = "typing";
        } else {
            this.usertyping = "nottyping";
        }
        Log.d("SELECTED DATA IS", "USER NAME" + this.usename + "USER Staus" + this.userstatus + " user onlile" + this.useronline + " user typing " + this.usertyping);
        if (this.bmyimage == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_photo);
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 0, blob);
            this.bmyimage = blob.toByteArray();
        }
        this.dataBaseDetails.InsertStudentDetails(this.usename, this.userstatus, this.useronline, this.usertyping, this.bmyimage);
        startActivity(new Intent(this, MainFackChat.class));
        finish();
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
            Uri compressUri = getImageUri(Media.getBitmap(getContentResolver(), this.selectedImageUri));
            this.user_profilepic.setImageURI(compressUri);
            this.bmyimage = saveImageInDB(compressUri);
            this.selectedImagePath = getPath(compressUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Uri getImageUri(Bitmap inImage) {
        inImage.compress(CompressFormat.JPEG, 10, new ByteArrayOutputStream());
        return Uri.parse(Media.insertImage(getContentResolver(), inImage, "Title", null));
    }

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
        byte[] buffer = new byte[3072];
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
}
