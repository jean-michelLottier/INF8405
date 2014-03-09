package com.inf8402.tps.tp1.bejeweled.dao;

import java.util.ArrayList;

import android.support.v4.util.ArrayMap;

public class Item {
	private int itemID;
	private ArrayList<Integer> coordinate;
	private ArrayMap<String, ArrayList<Integer>> neighbors;
	private boolean isDeleted;

	public Item() {

	}

	public Item(Item item) {
		this.itemID = item.getItemID();
		this.coordinate = item.getCoordinate();
		this.neighbors = item.getNeighbors();
		this.isDeleted = false;
	}

	public Item(int itemID) {
		this.itemID = itemID;
		this.isDeleted = false;
	}

	public Item(int itemID, ArrayList<Integer> coordinate) {
		this.itemID = itemID;
		this.coordinate = coordinate;
		this.isDeleted = false;
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

	public ArrayMap<String, ArrayList<Integer>> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayMap<String, ArrayList<Integer>> neighbors) {
		this.neighbors = neighbors;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
