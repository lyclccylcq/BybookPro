package com.Syufei.bybook.util;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.Syufei.bybook.R;

//动画工具
public class AnimationTools {
    public static AnimationTools animationTools;
    private Context context;
    private AnimationTools(Context context){
        this.context=context;
        translateAnimation();
        scaleAnimation();
        alphaAnimation();
        translateAnimationReverse();
    }
    public static AnimationTools getInstance(Context context){
        if(animationTools==null){
            synchronized (AnimationTools.class){
                animationTools=new AnimationTools(context);
            }
        }
        return animationTools;
    }

    public AlphaAnimation alphaAniShow, alphaAniHide;
    public TranslateAnimation translateAniShow, translateAniHide;
    public TranslateAnimation translateAniShow_reverse, translateAniHide_reverse;
    public Animation bigAnimation, smallAnimation;
    public void translateAnimationReverse(){
        translateAniShow_reverse=new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                1
        );
        translateAniShow_reverse.setDuration(500);
    }
    public void translateAnimation() {
        //向上位移显示动画  从自身位置的最下端向上滑动了自身的高度
        translateAniShow = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                1,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        translateAniShow.setRepeatMode(Animation.REVERSE);
        translateAniShow.setDuration(500);

        //向下位移隐藏动画  从自身位置的最上端向下滑动了自身的高度
        translateAniHide = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                1);//fromXValue表示结束的Y轴位置
        translateAniHide.setRepeatMode(Animation.REVERSE);
        translateAniHide.setDuration(500);
    }

    //缩放动画
    public void scaleAnimation() {
        //放大
        bigAnimation = AnimationUtils.loadAnimation(this.context, R.anim.scale_big);
        //缩小
        smallAnimation = AnimationUtils.loadAnimation(this.context, R.anim.scale_small);

    }

    //透明度动画
    public void alphaAnimation() {
        //显示
        alphaAniShow = new AlphaAnimation(0, 1);//百分比透明度，从0%到100%显示
        alphaAniShow.setDuration(1000);//一秒

        //隐藏
        alphaAniHide = new AlphaAnimation(1, 0);
        alphaAniHide.setDuration(1000);
    }
}
