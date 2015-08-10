package com.example.browser.view.main;

import java.util.List;

import com.example.browser.R;
import com.example.browser.view.custom.BrowserView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TabsAdapter extends BaseAdapter {

	private List<BrowserView> mTabs;
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private OnTabCloseListener mOnTabCloseListener;

	TabsAdapter(List<BrowserView> tabs, Context context) {
		mTabs = tabs;
		mContext = context.getApplicationContext();
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	
	public void setOnTabCloseListener(OnTabCloseListener onTabCloseListener){
		mOnTabCloseListener = onTabCloseListener;
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}

	@Override
	public BrowserView getItem(int position) {
		return mTabs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_view_tab, null);
			viewHolder.mTextViewTabName = (TextView) convertView
					.findViewById(R.id.text_view_tab_name);
			viewHolder.mTextViewTabWebsite = (TextView) convertView
					.findViewById(R.id.text_view_tab_website);
			viewHolder.mButtonClose = (Button) convertView
					.findViewById(R.id.button_close_tab);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mTextViewTabName.setText(mTabs.get(position).getTitle());
		viewHolder.mTextViewTabWebsite
				.setText(mTabs.get(position).getWebsite());
		viewHolder.mButtonClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mOnTabCloseListener != null){
					mOnTabCloseListener.onItemClose(position);
				}
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView mTextViewTabName;
		TextView mTextViewTabWebsite;
		Button mButtonClose;
	}
}
