package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import com.inf8402.tps.tp1.bejeweled.R;

public class GameModeActivity extends Activity {

	private ImageView button_speed;
	private ImageView button_tactic;
	private ImageView button_return;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_mode);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.game_mode, menu);
		return true;
	}

}
