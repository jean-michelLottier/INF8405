package com.inf8402.tps.tp1.bejeweled.service;

import java.util.ArrayList;
import java.util.Random;

import android.support.v4.util.ArrayMap;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.dao.Item;

public class GameService implements IGameService {

	private static final int X = 0;
	private static final int Y = 1;
	private static final String NORTH = "north";
	private static final String SOUTH = "south";
	private static final String EAST = "east";
	private static final String WEST = "west";
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
			ArrayList<Integer> coordinate = (ArrayList<Integer>) current
					.getCoordinate().clone();
			ArrayMap<String, ArrayList<Integer>> neighbours = new ArrayMap<String, ArrayList<Integer>>();
			int x = coordinate.get(X);
			int y = coordinate.get(Y);
			// NORTH
			if (y != 0) {
				coordinate.add(Y, y - 1);
			} else {
				coordinate.add(Y, -1);
			}
			neighbours.put(NORTH, coordinate);
			// SOUTH
			if (y != 7) {
				coordinate.add(Y, y + 1);
			} else {
				coordinate.add(Y, -1);
			}
			neighbours.put(SOUTH, coordinate);
			coordinate.add(Y, y);
			// EAST
			if (x != 7) {
				coordinate.add(X, x + 1);
			} else {
				coordinate.add(X, -1);
			}
			neighbours.put(EAST, coordinate);
			// WEST
			if (x != 0) {
				coordinate.add(X, x - 1);
			} else {
				coordinate.add(X, -1);
			}
			neighbours.put(WEST, coordinate);

			current.setNeighbours(neighbours);
		}

		return items;
	}
}
