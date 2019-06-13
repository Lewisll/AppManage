package com.bw.appmanage.common.utils;

import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/6/3
 * <br/>desc  :
 * </pre>
 */
public class LogUtils {
    private static final String GLOBAL_TAG = "AppManage";

    /**
     * 初始化配置logger
     *
     * @param showThread   是否显示线程信息。默认值true
     * @param methodCount  要显示的方法行数。默认值2
     * @param methodOffset 隐藏内部方法调用到偏移量。默认值5
     * @param isLoggable   是否要隐藏日志输出：true 打印 ，false 隐藏
     */
    public static void initLog(boolean showThread, int methodCount, int methodOffset, final boolean isLoggable) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(showThread)   // (Optional) Whether to show thread info or not. Default true
                .methodCount(methodCount)         // (Optional) How many method line to show. Default 2
                .methodOffset(methodOffset)      // (Optional) Hides internal method calls up to offset. Default 5
                .tag(GLOBAL_TAG)               // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return isLoggable;
            }
        });
    }

    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }

    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void e(String tag, String msg) {
        Logger.t(tag).e(msg);
    }

    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void w(String tag, String msg) {
        Logger.t(tag).w(msg);
    }

    public static void w(String msg) {
        Logger.w(msg);
    }


    public static void v(String tag, String msg) {
        Logger.t(tag).v(msg);
    }

    public static void v(String msg) {
        Logger.v(msg);
    }

    public static void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void wtf(String tag, String msg) {
        Logger.t(tag).wtf(msg);
    }

    public static void wtf(String msg) {
        Logger.wtf(msg);
    }

    public static void json(String tag, String msg) {
        Logger.t(tag).json(msg);
    }

    public static void json(String msg) {
        Logger.json(msg);
    }

    public static void xml(String tag, String msg) {
        Logger.t(tag).json(msg);
    }

    public static void xml(String msg) {
        Logger.json(msg);
    }


}
