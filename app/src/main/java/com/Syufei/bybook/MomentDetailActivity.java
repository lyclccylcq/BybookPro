package com.Syufei.bybook;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzhihua.bycar.bean.CommunityBean;
import com.lzhihua.bycar.bean.LoginBean;
import com.lzhihua.bycar.common.BaseActivity;
import com.lzhihua.bycar.commonui.PopupDialog;
import com.lzhihua.bycar.databinding.ActivityMomentDetailBinding;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.CommunityRepo;
import com.lzhihua.bycar.ui.adapter.MomentDetailAdapter;
import com.lzhihua.bycar.ui.adapter.MomentDetailImgAdapter;
import com.lzhihua.bycar.ui.dialog.InputCommmentDialog;
import com.lzhihua.bycar.ui.viewmodel.MomentDetailViewmodel;
import com.lzhihua.bycar.util.UITools;

import java.util.Arrays;
import java.util.List;

public class MomentDetailActivity extends BaseActivity implements PopupDialog.onDismissListener {
    private MomentDetailViewmodel viewmodel;
    private CommunityBean.Moment moment;
    private InputCommmentDialog inputCommmentDialog;
    private ActivityMomentDetailBinding binding;
    private MomentDetailImgAdapter imgAdapter;
    private MomentDetailAdapter detailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentStatusBar();
        binding = ActivityMomentDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        moment = (CommunityBean.Moment) getIntent().getSerializableExtra("moment");
        inputCommmentDialog = new InputCommmentDialog(this);
        inputCommmentDialog.setDialogType("inputCommmentDialog");
        inputCommmentDialog.setListener(this);
        viewmodel = new ViewModelProvider(this).get(MomentDetailViewmodel.class);
        progressDialog.setCancelable(false);
        progressDialog.show();
        binding.momentDetailOpenComment.setOnClickListener(view -> {
            inputCommmentDialog.show();
        });
        if (moment == null) {
            finish();
        }
        viewmodel.getUserSelfData().observe(this, new Observer<LoginBean.UserInfo>() {
            @Override
            public void onChanged(LoginBean.UserInfo userInfo) {
                if (userInfo!=null){
                    detailAdapter.setUserInfo(userInfo);
                }
            }
        });
        viewmodel.getGetUserInfo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    progressDialog.dismiss();
                    viewmodel.queryData();
                }
            }
        });
        viewmodel.getIsLastPage().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    UITools.showToast(MomentDetailActivity.this,"没有更多评论了");
                }
            }
        });
        viewmodel.getCommentData().observe(this, new Observer<List<CommunityBean.Comment>>() {
            @Override
            public void onChanged(List<CommunityBean.Comment> comments) {
                detailAdapter.setCommentList(comments);
            }
        });
        viewmodel.getRemoveIndex().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int index=integer.intValue();
                if (index!=-1){
                    detailAdapter.deleteItem(index);
                }
            }
        });
        String[] paths = moment.getImgUrl().split(";");
        binding.momentDetailContent.setText(moment.getContext());
        imgAdapter = new MomentDetailImgAdapter(this);
        imgAdapter.setPaths(Arrays.asList(paths));
        binding.momentDetailImgContainer.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.momentDetailImgContainer.setAdapter(imgAdapter);
        binding.momentDetailTop.topTv.setText("动态详情");
        binding.momentDetailTop.titleBack.setOnClickListener(view -> {
            finish();
        });
        detailAdapter = new MomentDetailAdapter(this);
        detailAdapter.setMomentDetailViewmodel(viewmodel);
        binding.momentDetailRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.momentDetailRecycler.setAdapter(detailAdapter);
        viewmodel.setMomentId(moment.getId());
    }

    @Override
    public void onDismiss(Bundle data, String type) {
        if (type.equals("inputCommmentDialog")) {
            String content = data.getString("input_comment");
            CommunityRepo.createComment(moment.getId(), content, new DataSuccessListenter() {
                @Override
                public void onDataSuccess(Object obj) {
                    viewmodel.queryData();
                }

                @Override
                public void onError(String error) {
                    UITools.showToast(MomentDetailActivity.this, "创建失败，稍后重试");
                }
            });
        }
    }
}