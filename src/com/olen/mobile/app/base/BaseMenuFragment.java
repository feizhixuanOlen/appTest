package com.olen.mobile.app.base;

import android.os.Bundle;

import com.olen.mobile.app.ModuleMenuIDS;
import com.olen.mobile.app.common.Contants;

/**
 * @description TODO
 * @author zhangxiaole
 * @data 2014-3-13
 */
public abstract class BaseMenuFragment extends BaseFragment{
	protected String TAG = Contants.makeLogTag(BaseMenuFragment.class);
	private int currentModuleId=ModuleMenuIDS.MODULE_LAUNDRY;
	
	public int getCurrentModuleId() {
		return currentModuleId;
	}
	
	public void setCurrentModuleId(int currentModuleId) {
		this.currentModuleId = currentModuleId;
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState!=null)
			setCurrentModuleId(savedInstanceState.getInt("currentModuleId"));
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentModuleId", currentModuleId);
	}
	
	public void toggleMenu(){
		
		if(getActivity()==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this
					.getActivity();
			bfActivity.toggle();
		}
		
	}
	
	
	public void setContentFragment(Class<? extends BaseContentFragment> fragmentClass,String tag,Bundle args,int moduleId) {
		
		if(getActivity()==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this
					.getActivity();
			bfActivity.setContentFragment(fragmentClass, tag,args);
			onContentChange(moduleId);
		}
	}

	abstract protected void onContentChange(int moduleId);

}
