package com.Syufei.bybook.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Syufei.bybook.bean.ManagerBean;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.commonui.PopupDialog;
import com.Syufei.bybook.databinding.ActivityManageAfterBinding;
import com.Syufei.bybook.ui.adapter.ManageAfterAdapter;
import com.Syufei.bybook.ui.dialog.FilterItemDialog;
import com.Syufei.bybook.ui.viewmodel.ManageAfterViewmodel;
import com.Syufei.bybook.util.UITools;

import java.util.ArrayList;
import java.util.List;

public class ManageAfterActivity extends BaseActivity implements PopupDialog.onDismissListener {
    private ActivityManageAfterBinding binding;
    private ManageAfterViewmodel viewmodel;
    private ManageAfterAdapter adapter;
    private FilterItemDialog filterItemDialog;//选择查询的订单类型，0代表换书单，1代表退书单
    private List<String> types;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityManageAfterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTransparentStatusBar();
        viewmodel=new ViewModelProvider(this).get(ManageAfterViewmodel.class);
        adapter=new ManageAfterAdapter(this);
        adapter.setManageAfterViewmodel(viewmodel);
        binding.manageAfterRecycler.setAdapter(adapter);
        binding.manageAfterRecycler.setLayoutManager(new LinearLayoutManager(this));
        filterItemDialog=new FilterItemDialog(this);
        filterItemDialog.setDialogType("filterItemDialog");
        filterItemDialog.setListener(this);
        types=new ArrayList<>();
        types.add("换书订单");
        types.add("退书订单");
        filterItemDialog.setItems(types);
        binding.manageAfterRefresh.setProgressBackgroundColorSchemeColor(Color.parseColor( "#ffffff"));
        binding.manageAfterRefresh.setColorSchemeColors(Color.parseColor("#00bcbc"));
        viewmodel.getTypeLivedata().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                viewmodel.queryData(integer.intValue());
            }
        });
        viewmodel.getListLiveData().observe(this, new Observer<List<ManagerBean.AfterOrderList.Result>>() {
            @Override
            public void onChanged(List<ManagerBean.AfterOrderList.Result> results) {
                binding.manageAfterRefresh.setRefreshing(false);
                adapter.setmList(results);
            }
        });
        viewmodel.getShowProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    showProgress();
                }else{
                    dismissProgress();
                }
            }
        });
//        viewmodel.getOffsetData().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                int offset=viewmodel.getOffsetData().getValue();
//                binding.manageTryCarPrevpage.setVisibility(offset==1? View.GONE : View.VISIBLE);
//                if (offset>=1){
//                    viewmodel.queryData(offset);
//                }
//            }
//        });
        viewmodel.getIsLastPage().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    binding.manageTryCarNextpage.setVisibility(View.GONE);
                    binding.manageAfterRefresh.setRefreshing(false);
                    UITools.showToast(ManageAfterActivity.this,"已经是最后一页了");
                }else {
                    binding.manageTryCarNextpage.setVisibility(View.VISIBLE);
                }
            }
        });
        viewmodel.getShowBottom().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.manageAfterBottom.setVisibility(aBoolean? View.VISIBLE:View.GONE);
                if (aBoolean){
                    binding.manageAfterBottomId.setText("订单Id："+viewmodel.getSelectId());
                    binding.manageAfterBottomPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }
        });
        //viewmodel.getTypeLivedata().setValue(0);
//        viewmodel.getOffsetData().setValue(1);
        binding.manageTryCarFilter.setOnClickListener(view -> {
            filterItemDialog.show();
        });
        binding.manageTryCarFilterIc.setOnClickListener(view -> {
            filterItemDialog.show();
        });
        binding.manageTryCarPrevpage.setOnClickListener(view -> {
            int offset=viewmodel.getOffset().get();
            if (offset>1){
                viewmodel.setOffset(offset-1);
            }
        });
        binding.manageTryCarNextpage.setOnClickListener(view -> {
            int offset=viewmodel.getOffset().get();
            viewmodel.setOffset(offset+1);
        });
        binding.manageAfterRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewmodel.queryData(viewmodel.getTypeLivedata().getValue());
            }
        });
        binding.manageAfterBottomClose.setOnClickListener(view -> {
            binding.manageAfterBottom.setVisibility(View.GONE);
        });
        binding.manageAfterBottomConfirm.setOnClickListener(view -> {
            String price=binding.manageAfterBottomPrice.getText().toString().trim();
            viewmodel.priceOrder(Double.valueOf(price),viewmodel.getSelectId());
        });
    }

    @Override
    public void onDismiss(Bundle data, String type) {
        if (type.equals("filterItemDialog")){
            String select_item= (String) data.get("select_item");
            if (select_item.equals("换书订单")){
                viewmodel.getTypeLivedata().setValue(0);
            }else if (select_item.equals("退书订单")){
                viewmodel.getTypeLivedata().setValue(1);
            }
        }
    }
}