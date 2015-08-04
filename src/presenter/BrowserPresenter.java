package presenter;

import java.util.Locale;

import android.text.TextUtils;
import utils.Utils;
import view.IBrowserView;

public class BrowserPresenter {

	private IBrowserView mBrowserView;

	public BrowserPresenter(IBrowserView browserView) {
		mBrowserView = browserView;
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

}
