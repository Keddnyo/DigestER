package io.github.keddnyo.digester.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface AsyncTask {
    /**
     * Creates a new thread for async task call
     */
    ExecutorService executorService = Executors.newCachedThreadPool();
    /**
     * Used for execute code on UI thread from another thread
     */
    Handler handler = new Handler((Looper.getMainLooper()));
}
