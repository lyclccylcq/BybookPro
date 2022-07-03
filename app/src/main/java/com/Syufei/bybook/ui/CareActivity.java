package com.Syufei.bybook.ui;

import android.os.Bundle;

import com.Syufei.bybook.R;
import com.Syufei.bybook.common.BaseActivity;

public class CareActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentStatusBar();
        setContentView(R.layout.activity_impair);
    }
}