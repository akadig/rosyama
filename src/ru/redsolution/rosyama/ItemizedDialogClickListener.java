package ru.redsolution.rosyama;

/**
 * Слушатель выбора элемента списка.
 * 
 * @author alexander.ivanov
 * 
 */
public interface ItemizedDialogClickListener {
	/**
	 * Был выбран элемент.
	 * 
	 * @param itemizedDialogBuilder
	 * @param selected
	 *            Номер элемента.
	 */
	void onSelect(ItemizedDialogBuilder builder, int selected);

	/**
	 * Диалог был отменен.
	 * 
	 * @param itemizedDialogBuilder
	 */
	void onCancel(ItemizedDialogBuilder builder);
}
