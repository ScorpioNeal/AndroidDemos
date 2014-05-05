package com.scorpioneal.demos.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.scorpioneal.demos.R;

public class MyToastView extends Activity
{

    private Button mButton;
    private Context mContext = MyToastView.this;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytoastlayout);
        
        mButton = (Button)findViewById(R.id.toast_btn);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                new MyToast(mContext).show("弹出Toast~");
            }
        });
    }
}
