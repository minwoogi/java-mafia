package ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import handling.game.GameHandler;

public class GameChatPanel extends JPanel {
	public GameChatPanel() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		setVisible(true);
	}

	public void addTextPanel(TextPanel textPanel) {
		this.add(textPanel);
		this.revalidate();
		this.repaint();
	}
}
