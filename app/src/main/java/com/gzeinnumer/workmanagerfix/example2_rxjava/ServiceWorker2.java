package com.gzeinnumer.workmanagerfix.example2_rxjava;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ServiceWorker2 extends Worker {
    public ServiceWorker2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private static final String TAG = "NotificationWorker_";

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork_fix: kepanggil2");
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, "doWork: kepanggil_2 " + i);
            setLog();
        }

        return Result.success();
    }

    private void setLog() {
        final StringBuilder str = new StringBuilder();

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                str.append("2. Value Default").append("\n");
                setView(str);

                emitter.onNext("Hello Zein");
//                emitter.onNext(null); // contoh error
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                str.append("1. Loading onSubscribe").append("\n");
                setView(str);
            }

            @Override
            public void onNext(@NonNull String s) {
                str.append("3. onNext ").append(s).append("\n");
                setView(str);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                str.append("3. onError ").append(e.getMessage()).append("\n");
                setView(str);
            }

            @Override
            public void onComplete() {
                str.append("4. Loading onComplete").append("\n");
                setView(str);
            }
        });
    }

    private void setView(StringBuilder str) {
        Log.d(TAG, "setView: " + str.toString());
    }
}
