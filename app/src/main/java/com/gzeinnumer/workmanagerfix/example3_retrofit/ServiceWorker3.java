package com.gzeinnumer.workmanagerfix.example3_retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.gzeinnumer.workmanagerfix.example3_retrofit.api.RetroServer;
import com.gzeinnumer.workmanagerfix.example3_retrofit.model.ResponseNews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.Response;

public class ServiceWorker3 extends Worker {
    public ServiceWorker3(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private static final String TAG = "NotificationWorker_";

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork_fix: kepanggil3");
        for (int i=0; i<10; i++){
            setRetrofit(i);
        }
        return Result.success();
    }

    @SuppressLint("CheckResult")
    private void setRetrofit(final int i) {
        RetroServer.getInstance()
                .getBeritaFlowable("us", "e5430ac2a413408aaafdf60bfa27a874")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, Response<ResponseNews>>() {
                    @Override
                    public Response<ResponseNews> apply(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "apply: " + throwable);
                        return null;
                    }
                })
                .subscribe(new Consumer<Response<ResponseNews>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void accept(Response<ResponseNews> listResponse) throws Exception {
                        ResponseNews data = listResponse.body(); //json body
                        int code = listResponse.code(); //200
                        String msg = listResponse.message(); //SUCCESS

                        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss.SSSXXX", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        Log.d(TAG, "accept:_"+i+"_"+code+"_"+currentDateandTime);
                    }
                });
    }

}
