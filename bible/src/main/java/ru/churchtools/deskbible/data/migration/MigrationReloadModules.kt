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
 * Project: DeskBible
 * File: MigrationReloadModules.kt
 *
 * Created by Vladimir Yakushev at 11/2021
 * E-mail: ru.phoenix@gmail.com
 * WWW: http://www.churchtools.ru
 */

package ru.churchtools.deskbible.data.migration

import com.BibleQuote.R
import com.BibleQuote.domain.controller.ILibraryController
import ru.churchtools.deskbible.domain.migration.Migration

/**
 * Миграция, котороя запускает заново поиск модулей пользователя
 *
 * @author Vladimir Yakushev <ru.phoenix@gmail.com>
 */
class MigrationReloadModules(
    private val libraryController: ILibraryController,
    version: Int
) : Migration(version) {

    override fun doMigrate() {
        libraryController.reloadModules()
    }

    override fun getMigrationDescription(): Int {
        return R.string.update_reload_modules
    }
}