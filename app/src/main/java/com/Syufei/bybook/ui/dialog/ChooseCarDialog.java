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
import com.lzhihua.bycar.bean.CarBean;
import com.lzhihua.bycar.commonui.PopupDialog;
import com.lzhihua.bycar.databinding.ChooseCarDialogBinding;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.TryCarRepo;
import com.lzhihua.bycar.util.UITools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseCarDialog extends PopupDialog {
    private ChooseCarDialogBinding chooseCarDialogBinding;
    private List<CarBean.CarList.CarListSubData> cars;
    private Context context;

    public ChooseCarDialog(Context context) {
        this(context, R.layout.choose_car_dialog, 0);
        chooseCarDialogBinding = ChooseCarDialogBinding.inflate(LayoutInflater.from(context));
        mDialog.setContentView(chooseCarDialogBinding.getRoot());
        this.context=context;
        cars=new ArrayList<>();
        queryCarList();
    }

    public ChooseCarDialog(Context context, int resId, int heightType) {
        super(context, resId, 0);
    }

    private void updateUI(){
        if (cars!=null && cars.size()>0){
            chooseCarDialogBinding.chooseCarContainer.removeAllViews();
            for (CarBean.CarList.CarListSubData carListSubData:cars){
                View view=LayoutInflater.from(context).inflate(R.layout.choose_car_card,null,false);
                view.setTag(carListSubData);
                view.setOnClickListener(view1 -> {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("car_bean",(CarBean.CarList.CarListSubData) view1.getTag());
                    setData(bundle);
                    dismiss();
                });
                TextView titleTv=view.findViewById(R.id.choose_car_card_card_title);
                ImageView img=view.findViewById(R.id.choose_car_card_card_img);
                titleTv.setText("NIO "+carListSubData.getName());
                img.setImageDrawable(UITools.getDrawable(context.getResources(),carListSubData.getName()));
                RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,UITools.dip2px(180));
                lp.rightMargin=UITools.dip2px(10);
                lp.leftMargin=UITools.dip2px(10);
                lp.topMargin=UITools.dip2px(10);
                lp.bottomMargin=UITools.dip2px(10);
                view.setLayoutParams(lp);
                chooseCarDialogBinding.chooseCarContainer.addView(view);
            }
        }
    }
    private void queryCarList(){
        TryCarRepo.getCarlist(new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                CarBean.CarList carList = (CarBean.CarList) obj;
                List<CarBean.CarList.CarListSubData> tmp=carList.getData().getList();
                //去重
                Set<String> names=new HashSet<>();

                for(CarBean.CarList.CarListSubData carListSubData:tmp){
                    if(!names.contains(carListSubData.getName())){
                        cars.add(carListSubData);
                        names.add(carListSubData.getName());
                    }
                }
                updateUI();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
