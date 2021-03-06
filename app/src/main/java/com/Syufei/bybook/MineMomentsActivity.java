package com.Syufei.bybook;

import android.app.Dialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.Syufei.bybook.bean.CommunityBean;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.databinding.ActivityMineMomentsBinding;
import com.Syufei.bybook.ui.adapter.MomentItemAdapter;
import com.Syufei.bybook.ui.viewmodel.MorefragViewmodel;

import java.util.List;

public class MineMomentsActivity extends BaseActivity {
    private MorefragViewmodel morefragViewmodel;
    private MomentItemAdapter adapter;
    private ActivityMineMomentsBinding binding;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMineMomentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTransparentStatusBar();
        morefragViewmodel = new ViewModelProvider(this).get(MorefragViewmodel.class);
        adapter = new MomentItemAdapter(this);
        adapter.setMorefragViewmodel(morefragViewmodel);
        adapter.setType(1);
        morefragViewmodel.setAdapter(adapter);
        morefragViewmodel.setType(0);
        morefragViewmodel.getMomentLivedata().observe(this, new Observer<List<CommunityBean.Moment>>() {
            @Override
            public void onChanged(List<CommunityBean.Moment> moments) {
                adapter.setMomentList(moments);
            }
        });
        morefragViewmodel.getShowBigImg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.equals("")) {
                    WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                    lp.width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    height *= 0.8;
                    lp.height = height;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();
                    ImageView img = dialog.findViewById(R.id.image);
                    Glide.with(MineMomentsActivity.this).load(s).into(img);
                    img.setOnClickListener(view -> {
                        dialog.dismiss();
                    });
                }
            }
        });
        binding.mineMomentsRecyeler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // ??????????????????
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //?????????????????????????????????ItemPosition ,?????????
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    //????????????,?????????
                    int totalItemCount = manager.getItemCount();

                    // ???????????????????????????????????????????????????
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        morefragViewmodel.nextPage();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx?????????????????????????????????dy??????????????????????????????
                //dx>0:????????????,dx<0:????????????
                //dy>0:????????????,dy<0:????????????
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }
        });
        binding.mineMomentsRecyeler.setLayoutManager(new LinearLayoutManager(this));
        binding.mineMomentsRecyeler.setAdapter(adapter);
        binding.mineMomentsTop.topTv.setText("????????????");
        binding.mineMomentsTop.titleBack.setOnClickListener(view -> {
            finish();
        });
    }
}