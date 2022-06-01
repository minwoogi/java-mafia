package ui;

import java.awt.BorderLayout;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;

import handling.netty.ClientHandler;
import handlinig.packet.LobbyPacket;
import information.ClientInf;
import information.FrameLocation;
import information.LocationInformation;
import information.RoomInf;

public class LobbyFrame extends JFrame {

	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel bottomPanel;
	private JPanel centerPanel;
	private JPanel levelPanel;
	private JPanel tierPanel;
	private JScrollPane scroll;
	private JTextField searchRoomTf;
	private JButton rankMatchBtn;
	private JButton viewRankingBtn;
	private JButton searchRoomBtn;
	private JButton makeRoomBtn;
	private JLabel tierLabel;
	private JLabel levelLabel;
	private JButton nickNameLabel;
	private JProgressBar expBar;
	private Image leftBackImg;
	private Image rightBackImg;
	private Image bottomBackImg;
	private JButton levelBack;
	private Point initialClick;
	private JButton logOutBtn;
	LobbyRowsPanel rowsPanel;
	Map<Integer, LobbyRoomPanel> roomList;

	public Map<Integer, LobbyRoomPanel> getRoomList() {
		return roomList;
	}

	public JLabel getTierLabel() {
		return tierLabel;
	}

	public JLabel getLevelLabel() {
		return levelLabel;
	}

	public JButton getNickNameLabel() {
		return nickNameLabel;
	}

	public JProgressBar getExpBar() {
		return expBar;
	}

	public LobbyFrame() {
		FrameHandler.setLobbyFrame(this);
		setTitle("Roby");
		setSize(1100, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(FrameLocation.X,FrameLocation.Y);
		setResizable(false);
		setLayout(new GridLayout(0, 2));

		roomList = new HashMap<>();
		newComponents();
		setComponents();
		addComponents();
		addActionBtn();

		this.addMouseListener(new MoveWindow());
		this.addMouseMotionListener(new MoveWindow());

		setUndecorated(true);
		setVisible(true);
	}

	public void newComponents() {
		leftPanel = new RobyLeftPanelBackground();
		rightPanel = new CenterPanelBackground();
		centerPanel = new JPanel();
		bottomPanel = new BottomPanelBackground();
		tierPanel = new JPanel();
		levelPanel = new JPanel();
		rowsPanel = new LobbyRowsPanel();
		scroll = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		rankMatchBtn = new JButton(new ImageIcon("btnImg/rankMatchBtn.png"));
		searchRoomTf = new JTextField("방 검색");
		viewRankingBtn = new JButton(new ImageIcon("btnImg/viewRank.png"));
		searchRoomBtn = new JButton(new ImageIcon("btnImg/searchRoom.png"));
		makeRoomBtn = new JButton(new ImageIcon("btnImg/makeRoom.png"));
		logOutBtn = new JButton(new ImageIcon("btnImg/logOut.png"));

		tierLabel = new JLabel("");
		levelLabel = new JLabel(" LV.100");
		nickNameLabel = new JButton("NICKNAME");

		expBar = new JProgressBar(0, 100);
		expBar.setForeground(Color.BLACK);
		expBar.setFont(new Font("", Font.BOLD, 20));

		leftBackImg = new ImageIcon("backgroundImage/leftPanelBack.png").getImage();
		rightBackImg = new ImageIcon("backgroundImage/rightPanelBack.png").getImage();
		bottomBackImg = new ImageIcon("backgroundImage/bottomBack.png").getImage();
		levelBack = new JButton(new ImageIcon("btnImg/nickNameLabel.png"));
	}

	public void setComponents() {
		tierPanel.setBackground(Color.black);
		levelPanel.setBackground(new Color(212, 211, 210));

		leftPanel.setLayout(null);
		bottomPanel.setLayout(new FlowLayout());
		rightPanel.setLayout(new BorderLayout());
		tierPanel.setLayout(new BorderLayout());
		levelPanel.setLayout(new BorderLayout());

		rankMatchBtn.setBounds(50, 40, 450, 60);
		searchRoomTf.setBounds(60, 130, 370, 60);
		searchRoomBtn.setBounds(430, 125, 75, 70);
		viewRankingBtn.setBounds(50, 220, 450, 60);
		tierPanel.setBounds(50, 315, 180, 200);
		levelLabel.setBounds(253, 360, 80, 30);
		nickNameLabel.setBounds(250, 410, 250, 70);
		logOutBtn.setBounds(0, 540, 60, 60);

		btnInvisible(rankMatchBtn);
		btnInvisible(makeRoomBtn);
		btnInvisible(searchRoomBtn);
		btnInvisible(viewRankingBtn);
		btnInvisible(nickNameLabel);
		btnInvisible(logOutBtn);

		searchRoomTf.setOpaque(false);
		searchRoomTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		searchRoomTf.setFont(new Font("", Font.BOLD, 20));

		makeRoomBtn.setPreferredSize(new Dimension(100, 40)); // * 방만들기 버튼 크기조정 * //

		levelLabel.setFont(new Font("", Font.BOLD, 18));
		levelLabel.setForeground(Color.BLACK);
		nickNameLabel.setFont(new Font("", Font.BOLD, 30));

		tierLabel.setIcon(new ImageIcon("tierImage/0.jpg"));
		rankMatchBtn.setPressedIcon(new ImageIcon("btnImg/rankMatchPush.png"));
		makeRoomBtn.setPressedIcon(new ImageIcon("btnImg/makeRoomPush.png"));
		searchRoomBtn.setPressedIcon(new ImageIcon("btnImg/searchRoomPush.png"));
		viewRankingBtn.setPressedIcon(new ImageIcon("btnImg/viewRankPush.png"));
		logOutBtn.setPressedIcon(new ImageIcon("btnImg/logOutPush.png"));
		nickNameLabel.setIcon(new ImageIcon("btnImg/nickNameLabel.png"));

		nickNameLabel.setHorizontalTextPosition(JButton.CENTER); // 텍스트 가운데

		scroll.setBorder(BorderFactory.createEmptyBorder()); // * 스크롤팬 테두리 제거 * //

		Border border = BorderFactory.createTitledBorder("EXP");
		expBar.setValue(50);
		expBar.setBorder(border);
		expBar.setBorderPainted(true);
		expBar.setStringPainted(true);
		expBar.setOpaque(false);
		expBar.setBounds(320, 350, 170, 50);
		expBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		tierPanel.setOpaque(true);
		tierPanel.setBackground(new Color(0, 0, 0, 0));
		levelPanel.setOpaque(true);
		levelPanel.setBackground(new Color(0, 0, 0, 0));

		btnInvisible(nickNameLabel);

		levelBack.setBounds(250, 340, 250, 70);
		btnInvisible(levelBack);

		centerPanel.setOpaque(false);
		centerPanel.setBackground(new Color(0, 0, 0, 0));
		scroll.setOpaque(false);
		scroll.setBackground(new Color(0, 0, 0, 0));
		scroll.getViewport().setOpaque(false);
		rowsPanel.setOpaque(false);
		rowsPanel.setBackground(new Color(0, 0, 0, 0));
		tierPanel.setBackground(new Color(0, 0, 0, 0));
		tierPanel.setOpaque(false);
		tierLabel.setOpaque(false);
		tierLabel.setBackground(new Color(0, 0, 0, 0));

	}

	public void addComponents() {
		add(leftPanel);
		add(rightPanel);

		leftPanel.add(rankMatchBtn);
		leftPanel.add(searchRoomTf);
		leftPanel.add(searchRoomBtn);
		leftPanel.add(viewRankingBtn);
		leftPanel.add(tierPanel);
		leftPanel.add(nickNameLabel);
		leftPanel.add(levelLabel);
		leftPanel.add(expBar);
		leftPanel.add(logOutBtn);

		rightPanel.add(scroll, BorderLayout.CENTER);
		rightPanel.add(bottomPanel, BorderLayout.SOUTH);

		bottomPanel.add(makeRoomBtn);
		centerPanel.add(rowsPanel);

		tierPanel.add(tierLabel, BorderLayout.CENTER);

		leftPanel.add(levelBack);

	}

	public void btnInvisible(JButton btn) { // * 버튼 투명화(이미지 보이게) * //
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
	}

	public void addActionBtn() {
		makeRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MakeRoom();
			}
		});

		searchRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameHandler.removeAllPanel();
				if (!searchRoomTf.getText().equals("")) {
					String keyword = searchRoomTf.getText();
					if (roomList.containsValue(keyword)) {
						//FrameHandler.addRoomPanel(roomList.get(getKey(roomList, keyword)));
					}
				} else {
					roomList.forEach((key, value) -> {
						//FrameHandler.addRoomPanel(roomList.get(key));
					});
				}
			}
		});

		logOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int isLogOut = JOptionPane.showConfirmDialog(getLayeredPane(),"로그아웃 하시겠습니까?", "LogOut",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (isLogOut == 0) {
					ClientHandler.send(LobbyPacket.makeLogOutPacket(ClientInf.getUserId()));
				}
			}
		});
	}

	public static <Integer, RoomInf> Integer getKey(Map<Integer, RoomInf> map, String value) { // * value값으로 key 얻기 * //
		for (Integer key : map.keySet()) {
			if (value.equals(map.get(key))) {
				return key;
			}
		}
		return null;
	}

	class CenterPanelBackground extends JPanel { // * 로비 오른쪽 배경 이미지 * //
		public CenterPanelBackground() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(rightBackImg, 0, 0, this);
		}
	}

	class RobyLeftPanelBackground extends JPanel { // * 로비에서 left패널 이미지 * //
		public RobyLeftPanelBackground() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(leftBackImg, 0, 0, this);
		}
	}

	class BottomPanelBackground extends JPanel { // * 바텀 패널 이미지 * //
		public BottomPanelBackground() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bottomBackImg, 0, 0, this);
		}
	}

	class MoveWindow extends MouseAdapter { // * 프레임 이동 * //
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
		new LobbyFrame();
	}

}
