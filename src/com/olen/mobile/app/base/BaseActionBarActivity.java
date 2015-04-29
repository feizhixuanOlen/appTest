package com.olen.mobile.app.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
public abstract class BaseActionBarActivity extends ActionBarActivity {
	protected String TAG = Contants.makeLogTag(BaseActionBarActivity.class);
	private static final boolean LIFECYCLE=Contants.LIFECYCLE;
	protected MobileApplication app;
	protected CustomFragmentManager stack;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		app = (MobileApplication) this.getApplication();
		TAG = Contants.makeLogTag(this.getClass());
		addActivityToManager(this);
		if(savedInstanceState==null){
			
		}else{
			if(null!=stack)stack.restoreState(savedInstanceState);
		}
	}

	abstract protected void findViews();

	abstract protected void initViews(Bundle savedInstanceState);

	abstract protected void initData(Bundle savedInstanceState);
	
	
	protected void initFragmentStack(int containerId){
		if(containerId<=0){
			throw new IllegalStateException("getContainerId must return a valid  containerId");
		}
		if(null==stack)
		stack = CustomFragmentManager.forContainer(this, containerId,this.getSupportFragmentManager());
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		delActivityFromManager(this);
	}
	public void showToast(int resId){
		Toast.makeText(this,resId,Toast.LENGTH_SHORT).show();
	}
	public void showToast(String str){
		Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
	}
	@Override
	final public void onBackPressed() {
		Log.e(TAG, "onBackPressed ");
		if(null==stack){
			onBack();
			return;
		}
		if (stack.size() <= 1) {
			onBack();
		} else {
			if (stack.peek().onBackPressed()) {
				return;
			} else {
				stack.pop();
				return;
			}
		}
	}
	public void onBack(){
		if(LIFECYCLE)Log.v(TAG, "onBack");
		super.onBackPressed();
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onRestoreInstanceState");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if(null!=stack)stack.saveState(outState);
		super.onSaveInstanceState(outState);
		if(LIFECYCLE)Log.v(TAG, "onSaveInstanceState");
	}

	@Override
	public Object getLastCustomNonConfigurationInstance() {
		if(LIFECYCLE)Log.v(TAG, "getLastCustomNonConfigurationInstance");
		return super.getLastCustomNonConfigurationInstance();
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		if(LIFECYCLE)Log.v(TAG, "onRetainCustomNonConfigurationInstance");
		return super.onRetainCustomNonConfigurationInstance();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(LIFECYCLE)Log.v(TAG, "onConfigurationChanged");
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
	public void replaceFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args) {
		if(null==stack){
			throw new IllegalStateException("stack is null");
		}
		stack.replace(fragmentClass, tag,args);
		stack.commit();
	}
}
