package com.scorpioneal.demos.view;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.scorpioneal.demos.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ShimmerView extends Activity{
    private ShimmerTextView tv;
    private Shimmer shimmer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shimmer_view);
        
        tv = (ShimmerTextView)findViewById(R.id.shimmer_tv);
    }
    
    public void toggleAnimation(View target){
        if(shimmer != null && shimmer.isAnimating()){
            shimmer.cancel();
        }else {
            shimmer = new Shimmer();
            shimmer.start(tv);
        }
    }
}
