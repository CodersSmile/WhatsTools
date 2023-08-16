package com.whatstools.fackChat.DataBaseDetails;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context, "UserProfile", null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table user_info(uid INTEGER PRIMARY KEY ,uname TEXT,ustatus TEXT,uonline TEXT,utyping TEXT,uprofile BLOG)");
        sqLiteDatabase.execSQL("create table chat_info(chatid INTEGER PRIMARY KEY,uid TEXT,ismsg TEXT,sender TEXT, msg TEXT,imagepath TEXT,time TEXT,msgstatus TEXT)");
        Log.d("Database", "Table Created table....");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user_info");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS chat_info");
        onCreate(sqLiteDatabase);
    }

    public void InsertStudentDetails(String username, String userstatus, String useronline, String usertyping, byte[] bmyimage) {
        this.sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("uname", username);
        cv.put("ustatus", userstatus);
        cv.put("uonline", useronline);
        cv.put("utyping", usertyping);
        cv.put("uprofile", bmyimage);
        this.sqLiteDatabase.insert("user_info", null, cv);
        Log.d("Database", "Record.....]]]]] Created table....");
        this.sqLiteDatabase.close();
    }

    @SuppressLint({"LongLogTag"})
    public Cursor ViewUserList() {
        this.sqLiteDatabase = getWritableDatabase();
        Cursor cursor = this.sqLiteDatabase.rawQuery("select * from user_info", null);
        Log.d("Datasend", "Data send to user");
        return cursor;
    }

    public void saveUserChat(String user_id, String sender, String ismsg, String msg, String strDate, String msgstatus, String imagepath) {
        this.sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("uid", user_id);
        cv.put("sender", sender);
        cv.put("ismsg", ismsg);
        cv.put(NotificationCompat.CATEGORY_MESSAGE, msg);
        cv.put("time", strDate);
        cv.put("msgstatus", msgstatus);
        cv.put("imagepath", imagepath);
        this.sqLiteDatabase.insert("chat_info", null, cv);
        Log.d("Database", "Record.....]]]]] Created table....");
        this.sqLiteDatabase.close();
    }

    public Cursor getUserChatHistory(String s) {
        this.sqLiteDatabase = getWritableDatabase();
        return this.sqLiteDatabase.rawQuery("select * from chat_info where uid ='" + s + "'", null);
    }

    public void UpdateMessageDetails(String position, String sender, String message, String status) {
        this.sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sender", sender);
        values.put(NotificationCompat.CATEGORY_MESSAGE, message);
        values.put("msgstatus", status);
        this.sqLiteDatabase.update("chat_info", values, "chatid=" + position, null);
    }

    public Cursor getUserHistory(String s) {
        this.sqLiteDatabase = getWritableDatabase();
        return this.sqLiteDatabase.rawQuery("select * from user_info where uid ='" + s + "'", null);
    }

    public void getUserDetailsUpdate(int user_id, String uname, String status, String isonline, String istyping, byte[] bmyimage) {
        this.sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uname", uname);
        values.put("ustatus", status);
        values.put("uonline", isonline);
        values.put("utyping", istyping);
        values.put("uprofile", bmyimage);
        this.sqLiteDatabase.update("user_info", values, "uid=" + user_id, null);
    }

    public void DeleteMessage(String chat_id) {
        this.sqLiteDatabase = getWritableDatabase();
        this.sqLiteDatabase.execSQL("DELETE FROM chat_info WHERE chatid = '" + chat_id + "'");
    }

    public void DeleteUserProfile(int user_id) {
        this.sqLiteDatabase = getWritableDatabase();
        this.sqLiteDatabase.execSQL("DELETE FROM user_info WHERE uid = '" + user_id + "'");
        this.sqLiteDatabase.execSQL("DELETE FROM chat_info WHERE uid = '" + user_id + "'");
    }
}
