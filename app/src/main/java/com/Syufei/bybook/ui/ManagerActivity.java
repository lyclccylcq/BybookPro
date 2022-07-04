package com.Syufei.bybook.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.Syufei.bybook.databinding.ActivityManagerBinding;
import com.Syufei.bybook.network.NetworkUtil;
import com.Syufei.bybook.ui.dialog.LoginDialog;
import com.Syufei.bybook.ui.dialog.ManagerLoginDialog;

public class ManagerActivity extends AppCompatActivity implements LoginDialog.DialogListener {
    private ManagerLoginDialog managerLoginDialog;
    private ActivityManagerBinding managerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managerBinding=ActivityManagerBinding.inflate(getLayoutInflater());
        setContentView(managerBinding.getRoot());
        setTransparentStatusBar();
        boolean isLogin = getIntent().getBooleanExtra("is_login_manager", false);
        if (!isLogin) {
            managerLoginDialog = new ManagerLoginDialog(this);
            managerLoginDialog.setListener(this);
            managerLoginDialog.show();
        }
        managerBinding.managerMainBill.setVisibility(View.VISIBLE);
        managerBinding.managerMainMoney.setVisibility(View.VISIBLE);
        managerBinding.managerMainImpair.setVisibility(View.VISIBLE);
        managerBinding.managerMainTrycar.setVisibility(View.VISIBLE);
        NetworkUtil.init(this);
        managerBinding.managerMainBill.setOnClickListener(view -> {
            Intent intent=new Intent(ManagerActivity.this,ManageOrderActivity.class);
            startActivity(intent);
        });
        managerBinding.managerMainImpair.setOnClickListener(view -> {
            Intent intent=new Intent(ManagerActivity.this,ManageAfterActivity.class);
            startActivity(intent);
        });
        managerBinding.managerMainTrycar.setOnClickListener(view -> {
            Intent intent=new Intent(ManagerActivity.this, ManageTryBookActivity.class);
            startActivity(intent);
        });
        managerBinding.managerMainMoney.setOnClickListener(view -> {
            Intent intent=new Intent(ManagerActivity.this,ManageCarActivity.class);
            startActivity(intent);
        });
    }
    private void setTransparentStatusBar() {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = this.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            this.getWindow().setStatusBarColor(Color.TRANSPARENT);
            //4.4到5.0
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = this.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    @Override
    public void onDismiss(boolean isSuccess, int type) {
        if (isSuccess && type==0){
            managerBinding.managerMainBill.setVisibility(View.VISIBLE);
            managerBinding.managerMainMoney.setVisibility(View.VISIBLE);
            managerBinding.managerMainImpair.setVisibility(View.VISIBLE);
        }
    }
}