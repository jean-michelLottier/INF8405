package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

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

	// Box dialog contents
	private static final String BOX_DIALOG_QUIT_TITLE = "Bejeweled";
	private static final String BOX_DIALOG_QUIT_MESSAGE = "Êtes-vous sûr de vouloir quitter l'application?";
	private static final String BOX_DIALOG_BUTTON_YES = "OUI";
	private static final String BOX_DIALOG_BUTTON_NO = "NON";

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

		default:
			break;
		}
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
		alertDialog.setTitle(BOX_DIALOG_QUIT_TITLE);
		alertDialog.setMessage(BOX_DIALOG_QUIT_MESSAGE);
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
				BOX_DIALOG_BUTTON_YES, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
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

}
