package com.example.browser.view.main;

import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.browser.R;
import com.example.browser.view.custom.BrowserView;

public class TabsView extends RelativeLayout {
	private List<BrowserView> mTabs;
	private ListView mListViewTabs;
	private Button mButtonNewTab;
	private TabsAdapter mTabsAdapter;
	private OnTabChangeListener mOnTabChangeListener;
	private boolean isShowing = false;
	private boolean isAnimating = false;
	private final static int ANIMATION_DISTANCE_DURATION = 150;
	private final static int ANIMATION_BACKGROUND_DURATION = 60;

	public TabsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.custom_tabs_view, this);

		mListViewTabs = (ListView) findViewById(R.id.list_view_tabs);
		mButtonNewTab = (Button) findViewById(R.id.button_add_tab);

		mTabsAdapter = new TabsAdapter(context);
		mListViewTabs.setAdapter(mTabsAdapter);

		mButtonNewTab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mOnTabChangeListener != null) {
					mOnTabChangeListener.onTabAdd();
				}
			}
		});

		mListViewTabs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mOnTabChangeListener != null) {
					mOnTabChangeListener.onTabSelected(position);
				}
			}
		});

		mTabsAdapter.setOnTabCloseListener(new OnTabChangeListener() {

			@Override
			public void onTabSelected(int position) {

			}

			@Override
			public void onTabClose(int position) {
				if (mOnTabChangeListener != null) {
					mOnTabChangeListener.onTabClose(position);
				}
			}

			@Override
			public void onTabAdd() {

			}
		});

		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}

	public void setTabList(List<BrowserView> tabs) {
		mTabs = tabs;
		mTabsAdapter.setTabs(mTabs);
	}

	public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
		mOnTabChangeListener = onTabChangeListener;
	}

	public void notifyDataSetChanged() {
		mTabsAdapter.notifyDataSetChanged();
	}

	public boolean isShowing() {
		return isShowing;
	}

	public void dismiss() {
		if (isAnimating) {
			return;
		}

		isAnimating = true;
		clearAnimation();

		Integer colorFrom = getResources().getColor(R.color.transparent_dark);
		Integer colorTo = getResources().getColor(R.color.transparent);
		ValueAnimator backgroundAnimation = getBackgroundChangeAnimation(
				colorFrom, colorTo);

		backgroundAnimation.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {

				getTranslationYAnimation(getHeight()).setInterpolator(
						new AccelerateDecelerateInterpolator()).setListener(
						new AnimatorListenerAdapter() {

							@Override
							public void onAnimationEnd(Animator animation) {
								isAnimating = false;
								isShowing = false;
							}

						});

			}

		});

		backgroundAnimation.start();

	}

	public void show() {
		if (isAnimating) {
			return;
		}

		isAnimating = true;

		clearAnimation();

		getTranslationYAnimation(0).setListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationStart(Animator animation) {
				if (getVisibility() != VISIBLE) {
					setVisibility(VISIBLE);
				}
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				isAnimating = false;
				isShowing = true;
				Integer colorFrom = getResources()
						.getColor(R.color.transparent);
				Integer colorTo = getResources().getColor(
						R.color.transparent_dark);
				getBackgroundChangeAnimation(colorFrom, colorTo).start();
			}

		}).start();
	}

	private ViewPropertyAnimator getTranslationYAnimation(int endY) {
		return animate().translationY(endY)
				.setDuration(ANIMATION_DISTANCE_DURATION)
				.setInterpolator(new AccelerateDecelerateInterpolator());
	}

	private ValueAnimator getBackgroundChangeAnimation(Integer startColor,
			Integer endColor) {
		ValueAnimator backgroundAnimation = ValueAnimator.ofObject(
				new ArgbEvaluator(), startColor, endColor);
		backgroundAnimation.setDuration(ANIMATION_BACKGROUND_DURATION);
		backgroundAnimation.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				setBackgroundColor((Integer) animator.getAnimatedValue());
			}

		});

		return backgroundAnimation;
	}

}
