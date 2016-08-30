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
 * File: LinkConverter.java
 *
 * Created by Vladimir Yakushev at 8/2016
 * E-mail: ru.phoenix@gmail.com
 * WWW: http://www.scripturesoftware.org
 */

package com.BibleQuote.utils.modules;

import com.BibleQuote.BibleQuoteApp;
import com.BibleQuote.domain.entity.BibleReference;
import com.BibleQuote.domain.entity.Book;
import com.BibleQuote.domain.entity.Module;
import com.BibleQuote.domain.exceptions.BookNotFoundException;
import com.BibleQuote.domain.exceptions.OpenModuleException;
import com.BibleQuote.managers.BibleBooksID;
import com.BibleQuote.managers.Librarian;

public final class LinkConverter {

	private LinkConverter() throws InstantiationException {
		throw new InstantiationException("This class is not for instantiation");
	}

	public static String getOSIStoHuman(String linkOSIS, Librarian librarian) throws BookNotFoundException, OpenModuleException {
		String[] param = linkOSIS.split("\\.");
		if (param.length < 3) {
			return "";
		}

		String moduleID = param[0];
		String bookID = param[1];
		String chapter = param[2];

		Module currModule;
		try {
			currModule = BibleQuoteApp.getInstance().getLibraryController().getModuleByID(moduleID);
		} catch (OpenModuleException e) {
			return "";
		}
		Book currBook = librarian.getBookByID(currModule, bookID);
		if (currBook == null) {
			return "";
		}

		String humanLink = moduleID + ": " + currBook.getShortName() + " " + chapter;
		if (param.length > 3) {
			humanLink += ":" + param[3];
		}

		return humanLink;
	}

	public static String getOSIStoHuman(BibleReference reference) {
		if (reference.getFromVerse() != reference.getToVerse()) {
			return String.format("%1$s %2$s:%3$s-%4$s",
					reference.getBookFullName(), reference.getChapter(),
					reference.getFromVerse(), reference.getToVerse());
		} else {
			return String.format("%1$s %2$s:%3$s",
					reference.getBookFullName(), reference.getChapter(), reference.getFromVerse());
		}
	}

	public static String getHumanToOSIS(String humanLink) {
		// Получим имя модуля
		int position = humanLink.indexOf(':');
		if (position == -1) {
			return "";
		}
		String linkOSIS = humanLink.substring(0, position).trim();
		humanLink = humanLink.substring(position + 1).trim();
		if (humanLink.isEmpty()) {
			return "";
		}

		// Получим имя книги
		position = humanLink.indexOf(' ');
		if (position == -1) {
			return "";
		}
		linkOSIS += "." + BibleBooksID.getID(humanLink.substring(0, position).trim());
		humanLink = humanLink.substring(position).trim();
		if (humanLink.isEmpty()) {
			return linkOSIS + ".1";
		}

		// Получим номер главы
		position = humanLink.indexOf(':');
		if (position == -1) {
			return "";
		}
		linkOSIS += "." + humanLink.substring(0, position).trim().replaceAll("\\D", "");
		humanLink = humanLink.substring(position).trim().replaceAll("\\D", "");
		if (humanLink.isEmpty()) {
			return linkOSIS;
		} else {
			// Оставшийся кусок - номер стиха
			return linkOSIS + "." + humanLink;
		}
	}

}
