package com.Syufei.bybook.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.Syufei.bybook.bean.ManagerBean;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.databinding.ActivityManageTrybookBinding;
import com.Syufei.bybook.ui.adapter.ManageTrycarAdapter;
import com.Syufei.bybook.ui.viewmodel.ManageTrycarViewmodel;
import com.Syufei.bybook.util.UITools;

import java.util.List;

public class ManageTryBookActivity extends BaseActivity {
    private ManageTrycarViewmodel manageTrycarViewmodel;
    private ManageTrycarAdapter adapter;
    private ActivityManageTrybookBinding manageTryarBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manageTryarBinding=ActivityManageTrybookBinding.inflate(getLayoutInflater());
        manageTrycarViewmodel=new ViewModelProvider(this).get(ManageTrycarViewmodel.class);
        setContentView(manageTryarBinding.getRoot());
        adapter=new ManageTrycarAdapter(this);
        setTransparentStatusBar();
        adapter.setManageTrycarViewmodel(manageTrycarViewmodel);
        manageTryarBinding.manageTryCarRecycler.setAdapter(adapter);
        manageTryarBinding.manageTryCarRecycler.setLayoutManager(new LinearLayoutManager(this));
        manageTrycarViewmodel.getListLivedate().observe(this, new Observer<List<ManagerBean.TrybookList.Result>>() {
            @Override
            public void onChanged(List<ManagerBean.TrybookList.Result> results) {
                if(results!=null && results.size()==0){
                    UITools.showToast(ManageTryBookActivity.this,"没有更多数据了");
                }else{
                    adapter.setCarList(results);
                }
            }
        });
        manageTrycarViewmodel.getNetworkError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    manageTryarBinding.manageTryCarRecycler.setVisibility(View.GONE);
                    manageTryarBinding.manageTryCarNeterror.setVisibility(View.VISIBLE);
                }else{
                    manageTryarBinding.manageTryCarRecycler.setVisibility(View.VISIBLE);
                    manageTryarBinding.manageTryCarNeterror.setVisibility(View.GONE);
                }
            }
        });
        manageTryarBinding.manageTryCarNeterror.setOnClickListener(view -> {
            manageTrycarViewmodel.queryData();
        });
        manageTryarBinding.manageTryCarTopBar.titleBack.setOnClickListener(view -> {
            finish();
        });
        manageTryarBinding.manageTryCarTopBar.topTv.setText("试驾列表");
    }
}