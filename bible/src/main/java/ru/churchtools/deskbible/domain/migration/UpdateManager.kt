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
 * File: UpdateManager.kt
 *
 * Created by Vladimir Yakushev at 11/2021
 * E-mail: ru.phoenix@gmail.com
 * WWW: http://www.churchtools.ru
 */

package ru.churchtools.deskbible.domain.migration

import com.BibleQuote.BuildConfig
import com.BibleQuote.utils.PreferenceHelper
import io.reactivex.Observable
import ru.churchtools.deskbible.domain.logger.StaticLogger

/**
 * Класс отвечающий за выполнение [Migration] при обновлении версии приложения
 */
class UpdateManager(
    private val prefHelper: PreferenceHelper,
    private val migrationList: Set<Migration>
) {

    fun update(): Observable<Int> {
        return Observable.create { emitter ->
            StaticLogger.info(this, "Start update manager...")
            val currVersionCode = prefHelper.getInt("versionCode")
            if (BuildConfig.VERSION_CODE > currVersionCode) {
                migrationList
                    .filter { it.version > currVersionCode }
                    .sortedBy { it.version }
                    .forEach {
                        emitter.onNext(it.description)
                        it.migrate(currVersionCode)
                    }
                prefHelper.saveInt("versionCode", BuildConfig.VERSION_CODE)
                StaticLogger.info(this, "Update success")
            }
            emitter.onComplete()
        }
    }
}
