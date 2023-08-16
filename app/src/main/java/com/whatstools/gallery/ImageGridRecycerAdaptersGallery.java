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

public class ImageGridRecycerAdaptersGallery extends RecyclerView.Adapter<ImageGridRecycerAdaptersGallery.MyViewHolder> {
    static ArrayList<FileModels> fileModelArrayList;
    private int activity_type;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.img);
        }
    }

    ImageGridRecycerAdaptersGallery(Context context, ArrayList<FileModels> filePathStrings, int TYPE) {
        this.context = context;
        this.activity_type = TYPE;
        fileModelArrayList = filePathStrings;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_for_image, parent, false));
    }

    @SuppressLint({"LongLogTag"})
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) this.context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        FileModels fileModel = new FileModels();
        fileModel = fileModelArrayList.get(position);
        String imageFilePath = fileModel.getImageFilePath();
        Log.e("file path in adapter " + position + "  ", imageFilePath);
        Log.e("file image is visiblity or not", fileModel.getImageChecked() + "");
        if (imageFilePath != null) {
            Glide.with(this.context).load(new File(imageFilePath)).override((width - 10) / 3, 180).fitCenter().centerCrop().into(holder.img);
            holder.img.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(ImageGridRecycerAdaptersGallery.this.context, ImageViewersGallery.class);
                    i.putExtra("type", ImageGridRecycerAdaptersGallery.this.activity_type);
                    i.putExtra("Position", position);
                    i.putExtra("Vplay", ImageGridRecycerAdaptersGallery.fileModelArrayList.get(position).getImageFilePath());
                    ImageGridRecycerAdaptersGallery.this.context.startActivity(i);
                    StringBuilder stringBuilder = new StringBuilder();
                    Log.e("position of recycler view", stringBuilder.append(ImageGridRecycerAdaptersGallery.fileModelArrayList.get(position).getImageFilePath()).append("").toString());
                }
            });
        }
    }

    public int getItemCount() {
        return (fileModelArrayList == null) ? 0 : fileModelArrayList.size();
    }
}
