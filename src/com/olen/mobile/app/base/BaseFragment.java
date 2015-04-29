package com.olen.mobile.app.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public abstract class BaseFragment extends Fragment{
	protected String TAG = Contants.makeLogTag(BaseFragment.class);
	private static final boolean LIFECYCLE=Contants.LIFECYCLE;
	private String title;
	private CustomFragmentManager stack;
	protected MobileApplication app;
	protected FragmentInfo mUpFragment;
	
	abstract protected void initData(Bundle savedInstanceState);
	
	abstract protected void findViews(View view);

	abstract protected void initViews(Bundle savedInstanceState);
	
	abstract protected FragmentInfo getNavigtionUpToFragment();	
	protected ActionBar getSupportActionBar() {
		ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
		if(abActivity==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			return abActivity.getSupportActionBar();
		}
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title=title;
		if(getSupportActionBar()!=null)
		getSupportActionBar().setTitle(title);
	}
	public void setTitle(int title) {
		this.title=getString(title);
		if(getSupportActionBar()!=null)
		getSupportActionBar().setTitle(title);
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(LIFECYCLE)if(LIFECYCLE)Log.v(TAG, "onAttach");
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onCreate");
		setHasOptionsMenu(true);
		setMenuVisibility(true);
		app = (MobileApplication) this.getActivity().getApplication();
		TAG = Contants.makeLogTag(this.getClass());
		if(savedInstanceState==null){
		}else{
			title=savedInstanceState.getString("title");
			if(null!=stack)stack.restoreState(savedInstanceState);
		}
	}
	protected void initFragmentStack(int containerId){
		if(null==stack)
		stack = CustomFragmentManager.forContainer(this.getActivity(), containerId,this.getChildFragmentManager());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(LIFECYCLE)Log.v(TAG, "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onViewCreated");
		if(null!=title)this.setTitle(title);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onActivityCreated");
	}
	

	@Override
	public void onPause() {
		super.onPause();
		if(LIFECYCLE)Log.v(TAG, "onPause");
	}

	@Override
	public void onResume() {
		super.onResume();
		if(LIFECYCLE)Log.v(TAG, "onResume");
	}

	@Override
	public void onStart() {
		super.onStart();
		if(LIFECYCLE)Log.v(TAG, "onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		if(LIFECYCLE)Log.v(TAG, "onStop");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if(LIFECYCLE)Log.v(TAG, "onDetach");
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if(LIFECYCLE)Log.v(TAG, "onDestroyView");
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(LIFECYCLE)Log.v(TAG, "onDestroy");
	}
	
	public void showToast(int resId){
		Toast.makeText(this.getActivity(),resId,Toast.LENGTH_SHORT).show();
	}
	public void showToast(String str){
		Toast.makeText(this.getActivity(),str,Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		if(LIFECYCLE)Log.v(TAG, "onSaveInstanceState");
		outState.putString("title", title);
		if(null!=stack)stack.saveState(outState);
		super.onSaveInstanceState(outState);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(LIFECYCLE)Log.v(TAG, "onConfigurationChanged");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(LIFECYCLE)Log.v(TAG, "onActivityResult");
		
	}
	
	public boolean onBackPressed() {
		if(LIFECYCLE)Log.v(TAG, "onBackPressed");
		if(null==stack)return false;
		if (stack.size() <= 1) {
			return false;
		} else {
			if (stack.peek().onBackPressed()) {
				return true;
			} else {
				stack.pop();
				return true;
			}
		}
	}
	public boolean isSingleton(){
		return true;
	}
	public boolean isCleanStack(){
		return false;
	}
	public boolean onSupportNavigateUp() {
		
		return false;
	}
	public void  popBackStack(){
		BaseFragment parent=(BaseFragment) this.getParentFragment();
		if(parent!=null){
			parent.getCustomFragmentManager().pop();
		}else{
			if(getActivity()==null){
				throw new IllegalStateException("getActivity is null");
			}else{
				BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this.getActivity();
				bfActivity.getCustomFragmentManager().pop();
			}
		}
	}
	
	public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
		if(LIFECYCLE)Log.v(TAG, "onFragmentResult");
    }	
	
	public void setReult(int resultCode){
		this.setReult(resultCode, null);
	}
	
	public void setReult(int resultCode,Bundle data){
		BaseFragment taget=(BaseFragment) getTargetFragment();
		if(taget!=null){
			taget.onFragmentResult(getTargetRequestCode(),resultCode, data);
		}else{
			throw new IllegalStateException("Target Fragment is null");
		}
	}
	
	public CustomFragmentManager getCustomFragmentManager() {
		return stack;
	}
	
	public void replaceFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args) {
		this.replaceFragment(fragmentClass, tag, args,null);
	}
	public void replaceFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,int requestCode) {
		if(requestCode!=-1){
			this.replaceFragment(fragmentClass, tag, args,new CustomFragmentTransaction().setTargetFragment(this, requestCode));	
		}else{
			throw new IllegalStateException("requestCode!=-1");
		}
	}
	public void replaceFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,CustomFragmentTransaction customFragmentTransaction){
		BaseFragment parent=(BaseFragment) this.getParentFragment();
		CustomFragmentManager stack=null;
		if(parent!=null){
			stack=parent.getCustomFragmentManager();
		}else{
			if(getActivity()==null){
				throw new IllegalStateException("getActivity is null");
			}else{
				BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this.getActivity();
				stack=bfActivity.getCustomFragmentManager();
			}
		}
		stack.replace(fragmentClass, tag,args,customFragmentTransaction);
		stack.commit();
	}
	
	public void replaceChildFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args) {
		replaceChildFragment(fragmentClass, tag, args,null);
	}
	
	public void replaceChildFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,int enter,int exit) {
		replaceChildFragment(fragmentClass, tag,args,new CustomFragmentTransaction().setCustomAnimations(enter, exit));	
	}
	
	public void replaceChildFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,int enter,int exit,int popEnter, int popExit) {
		replaceChildFragment(fragmentClass, tag,args,new CustomFragmentTransaction().setCustomAnimations(enter,exit,popEnter,popExit));	
	}
	
	public void replaceChildFragment(Class<? extends BaseFragment> fragmentClass,String tag,Bundle args,CustomFragmentTransaction customFragmentTransaction) {
		if(stack!=null){
			stack.replace(fragmentClass, tag,args,customFragmentTransaction);
			stack.commit();
		}else{
			throw new IllegalStateException("fragment'CustomFragmentManager is null, Pleaser initFragmentStack");
		}
		
	}
//	/**ActionBarActivity method**/
//	public void requestWindowFeature(int featureId){
//		if(getActivity()==null){
//			throw new IllegalStateException("getActivity is null");
//		}else{
//			ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
//			abActivity.requestWindowFeature(featureId);
//		}
//	}
	
	public AppService getAppService(String name) {
		return app.getAppService(name);
	}
	public void setSupportProgress(int progress) {
		if(getActivity()==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
			abActivity.setSupportProgress(progress);
		}
	}

	public void setSupportProgressBarIndeterminate(boolean indeterminate) {
		if(getActivity()==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
			abActivity.setSupportProgressBarIndeterminate(indeterminate);
		}
	}

	public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
		if(getActivity()==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			 ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
			 abActivity.setSupportProgressBarIndeterminateVisibility(visible);
		}
	}

	public void setSupportProgressBarVisibility(boolean visible) {
		if(getActivity()==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
			abActivity.setSupportProgressBarVisibility(visible);
		}
	}
	public ActionMode startSupportActionMode(Callback callback) {
		if(getActivity()==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
			return abActivity.startSupportActionMode(callback);
		}
	}
	public boolean supportRequestWindowFeature(int featureId) {
		if(getActivity()==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			ActionBarActivity abActivity = (ActionBarActivity) this.getActivity();
			return abActivity.supportRequestWindowFeature(featureId);
		}
	}
	public void onSupportActionModeFinished(ActionMode mode) {
		Log.v(TAG, "onSupportActionModeFinished");
	}
	public void onSupportActionModeStarted(ActionMode mode) {
		Log.v(TAG, "onSupportActionModeStarted");
	}
	
}
