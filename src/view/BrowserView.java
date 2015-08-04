package view;

import org.apache.commons.validator.util.ValidatorUtils;

import presenter.BrowserPresenter;
import utils.Utils;

import com.example.browser.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class BrowserView extends RelativeLayout implements IBrowserView {

	private EditText mEdittextWebsite;
	private Button mButtonRefreshWebsite;
	private WebView mWebViewContent;
	private BrowserPresenter mBrowserPresenter;

	public BrowserView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
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
		mWebViewContent = (WebView) findViewById(R.id.web_view_content);
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

	}

	private void setUpButton() {

		mButtonRefreshWebsite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBrowserPresenter.reloadWebsite(mEdittextWebsite.getText().toString());
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
							mBrowserPresenter.loadWebsite(mEdittextWebsite.getText().toString());
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
}
