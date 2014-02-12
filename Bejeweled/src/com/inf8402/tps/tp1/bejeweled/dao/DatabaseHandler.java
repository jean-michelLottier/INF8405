package com.inf8402.tps.tp1.bejeweled.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * <p>
 * Base de donnée du jeu Bejeweled. Toutes les tables sont créées ici.
 * </p>
 * 
 * @author jean-michel
 * 
 */
public class DatabaseHandler extends SQLiteOpenHelper {
	public static final String PLAYER_KEY = "id";
	public static final String PLAYER_PSEUDO = "pseudo";
	public static final String PLAYER_SCORE = "score";
	// public static final String PLAYER_RANK = "rank";

	public static final String PLAYER_TABLE_NAME = "Player";
	public static final String PLAYER_TABLE_CREATE = "CREATE TABLE"
			+ PLAYER_TABLE_NAME + " (" + PLAYER_KEY
			+ " INTEGER PRIMARY KEY AUTOINCREMENT" + PLAYER_PSEUDO + " TEXT, "
			+ PLAYER_SCORE + " INTEGER);";
	public static final String PLAYER_TABLE_DROP = "DROP TABLE IF EXISTS "
			+ PLAYER_TABLE_NAME + ";";

	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(PLAYER_TABLE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(PLAYER_TABLE_DROP);
		onCreate(db);
	}

}
