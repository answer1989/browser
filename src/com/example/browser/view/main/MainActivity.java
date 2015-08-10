package com.example.browser.view.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.browser.R;
import com.example.browser.R.layout;
import com.example.browser.view.bookmark.BookmarkActivity;
import com.example.browser.view.custom.BrowserView;
import com.example.browser.view.custom.IBrowserView;

public class MainActivity extends Activity implements OnClickListener, OnTabCloseListener{

	private BrowserView mCurrentBrowserView;
	private Button mButtonGoPreviousPage;
	private Button mButtonGoNextPage;
	private Button mButtonAddBookMark;
	private Button mButtonTab;
	private LinearLayout mLinearLayoutWebViewContainer;
	private LinearLayout mLinearLayoutTabs;
	private ListView mListViewTabsList;
	private TabsAdapter mTabsAdapter;
	private Button mButtonNewTab;
	
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
		mButtonNewTab.setOnClickListener(this);
	}

	private void initView() {
		mCurrentBrowserView = new BrowserView(this.getApplicationContext());
		mBrowserViews.add(mCurrentBrowserView);
		
		mLinearLayoutWebViewContainer = (LinearLayout) findViewById(R.id.linear_layout_web_view_container);
		showWebView(mCurrentBrowserView);
		
		
		mButtonGoPreviousPage = (Button) findViewById(R.id.button_go_previous_page);
		mButtonGoNextPage = (Button) findViewById(R.id.button_go_next_page);
		mButtonAddBookMark = (Button) findViewById(R.id.button_go_bookmark);
		mButtonTab = (Button) findViewById(R.id.button_tab);
		mLinearLayoutTabs = (LinearLayout)findViewById(R.id.linear_layout_tab);
		mButtonNewTab = (Button)findViewById(R.id.button_new_tab);
		
		mListViewTabsList = (ListView)findViewById(R.id.list_view_tabs);
		mTabsAdapter = new TabsAdapter(mBrowserViews,getApplicationContext());
		mListViewTabsList.setAdapter(mTabsAdapter);
		
		mListViewTabsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showWebView(mBrowserViews.get(position));
			}
		});
		mTabsAdapter.setOnTabCloseListener(this);
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
		
		case R.id.button_new_tab:
			addNewTab();
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
		mTabsAdapter.notifyDataSetChanged();
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
		if(mLinearLayoutTabs.getVisibility() == View.VISIBLE){
			mLinearLayoutTabs.setVisibility(View.GONE);
		}else{
			mTabsAdapter.notifyDataSetChanged();
			mLinearLayoutTabs.setVisibility(View.VISIBLE);
		}
	}
	
	private void showWebView(BrowserView browserView){
		mCurrentBrowserView = browserView;
		mLinearLayoutWebViewContainer.removeAllViews();
		mLinearLayoutWebViewContainer.addView(browserView);
	}

	@Override
	public void onBackPressed() {
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
	public void onItemClose(int position) {
		if(mBrowserViews.size() == 1){
			mBrowserViews.remove(position);
			BrowserView browserView = new BrowserView(getApplicationContext());
			mBrowserViews.add(browserView);
			showWebView(browserView);
		}else{
			mBrowserViews.remove(position);
			showWebView(mBrowserViews.get(0));
		}
		mTabsAdapter.notifyDataSetChanged();
	}

}
