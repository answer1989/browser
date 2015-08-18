package com.example.browser.view.custom;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.browser.R;
import com.example.browser.model.bean.Bookmark;
import com.example.browser.presenter.BrowserPresenter;
import com.example.browser.util.Utils;

public class BrowserView extends RelativeLayout implements IBrowserView {

	private EditText mEdittextWebsite;
	private Button mButtonRefreshWebsite;
	private Button mButtonAddBookMark;
	private CustomWebView mWebViewContent;
	private ProgressBarView mProgressBarView;
	private BrowserPresenter mBrowserPresenter;
	private RelativeLayout mRelativeLayoutWebsiteBar;

	private final static int ANIMATION_DISTANCE = 80;
	private boolean mIsWebsiteBarVisible = true;
	private AlertDialog mAlertDialogExistBookmark;

	public BrowserView(Context context) {
		this(context, null);
	}

	public BrowserView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context)
				.inflate(R.layout.custom_browser_view, this);

		initView();

		setUpWebsiteEditText();

		setUpButton();

		setUpWebView();

		mBrowserPresenter = new BrowserPresenter(this);
	}

	private void initView() {
		mEdittextWebsite = (EditText) findViewById(R.id.edit_text_website);
		mButtonRefreshWebsite = (Button) findViewById(R.id.button_refresh_website);
		mButtonAddBookMark = (Button) findViewById(R.id.button_add_bookmark);
		mWebViewContent = (CustomWebView) findViewById(R.id.web_view_content);
		mProgressBarView = (ProgressBarView) findViewById(R.id.progress_bar_view);
		mRelativeLayoutWebsiteBar = (RelativeLayout) findViewById(R.id.relative_layout_website_bar);

		mAlertDialogExistBookmark = new AlertDialog.Builder(getContext())
				.setMessage(R.string.alert_bookmark_exist).create();

		mAlertDialogExistBookmark.setButton(DialogInterface.BUTTON_NEGATIVE,
				getContext().getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mAlertDialogExistBookmark.dismiss();
					}
				});

		mAlertDialogExistBookmark.setButton(DialogInterface.BUTTON_POSITIVE,
				getContext().getString(R.string.confirm),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mBrowserPresenter.replaceBookmark(
								mWebViewContent.getTitle(),
								mWebViewContent.getUrl());
					}
				});
	}

	private void setUpWebView() {
		mWebViewContent.getSettings().setJavaScriptEnabled(true);
		mWebViewContent.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(getContext(), description, Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				mEdittextWebsite.setText(url);
			}
		});

		mWebViewContent.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				mBrowserPresenter.showLoadingProgress(newProgress);
			}

		});

		mWebViewContent.setOnScrollChangeListener(new OnScrollChangeListener() {

			@Override
			public void onScrollChange(WebView webView, int x, int y, int oldX,
					int oldY) {
				int dy = y - oldY;

				if (dy > ANIMATION_DISTANCE && mIsWebsiteBarVisible) {
					mIsWebsiteBarVisible = false;
					animateHideWebsiteBar();
				} else if (dy < -ANIMATION_DISTANCE && !mIsWebsiteBarVisible) {
					mIsWebsiteBarVisible = true;
					animateShowWebsiteBar();
				}

			}

			@Override
			public void onScrollToEnd(WebView webView) {
				if (!mIsWebsiteBarVisible) {
					mIsWebsiteBarVisible = true;
					animateShowWebsiteBar();
				}
			}

			@Override
			public void onScrollToTop(WebView webview) {
				if (!mIsWebsiteBarVisible) {
					mIsWebsiteBarVisible = true;
					animateShowWebsiteBar();
				}
			}

		});
	}

	protected void animateShowWebsiteBar() {
		makeMarginValueAnimator(0).start();
	}

	private void animateHideWebsiteBar() {
		makeMarginValueAnimator(-mRelativeLayoutWebsiteBar.getHeight()).start();
	}

	private ValueAnimator makeMarginValueAnimator(int finalValue) {
		ValueAnimator varl = ValueAnimator.ofInt(finalValue);
		varl.setDuration(100);
		varl.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mRelativeLayoutWebsiteBar
						.getLayoutParams();
				lp.setMargins(0, (Integer) animation.getAnimatedValue(), 0, 0);
				mRelativeLayoutWebsiteBar.setLayoutParams(lp);
			}
		});

		return varl;
	}

	private void setUpButton() {

		mButtonRefreshWebsite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBrowserPresenter.reloadWebsite(mEdittextWebsite.getText()
						.toString());
			}
		});

		mButtonAddBookMark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBrowserPresenter.addBookmark(mWebViewContent.getTitle(),
						mWebViewContent.getUrl());
			}
		});

	}

	private void setUpWebsiteEditText() {

		mEdittextWebsite
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_GO) {
							Utils.hideKeyBoard(getWindowToken(), getContext());
							mBrowserPresenter.loadWebsite(mEdittextWebsite
									.getText().toString());
							return true;
						}
						return false;
					}
				});

	}

	@Override
	public void goPreviousPage() {
		mWebViewContent.goBack();
	}

	@Override
	public void goNextPage() {
		mWebViewContent.goForward();
	}

	@Override
	public void loadWebsite(String website) {
		mWebViewContent.loadUrl(website);
	}

	@Override
	public void refresh() {
		mWebViewContent.reload();
	}

	@Override
	public boolean canGoPreviousPage() {
		return mWebViewContent.canGoBack();
	}

	@Override
	public boolean canGoNextPage() {
		return mWebViewContent.canGoForward();
	}

	@Override
	public void setProgress(int progress) {
		mProgressBarView.setProgress(progress);
	}

	@Override
	public void showProgress() {
		if (mProgressBarView.getVisibility() != VISIBLE) {
			mProgressBarView.setVisibility(VISIBLE);
		}
	}

	@Override
	public void hideProgress() {
		mProgressBarView.setVisibility(GONE);
	}

	@Override
	public void showBookmarkExist(Bookmark bookmark) {
		if (!mAlertDialogExistBookmark.isShowing()) {
			mAlertDialogExistBookmark.show();
		}
	}

	@Override
	public void showBookmarkAddSuccess() {
		Toast.makeText(getContext(), R.string.add_bookmark_success,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public String getTitle() {
		return mWebViewContent.getTitle();
	}

	@Override
	public String getWebsite() {
		// TODO Auto-generated method stub
		return mWebViewContent.getUrl();
	}

}
