package com.bw.appmanage.app;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bw.appmanage.R;
import com.bw.appmanage.common.utils.AppUtils;
import com.bw.appmanage.common.utils.BaseUtils;
import com.bw.appmanage.common.utils.CommonUtils;
import com.bw.appmanage.common.utils.PinYin;
import com.bw.appmanage.model.Config;
import com.bw.appmanage.model.adapter.HomeLvAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private SearchView view_searcher;
    private TextView tv_search;
    private TextView search_empty_tv;
    private SwipeMenuListView search_swipeMenuListView;
    private EditText mViewSearchEditor;
    private ArrayList<BaseUtils.AppInfo> appInfos;
    private ArrayList<BaseUtils.AppInfo> searchAppInfos;
    private HomeLvAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();
        appInfos = getIntent().getParcelableArrayListExtra(Config.DATA_TAG);
        view_searcher = (SearchView) findViewById(R.id.view_searcher);
        tv_search = (TextView) findViewById(R.id.tv_search);
        mViewSearchEditor = (EditText) findViewById(R.id.search_src_text);
        search_empty_tv = (TextView) findViewById(R.id.search_empty_tv);
        search_swipeMenuListView = (SwipeMenuListView) findViewById(R.id.search_swipeMenuListView);
        search_swipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //跳转到应用详情
                AppUtils.closeKeyboard(mViewSearchEditor);
                Intent intent = new Intent();
                intent.putExtra(Config.DATA_TAG, searchAppInfos.get(position));
                intent.setClass(SearchActivity.this, DetailActivity.class);
                SearchActivity.this.startActivity(intent);
            }
        });
        setStyle(search_swipeMenuListView, SearchActivity.this);

        mViewSearchEditor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_search.setOnClickListener(this);

        view_searcher.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                view_searcher.clearFocus();
                AppUtils.closeKeyboard(mViewSearchEditor);
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                }
                return false;
            }
        });

    }

    private void setStyle(final SwipeMenuListView listView, final Context context) {
        listView.setMenuCreator(buildSwipeMenuCreatorCreator(context));

        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        listView.setCloseInterpolator(new BounceInterpolator());
        listView.setOpenInterpolator(new BounceInterpolator());
    }

    private SwipeMenuCreator buildSwipeMenuCreatorCreator(final Context context) {
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
            }
        };
    }

    /**
     * item的添加 按钮功能
     */
    private void itemClick(final List<BaseUtils.AppInfo> partInfoLis) {
        search_swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                AppUtils.closeKeyboard(mViewSearchEditor);
                switch (index) {
                    case 0:
                        AppUtils.openApp(partInfoLis.get(position).getPackageName(), SearchActivity.this.getPackageManager(), SearchActivity.this);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view == tv_search) {
            AppUtils.closeKeyboard(mViewSearchEditor);
            search(mViewSearchEditor.getText().toString().trim());
        }
    }

    //查询应用
    private void search(String query) {
        if (TextUtils.isEmpty(query)) {
            CommonUtils.showToast(CommonUtils.getStringByName("search_keyword_empty_error", SearchActivity.this), SearchActivity.this);
            return;
        }
        searchAppInfos = new ArrayList<>();
        if (appInfos != null && appInfos.size() != 0) {
            //
            for (BaseUtils.AppInfo appInfo : appInfos) {
                if (PinYin.getPinYin(appInfo.getName()).contains(PinYin.getPinYin(query))
                        || PinYin.getPinYin(appInfo.getPackageName()).contains(PinYin.getPinYin(query))) {
                    searchAppInfos.add(appInfo);
                }
            }
        } else {
            //如果没有infos传过来，需要重新获取
            for (BaseUtils.AppInfo appInfo : AppUtils.getAppsInfo()) {
                if (PinYin.getPinYin(appInfo.getName()).contains(PinYin.getPinYin(query))
                        || PinYin.getPinYin(appInfo.getPackageName()).contains(PinYin.getPinYin(query))) {
                    searchAppInfos.add(appInfo);
                }
            }
        }
        itemClick(searchAppInfos);
        if (adapter == null) {
            adapter = new HomeLvAdapter(SearchActivity.this, searchAppInfos);
            search_swipeMenuListView.setAdapter(adapter);
        } else {
            adapter.refreshData(searchAppInfos);
        }

        if (searchAppInfos == null || searchAppInfos.size() == 0){
            search_swipeMenuListView.setVisibility(View.GONE);
            search_empty_tv.setVisibility(View.VISIBLE);
        }else {
            search_swipeMenuListView.setVisibility(View.VISIBLE);
            search_empty_tv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
