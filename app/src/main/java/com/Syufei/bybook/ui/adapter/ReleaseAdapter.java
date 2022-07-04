package com.Syufei.bybook.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Syufei.bybook.R;

import java.util.List;

public class ReleaseAdapter extends RecyclerView.Adapter  {
    private List<String> mList;
    private Context context;
    private ReleaseListener listener;

    public void setListener(ReleaseListener listener) {
        this.listener = listener;
    }
    public ReleaseAdapter(Context context){
        this.context=context;
    }


    public void setmList(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public List<String> getmList() {
        return mList;
    }

    public void addItem(String path){
        this.mList.add(path);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.release_photo_item_single,parent,false);
        return new ReleaseHolderSingle(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String path=mList.get(position);
        Bitmap bitmap=BitmapFactory.decodeFile(path);
        ((ReleaseHolderSingle) holder).photoImg.setImageBitmap(bitmap);
        ((ReleaseHolderSingle) holder).delTv.setOnClickListener(view -> {
            mList.remove(position);
            notifyDataSetChanged();
            listener.updateAdd(getSize());
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    private class ReleaseHolderSingle extends RecyclerView.ViewHolder {
        ImageView photoImg;
        TextView delTv;
        public ReleaseHolderSingle(@NonNull View itemView) {
            super(itemView);
            photoImg=itemView.findViewById(R.id.release_photo_img);
            delTv=itemView.findViewById(R.id.release_photo_del);
        }
    }
    public int getSize(){
        return mList.size();
    }
    public interface ReleaseListener{
        void updateAdd(int size);
    }
}
