package presenter;

import java.util.List;
import java.util.Locale;

import model.BookMarkModel;
import model.Bookmark;
import model.IBookmarkModel;

import android.text.TextUtils;
import android.util.Log;
import utils.Utils;
import view.IBrowserView;

public class BrowserPresenter {

	private IBrowserView mBrowserView;
	private IBookmarkModel mBookmarkModel;

	public BrowserPresenter(IBrowserView browserView) {
		mBrowserView = browserView;
		mBookmarkModel = new BookMarkModel();
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
		
		List<Bookmark> bookmarks = mBookmarkModel.getlimitedSizeBookMark(-1, 100);
		for(Bookmark b : bookmarks){
			Log.e("book mark", b.toString());
		}
	}

	public void replaceBookmark(String title, String website) {
		Bookmark bookmark = new Bookmark();
		bookmark.setTitle(title);
		bookmark.setWebsite(website);

		mBookmarkModel.replaceBookmark(bookmark);
	}
}
