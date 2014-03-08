package com.inf8402.tps.tp1.bejeweled.service;

import java.util.List;

import android.content.Intent;

import com.inf8402.tps.tp1.bejeweled.dao.Player;
import com.inf8402.tps.tp1.bejeweled.exception.BadInputParameterException;

public interface IMenuService {
	public static final int SPEED_MODE = 1;
	public static final int TACTIC_MODE = 2;

	/**
	 * <p>
	 * Get top 10 players in speed mode or in tactics mode.
	 * </p>
	 * 
	 * @param typeMode
	 *            - two types possible 'SPEED_MODE' or 'TACTIC_MODE'
	 * @return return a list of players otherwise empty list
	 * @throws BadInputParameterException
	 */
	public List<Player> getTopTenPlayers(int mode)
			throws BadInputParameterException;

	/**
	 * <p>
	 * When a player enter a pseudo this method permit to find informations
	 * thanks to pseudo. If the player does not exist he is automatically
	 * create.
	 * </p>
	 * 
	 * @param pseudo
	 * @return a Player object
	 * @throws BadInputParameterException
	 */
	public Player startPlayerSession(String pseudo)
			throws BadInputParameterException;
	
	public void goQuit(Intent intent);
	
	public void goPlayGame();
	
	public void goSpeedMode();
	
	public void goTacticMode();
	
	public void goListScores();
	
	public void goBackFromMode();
	
	public void goBackFromScore();
	
	public void initSession();
	
	public void quitSession();
	
	
}
