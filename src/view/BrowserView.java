package view;

import presenter.BrowserPresenter;
import utils.Utils;

import com.example.browser.R;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class BrowserView extends RelativeLayout implements IBrowserView {

	private EditText mEdittextWebsite;
	private Button mButtonRefreshWebsite;
	private CustomWebView mWebViewContent;
	private ProgressBarView mProgressBarView;
	private BrowserPresenter mBrowserPresenter;
	private LinearLayout mLinearLayoutWebsiteBar;

	private final static int ANIMATION_DISTANCE = 80;
	private boolean mIsWebsiteBarVisible = true;

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
		mWebViewContent = (CustomWebView) findViewById(R.id.web_view_content);
		mProgressBarView = (ProgressBarView) findViewById(R.id.progress_bar_view);
		mLinearLayoutWebsiteBar = (LinearLayout) findViewById(R.id.linear_layout_website_bar);
	}

	private void setUpWebView() {
		mWebViewContent.getSettings().setJavaScriptEnabled(true);
		mWebViewContent.getSettings().setBuiltInZoomControls(true);
		mWebViewContent.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				mWebViewContent.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(getContext(), description, Toast.LENGTH_SHORT)
						.show();
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
					animateHideWebsiteBar();
					mIsWebsiteBarVisible = false;
				} else if (dy < -ANIMATION_DISTANCE && !mIsWebsiteBarVisible) {
					animateShowWebsiteBar();
					mIsWebsiteBarVisible = true;
				}

			}

		});
	}

	protected void animateShowWebsiteBar() {
		makeMarginValueAnimator(0).start();
	}

	private void animateHideWebsiteBar() {
		makeMarginValueAnimator(-mLinearLayoutWebsiteBar.getHeight()).start();
	}

	private ValueAnimator makeMarginValueAnimator(int finalValue) {
		ValueAnimator varl = ValueAnimator.ofInt(finalValue);
		varl.setDuration(100);
		varl.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mLinearLayoutWebsiteBar
						.getLayoutParams();
				lp.setMargins(0, (Integer) animation.getAnimatedValue(), 0, 0);
				mLinearLayoutWebsiteBar.setLayoutParams(lp);
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
}
