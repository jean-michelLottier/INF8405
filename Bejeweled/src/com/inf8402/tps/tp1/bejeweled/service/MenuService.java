package com.inf8402.tps.tp1.bejeweled.service;

import java.util.List;

import com.inf8402.tps.tp1.bejeweled.dao.IPlayerDAO;
import com.inf8402.tps.tp1.bejeweled.dao.Player;
import com.inf8402.tps.tp1.bejeweled.exception.BadInputParameterException;

public class MenuService implements IMenuService {

	private final int TOP_TEN = 10;
	private IPlayerDAO playerDAO;

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
		// TODO Auto-generated method stub
		return playerDAO.getTopXPlayersTacticMode(TOP_TEN);
	}

	private List<Player> getTopTenPlayersSpeedMode() {
		// TODO Auto-generated method stub
		return playerDAO.getTopXPlayersSpeedMode(TOP_TEN);
	}

}
