package com.gzeinnumer.workmanagerfix;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.gzeinnumer.workmanagerfix.example1_notification.ServiceWorker;
import com.gzeinnumer.workmanagerfix.example2_rxjava.ServiceWorker2;
import com.gzeinnumer.workmanagerfix.example3_retrofit.ServiceWorker3;

import java.util.concurrent.TimeUnit;

public class ServiceClass {
    public ServiceClass(Context applicationContext, int interval) {
        start(applicationContext, interval);
    }

    private void start(Context context, int INTERVAL) {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .setRequiresBatteryNotLow(true)
//                .setRequiresCharging(true)
//                .setRequiresStorageNotLow(true)
                .build();

        PeriodicWorkRequest w1 = new PeriodicWorkRequest.Builder(ServiceWorker.class, INTERVAL, TimeUnit.MINUTES).setConstraints(constraints).build();
        WorkManager.getInstance(context).enqueue(w1);

        WorkRequest w2 = new PeriodicWorkRequest.Builder(ServiceWorker2.class, INTERVAL, TimeUnit.MINUTES).setConstraints(constraints).build();
        WorkManager.getInstance(context).enqueue(w2);

        WorkRequest w3 = new PeriodicWorkRequest.Builder(ServiceWorker3.class, INTERVAL, TimeUnit.MINUTES).setConstraints(constraints).build();
        WorkManager.getInstance(context).enqueue(w3);
    }
}
