package com.inf8402.tps.tp1.bejeweled.service;

import java.util.ArrayList;
import java.util.Random;

import android.support.v4.util.ArrayMap;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.dao.Item;

public class GameService implements IGameService {

	private static final int GRID_LENGTH = 64;

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
			items.add(item);
		}

		for (Item current : items) {
			current = fillNeighborsMap(current);
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
		int positionItem2 = translateCoordByPosition(item.getNeighbors().get(
				direction));

		Item item1 = new Item(items.get(positionItem1));
		Item item2 = new Item(items.get(positionItem2));

		item1.setCoordinate(item2.getCoordinate());
		item1 = fillNeighborsMap(item1);
		item2.setCoordinate(item.getCoordinate());
		item2 = fillNeighborsMap(item2);

		items.set(positionItem1, item2);
		items.set(positionItem2, item1);

		return items;
	}

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
}
