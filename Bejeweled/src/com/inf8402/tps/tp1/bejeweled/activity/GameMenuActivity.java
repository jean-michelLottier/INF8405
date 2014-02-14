package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.inf8402.tps.tp1.bejeweled.R;

public class GameMenuActivity extends Activity {

	private ImageView button_play = null;
	private ImageView button_score = null;
	private ImageView button_quit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_menu);

		button_score = (ImageView) findViewById(R.id.boutonMenu_score);
		button_score.setOnClickListener(onClickListener);

		button_play = (ImageView) findViewById(R.id.boutonMenu_jouer);
		button_play.setOnClickListener(onClickListener);

		button_quit = (ImageView) findViewById(R.id.boutonMenu_quitter);
		button_quit.setOnClickListener(onClickListener);
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
			Intent intent = null;
			switch (v.getId()) {
			case R.id.boutonMenu_score:
				intent = new Intent(GameMenuActivity.this,
						GameScoreActivity.class);
				startActivity(intent);
				break;
			case R.id.boutonMenu_jouer:
				intent = new Intent(GameMenuActivity.this,
						GameModeActivity.class);
				startActivity(intent);
				break;
			case R.id.boutonMenu_quitter:
				DialogFragment dialog = new GameDialogFragment();
				Bundle args = new Bundle();
				args.putInt(GameDialogFragment.BOX_DIALOG_KEY,
						GameDialogFragment.BOX_DIALOG_QUIT);
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), "GameDialogFragment");
				break;
			default:
				break;
			}
		}
	};

}
