package com.olen.mobile.app.laundry.adapter;

import tv.pps.modules.imagelogic.ImageLogic;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class LaundryListAdapter extends BaseAdapter{

	private Context context;
	private ImageLogic mImageLogic;
	
	public LaundryListAdapter(Context context, ImageLogic mImageLogic) {
		this.context = context;
		this.mImageLogic = mImageLogic;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
}
