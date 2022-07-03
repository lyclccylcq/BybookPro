package com.Syufei.bybook.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lzhihua.bycar.bean.ManagerBean;
import com.lzhihua.bycar.common.BaseActivity;
import com.lzhihua.bycar.databinding.ActivityManageTryarBinding;
import com.lzhihua.bycar.ui.adapter.ManageTrycarAdapter;
import com.lzhihua.bycar.ui.viewmodel.ManageTrycarViewmodel;
import com.lzhihua.bycar.util.UITools;

import java.util.List;

public class ManageTryBookActivity extends BaseActivity {
    private ManageTrycarViewmodel manageTrycarViewmodel;
    private ManageTrycarAdapter adapter;
    private ActivityManageTryarBinding manageTryarBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manageTryarBinding=ActivityManageTryarBinding.inflate(getLayoutInflater());
        manageTrycarViewmodel=new ViewModelProvider(this).get(ManageTrycarViewmodel.class);
        setContentView(manageTryarBinding.getRoot());
        adapter=new ManageTrycarAdapter(this);
        setTransparentStatusBar();
        adapter.setManageTrycarViewmodel(manageTrycarViewmodel);
        manageTryarBinding.manageTryCarRecycler.setAdapter(adapter);
        manageTryarBinding.manageTryCarRecycler.setLayoutManager(new LinearLayoutManager(this));
        manageTrycarViewmodel.getListLivedate().observe(this, new Observer<List<ManagerBean.TrycarList.Result>>() {
            @Override
            public void onChanged(List<ManagerBean.TrycarList.Result> results) {
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