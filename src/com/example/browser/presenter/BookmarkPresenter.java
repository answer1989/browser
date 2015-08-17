package com.example.browser.presenter;

import java.util.List;

import com.example.browser.model.BookmarkModel;
import com.example.browser.model.bean.Bookmark;
import com.example.browser.view.bookmark.IBookmarkView;

public class BookmarkPresenter {
	private BookmarkModel mBookMarkModel;
	private IBookmarkView mBookmarkView;
	private final static int LOAD_SIZE = 20;

	public BookmarkPresenter(IBookmarkView bookmarkView) {
		mBookmarkView = bookmarkView;
		mBookMarkModel = new BookmarkModel();
	}

	public void loadBookmark(long fromId) {
		List<Bookmark> bookmarks = mBookMarkModel.getlimitedSizeBookMark(
				fromId, LOAD_SIZE);
		if (bookmarks.size() == 0) {
			mBookmarkView.showEmptyView();
		} else {
			mBookmarkView.showBookmarkView(bookmarks);
		}
	}

	public void loadMoreBookmark(long fromId) {
		List<Bookmark> bookmarks = mBookMarkModel.getlimitedSizeBookMark(
				fromId, LOAD_SIZE);
		mBookmarkView.appendMoreBookmark(bookmarks);
	}

	public void deleteBookmark(Bookmark bookmark, int position) {
		mBookMarkModel.deleteBookmark(bookmark.getId());
		mBookmarkView.deleteBookmark(bookmark, position);
	}
}
