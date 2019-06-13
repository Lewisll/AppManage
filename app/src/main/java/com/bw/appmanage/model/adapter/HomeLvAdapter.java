package com.bw.appmanage.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.appmanage.R;

import com.bw.appmanage.common.utils.AppUtils;
import com.bw.appmanage.common.utils.BaseUtils;

import java.util.List;

/**
 * 类名；HomeLvAdapter
 * 类描述：作为应用展示的适配器
 */
public class HomeLvAdapter extends BaseAdapter {
    private Context context;
    private List<BaseUtils.AppInfo> appPartInfoList;
    private LayoutInflater inflater;

    public HomeLvAdapter(Context context, List<BaseUtils.AppInfo> appInfoList) {
        this.context = context;
        this.appPartInfoList = appInfoList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return appPartInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return appPartInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_lv, parent, false);
            ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            TextView tv_appName = (TextView) convertView.findViewById(R.id.tv_appName);
            TextView tv_packageName = (TextView) convertView.findViewById(R.id.tv_packageName);
            TextView tv_version = (TextView) convertView.findViewById(R.id.tv_version);
            TextView tv_versionName = (TextView) convertView.findViewById(R.id.tv_versionName);
            holder.iv_icon = iv_icon;
            holder.tv_appName = tv_appName;
            holder.tv_packageName = tv_packageName;
            holder.tv_version = tv_version;
            holder.tv_versionName = tv_versionName;
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (appPartInfoList.get(position).getIcon() != null) {
            holder.iv_icon.setImageDrawable(appPartInfoList.get(position).getIcon());
        } else {
            holder.iv_icon.setImageDrawable(AppUtils.getAppIcon(appPartInfoList.get(position).getPackageName()));
        }
        holder.tv_appName.setText(appPartInfoList.get(position).getName());
        holder.tv_packageName.setText("包名：" + appPartInfoList.get(position).getPackageName());
        holder.tv_version.setText("版本号：" + appPartInfoList.get(position).getVersionCode());
        holder.tv_versionName.setText("版本名：" + appPartInfoList.get(position).getVersionName());
        return convertView;

    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_appName, tv_packageName, tv_version, tv_versionName;
    }

    /**
     * 刷新数据
     */
    public void refreshData(List<BaseUtils.AppInfo> array) {
        this.appPartInfoList = array;
        notifyDataSetChanged();

    }


}

