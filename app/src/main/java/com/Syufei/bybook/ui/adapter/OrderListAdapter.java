package com.Syufei.bybook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzhihua.bycar.R;
import com.lzhihua.bycar.bean.OrderBean;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.OrderRepo;
import com.lzhihua.bycar.ui.presenter.UIShowListener;
import com.lzhihua.bycar.util.CommonTools;
import com.lzhihua.bycar.util.UITools;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<OrderBean.OrderList.Result> orderList;
    private UIShowListener uiShowListener;

    public OrderListAdapter(Context context) {
        this.context = context;
    }

    public void setUiShowListener(UIShowListener uiShowListener) {
        this.uiShowListener = uiShowListener;
    }

    public void setOrderList(List<OrderBean.OrderList.Result> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_card, parent, false);
        return new OrderListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderBean.OrderList.Result result = orderList.get(position);
        ((OrderListHolder) holder).nameTv.setText(result.getCar().getName() + " " + result.getCar().getVersion());
        ((OrderListHolder) holder).priceTv.setText("总计：￥" + result.getPrice()*10000);
        ((OrderListHolder) holder).createTimeTv.setText("订单创建时间：" + CommonTools.formatUtcTime(result.getCreateTime()));
        ((OrderListHolder) holder).updateTimeTv.setText("订单修改时间：" + CommonTools.formatUtcTime(result.getUpdateTime()));
        ((OrderListHolder) holder).addressTv.setText("交付地：" + result.getAddress());
         String[] status=new String[]{"待支付","待处理","处理中","已完成"};
        ((OrderListHolder) holder).statusTv.setText(result.getStatus()==-1? "已取消" : status[result.getStatus()]);
        ((OrderListHolder) holder).msgBtn.setTag(result);
        ((OrderListHolder) holder).imageView.setImageDrawable(UITools.getDrawable(context.getResources(),result.getCar().getName()));
        if (result.getStatus() == -1 || result.getStatus() == 3) {
            ((OrderListHolder) holder).msgBtn.setVisibility(View.GONE);
        } else if (result.getStatus() == 0) {
            ((OrderListHolder) holder).msgBtn.setVisibility(View.VISIBLE);
            ((OrderListHolder) holder).msgTv.setText("去支付");
            ((OrderListHolder) holder).msgBtn.setOnClickListener(view -> {
                uiShowListener.showProgress();
                OrderBean.OrderList.Result myResult = (OrderBean.OrderList.Result) view.getTag();
                OrderRepo.orderPay("" + myResult.getId(), new DataSuccessListenter() {
                    @Override
                    public void onDataSuccess(Object obj) {
                        UITools.showToast(context, "支付成功");
                        uiShowListener.requestData();
                        uiShowListener.dismissProgress();
                    }

                    @Override
                    public void onError(String error) {
                        UITools.showToast(context, "支付失败");
                        uiShowListener.dismissProgress();
                    }
                });
            });
        } else if (result.getStatus() == 1 || result.getStatus() == 2) {
            ((OrderListHolder) holder).msgBtn.setVisibility(View.VISIBLE);
            ((OrderListHolder) holder).msgTv.setText("取消订单");
            ((OrderListHolder) holder).msgBtn.setOnClickListener(view -> {
                uiShowListener.showProgress();
                OrderBean.OrderList.Result myResult = (OrderBean.OrderList.Result) view.getTag();
                OrderRepo.cancelOrder("" + myResult.getId(), new DataSuccessListenter() {
                    @Override
                    public void onDataSuccess(Object obj) {
                        uiShowListener.requestData();
                        uiShowListener.dismissProgress();
                    }

                    @Override
                    public void onError(String error) {
                        uiShowListener.dismissProgress();
                    }
                });
            });
        }

    }

    @Override
    public int getItemCount() {
        return orderList == null ? 0 : orderList.size();
    }

    private class OrderListHolder extends RecyclerView.ViewHolder {
        private TextView nameTv;
        private TextView priceTv;
        private TextView createTimeTv;
        private TextView updateTimeTv;
        private TextView statusTv;
        private LinearLayout msgBtn;
        private TextView msgTv;
        private TextView addressTv;
        private ImageView imageView;
        public OrderListHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.order_list_car_name);
            priceTv = itemView.findViewById(R.id.order_list_car_price);
            createTimeTv = itemView.findViewById(R.id.order_list_car_create_time);
            updateTimeTv = itemView.findViewById(R.id.order_list_car_update_time);
            statusTv = itemView.findViewById(R.id.order_list_car_status);
            msgBtn = itemView.findViewById(R.id.order_list_car_btn);
            msgTv = itemView.findViewById(R.id.order_list_car_btn_tv);
            addressTv = itemView.findViewById(R.id.order_list_car_address);
            imageView=itemView.findViewById(R.id.order_list_car_img);
        }

    }
}
