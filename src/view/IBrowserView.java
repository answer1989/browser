package view;

public interface IBrowserView {

	public void loadWebsite(String website);
	
	public void refresh();
	
	public void goNextPage();
	
	public void goPreviousPage();
	
	public boolean canGoPreviousPage();
	
	public boolean canGoNextPage();
	
}
