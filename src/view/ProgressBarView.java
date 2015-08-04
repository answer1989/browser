package view;

import com.example.browser.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ProgressBarView extends View {

	private int mProgress = 0;
	private int mProgressColor = Color.BLUE;
	private Paint mPaint;
	private Rect mProgressRect;

	public ProgressBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.progress_bar_view);
		mProgressColor = a.getColor(
				R.styleable.progress_bar_view_progress_color, Color.BLUE);
		a.recycle();
		initAttr();
	}

	private void initAttr() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(mProgressColor);
		mPaint.setStyle(Paint.Style.FILL);
		mProgressRect = new Rect();
	}

	public void setProgress(int progress) {
		mProgress = progress;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mProgressRect.left = 0;
		mProgressRect.top = 0;
		mProgressRect.right = getRight() * mProgress / 100;
		mProgressRect.bottom = getBottom();
		canvas.drawRect(mProgressRect, mPaint);
	}

}
