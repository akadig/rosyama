package ru.redsolution.rosyama;

import ru.redsolution.rosyama.data.Rosyama;
import ru.redsolution.rosyama.data.UpdateListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Main extends Activity implements OnClickListener,
		YesNoDialogClickListener, UpdateListener {
	/**
	 * Диалог с сообщением перед созданием дефекта.
	 */
	private static final int DIALOG_CREATE_ID = 0;

	/**
	 * Диалог с выбором источника фотографии.
	 */
	private static final int DIALOG_PHOTO_ID = 1;

	/**
	 * Выход из текущей сессии.
	 */
	private static final int OPTION_MENU_LOGOUT_ID = 0;

	/**
	 * Выход из текущей сессии.
	 */
	private static final int OPTION_MENU_ABOUT_ID = 1;

	/**
	 * Приложение.
	 */
	private Rosyama rosyama;

	/**
	 * Помошник создания фотографии.
	 */
	private PhotoDialogHelper photoDialogHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		rosyama = (Rosyama) getApplication();

		findViewById(R.id.create).setOnClickListener(this);
		findViewById(R.id.list).setOnClickListener(this);

		photoDialogHelper = new PhotoDialogHelper(this, DIALOG_PHOTO_ID,
				savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		rosyama.setUpdateListener(this);
		InterfaceUtilities.setTiledBackground(this, R.id.background,
				R.drawable.background);
		InterfaceUtilities.setTiledBackground(this, R.id.dot, R.drawable.dot);
		InterfaceUtilities.setTiledBackground(this, R.id.dot2, R.drawable.dot);
	}

	@Override
	protected void onPause() {
		super.onPause();
		rosyama.setUpdateListener(null);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		photoDialogHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Uri uri = photoDialogHelper.getResultUri(requestCode, resultCode, data);
		if (uri != null) {
			rosyama.createHole(uri);
			Intent intent = new Intent(this, HoleEdit.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, OPTION_MENU_LOGOUT_ID, 0, getString(R.string.logout))
				.setIcon(R.drawable.ic_menu_login);
		menu.add(0, OPTION_MENU_ABOUT_ID, 0, getString(R.string.about_title))
				.setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case OPTION_MENU_LOGOUT_ID:
			rosyama.logout();
			return true;
		case OPTION_MENU_ABOUT_ID:
			Intent intent = new Intent(this, About.class);
			startActivity(intent);
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.create:
			showDialog(DIALOG_CREATE_ID);
			break;
		case R.id.list:
			intent = new Intent(this, HoleList.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		super.onCreateDialog(id);
		switch (id) {
		case DIALOG_CREATE_ID:
			return new YesNoDialogBuilder(this, this, DIALOG_CREATE_ID,
					getString(R.string.hole_create_dialog)).create();
		case DIALOG_PHOTO_ID:
			return photoDialogHelper.create();
		default:
			return null;
		}
	}

	@Override
	public void onAccept(YesNoDialogBuilder builder) {
		switch (builder.getDialogId()) {
		case DIALOG_CREATE_ID:
			showDialog(DIALOG_PHOTO_ID);
			break;
		}
	}

	@Override
	public void onDecline(YesNoDialogBuilder builder) {
	}

	@Override
	public void onCancel(YesNoDialogBuilder builder) {
	}

	@Override
	public void onUpdate() {
		if (!rosyama.getAuthorizeOperation().isComplited()) {
			Intent intent = new Intent(this, Auth.class);
			startActivity(intent);
			finish();
			return;
		}
	}
}