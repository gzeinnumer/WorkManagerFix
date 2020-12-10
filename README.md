# WorkManagerFix
 Work Manager sudah fix, ada optional BroadcastRecaiver. abaikan jita tidak ingin pakai

-[gradle]
```gradle
implementation "androidx.work:work-runtime:2.3.0"
implementation "androidx.work:work-rxjava2:2.3.0"

implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
implementation 'io.reactivex.rxjava2:rxjava:2.2.9'
implementation 'com.squareup.okhttp3:logging-interceptor:3.10.0'
```

- [Manifest]
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest >

    <uses-permission android:name="android.permission.INTERNET"/>

    <application >

<!--        optional abaikan jika tidak menggunakan notification-->
<!--        <receiver android:name=".ServiceReceiver" android:process=".remote"/>-->

    </application>

</manifest>
```

- [MainActivity](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/MainActivity.java)
```java
public class MainActivity extends AppCompatActivity {

    public static int INTERVAL = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ServiceClass(getApplicationContext(), INTERVAL);
    }
}
```

- [ServiceClass](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/ServiceClass.java)
```java
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
```

- [ServiceWorker](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/example1_notification/ServiceWorker.java)
```java
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
```

- [ServiceWorker2](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/example2_rxjava/ServiceWorker2.java)
- [ServiceWorker3](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/example3_retrofit/ServiceWorker3.java)
  - [ArticlesItem](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/example3_retrofit/model/ArticlesItem.java)
  - [ResponseNews](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/example3_retrofit/model/ResponseNews.java)
  - [Source](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/example3_retrofit/model/Source.java)
  - [ApiService](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/example3_retrofit/api/ApiService.java)
  - [RetroServer](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/example3_retrofit/api/RetroServer.java)
- [ServiceReceiver](https://github.com/gzeinnumer/WorkManagerFix/blob/master/app/src/main/java/com/gzeinnumer/workmanagerfix/ServiceReceiver.java)

---

```
Copyright 2020 M. Fadli Zein
```