package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import handling.game.GameHandler;
import ui.MakeRoom.MoveWindows;

public class GameFrame extends JFrame {

	private JPanel leftPanel;
	private JPanel rightPanel;
	private JTextArea chatArea;
	private JTextField chatTf;
	private JButton sendBtn;
	private Point initialClick;
	private JScrollPane scroll;

	public GameFrame() {
		GameHandler.setGameFrame(this);
		setTitle("Mafia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(0, 2));

		newComponents();
		setComponents();
		addComponents();

		this.addMouseListener(new MoveWindows());
		this.addMouseMotionListener(new MoveWindows());
		setUndecorated(true);
		setVisible(true);
	}

	public void newComponents() {
		leftPanel = new LeftPanel();
		rightPanel = new RightPanel();
		chatArea = new JTextArea();
		scroll = new JScrollPane(chatArea);
		chatTf = new JTextField();
		sendBtn = new JButton();
	}

	public void setComponents() {

		leftPanel.setLayout(null);

		chatArea.setOpaque(true); // * 투명 * //
		chatArea.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		chatArea.setFont(new Font("", Font.BOLD, 20));
		chatArea.setForeground(Color.BLACK);

		scroll.setBounds(60, 40, 430, 400);

		chatTf.setBounds(60, 480, 350, 50);
		chatTf.setFont(new Font("", Font.BOLD, 20));
		
		sendBtn.setBounds(420, 480, 70, 50);

	}

	public void addComponents() {
		add(leftPanel);
		add(rightPanel);

		leftPanel.add(scroll);
		leftPanel.add(chatTf);
		leftPanel.add(sendBtn);

	}

	class LeftPanel extends JPanel {
		Image leftBackImg = new ImageIcon("backgroundImage/gameLeftBack.png").getImage();

		public LeftPanel() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(leftBackImg, 0, 0, this);
		}
	}

	class RightPanel extends JPanel {
		Image rightBackImg = new ImageIcon("backgroundImage/gameRightBack.png").getImage();

		public RightPanel() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(rightBackImg, 0, 0, this);
		}
	}

	class MoveWindows extends MouseAdapter { // * 프레임 이동 * //
		public void mousePressed(MouseEvent e) {
			initialClick = e.getPoint();
			getComponentAt(initialClick);
		}

		public void mouseDragged(MouseEvent e) {
			JFrame jf = (JFrame) e.getSource();

			int thisX = jf.getLocation().x;
			int thisY = jf.getLocation().y;

			int xMoved = e.getX() - initialClick.x;
			int yMoved = e.getY() - initialClick.y;

			int X = thisX + xMoved;
			int Y = thisY + yMoved;
			jf.setLocation(X, Y);
		}
	}

	public void addLog(String log) {

		chatArea.append(log + "\n"); // 로그 내용을 JTextArea 위에 붙여주고
		chatArea.setCaretPosition(chatArea.getDocument().getLength()); // 맨아래로 스크롤한다.
	}

	public static void main(String[] args) {
		new GameFrame();
	}

}
