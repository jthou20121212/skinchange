package com.jthou.skin.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class ViewHelper {

	public static final void setDrawLeftBitmap(Resources resources, int drawble, TextView target) {
		Drawable drawable = resources.getDrawable(drawble);
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		target.setCompoundDrawables(drawable, null, null, null);
	}
	
	public static final void setDrawLeftBitmap(Drawable drawable, TextView target) {
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		target.setCompoundDrawables(drawable, null, null, null);
	}

	public static final void setDrawRightBitmap(Drawable drawable, TextView target) {
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		target.setCompoundDrawables(null, null, drawable, null);
	}

	public static final void setDrawRightBitmap(Resources resources, int resId, TextView target) {
		Drawable drawable = resources.getDrawable(resId);
		// / 这一步必须要做,否则不会显示.
		if (drawable != null)
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		target.setCompoundDrawables(null, null, drawable, null);
	}

	public static final void setDrawTopBitmap(Resources resources, int drawble, TextView target) {
		Drawable drawable = resources.getDrawable(drawble);
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		target.setCompoundDrawables(null, drawable, null, null);
	}
	
	public static final void setDrawTopBitmap(Drawable drawable, TextView target) {
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		target.setCompoundDrawables(null, drawable, null, null);
	}

	public static final void setDrawBottomBitmap(Resources resources, int drawble, TextView target) {
		Drawable drawable = resources.getDrawable(drawble);
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		target.setCompoundDrawables(null, null, null, drawable);
	}
	
	public static final void setDrawBottomBitmap(Drawable drawable, TextView target) {
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		target.setCompoundDrawables(null, null, null, drawable);
	}
	
	public static final void setDrawRightClick(final TextView target, final OnClickListener listener) {
		final int drawablePadding = target.getCompoundDrawablePadding();
		Drawable[] drawables = target.getCompoundDrawables();
		final Drawable drawableTop = drawables[2];
		target.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				if (x > target.getWidth() - (drawableTop.getIntrinsicWidth() + drawablePadding * 2) && x < target.getWidth()) {
					// 认为点击了右侧的删除按钮
					listener.onClick(target);
				}
				return false;
			}
		});
	}
}
