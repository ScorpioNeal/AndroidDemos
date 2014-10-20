package com.scorpioneal.demos.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class MyPhoneInfoTest extends Activity {

	private Context mContext = MyPhoneInfoTest.this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView textView = new TextView(mContext);
		textView.setTextSize(15);
		textView.setText(PhoneInfoUtil.getPhoneInfo(MyPhoneInfoTest.this) + "");
		setContentView(textView);
	}
}