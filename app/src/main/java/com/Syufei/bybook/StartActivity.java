package com.Syufei.bybook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.Syufei.bybook.common.BaseActivity;

import site.gemus.openingstartanimation.OpeningStartAnimation;

public class StartActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setWhiteStatusBar();
        OpeningStartAnimation openingStartAnimation = new OpeningStartAnimation.Builder(this)
                .setAppIcon(getResources().getDrawable(R.drawable.start_bg)) //设置图标
                .setAppStatement("智能出行，畅享未来") //设置一句话描述
                .setAnimationFinishTime(3000) // 设置动画的消失时长
                .create();
        openingStartAnimation.show(this);
        new Handler(new Handler.Callback() {
            //处理接收到的消息的方法
            @Override
            public boolean handleMessage(Message arg0) {
                Intent intent = new Intent(StartActivity.this, com.Syufei.bybook.ui.MainActivity.class);
                startActivity(intent);
                finish();
                return false;
            }
        }).sendEmptyMessageDelayed(0, 3000); //表示延时三秒进行任务的执行
    }
}