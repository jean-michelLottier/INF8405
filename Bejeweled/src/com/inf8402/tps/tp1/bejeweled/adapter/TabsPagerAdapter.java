package com.inf8402.tps.tp1.bejeweled.adapter;

import com.inf8402.tps.tp1.bejeweled.activity.ListScoreSpeed;
import com.inf8402.tps.tp1.bejeweled.activity.ListScoreTactic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		// TODO Auto-generated constructor stub
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		switch(index)
		{
			case 0:
				return new ListScoreSpeed();
				
			case 1:
				return new ListScoreTactic();
				
			default:
				break;
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
