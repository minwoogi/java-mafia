package ui;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class GameChatPanel extends JPanel {
	public GameChatPanel() {
		setLayout(new GridLayout(0,1,0,0));
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
