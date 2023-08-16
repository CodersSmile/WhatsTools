package com.whatstools.fackChat;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.fackChat.DataBaseDetails.DatabaseHelper;
import com.whatstools.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions.KeyboardListener;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class UserChat extends AppCompatActivity implements OnClickListener, OnItemClickListener {
    private static final String TAG = "USER Activity";
    public static Bitmap callImages;
    public static ListView chatlist;
    public static String tokenname;
    LinearLayout Attach;
    ImageView Call;
    LinearLayout More;
    LinearLayout VideoCall;
    LinearLayout attach;
    LinearLayout backmenu;
    Bitmap bitmap;
    LinearLayout camera;
    RelativeLayout chatbackground;
    ArrayList<String> chatid = new ArrayList<>();
    DatabaseHelper databaseHelper;
    EmojIconActions emojIcon;
    String imagepath;
    ArrayList<String> imagepathList = new ArrayList<>();
    ArrayList<String> ismessagetype = new ArrayList<>();
    String istext;
    EmojiconEditText message;
    ArrayList<String> messageArray = new ArrayList<>();
    RelativeLayout messageLayout;
    ImageView more_chat;
    ArrayList<String> msgstatuslist = new ArrayList<>();
    ArrayList<String> msgtime = new ArrayList<>();
    TextView name;
    String online;
    byte[] profile;
    ImageView receive;
    RelativeLayout recordlayout;
    ImageView send;
    String sender;
    ArrayList<String> senderUser = new ArrayList<>();
    String typing;
    Uri uri;
    ImageView user_icon;
    int user_id;
    LinearLayout userdetails;
    String username;
    TextView visibilitystatus;


    //When click on message this method call or when you write text on textfield its called
    private class btnMessageListner implements TextWatcher {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count > 0) {
                UserChat.this.ViewLayout();
                return;
            }
            UserChat.this.messageLayout.setVisibility(View.INVISIBLE);
            UserChat.this.recordlayout.setVisibility(View.VISIBLE);
            UserChat.this.camera.setVisibility(View.VISIBLE);
            UserChat.this.attach.setVisibility(View.VISIBLE);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            UserChat.this.messageLayout.setVisibility(View.INVISIBLE);
            UserChat.this.recordlayout.setVisibility(View.VISIBLE);
            UserChat.this.camera.setVisibility(View.VISIBLE);
            UserChat.this.attach.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {
        }
    }

    //Emoji Icon clicked method
    private class btnEmojiIconListner implements KeyboardListener {
        public void onKeyboardOpen() {
            Log.e(UserChat.TAG, "Keyboard opened!");
        }

        public void onKeyboardClose() {
            Log.e(UserChat.TAG, "Keyboard closed");
        }
    }

    //Call button clicked
    private class btnCallListner implements OnClickListener {
        public void onClick(View view) {
            UserChat.callImages = UserChat.this.getImagefromdatabase(UserChat.this.profile);
            Intent intent = new Intent(UserChat.this, Calls.class);
            intent.putExtra("NAME", UserChat.this.username);
            intent.putExtra("ID", 2);
            UserChat.this.startActivity(intent);
        }
    }

    //More button click listner
    private class btnMoreListner implements OnClickListener {

        public void onClick(View view) {
        }
    }

    //Videocall button click listner
    private class btnVideoCallListner implements OnClickListener {
        public void onClick(View view) {
        }
    }

    //Attached file button click listner
    private class btnAttachListner implements OnClickListener {
        public void onClick(View view) {
        }
    }

    @SuppressLint({"WrongViewCast"})
    @RequiresApi(api = 16)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
        this.message = findViewById(R.id.message);
        this.imagepath = tokenname;
        this.databaseHelper = new DatabaseHelper(this);
        this.user_id = getIntent().getExtras().getInt("USER_ID");
        this.username = getIntent().getExtras().getString("USER_NAME");
        this.online = getIntent().getExtras().getString("USER_ONLINE");
        this.typing = getIntent().getExtras().getString("USER_TYPING");
        this.Call = findViewById(R.id.imCall);
        this.recordlayout = findViewById(R.id.recordLayout);
        this.messageLayout = findViewById(R.id.messageLayout);
        this.name = findViewById(R.id.username);
        this.name.setText(this.username + "");
        this.profile = UserAdapter.userdetails.get(getIntent().getExtras().getInt("USER_PROFILE")).getBytes();
        this.user_icon = findViewById(R.id.user_icon);
        if (this.profile != null) {
            this.user_icon.setImageBitmap(getImagefromdatabase(this.profile));
        }
        this.visibilitystatus = findViewById(R.id.visibilitystatus);
        if (this.online.equals("online")) {
            this.visibilitystatus.setText("Online");
        }
        if (this.typing.equals("typing")) {
            this.visibilitystatus.setText("typing...");
        }
        this.More = findViewById(R.id.more);
        this.VideoCall = findViewById(R.id.videocall);
        this.Attach = findViewById(R.id.attach);
        this.camera = findViewById(R.id.camera);
        this.attach = findViewById(R.id.attach);
        this.send = findViewById(R.id.send);
        this.receive = findViewById(R.id.receive);
        this.backmenu = findViewById(R.id.backmenu);
        this.backmenu.setOnClickListener(this);
        this.recordlayout.setOnClickListener(this);
        chatlist = findViewById(R.id.chatlist);
        this.chatbackground = findViewById(R.id.chatbackground);
        if (this.imagepath != null) {
            try {
                this.bitmap = Media.getBitmap(getContentResolver(), FileProvider.getUriForFile(this, "android.arch.core.provider", new File(this.imagepath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.chatbackground.setBackgroundDrawable(new BitmapDrawable(this.bitmap));
        }
        this.userdetails = findViewById(R.id.userdetails);
        this.more_chat = findViewById(R.id.background);
        this.send.setOnClickListener(this);
        this.receive.setOnClickListener(this);
        this.camera.setOnClickListener(this);
        this.more_chat.setOnClickListener(this);
        this.userdetails.setOnClickListener(this);
        chatlist.setOnItemClickListener(this);
        this.messageLayout.setVisibility(View.INVISIBLE);
        DisplayChat();
        this.message.addTextChangedListener(new btnMessageListner());
        this.chatbackground = findViewById(R.id.chatbackground);
        ImageView emojiImageView = findViewById(R.id.emoji_btn);
        this.message = findViewById(R.id.message);
        this.emojIcon = new EmojIconActions(this, this.chatbackground, this.message, emojiImageView);
        this.emojIcon.ShowEmojIcon();
        this.emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.smiley);
        this.emojIcon.setKeyboardListener(new btnEmojiIconListner());
        this.Call.setOnClickListener(new btnCallListner());
        this.More.setOnClickListener(new btnMoreListner());
        this.VideoCall.setOnClickListener(new btnVideoCallListner());
        this.Attach.setOnClickListener(new btnAttachListner());
    }

    private Bitmap getImagefromdatabase(byte[] profile) {
        Bitmap bmp = BitmapFactory.decodeByteArray(profile, 0, profile.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.JPEG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


    //Display all your old chat method
    private void DisplayChat() {
        Cursor c = this.databaseHelper.getUserChatHistory(this.user_id + "");
        Log.d("Total Colounmn", c.getCount() + "");
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int id = c.getInt(c.getColumnIndex("chatid"));
            String sender = c.getString(c.getColumnIndex("sender"));
            String ismsg = c.getString(c.getColumnIndex("ismsg"));
            String msg = c.getString(c.getColumnIndex(NotificationCompat.CATEGORY_MESSAGE));
            String time = c.getString(c.getColumnIndex("time"));
            String status = c.getString(c.getColumnIndex("msgstatus"));
            String imagepath = c.getString(c.getColumnIndex("imagepath"));
            this.messageArray.add(msg);
            this.senderUser.add(sender);
            this.msgtime.add(time);
            this.msgstatuslist.add(status);
            this.chatid.add(id + "");
            this.ismessagetype.add(ismsg);
            this.imagepathList.add(imagepath);
            c.moveToNext();
            Log.i(TAG, "DisplayChat: " + this.chatid);
            chatlist.setAdapter(new ChatAdapter(this, this.messageArray, this.senderUser, this.msgtime, this.msgstatuslist, this.ismessagetype, this.imagepathList));
        }
    }

    private void ViewLayout() {
        this.messageLayout.setVisibility(View.VISIBLE);
        this.recordlayout.setVisibility(View.INVISIBLE);
        this.camera.setVisibility(View.INVISIBLE);
        this.attach.setVisibility(View.INVISIBLE);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backmenu:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                StartAppAd.onBackPressed(this);
                return;
            case R.id.camera:
                OpenDialog();
                return;
            case R.id.receive:
                this.sender = "no";
                this.istext = "yes";
                this.imagepath = " ";
                SaveChatUser(this.user_id, this.sender, this.istext, this.imagepath);
                return;
            case R.id.recordLayout:
                CharSequence[] items = new CharSequence[]{"We are still working on this option"};
                Builder builder = new Builder(this);
                builder.setTitle("Under Construction.");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return;
            case R.id.send:
                this.sender = "yes";
                this.istext = "yes";
                this.imagepath = " ";
                SaveChatUser(this.user_id, this.sender, this.istext, this.imagepath);
                return;
            case R.id.userdetails:
                Intent editchatuser = new Intent(this, EditChatUserProfile.class);
                editchatuser.putExtra("USER_ID", this.user_id);
                startActivity(editchatuser);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                return;
            default:
                return;
        }
    }


    //Its a method of opendialog
    private void OpenDialog() {
        final CharSequence[] items = new CharSequence[]{"Me", "My Friend"};
        Builder builder = new Builder(this).setTitle("Who?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Me")) {
                    UserChat.this.sender = "yes";
                    UserChat.this.SelectImageFromgallery();
                } else if (items[item].equals("My Friend")) {
                    UserChat.this.sender = "no";
                    UserChat.this.SelectImageFromgallery();
                }
            }
        });
        builder.show();
    }

    private void SelectImageFromgallery() {
        startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 101);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 101) {
            onSelectFromGalleryResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        try {
            this.uri = data.getData();
            this.istext = "no";
            this.imagepath = this.uri.toString();
            SaveChatUser(this.user_id, this.sender, this.istext, this.imagepath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveChatUser(int user_id, String sender, String ismsg, String imagepath) {
        int i;
        String msgstatus = "read";
        Calendar calendar = Calendar.getInstance();
        String strDate = new SimpleDateFormat("HH:mm").format(calendar.getTime());
        Log.d("CURRENT TIME", strDate);
        String msg = this.message.getText().toString();
        this.databaseHelper.saveUserChat(user_id + "", sender, ismsg, msg, strDate, msgstatus, imagepath);
        Cursor c = this.databaseHelper.getUserChatHistory(user_id + "");
        int to = c.getCount();
        Log.d("Total Colounmn", to + "");
        c.moveToFirst();
        for (i = 0; i < c.getCount(); i++) {
            this.chatid.add(c.getInt(c.getColumnIndex("chatid")) + "");
        }
        chatlist.smoothScrollToPosition(to);
        this.message.setText("");
        this.messageArray.add(msg);
        this.senderUser.add(sender);
        this.msgtime.add(strDate);
        this.msgstatuslist.add(msgstatus);
        this.ismessagetype.add(ismsg);
        this.imagepathList.add(imagepath);
        ChatAdapter chatAdapter = new ChatAdapter(this, this.messageArray, this.senderUser, this.msgtime, this.msgstatuslist, this.ismessagetype, this.imagepathList);
        chatlist.setAdapter(chatAdapter);
        i = chatAdapter.getCount();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent editmessageintent = new Intent(this, EditMessageActivity.class);
        editmessageintent.putExtra("MESSAGE", this.messageArray.get(i));
        editmessageintent.putExtra("SENDER", this.senderUser.get(i));
        editmessageintent.putExtra("STATUS", this.msgstatuslist.get(i));
        editmessageintent.putExtra("CHATID", this.chatid.get(i));
        editmessageintent.putExtra("POSITION", this.user_id);
        editmessageintent.putExtra("USER_NAME", this.username);
        editmessageintent.putExtra("USER_ONLINE", this.online);
        editmessageintent.putExtra("USER_TYPING", this.typing);
        editmessageintent.putExtra("USER_PROFILE", this.profile);
        startActivity(editmessageintent);
        finish();
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }
}
