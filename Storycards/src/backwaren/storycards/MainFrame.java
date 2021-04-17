/*
    Storycards - A simple story card editor.
    
    Copyright (C) 2020 bennibacktbackwaren
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
package backwaren.storycards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.InputEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class MainFrame extends JFrame {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -1660038586421324393L;
	private final MainFrame _this = this;
	private JPanel contentPane;
	private JTextField textFieldPriority;
	private JTextField textFieldName;
	private JTextField textFieldRisk;
	private JTextArea textAreaBack;
	private JTextArea textAreaDescription;
	private JSpinner spinnerStoryPoints;
	private JSpinner spinnerStoryPointsPost;
	private final Action showVersionInformationAction = new ShowVersionInformationAction();
	private final Action createNewStorycardAction = new CreateNewStorycardAction();
	private final Action openStorycardAction = new OpenStorycardAction();
	private final Action saveStorycardAction = new SaveStorycardAction();
	private final Action saveStorycardAsAction = new SaveStorycardAsAction();
	private final Action exportStorycardAction = new ExportStorycardAction();
	private final Action exitApplicationAction = new ExitApplicationAction();

	/**
	 * The last file used.
	 */
	private File lastFileName = null;

	/**
	 * @brief Gets the last file.
	 * @return the lastFileName
	 */
	public File getLastFileName() {
		return lastFileName;
	}

	/**
	 * @brief Sets the last file.
	 * @param lastFileName the lastFileName to set
	 */
	public void setLastFileName(File lastFileName) {
		this.lastFileName = lastFileName;
	}

	/**
	 * The current story card.
	 */
	private Storycard storycard;

	/**
	 * @brief Gets the current story card.
	 * @return the storycard
	 */
	public Storycard getStorycard() {
		storycard.setPriority(textFieldPriority.getText());
		storycard.setName(textFieldName.getText());
		storycard.setStoryPoints((int) spinnerStoryPoints.getValue());
		storycard.setDescription(textAreaDescription.getText());
		storycard.setRisk(textFieldRisk.getText());
		storycard.setStoryPointsPost((int) spinnerStoryPointsPost.getValue());
		storycard.setBack(textAreaBack.getText());
		return storycard;
	}

	/**
	 * @brief Sets the story card and updates the ui.
	 * @param storycard the storycard to set
	 */
	public void setStorycard(Storycard storycard) {
		this.storycard = storycard;

		// Update ui
		this.textFieldPriority.setText(this.storycard.getPriority());
		this.textFieldName.setText(this.storycard.getName());
		this.spinnerStoryPoints.setValue(this.storycard.getStoryPoints());
		this.textAreaDescription.setText(this.storycard.getDescription());
		this.textFieldRisk.setText(this.storycard.getRisk());
		this.spinnerStoryPointsPost.setValue(this.storycard.getStoryPointsPost());
		this.textAreaBack.setText(this.storycard.getBack());
	}

	/**
	 * Indicates whether the story card was modified.
	 */
	private boolean modified;

	/**
	 * @param modified the modified to set
	 */
	public void setModified(boolean modified) {
		this.modified = modified;

		// Update the title
		String title = "Storycards";
		title += modified ? "*" : "";

		this.setTitle(title);
	}

	/**
	 * @return the modified
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "An unexpected error occurred",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Storycards");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnfile = new JMenu("File");
		mnfile.setMnemonic('F');
		menuBar.add(mnfile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setAction(createNewStorycardAction);
		mntmNew.setMnemonic('N');
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnfile.add(mntmNew);

		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.setAction(openStorycardAction);
		mntmOpen.setMnemonic('O');
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnfile.add(mntmOpen);

		JSeparator separator = new JSeparator();
		mnfile.add(separator);

		JMenuItem mntmSave = new JMenuItem("Save...");
		mntmSave.setAction(saveStorycardAction);
		mntmSave.setMnemonic('S');
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnfile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.setAction(saveStorycardAsAction);
		mntmSaveAs.setMnemonic('A');
		mnfile.add(mntmSaveAs);

		JMenuItem mntmExport = new JMenuItem("Export...");
		mntmExport.setAction(exportStorycardAction);
		mntmExport.setMnemonic('E');
		mntmExport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnfile.add(mntmExport);

		JSeparator separator_1 = new JSeparator();
		mnfile.add(separator_1);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAction(exitApplicationAction);
		mntmExit.setMnemonic('E');
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mnfile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		mnHelp.setMnemonic('H');
		menuBar.add(mnHelp);

		JMenuItem mntmAboutStorycards = new JMenuItem("About Storycards...");
		mntmAboutStorycards.setAction(showVersionInformationAction);
		mntmAboutStorycards.setMnemonic('A');
		mntmAboutStorycards.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnHelp.add(mntmAboutStorycards);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel frontPanel = new JPanel();
		tabbedPane.addTab("   Front   ", null, frontPanel, null);
		frontPanel.setLayout(new MigLayout("", "[center][grow,center][center]", "[][][][grow][][]"));

		JLabel lblPriority = new JLabel("Priority");
		frontPanel.add(lblPriority, "cell 0 0");

		JLabel lblName = new JLabel("Name");
		frontPanel.add(lblName, "cell 1 0");

		JLabel lblStoryPoints = new JLabel("Story Points");
		frontPanel.add(lblStoryPoints, "cell 2 0");

		textFieldPriority = new JTextField();
		textFieldPriority.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				_this.setModified(true);
			}
		});
		frontPanel.add(textFieldPriority, "cell 0 1,growx");
		textFieldPriority.setColumns(10);

		textFieldName = new JTextField();
		textFieldName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				_this.setModified(true);
			}
		});
		frontPanel.add(textFieldName, "cell 1 1,growx");
		textFieldName.setColumns(10);

		spinnerStoryPoints = new JSpinner();
		spinnerStoryPoints.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				_this.setModified(true);
			}
		});
		spinnerStoryPoints.setMinimumSize(new Dimension(65, 20));
		frontPanel.add(spinnerStoryPoints, "cell 2 1");

		JLabel lblDescription = new JLabel("Description");
		frontPanel.add(lblDescription, "cell 1 2");

		JScrollPane scrollPaneDescription = new JScrollPane();
		frontPanel.add(scrollPaneDescription, "cell 0 3 3 1,grow");

		textAreaDescription = new JTextArea();
		textAreaDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				_this.setModified(true);
			}
		});
		scrollPaneDescription.setViewportView(textAreaDescription);

		JLabel lblRisk = new JLabel("Risk");
		frontPanel.add(lblRisk, "cell 0 4");

		JLabel lblStoryPointspost = new JLabel("Story Points (Post)");
		frontPanel.add(lblStoryPointspost, "cell 2 4");

		textFieldRisk = new JTextField();
		textFieldRisk.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				_this.setModified(true);
			}
		});
		frontPanel.add(textFieldRisk, "cell 0 5,growx");
		textFieldRisk.setColumns(10);

		spinnerStoryPointsPost = new JSpinner();
		spinnerStoryPointsPost.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				_this.setModified(true);
			}
		});
		spinnerStoryPointsPost.setMinimumSize(new Dimension(65, 20));
		frontPanel.add(spinnerStoryPointsPost, "cell 2 5");

		JPanel backPanel = new JPanel();
		tabbedPane.addTab("   Back   ", null, backPanel, null);
		backPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneBack = new JScrollPane();
		backPanel.add(scrollPaneBack, BorderLayout.CENTER);

		textAreaBack = new JTextArea();
		textAreaBack.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				_this.setModified(true);
			}
		});
		scrollPaneBack.setViewportView(textAreaBack);

		// Create a new story card
		this.createNewStorycardAction.actionPerformed(new ActionEvent(this, 0, ""));
	}

	private class ShowVersionInformationAction extends AbstractAction {
		/**
		 * The serialization id.
		 */
		private static final long serialVersionUID = 4366808786924940615L;

		public ShowVersionInformationAction() {
			putValue(NAME, "About Storycards...");
			putValue(SHORT_DESCRIPTION, "Displays the version information");
		}

		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(_this, "Version 1.0\r\nCopyright © 2021 bennibacktbackwaren", "About Storycards", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class CreateNewStorycardAction extends AbstractAction {
		/**
		 * The serialization id.
		 */
		private static final long serialVersionUID = -3696647291224390174L;

		public CreateNewStorycardAction() {
			putValue(NAME, "New");
			putValue(SHORT_DESCRIPTION, "Creates a new story card");
		}

		public void actionPerformed(ActionEvent e) {
			if (_this.isModified()) {
				int res = JOptionPane.showConfirmDialog(_this,
						"Do you want to save changes to '" + _this.getStorycard().getName() + "'?", "Storycards",
						JOptionPane.YES_NO_CANCEL_OPTION);

				switch (res) {
				case JOptionPane.YES_OPTION:
					_this.saveStorycardAction.actionPerformed(e);
					break;

				case JOptionPane.CANCEL_OPTION:
					return;

				case JOptionPane.NO_OPTION:
				default:
					break;
				}
			}

			// Create a new story card and update the ui
			_this.setStorycard(new Storycard());
			_this.setModified(false);
		}
	}

	private class OpenStorycardAction extends AbstractAction {
		/**
		 * The serialization id.
		 */
		private static final long serialVersionUID = -3141516381237062315L;

		public OpenStorycardAction() {
			putValue(NAME, "Open...");
			putValue(SHORT_DESCRIPTION, "Opens a story card");
		}

		public void actionPerformed(ActionEvent e) {
			// Get the file name
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("Storycards (*.sc)", "sc"));

			if (fc.showOpenDialog(_this) != JFileChooser.APPROVE_OPTION)
				return;

			// Deserialize the xml
			XMLDecoder dec = null;
			try {
				dec = new XMLDecoder(new FileInputStream(fc.getSelectedFile()));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(_this, ex.getMessage(), "An unexpected error occurred",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Storycard card = (Storycard) dec.readObject();
			dec.close();

			// Update the ui
			_this.setStorycard(card);
			_this.setModified(false);
		}
	}

	private class SaveStorycardAction extends AbstractAction {
		/**
		 * The serialization id.
		 */
		private static final long serialVersionUID = -1496239788780589882L;

		public SaveStorycardAction() {
			putValue(NAME, "Save...");
			putValue(SHORT_DESCRIPTION, "Saves the story card");
		}

		public void actionPerformed(ActionEvent e) {
			if (_this.lastFileName == null) {
				_this.saveStorycardAsAction.actionPerformed(e);
				return;
			}

			// Get the story card
			Storycard card = _this.getStorycard();

			// Serialize the card into a xml
			XMLEncoder enc = null;
			try {
				enc = new XMLEncoder(new FileOutputStream(lastFileName));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(_this, ex.getMessage(), "An unexpected error occurred",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			enc.writeObject(card);
			enc.close();

			// Remove modified flag
			_this.setModified(false);
		}
	}

	private class ExportStorycardAction extends AbstractAction {
		/**
		 * The serialization id.
		 */
		private static final long serialVersionUID = -5613312873791710707L;

		public ExportStorycardAction() {
			putValue(NAME, "Export...");
			putValue(SHORT_DESCRIPTION, "Exports the story card");
		}

		/**
		 * Draw a String centered in the middle of a Rectangle.
		 *
		 * @param g    The Graphics instance.
		 * @param text The String to draw.
		 * @param rect The Rectangle to center the text in.
		 */
		public void drawCenteredString(Graphics2D g, String text, Rectangle rect) {
			// Get the FontMetrics
			FontMetrics metrics = g.getFontMetrics();
			int y = 90;

			for (String s : text.split("\n")) {
				// Determine the X coordinate for the text
				int x = rect.x + (rect.width - metrics.stringWidth(s)) / 2;
				// Draw the String
				g.drawString(s, x, y);
				// Translate the text
				y += metrics.getHeight() + metrics.getAscent();
			}
		}

		public void actionPerformed(ActionEvent e) {
			// Get the file name
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (fc.showSaveDialog(_this) != JFileChooser.APPROVE_OPTION)
				return;

			// Get the story card
			Storycard card = _this.getStorycard();

			// Create an image
			BufferedImage bi = new BufferedImage(500, 350, BufferedImage.TYPE_INT_ARGB);
			BufferedImage bi_back = new BufferedImage(500, 350, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = bi.createGraphics();
			Graphics2D g_back = bi_back.createGraphics();

			// Enable anti aliasing
			g.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g_back.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			// Clear background
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 500, 350);
			// Draw black border
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 499, 349);
			g.drawRect(1, 1, 497, 347);
			// Draw upper bar
			g.drawRect(0, 0, 50, 50);
			g.drawRect(1, 1, 48, 48);
			g.drawRect(70, 0, 360, 50);
			g.drawRect(71, 1, 358, 48);
			g.drawRect(449, 0, 50, 50);
			g.drawRect(450, 1, 48, 48);
			// Draw lower bar
			g.drawRect(0, 299, 50, 50);
			g.drawRect(1, 300, 48, 48);
			g.drawRect(449, 299, 50, 50);
			g.drawRect(450, 300, 48, 48);
			// Draw the strings
			var m = g.getFontMetrics();
			int h = m.getHeight();
			int w = 0;

			/* priority */
			w = m.stringWidth(card.getPriority());
			g.drawString(card.getPriority(), 1 + 24 - w / 2, 24 + h / 2 - 2);
			/* name */
			w = m.stringWidth(card.getName());
			g.drawString(card.getName(), 71 + 180 - w / 2, 24 + h / 2 - 2);
			/* story points */
			w = m.stringWidth(String.valueOf(card.getStoryPoints()));
			g.drawString(String.valueOf(card.getStoryPoints()), 450 + 24 - w / 2, 24 + h / 2 - 2);
			/* description */
			drawCenteredString(g, card.getDescription(), new Rectangle(2, 2, 498, 248));
			/* risk */
			w = m.stringWidth(card.getRisk());
			g.drawString(card.getRisk(), 1 + 24 - w / 2, 300 + 24 + h / 2 - 2);
			/* story points post */
			w = m.stringWidth(String.valueOf(card.getStoryPointsPost()));
			g.drawString(String.valueOf(card.getStoryPointsPost()), 450 + 24 - w / 2, 300 + 24 + h / 2 - 2);

			// Draw the back side
			
			// Clear background
			g_back.setColor(Color.WHITE);
			g_back.fillRect(0, 0, 500, 350);
			// Draw black border
			g_back.setColor(Color.BLACK);
			g_back.drawRect(0, 0, 499, 349);
			g_back.drawRect(1, 1, 497, 347);
			
			/* back */
			drawCenteredString(g_back, card.getBack(), new Rectangle(2, 2, 498, 348));
			
			// Write the output
			try {
				ImageIO.write(bi, "PNG",
						new File(fc.getSelectedFile() + File.separator + card.getName() + "_front.png"));
				ImageIO.write(bi_back, "PNG",
						new File(fc.getSelectedFile() + File.separator + card.getName() + "_back.png"));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "An unexpected error occurred",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
	}

	private class ExitApplicationAction extends AbstractAction {
		/**
		 * The serialization id.
		 */
		private static final long serialVersionUID = 8897186784834748276L;

		public ExitApplicationAction() {
			putValue(NAME, "Exit");
			putValue(SHORT_DESCRIPTION, "Quits the application");
		}

		public void actionPerformed(ActionEvent e) {
			if (_this.isModified()) {
				int res = JOptionPane.showConfirmDialog(_this,
						"Do you want to save changes to '" + _this.getStorycard().getName() + "'?", "Storycards",
						JOptionPane.YES_NO_CANCEL_OPTION);

				switch (res) {
				case JOptionPane.YES_OPTION:
					_this.saveStorycardAction.actionPerformed(e);
					break;

				case JOptionPane.CANCEL_OPTION:
					return;

				case JOptionPane.NO_OPTION:
				default:
					break;
				}
			}

			System.exit(0);
		}
	}

	private class SaveStorycardAsAction extends AbstractAction {
		/**
		 * The serialization id.
		 */
		private static final long serialVersionUID = 4644624988378350223L;

		public SaveStorycardAsAction() {
			putValue(NAME, "Save As...");
			putValue(SHORT_DESCRIPTION, "Saves the story card under a new location");
		}

		public void actionPerformed(ActionEvent e) {
			// Get the file name
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("Storycards (*.sc)", "sc"));

			if (fc.showSaveDialog(_this) != JFileChooser.APPROVE_OPTION)
				return;

			_this.lastFileName = fc.getSelectedFile();

			// Invoke the save action
			_this.saveStorycardAction.actionPerformed(e);
		}
	}
}
