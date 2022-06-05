package ui;

import java.awt.Color;
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
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import handling.game.GameHandler;
import handling.netty.ClientHandler;
import handlinig.packet.GamePacket;
import information.FrameLocation;

public class GameFrame extends JFrame {

	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel votePanel;
	private JPanel doubtPanel;
	private JPanel centerChatPanel;
	private GameChatPanel chatPanel;
	private JTextField chatTf;
	private JTextField timer;
	private JButton sendBtn;
	private Point initialClick;
	private JScrollPane scroll;
	private JButton page;
	private JTextField nightInf;
	private JButton vote;

	public static HashMap<Integer, JButton> btnMap = new HashMap<>();
	public static HashMap<JButton, Integer> btnState = new HashMap<>(); // 1선택 0선택X
	public static HashMap<JButton, ActionListener> btnHandler = new HashMap<>();

	
	public GameChatPanel getChatPanel() {
		return chatPanel;
	}
	
	public JPanel getVotePanel() {
		return votePanel;
	}
	

	public JTextField getChatTf() {
		return chatTf;
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
		setLocation(FrameLocation.X,FrameLocation.Y);
		setLayout(new GridLayout(0, 2));

		newComponents();
		setComponents();
		addComponents();
		
		
		this.addMouseListener(new MoveWindows());
		this.addMouseMotionListener(new MoveWindows());
		setUndecorated(true);
		setVisible(true);
		
		GameHandler.addPersonList(votePanel, 1);
		GameHandler.deadBtnSetting(btnMap.get(1), 1);
		GameHandler.addTextPanel(6, 20, "No.1 : 안녕하세요 안녕하세요 안녕하세요 안");
		GameHandler.addTextPanel(6, 20, "No.1 : 안녕하세요 안녕하세요 안녕하세요");
		GameHandler.addTextPanel(6, 20, "No.1 : 안녕하세요 안녕하세요 안녕하세요");
		
		this.revalidate();
		this.repaint();
	
	}

	public void newComponents() {
		leftPanel = new LeftPanel();
		rightPanel = new RightPanel();
		votePanel = new JPanel();
		doubtPanel = new JPanel();
		centerChatPanel = new JPanel();
		chatPanel = new GameChatPanel();
		scroll = new JScrollPane(centerChatPanel,scroll.VERTICAL_SCROLLBAR_AS_NEEDED,scroll.HORIZONTAL_SCROLLBAR_NEVER);
		chatTf = new JTextField(25);
		sendBtn = new JButton(new ImageIcon("btnImg/gameSendBtn.png"));
		nightInf = new JTextField();
		timer = new JTextField();
		page = new JButton(new ImageIcon("btnImg/rightArrow.png"));
		vote = new JButton(new ImageIcon("btnImg/vote.png"));
		

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

		centerChatPanel.setBackground(new Color(0,0,0,0));
		btnInvisible(sendBtn);
		btnInvisible(vote);
		sendBtn.setBounds(420, 500, 70, 50);
		sendBtn.setPressedIcon(new ImageIcon("btnImg/gameSendPush.png"));

		scroll.setBounds(60, 40, 430, 410);
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

		vote.setBounds(233, 555, 93, 38);
		vote.setPressedIcon(new ImageIcon("btnImg/votePush.png"));
		vote.addActionListener(new ActionListener() { // * 투표버튼 * //
			public void actionPerformed(ActionEvent e) {
				Iterator<Integer> mapIter = GameHandler.getGameFrame().btnMap.keySet().iterator();
				while (mapIter.hasNext()) {
					Integer key = mapIter.next();
					if (GameHandler.getGameFrame().btnState.get(GameHandler.getGameFrame().btnMap.get(key)) == 1) {
						ClientHandler.send(GamePacket.makeVotePacket(key));
					}
				}
			}
		});

		page.setBounds(500, 380, 35, 35);
		page.setPressedIcon(new ImageIcon("btnImg/rightArrowPush.png"));
		btnInvisible(page);

		page.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (votePanel.isVisible()) {
					votePanel.setVisible(false);
					doubtPanel.setVisible(true);
					vote.setVisible(false);
					page.setBounds(15, 380, 35, 35);
					page.setIcon(new ImageIcon("btnImg/leftArrow.png"));
					page.setPressedIcon(new ImageIcon("btnImg/leftArrowPush.png"));
				} else {
					votePanel.setVisible(true);
					doubtPanel.setVisible(false);
					vote.setVisible(true);
					page.setBounds(500, 380, 35, 35);
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
		rightPanel.add(vote);
		centerChatPanel.add(chatPanel);
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

	public static void main(String[] args) {
		new GameFrame();
	}

}
