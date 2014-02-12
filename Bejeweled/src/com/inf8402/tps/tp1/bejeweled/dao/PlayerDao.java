package com.inf8402.tps.tp1.bejeweled.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Gestion de la table Player.
 * 
 * @author jean-michel
 * 
 */
public class PlayerDao extends DAOBase implements IPlayerDAO {
	public static final String PLAYER_KEY = "id";
	public static final String PLAYER_PSEUDO = "pseudo";
	public static final String PLAYER_SCORE_SPEED_MODE = "score_speed_mode";
	public static final String PLAYER_SCORE_TACTIC_MODE = "score_tactic_mode";
	// public static final String PLAYER_RANK = "rank";

	public static final String PLAYER_TABLE_NAME = "Player";

	public PlayerDao(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addPlayer(Player player) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(PLAYER_PSEUDO, player.getPseudo());
		contentValues.put(PLAYER_SCORE_SPEED_MODE, player.getScoreSpeedMode());
		contentValues
				.put(PLAYER_SCORE_TACTIC_MODE, player.getScoreTacticMode());
		sqLiteDatabase.insert(PLAYER_TABLE_NAME, null, contentValues);
	}

	@Override
	public int deletePlayer(int playerID) {
		return sqLiteDatabase.delete(PLAYER_TABLE_NAME, PLAYER_KEY + " = ?",
				new String[] { String.valueOf(playerID) });
	}

	@Override
	public Player findPlayerByID(int playerID) {
		String strQuery = "SELECT * FROM " + PLAYER_TABLE_NAME + " WHERE "
				+ PLAYER_KEY + " = ?";
		Cursor cursor = sqLiteDatabase.rawQuery(strQuery,
				new String[] { String.valueOf(playerID) });

		if (!cursor.moveToFirst()) {
			return null;
		}

		Player player = cursorToPlayer(cursor);

		return player;
	}

	@Override
	public Player findPlayerByPseudo(String pseudo) {
		String strQuery = "SELECT *" + " FROM " + PLAYER_TABLE_NAME + " WHERE "
				+ PLAYER_PSEUDO + " = ?";
		Cursor cursor = sqLiteDatabase.rawQuery(strQuery,
				new String[] { pseudo });

		if (!cursor.moveToFirst()) {
			return null;
		}

		Player player = cursorToPlayer(cursor);

		return player;
	}

	@Override
	public List<Player> getAllPlayersOrderByScoreSpeedMode() {
		String strQuery = "SELECT *" + " FROM " + PLAYER_TABLE_NAME
				+ " ORDER BY " + PLAYER_SCORE_SPEED_MODE + " DESC";
		Cursor cursor = sqLiteDatabase.rawQuery(strQuery, null);
		if (!cursor.moveToFirst()) {
			return null;
		}

		List<Player> playerList = new ArrayList<Player>();

		while (cursor.moveToNext()) {
			Player player = cursorToPlayer(cursor);
			playerList.add(player);
		}

		return playerList;
	}

	@Override
	public List<Player> getTopXPlayersSpeedMode(int xFirstPlayers) {
		String strQuery = "SELECT * FROM " + PLAYER_TABLE_NAME + " ORDER BY "
				+ PLAYER_SCORE_SPEED_MODE + " DESC LIMIT ?";
		Cursor cursor = sqLiteDatabase.rawQuery(strQuery,
				new String[] { String.valueOf(xFirstPlayers) });

		if (!cursor.moveToFirst()) {
			return null;
		}

		List<Player> playerList = new ArrayList<Player>();

		while (cursor.moveToNext()) {
			Player player = cursorToPlayer(cursor);
			playerList.add(player);
		}

		return playerList;
	}

	@Override
	public List<Player> getAllPlayersOrderByScoreTacticMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> getTopXPlayersTacticMode(int xFirstPlayers) {
		// TODO Auto-generated method stub
		return null;
	}

	private Player cursorToPlayer(Cursor cursor) {
		Player player = new Player();
		player.setPlayerID(cursor.getInt(cursor.getColumnIndex(PLAYER_KEY)));
		player.setPseudo(cursor.getString(cursor.getColumnIndex(PLAYER_PSEUDO)));
		player.setScoreSpeedMode(cursor.getInt(cursor
				.getColumnIndex(PLAYER_SCORE_SPEED_MODE)));
		player.setScoreTacticMode(cursor.getInt(cursor
				.getColumnIndex(PLAYER_SCORE_TACTIC_MODE)));

		return player;
	}
}
