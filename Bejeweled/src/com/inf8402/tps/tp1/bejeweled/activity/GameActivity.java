package com.inf8402.tps.tp1.bejeweled.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.adapter.GridAdapter;
import com.inf8402.tps.tp1.bejeweled.dao.Item;
import com.inf8402.tps.tp1.bejeweled.service.GameService;
import com.inf8402.tps.tp1.bejeweled.service.IGameService;

/**
 * <p>
 * Main Activity for the game.
 * </p>
 * 
 * @author jean-michel
 * @author Kevin
 * 
 */
public class GameActivity extends Activity {

	public static final String KEY_SPEED_MODE = "speed_mode";
	public static final String KEY_TACTIC_MODE = "tactic_mode";

	private TextView txt_chrono;
	private Chronometer chrono;
	private TextView restant;
	private TextView nbrRestant;
	private TextView txt_chaines;
	private TextView chaines;
	private TextView score;
	private TextView points;
	private TextView bonus;

	private IGameService gameService;
	private ArrayList<Item> items;
	private GridAdapter gridAdapter;
	private float pressedDownX;
	private float pressedDownY;

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

		gameService = getGameService();

		txt_chrono = (TextView) findViewById(R.id.Game_txtchrono);
		chrono = (Chronometer) findViewById(R.id.Game_chrono);
		restant = (TextView) findViewById(R.id.Game_coupsRestants);
		nbrRestant = (TextView) findViewById(R.id.Game_nbrCoupsRestants);
		chaines = (TextView) findViewById(R.id.Game_nbrChaines);
		txt_chaines = (TextView) findViewById(R.id.Game_chaines);
		score = (TextView) findViewById(R.id.Game_score);
		points = (TextView) findViewById(R.id.Game_points);
		bonus = (TextView) findViewById(R.id.Game_bonus);

		Typeface flipbash = Typeface.createFromAsset(getAssets(),
				"fonts/Flipbash.ttf");
		Typeface geminacad = Typeface.createFromAsset(getAssets(),
				"fonts/gemina2acad.ttf");
		Typeface gemina = Typeface.createFromAsset(getAssets(),
				"fonts/gemina2.ttf");
		Typeface tron = Typeface.createFromAsset(getAssets(), "fonts/Tr2n.ttf");

		txt_chrono.setTypeface(geminacad);
		chrono.setTypeface(geminacad);
		txt_chaines.setTypeface(geminacad);
		chaines.setTypeface(geminacad);
		restant.setTypeface(gemina);
		nbrRestant.setTypeface(gemina);
		score.setTypeface(flipbash);
		points.setTypeface(tron);
		bonus.setTypeface(tron);

		points.setTextSize((float) ((float) score.getTextSize() / 1.3));
		bonus.setTextSize((float) ((float) score.getTextSize() / 1.8));
		points.setText(" ");
		bonus.setText(" ");
		score.setText("0");
		chaines.setText("0");
		nbrRestant.setText("40");

		gameService.setLimitMove(40);

		gameService.setChrono(chrono);
		gameService.initializeChrono(10);
		gameService.getChrono().setOnChronometerTickListener(chronoListener);
		gameService.startChrono();

		items = gameService.initGrid();

		GridView gridView = (GridView) findViewById(R.id.gridViewItems);

		// gridAdapter = new GridAdapter(this,
		// items.toArray(new Item[items.size()]));
		gridAdapter = new GridAdapter(this, items);
		gridView.setAdapter(gridAdapter);

		Bundle bundle = getIntent().getExtras();
		boolean isSpeedMode = bundle.getBoolean(KEY_SPEED_MODE, false);
		boolean isTacticMode = bundle.getBoolean(KEY_TACTIC_MODE, false);

		if (isSpeedMode) {

		}

		gridView.setOnTouchListener(onTouchListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	private final OnChronometerTickListener chronoListener = new OnChronometerTickListener() {

		@Override
		public void onChronometerTick(Chronometer chronometer) {
			// TODO Auto-generated method stub
			gameService
					.setTimeChrono((SystemClock.elapsedRealtime() - chronometer
							.getBase()) / 1000);
			int timer = (int) (gameService.getLimitChrono() - gameService
					.getTimeChrono());
			gameService.getChrono().setText(String.valueOf(timer));

			if (gameService.getTimeChrono() >= gameService.getLimitChrono()) {
				gameService.stopChrono();
				endOfGame();
			}

		}

	};

	private final OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			GridView gridView = (GridView) v.findViewById(R.id.gridViewItems);
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (gridView == null) {
					pressedDownX = -1;
					pressedDownY = -1;
				} else {
					pressedDownX = event.getX();
					pressedDownY = event.getY();
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if (gridView == null || pressedDownX == -1
						|| pressedDownY == -1) {
					return true;
				}
				float pressedUpX = event.getX();
				float pressedUpY = event.getY();
				int startPosition = gridView.pointToPosition(
						(int) pressedDownX, (int) pressedDownY);
				int endPosition = gridView.pointToPosition((int) pressedUpX,
						(int) pressedUpY);

				if (endPosition < 0) {
					return true;
				}

				gameService = getGameService();
				Item item = items.get(startPosition);
				ArrayList<Integer> functionF = gameService
						.generateAffineFunction(
								item.getCoordinate().get(GameService.X), item
										.getCoordinate().get(GameService.Y),
								item.getCoordinate().get(GameService.X) + 1,
								item.getCoordinate().get(GameService.Y) - 1);
				ArrayList<Integer> functionG = gameService
						.generateAffineFunction(
								item.getCoordinate().get(GameService.X), item
										.getCoordinate().get(GameService.Y),
								item.getCoordinate().get(GameService.X) + 1,
								item.getCoordinate().get(GameService.Y) + 1);

				item = items.get(endPosition);
				int resultFX = functionF.get(0)
						* item.getCoordinate().get(GameService.X)
						+ functionF.get(1);
				int resultGX = functionG.get(0)
						* item.getCoordinate().get(GameService.X)
						+ functionG.get(1);

				String direction = null;
				if (item.getCoordinate().get(GameService.Y) <= resultFX
						&& item.getCoordinate().get(GameService.Y) <= resultGX) {
					direction = GameService.NORTH;
				} else if (item.getCoordinate().get(GameService.Y) >= resultFX
						&& item.getCoordinate().get(GameService.Y) >= resultGX) {
					direction = GameService.SOUTH;
				} else if (item.getCoordinate().get(GameService.Y) > resultFX
						&& item.getCoordinate().get(GameService.Y) < resultGX) {
					direction = GameService.EAST;
				} else if (item.getCoordinate().get(GameService.Y) < resultFX
						&& item.getCoordinate().get(GameService.Y) > resultGX) {
					direction = GameService.WEST;
				}

				items = gameService.moveItem(items, items.get(startPosition),
						direction);

				if (gameService.hasChain()) {
					points.setText("+" + gameService.getPoints());
					if (gameService.getBonus() > 0)
						bonus.setText("+" + gameService.getBonus());
					score.setText(String.valueOf(gameService.getScore()));
					gameService.moveUpdate();
					nbrRestant.setText(String.valueOf(gameService
							.getLimitMove()));
					chaines.setText(String.valueOf(gameService.getChain()));
					if (gameService.getChain() == gameService.getLimitMove()) {
						endOfGame();
					}
				}
				gridAdapter.notifyDataSetChanged();
			}
			return true;
		}
	};

	// Routine où coder la situation de fin de partie
	private void endOfGame() {

	}
}
