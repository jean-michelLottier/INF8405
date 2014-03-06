package com.inf8402.tps.tp1.bejeweled.service;

import java.util.ArrayList;

import com.inf8402.tps.tp1.bejeweled.dao.Item;

public interface IGameService {
	public static final int X = 0;
	public static final int Y = 1;
	public static final String NORTH = "north";
	public static final String SOUTH = "south";
	public static final String EAST = "east";
	public static final String WEST = "west";

	public ArrayList<Item> initGrid();

	/**
	 * <p>
	 * Method to generate an affine function.</br>This method is mainly use to
	 * define different areas (thanks to 2 affine functions) that the item will
	 * be able to permute with the neighbor selected (thanks to direction)
	 * </p>
	 * 
	 * @param xa
	 * @param ya
	 * @param xb
	 * @param yb
	 * @return director coefficient and intercept into arrayList
	 */
	public ArrayList<Integer> generateAffineFunction(int xa, int ya, int xb,
			int yb);

	/**
	 * <p>
	 * Exchange item selected with its neighbor (define by the direction).
	 * </p>
	 * 
	 * @param items
	 * @param item
	 * @param direction
	 * @return The list of items modified
	 */
	public ArrayList<Item> moveItem(ArrayList<Item> items, Item item,
			String direction);
}
