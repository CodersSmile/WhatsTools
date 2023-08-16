package com.whatstools.asciiFaces;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.whatstools.R;

class OtherRecycerAdaptersGallery extends RecyclerView.Adapter<OtherRecycerAdaptersGallery.MyViewHolder> {
    private String[] OtherAscii;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Share;
        TextView TextFaces;
        ImageView WhatsApp;

        MyViewHolder(View itemView) {
            super(itemView);
            this.TextFaces = itemView.findViewById(R.id.txt);
            this.WhatsApp = itemView.findViewById(R.id.whats);
            this.Share = itemView.findViewById(R.id.share);
        }
    }

    OtherRecycerAdaptersGallery(FragmentActivity activity, String[] otherAscii) {
        this.context = activity;
        this.OtherAscii = otherAscii;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listfacis, parent, false));
    }

    @SuppressLint({"LongLogTag"})
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.TextFaces.setText(this.OtherAscii[position]);
        holder.WhatsApp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String FACE = OtherRecycerAdaptersGallery.this.OtherAscii[position];
                Intent whatsappIntent = new Intent("android.intent.action.SEND");
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra("android.intent.extra.TEXT", FACE);
                try {
                    OtherRecycerAdaptersGallery.this.context.startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(OtherRecycerAdaptersGallery.this.context, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Share button clicked event
        holder.Share.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String FACE = OtherRecycerAdaptersGallery.this.OtherAscii[position];
                Intent whatsappIntent = new Intent("android.intent.action.SEND");
                whatsappIntent.setType("text/plain");
                whatsappIntent.putExtra("android.intent.extra.TEXT", FACE);
                try {
                    OtherRecycerAdaptersGallery.this.context.startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(OtherRecycerAdaptersGallery.this.context, "Some problems", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int getItemCount() {
        return (this.OtherAscii == null) ? 0 : this.OtherAscii.length;
    }
}
