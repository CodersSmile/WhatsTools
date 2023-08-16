package com.whatstools.directChat;

import android.content.Context;
import android.telephony.TelephonyManager;

class Helper {

    //Get current localisation
    static String getCurrentLocale(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getNetworkCountryIso();
        }
        return null;
    }
}
