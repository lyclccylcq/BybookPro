package com.Syufei.bybook.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Syufei.bybook.R;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.databinding.ActivityMainBinding;
import com.Syufei.bybook.ui.dialog.LoginDialog;
import com.Syufei.bybook.ui.fragment.CarFragment;
import com.Syufei.bybook.ui.fragment.ImpairFragment;
import com.Syufei.bybook.ui.fragment.MineFragment;
import com.Syufei.bybook.ui.fragment.MoreFragment;

public class MainActivity extends BaseActivity implements LoginDialog.DialogListener {
    private ActivityMainBinding mainBinding;
    private int currentIndex=0;//1:more  2:car   3:impair    4:mine
    private FragmentManager fragmentManager;
    private Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        fragmentManager=getSupportFragmentManager();
        resources=getResources();
        initBottom();
        onBottomClick(2);
    }

    private void initBottom(){
        mainBinding.mainBottom.bottomMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBottomClick(1);
            }
        });
        mainBinding.mainBottom.bottomCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBottomClick(2);
            }
        });
        mainBinding.mainBottom.bottomImpair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBottomClick(3);
            }
        });
        mainBinding.mainBottom.bottomMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBottomClick(4);
            }
        });
    }
    private void onBottomClick(int index){
        if(currentIndex!=index) {
            resetImage(currentIndex);
            switch (index){
                case 1:
                    currentIndex=1;
                    mainBinding.mainBottom.bottomMoreIv.setImageDrawable(resources.getDrawable(R.drawable.ic_more_select));
                    replaceFragment(new MoreFragment());
                    break;
                case 2:
                    currentIndex=2;
                    mainBinding.mainBottom.bottomCarIv.setImageDrawable(resources.getDrawable(R.drawable.ic_car_select));
                    CarFragment carFragment=new CarFragment();
                    replaceFragment(carFragment);
                    break;
                case 3:
                    currentIndex=3;
                    mainBinding.mainBottom.bottomImpairIv.setImageDrawable(resources.getDrawable(R.drawable.ic_impair_select));
                    ImpairFragment impairFragment=new ImpairFragment();
                    replaceFragment(impairFragment);
                    break;
                case 4:
                    currentIndex=4;
                    mainBinding.mainBottom.bottomMineIv.setImageDrawable(resources.getDrawable(R.drawable.ic_mine_select));
                    MineFragment mineFragment= new MineFragment();
                    mineFragment.setmAcitivty(this);
                    replaceFragment(mineFragment);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTransparentStatusBar();
        checkLogin(this);
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container,fragment).commit();
    }

    private void resetImage(int index){
        switch (index){
            case 0:
                break;
            case 1:
                mainBinding.mainBottom.bottomMoreIv.setImageDrawable(resources.getDrawable(R.drawable.ic_more_not_select));
                break;
            case 2:
                mainBinding.mainBottom.bottomCarIv.setImageDrawable(resources.getDrawable(R.drawable.ic_car_not_select));
                break;
            case 3:
                mainBinding.mainBottom.bottomImpairIv.setImageDrawable(resources.getDrawable(R.drawable.ic_impair_not_select));
                break;
            case 4:
                mainBinding.mainBottom.bottomMineIv.setImageDrawable(resources.getDrawable(R.drawable.ic_mine_not_select));
                break;
        }
    }


    @Override
    public void onDismiss(boolean isSuccess,int type) {

    }
}