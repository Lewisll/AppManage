package com.bw.appmanage.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bw.appmanage.app.AppApplication;
import com.bw.appmanage.common.utils.LogUtils;

/**
 * Created by Ben
 */
public class UninstallReceiver extends BroadcastReceiver {

    public UninstallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //接收广播：设备上删除了一个应用程序包。
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getData().getSchemeSpecificPart();
            AppApplication.isUnstall = true;
            listener.reflashUI(packageName, true);
            LogUtils.e("UninstallReceiver OnReceive--->packageName:" + packageName);
        }
    }

    public OnUninstallOtherApplicationListener listener;

    public void setListener(OnUninstallOtherApplicationListener listener) {
        this.listener = listener;
    }

    public interface OnUninstallOtherApplicationListener {
        void reflashUI(String packageName, boolean isUnstall);
    }
}
