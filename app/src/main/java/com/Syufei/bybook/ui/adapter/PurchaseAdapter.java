package com.Syufei.bybook.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Syufei.bybook.R;
import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.ui.OrderDetailActivity;
import com.Syufei.bybook.ui.presenter.UIShowListener;
import com.Syufei.bybook.util.UITools;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BookBean.BookList.BookListSubData> carList;
    private UIShowListener listener;

    public void setListener(UIShowListener listener) {
        this.listener = listener;
    }

    public PurchaseAdapter(Context context) {
        this.context = context;
    }

    public void setCarList(List<BookBean.BookList.BookListSubData> carList) {
        this.carList = carList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.purchase_car_card, parent, false);
        return new PurchaseCarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookBean.BookList.BookListSubData car = carList.get(position);

        ((PurchaseCarHolder) holder).priceTv.setText("￥ " + car.getPrice()*10000);
        ((PurchaseCarHolder) holder).nameTv.setText(car.getName());
        ((PurchaseCarHolder) holder).desTv.setText(car.getDescription());
        ((PurchaseCarHolder) holder).root.setTag(car.getId());
        ((PurchaseCarHolder) holder).version.setText(car.getAuthor());
        ((PurchaseCarHolder) holder).buy.setTag(car);
        ((PurchaseCarHolder) holder).buy.setOnClickListener(view -> {
             Intent intent=new Intent(context, OrderDetailActivity.class);
             intent.putExtra("car_bean",car);
             context.startActivity(intent);
        });
        ((PurchaseCarHolder) holder).root.setOnClickListener(view -> {
            Intent intent=new Intent(context, OrderDetailActivity.class);
            intent.putExtra("car_bean",car);
            context.startActivity(intent);
        });
        ((PurchaseCarHolder) holder).carImg.setImageDrawable(UITools.getDrawable(context.getResources(),car.getName()));
    }

    @Override
    public int getItemCount() {
        return carList == null ? 0 : carList.size();
    }

    private class PurchaseCarHolder extends RecyclerView.ViewHolder {
        private ImageView carImg;
        private TextView priceTv;
        private TextView nameTv;
        private TextView desTv;
        private RelativeLayout root;
        private LinearLayout buy;
        private TextView version;

        public PurchaseCarHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.purchase_card_root);
            carImg = itemView.findViewById(R.id.purchase_card_img);
            priceTv = itemView.findViewById(R.id.purchase_card_price);
            nameTv = itemView.findViewById(R.id.purchase_card_name);
            desTv = itemView.findViewById(R.id.purchase_card_description);
            version = itemView.findViewById(R.id.purchase_card_version);
            buy = itemView.findViewById(R.id.purchase_card_buy);
        }
    }
}
