package com.example.browser.view.bookmark;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.browser.R;
import com.example.browser.model.bean.Bookmark;
import com.example.browser.presenter.BookmarkPresenter;

public class BookmarkActivity extends Activity implements IBookmarkView {

	private ListView mListViewBookmark;
	private LinearLayout mLinearLayoutEmptyBookmark;
	private Button mButtonBack;
	private BookmarkAdapter mBookmarkAdapter;

	private BookmarkPresenter mBookmarkPresenter;
	private final static long QUERY_START_ID = -1;
	public final static String OPEN_BOOK_MARK = "bookmark";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookmark);

		initView();

		mBookmarkPresenter = new BookmarkPresenter(this);
		mBookmarkPresenter.loadBookmark(QUERY_START_ID);
	}

	private void initView() {

		mLinearLayoutEmptyBookmark = (LinearLayout) findViewById(R.id.linear_empty_bookmark_view);
		
		mButtonBack = (Button)findViewById(R.id.button_go_back);
		mButtonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mListViewBookmark = (ListView) findViewById(R.id.list_view_bookmark);
		mListViewBookmark.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra(OPEN_BOOK_MARK,
						mBookmarkAdapter.getItem(position).getWebsite());
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		mListViewBookmark.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						mBookmarkPresenter.loadMoreBookmark(mBookmarkAdapter
								.getItem(mBookmarkAdapter.getCount() - 1)
								.getId());
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});

		mBookmarkAdapter = new BookmarkAdapter(getApplicationContext());

		mBookmarkAdapter
				.setOnBookmarkDeleteListener(new OnBookmarkDeleteListener() {

					@Override
					public void onBookmarkDelete(Bookmark bookmark, int position) {
						mBookmarkPresenter.deleteBookmark(bookmark, position);

					}
				});

		mListViewBookmark.setAdapter(mBookmarkAdapter);
	}

	
	
	

	@Override
	public void appendMoreBookmark(List<Bookmark> bookmarks) {
		mBookmarkAdapter.addBookmark(bookmarks);
	}

	@Override
	public void deleteBookmark(Bookmark bookmark, int position) {
		mBookmarkAdapter.deleteBookmark(position);
		if(mBookmarkAdapter.getCount() == 0){
			showEmptyView();
		}
	}

	@Override
	public void showEmptyView() {
		mListViewBookmark.setVisibility(View.GONE);
		mLinearLayoutEmptyBookmark.setVisibility(View.VISIBLE);
	}

	@Override
	public void showBookmarkView(List<Bookmark> bookmarks) {
		mListViewBookmark.setVisibility(View.VISIBLE);
		mLinearLayoutEmptyBookmark.setVisibility(View.GONE);
		mBookmarkAdapter.addBookmark(bookmarks);
	}
}
