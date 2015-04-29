package com.olen.mobile.app.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;

import com.olen.mobile.app.common.SharedPreferencesHelper;
import com.olen.mobile.app.widget.pullToRefreshListView.PullToRefreshListView;

public class UIUtils {

	/**
	 * 设置上次更新数据时间
	 * @param listView
	 * @param key
	 */
	public static void setPullToRefreshLastUpdated(PullToRefreshListView listView, String key) {
		final Context context = listView.getContext();
		SharedPreferencesHelper spHelper = SharedPreferencesHelper.getInstance(context);
		long lastUpdateTimeStamp = spHelper.getLongValue(key);
		listView.setLastUpdatedLabel(getUpdateTimeString(lastUpdateTimeStamp));
	}
	
	/**
	 * 保存更新数据时间
	 * @param listView
	 * @param key
	 */
	public static void savePullToRefreshLastUpdateAt(PullToRefreshListView listView, String key) {
		final Context context = listView.getContext();
		SharedPreferencesHelper spHelper = SharedPreferencesHelper.getInstance(context);
		long lastUpdateTimeStamp=System.currentTimeMillis();
		spHelper.putLongValue(key, lastUpdateTimeStamp);
		listView.setLastUpdatedLabel(getUpdateTimeString(lastUpdateTimeStamp));
	}
	
	/**
	 * 更新时间字符串
	 * @param timestamp
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getUpdateTimeString(long timestamp) {
		if (timestamp <= 0) {
			return "上次更新时间:";
		} else {
			String textDate = "上次更新时间:";
			Calendar now = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(timestamp);
            if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                    && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                    && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                return textDate += sdf.format(c.getTime());
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
                return textDate += sdf.format(c.getTime());
            } else {
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                return textDate += sdf.format(c.getTime());
            }
		}
	}
}
