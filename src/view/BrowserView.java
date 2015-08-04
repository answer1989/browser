package view;

import com.example.browser.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class BrowserView extends RelativeLayout{

	public BrowserView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.custom_browser_view, this);

	}
	

	
	

}
