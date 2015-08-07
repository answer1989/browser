package model;

import java.util.List;


public interface IBookmarkModel {
	
	public boolean isBookmarkExist(String website);
	
	public void deleteBookmark(long bookmarkId);
	
	public List<Bookmark> getlimitedSizeBookMark(long fromId, int size);
	
	public void saveBookmark(Bookmark bookmark);
	
	public void replaceBookmark(Bookmark bookmark);

}
