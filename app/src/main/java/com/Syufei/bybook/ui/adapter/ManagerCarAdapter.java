package com.Syufei.bybook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Syufei.bybook.R;
import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.commonui.CommonDialog;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.ManagerRepo;
import com.Syufei.bybook.ui.presenter.UIShowListener;
import com.Syufei.bybook.util.UITools;

import java.util.List;

public class ManagerCarAdapter extends RecyclerView.Adapter<ManagerCarAdapter.ManagerCarHolder> {
    private List<BookBean.BookList.BookListSubData> carList;
    private Context context;
    private CommonDialog confirmDialog;
    private UIShowListener uiShowListener;

    public void setUiShowListener(UIShowListener uiShowListener) {
        this.uiShowListener = uiShowListener;
    }

    public void setCarList(List<BookBean.BookList.BookListSubData> bookList) {
        this.carList = bookList;
        notifyDataSetChanged();
    }

    public ManagerCarAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ManagerCarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manager_car_item, null, false);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UITools.dip2px(200));
        lp.topMargin = UITools.dip2px(10);
        view.setLayoutParams(lp);
        return new ManagerCarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerCarHolder holder, int position) {
        BookBean.BookList.BookListSubData subData = carList.get(position);
        holder.name.setText(subData.getName());
        holder.version.setText(subData.getAuthor());
        holder.description.setText(subData.getDescription());
        holder.carImg.setImageDrawable(UITools.getDrawable(context.getResources(), subData.getName()));
        holder.delbtn.setOnClickListener(view -> {
            confirmDialog=new CommonDialog(context);
            confirmDialog.setTitle("提示");
            confirmDialog.setMessage("确定要删除书籍吗，删除后数据库将不存在该书籍，操作不可逆。");

            confirmDialog.setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                @Override
                public void onPositiveClick(String type) {
                    ManagerRepo.deleteCar(subData.getId(), new DataSuccessListenter() {
                        @Override
                        public void onDataSuccess(Object obj) {
                            BookBean.CommonResponse commonResponse = (BookBean.CommonResponse) obj;
                            if (commonResponse.getStatus().equals("success")) {
                                uiShowListener.requestData();
                            }
                        }

                        @Override
                        public void onError(String error) {
                            UITools.showToast(context, "请求失败，稍后重试");

                        }
                    });
                    confirmDialog.dismiss();
                }

                @Override
                public void onNegativeClick(String type) {
                    confirmDialog.dismiss();
                }
            });
            confirmDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return carList == null ? 0 : carList.size();
    }

    protected class ManagerCarHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView version;
        TextView description;
        TextView delbtn;
        ImageView carImg;

        public ManagerCarHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.manage_car_item_name);
            version = itemView.findViewById(R.id.manage_car_item_version);
            description = itemView.findViewById(R.id.manage_car_item_des);
            delbtn = itemView.findViewById(R.id.manage_car_item_del);
            carImg = itemView.findViewById(R.id.manage_car_item_img);
        }
    }
}
