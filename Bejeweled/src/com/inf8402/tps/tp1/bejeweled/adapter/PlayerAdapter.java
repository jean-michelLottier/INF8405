package com.inf8402.tps.tp1.bejeweled.adapter;

import java.util.ArrayList;
import java.util.List;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.dao.Player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class PlayerAdapter extends BaseAdapter{

	private List<Player> playerList;
	
	private final Context context;
	
	public PlayerAdapter(Context c, List<Player> p)
	{
		this.playerList = new ArrayList<Player>();
		this.context = c;
		if (p!=null)
			this.playerList = p;
	}
	
	public void updatePlayerList(List<Player> p){
	    this.playerList = p;
	    notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return this.playerList.size();
	}

	@Override
	public Player getItem(int pos) {
		// TODO Auto-generated method stub
		return this.playerList.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null)
		{
			convertView = LayoutInflater.from(this.context).
					inflate(R.layout.score_tab, parent, false);	//Ajoute le layout "score_tab" des items de la ListView
		}
		TextView scorePlayer = (TextView) convertView.findViewById(R.id.tabScore_player);	//Récupère le champ dans "score_tab" ou s'affiche le score du joueur
																							// pour le modifier après
		
		Player p = getItem(pos);
		int classement = pos+1;
		scorePlayer.setText(classement+" - "+p.toString());	// Met pour chaque joueur son classement son nom et son score
		if (pos==0)
		{
			scorePlayer.setTextColor(context.getResources().getColor(R.color.gold));
			scorePlayer.setTextSize(scorePlayer.getTextSize() + 5);
		}
		return convertView;
	}
	
}
