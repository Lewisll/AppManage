package com.bw.appmanage.app;

import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.appmanage.R;
import com.bw.appmanage.common.utils.AppUtils;
import com.bw.appmanage.common.utils.BaseUtils;
import com.bw.appmanage.common.utils.CommonUtils;
import com.bw.appmanage.common.utils.LogUtils;
import com.bw.appmanage.model.Config;

import java.lang.reflect.Method;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/6/11
 * <br/>desc  :
 * </pre>
 */
public class DetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView detail_iv_back, detailAppIcon;
    private TextView detailAppName;
    private TextView detail_appName, detail_packageName, detail_sign, detail_sign_sha1, detail_sign_sha256, detail_versionName, detail_versionCode, detail_installTime;
    private TextView detail_totalMemory, detail_appMemory, detail_database, detail_cache;
    private TextView permission;
    private Button detail_permission;
    private BaseUtils.AppInfo appInfo;
    private long cachesize; //缓存大小
    private long datasize;  //数据大小
    private long codesize;  //应用程序大小
    private long totalsize; //总大小
    private String d_cachesize;
    private String d_datasize;
    private String d_codesize;
    private String d_totalsize;

    @Override
    protected int getContentView() {
        return R.layout.activity_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();
        initView();
        setData();
    }


    private void initView() {
        detail_iv_back = (ImageView) findViewById(R.id.detail_iv_back);
        //icon
        detailAppIcon = (ImageView) findViewById(R.id.detailAppIcon);
        //应用名
        detailAppName = (TextView) findViewById(R.id.detailAppName);
        detail_permission = (Button) findViewById(R.id.detail_permission);
        //应用名
        detail_appName = (TextView) findViewById(R.id.detail_appName);
        //包名
        detail_packageName = (TextView) findViewById(R.id.detail_packageName);
        //签名md5
        detail_sign = (TextView) findViewById(R.id.detail_sign);
        //签名sha1
        detail_sign_sha1 = (TextView) findViewById(R.id.detail_sign_sha1);
        detail_sign_sha256 = (TextView) findViewById(R.id.detail_sign_sha256);
        detail_versionName = (TextView) findViewById(R.id.detail_versionName);
        detail_versionCode = (TextView) findViewById(R.id.detail_versionCode);
        detail_installTime = (TextView) findViewById(R.id.detail_installTime);

        detail_totalMemory = (TextView) findViewById(R.id.detail_totalMemory);
        detail_appMemory = (TextView) findViewById(R.id.detail_appMemory);
        detail_database = (TextView) findViewById(R.id.detail_database);
        detail_cache = (TextView) findViewById(R.id.detail_cache);

        permission = (TextView) findViewById(R.id.permission);

        detail_iv_back.setOnClickListener(this);
        detail_permission.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == detail_iv_back) {
            finish();
        } else if (view == detail_permission) {
            AppUtils.turnToPermissionManager(appInfo.getPackageName());
        }
    }


    public void setData() {
        appInfo = (BaseUtils.AppInfo) getIntent().getParcelableExtra(Config.DATA_TAG);
        if (appInfo == null) return;
        String packageName = appInfo.getPackageName();
        detailAppName.setText(appInfo.getName());
        permission.setText(AppUtils.getPersmissions(packageName));
        detail_appName.setText(appInfo.getName());
        detail_packageName.setText(packageName);
        detail_sign.setText(AppUtils.getAppSignatureMD5(packageName));
        detail_sign_sha1.setText(AppUtils.getAppSignatureSHA1(packageName));
        detail_sign_sha256.setText(AppUtils.getAppSignatureSHA256(packageName));
        detail_versionName.setText(AppUtils.getAppVersionName(packageName));
        detail_versionCode.setText(AppUtils.getAppVersionCode(packageName) + "");
        detail_installTime.setText(AppUtils.getInstallTime(packageName));
        if (appInfo.getIcon() != null) {
            detailAppIcon.setImageDrawable(appInfo.getIcon());
        } else {
            detailAppIcon.setImageDrawable(AppUtils.getAppIcon(packageName));
        }

        try {
            PackageManager pm = DetailActivity.this.getPackageManager();
            //通过反射机制获得该隐藏函数
            Method getPackageSizeInfo = pm.getClass().getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
            //调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数
            getPackageSizeInfo.invoke(pm, packageName, new PkgSizeObserver());
        } catch (Exception ex) {
            LogUtils.e("NoSuchMethodException");
            ex.printStackTrace();
        }
    }

    //aidl文件形成的Bindler机制服务类
    public class PkgSizeObserver extends IPackageStatsObserver.Stub {
        /***
         * 回调函数，
         *
         * @param succeeded 代表回调成功
         */
        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                throws RemoteException {
            cachesize = pStats.cacheSize; //缓存大小
            datasize = pStats.dataSize;  //数据大小
            codesize = pStats.codeSize;  //应用程序大小
            totalsize = cachesize + datasize + codesize; //总大小

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putLong("cachesize", cachesize);
                    bundle.putLong("datasize", datasize);
                    bundle.putLong("codesize", codesize);
                    bundle.putLong("totalsize", totalsize);
                    msg.obj = bundle;
                    handler.sendMessage(msg);
                }
            }).start();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = (Bundle) msg.obj;

            d_cachesize = CommonUtils.formateFileSize(DetailActivity.this, bundle.getLong("cachesize"));
            //数据大小
            d_datasize = CommonUtils.formateFileSize(DetailActivity.this, bundle.getLong("datasize"));
            //应用程序大小
            d_codesize = CommonUtils.formateFileSize(DetailActivity.this, bundle.getLong("codesize"));
            //总大小
            d_totalsize = CommonUtils.formateFileSize(DetailActivity.this, bundle.getLong("totalsize"));
            detail_totalMemory.setText(d_totalsize);
            detail_appMemory.setText(d_codesize);
            detail_database.setText(d_datasize);
            detail_cache.setText(d_cachesize);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
