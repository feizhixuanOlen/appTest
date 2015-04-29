package com.olen.mobile.app;

import com.olen.mobile.app.base.BaseMenuFragment;
import com.olen.mobile.app.base.FragmentInfo;
import com.olen.mobile.app.home.HomeFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LeftMenuFragment extends BaseMenuFragment implements OnClickListener{
	
	public LinearLayout loginLayout, laundryLayout, ordersLayout, knowledgeLayout, aboutLayout;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_leftmenu, container,false);
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
		if(savedInstanceState!=null){
			updateFocus(this.getCurrentModuleId());
		}
	}

	@Override
	protected void findViews(View v) {
		loginLayout=(LinearLayout) v.findViewById(R.id.leftmenu_login_layout);
		laundryLayout=(LinearLayout) v.findViewById(R.id.leftmenu_laundry_layout);
		ordersLayout=(LinearLayout) v.findViewById(R.id.leftmenu_orders_layout);
		knowledgeLayout=(LinearLayout) v.findViewById(R.id.leftmenu_knowledge_layout);
		aboutLayout=(LinearLayout) v.findViewById(R.id.leftmenu_about_layout);

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		loginLayout.setOnClickListener(this);
		laundryLayout.setOnClickListener(this);
		ordersLayout.setOnClickListener(this);
		knowledgeLayout.setOnClickListener(this);
		aboutLayout.setOnClickListener(this);
		laundryLayout.setSelected(true);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.leftmenu_login_layout:
			setContentFragment(HomeFragment.class, "HomeFragment",null,ModuleMenuIDS.MODULE_LOGIN);
			break;
			case R.id.leftmenu_laundry_layout:
				setContentFragment(HomeFragment.class, "HomeFragment",null,ModuleMenuIDS.MODULE_LAUNDRY);
				break;
			case R.id.leftmenu_orders_layout:
//				setContentFragment(ListDataFragment.class, "ListFragment",null,ModuleMenuIDS.MODULE_LIST);
				break;
			case R.id.leftmenu_knowledge_layout:
//				setContentFragment(SettingFragment.class, "ReaderFragment",null,ModuleMenuIDS.MODULE_SETTING);
				break;
			case R.id.leftmenu_about_layout:
//				setContentFragment(SwitchFragment.class, "SwitchFragment",null,ModuleMenuIDS.MODULE_SWITCH);
				break;
		}
		toggleMenu();
	}

	@Override
	protected void onContentChange(int moduleId) {
		this.setCurrentModuleId(moduleId);
		if(this.getView()!=null){
			updateFocus(moduleId);
			restoreFocus(getCurrentModuleId());
		}
	}
	
	private void updateFocus(int moduleId) {
		switch(moduleId){
			case ModuleMenuIDS.MODULE_LOGIN:
				loginLayout.setSelected(true);
				break;
			case ModuleMenuIDS.MODULE_LAUNDRY:
				laundryLayout.setSelected(true);
				break;
			case ModuleMenuIDS.MODULE_ORDERS:
				ordersLayout.setSelected(true);
				break;
			case ModuleMenuIDS.MODULE_KNOWLEDGE:
				knowledgeLayout.setSelected(true);
				break;
			case ModuleMenuIDS.MODULE_ABOUT:
				aboutLayout.setSelected(true);
				break;
		}
	}
	
	private void restoreFocus(int moduleId) {
		switch (moduleId) {
		case ModuleMenuIDS.MODULE_LOGIN:
			loginLayout.setSelected(false);
			break;
		case ModuleMenuIDS.MODULE_LAUNDRY:
			laundryLayout.setSelected(false);
			break;
		case ModuleMenuIDS.MODULE_ORDERS:
			ordersLayout.setSelected(false);
			break;
		case ModuleMenuIDS.MODULE_KNOWLEDGE:
			knowledgeLayout.setSelected(false);
			break;
		case ModuleMenuIDS.MODULE_ABOUT:
			aboutLayout.setSelected(false);
			break;
		}
	}
	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		// TODO Auto-generated method stub
		return null;
	}

}
