package com.Syufei.bybook.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.lzhihua.bycar.R;
import com.lzhihua.bycar.commonui.PopupDialog;
import com.lzhihua.bycar.databinding.ChooseDateDialogBinding;

public class ChooseDateDialog extends PopupDialog {
    private ChooseDateDialogBinding chooseDateDialogBinding;
    private int[] res;
    public ChooseDateDialog(Context context){
        this(context, R.layout.choose_date_dialog,1);
    }
    public ChooseDateDialog(Context context, int resId, int heightType) {
        super(context, resId, heightType);
        res=new int[3];
        chooseDateDialogBinding=ChooseDateDialogBinding.inflate(LayoutInflater.from(context));
        mDialog.setContentView(chooseDateDialogBinding.getRoot());
        chooseDateDialogBinding.chooseDateTop.topTv.setText("选择日期");
        chooseDateDialogBinding.chooseDateTop.titleBack.setOnClickListener(view -> {
            dismiss();
        });
        chooseDateDialogBinding.chooseDateCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
//            i:年 i1：月 i2：日
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                res[0]=i;
                res[1]=i1;
                res[2]=i2;

            }
        });
        chooseDateDialogBinding.chooseDateCancel.setOnClickListener(view -> {
            dismiss();
        });
        chooseDateDialogBinding.chooseDateConfirm.setOnClickListener(view -> {
            Bundle bundle=new Bundle();
            bundle.putIntArray("date_res",res);
            bundle.putBoolean("date_is_elect",true);
            setData(bundle);
            dismiss();
        });
    }
}
