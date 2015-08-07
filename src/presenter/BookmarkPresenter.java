package presenter;

import java.util.List;

import view.IBookmarkView;
import model.Bookmark;
import model.BookmarkModel;

public class BookmarkPresenter {
	private BookmarkModel mBookMarkModel;
	private IBookmarkView mBookmarkView;
	private final static int LOAD_SIZE = 20;
	
	public BookmarkPresenter(IBookmarkView bookmarkView){
		mBookmarkView = bookmarkView;
		mBookMarkModel = new BookmarkModel();
	}
	
	
	public void loadBookmark(long fromId){
		List<Bookmark> bookmarks = mBookMarkModel.getlimitedSizeBookMark(fromId, LOAD_SIZE);
		mBookmarkView.loadBookmark(bookmarks);
	}
	
	public void loadMoreBookmark(long fromId){
		List<Bookmark> bookmarks = mBookMarkModel.getlimitedSizeBookMark(fromId, LOAD_SIZE);
		mBookmarkView.appendMoreBookmark(bookmarks);
	}
	
	public void deleteBookmark(Bookmark bookmark,int position){
		mBookMarkModel.deleteBookmark(bookmark.getId());
		mBookmarkView.deleteBookmark(bookmark, position);
	}
}
