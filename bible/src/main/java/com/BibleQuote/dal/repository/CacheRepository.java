package com.BibleQuote.dal.repository;

import com.BibleQuote.dal.CacheContext;
import com.BibleQuote.utils.Log;
import com.BibleQuote.exceptions.FileAccessException;

public class CacheRepository<T> {
	private static final String TAG = "CacheRepository";

	private CacheContext cacheContext;

	public CacheRepository(CacheContext cacheContext) {
		this.cacheContext = cacheContext;
	}

	public CacheContext getCacheContext() {
		return cacheContext;
	}

	public T getData() throws FileAccessException {
		Log.i(TAG, "Loading data from a file system cache.");
		return cacheContext.loadData();
	}

	public void saveData(T data) throws FileAccessException {
		Log.i(TAG, "Save modules to a file system cache.");
		cacheContext.saveData(data);
	}

	public boolean isCacheExist() {
		return cacheContext.isCacheExist();
	}
}
