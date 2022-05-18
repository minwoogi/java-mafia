package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



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
	LobbyRowsPanel rowsPanel;

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

	public WaitingRoomFrame() {
		FrameHandler.setWaitingRoomFrame(this);
		setTitle("대기실");
		setSize(1100, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(0, 2));

		newComponents();
		setComponents();
		addComponents();
		addActionBtn();

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
		scroll = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		readyBtn = new JButton("준비완료");
		inviteBtn = new JButton("초대하기");
		viewRankingBtn = new JButton(new ImageIcon("btnImg/viewRank.png"));
		quitBtn = new JButton(new ImageIcon("btnImg/backBtn.png"));

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

		readyBtn.setBounds(50, 40, 450, 60);
		inviteBtn.setBounds(50, 130, 450, 60);
		viewRankingBtn.setBounds(50, 220, 450, 60);
		tierPanel.setBounds(50, 315, 180, 200);
		levelLabel.setBounds(253, 360, 80, 30);
		nickNameLabel.setBounds(250, 410, 250, 70);

//		btnInvisible(rankMatchBtn);
		btnInvisible(quitBtn);
		btnInvisible(viewRankingBtn);
		btnInvisible(nickNameLabel);

		inviteBtn.setOpaque(false);
		inviteBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		inviteBtn.setFont(new Font("", Font.BOLD, 20));

		quitBtn.setPreferredSize(new Dimension(100, 40)); // * 방만들기 버튼 크기조정 * //

		levelLabel.setFont(new Font("", Font.BOLD, 18));
		levelLabel.setForeground(Color.BLACK);
		nickNameLabel.setFont(new Font("", Font.BOLD, 30));

		tierLabel.setIcon(new ImageIcon("tierImage/0.jpg"));
		readyBtn.setPressedIcon(new ImageIcon("btnImg/rankMatchPush.png"));
		quitBtn.setPressedIcon(new ImageIcon("btnImg/backBtnPush.png"));
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

	public static void main(String[] args) {
		new WaitingRoomFrame();
	}

}
