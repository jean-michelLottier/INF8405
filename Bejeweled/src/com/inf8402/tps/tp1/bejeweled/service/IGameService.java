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
	public static final String NORTH = "north";
	public static final String SOUTH = "south";
	public static final String EAST = "east";
	public static final String WEST = "west";
	public static final int PAUSE_NORMAL_LAYOUT = R.drawable.pause_normal;
	public static final int PAUSE_SPEED_LAYOUT = R.drawable.pause_speed;
	public static final int PAUSE_TACTIC_LAYOUT = R.drawable.pause_tactic;
	
	public ArrayList<Item> initGrid();

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
	 * Exchange item selected with its neighbor (define by the direction).
	 * </p>
	 * 
	 * @param items
	 * @param item
	 * @param direction
	 * @return The list of items modified
	 */
	public void moveItem(Item item,
			String direction);

	/**
	 * <p>
	 * Research all items to delete and permute these one by neighbor above
	 * their. This permutation is made until items to delete have no neighbor.
	 * The last items to delete replaced by new items.
	 * </p>
	 * 
	 * @param items
	 */
	public void replaceItemsDeleted();

	/**
	 * <p>
	 * Get the score obtained with the current move
	 * </p>
	 * 
	 * @return The score
	 */
	public int getPoints();

	/**
	 * <p>
	 * Get the bonus score obtained of the current move
	 * </p>
	 * 
	 * @return The bonus score
	 */
	public int getBonus();

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
	 * Method to know if a move has generated a chain
	 * </p>
	 * 
	 * @return true if there was a chain else false
	 */
	public boolean hasChain();

	/**
	 * <p>
	 * Increment the number of chains
	 * </p>
	 * 
	 */
	public void incrementChain();

	/**
	 * <p>
	 * Get the total number of chains
	 * </p>
	 * 
	 * @return The total number of chains
	 */
	public int getChain();

	/**
	 * <p>
	 * Method to know if the score have changed due to a move
	 * </p>
	 * 
	 * @return true if there was a change else false
	 */
	public boolean hasNewScore();

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
	 * Update the score parameters after a move
	 * </p>
	 * 
	 */
	public void moveUpdate();

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

	public boolean isCombinationFound();

	public boolean isGamePaused();

	public void setGamePaused(boolean gamePaused);

	/**
	 * <p>
	 * Research valid combination into grid and mark items as deleted
	 * </p>
	 * 
	 * @param items
	 * @return
	 */
	public void researchCombinationIntoGrid();

	public void reinitialize();

	public void onPause();
	
	public void resumePause();

	public void init();
	
	public void clear();
	public boolean isGameStart();

	public void setGameStart(boolean gameStart);
}
