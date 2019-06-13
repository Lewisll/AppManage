package com.bw.appmanage.common.utils;

import android.Manifest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/25.
 */
public class Permissions {
    public final static Map<String, String> initPermissionMap() {

        Map<String, String> map = new HashMap<String, String>();
        map.put(Manifest.permission.ACCESS_CHECKIN_PROPERTIES, "访问登记属性");
        map.put(Manifest.permission.ACCESS_COARSE_LOCATION, "大概位置");
        map.put(Manifest.permission.ACCESS_FINE_LOCATION, "精准的(GPS)位置");
        map.put(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, "访问额外的位置信息提供程序命令");
        map.put(Manifest.permission.ACCESS_NETWORK_STATE, "查看网络状态");
        map.put(Manifest.permission.ACCESS_WIFI_STATE, "查看 WLAN 状态");
        map.put(Manifest.permission.ACCOUNT_MANAGER, "作为帐户身份验证程序");
        map.put(Manifest.permission.ADD_VOICEMAIL, "允许应用程序添加系统中的语音邮件");

        map.put(Manifest.permission.BATTERY_STATS, "修改电池统计信息");
        map.put(Manifest.permission.BIND_ACCESSIBILITY_SERVICE, "");
        map.put(Manifest.permission.BIND_APPWIDGET, "选择窗口小部件");
        map.put(Manifest.permission.BIND_DEVICE_ADMIN, "与设备管理器交互");
        map.put(Manifest.permission.BIND_DREAM_SERVICE, "");
        map.put(Manifest.permission.BIND_INPUT_METHOD, "绑定至输入法");
        map.put(Manifest.permission.BIND_NFC_SERVICE, "");
        map.put(Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE, "");
        map.put(Manifest.permission.BIND_PRINT_SERVICE, "");
        map.put(Manifest.permission.BIND_TEXT_SERVICE, "必须通过TextService服务来请求");
        map.put(Manifest.permission.BIND_VOICE_INTERACTION, "");
        map.put(Manifest.permission.BIND_VPN_SERVICE, "必须通过VpnService服务来请求");
        map.put(Manifest.permission.BIND_WALLPAPER, "绑定到壁纸");
        map.put(Manifest.permission.BLUETOOTH, "创建蓝牙连接");
        map.put(Manifest.permission.BLUETOOTH_ADMIN, "允许应用程序搜索并且配对蓝牙设备");
        map.put(Manifest.permission.BLUETOOTH_PRIVILEGED, "");
        map.put(Manifest.permission.BODY_SENSORS, "人体传感器");
        map.put(Manifest.permission.BROADCAST_PACKAGE_REMOVED, "发送包删除的广播");
        map.put(Manifest.permission.BROADCAST_SMS, "发送短信收到的广播");
        map.put(Manifest.permission.BROADCAST_STICKY, "发送置顶广播");
        map.put(Manifest.permission.BROADCAST_WAP_PUSH, "WAP PUSH广播");

        map.put(Manifest.permission.CALL_PHONE, "直接拨打电话号码");
        map.put(Manifest.permission.CALL_PRIVILEGED, "直接呼叫任何电话号码");
        map.put(Manifest.permission.CAMERA, "拍照");
        map.put(Manifest.permission.CAPTURE_AUDIO_OUTPUT, "");
        map.put(Manifest.permission.CAPTURE_SECURE_VIDEO_OUTPUT, "");
        map.put(Manifest.permission.CAPTURE_VIDEO_OUTPUT, "");
        map.put(Manifest.permission.CHANGE_COMPONENT_ENABLED_STATE, "启用或停用应用程序组件");
        map.put(Manifest.permission.CHANGE_CONFIGURATION, "更改用户界面设置");
        map.put(Manifest.permission.CHANGE_NETWORK_STATE, "更改网络连接性");
        map.put(Manifest.permission.CHANGE_WIFI_MULTICAST_STATE, "允许接收WLAN多播");
        map.put(Manifest.permission.CHANGE_WIFI_STATE, "更改 WLAN 状态");
        map.put(Manifest.permission.CLEAR_APP_CACHE, "删除所有应用程序缓存数据");
        map.put(Manifest.permission.CONTROL_LOCATION_UPDATES, "控制位置更新通知");

        map.put(Manifest.permission.DELETE_CACHE_FILES, "删除其他应用程序的缓存");
        map.put(Manifest.permission.DELETE_PACKAGES, "删除应用程序");
        map.put(Manifest.permission.DIAGNOSTIC, "读取/写入诊断所拥有的资源");
        map.put(Manifest.permission.DISABLE_KEYGUARD, "停用键锁");
        map.put(Manifest.permission.DUMP, "检索系统内部状态");

        map.put(Manifest.permission.EXPAND_STATUS_BAR, "展开/收拢状态栏");

        map.put(Manifest.permission.FACTORY_TEST, "在出厂测试模式下运行");

        map.put(Manifest.permission.GET_ACCOUNTS, "发现已知帐户");
        map.put(Manifest.permission.GET_PACKAGE_SIZE, "计算应用程序存储空间");
        map.put(Manifest.permission.GLOBAL_SEARCH, "全局搜索");
        map.put(Manifest.permission.GET_TASKS, "检索当前运行的应用程序");

        map.put(Manifest.permission.INSTALL_LOCATION_PROVIDER, "允许应用程序程序安装一个位置服务到位置管理器");
        map.put(Manifest.permission.INSTALL_PACKAGES, "直接安装应用程序");
        map.put(Manifest.permission.INSTALL_SHORTCUT, "安装快捷方式");
        map.put(Manifest.permission.INTERNET, "访问网络");

        map.put(Manifest.permission.KILL_BACKGROUND_PROCESSES, "结束后台进程");

        map.put(Manifest.permission.LOCATION_HARDWARE, "");

        map.put(Manifest.permission.MANAGE_DOCUMENTS, "允许一个应用程序来管理文档的访问");
        map.put(Manifest.permission.MASTER_CLEAR, "恢复出厂设置");
        map.put(Manifest.permission.MEDIA_CONTENT_CONTROL, "允许对多媒体文件进行控制");
        map.put(Manifest.permission.MODIFY_AUDIO_SETTINGS, "更改您的音频设置");
        map.put(Manifest.permission.MODIFY_PHONE_STATE, "修改手机状态");
        map.put(Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, "格式化外部存储设备");
        map.put(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, "装载和卸载文件系统");

        map.put(Manifest.permission.NFC, "允许NFC通讯");

        map.put(Manifest.permission.PROCESS_OUTGOING_CALLS, "拦截外拨电话");
        map.put(Manifest.permission.PERSISTENT_ACTIVITY, "让应用程序始终运行");

        map.put(Manifest.permission.READ_CALENDAR, "读取日历活动");
        map.put(Manifest.permission.READ_CALL_LOG, "读取通话记录");
        map.put(Manifest.permission.READ_CONTACTS, "读取联系人数据");
        map.put(Manifest.permission.READ_EXTERNAL_STORAGE, "读取您的SD卡中的内容");
        map.put(Manifest.permission.READ_FRAME_BUFFER, "读取帧缓冲区");
        map.put(Manifest.permission.READ_LOGS, "读取系统日志文件");
        map.put(Manifest.permission.READ_PHONE_STATE, "读取手机状态和身份");
        map.put(Manifest.permission.READ_SMS, "读取短信或彩信");
        map.put(Manifest.permission.READ_SYNC_SETTINGS, "读取同步设置");
        map.put(Manifest.permission.READ_SYNC_STATS, "读取同步统计信息");
        map.put(Manifest.permission.READ_VOICEMAIL, "");
        map.put(Manifest.permission.REBOOT, "强行重新启动手机");
        map.put(Manifest.permission.RECEIVE_BOOT_COMPLETED, "开机时自动启动");
        map.put(Manifest.permission.RECEIVE_MMS, "接收彩信");
        map.put(Manifest.permission.RECEIVE_SMS, "接收短信");
        map.put(Manifest.permission.RECEIVE_WAP_PUSH, "接收 WAP");
        map.put(Manifest.permission.RECORD_AUDIO, "录音");
        map.put(Manifest.permission.REORDER_TASKS, "对正在运行的应用程序重新排序");
        map.put(Manifest.permission.READ_INPUT_STATE, "记录您键入的内容和执行的操作");
        map.put(Manifest.permission.RESTART_PACKAGES, "重启程序");

        map.put(Manifest.permission.SEND_RESPOND_VIA_MESSAGE, "允许用户在来电的时候用你的应用进行即时的短信息回复");
        map.put(Manifest.permission.SEND_SMS, "发送短信");
        map.put(Manifest.permission.SET_ALARM, "设置闹钟");
        map.put(Manifest.permission.SET_ALWAYS_FINISH, "关闭所有后台应用程序");
        map.put(Manifest.permission.SET_ANIMATION_SCALE, "修改全局动画速度");
        map.put(Manifest.permission.SET_DEBUG_APP, "启用应用程序调试");
        map.put(Manifest.permission.SET_PROCESS_LIMIT, "限制运行的进程个数");
        map.put(Manifest.permission.SET_TIME, "设置系统时间");
        map.put(Manifest.permission.SET_TIME_ZONE, "设置时区");
        map.put(Manifest.permission.SET_WALLPAPER, "设置壁纸");
        map.put(Manifest.permission.SET_WALLPAPER_HINTS, "设置壁纸大小提示");
        map.put(Manifest.permission.SIGNAL_PERSISTENT_PROCESSES, "向应用程序发送 Linux 信号");
        map.put(Manifest.permission.STATUS_BAR, "停用或修改状态栏");
        map.put(Manifest.permission.SYSTEM_ALERT_WINDOW, "显示系统级警报");
        map.put(Manifest.permission.SET_PREFERRED_APPLICATIONS, "设置首选应用程序");

        map.put(Manifest.permission.TRANSMIT_IR, "允许使用设备的红外发射器");

        map.put(Manifest.permission.UNINSTALL_SHORTCUT, "卸载快捷方式");
        map.put(Manifest.permission.UPDATE_DEVICE_STATS, "更新设备状态");

        map.put(Manifest.permission.USE_SIP, "使用SIP视频");


        map.put(Manifest.permission.VIBRATE, "控制振动器");

        map.put(Manifest.permission.WAKE_LOCK, "防止手机休眠");
        map.put(Manifest.permission.WRITE_APN_SETTINGS, "写入\\“接入点名称\\”设置");
        map.put(Manifest.permission.WRITE_CALENDAR, "添加或修改日历活动以及向邀请对象发送电子邮件");
        map.put(Manifest.permission.WRITE_CALL_LOG, "写入通话记录");
        map.put(Manifest.permission.WRITE_CONTACTS, "写入联系数据");
        map.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "写入外部存储");
        map.put(Manifest.permission.WRITE_GSERVICES, "修改 Google 地图");
        map.put(Manifest.permission.WRITE_SECURE_SETTINGS, "读写系统敏感设置");
        map.put(Manifest.permission.WRITE_SETTINGS, "读写系统设置");
        map.put(Manifest.permission.WRITE_SYNC_SETTINGS, "允许程序写入同步设置");
        map.put(Manifest.permission.WRITE_VOICEMAIL, "");

        return map;
    }

}
