package com.inf8402.tps.tp1.bejeweled.dao;

import java.util.List;

public interface IPlayerDAO {
	/**
	 * <p>
	 * Add a player into databases.
	 * </p>
	 * @param player
	 */
	public void addPlayer(Player player);

	/**
	 * <p>
	 * Remove a player from his id.
	 * </p>
	 * @param playerID
	 * @return Number of rows deleted
	 */
	public int deletePlayer(int playerID);

	/**
	 * <p>Find a player by his id.</p>
	 * @param playerID
	 * @return player found otherwise null
	 */
	public Player findPlayerByID(int playerID);

	/**
	 * <p>Find a player by his pseudo.</p>
	 * @param pseudo
	 * @return player found otherwise null
	 */
	public Player findPlayerByPseudo(String pseudo);

	/**
	 * <p>Get all player registered into databases order by score.</p>
	 * @return list of players otherwise null
	 */
	public List<Player> getAllPlayersOrderByScore();

	/**
	 * <p>Get list of X best players.</p>
	 * @param xFirstPlayers
	 * @return list of players otherwise null
	 */
	public List<Player> getTopXPlayers(int xFirstPlayers);
}
