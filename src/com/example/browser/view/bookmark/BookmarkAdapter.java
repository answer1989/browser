package com.example.browser.view.bookmark;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.browser.R;
import com.example.browser.model.bean.Bookmark;

public class BookmarkAdapter extends BaseAdapter{

	private List<Bookmark> mBookmarkList = new ArrayList<Bookmark>();
	private LayoutInflater mLayoutInflater;
	private Context mContext;
	private OnBookmarkDeleteListener mOnBookmarkDeleteListener;

	public BookmarkAdapter(Context context) {
		mContext = context.getApplicationContext();
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	public void setOnBookmarkDeleteListener(OnBookmarkDeleteListener onBookmarkDeleteListener){
		mOnBookmarkDeleteListener = onBookmarkDeleteListener;
	}

	public void addBookmark(List<Bookmark> bookmarks) {
		mBookmarkList.addAll(bookmarks);
		notifyDataSetChanged();
	}
	
	public void deleteBookmark(int position){
		mBookmarkList.remove(position);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mBookmarkList.size();
	}

	@Override
	public Bookmark getItem(int position) {
		// TODO Auto-generated method stub
		return mBookmarkList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_view_bookmark, null);
			viewHolder = new ViewHolder();
			viewHolder.textViewTitle = (TextView)convertView.findViewById(R.id.text_view_title);
			viewHolder.textViewWebsite = (TextView)convertView.findViewById(R.id.text_view_website);
			viewHolder.buttonDelete = (Button)convertView.findViewById(R.id.button_delete_bookmark);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.textViewTitle.setText(mBookmarkList.get(position).getTitle());
		viewHolder.textViewWebsite.setText(mBookmarkList.get(position).getWebsite());
		viewHolder.buttonDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnBookmarkDeleteListener != null){
					mOnBookmarkDeleteListener.onBookmarkDelete(mBookmarkList.get(position), position);
				}
			}
		});
		
		return convertView;
	}

		
	class ViewHolder {
		public TextView textViewTitle;
		public TextView textViewWebsite;
		public Button buttonDelete;
	}
}
