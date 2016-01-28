package com.asdzheng.suitedrecyclerview.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.asdzheng.suitedrecyclerview.MyApplication;

/**
 * Created by asdzheng on 2015/12/28.
 */
public class DisplayUtils {

    public static int dpToPx(final float n, final Context context) {
        return (int) TypedValue.applyDimension(1, n, context.getResources().getDisplayMetrics());
    }

    public static int pxToDp(final int n, final Context context) {
        return (int)TypedValue.applyDimension(0, (float)n, context.getResources().getDisplayMetrics());
    }

    // 获取 设备的Hight
    public static int getDeviceHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) MyApplication.context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    // 获取 设备的Width
    public static int getDisplayWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) MyApplication.context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
