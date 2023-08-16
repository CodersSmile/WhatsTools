package com.whatstools.fackChat;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.startapp.android.publish.ads.banner.Banner;
import com.whatstools.Internetconnection;
import com.whatstools.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallsFragment extends Fragment {
    static Uri selectedImageUri;
    private byte[] bmyimage;
    private ImageView callNow;
    private EditText txtName;
    private String nameProfile;
    private CircleImageView user_profilepic;
    View view;


    //User profile button click listener method
    private class btnUserProfilePicListner implements OnClickListener {
        public void onClick(View view) {
            CallsFragment.this.startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 101);
        }
    }

    //Call now button click listener method
    private class btnCallNowListner implements OnClickListener {
        public void onClick(View view) {
            CallsFragment.this.nameProfile = CallsFragment.this.txtName.getText().toString();
            Log.i("ContentValues", "onClick: " + CallsFragment.this.nameProfile);
            if (CallsFragment.this.nameProfile.isEmpty()) {
                Toast.makeText(CallsFragment.this.getActivity(), "Please Enter The Caller Name ", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(CallsFragment.this.getActivity(), Calls.class);
            intent.putExtra("NAME", CallsFragment.this.nameProfile);
            intent.putExtra("ID", 1);
            intent.putExtra("PROFILEPIC", CallsFragment.selectedImageUri);
            CallsFragment.this.startActivity(intent);
        }
    }

    //initial method
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Create view method
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_call, container, false);

        if (!Internetconnection.checkConnection(getActivity())) {
            Banner banner = this.view.findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        this.txtName = this.view.findViewById(R.id.user_name);
        this.user_profilepic = this.view.findViewById(R.id.user_profilepic);
        this.callNow = this.view.findViewById(R.id.callnow);
        this.user_profilepic.setOnClickListener(new btnUserProfilePicListner());
        this.callNow.setOnClickListener(new btnCallNowListner());
        return this.view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && requestCode == 101) {
            onSelectFromGalleryResult(data);
        }
    }

    //Select image from gallery method
    private void onSelectFromGalleryResult(Intent data) {
        try {
            selectedImageUri = data.getData();
            this.user_profilepic.setImageURI(selectedImageUri);
            this.bmyimage = saveImageInDB(selectedImageUri);
            getPath(selectedImageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Save image into db method
    private byte[] saveImageInDB(Uri selectedImageUri) {
        try {
            return getBytes(getActivity().openFileInput(String.valueOf(selectedImageUri)));
        } catch (IOException ioe) {
            Log.e("Hello1", "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            return null;
        }
    }

    //Get current image size method
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

    //Get current image path method
    private String getPath(Uri selectedImageUri) {
        Cursor cursor = getActivity().managedQuery(selectedImageUri, new String[]{"_data"}, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
