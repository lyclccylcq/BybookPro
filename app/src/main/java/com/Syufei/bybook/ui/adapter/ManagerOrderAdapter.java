package com.Syufei.bybook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzhihua.bycar.R;
import com.lzhihua.bycar.bean.CarBean;
import com.lzhihua.bycar.bean.ManagerBean;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.ManagerRepo;
import com.lzhihua.bycar.ui.presenter.UIShowListener;
import com.lzhihua.bycar.util.CommonTools;
import com.lzhihua.bycar.util.UITools;

import java.util.List;

public class ManagerOrderAdapter extends RecyclerView.Adapter<ManagerOrderAdapter.OrderHolder> {
    private List<ManagerBean.SaleList.Result> resultList;
    private Context context;
    private UIShowListener listener;
    private String[] status=new String[]{"待支付","待处理，去处理","处理中，去完成","已完成"};

    public void setListener(UIShowListener listener) {
        this.listener = listener;
    }

    public ManagerOrderAdapter(Context context) {
        this.context = context;
    }

    public void setResultList(List<ManagerBean.SaleList.Result> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manager_order_card, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        ManagerBean.SaleList.Result result = resultList.get(position);
        holder.orderId.setText("订单id：" + result.getId());
        holder.orderPrice.setText("订单价格：" + result.getPrice());
        holder.orderAddress.setText("交付地址：" + result.getAddress());
        holder.orderUpdateTime.setText("更新时间：" + CommonTools.formatUtcTime(result.getUpdateTime()));
        holder.orderCreateTime.setText("创建时间：" + CommonTools.formatUtcTime(result.getCreateTime()));
        holder.carName.setText("车辆系列：" + result.getCar().getName());
        holder.carVersion.setText("车辆版本："+result.getCar().getVersion());
        holder.carPrice.setText("基础价格："+result.getCar().getPrice());
        holder.knowMore.setVisibility(position==resultList.size()-1? View.VISIBLE :View.GONE);
        holder.knowMore.setOnClickListener(view->{
            listener.requestData();
        });
        holder.carImg.setImageDrawable(UITools.getDrawable(context.getResources(),result.getCar().getName()));
        String statusString=result.getStatus()==-1? "已取消": status[result.getStatus()];
        holder.statusBar.setText(statusString);
        holder.statusBar.setTag(result);
        holder.statusBar.setOnClickListener(view -> {
            ManagerBean.SaleList.Result result1= (ManagerBean.SaleList.Result) view.getTag();
            if(result1.getStatus()==1){
                processOrder(result1.getId(),result1);
            }else if (result.getStatus()==2){
                finishOrder(result1.getId(),result1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    protected class OrderHolder extends RecyclerView.ViewHolder {
        private TextView orderId;
        private TextView orderPrice;
        private TextView orderAddress;
        private TextView orderUpdateTime;
        private TextView orderCreateTime;
        private TextView carName;
        private TextView carVersion;
        private TextView carPrice;
        private TextView knowMore;
        private TextView statusBar;
        private ImageView carImg;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.manager_order_card_id);
            orderPrice = itemView.findViewById(R.id.manager_order_card_price);
            orderAddress = itemView.findViewById(R.id.manager_order_card_address);
            orderUpdateTime = itemView.findViewById(R.id.manager_order_card_update_time);
            orderCreateTime = itemView.findViewById(R.id.manager_order_card_create_time);
            carName = itemView.findViewById(R.id.manager_order_card_car_name);
            carVersion = itemView.findViewById(R.id.manager_order_card_car_version);
            carPrice = itemView.findViewById(R.id.manager_order_card_car_price);
            knowMore = itemView.findViewById(R.id.manager_order_card_know_more);
            statusBar=itemView.findViewById(R.id.status_btn);
            carImg=itemView.findViewById(R.id.manager_order_card_car_img);
        }
    }

    //0待支付，1待处理，2处理中，3已完成, -1已取消
    private void processOrder(int orderId, ManagerBean.SaleList.Result result){
        listener.showProgress();
        ManagerRepo.processOrder(orderId, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                CarBean.CommonResponse commonResponse=(CarBean.CommonResponse) obj;
                if (commonResponse.getStatus().equals("success")){
                    result.setStatus(2);
                }
                listener.dismissProgress();
                notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void finishOrder(int orderId, ManagerBean.SaleList.Result result){
        listener.showProgress();
        ManagerRepo.finishOrder(orderId, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                CarBean.CommonResponse commonResponse=(CarBean.CommonResponse) obj;
                if (commonResponse.getStatus().equals("success")){
                    result.setStatus(3);
                }
                listener.dismissProgress();
                notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
