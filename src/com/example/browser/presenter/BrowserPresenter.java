package com.example.browser.presenter;

import java.util.Locale;

import com.example.browser.model.BookmarkModel;
import com.example.browser.model.IBookmarkModel;
import com.example.browser.model.bean.Bookmark;
import com.example.browser.util.Utils;
import com.example.browser.view.custom.IBrowserView;


import android.text.TextUtils;

public class BrowserPresenter {

	private IBrowserView mBrowserView;
	private IBookmarkModel mBookmarkModel;

	public BrowserPresenter(IBrowserView browserView) {
		mBrowserView = browserView;
		mBookmarkModel = new BookmarkModel();
	}

	public void loadWebsite(String website) {
		if (TextUtils.isEmpty(website)) {
			return;
		}

		website = website.toLowerCase(Locale.US);

		if (!Utils.isValidWebsite(website)) {
			website = Utils.getBaiduSearchString(website);
		}

		mBrowserView.loadWebsite(website);
	}

	public void reloadWebsite(String website) {
		mBrowserView.refresh();
	}

	public void loadPreviousPage() {
		mBrowserView.goPreviousPage();
	}

	public void loadNextPage() {
		mBrowserView.goNextPage();
	}

	public void showLoadingProgress(int progress) {
		mBrowserView.setProgress(progress);
		if (progress >= 100 || progress <= 0) {
			mBrowserView.hideProgress();
		} else {
			mBrowserView.showProgress();
		}
	}

	public void addBookmark(String title, String website) {
		if (TextUtils.isEmpty(title) || TextUtils.isEmpty(website)) {
			return;
		}

		Bookmark bookmark = new Bookmark();
		bookmark.setTitle(title);
		bookmark.setWebsite(website);

		if (mBookmarkModel.isBookmarkExist(website)) {
			mBrowserView.showBookmarkExist(bookmark);
		} else {
			mBookmarkModel.saveBookmark(bookmark);
			mBrowserView.showBookmarkAddSuccess();
		}
	}

	public void replaceBookmark(String title, String website) {
		Bookmark bookmark = new Bookmark();
		bookmark.setTitle(title);
		bookmark.setWebsite(website);

		mBookmarkModel.replaceBookmark(bookmark);
	}
}
