package com.whatstools.statusSaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.whatstools.R;

import java.io.File;
import java.util.ArrayList;

class SavedImageAdapter extends PagerAdapter {
    private ArrayList<FileModel> ImagesList = ImagesSavedFragment.FilePathStrings;
    private Context context;
    private LayoutInflater mLayoutInflater;

    SavedImageAdapter(Context context) {
        this.context = context;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.ImagesList.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = this.mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.getimageview);
        String imageFilePath = this.ImagesList.get(position).getImageFilePath();
        imageView.setScaleType(ScaleType.CENTER_INSIDE);
        Glide.with(this.context).load(new File(imageFilePath)).fitCenter().into(imageView);
        container.addView(itemView);
        return itemView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
