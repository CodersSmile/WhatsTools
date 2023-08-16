package com.whatstools.gallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whatstools.R;

import java.io.File;
import java.util.ArrayList;

public class VideoGridRecycerAdaptersGallery extends RecyclerView.Adapter<VideoGridRecycerAdaptersGallery.MyViewHolder> {
    private int activity_type;
    private Context context;
    private ArrayList<FileModels> fileModelArrayList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        MyViewHolder(View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.img);
        }
    }

    VideoGridRecycerAdaptersGallery(Context context, ArrayList<FileModels> filePathStrings) {
        this.context = context;
        this.fileModelArrayList = filePathStrings;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_for_image_recycler, parent, false));
    }

    @SuppressLint({"LongLogTag"})
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) this.context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        FileModels fileModel = new FileModels();
        fileModel = this.fileModelArrayList.get(position);
        String imageFilePath = fileModel.getImageFilePath();
        Log.e("file path in adapter " + position + "  ", imageFilePath);
        Log.e("file image is visiblity or not", fileModel.getImageChecked() + "");
        if (imageFilePath != null) {
            Glide.with(this.context).load(new File(imageFilePath)).override((width - 10) / 3, 180).fitCenter().centerCrop().into(holder.img);
            holder.img.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(VideoGridRecycerAdaptersGallery.this.context, VideoPlayersGallery.class);
                    i.putExtra("type", VideoGridRecycerAdaptersGallery.this.activity_type);
                    i.putExtra("Vplay", VideoGridRecycerAdaptersGallery.this.fileModelArrayList.get(position).getImageFilePath());
                    VideoGridRecycerAdaptersGallery.this.context.startActivity(i);
                }
            });
            Log.e("position of recycler view", position + "");
        }
    }

    public int getItemCount() {
        return (fileModelArrayList == null) ? 0 : fileModelArrayList.size();
    }
}
