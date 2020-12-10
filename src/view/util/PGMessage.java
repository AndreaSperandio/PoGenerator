package view.util;

import java.awt.Component;

import javax.swing.JOptionPane;

import view.MainView;

public class PGMessage {
	private static final PGLocalizator LOC = new PGLocalizator(PGMessage.class);

	public static boolean showConfirmDialog(final Component comp, String message) {
		message = message != null ? message.trim() : null;
		if (comp == null || message == null) {
			return false;
		}

		return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(comp, message,
				PGMessage.getMessageTitle(comp, "confirmDialog"), JOptionPane.YES_NO_OPTION);
	}

	public static boolean showConfirmWarnDialog(final Component comp, String message) {
		message = message != null ? message.trim() : null;
		if (comp == null || message == null) {
			return false;
		}

		return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(comp, message,
				PGMessage.getMessageTitle(comp, "confirmDialog"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
	}

	public static void showInfoDialog(final Component comp, final String message) {
		PGMessage.showDialog(comp, message, "infoDialog", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showWarnDialog(final Component comp, final String message) {
		PGMessage.showDialog(comp, message, "warnDialog", JOptionPane.WARNING_MESSAGE);
	}

	public static void showErrDialog(final Component comp, final String message) {
		PGMessage.showDialog(comp, message, "errDialog", JOptionPane.ERROR_MESSAGE);
	}

	private static void showDialog(final Component comp, String message, final String resTitle, final int msgType) {
		message = message != null ? message.trim() : null;
		if (comp == null || message == null) {
			return;
		}

		JOptionPane.showMessageDialog(comp, message, PGMessage.getMessageTitle(comp, resTitle), msgType);
	}

	private static String getMessageTitle(final Component comp, final String resTitle) {
		return comp instanceof MainView ? ((MainView) comp).getTitle() : PGMessage.LOC.getRes(resTitle);
	}
}