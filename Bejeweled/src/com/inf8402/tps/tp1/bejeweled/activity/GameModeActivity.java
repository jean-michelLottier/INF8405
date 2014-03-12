package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.dao.SessionManager;
import com.inf8402.tps.tp1.bejeweled.service.GameService;
import com.inf8402.tps.tp1.bejeweled.service.MenuService;

public class GameModeActivity extends IActivity {

	private ImageView button_return;

	private LinearLayout buttons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		menuService = new MenuService(this);
		setContentView(R.layout.activity_game_mode);
		/*TextView pseudo = (TextView) findViewById(R.id.Mode_pseudo);
		TextView scoreSpeed = (TextView) findViewById(R.id.Mode_scoreSpeed);
		TextView scoreTactic = (TextView) findViewById(R.id.Mode_scoreTactic);
		Typeface gemina = Typeface.createFromAsset(getAssets(),
				"fonts/gemina2.ttf");*/
		if(!menuService.initSession().hasStarted())
		{
			menuService.goRegisterInit();
		}
		/*SessionManager session = menuService.initSession();
		if(session.getPlayerPseudo() != null)
		{
			pseudo.setText(session.getPlayerPseudo());
			scoreSpeed.setText(session.getPlayerScoreSpeedMode());
			scoreTactic.setText(session.getPlayerPseudo());
		}*/
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == REQUEST_EXIT) {
	         if (resultCode == RESULT_QUIT) {
					menuService.initSession();
		        	menuService.quitSession();
	        	setResult(RESULT_QUIT, null);
	            this.finish();

	         }
	     }
	}

	@Override
	void buttonManager(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case R.id.boutonMode_vitesse:
			menuService.goSpeedMode();
			GameActivity.gameService = new GameService(this,true);
			GameActivity.gameService.init();
			break;
		case R.id.boutonMode_tactique:
			menuService.goTacticMode();
			GameActivity.gameService = new GameService(this,false);
			GameActivity.gameService.init();
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
