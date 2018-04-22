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
 * File: SplashPresenter.java
 *
 * Created by Vladimir Yakushev at 10/2017
 * E-mail: ru.phoenix@gmail.com
 * WWW: http://www.scripturesoftware.org
 */

package com.BibleQuote.presentation.ui.splash;

import com.BibleQuote.R;
import com.BibleQuote.domain.controller.ILibraryController;
import com.BibleQuote.domain.logger.StaticLogger;
import com.BibleQuote.presentation.ui.base.BasePresenter;
import com.BibleQuote.utils.UpdateManager;

import javax.inject.Inject;

class SplashPresenter extends BasePresenter<SplashView> {

    private final ILibraryController libraryController;
    private final UpdateManager updateManager;

    @Inject
    SplashPresenter(ILibraryController libraryController, UpdateManager updateManager) {
        this.libraryController = libraryController;
        this.updateManager = updateManager;
    }

    @Override
    public void onViewCreated() {
        // nothing
    }

    void update() {
        addSubscription(new AppInitTask(getView(), updateManager, libraryController)
                .subscribeOn(getView().backgroundThread())
                .observeOn(getView().mainThread())
                .subscribe(context -> {
                    SplashView view = getView();
                    if (view != null) {
                        view.gotoReaderActivity();
                    }
                }, throwable -> {
                    StaticLogger.error(this, "Update error", throwable);
                    SplashView view = getView();
                    if (view != null) {
                        view.showToast(R.string.error_initialization_failed);
                        view.gotoReaderActivity();
                    }
                }));
    }
}