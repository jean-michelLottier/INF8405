package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.service.MenuService;

public class GameModeActivity extends IActivity {

	private ImageView button_return;

	private LinearLayout buttons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		menuService = new MenuService(this);
		setContentView(R.layout.activity_game_mode);
		DialogFragment dialog = new GameDialogFragment();
		Bundle args = new Bundle();
		args.putInt(GameDialogFragment.BOX_DIALOG_KEY,
				GameDialogFragment.BOX_DIALOG_REGISTER);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "GameDialogFragment");

		buttons = (LinearLayout) findViewById(R.id.modeButtonsLayout);
		buttons.setOnTouchListener(multipleButtonsListener);

		button_return = (ImageView) findViewById(R.id.boutonMode_retour);
		button_return.setOnTouchListener(singleButtonListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.game_mode, menu);
		return true;
	}

	@Override
	void buttonManager(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case R.id.boutonMode_vitesse:
			menuService.goSpeedMode();
			GameActivity.gameService = null;
			break;
		case R.id.boutonMode_tactique:
			menuService.goTacticMode();
			GameActivity.gameService = null;
			break;
		case R.id.boutonMode_retour:
			menuService.initSession();
			menuService.quitSession();
			menuService.goBackFromMode();
			break;
		default:
			break;
		}
	}

}
