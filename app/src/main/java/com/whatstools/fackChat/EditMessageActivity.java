package com.whatstools.fackChat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.whatstools.fackChat.DataBaseDetails.DatabaseHelper;
import com.whatstools.R;

public class EditMessageActivity extends AppCompatActivity implements OnClickListener {
    String chatid;
    DatabaseHelper databaseHelper;
    TextView delete_usermessage;
    TextView edit_usermessage;
    LinearLayout menu;
    String message;
    RadioGroup messagestatus;
    EditText msgedit;
    String online;
    int position;
    byte[] profile;
    String sender;
    RadioGroup senduser;
    String status;
    String typing;
    String username;

    //Send message from user listner
    private class btnSendUserListner implements OnCheckedChangeListener {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.me) {
                EditMessageActivity.this.sender = "yes";
                return;
            }
            EditMessageActivity.this.sender = "no";
        }
    }

    //Status listner
    private class btnMessageStatusListner implements OnCheckedChangeListener {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.send) {
                EditMessageActivity.this.status = "send";
            } else if (checkedId == R.id.receive) {
                EditMessageActivity.this.status = "receive";
            } else {
                EditMessageActivity.this.status = "read";
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_message);
        Bundle bundle = getIntent().getExtras();
        this.databaseHelper = new DatabaseHelper(this);
        if (bundle != null) {
            this.position = bundle.getInt("POSITION");
            this.message = bundle.getString("MESSAGE");
            this.sender = bundle.getString("SENDER");
            this.status = bundle.getString("STATUS");
            this.chatid = bundle.getString("CHATID");
            this.username = getIntent().getExtras().getString("USER_NAME");
            this.profile = getIntent().getExtras().getByteArray("USER_PROFILE");
            this.online = getIntent().getExtras().getString("USER_ONLINE");
            this.typing = getIntent().getExtras().getString("USER_TYPING");
        }
        this.msgedit = findViewById(R.id.msgedit);
        this.edit_usermessage = findViewById(R.id.edit_usermessage);
        this.delete_usermessage = findViewById(R.id.delete_usermessage);
        this.senduser = findViewById(R.id.senduser);
        this.messagestatus = findViewById(R.id.messagestatus);
        this.menu = findViewById(R.id.menu);
        this.menu.setOnClickListener(this);
        this.edit_usermessage.setOnClickListener(this);
        this.delete_usermessage.setOnClickListener(this);
        this.msgedit.setText(this.message + "");
        if (this.sender.equals("yes")) {
            this.senduser.check(R.id.me);
        } else {
            this.senduser.check(R.id.myfriend);
        }
        if (this.status.equals("send")) {
            this.messagestatus.check(R.id.send);
        } else if (this.status.equals("receive")) {
            this.messagestatus.check(R.id.receive);
        } else {
            this.messagestatus.check(R.id.read);
        }
        this.senduser.setOnCheckedChangeListener(new btnSendUserListner());
        this.messagestatus.setOnCheckedChangeListener(new btnMessageStatusListner());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_usermessage:
                this.databaseHelper.DeleteMessage(this.chatid);
                Intent editmessageintent = new Intent(this, UserChat.class);
                editmessageintent.putExtra("USER_ID", this.position);
                editmessageintent.putExtra("USER_NAME", this.username);
                editmessageintent.putExtra("USER_ONLINE", this.online);
                editmessageintent.putExtra("USER_TYPING", this.typing);
                editmessageintent.putExtra("USER_PROFILE", this.profile);
                startActivity(editmessageintent);
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                return;
            case R.id.edit_usermessage:
                this.message = this.msgedit.getText().toString();
                this.databaseHelper.UpdateMessageDetails(this.chatid, this.sender, this.message, this.status);
                Intent intent = new Intent(this, UserChat.class);
                intent.putExtra("USER_ID", this.position);
                intent.putExtra("USER_NAME", this.username);
                intent.putExtra("USER_ONLINE", this.online);
                intent.putExtra("USER_TYPING", this.typing);
                intent.putExtra("USER_PROFILE", this.profile);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                return;
            case R.id.menu:
                Intent intentback = new Intent(this, UserChat.class);
                intentback.putExtra("USER_ID", this.position);
                intentback.putExtra("USER_NAME", this.username);
                intentback.putExtra("USER_ONLINE", this.online);
                intentback.putExtra("USER_TYPING", this.typing);
                intentback.putExtra("USER_PROFILE", this.profile);
                startActivity(intentback);
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                return;
            default:
                return;
        }
    }
}
