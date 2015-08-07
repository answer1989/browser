package model;

import java.util.List;


public interface IBookmarkModel {
	
	public boolean isBookmarkExist(String website);
	
	public void deleteBookmark(int bookmarkId);
	
	public List<Bookmark> getlimitedSizeBookMark(int fromId, int size);
	
	public void saveBookmark(Bookmark bookmark);
	
	public void replaceBookmark(Bookmark bookmark);

}
