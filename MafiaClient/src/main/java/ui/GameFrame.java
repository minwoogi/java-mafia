package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import handling.netty.ClientHandler;
import handlinig.packet.GamePacket;

public class GameFrame extends JFrame {

	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel votePanel;
	private JPanel doubtPanel;
	private JTextArea chatArea;
	private JTextField chatTf;
	private JTextField timer;
	private JButton sendBtn;
	private Point initialClick;
	private JScrollPane scroll;
	private JButton page;
	private JTextField nightInf;

	public JPanel getVotePanel() {
		return votePanel;
	}

	public JTextField getChatTf() {
		return chatTf;
	}

	public JTextArea getChatArea() {
		return chatArea;
	}

	public JPanel getDoubtPanel() {
		return doubtPanel;
	}

	public JTextField getNightInf() {
		return nightInf;
	}

	public JTextField getTimer() {
		return timer;
	}

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
		votePanel = new JPanel();
		doubtPanel = new JPanel();
		chatArea = new JTextArea();
		scroll = new JScrollPane(chatArea);
		chatTf = new JTextField();
		sendBtn = new JButton(new ImageIcon("btnImg/gameSendBtn.png"));
		nightInf = new JTextField();
		timer = new JTextField();
		page = new JButton(new ImageIcon("btnImg/rightArrow.png"));

	}

	public void setComponents() {

		leftPanel.setLayout(null);
		rightPanel.setLayout(null);

		votePanel.setBounds(60, 250, 430, 300);
		votePanel.setBackground(new Color(0, 0, 0, 150));
		votePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 21, 15));

		doubtPanel.setBounds(60, 250, 430, 300);
		doubtPanel.setBackground(new Color(0, 0, 0, 150));
		doubtPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 21, 15));
		doubtPanel.setVisible(false);

		Font btnFont = new Font("", Font.BOLD, 15);

		JButton btn1 = new JButton("No.1", new ImageIcon("job/1.png"));
		btn1.setPreferredSize(new Dimension(80, 80));
		btn1.setHorizontalTextPosition(JButton.CENTER);
		btn1.setFont(btnFont);
		btn1.setForeground(Color.red);
		btnInvisible(btn1);
		lineOverRap(btn1);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowMessage doubt = new ShowMessage();
				doubt.doubtJob(btn1);
			}
		});

		JButton btn2 = new JButton(new ImageIcon("job/2.png"));
		btn2.setPreferredSize(new Dimension(80, 80));
		JButton btn3 = new JButton(new ImageIcon("job/4.png"));
		btn3.setPreferredSize(new Dimension(80, 80));
		JButton btn4 = new JButton(new ImageIcon("job/0.png"));
		btn4.setPreferredSize(new Dimension(80, 80));
		JButton btn5 = new JButton();
		btn5.setPreferredSize(new Dimension(80, 80));
		JButton btn6 = new JButton();
		btn6.setPreferredSize(new Dimension(80, 80));
		JButton btn7 = new JButton();
		btn7.setPreferredSize(new Dimension(80, 80));
		JButton btn8 = new JButton();
		btn8.setPreferredSize(new Dimension(80, 80));
		JButton btn9 = new JButton();
		btn9.setPreferredSize(new Dimension(80, 80));
		votePanel.add(btn1);
		votePanel.add(btn2);
		votePanel.add(btn3);
		votePanel.add(btn4);
		votePanel.add(btn5);
		votePanel.add(btn6);
		votePanel.add(btn7);
		votePanel.add(btn8);
		votePanel.add(btn9);

		btnInvisible(sendBtn);
		sendBtn.setBounds(420, 500, 70, 50);
		sendBtn.setPressedIcon(new ImageIcon("btnImg/gameSendPush.png"));

		chatArea.setOpaque(true); // * 투명 * //
		chatArea.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		chatArea.setFont(new Font("", Font.BOLD, 20));
		chatArea.setForeground(Color.WHITE);
		chatArea.setBackground(new Color(0, 0, 0, 150));
		chatArea.setOpaque(false);
		chatArea.setEnabled(false);

		scroll.setBounds(60, 40, 430, 410);
		scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setOpaque(false);
		scroll.setBackground(new Color(0, 0, 0, 0));
		scroll.getViewport().setOpaque(false);

		chatTf.setBounds(60, 500, 350, 50);
		chatTf.setFont(new Font("", Font.BOLD, 20));
		chatTf.setBackground(new Color(0, 0, 0, 0));
		chatTf.setOpaque(false);
		chatTf.setForeground(Color.WHITE);

		nightInf.setBounds(60, 40, 430, 70);
		nightInf.setFont(new Font("", Font.BOLD, 20));
		nightInf.setBackground(new Color(0, 0, 0, 0));
		nightInf.setOpaque(false);
		nightInf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		nightInf.setForeground(Color.WHITE);
		nightInf.setHorizontalAlignment(JTextField.CENTER); // * 글자 가운데 정렬 * //
		nightInf.setEnabled(false);

		timer.setBounds(60, 150, 430, 70);
		timer.setFont(new Font("", Font.BOLD, 20));
		timer.setBackground(new Color(0, 0, 0, 0));
		timer.setOpaque(false);
		timer.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		timer.setForeground(Color.WHITE);
		timer.setHorizontalAlignment(JTextField.CENTER);
		timer.setEnabled(false);

		page.setBounds(255, 555, 35, 35);
		page.setPressedIcon(new ImageIcon("btnImg/rightArrowPush.png"));
		btnInvisible(page);

		page.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (votePanel.isVisible()) {
					votePanel.setVisible(false);
					doubtPanel.setVisible(true);
					page.setIcon(new ImageIcon("btnImg/leftArrow.png"));
					page.setPressedIcon(new ImageIcon("btnImg/leftArrowPush.png"));
				} else {
					votePanel.setVisible(true);
					doubtPanel.setVisible(false);
					page.setIcon(new ImageIcon("btnImg/rightArrow.png"));
					page.setPressedIcon(new ImageIcon("btnImg/rightArrowPush.png"));
				}

			}
		});

		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler.send(GamePacket.makeMessagePacket(chatTf.getText()));
			}
		});

	}

	public static void lineOverRap(JButton btn) {
		String text = "<html><body><center>";
		char[] arr = btn.getText().toCharArray();
		if (btn.getText().length() >= 8) {
			for (int i = 0; i < 4 ; i++) {
				text += arr[i];
			}
			text += "<br>";
			for (int i = 4; i < 8; i++) {
				text += arr[i];
			}
			text += "<br>";
			for(int i=8; i<arr.length; i++) {
				text += arr[i];
			}
			text += "</center></body></html>";
			btn.setText(text);

		} else if (btn.getText().length() >= 4) {
			for (int i = 0; i < 4; i++) {
				text += arr[i];
			}
			text += "<br>";
			for (int i = 4; i < arr.length; i++) {
				text += arr[i];
			}
			text += "</center></body></html>";
			btn.setText(text);
		}
	}

	public void addComponents() {
		add(leftPanel);
		add(rightPanel);

		leftPanel.add(scroll);
		leftPanel.add(chatTf);
		leftPanel.add(sendBtn);

		rightPanel.add(nightInf);
		rightPanel.add(timer);
		rightPanel.add(votePanel);
		rightPanel.add(page);
		rightPanel.add(doubtPanel);
	}

	public void btnInvisible(JButton btn) { // * 버튼 투명화(이미지 보이게) * //
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
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
