package com.gzeinnumer.workmanagerfix;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.gzeinnumer.workmanagerfix.MainActivity.INTERVAL;

//optional
public class ServiceReceiver extends BroadcastReceiver {
    //akan di aktifkan ketika notifiasi di kirim, janga lupa daftarkan di manifest
    @Override
    public void onReceive(Context context, Intent intent) {

        new ServiceClass(context, INTERVAL);
    }
}
