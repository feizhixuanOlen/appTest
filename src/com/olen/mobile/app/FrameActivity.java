package com.olen.mobile.app;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import com.olen.mobile.app.base.BaseSlidingFragmentActivity;
import com.olen.mobile.app.home.HomeFragment;
import com.olen.mobile.app.laundry.LaundryFragment;
import com.olen.moblie.core.service.status.StatusListener;

public class FrameActivity extends BaseSlidingFragmentActivity implements StatusListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame_layout);
		if (savedInstanceState == null) {
			this.setMenuFragment(LeftMenuFragment.class, null);
			this.setContentFragment(LaundryFragment.class, "LaundryFragment", null,ModuleMenuIDS.MODULE_LAUNDRY);

		} else {
		}
		findViews();
		initViews(savedInstanceState);
		initData(savedInstanceState);
	}
	
	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

	@Override
	public void networkConnect(Context arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void networkDisconnect(Context arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void networkTo3G(Context arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storageMount(Context arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storageRemove(Context arg0) {
		// TODO Auto-generated method stub
		
	}
}
