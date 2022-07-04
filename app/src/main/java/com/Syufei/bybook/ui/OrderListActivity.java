package com.Syufei.bybook.ui;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.Syufei.bybook.bean.OrderBean;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.databinding.OrderListBinding;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.OrderRepo;
import com.Syufei.bybook.ui.adapter.OrderListAdapter;
import com.Syufei.bybook.ui.presenter.UIShowListener;

import java.util.List;

public class OrderListActivity extends BaseActivity implements UIShowListener {
    private OrderListBinding orderListBinding;
    private OrderListAdapter orderListAdapter;
    private List<OrderBean.OrderList.Result> orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderListBinding=OrderListBinding.inflate(getLayoutInflater());
        setContentView(orderListBinding.getRoot());
        setWhiteStatusBar();
        orderListBinding.orderListBack.setOnClickListener(view -> finish());

        orderListAdapter=new OrderListAdapter(this);
        orderListAdapter.setOrderList(orderList);
        orderListAdapter.setUiShowListener(this);
        orderListBinding.orderListRecycler.setLayoutManager(new LinearLayoutManager(this));
        orderListBinding.orderListRecycler.setAdapter(orderListAdapter);
        requestData();
    }

    @Override
    public void showProgress() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void dismissProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void requestData() {
        OrderRepo.getOrderList(10, 1, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                OrderBean.OrderList orderBean=(OrderBean.OrderList) obj;
                orderList=orderBean.getData().getList();
                orderListAdapter.setOrderList(orderList);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}