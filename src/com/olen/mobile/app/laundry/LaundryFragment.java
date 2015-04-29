package com.olen.mobile.app.laundry;

import tv.pps.modules.imagelogic.ImageLogic;
import tv.pps.modules.imagelogic.ImageOnScrollListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.olen.mobile.app.R;
import com.olen.mobile.app.UIUtils.UIUtils;
import com.olen.mobile.app.base.BaseContentFragment;
import com.olen.mobile.app.base.FragmentInfo;
import com.olen.mobile.app.laundry.adapter.LaundryListAdapter;
import com.olen.mobile.app.widget.pullToRefreshListView.PullToRefreshBase.OnRefreshListener;
import com.olen.mobile.app.widget.pullToRefreshListView.PullToRefreshListView;
import com.olen.moblie.core.http.AsyncHttpClient;
import com.olen.moblie.core.http.AsyncHttpResponseHandler;
import com.olen.moblie.core.http.JsonHttpResponseHandler;
import com.olen.moblie.core.http.RequestParams;

public class LaundryFragment extends BaseContentFragment{

	private Activity mActivity;
	private ActionBar mActionBar;
	private PullToRefreshListView mRefreshView;
	private ImageLogic mImageLogic;
	private AsyncHttpClient httpClient;
	private ListView mListView;
	private static final String LAST_UPDATE_TIMESTAMP = "lastUpdateTimestamp";
	private LaundryListAdapter mListAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivity = getActivity();
		this.httpClient = AsyncHttpClient.build(TAG);
		this.mImageLogic=ImageLogic.create(mActivity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowCustomEnabled(false);
		return inflater.inflate(R.layout.laundry_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initData(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		findViews(view);
		initViews(savedInstanceState);
	}
	
	@Override
	protected void initData(Bundle savedInstanceState) {
		if (mListAdapter == null) {
			mListAdapter = new LaundryListAdapter(mActivity, mImageLogic);
		}
		mListView.setAdapter(mListAdapter);
		RequestParams params = new RequestParams();
		params.put("apiName", "user.login");
		params.put("UDID", "62e6a5e86948e11d467085c6a41b2350d7af132c");
		params.put("apiKey", "DC_IOS");
		params.put("sign", "1221");
		params.put("user_name", "12");
		params.put("phone", "18521301861");
		params.put("user_psw", "9733783262fd");
		httpClient.post("http://115.29.17.212/api/api.php", params, new AsyncHttpResponseHandler(){
			@Override
			public void onStart() {
				Log.d(TAG, "onStart : ");
				super.onStart();
			}
			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				Log.d(TAG, "onSuccess json : " + content);
				
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				// TODO Auto-generated method stub
				super.onFailure(error, content);
				Log.d(TAG, "onFailure : " + content);
			}
			@Override
			public void onFinish() {
				super.onFinish();
				Log.d(TAG, "onFinish");
			}
		});
	}

	@Override
	protected void findViews(View view) {
		mRefreshView = (PullToRefreshListView)view.findViewById(R.id.laundry_listview);
		mListView = mRefreshView.getRefreshableView();
		mRefreshView.setPullToRefreshEnabled(false);
		UIUtils.setPullToRefreshLastUpdated(mRefreshView, LAST_UPDATE_TIMESTAMP);
		// 设置下拉刷新事件监听
		mRefreshView.setOnRefreshListener(new ListItemRefreshListener());
		// 列表点击事件监听
		mListView.setOnItemClickListener(new ListItemClickListener());
		// 列表滚动监听
		mListView.setOnScrollListener(new ImageOnScrollListener(mActivity));
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected FragmentInfo getNavigtionUpToFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	/*列表下拉刷新事件监听实现类 */
	private class ListItemRefreshListener implements OnRefreshListener {
		@Override
		public void onRefresh() {
//			sendTopRequest();
//			sendChannelRequest();
		}
	}
	
	/*列表Item事件监听实现类 */
	private class ListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
		}
	}
}
