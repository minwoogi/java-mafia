package ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import handling.game.GameHandler;

public class GameChatPanel extends JPanel {
	public GameChatPanel() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setOpaque(false);
		setBackground(new Color(0,0,0,0));
		setVisible(true);
	}
	
	public void addTextPanel(TextPanel textPanel) {
		this.add(textPanel);
		this.revalidate();
		this.repaint();
		Runnable doScroll = new Runnable() {
			public void run() {
				GameHandler.getGameFrame().getScroll().getVerticalScrollBar().setValue(GameHandler.getGameFrame().getScroll().getVerticalScrollBar().getMaximum());
			}
		};
		SwingUtilities.invokeLater(doScroll);
	}
}
