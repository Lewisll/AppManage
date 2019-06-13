package com.bw.appmanage.common.utils;

import android.app.Activity;
import android.content.Context;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.widget.Toast;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/6/3
 * <br/>desc  :
 * </pre>
 */
public class CommonUtils {

    /**
     * 显示toast
     */
    private static Toast toast;

    public static void showToast(String content, Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 获取资源ｉｄ
     */
    public static int getResourceId(String name, String type, Context context) {
        int id = 0;
        try {
            id = context.getResources().getIdentifier(name, type, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" type:" + type + ",name:" + name + " NOT FOUND!");
        }
        return id;
    }

    /**
     * 获取字符串资源的值
     */
    public static String getStringByName(String name, Context context) {
        int id = 0;
        try {
            id = context.getResources().getIdentifier(name, "string", context.getPackageName());
            return context.getResources().getString(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" type:string" + ",name:" + name + " NOT FOUND!");
        }
        return null;
    }

    /**
     * dipתΪ px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px תΪ dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     */
    public final static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的宽度
     */
    public final static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    //系统函数，字符串转换 long -String (kb)
    public static String formateFileSize(Context context, long size) {
        return Formatter.formatFileSize(context, size);
    }


}
