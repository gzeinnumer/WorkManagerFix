package com.gzeinnumer.workmanagerfix.example1_notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.gzeinnumer.workmanagerfix.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceWorker extends Worker {
    public ServiceWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private static final String TAG = "NotificationWorker_";

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork_fix: kepanggil1");
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        showNotification("WorkManager fix", currentDateandTime);

        return Result.success();
    }

    private void showNotification(String task, String desc) {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "task_channel";
        String channelName = "task_name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(task)
                .setContentText(desc)
                .setGroup("com.gzeinnumer.workmanagerfix")
                .setSmallIcon(R.mipmap.ic_launcher);
        manager.notify(1, builder.build());
    }
}
