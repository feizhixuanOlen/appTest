package com.olen.mobile.app.base;

import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.olen.moblie.core.log.Log;


/**
 * @description TODO
 * @author zhangxiaole
 * @data 2014-3-13
 */
public class CustomFragmentManager {
	private static final String STATE_TAG = "CustomFragmentManager";
	private Stack<BaseFragment> stack = new Stack<BaseFragment>();
	private Stack<String> tagStack = new Stack<String>();
	private Object lock = new Object();
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private int containerId;
	private FragmentActivity fActivity;
	private Handler handler;
	private int enterAnimation;
	private int exitAnimation;
	private int popStackEnterAnimation;
	private int popStackExitAnimation;
	private final Runnable execPendingTransactions = new Runnable() {
		    @Override
		    public void run() {
		      if (fragmentTransaction != null&&fActivity!=null) {
		        fragmentTransaction.commit();
		        fragmentManager.executePendingTransactions();
		        fragmentTransaction = null;
		      }
		    }
	};
	public static CustomFragmentManager forContainer(FragmentActivity activity, int containerId,
			  FragmentManager fragmentManager) {
	    return new CustomFragmentManager(activity, containerId, fragmentManager);
	}
	
	private CustomFragmentManager(FragmentActivity fActivity, int containerId,FragmentManager fragmentManager) {
		this.fActivity = fActivity;
		this.fragmentManager =fragmentManager;
		this.containerId = containerId;
		handler = new Handler();
	}
	public void setDefaultAnimation(int enter, int exit, int popEnter, int popExit) {
		    enterAnimation = enter;
		    exitAnimation = exit;
		    popStackEnterAnimation = popEnter;
		    popStackExitAnimation = popExit;
	}
	public void saveState(Bundle outState) {
		executePendingTransactions();
		final int stackSize = stack.size();
		String[] stackTags = new String[stackSize];

		int i = 0;
		for (String tag : tagStack) {
			Log.i(STATE_TAG, "tag ="+tag);
			stackTags[i++] = tag;
		}

		outState.putStringArray(STATE_TAG, stackTags);
	}

	public void restoreState(Bundle state) {
		String[] stackTags = state.getStringArray(STATE_TAG);
		for (String tag : stackTags) {
			BaseFragment f = (BaseFragment) fragmentManager.findFragmentByTag(tag);
			stack.add(f);
			tagStack.add(tag);
		}
	}
	private FragmentTransaction beginTransaction(){
		if (fragmentTransaction == null) fragmentTransaction = fragmentManager.beginTransaction();
		handler.removeCallbacks(execPendingTransactions);
		return fragmentTransaction;
	}
	public void replace(Class<? extends BaseFragment> clazz, String tag, Bundle args){
		this.replace(clazz, tag, args,null);
	}
	public void replace(Class<? extends BaseFragment> clazz, String tag, Bundle args,CustomFragmentTransaction customFragmentTransaction){
		if(stack.size()>0){
			BaseFragment first = stack.firstElement();
			if (first != null && tag.equals(tagStack.firstElement())) {
				if(customFragmentTransaction==null||!customFragmentTransaction.fillCustomAnimations(beginTransaction())){
					if(enterAnimation>0&&exitAnimation>0&&popStackEnterAnimation>0&&popStackExitAnimation>0){
						beginTransaction().setCustomAnimations(enterAnimation,exitAnimation,popStackEnterAnimation,popStackExitAnimation);
					}else if(enterAnimation>0&&exitAnimation>0){
						beginTransaction().setCustomAnimations(enterAnimation,exitAnimation);
					}
				}
				
				if (stack.size() > 1) {
					while (stack.size() > 1) {
						synchronized(lock){
							stack.pop();
							tagStack.pop();
						}
						fragmentManager.popBackStack();
					}
				}
				return;
			}
			BaseFragment last =stack.peek();
			if(last!=null&&tag.equals(tagStack.peek())){
				if (last.isCleanStack()||last.isSingleton()){
					return;//导致 fragmentTransaction 为null
				}
			}
		}
		BaseFragment fragment =(BaseFragment) fragmentManager.findFragmentByTag(tag);
		if (fragment == null||!fragment.isSingleton()) {
			fragment = (BaseFragment) Fragment.instantiate(fActivity,clazz.getName(), args);
		}
		if (fragment.isCleanStack()) {
			while (stack.size() > 0) {
				synchronized(lock){
					stack.pop();
					tagStack.pop();
				}
				fragmentManager.popBackStack();
			}
		}
		if(customFragmentTransaction!=null)customFragmentTransaction.fillTargetFragment(fragment);
		
		if(customFragmentTransaction==null||!customFragmentTransaction.fillCustomAnimations(beginTransaction())){
			if(enterAnimation>0&&exitAnimation>0&&popStackEnterAnimation>0&&popStackExitAnimation>0){
				beginTransaction().setCustomAnimations(enterAnimation,exitAnimation,popStackEnterAnimation,popStackExitAnimation);
			}else if(enterAnimation>0&&exitAnimation>0){
				beginTransaction().setCustomAnimations(enterAnimation,exitAnimation);
			}
		}
		attachFragment(fragment,tag);
		synchronized(lock){
			stack.add(fragment);
			tagStack.add(tag);
		}
	}
	
	private void removeFragment(Fragment fragment) {
		if (fragment != null) {
			beginTransaction().remove(fragment);
		}
	}
	private void attachFragment(Fragment fragment, String tag) {
		if (fragment != null) {
			Log.i(STATE_TAG, "attachFragment tag="+tag);
			if (fragment.isDetached()) {
				beginTransaction()
				.attach(fragment);
				if(stack.size()>0){
					beginTransaction().addToBackStack(tag);
				}
			}else if (!fragment.isAdded()){
				beginTransaction()
				.replace(containerId, fragment, tag);
				if(stack.size()>0){
					beginTransaction().addToBackStack(tag);
				}
			}else{
				Log.i(STATE_TAG, "fragment state illegal "+fragment);
			}
		}else{
			Log.i(STATE_TAG, "fragment is null");
		}
	}
	
	public BaseFragment peek() {
		return stack.peek();
	}
	
	public int size() {
		return stack.size();
	}
	
	public boolean pop() {
		if (stack.size() > 1) {
			fragmentManager.popBackStackImmediate();
			synchronized(lock){
				if(stack.peek().getTargetFragment()!=null){
					stack.peek().setReult(Activity.RESULT_CANCELED);
				}
				stack.pop();
				tagStack.pop();
			}
			return true;
		}
		return false;
	}	
	public void commit() {
		if(fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
		      handler.removeCallbacks(execPendingTransactions);
		      handler.post(execPendingTransactions);
		}else{
			Log.i(STATE_TAG, "fragmentTransaction is null or empty");
		}
	}
	public boolean executePendingTransactions() {
	    if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
	      handler.removeCallbacks(execPendingTransactions);
	      fragmentTransaction.commitAllowingStateLoss();
	      fragmentTransaction = null;
	      return fragmentManager.executePendingTransactions();
	    }
	    return false;
	}
	
}