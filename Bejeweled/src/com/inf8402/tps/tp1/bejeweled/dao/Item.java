package com.inf8402.tps.tp1.bejeweled.dao;

import java.util.ArrayList;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.activity.GameActivity;

import android.graphics.drawable.AnimationDrawable;
import android.support.v4.util.ArrayMap;
import android.widget.ImageView;

public class Item {
	public static final int NORMAL = 0;
	public static final int SELECTED = 1;
	public static final int MOVED = 2;
	public static final int DELETED = 3;
	public static final int CREATED = 4;
	private int itemID;
	private int state;
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

	public void setState(int s) {
		state = s;
	}

	public int getState() {
		return state;
	}

	public boolean isAnimated() {
		if (state != NORMAL)
			return true;

		return false;
	}

	public int getResourceImage() {
		int r = itemID;
		if (state == SELECTED) {
			switch (itemID) {
			case R.drawable.item_eau:
				r = R.drawable.selection_eau;
				break;
			case R.drawable.item_feu:
				r = R.drawable.selection_feu;
				break;
			case R.drawable.item_terre:
				r = R.drawable.selection_terre;
				break;
			case R.drawable.item_jewel:
				r = R.drawable.selection_jewel;
				break;
			case R.drawable.item_vent:
				r = R.drawable.selection_vent;
				break;
			}
		}

		return r;
	}
}
