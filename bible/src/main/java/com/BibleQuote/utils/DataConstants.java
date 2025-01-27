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
 * File: DataConstants.java
 *
 * Created by Vladimir Yakushev at 5/2018
 * E-mail: ru.phoenix@gmail.com
 * WWW: http://www.scripturesoftware.org
 */

package com.BibleQuote.utils;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.File;

public final class DataConstants {

    private static final String APP_DIR_NAME = "BibleQuote";
    private static final String DB_DATA_DIR_NAME = "data";
    private static final String MODULE_DIR_NAME = "modules";

    private DataConstants() {
    }

    public static String getDbExternalDataPath() {
        return Environment.getExternalStorageDirectory() + File.separator
                + APP_DIR_NAME + File.separator + DataConstants.DB_DATA_DIR_NAME;
    }

    public static String getFsAppDirName() {
        return Environment.getExternalStorageDirectory() + File.separator + APP_DIR_NAME;
    }

    @NonNull
    public static File getLibraryPath(@NonNull Context context) {
        return new File(context.getFilesDir(), MODULE_DIR_NAME);
    }
}
