package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LobbyFrame extends JFrame {

	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel bottomPanel;
	private JPanel centerPanel;
	private JPanel levelPanel;
	private JPanel nickNamePanel;
	private JPanel tierPanel;
	private JPanel nicklevelPanel;
	private JScrollPane scroll;
	private JTextField searchRoomTf;
	private JButton rankMatchBtn;
	private JButton viewRankingBtn;
	private JButton searchRoomBtn;
	private JButton makeRoomBtn;
	private JLabel tierLabel;
	private JLabel levelLabel;
	private JLabel nickNameLabel;
	private JProgressBar expBar;
	private Image leftBackImg;
	private Image rightBackImg;
	private Image bottomBackImg;
	RowsPanel rowsPanel;

	public JLabel getTierLabel() {
		return tierLabel;
	}

	public JLabel getLevelLabel() {
		return levelLabel;
	}

	public JLabel getNickNameLabel() {
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
		setLocationRelativeTo(null);
		setLayout(new GridLayout(0, 2));

		newComponents();
		setComponents();
		addComponents();
		addActionBtn();

		setVisible(true);
	}

	public void newComponents() {
		leftPanel = new RobyLeftPanelBackground();
		rightPanel = new JPanel();
		centerPanel = new CenterPanelBackground();
		bottomPanel = new BottomPanelBackground();
		tierPanel = new JPanel();
		nickNamePanel = new JPanel();
		levelPanel = new JPanel();
		nicklevelPanel = new JPanel();
		rowsPanel = new RowsPanel();
		scroll = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		rankMatchBtn = new JButton("랭크 매칭(준비중)");
		searchRoomTf = new JTextField("일반 방 검색(키워드)");
		viewRankingBtn = new JButton("랭킹 보기(준비중)");
		searchRoomBtn = new JButton("방검색");
		makeRoomBtn = new JButton("방만들기");

		tierLabel = new JLabel("");
		levelLabel = new JLabel("");
		nickNameLabel = new JLabel("");

		expBar = new JProgressBar(0, 100);

		leftBackImg = new ImageIcon("backgroundImage/leftPanelBack.png").getImage();
		rightBackImg = new ImageIcon("backgroundImage/rightPanelBack.png").getImage();
		bottomBackImg = new ImageIcon("backgroundImage/bottomBack.png").getImage();
	}

	public void setComponents() {
		leftPanel.setBackground(Color.GRAY);
		tierPanel.setBackground(Color.black);
		levelPanel.setBackground(Color.pink);

		leftPanel.setLayout(null);
		bottomPanel.setLayout(new FlowLayout());
		rightPanel.setLayout(new BorderLayout());
		nicklevelPanel.setLayout(new GridLayout(2, 0));
		tierPanel.setLayout(new BorderLayout());

		rankMatchBtn.setBounds(50, 40, 450, 60);
		searchRoomTf.setBounds(50, 130, 380, 60);
		searchRoomBtn.setBounds(440, 130, 60, 60);
		viewRankingBtn.setBounds(50, 220, 450, 60);
		tierPanel.setBounds(50, 315, 180, 200);
		nicklevelPanel.setBounds(250, 315, 250, 200);

		makeRoomBtn.setPreferredSize(new Dimension(100, 40)); // * 방만들기 버튼 크기조정 * //

		tierLabel.setIcon(new ImageIcon("tierImage/0.jpg"));

		scroll.setBorder(BorderFactory.createEmptyBorder()); // * 스크롤팬 테두리 제거 * //

		Border border = BorderFactory.createTitledBorder("EXP");
		expBar.setValue(50);
		expBar.setBorder(border);
		expBar.setStringPainted(true);
		expBar.setOpaque(false);

	}

	public void addComponents() {
		add(leftPanel);
		add(rightPanel);

		leftPanel.add(rankMatchBtn);
		leftPanel.add(searchRoomTf);
		leftPanel.add(searchRoomBtn);
		leftPanel.add(viewRankingBtn);
		leftPanel.add(tierPanel);
		leftPanel.add(nicklevelPanel);

		nicklevelPanel.add(levelPanel);
		nicklevelPanel.add(nickNamePanel);

		rightPanel.add(scroll, BorderLayout.CENTER);
		rightPanel.add(bottomPanel, BorderLayout.SOUTH);

		bottomPanel.add(makeRoomBtn);
		centerPanel.add(rowsPanel);

		tierPanel.add(tierLabel, BorderLayout.CENTER);
		levelPanel.add(levelLabel);
		levelPanel.add(expBar);
		nickNamePanel.add(nickNameLabel);
	}

	public void addActionBtn() {
		makeRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MakeRoom();

			}
		});

		expBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

			}
		});

		searchRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyword = searchRoomTf.getText();
			}
		});
	}

	class CenterPanelBackground extends JPanel {
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

	public static void main(String[] args) {
		new LobbyFrame();

	}

}
