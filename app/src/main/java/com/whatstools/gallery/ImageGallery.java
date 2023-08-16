package com.whatstools.gallery;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whatstools.R;

import java.io.File;
import java.util.ArrayList;

public class ImageGallery extends Fragment {
    static ArrayList<FileModels> FilePathStrings;
    private ImageGridRecycerAdaptersGallery adapter;
    private TextView datatext;
    private File file;
    private String fileNames;
    private String filepath;
    private File imageRoot = new File(Environment.getExternalStorageDirectory() + "/Whatsapp/Media/Whatsapp Images/");
    private File[] listFile;
    private RelativeLayout nodata;
    private RecyclerView recyclerView;
    private View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.image_display, container, false);
        this.nodata = this.view.findViewById(R.id.nodata);
        this.datatext = this.view.findViewById(R.id.text);
        setRecyclerView();
        return this.view;
    }

    public void onResume() {
        super.onResume();
        setRecyclerView();
    }

    private void setRecyclerView() {
        fetchImageLocationAndName();
        this.recyclerView = this.view.findViewById(R.id.imgGridRecyclerView);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        this.recyclerView.setHasFixedSize(true);
        this.adapter = new ImageGridRecycerAdaptersGallery(getActivity(), FilePathStrings, 72);
        this.recyclerView.setAdapter(this.adapter);
        if (this.adapter.getItemCount() > 0) {
            this.nodata.setVisibility(View.INVISIBLE);
        } else {
            this.datatext.setText("You have not download any Images from WhatsApp!!!");
        }
    }

    private void fetchImageLocationAndName() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.file = new File(this.imageRoot + File.separator);
        } else {
            Toast.makeText(getActivity(), "Error! No SDCARD Found!", Toast.LENGTH_SHORT).show();
        }
        if (this.file.isDirectory()) {
            FilePathStrings = new ArrayList();
            this.listFile = this.file.listFiles();
            if (this.listFile != null) {
                for (int i = 0; i < this.listFile.length; i++) {
                    this.filepath = this.listFile[i].getAbsolutePath();
                    this.fileNames = this.listFile[i].getName();
                    if (this.fileNames.endsWith(".jpg") || this.fileNames.endsWith(".jpeg") || this.fileNames.endsWith(".png") || this.fileNames.endsWith(".gif")) {
                        FileModels fileModel = new FileModels();
                        fileModel.setImageFilePath(this.filepath);
                        fileModel.setImageFileName(this.fileNames);
                        fileModel.setImageChecked(Boolean.FALSE);
                        FilePathStrings.add(fileModel);
                    }
                }
            }
        }
    }
}
