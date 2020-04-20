package com.android.weatherjecpack.Utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class AppExecutors {

    public static final int THREAD_NUM = 3;
    private Executor diskIO;
    private Executor mainThread;
    private Executor networkIO;

    @Inject
    public AppExecutors(){
        diskIO = Executors.newSingleThreadExecutor();
        networkIO = Executors.newScheduledThreadPool(THREAD_NUM);
        mainThread = new MainThread();
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    private static class MainThread implements Executor{
        Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    }

}
