package com.olen.mobile.app;

import android.content.Context;

import com.olen.moblie.core.log.Log;
import com.olen.moblie.core.service.status.StatusListener;

/**
 * @description TODO
 * @author zhangxiaole
 * @data 2014-3-13
 */
public class AppStatusListener implements StatusListener{

	@Override
	public void networkConnect(Context context) {
		Log.d("networkDisconnect 开始下载");
	}

	@Override
	public void networkDisconnect(Context context) {
		Log.d("networkDisconnect 停止下载");
	}

	@Override
	public void networkTo3G(Context context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storageRemove(Context context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storageMount(Context context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callStateIdle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callStateOffhook() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callStateRinging() {
		// TODO Auto-generated method stub
		
	}

}
