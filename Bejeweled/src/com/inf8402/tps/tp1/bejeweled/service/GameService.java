package com.inf8402.tps.tp1.bejeweled.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.activity.GameActivity;
import com.inf8402.tps.tp1.bejeweled.activity.IActivity;
import com.inf8402.tps.tp1.bejeweled.dao.Item;

public class GameService implements IGameService {

	private static final int GRID_LENGTH = 64;
	private ArrayList<Item> itemsGrid;
	private int coupsRestants = GameActivity.LIMIT_MOVE;
	private int chaines = 0;
	private Chronometer timer;
	private boolean hasChain;
	private boolean gamePaused;
	private boolean isCombinationFound;
	private boolean chronoIsRunning;
	private boolean gameStart;
	private int points = 0;
	private int bonus = 0;
	private int score = 0;
	private long stopTime = GameActivity.LIMIT_TIME;
	private long elapsedTime = 0;
	private Context context;
	private boolean isSpeedMode;

	public boolean isGameStart() {
		return gameStart;
	}

	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}

	public GameService(Context c, boolean b) {
		this.setContext(c);
		this.isSpeedMode = b;
	}

	@Override
	public boolean isCombinationFound() {
		return isCombinationFound;
	}

	public void setCombinationFound(boolean isCombinationFound) {
		this.isCombinationFound = isCombinationFound;
	}

	/****** Timer Functions *******/
	public void setChrono(Chronometer m) {
		timer = m;
	}

	public Chronometer getChrono() {
		return timer;
	}

	public void initializeChrono() {
		elapsedTime = 0;
	}

	public void pauseChrono() {
		if (isSpeedMode) {
			timer.stop();
			chronoIsRunning = false;
		}
	}

	public void stopChrono() {
		timer.stop();
		elapsedTime = 0;
	}

	public void startChrono() {
		if (isSpeedMode) {
			timer.setBase(SystemClock.elapsedRealtime() - elapsedTime * 1000);
			timer.start();
			chronoIsRunning = true;
		}
	}

	public long getTimeChrono() {
		return elapsedTime;
	}

	public void setTimeChrono(long time) {
		elapsedTime = time;
	}

	public long getLimitChrono() {
		return stopTime;
	}

	public void setLimitChrono(long time) {
		stopTime = time;
	}

	/*** ----------------------------------- ***/

	public ArrayList<Item> getGridItems() {
		return itemsGrid;
	}

	public void setGridItems(ArrayList<Item> i) {
		itemsGrid = i;
	}

	private static final ArrayList<Integer> itemsID;
	static {
		itemsID = new ArrayList<Integer>();
		itemsID.add(R.drawable.item_eau);
		itemsID.add(R.drawable.item_feu);
		itemsID.add(R.drawable.item_jewel);
		itemsID.add(R.drawable.item_terre);
		itemsID.add(R.drawable.item_vent);
	}

	@Override
	public ArrayList<Item> initGrid() {
		ArrayList<Item> items = new ArrayList<Item>();
		Random random = new Random();
		for (int i = 0; i < GRID_LENGTH; i++) {
			int value = random.nextInt(5);
			ArrayList<Integer> coordinate = new ArrayList<Integer>();

			coordinate.add((i % 8));
			coordinate.add(Math.round(i / 8));

			Item item = new Item(itemsID.get(value), coordinate);
			item = fillNeighborsMap(item);
			items.add(item);

			Combination combination = new Combination();
			combination.addHorizontalAxisMovement(item);
			combination.addVerticalAxisMovement(item);
			combination.addItemVisited(item);
			combination = findCombination(items, item, combination);
			ArrayList<Item> result = combination.getCombination();
			int counter = 0;
			while (!result.isEmpty()) {
				value = (value + 1) % 5;
				items.get(items.size() - 1).setItemID(itemsID.get(value));
				item = items.get(items.size() - 1);
				combination = new Combination();
				combination.addHorizontalAxisMovement(item);
				combination.addVerticalAxisMovement(item);
				combination.addItemVisited(item);
				result = combination.getCombination();
				counter++;
				if (counter == 5)
					break;
			}
		}

		return items;
	}

	@Override
	public ArrayList<Integer> generateAffineFunction(int xa, int ya, int xb,
			int yb) {
		ArrayList<Integer> coefficients = new ArrayList<Integer>();
		int a = (yb - ya) / (xb - xa);
		int b = (xb * ya - xa * yb) / (xb - xa);
		coefficients.add(a);
		coefficients.add(b);

		return coefficients;
	}

	@Override
	public ArrayList<Item> moveItem(ArrayList<Item> items, Item item,
			String direction) {
		if (item == null || direction == null || direction.isEmpty()) {
			return items;
		}

		int positionItem1 = translateCoordByPosition(item.getCoordinate());
		if (item.getNeighbors().get(direction).get(X) == -1
				|| item.getNeighbors().get(direction).get(Y) == -1) {
			return items;
		}
		int positionItem2 = translateCoordByPosition(item.getNeighbors().get(
				direction));
		System.out.println();
		Item item1 = new Item(items.get(positionItem1));
		Item item2 = new Item(items.get(positionItem2));

		item1.setCoordinate(item2.getCoordinate());
		item1 = fillNeighborsMap(item1);
		item2.setCoordinate(item.getCoordinate());
		item2 = fillNeighborsMap(item2);

		items.set(positionItem1, item2);
		items.set(positionItem2, item1);

		Combination combination = new Combination();
		combination.addHorizontalAxisMovement(item2);
		combination.addVerticalAxisMovement(item2);
		combination.addItemVisited(item2);
		// System.out.println("Originitem : " + item2.getItemID() + " - ("
		// + item2.getCoordinate().get(0) + ","
		// + item2.getCoordinate().get(1) + ")");
		combination = findCombination(items, item2, combination);
		ArrayList<Item> result = combination.getCombination();

		int nombre_bonus = 0;
		this.hasChain = false;
		this.points = 0;
		this.bonus = 0;
		if (!result.isEmpty()) {
			nombre_bonus += result.size();
			this.points = GameActivity.V_POINTS;
			this.hasChain = true;
		}

		boolean isCombinationFound = false;
		if (!result.isEmpty()) {
			// System.out.println("***********Combination found***********");
			for (Item current : result) {
				int position = translateCoordByPosition(current.getCoordinate());
				// System.out.println("itemID : " + current.getItemID()
				// + " - position : " + position + " - ("
				// + current.getCoordinate().get(0) + ","
				// + current.getCoordinate().get(1) + ")");
				current.setState(Item.DELETED);
				items.remove(position);
				items.add(position, current);
			}
			isCombinationFound = true;
		}

		combination = new Combination();
		combination.addHorizontalAxisMovement(item1);
		combination.addVerticalAxisMovement(item1);
		combination.addItemVisited(item1);
		combination = findCombination(items, item1, combination);
		result = combination.getCombination();

		if (!result.isEmpty()) {
			nombre_bonus += result.size();
			this.points = GameActivity.V_POINTS;
			this.bonus = (nombre_bonus - 3) * GameActivity.V_BONUS;
			this.hasChain = true;
		}

		if (result.isEmpty() && !isCombinationFound) {
			// System.out.println("***********No combination***********");
			item2.setCoordinate(item1.getCoordinate());
			item2 = fillNeighborsMap(item2);
			item1.setCoordinate(item.getCoordinate());
			item1 = fillNeighborsMap(item1);
			items.set(positionItem1, item1);
			items.set(positionItem2, item2);
		} else {
			for (Item current : result) {
				int position = translateCoordByPosition(current.getCoordinate());
				// System.out.println("itemID : " + current.getItemID()
				// + " - position : " + position + " - ("
				// + current.getCoordinate().get(0) + ","
				// + current.getCoordinate().get(1) + ")");
				current.setState(Item.DELETED);
				items.remove(position);
				items.add(position, current);
			}
		}

		this.score += this.points + this.bonus;

		return items;
	}

	@Override
	public ArrayList<Item> replaceItemsDeleted(ArrayList<Item> items) {
		ArrayMap<Integer, ArrayList<Item>> itemsDeleted = new ArrayMap<Integer, ArrayList<Item>>();
		for (Item item : items) {
			if (item.getState() == Item.DELETED) {
				ArrayList<Integer> coordinate = item.getCoordinate();
				ArrayList<Item> temp = new ArrayList<Item>();
				if (itemsDeleted.containsKey(coordinate.get(X))) {
					temp = itemsDeleted.get(coordinate.get(X));
				}
				temp.add(item);
				Collections.sort(temp, new Comparator<Item>() {

					@Override
					public int compare(Item item1, Item item2) {
						ArrayList<Integer> coordinate1 = item1.getCoordinate();
						ArrayList<Integer> coordinate2 = item2.getCoordinate();

						return coordinate2.get(Y).compareTo(coordinate1.get(Y));
					}
				});

				itemsDeleted.put(coordinate.get(X), temp);
			}
		}

		if (itemsDeleted.isEmpty()) {
			return items;
		}

		boolean isProcessed = true;
		while (isProcessed) {
			int cpt = 0;
			for (int axisX : itemsDeleted.keySet()) {
				ArrayList<Item> itemsToDelete = itemsDeleted.get(axisX);
				if (itemsToDelete.isEmpty()) {
					cpt++;
					continue;
				}
				ArrayList<Item> newItemToDelete = new ArrayList<Item>();

				for (Item item1 : itemsToDelete) {
					ArrayList<Integer> coordinate = (ArrayList<Integer>) item1
							.getCoordinate().clone();
					int position1 = translateCoordByPosition(coordinate);
					int axisY = coordinate.get(Y);
					axisY -= itemsToDelete.size();
					if (axisY >= 0) {
						coordinate.set(Y, axisY);
						int position2 = translateCoordByPosition(coordinate);
						Item item2 = items.get(position2);
						Item temp = new Item(item1);

						item1.setCoordinate(item2.getCoordinate());
						item1 = fillNeighborsMap(item1);
						item2.setCoordinate(temp.getCoordinate());
						item2 = fillNeighborsMap(item2);

						items.set(position1, item2);
						items.set(position2, item1);

						newItemToDelete.add(item1);
					} else {
						item1.setState(Item.NORMAL);
						Random random = new Random();
						int value = random.nextInt(5);
						item1.setItemID(itemsID.get(value));
						items.set(position1, item1);
					}
				}

				itemsDeleted.put(axisX, newItemToDelete);
			}

			if (cpt == itemsDeleted.size()) {
				isProcessed = false;
			}
		}

		return items;
	}

	@Override
	public ArrayList<Item> researchCombinationIntoGrid(ArrayList<Item> items) {
		this.isCombinationFound = false;
		for (Item item : items) {
			Combination combination = new Combination();
			combination.addHorizontalAxisMovement(item);
			combination.addVerticalAxisMovement(item);
			combination.addItemVisited(item);
			combination = findCombination(items, item, combination);
			ArrayList<Item> result = combination.getCombination();

			if (!result.isEmpty()) {
				this.bonus += result.size() * GameActivity.V_BONUS;
			}

			if (!result.isEmpty()) {
				this.isCombinationFound = true;
				for (Item current : result) {
					int position = translateCoordByPosition(current
							.getCoordinate());
					current.setState(Item.DELETED);
					items.set(position, current);
				}
				break;
			}
		}

		return items;
	}

	/*** Score Functions ***/
	public boolean hasNewScore() {
		if (this.bonus + this.points == 0)
			return false;
		else
			return true;
	}

	public boolean hasChain() {
		return this.hasChain;
	}

	public void incrementChain() {
		this.chaines += 1;
	}

	public int getChain() {
		return this.chaines;
	}

	public int getPoints() {
		return this.points;
	}

	public int getBonus() {
		return this.bonus;
	}

	public int getScore() {
		return this.score;
	}

	public int getNbrMoveLeft() {
		return coupsRestants;
	}

	public void moveUpdate() {
		if (hasChain) {
			chaines += 1;
			if (coupsRestants > 0)
				coupsRestants -= 1;
		}
	}

	/** ------------------------ **/

	/**
	 * <p>
	 * This method find and insert neighbors' item put in input parameter.
	 * </p>
	 * 
	 * @param item
	 * @return
	 */
	private Item fillNeighborsMap(Item item) {
		ArrayList<Integer> coordinate = (ArrayList<Integer>) item
				.getCoordinate().clone();
		ArrayMap<String, ArrayList<Integer>> neighbors = new ArrayMap<String, ArrayList<Integer>>();
		int x = coordinate.get(X);
		int y = coordinate.get(Y);
		// NORTH
		coordinate = new ArrayList<Integer>();
		coordinate.add(x);
		if (y != 0) {
			coordinate.add(y - 1);
		} else {
			coordinate.add(-1);
		}
		neighbors.put(NORTH, coordinate);
		// SOUTH
		coordinate = new ArrayList<Integer>();
		coordinate.add(x);
		if (y != 7) {
			coordinate.add(y + 1);
		} else {
			coordinate.add(-1);
		}
		neighbors.put(SOUTH, coordinate);
		// coordinate.add(Y, y);
		// EAST
		coordinate = new ArrayList<Integer>();
		if (x != 7) {
			coordinate.add(x + 1);
		} else {
			coordinate.add(-1);
		}
		coordinate.add(y);
		neighbors.put(EAST, coordinate);
		// WEST
		coordinate = new ArrayList<Integer>();
		if (x != 0) {
			coordinate.add(x - 1);
		} else {
			coordinate.add(-1);
		}
		coordinate.add(y);
		neighbors.put(WEST, coordinate);

		item.setNeighbors(neighbors);

		return item;
	}

	private int translateCoordByPosition(ArrayList<Integer> coordinate) {
		return (coordinate.get(Y) * 8 + coordinate.get(X));
	}

	private Combination findCombination(ArrayList<Item> items, Item item,
			Combination combination) {
		for (String destination : item.getNeighbors().keySet()) {
			ArrayList<Integer> coordinate = item.getNeighbors()
					.get(destination);
			if (coordinate.get(X) == -1 || coordinate.get(Y) == -1) {
				continue;
			}

			int position = translateCoordByPosition(coordinate);
			Item neighborItem = new Item();
			try {
				neighborItem = items.get(position);
			} catch (NullPointerException e) {
				continue;
			} catch (IndexOutOfBoundsException e) {
				continue;
			}

			if (combination.isItemHasAlreadyVisited(neighborItem)) {
				continue;
			}

			if (neighborItem.getItemID() == item.getItemID()) {
				combination.addHorizontalAxisMovement(neighborItem);
				combination.addVerticalAxisMovement(neighborItem);
				combination.addItemVisited(neighborItem);

				combination = findCombination(items, neighborItem, combination);
			}
		}

		return combination;
	}

	private boolean isOppositeDirections(String direction1, String direction2) {
		if (!direction1.equals(NORTH) && !direction1.equals(SOUTH)
				&& !direction1.equals(EAST) && !direction1.equals(WEST)) {
			return false;
		}
		if (!direction2.equals(NORTH) && !direction2.equals(SOUTH)
				&& !direction2.equals(EAST) && !direction2.equals(WEST)) {
			return false;
		}
		if ((direction1.equals(NORTH) && direction2.equals(SOUTH))
				|| (direction2.equals(NORTH) && direction1.equals(SOUTH))) {
			return true;
		}
		if ((direction1.equals(EAST) && direction2.equals(WEST))
				|| (direction2.equals(EAST) && direction1.equals(WEST))) {
			return true;
		}

		return false;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	private class Combination {
		private ArrayMap<Integer, ArrayList<Item>> horizontalAxisMovement;
		private ArrayMap<Integer, ArrayList<Item>> verticalAxisMovement;
		private ArrayList<Item> itemsVisited;

		public ArrayMap<Integer, ArrayList<Item>> getHorizontalAxisMovement() {
			if (horizontalAxisMovement == null) {
				horizontalAxisMovement = new ArrayMap<Integer, ArrayList<Item>>();
			}
			return horizontalAxisMovement;
		}

		public void setHorizontalAxisMovement(
				ArrayMap<Integer, ArrayList<Item>> horizontalAxisMovement) {
			this.horizontalAxisMovement = horizontalAxisMovement;
		}

		public ArrayMap<Integer, ArrayList<Item>> getVerticalAxisMovement() {
			if (verticalAxisMovement == null) {
				verticalAxisMovement = new ArrayMap<Integer, ArrayList<Item>>();
			}
			return verticalAxisMovement;
		}

		public void setVerticalAxisMovement(
				ArrayMap<Integer, ArrayList<Item>> verticalAxisMovement) {
			this.verticalAxisMovement = verticalAxisMovement;
		}

		public ArrayList<Item> getItemsVisited() {
			return itemsVisited;
		}

		public void setItemsVisited(ArrayList<Item> itemsVisited) {
			this.itemsVisited = itemsVisited;
		}

		public boolean isItemHasAlreadyVisited(Item item) {
			if (itemsVisited == null || itemsVisited.isEmpty()) {
				return false;
			}

			for (Item current : itemsVisited) {
				if (current.equals(item)) {
					return true;
				}
			}

			return false;
		}

		public void addItemVisited(Item item) {
			if (itemsVisited == null) {
				itemsVisited = new ArrayList<Item>();
			}
			itemsVisited.add(item);
		}

		public void addHorizontalAxisMovement(Item item) {
			ArrayMap<Integer, ArrayList<Item>> axisMovement = getHorizontalAxisMovement();
			ArrayList<Item> itemsMovement = new ArrayList<Item>();
			ArrayList<Integer> coordinate = item.getCoordinate();
			if (axisMovement.containsKey(coordinate.get(X))) {
				itemsMovement = axisMovement.get(coordinate.get(X));
			}
			itemsMovement.add(item);
			axisMovement.put(coordinate.get(X), itemsMovement);

			horizontalAxisMovement = axisMovement;
		}

		public void addVerticalAxisMovement(Item item) {
			ArrayMap<Integer, ArrayList<Item>> axisMovement = getVerticalAxisMovement();
			ArrayList<Item> itemsMovement = new ArrayList<Item>();
			ArrayList<Integer> coordinate = item.getCoordinate();
			if (axisMovement.containsKey(coordinate.get(Y))) {
				itemsMovement = axisMovement.get(coordinate.get(Y));
			}
			itemsMovement.add(item);
			axisMovement.put(coordinate.get(Y), itemsMovement);

			verticalAxisMovement = axisMovement;
		}

		public ArrayList<Item> getCombination() {
			ArrayList<Item> combination = new ArrayList<Item>();
			ArrayList<Item> movementX = new ArrayList<Item>();
			ArrayList<Item> movementY = new ArrayList<Item>();
			for (int key : horizontalAxisMovement.keySet()) {
				if (movementX.isEmpty()
						|| movementX.size() < horizontalAxisMovement.get(key)
								.size()) {
					movementX.clear();
					movementX.addAll(horizontalAxisMovement.get(key));
				}
			}

			for (int key : verticalAxisMovement.keySet()) {
				if (movementY.isEmpty()
						|| movementY.size() < verticalAxisMovement.get(key)
								.size()) {
					movementY.clear();
					movementY.addAll(verticalAxisMovement.get(key));
				}
			}

			// if ((movementX.size() == 5 && movementY.size() == 3)
			// || (movementX.size() == 3 && movementY.size() == 5)
			// || (movementX.size() == 4 && movementY.size() == 3)
			// || (movementX.size() == 3 && movementY.size() == 4)
			// || (movementX.size() == 3 && movementY.size() == 3)) {
			// combination.addAll(movementX);
			// for (Item current : movementY) {
			// if (!combination.contains(current)) {
			// combination.add(current);
			// }
			// }
			// } else if (movementX.size() == 5 && movementY.size() < 3) {
			// combination.addAll(movementX);
			// } else if (movementX.size() < 3 && movementY.size() == 5) {
			// combination.addAll(movementY);
			// } else if (movementX.size() == 4 && movementY.size() < 3) {
			// combination.addAll(movementX);
			// } else if (movementX.size() < 3 && movementY.size() == 4) {
			// combination.addAll(movementY);
			// } else if (movementX.size() == 3 && movementY.size() < 3) {
			// combination.addAll(movementX);
			// } else if (movementX.size() < 3 && movementY.size() == 3) {
			// combination.addAll(movementY);
			// }

			if ((movementX.size() >= 3 && movementY.size() >= 3)) {
				combination.addAll(movementX);
				for (Item current : movementY) {
					if (!combination.contains(current)) {
						combination.add(current);
					}
				}
			} else if ((movementX.size() >= 3 && movementY.size() < 3)) {
				combination.addAll(movementX);
			} else if ((movementX.size() < 3 && movementY.size() >= 3)) {
				combination.addAll(movementY);
			}
			return combination;
		}
	}

	@Override
	public void reinitialize() {
		gameStart = true;
		chaines = 0;
		hasChain = false;
		gamePaused = false;
		isCombinationFound = false;
		points = 0;
		bonus = 0;
		score = 0;
		elapsedTime = 0;
		//Activity activity = (Activity) context;
		// Chronometer chronometer = (Chronometer)
		// activity.findViewById(R.id.Game_chrono);
		/*TextView nbrRestant = (TextView) activity
				.findViewById(R.id.Game_coupsRestants);
		TextView chaines = (TextView) activity.findViewById(R.id.Game_chaines);
		TextView score = (TextView) activity.findViewById(R.id.Game_score);
		TextView points = (TextView) activity.findViewById(R.id.Game_points);
		TextView bonus = (TextView) activity.findViewById(R.id.Game_bonus);
		nbrRestant.setText(String.valueOf(GameActivity.LIMIT_MOVE));
		chaines.setText("0");
		score.setText("0");
		points.setText(" ");
		bonus.setText(" ");
		startChrono();*/
		itemsGrid = initGrid();

	}

	@Override
	public void resumePause() {
		// TODO Auto-generated method stub
		this.startChrono();
		Activity activity = (Activity) context;
		ImageView pause = (ImageView) activity.findViewById(R.id.pauselayout);
		GridView grid = (GridView) activity.findViewById(R.id.gridViewItems);
		pause.setVisibility(View.GONE);
		grid.setVisibility(View.VISIBLE);

	}
	private void setPauseBackground(ImageView v)
	{
		if(gameStart)
		{
			if(isSpeedMode)
				v.setImageResource(PAUSE_SPEED_LAYOUT);
			else
				v.setImageResource(PAUSE_TACTIC_LAYOUT);
		}
		else
			v.setImageResource(PAUSE_NORMAL_LAYOUT);
	}
	@Override
	public void onPause() {

		this.pauseChrono();
		Activity activity = (Activity) context;
		ImageView pause = (ImageView) activity.findViewById(R.id.pauselayout);
		setPauseBackground(pause);
		GridView grid = (GridView) activity.findViewById(R.id.gridViewItems);
		pause.setVisibility(View.VISIBLE);
		grid.setVisibility(View.GONE);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		gameStart = true;
		chaines = 0;
		hasChain = false;
		gamePaused = false;
		isCombinationFound = false;
		points = 0;
		bonus = 0;
		score = 0;
		elapsedTime = 0;
		itemsGrid = initGrid();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		gameStart = false;
		Activity activity = (Activity) context;
		ImageView button_recommencer = (ImageView) activity.findViewById(R.id.Game_recommencer);
		ImageView button_quitter = (ImageView) activity.findViewById(R.id.Game_quitter);
		button_recommencer.setImageResource(R.color.transparent);
		button_quitter.setImageResource(R.color.transparent);
	}
}
