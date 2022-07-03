package com.Syufei.bybook.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzhihua.bycar.R;
import com.lzhihua.bycar.commonui.PopupDialog;

import java.util.List;

public class FilterItemDialog extends PopupDialog {
    private ImageView back;
    private LinearLayout container;
    public FilterItemDialog(Context context) {
        this(context,R.layout.filter_item_dialog,1);
    }
    public FilterItemDialog(Context context, int resId, int heightType) {
        super(context, resId, heightType);
        back=mView.findViewById(R.id.title_back);
        container=mView.findViewById(R.id.filter_item_container);
        back.setOnClickListener(view -> {
            dismiss();
        });
    }

    public void setItems(List<String> items) {
        container.removeAllViews();
        if (items!=null && items.size()>0){
            for (String s:items){
                View view= LayoutInflater.from(context).inflate(R.layout.filter_item,null,false);
                TextView textView=view.findViewById(R.id.filter_item_tv);
                textView.setText(s);
                textView.setTag(s);
                view.setOnClickListener(view1->{
                    Bundle bundle=new Bundle();
                    bundle.putString("select_item",s);
                    setData(bundle);
                    dismiss();
                });
                container.addView(view);
            }
        }
    }
}
