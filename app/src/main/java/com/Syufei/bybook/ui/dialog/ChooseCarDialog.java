package com.Syufei.bybook.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Syufei.bybook.R;
import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.commonui.PopupDialog;
import com.Syufei.bybook.databinding.ChooseCarDialogBinding;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.TryBookRepo;
import com.Syufei.bybook.util.UITools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseCarDialog extends PopupDialog {
    private ChooseCarDialogBinding chooseCarDialogBinding;
    private List<BookBean.BookList.BookListSubData> cars;
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
            for (BookBean.BookList.BookListSubData carListSubData:cars){
                View view=LayoutInflater.from(context).inflate(R.layout.choose_car_card,null,false);
                view.setTag(carListSubData);
                view.setOnClickListener(view1 -> {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("car_bean",(BookBean.BookList.BookListSubData) view1.getTag());
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
        TryBookRepo.getBooklist(new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                BookBean.BookList carList = (BookBean.BookList) obj;
                List<BookBean.BookList.BookListSubData> tmp=carList.getData().getList();
                //去重
                Set<String> names=new HashSet<>();

                for(BookBean.BookList.BookListSubData BookListSubData:tmp){
                    if(!names.contains(BookListSubData.getName())){
                        cars.add(BookListSubData);
                        names.add(BookListSubData.getName());
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
