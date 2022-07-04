package com.Syufei.bybook.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Syufei.bybook.R;
import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.commonui.CommonDialog;
import com.Syufei.bybook.commonui.PopupDialog;
import com.Syufei.bybook.databinding.OrderDetailBinding;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.OrderRepo;
import com.Syufei.bybook.ui.dialog.ChooseCityDialog;
import com.Syufei.bybook.util.UITools;

public class OrderDetailActivity extends BaseActivity implements PopupDialog.onDismissListener {
//    订单生成
    private CommonDialog cancelDialog;
    private BookBean.BookList.BookListSubData bookBean;
    private OrderDetailBinding orderDetailBinding;
    private PopupDialog chooseIDTypeDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWhiteStatusBar();
        orderDetailBinding=OrderDetailBinding.inflate(getLayoutInflater());
        setContentView(orderDetailBinding.getRoot());
        cancelDialog=new CommonDialog(this);
        cancelDialog.setTitle("确认要退出吗？");
        cancelDialog.setMessage("现在退出将不能享受购书优惠哦...");
        cancelDialog.setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick(String type) {
                finish();
            }

            @Override
            public void onNegativeClick(String type) {
                cancelDialog.dismiss();
            }
        });
        bookBean = (BookBean.BookList.BookListSubData) getIntent().getSerializableExtra("book_bean");
        if (bookBean ==null){
            finish();
        }
        orderDetailBinding.createOrderTop.topTv.setText("创建订单");
        orderDetailBinding.createOrderName.setText("书籍名称： "+ bookBean.getName());
        orderDetailBinding.createOrderPriceTotalTv.setText("总价：￥ "+ bookBean.getPrice()*1);
        orderDetailBinding.createOrderVersion.setText("作者："+ bookBean.getAuthor());
        orderDetailBinding.createOrderImg.setImageDrawable(UITools.getDrawable(getResources(), bookBean.getName()));
        orderDetailBinding.createOrderCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseCityDialog chooseCityDialog=new ChooseCityDialog(OrderDetailActivity.this);
                chooseCityDialog.setDialogType("chooseCityDialog");
                chooseCityDialog.setListener(OrderDetailActivity.this);
                chooseCityDialog.show();
            }
        });
        orderDetailBinding.createOrderCancel.setOnClickListener(view -> {
            cancelDialog.show();
        });
        orderDetailBinding.createOrderTop.titleBack.setOnClickListener(view -> {
            cancelDialog.show();
        });
        orderDetailBinding.createOrderCommit.setOnClickListener(view -> {
            TextView cityTv=orderDetailBinding.createOrderCity.findViewById(R.id.order_car_city_name);
            EditText receiverTv=orderDetailBinding.createOrderReceiver.findViewById(R.id.create_order_receiver_name);
            EditText phoneTv=orderDetailBinding.createOrderPhone.findViewById(R.id.create_order_phone_number);
            if (TextUtils.isEmpty(cityTv.getText().toString()) || TextUtils.isEmpty(receiverTv.getText().toString()) || TextUtils.isEmpty(phoneTv.getText().toString())){
                UITools.showToast(OrderDetailActivity.this,"信息不完整哦..");

            }else {
                progressDialog.show();
                OrderRepo.createOrder(bookBean.getId(), cityTv.getText().toString().trim(), new DataSuccessListenter() {
                    @Override
                    public void onDataSuccess(Object obj) {
                        orderDetailBinding.createOrderCancel.setVisibility(View.GONE);
                        orderDetailBinding.createOrderCommit.setVisibility(View.GONE);
                        orderDetailBinding.createOrderPay.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        UITools.showToast(OrderDetailActivity.this,"未知错误，稍后再试");
                        progressDialog.dismiss();
                    }
                });
            }


        });
        orderDetailBinding.createOrderPay.setOnClickListener(view -> {
            progressDialog.show();
            OrderRepo.orderPay("" + bookBean.getId(), new DataSuccessListenter() {
                @Override
                public void onDataSuccess(Object obj) {
                    UITools.showToast(OrderDetailActivity.this, "支付成功");
                    progressDialog.dismiss();
                    finish();
                }

                @Override
                public void onError(String error) {
                    UITools.showToast(OrderDetailActivity.this, "支付失败");
                    progressDialog.dismiss();
                }
            });
        });
        orderDetailBinding.createOrderOwnerIdType.setOnClickListener(view -> {
            chooseIDTypeDialog=new PopupDialog(OrderDetailActivity.this,R.layout.common_dialog_list,1);
            chooseIDTypeDialog.setDialogType("chooseIDTypeDialog");
            chooseIDTypeDialog.setListener(this);
            LinearLayout container=(LinearLayout) chooseIDTypeDialog.getmView().findViewById(R.id.common_dialog_listcontainer);
            TextView topTv=(TextView) chooseIDTypeDialog.getmView().findViewById(R.id.common_dialog_list_toptv);
            topTv.setText("选择证件类型");
            String[] types=new String[]{"居民身份证","护照","香港身份证","军官证","台胞证","澳门居民身份证"};
            container.removeAllViews();
            for (String s:types){
                TextView textView= (TextView) LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.commmon_text_view_1,null,false);
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.bottomMargin=UITools.dip2px(20);
                lp.topMargin=UITools.dip2px(20);
                lp.leftMargin=UITools.dip2px(20);
                lp.rightMargin=UITools.dip2px(20);
                textView.setLayoutParams(lp);
                textView.setText(s);
                container.addView(textView);
                textView.setOnClickListener(view1 -> {
                    Bundle bundle=new Bundle();
                    bundle.putString("choose_type",s);
                    chooseIDTypeDialog.setData(bundle);
                    chooseIDTypeDialog.dismiss();
                });
            }
            chooseIDTypeDialog.show();
        });
    }

    @Override
    public void onDismiss(Bundle data,String type) {
        if (type.equals("chooseCityDialog")){
            String city=data.getString("city","");
            if(!TextUtils.isEmpty(city)){
                TextView cityTv=orderDetailBinding.createOrderCity.findViewById(R.id.order_car_city_name);
                cityTv.setText(city);
            }
        }else if (type.equals("chooseIDTypeDialog")){
            String idType=data.getString("choose_type","");
            if(!TextUtils.isEmpty(type)){
                orderDetailBinding.createOrderOwnerIdTypeTv.setText(idType);
            }
        }
    }
}