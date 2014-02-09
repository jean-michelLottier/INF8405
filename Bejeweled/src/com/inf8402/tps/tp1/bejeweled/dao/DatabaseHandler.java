package com.inf8402.tps.tp1.bejeweled.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * <p>
 * Base de donnée du jeu Bejeweled. Toutes les tables sont 
 * créées ici.
 * </p>
 * @author jean-michel
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper{

	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
