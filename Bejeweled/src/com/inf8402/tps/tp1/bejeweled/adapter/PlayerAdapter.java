package com.inf8402.tps.tp1.bejeweled.adapter;

import java.util.ArrayList;
import java.util.List;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.dao.Player;
import com.inf8402.tps.tp1.bejeweled.service.IMenuService;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PlayerAdapter extends BaseAdapter{

	private List<Player> playerList;
	private int mode;
	private final Context context;
	
	public PlayerAdapter(Context c, List<Player> p, int m)
	{
		this.mode = m;
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
			convertView = new View(this.context);
		}
		

		Player p = getItem(pos);

		if (p != null)
		{
			convertView = LayoutInflater.from(this.context).
				inflate(R.layout.score_tab, parent, false);	//Ajoute le layout "score_tab" des items de la ListView
		
			parent.setBackgroundResource(R.drawable.background);
			
			Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/gemina2acad.ttf");
			Typeface tf2 = Typeface.createFromAsset(context.getAssets(), "fonts/gemina2acadlaser.ttf");
			TextView tabPlayer = (TextView) convertView.findViewById(R.id.tabScore_player);	//Récupère le champ dans "score_tab" ou s'affiche le score du joueur
			TextView tabScore = (TextView) convertView.findViewById(R.id.tabScore_score);	// pour le modifier après
			tabPlayer.setTypeface(tf);
			tabScore.setTypeface(tf);
			
			int classement = pos+1;
			switch(mode)
			{
				case IMenuService.SPEED_MODE:
					tabPlayer.setText(classement+" - "+p.getPseudo()+": ");	// Met pour chaque joueur son classement son nom et son score
					tabScore.setText(p.getScoreSpeedMode()+" ");
					break;
				case IMenuService.TACTIC_MODE:
					tabPlayer.setText(classement+" - "+p.getPseudo()+": ");	// Met pour chaque joueur son classement son nom et son score
					tabScore.setText(p.getScoreTacticalMode()+" ");
					break;
				default:
					tabPlayer.setText("Unknown :");
					tabScore.setText("-- ");
					break;
			}
			if (pos==0)
			{
				tabPlayer.setTypeface(tf2);
				tabScore.setTypeface(tf2);
				tabPlayer.setTextColor(context.getResources().getColor(R.color.gold));
				tabPlayer.setTextSize(tabPlayer.getTextSize() + 5);
				tabScore.setTextColor(context.getResources().getColor(R.color.gold));
				tabScore.setTextSize(tabScore.getTextSize() + 5);
			}
		}
		else
		{
			parent.setBackgroundResource(R.drawable.background_score);
		}
		return convertView;
	}
	
}
