package com.Syufei.bybook.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public class WeakRefHanlder extends Handler {
    /*
    * 防止内存泄漏得hanlder
    * */
    private WeakReference<Callback> mWeakRef;

    public WeakRefHanlder(Callback callback) {
        mWeakRef = new WeakReference<Callback>(callback);
    }

    public WeakRefHanlder(Callback callback, Looper looper) {
        super(looper);
        mWeakRef = new WeakReference<Callback>(callback);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        if (mWeakRef != null && mWeakRef.get() != null) {
            Callback callback = mWeakRef.get();
            callback.handleMessage(msg);
        }
    }
}
