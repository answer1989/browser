package com.example.browser.view.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.browser.R;
import com.example.browser.view.bookmark.BookmarkActivity;
import com.example.browser.view.custom.BrowserView;

public class MainActivity extends Activity implements OnClickListener, OnTabChangeListener{

	private BrowserView mCurrentBrowserView;
	private Button mButtonGoPreviousPage;
	private Button mButtonGoNextPage;
	private Button mButtonAddBookMark;
	private Button mButtonTab;
	private FrameLayout mFrameLayoutWebViewContainer;
	private TabsView mTabsView;
	private List<BrowserView> mBrowserViews = new ArrayList<BrowserView>();
	
	private final static int MAX_TAB = 10;
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
		mButtonTab.setOnClickListener(this);
	}

	private void initView() {
		mCurrentBrowserView = new BrowserView(this.getApplicationContext());
		mBrowserViews.add(mCurrentBrowserView);
		
		mFrameLayoutWebViewContainer = (FrameLayout) findViewById(R.id.frame_layout_web_view_container);
		showWebView(mCurrentBrowserView);
				
		mButtonGoPreviousPage = (Button) findViewById(R.id.button_go_previous_page);
		mButtonGoNextPage = (Button) findViewById(R.id.button_go_next_page);
		mButtonAddBookMark = (Button) findViewById(R.id.button_go_bookmark);
		mButtonTab = (Button) findViewById(R.id.button_tab);
		mTabsView = (TabsView)findViewById(R.id.custom_tabs_view);
		mTabsView.setTabList(mBrowserViews);
		mTabsView.setOnTabChangeListener(this);
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

		case R.id.button_tab:
			showTabList();
			break;
			
		default:
			break;
		}

	}

	private void addNewTab() {
		if(mBrowserViews.size() == MAX_TAB){
			Toast.makeText(getApplicationContext(), "reach max tabs", Toast.LENGTH_SHORT).show();
		}
		BrowserView browserView = new BrowserView(getApplicationContext());
		mBrowserViews.add(browserView);
		mTabsView.notifyDataSetChanged();
		showWebView(browserView);
		mTabsView.dismiss();
	}

	private void goPreviousPage() {
		mCurrentBrowserView.goPreviousPage();
	}

	private void goNextPage() {
		mCurrentBrowserView.goNextPage();
	}

	private void goToBookMark() {
		Intent intent = new Intent();
		intent.setClass(this, BookmarkActivity.class);
		startActivityForResult(intent, OPEN_BOOKMARK_CODE);
	}

	private void showTabList() {
		if(mTabsView.isShowing()){
			mTabsView.dismiss();
		}else{
			mTabsView.notifyDataSetChanged();
			mTabsView.show();
		}
	}
	
	private void showWebView(BrowserView browserView){
		mCurrentBrowserView = browserView;
		mFrameLayoutWebViewContainer.removeAllViews();
		mFrameLayoutWebViewContainer.addView(browserView);
	}

	@Override
	public void onBackPressed() {
		
		if(mTabsView.isShowing()){
			mTabsView.dismiss();
			return;
		}
		
		if (mCurrentBrowserView.canGoPreviousPage()) {
			mCurrentBrowserView.goPreviousPage();
		} else {
			super.onBackPressed();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == OPEN_BOOKMARK_CODE && resultCode == RESULT_OK){
			String bookmark = data.getStringExtra(BookmarkActivity.OPEN_BOOK_MARK);
			mCurrentBrowserView.loadWebsite(bookmark);
		}
	}

	@Override
	public void onTabClose(int position) {
		if(mBrowserViews.size() == 1){
			mBrowserViews.remove(position);
			BrowserView browserView = new BrowserView(getApplicationContext());
			mBrowserViews.add(browserView);
			showWebView(browserView);
		}else{
			mBrowserViews.remove(position);
			showWebView(mBrowserViews.get(0));
		}
		mTabsView.notifyDataSetChanged();
		mTabsView.dismiss();
	}

	@Override
	public void onTabSelected(int position) {
		mTabsView.dismiss();
		showWebView(mBrowserViews.get(position));
	}

	@Override
	public void onTabAdd() {
		addNewTab();
	}

}
