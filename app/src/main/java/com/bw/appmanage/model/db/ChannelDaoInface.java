package com.bw.appmanage.model.db;

import android.content.ContentValues;

import java.util.List;
import java.util.Map;

public interface ChannelDaoInface {

	public boolean addCache(ChannelItem item);

	public boolean addCache(List<ChannelItem> channelItemList);

	public boolean deleteCache(String whereClause, String[] whereArgs);

	public boolean updateCache(ContentValues values, String whereClause,
                               String[] whereArgs);

	public Map<String, String> viewCache(String selection,
                                         String[] selectionArgs);

	public List<Map<String, String>> listCache(String selection,
                                               String[] selectionArgs);


	public List<String> listCacheData(String selection,
                                      String[] selectionArgs);

	public List<String> listAllCache(String selection,
                                     String[] selectionArgs);

	public void clearFeedTable();


	public boolean deleteOtherCache(String selection,
                                    String[] selectionArgs);


}
