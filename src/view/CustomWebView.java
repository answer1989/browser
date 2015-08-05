package view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class CustomWebView extends WebView {

	private OnScrollChangeListener mOnScrollChangeListener;

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
		if (mOnScrollChangeListener != null) {
			mOnScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
		}
	}
}
