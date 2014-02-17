package com.inf8402.tps.tp1.bejeweled.service;

import java.util.List;

import android.content.Context;

import com.inf8402.tps.tp1.bejeweled.dao.IPlayerDAO;
import com.inf8402.tps.tp1.bejeweled.dao.Player;
import com.inf8402.tps.tp1.bejeweled.dao.PlayerDao;
import com.inf8402.tps.tp1.bejeweled.exception.BadInputParameterException;

public class MenuService implements IMenuService {

	private final int TOP_TEN = 10;
	private IPlayerDAO playerDAO;
	private final Context context;

	public MenuService(Context context) {
		this.context = context;
	}

	public IPlayerDAO getPlayerDAO() {
		if (playerDAO == null) {
			playerDAO = new PlayerDao(context);
		}
		return playerDAO;
	}

	public void setPlayerDAO(IPlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
	}

	@Override
	public List<Player> getTopTenPlayers(String typeMode)
			throws BadInputParameterException {

		if (typeMode.equals(SPEED_MODE)) {
			return getTopTenPlayersSpeedMode();
		} else if (typeMode.equals(TACTIC_MODE)) {
			return getTopTenPlayersTacticMode();
		} else {
			throw new BadInputParameterException();
		}
	}

	private List<Player> getTopTenPlayersTacticMode() {
		playerDAO = getPlayerDAO();
		return playerDAO.getTopXPlayersTacticMode(TOP_TEN);
	}

	private List<Player> getTopTenPlayersSpeedMode() {
		playerDAO = getPlayerDAO();
		return playerDAO.getTopXPlayersSpeedMode(TOP_TEN);
	}

	@Override
	public Player startPlayerSession(String pseudo)
			throws BadInputParameterException {
		if (pseudo == null || pseudo.isEmpty() || pseudo.length() < 4) {
			throw new BadInputParameterException(
					"pseudo is null or empty or too short");
		}

		playerDAO = getPlayerDAO();
		Player player = playerDAO.findPlayerByPseudo(pseudo);
		if (player == null) {
			player = new Player(pseudo, 0, 0);
			playerDAO.addPlayer(player);
		}

		return player;
	}

}
