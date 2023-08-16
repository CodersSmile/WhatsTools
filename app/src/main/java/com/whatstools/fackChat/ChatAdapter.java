package com.whatstools.fackChat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.whatstools.R;

import java.util.ArrayList;

class ChatAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<String> imagpath;
    private ArrayList<String> ismessagetype;
    private ArrayList<String> messageArray;
    private ArrayList<String> msgstatuslist;
    private ArrayList<String> sendUser;
    private ArrayList<String> time;

    private class Holder {
        LinearLayout imageLayoutreceiver;
        LinearLayout imageLayoutsender;
        ImageView msgread;
        ImageView msgreceive;
        ImageView msgsend;
        TextView receive;
        LinearLayout receiverLayout;
        TextView receivetime;
        TextView receivetimeimage;
        ImageView receverimage;
        TextView send;
        LinearLayout senderLayout;
        ImageView senderimage;
        TextView sendertimeimage;
        TextView sendtime;

    }

    public ChatAdapter(Context context, ArrayList<String> messageArray, ArrayList<String> senderUser, ArrayList<String> time, ArrayList<String> msgstatuslist, ArrayList<String> ismessagetype, ArrayList<String> imagpath) {
        this.context = context;
        this.messageArray = messageArray;
        this.sendUser = senderUser;
        this.time = time;
        this.msgstatuslist = msgstatuslist;
        this.ismessagetype = ismessagetype;
        this.imagpath = imagpath;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.messageArray.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.custlistview, null);
        holder.send = rowView.findViewById(R.id.send);
        holder.receive = rowView.findViewById(R.id.receive);
        holder.sendtime = rowView.findViewById(R.id.sendtime);
        holder.receivetime = rowView.findViewById(R.id.receivetime);
        holder.senderLayout = rowView.findViewById(R.id.senderLayout);
        holder.receiverLayout = rowView.findViewById(R.id.receiverLayout);
        holder.msgsend = rowView.findViewById(R.id.msgsend);
        holder.msgreceive = rowView.findViewById(R.id.msgreceive);
        holder.msgread = rowView.findViewById(R.id.msgread);
        holder.imageLayoutsender = rowView.findViewById(R.id.imageLayoutsender);
        holder.imageLayoutreceiver = rowView.findViewById(R.id.imageLayoutreceiver);
        holder.senderimage = rowView.findViewById(R.id.senderimage);
        holder.receverimage = rowView.findViewById(R.id.receverimage);
        holder.receivetimeimage = rowView.findViewById(R.id.receivetimeimage);
        holder.sendertimeimage = rowView.findViewById(R.id.sendertimeimage);
        String sendermsg = this.sendUser.get(i);
        if (this.ismessagetype.get(i).equals("yes")) {
            if (sendermsg.equals("yes")) {
                holder.receiverLayout.setVisibility(View.INVISIBLE);
                holder.imageLayoutsender.setVisibility(View.INVISIBLE);
                holder.imageLayoutreceiver.setVisibility(View.INVISIBLE);
                holder.senderLayout.setVisibility(View.VISIBLE);
                holder.send.setText(this.messageArray.get(i));
                holder.sendtime.setText(this.time.get(i));
            } else {
                holder.imageLayoutsender.setVisibility(View.INVISIBLE);
                holder.imageLayoutreceiver.setVisibility(View.INVISIBLE);
                holder.senderLayout.setVisibility(View.INVISIBLE);
                holder.receiverLayout.setVisibility(View.VISIBLE);
                holder.receive.setText(this.messageArray.get(i));
                holder.receivetime.setText(this.time.get(i));
            }
        } else if (sendermsg.equals("yes")) {
            holder.receiverLayout.setVisibility(View.INVISIBLE);
            holder.imageLayoutreceiver.setVisibility(View.INVISIBLE);
            holder.senderLayout.setVisibility(View.INVISIBLE);
            holder.imageLayoutsender.setVisibility(View.VISIBLE);
            holder.senderimage.requestLayout();
            holder.senderimage.getLayoutParams().height = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            holder.senderimage.getLayoutParams().width = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            holder.senderimage.setScaleType(ScaleType.FIT_XY);
            holder.senderimage.setImageURI(Uri.parse(this.imagpath.get(i)));
            holder.sendertimeimage.setText(this.time.get(i));
        } else {
            holder.receiverLayout.setVisibility(View.INVISIBLE);
            holder.senderLayout.setVisibility(View.INVISIBLE);
            holder.imageLayoutsender.setVisibility(View.INVISIBLE);
            holder.imageLayoutreceiver.setVisibility(View.VISIBLE);
            holder.receverimage.requestLayout();
            holder.receverimage.getLayoutParams().height = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            holder.receverimage.getLayoutParams().width = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            holder.receverimage.setScaleType(ScaleType.FIT_XY);
            holder.receverimage.setImageURI(Uri.parse(this.imagpath.get(i)));
            holder.receivetimeimage.setText(this.time.get(i));
        }
        String msgstatus = this.msgstatuslist.get(i);
        if (msgstatus.equals("send")) {
            holder.msgreceive.setVisibility(View.INVISIBLE);
            holder.msgread.setVisibility(View.INVISIBLE);
            holder.msgsend.setVisibility(View.VISIBLE);
        } else if (msgstatus.equals("receive")) {
            holder.msgread.setVisibility(View.INVISIBLE);
            holder.msgsend.setVisibility(View.INVISIBLE);
            holder.msgreceive.setVisibility(View.VISIBLE);
        } else {
            holder.msgsend.setVisibility(View.INVISIBLE);
            holder.msgreceive.setVisibility(View.INVISIBLE);
            holder.msgread.setVisibility(View.VISIBLE);
        }
        return rowView;
    }

}
