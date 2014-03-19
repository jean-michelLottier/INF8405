package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.dao.Player;
import com.inf8402.tps.tp1.bejeweled.dao.SessionManager;
import com.inf8402.tps.tp1.bejeweled.exception.BadInputParameterException;
import com.inf8402.tps.tp1.bejeweled.service.GameService;
import com.inf8402.tps.tp1.bejeweled.service.MenuService;

public class GameModeActivity extends IActivity {

	private ImageView button_return;

	private LinearLayout buttons;
	private TextView pseudo;
	private TextView scoreSpeed;
	private TextView scoreTactic;
	private LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		menuService = new MenuService(this);
		setContentView(R.layout.activity_game_mode);
		pseudo = (TextView) findViewById(R.id.Mode_pseudo);
		scoreSpeed = (TextView) findViewById(R.id.Mode_scoreSpeed);
		scoreTactic = (TextView) findViewById(R.id.Mode_scoreTactic);
		TextView txtPseudo = (TextView) findViewById(R.id.Mode_txtPseudo);
		TextView txtScoreSpeed = (TextView) findViewById(R.id.Mode_txtScoreSpeed);
		TextView txtScoreTactic = (TextView) findViewById(R.id.Mode_txtScoreTactic);
		TextView titleScore = (TextView) findViewById(R.id.Mode_titleScore);
		LinearLayout layout = (LinearLayout) findViewById(
				R.id.Mode_infos);
		layout.setVisibility(View.INVISIBLE);
		Typeface gemina = Typeface.createFromAsset(getAssets(),
				"fonts/gemina2.ttf");

		pseudo.setTypeface(gemina);
		scoreSpeed.setTypeface(gemina);
		scoreTactic.setTypeface(gemina);
		txtPseudo.setTypeface(gemina);
		txtScoreSpeed.setTypeface(gemina);
		txtScoreTactic.setTypeface(gemina);
		titleScore.setTypeface(gemina);
		if(!menuService.initSession().hasStarted())
		{
			menuService.goRegisterInit();
			
		}
		else
		{
			SessionManager session = menuService.initSession();
			Player player = session.getPlayerDetails();
			if(player.getPseudo() != null)
			{
				pseudo.setText(" "+player.getPseudo());
				scoreSpeed.setText(" "+player.getScoreSpeedMode());
				scoreTactic.setText(" "+player.getScoreTacticalMode());
				layout.setVisibility(View.VISIBLE);
			}
			
			
			
		}
			
			
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
	protected void onResume()
	{
		SessionManager session = menuService.initSession();
		Player player = session.getPlayerDetails();
		if(player.getPseudo() != null)
		{
			scoreSpeed.setText(" "+player.getScoreSpeedMode());
			scoreTactic.setText(" "+player.getScoreTacticalMode());
		}
		super.onResume();
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
