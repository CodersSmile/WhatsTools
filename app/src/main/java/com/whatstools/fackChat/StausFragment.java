package com.whatstools.fackChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.startapp.android.publish.ads.banner.Mrec;
import com.whatstools.Internetconnection;
import com.whatstools.R;

public class StausFragment extends Fragment {
    View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_status, container, false);
        if (!Internetconnection.checkConnection(getActivity())) {
            Mrec banner = this.view.findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        return this.view;
    }
}
