package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	private JPanel listPanel;
	private JTextArea chatArea;
	private JTextField chatTf;
	private JTextField nightInf;
	private JTextField timer;
	private JButton sendBtn;
	private Point initialClick;
	private JScrollPane scroll;
	private JButton page;

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
		listPanel = new JPanel();
		chatArea = new JTextArea();
		scroll = new JScrollPane(chatArea);
		chatTf = new JTextField();
		sendBtn = new JButton(new ImageIcon("btnImg/gameSendBtn.png"));
		nightInf = new JTextField();
		timer = new JTextField();
		page = new JButton();
		
	}

	public void setComponents() {

		leftPanel.setLayout(null);
		rightPanel.setLayout(null);
		
		listPanel.setBounds(60, 250, 430, 300);
		listPanel.setBackground(new Color(0,0,0,150));
		listPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,15));
		
		page.setBounds(255, 555, 35, 35);
		
		JButton btn1 = new JButton();
		btn1.setPreferredSize(new Dimension(80,80));
		JButton btn2 = new JButton();
		btn2.setPreferredSize(new Dimension(80,80));
		JButton btn3 = new JButton();
		btn3.setPreferredSize(new Dimension(80,80));
		JButton btn4 = new JButton();
		btn4.setPreferredSize(new Dimension(80,80));
		JButton btn5 = new JButton();
		btn5.setPreferredSize(new Dimension(80,80));
		JButton btn6 = new JButton();
		btn6.setPreferredSize(new Dimension(80,80));
		JButton btn7 = new JButton();
		btn7.setPreferredSize(new Dimension(80,80));
		JButton btn8 = new JButton();
		btn8.setPreferredSize(new Dimension(80,80));
		JButton btn9 = new JButton();
		btn9.setPreferredSize(new Dimension(80,80));
		listPanel.add(btn1);
		listPanel.add(btn2);
		listPanel.add(btn3);
		listPanel.add(btn4);
		listPanel.add(btn5);
		listPanel.add(btn6);
		listPanel.add(btn7);
		listPanel.add(btn8);
		listPanel.add(btn9);
		
		sendBtn.setFocusPainted(false);
		sendBtn.setContentAreaFilled(false);
		sendBtn.setBorderPainted(false);
		sendBtn.setPressedIcon(new ImageIcon("btnImg/gameSendPush.png"));

		chatArea.setOpaque(true); // * 투명 * //
		chatArea.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		chatArea.setFont(new Font("", Font.BOLD, 20));
		chatArea.setForeground(Color.WHITE);
		chatArea.setBackground(new Color(0,0,0,150));
		chatArea.setOpaque(false);		

		scroll.setBounds(60, 40, 430, 410);
		scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setOpaque(false);
		scroll.setBackground(new Color(0, 0, 0, 0));
		scroll.getViewport().setOpaque(false);
		
		chatTf.setBounds(60, 500, 350, 50);
		chatTf.setFont(new Font("", Font.BOLD, 20));
		chatTf.setBackground(new Color(0,0,0,0));
		chatTf.setOpaque(false);
		chatTf.setForeground(Color.WHITE);
		
		nightInf.setBounds(60, 40, 430,70);
		nightInf.setFont(new Font("",Font.BOLD,20));
		nightInf.setBackground(new Color(0,0,0,0));
		nightInf.setOpaque(false);
		nightInf.setForeground(Color.WHITE);
		nightInf.setHorizontalAlignment(JTextField.CENTER); // * 글자 가운데 정렬 * //
		
		timer.setBounds(60, 150, 430,70);
		timer.setFont(new Font("",Font.BOLD,20));
		timer.setBackground(new Color(0,0,0,0));
		timer.setOpaque(false);
		timer.setForeground(Color.WHITE);
		timer.setHorizontalAlignment(JTextField.CENTER);
		
		sendBtn.setBounds(420, 500, 70, 50);

	}

	public void addComponents() {
		add(leftPanel);
		add(rightPanel);

		leftPanel.add(scroll);
		leftPanel.add(chatTf);
		leftPanel.add(sendBtn);
		
		rightPanel.add(nightInf);
		rightPanel.add(timer);
		rightPanel.add(listPanel);
		rightPanel.add(page);
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
