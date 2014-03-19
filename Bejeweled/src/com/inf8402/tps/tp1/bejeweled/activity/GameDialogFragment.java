package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inf8402.tps.tp1.bejeweled.R;
import com.inf8402.tps.tp1.bejeweled.dao.Player;
import com.inf8402.tps.tp1.bejeweled.dao.SessionManager;
import com.inf8402.tps.tp1.bejeweled.exception.BadInputParameterException;
import com.inf8402.tps.tp1.bejeweled.service.IMenuService;
import com.inf8402.tps.tp1.bejeweled.service.MenuService;

/**
 * <p>
 * This class permit to manage all dialog boxes of Bejeweled API.
 * </p>
 * 
 * @author jean-michel
 * 
 */
public class GameDialogFragment extends DialogFragment {
	public static final String BOX_DIALOG_KEY = "box_dialog_key";

	// Box dialog value
	public static final int BOX_DIALOG_QUIT = 0;
	public static final int BOX_DIALOG_REGISTER = 1;
	public static final int BOX_DIALOG_ENDGAME = 2;
	public static final int BOX_DIALOG_QUITGAME = 3;
	public static final int BOX_DIALOG_RESTARTGAME = 4;

	// Box dialog parameters
	public static final String BOX_DIALOG_CURRENTSCORE = "newSore";
	public static final String BOX_DIALOG_BESTSCORE = "oldScore";
	public static final String BOX_DIALOG_PSEUDO = "pseudo";
	public static final String BOX_DIALOG_RANK = "rank";

	// Box dialog contents
	private static final String BOX_DIALOG_QUIT_TITLE = "Bejeweled - Quitter";
	private static final String BOX_DIALOG_QUIT_MESSAGE = "Etes-vous sur de vouloir quitter l'application?";
	private static final String BOX_DIALOG_BUTTON_YES = "OUI";
	private static final String BOX_DIALOG_BUTTON_NO = "NON";
	private static final String BOX_DIALOG_BUTTON_OK = "Ok";
	private static final String BOX_DIALOG_BUTTON_BACK = "Retour";
	private static final String BOX_DIALOG_BUTTON_CANCEL = "Annuler";

	private static final String BOX_DIALOG_QUITGAME_MESSAGE = "Etes-vous sur de vouloir quitter l'application ou souhaitez vous changer de mode?";
	private static final String BOX_DIALOG_BUTTON_QUIT = "Quitter";

	private static final String BOX_DIALOG_REGISTER_TITLE = "Bejeweled - Pseudo";
	private static final String BOX_DIALOG_REGISTER_ALERT = "Veuillez saisir un pseudo valide!";

	private static final String BOX_DIALOG_RESTARTGAME_TITLE = "Bejeweled - Recommencer";

	private static final String BOX_DIALOG_ENDGAME_TITLE = "Bejeweled - Fin de partie";
	private static final String BOX_DIALOG_ENDGAME_MESSAGE = "Voulez-vous recommencer la partie?";
	private static final String BOX_DIALOG_BUTTON_RESTART = "Recommencer";
	private static final String BOX_DIALOG_BUTTON_MENU = "Menu";

	// Player informations
	private static final String PLAYER_INF_SCORE_TITLE = "Vos scores :";
	private static final String PLAYER_INF_SPEED_SCORE = "Vitesse : ";
	private static final String PLAYER_INF_TACTIC_SCORE = "Tactique : ";
	private static final String PLAYER_INF_SELECT_MODE = "Selectionnez un mode de jeu";

	private IMenuService menuService;
	private Intent intentMediaService;
	private SessionManager session;

	private View customView;
	
	public IMenuService getMenuService() {
		if (menuService == null) {
			menuService = new MenuService(getActivity());
		}
		return menuService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	public Intent getIntentMediaService() {
		return intentMediaService;
	}

	public void setIntentMediaService(Intent intentMediaService) {
		this.intentMediaService = intentMediaService;
	}
	
	private OnKeyListener backListener = new OnKeyListener(){

		@Override
		public boolean onKey(DialogInterface dialog, int keycode, KeyEvent event) {
			// TODO Auto-generated method stub
			if(keycode == KeyEvent.KEYCODE_BACK)
			{
				GameModeActivity activity = (GameModeActivity) ((AlertDialog) dialog)
						.getOwnerActivity();
				activity.finish();
				dismiss();
				return true;
			}
			return false;
		}
		
	};

	/**
	 * <p>
	 * Constructor of GameDialogFragment class
	 * </p>
	 */
	@Override
	public Dialog onCreateDialog(Bundle saveInstanceState) {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		int boxID = getArguments().getInt(BOX_DIALOG_KEY);
		switch (boxID) {
		case BOX_DIALOG_QUIT:
			alertDialog = initQuitBoxDialog(alertDialog);
			break;
		case BOX_DIALOG_REGISTER:
			alertDialog = initRegisterBoxDialog(alertDialog);
			alertDialog.setOnKeyListener(backListener);
			alertDialog.setOnShowListener(onShowListener);
			break;
		case BOX_DIALOG_ENDGAME:
			int currentScore = getArguments().getInt(BOX_DIALOG_CURRENTSCORE);
			int bestScore = getArguments().getInt(BOX_DIALOG_BESTSCORE);
			String pseudo = getArguments().getString(BOX_DIALOG_PSEUDO);
			int rank = getArguments().getInt(BOX_DIALOG_RANK);
			alertDialog = initEndGameBoxDialog(alertDialog, pseudo,
					currentScore, bestScore, rank);
			break;
		case BOX_DIALOG_QUITGAME:
			alertDialog = initQuitGameBoxDialog(alertDialog);
			break;
		case BOX_DIALOG_RESTARTGAME:
			alertDialog = initRestartGameBoxDialog(alertDialog);
			break;
		default:
			break;
		}
		return alertDialog;
	}

	private AlertDialog initRestartGameBoxDialog(AlertDialog alertDialog) {
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setTitle(BOX_DIALOG_RESTARTGAME_TITLE);

		alertDialog.setMessage(BOX_DIALOG_ENDGAME_MESSAGE);
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
				BOX_DIALOG_BUTTON_YES, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						GameActivity activity = (GameActivity) ((AlertDialog) dialog)
								.getOwnerActivity();


						activity.reinitialize();
						dismiss();
					}
				});
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
				BOX_DIALOG_BUTTON_NO, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						GameActivity.gameService.resumePause();
					}
				});

		return alertDialog;
	}

	private AlertDialog initQuitGameBoxDialog(AlertDialog alertDialog) {
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setTitle(BOX_DIALOG_QUIT_TITLE);

		alertDialog.setMessage(BOX_DIALOG_QUITGAME_MESSAGE);
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
				BOX_DIALOG_BUTTON_QUIT, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						GameActivity activity = (GameActivity) ((AlertDialog) dialog)
								.getOwnerActivity();
						GameActivity.gameService.setGameQuit(true);
						activity.setResult(IActivity.RESULT_QUIT,null);
						activity.finish();
					}
				});
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
				BOX_DIALOG_BUTTON_MENU, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						GameActivity.gameService.clear();
						GameActivity.gameService.setGameQuit(true);
						((AlertDialog) dialog).getOwnerActivity().finish();
					}
				});
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
				BOX_DIALOG_BUTTON_CANCEL, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						GameActivity.gameService.resumePause();
					}
				});

		return alertDialog;
	}

	private AlertDialog initEndGameBoxDialog(AlertDialog alertDialog,
			String pseudo, int currentScore, int bestScore, int rank) {
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setTitle(BOX_DIALOG_ENDGAME_TITLE);
		StringBuilder sb = new StringBuilder();
		
		int oldScore = bestScore;
		String txt="";
		boolean fail = false;
		if(currentScore <= oldScore)
			fail = true;
		if (fail)
		txt += "ID: "+pseudo+".\n\n";
		txt += "Score: "+currentScore+" points.\n";
		if (oldScore > 0)
		{
			if(currentScore > oldScore)
			{
				txt += "\nBravo!! Vous avez battu votre meilleur score de: "+oldScore+" points.\n";
				txt += "\nNOUVEAU MEILLEUR SCORE ="+currentScore+" points.\n";
				txt += "RANK: "+rank;
					
			}
			else
			{
				txt += "Votre meilleur score: "+oldScore+" points.\n";
				txt += "RANK: "+rank;
			}
		}
		else
		{
			if(currentScore > 0)
				txt += "RANK: "+rank;
			else
				txt += "RANK: --";
		}
		sb.append(txt);
		// sb.append("\n\n").append(BOX_DIALOG_ENDGAME_MESSAGE);
		alertDialog.setMessage(sb.toString());
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
				BOX_DIALOG_BUTTON_RESTART,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// GameActivity.gameService.reinitialize();
						GameActivity activity = (GameActivity) ((AlertDialog) dialog)
								.getOwnerActivity();
						activity.reinitialize();
						dismiss();
					}
				});
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
				BOX_DIALOG_BUTTON_MENU, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						GameActivity.gameService.clear();
						GameActivity.gameService.setGameQuit(true);
						((AlertDialog) dialog).getOwnerActivity().finish();
					}
				});

		return alertDialog;
	}

	private AlertDialog initRegisterBoxDialog(AlertDialog alertDialog) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = getActivity().getLayoutInflater();
		customView = inflater.inflate(R.layout.register_dialog, null);
		alertDialog.setView(customView);

		alertDialog.setTitle(BOX_DIALOG_REGISTER_TITLE);

		alertDialog.setCanceledOnTouchOutside(false);
		
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
				BOX_DIALOG_BUTTON_OK, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
				BOX_DIALOG_BUTTON_BACK, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Activity activity = ((AlertDialog) dialog).getOwnerActivity();
						session = new SessionManager(activity
								.getApplicationContext());
						session.setHasStarted(false);
						activity.finish();
					}

		});
		
		return alertDialog;
	}

	/**
	 * <p>
	 * <strong>private</strong> method to initialize quit dialog box.
	 * </p>
	 * 
	 * @param alertDialog
	 * @return dialog box with its contents
	 */
	private AlertDialog initQuitBoxDialog(AlertDialog alertDialog) {
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setTitle(BOX_DIALOG_QUIT_TITLE);
		alertDialog.setMessage(BOX_DIALOG_QUIT_MESSAGE);
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
				BOX_DIALOG_BUTTON_YES, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						getActivity().stopService(getIntentMediaService());

						getActivity().finish();
					}
				});
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
				BOX_DIALOG_BUTTON_NO, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});

		return alertDialog;
	}

	
	private OnShowListener onShowListener = new OnShowListener() {
		
		@Override
		public void onShow(DialogInterface dialog) {
			// TODO Auto-generated method stub
                Button button = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	View vParent = customView;
    				EditText editText_pseudo = (EditText) vParent
    						.findViewById(R.id.register_dialog_pseudo);
    				boolean isRegister = false;
    				Editable pseudo = editText_pseudo.getText();
    				if (pseudo == null || pseudo.toString().isEmpty()
    						|| pseudo.toString().length() < 4) {
    					Toast.makeText(v.getContext(), BOX_DIALOG_REGISTER_ALERT,
    							Toast.LENGTH_SHORT).show();
    				} else {
    					menuService = getMenuService();
    					Player player = new Player();
    					try {
    						player = menuService.startPlayerSession(pseudo
    								.toString());
    						session = new SessionManager(getActivity()
    								.getApplicationContext());
    						session.initPlayerSession(player);
    						isRegister = true;

    					} catch (BadInputParameterException e) {
    						// TODO: handle exception
    						Toast.makeText(v.getContext(),
    								BOX_DIALOG_REGISTER_ALERT, Toast.LENGTH_SHORT)
    								.show();
    					}
    				}

    				if (isRegister) {
    					TextView textView = (TextView) getActivity().findViewById(
    							R.id.Mode_pseudo);
    					textView.setText(" "+session.getPlayerPseudo());
//    					textView = (TextView) getActivity().findViewById(
//    							R.id.textViewScoreTitle);
//    					textView.setText(PLAYER_INF_SCORE_TITLE);
    					textView = (TextView) getActivity().findViewById(
    							R.id.Mode_scoreSpeed);
    					textView.setText(" "+session.getPlayerScoreSpeedMode());
    					textView = (TextView) getActivity().findViewById(
    							R.id.Mode_scoreTactic);
    					textView.setText(" "+session.getPlayerScoreTacticMode());
//    					textView = (TextView) getActivity().findViewById(
//    							R.id.textViewSelectGameMode);
//    					textView.setText(PLAYER_INF_SELECT_MODE);

    					LinearLayout layout = (LinearLayout) getActivity().findViewById(
    							R.id.Mode_infos);
    					layout.setVisibility(View.VISIBLE);
    					dismiss();
    				}
                }
            });
        }
	};
}
