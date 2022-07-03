package com.Syufei.bybook.ui.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.lzhihua.bycar.MineMomentsActivity;
import com.lzhihua.bycar.R;
import com.lzhihua.bycar.bean.CommunityBean;
import com.lzhihua.bycar.databinding.MoreFragmentBinding;
import com.lzhihua.bycar.ui.ImpairActivity;
import com.lzhihua.bycar.ui.PurchaseActivity;
import com.lzhihua.bycar.ui.ReleaseMessageactivity;
import com.lzhihua.bycar.ui.TryCarActivity;
import com.lzhihua.bycar.ui.adapter.MomentItemAdapter;
import com.lzhihua.bycar.ui.viewmodel.MorefragViewmodel;

import java.util.List;

public class MoreFragment extends Fragment {
    private MorefragViewmodel viewmodel;
    private MoreFragmentBinding binding;
    private MomentItemAdapter adapter;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    public static final int PUBLISH_MOMENT = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case PUBLISH_MOMENT:
                if (resultCode==RESULT_OK){
                    CommunityBean.CreateCommentResp commentResp=(CommunityBean.CreateCommentResp) data.getSerializableExtra("result");
                    if (commentResp.getStatus().equals("success")){
                        viewmodel.refreshData();
                    }
                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MoreFragmentBinding.inflate(inflater);
        viewmodel = new ViewModelProvider(this).get(MorefragViewmodel.class);
        adapter = new MomentItemAdapter(getContext());
        adapter.setMorefragViewmodel(viewmodel);
        progressDialog = new ProgressDialog(getContext());
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.show_bigimg);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        viewmodel.setType(1);
        viewmodel.getMomentLivedata().observe(getViewLifecycleOwner(), new Observer<List<CommunityBean.Moment>>() {
            @Override
            public void onChanged(List<CommunityBean.Moment> moments) {
                if (viewmodel.getOffset().get()==1){
                    adapter.setMomentList(moments);
                }else if (viewmodel.getOffset().get()>1){
                    adapter.addMoment(moments);
                }

            }
        });
        viewmodel.getShowBigImg().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.equals("")) {
                    WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                    lp.width = getContext().getResources().getDisplayMetrics().widthPixels;
                    int height = getContext().getResources().getDisplayMetrics().heightPixels;
                    height *= 0.8;
                    lp.height = height;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();
                    ImageView img = dialog.findViewById(R.id.image);
                    Glide.with(getContext()).load(s).into(img);
                    img.setOnClickListener(view -> {
                        dialog.dismiss();
                    });
                }

            }
        });
        viewmodel.getUpdateLike().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int pos=integer.intValue();
                if (pos!=-1 ){
                    RecyclerView.ViewHolder holder=binding.momentRecycler.getChildViewHolder(binding.momentRecycler.getChildAt(pos));
                    adapter.updateItem(holder);
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return super.canScrollVertically();
            }
        };
        binding.momentRecycler.setLayoutManager(linearLayoutManager);
        binding.momentRecycler.setAdapter(adapter);
        binding.momentTopSubmit.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ReleaseMessageactivity.class);
            startActivityForResult(intent,PUBLISH_MOMENT);
        });
        binding.momentTopRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewmodel.refreshData();
                binding.momentTopRefresh.setRefreshing(false);
            }
        });
        binding.momentRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition ,角标值
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    //所有条目,数量值
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        viewmodel.nextPage();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                //dx>0:向右滑动,dx<0:向左滑动
                //dy>0:向下滑动,dy<0:向上滑动
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }
        });
        binding.momentTopFast.momentOpenPurchase.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), PurchaseActivity.class);
            getActivity().startActivity(intent);
        });
        binding.momentTopFast.momentOpenCaring.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), ImpairActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt("impair_type",0);
            intent.putExtra("impair_bundle",bundle);
            getActivity().startActivity(intent);
        });
        binding.momentUserAvatar.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), MineMomentsActivity.class);
            getActivity().startActivity(intent);
        });
        binding.momentTopFast.momentOpenTrycar.setOnClickListener(view -> {
            Intent intent=new Intent(getContext(), TryCarActivity.class);
            getActivity().startActivity(intent);
        });
        return binding.getRoot();
    }
}
