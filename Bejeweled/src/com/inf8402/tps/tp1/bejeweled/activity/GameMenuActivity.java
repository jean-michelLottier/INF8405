package com.inf8402.tps.tp1.bejeweled.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.service.MenuService;
import com.inf8402.tps.tp1.bejeweled.service.MediaService;

public class GameMenuActivity extends IActivity{
	
	private LinearLayout buttons = null;
	private Intent intentMediaService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_menu);
		   
		menuService = new MenuService(this);
		buttons = (LinearLayout) findViewById(R.id.menuButtonsLayout);
		buttons.setOnTouchListener(multipleButtonsListener);

		intentMediaService = new Intent(this, MediaService.class);
		//getApplicationContext().startService(intentMediaService);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.game_menu, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == REQUEST_EXIT) {
	         if (resultCode == RESULT_QUIT) {
	            this.finish();

	         }
	     }
	}

	@Override
	void buttonManager(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case R.id.boutonMenu_score:
			menuService.goListScores();
			break;
		case R.id.boutonMenu_jouer:
			menuService.goPlayGame();
			break;
		case R.id.boutonMenu_quitter:
			menuService.goQuit(intentMediaService);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void onBackPressed() {
		menuService.goQuit(intentMediaService);
	return;
	
	}

}
