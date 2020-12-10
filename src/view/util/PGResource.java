package view.util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
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

	public static List<? extends Image> getLogoIcons() {
		final List<Image> images = new ArrayList<>();
		try {
			images.add(ImageIO.read(PGResource.get("logo_16.png")));
			images.add(ImageIO.read(PGResource.get("logo_32.png")));
			images.add(ImageIO.read(PGResource.get("logo_64.png")));
			images.add(ImageIO.read(PGResource.get("logo_128.png")));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return images;
	}

	private static URL get(final String resource) {
		final URL res = MainView.class.getResource("/resources/img/" + resource);
		return res != null ? res : MainView.class.getResource("/resources/img/notFound.png");
	}
}
