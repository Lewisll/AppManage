package com.bw.appmanage.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.appmanage.R;
import com.bw.appmanage.common.utils.CommonUtils;
import com.bw.appmanage.model.Config;
import com.bw.appmanage.model.db.ChannelItem;
import com.bw.appmanage.model.db.ChannelManage;
import com.bw.appmanage.model.db.SQLhelperInstance;
import com.bw.appmanage.view.custom.ColumnHorizontalScrollView;
import com.bw.appmanage.view.fg.PagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.获取（默认或从数据库）user并显示出来（数据库读取）
 * 2.点击添加：通过Manage类将标签添加到数据库（查询对比并添加到数据库），重新刷新（接口回调），并把值传到channelactivity由之显示出来
 */
public class MainActivity extends BaseActivity implements PagerFragment.OnListFragmentInteractionListener, View.OnClickListener {

    /**
     * 调整返回的RESULTCODE
     */
    public final static int CHANNELRESULT = 10;
    public final static int CHANNEL_RESULTCODE = 100;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth = 0;

    /**
     * Item宽度
     */
    private int mItemWidth = 0;
    /**
     * 当前选中的栏目
     */
    private int columnSelectIndex = 0;
    /**
     * fragment 容器
     */
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    /**
     * 搜索图标
     */
    private ImageView search_icon;
    /**
     * 定义一个变量，来标识是否退出
     */
    private static boolean isExit = false;
    private Fragment currentFragment;
    private FragmentManager manager;
    private int columCount;

    private RelativeLayout rl;

    private LinearLayout ll_more_columns, mRadioGroup_content;
    private ImageView button_more_columns, shade_left, shade_right;
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private RelativeLayout rl_column;
    //用户选择的标签列表
    private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();

        intView();

        setChangelView();
        initFirstFragment();
    }

    private void initFirstFragment() {
        currentFragment = fragments.get(0);
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.main_container, currentFragment, "0").commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 当栏目项发生变化时候调用
     */
    private void setChangelView() {
        initColumnData();
        initTabColumn();
        initFragments();
    }

    /**
     * 获取Column栏目 数据
     */
    private void initColumnData() {
        userChannelList = (ArrayList<ChannelItem>) ChannelManage.getManage(SQLhelperInstance.getInstance().getSQLHelper()).getUserChannel();
    }

    //初始化tab布局
    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
        int count = userChannelList.size();
        //获取显示tab的数量
        mColumnHorizontalScrollView.setParam(this, CommonUtils.getScreenWidth(this), mRadioGroup_content, shade_left, shade_right, ll_more_columns, rl_column);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, LinearLayout.LayoutParams.MATCH_PARENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            TextView columnTextView = new TextView(this);
            columnTextView.setTextAppearance(this, R.style.top_category_scroll_view_item_text);
            columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(3, 3, 3, 3);
            columnTextView.setId(i);
            columnTextView.setText(userChannelList.get(i).getName());
            columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int j = 0;
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else {
                            localView.setSelected(true);
                            j = i;
                        }
                    }

                    /**显示所点击的标签的fragment*/
                    FragmentTransaction ft = manager.beginTransaction();

                    System.out.println("J=" + j);

                    if (fragments.get(j).isAdded()) {   // if (fragments.get(v.getId()).isAdded()){
                        ft.hide(currentFragment).show(fragments.get(j));
                    } else {
                        ft.hide(currentFragment).add(R.id.main_container, fragments.get(j));
                    }
                    currentFragment = fragments.get(j);
                    ft.commit();
                }
            });

            mRadioGroup_content.addView(columnTextView, i, params);
        }

    }

    //初始化view
    private void intView() {
        mScreenWidth = CommonUtils.getScreenWidth(this);
        mItemWidth = mScreenWidth / 5;
        manager = getSupportFragmentManager();

        ll_more_columns = (LinearLayout) findViewById(R.id.ll_more_columns);
        button_more_columns = (ImageView) findViewById(R.id.button_more_columns);
        search_icon = (ImageView) findViewById(R.id.search_icon);
        shade_left = (ImageView) findViewById(R.id.shade_left);
        shade_right = (ImageView) findViewById(R.id.shade_right);
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) findViewById(R.id.mColumnHorizontalScrollView);
        mRadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup_content);//tab容器
        rl_column = (RelativeLayout) findViewById(R.id.rl_column);//tab容器

        button_more_columns.setOnClickListener(this);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SearchActivity.class);
                if (fragments.size() != 0 && ((PagerFragment) fragments.get(0)).partInfoLis.size() != 0) {
                    intent.putParcelableArrayListExtra(Config.DATA_TAG, ((PagerFragment) fragments.get(0)).partInfoLis);
                }
                MainActivity.this.startActivity(intent);
            }
        });
    }


    private void initFragments() {
        fragments.clear();//清空
        int count = userChannelList.size();
        manager = getSupportFragmentManager();

        //获取显示tab数量
        if (count == 1) {
            PagerFragment fragment = PagerFragment.newInstance(userChannelList.get(0).getName(), userChannelList.get(0).getId());
            fragments.add(fragment);
        } else {
            for (int i = 0; i < count; i++) {
                PagerFragment fragment = PagerFragment.newInstance(userChannelList.get(i).getName(), userChannelList.get(i).getId());
                fragments.add(fragment);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CHANNELRESULT:
                if (resultCode == CHANNELRESULT) {
                    initColumnData();
                    initFragments();
                    initTabColumn();

                    FragmentTransaction transaction = manager.beginTransaction();
                    if (fragments.get(0).isAdded()) {
                        transaction.hide(currentFragment).show(fragments.get(0));
                    } else {
                        transaction.hide(currentFragment).add(R.id.main_container, fragments.get(0));
                    }
                    currentFragment = fragments.get(0);
                    transaction.commitAllowingStateLoss();
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 两次按back键退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onListFragmentInteraction(List<ChannelItem> userList) {
        CommonUtils.showToast(CommonUtils.getStringByName("addButton", MainActivity.this), MainActivity.this);

        userChannelList = (ArrayList<ChannelItem>) userList;
        initTabColumn();
        initFragments();
    }


    @Override
    public void onClick(View view) {
        if (view == button_more_columns) {
            startActivityForResult(new Intent(MainActivity.this, ChannelActivity.class), CHANNELRESULT);
        }
    }
}
