package com.Syufei.bybook.ui;

import android.os.Bundle;
import android.view.View;

import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.databinding.ActivityNoticeBinding;

public class NoticeActivity extends BaseActivity {
//    通过传入的pagetype选择加载的页面，这里将“关于我们”和“我的积分”放到一起处理,
//    1：显示积分    2：显示关于我们
    private ActivityNoticeBinding activityNoticeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentStatusBar();
        activityNoticeBinding=ActivityNoticeBinding.inflate(getLayoutInflater());
        setContentView(activityNoticeBinding.getRoot());
        int pageType=getIntent().getIntExtra("page_type",-1);
        if(pageType==-1){
            finish();
        }
        activityNoticeBinding.noticeAboutUs.aboutUsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoticeActivity.this.finish();
            }
        });
        activityNoticeBinding.noticeMyScore.scroeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoticeActivity.this.finish();
            }
        });
        activityNoticeBinding.noticeMyScore.getRoot().setVisibility(pageType==1? View.VISIBLE : View.GONE);
        activityNoticeBinding.noticeAboutUs.getRoot().setVisibility(pageType==2? View.VISIBLE : View.GONE);
    }
}