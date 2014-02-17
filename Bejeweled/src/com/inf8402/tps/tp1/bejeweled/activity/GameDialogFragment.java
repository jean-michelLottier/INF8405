package com.inf8402.tps.tp1.bejeweled.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

	// Box dialog contents
	private static final String BOX_DIALOG_QUIT_TITLE = "Bejeweled - Quitter";
	private static final String BOX_DIALOG_QUIT_MESSAGE = "Êtes-vous sûr de vouloir quitter l'application?";
	private static final String BOX_DIALOG_BUTTON_YES = "OUI";
	private static final String BOX_DIALOG_BUTTON_NO = "NON";

	private static final String BOX_DIALOG_REGISTER_TITLE = "Bejeweled - Pseudo";
	private static final String BOX_DIALOG_REGISTER_ALERT = "Veuillez saisir un pseudo valide!";

	private IMenuService menuService;
	private SessionManager session;

	public IMenuService getMenuService() {
		if (menuService == null) {
			menuService = new MenuService(getActivity().getApplicationContext());
		}
		return menuService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

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
		default:
			break;
		}
		return alertDialog;
	}

	private AlertDialog initRegisterBoxDialog(AlertDialog alertDialog) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = getActivity().getLayoutInflater();

		alertDialog.setView(inflater.inflate(R.layout.register_dialog, null));
		alertDialog.setTitle(BOX_DIALOG_REGISTER_TITLE);
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();

		Button button_ok = (Button) alertDialog
				.findViewById(R.id.register_dialog_butt);
		button_ok.setOnClickListener(onClickListener);

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

	private final OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.register_dialog_butt:
				View vParent = (View) v.getParent();
				boolean isRegister = false;
				EditText editText_pseudo = (EditText) vParent
						.findViewById(R.id.register_dialog_pseudo);

				Editable pseudo = editText_pseudo.getText();
				if (pseudo == null || pseudo.toString().isEmpty()
						|| pseudo.toString().length() < 4) {
					Toast.makeText(v.getContext(), BOX_DIALOG_REGISTER_ALERT,
							Toast.LENGTH_SHORT).show();
				} else {
					System.out.println("******************pseudo:"
							+ pseudo.toString() + "*****************");
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
					dismiss();
				}

				break;

			default:
				break;
			}
		}
	};
}
