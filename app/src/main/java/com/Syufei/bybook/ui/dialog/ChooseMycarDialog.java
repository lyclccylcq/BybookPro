package com.Syufei.bybook.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzhihua.bycar.R;
import com.lzhihua.bycar.bean.AfterOrderBean;
import com.lzhihua.bycar.commonui.PopupDialog;
import com.lzhihua.bycar.databinding.ChooseCarDialogBinding;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.ImpairRepo;
import com.lzhihua.bycar.util.UITools;

import java.util.List;

public class ChooseMycarDialog extends PopupDialog {
    private ChooseCarDialogBinding chooseCarDialogBinding;
    private List<AfterOrderBean.SelfCar.Result> carList;
    private String title;

    public void setTitle(String title) {
        this.title = title;
        chooseCarDialogBinding.chooseCarTop.topTv.setText(title);
    }

    public ChooseMycarDialog(Context context) {
        this(context, R.layout.choose_car_dialog, 0);
        chooseCarDialogBinding = ChooseCarDialogBinding.inflate(LayoutInflater.from(context));
        mDialog.setContentView(chooseCarDialogBinding.getRoot());
        ImpairRepo.getSelfCarList(new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                AfterOrderBean.SelfCar selfCar = (AfterOrderBean.SelfCar) obj;
                if (selfCar != null) {
                    carList = selfCar.getData().getList();
                    updateUi();
                }
            }

            @Override
            public void onError(String error) {

            }
        });

        chooseCarDialogBinding.chooseCarTop.titleBack.setOnClickListener(view -> {
            dismiss();
        });
    }

    public ChooseMycarDialog(Context context, int resId, int heightType) {
        super(context, resId, heightType);
    }

    private void updateUi() {
        chooseCarDialogBinding.chooseCarContainer.removeAllViews();
        for (AfterOrderBean.SelfCar.Result car : carList) {
            View item = LayoutInflater.from(context).inflate(R.layout.purchase_car_card, null, false);
            RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            lp.rightMargin=UITools.dip2px(10);
//            lp.leftMargin=UITools.dip2px(10);
            lp.topMargin=UITools.dip2px(10);
            lp.bottomMargin=UITools.dip2px(10);
            item.setLayoutParams(lp);
            TextView name = item.findViewById(R.id.purchase_card_name);
            TextView version = item.findViewById(R.id.purchase_card_version);
            TextView description = item.findViewById(R.id.purchase_card_description);
            LinearLayout buy = item.findViewById(R.id.purchase_card_buy);
            ImageView carImg = item.findViewById(R.id.purchase_card_img);
            TextView price = item.findViewById(R.id.purchase_card_price);
            price.setVisibility(View.GONE);
            buy.setVisibility(View.GONE);
            name.setText(car.getName());
            version.setText(car.getVersion());
            description.setText(car.getDescription());
            carImg.setImageDrawable(UITools.getDrawable(context.getResources(), car.getName()));
            item.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("after_sale_car_id", car.getId());
                bundle.putString("after_sale_car_name", car.getName());
                setData(bundle);
                dismiss();
            });
            chooseCarDialogBinding.chooseCarContainer.addView(item);
        }
    }

}
