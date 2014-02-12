package com.inf8402.tps.tp1.bejeweled.service;

import java.util.List;

import com.inf8402.tps.tp1.bejeweled.dao.Player;
import com.inf8402.tps.tp1.bejeweled.exception.BadInputParameterException;

public interface IMenuService {
	public static final String SPEED_MODE = "speed_mode";
	public static final String TACTIC_MODE = "tactic_mode";

	public List<Player> getTopTenPlayers(String typeMode) throws BadInputParameterException;
}
