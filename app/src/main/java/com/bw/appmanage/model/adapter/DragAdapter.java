package com.bw.appmanage.model.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bw.appmanage.R;
import com.bw.appmanage.model.db.ChannelItem;

import java.util.List;

public class DragAdapter extends BaseAdapter {
	/** TAG*/
	private final static String TAG = "DragAdapter";
	/** 是否显示底部的ITEM */
	private boolean isItemShow = false;
	private Context context;
	/** 控制的postion */
	private int holdPosition;
	/** 是否改变 */
	private boolean isChanged = false;
	/** 列表数据是否改变 */
	private boolean isListChanged = false;
	/** 是否可见 */
	boolean isVisible = true;
	/** 可以拖动的列表（即用户选择的应用列表） */
	public List<ChannelItem> channelList;
	/** TextView  */
	private TextView item_text;
	/** 要删除的position */
	public int remove_position = -1;

	public DragAdapter(Context context, List<ChannelItem> channelList) {
		this.context = context;
		this.channelList = channelList;
	}
	
	@Override
	public int getCount() {
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public ChannelItem getItem(int position) {
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.channel_item, null);
		item_text = (TextView) view.findViewById(R.id.text_item);
		ChannelItem channel = getItem(position);
		item_text.setText(channel.getName());
		if (position == 0){  //) || (position == 1)
//			item_text.setTextColor(context.getResources().getColor(R.color.black));
			item_text.setEnabled(false);
		}

		if (position==1){
		}
		if (isChanged && (position == holdPosition) && !isItemShow) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
			isChanged = false;
		}
		if (!isVisible && (position == -1 + channelList.size())) {  // if (!isVisible && (position == -1 + channelList.size())) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
		}
		if(remove_position == position){
			item_text.setText("");
		}
		return view;
	}

	/** 添加应用列表 */
	public void addItem(ChannelItem channel) {
		channelList.add(channel);
		isListChanged = true;
		notifyDataSetChanged();
	}

	/** 拖动变更应用排序 */
	public void exchange(int dragPostion, int dropPostion) {
		holdPosition = dropPostion;
		ChannelItem dragItem = getItem(dragPostion);
		Log.d(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
		if (dragPostion < dropPostion) {
			channelList.add(dropPostion + 1, dragItem);
			channelList.remove(dragPostion);
		} else {
			channelList.add(dropPostion, dragItem);
			channelList.remove(dragPostion + 1);
		}
		isChanged = true;
		isListChanged = true;
		notifyDataSetChanged();
	}
	
	/** 获取应用列表 */
	public List<ChannelItem> getChannnelLst() {
		return channelList;
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
	}

	/** 删除应用列表 */
	public void remove() {
		channelList.remove(remove_position);
		remove_position = -1;
		isListChanged = true;
		notifyDataSetChanged();
	}
	
	/** 设置应用列表 */
	public void setListDate(List<ChannelItem> list) {
		channelList = list;
	}
	
	/** 获取是否可见 */
	public boolean isVisible() {
		return isVisible;
	}
	
	/** 排序是否发生改变 */
	public boolean isListChanged() {
		return isListChanged;
	}
	
	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}
}