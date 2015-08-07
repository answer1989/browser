package model;

import android.provider.BaseColumns;

public class Bookmark implements BaseColumns{

	public static final String TABLE_NAME = "bookmark";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_WEBSITE = "website";
    
    private long id;
    private String title;
    private String website;
    
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
    
	@Override
	public String toString() {
		return "id " + id + " title " + title + " website " + website;
	}
    
    
}
