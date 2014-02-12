package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.inf8402.tps.tp1.bejeweled.R;

public class GameMenuActivity extends Activity {

	private final ImageView button_player = null;
	private ImageView button_score = null;
	private final ImageView button_quit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_menu);

		button_score = (ImageView) findViewById(R.id.boutonMenu_score);
		button_score.setOnClickListener(onClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_menu, menu);
		return true;
	}

	private final OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.boutonMenu_score:
				Intent intent = new Intent(GameMenuActivity.this,
						GameScoreActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	};

}
