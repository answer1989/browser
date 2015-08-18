package com.example.browser.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class CustomWebView extends WebView {

	private OnScrollChangeListener mOnScrollChangeListener = new OnScrollChangeListener() {

		@Override
		public void onScrollToTop(WebView webview) {
			// Default do nothing
		}

		@Override
		public void onScrollToEnd(WebView webView) {
			// Default do nothing
		}

		@Override
		public void onScrollChange(WebView webView, int x, int y, int oldX,
				int oldY) {
			// Default do nothing
		}
	};

	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setOnScrollChangeListener(
			OnScrollChangeListener onScrollChangeListener) {
		mOnScrollChangeListener = onScrollChangeListener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		if (t <= 50) {
			mOnScrollChangeListener.onScrollToTop(this);
			return;
		}

		int height = (int) Math.floor(getContentHeight() * getScale());
		int webViewHeight = getHeight();
		int currentOffsetToEnd = height - webViewHeight - 10;
		if (t >= currentOffsetToEnd) {
			mOnScrollChangeListener.onScrollToEnd(this);
			return;

		}

		mOnScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);

	}
}
