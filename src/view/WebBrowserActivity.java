package view;

import com.example.browser.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WebBrowserActivity extends Activity implements OnClickListener {

	private IBrowserView mBrowserView;
	private Button mButtonGoPreviousPage;
	private Button mButtonGoNextPage;
	private Button mButtonAddBookMark;
	private Button mButtonAddNewTab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_browser);

		initView();

		setUpButtonListener();
	}

	private void setUpButtonListener() {
		mButtonGoPreviousPage.setOnClickListener(this);
		mButtonGoNextPage.setOnClickListener(this);
		mButtonAddBookMark.setOnClickListener(this);
		mButtonAddNewTab.setOnClickListener(this);
	}

	private void initView() {
		mBrowserView = (IBrowserView) findViewById(R.id.browser_view);
		mButtonGoPreviousPage = (Button) findViewById(R.id.button_go_previous_page);
		mButtonGoNextPage = (Button) findViewById(R.id.button_go_next_page);
		mButtonAddBookMark = (Button) findViewById(R.id.button_add_bookmark);
		mButtonAddNewTab = (Button) findViewById(R.id.button_new_tab);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_go_previous_page:
			goPreviousPage();
			break;

		case R.id.button_go_next_page:
			goNextPage();
			break;

		case R.id.button_add_bookmark:
			addBookMark();
			break;

		case R.id.button_new_tab:
			switchNewTab();
			break;
		default:
			break;
		}

	}

	private void goPreviousPage() {
		mBrowserView.goPreviousPage();
	}

	private void goNextPage() {
		mBrowserView.goNextPage();
	}

	private void addBookMark() {

	}

	private void switchNewTab() {

	}

	@Override
	public void onBackPressed() {
		if (mBrowserView.canGoPreviousPage()) {
			mBrowserView.goPreviousPage();
		} else {
			super.onBackPressed();
		}
	}

}
