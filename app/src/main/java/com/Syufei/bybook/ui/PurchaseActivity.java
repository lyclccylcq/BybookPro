package com.Syufei.bybook.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.databinding.ActivityPurchaseBinding;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.TryBookRepo;
import com.Syufei.bybook.ui.adapter.PurchaseAdapter;

import java.util.List;

public class PurchaseActivity extends BaseActivity {
    private ActivityPurchaseBinding activityPurchaseBinding;
    private PurchaseAdapter purchaseAdapter;
    private List<BookBean.BookList.BookListSubData> carList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentStatusBar();

        activityPurchaseBinding = ActivityPurchaseBinding.inflate(getLayoutInflater());
        activityPurchaseBinding.purchaseTopBar.titleBack.setOnClickListener(view -> {
            finish();
        });
//        activityPurchaseBinding.purchaseTopBar.topTv.setText("选购车辆");
        activityPurchaseBinding.purchaseTopBar.topTv1.setText("我的订单");
        activityPurchaseBinding.purchaseTopBar.topTv1.setOnClickListener(view -> {
            Intent intent=new Intent(PurchaseActivity.this,OrderListActivity.class);
            startActivity(intent);
        });
        setContentView(activityPurchaseBinding.getRoot());
        purchaseAdapter=new PurchaseAdapter(this);
        activityPurchaseBinding.purchaseRecyclerView.setAdapter(purchaseAdapter);
        activityPurchaseBinding.purchaseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        purchaseAdapter.setCarList(carList);
        progressDialog.show();
        TryBookRepo.getBooklist(new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                BookBean.BookList carBean=(BookBean.BookList) obj;
                carList=carBean.getData().getList();
                purchaseAdapter.setCarList(carList);
                progressDialog.dismiss();
            }

            @Override
            public void onError(String error) {
                progressDialog.dismiss();
            }
        });
    }


}