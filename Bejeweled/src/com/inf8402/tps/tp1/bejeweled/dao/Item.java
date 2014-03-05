package com.inf8402.tps.tp1.bejeweled.dao;

import java.util.ArrayList;

import android.support.v4.util.ArrayMap;

public class Item {
	private int itemID;
	private ArrayList<Integer> coordinate;
	private ArrayMap<String, ArrayList<Integer>> neighbours;

	public Item(int itemID) {
		this.itemID = itemID;
	}

	public Item(int itemID, ArrayList<Integer> coordinate) {
		this.itemID = itemID;
		this.coordinate = coordinate;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public ArrayList<Integer> getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(ArrayList<Integer> coordinate) {
		this.coordinate = coordinate;
	}

	public ArrayMap<String, ArrayList<Integer>> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(ArrayMap<String, ArrayList<Integer>> neighbours) {
		this.neighbours = neighbours;
	}

}
