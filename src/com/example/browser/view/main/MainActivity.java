package com.example.browser.view.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.browser.R;
import com.example.browser.view.bookmark.BookmarkActivity;
import com.example.browser.view.custom.IBrowserView;

public class MainActivity extends Activity implements OnClickListener{

	private IBrowserView mBrowserView;
	private Button mButtonGoPreviousPage;
	private Button mButtonGoNextPage;
	private Button mButtonAddBookMark;
	private Button mButtonAddNewTab;
	
	private final static int OPEN_BOOKMARK_CODE = 0x1000;

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
		mButtonAddBookMark = (Button) findViewById(R.id.button_go_bookmark);
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

		case R.id.button_go_bookmark:
			goToBookMark();
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

	private void goToBookMark() {
		Intent intent = new Intent();
		intent.setClass(this, BookmarkActivity.class);
		startActivityForResult(intent, OPEN_BOOKMARK_CODE);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == OPEN_BOOKMARK_CODE && resultCode == RESULT_OK){
			String bookmark = data.getStringExtra(BookmarkActivity.OPEN_BOOK_MARK);
			mBrowserView.loadWebsite(bookmark);
		}
	}

}
