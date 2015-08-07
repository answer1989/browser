package model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BookmarkModel implements IBookmarkModel {

	private SQLiteDatabase mSqLiteDatabase;

	public BookmarkModel() {
		mSqLiteDatabase = BrowserDatabaseHelper.getInstance()
				.getWritableDatabase();
	}

	@Override
	public boolean isBookmarkExist(String website) {
		String projection[] = { Bookmark.COLUMN_NAME_WEBSITE };
		String selectionArgs[] = {website};
		Cursor cursor = mSqLiteDatabase.query(Bookmark.TABLE_NAME, projection,
				Bookmark.COLUMN_NAME_WEBSITE + "=?", selectionArgs, null, null, null);

		if (cursor != null && cursor.getCount() > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void deleteBookmark(long bookmarkId) {
		String selection = Bookmark._ID + " = ?";
		String args[] = { String.valueOf(bookmarkId) };
		mSqLiteDatabase.delete(Bookmark.TABLE_NAME, selection, args);
	}

	@Override
	public List<Bookmark> getlimitedSizeBookMark(long fromId, int size) {
		List<Bookmark> bookmarkList = new ArrayList<Bookmark>();

		String projection[] = { Bookmark._ID, Bookmark.COLUMN_NAME_TITLE,
				Bookmark.COLUMN_NAME_WEBSITE };

		String whereSelection = Bookmark._ID + " > ?";

		String whereArgs[] = { String.valueOf(fromId) };

		Cursor cursor = mSqLiteDatabase.query(Bookmark.TABLE_NAME, projection,
				whereSelection, whereArgs, null, null, null,
				String.valueOf(size));

		if (cursor != null && cursor.getCount() != 0) {
			while (cursor.moveToNext()) {
				Bookmark bookmark = new Bookmark();
				
				long id = cursor.getLong(cursor
						.getColumnIndexOrThrow(Bookmark._ID));
				String bookmarkTitle = cursor.getString(cursor.getColumnIndexOrThrow(Bookmark.COLUMN_NAME_TITLE));
				String bookmarkWebsite = cursor.getString(cursor.getColumnIndexOrThrow(Bookmark.COLUMN_NAME_WEBSITE));
				
				bookmark.setId(id);
				bookmark.setTitle(bookmarkTitle);
				bookmark.setWebsite(bookmarkWebsite);
				
				bookmarkList.add(bookmark);
			}
		}
		return bookmarkList;
	}

	@Override
	public void saveBookmark(Bookmark bookmark) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(Bookmark.COLUMN_NAME_TITLE, bookmark.getTitle());
		contentValues.put(Bookmark.COLUMN_NAME_WEBSITE, bookmark.getWebsite());
		mSqLiteDatabase.insert(Bookmark.TABLE_NAME, null, contentValues);
	}

	@Override
	public void replaceBookmark(Bookmark bookmark) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(Bookmark.COLUMN_NAME_TITLE, bookmark.getTitle());
		contentValues.put(Bookmark.COLUMN_NAME_WEBSITE, bookmark.getWebsite());
		mSqLiteDatabase.replace(Bookmark.TABLE_NAME, null, contentValues);
	}

}
