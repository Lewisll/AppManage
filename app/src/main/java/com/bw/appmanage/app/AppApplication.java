package com.bw.appmanage.app;

import android.app.Application;

import com.bw.appmanage.common.utils.LogUtils;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/6/6
 * <br/>desc  :
 * </pre>
 */
public class AppApplication extends Application {

    private static AppApplication mAppApplication;
    //    private SQLHelper sqlHelper;
    public static boolean isUnstall = false;


    @Override
    public void onCreate() {
        super.onCreate();
        //init Log
        LogUtils.initLog(true, 2, 5, true);

        mAppApplication = this;
    }

    /**
     * 获取Application
     */
    public static AppApplication getIntance() {
        return mAppApplication;
    }
}
