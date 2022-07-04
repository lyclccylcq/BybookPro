package com.Syufei.bybook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.databinding.MineFragmentBinding;
import com.Syufei.bybook.ui.ImpairOrderActivity;
import com.Syufei.bybook.ui.MainActivity;
import com.Syufei.bybook.ui.ManagerActivity;
import com.Syufei.bybook.ui.NoticeActivity;
import com.Syufei.bybook.ui.OrderListActivity;
import com.Syufei.bybook.ui.dialog.ChooseMycarDialog;
import com.Syufei.bybook.ui.dialog.LoginDialog;

public class MineFragment extends Fragment implements LoginDialog.DialogListener{
    private MineFragmentBinding mineFragmentBinding;
    private MainActivity mAcitivty;

    public void setmAcitivty(MainActivity mAcitivty) {
        this.mAcitivty = mAcitivty;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        showLogin();
        mineFragmentBinding=MineFragmentBinding.inflate(inflater,container,false);
        updateUI();
        //顶部调登录
        mineFragmentBinding.mineTitle.mineTitleLoginMsg.setOnClickListener(view -> showLogin());
        mineFragmentBinding.mineTitle.mineTitleAvatar.setOnClickListener(view -> showLogin());
        //content部分
        mineFragmentBinding.mineContent.mineContentMycar.setOnClickListener(view -> {
            ChooseMycarDialog chooseMycarDialog=new ChooseMycarDialog(getContext());
            chooseMycarDialog.setTitle("我的书籍");
            chooseMycarDialog.show();
        });
        mineFragmentBinding.mineContent.mineContentBill.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), OrderListActivity.class);
            startActivity(intent);
        });
        mineFragmentBinding.mineContent.mineContentQuit.setOnClickListener(view -> {
            BaseActivity.logout(getContext());
            updateUI();
        });
        mineFragmentBinding.mineContent.mineContentScore.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), NoticeActivity.class);
            intent.putExtra("page_type",1);
            startActivity(intent);
        });
        mineFragmentBinding.mineContent.mineContentAbout.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(),NoticeActivity.class);
            intent.putExtra("page_type",2);
            startActivity(intent);
        });
        mineFragmentBinding.mineContent.mineContentImpair.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), ImpairOrderActivity.class);
            startActivity(intent);
        });
        mineFragmentBinding.mineTitle.switchBtn.setOnClickListener(view->{
            Intent intent=new Intent(getContext(), ManagerActivity.class);
            startActivity(intent);
            BaseActivity.logout(getContext());
            mAcitivty.finish();
        });
        return mineFragmentBinding.getRoot();
    }

    private void showLogin(){
        if (BaseActivity.checkLogin(getContext())==false){
            LoginDialog loginDialog=new LoginDialog(getContext(),0);
            loginDialog.setListener(this);
            loginDialog.show();
        }
    }

//    登录状态
    @Override
    public void onDismiss(boolean isSuccess,int type) {
        updateUI();
    }
    private void updateUI(){
        if (!BaseActivity.checkLogin(getContext())){
            mineFragmentBinding.mineTitle.mineTitleLoginUid.setVisibility(View.GONE);
            mineFragmentBinding.mineContent.mineContentQuit.setVisibility(View.GONE);
            mineFragmentBinding.mineTitle.mineTitleLoginMsg.setText("登录/注册");
        }else{
            mineFragmentBinding.mineTitle.mineTitleLoginUid.setVisibility(View.VISIBLE);
            mineFragmentBinding.mineContent.mineContentQuit.setVisibility(View.VISIBLE);
            mineFragmentBinding.mineTitle.mineTitleLoginMsg.setText("ByBook用户");
        }
    }
}
