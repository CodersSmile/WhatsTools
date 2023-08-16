package com.whatstools.statusSaver;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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

public class VideoFragment extends Fragment {
    private ArrayList<FileModel> FilePathStrings;
    private VideoGridRecycerAdapter adapter;
    private TextView datatext;
    File file;
    private File imageRoot = new File(Environment.getExternalStorageDirectory() + "/Whatsapp/Media/.Statuses/");
    private File[] listFile;
    private RelativeLayout nodata;
    private RecyclerView recyclerView;
    View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_video_display, container, false);
        this.recyclerView = this.view.findViewById(R.id.video_list);
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
        this.recyclerView = this.view.findViewById(R.id.video_list);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        this.recyclerView.setHasFixedSize(true);
        this.adapter = new VideoGridRecycerAdapter(getActivity(), this.FilePathStrings);
        this.recyclerView.setAdapter(this.adapter);
        if (this.adapter.getItemCount() > 0) {
            this.nodata.setVisibility(View.INVISIBLE);
        } else {
            this.datatext.setText("First you seen WhatsApp Status video. You have not seen video yet.");
        }
    }

    private void fetchImageLocationAndName() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.file = new File(this.imageRoot + File.separator);
            Log.e("file location", this.file.toString());
        } else {
            Toast.makeText(getActivity(), "Error! No SDCARD Found!", Toast.LENGTH_SHORT).show();
        }
        this.FilePathStrings = new ArrayList();
        if (this.file.isDirectory()) {
            this.listFile = this.file.listFiles();
            Log.e("file length", this.listFile.length + "");
            if (this.listFile != null) {
                for (int i = 0; i < this.listFile.length; i++) {
                    String filepath = this.listFile[i].getAbsolutePath();
                    if (filepath.contains(".mp4")) {
                        FileModel fileModel = new FileModel();
                        fileModel.setImageFilePath(filepath);
                        fileModel.setImageChecked(Boolean.FALSE);
                        this.FilePathStrings.add(fileModel);
                    }
                    Log.e("file path string ", filepath);
                }
            }
        }
    }
}
