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
	private int score;
	private int rank;

	public Player() {

	}

	public Player(String pseudo, int score) {
		this.pseudo = pseudo;
		this.score = score;
	}

	public Player(int id, String pseudo, int score) {
		this.playerID = id;
		this.pseudo = pseudo;
		this.score = score;
	}

	public Player(int id, String pseudo, int score, int rank) {
		this.playerID = id;
		this.pseudo = pseudo;
		this.score = score;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
