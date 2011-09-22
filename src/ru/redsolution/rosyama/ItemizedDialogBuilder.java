package ru.redsolution.rosyama;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Конструктор диалога со списком доступных действий.
 * 
 * @author alexander.ivanov
 * 
 */
public class ItemizedDialogBuilder extends AlertDialog.Builder implements
		DialogInterface.OnDismissListener, DialogInterface.OnClickListener {
	protected final Activity activity;
	protected final int dialogId;
	private ItemizedDialogClickListener listener;
	private Integer selected;

	public ItemizedDialogBuilder(Activity activity,
			ItemizedDialogClickListener listener, int dialogId, String title,
			CharSequence[] items) {
		super(activity);
		this.activity = activity;
		this.dialogId = dialogId;
		this.listener = listener;
		selected = null;
		setTitle(title);
		setItems(items, this);
	}

	ItemizedDialogClickListener getListener() {
		return listener;
	}

	void setListener(ItemizedDialogClickListener listener) {
		this.listener = listener;
	}

	@Override
	public AlertDialog create() {
		AlertDialog alertDialog = super.create();
		alertDialog.setOnDismissListener(this);
		return alertDialog;
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		activity.removeDialog(dialogId);
		if (listener == null)
			return;
		if (selected == null)
			listener.onCancel(this);
		else
			listener.onSelect(this, selected);
	}

	@Override
	public void onClick(DialogInterface dialog, int id) {
		selected = id;
		dialog.dismiss();
	}

	public int getDialogId() {
		return dialogId;
	}
}
