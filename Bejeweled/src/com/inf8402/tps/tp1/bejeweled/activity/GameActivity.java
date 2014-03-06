package com.inf8402.tps.tp1.bejeweled.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.GridView;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.adapter.GridAdapter;
import com.inf8402.tps.tp1.bejeweled.dao.Item;
import com.inf8402.tps.tp1.bejeweled.service.GameService;
import com.inf8402.tps.tp1.bejeweled.service.IGameService;

/**
 * <p>
 * Main Activity for the game.
 * </p>
 * 
 * @author jean-michel
 * 
 */
public class GameActivity extends Activity {

	public static final String KEY_SPEED_MODE = "speed_mode";
	public static final String KEY_TACTIC_MODE = "tactic_mode";

	private IGameService gameService;
	private ArrayList<Item> items;
	private GridAdapter gridAdapter;
	private float pressedDownX;
	private float pressedDownY;

	public IGameService getGameService() {
		if (gameService == null) {
			gameService = new GameService();
		}
		return gameService;
	}

	public void setGameService(IGameService gameService) {
		this.gameService = gameService;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		gameService = getGameService();
		items = gameService.initGrid();

		GridView gridView = (GridView) findViewById(R.id.gridViewItems);
		// gridAdapter = new GridAdapter(this,
		// items.toArray(new Item[items.size()]));
		gridAdapter = new GridAdapter(this, items);
		gridView.setAdapter(gridAdapter);

		Bundle bundle = getIntent().getExtras();
		boolean isSpeedMode = bundle.getBoolean(KEY_SPEED_MODE, false);
		boolean isTacticMode = bundle.getBoolean(KEY_TACTIC_MODE, false);

		if (isSpeedMode) {

		}

		gridView.setOnTouchListener(onTouchListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	private final OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			GridView gridView = (GridView) v.findViewById(R.id.gridViewItems);
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (gridView == null) {
					pressedDownX = -1;
					pressedDownY = -1;
				} else {
					pressedDownX = event.getX();
					pressedDownY = event.getY();
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if (gridView == null || pressedDownX == -1
						|| pressedDownY == -1) {
					return false;
				}
				float pressedUpX = event.getX();
				float pressedUpY = event.getY();
				int startPosition = gridView.pointToPosition(
						(int) pressedDownX, (int) pressedDownY);
				int endPosition = gridView.pointToPosition((int) pressedUpX,
						(int) pressedUpY);

				if (endPosition < 0) {
					return false;
				}

				gameService = getGameService();
				Item item = items.get(startPosition);
				ArrayList<Integer> functionF = gameService
						.generateAffineFunction(
								item.getCoordinate().get(GameService.X), item
										.getCoordinate().get(GameService.Y),
								item.getCoordinate().get(GameService.X) + 1,
								item.getCoordinate().get(GameService.Y) - 1);
				ArrayList<Integer> functionG = gameService
						.generateAffineFunction(
								item.getCoordinate().get(GameService.X), item
										.getCoordinate().get(GameService.Y),
								item.getCoordinate().get(GameService.X) + 1,
								item.getCoordinate().get(GameService.Y) + 1);

				item = items.get(endPosition);
				int resultFX = functionF.get(0)
						* item.getCoordinate().get(GameService.X)
						+ functionF.get(1);
				int resultGX = functionG.get(0)
						* item.getCoordinate().get(GameService.X)
						+ functionG.get(1);

				String direction = null;
				if (item.getCoordinate().get(GameService.Y) <= resultFX
						&& item.getCoordinate().get(GameService.Y) <= resultGX) {
					direction = GameService.NORTH;
				} else if (item.getCoordinate().get(GameService.Y) >= resultFX
						&& item.getCoordinate().get(GameService.Y) >= resultGX) {
					direction = GameService.SOUTH;
				} else if (item.getCoordinate().get(GameService.Y) > resultFX
						&& item.getCoordinate().get(GameService.Y) < resultGX) {
					direction = GameService.EAST;
				} else if (item.getCoordinate().get(GameService.Y) < resultFX
						&& item.getCoordinate().get(GameService.Y) > resultGX) {
					direction = GameService.WEST;
				}

				items = gameService.moveItem(items, items.get(startPosition),
						direction);
				gridAdapter.notifyDataSetChanged();
			}
			return false;
		}
	};
}
