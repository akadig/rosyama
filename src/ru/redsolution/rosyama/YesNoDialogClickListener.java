package ru.redsolution.rosyama;

/**
 * Слушатель ответа на диалог.
 * 
 * @author alexander.ivanov
 * 
 */
public interface YesNoDialogClickListener {
	/**
	 * Запрос был утвержден.
	 * 
	 * @param builder
	 */
	void onAccept(YesNoDialogBuilder builder);

	/**
	 * Запрос был отвергнут.
	 * 
	 * @param builder
	 */
	void onDecline(YesNoDialogBuilder builder);

	/**
	 * Диалог был закрыт.
	 * 
	 * @param builder
	 */
	void onCancel(YesNoDialogBuilder builder);
}
