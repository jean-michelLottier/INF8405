package com.inf8402.tps.tp1.bejeweled.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.adapter.GridAdapter;
import com.inf8402.tps.tp1.bejeweled.dao.Item;
import com.inf8402.tps.tp1.bejeweled.service.GameService;
import com.inf8402.tps.tp1.bejeweled.service.IGameService;
import com.inf8402.tps.tp1.bejeweled.service.IMenuService;
import com.inf8402.tps.tp1.bejeweled.service.MenuService;

/**
 * <p>
 * Main Activity for the game.
 * </p>
 * 
 * @author jean-michel
 * @author Kevin
 * 
 */
public class GameActivity extends IActivity implements KeyListener{

	public static final String KEY_SPEED_MODE = "speed_mode";
	public static final String KEY_TACTIC_MODE = "tactic_mode";
	public static final int LIMIT_MOVE = 10;
	public static final long LIMIT_TIME = 60;
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
	private ImageView pause_button;
	private LinearLayout grpChrono;
	private LinearLayout grpCoupsRestants;
	private LinearLayout buttons;
	private GridView gridView;
	private ImageView button_recommencer;
	private ImageView button_quitter;

	public static IGameService gameService = null;
	private GridAdapter gridAdapter;

	private float pressedDownX;
	private float pressedDownY;

	private boolean isSpeedMode;
	private boolean isTacticMode;
	private boolean isNewCombinationCreated;

	// Animations
	private Animation fadeoutPoints;
	private Animation fadeoutBonus;

	private AnimationListener fadeoutListener = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			if (fadeoutPoints.hasStarted()) {
				points.setVisibility(View.INVISIBLE);
			}
			if (fadeoutBonus.hasStarted()) {
				bonus.setVisibility(View.INVISIBLE);
			}
			animation.reset();

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

		}

	};

	// ---------

	public IGameService getGameService() {
		if (gameService == null) {
			gameService = new GameService(this,isSpeedMode);
		}
		return gameService;
	}

	public void setGameService(IGameService service) {
		gameService = service;
	}

	public IMenuService getMenuService() {
		if (menuService == null) {
			menuService = new MenuService(this);
		}
		return menuService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		Bundle bundle = getIntent().getExtras();
		isSpeedMode = bundle.getBoolean(KEY_SPEED_MODE, false);
		isTacticMode = bundle.getBoolean(KEY_TACTIC_MODE, false);
		
		gameService = getGameService();
		gameService.setContext(this);
		
		// Animations
		fadeoutPoints = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_out);
		fadeoutPoints.setAnimationListener(fadeoutListener);
		fadeoutBonus = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_out);
		fadeoutBonus.setAnimationListener(fadeoutListener);

		// --------

		button_recommencer = (ImageView) findViewById(R.id.Game_recommencer);
		button_quitter = (ImageView) findViewById(R.id.Game_quitter);
		pause_button = (ImageView) findViewById(R.id.Game_pause);
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
			
		points.setVisibility(View.INVISIBLE);
		bonus.setVisibility(View.INVISIBLE);


		chrono.setTextColor(getResources().getColor(R.color.chrono));
		txt_chrono.setTextColor(getResources().getColor(R.color.white));
		restant.setTextColor(getResources().getColor(R.color.white));
		nbrRestant
				.setTextColor(getResources().getColor(R.color.txt_nbrRestant));

		grpChrono.setVisibility(View.GONE);
		grpCoupsRestants.setVisibility(View.GONE);

		
		score.setText(String.valueOf(gameService.getScore()));
		chaines.setText(String.valueOf(gameService.getChain()));
		gameService.setChrono(chrono);
		if (isSpeedMode) {
			grpChrono.setVisibility(View.VISIBLE);
			gameService.getChrono().setOnChronometerTickListener(
					chronoListener);
			gameService.startChrono();
			chrono.setText(String.valueOf((int)(LIMIT_TIME - gameService.getTimeChrono())));
			gameService.getChrono().setText(String.valueOf((int)(LIMIT_TIME - gameService.getTimeChrono())));
		} else if (isTacticMode) {
			grpCoupsRestants.setVisibility(View.VISIBLE);

			nbrRestant
					.setText(String.valueOf(gameService.getNbrMoveLeft()));
		}

		gridView = (GridView) findViewById(R.id.gridViewItems);

		
		//Mettre le jeu en Pause
		gameService.pauseChrono();
		gameService.setGamePaused(true);
		ImageView pause = (ImageView) findViewById(R.id.pauselayout);
		if(gameService.isGameStart()){

			if(isSpeedMode)
				pause.setImageResource(IGameService.PAUSE_SPEED_LAYOUT);
			else
				pause.setImageResource(IGameService.PAUSE_TACTIC_LAYOUT);
				
			pause_button.setBackgroundResource(R.drawable.game_start);
		}
		else{
			pause.setImageResource(IGameService.PAUSE_NORMAL_LAYOUT);
			pause_button.setBackgroundResource(R.drawable.game_play);
		}
		gridView.setVisibility(View.GONE);
		pause.setVisibility(View.VISIBLE);
		button_recommencer.setImageResource(R.color.transparent);
		button_recommencer.getBackground().setColorFilter(R.color.black_shadow, PorterDuff.Mode.SRC_OVER);
		button_quitter.setImageResource(R.color.transparent);
		button_quitter.getBackground().setColorFilter(R.color.black_shadow, PorterDuff.Mode.SRC_OVER );

		gridAdapter = new GridAdapter(this, gameService.getGridItems());
		gridView.setAdapter(gridAdapter);

		buttons = (LinearLayout) findViewById(R.id.Game_buttons);
		buttons.setOnTouchListener(multipleButtonsListener);
		
		gridView.setOnTouchListener(onTouchListener);
		if(gameService.isGameStart()){
			gameService.setGameStart(false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	private final OnChronometerTickListener chronoListener = new OnChronometerTickListener() {

		@Override
		public void onChronometerTick(Chronometer chronometer) {
			// TODO Auto-generated method stub
			gameService
					.setTimeChrono((SystemClock.elapsedRealtime() - chronometer
							.getBase()) / 1000);

			long timer = (LIMIT_TIME + 1 - gameService.getTimeChrono());

			gameService.getChrono().setText(String.valueOf(timer - 1));

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

					gameService = getGameService();
					int startPosition = gridView.pointToPosition(
							(int) pressedDownX, (int) pressedDownY);

					if (startPosition <= gameService.getGridItems().size()) {
						Item item = gameService.getGridItems().get(
								startPosition);
						if (item != null) {
							item.setState(Item.SELECTED);
							gridAdapter.notifyDataSetChanged();
						}
					}

				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if (gridView == null || pressedDownX == -1
						|| pressedDownY == -1) {
					return false;
				}
				float pressedUpX = event.getX();
				float pressedUpY = event.getY();
				int startPosition = gridView.pointToPosition(
						(int) pressedDownX, (int) pressedDownY);
				int endPosition = gridView.pointToPosition((int) pressedUpX,
						(int) pressedUpY);

				if (endPosition < 0) {
					return false;
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

				gameService.moveItem(gameService.getGridItems().get(startPosition),
						direction);
				gridAdapter.notifyDataSetChanged();
				
				Handler h = new Handler();
				h.postDelayed(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (gameService.hasChain()) {
							points.setText("+" + gameService.getPoints());
							points.setVisibility(View.VISIBLE);
							points.startAnimation(fadeoutPoints);
						}

						gameService.replaceItemsDeleted();
						gridAdapter.notifyDataSetChanged();

						isNewCombinationCreated = true;
						
						verification();
						
						if (gameService.hasChain()) 
						{
							if (gameService.getBonus() > 0)
							{
								bonus.setText("+" + gameService.getBonus());
								bonus.setVisibility(View.VISIBLE);
								bonus.startAnimation(fadeoutBonus);
							}
							score.setText(String.valueOf(gameService.getScore()));

							gameService.moveUpdate();
							if (isTacticMode)
								nbrRestant.setText(String.valueOf(gameService
										.getNbrMoveLeft()));
							chaines.setText(String.valueOf(gameService.getChain()));
							if (gameService.getChain() == LIMIT_MOVE) {
								endOfGame();
							}
						}
					}
					
				}, 500);
				
				
			}
			return true;
		}
	};

	private void verification()
	{
		if(isNewCombinationCreated) {

			gameService.researchCombinationIntoGrid();
			gridAdapter.notifyDataSetChanged();
			
			if (!gameService.isCombinationFound()) {
				isNewCombinationCreated = false;
			} else {

				Handler h = new Handler();
				h.postDelayed(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						gameService.replaceItemsDeleted();
						gridAdapter.notifyDataSetChanged();
						verification();
					}
					
				},500);
			}
		}
		
		
	}
	// Routine où coder la situation de fin de partie
	private void endOfGame() {
		menuService = getMenuService();
		gameService = getGameService();

		menuService.initEndOfGameProcedure(isSpeedMode, isTacticMode,
				gameService.getScore());
	}

	private void pause(){
		gameService.setGamePaused(true);
		gameService.onPause();
		button_recommencer.setImageResource(R.color.transparent);
		button_recommencer.getBackground().setColorFilter(R.color.black_shadow, PorterDuff.Mode.SRC_OVER );
		button_quitter.setImageResource(R.color.transparent);
		button_quitter.getBackground().setColorFilter(R.color.black_shadow, PorterDuff.Mode.SRC_OVER );
		pause_button.setBackgroundResource(R.drawable.game_play);
	}
	private void resumePause(){

	    gameService.setGamePaused(false);
		gameService.resumePause();
		button_recommencer.setImageResource(R.drawable.button_menu);
		button_recommencer.getBackground().clearColorFilter();
		button_quitter.setImageResource(R.drawable.button_menu);
		button_quitter.getBackground().clearColorFilter();
		pause_button.setBackgroundResource(R.drawable.game_pause);
	}
	@Override
	void buttonManager(int id) {
		// TODO Auto-generated method stub

		switch (id) {
		case R.id.Game_pause:
			if (!gameService.isGamePaused()) {
				pause();
			} else {
				resumePause();
			}
			break;
		case R.id.Game_quitter:
			if (!gameService.isGamePaused()) {
				gameService.onPause();
				menuService = getMenuService();
				menuService.goQuitGame();
			}
			break;
		case R.id.Game_recommencer:
			if (!gameService.isGamePaused()) {
				gameService.onPause();
				menuService = getMenuService();
				menuService.goRestartGame();
			}
			break;
		default:
			break;
		}
	}

	public GridAdapter getGridAdapter() {
		return gridAdapter;
	}

	public void setGridAdapter(GridAdapter gridAdapter) {
		this.gridAdapter = gridAdapter;
	}
	@Override
	public void onPause() {
	    super.onPause(); 
	    pause();
	}
	
	@Override
	public void clearMetaKeyState(View view, Editable content, int states) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInputType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean onKeyDown(View view, Editable text, int keyCode,
			KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_APP_SWITCH) {
			pause();
			return true;
		}
		// TODO Auto-generated method stub
	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyOther(View view, Editable text, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onBackPressed() {
		gameService.onPause();
		menuService = getMenuService();
		menuService.goQuitGame();
	return;
	}
}
