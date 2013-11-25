
package com.scorpioneal.demos.view;

import com.scorpioneal.demos.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class MyToast
{

    private Context mContext;
    private WindowManager mWM;
    private WindowManager.LayoutParams mParams;
    private boolean mIsVisible = false;
    private static final int FLOAT_VIEW_DISPLAY_TIME = 2000;
    private TextView mTextView;
    private RelativeLayout mRelativeLayout;

    public MyToast(Context mContext)
    {
        this.mContext = mContext;
        mWM = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // mParams.width = Utility.dp2px(mContext, 550);
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        mParams.gravity = Gravity.CENTER_HORIZONTAL;
        mParams.gravity = Gravity.BOTTOM;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;

        mTextView = new TextView(mContext);
        mTextView.setBackgroundResource(R.drawable.toast);
        mTextView
                .setLayoutParams(new android.view.ViewGroup.LayoutParams(
                                                                         android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                         android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        mTextView.setTextColor(Color.WHITE);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(40);

        mRelativeLayout = new RelativeLayout(mContext);
        mRelativeLayout.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                                         RelativeLayout.LayoutParams.WRAP_CONTENT));
        mRelativeLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mRelativeLayout.addView(mTextView);

    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable()
    {

        @Override
        public void run()
        {
            if (mIsVisible)
            {
                mWM.removeView(mRelativeLayout);
                mIsVisible = false;
            }
        }
    };

    public void show(String text)
    {
        mTextView.setText("  " + text + "  ");
        if (!mIsVisible)
        {
            mWM.addView(mRelativeLayout, mParams);
        }
        mIsVisible = true;
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, FLOAT_VIEW_DISPLAY_TIME);
    }
}
