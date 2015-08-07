package view;

import java.util.List;

import model.Bookmark;

public interface IBookmarkView {
	public void loadBookmark(List<Bookmark> bookmarks);
	
	public void appendMoreBookmark(List<Bookmark> bookmarks);
	
	public void deleteBookmark(Bookmark bookmark,int position);
}
