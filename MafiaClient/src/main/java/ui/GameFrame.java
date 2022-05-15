package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameFrame extends JFrame {

	Image leftBackImg;
	JPanel leftPanel;
	JPanel rightPanel;
	JTextArea chatArea;
	JTextField chatTf;

	public GameFrame() {
		setTitle("Mafia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(800, 500);
		setLayout(new GridLayout(0, 2));

		newComponents();
		setComponents();
		addComponents();

		setVisible(true);

		chatArea.setText("chat\nchat");
	}

	public void newComponents() {
		leftBackImg = new ImageIcon("backgroundImage/leftPanelBack.png").getImage();
		leftPanel = new LeftPanel();
		rightPanel = new JPanel();
		chatArea = new JTextArea();
		chatTf = new JTextField();
	}

	public void setComponents() {

		leftPanel.setLayout(null);

		chatArea.setOpaque(false);
		chatArea.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		chatArea.setFont(new Font("", Font.BOLD, 20));
		chatArea.setForeground(Color.white);

	}

	public void addComponents() {
		add(leftPanel);
		add(rightPanel);
		leftPanel.add(chatArea);
		leftPanel.add(chatTf);

	}

	class LeftPanel extends JPanel {
		public LeftPanel() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(leftBackImg, 0, 0, this);
		}
	}

	public static void main(String[] args) {
		new GameFrame();
	}

}
