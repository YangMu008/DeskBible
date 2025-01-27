/*
 * Copyright (C) 2011 Scripture Software
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Project: BibleQuote-for-Android
 * File: SplashActivity.java
 *
 * Created by Vladimir Yakushev at 4/2018
 * E-mail: ru.phoenix@gmail.com
 * WWW: http://www.scripturesoftware.org
 */
package com.BibleQuote.presentation.ui.splash;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.BibleQuote.R;
import com.BibleQuote.di.component.ActivityComponent;
import com.BibleQuote.presentation.ui.base.BaseActivity;
import com.BibleQuote.presentation.ui.reader.ReaderActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import ru.churchtools.deskbible.domain.logger.StaticLogger;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView {

    @BindView(R.id.root_layout)
    ViewGroup rootLayout;
    @BindView(R.id.update_description)
    TextView updateDescriptionView;
    private ActivityResultLauncher<String> mActivityResultLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            FirebaseAnalytics instance = FirebaseAnalytics.getInstance(this);
            if (isGranted) {
                StaticLogger.info(this, "Permissions request granted");
                presenter.update();
            } else {
                Snackbar.make(rootLayout, R.string.msg_permission_denied, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry, v -> checkPermissions())
                        .show();
                StaticLogger.info(this, "Permissions denied");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermissions();
    }

    @Override
    protected void attachView() {
        presenter.attachView(this);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void gotoReaderActivity() {
        startActivity(new Intent(this, ReaderActivity.class));
        finish();
    }

    @Override
    public void showUpdateMessage(int message) {
        StaticLogger.info(this, getString(message));
        updateDescriptionView.setText(message);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            mActivityResultLauncher.launch(READ_EXTERNAL_STORAGE);
        } else {
            StaticLogger.info(this, "Permissions granted");
            presenter.update();
        }
    }
}