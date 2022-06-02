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
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;
import handling.netty.ClientHandler;
import handlinig.packet.WaitingRoomPacket;
import information.ClientInf;
import information.FrameLocation;
import information.UserInf;

/**
 * 대기실 프레임
 */


public class WaitingRoomFrame extends JFrame {

	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel bottomPanel;
	private JPanel centerPanel;
	private JPanel levelPanel;
	private JPanel tierPanel;
	private JScrollPane scroll;
	private JButton inviteBtn;
	private JButton readyBtn;
	private JButton viewRankingBtn;
	private JButton quitBtn;
	private JLabel tierLabel;
	private JLabel levelLabel;
	private JButton nickNameLabel;
	private JProgressBar expBar;
	private Image leftBackImg;
	private Image rightBackImg;
	private Image bottomBackImg;
	private JButton levelBack;
	private Point initialClick;
	WaitingRowsPanel rowsPanel;
	public static HashMap<Integer,String> userList;
	public static HashMap<Integer,WaitingRoomPanel> userPanel;

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
	
	public JButton getReadyBtn() {
		return readyBtn;
	}

	public WaitingRoomFrame() {
		FrameHandler.setWaitingRoomFrame(this);
		setSize(1100, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(FrameLocation.X,FrameLocation.Y);
		setResizable(false);
		setUndecorated(true);
		setLayout(new GridLayout(0, 2));

		userList = new HashMap<>();// * 대기실 유저 목록 * //
		userPanel = new HashMap<>();// * 유저패널 * //
		newComponents();
		setComponents();
		addComponents();
		addActionBtn();
		
		this.addMouseListener(new MoveWindow());
		this.addMouseMotionListener(new MoveWindow());

		setVisible(true);
	}

	public void newComponents() {
		leftPanel = new RobyLeftPanelBackground();
		rightPanel = new CenterPanelBackground();
		centerPanel = new JPanel();
		bottomPanel = new BottomPanelBackground();
		tierPanel = new JPanel();
		levelPanel = new JPanel();
		rowsPanel = new WaitingRowsPanel();
		scroll = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		readyBtn = new JButton(new ImageIcon("btnImg/readyBtn.png"));
		inviteBtn = new JButton(new ImageIcon("btnImg/inviteBtn.png"));
		viewRankingBtn = new JButton(new ImageIcon("btnImg/viewRank.png"));
		quitBtn = new JButton(new ImageIcon("btnImg/quitBtn.png"));

		tierLabel = new JLabel("");
		levelLabel = new JLabel(" LV.100");
		nickNameLabel = new JButton("NICKNAME");

		expBar = new JProgressBar(0, 100);
		expBar.setForeground(Color.BLACK);
		expBar.setFont(new Font("", Font.BOLD, 20));

		leftBackImg = new ImageIcon("backgroundImage/waitingLeftBack.png").getImage();
		rightBackImg = new ImageIcon("backgroundImage/waitingRightBack.png").getImage();
		bottomBackImg = new ImageIcon("backgroundImage/waitingBottomBack.png").getImage();
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

		readyBtn.setBounds(50, 40, 450, 60);
		inviteBtn.setBounds(50, 130, 450, 60);
		viewRankingBtn.setBounds(50, 220, 450, 60);
		tierPanel.setBounds(60, 315, 180, 200);
		levelLabel.setBounds(253, 360, 80, 30);
		nickNameLabel.setBounds(250, 410, 250, 70);

		btnInvisible(quitBtn);
		btnInvisible(inviteBtn);
		btnInvisible(viewRankingBtn);
		btnInvisible(nickNameLabel);
		btnInvisible(readyBtn);

		inviteBtn.setOpaque(false);
		inviteBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		inviteBtn.setFont(new Font("", Font.BOLD, 20));

		quitBtn.setPreferredSize(new Dimension(100, 40)); // * 방만들기 버튼 크기조정 * //

		levelLabel.setFont(new Font("", Font.BOLD, 18));
		levelLabel.setForeground(Color.BLACK);
		nickNameLabel.setFont(new Font("", Font.BOLD, 30));

		tierLabel.setIcon(new ImageIcon("tierImage/0.jpg"));
		
		readyBtn.setPressedIcon(new ImageIcon("btnImg/readyBtnPush.png"));
		inviteBtn.setPressedIcon(new ImageIcon("btnImg/inviteBtnPush.png"));
		quitBtn.setPressedIcon(new ImageIcon("btnImg/quitBtnPush.png"));
		viewRankingBtn.setPressedIcon(new ImageIcon("btnImg/viewRankPush.png"));
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

		leftPanel.add(readyBtn);
		leftPanel.add(inviteBtn);
		leftPanel.add(viewRankingBtn);
		leftPanel.add(tierPanel);
		leftPanel.add(nickNameLabel);
		leftPanel.add(levelLabel);
		leftPanel.add(expBar);

		rightPanel.add(scroll, BorderLayout.CENTER);
		rightPanel.add(bottomPanel, BorderLayout.SOUTH);

		bottomPanel.add(quitBtn);
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
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler.send(WaitingRoomPacket.quitWaitingRoom(ClientInf.getUserId()));
				FrameHandler.quitWaittingFrame();
			}
		});
		
		readyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ClientInf.isReadyState()) {
					System.out.println("준비완료");
					ClientInf.setReadyState(false);
					ClientHandler.send(WaitingRoomPacket.makeReadyPacket(ClientInf.isReadyState()));
					readyBtn.setIcon(new ImageIcon("btnImg/readyXBtn.png"));
					readyBtn.setPressedIcon(new ImageIcon("btnImg/readyXBtnPush.png"));
				} else {
					System.out.println("준비해제상태");
					ClientInf.setReadyState(true);
					ClientHandler.send(WaitingRoomPacket.makeReadyPacket(ClientInf.isReadyState()));
					readyBtn.setIcon(new ImageIcon("btnImg/readyBtn.png"));
					readyBtn.setPressedIcon(new ImageIcon("btnImg/readyBtnPush.png"));
				}
			}
		});
	}
	

	class CenterPanelBackground extends JPanel { // * 대기실 오른쪽 배경 이미지 * //
		public CenterPanelBackground() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(rightBackImg, 0, 0, this);
		}
	}

	class RobyLeftPanelBackground extends JPanel { // * 대기실에서 left패널 이미지 * //
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
		new WaitingRoomFrame();
	}

}
