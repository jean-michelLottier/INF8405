package com.inf8402.tps.tp1.bejeweled.dao;

/**
 * <p>
 * Objet regroupant toutes les informations en relation avec le
 * joueur.
 * </p>
 * @author jean-michel
 *
 */
public class Player {
	private Long playerID;
	private String pseudo;
	private Long score;
	private Long rank;
	
	public Long getPlayerID() {
		return playerID;
	}
	public void setPlayerID(Long playerID) {
		this.playerID = playerID;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
}
