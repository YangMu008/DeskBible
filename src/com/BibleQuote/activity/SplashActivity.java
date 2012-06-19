/*
 * Copyright (C) 2011 Scripture Software (http://scripturesoftware.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.BibleQuote.activity;

import greendroid.app.GDActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.BibleQuote.BibleQuoteApp;
import com.BibleQuote.R;
import com.BibleQuote.managers.AsyncCommand;
import com.BibleQuote.managers.AsyncManager;
import com.BibleQuote.managers.AsyncCommand.ICommand;
import com.BibleQuote.utils.OnTaskCompleteListener;
import com.BibleQuote.utils.Task;
import com.BibleQuote.utils.Log;

public class SplashActivity extends GDActivity implements OnTaskCompleteListener {

	private AsyncManager mAsyncManager;
	private static final String TAG = "SplashActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setActionBarContentView(R.layout.main);
		getActionBar().setVisibility(View.GONE);
		
		Log.Init(getApplicationContext());
		
		Log.i(TAG, "Start task InitApplication()");
		BibleQuoteApp app = (BibleQuoteApp) getGDApplication();
		String progressMessage = getResources().getString(R.string.messageLoad);

		mAsyncManager = app.getAsyncManager();
		mAsyncManager.handleRetainedTask(getLastNonConfigurationInstance(), this);
		mAsyncManager.setupTask(new AsyncCommand(new InitApplication(), progressMessage, true), this);
	}
    
	private class InitApplication implements ICommand {
		@Override
		public void execute() throws Exception {
			Log.i(TAG, "InitApplication.execute()");
			BibleQuoteApp app = (BibleQuoteApp) getGDApplication();
			app.Init();
		}
	}

	@Override
	public void onTaskComplete(Task task) {
		Log.i(TAG, "Start reader activity");
    	startActivity(new Intent(this, ReaderActivity.class));
		finish();
	}
}