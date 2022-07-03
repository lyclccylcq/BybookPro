package com.Syufei.bybook.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.lzhihua.bycar.databinding.CarFragmentBinding;
import com.lzhihua.bycar.ui.PurchaseActivity;
import com.lzhihua.bycar.ui.TryCarActivity;
import com.lzhihua.bycar.util.AnimationTools;

public class CarFragment extends Fragment{
    private CarFragmentBinding carFragmentBinding;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        carFragmentBinding=CarFragmentBinding.inflate(inflater,container,false);
        carFragmentBinding.carFragScrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Rect rect = new Rect();
                if (!carFragmentBinding.carFragmentPurchase.carFragPurchaseTv1.getGlobalVisibleRect(rect)){
                    if(carFragmentBinding.carFragmentTopView.getRoot().getVisibility()==View.GONE){
                        carFragmentBinding.carFragmentTopView.getRoot().startAnimation(AnimationTools.getInstance(getContext()).alphaAniShow);
                    }
                    carFragmentBinding.carFragmentTopView.getRoot().setVisibility(View.VISIBLE);

                }else{
                    carFragmentBinding.carFragmentTopView.getRoot().setVisibility(View.GONE);

                }

            }
        });
        int height=getContext().getResources().getDisplayMetrics().heightPixels;
        int width=getContext().getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width,height);
        carFragmentBinding.carFragmentPurchase.getRoot().setLayoutParams(lp);
        carFragmentBinding.carFragmentTopView.carFragTopBuyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PurchaseActivity.class);
            }
        });
        carFragmentBinding.carFragmentTopView.carFragTopTryCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TryCarActivity.class);
            }
        });
        carFragmentBinding.carFragmentPurchase.carFragPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PurchaseActivity.class);
            }
        });
        carFragmentBinding.carFragmentPurchase.carFragPurchase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PurchaseActivity.class);
            }
        });
        carFragmentBinding.carFragmentTryCar.carFragTryCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TryCarActivity.class);
            }
        });
        carFragmentBinding.carFragmentPurchase.carFragKnowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PurchaseActivity.class);
            }
        });
        return carFragmentBinding.getRoot();
    }


    private void startActivity(Class target){
        Intent intent=new Intent(getContext(),target);
        startActivity(intent);
    }
}
