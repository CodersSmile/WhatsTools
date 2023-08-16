package com.whatstools.fackChat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.github.clans.fab.FloatingActionButton;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.fackChat.DataBaseDetails.DatabaseHelper;
import com.whatstools.fackChat.DataBaseDetails.UserDetails;
import com.whatstools.R;

import java.util.ArrayList;

public class ChatsFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener {
    private static ArrayList<UserDetails> arrayList;
    private Cursor objCursor;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton materialDesignFAM;
    private UserAdapter userAdapter;
    private ListView userList;
    View view;


    //Fab button click listener for new chat
    private class btnFamListner implements OnClickListener {
        public void onClick(View view) {
            ChatsFragment.this.startActivity(new Intent(ChatsFragment.this.getActivity(), ChatProfile.class));
            ChatsFragment.this.LoadAd();
        }
    }

    //Button dialog no listener
    private class btnDialogNoListner implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
        }
    }

    //Rate us Dialog method
    private class btnDialogRateUsListner implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            ChatsFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + ChatsFragment.this.getActivity().getPackageName())));
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_chat, container, false);
        this.databaseHelper = new DatabaseHelper(getActivity());
        this.materialDesignFAM = this.view.findViewById(R.id.material_design_android_floating_action_menu);
        this.userList = this.view.findViewById(R.id.userlist);
        CallList();
        this.materialDesignFAM.setOnClickListener(new btnFamListner());
        return this.view;
    }

    //Get recent call list method
    private void CallList() {
        GetUserDetails();
        this.userList.setOnItemClickListener(this);
        this.userList.setOnItemLongClickListener(this);
    }

    //Get user details method
    private void GetUserDetails() {
        this.objCursor = this.databaseHelper.ViewUserList();
        arrayList = new ArrayList();
        Log.d("Total Colounmn", this.objCursor.getCount() + "");
        this.objCursor.moveToFirst();
        for (int i = 0; i < this.objCursor.getCount(); i++) {
            int id = this.objCursor.getInt(this.objCursor.getColumnIndex("uid"));
            String name = this.objCursor.getString(this.objCursor.getColumnIndex("uname"));
            String status = this.objCursor.getString(this.objCursor.getColumnIndex("ustatus"));
            String typing = this.objCursor.getString(this.objCursor.getColumnIndex("utyping"));
            String online = this.objCursor.getString(this.objCursor.getColumnIndex("uonline"));
            byte[] blob = this.objCursor.getBlob(this.objCursor.getColumnIndex("uprofile"));
            UserDetails userDetails = new UserDetails();
            userDetails.setUid(id);
            userDetails.setUname(name);
            userDetails.setUstatus(status);
            userDetails.setUonline(online);
            userDetails.setUtyping(typing);
            userDetails.setBytes(blob);
            arrayList.add(userDetails);
            this.objCursor.moveToNext();
            this.userAdapter = new UserAdapter(getActivity(), arrayList);
            this.userList.setAdapter(this.userAdapter);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), UserChat.class);
        UserDetails userlist = arrayList.get(i);
        Log.i("ContentValues", "SEND ID: " + userlist.getUid());
        intent.putExtra("USER_ID", userlist.getUid());
        intent.putExtra("USER_NAME", userlist.getUname());
        intent.putExtra("USER_ONLINE", userlist.getUonline());
        intent.putExtra("USER_TYPING", userlist.getUtyping());
        intent.putExtra("USER_PROFILE", i);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        CallDialog(i);
        return true;
    }

    //Call dialog method
    private void CallDialog(final int i) {
        final UserDetails userDetails = arrayList.get(i);
        Log.i("ContentValues", "SEND ID: " + userDetails.getUid());
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Delete Conversation.");
        alertDialog.setMessage("Are you sure you want to delete this conversation?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ChatsFragment.this.databaseHelper.DeleteUserProfile(userDetails.getUid());
                ChatsFragment.arrayList.remove(i);
                ChatsFragment.this.userAdapter.notifyDataSetChanged();
            }
        });
        alertDialog.setNegativeButton("NO", new btnDialogNoListner());
        alertDialog.setNeutralButton("Rate Us", new btnDialogRateUsListner());
        alertDialog.show();
    }

    private void LoadAd() {
        StartAppAd.showAd(getActivity());
    }
}
