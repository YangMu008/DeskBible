package com.BibleQuote.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.TreeSet;

import com.BibleQuote.utils.StringProc;

public class Chapter {
	
	private Integer number;
	private String text;
	private TreeMap <Integer, Verse> verses = new TreeMap<Integer, Verse>();
	private Book book;
	
	
	public Integer getNumber() {
		return number;
	}
	
	public String getText() {
		if (text == null && verses.size() > 0) {
			StringBuilder buffer = new StringBuilder();
			for (Integer verseNumber : verses.keySet()) {
				buffer.append(verses.get(verseNumber).getText());
			}
			text = buffer.toString();
		}
		return text;
	}
	
	public String getText(int fromVerse, int toVerse) {
		StringBuilder buffer = new StringBuilder();
		for (int verseNumber = fromVerse; verseNumber <= toVerse; verseNumber++) {
			Verse ver = verses.get(verseNumber);
			if (ver != null) {
				buffer.append(ver.getText());
			}
		}
		return buffer.toString();
	}
	
	public ArrayList<Integer> getVerseNumbers() {
		ArrayList<Integer> verseNumbers = new ArrayList<Integer>();
		for (Integer verse : verses.keySet()) {
			verseNumbers.add(verse);
		}
		return verseNumbers;
	}
	
	public LinkedHashMap<Integer, String> getVerses(TreeSet<Integer> verses) {
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		ArrayList<Verse> versesList = getVerseList();
		for (Integer verse : verses) {
			if (versesList.size() < (verse - 1)) {
				break;
			}
			result.put(verse, StringProc.cleanVerseText(versesList.get(verse).getText()));
		}
		
		return result;
	}

	public ArrayList<Verse> getVerseList() {
		ArrayList<Verse> verseList = new ArrayList<Verse>();
		for (Integer verse : verses.keySet()) {
			verseList.add(verses.get(verse));
		}
		return verseList;
	}
	
	public Chapter(Book book, Integer number, ArrayList<Verse> verseList) {
		this.book = book;
		this.number = number;
		
		Integer verseNumber = 1;
		for (Verse verse : verseList) {
			verses.put(verseNumber++, verse);
		}
	}
	
	public Book getBook() {
		return book;
	}
}
