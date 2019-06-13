package com.bw.appmanage.view.fg;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bw.appmanage.R;
import com.bw.appmanage.app.DetailActivity;
import com.bw.appmanage.common.utils.AppUtils;
import com.bw.appmanage.common.utils.BaseUtils;
import com.bw.appmanage.common.utils.CommonUtils;
import com.bw.appmanage.model.Config;
import com.bw.appmanage.model.adapter.HomeLvAdapter;
import com.bw.appmanage.model.db.ChannelItem;
import com.bw.appmanage.model.db.ChannelManage;
import com.bw.appmanage.model.db.SQLHelper;
import com.bw.appmanage.model.db.SQLhelperInstance;
import com.bw.appmanage.receiver.UninstallReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/6/5
 * <br/>desc  :
 * </pre>
 */
public class PagerFragment extends BaseFragment {

    private static final String KEY_TITLE = "text";
    private static final String KEY_ID = "id";
    private String tabTitle;
    private int tabId;
    private static final int MSG = 1000;

    private SwipeMenuListView swipeMenuListView;
    private ProgressBar progressBar;
    private ImageView empty_icon;
    public ArrayList<BaseUtils.AppInfo> partInfoLis = new ArrayList<>();
    private HomeLvAdapter adapter;
    private UninstallReceiver uninstallReceiver;

    public PagerFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnListFragmentInteractionListener) getActivity();
        Bundle arguments = getArguments();
        tabTitle = arguments.getString(KEY_TITLE);
        tabId = arguments.getInt(KEY_ID);
    }

    public static PagerFragment newInstance(String text, int id) {
        PagerFragment pagerFragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, text);
        bundle.putInt(KEY_ID, id);
        pagerFragment.setArguments(bundle);
        return pagerFragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);

        uninstallReceiver = new UninstallReceiver();// UninstallReceiver.getInstance();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter2.addDataScheme("package");
        getActivity().registerReceiver(uninstallReceiver, intentFilter2);

        uninstallReceiver.setListener(new UninstallReceiver.OnUninstallOtherApplicationListener() {
            @Override
            public void reflashUI(String packageName, boolean isUnstall) {
                Message message = new Message();
                message.what = MSG;
                message.obj = packageName;
                handler.sendMessage(message);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pager;
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        swipeMenuListView = (SwipeMenuListView) root.findViewById(R.id.swipeMenuListView);
        empty_icon = (ImageView) root.findViewById(R.id.empty_icon);
        setStyle(swipeMenuListView, mContext);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<BaseUtils.AppInfo> allAppInfos;
                if (!tabTitle.equals(CommonUtils.getStringByName("tab_top_title", mContext))) {
                    allAppInfos = AppUtils.filterList(AppUtils.getAppsInfo(), tabTitle);
                } else {
                    allAppInfos = AppUtils.getAppsInfo();
                }
                Message msg = new Message();
                msg.obj = allAppInfos;
                handler.sendMessage(msg);
            }
        }).start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG) {
                if (partInfoLis.size() == 0) {
                    partInfoLis.clear();
                } else {
                    for (int i = 0; i < partInfoLis.size(); i++) {
                        if (partInfoLis.get(i).getPackageName().equals((String) msg.obj)) {
                            partInfoLis.remove(i);
                            adapter.refreshData(partInfoLis);
                        }
                    }
                }
                if (partInfoLis == null || partInfoLis.size() == 0) {
                    swipeMenuListView.setVisibility(View.GONE);
                    empty_icon.setVisibility(View.VISIBLE);
                }
            }
            partInfoLis = (ArrayList<BaseUtils.AppInfo>) msg.obj;
            if (partInfoLis.size() == 0) {
                empty_icon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                swipeMenuListView.setVisibility(View.GONE);
            } else {
                adapter = new HomeLvAdapter(mContext, partInfoLis);
                swipeMenuListView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                swipeMenuListView.setVisibility(View.VISIBLE);
                itemClick();
            }
        }
    };

    /**
     * item的添加 按钮功能
     */
    private void itemClick() {

        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {

                boolean isAdd = true;
                switch (index) {
                    case 0:

                        if (!TextUtils.isEmpty(tabTitle) && !tabTitle.equals(CommonUtils.getStringByName("tab_top_title", mContext))) {
                            AppUtils.openApp(partInfoLis.get(position).getPackageName(), mContext.getPackageManager(), getActivity());
                        } else {

                            List<ChannelItem> userChannel = ChannelManage.getManage(SQLhelperInstance.getInstance().getSQLHelper()).getUserChannel();
                            List<ChannelItem> otherChannel = ChannelManage.getManage(SQLhelperInstance.getInstance().getSQLHelper()).getOtherChannel();
                            String name = partInfoLis.get(position).getName();

                            //如果tab中没有则添加；若tab中没有，但是在不选中的tab中，则添加到tab，然后删除不在的部分
                            //1.tab中有标签
                            for (int i = 0; i < userChannel.size(); i++) {
                                if (userChannel.get(i).getName().equals(name)) {
                                    CommonUtils.showToast(CommonUtils.getStringByName("addButton_toast", mContext), getContext());
                                    isAdd = false;
                                    break;
                                }
                            }
                            if (isAdd) {
                                ChannelManage.getManage(SQLhelperInstance.getInstance().getSQLHelper()).saveSimpleUserChannel(new ChannelItem(userChannel.size() + 1, name, userChannel.size() + 1, 1));

                                listener.onListFragmentInteraction(ChannelManage.getManage(SQLhelperInstance.getInstance().getSQLHelper()).getUserChannel());


                                for (int i = 0; i < otherChannel.size(); i++) {
                                    if (otherChannel.get(i).getName().equals(name)) {//说明有相同的
                                        //1.先去掉
                                        ChannelManage.getManage(SQLhelperInstance.getInstance().getSQLHelper()).deleleteSimpleChannel(SQLHelper.NAME + "=?", new String[]{name});
                                    }
                                }
                            }
                        }

                        break;
                    case 1:
                        if (!TextUtils.isEmpty(tabTitle) && !tabTitle.equals(CommonUtils.getStringByName("tab_top_title", mContext))) {
                            AppUtils.unInstallApp(partInfoLis.get(position).getPackageName(), getContext());
                        } else {
                            AppUtils.openApp(partInfoLis.get(position).getPackageName(), getContext().getPackageManager(), getActivity());
                        }
                        break;
                    case 2:
                        AppUtils.unInstallApp(partInfoLis.get(position).getPackageName(), getContext());
                        break;
                }
                return false;
            }
        });

//打开应用详情
        swipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(Config.DATA_TAG, partInfoLis.get(position));
                intent.setClass(getContext(), DetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    private void setStyle(final SwipeMenuListView listView, final Context context) {
        listView.setMenuCreator(buildSwipeMenuCreatorCreator(tabTitle, context));

        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        listView.setCloseInterpolator(new BounceInterpolator());
        listView.setOpenInterpolator(new BounceInterpolator());
    }

    private SwipeMenuCreator buildSwipeMenuCreatorCreator(String appName, final Context context) {
        if (!TextUtils.isEmpty(appName) && appName.equals(CommonUtils.getStringByName("tab_top_title", context))) {
            return new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem addTab = new SwipeMenuItem(
                            context);
                    // set item background
                    addTab.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                            0xCE)));
                    // set item width
                    addTab.setWidth((280));
                    // set item title
                    addTab.setTitle(CommonUtils.getStringByName("slide_tag_add", context));
                    // set item title fontsize
                    addTab.setTitleSize(17);
                    // set item title font color
                    addTab.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(addTab);

                    // create "open" item
                    SwipeMenuItem openItem = new SwipeMenuItem(
                            context);
                    // set item background
                    openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                            170)));
                    // set item width
                    openItem.setWidth((280));
                    // set item title
                    openItem.setTitle(CommonUtils.getStringByName("slide_tag_open", context));
                    // set item title fontsize
                    openItem.setTitleSize(17);
                    // set item title font color
                    openItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(openItem);
                    // create "delete" item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            context);
                    // set item background
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                            0x3F, 0x25)));
                    // set item width
                    deleteItem.setWidth((280));
                    deleteItem.setTitle(CommonUtils.getStringByName("slide_tag_delete", context));
                    // set item title fontsize
                    deleteItem.setTitleSize(17);
                    deleteItem.setTitleColor(Color.WHITE);
                    // set a icon
//                deleteItem.setIcon(R.mipmap.ic_launcher);
                    // add to menu
                    menu.addMenuItem(deleteItem);
                }
            };
        } else if (!TextUtils.isEmpty(appName) && !appName.equals(CommonUtils.getStringByName("tab_top_title", context))) {
            return new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    // create "open" item
                    SwipeMenuItem openItem = new SwipeMenuItem(
                            context);
                    // set item background
                    openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                            170)));
                    // set item width
                    openItem.setWidth((280));
                    // set item title
                    openItem.setTitle(CommonUtils.getStringByName("slide_tag_open", context));
                    // set item title fontsize
                    openItem.setTitleSize(17);
                    // set item title font color
                    openItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(openItem);
                    // create "delete" item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            context);
                    // set item background
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                            0x3F, 0x25)));
                    // set item width
                    deleteItem.setWidth((280));
                    deleteItem.setTitle(CommonUtils.getStringByName("slide_tag_delete", context));
                    // set item title fontsize
                    deleteItem.setTitleSize(17);
                    deleteItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(deleteItem);
                }
            };
        } else {
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        mContext.unregisterReceiver(uninstallReceiver);
        handler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    /**
     * 添加tab 的回调
     */
    OnListFragmentInteractionListener listener;


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(List<ChannelItem> userList);
    }


}
