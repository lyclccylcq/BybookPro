package com.Syufei.bybook.util;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

public class VibratorUtil {

    private Activity mActivity;
    private Vibrator vibrator;

    public VibratorUtil(Activity activity) {
        mActivity = activity;
        if (vibrator == null) {
            vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        }
    }

    /**
     * 启动振动一次
     *
     * @param time 持续振动时间 毫秒
     */
    public void startVibrator(long time) {
        vibrator.vibrate(time);
    }

    /**
     * 启动振动 可设置振动模式
     *
     * @param pattern 振动频次
     * @param repeat  重复模式 -1为不重复，0为一直震动
     */
    public void startVibratorForRepeat(long[] pattern, int repeat) {
        vibrator.vibrate(pattern, repeat);
    }

    /**
     * 取消振动
     */
    public void cancleVibrator() {
        if (null != vibrator) {
            vibrator.cancel();
        }
    }

}