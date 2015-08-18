package com.example.browser.view.custom;

import android.webkit.WebView;

public interface OnScrollChangeListener {

	public void onScrollChange(WebView webView, int x, int y, int oldX, int oldY);
	
	public void onScrollToEnd(WebView webView);
	
	public void onScrollToTop(WebView webview);
}
