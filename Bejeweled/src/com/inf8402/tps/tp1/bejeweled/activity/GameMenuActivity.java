package com.inf8402.tps.tp1.bejeweled.activity;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.R.layout;
import com.inf8402.tps.tp1.bejeweled.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GameMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_menu, menu);
		return true;
	}

}
