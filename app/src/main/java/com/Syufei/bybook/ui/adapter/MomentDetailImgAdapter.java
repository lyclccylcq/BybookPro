package com.Syufei.bybook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.Syufei.bybook.R;

import java.util.List;

public class MomentDetailImgAdapter extends RecyclerView.Adapter {
    private List<String> paths;
    private Context context;

    public MomentDetailImgAdapter(Context context) {
        this.context = context;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.moment_img_item,parent,false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String path=paths.get(position);
        Glide.with(context).load(path).into( ((ImageHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return paths==null? 0 :paths.size();
    }
    private class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.moment_detail_img);
        }
    }
}
