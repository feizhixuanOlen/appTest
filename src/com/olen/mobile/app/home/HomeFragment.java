package com.olen.mobile.app.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.olen.mobile.app.R;
import com.olen.mobile.app.base.BaseContentFragment;
import com.olen.mobile.app.base.FragmentInfo;

public class HomeFragment extends BaseContentFragment {
	private Button mButton1;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_home, container,false);
		return v;
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		findViews(view);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews(savedInstanceState);
		initData(savedInstanceState);
	}
	@Override
	protected void initData(Bundle savedInstanceState) {

	}

	@Override
	protected void findViews(View view) {
		mButton1=(Button) view.findViewById(R.id.button1);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		this.setTitle(this.getClass().getSimpleName());
		mButton1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				toFragmentForResult();
//				 Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
//		         startActivityForResult(intent,1);
			}
			
		});
		
	}
	private void toFragmentForResult(){
//		replaceFragment(ItemFragment.class, "ItemFragment", new Bundle(),1);
	}
	
	@Override
	public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
		super.onFragmentResult(requestCode, resultCode, data);
		switch(requestCode){
			case 1:
				showToast("onFragmentResult resultCode="+resultCode);
				mButton1.setText("OK");
				break;
		}
	}

	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		return null;
	}


	@Override
	public boolean isCleanStack() {
		return true;
	}
	
}
