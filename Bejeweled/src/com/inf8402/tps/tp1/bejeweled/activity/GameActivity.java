package com.inf8402.tps.tp1.bejeweled.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
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
public class GameActivity extends IActivity {

	public static final String KEY_SPEED_MODE = "speed_mode";
	public static final String KEY_TACTIC_MODE = "tactic_mode";
	public static final int LIMIT_MOVE = 10;
	public static final long LIMIT_TIME = 10;
	public static final int V_POINTS = 100;
	public static final int V_BONUS = 50;
	

	private Chronometer chrono;
	private TextView restant;
	private TextView nbrRestant;
	private TextView txt_chaines;
	private TextView chaines;
	private TextView score;
	private TextView points;
	private TextView bonus;
	private TextView txt_chrono;
	private LinearLayout grpChrono;
	private LinearLayout grpCoupsRestants;
	private LinearLayout buttons;
	
	public static IGameService gameService=null;
	private GridAdapter gridAdapter;
	private float pressedDownX;
	private float pressedDownY;

	private boolean isSpeedMode;
	private boolean isTacticMode;
	
	public IGameService getGameService() {
		if (gameService == null) {
			gameService = new GameService(this);
		}
		return gameService;
	}

	public void setGameService(IGameService service) {
		gameService = service;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		

		Bundle bundle = getIntent().getExtras();
		isSpeedMode = bundle.getBoolean(KEY_SPEED_MODE, false);
		isTacticMode = bundle.getBoolean(KEY_TACTIC_MODE, false);
		
		chaines = (TextView) findViewById(R.id.Game_nbrChaines);
		txt_chaines = (TextView) findViewById(R.id.Game_chaines);
		score = (TextView) findViewById(R.id.Game_score);
		points = (TextView) findViewById(R.id.Game_points);
		bonus = (TextView) findViewById(R.id.Game_bonus);
		grpChrono = (LinearLayout) findViewById(R.id.Game_grpChrono);
		txt_chrono = (TextView) findViewById(R.id.Game_txtChrono);
		chrono = (Chronometer) findViewById(R.id.Game_chrono);
		grpCoupsRestants = (LinearLayout) findViewById(R.id.Game_grpCoupsRestants);
		restant = (TextView) findViewById(R.id.Game_coupsRestants);
		nbrRestant = (TextView) findViewById(R.id.Game_nbrCoupsRestants);
		
		Typeface flipbash = Typeface.createFromAsset(getAssets(),
				"fonts/Flipbash.ttf");
		Typeface geminacad = Typeface.createFromAsset(getAssets(),
				"fonts/gemina2acad.ttf");
		Typeface gemina = Typeface.createFromAsset(getAssets(),
				"fonts/gemina2.ttf");
		Typeface tron = Typeface.createFromAsset(getAssets(), "fonts/Tr2n.ttf");

		
		txt_chaines.setTypeface(geminacad);
		chaines.setTypeface(geminacad);
		score.setTypeface(flipbash);
		points.setTypeface(tron);
		bonus.setTypeface(tron);
		chrono.setTypeface(geminacad);
		nbrRestant.setTypeface(gemina);
		restant.setTypeface(gemina);
		txt_chrono.setTypeface(gemina);
		
		
		points.setText(" ");
		bonus.setText(" ");
		chaines.setText("0");
		

		points.setTextSize((float) ((float) score.getTextSize() / 1.3));
		bonus.setTextSize((float) ((float) score.getTextSize() / 1.8));

		chrono.setTextColor(getResources().getColor(R.color.chrono));
		txt_chrono.setTextColor(getResources().getColor(R.color.white));
		restant.setTextColor(getResources().getColor(R.color.white));
		nbrRestant.setTextColor(getResources().getColor(R.color.txt_nbrRestant));
		
		grpChrono.setVisibility(View.GONE);
		grpCoupsRestants.setVisibility(View.GONE);

		restant.setTypeface(gemina);
		
		if(GameActivity.gameService != null)
		{
			gameService = getGameService();
			score.setText(String.valueOf(gameService.getScore()));
			chaines.setText(String.valueOf(gameService.getChain()));
			if (isSpeedMode) {
				grpChrono.setVisibility(View.VISIBLE);
				gameService.setChrono(chrono);
				gameService.getChrono().setOnChronometerTickListener(chronoListener);
				gameService.startChrono();
			}
			else if (isTacticMode)
			{
				grpCoupsRestants.setVisibility(View.VISIBLE);

				nbrRestant.setText(String.valueOf(gameService.getNbrMoveLeft()));
			}
			
		}
		else
		{
			gameService = getGameService();
			score.setText("0");
			chaines.setText("0");
			gameService.setGridItems(gameService.initGrid());
			if (isSpeedMode) {

				grpChrono.setVisibility(View.VISIBLE);
				gameService.setChrono(chrono);
				gameService.initializeChrono();
				gameService.getChrono().setOnChronometerTickListener(chronoListener);
				
				gameService.startChrono();
			}
			else if (isTacticMode)
			{
				grpCoupsRestants.setVisibility(View.VISIBLE);
				
				nbrRestant.setText(String.valueOf(LIMIT_MOVE));
			}
		}

		GridView gridView = (GridView) findViewById(R.id.gridViewItems);

		// gridAdapter = new GridAdapter(this,
		// items.toArray(new Item[items.size()]));
		gridAdapter = new GridAdapter(this, gameService.getGridItems());
		gridView.setAdapter(gridAdapter);
		

		buttons = (LinearLayout) findViewById(R.id.Game_buttons);
		buttons.setOnTouchListener(multipleButtonsListener);

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
			gameService.setTimeChrono((SystemClock.elapsedRealtime() - chronometer
							.getBase()) / 1000);

			long timer = (LIMIT_TIME+1 - gameService.getTimeChrono());
		
			gameService.getChrono().setText(String.valueOf(timer-1));
			
			if (gameService.getTimeChrono() >= LIMIT_TIME) {
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
				Item item = gameService.getGridItems().get(startPosition);
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

				item = gameService.getGridItems().get(endPosition);
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

				ArrayList<Item> items = gameService.moveItem(gameService.getGridItems(), gameService.getGridItems().get(startPosition),
						direction);
				gameService.setGridItems(items);
				
				if (gameService.hasChain()) {
					points.setText("+" + gameService.getPoints());
					if (gameService.getBonus() > 0)
						bonus.setText("+" + gameService.getBonus());
					score.setText(String.valueOf(gameService.getScore()));
					gameService.moveUpdate();
					if(isTacticMode)
						nbrRestant.setText(String.valueOf(gameService.getNbrMoveLeft()));
					chaines.setText(String.valueOf(gameService.getChain()));
					if (gameService.getChain() == LIMIT_MOVE) {
						endOfGame();
					}
				}

				items = gameService.replaceItemsDeleted(items);
				gridAdapter.notifyDataSetChanged();
				
				boolean isNewCombinationCreated = true;
				while (isNewCombinationCreated) {
					items = gameService.researchCombinationIntoGrid(items);
					gridAdapter.notifyDataSetChanged();

					if (!gameService.isCombinationFound()) {
						isNewCombinationCreated = false;
					} else {
						items = gameService.replaceItemsDeleted(items);
						gridAdapter.notifyDataSetChanged();
					}
				}
			}
			return true;
		}
	};

	// Routine où coder la situation de fin de partie
	private void endOfGame() {

	}

	@Override
	void buttonManager(int id) {
		// TODO Auto-generated method stub

		switch (id) {
		case R.id.Game_pause:
			
			break;
		case R.id.Game_quitter:
			
			break;
		case R.id.Game_recommencer:
			
			break;
		default:
			break;
		}
	}
}
