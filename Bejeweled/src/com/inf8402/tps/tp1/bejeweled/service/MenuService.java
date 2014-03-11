package com.inf8402.tps.tp1.bejeweled.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.inf8402.tps.tp1.bejeweled.activity.GameActivity;
import com.inf8402.tps.tp1.bejeweled.activity.GameDialogFragment;
import com.inf8402.tps.tp1.bejeweled.activity.GameModeActivity;
import com.inf8402.tps.tp1.bejeweled.activity.GameScoreActivity;
import com.inf8402.tps.tp1.bejeweled.dao.IPlayerDAO;
import com.inf8402.tps.tp1.bejeweled.dao.Player;
import com.inf8402.tps.tp1.bejeweled.dao.PlayerDao;
import com.inf8402.tps.tp1.bejeweled.dao.SessionManager;
import com.inf8402.tps.tp1.bejeweled.exception.BadInputParameterException;

public class MenuService implements IMenuService {

	private final int TOP_TEN = 10;
	private IPlayerDAO playerDAO;
	private final Context context;
	private Intent intent = null;
	public static SessionManager session;
	public FragmentActivity activity;

	public MenuService(Context context) {
		this.context = context;
		activity = (FragmentActivity) context;
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
	public List<Player> getTopTenPlayers(int mode)
			throws BadInputParameterException {
		switch (mode) {
		case SPEED_MODE:
			return getTopTenPlayersSpeedMode();
		case TACTIC_MODE:
			return getTopTenPlayersTacticMode();
		default:
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

	@Override
	public void initSession() {
		session = new SessionManager(context);
	}

	@Override
	public void quitSession() {
		session.clearSession();
	}

	@Override
	public void goQuit(Intent intent) {
		GameDialogFragment dialog = new GameDialogFragment();
		Bundle args = new Bundle();
		args.putInt(GameDialogFragment.BOX_DIALOG_KEY,
				GameDialogFragment.BOX_DIALOG_QUIT);
		dialog.setArguments(args);
		dialog.setIntentMediaService(intent);
		dialog.show(activity.getFragmentManager(), "GameDialogFragment");
	}

	@Override
	public void goListScores() {
		intent = new Intent(context, GameScoreActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void goPlayGame() {
		intent = new Intent(context, GameModeActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void goBackFromMode() {
		activity.finish();
	}

	@Override
	public void goBackFromScore() {
		activity.finish();
	}

	@Override
	public void goSpeedMode() {
		// TODO Auto-generated method stub
		intent = new Intent(context, GameActivity.class);
		intent.putExtra(GameActivity.KEY_SPEED_MODE, true);
		context.startActivity(intent);

	}

	@Override
	public void goTacticMode() {
		// TODO Auto-generated method stub
		intent = new Intent(context, GameActivity.class);
		intent.putExtra(GameActivity.KEY_TACTIC_MODE, true);
		context.startActivity(intent);
	}

	@Override
	public void initEndOfGameProcedure(boolean isSpeedMode,
			boolean isTacticMode, int score) {
		initSession();
		Player player = session.getPlayerDetails();

		if (player != null) {
			System.out.println("player : " + player.getPseudo() + ", ID : "
					+ player.getPlayerID() + ", speedScore : "
					+ player.getScoreSpeedMode() + ", tacticScore : "
					+ player.getScoreTacticalMode());
		} else {
			System.out.println("!!!!!!!!!! player null !!!!!!!!!!");
		}

		int actualBestScore = 0;
		if (isSpeedMode) {
			actualBestScore = player.getScoreSpeedMode();
			if (score > actualBestScore) {
				session.setPlayerScoreSpeedMode(score);
				player.setScoreSpeedMode(score);
			}
		} else if (isTacticMode) {
			actualBestScore = player.getScoreTacticalMode();
			if (score > actualBestScore) {
				session.setPlayerScoreTacticMode(score);
				player.setScoreTacticalMode(score);
			}
		}

		playerDAO = getPlayerDAO();
		playerDAO.updatePlayer(player);

		GameDialogFragment dialog = new GameDialogFragment();
		Bundle args = new Bundle();
		args.putInt(GameDialogFragment.BOX_DIALOG_KEY,
				GameDialogFragment.BOX_DIALOG_ENDGAME);
		args.putString(GameDialogFragment.BOX_DIALOG_PSEUDO, player.getPseudo());
		args.putInt(GameDialogFragment.BOX_DIALOG_CURRENTSCORE, score);
		if (isSpeedMode) {
			args.putInt(GameDialogFragment.BOX_DIALOG_BESTSCORE,
					player.getScoreSpeedMode());
			args.putInt(GameDialogFragment.BOX_DIALOG_RANK,
					getPlayerRank(player.getPlayerID(), SPEED_MODE));
		} else {
			args.putInt(GameDialogFragment.BOX_DIALOG_BESTSCORE,
					player.getScoreTacticalMode());
			args.putInt(GameDialogFragment.BOX_DIALOG_RANK,
					getPlayerRank(player.getPlayerID(), TACTIC_MODE));
		}

		dialog.setArguments(args);
		dialog.show(activity.getFragmentManager(), "GameDialogFragment");
	}

	@Override
	public int getPlayerRank(int playerID, int typeMode) {
		if (playerID < 0) {
			return 0;
		}

		playerDAO = getPlayerDAO();
		List<Player> players = new ArrayList<Player>();
		if (typeMode == SPEED_MODE) {
			players = playerDAO.getAllPlayersOrderByScoreSpeedMode();
		} else if (typeMode == TACTIC_MODE) {
			players = playerDAO.getAllPlayersOrderByScoreTacticMode();
		} else {
			return 0;
		}

		for (Player player : players) {
			System.out.println("player : " + player.getPseudo());
		}

		if (players.isEmpty()) {
			return 0;
		}

		int rank = 0;
		for (Player player : players) {
			rank++;
			if (player.getPlayerID() == playerID) {
				break;
			}
		}

		return rank;
	}

	@Override
	public void goQuitGame() {
		// TODO Auto-generated method stub
		GameDialogFragment dialog = new GameDialogFragment();
		Bundle args = new Bundle();
		args.putInt(GameDialogFragment.BOX_DIALOG_KEY,
				GameDialogFragment.BOX_DIALOG_QUITGAME);

		dialog.setArguments(args);
		dialog.show(activity.getFragmentManager(), "GameDialogFragment");

	}

	@Override
	public void goRestartGame() {
		// TODO Auto-generated method stub
		GameDialogFragment dialog = new GameDialogFragment();
		Bundle args = new Bundle();
		args.putInt(GameDialogFragment.BOX_DIALOG_KEY,
				GameDialogFragment.BOX_DIALOG_RESTARTGAME);

		dialog.setArguments(args);
		dialog.show(activity.getFragmentManager(), "GameDialogFragment");

	}
}
