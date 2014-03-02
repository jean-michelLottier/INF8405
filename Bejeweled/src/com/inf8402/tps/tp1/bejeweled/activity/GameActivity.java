package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.service.GameService;
import com.inf8402.tps.tp1.bejeweled.service.IGameService;

/**
 * <p>
 * Main Activity for the game.
 * </p>
 * 
 * @author jean-michel
 * 
 */
public class GameActivity extends Activity {

	public static final String KEY_SPEED_MODE = "speed_mode";
	public static final String KEY_TACTIC_MODE = "tactic_mode";
	
	private IGameService gameService;

	public IGameService getGameService() {
		if (gameService == null) {
			gameService = new GameService();
		}
		return gameService;
	}

	public void setGameService(IGameService gameService) {
		this.gameService = gameService;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
