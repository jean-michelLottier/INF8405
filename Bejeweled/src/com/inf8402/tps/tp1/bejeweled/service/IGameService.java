package com.inf8402.tps.tp1.bejeweled.service;

import java.util.ArrayList;

import android.os.SystemClock;
import android.widget.Chronometer;

import com.inf8402.tps.tp1.bejeweled.dao.Item;

public interface IGameService {
	public static final int X = 0;
	public static final int Y = 1;
	public static final String NORTH = "north";
	public static final String SOUTH = "south";
	public static final String EAST = "east";
	public static final String WEST = "west";

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
	public ArrayList<Item> moveItem(ArrayList<Item> items, Item item,
			String direction);

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
	public void initializeChrono(long time);

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
	 * Get the duration of the Chronometer
	 * </p>
	 * 
	 * @return The duration
	 */
	public long getLimitChrono();

	/**
	 * <p>
	 * Set the duration of the Chronometer
	 * </p>
	 * 
	 * @param Theduration
	 * 
	 */
	public void setLimitChrono(long time);

	/**
	 * <p>
	 * Get the maximum of moves available
	 * </p>
	 * 
	 * @return the maximum number of moves
	 */
	public int getLimitMove();

	/**
	 * <p>
	 * Set the maximum of moves available
	 * </p>
	 * 
	 * @param the maximum number of moves
	 */
	public void setLimitMove(int limit);

	/**
	 * <p>
	 * Update the score parameters after a move
	 * </p>
	 * 
	 */
	public void moveUpdate();
}
