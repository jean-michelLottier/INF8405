package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.inf8402.tps.tp1.bejeweled.R;

public class GameModeActivity extends Activity {

	private ImageView button_speed;
	private ImageView button_tactic;
	private ImageView button_return;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_mode);

		DialogFragment dialog = new GameDialogFragment();
		Bundle args = new Bundle();
		args.putInt(GameDialogFragment.BOX_DIALOG_KEY,
				GameDialogFragment.BOX_DIALOG_REGISTER);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), "GameDialogFragment");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.game_mode, menu);
		return true;
	}

	private final OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.register_dialog_butt:
				break;

			default:
				break;
			}
		}
	};

}
