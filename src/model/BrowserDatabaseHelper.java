package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BrowserDatabaseHelper extends SQLiteOpenHelper {

	private final static int DATABASE_VERSION = 1;
	private final static String DATABASE_NAME = "browser.db";
	private static final String TEXT_TYPE = " TEXT";
	private static final String UNIQUE = " NOT NULL UNIQUE";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_BOOKMARK_DB = "CREATE TABLE "
			+ Bookmark.TABLE_NAME + " (" + Bookmark._ID
			+ " INTEGER PRIMARY KEY," + Bookmark.COLUMN_NAME_TITLE + TEXT_TYPE + UNIQUE
			+ COMMA_SEP + Bookmark.COLUMN_NAME_WEBSITE + TEXT_TYPE 
			+ " )";

	private static BrowserDatabaseHelper sBrowserDatabaseHelper;

	public static void init(Context context) {
		if (sBrowserDatabaseHelper == null) {
			sBrowserDatabaseHelper = new BrowserDatabaseHelper(
					context.getApplicationContext());
		}
	}

	public static void destroy() {
		if (sBrowserDatabaseHelper != null) {
			sBrowserDatabaseHelper.close();
		}
	}

	public static BrowserDatabaseHelper getInstance() {
		if (sBrowserDatabaseHelper == null) {
			throw new RuntimeException(
					"hava you call init(Context context) in Application onCreate()?");
		}
		return sBrowserDatabaseHelper;
	}

	private BrowserDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_BOOKMARK_DB);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onDowngrade(db, oldVersion, newVersion);
		// TODO
	}

}
