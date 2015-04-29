package com.olen.mobile.app.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.ActionMode;
import android.view.WindowManager;
import android.widget.Toast;

import com.olen.mobile.app.MobileApplication;
import com.olen.mobile.app.R;
import com.olen.mobile.app.common.Contants;
import com.olen.mobile.app.navigation.SlidingFragmentActivity;
import com.olen.mobile.app.navigation.SlidingMenu;
import com.olen.mobile.app.navigation.SlidingMenu.OnClosedListener;
import com.olen.mobile.app.navigation.SlidingMenu.OnOpenedListener;
import com.olen.moblie.core.log.Log;
import com.olen.moblie.core.service.AppService;

/**
 * @description TODO
 * @author zhangxiaole
 * @data 2014-3-13
 */
public abstract class BaseSlidingFragmentActivity extends SlidingFragmentActivity {
	protected String TAG = Contants.makeLogTag(BaseSlidingFragmentActivity.class);
	private static final boolean LIFECYCLE=Contants.LIFECYCLE;
	protected MobileApplication app;
	private BaseMenuFragment menuFragment;
	private CustomFragmentManager stack;
	private SlidingMenu sm;
	private static final String MENU_TAG="MenuFragment";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		app = (MobileApplication) this.getApplication();
		TAG = Contants.makeLogTag(this.getClass());
		addActivityToManager(this);
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		// customize the SlidingMenu
		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setBehindWidthRes(R.dimen.behindWidth);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setSlidingEnabled(false);
		this.setSlidingActionBarEnabled(true);
		sm.setOnClosedListener(new OnClosedListener(){

			@Override
			public void onClosed() {
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			}
			
		});
		sm.setOnOpenedListener(new OnOpenedListener(){

			@Override
			public void onOpened() {
				getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			}
			
		});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		stack = CustomFragmentManager.forContainer(this, R.id.content_frame,this.getSupportFragmentManager());
		if(savedInstanceState==null){
		}else{
			stack.restoreState(savedInstanceState);
		}
	}
	public void setSlidingEnabled(boolean b) {
		if(sm!=null)
		sm.setSlidingEnabled(b);
	}
	final public void setMenuFragment(Class<? extends BaseMenuFragment> fragmentClass,Bundle args) {
		menuFragment = (BaseMenuFragment) Fragment.instantiate(this,fragmentClass.getName(), args);
		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		t.replace(R.id.menu_frame, menuFragment,MENU_TAG);
		t.commit();
		getSupportFragmentManager().executePendingTransactions();
	}
	final public void notifyMenuChange(int moduleId){
		if(menuFragment==null){
			throw new IllegalStateException("menuFragment is null");
		}
		menuFragment.onContentChange(moduleId);
	}
	final public void setContentFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,int moduleId) {
		notifyMenuChange(moduleId);
		setContentFragment(fragmentClass,tag,args);
	}
	final public void setContentFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args) {
		stack.replace(fragmentClass, tag,args);
		stack.commit();
	}
	abstract protected void findViews();

	abstract protected void initViews(Bundle savedInstanceState);

	abstract protected void initData(Bundle savedInstanceState);

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
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		menuFragment=(BaseMenuFragment) getSupportFragmentManager().findFragmentByTag(MENU_TAG);
		if(LIFECYCLE)Log.v(TAG, "onRestoreInstanceState");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		stack.saveState(outState);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(LIFECYCLE)Log.v(TAG, "onActivityResult");
		
	}
	@Override
	public boolean onSupportNavigateUp() {
		if(LIFECYCLE)
			Log.v(TAG, "onSupportNavigateUp ");
		if (getSlidingMenu().isMenuShowing()) {
			this.toggle();
			return true;
		} else {
			if (stack.size() <= 1) {
				this.showMenu();
				return true;
			} else {
				if (stack.peek().onSupportNavigateUp()) {
					return true;
				} else {
					FragmentInfo upFragment=stack.peek().getNavigtionUpToFragment();
					if(upFragment!=null){
						setContentFragment(upFragment.clss,upFragment.tag,upFragment.args);
					}else{
						stack.pop();
					}
					return true;
				}
			}
		}
	}
	public CustomFragmentManager getCustomFragmentManager() {
		return stack;
	}
	@Override
	final public void onBackPressed() {
		//if(LIFECYCLE)
			Log.v(TAG, "onBackPressed ");
		if (stack.size() <= 1) {
			if (stack.peek().onBackPressed()) {
				return;
			} else {
				onBack();
			}
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
	public void onSupportActionModeFinished(ActionMode mode) {
		stack.peek().onSupportActionModeFinished(mode);
	}
	
	@Override
	public void onSupportActionModeStarted(ActionMode mode) {
		stack.peek().onSupportActionModeStarted(mode);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(LIFECYCLE)Log.v(TAG, "onConfigurationChanged");
	}
	
}
