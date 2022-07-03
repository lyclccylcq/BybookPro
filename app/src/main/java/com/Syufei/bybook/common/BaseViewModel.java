package com.Syufei.bybook.common;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    private String mTag="BaseViewModel";

    private Bundle mData;
    public BaseViewModel(Bundle bundle){this.mData=bundle;}

    public Bundle getmData() {
        return mData;
    }
}
