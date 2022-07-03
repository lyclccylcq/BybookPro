package com.Syufei.bybook.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzhihua.bycar.MomentDetailActivity;
import com.lzhihua.bycar.R;
import com.lzhihua.bycar.bean.CommunityBean;
import com.lzhihua.bycar.ui.view.MultiImageView;
import com.lzhihua.bycar.ui.viewmodel.MorefragViewmodel;
import com.lzhihua.bycar.util.CommonTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MomentItemAdapter extends RecyclerView.Adapter<MomentItemAdapter.MomentItemHolder> implements MultiImageView.OnItemClickListener {
    private List<CommunityBean.Moment> momentList = new ArrayList<>();
    private Context context;
    private MorefragViewmodel morefragViewmodel;
    private int type=0;//0为所有动态 1为个人动态

    public MomentItemAdapter(Context context) {
        this.context = context;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void removeItem(int pos){
        momentList.remove(pos);
        notifyDataSetChanged();
    }
    public void setMomentList(List<CommunityBean.Moment> momentList) {
        this.momentList=momentList;
        notifyDataSetChanged();
    }

    public void addMoment(List<CommunityBean.Moment> moments) {
        for (CommunityBean.Moment moment:moments){
            this.momentList.add(moment);
        }
        notifyDataSetChanged();
    }

    public void setMorefragViewmodel(MorefragViewmodel morefragViewmodel) {
        this.morefragViewmodel = morefragViewmodel;
    }

    @NonNull
    @Override
    public MomentItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.moment_item, parent, false);
        return new MomentItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MomentItemHolder holder, int position) {
        CommunityBean.Moment moment = momentList.get(position);
        holder.content.setText(moment.getContext());
        holder.time.setText(CommonTools.formatUtcTime(moment.getCreateTime()==null? "" : moment.getCreateTime()));
        holder.name.setText(moment.getUser().getName());
        String[] paths = moment.getImgUrl().split(";");
        holder.multiImageView.setOnItemClickListener(this);
        holder.multiImageView.setList(Arrays.asList(paths));
        holder.more.setOnClickListener(view -> {
            Intent intent = new Intent(context, MomentDetailActivity.class);
            intent.putExtra("moment", moment);
            context.startActivity(intent);
        });
        holder.like.setTag(position);
        holder.like.setOnClickListener(view -> {
            morefragViewmodel.momentLike(moment.getId(), position);
        });
        holder.comment.setOnClickListener(view -> {
            Intent intent = new Intent(context, MomentDetailActivity.class);
            intent.putExtra("moment", moment);
            context.startActivity(intent);
        });
        holder.del.setVisibility(type==0? View.GONE :View.VISIBLE);
        holder.del.setOnClickListener(view -> {
            morefragViewmodel.delMoment(moment.getId(),position);
        });
    }

    @Override
    public int getItemCount() {
        return momentList == null ? 0 : momentList.size();
    }

    @Override
    public void onItemClick(View view, String url) {
        morefragViewmodel.getShowBigImg().setValue(url);
    }

    protected class MomentItemHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView more;
        MultiImageView multiImageView;
        TextView time;
        ImageView comment;
        ImageView like;
        TextView name;
        TextView del;
        public MomentItemHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.moment_item_content);
            more = itemView.findViewById(R.id.moment_item_more);
            multiImageView = itemView.findViewById(R.id.moment_item_multiimg);
            time = itemView.findViewById(R.id.moment_item_create_time);
            comment = itemView.findViewById(R.id.moment_item_comment);
            like = itemView.findViewById(R.id.moment_item_like);
            name = itemView.findViewById(R.id.moment_item_name);
            del = itemView.findViewById(R.id.moment_item_del);
        }
    }

    public void updateItem(RecyclerView.ViewHolder holder) {
        ((MomentItemHolder) holder).like.setImageDrawable(context.getResources().getDrawable(R.drawable.like_select));
    }
}
