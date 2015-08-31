package com.example.browser.view.custom;

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
import com.example.browser.util.AutoCompleteWebsiteUtils;

public class WebsiteAutoCompleteAdapter extends BaseAdapter {

	private List<String> mWebsiteList = new ArrayList<String>();
	private LayoutInflater mLayoutInflater;
	private AutoCompleteWebsiteUtils mAutoCompleteWebsiteUtils;
	private OnWebsiteDoneClickListener mOnWebsiteDoneClickListener = new OnWebsiteDoneClickListener() {

		@Override
		public void onWebsiteDoneClick(String website) {
			// do nothing
		}
	};

	public WebsiteAutoCompleteAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
		mAutoCompleteWebsiteUtils = new AutoCompleteWebsiteUtils();
	}

	@Override
	public int getCount() {
		return mWebsiteList.size();
	}

	@Override
	public String getItem(int position) {
		return mWebsiteList.get(position);
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
			convertView = mLayoutInflater.inflate(
					R.layout.item_view_auto_complete_website, null);
			viewHolder.mTextViewWebsite = (TextView) convertView
					.findViewById(R.id.text_view_auto_complete_website);
			viewHolder.mButtonDone = (Button) convertView
					.findViewById(R.id.button_done);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.mTextViewWebsite.setText(mWebsiteList.get(position));
		viewHolder.mButtonDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mOnWebsiteDoneClickListener.onWebsiteDoneClick(mWebsiteList
						.get(position));
			}
		});

		if (position == mWebsiteList.size() - 1) {
			viewHolder.mButtonDone.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.mButtonDone.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	public void updateCompleteContent(String keyword) {
		mWebsiteList = mAutoCompleteWebsiteUtils
				.getAutoCompleteWebsite(keyword);
		notifyDataSetChanged();
	}

	public void setOnWebsiteDoneClickListener(
			OnWebsiteDoneClickListener onWebsiteDoneClickListener) {
		mOnWebsiteDoneClickListener = onWebsiteDoneClickListener;
	}

	class ViewHolder {
		TextView mTextViewWebsite;
		Button mButtonDone;
	}

}
