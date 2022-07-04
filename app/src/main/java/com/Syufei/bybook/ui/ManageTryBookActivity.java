package com.Syufei.bybook.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.Syufei.bybook.bean.ManagerBean;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.databinding.ActivityManageTrybookBinding;
import com.Syufei.bybook.ui.adapter.ManageTrybookAdapter;
import com.Syufei.bybook.ui.viewmodel.ManageTrybookViewmodel;
import com.Syufei.bybook.util.UITools;

import java.util.List;

public class ManageTryBookActivity extends BaseActivity {
    private ManageTrybookViewmodel manageTrybookViewmodel;
    private ManageTrybookAdapter adapter;
    private ActivityManageTrybookBinding manageTrybookBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manageTrybookBinding=ActivityManageTrybookBinding.inflate(getLayoutInflater());
        manageTrybookViewmodel =new ViewModelProvider(this).get(ManageTrybookViewmodel.class);
        setContentView(manageTrybookBinding.getRoot());
        adapter=new ManageTrybookAdapter(this);
        setTransparentStatusBar();
        adapter.setManageTrycarViewmodel(manageTrybookViewmodel);
        manageTrybookBinding.manageTryCarRecycler.setAdapter(adapter);
        manageTrybookBinding.manageTryCarRecycler.setLayoutManager(new LinearLayoutManager(this));
        manageTrybookViewmodel.getListLivedate().observe(this, new Observer<List<ManagerBean.TrybookList.Result>>() {
            @Override
            public void onChanged(List<ManagerBean.TrybookList.Result> results) {
                if(results!=null && results.size()==0){
                    UITools.showToast(ManageTryBookActivity.this,"没有更多数据了");
                }else{
                    adapter.setCarList(results);
                }
            }
        });
        manageTrybookViewmodel.getNetworkError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    manageTrybookBinding.manageTryCarRecycler.setVisibility(View.GONE);
                    manageTrybookBinding.manageTryCarNeterror.setVisibility(View.VISIBLE);
                }else{
                    manageTrybookBinding.manageTryCarRecycler.setVisibility(View.VISIBLE);
                    manageTrybookBinding.manageTryCarNeterror.setVisibility(View.GONE);
                }
            }
        });
        manageTrybookBinding.manageTryCarNeterror.setOnClickListener(view -> {
            manageTrybookViewmodel.queryData();
        });
        manageTrybookBinding.manageTryCarTopBar.titleBack.setOnClickListener(view -> {
            finish();
        });
        manageTrybookBinding.manageTryCarTopBar.topTv.setText("试读列表");
    }
}