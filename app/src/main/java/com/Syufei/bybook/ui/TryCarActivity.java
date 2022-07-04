package com.Syufei.bybook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.commonui.PopupDialog;
import com.Syufei.bybook.databinding.ActivityTryBookBinding;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.TryBookRepo;
import com.Syufei.bybook.ui.dialog.ChooseCarDialog;
import com.Syufei.bybook.ui.dialog.ChooseCityDialog;
import com.Syufei.bybook.util.UITools;

public class TryCarActivity extends BaseActivity implements PopupDialog.onDismissListener {
    private ActivityTryBookBinding activityTryBookBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentStatusBar();
        activityTryBookBinding = activityTryBookBinding.inflate(getLayoutInflater());
        setContentView(activityTryBookBinding.getRoot());
        activityTryBookBinding.tryCarTop.titleBack.setOnClickListener(view -> finish());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.rightMargin = UITools.dip2px( 20);
        activityTryBookBinding.tryCarTop.topTv.setLayoutParams(lp);
        activityTryBookBinding.tryCarTop.topTv.setText("试读页数");
        activityTryBookBinding.tryCarTop.topTv.setOnClickListener(view -> {
            Intent intent=new Intent(TryCarActivity.this,TryCarListActivity.class);
            startActivity(intent);
        });
        activityTryBookBinding.tryCarChooseCar.setOnClickListener(view -> {
            ChooseCarDialog chooseCarDialog=new ChooseCarDialog(TryCarActivity.this);
            chooseCarDialog.setDialogType("chooseCarDialog");
            chooseCarDialog.setListener(this);
            chooseCarDialog.show();
        });
        activityTryBookBinding.tryCarChooseCity.setOnClickListener(view -> {
            ChooseCityDialog chooseCityDialog=new ChooseCityDialog(TryCarActivity.this);
            chooseCityDialog.setDialogType("chooseCarDialog");
            chooseCityDialog.setListener(this);
            chooseCityDialog.show();
        });
        activityTryBookBinding.tryCarChooseToTry.setOnClickListener(view -> {
            String address= activityTryBookBinding.tryCarChooseCityName.getText().toString().trim();
            String phone= activityTryBookBinding.tryCarChoosePhoneName.getText().toString().trim();
            BookBean.BookList.BookListSubData carBean= (BookBean.BookList.BookListSubData) view.getTag();
            if(!TextUtils.isEmpty(address) && !TextUtils.isEmpty(phone) && carBean!=null){
                progressDialog.show();
                TryBookRepo.createTryBook(carBean.getId(), address, phone, new DataSuccessListenter() {
                    @Override
                    public void onDataSuccess(Object obj) {
                        UITools.showToast(TryCarActivity.this,"创建成功");
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onDismiss(Bundle data,String type) {
        if (type.equals("chooseCarDialog")){
            String city=data.getString("city","");
            if(!TextUtils.isEmpty(city)){
                activityTryBookBinding.tryCarChooseCityName.setText(city);
            }
            BookBean.BookList.BookListSubData carListSubData= (BookBean.BookList.BookListSubData) data.getSerializable("car_bean");
            if (carListSubData!=null){
                activityTryBookBinding.tryCarChooseCarName.setText(carListSubData.getName());
                activityTryBookBinding.tryCarChooseToTry.setTag(carListSubData);
            }
        }
    }
}