package com.Syufei.bybook.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.Syufei.bybook.R;

import java.util.ArrayList;
import java.util.List;

public class UITools {
    private  static float density = Resources.getSystem().getDisplayMetrics().density;
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dp(float pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return (int) (0.5f + dpValue * density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(float pxValue) {
        return (pxValue / density);
    }

    public static void  showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static Drawable getDrawable(Resources resources,final String id){
        Drawable drawable;
        switch (id){
            case "ec6":
                drawable=resources.getDrawable(R.drawable.ec6);
                break;
            case "es6":
                drawable=resources.getDrawable(R.drawable.es6);
                break;
            case "es8":
                drawable=resources.getDrawable(R.drawable.es8);
                break;
            case "et5":
                drawable=resources.getDrawable(R.drawable.et5);
                break;
            case "et7":
                drawable=resources.getDrawable(R.drawable.et7);
                break;
            default:
                drawable=resources.getDrawable(R.drawable.et7);
                break;
        }
        return drawable;
    }

    public static List<Integer> getWidthAndHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
//        int screenWidth = (int) (width / density);
//        int screenHeight = (int) (height / density);// 屏幕高度(dp)

        List<Integer> res=new ArrayList<>();
        res.add(width);
        res.add(height);
        return res;
    }
}
