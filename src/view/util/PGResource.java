package view.util;

import java.net.URL;

import javax.swing.ImageIcon;

import view.MainView;

public class PGResource {
	private PGResource() {
	}

	public static ImageIcon getStartImage() {
		return new ImageIcon(PGResource.get("start.png"));
	}

	public static ImageIcon getStopImage() {
		return new ImageIcon(PGResource.get("stop.png"));
	}

	public static ImageIcon getResetImage() {
		return new ImageIcon(PGResource.get("reset.png"));
	}

	public static ImageIcon getExportImage() {
		return new ImageIcon(PGResource.get("export.png"));
	}

	private static URL get(final String resource) {
		final URL res = MainView.class.getResource("/resources/img/" + resource);
		return res != null ? res : MainView.class.getResource("/resources/img/notFound.png");
	}
}
