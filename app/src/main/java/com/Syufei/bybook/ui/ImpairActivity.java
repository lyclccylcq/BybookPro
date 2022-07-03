package com.Syufei.bybook.ui;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.Syufei.bybook.R;
import com.Syufei.bybook.bean.AfterOrderBean;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.commonui.CommonDialog;
import com.Syufei.bybook.commonui.PopupDialog;
import com.Syufei.bybook.databinding.ActivityImpairBinding;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.ImpairRepo;
import com.Syufei.bybook.ui.dialog.ChooseCityDialog;
import com.Syufei.bybook.ui.dialog.ChooseDateDialog;
import com.Syufei.bybook.ui.dialog.ChooseMycarDialog;

public class ImpairActivity extends BaseActivity implements PopupDialog.onDismissListener, CommonDialog.OnClickBottomListener {
    private int select = 0;//1：上门取车  2：自主驾车
    private int type = -1;
    private ActivityImpairBinding activityImpairBinding;
    private int saleOrderId = -1;
    private CommonDialog confirmDialog;
    private CommonDialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getBundleExtra("impair_bundle");
        type = bundle.getInt("impair_type");
        activityImpairBinding = ActivityImpairBinding.inflate(getLayoutInflater());
        setTransparentStatusBar();
        setContentView(activityImpairBinding.getRoot());
        confirmDialog = new CommonDialog(this);
        confirmDialog.setTitle("提示");
        confirmDialog.setOnClickBottomListener(this);
        confirmDialog.setMessage("选择上门取车后工作人员会上门取车，并帮您完成整个维修/保养过程，您无需亲自到场，完成后工作人员会帮您把车开回原处（可能收取额外费用）");
        confirmDialog.setType("confirmDialog");
        errorDialog = new CommonDialog(this);
        errorDialog.setType("errorDialog");
        errorDialog.setSingle(true);
        errorDialog.setTitle("错误");
        errorDialog.setOnClickBottomListener(this);
        activityImpairBinding.impairActHelpcar.setOnClickListener(view -> {
            confirmDialog.show();
        });
        activityImpairBinding.impairActSelfcar.setOnClickListener(view -> {
            activityImpairBinding.impairActHelpcar.setBackground(getResources().getDrawable(R.drawable.round_bg_6));
            activityImpairBinding.impairActSelfcar.setBackground(getResources().getDrawable(R.drawable.round_bg_5));
            select = 2;
        });
        activityImpairBinding.impairActTop.topTv.setText("维修/保养");
        activityImpairBinding.impairActTop.titleBack.setOnClickListener(view -> {
            finish();
        });
        activityImpairBinding.impairActChooseDate.setOnClickListener(view -> {
            ChooseDateDialog chooseDateDialog = new ChooseDateDialog(ImpairActivity.this);
            chooseDateDialog.setDialogType("chooseDateDialog");
            chooseDateDialog.setListener(ImpairActivity.this);
            chooseDateDialog.show();
        });
        activityImpairBinding.impairActChooseCar.setOnClickListener(view -> {
            ChooseMycarDialog chooseMycarDialog = new ChooseMycarDialog(ImpairActivity.this);
            chooseMycarDialog.setDialogType("chooseCarDialog");
            chooseMycarDialog.setTitle("选择车辆");
            chooseMycarDialog.setListener(this);
            chooseMycarDialog.show();
        });
        activityImpairBinding.impairActChooseCity.setOnClickListener(view -> {
            ChooseCityDialog chooseCityDialog = new ChooseCityDialog(ImpairActivity.this);
            chooseCityDialog.setDialogType("chooseCityDialog");
            chooseCityDialog.setListener(this);
            chooseCityDialog.show();
        });
        activityImpairBinding.impairActCommitBtn.setOnClickListener(view -> {

            String address = activityImpairBinding.impairActChooseCityTv.getText().toString();
            if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(activityImpairBinding.impairActChooseCarTv.getText().toString())
                    && !TextUtils.isEmpty(activityImpairBinding.impairActChooseDateTv.getText().toString())
                    && !TextUtils.isEmpty(activityImpairBinding.impairActChoosePhoneTv.getText().toString())) {
                progressDialog.show();
                ImpairRepo.createAfterOrder(type, saleOrderId, address, new DataSuccessListenter() {
                    @Override
                    public void onDataSuccess(Object obj) {
                        progressDialog.dismiss();
                        AfterOrderBean.CreateAfterOrderRes res = (AfterOrderBean.CreateAfterOrderRes) obj;
                        if (res.getStatus().equals("success")) {
                            finish();
                        } else {
                            errorDialog.setMessage("网络错误，稍后重试");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        errorDialog.setMessage("网络错误，稍后重试");
                        progressDialog.dismiss();
                    }
                });
            } else {
                errorDialog.setMessage("信息不完整，请输入完整后重试");
                errorDialog.show();
            }
        });
    }

    @Override
    public void onDismiss(Bundle data, String type) {
        if (type.equals("chooseCarDialog")) {
            saleOrderId = data.getInt("after_sale_car_id");
            String carName = data.getString("after_sale_car_name", "");
            if (!TextUtils.isEmpty(carName)) {
                activityImpairBinding.impairActChooseCarTv.setText(carName);
            }
        } else if (type.equals("chooseDateDialog")) {
            int[] res = (int[]) data.get("date_res");
            boolean selected = (boolean) data.get("date_is_elect");
            if (res != null && selected == true) {
                int month=res[1];
                month+=1;
                activityImpairBinding.impairActChooseDateTv.setText(res[0] + "年" +month + "月" + res[2] + "日");
            }
        } else if (type.equals("chooseCityDialog")) {
            String city = data.getString("city", "");
            if (!TextUtils.isEmpty(city)) {
                activityImpairBinding.impairActChooseCityTv.setText(city);
            }
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        if (message.what == 1) {
            this.finish();
        }
        return super.handleMessage(message);
    }

    @Override
    public void onPositiveClick(String type) {
        if (type.equals("confirmDialog")) {
            confirmDialog.dismiss();
            activityImpairBinding.impairActHelpcar.setBackground(getResources().getDrawable(R.drawable.round_bg_5));
            activityImpairBinding.impairActSelfcar.setBackground(getResources().getDrawable(R.drawable.round_bg_6));
            select = 1;
        } else if (type.equals("errorDialog")) {
            errorDialog.dismiss();
        }
    }

    @Override
    public void onNegtiveClick(String type) {
        confirmDialog.dismiss();
    }
}