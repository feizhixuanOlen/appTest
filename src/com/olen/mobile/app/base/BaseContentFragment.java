package com.olen.mobile.app.base;

import android.os.Bundle;
import android.view.View;

/**
 * @description TODO
 * @author zhangxiaole
 * @data 2014-3-13
 */
public abstract class BaseContentFragment extends BaseFragment{
	
	final public void setContentFragment(Class<? extends BaseContentFragment> fragmentClass,String tag,Bundle args,int moduleId) {
		if(getActivity()==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this.getActivity();
			bfActivity.setContentFragment(fragmentClass, tag,args,moduleId);
		}
		
	}
	final public void setContentFragment(Class<? extends BaseContentFragment> fragmentClass,String tag,Bundle args) {
		if(getActivity()==null){
			throw new IllegalStateException("getActivity is null");
		}else{
			BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this.getActivity();
			bfActivity.setContentFragment(fragmentClass, tag,args);
		}
	}
	final public void setSlidingEnabled(boolean b) {
		//只有顶级fragment才有设置setSlidingEnabled的权限,其他无效
		BaseFragment parent=(BaseFragment) this.getParentFragment();
		if(parent==null){
			if(getActivity()==null){
				throw new IllegalStateException("getActivity is null");
			}else{
				BaseSlidingFragmentActivity bfActivity = (BaseSlidingFragmentActivity) this.getActivity();
				bfActivity.setSlidingEnabled(b);
			}
		}else{
		}
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setSlidingEnabled(isCleanStack());
	}
}
