package com.inf8402.tps.tp1.bejeweled.dao;

/**
 * <p>
 * Objet regroupant toutes les informations en relation avec le joueur.
 * </p>
 * 
 * @author jean-michel
 * 
 */
public class Player {
	private int playerID;
	private String pseudo;
	private int scoreSpeedMode;
	private int scoreTacticalMode;
	private int rank;

	public Player() {

	}

	public Player(String pseudo, int scoreSpeedMode, int scoreTacticalMode) {
		this.pseudo = pseudo;
		this.scoreSpeedMode = scoreSpeedMode;
		this.scoreTacticalMode = scoreTacticalMode;
	}

	public Player(int id, String pseudo, int scoreSpeedMode, int scoreTacticalMode) {
		this.playerID = id;
		this.pseudo = pseudo;
		this.scoreSpeedMode = scoreSpeedMode;
		this.scoreTacticalMode = scoreTacticalMode;
	}

	public Player(int id, String pseudo, int scoreSpeedMode,
			int scoreTacticalMode, int rank) {
		this.playerID = id;
		this.pseudo = pseudo;
		this.scoreSpeedMode = scoreSpeedMode;
		this.scoreTacticalMode = scoreTacticalMode;
		this.rank = rank;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getScoreSpeedMode() {
		return scoreSpeedMode;
	}

	public void setScoreSpeedMode(int scoreSpeedMode) {
		this.scoreSpeedMode = scoreSpeedMode;
	}

	public int getScoreTacticalMode() {
		return scoreTacticalMode;
	}

	public void setScoreTacticalMode(int scoreTacticalMode) {
		this.scoreTacticalMode = scoreTacticalMode;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
}
