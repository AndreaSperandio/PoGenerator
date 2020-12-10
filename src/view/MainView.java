package view;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.component.PGButton;
import view.util.PGColor;
import view.util.PGLocalizator;
import view.util.PGMessage;
import view.util.PGResource;

public class MainView extends JFrame {
	private static final long serialVersionUID = 846132440578478084L;

	private static final PGLocalizator LOC = new PGLocalizator(MainView.class);
	private static final String DESKTOP_FOLDER = System.getProperty("user.home") + File.separator + "Desktop";
	private static final String TRANS_SEPARATOR = "\t";
	private static final String TXT_EXTENSION = ".txt";
	private static final String PO_EXTENSION = ".po";
	private static final String FILE_ENCODING = "UTF-8";

	private final JLabel lblInstruction = new JLabel(MainView.LOC.getRes("lblInstruction"));
	private final JLabel lblTranslated = new JLabel(MainView.LOC.getRes("lblTranslated"));
	private final PGButton btnTranslated = new PGButton(MainView.LOC.getRes("btnLoad"));
	private final JLabel lblTranslatedFile = new JLabel();
	private final JLabel lblPo = new JLabel(MainView.LOC.getRes("lblPo"));
	private final PGButton btnPo = new PGButton(MainView.LOC.getRes("btnLoad"));
	private final JLabel lblPoFile = new JLabel();
	private final PGButton btnMatch = new PGButton(PGResource.getStartImage());
	private final JLabel lblMatch = new JLabel(MainView.LOC.getRes("lblMatch"));
	private final JLabel lblExportPath = new JLabel(MainView.LOC.getRes("lblExportPath"));
	private final JLabel lblExportPathWrn = new JLabel(MainView.LOC.getRes("lblExportPathWrn"));
	private final PGButton btnExportPath = new PGButton(MainView.LOC.getRes("btnExportPath"));
	private final JLabel lblExportFile = new JLabel();
	private final PGButton btnExport = new PGButton(PGResource.getExportImage());
	private final JLabel lblExport = new JLabel(MainView.LOC.getRes("lblExport"));
	private final JLabel lblAuthor = new JLabel(MainView.LOC.getRes("lblAuthor"));

	private File translatedFile = null;
	private File poFile = null;
	private File exportFile = null;
	private String translatedPo = null;

	public MainView() {
		this.setup();
		this.init();
	}

	private void setup() {
		this.setTitle(MainView.LOC.getRes("title"));
		final Dimension dimension = new Dimension(630, 610);// Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(dimension);
		this.setPreferredSize(dimension);
		// this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				//if (TMMessage.showConfirmWarnDialog(MainView.this, MainView.LOC.getRes("cnfExit"))) {
				MainView.this.dispose();
				System.exit(0);
				//}
			}
		});
		this.setLayout(null);

		this.add(this.lblInstruction);
		this.add(this.lblTranslated);
		this.add(this.btnTranslated);
		this.add(this.lblTranslatedFile);
		this.add(this.lblPo);
		this.add(this.btnPo);
		this.add(this.lblPoFile);
		this.add(this.btnMatch);
		this.add(this.lblMatch);
		this.add(this.lblExportPath);
		this.add(this.lblExportPathWrn);
		this.add(this.btnExportPath);
		this.add(this.lblExportFile);
		this.add(this.btnExport);
		this.add(this.lblExport);
		this.add(this.lblAuthor);

		final int height = 20;
		final int margin = height + 10;
		final int x = 20;
		int y = 10;
		this.lblInstruction.setBounds(x, y, 600, height * 8);
		y += 180;
		this.lblTranslated.setBounds(x, y, 500, height);
		y += margin;
		this.btnTranslated.setBounds(x, y, 100, height);
		this.lblTranslatedFile.setBounds(x + 120, y, 400, height);
		y += margin;
		this.lblPo.setBounds(x, y, 500, height);
		y += margin;
		this.btnPo.setBounds(x, y, 100, height);
		this.lblPoFile.setBounds(x + 120, y, 400, height);
		y += 50;
		this.btnMatch.setBounds(x, y, 35, height + 10);
		this.lblMatch.setBounds(x + 45, y, 200, height + 10);
		y += 70;
		this.lblExportPath.setBounds(x, y, 100, height);
		this.lblExportPathWrn.setBounds(x + 120, y, 400, height);
		y += margin;
		this.btnExportPath.setBounds(x, y, 100, height);
		this.lblExportFile.setBounds(x + 120, y, 400, height);
		y += 50;
		this.btnExport.setBounds(x, y, 35, height + 10);
		this.lblExport.setBounds(x + 45, y, 200, height + 10);
		y += 50;
		this.lblAuthor.setBounds(dimension.width - 150, y, 120, height);

		this.lblTranslated.setToolTipText(MainView.LOC.getRes("lblTranslatedToolTip"));
		this.lblPo.setToolTipText(MainView.LOC.getRes("lblPoToolTip"));
		this.lblMatch.setToolTipText(MainView.LOC.getRes("lblMatchToolTip"));
		this.lblExportPath.setToolTipText(MainView.LOC.getRes("lblExportPathToolTip"));

		this.lblTranslatedFile.setForeground(PGColor.LBL_BLUE);
		this.lblPoFile.setForeground(PGColor.LBL_BLUE);
		this.lblExportFile.setForeground(PGColor.LBL_BLUE);
		this.lblExportPathWrn.setForeground(PGColor.LBL_ORANGE);
		this.lblAuthor.setForeground(PGColor.LBL_BLUE);

		this.btnTranslated.addActionListener(e -> {
			this.btnFileChooserActionPerformed(this.btnTranslated, MainView.LOC.getRes("jfcTranslated"),
					this.lblTranslatedFile, "Text Files", "txt");
			this.updateGraphics();
		});
		this.btnPo.addActionListener(e -> {
			this.btnFileChooserActionPerformed(this.btnPo, MainView.LOC.getRes("jfcPo"), this.lblPoFile, "Po Files",
					"po");
			this.updateGraphics();
		});
		this.btnMatch.addActionListener(e -> {
			this.btnMatchActionPerformed();
			this.updateGraphics();
		});
		this.btnExportPath.addActionListener(e -> {
			this.btnFileChooserActionPerformed(this.btnExport, MainView.LOC.getRes("jfcExport"), this.lblExportFile,
					"Po Files", "po");
			this.updateGraphics();
		});
		this.btnExport.addActionListener(e -> this.btnExportActionPerformed());
	}

	private void init() {
		this.updateGraphics();
		this.setVisible(true);
	}

	private void updateGraphics() {
		this.btnMatch.setEnabled(this.translatedFile != null && this.poFile != null);
		this.btnExportPath.setEnabled(this.translatedPo != null && !this.translatedPo.isEmpty());
		this.btnExport.setEnabled(this.exportFile != null);

		this.lblExportPathWrn.setVisible(this.checkFile(this.exportFile, false, MainView.PO_EXTENSION));
	}

	private void btnFileChooserActionPerformed(final PGButton caller, final String title, final JLabel label,
			final String extensionDesc, final String extension) {
		final FileNameExtensionFilter filter = new FileNameExtensionFilter(extensionDesc, extension);
		final JFileChooser jfc = new JFileChooser(MainView.DESKTOP_FOLDER);
		jfc.setFileFilter(filter);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setMultiSelectionEnabled(false);
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setDialogTitle(title);

		final int retVaule = jfc.showOpenDialog(caller);
		if (retVaule == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			if (caller == this.btnTranslated) {
				this.translatedFile = file;
				this.translatedPo = null;
			} else if (caller == this.btnPo) {
				this.poFile = file;
				this.translatedPo = null;
			} else {
				if (!file.getName().endsWith(MainView.PO_EXTENSION)) {
					file = new File(file.getAbsolutePath() + MainView.PO_EXTENSION);
				}
				this.exportFile = file;
			}
			label.setText(file.getName());
		} else if (retVaule == JFileChooser.CANCEL_OPTION) {
			if (caller == this.btnTranslated) {
				this.translatedFile = null;
				this.translatedPo = null;
			} else if (caller == this.btnPo) {
				this.poFile = null;
				this.translatedPo = null;
			} else {
				this.exportFile = null;
			}
			label.setText(null);
		}
	}

	private void btnMatchActionPerformed() {
		if (!this.checkParams()) {
			return;
		}

		final Map<String, String> translations = this.scanFile(this.translatedFile);
		if (translations.isEmpty()) {
			PGMessage.showInfoDialog(this, MainView.LOC.getRes("infNothingToTranslate"));
		}

		int count = 0;
		final StringBuilder toWrite = new StringBuilder();
		try (FileInputStream fis = new FileInputStream(this.poFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis, MainView.FILE_ENCODING));) {
			while (br.ready()) {
				final String line = br.readLine();
				toWrite.append(line + System.lineSeparator());
				if (line != null && line.startsWith("msgid")) {
					final String[] lineSplit = line.split("msgid");
					if (lineSplit.length != 2) {
						continue;
					}

					final String id = lineSplit[1].trim().replace("\"", "");
					if (!translations.containsKey(id)) {
						continue;
					}

					if (br.ready()) {
						final String nextLine = br.readLine();
						if (nextLine != null && nextLine.startsWith("msgstr")) {
							final String[] nextLineSplit = nextLine.split("msgstr");
							if (nextLineSplit.length == 2 && "".equals(nextLineSplit[1].trim().replace("\"", ""))) {
								toWrite.append("msgstr" + " \"" + translations.get(id) + "\"" + System.lineSeparator());
								count++;
							} else {
								toWrite.append(nextLine + System.lineSeparator());
							}
						}
					}
				}
			}
		} catch (final FileNotFoundException e) {
			PGMessage.showErrDialog(this, MainView.LOC.getRes("errInternalError"));
			e.printStackTrace();
		} catch (final IOException e) {
			PGMessage.showErrDialog(this, MainView.LOC.getRes("errInternalError"));
			e.printStackTrace();
		}

		this.translatedPo = toWrite.toString();
		PGMessage.showInfoDialog(this, MainView.LOC.getRes("infNTranslated", count));
	}

	private void btnExportActionPerformed() {
		if (!this.checkExportParams()) {
			return;
		}

		if (this.writeToFile(this.exportFile, this.translatedPo)) {
			if (PGMessage.showConfirmDialog(this, MainView.LOC.getRes("cnfExported"))) {
				try {
					Desktop.getDesktop().open(this.exportFile);
				} catch (final IOException e) {
					PGMessage.showErrDialog(this, "errInternalError");
					e.printStackTrace();
				}
			}

			this.lblTranslatedFile.setText(null);
			this.lblPoFile.setText(null);
			this.lblExportFile.setText(null);
			this.translatedFile = null;
			this.poFile = null;
			this.exportFile = null;
			this.translatedPo = null;
			this.updateGraphics();
		}
	}

	private boolean checkParams() {
		if (!this.checkFile(this.translatedFile, false, MainView.TXT_EXTENSION)) {
			PGMessage.showErrDialog(this, MainView.LOC.getRes("errTranslatedFile"));
			return false;
		}
		if (!this.checkFile(this.poFile, false, MainView.PO_EXTENSION)) {
			PGMessage.showErrDialog(this, MainView.LOC.getRes("errPoFile"));
			return false;
		}

		return true;
	}

	private boolean checkExportParams() {
		if (!this.checkFile(this.exportFile, true, MainView.PO_EXTENSION)) {
			PGMessage.showErrDialog(this, MainView.LOC.getRes("errExportPath"));
			return false;
		}

		return true;
	}

	private boolean checkFile(final File file, final boolean createIfAbsent, final String fileExtension) {
		if (file == null || !file.exists() || !file.isFile()) {
			if (createIfAbsent) {
				try {
					file.createNewFile();
					return true;
				} catch (final IOException e) {
					PGMessage.showErrDialog(this, "errInternalError");
					e.printStackTrace();
				}
			}
			return false;
		}

		return file.getName().endsWith(fileExtension);
	}

	private Map<String, String> scanFile(final File file) {
		final Map<String, String> map = new LinkedHashMap<>();
		if (file == null) {
			return map;
		}

		try (FileInputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis, MainView.FILE_ENCODING));) {
			while (br.ready()) {
				final String line = br.readLine();
				if (line != null) {
					final String[] lineSplit = line.split(MainView.TRANS_SEPARATOR);
					map.put(lineSplit[0], lineSplit.length > 1 ? lineSplit[1] : null);
				}
			}
		} catch (final FileNotFoundException e) {
			PGMessage.showErrDialog(this, MainView.LOC.getRes("errInternalError"));
			e.printStackTrace();
		} catch (final IOException e) {
			PGMessage.showErrDialog(this, MainView.LOC.getRes("errInternalError"));
			e.printStackTrace();
		}
		return map;
	}

	private boolean writeToFile(final File file, final String string) {
		if (file == null) {
			return false;
		}

		try (FileOutputStream fos = new FileOutputStream(file);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, MainView.FILE_ENCODING));) {
			bw.write(string);
			return true;
		} catch (final IOException e) {
			PGMessage.showErrDialog(this, MainView.LOC.getRes("errInternalError"));
			e.printStackTrace();
		}
		return false;
	}

	public static void main(final String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				final MainView frame = new MainView();
				frame.setVisible(true);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
	}
}
