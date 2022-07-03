package com.Syufei.bybook.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.lzhihua.bycar.bean.ManagerBean;
import com.lzhihua.bycar.common.BaseActivity;
import com.lzhihua.bycar.databinding.ActivityManageOrderBinding;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.ManagerRepo;
import com.lzhihua.bycar.ui.adapter.ManagerOrderAdapter;
import com.lzhihua.bycar.ui.presenter.UIShowListener;
import com.lzhihua.bycar.util.UITools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ManageOrderActivity extends BaseActivity implements UIShowListener {
    private ActivityManageOrderBinding activityManageOrderBinding;
    private ProgressDialog progressDialog;
    private ManagerOrderAdapter managerOrderAdapter;
    private AtomicInteger offset;
    private List<ManagerBean.SaleList.Result> resultList;
    private List<String> SpinString;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManageOrderBinding = ActivityManageOrderBinding.inflate(getLayoutInflater());
        offset = new AtomicInteger(1);
        progressDialog = new ProgressDialog(this);
        setContentView(activityManageOrderBinding.getRoot());
        setTransparentStatusBar();
        managerOrderAdapter = new ManagerOrderAdapter(this);
        managerOrderAdapter.setListener(this);
        SpinString = new ArrayList<>();
        SpinString.add("筛选");
        SpinString.add("待支付");
        SpinString.add("待处理");
        SpinString.add("处理中");
        SpinString.add("已完成");
        SpinString.add("已取消");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityManageOrderBinding.managerOrderRecycler.setLayoutManager(new LinearLayoutManager(this));
        activityManageOrderBinding.managerOrderRecycler.setAdapter(managerOrderAdapter);
        activityManageOrderBinding.manageOrderSpinner.setAdapter(adapter);
        activityManageOrderBinding.manageOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                managerOrderAdapter.setResultList(filterOrder(adapter.getItem(i),resultList));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        querySaleList();
        activityManageOrderBinding.manageOrderBack.setOnClickListener(view -> {
            finish();
        });

    }

    private void querySaleList() {
        showProgress();
        ManagerRepo.GetOrderList(10, offset.get(), new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                ManagerBean.SaleList saleList = (ManagerBean.SaleList) obj;
                if (saleList.getData().getList().size() != 0) {
                    if (resultList == null) {
                        resultList = saleList.getData().getList();
                    } else {
                        for (ManagerBean.SaleList.Result result : saleList.getData().getList()) {
                            resultList.add(result);
                        }
                    }
                    managerOrderAdapter.setResultList(resultList);
                    activityManageOrderBinding.manageOrderSpinner.setSelection(0);
                    offset.incrementAndGet();
                } else {
                    UITools.showToast(ManageOrderActivity.this, "没有更多数据了");
                }
                dismissProgress();
            }

            @Override
            public void onError(String error) {
                dismissProgress();
            }
        });
    }

    private List<ManagerBean.SaleList.Result> filterOrder(String status, List<ManagerBean.SaleList.Result> list) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("待支付", 0);
        map.put("待处理", 1);
        map.put("处理中", 2);
        map.put("已完成", 3);
        map.put("已取消", -1);
        List<ManagerBean.SaleList.Result> filterList = new ArrayList<>();
        if (!status.equals("筛选") && list!=null && list.size()!=0) {
            filterList.clear();
            int index = map.get(status);
            for (ManagerBean.SaleList.Result result : list) {
                if (result.getStatus() == index) {
                    filterList.add(result);
                }
            }
        }else{
            filterList=list;
        }
        return filterList;
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
        querySaleList();
    }
}