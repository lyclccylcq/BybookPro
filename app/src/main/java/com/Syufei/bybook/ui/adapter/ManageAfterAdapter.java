package com.Syufei.bybook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Syufei.bybook.R;
import com.Syufei.bybook.bean.ManagerBean;
import com.Syufei.bybook.ui.viewmodel.ManageAfterViewmodel;
import com.Syufei.bybook.util.CommonTools;

import java.util.List;

public class ManageAfterAdapter extends RecyclerView.Adapter<ManageAfterAdapter.ManageAfterHolder>{
    private Context context;
    private ManageAfterViewmodel manageAfterViewmodel;
    private List<ManagerBean.AfterOrderList.Result> mList;
    private String[] statusString=new String[]{"待处理","换书/退书中","待支付","已完成"};
    private boolean isFolded=false;//是否折叠

    public void setFolded(boolean folded) {
        isFolded = folded;
    }

    public void setmList(List<ManagerBean.AfterOrderList.Result> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setManageAfterViewmodel(ManageAfterViewmodel manageAfterViewmodel) {
        this.manageAfterViewmodel = manageAfterViewmodel;
    }

    public ManageAfterAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ManageAfterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manage_after_item, parent, false);
        return new ManageAfterHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ManageAfterHolder holder, int position) {
        ManagerBean.AfterOrderList.Result result=mList.get(position);
        holder.id.setText("订单Id："+result.getId());
        holder.price.setText("订单价格："+result.getPrice());
        holder.address.setText("换书/退书地点："+result.getAddress());
        holder.createTime.setText("创建时间："+ CommonTools.formatUtcTime(result.getCreateTime()));
        holder.updateTime.setText("更新时间："+ CommonTools.formatUtcTime(result.getUpdateTime()));
        holder.bookName.setText("书籍名称："+result.getCarResp().getName());
        holder.bookAuthor.setText("书籍作者："+result.getCarResp().getAuthor());
        holder.userId.setText("用户Id："+result.getUserResp().getId());
        holder.userName.setText("用户名："+result.getUserResp().getName());
        holder.phone.setText("联系电话："+result.getUserResp().getPhone());
        holder.process.setVisibility(result.getStatus()==0? View.VISIBLE :View.GONE);
        if (result.getStatus()==-1){
            holder.status.setText("已取消");
        }else{
            holder.status.setText(statusString[result.getStatus()]);
        }
        holder.priceGive.setOnClickListener(view -> {
            manageAfterViewmodel.getShowBottom().setValue(true);
            manageAfterViewmodel.setSelectId(result.getId());
        });
        holder.process.setOnClickListener(view -> {
            int id=result.getId();
            manageAfterViewmodel.processOrder(id);
        });
        holder.open.setOnClickListener(view -> {
            holder.open.setVisibility(View.GONE);
            holder.other.setVisibility(View.VISIBLE);
        });
        holder.close.setOnClickListener(view -> {
            holder.open.setVisibility(View.VISIBLE);
            holder.other.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return mList==null? 0 :mList.size();
    }

    protected class ManageAfterHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView price;
        private TextView address;
        private TextView updateTime;
        private TextView createTime;
        private TextView open;
        private LinearLayout other;
        private TextView bookName;
        private TextView bookAuthor;
        private TextView userName;
        private TextView userId;
        private TextView phone;
        private TextView close;
        private TextView priceGive;
        private TextView process;

        private TextView status;
        public ManageAfterHolder(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.manage_after_order_status);
            id = itemView.findViewById(R.id.manage_after_order_id);
            price = itemView.findViewById(R.id.manage_after_order_price);
            address = itemView.findViewById(R.id.manage_after_order_address);
            updateTime = itemView.findViewById(R.id.manage_after_order_update_time);
            createTime = itemView.findViewById(R.id.manage_after_order_create_time);
            open = itemView.findViewById(R.id.manage_after_order_open);
            other = itemView.findViewById(R.id.manage_after_order_other);
            bookName = itemView.findViewById(R.id.manage_after_order_car_name);
            bookAuthor = itemView.findViewById(R.id.manage_after_order_car_version);
            userName = itemView.findViewById(R.id.manage_after_order_user_name);
            userId = itemView.findViewById(R.id.manage_after_order_user_id);
            phone = itemView.findViewById(R.id.manage_after_order_user_phone);
            close = itemView.findViewById(R.id.manage_after_order_close);
            priceGive = itemView.findViewById(R.id.manage_after_order_giveprice);
            process = itemView.findViewById(R.id.manage_after_order_process);
        }
    }
}
