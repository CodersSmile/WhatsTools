package com.whatstools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Internetconnection {
    public static boolean checkConnection(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && (activeNetworkInfo.getType() == 1 || activeNetworkInfo.getType() == 0);
    }
}
