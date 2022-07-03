package com.Syufei.bybook.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzhihua.bycar.R;
import com.lzhihua.bycar.bean.CarBean;
import com.lzhihua.bycar.common.BaseActivity;
import com.lzhihua.bycar.databinding.ActivityTryCarListBinding;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.TryCarRepo;
import com.lzhihua.bycar.util.CommonTools;
import com.lzhihua.bycar.util.UITools;

import java.util.List;

public class TryCarListActivity extends BaseActivity {
    private ActivityTryCarListBinding tryCarListBinding;
    private List<CarBean.TryCarList.Result> resultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWhiteStatusBar();
        tryCarListBinding = ActivityTryCarListBinding.inflate(getLayoutInflater());
        setContentView(tryCarListBinding.getRoot());
        tryCarListBinding.tryCarListTop.titleBack.setOnClickListener(view -> {
            finish();
        });
        queryData();
    }
    private void queryData(){
        TryCarRepo.trycarList(10, 1, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                CarBean.TryCarList carList = (CarBean.TryCarList) obj;
                resultList=carList.getData().getList();
                updateUI();
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void updateUI() {
        if (resultList==null || resultList.size()==0){
            tryCarListBinding.tryCarListMissing.setVisibility(View.VISIBLE);
            tryCarListBinding.tryCarListContainer.setVisibility(View.GONE);
        }else{
            tryCarListBinding.tryCarListMissing.setVisibility(View.GONE);
            tryCarListBinding.tryCarListContainer.setVisibility(View.VISIBLE);
            tryCarListBinding.tryCarListContainer.removeAllViews();
            for(CarBean.TryCarList.Result result:resultList){
                View view= LayoutInflater.from(this).inflate(R.layout.order_list_card,null,false);
                TextView carName=view.findViewById(R.id.order_list_car_name);
                LinearLayout cancelBtn=view.findViewById(R.id.order_list_car_btn);
                TextView statusTv=view.findViewById(R.id.order_list_car_status);
                TextView priceTv=view.findViewById(R.id.order_list_car_price);
                TextView addressTv=view.findViewById(R.id.order_list_car_address);
                TextView updateTv=view.findViewById(R.id.order_list_car_update_time);
                TextView createTv=view.findViewById(R.id.order_list_car_create_time);
                ImageView img=view.findViewById(R.id.order_list_car_img);
                img.setImageDrawable(UITools.getDrawable(TryCarListActivity.this.getResources(),result.getCar().getName()));
                carName.setText("NIO "+result.getCar().getName());
                addressTv.setText("试驾地点："+result.getAddress());
                createTv.setText("创建时间："+ CommonTools.formatUtcTime(result.getCreateTime()));
                cancelBtn.setTag(result);
                LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp1.topMargin=UITools.dip2px(20);
                lp1.bottomMargin=UITools.dip2px(20);
                view.setLayoutParams(lp1);
                cancelBtn.setOnClickListener(view1 -> {
                    progressDialog.show();
                    CarBean.TryCarList.Result result1=(CarBean.TryCarList.Result) view1.getTag();
                    TryCarRepo.cancelTrycar(result1.getId(), new DataSuccessListenter() {
                        @Override
                        public void onDataSuccess(Object obj) {
                            UITools.showToast(TryCarListActivity.this,"取消成功");
                            int i=0;
                            for(;i<resultList.size();i++){
                                CarBean.TryCarList.Result current=resultList.get(i);
                                if (result1.getId()==result1.getId()){
                                    break;
                                }
                            }
                            resultList.remove(i);
                            updateUI();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(String error) {
                            UITools.showToast(TryCarListActivity.this,"取消失败，稍后重试");
                            progressDialog.dismiss();
                        }
                    });
                });
                priceTv.setVisibility(View.GONE);
                statusTv.setVisibility(View.GONE);
                updateTv.setVisibility(View.GONE);
                tryCarListBinding.tryCarListContainer.addView(view);
            }
        }
    }
}