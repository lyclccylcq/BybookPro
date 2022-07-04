package com.Syufei.bybook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Syufei.bybook.R;
import com.Syufei.bybook.bean.CommunityBean;
import com.Syufei.bybook.bean.LoginBean;
import com.Syufei.bybook.ui.viewmodel.MomentDetailViewmodel;
import com.Syufei.bybook.util.CommonTools;

import java.util.ArrayList;
import java.util.List;

public class MomentDetailAdapter extends RecyclerView.Adapter<MomentDetailAdapter.MomentDetailHolder> {
    private List<CommunityBean.Comment> commentList = new ArrayList<>();
    private LoginBean.UserInfo userInfo;
    private MomentDetailViewmodel momentDetailViewmodel;
    private Context context;

    public MomentDetailAdapter(Context context) {
        this.context = context;
    }

    public void setUserInfo(LoginBean.UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setMomentDetailViewmodel(MomentDetailViewmodel momentDetailViewmodel) {
        this.momentDetailViewmodel = momentDetailViewmodel;
    }

    public void setCommentList(List<CommunityBean.Comment> commentList) {
        if (this.commentList.size()==0){
            this.commentList=commentList;
        }else if (this.commentList.size()>0){
            for (CommunityBean.Comment comment:commentList){
                this.commentList.add(comment);
            }
        }

        notifyDataSetChanged();
    }

    public void addComment(CommunityBean.Comment comment){
        this.commentList.add(comment);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MomentDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
        return new MomentDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MomentDetailHolder holder, int position) {
        if (commentList.size()>0){
            CommunityBean.Comment comment=commentList.get(position);
            holder.name.setText(comment.getUser().getName());
            holder.content.setText(comment.getContext());
            if (momentDetailViewmodel.getUserSelfData()!=null){
                LoginBean.UserInfo userInfo=momentDetailViewmodel.getUserSelfData().getValue();
                if (!userInfo.getData().getUserId().equals(comment.getUser().getId())){
                    holder.del.setVisibility(View.VISIBLE);
                }else {
                    holder.del.setVisibility(View.GONE);
                }
            }else {
                holder.del.setVisibility(View.GONE);
            }
            holder.del.setOnClickListener(view -> {
                momentDetailViewmodel.delComment(comment.getId(),position);
            });
            holder.time.setText(CommonTools.formatUtcTime(comment.getCreateTime()));
            if (position==commentList.size()-1){
                holder.more.setVisibility(View.VISIBLE);
            }else{
                holder.more.setVisibility(View.GONE);
            }
            holder.more.setOnClickListener(view -> {
                momentDetailViewmodel.nextPage();
            });
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
    public void deleteItem(int pos){
        commentList.remove(pos);
        notifyDataSetChanged();
    }
    protected class MomentDetailHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView content;
        TextView del;
        ImageView avatar;
        TextView time;
        TextView more;
        public MomentDetailHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.comment_item_name);
            content=itemView.findViewById(R.id.comment_item_content);
            del=itemView.findViewById(R.id.comment_item_del);
            avatar=itemView.findViewById(R.id.comment_item_avatar);
            time=itemView.findViewById(R.id.comment_item_time);
            more=itemView.findViewById(R.id.comment_item_more);
        }
    }
}
