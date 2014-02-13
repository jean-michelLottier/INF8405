package com.inf8402.tps.tp1.bejeweled.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.adapter.PlayerAdapter;
import com.inf8402.tps.tp1.bejeweled.dao.Player;
import com.inf8402.tps.tp1.bejeweled.exception.BadInputParameterException;
import com.inf8402.tps.tp1.bejeweled.service.IMenuService;
import com.inf8402.tps.tp1.bejeweled.service.MenuService;

public class ListScoreSpeed extends Fragment {

	private ListView listView;
	private PlayerAdapter playerAdapter;
	private IMenuService menuService;

	public IMenuService getMenuService() {
		if (menuService == null) {
			menuService = new MenuService(getActivity().getApplicationContext());
		}
		return menuService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		List<Player> topTenPlayers = new ArrayList<Player>();
		try {
			menuService = getMenuService();
			topTenPlayers = menuService
					.getTopTenPlayers(menuService.SPEED_MODE);
		} catch (BadInputParameterException e) {
			topTenPlayers = new ArrayList<Player>();
		}
		playerAdapter = new PlayerAdapter(container.getContext(), topTenPlayers);

		listView = (ListView) container.findViewById(R.id.listViewSpeed);
		listView.setAdapter(playerAdapter);

		View rootView = inflater.inflate(R.layout.score_category_speed,
				container, false);
		return rootView;
	}

}
