package com.inf8402.tps.tp1.bejeweled.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.TypedValue;
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
import android.widget.RelativeLayout;
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

	private int pointsSize = 40;
	
	private Chronometer chrono;
	private TextView restant;
	private TextView nbrRestant;
	private TextView txt_chaines;
	private TextView chaines;
	private TextView score;
	private TextView txt_chrono;
	private ImageView pause;
	private ImageView pause_button;
	private LinearLayout grpChrono;
	private LinearLayout grpCoupsRestants;
	private LinearLayout buttons;
	private GridView gridView;
	private RelativeLayout screenLayout;
	private ArrayList<CustomTextView> textViewList = new ArrayList<CustomTextView>();
	public static IGameService gameService = null;
	private GridAdapter gridAdapter;

	private float pressedDownX;
	private float pressedDownY;

	private boolean isSpeedMode;
	private boolean isTacticMode;
	private boolean isFirstChain;
	private boolean pauseInGame;
	
	private Typeface flipbash;
	private Typeface geminacad;
	private Typeface gemina;
	private Typeface tron;
	private Typeface halftone;

	// Animations
	private int fadeout = R.anim.fade_out;
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

		
		
		
		screenLayout = (RelativeLayout) findViewById(R.id.Game_screen);
		
		pause_button = (ImageView) findViewById(R.id.Game_pause);
		chaines = (TextView) findViewById(R.id.Game_nbrChaines);
		txt_chaines = (TextView) findViewById(R.id.Game_chaines);
		score = (TextView) findViewById(R.id.Game_score);
		grpChrono = (LinearLayout) findViewById(R.id.Game_grpChrono);
		txt_chrono = (TextView) findViewById(R.id.Game_txtChrono);
		chrono = (Chronometer) findViewById(R.id.Game_chrono);
		grpCoupsRestants = (LinearLayout) findViewById(R.id.Game_grpCoupsRestants);
		restant = (TextView) findViewById(R.id.Game_coupsRestants);
		nbrRestant = (TextView) findViewById(R.id.Game_nbrCoupsRestants);

		flipbash = Typeface.createFromAsset(getAssets(),
				"fonts/Flipbash.ttf");
		geminacad = Typeface.createFromAsset(getAssets(),
				"fonts/gemina2acad.ttf");
		gemina = Typeface.createFromAsset(getAssets(),
				"fonts/gemina2.ttf");
		tron = Typeface.createFromAsset(getAssets(), "fonts/Tr2n.ttf");
		halftone = Typeface.createFromAsset(getAssets(), "fonts/halftone.ttf");

		txt_chaines.setTypeface(geminacad);
		chaines.setTypeface(geminacad);
		score.setTypeface(halftone);
		chrono.setTypeface(geminacad);
		nbrRestant.setTypeface(gemina);
		restant.setTypeface(gemina);
		txt_chrono.setTypeface(gemina);


		chrono.setTextColor(getResources().getColor(R.color.chrono));
		txt_chrono.setTextColor(getResources().getColor(R.color.white));
		restant.setTextColor(getResources().getColor(R.color.white));
		nbrRestant
				.setTextColor(getResources().getColor(R.color.txt_nbrRestant));

		grpChrono.setVisibility(View.GONE);
		grpCoupsRestants.setVisibility(View.GONE);

		
		score.setText(String.valueOf(gameService.getScore()));
		chaines.setText(String.valueOf(gameService.getChains()));
		gameService.setChrono(chrono);
		if (isSpeedMode) {
			grpChrono.setVisibility(View.VISIBLE);
			gameService.getChrono().setOnChronometerTickListener(
					chronoListener);
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
		pause = (ImageView) findViewById(R.id.pauselayout);
		if(gameService.isGameStart()){

			if(isSpeedMode)
				pause.setImageResource(IGameService.PAUSE_SPEED_LAYOUT);
			else
				pause.setImageResource(IGameService.PAUSE_TACTIC_LAYOUT);
				
			pause_button.setBackgroundResource(R.drawable.game_start);
			pause_button.requestFocus();
		}
		else{
			pause.setImageResource(IGameService.PAUSE_NORMAL_LAYOUT);
			pause_button.setBackgroundResource(R.drawable.game_play);
			pause_button.requestFocus();
		}
		gridView.setVisibility(View.GONE);
		pause.setVisibility(View.VISIBLE);

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

					if (startPosition < gameService.getGridItems().size() && startPosition>=0) {
						Item item = gameService.getGridItems().get(
								startPosition);
						if (item != null) {
							item.setState(Item.SELECTED);
							gridAdapter.notifyDataSetChanged();
						}
					}

				}
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				
				float pressedUpX = event.getX();
				float pressedUpY = event.getY();
				int startPosition = gridView.pointToPosition(
						(int) pressedDownX, (int) pressedDownY);
				gameService = getGameService();
				if (startPosition >= gameService.getGridItems().size() || startPosition<0) {
					return true;}
				Item item = gameService.getGridItems().get(startPosition);
				item.setState(Item.NORMAL);
				gridAdapter.notifyDataSetChanged();
				
				int endPosition = gridView.pointToPosition((int) pressedUpX,
						(int) pressedUpY);
				if (gridView == null || pressedDownX == -1
						|| pressedDownY == -1) {
					return true;
				}
				if (endPosition < 0) {
					return true;
				}
				ArrayList<Integer> functionF = gameService
						.generateAffineFunction(
								item.getX(), item.getY(),
								item.getX() + 1,
								item.getY() - 1);
				ArrayList<Integer> functionG = gameService
						.generateAffineFunction(
								item.getX(), item.getY(),
								item.getX() + 1,
								item.getY() + 1);

				item = gameService.getGridItems().get(endPosition);
				int resultFX = functionF.get(0)
						* item.getX()
						+ functionF.get(1);
				int resultGX = functionG.get(0)
						* item.getX()
						+ functionG.get(1);

				int direction = 0;
				if (item.getY() <= resultFX
						&& item.getY() <= resultGX) {
					direction = Item.UP;
				} else if (item.getY() >= resultFX
						&& item.getY() >= resultGX) {
					direction = Item.DOWN;
				} else if (item.getY() > resultFX
						&& item.getY() < resultGX) {
					direction = Item.RIGHT;
				} else if (item.getY() < resultFX
						&& item.getY() > resultGX) {
					direction = Item.LEFT;
				}
				
				isFirstChain = true;
				boolean isCombination = false;
				isCombination = gameService.switchMove(gameService.getGridItems().get(startPosition),direction);
				if(isCombination)
				{
					gridView.setOnTouchListener(null);
					Handler h = new Handler();
					h.postDelayed(suppress_phase(), 500);
				}
			}
			return true;
		}
	};
	private Runnable suppress_phase()
	{
		return new Runnable(){

			@Override
			public void run() {
				gameService.deleteCombinations();
				gridAdapter.notifyDataSetChanged();
				clearPointsAnimation();
				if(isFirstChain)
				{
					getPoints();
				}
				getBonus(); // Met isFirstChain a false
				if(!gameService.isGamePaused()){
					displayPointsAnimation();}
				updateScore();
				updateChains();
				gridAdapter.notifyDataSetChanged();
				Handler h = new Handler();
				h.postDelayed(falling_phase(), 500);
				
			}};
	}
	private Runnable falling_phase()
	{
		return new Runnable(){

			@Override
			public void run() {
				gameService.moveFallingItems();
				if(gameService.isGamePaused())
				{
					pauseInGame = true;
					clearPointsAnimation();
				}
				else
				{
					gridAdapter.notifyDataSetChanged();
					Handler h = new Handler();
					h.postDelayed(checkCombination_phase(), 500);
				}
			}};
		
	}
	private Runnable checkCombination_phase()
	{
		return new Runnable(){

			@Override
			public void run() {
				boolean isCombination=false;
				isCombination = gameService.fullSearchCombination();
				Handler h = new Handler();
				if(isCombination)
					h.post(suppress_phase());
				else
				{
					h.post(gridFilling_phase());
				}
			}};
		
	}
	private Runnable gridFilling_phase()
	{
		return new Runnable(){

			@Override
			public void run() {
				gameService.updateBeforeFill();
				gameService.fillGrid();
				gridAdapter.notifyDataSetChanged();
				gameService.updateAfterFill();
				Handler h = new Handler();
				h.post(end_phase());
				
			}};
	}
	private Runnable end_phase()
	{
		return new Runnable(){

			@Override
			public void run() {
				
				if(!isSpeedMode)
					updateMove();
				gridAdapter.notifyDataSetChanged();
				if(gameService.getNbrMoveLeft()==0 && !isSpeedMode)
					endOfGame();
				else
				{
					gridView.setOnTouchListener(onTouchListener);
				}
				
			}};
	}
	// Routine où coder la situation de fin de partie
	private void endOfGame() {
		menuService = getMenuService();
		gameService = getGameService();

		menuService.initEndOfGameProcedure(isSpeedMode, isTacticMode,
				gameService.getScore());
	}
	
	@Override
	void buttonManager(int id) {
		// TODO Auto-generated method stub

		switch (id) {
		case R.id.Game_pause:
			if (!gameService.isGamePaused()) {
				gameService.onPause();
			} else {
				gameService.resumePause();
				if(pauseInGame)
				{
					pauseInGame = false;
					gridAdapter.notifyDataSetChanged();
					Handler h = new Handler();
					h.post(checkCombination_phase());
				}
			}
			break;
		case R.id.Game_quitter:
			if(!gameService.isGamePaused()){
				gameService.onPause();}
			menuService = getMenuService();
			menuService.goQuitGame();
			break;
		case R.id.Game_recommencer:
			if(!gameService.isGamePaused()){
				gameService.onPause();}
			menuService = getMenuService();
			menuService.goRestartGame();
			break;
		default:
			break;
		}
	}

	public void getBonus()
	{
		int bonusScore = 0;
		int position = 0;
		int debut=0;
		if(isFirstChain)
		{
			debut = 1;
			isFirstChain = false;
		}
		for(int i=debut;i<gameService.getBonusPoints().size();i++)
		{
			position = gameService.getBonusPoints().get(i).get(0);
			bonusScore = gameService.getBonusPoints().get(i).get(1);
			CustomTextView bonusView = new CustomTextView(this,AnimationUtils.loadAnimation(getApplicationContext(),fadeout));
			bonusView.setTextColor(getResources().getColor(R.color.txt_bonus));
			bonusView.setText("+"+bonusScore);
			
			textViewList.add(bonusView);
			screenLayout.addView(bonusView);
			View v = gridAdapter.getView(position, null, null);
			int[] location = new int[2];
			v.getLocationOnScreen(location);
			
			bonusView.setX(location[0]);
			bonusView.setY(location[1]);
			gameService.setScore(gameService.getScore()+bonusScore);
		}
		
	}
	public void updateMove()
	{
		gameService.setNbrMoveLeft(gameService.getNbrMoveLeft()-1);
		nbrRestant.setText(String.valueOf(gameService.getNbrMoveLeft()));
	}
	public void updateScore()
	{
		score.setText(String.valueOf(gameService.getScore()));
	}
	public void clearPointsAnimation()
	{
		for(CustomTextView customView: textViewList)
		{
			if(customView.isAnimated())
			{
				customView.stopAnimation();
			}
			screenLayout.removeView(customView);
		}
		textViewList.clear();
	}
	public void displayPointsAnimation()
	{
		for(CustomTextView customView: textViewList)
		{
			customView.startAnimation();
		}
	}
	public void updateChains()
	{
		gameService.setChains(gameService.getChains()+gameService.getBonusPoints().size());
		chaines.setText(String.valueOf(gameService.getChains()));
	}
	public void getPoints()
	{
		int points = 0;
		int position = 0;
		position = gameService.getFirstChainPoints().get(0);
		points = gameService.getFirstChainPoints().get(1);
		CustomTextView pointsView = new CustomTextView(this,AnimationUtils.loadAnimation(getApplicationContext(),fadeout));
		pointsView.setText("+"+points);
		pointsView.setTextColor(getResources().getColor(R.color.txt_points));
		
		textViewList.add(pointsView);
		screenLayout.addView(pointsView);
		View v = gridAdapter.getView(position, null, null);
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		
		pointsView.setX(location[0]);
		pointsView.setY(location[1]);
		gameService.setScore(gameService.getScore()+points);
		
	}
	public GridAdapter getGridAdapter() {
		return gridAdapter;
	}

	public void setGridAdapter(GridAdapter gridAdapter) {
		this.gridAdapter = gridAdapter;
	}
	public void reinitialize()
	{
		gameService.reinitialize();
		score.setText(String.valueOf(gameService.getScore()));
		chaines.setText(String.valueOf(gameService.getChains()));
		chrono.setText(String.valueOf((int)(GameActivity.LIMIT_TIME - gameService.getTimeChrono())));
		nbrRestant
		.setText(String.valueOf(gameService.getNbrMoveLeft()));
		gameService.setGamePaused(true);
		gameService.onPause();
		if(isSpeedMode)
			pause.setImageResource(IGameService.PAUSE_SPEED_LAYOUT);
		else
			pause.setImageResource(IGameService.PAUSE_TACTIC_LAYOUT);
			
		pause_button.setBackgroundResource(R.drawable.game_start);
		pause_button.requestFocus();
		gridAdapter = new GridAdapter(this, gameService.getGridItems());
		gridView.setAdapter(gridAdapter);
		
		gridView.setOnTouchListener(onTouchListener);
		gameService.setGameStart(false);
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	    if(!gameService.isGameQuit()){
	    	gameService.onPause();}
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
			gameService.onPause();
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
	public class CustomTextView extends TextView implements AnimationListener{

		private Animation anim;
		public CustomTextView(Context context, Animation animation) {
			super(context);
			this.anim = animation;
			anim.setAnimationListener(this);
			this.setTextSize(TypedValue.COMPLEX_UNIT_SP, pointsSize);
			this.setTypeface(tron);
			this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
	                LayoutParams.WRAP_CONTENT));
			this.setTextColor(getResources().getColor(R.color.txt_points));
			// TODO Auto-generated constructor stub
		}
		public boolean isAnimated()
		{
			return (anim.hasStarted() && !anim.hasEnded());
		}
		
		public void startAnimation()
		{
			this.startAnimation(anim);
		}
		public void stopAnimation()
		{
			anim.cancel();
		}
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			this.setVisibility(View.GONE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
