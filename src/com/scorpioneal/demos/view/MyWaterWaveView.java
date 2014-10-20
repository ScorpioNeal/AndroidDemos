package com.scorpioneal.demos.view;

import com.scorpioneal.demos.R;

import android.app.Activity;
import android.os.Bundle;

public class MyWaterWaveView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waterwave_view);

		WaterWaveView waterWaveView = (WaterWaveView) findViewById(R.id.wav);
		waterWaveView.startWave();
	}

}
