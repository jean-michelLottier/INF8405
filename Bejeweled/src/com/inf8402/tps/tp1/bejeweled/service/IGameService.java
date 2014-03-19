package com.inf8402.tps.tp1.bejeweled.service;

import java.util.ArrayList;

import android.content.Context;
import android.os.SystemClock;
import android.widget.Chronometer;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.dao.Item;

public interface IGameService {
	public static final int X = 0;
	public static final int Y = 1;
	public static final int GRID_LENGTH = 64;
	public static final int GRID_SIZE = 8;
	public static final String NORTH = "north";
	public static final String SOUTH = "south";
	public static final String EAST = "east";
	public static final String WEST = "west";
	public static final int PAUSE_NORMAL_LAYOUT = R.drawable.pause_normal;
	public static final int PAUSE_SPEED_LAYOUT = R.drawable.pause_speed;
	public static final int PAUSE_TACTIC_LAYOUT = R.drawable.pause_tactic;
	
	public void initGrid();

	/**
	 * <p>
	 * Method to generate an affine function.</br>This method is mainly use to
	 * define different areas (thanks to 2 affine functions) that the item will
	 * be able to permute with the neighbor selected (thanks to direction)
	 * </p>
	 * 
	 * @param xa
	 * @param ya
	 * @param xb
	 * @param yb
	 * @return director coefficient and intercept into arrayList
	 */
	public ArrayList<Integer> generateAffineFunction(int xa, int ya, int xb,
			int yb);

	/**
	 * <p>
	 * Get the total score
	 * </p>
	 * 
	 * @return The total score
	 */
	public int getScore();

	/**
	 * <p>
	 * Get the total number of chains
	 * </p>
	 * 
	 * @return The total number of chains
	 */
	public int getChains();

	/**
	 * <p>
	 * Set a Chronometer
	 * </p>
	 * 
	 */
	public void setChrono(Chronometer m);

	/**
	 * <p>
	 * Get the Chronometer
	 * </p>
	 * 
	 */
	public Chronometer getChrono();

	/**
	 * <p>
	 * Initialize a Chronometer
	 * </p>
	 * 
	 * @param duration
	 *            in milliseconds
	 */
	public void initializeChrono();

	/**
	 * <p>
	 * Pause the Chronometer
	 * </p>
	 * 
	 */
	public void pauseChrono();

	/**
	 * <p>
	 * Stop the Chronometer
	 * </p>
	 * 
	 */
	public void stopChrono();

	/**
	 * <p>
	 * Start the Chronometer
	 * </p>
	 * 
	 */
	public void startChrono();

	/**
	 * <p>
	 * Get the elapsed time since the Chronometer started
	 * </p>
	 * 
	 * @return The elapsed time
	 */
	public long getTimeChrono();

	/**
	 * <p>
	 * Set the current time of the Chronometer
	 * </p>
	 * 
	 * @param The
	 *            current
	 */
	public void setTimeChrono(long time);

	/**
	 * <p>
	 * Get the number of moves available
	 * </p>
	 * 
	 * @return the number of moves available
	 */
	public int getNbrMoveLeft();

	/**
	 * <p>
	 * Get the items of the grid
	 * </p>
	 * 
	 */
	public ArrayList<Item> getGridItems();

	/**
	 * <p>
	 * Set the items of the grid
	 * </p>
	 * 
	 * @params The items of the grid
	 */
	public void setGridItems(ArrayList<Item> i);

	/**
	 * <p>
	 * Get the context of the activityGame
	 * </p>
	 * 
	 * @return The context
	 * 
	 */
	public Context getContext();

	/**
	 * <p>
	 * Set the context of the activity
	 * </p>
	 * 
	 * @params The context
	 * 
	 */
	public void setContext(Context context);

	public boolean isGamePaused();

	public void setGamePaused(boolean gamePaused);

	
	public void reinitialize();

	public void onPause();
	
	public void resumePause();

	public void init();
	
	public void clear();
	public boolean isGameStart();

	public void setGameStart(boolean gameStart);
	//New set of functions
	public boolean switchMove(Item item, int direction);
	public void deleteCombinations();
	public void moveFallingItems();
	public void fillGrid();
	public boolean fullSearchCombination();
	public ArrayList<Integer> getFirstChainPoints();
	public ArrayList<ArrayList<Integer>> getBonusPoints();
	public void setScore(int score);
	public void setChains(int chains);
	public void setNbrMoveLeft(int i);
	public boolean isReinitialized();
	public void setReinitialized(boolean v);
	public boolean isGameQuit();
	public void setGameQuit(boolean gameQuit);
	public void updateBeforeFill();
	public void updateAfterFill();
}
