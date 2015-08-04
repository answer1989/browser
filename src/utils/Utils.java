package utils;

import org.apache.commons.validator.routines.UrlValidator;


import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public class Utils {

	public static void hideKeyBoard(IBinder windowToken, Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(windowToken, 0);
	}

	public static boolean isValidWebsite(String url) {
		UrlValidator urlValidator = new UrlValidator();
		return urlValidator.isValid(url);
	}

	public static String getBaiduSearchString(String keyword) {
		return "https://www.baidu.com/s?wd=" + keyword;
	}
}
