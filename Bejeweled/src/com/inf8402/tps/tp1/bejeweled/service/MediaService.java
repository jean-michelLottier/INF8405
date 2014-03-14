package com.inf8402.tps.tp1.bejeweled.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.inf8402.tps.tp1.bejeweled.R;

public class MediaService extends Service {
	private MediaPlayer mediaPlayer;

	public MediaService() {

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void onCreate() {
		System.out.println("*************onCreate method START*************");
		super.onCreate();
		// play(R.raw.the_glitch_mob_we_can_make_the_world_stop);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out
				.println("*************onStartCommand method START*************");
		super.onStartCommand(intent, flags, startId);
		//play(R.raw.the_glitch_mob_we_can_make_the_world_stop);

		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		stop();
	}

	private void play(int musicID) {
		System.out.println("*************play method START*************");
		mediaPlayer = new MediaPlayer();
		mediaPlayer = MediaPlayer.create(this, musicID);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
		System.out.println("*************play method END*************");
	}

	private void stop() {
		System.out.println("*************stop method START*************");
		if (mediaPlayer != null) {
			System.out.println("*************TEST2*************");
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	private void pause() {
		mediaPlayer.pause();
	}

}
