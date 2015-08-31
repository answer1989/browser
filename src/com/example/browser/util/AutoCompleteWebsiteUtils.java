package com.example.browser.util;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteWebsiteUtils {

	private List<String> mWebsiteListe = new ArrayList<String>();;
	private final static String HTTP_PREFIX = "http://";
	private final static String HTTPS_PREFIX = "https://";
	private final static String COM_SUFFIX = ".com";
	private final static String BAIDU_SEARCH_PREFIX = "https://www.baidu.com/s?wd=";

	public List<String> getAutoCompleteWebsite(String keyWord) {
		mWebsiteListe.clear();
		mWebsiteListe.add(0, getHttpItem(keyWord));
		mWebsiteListe.add(1, getHttpsItem(keyWord));
		mWebsiteListe.add(2, getBaiduSearchString(keyWord));
		return mWebsiteListe;
	}

	private String getHttpItem(String keyWord) {
		if (keyWord.startsWith(HTTP_PREFIX)) {
			if (keyWord.endsWith(COM_SUFFIX)) {
				return keyWord;
			} else {
				return keyWord + COM_SUFFIX;
			}
		} else if (keyWord.startsWith(HTTPS_PREFIX)) {
			if (keyWord.endsWith(COM_SUFFIX)) {
				return keyWord.replace(HTTPS_PREFIX, HTTP_PREFIX);
			} else {
				return keyWord.replace(HTTPS_PREFIX, HTTP_PREFIX) + COM_SUFFIX;
			}
		} else {
			if (keyWord.endsWith(COM_SUFFIX)) {
				return HTTP_PREFIX + keyWord;
			} else {
				return HTTP_PREFIX + keyWord + COM_SUFFIX;
			}
		}
	}

	private String getHttpsItem(String keyWord) {
		if (keyWord.startsWith(HTTPS_PREFIX)) {
			if (keyWord.endsWith(COM_SUFFIX)) {
				return keyWord;
			} else {
				return keyWord + COM_SUFFIX;
			}
		} else if (keyWord.startsWith(HTTP_PREFIX)) {
			if (keyWord.endsWith(COM_SUFFIX)) {
				return keyWord.replace(HTTP_PREFIX, HTTPS_PREFIX);
			} else {
				return keyWord.replace(HTTP_PREFIX, HTTPS_PREFIX) + COM_SUFFIX;
			}
		} else {
			if (keyWord.endsWith(COM_SUFFIX)) {
				return HTTPS_PREFIX + keyWord;
			} else {
				return HTTPS_PREFIX + keyWord + COM_SUFFIX;
			}
		}
	}

	public String getBaiduSearchString(String keyword) {
		return BAIDU_SEARCH_PREFIX + keyword;
	}
}
