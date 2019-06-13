package com.bw.appmanage.common.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class AppUtils extends BaseUtils {

    public static AppUtils appUtils;
    public static PackageManager packageManager;

    private AppUtils(Context context) {
        packageManager = context.getPackageManager();
    }

    /**
     * 从集合中获取相同元素的集合
     */
    public static List<AppInfo> filterList(List<AppInfo> list, String name) {
        List<AppInfo> appPartInfoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                AppInfo appPartInfo = new AppInfo();
                appPartInfo.setIcon(list.get(i).getIcon());
                appPartInfo.setName(list.get(i).getName());
                appPartInfo.setPackageName(list.get(i).getPackageName());
                appPartInfo.setVersionCode(list.get(i).getVersionCode());
                appPartInfo.setVersionName(list.get(i).getVersionName());

                appPartInfoList.add(appPartInfo);
            }
        }
        return appPartInfoList;
    }


    /**
     * 判断某一个应用程序是不是系统的应用程序,如果是返回true，否则返回false。
     */
    public static boolean filterApp(ApplicationInfo info) {
        //有些系统应用是可以更新的，如果用户自己下载了一个系统的应用来更新了原来的，它还是系统应用，这个就是判断这种情况的
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {//判断是不是系统应用
            return true;
        }
        return false;
    }


    public static OnClickDialogListener onClickDialogListener;

    public static void setOnClickDialogListener(OnClickDialogListener listener) {
        onClickDialogListener = listener;
    }

    public interface OnClickDialogListener {
        void clickOkBtn(View view);

        void clickCancelBtn(View view);
    }


    /**
     * 根据包名打开应用
     */
    public static void openApp(String packageName, PackageManager packageManager, Activity activity) {
        PackageInfo pi = null;
        try {
            pi = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pi == null) {
            CommonUtils.showToast("该应用已被删除", activity);
            return;
        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);
        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);
        if (apps == null || apps.size() == 0) {
            CommonUtils.showToast("该应用不能执行此操作", activity);
            return;
        }
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).activityInfo.packageName.equals(packageName)) {
                System.out.println(packageName + ":" + apps.get(i).activityInfo.name);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName(packageName, apps.get(i).activityInfo.name);
                intent.setComponent(cn);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                break;
            }
        }
    }

    /**
     * 打开app
     *
     * @param packageName
     */
    public static void launchApp(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            CommonUtils.showToast("该应用已被删除", getApp());
            return;
        }
        Intent intent = getApp().getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApp().startActivity(intent);
    }


    /**
     * 根据包名卸载app
     */
    public static void unInstallApp(String packageName, Context context) {
        Uri uri = Uri.parse("package:" + packageName);//获取删除包名的URI
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DELETE);//设置我们要执行的卸载动作
        intent.setData(uri);//设置获取到的UR
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到APP权限页面
     **/
    public static void turnToPermissionManager(String packageName) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (!isIntentAvailable(getApp(), intent)) return;
        getApp().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    /**
     * 打开应用具体设置
     */
    public static void launchAppDetailsSettings(Context context, String packageName) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (!isIntentAvailable(context, intent)) {
            return;
        }
        context.getApplicationContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /*********************************** APP 属性信息 **********************************************************************************************/

    /**
     * 根据包名获取APP图标
     *
     * @param packageName
     * @return
     */
    public static Drawable getAppIcon(final String packageName) {
        if (TextUtils.isEmpty(packageName)) return null;
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前APP包名
     *
     * @return
     */
    public static String getAppPackageName() {
        return getApp().getPackageName();
    }

    /**
     * 通过包名获取APP名
     *
     * @param packageName
     * @return
     */
    public static String getAppName(String packageName) {
        if (TextUtils.isEmpty(packageName)) return "";
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 通过包名获取 App 版本号
     *
     * @param packageName
     * @return
     */
    public static String getAppVersionName(String packageName) {
        if (TextUtils.isEmpty(packageName)) return "";
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 通过包名获取 App 版本码
     *
     * @param packageName
     * @return
     */
    public static int getAppVersionCode(String packageName) {
        if (TextUtils.isEmpty(packageName)) return -1;
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 通过包名获取APP签名的 SHA1 值
     *
     * @param packageName
     * @return
     */
    public static String getAppSignatureSHA1(String packageName) {
        return getAppSignatureHash(packageName, "SHA1");
    }

    /**
     * 通过包名获取APP的 SHA256 值
     *
     * @param packageName
     * @return
     */
    public static String getAppSignatureSHA256(String packageName) {
        return getAppSignatureHash(packageName, "SHA256");
    }

    /**
     * 通过包名获取APP的 MD5 值
     *
     * @param packageName
     * @return
     */
    public static String getAppSignatureMD5(String packageName) {
        return getAppSignatureHash(packageName);
    }

    /**
     * 通过包名获取APP 信息
     *
     * @param packageName
     * @return
     */
    public static AppInfo getAppInfo(String packageName) {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过包名获取APP 安装时间
     *
     * @param packageName
     * @return
     */
    public static String getInstallTime(String packageName) {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(pi.firstInstallTime);
            return simpleDateFormat.format(date);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过包名获取APP 权限信息
     *
     * @param packageName
     * @return
     */
    public static String getPersmissions(String packageName) {
        try {
            PackageManager pm = getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            String[] requestedPermissions = pi.requestedPermissions;

            StringBuilder builder = new StringBuilder();

            if (requestedPermissions != null && requestedPermissions.length != 0) {
                for (Map.Entry<String, String> entry : Permissions.initPermissionMap().entrySet()) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        if (requestedPermissions[i].equals(entry.getKey())) {
                            builder.append(entry.getKey() + "\n" + "(" + entry.getValue() + ")" + "\n" + "\n");
                        }
                    }
                }
                return builder.toString();//权限
            } else {
                return "权限为空";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取所有已安装 App 信息
     *
     * @return
     */
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = getApp().getPackageManager();
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) continue;
            list.add(ai);
        }
        return list;
    }

    public static void closeKeyboard(EditText view) {
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }


    public static void openKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


}




















