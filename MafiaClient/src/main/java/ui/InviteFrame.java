package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import information.FrameLocation;
import ui.LobbyFrame.MoveWindow;

public class InviteFrame extends JFrame {

	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	private JButton closeBtn;
	private JScrollPane scroll;
	private Image background;
	InviteRowsPanel inviteRowsPanel;
	Point initialClick;

	public InviteFrame() {
		FrameHandler.setInviteFrame(this);
		setSize(350, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(FrameLocation.X, FrameLocation.Y);
		setUndecorated(true);

		newComponents();
		setComponents();
		addComponents();

		this.addMouseListener(new MoveWindows());
		this.addMouseMotionListener(new MoveWindows());
		setVisible(true);
	}

	public void newComponents() {
		mainPanel = new BackGroundPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		closeBtn = new JButton(new ImageIcon("btnImg/close.png")); // * 닫기 * //
		scroll = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		inviteRowsPanel = new InviteRowsPanel();
		background = new ImageIcon("backgroundImage/GameLeftBack.png").getImage();

	}

	public void setComponents() {
		mainPanel.setOpaque(false);
		mainPanel.setBackground(new Color(0, 0, 0, 0));
		mainPanel.setLayout(new BorderLayout());

		centerPanel.setOpaque(false);
		centerPanel.setBackground(new Color(0, 0, 0, 0));

		scroll.setOpaque(false);
		scroll.setBackground(new Color(0, 0, 0, 0));
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(BorderFactory.createEmptyBorder()); // * 스크롤팬 테두리 제거 * //

		closeBtn.setPressedIcon(new ImageIcon("btnImg/closePush.png"));
		closeBtn.setFocusPainted(false);
		closeBtn.setContentAreaFilled(false);
		closeBtn.setBorderPainted(false);
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		bottomPanel.setOpaque(false);
		bottomPanel.setBackground(new Color(0,0,0,0));
		
		inviteRowsPanel.setOpaque(false);
		inviteRowsPanel.setBackground(new Color(0, 0, 0, 0));

	}

	public void addComponents() {
		add(mainPanel);
		mainPanel.add(scroll, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.add(closeBtn);
		centerPanel.add(inviteRowsPanel);
	}

	class InviteRowsPanel extends JPanel {

		public InviteRowsPanel() {
			setLayout(new GridLayout(0, 1, 0, 0));
			InviteUserPanel userPanel = new InviteUserPanel("minwook");
			InviteUserPanel userPanel2 = new InviteUserPanel("minwook");
			InviteUserPanel userPanel3 = new InviteUserPanel("minwook");
			InviteUserPanel userPanel4 = new InviteUserPanel("minwook");
			InviteUserPanel userPanel5 = new InviteUserPanel("minwook");
			InviteUserPanel userPanel6 = new InviteUserPanel("minwook");

			addUserList(userPanel);
			addUserList(userPanel2);
			addUserList(userPanel3);
			addUserList(userPanel4);
			addUserList(userPanel5);
			addUserList(userPanel6);
			setVisible(true);

		}

		public void addUserList(InviteUserPanel inviteUserPanel) {
			this.add(inviteUserPanel);
			this.revalidate();
			this.repaint();
		}
	}

	class BackGroundPanel extends JPanel {
		public BackGroundPanel() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, this);
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
		new InviteFrame();
	}
}