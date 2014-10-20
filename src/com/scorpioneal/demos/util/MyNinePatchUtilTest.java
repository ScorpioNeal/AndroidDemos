package com.scorpioneal.demos.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MyNinePatchUtilTest extends Activity {

	private Context mContext = MyNinePatchUtilTest.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(mContext);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);
		
		Button button = new Button(mContext);
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(300, 300);
		params1.addRule(RelativeLayout.CENTER_IN_PARENT);
		try {
			Drawable drawable = NinePatchUtils.decodeDrawableFromAsset(mContext, "test.9.png");
			button.setBackgroundDrawable(drawable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		layout.addView(button, params1);
		
		setContentView(layout);

	}
}
