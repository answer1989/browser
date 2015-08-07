package com.example.browser.view.bookmark;

import java.util.List;

import com.example.browser.model.bean.Bookmark;



public interface IBookmarkView {
	public void loadBookmark(List<Bookmark> bookmarks);
	
	public void appendMoreBookmark(List<Bookmark> bookmarks);
	
	public void deleteBookmark(Bookmark bookmark,int position);
}
