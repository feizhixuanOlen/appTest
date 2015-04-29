package com.olen.mobile.app;
/** 
 * Copyright (c) 2014 zhangxl.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.olen.mobile.app.common.Contants;
import com.olen.moblie.core.CoreApplication;
import com.olen.moblie.core.log.Log;
import com.olen.moblie.core.service.AppService;
import com.olen.moblie.core.service.status.StatusService;


/**
 * @description TODO
 * @author zhangxiaole
 * @data 2014-3-13
 */
public class MobileApplication extends CoreApplication {
	public final String TAG = Contants.makeLogTag(MobileApplication.class);
	private static final boolean LIFECYCLE = Contants.LIFECYCLE;
	public List<Activity> activityManager;
	private StatusService statusService;
	private AppStatusListener appStatusListener;

	@Override
	public void onCreate() {
		this.setDevMode(true);
		Log.setLogLevelFormat(Contants.LOG_LEVEL, Contants.LOG_FORMAT);
		super.onCreate();
		if (LIFECYCLE)
			Log.v(TAG, "onCreate");
		init();
	}

	public void init() {
		if (LIFECYCLE)
			Log.v(TAG, "init");
		activityManager = new ArrayList<Activity>();
		statusService = (StatusService) getAppService(AppService.STATUS_SERVICE);
		appStatusListener = new AppStatusListener();
		statusService.registerStatusListener(appStatusListener);
	}

	@Override
	public void onTerminate() {
		if (LIFECYCLE)
			Log.v(TAG, "onTerminate");
		super.onTerminate();
	}

	@Override
	public void onLowMemory() {
		if (LIFECYCLE)
			Log.v(TAG, "onLowMemory");
		super.onLowMemory();
	}

	@Override
	public void onTrimMemory(int level) {
		if (LIFECYCLE)
			Log.v(TAG, "onTrimMemory");
		super.onTrimMemory(level);
	}

	public void exit() {
		if (LIFECYCLE)
			Log.v(TAG, "exit");
		if (activityManager != null)
			activityManager.clear();
		statusService.unregisterStatusListener(appStatusListener);
		getAppServiceManager().destoryAllService();
		super.exit();
	}
}
