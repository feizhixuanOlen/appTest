package com.olen.mobile.app.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.olen.mobile.app.MobileApplication;
import com.olen.mobile.app.common.Contants;
import com.olen.moblie.core.log.Log;
import com.olen.moblie.core.service.AppService;


/**
 * @description TODO
 * @author zhangxiaole
 * @data 2014-3-13
 */
public abstract class BaseActivity extends Activity {
	protected String TAG = Contants.makeLogTag(BaseActivity.class);
	private static final boolean LIFECYCLE=Contants.LIFECYCLE;
	public MobileApplication app;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		app = (MobileApplication) this.getApplication();
		TAG = Contants.makeLogTag(this.getClass());
		addActivityToManager(this);
	}

	protected abstract void findViews();

	protected abstract void initViews(Bundle savedInstanceState);

	protected abstract void initData(Bundle savedInstanceState);

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if(LIFECYCLE)Log.v(TAG, "onNewIntent");
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(LIFECYCLE)Log.v(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(LIFECYCLE)Log.v(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(LIFECYCLE)Log.v(TAG, "onPause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if(LIFECYCLE)Log.v(TAG, "onRestart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(LIFECYCLE)Log.v(TAG, "onStop");
	}
	public void showToast(int resId){
		Toast.makeText(this,resId,Toast.LENGTH_SHORT).show();
	}
	public void showToast(String str){
		Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onRestoreInstanceState");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(LIFECYCLE)Log.v(TAG, "onSaveInstanceState");
	}

	protected void addActivityToManager(Activity act) {
		if(LIFECYCLE)Log.v(TAG, "addActivityToManager");
		if (!app.activityManager.contains(act)) {
			if(LIFECYCLE)Log.v(TAG, "addActivityToManager, name = "
					+ act.getClass().getSimpleName());
			app.activityManager.add(act);
		}
	}

	protected void closeAllActivities() {
		if(LIFECYCLE)Log.v(TAG, "closeAllActivities");
		for (final Activity act : app.activityManager) {
			if (act != null) {
				act.finish();
			}
		}
	}

	protected void delActivityFromManager(Activity act) {
		if(LIFECYCLE)Log.v(TAG, "delActivityFromManager");
		if (app.activityManager.contains(act)) {
			app.activityManager.remove(act);
		}
	}
	public AppService getAppService(String name) {
		return app.getAppService(name);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(LIFECYCLE)Log.v(TAG, "onConfigurationChanged");
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(LIFECYCLE)Log.v(TAG, "onDestroy");
		delActivityFromManager(this);
	}

	@Override
	public void onBackPressed() {
		if(LIFECYCLE)Log.v(TAG, "onBackPressed");
		super.onBackPressed();
	}
}
