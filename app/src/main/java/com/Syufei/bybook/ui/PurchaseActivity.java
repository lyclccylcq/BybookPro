package com.Syufei.bybook.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.lzhihua.bycar.bean.CarBean;
import com.lzhihua.bycar.common.BaseActivity;
import com.lzhihua.bycar.databinding.ActivityPurchaseBinding;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.TryCarRepo;
import com.lzhihua.bycar.ui.adapter.PurchaseAdapter;

import java.util.List;

public class PurchaseActivity extends BaseActivity {
    private ActivityPurchaseBinding activityPurchaseBinding;
    private PurchaseAdapter purchaseAdapter;
    private List<CarBean.CarList.CarListSubData> carList;


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
        TryCarRepo.getCarlist(new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                CarBean.CarList carBean=(CarBean.CarList) obj;
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