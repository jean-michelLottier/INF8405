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
	public static final int EMPTY = 5;
	
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public static final int VERTICAL = 5;
	public static final int HORIZONTAL = 6;
	public static final int BOTH = 7;
	
	private int itemID;
	private int state;
	private int posX;
	private int posY;
	
	
	public Item() {
		this.itemID = 0;
		this.state = EMPTY;
	}

	public Item(Item item) {
		this.itemID = item.getItemID();
		this.posX = item.getX();
		this.posY = item.getY();
		this.state = NORMAL;
	}

	public Item(int itemID) {
		this.itemID = itemID;
		this.state = NORMAL;
	}

	public Item(int itemID, int x, int y) {
		this.itemID = itemID;
		this.posX = x;
		this.posY = y;
		this.state = NORMAL;
	}
	
	
	public int getX() {
		return posX;
	}

	public void setX(int posX) {
		this.posX = posX;
	}

	public int getY() {
		return posY;
	}

	public void setY(int posY) {
		this.posY = posY;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public void setState(int s) {
		state = s;
	}

	public int getState() {
		return state;
	}

	public boolean isAnimated() {
		switch(state)
		{
			//case DELETED:
			//case CREATED:
			//case SELECTED:
			//	return true;
			case NORMAL:
			case EMPTY:
				return false;
			default:
				return false;
		}
	}

	public int getResourceImage() {
		int r = itemID;
		switch(state)
		{
			case SELECTED:
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
					break;
			case DELETED:/*
				switch (itemID) {
				case R.drawable.item_eau:
					r = R.drawable.deleted_eau;
					break;
				case R.drawable.item_feu:
					r = R.drawable.deleted_feu;
					break;
				case R.drawable.item_terre:
					r = R.drawable.deleted_terre;
					break;
				case R.drawable.item_jewel:
					r = R.drawable.deleted_jewel;
					break;
				case R.drawable.item_vent:
					r = R.drawable.deleted_vent;
					break;
				}*/
				r = 0;
				break;
			case MOVED:
				break;
				
			case CREATED:/*
				switch (itemID) {
				case R.drawable.item_eau:
					r = R.drawable.generate_eau;
					break;
				case R.drawable.item_feu:
					r = R.drawable.generate_feu;
					break;
				case R.drawable.item_terre:
					r = R.drawable.generate_terre;
					break;
				case R.drawable.item_jewel:
					r = R.drawable.generate_jewel;
					break;
				case R.drawable.item_vent:
					r = R.drawable.generate_vent;
					break;
				}*/
				break;
			case EMPTY:
				r = 0;
				break;
			default:
				break;
		}

		return r;
	}
}
