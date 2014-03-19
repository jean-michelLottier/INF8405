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
import android.view.ViewGroup.LayoutParams;
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

	
	private ArrayList<Item> itemsGrid;
	private int coupsRestants = GameActivity.LIMIT_MOVE;
	private int chaines = 0;
	private Chronometer timer;
	private boolean hasChain;
	private boolean gamePaused;
	private boolean isCombinationFound;
	private boolean chronoIsRunning;
	private boolean isReinitialized;
	private boolean gameStart;
	private boolean gameQuit;
	private int score = 0;
	private long stopTime = GameActivity.LIMIT_TIME;
	private long elapsedTime = 0;
	private Context context;
	private boolean isSpeedMode;
	// New attr
	private ArrayList<Item> itemsToDelete = new ArrayList<Item>();
	private Random random = new Random();
	private ArrayList<Item> movedItems = new ArrayList<Item>();
	private ArrayList<ArrayList<Item>> chains = new ArrayList<ArrayList<Item>>();
	private ArrayList<Item> horizontalChain = new ArrayList<Item>();
	private ArrayList<Item> verticalChain = new ArrayList<Item>();
	private ArrayList<Item> generatedItems = new ArrayList<Item>();

	public boolean isGameStart() {
		return gameStart;
	}

	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}
	public boolean isGameQuit() {
		return gameQuit;
	}

	public void setGameQuit(boolean gameQuit) {
		this.gameQuit = gameQuit;
	}
	public GameService(Context c, boolean b) {
		this.setContext(c);
		this.isSpeedMode = b;
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
	public void initGrid() {
		itemsGrid = new ArrayList<Item>();
		for (int j = 0; j < GRID_SIZE; j++) {
			for (int i = 0; i < GRID_SIZE; i++) {
				itemsGrid.add(new Item());
			}
		}
		for (int j = 0; j < GRID_SIZE; j++) {
			for (int i = 0; i < GRID_SIZE; i++) {
				setItem(i,j,generateItem(i,j));
			}
		}
	}

	private Item generateItem(int x, int y)
	{
		int value = random.nextInt(5);
		Item item = new Item(itemsID.get(value), x, y);
		int counter = 0;
		boolean conflict = checkConflict(item);
		while(conflict == true || counter < 5)
		{
			value = (value + 1) % 5;
			item.setItemID(itemsID.get(value));
			conflict = checkConflict(item);
			counter++;
		}
		return item;
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
	public boolean switchMove(Item item, int direction)
	{
		itemsToDelete.clear();
		boolean isValidMove= true;
		boolean isCombination = false;
		Item item2 = new Item();
		switch(direction)
		{
			case Item.UP:
				if (item.getY() == 0)
					isValidMove = false;
				else
				{
					item2 = switchItem(item, Item.UP);
				}
				break;
			case Item.DOWN:
				if (item.getY() == 7)
					isValidMove = false;
				else
				{
					item2 = switchItem(item, Item.DOWN);
				}
				break;
			case Item.LEFT:
				if (item.getX() == 0)
					isValidMove = false;
				else
					{
						item2 = switchItem(item, Item.LEFT);
					}
				break;
			case Item.RIGHT:
				if (item.getX() == 7)
					isValidMove = false;
				else
				{
					item2 = switchItem(item, Item.RIGHT);
				}
				break;
			default:
				break;
		}
		if (isValidMove)
		{
			isCombination = checkCombinationAfterMove(item,item2);
			if (!isCombination)
			{
				switch(direction)
				{
					case Item.UP:
						switchItem(item, Item.DOWN);
						break;
					case Item.DOWN:
						switchItem(item, Item.UP);
						break;
					case Item.LEFT:
						switchItem(item, Item.RIGHT);
						break;
					case Item.RIGHT:
						switchItem(item, Item.LEFT);
						break;
					default:
						break;
				}
				return false;
			}
			else
			{
				getItem(item2.getX(),item2.getY()).setState(Item.MOVED);
				getItem(item.getX(),item.getY()).setState(Item.MOVED);

				movedItems.add(getItem(item2.getX(),item2.getY()));
				movedItems.add(getItem(item.getX(),item.getY()));
			}
		}
		else
		{
			switch(direction)
			{
				case Item.UP:
					switchItem(item, Item.DOWN);
					break;
				case Item.DOWN:
					switchItem(item, Item.UP);
					break;
				case Item.LEFT:
					switchItem(item, Item.RIGHT);
					break;
				case Item.RIGHT:
					switchItem(item, Item.LEFT);
					break;
				default:
					break;
			}
			return false;
		}
		return true;
	}
	
	private void fallItem(Item item, int offset)
	{
		setItem(item.getX(),item.getY()+offset,item);
		setItem(item.getX(),item.getY(), new Item());
		getItem(item.getX(),item.getY()).setState(Item.EMPTY);
		item.setY(item.getY()+offset);
	}
	public void updateBeforeFill(){}
	public void fillGrid()
	{
		int j;
		for(int i=0; i<GRID_SIZE; i++)
		{

			j = GRID_SIZE - 1;
			while(j>=0)
			{
				if(getItem(i,j).getState()==Item.EMPTY || getItem(i,j).getState()==Item.DELETED)
				{
					setItem(i,j,generateItem(i,j));
					/*
					getItem(i,j).setState(Item.CREATED);
					generatedItems.add(getItem(i,j));*/
				}
				j--;
			}
		}
	}
	public void updateAfterFill(){/*
		for(Item item: generatedItems)
		{
			item.setState(Item.NORMAL);
		}
		generatedItems.clear();*/
	}
	public void moveFallingItems()
	{
		int j;
		int destY;
		int posY;
		int offset;

		for(int i=0; i<GRID_SIZE; i++)
		{

			j = GRID_SIZE - 1;
			destY = -1;
			while(j>=0)
			{
				if(getItem(i,j).getState()==Item.DELETED)
				{
					destY = j;
					break;
				}
				j--;
			}
			if (destY == 0)
			{
				getItem(i,j).setState(Item.EMPTY);
			}
			else
			{
				if(destY!=-1)
				{
					j = 0;
					posY = -1;
					while(j<GRID_SIZE-1)
					{
						if(getItem(i,j).getState()==Item.NORMAL && getItem(i,j+1).getState()==Item.DELETED)
						{
							posY = j;
							break;
						}
						j++;
					}
					if(posY!=-1)
					{
						offset = destY - posY;
						j = posY;
						while(j>=0 && getItem(i,j).getState()!=Item.EMPTY)
						{
							fallItem(getItem(i,j),offset);
							j--;
						}
					}
				}
			}
		}
	}
	
	private boolean checkCombinationNeighbours(Item item)
	{
		
		int counter = 0;
		horizontalChain.clear();
		verticalChain.clear();
		ArrayList<Item> h = new ArrayList<Item>();
		ArrayList<Item> v = new ArrayList<Item>();
		//Check vertical combination
		int i = item.getY()-1;
		while(i>=0 && getItem(item.getX(),i).getState()!=Item.EMPTY)
		{
			if(getItem(item.getX(),i).getItemID() == item.getItemID() && getItem(item.getX(),i).getState()==Item.NORMAL)
			{
				counter++;
				v.add(getItem(item.getX(),i));
			}
			else
			{
				break;
			}

			i--;
		}
		Collections.reverse(v);
		v.add(item);
		i = item.getY()+1;
		while(i<GRID_SIZE && getItem(item.getX(),i).getState()!=Item.EMPTY)
		{
			
			if(getItem(item.getX(),i).getItemID() == item.getItemID() && getItem(item.getX(),i).getState()==Item.NORMAL)
			{
				counter++;
				v.add(getItem(item.getX(),i));
			}
			else
			{
				break;
			}
			i++;
		}
		// If vertical combination, check horizontal Combination
		if (counter >= 2)
		{
			counter = 0;
			//Vertical combination ==true + Check horizontal combination
			i = item.getX()-1;
			while(i>=0 && getItem(i,item.getY()).getState()!=Item.EMPTY)
			{
				if(getItem(i,item.getY()).getItemID() == item.getItemID() && getItem(i,item.getY()).getState()==Item.NORMAL)
				{
					counter++;
					h.add(getItem(i,item.getY()));
				}
				else
				{
					break;
				}

				i--;
			}
			Collections.reverse(h);
			h.add(item);
			i = item.getX()+1;
			while(i<GRID_SIZE && getItem(i,item.getY()).getState()!=Item.EMPTY)
			{
				if(getItem(i,item.getY()).getItemID() == item.getItemID() && getItem(i,item.getY()).getState()==Item.NORMAL)
				{
					counter++;
					h.add(getItem(i,item.getY()));
				}
				else
				{
					break;
				}

				i++;
			}
			//if Vertical combination ==true + Horizontal combination== true
			if (counter >= 2)
			{
				counter = 0;
				itemsToDelete.addAll(v);
				itemsToDelete.addAll(h);
				verticalChain.addAll(v);
				horizontalChain.addAll(h);
				chains.add(new ArrayList<Item>(verticalChain));
				chains.add(new ArrayList<Item>(horizontalChain));
				return true;
			}
			else
			{
				itemsToDelete.addAll(v);
				verticalChain.addAll(v);
				chains.add(new ArrayList<Item>(verticalChain));
				return true;
			}
		}
		else
		{
			counter = 0;
			//Check horizontal combination
			i = item.getX()-1;
			while(i>=0 && getItem(i,item.getY()).getState()!=Item.EMPTY)
			{
				if(getItem(i,item.getY()).getItemID() == item.getItemID() && getItem(i,item.getY()).getState()==Item.NORMAL)
				{
					counter++;
					h.add(getItem(i,item.getY()));
				}
				else
				{
					break;
				}

				i--;
			}
			Collections.reverse(h);
			h.add(item);
			i = item.getX()+1;
			while(i<GRID_SIZE && getItem(i,item.getY()).getState()!=Item.EMPTY)
			{
				if(getItem(i,item.getY()).getItemID() == item.getItemID() && getItem(i,item.getY()).getState()==Item.NORMAL)
				{
					counter++;
					h.add(getItem(i,item.getY()));
				}
				else
				{
					break;
				}

				i++;
			}
			//if Horizontal combination== true
			if (counter >= 2)
			{
				counter = 0;
				itemsToDelete.addAll(h);
				horizontalChain.addAll(h);
				chains.add(new ArrayList<Item>(horizontalChain));
				return true;
			}
		}
		return false;
	}
	
	private boolean checkConflict(Item item)
	{
		int counter = 0;
		//Check vertical combination
		int i = item.getY()-1;
		while(i>=0 && getItem(item.getX(),i).getState()!=Item.EMPTY)
		{
			if(getItem(item.getX(),i).getItemID() == item.getItemID() && getItem(item.getX(),i).getState()==Item.NORMAL)
			{
				counter++;
			}
			else
			{
				break;
			}

			i--;
		}
		i = item.getY()+1;
		while(i<GRID_SIZE && getItem(item.getX(),i).getState()!=Item.EMPTY)
		{
			
			if(getItem(item.getX(),i).getItemID() == item.getItemID() && getItem(item.getX(),i).getState()==Item.NORMAL)
			{
				counter++;
			}
			else
			{
				break;
			}
			i++;
		}
		// If vertical combination, check horizontal Combination
		if (counter >= 2)
		{
			return true;
		}
		counter = 0;
		//Check horizontal combination
		i = item.getX()-1;
		while(i>=0 && getItem(i,item.getY()).getState()!=Item.EMPTY)
		{
			if(getItem(i,item.getY()).getItemID() == item.getItemID() && getItem(i,item.getY()).getState()==Item.NORMAL)
			{
				counter++;
			}
			else
			{
				break;
			}

			i--;
		}
		i = item.getX()+1;
		while(i<GRID_SIZE && getItem(i,item.getY()).getState()!=Item.EMPTY)
		{
			if(getItem(i,item.getY()).getItemID() == item.getItemID() && getItem(i,item.getY()).getState()==Item.NORMAL)
			{
				counter++;
			}
			else
			{
				break;
			}

			i++;
		}
		//if Horizontal combination== true
		if (counter >= 2)
		{
			return true;
		}
		return false;
	}
	public void deleteCombinations()
	{

		if(!movedItems.isEmpty())
		{
			for(Item item: movedItems)
			{
				getItem(item.getX(),item.getY()).setState(Item.NORMAL);
			}
		}
		movedItems.clear();
		
		for(Item item: itemsToDelete)
		{
			getItem(item.getX(),item.getY()).setState(Item.DELETED);
		}
		itemsToDelete.clear();
	}
	
	private Item getItem(int positionX, int positionY)
	{
		int pos = positionX+positionY*GRID_SIZE;
		return itemsGrid.get(pos);
		
	}
	
	private void setItem(int positionX, int positionY, Item item)
	{
		int pos = positionX+positionY*GRID_SIZE;
		itemsGrid.set(pos,item);
	}

	private boolean checkCombinationAfterMove(Item item,Item item2)
	{
		boolean v1 = false;
		boolean v2 = false;
		v1 = checkCombinationNeighbours(item);
		v2 = checkCombinationNeighbours(item2);
		if(v1||v2)
		{
			return true;
		}
		return false;
	}
	public boolean fullSearchCombination()
	{
		int counter;
		chains.clear();
		verticalChain.clear();
		horizontalChain.clear();
		boolean isCombination = false;
		boolean verifV = false;
		boolean initV = false;
		int itemID = 0;
		int j = 0;
		int i = 0;
		ArrayList<Item> h = new ArrayList<Item>();
		ArrayList<Item> v = new ArrayList<Item>();
		
		//COLUMS SEARCH
		for(i=0; i<GRID_SIZE; i++)
		{
			counter = 0;
			j=0;
			v.clear();
			verifV = false;
			initV = false;
			while(j<GRID_SIZE)
			{
				if(getItem(i,j).getState()!=Item.EMPTY && getItem(i,j).getState()!=Item.DELETED)
				{
					if (!initV)
					{
						itemID = getItem(i,j).getItemID();
						v.add(getItem(i,j));
						counter=1;
						initV = true;
					}
					else
					{
						if (getItem(i,j).getItemID()==itemID)
						{
							counter++;
							v.add(getItem(i,j));
							if(counter==3)
							{
								verifV = true;
								isCombination = true;
							}
						}
						else
						{
							if(verifV)
							{
								itemsToDelete.addAll(v);
								verticalChain.addAll(v);
								chains.add(new ArrayList<Item>(verticalChain));
								v.clear();
								verifV = false;
								counter = 0;
								// End of search for the column
							}
							else
							{
								counter = 1;
								v.clear();
								itemID = getItem(i,j).getItemID();
								v.add(getItem(i,j));
							}
						}
					}
				}
				else
				{
					if (verifV)
					{
						itemsToDelete.addAll(v);
						verticalChain.addAll(v);
						chains.add(new ArrayList<Item>(verticalChain));
						v.clear();
						verifV = false;
						counter = 0;
						// End of search for the column
					}
					else
					{
						counter = 0;
						initV=false;
						v.clear();
					}
				}
				j++;
				if (j>=GRID_SIZE && verifV)
				{
					itemsToDelete.addAll(v);
					verticalChain.addAll(v);
					chains.add(new ArrayList<Item>(verticalChain));
					v.clear();
					verifV = false;
					counter = 0;
					// End of search for the column
				}
			}
		}
		
		
		//ROW SEARCH
		for(j=0; j<GRID_SIZE; j++)
		{
			counter = 0;
			i=0;
			h.clear();
			initV = false;
			verifV = false;
			while(i<GRID_SIZE)
			{
				if(getItem(i,j).getState()!=Item.EMPTY && getItem(i,j).getState()!=Item.DELETED)
				{
					if (!initV)
					{
						itemID = getItem(i,j).getItemID();
						h.add(getItem(i,j));
						counter=1;
						initV = true;
					}
					else
					{
						if (getItem(i,j).getItemID()==itemID)
						{
							counter++;
							h.add(getItem(i,j));
							if(counter==3)
							{
								verifV = true;
								isCombination = true;
							}
						}
						else
						{
							if(verifV)
							{
								itemsToDelete.addAll(h);
								horizontalChain.addAll(h);
								chains.add(new ArrayList<Item>(horizontalChain));
								h.clear();
								verifV = false;
								counter = 0;
								// End of search for the column
							}
							else
							{
								counter = 1;
								h.clear();
								itemID = getItem(i,j).getItemID();
								h.add(getItem(i,j));
							}
						}
					}
				}
				else
				{
					if (verifV)
					{
						itemsToDelete.addAll(h);
						horizontalChain.addAll(h);
						chains.add(new ArrayList<Item>(horizontalChain));
						h.clear();
						verifV = false;
						counter = 0;
						// End of search for the column
					}
					else
					{
						counter = 0;
						initV=false;
						h.clear();
					}
				}
				i++;
				if (i>=GRID_SIZE && verifV)
				{
					itemsToDelete.addAll(h);
					horizontalChain.addAll(h);
					chains.add(new ArrayList<Item>(horizontalChain));
					h.clear();
					verifV = false;
					counter = 0;
					// End of search for the column
				}
			}
		}
		return isCombination;
		
	}
	public Item switchItem(Item item, int direction){
		Item item2 = new Item();
		Item temp;
		switch(direction)
		{
			case Item.UP:
				temp = getItem(item.getX(),item.getY()-1);
				item2 = temp;
				setItem(item.getX(),item.getY()-1,item);
				setItem(item.getX(),item.getY(),item2);
				item.setY(item.getY()-1);
				item2.setY(item2.getY()+1);
				break;
			case Item.DOWN:
				temp = getItem(item.getX(),item.getY()+1);
				item2 = temp;
				setItem(item.getX(),item.getY()+1,item);
				setItem(item.getX(),item.getY(),item2);
				item.setY(item.getY()+1);
				item2.setY(item2.getY()-1);
				break;
			case Item.LEFT:
				temp = getItem(item.getX()-1,item.getY());
				item2 = temp;
				setItem(item.getX()-1,item.getY(),item);
				setItem(item.getX(),item.getY(),item2);
				item.setX(item.getX()-1);
				item2.setX(item2.getX()+1);
				break;
			case Item.RIGHT:
				temp = getItem(item.getX()+1,item.getY());
				item2 = temp;
				setItem(item.getX()+1,item.getY(),item);
				setItem(item.getX(),item.getY(),item2);
				item.setX(item.getX()+1);
				item2.setX(item2.getX()-1);
				break;
			default:
				break;
		}
		
		return item2;
	}

	public int getChains() {
		return this.chaines;
	}
	public void setChains(int chains) {
		this.chaines = chains;
	}

	public int getScore() {
		return this.score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	// [0] = position, [1] = bonusScore
	public ArrayList<ArrayList<Integer>> getBonusPoints()
	{
		int itemsNumber = 0;
		int bonusScore = 0;
		int position;
		ArrayList<Integer> v = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> h= new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<chains.size();i++)
		{
			itemsNumber = chains.get(i).size();
			v.clear();
			bonusScore = itemsNumber*GameActivity.V_BONUS;
			Item item = chains.get(i).get(itemsNumber/2);
			position = item.getX()+item.getY()*GameService.GRID_SIZE;
			v.add(position);
			v.add(bonusScore);
			h.add(new ArrayList<Integer>(v));
		}
		return new ArrayList<ArrayList<Integer>>(h);
	}
	public ArrayList<Integer> getFirstChainPoints()
	{
		int itemsNumber = 0;
		int points = 0;
		int position;
		ArrayList<Integer> v = new ArrayList<Integer>();
		itemsNumber = chains.get(0).size();
		points = GameActivity.V_POINTS+(itemsNumber-3)*GameActivity.V_BONUS;
		Item item = chains.get(0).get(itemsNumber/2);
		position = item.getX()+item.getY()*GameService.GRID_SIZE;
		v.add(position);
		v.add(points);
		return new ArrayList<Integer>(v);
	}

	public int getNbrMoveLeft() {
		return coupsRestants;
	}
	public void setNbrMoveLeft(int i){
		this.coupsRestants = i;
	}
	public void moveUpdate() {
		if (hasChain) {
			chaines += 1;
			if (coupsRestants > 0)
				coupsRestants -= 1;
		}
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

	@Override
	public boolean isReinitialized()
	{
		return isReinitialized;
		
	}
	@Override
	public void setReinitialized(boolean v)
	{
		this.isReinitialized = v;
	}
	@Override
	public void reinitialize() {
		init();

	}

	@Override
	public void resumePause() {
		// TODO Auto-generated method stub
		this.startChrono();
		Activity activity = (Activity) context;
		ImageView pause = (ImageView) activity.findViewById(R.id.pauselayout);
		GridView grid = (GridView) activity.findViewById(R.id.gridViewItems);
		ImageView pause_button = (ImageView) activity.findViewById(R.id.Game_pause);
		pause_button.setBackgroundResource(R.drawable.game_pause);
		pause_button.requestFocus();
		this.gamePaused = false;
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
		ImageView pause_button = (ImageView) activity.findViewById(R.id.Game_pause);
		pause_button.setBackgroundResource(R.drawable.game_play);
		pause_button.requestFocus();
		this.gamePaused = true;
		pause.setVisibility(View.VISIBLE);
		grid.setVisibility(View.GONE);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		gameQuit = false;
		gameStart = true;
		chaines = 0;
		gamePaused = false;
		score = 0;
		elapsedTime = 0;
		coupsRestants=GameActivity.LIMIT_MOVE;
		initGrid();
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
